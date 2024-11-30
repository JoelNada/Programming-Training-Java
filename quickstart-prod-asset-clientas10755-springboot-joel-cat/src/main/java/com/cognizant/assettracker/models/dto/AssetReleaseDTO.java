package com.cognizant.assettracker.models.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AssetReleaseDTO {

    private String requestCreationTime;
    private String assetReleaseReason;
    private String message;
    private String serialNumber;
    private String pickupTime;
}
