package com.cognizant.assettracker.services.serviceimpl;


import be.quodlibet.boxable.*;
import be.quodlibet.boxable.datatable.DataTable;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.utils.PageContentStreamOptimized;
import com.cognizant.assettracker.models.ReportHeaders;
import com.cognizant.assettracker.models.dto.ReportResponseDTO;
import com.cognizant.assettracker.models.entity.PDFReport;
import com.cognizant.assettracker.repositories.PDFReportRepository;
import com.cognizant.assettracker.services.ReportPDFService;
import com.cognizant.assettracker.services.ReportService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Service
public class ReportPDFServiceImpl implements ReportPDFService {
    @Autowired
    PDFReportRepository pdfReportRepository;
    @Autowired
    ReportService reportService;
    static float pageWidth=PDRectangle.A4.getWidth();
    public PDFReport PdfDownload(List<ReportResponseDTO> reportData, int[] options, String reportTitle) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        PDPage page=new PDPage();
            double columnRatio=0.17200177*PDRectangle.A4.getWidth();
            pageWidth= (float) (columnRatio*(options.length)+100);
            page = new PDPage(new PDRectangle(pageWidth, PDRectangle.A4.getHeight()));
        PDDocument pdDocument = new PDDocument();
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
        PageContentStreamOptimized pageContentStreamOptimized=new PageContentStreamOptimized(contentStream);
        List<List> data = new ArrayList();
        List<String> pdfColumns=new ArrayList<>();
        List<ReportHeaders> reportHeadersList=reportService.initializeReportHeaders();
        List<Integer> list = Arrays.stream(options).boxed().toList();
        List<ReportHeaders> optionsSelected=new ArrayList<>();
        Image image = new Image(ImageIO.read(new File("src/main/resources/logo.jpg")));
        float imageWidth = 80;
        image = image.scaleByWidth(imageWidth);

        image.draw(pdDocument, pageContentStreamOptimized, 50, 815);

        if(options.length>5){
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
            contentStream.newLineAtOffset(pageWidth/2-60, 802);
            contentStream.showText(reportTitle);
            contentStream.endText();
        }
        else {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
            contentStream.newLineAtOffset(220, 802);
            contentStream.showText(reportTitle);
            contentStream.endText();

        }

        pdDocument.addPage(page);
        float margin = 50;
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        float tableWidth = page.getMediaBox().getWidth() - ( 2 * margin);
        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        float yPosition = 550;
        float currentX;

        for(ReportHeaders reportHeaders:reportHeadersList){
            if(list.contains(reportHeaders.getNo())){
                optionsSelected.add(reportHeaders);
            }
        }
        System.out.println("Options selected: "+optionsSelected);
        for (ReportHeaders reportHeaders : optionsSelected) {
            pdfColumns.add(reportHeaders.getHeader());
        }
        data.add(pdfColumns);
        List<ReportResponseDTO> filteredDataOptions=reportService.generateOptions(reportData,options);
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
        List<List<String>> finalReportData=new ArrayList<>();
        BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth+5, margin, pdDocument, page, true, true);
//Create Header row
        Row<PDPage> headerRow = table.createRow(15f);
        Cell<PDPage> cell;
        float cellWidth=(tableWidth-(pdfColumns.size()-1)*margin)/pdfColumns.size();
        currentX=margin;
        Color color=new Color(51,224,255);
        for(String head:pdfColumns){
            cell = headerRow.createCell( ((float) 100 /pdfColumns.size()), head);
            cell.setFillColor(color);
            cell.setTextColor(Color.BLACK);
            cell.setAlign(HorizontalAlignment.CENTER);
            cell.setFont(PDType1Font.HELVETICA_BOLD);
            currentX+=cellWidth+margin;
        }
        for(ReportResponseDTO reportResponseDTO :filteredDataOptions){
            List<String> subList= new ArrayList<>();
            Row<PDPage> row = table.createRow(10f);
            currentX=margin;
            for(int option:options){
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(getterOptions.get(option), ReportResponseDTO.class);
                Method readMethod = propertyDescriptor.getReadMethod();
                Object selectedValue = readMethod.invoke(reportResponseDTO);
                if(selectedValue==null)
                    selectedValue="";
                subList.add(selectedValue.toString());
                if(reportResponseDTO.getAssetCount()>1){
                cell = row.createCell(((float)100 /pdfColumns.size()), selectedValue.toString());
                cell.setFillColor(Color.YELLOW);
                cell.setAlign(HorizontalAlignment.CENTER);
                currentX+=cellWidth+margin;
                }
                else
                {
                    cell = row.createCell(((float) 100 /pdfColumns.size()), selectedValue.toString());
                    cell.setFillColor(Color.WHITE);
                    cell.setAlign(HorizontalAlignment.CENTER);
                    currentX+=cellWidth+margin;
                }
            }
            finalReportData.add(subList);
        }
        table.draw();
        contentStream.close();
        byte[] fileBytes = getByteContent(pdDocument);
        LocalDateTime currentTime = LocalDateTime.now();
        int count= pdfReportRepository.findAll().size();
        count++;
        PDFReport pdfReport=PDFReport.builder().document(fileBytes).documentName("_Report_"+count).createTimestamp(currentTime).build();
        pdfReportRepository.save(pdfReport);
        //pdDocument.save("testemployee.pdf");
        pdDocument.close();
        return pdfReport;
    }
    public PDFReport LaptopReturnPerYear(LinkedHashMap<String, Integer> LaptopReturnPerYear, String reportName) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        PDDocument pdDocument = new PDDocument();
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
        PageContentStreamOptimized pageContentStreamOptimized = new PageContentStreamOptimized(contentStream);
        Image image = new Image(ImageIO.read(new File("src/main/resources/logo.jpg")));
        float imageWidth = 80;
        image = image.scaleByWidth(imageWidth);
        image.draw(pdDocument, pageContentStreamOptimized, 50, 815);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
        contentStream.newLineAtOffset(220, 802);
        contentStream.showText(reportName);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
        contentStream.newLineAtOffset(52, 760);
        contentStream.showText("Year:"+LaptopReturnPerYear.get("YEAR"));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
        contentStream.newLineAtOffset(475, 760);
        contentStream.showText("Total Count:"+LaptopReturnPerYear.get("TOTAL COUNT"));
        contentStream.endText();
        pdDocument.addPage(page);
        float margin = 50;
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        float yPosition = 550;
        List<List> data = new ArrayList();
        data.add(new ArrayList<>(
                Arrays.asList("SNO", "MONTH","COUNT")));
        int i=1;
        Set<Map.Entry<String, Integer>> set=LaptopReturnPerYear.entrySet();
        for (Map.Entry<String, Integer> setIterator : set) {
            data.add(new ArrayList<>(
                    Arrays.asList(i++,(String) ((Map.Entry) setIterator).getKey(), (Integer) ((Map.Entry) setIterator).getValue())));
            if(i>=13)
                break;
        }
        Color color=new Color(51,224,255);
        BaseTable baseTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, pdDocument, page, true, true);
        DataTable dataTable = new DataTable(baseTable, page);
        dataTable.getDataCellTemplateEven().setAlign(HorizontalAlignment.CENTER);
        dataTable.getDataCellTemplateOdd().setAlign(HorizontalAlignment.CENTER);
        dataTable.getDataCellTemplateOdd().setFillColor(Color.WHITE);
        dataTable.getDataCellTemplateEven().setFillColor(Color.WHITE);
        dataTable.getHeaderCellTemplate().setFillColor(color);
        dataTable.addListToTable(data, DataTable.HASHEADER);
        baseTable.draw();
        contentStream.close();
        byte[] fileBytes = getByteContent(pdDocument);
        LocalDateTime currentTime = LocalDateTime.now();
        int count = pdfReportRepository.findAll().size();
        count++;
        PDFReport pdfReport = PDFReport.builder().document(fileBytes).documentName(reportName + "_Report_" + count).createTimestamp(currentTime).build();
        pdfReportRepository.save(pdfReport);
        //pdDocument.save("testemployee.pdf");
        pdDocument.close();
        return pdfReport;
    }
    public byte[] getByteContent(PDDocument document) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}