package com.cognizant.assettracker.models;


import com.cognizant.assettracker.models.dto.EmployeeDocumentDTO;
import com.cognizant.assettracker.models.entity.*;

import java.util.ArrayList;
import java.util.List;


public class ResponseBuilder {

	public static List<EmployeeDocumentDTO> buildEmpDocResponse(List<EmployeeDocuments> empDocs) {
		List<EmployeeDocumentDTO> amexEmpDocs = new ArrayList<>();
		if (empDocs != null) {
			empDocs.forEach(docs -> amexEmpDocs.add(EmployeeDocumentDTO.builder()
					.assignmentId(docs.getAmexEmployeeDetail().getAssignmentId()).documentId(docs.getDocumentId())
					.serialNumber(docs.getAssetDetail().getSerialNumber()).document(docs.getDocument())
					.documentName(docs.getDocumentName()).createTimestamp(docs.getCreateTimestamp())
					.updatedTimestamp(docs.getUpdatedTimestamp()).updatedBy(docs.getUpdatedBy()).build())

			);
		}
		return amexEmpDocs;
	}
}
