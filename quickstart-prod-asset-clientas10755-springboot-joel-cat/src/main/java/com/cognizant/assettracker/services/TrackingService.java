package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.TrackingResponse;

public interface TrackingService {

    public TrackingResponse track(String serialnumber);

}
