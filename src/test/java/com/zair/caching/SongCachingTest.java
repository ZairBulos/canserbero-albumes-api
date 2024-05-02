package com.zair.caching;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zair.models.dtos.SongDTO;
import com.zair.models.entities.Album;
import com.zair.models.entities.Song;
import com.zair.repositories.AlbumRepository;
import com.zair.repositories.SongRepository;
import com.zair.services.SongService;
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
public class SongCachingTest {

    @Autowired
    private SongService songService;

    @MockBean
    private SongRepository songRepository;

    @MockBean
    private AlbumRepository albumRepository;

    @BeforeEach
    public void setUp() {
        Song song = Song.builder().id(1L).name("Song").album(Album.builder().id(1L).build()).build();
        List<Song> songs = Collections.singletonList(song);

        Mockito.when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        Mockito.when(songRepository.findAll()).thenReturn(songs);
        Mockito.when(songRepository.findAllByNameContainingIgnoreCase("song")).thenReturn(songs);
        Mockito.when(songRepository.findAllByAlbum_Id(1L)).thenReturn(songs);
        Mockito.when(albumRepository.existsById(1L)).thenReturn(true);
    }

    @Test
    public void whenFindAll_thenReturnSongs() {
        List<SongDTO> firstCall = songService.findAll();
        List<SongDTO> secondCall = songService.findAll();

        Mockito.verify(songRepository, Mockito.times(1)).findAll();
        assertEquals(firstCall, secondCall);
    }

    @Test
    public void whenFindAllByName_thenReturnSongs() {
        List<SongDTO> firstCall = songService.findAllByName("song");
        List<SongDTO> secondCall = songService.findAllByName("song");

        Mockito.verify(songRepository, Mockito.times(1)).findAllByNameContainingIgnoreCase("song");
        assertEquals(firstCall, secondCall);
    }

    @Test
    public void whenFindAllByAlbumId_thenReturnSongs() {
        List<SongDTO> firstCall = songService.findAllByAlbumId(1L);
        List<SongDTO> secondCall = songService.findAllByAlbumId(1L);

        Mockito.verify(songRepository, Mockito.times(1)).findAllByAlbum_Id(1L);
        assertEquals(firstCall, secondCall);
    }
}
