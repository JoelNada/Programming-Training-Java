package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.AssetReleaseDTO;
import com.cognizant.assettracker.models.entity.AssetRelease;
import com.cognizant.assettracker.models.exceptions.AssetNotFoundException;

public interface AssetReleaseService {

    public AssetRelease findBySerialNumber(String serialNumber) throws AssetNotFoundException;

    public void saveAssetRelease(AssetReleaseDTO assetRelease);
}
