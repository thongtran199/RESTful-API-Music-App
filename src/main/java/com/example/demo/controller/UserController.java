package com.example.demo.controller;

import com.example.demo.domain.entities.User;
import com.example.demo.services.UserService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@RestController
public class UserController {
    private UserService UserService;
    public UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @PostMapping(path = "/User")
    public ResponseEntity<User> postMapping(@RequestBody final User User) {
        User savedUser = UserService.save(User);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/User")
    public List<User> getMapping() {
        List<User> Users = UserService.findAll();
        return Users.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/User/{id}")
    public ResponseEntity<User> getMappingById(@PathVariable("id") Long id) {
        Optional<User> foundUser = UserService.findOne(id);
        return foundUser.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
