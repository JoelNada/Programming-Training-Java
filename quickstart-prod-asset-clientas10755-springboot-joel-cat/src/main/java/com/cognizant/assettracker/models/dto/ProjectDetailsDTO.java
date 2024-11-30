package com.cognizant.assettracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailsDTO {

    private Long projectId;
    private String projectName;
    private Long projectManagerEmpId;
    private String projectManagerName;
    private String projectStartDate;
    private String projectEndDate;
    private String eplId;
    private String eplName;

}