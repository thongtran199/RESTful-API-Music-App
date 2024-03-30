package com.example.demo.services.impl;

import com.example.demo.domain.entities.Song;
import com.example.demo.repositories.SongRepository;
import com.example.demo.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Song> findAllByIsActiveTrue() {
        return new ArrayList<>(SongRepository.findAllByIsActiveTrue());
    }

    @Override
    public List<Song> findAllByGenre(Long idGenre) {
        return SongRepository.findAllByGenre(idGenre);
    }

    @Override
    public List<Song> findAllBySongName(String songName) {
        return SongRepository.findAllBySongName(songName);
    }

    @Override
    public List<Song> findSongsBySingerName(String singerName) {
        return SongRepository.findSongsBySingerName(singerName);
    }

    @Override
    public List<Song> findAllSongsByIdAlbum(Long idAlbum) {
        return SongRepository.findAllSongsByIdAlbum(idAlbum);
    }

    @Override
    public List<Song> findAllSongsByIdPlaylist(Long idPlaylist) {
        return SongRepository.findAllSongsByIdPlaylist(idPlaylist);
    }

    @Override
    public Optional<Song> findOne(Long id) {
        return SongRepository.findById(id);
    }

    @Override
    public Page<Song> findAllByOrderByPopularityDesc(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return SongRepository.findAllByOrderByPopularityDesc(pageable);
    }

    @Override
    public Page<Song> findAllByOrderByReleaseDateDesc(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return SongRepository.findAllByOrderByReleaseDateDesc(pageable);
    }
}
