package com.example.demo.controller;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Playlist;
import com.example.demo.domain.entities.Song;
import com.example.demo.domain.entities.User;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import com.example.demo.services.UserService;
import jakarta.persistence.Access;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@RestController
public class PlaylistController {
    @Autowired
    private PlaylistService PlaylistService;
    @Autowired
    private SongService SongService;
    @Autowired
    private UserService UserService;


    @PostMapping(path = "/Playlist/{idUser}")
    public ResponseEntity<Playlist> postMappingAddPl(@PathVariable("idUser") Long idUser, @RequestBody final Playlist Playlist) {
        Optional<User> oUser = UserService.findOne(idUser);
        if(oUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Playlist.setCreateDate(LocalDate.now());
        Playlist.setPlaylist_user(oUser.get());
        Playlist savedPlaylist = PlaylistService.save(Playlist);

        return new ResponseEntity<>(savedPlaylist, HttpStatus.CREATED);
    }
    @PostMapping(path = "/Playlist/AddSong/{idPlaylist}/{idSong}")
    public ResponseEntity<Playlist> postMappingPlAddSong(@PathVariable("idPlaylist") Long idPlaylist, @PathVariable("idSong") Long idSong) {
        Optional<Song> foundSong = SongService.findOne(idSong);
        Optional<Playlist> foundPlaylist = PlaylistService.findOne(idPlaylist);
        if(foundSong.isEmpty() || foundPlaylist.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Playlist playlist = foundPlaylist.get();
        Song song = foundSong.get();
        playlist.playlist_song.add(song);
        Playlist savedPlaylist = PlaylistService.save(playlist);
        return new ResponseEntity<>(savedPlaylist, HttpStatus.OK);
    }
    @GetMapping(path = "/Playlist")
    public List<Playlist> getMapping() {
        List<Playlist> Playlists = PlaylistService.findAll();
        return Playlists.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/Playlist/{id}")
    public ResponseEntity<Playlist> getMappingById(@PathVariable("id") Long id) {
        Optional<Playlist> foundPlaylist = PlaylistService.findOne(id);
        return foundPlaylist.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
