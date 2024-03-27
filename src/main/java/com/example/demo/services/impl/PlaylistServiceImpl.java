package com.example.demo.services.impl;

import com.example.demo.domain.entities.Playlist;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    @Autowired
    private PlaylistRepository PlaylistRepository;
    @Override
    public Playlist save(Playlist Playlist) {
        return PlaylistRepository.save(Playlist);
    }

    @Override
    public List<Playlist> findAll() {
        return StreamSupport
                .stream(PlaylistRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Playlist> findOne(Long id) {
        return PlaylistRepository.findById(id);
    }

    @Override
    public List<Playlist> findAllByIdUserAndIsActiveTrue(Long idUser) {
        return PlaylistRepository.findAllByIdUserAndIsActiveTrue(idUser);
    }
}
