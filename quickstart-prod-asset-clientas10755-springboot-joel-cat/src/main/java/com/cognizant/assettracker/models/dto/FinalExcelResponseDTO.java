package com.cognizant.assettracker.models.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FinalExcelResponseDTO {
    private Long assignmentId;
    private Long associateId;
    private String associateName;
    @JsonIgnore
    EmployeeUpdateDTO employeeUpdateDTO;
    private String missingFields;
    private String invalidDataFields;
}
