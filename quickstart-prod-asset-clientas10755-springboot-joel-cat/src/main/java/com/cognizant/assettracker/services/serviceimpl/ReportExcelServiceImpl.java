package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.ReportHeaders;
import com.cognizant.assettracker.models.dto.ReportResponseDTO;
import com.cognizant.assettracker.models.entity.ExcelReport;
import com.cognizant.assettracker.repositories.ExcelReportRepository;
import com.cognizant.assettracker.services.ReportExcelService;
import com.cognizant.assettracker.services.ReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReportExcelServiceImpl implements ReportExcelService {
    @Autowired
    ExcelReportRepository excelReportRepository;
    @Autowired
    ReportService reportService;

    public ExcelReport excelReportService(List<ReportResponseDTO> reportData, int[] options, String reportName) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        List<String> excelColumns=new ArrayList<>();
        List<List<String>> excelReportData = new ArrayList<>();
        List<ReportHeaders> reportHeadersList=reportService.initializeReportHeaders();
        List<Integer> list = Arrays.stream(options).boxed().toList();
        List<ReportHeaders> optionsSelected=new ArrayList<>();
        List<List<String>> finalReportData=new ArrayList<>();
        for(ReportHeaders reportHeaders:reportHeadersList){
            if(list.contains(reportHeaders.getNo())){
                optionsSelected.add(reportHeaders);
            }
        }
        for (ReportHeaders reportHeaders : optionsSelected) {
            excelColumns.add(reportHeaders.getHeader());
        }
        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet(reportName);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < excelColumns.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelColumns.get(i));
            cell.setCellStyle(headerCellStyle);
        }
        List<ReportResponseDTO> filteredDataList=reportService.generateOptions(reportData,options);
        LinkedHashMap<Integer, String> getterOptions = new LinkedHashMap<>();
        getterOptions.put(1,"associateId");
        getterOptions.put(2,"associateName");
        getterOptions.put(3,"associateAmexContractorId");
        getterOptions.put(4,"associateAmexEmailId");
        getterOptions.put(5,"associateCTSEmailId");
        getterOptions.put(6,"amexDirectorEmail");
        getterOptions.put(7,"city");
        getterOptions.put(8,"country");
        getterOptions.put(9,"serviceLine");
        getterOptions.put(10,"grade");
        getterOptions.put(11,"businessUnit");
        getterOptions.put(12,"percentAllocation");
        getterOptions.put(13,"billability");
        getterOptions.put(14,"projectId");
        getterOptions.put(15,"projectName");
        getterOptions.put(16,"projectManagerEmpId");
        getterOptions.put(17,"projectManagerName");
        getterOptions.put(18,"projectStartDate");
        getterOptions.put(19,"projectEndDate");
        getterOptions.put(20,"ctsEPLId");
        getterOptions.put(21,"ctsEPLName");
        getterOptions.put(22,"serialNumber");
        getterOptions.put(23,"assetMake");
        getterOptions.put(24,"assetModel");
        getterOptions.put(25,"issueDate");
        getterOptions.put(26,"status");
        getterOptions.put(27,"cognizantAsset");
        getterOptions.put(28,"trackingNumber");
        getterOptions.put(29,"releaseRequestedDate");
        getterOptions.put(30,"DWPickupRequestedDate");
        getterOptions.put(31,"DWPickupDate");
        getterOptions.put(32,"onboardingStatus");

        for(ReportResponseDTO report:filteredDataList){
            List<String> subList= new ArrayList<>();
            for(int option:options){
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(getterOptions.get(option), ReportResponseDTO.class);
                Method readMethod = propertyDescriptor.getReadMethod();
                Object selectedValue = readMethod.invoke(report);
                if(selectedValue==null)
                    selectedValue="";
                subList.add(selectedValue.toString());
            }
            finalReportData.add(subList);
        }
        excelReportData.addAll(finalReportData);
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        Iterator<List<String>> iterator = excelReportData.iterator();
        int rownum=1;
        int cellnum = 1;
        int rowCount=0;
        while (iterator.hasNext()) {
            List<String> tempList = iterator.next();
            Iterator<String> tempIterator= tempList.iterator();
            Row row = sheet.createRow(rownum++);
            cellnum = 0;
            while (tempIterator.hasNext()) {
                System.out.println("Row count: "+rowCount);
                System.out.println("report data: "+reportData.get(rowCount).getAssetCount());
                String temp = tempIterator.next();
                Cell cell = row.createCell(cellnum++);
                cell.setCellValue(temp);
                if(reportData.get(rowCount).getAssetCount()>1&&rowCount<reportData.size()){
                CellStyle style=workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(style);
                }
            }
            rowCount++;
        }
        for (int j = 0; j < excelColumns.size(); j++) {
            sheet.autoSizeColumn(j);
        }


        byte[] fileBytes = getByteContent(workbook);
        LocalDateTime currentTime = LocalDateTime.now();
        int count = excelReportRepository.findAll().size();
        count++;
        ExcelReport excelReport = ExcelReport.builder().document(fileBytes).documentName(reportName + "_Report_" + count).createTimestamp(currentTime).build();
        excelReportRepository.save(excelReport);
//        FileOutputStream fileOut = new FileOutputStream("finaltest.xlsx");
//        workbook.write(fileOut);
//        fileOut.close();
//        workbook.close();
        return excelReport;
    }
    public ExcelReport LaptopReturnPerYear(LinkedHashMap<String, Integer> LaptopReturnPerYear, String reportName) throws IOException {
        String[] columns = {"Sno", "Month","Count"};

        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Laptop Return Per Year");
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
        int count = 1;
        Set<Map.Entry<String, Integer>> set=LaptopReturnPerYear.entrySet();
        for (Map.Entry<String, Integer> setIterator : set) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(count++);
            row.createCell(1)
                    .setCellValue((String) ((Map.Entry) setIterator).getKey());
            row.createCell(2)
                    .setCellValue((Integer) ((Map.Entry) setIterator).getValue());
            if(count>=13)
                break;
        }
        for (int j = 0; j < columns.length; j++) {
            sheet.autoSizeColumn(j);
        }
        rowNum++;
        Row row1 = sheet.createRow(rowNum++);
        row1.createCell(1).setCellValue("Year:");
        row1.createCell(2).setCellValue(LaptopReturnPerYear.get("YEAR"));
        Row row2= sheet.createRow(rowNum++);
        row2.createCell(1).setCellValue("Total Count: ");
        row2.createCell(2).setCellValue(LaptopReturnPerYear.get("TOTAL COUNT"));
        byte[] fileBytes = getByteContent(workbook);
        LocalDateTime currentTime = LocalDateTime.now();
        int repositoryCount = excelReportRepository.findAll().size();
        repositoryCount++;
        ExcelReport excelReport = ExcelReport.builder().document(fileBytes).documentName(reportName + "_Report_" + repositoryCount).createTimestamp(currentTime).build();
        excelReportRepository.save(excelReport);
//        FileOutputStream fileOut = new FileOutputStream("finaltest.xlsx");
//        workbook.write(fileOut);
//        fileOut.close();
//        workbook.close();
        return excelReport;
    }

    public byte[] getByteContent(Workbook workbook) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
