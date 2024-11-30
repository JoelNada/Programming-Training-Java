package com.cognizant.assettracker.models.entity;

import com.poiji.annotation.ExcelCellName;
import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "TBL_AMEX_PROJECT_DTL")
public class ProjectDetails {
	@Id
	@Column(name = "PROJECT_ID")
	@ExcelCellName("Project ID")
	@NotNull(message ="Project ID is missing")
	private Long projectId;

	@Column(name = "PROJECT_NAME")
	@ExcelCellName("Project Description")
	@NotNull(message ="Project Name is missing")
	private String projectName;

	@Column(name = "PROJECT_MANAGER_EMP_ID")
	@ExcelCellName("Project Manager ID")
	@NotNull(message ="Project Manager ID is missing")
	private Long projectManagerEmpId;

	@Column(name = "PROJECT_MANAGER_NAME")
	@ExcelCellName("Project Manager Name")
	@NotNull(message ="Project Manager Name is missing")
	private String projectManagerName;

	@Column(name = "COGNIZANT_PROJECT_START_DATE")
	@ExcelCellName("Project Start Date")
	private String projectStartDate;

	@Column(name = "COGNIZANT_PROJECT_END_DATE")
	@ExcelCellName("Project End Date")
	private String projectEndDate;

	@Column(name = "COGNIZANT_EPL_NAME ")
	@ExcelCellName("EPL")
	private String ctsEPLName;

	@Column(name = "COGNIZANT_EPL_ID")
	@ExcelCellName("Home Manager ID")
	private String ctsEPLId;

	@Column(name = "CREATE_TIME_STAMP")
	private LocalDateTime createTimestamp;

	@Column(name = "UPDATED_TIME_STAMP")
	private LocalDateTime updatedTimestamp;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "IS_DELETED")
	private boolean isDeleted;

	public void setDeleted(boolean deleted) {
		isDeleted = false;
	}

}
