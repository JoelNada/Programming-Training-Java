package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.HomePageDTO;
import com.cognizant.assettracker.models.dto.HomePageRequestDTO;
import com.cognizant.assettracker.models.dto.HomePageSearchDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;

import java.util.List;

public interface HomePageService {

    public String updateHomePage(HomePageRequestDTO homecontent);

    public List<HomePageDTO> getGroupByResponse(List<HomePageSearchDTO> homesearch);

    String groupUpdateHome(List<HomePageRequestDTO> homeContent);

    public EmployeeDetail saveMultipleAsset(EmployeeDetail employeeDetail);
}
