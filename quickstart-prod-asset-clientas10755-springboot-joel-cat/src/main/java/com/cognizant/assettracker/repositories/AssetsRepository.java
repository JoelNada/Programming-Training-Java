package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.AssetDetail;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetsRepository extends JpaRepository<AssetDetail, Long> {

	public AssetDetail findBySerialNumber(String id);

	@Query("SELECT t FROM AssetDetail t WHERE t.amexEmployeeDetail.assignmentId =?1")
	public List<AssetDetail> findByAssignmentId(Long assignmentId);

	@Query("SELECT t FROM AssetDetail t WHERE t.amexEmployeeDetail.projectDetails.ctsEPLId = ?1")
	public List<AssetDetail> getAssetDetailsByEPL(String eplId);

	@Query("SELECT t FROM AssetDetail t WHERE t.amexEmployeeDetail.projectDetails.projectManagerEmpId = ?1")
	public List<AssetDetail> getAssetDetailsByESA(Long projectManagerEmpId);

}
