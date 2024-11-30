package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.ExcelReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ExcelReportRepository extends JpaRepository<ExcelReport,Long> {



    @Query("select document from ExcelReport t where t.excelId = ?1")
    byte[] downloadById(Long id);

    @Query("select documentName from ExcelReport t where t.excelId = ?1")
    String getFileName(Long id);


}
