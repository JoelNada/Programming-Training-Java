package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.NotificationsMissingFields;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsMissingFieldsRepository extends JpaRepository<NotificationsMissingFields,Long> {


}
