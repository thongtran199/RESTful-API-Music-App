package com.example.demo.controller;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Song;
import com.example.demo.services.AlbumService;
import com.example.demo.services.SongService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@RestController
public class AlbumController {
    @Autowired
    private AlbumService AlbumService;
    @Autowired
    private SongService SongService;

    @PostMapping(path = "/Album")
    public ResponseEntity<Album> postMapping(@RequestBody final Album Album) {
        Album savedAlbum = AlbumService.save(Album);
        return new ResponseEntity<>(savedAlbum, HttpStatus.CREATED);
    }
    @PostMapping(path = "/Album/AddSong/{idAlbum}/{idSong}")
    public ResponseEntity<Album> postMapping(@PathVariable("idAlbum") Long idAlbum, @PathVariable("idSong") Long idSong) {
        Optional<Song> foundSong = SongService.findOne(idSong);
        Optional<Album> foundAlbum = AlbumService.findOne(idAlbum);
        if(foundSong.isEmpty() || foundAlbum.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Album album = foundAlbum.get();
        Song song = foundSong.get();
        album.album_song.add(song);
        Album savedAlbum = AlbumService.save(album);
        return new ResponseEntity<>(savedAlbum, HttpStatus.OK);
    }

    @GetMapping(path = "/Album")
    public List<Album> getMapping() {
        List<Album> Albums = AlbumService.findAll();
        return Albums.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/Album/{id}")
    public ResponseEntity<Album> getMappingById(@PathVariable("id") Long id) {
        Optional<Album> foundAlbum = AlbumService.findOne(id);
        return foundAlbum.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
