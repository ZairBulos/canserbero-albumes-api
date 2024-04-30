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
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// TODO: Implement logging SLF4J
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final AlbumMapper albumMapper;

    @Override
    public List<AlbumDTO> findAll() {
        try {
            List<Album> albums = albumRepository.findAll();
            return albumMapper.toDTOsList(albums);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServiceException("Failed to fetch all albums", e);
        }
    }

    @Override
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
            System.out.println(e.getMessage());
            throw new ServiceException("Failed to fetch all albums with songs", e);
        }
    }

    @Override
    public AlbumDTO findById(Long id) {
        try {
            Optional<Album> optional = albumRepository.findById(id);

            if (optional.isEmpty()) {
                throw new AlbumNotFoundException("Album not found with id: " + id);
            }

            Album album = optional.get();
            return albumMapper.toDTO(album);
        } catch (AlbumNotFoundException e) {
            System.out.println("Album not found with id: " + id);
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServiceException("Failed to fetch album by id: " + id, e);
        }
    }

    @Override
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
            System.out.println("Album not found with id: " + id);
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServiceException("Failed to fetch album with songs by id: " + id, e);
        }
    }
}
