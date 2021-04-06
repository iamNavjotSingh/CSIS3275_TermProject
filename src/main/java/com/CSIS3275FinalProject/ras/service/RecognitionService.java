package com.CSIS3275FinalProject.ras.service;

import com.CSIS3275FinalProject.ras.repository.RecognitionRepository;
import com.CSIS3275FinalProject.ras.entity.Recognition;
import com.CSIS3275FinalProject.ras.entity.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecognitionService {

    @Autowired
    RecognitionRepository recognitionRepository;

    public void insert(Recognition recognition)
    {
        recognitionRepository.save(recognition);
    }

    public int getEarned(String email,String Badge)
    {
        return recognitionRepository.countByUserEmailAndBadgeTypeAndFlag(email,Badge,1);
    }
    public List<Recognition> getList()
    {
        return recognitionRepository.list();
    }
    public List<Recognition> getFilterList(Date startDate,Date endDate)
    {
        return recognitionRepository.filterByDate(startDate,endDate);
    }
    public List<Recognition> getListByEmail(String email, Registration registration)
    {
        return recognitionRepository.findByUserEmailOrRegistration(email,registration);
    }
    public List<Recognition> getListshared(Registration registration)
    {
        return recognitionRepository.findByRegistrationAndFlag(registration,1);
    }
    public List<Recognition> getListReceived(String email)
    {
        return recognitionRepository.findByUserEmailAndFlag(email,1);
    }

    public Recognition getListid(int id)
    {
        return  recognitionRepository.findByRecognitionId(id);
    }
}
