package com.cognizant.assettracker.models.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class EmployeeDocumentDTO {

	private Long documentId;
	private Long assignmentId;
	private String serialNumber;
	private byte[] document;
	private String documentName;
	private LocalDateTime createTimestamp;
	private LocalDateTime updatedTimestamp;
	private String updatedBy;

}
