package com.example.demo.repositories;

import com.example.demo.domain.entities.Genre;
import com.example.demo.domain.entities.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
}
