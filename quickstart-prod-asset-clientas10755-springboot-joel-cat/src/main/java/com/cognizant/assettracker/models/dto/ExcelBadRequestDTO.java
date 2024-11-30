package com.cognizant.assettracker.models.dto;


import com.cognizant.assettracker.models.EmployeeCheck;
import com.cognizant.assettracker.models.enums.ExcelBadRequestType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class ExcelBadRequestDTO {

    HttpStatus httpStatus;
    List<ExcelBadRequestType> excelBadRequest;
    List<ExcelExistingFieldResponseDTO> excelExistingFieldResponseDTOS;
    List<FinalExcelResponseDTO> finalExcelResponseDTOList;
    @JsonIgnore
    EmployeeCheck excelEmptyFieldsResponseDTO;

}
