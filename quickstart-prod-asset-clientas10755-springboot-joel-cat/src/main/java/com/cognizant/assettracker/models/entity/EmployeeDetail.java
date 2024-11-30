package com.cognizant.assettracker.models.entity;
import com.poiji.annotation.ExcelCellName;
import jakarta.validation.constraints.Email;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "TBL_AMEX_EMP_DTL")
public class EmployeeDetail {
	@Id
	@Column(name = "ASSIGNMENT_ID")

	@ExcelCellName("Assignment ID")
	private Long assignmentId;

	@Column(name = "ASSOCIATE_ID")
	@ExcelCellName("Associate ID")
	private Long associateId;

	@Column(name = "ASSOCIATE_NAME")
	@ExcelCellName("Associate Name")
	private String associateName;

	@Column(name = "ASSOCIATE_CTS_EMAIL_ID")
	@Email
	private String associateCTSEmailId;

	@Column(name = "AMEX_CONTRACTOR_ID")
	@ExcelCellName("C ID")
	private String associateAmexContractorId;

	@Column(name = "AMEX_EMAIL_ID")
	@ExcelCellName("AEXP ID")
	@Email
	private String associateAmexEmailId;

	@Column(name = "AMEX_DIRECTOR_EMAIL")
	@Email
	private String amexDirectorEmail;

	@Column(name = "LOCATION_CITY")
	@ExcelCellName("City")
	private String city;

	@Column(name = "LOCATION_COUNTRY")
	@ExcelCellName("Country")
	@NotNull(message ="Country is missing")
	private String country;

	@Column(name = "SERVICE_LINE")
	@ExcelCellName("Extract Department")
	private String serviceLine;

	@Column(name = "GRADE")
	@ExcelCellName("Grade")
	private String grade;

	@Column(name = "BUSINESS_UNIT")
	@ExcelCellName("Business Unit")
	private String businessUnit;

	@Column(name = "PERCENT_ALLOCATION")
	@ExcelCellName("Percent Allocation")
	private Integer percentAllocation;


	@Column(name="BILLABILITY")
	@ExcelCellName("Billability Status")
	private String billability;

	@Column(name = "ASSOCIATE_ACTIVE_IN_AMEX")
	private Boolean isActiveInAmex;

	@Column(name = "ASSOCIATE_RELEASE_DATE_FROM_AMEX")
	private String releaseDate;

	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	@Valid
	private ProjectDetails projectDetails;

	private LocalDateTime createTimestamp;

	@Column(name = "UPDATED_TIME_STAMP")
	private LocalDateTime updatedTimestamp;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ASSIGNMENT_ID")
	private List<AssetDetail> assetsDetails;

	@Column(name = "IS_DELETED")
	private boolean isDeleted;

	@Column(name = "IS_EDITED")
	private boolean isEdited;

	@Column(name = "ONBOARDING_STATUS")
	private String onboardingStatus;

	@Column(name="ASSET_COUNT")
	private int assetCount;
	public void setDeleted(boolean deleted) {
		isDeleted = false;
	}

	public EmployeeDetail(Long assignmentId, Long associateId, String associateName, String associateCTSEmailId, String associateAmexContractorId, String associateAmexEmailId, String amexDirectorEmail, String city, String country, String serviceLine,String grade,String businessUnit,int percentAllocation,String billability, Long projectId,String projectName,Long projectManagerEmpId,String projectManagerName,String projectStartDate,String projectEndDate,String ctsEPLName,String ctsEPLId) {
		this.assignmentId = assignmentId;
		this.associateId = associateId;
		this.associateName = associateName;
		this.associateCTSEmailId = associateCTSEmailId;
		this.associateAmexContractorId = associateAmexContractorId;
		this.associateAmexEmailId = associateAmexEmailId;
		this.amexDirectorEmail = amexDirectorEmail;
		this.city = city;
		this.country = country;
		this.serviceLine = serviceLine;
		this.billability=billability;
		this.grade=grade;
		this.businessUnit=businessUnit;
		this.percentAllocation=percentAllocation;
		this.projectDetails= ProjectDetails.builder().projectId(projectId).projectName(projectName).projectManagerEmpId(projectManagerEmpId).projectManagerName(projectManagerName).projectStartDate(projectStartDate).projectEndDate(projectEndDate).ctsEPLName(ctsEPLName).ctsEPLId(ctsEPLId).build();
	}

}
