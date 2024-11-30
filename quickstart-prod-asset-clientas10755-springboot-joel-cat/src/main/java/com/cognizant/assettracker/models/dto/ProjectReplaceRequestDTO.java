package com.cognizant.assettracker.models.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProjectReplaceRequestDTO {

    private List<Long> projectID;
    private String associateId;
    private String associateName;
    private String role;


}
