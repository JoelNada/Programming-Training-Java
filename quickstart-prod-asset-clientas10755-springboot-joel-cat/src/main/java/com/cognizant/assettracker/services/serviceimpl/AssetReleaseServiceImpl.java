package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.AssetRelease;
import com.cognizant.assettracker.models.exceptions.AssetNotFoundException;
import com.cognizant.assettracker.repositories.AssetReleaseRepository;
import com.cognizant.assettracker.repositories.AssetsRepository;
import com.cognizant.assettracker.models.dto.AssetReleaseDTO;
import com.cognizant.assettracker.services.AssetReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetReleaseServiceImpl implements AssetReleaseService {
    @Autowired
    AssetReleaseRepository assetReleaseRepository;

    @Autowired
    AssetsRepository assetsRepository;

    public AssetRelease findBySerialNumber(String serialNumber) {
        return assetReleaseRepository.findBySerialNumber(serialNumber);


    }

    public void saveAssetRelease(AssetReleaseDTO assetReleaseRequest) {

        String serialNumber = assetReleaseRequest.getSerialNumber();
        AssetDetail assetDetail = null;
        String pickuptime = assetReleaseRequest.getPickupTime();

        if (serialNumber != null && !serialNumber.isEmpty()) {

            assetDetail = assetsRepository.findBySerialNumber(serialNumber);
        }

        if(pickuptime==null) {
            AssetRelease newAssetRelease = AssetRelease.builder()
                    .assetReleaseReason(assetReleaseRequest.getAssetReleaseReason())
                    .message(assetReleaseRequest.getMessage())
                    .requestCreationTime(assetReleaseRequest.getRequestCreationTime())
                    .assetDetail(assetDetail)
                    .build();
            assetReleaseRepository.save(newAssetRelease);
        }
        else {
            AssetRelease existingAssetRelease = assetReleaseRepository.findBySerialNumber(serialNumber);
            if (existingAssetRelease != null) {
                int id = existingAssetRelease.getAssetReleaseId();
                assetReleaseRepository.updateBySerialNumber(pickuptime, id);
            }
        }
    }
}
