package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.ReportResponseDTO;
import com.cognizant.assettracker.models.entity.ExcelReport;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;

public interface ReportExcelService {
    public ExcelReport excelReportService(List<ReportResponseDTO> reportData, int[] options, String reportName) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException;
    public ExcelReport LaptopReturnPerYear(LinkedHashMap<String, Integer> LaptopReturnPerYear, String reportName) throws IOException;
}
