package com.example.demo.repositories;

import com.example.demo.domain.entities.Playlist;
import com.example.demo.domain.entities.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
}
