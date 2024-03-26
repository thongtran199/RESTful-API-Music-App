package com.example.demo.controller;

import com.example.demo.domain.entities.Song;
import com.example.demo.services.SongService;
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
public class SongController {
    private SongService SongService;
    public SongController(SongService SongService) {
        this.SongService = SongService;
    }

    @PostMapping(path = "/Song")
    public ResponseEntity<Song> postMapping(@RequestBody final Song Song) {
        Song savedSong = SongService.save(Song);
        return new ResponseEntity<>(savedSong, HttpStatus.CREATED);
    }

    @GetMapping(path = "/Song")
    public List<Song> getMapping() {
        List<Song> songs = SongService.findAll();
        return songs.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/Song/{id}")
    public ResponseEntity<Song> getMappingById(@PathVariable("id") Long id) {
        Optional<Song> foundSong = SongService.findOne(id);
        return foundSong.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
