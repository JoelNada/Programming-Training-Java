package com.cognizant.assettracker.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@AllArgsConstructor

public class HomePageMasterHistoryDTO {

    private long assignmentId;
    private long associateId;
    private String associateName;
    private long projectId;
    private String projectName;
    private AssetDetailDTO asset;
    private LocalDateTime updatedTimestamp;
    private String updatedwithFile;
    private String updatedBy;


    public HomePageMasterHistoryDTO() {
    }

}
