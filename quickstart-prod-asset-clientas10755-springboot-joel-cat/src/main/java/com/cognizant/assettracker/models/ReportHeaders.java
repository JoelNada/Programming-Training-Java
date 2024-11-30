package com.cognizant.assettracker.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReportHeaders {
    int no;
    String header;
    String defaultSelect;
    public ReportHeaders(int no, String header) {
        this.no = no;
        this.header = header;
    }
}
