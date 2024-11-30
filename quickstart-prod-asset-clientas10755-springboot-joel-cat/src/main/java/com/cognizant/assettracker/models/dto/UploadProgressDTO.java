package com.cognizant.assettracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadProgressDTO {
    boolean status;
    String message;
    Integer percentage;
    String excelName;
    String uploadedBy;
    String uploadTimeStamp;

    public UploadProgressDTO increment(Integer percentage){
        this.percentage=percentage;
        return this;
    }

}
