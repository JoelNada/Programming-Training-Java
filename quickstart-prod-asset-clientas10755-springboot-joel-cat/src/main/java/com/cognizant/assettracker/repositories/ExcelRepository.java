package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.ExcelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ExcelRepository extends JpaRepository<ExcelDetail, Integer> {
    Optional<ExcelDetail> findByName(String fileName);

}
