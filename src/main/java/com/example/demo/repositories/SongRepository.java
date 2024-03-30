package com.example.demo.repositories;

import com.example.demo.domain.entities.Singer;
import com.example.demo.domain.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    List<Song> findAllByIsActiveTrue();
    @Query("SELECT s FROM Song s WHERE s.name LIKE %:name%")
    List<Song> findAllBySongName(@Param("name") String name);

    @Query("SELECT s FROM Song s WHERE s.song_genre.id = :idGenre")
    List<Song> findAllByGenre(@Param("idGenre") Long idGenre);

    @Query("SELECT s FROM Song s JOIN s.singers singer WHERE singer.name = :singerName")
    List<Song> findSongsBySingerName(@Param("singerName") String singerName);

    @Query("SELECT s FROM Song s JOIN s.albums album WHERE album.id =:idAlbum")
    List<Song> findAllSongsByIdAlbum(Long idAlbum);

    @Query("SELECT s FROM Song s JOIN s.playlists playlist WHERE playlist.id =:idPlaylist")
    List<Song> findAllSongsByIdPlaylist(Long idPlaylist);
    Page<Song> findAllByOrderByPopularityDesc(Pageable pageable);

    Page<Song> findAllByOrderByReleaseDateDesc(Pageable pageable);
}
