package com.cognizant.assettracker.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportOptions {
    private int no;
    private String option;
    private String description;

}
