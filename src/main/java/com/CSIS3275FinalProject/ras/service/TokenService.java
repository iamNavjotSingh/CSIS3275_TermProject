package com.CSIS3275FinalProject.ras.service;

import com.CSIS3275FinalProject.ras.repository.TokenRepository;
import com.CSIS3275FinalProject.ras.entity.Registration;
import com.CSIS3275FinalProject.ras.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public void insert(Token token)
    {
        tokenRepository.save(token);
    }

    public List<Token> getDetail(Registration registration)
    {
        return tokenRepository.findByRegistration(registration);
    }

    public void delete(Token token)
    {
        tokenRepository.delete(token);
    }
}
