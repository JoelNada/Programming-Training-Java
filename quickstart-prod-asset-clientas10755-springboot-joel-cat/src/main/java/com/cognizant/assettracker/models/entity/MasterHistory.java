package com.cognizant.assettracker.models.entity;


import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data

@Table(name = "TBL_AMEX_MASTER_HISTORY")
public class MasterHistory {

    @EmbeddedId
    private MasterHistoryUpdateDetails masterHistoryUpdateDetails;

    @Column(name = "ASSOCIATE_ID")
    private Long associateId;

    @Column(name = "ASSOCIATE_NAME")
    private String associateName;

    @Column(name = "ASSOCIATE_CTS_EMAIL_ID")
    private String associateCTSEmailId;

    @Column(name = "AMEX_CONTRACTOR_ID")
    private String associateAmexContractorId;

    @Column(name = "AMEX_EMAIL_ID")
    private String associateAmexEmailId;

    @Column(name = "AMEX_DIRECTOR_EMAIL")
    private String amexDirectorEmail;

    @Column(name = "LOCATION_CITY")
    private String city;

    @Column(name = "LOCATION_COUNTRY")
    private String country;

    @Column(name = "SERVICE_LINE")
    private String serviceLine;

    @Column(name = "ASSOCIATE_ACTIVE_IN_AMEX")
    private Boolean isActiveInAmex;

    @Column(name = "ASSOCIATE_RELEASE_DATE_FROM_AMEX")
    private String releaseDate;

    @Column(name = "PROJECT_ID")
    private Long projectId;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "PROJECT_MANAGER_EMP_ID")
    private Long projectManagerEmpId;

    @Column(name = "PROJECT_MANAGER_NAME")
    private String projectManagerName;

    @Column(name = "COGNIZANT_PROJECT_START_DATE")
    private String projectStartDate;

    @Column(name = "COGNIZANT_PROJECT_END_DATE")
    private String projectEndDate;

    @Column(name = "COGNIZANT_EPL_NAME ")
    private String ctsEPLName;

    @Column(name = "COGNIZANT_EPL_ID")
    private String ctsEPLId;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "ALLOCATED_DATE")
    public String allocated_date;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DW_PICKUP_DATE")
    private String dw_pickup_date;

    @Column(name = "ASSET_MAKE")
    private String assetMake;

    @Column(name = "ASSET_MODEL")
    private String assetModel;

    @Column(name = "ISSUE_DATE")
    private String issueDate;

    @Column(name = "RETURN_DATE")
    private String returnDate;

    @Column(name = "DW_PICKUP_REQUESTED")
    private String dw_pickup_requested;

    @Column(name = "TRACKING_NUMBER")
    private String trackingNumber;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Column(name = "DOCUMENT")
    private byte[] document;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = "CREATE_TIMESTAMP")
    private LocalDateTime createTimestamp;

    @Column(name = "ASSET_WARRANTY_END_DATE")
    private String assetWarrantyEndDate;

    @Column(name = "IS_ASSET_UP_DATE")
    private Boolean isAssetUpToDate;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "ASSET_TYPE_CODE ")
    private String assetTypeCode;

    @Column(name = "ASSET_NAME")
    private String assetName;

    @Column(name = "ASSET_RELEASE_REASON")
    private String assetReleaseReason;

    @Column(name = "PICKUP_TIMESTAMP")
    private String pickupTimestamp;

    @Column(name = "REQUEST_CREATION_TIME")
    private String requestCreationTime;

    @Column(name = "UPDATED_TIME_STAMP",  insertable=false, updatable=false)
    private LocalDateTime updatedTimestamp;

    @Column(name = "UPDATED_WITH_FILE", insertable=false, updatable=false)
    private String updatedwithFile;

    @Column(name = "UPDATED_BY", insertable=false, updatable=false )
    private String updatedBy;


    public void setDeleted(boolean deleted) {
        isDeleted = false;
    }

    public void setPickupReceiptPresent(boolean b) {
    }// new master history from employeedocumentservice

    public void setRequestFormPresent(boolean b) {
    }//new master history from employeedocumentservice

    public boolean isRequestFormPresent() {
        return false;
    }// from master history service
}
