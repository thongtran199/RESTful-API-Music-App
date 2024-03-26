package com.example.demo.services.impl;

import com.example.demo.domain.entities.Genre;
import com.example.demo.repositories.GenreRepository;
import com.example.demo.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository GenreRepository;
    @Override
    public Genre save(Genre Genre) {
        return GenreRepository.save(Genre);
    }

    @Override
    public List<Genre> findAll() {
        return StreamSupport
                .stream(GenreRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Genre> findOne(Long id) {
        return GenreRepository.findById(id);
    }
}
