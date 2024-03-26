package com.example.demo.services;

import com.example.demo.domain.entities.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreService {
    Genre save(Genre genre);

    List<Genre> findAll();

    Optional<Genre> findOne(Long id);

}
