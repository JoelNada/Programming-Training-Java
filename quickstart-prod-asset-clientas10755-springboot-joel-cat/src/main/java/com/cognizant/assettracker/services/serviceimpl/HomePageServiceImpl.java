package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.dto.AssetDetailDTO;
import com.cognizant.assettracker.models.dto.HomePageRequestDTO;
import com.cognizant.assettracker.models.dto.HomePageSearchDTO;
import com.cognizant.assettracker.models.dto.HomePageDTO;
import com.cognizant.assettracker.models.entity.*;
import com.cognizant.assettracker.models.exceptions.EmployeeNotFoundException;
import com.cognizant.assettracker.models.exceptions.HomePageException;
import com.cognizant.assettracker.repositories.AssetReleaseRepository;
import com.cognizant.assettracker.repositories.AssetsRepository;
import com.cognizant.assettracker.repositories.EmployeeDetailRepository;
import com.cognizant.assettracker.repositories.MasterHistoryRepository;
import com.cognizant.assettracker.services.AmexEmployeeDetailService;
import com.cognizant.assettracker.services.HomePageService;
import com.cognizant.assettracker.services.MasterHistoryService;
import com.cognizant.assettracker.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private AssetsRepository assetsRepository;
    @Autowired
    private AssetReleaseRepository assetReleaseRepository;
    @Autowired
    private EmployeeDetailRepository employeeDetailRepository;
    @Autowired
    private AmexEmployeeDetailService amexEmployeeDetailService;

    @Autowired
    private MasterHistoryService masterHistoryService;
    @Autowired
    private MasterHistoryRepository masterHistoryRepository;
    @Autowired
    TrackingServiceImpl trackingServiceImpl;

    @Autowired
    NotificationsService notificationsService;

    public String updateHomePage(HomePageRequestDTO homecontent)
    {
        List<AssetDetail> assetDetailsList = assetsRepository.findByAssignmentId(homecontent.getAssignmentId());
        EmployeeDetail updatedEmployeeDetails = employeeDetailRepository.findByAssignmentId(homecontent.getAssignmentId());
        MasterHistory updateMasterHistory = masterHistoryService.commonEmployeeDetailsBuilder(updatedEmployeeDetails,"NA");
        List<MasterHistory> masterHistorySaveList = new ArrayList<>();
        notificationsService.employeeEditNotification(updatedEmployeeDetails);

        if(homecontent.getAssetType().equals("asset_update"))
        {
            for(AssetDetail assetFromRepo:assetDetailsList)
            {
                for(AssetDetailDTO assetFromBody:homecontent.getAsset())
                {
                    if(assetFromBody.getSerialNumber().equals(assetFromRepo.getSerialNumber()))
                    {
                        AssetRelease assetReleaseObject = assetReleaseRepository.findBySerialNumber(assetFromBody.getSerialNumber());
                        if(assetReleaseObject!=null)
                        {
                            if(assetFromBody.getAssetReleaseReason()!=""){
                                assetReleaseObject.setAssetReleaseReason(assetFromBody.getAssetReleaseReason());
                            }
                            if(assetFromBody.getDw_pickup_requested()!="")
                            {
                                assetReleaseObject.setRequestCreationTime(assetFromBody.getDw_pickup_requested());
                            }
                            if(assetFromBody.getDw_pickup_date()!="")
                            {
                                assetReleaseObject.setPickupTimestamp(assetFromBody.getDw_pickup_date());
                            }
                           assetReleaseRepository.save(assetReleaseObject);
                            //TODO LOGGER
                            System.out.println("updated....assetrelease");
                        }
                        else
                        {
                            AssetRelease release=new AssetRelease();
                            release.setRequestCreationTime(assetFromBody.getDw_pickup_requested());
                            release.setPickupTimestamp(assetFromBody.getDw_pickup_date());
                            release.setAssetDetail(assetFromRepo);

                            masterHistoryService.AssetDetailMasterHistoryBuilder(updateMasterHistory,assetFromRepo);
                            assetReleaseRepository.save(release);
                            System.out.println("Saved new assetrelease");
                        }
                        if(assetFromBody.getSerialNumber()!="")
                        {
                            assetFromRepo.setSerialNumber(assetFromBody.getSerialNumber());
                        }
                        if(assetFromBody.getAllocated_date()!="")
                        {
                            assetFromRepo.setIssueDate(assetFromBody.getAllocated_date());
                        }
                        if(assetFromBody.getAssetModel()!="")
                        {
                            assetFromRepo.setAssetModel(assetFromBody.getAssetModel());
                        }
                        if(assetFromBody.getAssetMake()!="")
                        {
                            assetFromRepo.setAssetMake(assetFromBody.getAssetMake());
                        }
                        if(assetFromBody.getTrackingNumber()!="") {
                            assetFromRepo.setTrackingNumber(assetFromBody.getTrackingNumber());
                        }
                        if(assetFromBody.getRelease_date()!="")
                        {
                            assetFromRepo.setReturnDate(assetFromBody.getRelease_date());
                        }
                        masterHistoryService.AssetDetailMasterHistoryBuilder(updateMasterHistory,assetFromRepo);
                        AssetDetail save = assetsRepository.save(assetFromRepo);

                        //TODO Logger
                        System.out.println("updated.........");
                        amexEmployeeDetailService.buildHomePageDtoList(List.of(updatedEmployeeDetails));
                        trackingServiceImpl.track(assetFromBody.getSerialNumber());
                    }
                    else {
                        System.out.println("serialnumberupdate");
                    }
                }
            }

        }
        else
        {
            AssetDetail newAsset=new AssetDetail();

            EmployeeDetail employeeDetail = employeeDetailRepository.findByAssignmentId(homecontent.getAssignmentId());

            for(AssetDetailDTO assetDetailDTO :homecontent.getAsset())
            {
                newAsset.setSerialNumber(assetDetailDTO.getSerialNumber());
                newAsset.setIssueDate(assetDetailDTO.getAllocated_date());
                newAsset.setAssetMake(assetDetailDTO.getAssetMake());
                newAsset.setAssetModel(assetDetailDTO.getAssetModel());
                newAsset.setTrackingNumber(assetDetailDTO.getTrackingNumber());
                newAsset.setReturnDate(assetDetailDTO.getRelease_date());
                newAsset.setAmexEmployeeDetail(employeeDetail);
                List<AssetDetail> assetDetailList = new ArrayList<AssetDetail>();
                assetDetailList.add(newAsset);
                employeeDetail.setAssetsDetails(assetDetailList);
                employeeDetail.setAssetCount(assetDetailList.size());
                employeeDetailRepository.save(employeeDetail);

                AssetRelease assetRelease=new AssetRelease();
                assetRelease.setRequestCreationTime(assetDetailDTO.getDw_pickup_requested());
                assetRelease.setAssetReleaseReason(assetDetailDTO.getAssetReleaseReason());
                assetRelease.setPickupTimestamp(assetDetailDTO.getDw_pickup_date());
                assetRelease.setAssetDetail(newAsset);

                MasterHistory newAssetMasterHistory = masterHistoryService.commonEmployeeDetailsBuilder(employeeDetail,"NA");
                masterHistoryRepository.save(newAssetMasterHistory);
                assetReleaseRepository.save(assetRelease);
                amexEmployeeDetailService.buildHomePageDtoList(List.of(updatedEmployeeDetails));
                trackingServiceImpl.track(assetDetailDTO.getSerialNumber());
            }
        }

        employeeDetailRepository.markAsEdited(homecontent.getAssignmentId());

        return "Successfully modified asset details";
    }
    public String groupUpdateHome(List<HomePageRequestDTO> homeContent)
    {

        for(HomePageRequestDTO homedata:homeContent)
        {
            try{
                updateHomePage(homedata);
            }catch(Exception e){
                throw new HomePageException("Exception has occured due to: "+e.getMessage());
            }
        }

        return "Successfully modified assetdetails";
    }




    public List<HomePageDTO> getGroupByResponse(List<HomePageSearchDTO> homeSearch)
    {
        List<HomePageDTO> searchResults=new ArrayList<>();
        for(HomePageSearchDTO search:homeSearch) {
            List<HomePageDTO> searchResult = amexEmployeeDetailService.getEmployeeProjectIdOrName(search.getProjectId(), search.getProjectName(), search.getAssociateId(), search.getAssociateName());
            searchResults.addAll(searchResult);
        }
        if(searchResults.isEmpty()){
            throw new EmployeeNotFoundException("The search returned no results with the given parameters");
        }
        return searchResults;
    }
    public EmployeeDetail saveMultipleAsset(EmployeeDetail employeeDetail){
        employeeDetailRepository.save(employeeDetail);
        return employeeDetailRepository.getReferenceById(employeeDetail.getAssignmentId());
    }
}