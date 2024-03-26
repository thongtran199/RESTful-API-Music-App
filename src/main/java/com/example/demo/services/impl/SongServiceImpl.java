package com.example.demo.services.impl;

import com.example.demo.domain.entities.Song;
import com.example.demo.repositories.SongRepository;
import com.example.demo.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository SongRepository;
    @Override
    public Song save(Song Song) {
        return SongRepository.save(Song);
    }

    @Override
    public List<Song> findAll() {
        return StreamSupport
                .stream(SongRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Song> findOne(Long id) {
        return SongRepository.findById(id);
    }
}
