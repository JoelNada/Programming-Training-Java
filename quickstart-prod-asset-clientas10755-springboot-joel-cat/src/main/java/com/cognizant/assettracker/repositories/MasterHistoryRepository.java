package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.MasterHistory;
import com.cognizant.assettracker.models.entity.MasterHistoryUpdateDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterHistoryRepository extends JpaRepository<MasterHistory, MasterHistoryUpdateDetails> {

    @Query("SELECT C FROM MasterHistory C WHERE C.masterHistoryUpdateDetails.assignmentId =?1 ORDER BY C.masterHistoryUpdateDetails.updatedTimestamp DESC")
    List<MasterHistory> findbyAssignmentId(Long assignmentId);

    @Query("SELECT C FROM MasterHistory C WHERE C.masterHistoryUpdateDetails.updatedWithFile =?1 ORDER BY C.masterHistoryUpdateDetails.updatedTimestamp DESC")
    List<MasterHistory>findbyFile(String updatedWithFile);

    @Query("SELECT t FROM MasterHistory t WHERE t.associateName =?1 ORDER BY t.masterHistoryUpdateDetails.updatedTimestamp DESC")
    List<MasterHistory>searchWithCriteria(String name);

    @Query("SELECT t FROM MasterHistory t WHERE t.associateId =?1 ORDER BY t.masterHistoryUpdateDetails.updatedTimestamp DESC")
    List<MasterHistory>searchWithCriteria(Long id);

    @Query("SELECT t FROM MasterHistory t WHERE CONCAT(t.masterHistoryUpdateDetails.assignmentId, ' ', t.masterHistoryUpdateDetails.updatedWithFile, ' ', t.masterHistoryUpdateDetails.updatedTimestamp, ' ', t.masterHistoryUpdateDetails.updatedBy) LIKE %?1%")
    List<MasterHistory>search(String keyword);


    @Query("SELECT C FROM MasterHistory C WHERE C.masterHistoryUpdateDetails.updatedBy =?1 ")
    List<MasterHistory> findbyUpdatedBy(String searchValue);


}
