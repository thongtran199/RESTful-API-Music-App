package com.example.demo.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
@Table(name = "album")
public class Album {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "image")
    public String image;

    @Column(name = "popularity")
    public Integer popularity;

    @Column(name = "isActive")
    public Boolean isActive;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "user_album")
    public List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "album_song",
            joinColumns = @JoinColumn(name = "idAlbum"),
            inverseJoinColumns = @JoinColumn(name = "idSong")
    )
    public List<Song> album_song;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "singer_album")
    public List<Singer> singers;

}