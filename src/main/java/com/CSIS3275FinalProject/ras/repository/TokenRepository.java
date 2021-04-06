package com.CSIS3275FinalProject.ras.repository;

import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenRepository extends CrudRepository<Token,Integer> {
    List<Token> findByRegistration(Registration registration);
}
