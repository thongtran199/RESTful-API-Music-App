package com.example.demo.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "song")
public class Song {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "image")
    public String image;

    @Column(name = "urlLyric")
    public String urlLyric;

    @Column(name = "urlSong")
    public String urlSong;

    @Column(name = "popularity")
    public Integer popularity;

    @Column(name = "releaseDate")
    public LocalDate releaseDate;

    @Column(name=" isActive")
    public Boolean isActive;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "user_song")
    public List<User> users;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "album_song")
    public List<Album> albums;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "singer_song")
    public List<Singer> singers;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "playlist_song")
    public List<Playlist> playlists;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idGenre")
    public Genre song_genre ;
}