package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.dto.NotificationsDTO;
import com.cognizant.assettracker.models.entity.Notifications;
import com.cognizant.assettracker.models.exceptions.NotificationsException;
import com.cognizant.assettracker.events.NotificationEvent;
import com.cognizant.assettracker.repositories.NotificationsRepository;
import com.cognizant.assettracker.services.NotificationsService;
import com.cognizant.assettracker.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@CrossOrigin("*")
@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    private Sinks.Many<NotificationEvent> notificationAlertSink;

    @PostConstruct
    public void initializeSink(){
        notificationAlertSink =Sinks.many().multicast().onBackpressureBuffer();
    }

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    NotificationsService notificationsService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationsRepository notificationsRepository;

    @GetMapping(value = "/alert", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getUnread() {
        String email = userService.getUser();

        return notificationAlertSink.asFlux()
                .filter(notificationEvent -> email.equals(((Notifications) notificationEvent.getSource()).getEmailID()))
                .map(NotificationEvent::getMessage);
    }


    @GetMapping("/unread")
    public ResponseEntity<NotificationsDTO> unreadNotifications() {
        try {
            logger.info("Received request to get unread notifications.");
            NotificationsDTO notificationsDTOList = notificationsService.getUnreadNotifications();
            logger.debug("Retrieved {} unread notifications.", notificationsDTOList.getNotification().size());
            return ResponseEntity.status(HttpStatus.OK).body(notificationsDTOList);
        } catch (NotificationsException e) {
            logger.warn("There are no unread notifications!");
            throw new NotificationsException("There are no unread notifications!");
        }
    }


    @GetMapping("/all")
    public ResponseEntity<NotificationsDTO> allNotifications() {
        try {
            logger.info("Received request to get all notifications.");
            NotificationsDTO allNotificationsDTO = notificationsService.viewAllNotificationByUser();
            logger.debug("Retrieved {} notifications.", allNotificationsDTO.getNotification().size());
            return ResponseEntity.status(HttpStatus.OK).body(allNotificationsDTO);
        } catch (NotificationsException e) {
            logger.warn("There are no notifications!");
            throw new NotificationsException("There are no notifications!");
        }
    }

    @GetMapping(value = "/close")
    @PreDestroy
    public void close()
    {
        notificationAlertSink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
        initializeSink();
    }


    @EventListener
    public void onCustomEvent(NotificationEvent event) {
        // Send the event to subscribers
        if (notificationAlertSink != null) {
                notificationAlertSink.tryEmitNext(event);
        }
    }

    @PostMapping("/addressed")
    public ResponseEntity<String> addressNotification(@RequestBody Long notificationId) {
        try {
            logger.info("Received request to address notification with ID: {}.", notificationId);
            notificationsService.addressNotification(notificationId);
            logger.info("Notification with ID {} addressed.", notificationId);
            return ResponseEntity.ok("Issue Addressed For Notification ID : " + notificationId );
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getMessage());
            throw new NotificationsException("Exception occurred: " + e.getMessage());
        }
    }

    @PostMapping("/getreport")
    public byte[] getMissingFileByNotificationId(@RequestBody Long notificationId)
    {
        return notificationsService.notificationMissingFields(notificationId);
    }




}