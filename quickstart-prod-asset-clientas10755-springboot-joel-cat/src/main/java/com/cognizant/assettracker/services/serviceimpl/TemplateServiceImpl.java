package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.models.entity.Template;
import com.cognizant.assettracker.models.exceptions.DocumentNotFoundException;
import com.cognizant.assettracker.repositories.TemplateRepository;
import com.cognizant.assettracker.services.TemplateService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateRepository templateRepository;

    public String templateService() throws IOException {

        List<String> columns = List.of("SNo","Assignment ID", "Associate ID", "Associate Name","Grade","C ID","AEXP ID","City", "Country","Billability Status","Business Unit","Extract Department","Project ID","Project Description","Project Manager ID", "Project Manager Name", "Project Start Date", "Project End Date","Percent Allocation", "EPL","Cognizant Asset","Amex Asset serial number/Amex Asset ID","Amex Asset Make","Amex Asset Model");
        List<String> headers = List.of("SNo","Assignment ID","Associate ID", "Associate Name","Grade","City", "Country","Billability Status","Business Unit","Extract Department","Project ID","Project Description","Project Manager ID", "Project Manager Name", "Project Start Date", "Project End Date","Percent Allocation", "EPL");

        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Template");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
            cell.setCellStyle(headerCellStyle);
            if (headers.contains(columns.get(i))) {
                CellStyle cellStyle = cell.getCellStyle();
                if (cellStyle == null) {
                    cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                }
                cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(cellStyle);
            }

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        }

        byte[] fileBytes = getByteContent(workbook);
        LocalDateTime currentTime = LocalDateTime.now();
        int count= templateRepository.findAll().size();
        count++;
        Template template = Template.builder().document(fileBytes).documentName("ESA_Template_"+count).createTimestamp(currentTime).build();
        templateRepository.save(template);
        workbook.close();
        return null;
    }

    public byte[] getByteContent(Workbook workbook) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] downloadTemplate(Long fileId) throws IOException {
        templateService();
        if(templateRepository.downloadById(fileId)==null){
            throw new DocumentNotFoundException("Document not Found for download with Id: " + fileId);
        }
        return templateRepository.downloadById(fileId);
    }

    public String getTemplateName(Long fileId)
    {
        return templateRepository.getTemplateName(fileId);
    }
}