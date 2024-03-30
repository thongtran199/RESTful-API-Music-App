package com.example.demo.services;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Song;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumService {
    Album save(Album album);

    List<Album> findAll();

    Optional<Album> findOne(Long id);

    Page<Album> findAllByOrderByPopularityDesc(int page, int size);


}

