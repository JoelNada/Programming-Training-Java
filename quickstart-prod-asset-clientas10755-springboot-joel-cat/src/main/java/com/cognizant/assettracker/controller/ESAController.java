package com.cognizant.assettracker.controller;

import com.cognizant.assettracker.models.dto.HomePageEmployeeDTO;
import com.cognizant.assettracker.models.dto.HomePageProjectDTO;
import com.cognizant.assettracker.models.exceptions.EmployeeDocumentException;
import com.cognizant.assettracker.models.exceptions.EmployeeNotFoundException;
import com.cognizant.assettracker.models.exceptions.ProjectNotFoundException;
import com.cognizant.assettracker.services.AmexEmployeeDetailService;
import com.cognizant.assettracker.services.ESAService;
import com.cognizant.assettracker.models.dto.HomePageSearchDTO;
import com.cognizant.assettracker.models.dto.HomePageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/esa")
public class ESAController {


	private static final Logger logger = LoggerFactory.getLogger(ESAController.class);
	@Autowired
	ESAService esaService;

	@GetMapping("/list/all")
	public ResponseEntity<List<HomePageProjectDTO>> getDetails() throws RoleNotFoundException {
		try{
			logger.info("Received request to get all ESA details.");
		List<HomePageProjectDTO> esaDetails = esaService.viewAll();
			logger.debug("Retrieved {} ESA details.", esaDetails.size());
		return ResponseEntity.status(HttpStatus.OK).body(esaDetails);
		}catch (RoleNotFoundException e){
			logger.error("Invalid Role");
			throw new RoleNotFoundException("Invalid Role");
		}
		catch(ProjectNotFoundException e){
			logger.error("No projects found under the role");
			throw new EmployeeNotFoundException("No Associates found under the role");
		}
	}

	@GetMapping("/list/employees")
	public ResponseEntity<List<HomePageEmployeeDTO>>getEmployeeByProjectIdOrName (@RequestParam String type,@RequestParam String info)
	{
		try{
			logger.info("Received request to get employees by type: {} and info: {}", type, info);
		List<HomePageEmployeeDTO> searchResults = esaService.getEmployees(type,info);
			logger.debug("Retrieved {} employees based on search criteria.", searchResults.size());
			return ResponseEntity.status(HttpStatus.OK).body(searchResults);
		}
		catch (IllegalArgumentException e){
			logger.error("Invalid project info type");
			throw new IllegalArgumentException("Invalid project info type");
		}
		catch(EmployeeNotFoundException e){
			logger.error("No associates found under the role");
			throw new EmployeeNotFoundException("No associates found under the role");
		}

	}

}