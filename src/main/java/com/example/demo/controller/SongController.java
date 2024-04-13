package com.example.demo.controller;

import com.example.demo.constant.Firebase;
import com.example.demo.domain.entities.*;
import com.example.demo.services.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@RestController
public class SongController {
    @Autowired
    private SongService SongService;
    @Autowired
    private GenreService GenreService;
    @Autowired
    private UserService UserService;
    @Autowired
    private FirebaseService firebaseService;

    @PostMapping(path = "/Song")
    public ResponseEntity<Song> postMapping(@RequestBody final Song Song, @RequestParam long genreId) {
        Song.isActive = true;
        Song.popularity = 0;
        Optional<Genre> foundGenre = GenreService.findOne(genreId);
        if(foundGenre.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Song.setSong_genre(foundGenre.get());
        Song savedSong = SongService.save(Song);
        firebaseService.sendMessageToFirebase("Bài hát mới", "Nghe ngay", Firebase.getToken());
        return new ResponseEntity<>(savedSong, HttpStatus.OK);
    }
    @DeleteMapping(path = "/Song/{id}")
    public ResponseEntity<Song> postMapping(@PathVariable("id") Long id) {
        Optional<Song> foundSong = SongService.findOne(id);
        if(foundSong.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        foundSong.get().setIsActive(false);
        Song savedSong = SongService.save(foundSong.get());
        return new ResponseEntity<>(savedSong, HttpStatus.OK);
    }

    @GetMapping(path = "/Song")
    public List<Song> getMapping() {
        List<Song> songs = SongService.findAllByIsActiveTrue();
        return songs.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }
    @GetMapping(path = "/Song/Album/{id}")
    public List<Song> getMappingAlbum(@PathVariable("id") Long idAlbum) {
        List<Song> songs = SongService.findAllSongsByIdAlbum(idAlbum);
        return songs.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }
    @GetMapping(path = "/Song/Playlist/{id}")
    public List<Song> getMappingPlaylist(@PathVariable("id") Long idPlaylist) {
        List<Song> songs = SongService.findAllSongsByIdPlaylist(idPlaylist);
        return songs.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }
    @GetMapping(path = "/Song/Filter")
    public List<Song> getMappingFilter(@RequestParam(required = false) Long idGenre,
                                            @RequestParam(required = false) String songName,
                                            @RequestParam(required = false) String singerName) {
        List<Song> songs = null;
        if (idGenre != null) {
            songs = SongService.findAllByGenre(idGenre);
        } else{
            songs = new ArrayList<>();
            if (songName != null)
                songs.addAll(SongService.findAllBySongName(songName));
            if (singerName != null)
                songs.addAll(SongService.findSongsBySingerName(singerName));
        }
        return songs;
    }

    @GetMapping(path = "/Song/{id}")
    public ResponseEntity<Song> getMappingById(@PathVariable("id") Long id) {
        Optional<Song> foundSong = SongService.findOne(id);
        return foundSong.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @GetMapping(path = "/Song/TopPopularity")
    public List<Song> getMappingTop(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Song> albumPage = SongService.findAllByOrderByPopularityDesc(page, size);
        return albumPage.getContent();
    }

    @GetMapping(path = "/Song/Lastest")
    public List<Song> getMappingLastest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Song> albumPage = SongService.findAllByOrderByReleaseDateDesc(page, size);
        return albumPage.getContent();
    }
    @GetMapping(path = "/Song/Favorite/{id}")
    public List<Song> getMappingFavorite(@PathVariable("id") Long id) {
        List<Song> songs = SongService.findAllFavoritedSongsByIdUser(id);
        return songs.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/Song/AddFavorite/{idSong}/{idUser}")
    public ResponseEntity<Song> postMappingAddFavorite(@PathVariable("idSong") Long idSong, @PathVariable("idUser") Long idUser) {
        Optional<User> foundUser = UserService.findOne(idUser);
        Optional<Song> foundSong = SongService.findOne(idSong);
        if(foundUser.isEmpty() || foundSong.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Song song = foundSong.get();
        User user = foundUser.get();
        user.user_song.add(song);
        song.setPopularity(song.getPopularity() + 1);
        Song savedSong = SongService.save(song);
        User savedUser = UserService.save(user);
        return new ResponseEntity<>(savedSong, HttpStatus.OK);
    }

    @DeleteMapping(path = "/Song/DeleteFavorite/{idSong}/{idUser}")
    public ResponseEntity<Song> postMappingDeleteFavorite(@PathVariable("idSong") Long idSong, @PathVariable("idUser") Long idUser) {
        Optional<User> foundUser = UserService.findOne(idUser);
        Optional<Song> foundSong = SongService.findOne(idSong);
        if(foundUser.isEmpty() || foundSong.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);



        Song song = foundSong.get();
        User user = foundUser.get();


        boolean songExistsInFavoriteUser = user.user_song.stream().anyMatch(s -> s.getId().equals(idSong));
        if (!songExistsInFavoriteUser)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        List<Song> updatedSong = user.user_song
                .stream().filter(s -> !s.getId().equals(idSong))
                .collect(Collectors.toList());

        user.setUser_song(updatedSong);
        song.setPopularity(song.getPopularity() - 1);
        Song savedSong = SongService.save(song);
        User savedUser = UserService.save(user);
        return new ResponseEntity<>(savedSong, HttpStatus.OK);
    }
}
