package com.zair.repositories;

import com.zair.models.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByAlbum_Id(Long albumId);

    List<Song> findAllByNameContainingIgnoreCase(String name);
}
