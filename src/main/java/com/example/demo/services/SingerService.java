package com.example.demo.services;

import com.example.demo.domain.entities.Singer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SingerService {
    Singer save(Singer Singer);

    List<Singer> findAll();

    Optional<Singer> findOne(Long id);

}
