package com.example.demo.controller;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Singer;
import com.example.demo.domain.entities.Song;
import com.example.demo.domain.entities.User;
import com.example.demo.services.SingerService;
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
public class SingerController {
    @Autowired
    private SingerService SingerService;
    @Autowired
    private SongService SongService;
    @Autowired
    private UserService UserService;


    @PostMapping(path = "/Singer")
    public ResponseEntity<Singer> postMapping(@RequestBody final Singer Singer) {
        Singer.followers = 0;
        Singer savedSinger = SingerService.save(Singer);
        return new ResponseEntity<>(savedSinger, HttpStatus.OK);
    }
    @PostMapping(path = "/Singer/AddSong/{idSinger}/{idSong}")
    public ResponseEntity<Singer> postMapping(@PathVariable("idSinger") Long idSinger, @PathVariable("idSong") Long idSong) {
        Optional<Song> foundSong = SongService.findOne(idSong);
        Optional<Singer> foundSinger = SingerService.findOne(idSinger);
        if(foundSong.isEmpty() || foundSinger.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Singer singer = foundSinger.get();
        Song song = foundSong.get();
        singer.singer_song.add(song);
        Singer savedSinger = SingerService.save(singer);
        return new ResponseEntity<>(savedSinger, HttpStatus.OK);
    }

    @PostMapping(path = "/Singer/AddFollowers/{idSinger}/{idUser}")
    public ResponseEntity<Singer> postMappingAddFollowers(@PathVariable("idSinger") Long idSinger, @PathVariable("idUser") Long idUser) {
        Optional<User> foundUser = UserService.findOne(idUser);
        Optional<Singer> foundSinger = SingerService.findOne(idSinger);
        if(foundUser.isEmpty() || foundSinger.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Singer singer = foundSinger.get();
        User user = foundUser.get();
        user.user_singer.add(singer);
        singer.setFollowers(singer.getFollowers() + 1);
        Singer savedSinger = SingerService.save(singer);
        User savedUser = UserService.save(user);
        return new ResponseEntity<>(savedSinger, HttpStatus.OK);
    }
    @GetMapping(path = "/Singer")
    public List<Singer> getMapping() {
        List<Singer> Singers = SingerService.findAll();
        return Singers.stream()
                .map(entity -> entity)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/Singer/{id}")
    public ResponseEntity<Singer> getMappingById(@PathVariable("id") Long id) {
        Optional<Singer> foundSinger = SingerService.findOne(id);
        return foundSinger.map(entity -> {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/Singer/TopFollowers")
    public List<Singer> getMappingTopFollowers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Singer> albumPage = SingerService.findAllByOrderByFollowersDesc(page, size);
        return albumPage.getContent();
    }
}
