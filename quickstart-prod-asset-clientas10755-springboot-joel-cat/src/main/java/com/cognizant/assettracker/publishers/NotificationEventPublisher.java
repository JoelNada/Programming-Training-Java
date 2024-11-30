package com.cognizant.assettracker.publishers;

import com.cognizant.assettracker.events.NotificationEvent;
import com.cognizant.assettracker.models.entity.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventPublisher.class);

    public void publishNotificationEvent(final Notifications notifications)
    {
        logger.info("Publishing Notification Event: ");
        NotificationEvent notificationEvent = new NotificationEvent(notifications, "New Notification");
        applicationEventPublisher.publishEvent(notificationEvent);
    }
}
