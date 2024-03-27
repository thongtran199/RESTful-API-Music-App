package com.example.demo.services;

import com.example.demo.domain.entities.Singer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SingerService {
    Singer save(Singer Singer);

    List<Singer> findAll();

    Optional<Singer> findOne(Long id);
    Page<Singer> findAllByOrderByFollowersDesc(int page, int size);
}
