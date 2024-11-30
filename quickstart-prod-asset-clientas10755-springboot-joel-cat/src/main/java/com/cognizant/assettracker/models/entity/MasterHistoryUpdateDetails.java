package com.cognizant.assettracker.models.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class MasterHistoryUpdateDetails implements Serializable {

    @Column(name = "ASSIGNMENT_ID")
    private Long assignmentId;

    @Column(name = "UPDATED_TIME_STAMP")
    private LocalDateTime updatedTimestamp;

    @Column(name = "UPDATED_WITH_FILE")
    private String updatedWithFile;

    @Column(name = "UPDATED_BY")
    private String updatedBy;


}
