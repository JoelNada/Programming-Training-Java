package com.cognizant.assettracker.services;


import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface TemplateService {

    public String templateService() throws IOException;
    public byte[] getByteContent(Workbook workbook) throws IOException;

    public byte[] downloadTemplate(Long fileId) throws IOException;

    public String getTemplateName(Long fileId);
}
