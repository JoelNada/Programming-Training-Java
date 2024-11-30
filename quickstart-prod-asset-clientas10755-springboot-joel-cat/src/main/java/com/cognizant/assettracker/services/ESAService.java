package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.HomePageEmployeeDTO;
import com.cognizant.assettracker.models.dto.HomePageProjectDTO;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface ESAService {

    public List<HomePageProjectDTO> viewAll() throws RoleNotFoundException;
    public List<HomePageEmployeeDTO> getEmployees(String projectInfoType,String projectInfo);

}
