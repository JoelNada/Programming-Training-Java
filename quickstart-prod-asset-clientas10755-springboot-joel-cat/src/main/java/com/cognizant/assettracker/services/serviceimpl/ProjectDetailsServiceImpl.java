package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.dto.ProjectDetailsDTO;
import com.cognizant.assettracker.models.dto.ProjectReplaceRequestDTO;
import com.cognizant.assettracker.models.entity.*;
import com.cognizant.assettracker.repositories.ProjectDetailsHistoryRepository;
import com.cognizant.assettracker.repositories.ProjectDetailsRepository;
import com.cognizant.assettracker.services.NotificationsService;
import com.cognizant.assettracker.services.ProjectDetailsService;
import com.cognizant.assettracker.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectDetailsServiceImpl implements ProjectDetailsService {

    @Autowired
    UserService userService;

    LocalDateTime current = LocalDateTime.now();

    @Autowired
    ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    ProjectDetailsHistoryRepository projectDetailsHistoryRepository;

    @Autowired
    NotificationsService notificationsService;



    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    private ProjectDetailsHistory projectDetailsHistoryBuilder(ProjectDetails projectDetails,String updatedField)
    {
        return ProjectDetailsHistory.builder()
                .projectDetailsHistoryUpdateDetails(new ProjectDetailsHistoryUpdateDetails(projectDetails.getProjectId(), LocalDateTime.now(),updatedField, userService.getUser()))
                .projectName(projectDetails.getProjectName())
                .projectManagerEmpId(projectDetails.getProjectManagerEmpId())
                .projectManagerName(projectDetails.getProjectManagerName())
                .projectStartDate(projectDetails.getProjectStartDate())
                .projectEndDate(projectDetails.getProjectEndDate())
                .ctsEPLId(projectDetails.getCtsEPLId())
                .ctsEPLName(projectDetails.getCtsEPLName())
                .createTimestamp(projectDetails.getCreateTimestamp())
                .isDeleted(true)
                .build();
    }

    public List<ProjectDetailsDTO> projectsByUser(String userId, String role)
    {
        List<ProjectDetailsDTO> projectDetailsDTOList = new ArrayList<>();

        List<ProjectDetails> projectsUnderUser = new ArrayList<>();

        if (Objects.equals(role, "EPL")) {
            projectsUnderUser = projectDetailsRepository.findByEPL(userId);
        }
        if (Objects.equals(role,"ESA_PM")) {
            projectsUnderUser = projectDetailsRepository.findByESA_PM(userId);
        }

        projectsUnderUser.forEach(project -> {
            ProjectDetailsDTO projectDetailsDTO = this.modelMapper().map(project, ProjectDetailsDTO.class);
            projectDetailsDTOList.add(projectDetailsDTO);
        });

        return  projectDetailsDTOList;
    }

    public String replaceProjectDetail(ProjectReplaceRequestDTO projectReplaceRequestDTO)
    {

        projectReplaceRequestDTO.getProjectID().forEach(projectID -> {
            String projectName = projectDetailsRepository.findProjectNameById(projectID);

            ProjectDetails currentProjectDetails = projectDetailsRepository.findProjectDetailsById(projectID);


            projectDetailsHistoryRepository.save(projectDetailsHistoryBuilder(currentProjectDetails,projectReplaceRequestDTO.getRole()));

            if (Objects.equals(projectReplaceRequestDTO.getRole(), "EPL"))
            {

                projectDetailsRepository.updateProjectEPL(projectReplaceRequestDTO.getAssociateName(), projectReplaceRequestDTO.getAssociateId(), projectID);
                Long projectManagerId = projectDetailsRepository.findProjectManagerByProjectId(projectID);
                notificationsService.projectDetailsReplaceNotification(projectID,projectName,projectReplaceRequestDTO.getAssociateName(),projectReplaceRequestDTO.getAssociateId(), projectManagerId, "EPL");

            }
            if (Objects.equals(projectReplaceRequestDTO.getRole(), "ESA_PM"))
            {

                projectDetailsRepository.updateProjectESA(projectReplaceRequestDTO.getAssociateName(), projectReplaceRequestDTO.getAssociateId(), projectID);
                String eplId = projectDetailsRepository.findEPLByID(projectID);
                notificationsService.projectDetailsReplaceNotification(projectID,projectName,projectReplaceRequestDTO.getAssociateName(),projectReplaceRequestDTO.getAssociateId(), Long.valueOf(eplId),"ESA_PM");

            }

        });

        return "Replaced";
    }

}
