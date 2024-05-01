package com.zair.services.impl;

import com.zair.exceptions.InvalidAlbumIdException;
import com.zair.mappers.SongMapper;
import com.zair.models.dtos.SongDTO;
import com.zair.models.entities.Song;
import com.zair.repositories.AlbumRepository;
import com.zair.repositories.SongRepository;
import com.zair.services.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final SongMapper songMapper;

    @Override
    public List<SongDTO> findAll() {
        try {
            List<Song> songs = songRepository.findAll();
            return songMapper.toDTOsList(songs);
        } catch (Exception e) {
            log.error("Failed to fetch all songs - {}", e.getMessage());
            throw new ServiceException("Failed to fetch all songs", e);
        }
    }

    @Override
    public List<SongDTO> findAllByName(String name) {
        try {
            List<Song> songs = songRepository.findAllByNameContainingIgnoreCase(name);
            return songMapper.toDTOsList(songs);
        } catch (Exception e) {
            log.error("Failed to fetch songs by name {} - {}", name, e.getMessage());
            throw new ServiceException("Failed to fetch songs by name: " + name, e);
        }
    }

    @Override
    public List<SongDTO> findAllByAlbumId(Long albumId) {
        try {
            if (!albumRepository.existsById(albumId)) {
                throw new InvalidAlbumIdException("Invalid album ID: " + albumId);
            }

            List<Song> songs = songRepository.findAllByAlbum_Id(albumId);
            return songMapper.toDTOsList(songs);
        } catch (InvalidAlbumIdException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch songs by album id {} - {}", albumId, e.getMessage());
            throw new ServiceException("Failed to fetch songs by album id: " + albumId, e);
        }
    }
}
