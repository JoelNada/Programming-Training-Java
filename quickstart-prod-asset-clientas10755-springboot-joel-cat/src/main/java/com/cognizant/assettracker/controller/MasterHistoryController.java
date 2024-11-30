package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.dto.HomePageMasterHistoryDTO;
import com.cognizant.assettracker.models.entity.MasterHistory;
import com.cognizant.assettracker.models.exceptions.MasterHistoryException;
import com.cognizant.assettracker.repositories.AssetsRepository;
import com.cognizant.assettracker.services.MasterHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master")
@CrossOrigin("*")
public class MasterHistoryController {


    private static final Logger logger = LoggerFactory.getLogger(MasterHistoryController.class);
    @Autowired
    MasterHistoryService masterHistoryService;

    @GetMapping("/all")
    public ResponseEntity<List<HomePageMasterHistoryDTO>> getAll() {
        try{
            logger.info("Received request to get all master history records.");
            List<HomePageMasterHistoryDTO> History = masterHistoryService.viewAll();
            logger.debug("Retrieved {} master history records.", History.size());
        return new ResponseEntity<>(History, HttpStatus.OK);
        } catch(MasterHistoryException e){
        logger.error("No data found");
        throw new MasterHistoryException("No data found");
    }
    }

    @GetMapping("/{searchType}/{searchValue}")
    public ResponseEntity<List<HomePageMasterHistoryDTO>> getDetails(@PathVariable String searchType, @PathVariable String searchValue) {
        try {
            logger.info("Received request to get master history details by searchType: {} and searchValue: {}.", searchType, searchValue);
            List<HomePageMasterHistoryDTO> Details = masterHistoryService.getDetails(searchType, searchValue);
            logger.debug("Retrieved {} master history details.", Details.size());
            return ResponseEntity.ok().body(Details);
        }catch (MasterHistoryException e){
            logger.error("Given search parameters yielded no results");
            throw new MasterHistoryException("Given search parameters yielded no results");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<HomePageMasterHistoryDTO>> viewHomePage(@RequestParam(value = "name", required = false) String keyword,
                                                                    @RequestParam(value = "id", required = false) Long id) {
        try {
            logger.info("Received request to search master history with keyword: {}.", keyword);
            List<HomePageMasterHistoryDTO> masterHistoryList = masterHistoryService.searchWithCriteria(keyword,id);
            logger.debug("Found {} master history records matching the search criteria.", masterHistoryList.size());
            return new ResponseEntity<>(masterHistoryList,  HttpStatus.OK);
    }catch(MasterHistoryException e){
        logger.error("No data found");
        throw new MasterHistoryException("No data found");
    }
    }

}
