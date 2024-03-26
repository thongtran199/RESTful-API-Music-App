package com.example.demo.repositories;

import com.example.demo.domain.entities.History;
import com.example.demo.domain.entities.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {
}
