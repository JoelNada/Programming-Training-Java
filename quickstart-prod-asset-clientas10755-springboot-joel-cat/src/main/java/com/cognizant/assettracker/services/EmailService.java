package com.cognizant.assettracker.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendEmail(SimpleMailMessage email);
}
//SimpleMailMessage