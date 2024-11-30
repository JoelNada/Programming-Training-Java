package com.cognizant.assettracker.services;


import com.cognizant.assettracker.models.dto.ExcelBadRequestDTO;
import com.cognizant.assettracker.models.dto.NotificationsDTO;
import com.cognizant.assettracker.models.entity.EmployeeDetail;
import com.cognizant.assettracker.models.entity.ExcelDetail;
import com.cognizant.assettracker.models.entity.MissingFields;
import com.cognizant.assettracker.models.entity.Notifications;
import com.cognizant.assettracker.models.enums.ExcelBadRequestType;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

public interface NotificationsService {

    public NotificationsDTO getUnreadNotifications();

    public void createExcelUpdateNotification(ExcelDetail excel, HttpStatus httpStatus, List<MissingFields> missingFields, Long invalidDataFields, List<ExcelBadRequestType> excelBadRequestTypes, ExcelBadRequestDTO excelBadRequestDTO) throws IOException;

    public void addressNotification(long notificationId);

    public NotificationsDTO viewAllNotificationByUser();

    public void createHomePageEditNotification(String associateName,Long associateId);

    public void eplNotification(String type, String epl);

    public void userRoleChangeNotification(String targetUser, String role, String action);

    public void employeeEditNotification(EmployeeDetail employeeDetail);

    public byte[] notificationMissingFields(Long notificationId);

    public void projectDetailsReplaceNotification(Long projectId, String projectName, String newAssociateName, String newAssociateId, Long notifyingAssociateId, String role);

    public NotificationsDTO notificationsDTOBuilder(List<Notifications> notifications);
}
