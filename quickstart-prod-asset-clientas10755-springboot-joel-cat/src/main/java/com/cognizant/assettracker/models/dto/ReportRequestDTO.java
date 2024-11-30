package com.cognizant.assettracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequestDTO {
    String type;
    String month;
    String year;
    String reportName;
    int[] columns;
    String downloadType;
    String cidType;
}
