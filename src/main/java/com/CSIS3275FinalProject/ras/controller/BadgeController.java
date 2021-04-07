package com.CSIS3275FinalProject.ras.controller;

import com.CSIS3275FinalProject.ras.entity.Recognition;
import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.service.OrderDetailService;
import com.CSIS3275FinalProject.ras.service.RecognitionService;
import com.CSIS3275FinalProject.ras.service.RegistrationService;
import com.CSIS3275FinalProject.ras.service.RoleService;
import com.CSIS3275FinalProject.ras.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class BadgeController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    RoleService roleService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    RecognitionService recognitionService;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/Badges")
    public ModelAndView display()
    {
        if(httpSession.getAttribute("emailId")==null)
        {
            ModelAndView modelAndView=new ModelAndView("Registration");
            return modelAndView;
        }
        Registration registration = registrationService.checkForLogin((String) httpSession.getAttribute("emailId"), (String) httpSession.getAttribute("pass"));
        ModelAndView modelAndView=new ModelAndView("badges");
        modelAndView.addObject("earnedGold", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Gold"));
        modelAndView.addObject("earnedSilver", recognitionService.getEarned((String) httpSession.getAttribute("emailId"), "Silver"));
        modelAndView.addObject("earnedBronze", recognitionService.getEarned((String)httpSession.getAttribute("emailId"), "Bronze"));
        modelAndView.addObject("recognitionlist",recognitionService.getListByEmail((String) httpSession.getAttribute("emailId"),registration));
        modelAndView.addObject("points",registration.getPoints());
        modelAndView.addObject("sharedBadges",recognitionService.getListshared(registration));
        modelAndView.addObject("receivedBadges",recognitionService.getListReceived((String) httpSession.getAttribute("emailId")));
        modelAndView.addObject("redeempoints",orderDetailService.getListForOrderPLacing(registration,1));
        return  modelAndView;
    }

    @PostMapping("/revokeBadge")
    @ResponseBody
    public int delete(@RequestParam("badgeGiveId") Integer id)
    {

        Recognition listid = recognitionService.getListid(id);
        listid.setFlag(0);
        recognitionService.insert(listid);
        Registration registration=registrationService.findByEmail(listid.getUserEmail());//for decresing points who has take the badge
        Registration registration1=listid.getRegistration();//for increasing current badge who has given the badge
        int points=registration.getPoints();
        int earnedBadge;
        if(listid.getBadgeType().equals("Gold"))
        {
            if(points>30)
            {
                points=points-30;
            }
            earnedBadge=registration1.getCurrentBadgeGold()+1;
            registration1.setCurrentBadgeGold(earnedBadge);
        }
        if(listid.getBadgeType().equals("Silver"))
        {
            if(points>20)
            {
                points=points-20;
            }
            earnedBadge=registration1.getCurrentBadgeSilver()+1;
            registration1.setCurrentBadgeSilver(earnedBadge);
        }
        if(listid.getBadgeType().equals("Bronze"))
        {
            if(points>10)
            {
                points=points-10;
            }
            earnedBadge=registration1.getCurrentBadgebronze()+1;
            registration1.setCurrentBadgebronze(earnedBadge);
        }
        registration.setPoints(points);
        registrationService.insert(registration);
        registrationService.insert(registration1);
        return 1;
    }
    public int square(int x) {
        return x*x;
    }
}
