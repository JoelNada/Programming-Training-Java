package com.cognizant.assettracker.repositories;


import com.cognizant.assettracker.models.entity.EPLDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface EPLRepository extends JpaRepository<EPLDetails, String> {
    @Query("select e from EPLDetails e where e.eplID = ?1")
    EPLDetails getEPLDetails(String eplID);


    @Modifying
    @Query("DELETE FROM EPLDetails t WHERE t.id = ?1")
    void deleteById(String eplID);

    @Query("SELECT eplName FROM EPLDetails t WHERE t.eplID = ?1")
    Optional<EPLDetails> findById(String eplID);

    @Query("SELECT eplID FROM EPLDetails t WHERE t.eplName = ?1")
    String getEplIdFromName(String eplName);
}
