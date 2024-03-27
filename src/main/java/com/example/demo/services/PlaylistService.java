package com.example.demo.services;

import com.example.demo.domain.entities.Playlist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistService {
    Playlist save(Playlist playlist);

    List<Playlist> findAll();


    Optional<Playlist> findOne(Long id);

    List<Playlist> findAllByIdUserAndIsActiveTrue(Long idUser);
}
