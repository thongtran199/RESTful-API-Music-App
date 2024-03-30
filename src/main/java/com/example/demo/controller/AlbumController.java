package com.example.demo.controller;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Playlist;
import com.example.demo.domain.entities.Song;
import com.example.demo.domain.entities.User;
import com.example.demo.services.AlbumService;
import com.example.demo.services.SongService;
import com.example.demo.services.UserService;
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
public class AlbumController {
    @Autowired
    private AlbumService AlbumService;
    @Autowired
    private SongService SongService;
    @Autowired
    private UserService UserService;

    @PostMapping(path = "/Album")
    public ResponseEntity<Album> postMapping(@RequestBody final Album Album) {
        Album.setIsActive(true);
        Album.setPopularity(0);
        Album savedAlbum = AlbumService.save(Album);
        return new ResponseEntity<>(savedAlbum, HttpStatus.OK);
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
    @GetMapping(path = "/Album/Top")
    public List<Album> getMappingTop(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Album> albumPage = AlbumService.findAllByOrderByPopularityDesc(page, size);
        return albumPage.getContent();
    }
    @PostMapping(path = "/Album/AddFavorite/{idAlbum}/{idUser}")
    public ResponseEntity<Album> postMappingAddFavorite(@PathVariable("idAlbum") Long idAlbum, @PathVariable("idUser") Long idUser) {
        Optional<User> foundUser = UserService.findOne(idUser);
        Optional<Album> foundAlbum = AlbumService.findOne(idAlbum);
        if(foundUser.isEmpty() || foundAlbum.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Album album = foundAlbum.get();
        User user = foundUser.get();
        user.user_album.add(album);
        album.setPopularity(album.getPopularity() + 1);
        Album savedAlbum = AlbumService.save(album);
        User savedUser = UserService.save(user);
        return new ResponseEntity<>(savedAlbum, HttpStatus.OK);
    }
    @DeleteMapping(path = "/Album/DeleteSong/{idAlbum}/{idSong}")
    public ResponseEntity<Album> deleteMappingDeleteSong(@PathVariable("idAlbum") Long idAlbum, @PathVariable("idSong") Long idSong) {
        Optional<Album> foundAlbum = AlbumService.findOne(idAlbum);
        Optional<Song> foundSong = SongService.findOne(idSong);
        if(foundAlbum.isEmpty() || foundSong.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Album album = foundAlbum.get();

        boolean songExistsInAlbum = album.album_song.stream().anyMatch(s -> s.getId().equals(idSong));
        if (!songExistsInAlbum)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        List<Song> updatedSongs = album.album_song.stream()
                .filter(s -> !s.getId().equals(idSong))
                .collect(Collectors.toList());

        album.setAlbum_song(updatedSongs);

        Album savedAlbum = AlbumService.save(album);
        return new ResponseEntity<>(savedAlbum, HttpStatus.OK);
    }
}
