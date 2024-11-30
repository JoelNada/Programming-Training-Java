package com.cognizant.assettracker.models.dto;

import com.cognizant.assettracker.models.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class NotificationsMissingFieldsDTO {

    private Long notificationId;


    private String emailID;


    private List<Role> notificationRole;


    private String notificationMessage;


    private boolean notificationAddressed;



    private LocalDateTime creationTimestamp;


    private byte[] fileData;


}
