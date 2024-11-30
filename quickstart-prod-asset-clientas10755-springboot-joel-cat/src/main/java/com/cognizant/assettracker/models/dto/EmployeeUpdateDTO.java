package com.cognizant.assettracker.models.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateDTO {
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
    private Integer percentAllocation;
    private String billability;
    private Long projectId;
    private String projectName;
    private Long projectManagerEmpId;
    private String projectManagerName;
    private String projectStartDate;
    private String projectEndDate;
    private String ctsEPLName;
    private String ctsEPLId;
    private String serialNumber;
    private String cognizantAsset;
    private String assetMake;
    private String assetModel;

}
