package com.cognizant.assettracker.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailsHistoryUpdateDetails implements Serializable {

    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "UPDATED_TIME_STAMP")
    private LocalDateTime updatedTimestamp;

    @Column(name = "UPDATED_FIELD")
    private String updatedField;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

}
