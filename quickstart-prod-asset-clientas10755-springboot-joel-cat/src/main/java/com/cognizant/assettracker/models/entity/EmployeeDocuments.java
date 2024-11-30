package com.cognizant.assettracker.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_AMEX_EMP_DOC")
public class EmployeeDocuments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long documentId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "ASSIGNMENT_ID")
	private EmployeeDetail amexEmployeeDetail;

	@Column(name = "DOCUMENT")
	private byte[] document;

	@Column(name = "FILENAME")
	private String documentName;

	@Column(name = "CREATE_TIME_STAMP")
	private LocalDateTime createTimestamp;

	@Column(name = "UPDATED_TIME_STAMP")
	private LocalDateTime updatedTimestamp;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	private boolean pickupReceiptPresent;

	private boolean requestFormPresent;

	@Column(name = "IS_DELETED")
	private boolean isDeleted;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "SERIAL_NUMBER")
	private AssetDetail assetDetail;

	public void setDeleted(boolean deleted) {
		isDeleted = false;
	}
}
