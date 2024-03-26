package com.example.demo.repositories;

import com.example.demo.domain.entities.Singer;
import com.example.demo.domain.entities.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SingerRepository extends CrudRepository<Singer, Long> {
}
