package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.EmployeeDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface EmployeeDocumentsRepository extends JpaRepository<EmployeeDocuments, Long> {


	@Query("SELECT t FROM EmployeeDocuments t WHERE t.amexEmployeeDetail.assignmentId = ?1 and t.assetDetail.serialNumber = ?2")
	public List<EmployeeDocuments> findByAssignmentId(Long assignmentId, String serialNumber);

	@Query("SELECT t FROM EmployeeDocuments t WHERE t.assetDetail.serialNumber = ?1 AND t.isDeleted = FALSE")
	public List<EmployeeDocuments> findBySerialNumberAll(String serialNumber);

	@Query("SELECT t FROM EmployeeDocuments t WHERE t.assetDetail.serialNumber = ?1 AND t.requestFormPresent = TRUE AND t.isDeleted = FALSE")
	List<EmployeeDocuments> findBySerialNumberRequestForm(String serialNumber);

	@Query("SELECT t FROM EmployeeDocuments t WHERE t.assetDetail.serialNumber = ?1 AND t.pickupReceiptPresent = TRUE AND t.isDeleted = FALSE")
	List<EmployeeDocuments> findBySerialNumberAllPickupReciept(String serialNumber);

	@Query("SELECT t FROM EmployeeDocuments t WHERE t.assetDetail.serialNumber = ?1 AND t.isDeleted = TRUE")
	List<EmployeeDocuments> findBySerialNumberDeleted(String serialNumber);

	@Query("SELECT document FROM EmployeeDocuments t WHERE t.id = ?1")
	public byte[] downloadById(Long id);

	@Query("SELECT documentName FROM EmployeeDocuments t WHERE t.id = ?1")
	public String getFileName(Long id);

	@Modifying
	@Query("DELETE FROM EmployeeDocuments t WHERE t.id = ?1")
	void deleteById(Long id);

	@Modifying
	@Query("UPDATE EmployeeDocuments t SET t.isDeleted = TRUE WHERE t.id =?1")
	void softDeleteById(Long id);

	@Query("SELECT t FROM EmployeeDocuments t WHERE t.amexEmployeeDetail.assignmentId = ?1")
	public List<EmployeeDocuments> findAssignmentId(Long assignmentId);



}