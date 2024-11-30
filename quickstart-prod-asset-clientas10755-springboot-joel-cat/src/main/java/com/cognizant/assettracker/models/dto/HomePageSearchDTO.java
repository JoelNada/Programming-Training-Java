package com.cognizant.assettracker.models.dto;


import lombok.Data;

@Data
public class HomePageSearchDTO {
    Long projectId;
    String projectName;
    Long associateId;
    String associateName;

}
