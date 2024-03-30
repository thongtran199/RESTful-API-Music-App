package com.example.demo.repositories;

import com.example.demo.domain.entities.Album;
import com.example.demo.domain.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {
    Page<Album> findAllByOrderByPopularityDesc(Pageable pageable);

}
