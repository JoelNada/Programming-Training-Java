package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.models.dto.ExcelBadRequestDTO;
import com.cognizant.assettracker.models.dto.UserDTO;
import com.cognizant.assettracker.models.entity.*;
import com.cognizant.assettracker.models.enums.ExcelBadRequestType;
import com.cognizant.assettracker.models.enums.Role;
import com.cognizant.assettracker.models.exceptions.NotificationsException;
import com.cognizant.assettracker.publishers.NotificationEventPublisher;
import com.cognizant.assettracker.repositories.EmployeeDetailRepository;
import com.cognizant.assettracker.repositories.NotificationsMissingFieldsRepository;
import com.cognizant.assettracker.repositories.UserRepository;
import com.cognizant.assettracker.services.ExcelService;
import com.cognizant.assettracker.services.NotificationsService;
import com.cognizant.assettracker.models.dto.NotificationsDTO;
import com.cognizant.assettracker.repositories.NotificationsRepository;
import com.cognizant.assettracker.services.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class NotificationsServiceImpl implements NotificationsService {


    @Autowired
    NotificationsRepository notificationsRepository;
    @Autowired
    NotificationsMissingFieldsRepository notificationsMissingFieldsRepository;
    @Autowired
    UserService userService;
    @Autowired
    ExcelService excelService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    NotificationEventPublisher notificationEventPublisher;


    private static final Logger logger = LoggerFactory.getLogger(NotificationsServiceImpl.class);
    @SneakyThrows
    public void createExcelUpdateNotification(ExcelDetail excel, HttpStatus httpStatus, List<MissingFields> missingFields, Long invalidDataFields, List<ExcelBadRequestType> excelBadRequestTypes,ExcelBadRequestDTO excelBadRequestDTO) throws IOException {
        Notifications notifications = Notifications.builder()
                .emailID(userService.getUser())
                .notificationAddressed(false)
                .creationTimestamp(LocalDateTime.now())
                .build();

        if (httpStatus.is2xxSuccessful() || httpStatus==null)
        {
            notifications.setNotificationMessage("Excel Upload For the file : " + excel.getName() + " is completed");
            notifications.setNotificationType("Excel Upload Successful");
        }


        if (httpStatus.is4xxClientError())
        {

            if (excelBadRequestTypes.contains(ExcelBadRequestType.MISSING_FIELDS) && excelBadRequestTypes.contains(ExcelBadRequestType.DATA))
            {
                notifications.setNotificationMessage("Excel Upload For the file : " + excel.getName() + " is incomplete"
                        + ", it has " + excel.getErrorRecords() + " incomplete records" + " and " + invalidDataFields + " invalid data fields");
                notifications.setNotificationType("Excel Upload Failed");
            } else if (excelBadRequestTypes.contains(ExcelBadRequestType.MISSING_FIELDS)) {
                notifications.setNotificationMessage("Excel Upload For the file : " + excel.getName() + " is incomplete"
                        + ", it has " + excel.getErrorRecords() + " incomplete records");
                notifications.setNotificationType("Excel Upload Failed");
            }
            else if (excelBadRequestTypes.contains(ExcelBadRequestType.DATA)) {
                notifications.setNotificationMessage("Excel Upload For the file : " + excel.getName() + " is incomplete"
                        + " it has " +  invalidDataFields + " invalid data fields");
                notifications.setNotificationType("Excel Upload Failed");
            }
            else  if (excelBadRequestTypes.contains(ExcelBadRequestType.EDITED_RECORD)) {
                notifications.setNotificationMessage("Excel Upload For the file : " + excel.getName() + " is incomplete"
                        + " it has conflicting records");
                notifications.setNotificationType("Excel Upload Failed");
            }
        }
        if (excelBadRequestTypes.contains(ExcelBadRequestType.MISSING_FIELDS) || excelBadRequestTypes.contains(ExcelBadRequestType.DATA)) {
            notifications.setFileData(excelService.excelMissingFieldReport(excelBadRequestDTO.getFinalExcelResponseDTOList(), "Missing Fields and Invalid Data"));
            notifications.setFileName("Missing Fields and Invalid Data");
        }

        logger.info("Notified Current User");
        notificationsRepository.save(notifications);
        createExcelUpdateAllUserNotification(notifications.getEmailID(), httpStatus, excel, invalidDataFields, excelBadRequestTypes);
        notificationEventPublisher.publishNotificationEvent(notifications);
    }

    private void createExcelUpdateAllUserNotification(String userID, HttpStatus httpStatus, ExcelDetail excel, Long invalidDataFields, List<ExcelBadRequestType> excelBadRequestTypes) {

        String notificationMessage = null;

        if (httpStatus.is2xxSuccessful())
        {
            notificationMessage = "The User: " + userID
                    + " has uploaded the file: "
                    + excel.getName() + " successfully";

        } else if (httpStatus.is4xxClientError()) {
           if (excelBadRequestTypes.contains(ExcelBadRequestType.MISSING_FIELDS) && excelBadRequestTypes.contains(ExcelBadRequestType.DATA))
            {
               notificationMessage = "The user: " + userID+ " tried to upload the file " + excel.getName() + " but"
                        +  " it had " + excel.getErrorRecords() + " incomplete records" + " and " + invalidDataFields + " invalid data fields";
            } else if (excelBadRequestTypes.contains(ExcelBadRequestType.MISSING_FIELDS)) {
                notificationMessage = "The user: " + userID+ " tried to upload the file " + excel.getName() + " but"
                        +  " it had " + excel.getErrorRecords() + " incomplete records";
            }
            else if (excelBadRequestTypes.contains(ExcelBadRequestType.DATA)) {
                notificationMessage = "The user: " + userID+ " tried to upload the file " + excel.getName() + " but"
                        +  " it had " +  invalidDataFields + " invalid data fields";
            }
        } else {
            notificationMessage = "";
        }

        notifyAllUsers("Excel Update", notificationMessage);

    }

    public void createHomePageEditNotification(String associateName,Long associateId)
    {
        String notificationMessage = "The user " + userService.getUser()
                + "has updated the record for : " + associateId +" : "+ associateName;

        notifyAllUsers("Home Page Edit", notificationMessage);

    }

    private void notifyAllUsers(String notificationType, String notificationMessage)
    {
        List<UserDTO> allUsersList = userService.getUsers();

        List <String> allUserEmail = new ArrayList<>();

        allUsersList.forEach(userDTO ->
            allUserEmail.add(userDTO.getEmail()));

        allUserEmail.remove(userService.getUser());

        List<Notifications> notificationsList = new ArrayList<>();
        List<Role> allRoles = new ArrayList<>(Arrays.asList(Role.PMO, Role.EPL, Role.ESA_PM));

        allUserEmail.forEach(userDTO -> {
            Notifications notifications1 = Notifications.builder()
                    .notificationType(notificationType)
                    .notificationRole(allRoles)
                    .notificationAddressed(false)
                    .creationTimestamp(LocalDateTime.now())
                    .notificationMessage(notificationMessage)
                    .emailID(userDTO)
                    .build();

            notificationsList.add(notifications1);

            notificationEventPublisher.publishNotificationEvent(notifications1);
        });

        logger.info("Notifying All Users");
        notificationsRepository.saveAll(notificationsList);
    }

    private void notifyAllPMO(String notificationType, String notificationMessage)
    {
        List<Notifications> notificationsList = new ArrayList<>();
        List<Role> allRoles = new ArrayList<>(Arrays.asList(Role.PMO));
        List<String> pmoUsers = userRepository.findByPMORole();

        pmoUsers.remove(userService.getUser());
        pmoUsers.forEach(userDTO -> {
            Notifications notifications1 = Notifications.builder()
                    .notificationType(notificationType)
                    .notificationRole(allRoles)
                    .notificationAddressed(false)
                    .creationTimestamp(LocalDateTime.now())
                    .notificationMessage(notificationMessage)
                    .emailID(userDTO)
                    .build();

            notificationsList.add(notifications1);
            notificationEventPublisher.publishNotificationEvent(notifications1);
        });

        logger.info("Notifying all PMO");
        notificationsRepository.saveAll(notificationsList);
    }
    public NotificationsDTO getUnreadNotifications()
    {
        List<Notifications>  notifications = notificationsRepository.getUnreadNotificationsByUser(userService.getUser());
        if(notifications.isEmpty()){
            throw new NotificationsException("There are no unread notifications!");
        }
    return notificationsDTOBuilder(notifications);
    }


    public void addressNotification(long notificationId)
    {
        notificationsRepository.markAsAdressed(notificationId);
    }

    public NotificationsDTO viewAllNotificationByUser()
    {
        if(notificationsRepository.findByEmailID(userService.getUser()).isEmpty()){
            throw new NotificationsException("There are no notifications!");
        }
            return notificationsDTOBuilder(notificationsRepository.findByEmailID(userService.getUser()));
    }

    public NotificationsDTO notificationsDTOBuilder(List<Notifications> notifications)
    {

        NotificationsDTO notificationsDTO =NotificationsDTO.builder()
             .notification(notifications)
                .unreadNotificationCount((long) notifications.size())
             .build();

            notificationsDTO.setTotalNotificationCount((long) notificationsRepository.findByEmailID(userService.getUser()).size());

        return notificationsDTO;
    }

    public void eplNotification(String type, String epl)
    {
        String notificationMessage = "EPL: " + epl + " has been " + type;
        notifyAllUsers("EPL",notificationMessage);
    }

    public void projectDetailsReplaceNotification(Long projectId, String projectName, String newAssociateName, String newAssociateId, Long notifyingAssociateId, String role)
    {

        String notificationMessage = null;
        String notificationType = null;
        if (role.equals("EPL"))
        {
            notificationMessage = "The EPL for the Project " + projectId
                    + " : " + projectName + " has been changed to : " + newAssociateName;


            notificationType = "EPL: Replace";
        }

        if (role.equals("ESA_PM"))
        {

            notificationMessage = "The ESA PM for the Project " + projectId
                    + " : " + projectName + " has been changed to : " + newAssociateName;

            notificationType = "ESA PM: Replace";
        }


        notifyAllPMO(notificationType, notificationMessage);
        notifySpecificUser(newAssociateId, notificationMessage,notificationType,Role.EPL);
        notifySpecificUser(notifyingAssociateId.toString(), notificationMessage,notificationType,Role.ESA_PM);



    }

    private void notifySpecificUser(String employeeId, String notificationMessage, String notificationType, Role role)
    {
        String emailId = userRepository.findByEmailByEmployeeId(employeeId);


        List<Role> roles = new ArrayList<>(List.of(role));

        Notifications notifications = Notifications.builder()
                .notificationType(notificationType)
                .notificationRole(roles)
                .notificationAddressed(false)
                .creationTimestamp(LocalDateTime.now())
                .notificationMessage(notificationMessage)
                .emailID(emailId)
                .build();

        notificationEventPublisher.publishNotificationEvent(notifications);
        notificationsRepository.save(notifications);

    }

    public void userRoleChangeNotification(String targetUser, String role, String action)
    {
        String notificationAddMessage = "The role of user : " + targetUser + " has been changed to "
                + role + " by " + userService.getUser();

        String notificationRemoveMessage = "The role of user : " + targetUser + " role: " + role + "has been removed by " + userService.getUser();

        Notifications notifications = Notifications.builder()
                .notificationType("Role Change")
                .emailID(targetUser)
                .creationTimestamp(LocalDateTime.now())
                .notificationAddressed(false)
                .build();

        if (Objects.equals(action, "Add")) {
            notifications.setNotificationMessage(notificationAddMessage);
        } else if (Objects.equals(action, "Remove")) {
            notifications.setNotificationMessage(notificationRemoveMessage);
        }
        logger.info("Notifying User of Role Change");

        notificationsRepository.save(notifications);
        notificationEventPublisher.publishNotificationEvent(notifications);
    }

    public void employeeEditNotification(EmployeeDetail employeeDetail) {
        String eplId = (employeeDetailRepository.findByAssignmentId(employeeDetail.getAssignmentId()).getProjectDetails().getCtsEPLId());
        Long projectManagerId = employeeDetailRepository.findByAssignmentId(employeeDetail.getAssignmentId()).getProjectDetails().getProjectManagerEmpId();

        String notificationMessage = "The Employee " + employeeDetail.getAssociateName() + " Under the project " + employeeDetail.getProjectDetails().getProjectName()
                + " has been modified by " + userService.getUser();

        if(userRepository.findByEmployeeIdEquals(eplId)!=null)
        {
            try {

                User eplEmail = userRepository.findByEmployeeIdEquals(eplId);

                Notifications eplNotification = Notifications.builder()
                        .emailID(eplEmail.getEmail())
                        .notificationRole(Collections.singletonList(Role.EPL))
                        .notificationType("Employee Detail Update: EPL")
                        .creationTimestamp(LocalDateTime.now())
                        .notificationAddressed(false)
                        .notificationMessage(notificationMessage)
                        .build();

                notificationEventPublisher.publishNotificationEvent(eplNotification);

                notificationsRepository.save(eplNotification);

            }

            catch (Exception e)
            {
                logger.info("EPL Not Found");
                e.printStackTrace();
            }
        }

        if (userRepository.findByEmployeeIdEquals(projectManagerId.toString())!=null)
        {
            try {

                User esaPmEmail = userRepository.findByEmployeeIdEquals(projectManagerId.toString());

                Notifications esaPmNotification = Notifications.builder()
                        .emailID(esaPmEmail.getEmail())
                        .notificationRole(Collections.singletonList(Role.ESA_PM))
                        .notificationType("Employee Detail Update: ESA PM")
                        .creationTimestamp(LocalDateTime.now())
                        .notificationAddressed(false)
                        .notificationMessage(notificationMessage)
                        .build();

                notificationEventPublisher.publishNotificationEvent(esaPmNotification);
                notificationsRepository.save(esaPmNotification);
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
            notifyAllPMO("Employee Detail Update", notificationMessage);
    }
    public byte[] notificationMissingFields(Long notificationId)
    {
        Notifications missingFields = notificationsRepository.findByNotificationIdEquals(notificationId);
        //NotificationsMissingFields missingFields = notificationsMissingFieldsRepository.getReferenceById(notificationId);
        return missingFields.getFileData();
    }

    }