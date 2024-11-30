package com.cognizant.assettracker.models.dto;


import com.cognizant.assettracker.models.entity.Notifications;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class NotificationsDTO {

    private List<Notifications> notification;
    private Long unreadNotificationCount;
    private Long totalNotificationCount;
}
