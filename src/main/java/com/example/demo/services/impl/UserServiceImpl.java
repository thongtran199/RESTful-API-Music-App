package com.example.demo.services.impl;

import com.example.demo.domain.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository UserRepository;
    @Override
    public User save(User User) {
        return UserRepository.save(User);
    }

    @Override
    public List<User> findAll() {
        return StreamSupport
                .stream(UserRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findOne(Long id) {
        return UserRepository.findById(id);
    }
}
