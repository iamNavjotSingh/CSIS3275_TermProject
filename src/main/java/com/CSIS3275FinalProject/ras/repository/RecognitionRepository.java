package com.CSIS3275FinalProject.ras.repository;


import com.CSIS3275FinalProject.ras.entity.Recognition;
import com.CSIS3275FinalProject.ras.entity.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RecognitionRepository extends CrudRepository<Recognition, Integer> {

    Integer countByUserEmailAndBadgeTypeAndFlag(String email, String badge,int flag);

    @Query("select e from Recognition e where flag=1 order by recognitionId desc")
    List<Recognition> list();

    @Query("SELECT e FROM Recognition e WHERE e.date BETWEEN :startDate AND :endDate")
    List<Recognition> filterByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Recognition> findByUserEmailOrRegistration(String email, Registration registration);

    List<Recognition> findByRegistrationAndFlag(Registration registration,int flag);

    List<Recognition> findByUserEmailAndFlag(String email,int flag);

    Recognition findByRecognitionId(Integer id);

}
