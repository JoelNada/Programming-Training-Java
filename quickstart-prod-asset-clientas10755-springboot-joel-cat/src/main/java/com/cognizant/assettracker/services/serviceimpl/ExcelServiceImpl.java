package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.publishers.UploadProgressPublisher;
import com.cognizant.assettracker.models.dto.*;
import com.cognizant.assettracker.models.entity.*;
import com.cognizant.assettracker.repositories.ExcelRepository;
import com.cognizant.assettracker.services.ExcelService;
import com.cognizant.assettracker.services.ExcelUpdateService;
import com.cognizant.assettracker.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.cognizant.assettracker.controller.ExcelController.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelRepository excelRepository;

    @Autowired
    private ExcelUpdateService excelUpdateService;

    private final UploadProgressPublisher uploadProgressPublisher;
    public ExcelServiceImpl(UploadProgressPublisher uploadProgressPublisher) {
        this.uploadProgressPublisher = uploadProgressPublisher;
    }
    @Autowired
    UserService userService;

    int count = 1;
    static float uploadTotal=0;
    static float uploadCount=0;
    static String excelFileName;

    public static UploadProgressDTO uploadProgressDTO= UploadProgressDTO.builder()
            .status(false)
            .excelName("")
            .percentage(null)
            .uploadTimeStamp(null)
            .message("Yet to begin upload")
            .build();
    public ExcelBadRequestDTO uploadFile(MultipartFile multipartFile) throws IOException {
        int finalCount;
        sinkflux();
        ExcelBadRequestDTO excelBadRequestDTO = new ExcelBadRequestDTO();
        Files.createDirectories(Paths.get("prod-cqs-assets-storage/cat10285/"));
        Path path;
        int length = Objects.requireNonNull(Paths.get("prod-cqs-assets-storage/cat10285/").toFile().listFiles()).length;
        if (length == 0) {
            count = 1;
            finalCount=100000+count;
            path = Paths.get("prod-cqs-assets-storage/cat10285/" + finalCount + "_" + multipartFile.getOriginalFilename());
            excelFileName=finalCount + "_" + multipartFile.getOriginalFilename();
        } else {
            count++;
            finalCount=100000+count;
            path = Paths.get("prod-cqs-assets-storage/cat10285/" + finalCount + "_" + multipartFile.getOriginalFilename());
            excelFileName=finalCount + "_" + multipartFile.getOriginalFilename();
        }

        String[] split = multipartFile.getOriginalFilename().split("[.]");
        if ((split[split.length - 1].equals("xlsx")) || (split[split.length - 1].equals("csv"))) {
            File file = Files.write(path, multipartFile.getBytes()).toFile();
            ExcelDetail excelFile = ExcelDetail.builder()
                            .createdBy(userService.getUser())
                    .name(100000+count + "_" + multipartFile.getOriginalFilename())
                    .build();
            String extension = FilenameUtils.getExtension(file.getAbsolutePath());


            if (extension.equals("xlsx")) {

                excelBadRequestDTO = excelUpdateService.empUpdate(file, excelFile);
                return excelBadRequestDTO;
            }

        }
        excelBadRequestDTO.setHttpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        return excelBadRequestDTO;
    }

    public List<ExcelDetail> getAllExcel() {
        return excelRepository.findAll();
    }

    public byte[] downloadFile(String fileName) throws IOException {
        Optional<ExcelDetail> excelDownload = excelRepository.findByName(fileName);
        String filePath = excelDownload.get().getFilePath();
        byte[] excel = Files.readAllBytes(new File(filePath).toPath());
        return excel;
    }

    public byte[] excelMissingFieldReport(List<FinalExcelResponseDTO> employeeUpdateDTOList, String reportName) throws IOException {
        String[] columns = {"SNo","Assignment ID", "Associate ID", "Associate Name","Grade","C ID","AEXP ID","City", "Country","Billability Status","Business Unit","Extract Department","Project ID","Project Description","Project Manager ID", "Project Manager Name", "Project Start Date", "Project End Date","Percent Allocation","EPL","Cognizant Asset","Amex Asset serial number/Amex Asset ID","Amex Asset Make","Amex Asset Model","Missing Fields","Invalid Data Fields"};

        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Employee");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        int rowNum = 1;
        for(int i = 0; i< employeeUpdateDTOList.size(); i++)
        {
            if(employeeUpdateDTOList.get(i).getMissingFields()==null&&employeeUpdateDTOList.get(i).getInvalidDataFields()==null)
                continue;
            Row row = sheet.createRow(rowNum++);
            row.createCell(0)
                    .setCellValue(i+1);
            if(employeeUpdateDTOList.get(i).getAssignmentId()==null){
            row.createCell(1)
                    .setCellValue("");}
            else{
                row.createCell(1)
                        .setCellValue(employeeUpdateDTOList.get(i).getAssignmentId());
            }
            if(employeeUpdateDTOList.get(i).getAssociateId()==null){
            row.createCell(2)
                    .setCellValue("");
        }
            else{
            row.createCell(2)
                    .setCellValue(employeeUpdateDTOList.get(i).getAssociateId());
            }
            row.createCell(3)
                    .setCellValue(employeeUpdateDTOList.get(i).getAssociateName());
            row.createCell(4)
                            .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getGrade());
            row.createCell(5)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getAssociateAmexContractorId());
            row.createCell(6)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getAssociateAmexEmailId());
            row.createCell(7)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getCity());
            row.createCell(8)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getCountry());
            row.createCell(9)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getBillability());
            row.createCell(10)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getBusinessUnit());
            row.createCell(11)
                            .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getServiceLine());
            if(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectId()==null)
            {
                row.createCell(12)
                        .setCellValue("");
            }else {
                row.createCell(12)
                        .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectId());
            }

            row.createCell(13)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectName());

            if(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectManagerEmpId()==null){
                row.createCell(14)
                    .setCellValue("");
            }
            else
            { row.createCell(14)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectManagerEmpId());
            }
            row.createCell(15)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectManagerName());
            Cell projectStartDateCell = row.createCell(16);
            projectStartDateCell.setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectStartDate());
            projectStartDateCell.setCellStyle(dateCellStyle);
            Cell projectEndDateCell = row.createCell(17);
            projectEndDateCell.setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getProjectEndDate());
            projectEndDateCell.setCellStyle(dateCellStyle);
            row.createCell(18)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getPercentAllocation());

            row.createCell(19)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getCtsEPLName());
            row.createCell(20)
                    .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getCognizantAsset());
            row.createCell(21)
                            .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getSerialNumber());
            row.createCell(22)
                            .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getAssetMake());
            row.createCell(23)
                            .setCellValue(employeeUpdateDTOList.get(i).getEmployeeUpdateDTO().getAssetModel());
            row.createCell(24)
                    .setCellValue(employeeUpdateDTOList.get(i).getMissingFields());
            row.createCell(25)
                    .setCellValue(employeeUpdateDTOList.get(i).getInvalidDataFields());


        }
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        byte[] fileBytes = getByteContent(workbook);
        workbook.close();
        return fileBytes;
    }
    public byte[] getByteContent(Workbook workbook) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public float uploadProgressCalculation(float count, float total){
        uploadCount=count;
        uploadTotal=total;
        return (uploadCount/uploadTotal)*100;
    }

    public UploadProgressDTO generateUploadProgress() {
        System.out.println("Upload Time progress service: "+uploadTime);
        if(uploadStatus==HttpStatus.NOT_FOUND||uploadStatus==HttpStatus.OK){
            uploadCount=0;
            uploadTotal=0;
            uploadProgressDTO= UploadProgressDTO.builder().uploadedBy("")
                    .status(false)
                    .excelName("")
                    .percentage(null)
                    .uploadTimeStamp(uploadTime)
                    .uploadedBy(userName)
                    .message("Yet to begin upload").build();
            return uploadProgressDTO;
        }

        Double percentage= (double) uploadProgressCalculation(uploadCount, uploadTotal);
        boolean status=(percentage > 0 || percentage < 99.99);
        uploadProgressDTO = UploadProgressDTO.builder()
                .status(status)
                .excelName(fileName)
                .message("InProgress")
                .percentage(percentage.intValue())
                .uploadedBy(userName)
                .uploadTimeStamp(uploadTime)
                .build();
        if(percentage.isNaN()){
            uploadProgressDTO.setMessage("starting upload");
            uploadProgressDTO.setStatus(true);
        }
        if(percentage==100D)
        {
            uploadProgressDTO.setMessage("Finishing upload");
            uploadProgressDTO.setPercentage(100);
        }
        if(uploadProgressDTO.getPercentage()==100)
        {
            uploadProgressDTO.setStatus(false);
            uploadProgressDTO.setMessage("Upload Completed");
            uploadStatus=HttpStatus.NOT_FOUND;
        }
        return uploadProgressDTO;
    }
    public void updateData(UploadProgressDTO newData) {
        System.out.println("Upload Time publisher: "+uploadTime);
        uploadProgressPublisher.publishEvent(newData); // Publish the updated data
    }
    public UploadProgressDTO sinkflux(){
        updateData(generateUploadProgress());
        return generateUploadProgress();
    }

}
