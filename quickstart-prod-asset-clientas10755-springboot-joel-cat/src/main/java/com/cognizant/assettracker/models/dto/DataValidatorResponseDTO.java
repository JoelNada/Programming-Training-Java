package com.cognizant.assettracker.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataValidatorResponseDTO {

    private Long assignmentId;
    private String associateName;
    private List<String> dataFields;

}
