package com.example.demo.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

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
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "email")
    public String email;

    @Column(name = "vip")
    public Boolean vip;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "isActive")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_singer",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idSinger")
    )
    @JsonIgnore
    public List<Singer> user_singer;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_song",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idSong")
    )
    @JsonIgnore
    public List<Song> user_song;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_album",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idAlbum")
    )
    @JsonIgnore
    public List<Album> user_album;

    @JsonIgnore
    @OneToMany(mappedBy = "playlist_user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Playlist> user_playlist;



}

