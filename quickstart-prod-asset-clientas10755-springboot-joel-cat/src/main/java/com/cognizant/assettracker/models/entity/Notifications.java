package com.cognizant.assettracker.models.entity;


import com.cognizant.assettracker.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonSerialize
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_AMEX_NOTIFICATIONS")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "NOTIFICATION_ID")
    private Long notificationId;

    @Column
    private String emailID;

    @Column
    @JsonIgnore
    private List<Role> notificationRole;

    @Column
    private String notificationMessage;

    @Column
    private boolean notificationAddressed;

    @Column
    private String notificationType;

    @Column
    private LocalDateTime creationTimestamp;

    @Column
    private String fileName;

    @Column
    private byte[] fileData;
}
