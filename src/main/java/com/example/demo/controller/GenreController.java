package com.example.demo.controller;

import com.example.demo.domain.entities.Genre;
import com.example.demo.services.GenreService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@RestController
public class GenreController {
    private GenreService GenreService;
    public GenreController(GenreService GenreService) {
        this.GenreService = GenreService;
    }

    @PostMapping(path = "/Genre")
    public ResponseEntity<Genre> postMapping(@RequestBody final Genre Genre) {
        Genre savedGenre = GenreService.save(Genre);
        return new ResponseEntity<>(savedGenre, HttpStatus.OK);
    }

    @GetMapping(path = "/Genre")
    public List<Genre> getMapping() {
        List<Genre> Genres = GenreService.findAll();
        return Genres.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/Genre/{id}")
    public ResponseEntity<Genre> getMappingById(@PathVariable("id") Long id) {
        Optional<Genre> foundGenre = GenreService.findOne(id);
        return foundGenre.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
