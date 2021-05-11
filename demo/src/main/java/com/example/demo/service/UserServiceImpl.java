package com.example.demo.service;

import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserAccount> findByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public UserAccount save(UserAccount userAccount) {
        return userRepository.save(userAccount);

    }

    @Override
    public List<UserAccount> findAll() {
        return userRepository.findAll();
    }


}