package com.example.demo.services;

import com.example.demo.domain.entities.Album;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumService {
    Album save(Album album);

    List<Album> findAll();

    Optional<Album> findOne(Long id);

}
