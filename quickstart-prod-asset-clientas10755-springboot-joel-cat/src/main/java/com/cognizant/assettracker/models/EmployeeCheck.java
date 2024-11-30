package com.cognizant.assettracker.models;

import com.cognizant.assettracker.models.dto.EmployeeUpdateDTO;
import com.cognizant.assettracker.models.dto.ExcelExistingFieldResponseDTO;
import com.cognizant.assettracker.models.entity.MissingFields;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeCheck {
    List<EmployeeUpdateDTO> employeeDetailList;
    HttpStatus httpStatus;
    List<MissingFields> missingFields;

}
