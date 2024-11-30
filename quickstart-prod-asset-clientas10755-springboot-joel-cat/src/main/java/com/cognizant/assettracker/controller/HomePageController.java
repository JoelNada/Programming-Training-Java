package com.cognizant.assettracker.controller;

import com.cognizant.assettracker.models.dto.HomePageRequestDTO;
import com.cognizant.assettracker.models.dto.HomePageSearchDTO;
import com.cognizant.assettracker.models.dto.HomePageDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.exceptions.EmployeeNotFoundException;
import com.cognizant.assettracker.models.exceptions.HomePageException;
import com.cognizant.assettracker.services.HomePageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@CrossOrigin("*")
public class HomePageController {

    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
    @Autowired
    private HomePageService homeservice;

    @PostMapping("/update")
    public String update(@RequestBody List<HomePageRequestDTO> homeContent) {
        try{
            logger.info("Received request to update home content.");
            String result = homeservice.groupUpdateHome(homeContent);
            logger.info("Home content updated successfully.");
            return result;
        }catch(HomePageException e){
            logger.error("Exception caused due to: "+e.getMessage());
            throw new HomePageException("Exception caused due to: "+e.getMessage());
        }
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('PMO','EPL','ESA_PM')")
    public List<HomePageDTO> getGroupByResponse(@RequestBody List<HomePageSearchDTO> homedata) {
        try {
            logger.info("Received request to get group by response.");
            List<HomePageDTO> response = homeservice.getGroupByResponse(homedata);
            System.out.println("Response: "+response);
            logger.info("Retrieved group by response successfully.");
            return response;
        }catch (EmployeeNotFoundException e){
            logger.error("The search returned no results with the given parameters");
            throw new EmployeeNotFoundException("The search returned no results with the given parameters");
        }
    }
    @PostMapping("/multipleasset")
    public EmployeeDetail getMultipleAsset(@RequestBody EmployeeDetail employeeDetail){
        return homeservice.saveMultipleAsset(employeeDetail);
    }

}
