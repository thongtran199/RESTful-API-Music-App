package com.example.demo.services.impl;

import com.example.demo.domain.entities.History;
import com.example.demo.repositories.HistoryRepository;
import com.example.demo.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryRepository HistoryRepository;
    @Override
    public History save(History History) {
        return HistoryRepository.save(History);
    }

    @Override
    public List<History> findAll() {
        return StreamSupport
                .stream(HistoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<History> findOne(Long id) {
        return HistoryRepository.findById(id);
    }
}
