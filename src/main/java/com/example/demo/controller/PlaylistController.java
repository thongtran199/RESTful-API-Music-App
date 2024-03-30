package com.example.demo.controller;

import com.example.demo.domain.entities.*;
import com.example.demo.domain.entities.Playlist;
import com.example.demo.services.PlaylistService;
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
        Playlist.setIsActive(true);
        Playlist.setCreateDate(LocalDate.now());
        Playlist.setPlaylist_user(oUser.get());
        Playlist savedPlaylist = PlaylistService.save(Playlist);

        return new ResponseEntity<>(savedPlaylist, HttpStatus.OK);
    }
    @PostMapping(path = "/Playlist/AddSong/{idPlaylist}/{idSong}")
    public ResponseEntity<Playlist> postMappingAddSong(@PathVariable("idPlaylist") Long idPlaylist, @PathVariable("idSong") Long idSong) {
        Optional<Playlist> foundPlaylist = PlaylistService.findOne(idPlaylist);
        Optional<Song> foundSong = SongService.findOne(idSong);
        if(foundPlaylist.isEmpty() || foundSong.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Playlist playlist = foundPlaylist.get();
        Song song = foundSong.get();
        playlist.playlist_song.add(song);
        Playlist savedPlaylist = PlaylistService.save(playlist);
        return new ResponseEntity<>(savedPlaylist, HttpStatus.OK);
    }
    @DeleteMapping(path = "/Playlist/DeleteSong/{idPlaylist}/{idSong}")
    public ResponseEntity<Playlist> deleteMappingDeleteSong(@PathVariable("idPlaylist") Long idPlaylist, @PathVariable("idSong") Long idSong) {
        Optional<Playlist> foundPlaylist = PlaylistService.findOne(idPlaylist);
        Optional<Song> foundSong = SongService.findOne(idSong);
        if(foundPlaylist.isEmpty() || foundSong.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Playlist playlist = foundPlaylist.get();

        boolean songExistsInPlaylist = playlist.playlist_song.stream().anyMatch(s -> s.getId().equals(idSong));
        if (!songExistsInPlaylist)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        List<Song> updatedSongs = playlist.playlist_song.stream()
                .filter(s -> !s.getId().equals(idSong))
                .collect(Collectors.toList());

        playlist.setPlaylist_song(updatedSongs);

        Playlist savedPlaylist = PlaylistService.save(playlist);
        return new ResponseEntity<>(savedPlaylist, HttpStatus.OK);
    }

    @GetMapping(path = "/Playlist/{id}")
    public ResponseEntity<Playlist> getMappingById(@PathVariable("id") Long id) {
        Optional<Playlist> foundPlaylist = PlaylistService.findOne(id);
        return foundPlaylist.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/Playlist")
    public List<Playlist> getMappingByIdUser(@RequestParam(required = false) Long idUser) {
        if(idUser != null)
        {
            return PlaylistService.findAllByIdUserAndIsActiveTrue(idUser);
        }
        else {
            return PlaylistService.findAll();
        }
    }

    @DeleteMapping(path = "/Playlist/{id}")
    public ResponseEntity<Playlist> postMapping(@PathVariable("id") Long id) {
        Optional<Playlist> foundPlaylist = PlaylistService.findOne(id);
        if(foundPlaylist.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        foundPlaylist.get().setIsActive(false);
        Playlist savedPlaylist = PlaylistService.save(foundPlaylist.get());
        return new ResponseEntity<>(savedPlaylist, HttpStatus.OK);
    }
}
