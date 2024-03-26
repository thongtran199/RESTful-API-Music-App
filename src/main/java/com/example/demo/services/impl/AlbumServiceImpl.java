package com.example.demo.services.impl;

import com.example.demo.domain.entities.Album;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumRepository AlbumRepository;
    @Override
    public Album save(Album Album) {
        return AlbumRepository.save(Album);
    }

    @Override
    public List<Album> findAll() {
        return StreamSupport
                .stream(AlbumRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Album> findOne(Long id) {
        return AlbumRepository.findById(id);
    }
}
