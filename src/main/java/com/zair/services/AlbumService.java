package com.zair.services;

import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;

import java.util.List;

public interface AlbumService {
    List<AlbumDTO> findAll();

    List<AlbumFullDTO> findAllFull();

    AlbumDTO findById(Long id);

    AlbumFullDTO findFullById(Long id);
}
