package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.dto.ProjectDetailsDTO;
import com.cognizant.assettracker.models.dto.ProjectReplaceRequestDTO;
import com.cognizant.assettracker.models.exceptions.EPLNotPresentException;
import com.cognizant.assettracker.models.exceptions.ProjectNotFoundException;
import com.cognizant.assettracker.repositories.ProjectDetailsHistoryRepository;
import com.cognizant.assettracker.services.ProjectDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/projects")
public class ProjectDetailsController {

    @Autowired
    ProjectDetailsService projectDetailsService;

    @Autowired
    ProjectDetailsHistoryRepository projectDetailsHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProjectDetailsController.class);


    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestBody Long projectId)
    {
        return ResponseEntity.ok(projectDetailsHistoryRepository.findByProjectDetailsHistoryUpdateDetails_ProjectIdEquals(projectId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDetailsDTO>> projectsByUser(@RequestParam String userId, String role)
    {
        try {
            logger.info("Retrieving Projects under {} : {}",role,userId);
            List<ProjectDetailsDTO> eplProjectDetailsDTOList = projectDetailsService.projectsByUser(userId, role);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(eplProjectDetailsDTOList);
        }
        catch (EPLNotPresentException e)
        {
            logger.error("EPL is not present");
            throw new EPLNotPresentException("EPL Not Present");
        }
    }

    @PostMapping("/projectreplace")
    public ResponseEntity<String> replaceProjectEPL(@RequestBody ProjectReplaceRequestDTO projectReplaceRequestDTO)
    {
        try {
            logger.info("Replacing {} For Projects : {}",projectReplaceRequestDTO.getRole(), projectReplaceRequestDTO.getProjectID());
            projectDetailsService.replaceProjectDetail(projectReplaceRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Replaced "+ projectReplaceRequestDTO.getRole() + " For the Projects");

        }
        catch (ProjectNotFoundException e)
        {
            logger.error("Project Not Found for ID's");
            throw new ProjectNotFoundException("Project not Found for ID ");
        }
    }

}
