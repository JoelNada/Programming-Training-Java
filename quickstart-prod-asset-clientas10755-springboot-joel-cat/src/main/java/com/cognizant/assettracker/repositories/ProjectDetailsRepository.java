package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.dto.HomePageProjectDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.entity.ProjectDetails;
import com.linecorp.armeria.server.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, Long> {
    @Query("select p.ctsEPLId from ProjectDetails p where p.projectId = ?1")
    String findEPLByID(Long projectId);
    @Query("select p from ProjectDetails p where p.projectId = ?1")
    ProjectDetails findProjectDetailsById(Long projectId);
    @Query("select p.projectManagerEmpId from ProjectDetails p where p.projectId = ?1")
    Long findProjectManagerByProjectId(Long projectId);
    @Query("select p.projectName from ProjectDetails p where p.projectId = ?1")
    String findProjectNameById(Long projectId);
    @Transactional
    @Modifying
    @Query("update ProjectDetails p set p.ctsEPLName = ?1, p.ctsEPLId = ?2 where p.projectId = ?3")
    void updateProjectEPL(String newEPLName, String newEPLId, Long projectId);

    @Transactional
    @Modifying
    @Query("update ProjectDetails p set p.projectManagerName = ?1, p.projectManagerEmpId = ?2 where p.projectId = ?3")
    void updateProjectESA(String projectManagerName, String projectManagerEmpId, Long projectId);


    @Query("SELECT pd FROM ProjectDetails pd")
    public List<ProjectDetails>findESA();

    @Query("SELECT pd FROM ProjectDetails pd WHERE pd.projectManagerEmpId = :projectManagerId")
    List<ProjectDetails> findByESA_PM(@Param("projectManagerId") String projectManagerId);

    @Query("SELECT pd FROM ProjectDetails pd WHERE pd.ctsEPLId = :ctsEPLId")
    List<ProjectDetails> findByEPL(@Param("ctsEPLId") String ctsEPLId);

    @Query("SELECT e FROM EmployeeDetail e JOIN e.projectDetails p WHERE p.projectId = :projectId")
    List<EmployeeDetail> findEmployeesByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT e FROM EmployeeDetail e JOIN e.projectDetails p WHERE p.projectName = :projectName")
    List<EmployeeDetail> findEmployeesByProjectName(@Param("projectName") String projectName);

}
