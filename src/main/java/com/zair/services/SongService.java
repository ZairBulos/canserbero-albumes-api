package com.zair.services;

import com.zair.models.dtos.SongDTO;

import java.util.List;

public interface SongService {
    List<SongDTO> findAll();

    List<SongDTO> findAllByName(String name);

    List<SongDTO> findAllByAlbumId(Long albumId);
}
