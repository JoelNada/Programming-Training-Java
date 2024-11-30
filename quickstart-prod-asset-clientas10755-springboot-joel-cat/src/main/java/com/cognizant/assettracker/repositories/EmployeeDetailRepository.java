package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.linecorp.armeria.server.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

@Repository
@Transactional
public interface EmployeeDetailRepository
		extends JpaRepository<EmployeeDetail, Long>, JpaSpecificationExecutor<EmployeeDetail> {

	@Transactional(readOnly = true)
	public EmployeeDetail findByAssignmentId(Long assignmentId);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectId = ?1")
	public List<EmployeeDetail> findByProjectId(Long projectId);



	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectManagerEmpId = ?1")
	public List<EmployeeDetail> findByProjectManagerId(String projectManagerId);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.ctsEPLId = ?1")
	public List<EmployeeDetail> findByCtsEPLId(String ctsEPLId);

	@Modifying
	@Query("UPDATE EmployeeDetail t SET t.isDeleted = TRUE WHERE t.assignmentId =?1")
	void softDeleteByName(Long assignmentId);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.isDeleted = true")
	List<EmployeeDetail> displayDeleted();

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectName = ?1")
	public List<EmployeeDetail> findByProjectName(String projectName);


	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectId = ?1 AND t.associateId = ?2")
	public List<EmployeeDetail> gettingEmployeeDetailsWithProjectIdAndEmployeeId(Long projectId,Long EmployeeID);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectId = ?1 AND t.associateName = ?2")
	public  List<EmployeeDetail>  gettingEmployeeDetailsWithProjectIdAndEmployeeName(Long projectId,String EmployeeName);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectName = ?1 AND t.associateId = ?2")
	public  List<EmployeeDetail>  gettingEmployeeDetailsWithProjectNameAndEmployeeId(String projectName,Long EmployeeID);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectName = ?1 AND t.associateName = ?2")
	public  List<EmployeeDetail>  gettingEmployeeDetailsWithProjectNameAndEmployeeName(String projectName,String EmployeeName);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.ctsEPLId = ?1")
	public List<EmployeeDetail> getEmpDetailsByEPL(String eplId);

	@Query("SELECT t FROM EmployeeDetail t WHERE t.projectDetails.projectManagerEmpId = ?1")
	public List<EmployeeDetail> getEmpDetailsByESA(Long projectManagerEmpId);

	@Modifying
	@Query("UPDATE EmployeeDetail e SET e.isEdited = true WHERE e.assignmentId = ?1")
	void markAsEdited(Long assignmentId);

	@Query("select e.isEdited from EmployeeDetail e where e.assignmentId = ?1 and e.isEdited = true")
	boolean isEmployeeEdited(Long assignmentId);



}
