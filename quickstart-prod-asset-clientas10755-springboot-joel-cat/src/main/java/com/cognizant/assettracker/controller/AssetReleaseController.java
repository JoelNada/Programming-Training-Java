package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.dto.AssetReleaseDTO;
import com.cognizant.assettracker.models.entity.AssetRelease;
import com.cognizant.assettracker.models.exceptions.AssetNotFoundException;
import com.cognizant.assettracker.models.exceptions.AssetReleaseSaveException;
import com.cognizant.assettracker.services.AssetReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assetrelease")
@CrossOrigin( "*")
public class AssetReleaseController {

    private static final Logger logger = LoggerFactory.getLogger(AssetReleaseController.class);
    @Autowired
    AssetReleaseService assetReleaseService;

    @GetMapping("/{serialNumber}")
    public ResponseEntity <AssetRelease> getAssetRelease(@PathVariable String serialNumber)
    {
        try{
            logger.info("Received GET request for serial number: {}", serialNumber);
        AssetRelease assetRelease = assetReleaseService.findBySerialNumber(serialNumber);
            logger.debug("Retrieved assetRelease: {}", assetRelease);
        return ResponseEntity.status(HttpStatus.OK).body(assetRelease);
        }
        catch (AssetNotFoundException e){
            logger.error("Requested asset not found");
            throw new AssetNotFoundException("Requested asset not found");

        }
    }

    @PostMapping
    public ResponseEntity<String> saveAssetRelease(@RequestBody AssetReleaseDTO assetRelease)
    {
        try{
            logger.info("Received POST request to save AssetRelease: {}",assetRelease);
        assetReleaseService.saveAssetRelease(assetRelease);
            logger.debug("Saved AssetRelease successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved Successfully");
        }
        catch(Exception e){
            logger.error("Asset could not be saved due to: "+e.getMessage());
            throw new AssetReleaseSaveException("Asset could not be saved due to: "+e.getMessage());
        }
    }


}
