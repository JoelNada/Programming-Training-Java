package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.ReportResponseDTO;
import com.cognizant.assettracker.models.entity.PDFReport;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;

public interface ReportPDFService {
    public PDFReport PdfDownload(List<ReportResponseDTO> reportData, int[] options, String reportTitle) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException;
    public PDFReport LaptopReturnPerYear(LinkedHashMap<String, Integer> LaptopReturnPerYear, String reportName) throws IOException;
}
