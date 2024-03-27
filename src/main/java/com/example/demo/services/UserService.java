package com.example.demo.services;

import com.example.demo.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User User);

    List<User> findAll();

    Optional<User> findOne(Long id);

    Boolean isExist(Long id);


    User findByUsername(String username);

    User signUp(User user, Integer type);

}
