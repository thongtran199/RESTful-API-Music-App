package com.example.demo.services;

import com.example.demo.domain.entities.Song;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongService {
    Song save(Song song);

    List<Song> findAll();

    Optional<Song> findOne(Long id);

}
