package com.example.demo.services;

import com.example.demo.domain.entities.History;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoryService {
    History save(History history);

    List<History> findAll();

    Optional<History> findOne(Long id);

}
