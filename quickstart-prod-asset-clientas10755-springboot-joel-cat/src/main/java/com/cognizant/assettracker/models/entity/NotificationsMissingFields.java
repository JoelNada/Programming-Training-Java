package com.cognizant.assettracker.models.entity;

import com.cognizant.assettracker.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "TBL_AMEX_NOTIFICATIONS_MISSINGFIELDS")
public class NotificationsMissingFields {
    @Id
    @Column(name = "NOTIFICATION_ID")
    private Long notificationId;

    @Column
    private String emailID;

    @Column
    private List<Role> notificationRole;

    @Column
    private String notificationMessage;

    @Column
    private boolean notificationAddressed;


    @Column
    private LocalDateTime creationTimestamp;

    @Column
    private byte[] fileData;

    @Override
    public String toString() {
        return "NotificationsMissingFields{" +
                "notificationId=" + notificationId +
                ", emailID='" + emailID + '\'' +
                ", notificationRole=" + notificationRole +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", notificationAddressed=" + notificationAddressed +
                ", creationTimestamp=" + creationTimestamp +

                '}';
    }
}
