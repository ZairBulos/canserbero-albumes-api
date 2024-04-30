package com.zair.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.zair.exceptions.InvalidAlbumIdException;
import com.zair.mappers.SongMapper;
import com.zair.repositories.AlbumRepository;
import com.zair.repositories.SongRepository;
import com.zair.services.impl.SongServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private SongMapper songMapper;

    @InjectMocks
    private SongServiceImpl songService;

    @Test
    public void testFindAll() {
        // Arrange
        when(songRepository.findAll()).thenReturn(Collections.emptyList());
        when(songMapper.toDTOsList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        songService.findAll();

        // Assert
        verify(songRepository, times(1)).findAll();
        verify(songMapper, times(1)).toDTOsList(Collections.emptyList());
    }

    @Test
    void testFindAllByName() {
        // Arrange
        String name = "Test Song";
        when(songRepository.findAllByNameContainingIgnoreCase(name)).thenReturn(Collections.emptyList());
        when(songMapper.toDTOsList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        songService.findAllByName(name);

        // Assert
        verify(songRepository, times(1)).findAllByNameContainingIgnoreCase(name);
        verify(songMapper, times(1)).toDTOsList(Collections.emptyList());
    }

    @Test
    void testFindAllByAlbumId() {
        // Arrange
        Long albumId = 1L;
        when(albumRepository.existsById(albumId)).thenReturn(true);
        when(songRepository.findAllByAlbum_Id(albumId)).thenReturn(Collections.emptyList());
        when(songMapper.toDTOsList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        songService.findAllByAlbumId(albumId);

        // Assert
        verify(albumRepository, times(1)).existsById(albumId);
        verify(songRepository, times(1)).findAllByAlbum_Id(albumId);
        verify(songMapper, times(1)).toDTOsList(Collections.emptyList());
    }

    @Test
    void testFindAllByAlbumId_InvalidAlbumId() {
        // Arrange
        Long invalidAlbumId = 999L;
        when(albumRepository.existsById(invalidAlbumId)).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidAlbumIdException.class, () -> songService.findAllByAlbumId(invalidAlbumId));

        // Verify
        verify(albumRepository, times(1)).existsById(invalidAlbumId);
        verify(songRepository, never()).findAllByAlbum_Id(anyLong());
        verify(songMapper, never()).toDTOsList(any());
    }
}
