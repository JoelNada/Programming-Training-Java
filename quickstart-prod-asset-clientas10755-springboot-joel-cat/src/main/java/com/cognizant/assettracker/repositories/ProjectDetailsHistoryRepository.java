package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.ProjectDetailsHistory;
import com.cognizant.assettracker.models.entity.ProjectDetailsHistoryUpdateDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailsHistoryRepository extends JpaRepository<ProjectDetailsHistory, ProjectDetailsHistoryUpdateDetails> {
    @Query("select p from ProjectDetailsHistory p where p.projectDetailsHistoryUpdateDetails.projectId = ?1")
    List<ProjectDetailsHistory> findByProjectDetailsHistoryUpdateDetails_ProjectIdEquals(Long projectId);
}
