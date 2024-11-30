package com.cognizant.assettracker.services.serviceimpl;



import com.cognizant.assettracker.controller.UserController;
import com.cognizant.assettracker.models.dto.AssetDetailDTO;
import com.cognizant.assettracker.models.dto.HomePageMasterHistoryDTO;
import com.cognizant.assettracker.models.entity.*;
import com.cognizant.assettracker.models.exceptions.MasterHistoryException;
import com.cognizant.assettracker.repositories.AssetReleaseRepository;
import com.cognizant.assettracker.repositories.MasterHistoryRepository;
import com.cognizant.assettracker.models.dto.EmployeeDocumentDTO;
import com.cognizant.assettracker.services.MasterHistoryService;
import com.cognizant.assettracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MasterHistoryServiceImpl implements MasterHistoryService {

    @Autowired
    MasterHistoryRepository masterHistoryRepository;

    @Autowired
    UserService userService;

    LocalDateTime current = LocalDateTime.now();
    String timeInMilliSeconds = String.valueOf(System.currentTimeMillis());
    @Autowired
    private AssetReleaseRepository assetReleaseRepository;

    public List<HomePageMasterHistoryDTO> viewAll()
    {
        List<MasterHistory> masterHistoryList = masterHistoryRepository.findAll();
        if(masterHistoryList.isEmpty()){
            throw new MasterHistoryException("No data found");
        }
        return displayDetails(masterHistoryList);

    }

    public List<HomePageMasterHistoryDTO> searchWithCriteria(String name, Long id) {
        if (name != null && id==null) {
            return displayDetails(masterHistoryRepository.searchWithCriteria(name));
        }
        else if(name==null && id!=null)
            return displayDetails(masterHistoryRepository.searchWithCriteria(id));
        //TODO Write Exception - AB
        return null;
    }

    public List<HomePageMasterHistoryDTO> getDetails(String searchType, String searchValue) {


        List<MasterHistory> filterCriteria = new ArrayList<>();
        if (Objects.equals(searchType, "assignmentId")) {

            filterCriteria = masterHistoryRepository.findbyAssignmentId(Long.parseLong(searchValue));
        } else if (Objects.equals(searchType, "updatedWithFile")) {
            filterCriteria = masterHistoryRepository.findbyFile(searchValue);
        } else if (Objects.equals(searchType, "updatedBy"))  {
            filterCriteria = masterHistoryRepository.findbyUpdatedBy(searchValue);
        } else {
            return List.of();
        }
        return displayDetails(filterCriteria);


    }

    public List<HomePageMasterHistoryDTO> displayDetails(List<MasterHistory> masterHistories)
    {
        List<HomePageMasterHistoryDTO> homePageMasterHistoryDTOS = new ArrayList<HomePageMasterHistoryDTO>();
        masterHistories.stream()
                .forEach(
                        masterHistory -> {

                            HomePageMasterHistoryDTO homePageMasterHistoryDTO = new HomePageMasterHistoryDTO();

                            homePageMasterHistoryDTO.setAssociateId(masterHistory.getAssociateId());
                            homePageMasterHistoryDTO.setAssignmentId(masterHistory.getMasterHistoryUpdateDetails().getAssignmentId());
                            homePageMasterHistoryDTO.setAssociateName(masterHistory.getAssociateName());
                            homePageMasterHistoryDTO.setProjectId(masterHistory.getProjectId());
                            homePageMasterHistoryDTO.setProjectName(masterHistory.getProjectName());
                            homePageMasterHistoryDTO.setUpdatedTimestamp(masterHistory.getUpdatedTimestamp());
                            homePageMasterHistoryDTO.setUpdatedwithFile(masterHistory.getUpdatedwithFile());
                            homePageMasterHistoryDTO.setUpdatedBy(masterHistory.getUpdatedBy());

                            AssetDetailDTO assetDetailDTO = AssetDetailDTO.builder()
                                    .serialNumber(masterHistory.getSerialNumber())
                                    .allocated_date(masterHistory.getAllocated_date())
                                    .status(masterHistory.getStatus())
                                    .dw_pickup_date(masterHistory.getPickupTimestamp())
                                    .dw_pickup_requested(masterHistory.getRequestCreationTime())
                                    .assetMake(masterHistory.getAssetMake())
                                    .assetModel(masterHistory.getAssetModel())
                                    .assetReleaseReason(masterHistory.getAssetReleaseReason())
                                    .release_date(masterHistory.getReleaseDate())
                                    .trackingNumber(masterHistory.getTrackingNumber())
                                    .build();

                          List<EmployeeDocumentDTO> employeeDocumentDTOObj = new ArrayList<>();
                                 EmployeeDocumentDTO documentResponse = EmployeeDocumentDTO.builder()
                                    .document(masterHistory.getDocument())
                                    .documentName(masterHistory.getDocumentName())
                                    .documentId(masterHistory.getDocumentId())
                                    .build();
                                 employeeDocumentDTOObj.add(documentResponse);
                            if (masterHistory.isRequestFormPresent())
                                assetDetailDTO.setDwpickupreceipt(employeeDocumentDTOObj);
                            else
                                assetDetailDTO.setDwpickupdoc(employeeDocumentDTOObj);
                            homePageMasterHistoryDTO.
                                    setAsset(assetDetailDTO);
                            homePageMasterHistoryDTOS.add(homePageMasterHistoryDTO);

                        }
                );
        return homePageMasterHistoryDTOS;
    }

    public MasterHistory commonEmployeeDetailsBuilder(EmployeeDetail empmodel, String fileName)
    {

        return MasterHistory.builder()
                .masterHistoryUpdateDetails(new MasterHistoryUpdateDetails(empmodel.getAssignmentId(), current,fileName, userService.getUser()))
                .associateId(empmodel.getAssociateId())
                .associateName(empmodel.getAssociateName())
                .associateCTSEmailId(empmodel.getAssociateCTSEmailId())
                .associateAmexContractorId(empmodel.getAssociateAmexContractorId())
                .associateAmexEmailId(empmodel.getAssociateAmexEmailId())
                .amexDirectorEmail(empmodel.getAmexDirectorEmail())
                .city(empmodel.getCity())
                .country(empmodel.getCountry())
                .serviceLine(empmodel.getServiceLine())
                .isActiveInAmex(empmodel.getIsActiveInAmex())
                .releaseDate(empmodel.getReleaseDate())
                .projectId(empmodel.getProjectDetails().getProjectId())
                .projectName(empmodel.getProjectDetails().getProjectName())
                .projectManagerEmpId(empmodel.getProjectDetails().getProjectManagerEmpId())
                .projectManagerName(empmodel.getProjectDetails().getProjectManagerName())
                .projectStartDate(empmodel.getProjectDetails().getProjectStartDate())
                .projectEndDate(empmodel.getProjectDetails().getProjectEndDate())
                .ctsEPLId(empmodel.getProjectDetails().getCtsEPLId())
                .ctsEPLName(empmodel.getProjectDetails().getCtsEPLName())
                .isDeleted(true)
                .build();
    }

    public List<MasterHistory> AssetDetailMasterHistoryBuilder(MasterHistory masterHistory, AssetDetail assetDetail)
    {
        List<MasterHistory> masterHistoryList = new ArrayList<>();

        masterHistory.setSerialNumber(assetDetail.getSerialNumber());
        masterHistory.setAssetName(assetDetail.getAssetMake());
        masterHistory.setAssetModel(assetDetail.getAssetModel());
        masterHistory.setAllocated_date(assetDetail.getIssueDate());
        masterHistory.setAssetMake(assetDetail.getAssetMake());
        masterHistory.setTrackingNumber(assetDetail.getTrackingNumber());
        masterHistory.setReleaseDate(assetDetail.getReturnDate());
        masterHistory.setStatus(assetDetail.getStatus());
        masterHistory.getMasterHistoryUpdateDetails().setUpdatedTimestamp(LocalDateTime.now());

        AssetRelease assetRelease = assetReleaseRepository.findBySerialNumber(assetDetail.getSerialNumber());
        if (assetRelease!=null)
        {
            if (assetRelease.getAssetReleaseReason()!=null)
                masterHistory.setAssetReleaseReason(assetRelease.getAssetReleaseReason());
           if (assetRelease.getRequestCreationTime()!=null)
               masterHistory.setRequestCreationTime(assetRelease.getRequestCreationTime());
           if (assetRelease.getPickupTimestamp()!=null)
                masterHistory.setPickupTimestamp(assetRelease.getPickupTimestamp());

        }

        if (assetDetail.getDocumentDetails()!=null) {
            assetDetail.getDocumentDetails().forEach(employeeDocuments -> {
                masterHistory.setDocument(employeeDocuments.getDocument());
                masterHistory.setDocumentName(employeeDocuments.getDocumentName());
                masterHistory.setDocumentId(employeeDocuments.getDocumentId());
                masterHistory.setPickupReceiptPresent(employeeDocuments.isPickupReceiptPresent());
                masterHistory.setRequestFormPresent(employeeDocuments.isRequestFormPresent());

                masterHistoryList.add(masterHistory);
            });
        }
        else
            masterHistoryList.add(masterHistory);

        masterHistoryRepository.save(masterHistory);
        return masterHistoryList;
    }

}