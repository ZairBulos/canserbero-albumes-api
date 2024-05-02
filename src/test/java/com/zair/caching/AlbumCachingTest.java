package com.zair.caching;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.models.entities.Album;
import com.zair.repositories.AlbumRepository;
import com.zair.repositories.SongRepository;
import com.zair.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AlbumCachingTest {

    @Autowired
    private AlbumService albumService;

    @MockBean
    private AlbumRepository albumRepository;

    @MockBean
    private SongRepository songRepository;

    @BeforeEach
    public void setUp() {
        Album album = Album.builder().id(1L).name("Album").build();
        List<Album> albums = Collections.singletonList(album);

        Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        Mockito.when(albumRepository.findAll()).thenReturn(albums);
    }

    @Test
    public void whenFindAll_thenReturnAlbums() {
        List<AlbumDTO> firstCall = albumService.findAll();
        List<AlbumDTO> secondCall = albumService.findAll();

        Mockito.verify(albumRepository, Mockito.times(1)).findAll();
        assertEquals(firstCall, secondCall);
    }

    @Test
    public void whenFindAllFull_thenReturnAlbumsFull() {
        List<AlbumFullDTO> firstCall = albumService.findAllFull();
        List<AlbumFullDTO> secondCall = albumService.findAllFull();

        Mockito.verify(albumRepository, Mockito.times(1)).findAll();
        assertEquals(firstCall.size(), secondCall.size());
    }

    @Test
    public void whenFindById_thenReturnAlbum() {
        AlbumDTO firstCall = albumService.findById(1L);
        AlbumDTO secondCall = albumService.findById(1L);

        Mockito.verify(albumRepository, Mockito.times(1)).findById(1L);
        assertEquals(firstCall, secondCall);
    }
}
