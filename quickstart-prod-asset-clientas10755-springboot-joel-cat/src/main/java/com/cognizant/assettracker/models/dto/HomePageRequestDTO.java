package com.cognizant.assettracker.models.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
public class HomePageRequestDTO {
    private long assignmentId;
    private long associateId;
    private String associateName;
    private long projectId;
    private String projectName;
    private String assetType;
    private List<AssetDetailDTO> asset;
}
