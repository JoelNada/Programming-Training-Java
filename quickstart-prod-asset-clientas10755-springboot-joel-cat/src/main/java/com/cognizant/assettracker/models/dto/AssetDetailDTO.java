package com.cognizant.assettracker.models.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class AssetDetailDTO {
    private String serialNumber;
    private String allocated_date;
    private String status;
    private String dw_pickup_date;
    private String assetMake;
    private String assetModel;
    private String release_date;
    private String dw_pickup_requested;
    private String trackingNumber;
    private List<EmployeeDocumentDTO> dwpickupdoc;
    private List<EmployeeDocumentDTO> dwpickupreceipt;
    private String assetReleaseReason;
}
