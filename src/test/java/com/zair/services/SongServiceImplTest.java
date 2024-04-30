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
    private SongRepository songRepositoryMock;

    @Mock
    private AlbumRepository albumRepositoryMock;

    @Mock
    private SongMapper songMapperMock;

    @InjectMocks
    private SongServiceImpl songService;

    @Test
    public void testFindAll() {
        // Arrange
        when(songRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(songMapperMock.toDTOsList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        songService.findAll();

        // Assert
        verify(songRepositoryMock, times(1)).findAll();
        verify(songMapperMock, times(1)).toDTOsList(Collections.emptyList());
    }

    @Test
    void testFindAllByName() {
        // Arrange
        String name = "Test Song";
        when(songRepositoryMock.findAllByNameContainingIgnoreCase(name)).thenReturn(Collections.emptyList());
        when(songMapperMock.toDTOsList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        songService.findAllByName(name);

        // Assert
        verify(songRepositoryMock, times(1)).findAllByNameContainingIgnoreCase(name);
        verify(songMapperMock, times(1)).toDTOsList(Collections.emptyList());
    }

    @Test
    void testFindAllByAlbumId() {
        // Arrange
        Long albumId = 1L;
        when(albumRepositoryMock.existsById(albumId)).thenReturn(true);
        when(songRepositoryMock.findAllByAlbum_Id(albumId)).thenReturn(Collections.emptyList());
        when(songMapperMock.toDTOsList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        songService.findAllByAlbumId(albumId);

        // Assert
        verify(albumRepositoryMock, times(1)).existsById(albumId);
        verify(songRepositoryMock, times(1)).findAllByAlbum_Id(albumId);
        verify(songMapperMock, times(1)).toDTOsList(Collections.emptyList());
    }

    @Test
    void testFindAllByAlbumId_InvalidAlbumId() {
        // Arrange
        Long invalidAlbumId = 999L;
        when(albumRepositoryMock.existsById(invalidAlbumId)).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidAlbumIdException.class, () -> songService.findAllByAlbumId(invalidAlbumId));

        // Verify
        verify(albumRepositoryMock, times(1)).existsById(invalidAlbumId);
        verify(songRepositoryMock, never()).findAllByAlbum_Id(anyLong());
        verify(songMapperMock, never()).toDTOsList(any());
    }
}
