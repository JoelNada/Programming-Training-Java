package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.PDFReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PDFReportRepository extends JpaRepository<PDFReport,Long> {
    @Query("select document from PDFReport p where p.pdfId = ?1")
    byte[] downloadById(Long id);

    @Query("select documentName from PDFReport p where p.pdfId = ?1")
    String getFileName(Long id);

    void deleteById(Long id);
}
