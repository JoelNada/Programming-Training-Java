package com.cognizant.assettracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelEmptyFieldsResponseDTO {
    private Long assignmentId;
    private Long associateId;
    private String associateName;
    private String associateAmexContractorId;
    private String associateAmexEmailId;
    private String city;
    private String country;
    private String serviceLine;
    private String grade;
    private String businessUnit;
    private String percentAllocation;
    private String billability;
    private Long projectId;
    private String projectName;
    private String projectManagerEmpId;
    private String projectManagerName;
    private String projectStartDate;
    private String projectEndDate;
    private String ctsEPLName;
    private String ctsEPLId;
    private String serialNumber;
    private String cognizantAsset;
    private String assetMake;
    private String assetModel;
    private String missingFields;
    private String invalidData;


}
