package com.cognizant.assettracker.controller;
import com.cognizant.assettracker.models.exceptions.ReportNotFoundException;
import com.cognizant.assettracker.models.ReportHeaders;
import com.cognizant.assettracker.models.ReportOptions;
import com.cognizant.assettracker.models.dto.ReportRequestDTO;
import com.cognizant.assettracker.models.dto.ReportResponseDTO;
import com.cognizant.assettracker.models.entity.ExcelReport;
import com.cognizant.assettracker.models.entity.PDFReport;
import com.cognizant.assettracker.models.exceptions.WrongChoiceException;
import com.cognizant.assettracker.services.ReportExcelService;
import com.cognizant.assettracker.services.ReportPDFService;
import com.cognizant.assettracker.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/report")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportPDFService reportPDFService;
    @Autowired
    ReportService reportService;

    @Autowired
    ReportExcelService reportExcelService;
    static List<ReportResponseDTO> reportResponseDTOList =new ArrayList<>();


    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('PMO','EPL','ESA_PM')")
    public ResponseEntity<?> generateReport(@RequestBody ReportRequestDTO reportRequestDTO) throws Exception {
        reportService.assignEmployeeList();
        logger.info("Received request to generate a report of type: {}", reportRequestDTO.getType());
        switch (reportRequestDTO.getType()) {
            case "1" -> {
                List<ReportResponseDTO>  onboardingReports = reportService.onboardingReport();
                reportResponseDTOList =reportService.generateOptions(onboardingReports, reportRequestDTO.getColumns());
                if(onboardingReports.isEmpty()){
                    logger.error("Asset Issued-Onboarded Report is Empty");
                    throw new ReportNotFoundException("Asset Issued-Onboarded Report is Empty");
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "2"-> {
                List<ReportResponseDTO> releaseConfirmedList = reportService.ReleaseRequested();
                reportResponseDTOList =reportService.generateOptions(releaseConfirmedList, reportRequestDTO.getColumns());
                if(releaseConfirmedList.isEmpty()){
                    logger.error("Asset Release Requested Report is Empty");
                    throw new ReportNotFoundException("Asset Release Requested Report is Empty");
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "3" -> {
                List<ReportResponseDTO>  assetReleaseRequestedList = reportService.AssetReleaseRequested();
                reportResponseDTOList =reportService.generateOptions(assetReleaseRequestedList, reportRequestDTO.getColumns());

                if(assetReleaseRequestedList.isEmpty()){
                    logger.error("Asset Pickup Requested Report is Empty");
                    throw new ReportNotFoundException("Asset Pickup Requested Report is Empty");
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "4"-> {
                List<ReportResponseDTO> assetReturnCompletedList= reportService.AssetReturnCompleted();
                reportResponseDTOList =reportService.generateOptions(assetReturnCompletedList, reportRequestDTO.getColumns());
                if(assetReturnCompletedList.isEmpty()){
                    logger.error("Asset Pickup Completed Report is Empty");
                    throw new ReportNotFoundException("Asset Pickup Completed Report is Empty");
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "5"-> {
                if(reportRequestDTO.getCidType().equals("CIDTRUE")){
                List<ReportResponseDTO> amexCidTrueList= reportService.amexCidTrue();
                    reportResponseDTOList =reportService.generateOptions(amexCidTrueList, reportRequestDTO.getColumns());
                    if(amexCidTrueList.isEmpty()){
                        logger.error("Onboarded to Client Network Report is Empty");
                        throw new ReportNotFoundException("Onboarded to Client Network Report is Empty");
                    }
                    return ResponseEntity.ok(reportResponseDTOList);
                }
                if(reportRequestDTO.getCidType().equals("CIDFALSE")){
                List<ReportResponseDTO> amexCidFalseList=reportService.amexCidFalse();
                    reportResponseDTOList =reportService.generateOptions(amexCidFalseList, reportRequestDTO.getColumns());
                if(amexCidFalseList.isEmpty()){
                    logger.error("Onboarded to Client Network Report is Empty");
                    throw new ReportNotFoundException("Onboarded to Client Network Report is Empty");
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
                throw new WrongChoiceException("wrong choice, please choose again!");
            }

            case "6"-> {
                List<ReportResponseDTO> amexLaptopHolders= reportService.amexLaptopHolders();
                reportResponseDTOList =reportService.generateOptions(amexLaptopHolders, reportRequestDTO.getColumns());
                if(amexLaptopHolders == null){
                    logger.error("Client Asset Assignment List Report is Empty");
                    throw new ReportNotFoundException("Client Asset Assignment List Report is Empty");
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "7"->{
                List<ReportResponseDTO> laptopReturnPerMonth=reportService.laptopReturnPerMonth(reportRequestDTO.getMonth(), reportRequestDTO.getYear());
                reportResponseDTOList =reportService.generateOptions(laptopReturnPerMonth, reportRequestDTO.getColumns());
                if(laptopReturnPerMonth.isEmpty()) {
                    logger.error("There Are No Laptop Returns this: "+ reportRequestDTO.getMonth()+ " " + reportRequestDTO.getYear());
                    throw new ReportNotFoundException(("There Are No Laptop Returns this: "+ reportRequestDTO.getMonth()+ " "+ reportRequestDTO.getYear()));
                }

                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "8"->{
                List<ReportResponseDTO> incompleteAssetDetails=reportService.incompleteAssetDetails();
                reportResponseDTOList =reportService.generateOptions(incompleteAssetDetails, reportRequestDTO.getColumns());
                if(incompleteAssetDetails.isEmpty()) {
                    logger.error("Incomplete Associate Asset Details Report is Empty");
                    throw new ReportNotFoundException(("Incomplete Associate Asset Details Report is Empty"));
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "9"->{
                List<ReportResponseDTO> cidWithoutAssetList =reportService.cidWithoutAsset();
                reportResponseDTOList =reportService.generateOptions(cidWithoutAssetList, reportRequestDTO.getColumns());
                if(cidWithoutAssetList.isEmpty()) {
                    logger.error("Onboarded to Client Network without Client Asset Report is Empty");
                    throw new ReportNotFoundException(("Onboarded to Client Network without Client Asset Report is Empty"));
                }
                return ResponseEntity.ok(reportResponseDTOList);
            }
            case "10"->{
                reportResponseDTOList =reportService.laptopReturnPerYear(reportRequestDTO.getYear());
                if(reportResponseDTOList.get(0).getLaptopReturnPerYear().get("TOTAL COUNT")==0) {
                    logger.error("Laptop Return per Year Report is Empty");
                    throw new ReportNotFoundException(("Laptop Return per Year Report is Empty"));
                }
                return ResponseEntity.ok(reportResponseDTOList.get(0).getLaptopReturnPerYear());
            }
            default -> {
                logger.error("Wrong choice!");
                throw new WrongChoiceException("Wrong choice!");
            }
        }
    }
    @GetMapping("/list")
    public ResponseEntity<List<ReportOptions>> reportOptions(){
        logger.info("Received request to get a list of available report options.");
        List<ReportOptions> reportOptionsList=reportService.reportList();
        logger.debug("Retrieved {} report options.", reportOptionsList.size());
        return ResponseEntity.ok(reportOptionsList);
    }

    @PostMapping("/headers")
    public ResponseEntity<?> reportHeaders(@RequestBody int reportSelected) throws Exception {
        logger.info("Received request to get report headers for report type: {}", reportSelected);
        List<ReportHeaders> initializeReportHeaders=reportService.initializeReportHeaders();
        if(reportSelected==10)
            return ResponseEntity.ok("no headers required");
        List<ReportHeaders> reportHeadersList=reportService.reportHeaders(reportSelected,initializeReportHeaders);
        logger.debug("Retrieved {} report headers.", reportHeadersList.size());
        return ResponseEntity.ok(reportHeadersList);
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> download(@RequestBody ReportRequestDTO reportRequestDTO) throws Exception {
        logger.info("Received request to download a report of type: {} in format: {}", reportRequestDTO.getType(), reportRequestDTO.getDownloadType());
        if(reportRequestDTO.getDownloadType().equals("PDF")){
            PDFReport pdfReport;
            Long id;
            if(Objects.equals(reportRequestDTO.getType(),"10"))
                pdfReport= reportPDFService.LaptopReturnPerYear(reportResponseDTOList.get(0).getLaptopReturnPerYear(), reportRequestDTO.getReportName());
            else
                pdfReport = reportPDFService.PdfDownload(reportResponseDTOList, reportRequestDTO.getColumns(), reportRequestDTO.getReportName());
            id=pdfReport.getPdfId();
            byte[] downloadDoc = reportService.downloadFileById(id,"pdf");
                String downloadName = reportService.getFileName(id,"pdf");
            logger.debug("Downloaded PDF report with name: {}", downloadName);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadName + "\"")
                        .body(downloadDoc);
        }
        else if(reportRequestDTO.getDownloadType().equals("EXCEL")){
            ExcelReport excelReport;
            Long id;
            if(Objects.equals(reportRequestDTO.getType(),"10"))
                excelReport= reportExcelService.LaptopReturnPerYear(reportResponseDTOList.get(0).getLaptopReturnPerYear(), reportRequestDTO.getReportName());
            else
                excelReport = reportExcelService.excelReportService(reportResponseDTOList, reportRequestDTO.getColumns(), reportRequestDTO.getReportName());
            id = excelReport.getExcelId();
            byte[] downloadDoc = reportService.downloadFileById(id,"excel");
            String downloadName = reportService.getFileName(id,"excel");
            logger.debug("Downloaded Excel report with name: {}", downloadName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadName + "\"")
                    .body(downloadDoc);
        }
        else{

            logger.error("Wrong choice!Please select download type again");
            throw new WrongChoiceException("Wrong choice! Please select download type again");}
    }
    }







