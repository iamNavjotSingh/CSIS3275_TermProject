package com.CSIS3275FinalProject.ras.util;

import org.springframework.mail.SimpleMailMessage;

public interface EmailUtil {
    void sendEmail(SimpleMailMessage mail);
}
