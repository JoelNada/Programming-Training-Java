package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.models.dto.AssetDetailDTO;
import com.cognizant.assettracker.models.dto.HomePageDTO;
import com.cognizant.assettracker.models.entity.AssetRelease;
import com.cognizant.assettracker.models.exceptions.EmployeeNotFoundException;
import com.cognizant.assettracker.models.TrackingResponse;
import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.repositories.AssetsRepository;
import com.cognizant.assettracker.repositories.EmployeeDetailRepository;
import com.cognizant.assettracker.services.AmexEmployeeDetailService;
import com.cognizant.assettracker.services.EmployeeDocumentService;
import com.cognizant.assettracker.services.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AmexEmployeeDetailServiceImpl implements AmexEmployeeDetailService {
	@Autowired
	private EmployeeDetailRepository employeeDetailRepository;

	@Autowired
	private TrackingService trackingService;

	@Autowired
	private EmployeeDocumentService employeeDocumentService;

	@Autowired
	private EmployeeDetailRepository amexEmployeeDetailRepo;
	@Autowired
	private AssetReleaseServiceImpl releaseService;
	@Autowired
	private AssetsRepository assetsRepository;

	public List<HomePageDTO> buildHomePageDtoList(List<EmployeeDetail> employeeDetails) {
		List<HomePageDTO> homePageDTOS = new ArrayList<>();
		for (EmployeeDetail employeeDetail : employeeDetails) {
			HomePageDTO homePageDto = new HomePageDTO();
			EmployeeDetail onboardingUpdation = amexEmployeeDetailRepo.findByAssignmentId(employeeDetail.getAssignmentId());
			if(employeeDetail.getAssociateAmexContractorId()==null|| employeeDetail.getAssociateAmexContractorId().equals("")) {
				homePageDto.setOnboardingStatus("NotOnboarded");
				onboardingUpdation.setOnboardingStatus("NotOnboarded");
				amexEmployeeDetailRepo.save(onboardingUpdation);

			} else {
				homePageDto.setOnboardingStatus("Onboarded");
				onboardingUpdation.setOnboardingStatus("Onboarded");
				amexEmployeeDetailRepo.save(onboardingUpdation);
			}
			homePageDto.setAssignmentId(employeeDetail.getAssignmentId());
			homePageDto.setAssociateId(employeeDetail.getAssociateId());
			homePageDto.setAssociateName(employeeDetail.getAssociateName());
			homePageDto.setProjectId(employeeDetail.getProjectDetails().getProjectId());
			homePageDto.setProjectName(employeeDetail.getProjectDetails().getProjectName());
			homePageDto.setMultipleAsset(employeeDetail.getAssetCount()>1);
			List<AssetDetailDTO> assetList = new ArrayList<>();
			List<AssetDetailDTO> toAddList=new ArrayList<>();
			if (employeeDetail.getAssetsDetails() != null) {
				for (AssetDetail assetDetail : employeeDetail.getAssetsDetails()) {
					try {
						TrackingResponse trackingResponse = trackingService.track(assetDetail.getSerialNumber());
						AssetRelease assetReleaseResult = releaseService.findBySerialNumber(assetDetail.getSerialNumber());
                        AssetDetailDTO assetDetailDTO = new AssetDetailDTO();
                        assetDetailDTO.setSerialNumber(assetDetail.getSerialNumber());
                        assetDetailDTO.setAssetMake(assetDetail.getAssetMake());
                        assetDetailDTO.setAssetModel(assetDetail.getAssetModel());
                        assetDetailDTO.setAllocated_date(assetDetail.getIssueDate());
                        assetDetailDTO.setDw_pickup_date(trackingResponse.getPickuptimestamp());
                        assetDetailDTO.setStatus(trackingResponse.getStatus().getStatus());
                        assetDetailDTO.setTrackingNumber(assetDetail.getTrackingNumber());
                        assetDetailDTO.setRelease_date(assetDetail.getReturnDate());
                        assetDetailDTO.setDw_pickup_requested(trackingResponse.getCreatetimestamp());
                        assetDetailDTO.setDwpickupdoc(employeeDocumentService.getFile(assetDetail.getSerialNumber(), "requestForm"));
                        assetDetailDTO.setDwpickupreceipt(employeeDocumentService.getFile(assetDetail.getSerialNumber(), "pickupReceipt"));
                        if(assetReleaseResult==null)
                            assetDetailDTO.setAssetReleaseReason("");
						else
                            assetDetailDTO.setAssetReleaseReason(assetReleaseResult.getAssetReleaseReason());
                        toAddList.add(assetDetailDTO);
                    } catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				assetList.addAll(toAddList);

			}
			homePageDto.setAsset(assetList);

			if(homePageDto.getAsset().isEmpty() && employeeDetail.getOnboardingStatus().equals("Onboarded")) {
				AssetDetailDTO assetDetailDTO =new AssetDetailDTO();
				assetDetailDTO.setStatus("Asset detail is missing");
				List<AssetDetail> assetDetails = assetsRepository.findByAssignmentId(employeeDetail.getAssignmentId());

				for(AssetDetail asset:assetDetails) {
					asset.setStatus("Asset detail is missing");
					assetsRepository.save(asset);
				}
				homePageDto.setAsset(List.of(assetDetailDTO));
			}
			homePageDTOS.add(homePageDto);
		}
		return homePageDTOS;
	}

	public List<HomePageDTO> getEmployeeProjectIdOrName(Long projectId, String projectName, Long associateId, String associateName) {


		if (projectId != null && projectName == null && associateId != null && associateName == null) {
			List<EmployeeDetail> employeeDetails = new ArrayList<>();
			employeeDetails = employeeDetailRepository.gettingEmployeeDetailsWithProjectIdAndEmployeeId(projectId, associateId);
			if (employeeDetails.isEmpty()) {
				throw new EmployeeNotFoundException("Provide correct project Id" + projectId + "and associate Id" + associateId);
			} else {
				return buildHomePageDtoList(employeeDetails);
			}
		}

		if (projectId != null && projectName == null && associateId == null && associateName != null) {
			List<EmployeeDetail> employeeDetails = new ArrayList<>();

			employeeDetails = employeeDetailRepository.gettingEmployeeDetailsWithProjectIdAndEmployeeName(projectId, associateName);
			if (employeeDetails.isEmpty()) {
				throw new EmployeeNotFoundException("Provide correct project Id" + projectId + "and associate name" + associateName);
			} else {
				return buildHomePageDtoList(employeeDetails);
			}
		}
		if (projectId == null && projectName != null && associateId != null && associateName == null) {
			List<EmployeeDetail> employeeDetails = new ArrayList<>();
			employeeDetails = employeeDetailRepository.gettingEmployeeDetailsWithProjectNameAndEmployeeId(projectName, associateId);
			if (employeeDetails.isEmpty()) {
				throw new EmployeeNotFoundException("Provide correct project name" + projectName + "and associate id" + associateId);
			} else {
				return buildHomePageDtoList(employeeDetails);
			}

		}
		if(projectId == null && projectName != null && associateId == null && associateName != null) {
			List<EmployeeDetail> employeeDetails = new ArrayList<>();
			employeeDetails = employeeDetailRepository.gettingEmployeeDetailsWithProjectNameAndEmployeeName(projectName,associateName);
			if (employeeDetails.isEmpty()) {
				throw new EmployeeNotFoundException("Provide correct project name" + projectName + "and associate id" + associateId);
			} else {
				return buildHomePageDtoList(employeeDetails);
			}
		}
		if (projectId != null && associateName==null && associateId==null &&projectName==null) {
			List<EmployeeDetail> employeeDetails = new ArrayList<>();
			employeeDetails = employeeDetailRepository.findByProjectId(projectId);
			if (employeeDetails.isEmpty()) {
				throw new EmployeeNotFoundException("Associate not found with ESA Id " + projectId);
			} else {
				return buildHomePageDtoList(employeeDetails);
			}
		}
		if (projectName != null && projectId == null && associateName==null && associateId==null) {
			List<EmployeeDetail> employeeDetails = new ArrayList<>();
			employeeDetails = employeeDetailRepository.findByProjectName(projectName);
			if (employeeDetails.isEmpty()) {
				throw new EmployeeNotFoundException("Associate not found with ESA Name " + projectName);
			} else {
				return buildHomePageDtoList(employeeDetails);
			}

		} else {
			throw new IllegalArgumentException("Either Project ID or Project Name must be provided or project id or name and employee id or name");
		}
	}

	public List<HomePageDTO> findByProjectManagerId(String projectManagerId) {
		List<EmployeeDetail> employeeDetails=new ArrayList<>();
		employeeDetails= employeeDetailRepository.findByProjectManagerId(projectManagerId);
		if(employeeDetails.isEmpty()){
			throw new EmployeeNotFoundException("Associate not found with ESA Id "+projectManagerId);
		} else{
			return buildHomePageDtoList(employeeDetails);}
	}

	public List<EmployeeDetail> findByCtsEPLId(String ctsEPLId) {
		List<EmployeeDetail> employeeDetails=new ArrayList<>();
		employeeDetails= employeeDetailRepository.findByCtsEPLId(ctsEPLId);
		if(employeeDetails.isEmpty()){
			throw new EmployeeNotFoundException("Associate not found with ESA Id "+ctsEPLId);
		} else{
			return employeeDetails;}
	}

	public EmployeeDetail findByAssignmentId(Long id) {
		return employeeDetailRepository.findByAssignmentId(id);
	}
}
