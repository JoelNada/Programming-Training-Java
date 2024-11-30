package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.controller.UserController;
import com.cognizant.assettracker.models.enums.ReportEnum;
import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.entity.EmployeeDocuments;
import com.cognizant.assettracker.models.entity.MasterHistory;
import com.cognizant.assettracker.models.exceptions.DocumentNotFoundException;
import com.cognizant.assettracker.models.exceptions.EmployeeDocumentException;
import com.cognizant.assettracker.repositories.AssetsRepository;
import com.cognizant.assettracker.repositories.EmployeeDocumentsRepository;
import com.cognizant.assettracker.repositories.MasterHistoryRepository;
import com.cognizant.assettracker.models.dto.AmexEmployeeDocumentDTO;
import com.cognizant.assettracker.models.dto.EmployeeDocumentDTO;
import com.cognizant.assettracker.models.ResponseBuilder;
import com.cognizant.assettracker.services.AmexEmployeeDetailService;
import com.cognizant.assettracker.services.EmployeeDocumentService;
import com.cognizant.assettracker.services.MasterHistoryService;
import com.cognizant.assettracker.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

	@Autowired
	private EmployeeDocumentsRepository employeeDocumentsRepository;
	@Autowired
	private AmexEmployeeDetailService amexEmployeeDetailService;
	@Autowired
	private AssetsRepository assetsRepository;
	@Autowired
	UserService userService;
	@Autowired
	MasterHistoryService masterHistoryService;
	@Autowired
	MasterHistoryRepository masterHistoryRepository;

	public EmployeeDocuments store(AmexEmployeeDocumentDTO amexEmpDoc, int i) throws IOException {
		MultipartFile file = amexEmpDoc.getFiles()[i];

		String assignmentId = amexEmpDoc.getAssignmentId();
		String serialNumber = amexEmpDoc.getSerialNumber();
		EmployeeDetail empDetail = null;
		AssetDetail assetDetail = null;
		ReportEnum reportEnum=amexEmpDoc.getReportEnum();
		if (assignmentId != null && !assignmentId.isEmpty()) {
			Long id = Long.parseLong(assignmentId);
			empDetail = amexEmployeeDetailService.findByAssignmentId(id);
		}
		if (serialNumber != null && !serialNumber.isEmpty()) {
			assetDetail = assetsRepository.findBySerialNumber(serialNumber);
		}
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		LocalDateTime currentTime = LocalDateTime.now();
		MasterHistory newMasterHistory = masterHistoryService.commonEmployeeDetailsBuilder(empDetail, "NA");
		//masterHistoryService.AssetDetailMasterHistoryBuilder(newMasterHistory, assetDetail);
		EmployeeDocuments employeeDocuments = EmployeeDocuments.builder()
				.updatedBy(userService.getUser())
				.updatedTimestamp(currentTime)
				.document(file.getBytes()).documentName(fileName).amexEmployeeDetail(empDetail)
				.assetDetail(assetDetail).build();

		newMasterHistory.setDocument(file.getBytes());
		newMasterHistory.setDocumentName(fileName);
		if(reportEnum.equals(ReportEnum.PICKUPRECEIPT)){
			employeeDocuments.setPickupReceiptPresent(true);
			employeeDocuments.setRequestFormPresent(false);
			newMasterHistory.setPickupReceiptPresent(true);
		}
		if (reportEnum.equals(ReportEnum.REQUESTFORM)){
			employeeDocuments.setRequestFormPresent(true);
			employeeDocuments.setPickupReceiptPresent(false);
			newMasterHistory.setRequestFormPresent(true);
		}
		//masterHistoryRepository.save(newMasterHistory);
		return employeeDocumentsRepository.save(employeeDocuments);
	}

	public String save(AmexEmployeeDocumentDTO amexEmpDoc){
		String message = "";
		try {
			for (int i = 0; i < amexEmpDoc.getFiles().length; i++) {
				String path = amexEmpDoc.getFiles()[i].getOriginalFilename();
				String extension = FilenameUtils.getExtension(path);
				if (extension.equals("png") || extension.equals("pdf") || extension.equals("jpeg")) {
						store(amexEmpDoc, i);
					message = "Uploaded the file successfully: " + amexEmpDoc.getFiles()[i].getOriginalFilename();
				} else {
					message = "Could not upload the file: " + amexEmpDoc.getFiles()[i].getOriginalFilename() + "!" + " Invalid File Type";
				}

			}
		}catch(IOException e){
			throw new EmployeeDocumentException("Could not upload the file due to: "+e.getMessage());
		}
		return message;
	}

	public List<EmployeeDocumentDTO> getFile(String serialNumber, String docType) {
		List<EmployeeDocuments> employeeDocumentsList = null;
		if (Objects.equals(docType, "all")) {
			employeeDocumentsList = employeeDocumentsRepository.findBySerialNumberAll(serialNumber);
		}
		if (Objects.equals(docType, "requestForm")) {
			employeeDocumentsList = employeeDocumentsRepository.findBySerialNumberRequestForm(serialNumber);
		}
		if (Objects.equals(docType, "pickupReceipt")) {
			employeeDocumentsList = employeeDocumentsRepository.findBySerialNumberAllPickupReciept(serialNumber);
		}
		if (Objects.equals(docType, "deleted")) {
			employeeDocumentsList = employeeDocumentsRepository.findBySerialNumberDeleted(serialNumber);
		}
		List<EmployeeDocumentDTO> employeeDocumentResponse = ResponseBuilder.buildEmpDocResponse(employeeDocumentsList);
		if (employeeDocumentsList==null) {
			throw new DocumentNotFoundException("Document not found with serial number: " + serialNumber);
		}
		return employeeDocumentResponse;
	}

	public void deleteById(long id) {
		employeeDocumentsRepository.softDeleteById(id);
	}

	public byte[] downloadFileById(Long fileId) {
		if(employeeDocumentsRepository.downloadById(fileId)==null){
				throw new DocumentNotFoundException("Document not Found for download with Id: " + fileId);
		}
		return employeeDocumentsRepository.downloadById(fileId);
	}

	public String getFileName(Long fileId) {
		return employeeDocumentsRepository.getFileName(fileId);
	}
}