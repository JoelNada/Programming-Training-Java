package com.cognizant.assettracker.repositories;


import com.cognizant.assettracker.models.entity.MissingFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingFieldsRepository extends JpaRepository<MissingFields, String> {
}
