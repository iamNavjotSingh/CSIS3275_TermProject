package com.CSIS3275FinalProject.ras.controller;

import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.entity.Role;
import com.CSIS3275FinalProject.ras.service.RegistrationService;
import com.CSIS3275FinalProject.ras.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    RoleService roleService;

    @PostMapping("/saveChanges")
    @ResponseBody
    public int changeStatus(@RequestParam("email") String email, @RequestParam("checkbox") Boolean active,
                            @RequestParam("points") Integer points, @RequestParam("gold") Integer gold,
                            @RequestParam("silver") Integer silver, @RequestParam("bronze") Integer bronze,
                            @RequestParam(value = "roles[]") String[] roles)
    {
            Registration registration=registrationService.getUser(email);
            registration.setCurrentBadgebronze(bronze);
            registration.setCurrentBadgeSilver(silver);
            registration.setCurrentBadgeGold(gold);
            List<Role> roles1 = registration.getRoles();
            Role admin=new Role("admin");
            Role user=new Role("user",3,2,1);
            Role practiceHead=new Role("practice head",6,3,2);
            Role supervisior=new Role("superivior",9,6,3);
            List<Role> newRoles=new ArrayList<>();
            int flagForAdmin=0,flagForPracticeHead=0,flagForSupervisior=0;
            int adminInRoles=0,practiceInRole=0,supervisiorInRole=0;
            for (String role:roles) {
                if(role.equals("admin"))
                {
                    adminInRoles=1;
                    for (Role existRole:roles1) {
                        if(existRole.getRole().equals("admin"))
                        {
                            System.out.println("in admin");
                            flagForAdmin=1;
                            break;
                        }
                    }
                }
                if(role.equals("practice head"))
                {
                    practiceInRole=1;
                    for (Role existRole:roles1) {
                        if(existRole.getRole().equals("practice head"))
                        {
                            flagForPracticeHead=1;
                            break;
                        }
                    }
                }
                if(role.equals("supervisior"))
                {
                    supervisiorInRole=1;
                    System.out.println("if cleared");
                    for (Role existRole:roles1) {
                        System.out.println("existRole"+existRole);
                        if(existRole.getRole().equals("supervisior"))
                        {
                            flagForSupervisior=1;
                            break;
                        }
                    }
                }
            }
            newRoles.add(user);
            roleService.insert(user);
            if(flagForAdmin==0 && adminInRoles==1)
            {
                System.out.println("int this"+admin);
                newRoles.add(admin);
                roleService.insert(admin);
            }
            if(flagForPracticeHead==0 && practiceInRole==1)
            {
                newRoles.add(practiceHead);
                roleService.insert(practiceHead);
            }
            if(flagForSupervisior==0 && supervisiorInRole==1)
            {
                newRoles.add(supervisior);
                roleService.insert(supervisior);
            }
            registration.setRoles(newRoles);
        System.out.println("active"+active);
            if(active)
            {
                registration.setActive(1);
            }
            else
            {
                registration.setActive(0);
            }
            registration.setPoints(points);
            registrationService.insert(registration);
            return 1;
        }


}
