package com.cognizant.assettracker.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TBL_AMEX_EXCEL_DTL")
@EntityListeners(AuditingEntityListener.class)
public class ExcelDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long fileId;

    @Column(name = "file_name")
    private String name;

    private String filePath;

    @CreatedBy
    @Column(name = "updatedBy")
    protected String createdBy;

    @Builder.Default
    @Column(name = "created_at")
    @Temporal(TemporalType.TIME)
    @CreatedDate
    private Date createdAt = new Date(System.currentTimeMillis());

    @Temporal(TemporalType.DATE)
    @Column(name = "createdDate")
    @CreatedDate
    protected Date createdDate;

    @PrePersist
    private void onCreate(){
        createdDate = new Date();
    }

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @Column(name = "TOTAL_RECORDS")
    private String totalRecords;

    @Column(name = "UPDATED_RECORDS")
    private Long updatedRecords;

    @Column(name = "ERROR_RECORDS")
    private Long errorRecords;

    @Column(name = "TOTAL_TIME_TAKEN")
    private String totalTimeTaken;

    @Column(name = "AVERAGE_TIME_PER_RECORD")
    private String averageTimePerRecord;

    public void setDeleted(boolean deleted) {
        isDeleted = false;
    }




}