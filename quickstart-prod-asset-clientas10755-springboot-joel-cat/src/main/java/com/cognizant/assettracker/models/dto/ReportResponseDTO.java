package com.cognizant.assettracker.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportResponseDTO implements Cloneable {
    private String associateId;
    private String associateName;
    private String associateAmexContractorId;
    private String associateCTSEmailId;
    private String associateAmexEmailId;
    private String amexDirectorEmail;
    private String city;
    private String country;
    private String serviceLine;
    private String grade;
    private String businessUnit;
    private String percentAllocation;
    private String billability;
    private String projectId;
    private String projectName;
    private String projectManagerEmpId;
    private String projectManagerName;
    private String projectStartDate;
    private String projectEndDate;
    private String ctsEPLName;
    private String ctsEPLId;
    private String serialNumber;
    private String issueDate;
    private String status;
    private String returnDate;
    private String trackingNumber;
    private String cognizantAsset;
    private String assetMake;
    private String assetModel;
    private String releaseRequestedDate;
    private String DWPickupRequestedDate;
    private String DWPickupDate;
    private String onboardingStatus;
    @JsonIgnore
    private int assetCount;
    private boolean multipleAsset;
    LinkedHashMap<String, Integer> laptopReturnPerYear;
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
