package com.cognizant.assettracker.events;

import com.cognizant.assettracker.models.entity.Notifications;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class NotificationEvent extends ApplicationEvent {

    private String message;



    public NotificationEvent(Notifications source, String message) {
        super(source);
        this.message = "New Notification " + LocalDateTime.now();

    }


}
