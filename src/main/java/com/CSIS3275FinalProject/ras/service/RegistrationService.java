package com.CSIS3275FinalProject.ras.service;

import com.CSIS3275FinalProject.ras.repository.RegistrationRepository;
import com.CSIS3275FinalProject.ras.entity.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    RegistrationRepository registrationRepository;

    public void insert(Registration registration)
    {
        registrationRepository.save(registration);
    }

    public int getpoints(String email)
    {
        return registrationRepository.getPoints(email);
    }
    public Registration checkForLogin(String email,String password)
    {
      if(registrationRepository.findByEmailAndPassword(email,password)!=null)
      {
          return registrationRepository.findByEmailAndPassword(email, password);
      }
      else {
          return null;
      }
    }
    public List<Registration> getDetails(String email)
    {
        return (List<Registration>) registrationRepository.getList(email);
    }

    public void update(String email,String badge)
    {
        if(badge.equals("Gold"))
        {
            Registration registration=registrationRepository.findByEmail(email);
            registration.setCurrentBadgeGold(registration.getCurrentBadgeGold()-1);
            registrationRepository.save(registration);
        }
        else if(badge.equals("Silver"))
        {
            Registration registration=registrationRepository.findByEmail(email);
            registration.setCurrentBadgeSilver(registration.getCurrentBadgeSilver()-1);
            registrationRepository.save(registration);
        }
        else
        {
            Registration registration=registrationRepository.findByEmail(email);
            registration.setCurrentBadgebronze(registration.getCurrentBadgebronze()-1);
            registrationRepository.save(registration);
        }
    }

    public Registration getUser(String email)
    {
        return registrationRepository.findByEmail(email);
    }

    public Registration findByEmail(String email)
    {
             return  registrationRepository.findByEmail(email);
    }


}
