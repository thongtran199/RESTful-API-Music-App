package com.example.demo.controller;

import com.example.demo.domain.entities.Singer;
import com.example.demo.services.SingerService;
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
public class SingerController {
    private SingerService SingerService;
    public SingerController(SingerService SingerService) {
        this.SingerService = SingerService;
    }

    @PostMapping(path = "/Singer")
    public ResponseEntity<Singer> postMapping(@RequestBody final Singer Singer) {
        Singer savedSinger = SingerService.save(Singer);
        return new ResponseEntity<>(savedSinger, HttpStatus.CREATED);
    }

    @GetMapping(path = "/Singer")
    public List<Singer> getMapping() {
        List<Singer> Singers = SingerService.findAll();
        return Singers.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/Singer/{id}")
    public ResponseEntity<Singer> getMappingById(@PathVariable("id") Long id) {
        Optional<Singer> foundSinger = SingerService.findOne(id);
        return foundSinger.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
