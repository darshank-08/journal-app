package com.example.journalApp.Service;

import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public List<User> getAll(){
       return adminRepository.findAll();
    }

}
