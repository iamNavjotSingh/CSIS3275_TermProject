package com.CSIS3275FinalProject.ras.controller;

import com.CSIS3275FinalProject.ras.entity.Recognition;
import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.service.RecognitionService;
import com.CSIS3275FinalProject.ras.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class RecognitionController {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    RecognitionService recognitionService;
    @Autowired
    HttpSession httpSession;

    @RequestMapping(value = "/recognition", method = RequestMethod.POST)
    @ResponseBody
    public int recognizeUser(@RequestParam("email") String email,@RequestParam("message") String message,
                             @RequestParam("bagdeType") String badgetype,@RequestParam("karma") String karma)
    {

        if(httpSession.getAttribute("emailId")==null)
        {
            return 1;
        }
        if(!badgetype.equals("")&&!message.equals("")&&!karma.equals("")&&!email.equals(""))
        {
            Recognition recognition1=new Recognition();
            System.out.println("email"+email);
            recognition1.setKarma(karma);
            recognition1.setUserEmail(email);
            recognition1.setBadgeType(badgetype);
            recognition1.setMessage(message);
            System.out.println(message);
            System.out.println(httpSession.getAttribute("emailId"));
            System.out.println(recognition1.getBadgeType());
            System.out.println(email);
            Registration registration =registrationService.getUser(email);
            int points=registration.getPoints();
            if(badgetype.equals("Gold"))
            {
                points=points+30;
            }
            else if(badgetype.equals("Silver"))
            {
                points=points+20;
            }

            else if(badgetype.equals("Bronze"))
            {
                points=points+10;
            }
            registration.setPoints(points);
            registrationService.insert(registration);
            registrationService.update((String) httpSession.getAttribute("emailId"),recognition1.getBadgeType());
            recognition1.setRegistration(registrationService.getUser((String) httpSession.getAttribute("emailId")));
            recognitionService.insert(recognition1);
            return 2;
        }
        else
        {
            return 3;
        }
    }
}
