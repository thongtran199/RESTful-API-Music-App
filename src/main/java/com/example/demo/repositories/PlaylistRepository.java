package com.example.demo.repositories;

import com.example.demo.domain.entities.Playlist;
import com.example.demo.domain.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
    @Query("SELECT p FROM Playlist p WHERE p.playlist_user.id = :idUser AND p.isActive = true")
    List<Playlist> findAllByIdUserAndIsActiveTrue(Long idUser);

    @Query("SELECT p.playlist_song FROM Playlist p WHERE p.id = :idPlaylist")
    List<Song> findAllSongsByIdPlaylist(Long idPlaylist);
}
