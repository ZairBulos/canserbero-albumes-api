package com.zair.services.impl;

import com.zair.exceptions.AlbumNotFoundException;
import com.zair.mappers.AlbumMapper;
import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.models.entities.Album;
import com.zair.models.entities.Song;
import com.zair.repositories.AlbumRepository;
import com.zair.repositories.SongRepository;
import com.zair.services.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final AlbumMapper albumMapper;

    @Override
    @Cacheable("albums")
    public List<AlbumDTO> findAll() {
        try {
            List<Album> albums = albumRepository.findAll();
            return albumMapper.toDTOsList(albums);
        } catch (Exception e) {
            log.error("Failed to fetch all albums - {}", e.getMessage());
            throw new ServiceException("Failed to fetch all albums", e);
        }
    }

    @Override
    @Cacheable("albumsFull")
    public List<AlbumFullDTO> findAllFull() {
        try {
            List<Album> albums = albumRepository.findAll();
            Map<Long, List<Song>> albumSongs = new HashMap<>();

            albums.forEach(album -> {
                Long albumId = album.getId();
                List<Song> songs = songRepository.findAllByAlbum_Id(albumId);

                albumSongs.put(albumId, songs);
            });

            return albumMapper.toFullDTOsList(albums, albumSongs);
        } catch (Exception e) {
            log.error("Failed to fetch all albums with songs - {}", e.getMessage());
            throw new ServiceException("Failed to fetch all albums with songs", e);
        }
    }

    @Override
    @Cacheable(value = "album", key = "#id", unless = "#result == null")
    public AlbumDTO findById(Long id) {
        try {
            Optional<Album> optional = albumRepository.findById(id);

            if (optional.isEmpty()) {
                throw new AlbumNotFoundException("Album not found with id: " + id);
            }

            Album album = optional.get();
            return albumMapper.toDTO(album);
        } catch (AlbumNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch album by id {} - {}", id, e.getMessage());
            throw new ServiceException("Failed to fetch album by id: " + id, e);
        }
    }

    @Override
    @Cacheable(value = "albumFull", key = "#id", unless = "#result == null")
    public AlbumFullDTO findFullById(Long id) {
        try {
            Optional<Album> optional = albumRepository.findById(id);

            if (optional.isEmpty()) {
                throw new AlbumNotFoundException("Album not found with id: " + id);
            }

            Album album = optional.get();
            List<Song> songs = songRepository.findAllByAlbum_Id(id);
            return albumMapper.toFullDTO(album, songs);
        } catch (AlbumNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch album with songs by id {} - {}", id, e.getMessage());
            throw new ServiceException("Failed to fetch album with songs by id: " + id, e);
        }
    }
}
