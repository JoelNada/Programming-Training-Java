package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.AssetRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface AssetReleaseRepository extends JpaRepository<AssetRelease, String> {

    @Query("SELECT t FROM AssetRelease t WHERE t.assetDetail.serialNumber = ?1")
    AssetRelease findBySerialNumber(String serialNumber);

    @Transactional
    @Modifying
    @Query("UPDATE AssetRelease t SET t.pickupTimestamp =?1 WHERE t.assetReleaseId=?2")
    void updateBySerialNumber(String pickupTimestamp,int id);

    @Query("SELECT t FROM AssetRelease t WHERE t.assetDetail.amexEmployeeDetail.projectDetails.ctsEPLId = ?1")
    public List<AssetRelease> getAssetDetailsByEPL(String eplId);

    @Query("SELECT t FROM AssetRelease t WHERE t.assetDetail.amexEmployeeDetail.projectDetails.projectManagerEmpId = ?1")
    public List<AssetRelease> getAssetDetailsByESA(Long projectManagerEmpId);
}
