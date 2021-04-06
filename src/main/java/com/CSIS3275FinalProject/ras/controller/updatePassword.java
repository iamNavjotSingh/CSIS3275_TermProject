package com.CSIS3275FinalProject.ras.controller;

import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.entity.Token;
import com.CSIS3275FinalProject.ras.service.RegistrationService;
import com.CSIS3275FinalProject.ras.service.TokenService;
import com.CSIS3275FinalProject.ras.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class updatePassword {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    TokenService tokenService;
    @Autowired
    HttpSession session;
    /*
     * Sending the mail to get the password for login again,in case user has forget the password.
     * */


    @RequestMapping(value="/forget")
    public String forgetpage()
    {
        return "forget";
    }
    @PostMapping(value = "/forgot")
    @ResponseBody
    public int processForgotPasswordForm(@RequestParam("emailId") String email) {
// Lookup user in database by e-mail
        Registration user = registrationService.findByEmail(email);
        System.out.println("from controller"+user);
        if (user==null) {
            return 0;
        } else {
// Email message
            Token token=new Token();
            int randomPIN = (int)(Math.random()*9000)+1000;
            String PINString = String.valueOf(randomPIN);
            token.setAuthenticationKey(PINString);
            token.setRegistration(user);
            tokenService.insert(token);
            session.setAttribute("user",user);
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("One time token number is:"+PINString);

            emailUtil.sendEmail(passwordResetEmail);
            return 1;
        }
    }

    @RequestMapping("/passwordUpdation")
        public String passwordUpdationPage()
        {
            if(session.getAttribute("user")==null)
            {
                return "forget";
            }
            else
            {
                return "passwordUpdation";
            }
        }

}
