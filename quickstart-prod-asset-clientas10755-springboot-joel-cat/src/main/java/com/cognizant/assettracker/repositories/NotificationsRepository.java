package com.cognizant.assettracker.repositories;


import com.cognizant.assettracker.models.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    @Query("select n from Notifications n where n.notificationId = ?1")
    Notifications findByNotificationIdEquals(Long notificationId);

    @Query("select n from Notifications n where n.emailID = ?1 ORDER BY n.creationTimestamp DESC")
    List<Notifications> findByEmailID(String emailID);

    @Transactional
    @Modifying
    @Query("update Notifications n set n.notificationAddressed = true where n.notificationId = ?1")
    void markAsAdressed(Long notificationId);

    @Query("SELECT t FROM Notifications t WHERE t.emailID=?1 AND t.notificationAddressed=false ORDER BY t.creationTimestamp DESC")
    public List<Notifications> getUnreadNotificationsByUser(String emailID);

}