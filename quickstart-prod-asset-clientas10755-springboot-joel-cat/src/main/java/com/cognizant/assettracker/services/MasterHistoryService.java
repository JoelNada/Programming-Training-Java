package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.HomePageMasterHistoryDTO;
import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.entity.MasterHistory;
import com.cognizant.assettracker.models.entity.ProjectDetails;

import java.util.List;

public interface MasterHistoryService {

    public List<HomePageMasterHistoryDTO> viewAll();

    public List<HomePageMasterHistoryDTO> searchWithCriteria(String name, Long id);

    public List<HomePageMasterHistoryDTO> getDetails(String searchType, String searchValue);

    public List<HomePageMasterHistoryDTO> displayDetails(List<MasterHistory> masterHistories);

    public MasterHistory commonEmployeeDetailsBuilder(EmployeeDetail empmodel, String fileName);

    public List<MasterHistory> AssetDetailMasterHistoryBuilder(MasterHistory masterHistory, AssetDetail assetDetail);

}
