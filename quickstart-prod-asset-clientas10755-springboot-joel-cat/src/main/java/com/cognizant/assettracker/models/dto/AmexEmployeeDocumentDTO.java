package com.cognizant.assettracker.models.dto;

import com.cognizant.assettracker.models.enums.ReportEnum;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@Component
public class AmexEmployeeDocumentDTO {
	private MultipartFile[] files;
	private String assignmentId;
	private String serialNumber;
	private ReportEnum reportEnum;
}