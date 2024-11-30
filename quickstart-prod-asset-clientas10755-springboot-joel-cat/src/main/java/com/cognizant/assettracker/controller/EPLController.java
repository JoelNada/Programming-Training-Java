package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.entity.EPLDetails;
import com.cognizant.assettracker.models.exceptions.EPLException;
import com.cognizant.assettracker.models.exceptions.EPLNotPresentException;
import com.cognizant.assettracker.repositories.EPLRepository;
import com.cognizant.assettracker.repositories.ProjectDetailsRepository;
import com.cognizant.assettracker.services.EPLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/epl")
public class EPLController {

    private static final Logger logger = LoggerFactory.getLogger(EPLController.class);

    @Autowired
    EPLService eplService;

    @Autowired
    EPLRepository eplRepository;

    @Autowired
    ProjectDetailsRepository projectDetailsRepository;

    @GetMapping("/get/all")//Returns list of all the EPL's.
    public ResponseEntity<List<EPLDetails>> getAllEpl()
    {
        try{
            logger.info("Received request to get all EPLs.");
            List<EPLDetails> eplList = eplService.viewAll();
            logger.debug("Retrieved {} EPLs.", eplList.size());
            return ResponseEntity.status(HttpStatus.OK).body(eplList);
        }
        catch(EPLNotPresentException e){
            logger.error("EPL List is empty");
            throw new EPLNotPresentException("EPL List is empty");
        }

    }

    @PostMapping("/add/data")//Add new EPL data.
    public ResponseEntity<String> addEpl(@RequestBody EPLDetails newEpl)
    {
        try{
            logger.info("Received request to add new EPL data: {}", newEpl);
            eplService.addEpl(newEpl);
            logger.info("Added new EPL data successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("EPL ADDED");
        }
        catch(Exception e){
            throw new EPLException("The EPL could not be added because: "+e.getMessage());
        }
    }

    @DeleteMapping("/delete/{eplId}")//Delete EPL data.
    public ResponseEntity<String> deleteEPL(@PathVariable String eplId)
    {
        try{
            logger.info("Received request to delete EPL with ID: {}", eplId);
            eplService.deleteEpl(eplId);
            logger.info("Deleted EPL with ID: {}", eplId);
            return ResponseEntity.status(HttpStatus.OK).body("EPL DELETED");
        }
        catch(EPLNotPresentException e){
            logger.error("EPL not present");
           throw new EPLNotPresentException("EPL not present");
        }
    }

}
