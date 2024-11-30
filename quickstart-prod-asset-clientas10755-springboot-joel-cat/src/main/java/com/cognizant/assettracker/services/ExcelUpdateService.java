package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.EmployeeCheck;
import com.cognizant.assettracker.models.dto.ExcelBadRequestDTO;
import com.cognizant.assettracker.models.dto.ExcelExistingFieldResponseDTO;
import com.cognizant.assettracker.models.entity.ExcelDetail;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ExcelUpdateService {

    public ExcelBadRequestDTO empUpdate(File file, ExcelDetail excelDetail) throws IOException;

    public void saveEmployee(List<ExcelExistingFieldResponseDTO> excelExistingFieldResponseDTOS);
}
