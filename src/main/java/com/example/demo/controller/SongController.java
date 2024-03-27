package com.example.demo.controller;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Genre;
import com.example.demo.domain.entities.Song;
import com.example.demo.services.GenreService;
import com.example.demo.services.SongService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping(path = "/Song")
    public ResponseEntity<Song> postMapping(@RequestBody final Song Song) {
        Song.isActive = true;
        Optional<Genre> foundGenre = GenreService.findOne(Song.song_genre.id);
        if(foundGenre.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Song.setSong_genre(foundGenre.get());
        Song savedSong = SongService.save(Song);
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
    @GetMapping(path = "/Song/Filter")
    public List<Song> getMappingFilter(@RequestParam(required = false) Long idGenre,
                                            @RequestParam(required = false) String songName,
                                            @RequestParam(required = false) String singerName) {
        List<Song> songs = null;
        if (idGenre != null) {
            songs = SongService.findAllByGenre(idGenre);
        } else if (songName != null) {
            songs = SongService.findAllBySongName(songName);
        } else if (singerName != null) {
            songs = SongService.findSongsBySingerName(singerName);
        } else {
            songs = SongService.findAllByIsActiveTrue();
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
}
