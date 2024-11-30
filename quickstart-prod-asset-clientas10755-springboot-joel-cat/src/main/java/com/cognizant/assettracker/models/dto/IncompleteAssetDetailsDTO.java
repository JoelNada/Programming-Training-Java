package com.cognizant.assettracker.models.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class IncompleteAssetDetailsDTO {

        private Long AssociateId;
        private String AssociateName;
        private String AmexContractorId;
        private Long ProjectId;
        private String ProjectName;
        private String SerialNumber;
    }


