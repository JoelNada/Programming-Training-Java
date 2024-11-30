package com.cognizant.assettracker.controller;

import com.cognizant.assettracker.models.TrackingResponse;
import com.cognizant.assettracker.models.exceptions.TrackingException;
import com.cognizant.assettracker.services.TrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")

public class TrackingController {

    private static final Logger logger = LoggerFactory.getLogger(TrackingController.class);
    @Autowired
    TrackingService trackingService;
    @GetMapping("/tracking/{serialnumber}")
    public TrackingResponse track(@PathVariable String serialnumber) throws Exception {
        logger.info("Received request to track serial number: {}", serialnumber);
        TrackingResponse trackingResponse=trackingService.track(serialnumber);
        if (trackingResponse==null){
            logger.error("Tracking response is empty for serial number: {}", serialnumber);
            throw new TrackingException("tracking response empty");
        }
        else{
            logger.info("Tracking completed for serial number: {}", serialnumber);
            return trackingResponse;
        }
    }
}
