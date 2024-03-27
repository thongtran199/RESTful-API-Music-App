package com.example.demo.controller;

import com.example.demo.domain.entities.User;
import com.example.demo.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService UserService;

    @PostMapping(path = "/Register/{type}")
    public ResponseEntity<User> signUp(@PathVariable("type") Integer type, @RequestBody final User postUser) {
        User savedUser = UserService.signUp(postUser, type);
        if(savedUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);

    }

    @PutMapping(path = "/User/{id}")
    public ResponseEntity<User> putMapping(@PathVariable("id") Long id,
                                              @RequestBody User userDto)
    {
        if(!UserService.isExist(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userDto.setId(id);
        User savedUser = UserService.save(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
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
