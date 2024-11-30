package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.exceptions.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("api/documentation")
public class DocumentationController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationController.class);

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadDocumentation()
    {
        try {
            logger.info("Received request to download documentation");
            Resource documentation = new ClassPathResource("static/Client_Asset_Tracker_Documentation.docx");

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + documentation.getFilename());

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(documentation);
        }
        catch (DocumentNotFoundException e)
        {
         logger.error("Failed to download Documentation ", e);
         throw new DocumentNotFoundException("Documentation not found");
        }
    }

}
