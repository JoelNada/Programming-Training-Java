package com.cognizant.assettracker.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "TBL_AMEX_ASSET_DTL")
public class AssetDetail {


	@Id
	@Column(name = "SERIAL_NUMBER")
	@ExcelCellName("Amex Asset serial number/Amex Asset ID")
	private String serialNumber;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ASSIGNMENT_ID")
	private EmployeeDetail amexEmployeeDetail;

	@Column(name = "ISSUE_DATE")
	private String issueDate;


	@Column(name = "STATUS")
	@ExcelCellName("Current Status")
	private String status;

	@Column(name = "RETURN_DATE")
	private String returnDate;

	@Column(name = "TRACKING_NUMBER")
	@ExcelCellName("Tracking Number")
	private String trackingNumber;

	@Column(name = "COGNIZANT_ASSET")
	@ExcelCellName("Cognizant Asset")
	private String cognizantAsset;

	@ExcelCellName("Amex Asset Make")
	@Column(name = "ASSET_MAKE")
	private String assetMake;

	@Column(name = "ASSET_MODEL")
	@ExcelCellName("Amex Asset Model")
	private String assetModel;

	@Column(name = "ASSET_ID")
	@ExcelCellName("Asset ID")
	private String assetId;

	@Column(name = "ASSET_WARRANTY_END_DATE")
	private String assetWarrantyEndDate;

	@Column(name = "IS_ASSET_UP_DATE")
	private Boolean isAssetUpToDate;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "CREATE_TIME_STAMP")
	private LocalDateTime createTimestamp;

	@Column(name = "UPDATED_TIME_STAMP")
	private LocalDateTime updatedTimestamp;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@OneToMany(mappedBy = "assetDetail", cascade = CascadeType.ALL)
	private List<EmployeeDocuments> documentDetails;

}
