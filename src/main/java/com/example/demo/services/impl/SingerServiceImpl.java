package com.example.demo.services.impl;

import com.example.demo.domain.entities.Singer;
import com.example.demo.repositories.SingerRepository;
import com.example.demo.services.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerRepository SingerRepository;
    @Override
    public Singer save(Singer Singer) {
        return SingerRepository.save(Singer);
    }

    @Override
    public List<Singer> findAll() {
        return StreamSupport
                .stream(SingerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Singer> findOne(Long id) {
        return SingerRepository.findById(id);
    }

    @Override
    public Page<Singer> findAllByOrderByFollowersDesc(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return SingerRepository.findAllByOrderByFollowersDesc(pageable);
    }
}
