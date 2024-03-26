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
@Table(name = "singer")
public class Singer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "image")
    public String image;

    @Column(name = "description")
    public String description;

    @Column(name = "followers")
    public Integer followers;

    @Column(name = "birthday")
    public LocalDate birthday;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "user_singer")
    public List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "singer_song",
            joinColumns = @JoinColumn(name = "idSinger"),
            inverseJoinColumns = @JoinColumn(name = "idSong")
    )
    public List<Song> singer_song;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "singer_album",
            joinColumns = @JoinColumn(name = "idSinger"),
            inverseJoinColumns = @JoinColumn(name = "idAlbum")
    )
    public List<Album> singer_album;
}
