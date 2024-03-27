package com.example.demo.repositories;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Singer;
import com.example.demo.domain.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SingerRepository extends CrudRepository<Singer, Long> {
    Page<Singer> findAllByOrderByFollowersDesc(Pageable pageable);
}
