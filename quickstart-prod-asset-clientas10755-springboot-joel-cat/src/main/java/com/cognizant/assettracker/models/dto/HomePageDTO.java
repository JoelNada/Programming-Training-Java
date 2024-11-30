package com.cognizant.assettracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HomePageDTO {
    private long assignmentId;
    private long associateId;
    private String associateName;
    private long projectId;
    private String projectName;
    private List<AssetDetailDTO> asset;
    private String onboardingStatus;
    private boolean multipleAsset;
    public HomePageDTO() {
    }

}
