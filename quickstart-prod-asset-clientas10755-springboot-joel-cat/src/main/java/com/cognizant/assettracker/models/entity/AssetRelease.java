package com.cognizant.assettracker.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "TBL_AMEX_ASSET_RELEASE")
public class AssetRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_RELEASE_ID")
    private int assetReleaseId;

    @Column(name = "REQUEST_CREATION_TIME")
    private String requestCreationTime;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "ASSET_RELEASE_REASON")
    private String assetReleaseReason;

    @Column(name = "PICKUP_TIMESTAMP")
    private String pickupTimestamp;

    @Column(name="RELEASE_DATE")
    private String releaseTimestamp;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "SERIAL_NUMBER", referencedColumnName = "SERIAL_NUMBER")
    private AssetDetail assetDetail;

}
