package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.exceptions.DocumentNotFoundException;
import com.cognizant.assettracker.models.dto.AmexEmployeeDocumentDTO;
import com.cognizant.assettracker.models.dto.EmployeeDocumentDTO;
import com.cognizant.assettracker.models.ResponseMessage;
import com.cognizant.assettracker.models.exceptions.EmployeeDocumentException;
import com.cognizant.assettracker.repositories.EmployeeDocumentsRepository;
import com.cognizant.assettracker.services.EmployeeDocumentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentsController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentsController.class);
    @Autowired
    EmployeeDocumentService empDocService;

    @PostMapping("/save")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestBody AmexEmployeeDocumentDTO amexEmpDoc) throws IOException {
        String message = "";
        try{
            logger.info("Received file upload request");
            message=empDocService.save(amexEmpDoc);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch(EmployeeDocumentException e) {
            message = "Could not upload the file: " + "!";
            logger.error(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/get/{serialNumber}/{docType}")
    public ResponseEntity<List<EmployeeDocumentDTO>> getEmployeeDocuments(@PathVariable String serialNumber, @PathVariable String docType) {
        List<EmployeeDocumentDTO> amexEmpDocs=new ArrayList<>();
        try{
            logger.info("Received request to get employee documents for serial number: {} and docType: {}", serialNumber, docType);
            amexEmpDocs = empDocService.getFile(serialNumber, docType);
            return ResponseEntity.status(HttpStatus.OK).body(amexEmpDocs);
        }catch (DocumentNotFoundException e){
            logger.error("Document not found with the given information");
            throw new DocumentNotFoundException("Document not found with the given information");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteDocument(@PathVariable Long id) {
        try {
            logger.info("Received request to delete document with ID: {}", id);
            empDocService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Deleted successfully"));
        } catch (Exception e) {
            logger.error("Could not delete document due to: "+e.getMessage());
            throw new DocumentNotFoundException("Could not delete the document due to: "+e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(HttpServletResponse response, @PathVariable Long id) {
        try {
            logger.info("Received request to download document with ID: {}", id);
            byte[] downloadDoc = empDocService.downloadFileById(id);
            String downloadName = empDocService.getFileName(id);

            FileUtils.writeByteArrayToFile(new File(downloadName), downloadDoc);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename(downloadName).build());
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(downloadDoc);
        } catch (DocumentNotFoundException | IOException e) {
            logger.error("Failed to download document", e);
            throw new DocumentNotFoundException("Document not found with id: "+id);
        }
    }

}
