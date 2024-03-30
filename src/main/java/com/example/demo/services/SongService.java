package com.example.demo.services;

import com.example.demo.domain.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongService {
    Song save(Song song);

    List<Song> findAll();

    List<Song> findAllByIsActiveTrue();

    List<Song> findAllByGenre(Long idGenre);

    List<Song> findAllBySongName(String songName);
    List<Song> findSongsBySingerName(String singerName);

    List<Song> findAllSongsByIdAlbum(Long idAlbum);

    List<Song> findAllSongsByIdPlaylist(Long idPlaylist);

    Optional<Song> findOne(Long id);

    Page<Song> findAllByOrderByPopularityDesc(int page, int size);

    Page<Song> findAllByOrderByReleaseDateDesc(int page, int size);

}
