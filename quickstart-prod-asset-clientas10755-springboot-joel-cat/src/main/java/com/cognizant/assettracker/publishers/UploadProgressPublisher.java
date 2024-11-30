package com.cognizant.assettracker.publishers;

import com.cognizant.assettracker.events.UploadProgressEvent;
import com.cognizant.assettracker.models.dto.UploadProgressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
@Component
public class UploadProgressPublisher {
    private final ApplicationEventPublisher eventPublisher;

    private static final Logger logger = LoggerFactory.getLogger(UploadProgressPublisher.class);

    public UploadProgressPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    public void publishEvent(UploadProgressDTO data) {
        UploadProgressEvent uploadProgressEvent = new UploadProgressEvent(this, data);
        logger.info("Publishing Upload Progress Event :");
        eventPublisher.publishEvent(uploadProgressEvent);
    }
}