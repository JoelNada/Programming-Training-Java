package com.cognizant.assettracker.models.dto;

import com.cognizant.assettracker.models.entity.EmployeeDetail;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ExcelExistingFieldResponseDTO {

    private long assignmentId;
    private EmployeeDetail databaseRecord;
    private EmployeeDetail excelRecord;
    private boolean replaceRecord;

}
