package com.example.demo.service;

import com.example.demo.model.UserAccount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface UserService {

    Optional<UserAccount> findByEmail(String name);

    Optional<UserAccount> findById(Long id);

    UserAccount save(UserAccount userModel);

    List<UserAccount> findAll();




}