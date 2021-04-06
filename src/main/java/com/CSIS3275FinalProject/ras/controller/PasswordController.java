package com.CSIS3275FinalProject.ras.controller;

import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.entity.Token;
import com.CSIS3275FinalProject.ras.service.RegistrationService;
import com.CSIS3275FinalProject.ras.service.TokenService;
import com.CSIS3275FinalProject.ras.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PasswordController {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    TokenService tokenService;
    @Autowired
    HttpSession session;

    @PostMapping("/updateRequest")
    @ResponseBody
    public int updatePassword(@RequestParam(value = "token") String token, @RequestParam(value = "newPassword") String newPassword, @RequestParam(value = "confirmPassword") String confirmPassword) throws ParseException {
        int flag = 0;
        String userName = null;
        if (newPassword.length() < 6) {
            System.out.println("kindly please enter password of length 6 or more");
            return 0;
        }
        else if (newPassword.equals(confirmPassword)) {
            List<Token> tokenlist = tokenService.getDetail((Registration) session.getAttribute("user"));
            System.out.println(tokenlist);
            System.out.println(token);
            for (Token tokenlist1 : tokenlist) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                String format = formatter.format(new Date());
                Date date = formatter.parse(format);
                System.out.println(date);
                System.out.println(tokenlist1.getDate());
                if (tokenlist1.getDate() == date) {
                    continue;
                } else {
                    if (tokenlist1.getAuthenticationKey().equals(token)) {
                        Registration user = (Registration) session.getAttribute("user");
                        userName = user.getFirstName() + " " + user.getLastName();
                        user.setPassword(newPassword);
                        registrationService.insert(user);
                        tokenService.delete(tokenlist1);
                        flag = 1;
                        System.out.println("hello");
                        break;
                    }
                }
            }
            if (flag == 1) {
                System.out.println("successfully updated");
                return 1;
            } else {
                System.out.println("token is wrong");
                return 2;
            }
        }
        else
        {
            System.out.println("password is wrong");
            return 3;
        }
    }
}




