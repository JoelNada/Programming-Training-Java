package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.*;
import com.cognizant.assettracker.models.entity.ExcelDetail;
import com.cognizant.assettracker.models.entity.MissingFields;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

public interface ExcelService {

    public ExcelBadRequestDTO uploadFile(MultipartFile file) throws IOException;
    public List<ExcelDetail> getAllExcel();
    public byte[] downloadFile(String fileName) throws IOException;

    public byte[] excelMissingFieldReport(List<FinalExcelResponseDTO> employeeDetailList, String reportName) throws IOException;

    public byte[] getByteContent(Workbook workbook) throws IOException;

    public float uploadProgressCalculation(float count, float total);
    public UploadProgressDTO generateUploadProgress();

    public UploadProgressDTO sinkflux();
    public void updateData(UploadProgressDTO newData);


}
