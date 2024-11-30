package com.cognizant.assettracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackingResponse {
    private String createtimestamp;;
    private String reason;
    private String serialnumber;
    private String pickuptimestamp;
    private Status status;

}
