package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.dto.HomePageEmployeeDTO;
import com.cognizant.assettracker.models.dto.HomePageProjectDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.entity.ProjectDetails;
import com.cognizant.assettracker.models.exceptions.EmployeeNotFoundException;
import com.cognizant.assettracker.models.exceptions.ProjectNotFoundException;
import com.cognizant.assettracker.repositories.ProjectDetailsRepository;
import com.cognizant.assettracker.services.ESAService;
import com.cognizant.assettracker.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ESAServiceImpl implements ESAService {

    @Autowired
    ProjectDetailsRepository projectDetailsRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public HomePageProjectDTO projectDetailsToProjectDto(ProjectDetails projectDetails){

        return modelMapper.map(projectDetails, HomePageProjectDTO.class);
    }
    public HomePageEmployeeDTO employeeDetailsToEmployeeDto(EmployeeDetail employeeDetail){

        return modelMapper.map(employeeDetail, HomePageEmployeeDTO.class);
    }

    public List<HomePageProjectDTO> viewAll() throws RoleNotFoundException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = null;
        if (authentication != null && authentication.getDetails() instanceof String) {

            role = (String) authentication.getDetails();
        }

        if ("ESA_PM".equals(role)) {
            String employeeId = userService.getEmployeeId();
            if(projectDetailsRepository.findByESA_PM(employeeId).isEmpty()){
                throw new ProjectNotFoundException("No projects found under the role");
            }
            return projectDetailsRepository
                    .findByESA_PM(employeeId)
                    .stream()
                    .map(this::projectDetailsToProjectDto)
                    .collect(Collectors.toList());
        } else if ("EPL".equals(role)) {
            String employeeId = userService.getEmployeeId();
            if(projectDetailsRepository.findByEPL(employeeId).isEmpty()){
                throw new ProjectNotFoundException("No projects found under the role");
            }
            return projectDetailsRepository.findByEPL(employeeId).stream()
                    .map(this::projectDetailsToProjectDto)
                    .collect(Collectors.toList());
        } else if ("PMO".equals(role)) {
            if(projectDetailsRepository.findESA().isEmpty()){
                throw new ProjectNotFoundException("No projects found under the role");
            }
            return projectDetailsRepository.findESA().stream()
                    .map(this::projectDetailsToProjectDto)
                    .collect(Collectors.toList());
        }
        else
            throw new RoleNotFoundException("Invalid Role");
    }

    public List<HomePageEmployeeDTO> getEmployees(String projectInfoType,String projectInfo) {
                if ("projectId".equalsIgnoreCase(projectInfoType)) {
                    Long projectId = Long.parseLong( projectInfo);
                    if(projectDetailsRepository.findEmployeesByProjectId(projectId).isEmpty()){
                        throw new EmployeeNotFoundException("No associates found under the role");
                    }
                    return projectDetailsRepository.findEmployeesByProjectId(projectId).stream()
                            .map(this::employeeDetailsToEmployeeDto)
                            .collect(Collectors.toList());
                } else if ("projectName".equalsIgnoreCase(projectInfoType)) {
                    String projectName = (String) projectInfo;
                    if(projectDetailsRepository.findEmployeesByProjectName(projectName).isEmpty()){
                        throw new EmployeeNotFoundException("No associates found under the role");
                    }
                    return projectDetailsRepository.findEmployeesByProjectName(projectName).stream()
                            .map(this::employeeDetailsToEmployeeDto)
                            .collect(Collectors.toList());
                } else {
                    throw new IllegalArgumentException("Invalid project info type");
                }
            }
        }
