package com.cognizant.assettracker.controller;

import com.cognizant.assettracker.models.exceptions.DocumentNotFoundException;
import com.cognizant.assettracker.models.exceptions.TemplateException;
import com.cognizant.assettracker.repositories.TemplateRepository;
import com.cognizant.assettracker.services.TemplateService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;


@RestController
@CrossOrigin( "*")
@RequestMapping("/api/template")
public class TemplateController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);
    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    TemplateService templateService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateTemplate() throws IOException {
        logger.info("Received request to generate a template.");
            String temp= templateService.templateService();
        logger.info("Template generated successfully.");
            return  ResponseEntity.ok("Template generated");
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadTemplate(HttpServletResponse response, @PathVariable Long id) {
        try {
            logger.info("Received request to download template with ID: {}", id);
            byte[] downloadTemplate = templateService.downloadTemplate(id);
            String downloadName = templateService.getTemplateName(id)+".xlsx";
            FileUtils.writeByteArrayToFile(new File(downloadName), downloadTemplate);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename(downloadName).build());
            logger.info("Template with ID {} downloaded successfully.", id);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(downloadTemplate);
        } catch (IOException e) {
            logger.error("Template Exception occured: "+e.getMessage());
            throw new TemplateException("Template Exception occured: "+e.getMessage());
        }catch(DocumentNotFoundException e){
            logger.error("Document not Found for download with Id: " + id);
            throw new DocumentNotFoundException("Document not Found for download with Id: " + id);
        }
        }
    }

