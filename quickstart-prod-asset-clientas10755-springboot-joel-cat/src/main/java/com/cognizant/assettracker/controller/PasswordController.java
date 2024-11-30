package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.dto.PasswordResetRequestDTO;
import com.cognizant.assettracker.models.entity.User;
import com.cognizant.assettracker.models.exceptions.EmailNotFoundException;
import com.cognizant.assettracker.models.exceptions.InvalidPasswordResetException;
import com.cognizant.assettracker.services.EmailService;
import com.cognizant.assettracker.services.UserPasswordService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/forgot")
public class PasswordController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private UserPasswordService userPasswordService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/password")
    public ResponseEntity<String> forgotPasswordForm(@RequestParam("email")String userEmail, HttpServletRequest request){
        Optional<User> optional = userPasswordService.findUserPasswordByEmail(userEmail);
        if (!optional.isPresent()){
            logger.error("We didn't find an account for that e-mail address.");
            throw new EmailNotFoundException("We didn't find an account for that e-mail address.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("We didn't find an account for that e-mail address.");
        }else{
            User userPassword = optional.get();
            userPassword.setResetToken(UUID.randomUUID().toString());

            userPasswordService.save(userPassword);
            String appUrl = request.getScheme() + "://" + request.getServerName()+":"+3000;

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("clientassettrackercat@gmail.com");
            passwordResetEmail.setTo(userPassword.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + userPassword.getResetToken());
            emailService.sendEmail(passwordResetEmail);

            return ResponseEntity.ok("A password reset link has been sent to " + userEmail);
        }
    }


    @PostMapping("/password/reset")
    public ResponseEntity<String> setNewPassword(@RequestBody PasswordResetRequestDTO request, RedirectAttributes redir){
        Optional<User> optional = userPasswordService.findUserPasswordByResetToken(request.getToken());

        if(optional.isPresent()){
            logger.info("if token found in DB");
            User resetUser = optional.get();
            resetUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            resetUser.setResetToken(null);
            userPasswordService.save(resetUser);
            return ResponseEntity.ok("You have successfully reset your password. You may now login.");
        }else{
            logger.error("Oops! This is an invalid password reset link");
            throw new InvalidPasswordResetException("Oops! This is an invalid password reset link");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oops! This is an invalid password reset link.");
        }
    }

}
