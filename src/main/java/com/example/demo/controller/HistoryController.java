package com.example.demo.controller;

import com.example.demo.domain.entities.History;
import com.example.demo.services.HistoryService;
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
public class HistoryController {
    private HistoryService HistoryService;
    public HistoryController(HistoryService HistoryService) {
        this.HistoryService = HistoryService;
    }

    @PostMapping(path = "/History")
    public ResponseEntity<History> postMapping(@RequestBody final History History) {
        History savedHistory = HistoryService.save(History);
        return new ResponseEntity<>(savedHistory, HttpStatus.OK);
    }

    @GetMapping(path = "/History")
    public List<History> getMapping() {
        List<History> Historys = HistoryService.findAll();
        return Historys.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/History/{id}")
    public ResponseEntity<History> getMappingById(@PathVariable("id") Long id) {
        Optional<History> foundHistory = HistoryService.findOne(id);
        return foundHistory.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
