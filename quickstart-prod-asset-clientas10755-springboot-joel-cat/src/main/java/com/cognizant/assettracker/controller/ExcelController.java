package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.events.UploadProgressEvent;
import com.cognizant.assettracker.models.dto.EmployeeUpdateDTO;
import com.cognizant.assettracker.models.dto.ExcelBadRequestDTO;
import com.cognizant.assettracker.models.dto.ExcelExistingFieldResponseDTO;
import com.cognizant.assettracker.models.dto.UploadProgressDTO;
import com.cognizant.assettracker.models.entity.ExcelDetail;
import com.cognizant.assettracker.models.enums.ExcelBadRequestType;
import com.cognizant.assettracker.models.exceptions.ExcelTemplateException;
import com.cognizant.assettracker.models.exceptions.FileTooLargeException;
import com.cognizant.assettracker.models.exceptions.NoDocumentsException;
import com.cognizant.assettracker.models.exceptions.UnsupportedFileException;

import com.cognizant.assettracker.services.ExcelService;
import com.cognizant.assettracker.services.ExcelUpdateService;
import com.cognizant.assettracker.services.NotificationsService;
import com.cognizant.assettracker.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import reactor.core.publisher.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.concurrent.CompletableFuture.runAsync;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/excel")
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ExcelUpdateService excelUpdateService;

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationsService notificationsService;

    private Sinks.Many<UploadProgressEvent> uploadProgressSink;

    @PostConstruct
    public void initializeSink(){
        uploadProgressSink=Sinks.many().multicast().onBackpressureBuffer();
    }

//    public ExcelController() {
//        uploadProgressSink=Sinks.many().multicast().onBackpressureBuffer();
//    }

    public static ExcelBadRequestDTO excelBadRequestDTO = new ExcelBadRequestDTO();
    public static HttpStatus uploadStatus = HttpStatus.NOT_FOUND;
    public static String fileName;
   public static String uploadTime;
    public static String userName;


    public static UploadProgressDTO uploadProgressDTO;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@Valid @RequestParam("file") MultipartFile uploadfile) throws Exception {
        fileName = uploadfile.getOriginalFilename();
        uploadStatus = HttpStatus.PROCESSING;
        java.util.Date date = new java.util.Date();
        uploadTime = String.valueOf(date);
        System.out.println("Upload Time Controller: "+uploadTime);
        long bytes = uploadfile.getSize();
        String message = "Uploaded Successfully";
        List<EmployeeUpdateDTO> employeeDetails;
        logger.info("Received file upload request for file: {}", fileName);
        if (bytes / (1024 * 1024) > 5) {
            throw new FileTooLargeException(uploadfile.getOriginalFilename());
        }
        excelBadRequestDTO = excelService.uploadFile(uploadfile);

        if (excelBadRequestDTO.getHttpStatus() == HttpStatus.BAD_REQUEST  ) {
            logger.warn("Failed to upload file: {}", fileName);
            if (excelBadRequestDTO.getExcelBadRequest().contains(ExcelBadRequestType.MISSING_FIELDS) || excelBadRequestDTO.getExcelBadRequest().contains(ExcelBadRequestType.DATA)) {
                uploadStatus = HttpStatus.OK;
                logger.info("Excel {} has missing Records or improper date formats", fileName);
                //excelService.excelMissingFieldReport(excelBadRequestDTO.getFinalExcelResponseDTOList(), "Missing Fields and Invalid Data");
                return new ResponseEntity<>(excelBadRequestDTO.getFinalExcelResponseDTOList(), HttpStatus.BAD_REQUEST);
            }
            if (excelBadRequestDTO.getExcelBadRequest().contains(ExcelBadRequestType.EDITED_RECORD)) {
                logger.info("Excel {} has records which conflicts with records in database", fileName);
                uploadStatus = HttpStatus.OK;
                return new ResponseEntity<>(excelBadRequestDTO.getExcelExistingFieldResponseDTOS(), HttpStatus.BAD_REQUEST);
            }
        }
        else if (excelBadRequestDTO.getHttpStatus() == HttpStatus.UNSUPPORTED_MEDIA_TYPE) {
            logger.error("the file {} is of an unsupported file format",fileName);
            uploadStatus = HttpStatus.OK;
            throw new UnsupportedFileException(uploadfile.getOriginalFilename());
        }
        else if (excelBadRequestDTO.getHttpStatus() == HttpStatus.EXPECTATION_FAILED) {
            uploadStatus = HttpStatus.OK;
            logger.error("Headers don't match with template, please upload file again: {}", fileName);
            throw new ExcelTemplateException("Headers don't match with template, please upload file again");
        }

            uploadStatus = HttpStatus.OK;
            return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping("/missingFieldsReport")
    public byte[] missingFields() throws IOException {
        logger.info("Generating missing fields report.");
        //List<EmployeeUpdateDTO> employeeDetails = excelBadRequestDTO.getExcelEmptyFieldsResponseDTO().getEmployeeDetailList();
        byte[] missingFieldReport = excelService.excelMissingFieldReport(excelBadRequestDTO.getFinalExcelResponseDTOList(), "Missing Fields and Invalid Data");
        return missingFieldReport;
    }
    @PostMapping("/replacerecord")
    public ResponseEntity<?> replaceRecordsList(@RequestBody List<ExcelExistingFieldResponseDTO> excelExistingFieldResponseDTOS)
    {
        excelUpdateService.saveEmployee(excelExistingFieldResponseDTOS);
        return new ResponseEntity<>("Data Updated",HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public void downloadFile(HttpServletResponse response, @PathVariable String fileName) throws IOException
    {
        Path currentRelativePath = Paths.get("prod-cqs-assets-storage/cat10285/"+fileName);
        String[] split = fileName.split("[.]");
        if(split[split.length-1]=="xlsx")
        {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else if (split[split.length-1]=="csv") {
            response.setContentType("text/csv");
        }else {
            response.setContentType("application/vnd.ms-excel");
        }
        String s = currentRelativePath.toAbsolutePath().toString();
        logger.info("Current absolute path is: {}", s);
        logger.info("File exists: {}", Files.exists(currentRelativePath));
        System.out.println("Current absolute path is: " + s);
        System.out.println(Files.exists(currentRelativePath));
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;");
        try (OutputStream out = response.getOutputStream())
        {
            File toBeCopied = new File(currentRelativePath.toAbsolutePath().toString());
            Path path = toBeCopied.toPath();
            Files.copy(path, out);
            out.flush();
            logger.info("Downloaded file: {}", fileName);
        }catch(IOException e)
        {
            logger.error("Failed to download file: {}", fileName, e);
            e.printStackTrace();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ExcelDetail>> getAllExcel () {
        logger.info("Received request to get all uploaded Excel details.");
            List<ExcelDetail> excels = excelService.getAllExcel();
            if(excels.isEmpty()) {
                logger.warn("No documents uploaded.");
                throw new NoDocumentsException("No documents uploaded");
            }
            else
                logger.info("Retrieved {} Excel details.", excels.size());
        return new ResponseEntity<>(excels, HttpStatus.OK);
    }

    @GetMapping(value = "/uploadprogress",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UploadProgressEvent> getEvents() {
        userName=userService.getUser();
        return uploadProgressSink.asFlux();
    }
    @EventListener
    public void UploadEventListener(UploadProgressEvent event) {
        if (uploadProgressSink != null) {
            uploadProgressSink.tryEmitNext(event);
        }
    }

    @GetMapping("/closeuploadprogress")
    @PreDestroy
    public void cleanUp(){
        uploadProgressSink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
        initializeSink();
        }
    }
