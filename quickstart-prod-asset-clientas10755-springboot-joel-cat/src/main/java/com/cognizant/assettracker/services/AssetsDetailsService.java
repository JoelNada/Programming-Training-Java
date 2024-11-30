package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.entity.AssetDetail;

import java.util.List;

public interface AssetsDetailsService {

    public List<AssetDetail> getAllData();
    public AssetDetail getAssetsDetailsbyID(Long id);
    public AssetDetail findBySerialNumber(String id);
}
