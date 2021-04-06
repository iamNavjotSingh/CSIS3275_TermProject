package com.CSIS3275FinalProject.ras.repository;

import com.CSIS3275FinalProject.ras.entity.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationRepository extends CrudRepository<Registration,Integer> {
       Registration findByEmailAndPassword(String email,String password);

       @Query("select e from Registration e where email <>:email")
       List<Registration> getList(@Param("email") String email);

       Registration findByEmail(String email);

       @Query("select points from Registration where email=:email")
       Integer getPoints(@Param("email") String email);

}
