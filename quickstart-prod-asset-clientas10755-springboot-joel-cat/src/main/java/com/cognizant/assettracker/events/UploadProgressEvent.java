package com.cognizant.assettracker.events;

import com.cognizant.assettracker.models.dto.UploadProgressDTO;
import org.springframework.context.ApplicationEvent;
public class UploadProgressEvent extends ApplicationEvent {
    private final UploadProgressDTO data;
    public UploadProgressEvent(Object source, UploadProgressDTO data) {
        super(source);
        this.data = data;
    }
    public UploadProgressDTO getData() {
        return data;
    }
}