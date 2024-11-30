package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.ProjectDetailsDTO;
import com.cognizant.assettracker.models.dto.ProjectReplaceRequestDTO;

import java.util.List;

public interface ProjectDetailsService {


    public List<ProjectDetailsDTO> projectsByUser(String associateID, String role);

    public String replaceProjectDetail(ProjectReplaceRequestDTO projectReplaceRequestDTO);
}
