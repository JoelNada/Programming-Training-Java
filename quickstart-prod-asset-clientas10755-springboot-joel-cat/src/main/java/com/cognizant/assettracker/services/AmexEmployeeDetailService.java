package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.HomePageDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;

import java.util.List;

public interface AmexEmployeeDetailService {

    public List<HomePageDTO> buildHomePageDtoList(List<EmployeeDetail> employeeDetails);

    public List<HomePageDTO> getEmployeeProjectIdOrName(Long projectId, String projectName, Long associateId, String associateName);

    public List<HomePageDTO> findByProjectManagerId(String projectManagerId);

    public List<EmployeeDetail> findByCtsEPLId(String ctsEPLId);

    public EmployeeDetail findByAssignmentId(Long id);
}
