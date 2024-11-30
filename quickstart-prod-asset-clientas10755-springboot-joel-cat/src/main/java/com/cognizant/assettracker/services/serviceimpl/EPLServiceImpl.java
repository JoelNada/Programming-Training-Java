package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.models.dto.*;
import com.cognizant.assettracker.models.entity.EPLDetails;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.exceptions.EPLNotPresentException;
import com.cognizant.assettracker.repositories.*;
import com.cognizant.assettracker.services.EPLService;
import com.cognizant.assettracker.services.NotificationsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EPLServiceImpl implements EPLService {

    @Autowired
    EPLRepository eplRepository;

    @Autowired
    EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    NotificationsService notificationsService;
    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    ProjectDetailsHistoryRepository projectDetailsHistoryRepository;



    public List<EPLDetails> viewAll() {
        if (eplRepository.findAll().isEmpty()) {
            throw new EPLNotPresentException("EPL List is empty");
        }
        return eplRepository.findAll();
    }

    public void addEpl(EPLDetails newEpl) {
        eplRepository.save(newEpl);
        notificationsService.eplNotification("Added", newEpl.getEplName());
    }

    public void deleteEpl(String eplID) {

        String eplDetails = eplRepository.findById(eplID).toString();

        if (eplRepository.findById(eplID).isEmpty()) {
            throw new EPLNotPresentException("EPL not present");
        }
        eplRepository.deleteById(eplID);
        notificationsService.eplNotification("Removed", eplDetails.replace("Optional", ""));
    }

    public List<HomePageDTO> getEmpBydto(String eplId) {
        List<HomePageDTO> homePageDTOS = new ArrayList<>();
        List<EmployeeDetail> employeeDetails = employeeDetailRepository.getEmpDetailsByEPL(eplId);

        employeeDetails.forEach(employeeDetail -> {
            HomePageDTO homePageDto = new HomePageDTO();
            List<AssetDetailDTO> assetDetailDTOS = new ArrayList<>();

            homePageDto.setAssociateId(employeeDetail.getAssociateId());
            homePageDto.setAssociateName(employeeDetail.getAssociateName());
            homePageDto.setProjectId(employeeDetail.getProjectDetails().getProjectId());
            homePageDto.setProjectName(employeeDetail.getProjectDetails().getProjectName());
            homePageDto.setAssignmentId(employeeDetail.getAssignmentId());

            employeeDetail.getAssetsDetails().forEach(assetDetail -> {
                AssetDetailDTO assetDetailDTO = AssetDetailDTO.builder()
                        .serialNumber(assetDetail.getSerialNumber())
                        .assetMake(assetDetail.getAssetMake())
                        .assetModel(assetDetail.getAssetModel())
                        .status(assetDetail.getStatus())
                        .release_date(assetDetail.getReturnDate())
                        .trackingNumber(assetDetail.getTrackingNumber())
                        .build();

                List<EmployeeDocumentDTO> DWPickupReceipts = new ArrayList<>();
                List<EmployeeDocumentDTO> DWPickupDocuments = new ArrayList<>();

                assetDetail.getDocumentDetails().forEach(employeeDocuments -> {
                    EmployeeDocumentDTO employeeDocumentResponseobj = EmployeeDocumentDTO.builder()
                            .documentId(employeeDocuments.getDocumentId())
                            .documentName(employeeDocuments.getDocumentName())
                            .document(employeeDocuments.getDocument())
                            .createTimestamp(employeeDocuments.getCreateTimestamp())
                            .updatedBy(employeeDocuments.getUpdatedBy())
                            .updatedTimestamp(employeeDocuments.getUpdatedTimestamp())
                            .assignmentId(employeeDetail.getAssignmentId())
                            .build();

                    if (employeeDocuments.isPickupReceiptPresent()) {
                        DWPickupReceipts.add(employeeDocumentResponseobj);
                    } else {
                        DWPickupDocuments.add(employeeDocumentResponseobj);
                    }

                });
                assetDetailDTO.setDwpickupreceipt(DWPickupReceipts);
                assetDetailDTO.setDwpickupdoc(DWPickupDocuments);
                assetDetailDTOS.add(assetDetailDTO);
            });

            homePageDto.setAsset(assetDetailDTOS);
            homePageDTOS.add(homePageDto);
        });

        return homePageDTOS;
    }



}


