package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>  {
    @Query(value = "select document from tbl_amex_template t order by template_id desc limit 1", nativeQuery = true)
    byte[] downloadById(Long id);

    @Query("SELECT documentName FROM Template t WHERE t.templateId = ?1")
    String getTemplateName(Long id);

}
