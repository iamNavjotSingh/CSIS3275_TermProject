package com.CSIS3275FinalProject.ras.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtilImpl implements EmailUtil {

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendEmail(SimpleMailMessage mailMessage) {
        sender.send(mailMessage);
    }
}