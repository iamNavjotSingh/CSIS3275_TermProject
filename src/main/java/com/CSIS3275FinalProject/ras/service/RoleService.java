package com.CSIS3275FinalProject.ras.service;

import com.CSIS3275FinalProject.ras.repository.RoleRepository;
import com.CSIS3275FinalProject.ras.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public void insert(Role role)
    {
        roleRepository.save(role);
    }




}
