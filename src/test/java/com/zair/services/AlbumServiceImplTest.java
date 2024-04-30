package com.zair.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.zair.exceptions.AlbumNotFoundException;
import com.zair.mappers.AlbumMapper;
import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.models.entities.Album;
import com.zair.models.entities.Song;
import com.zair.repositories.AlbumRepository;
import com.zair.repositories.SongRepository;
import com.zair.services.impl.AlbumServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceImplTest {

    @Mock
    private AlbumRepository albumRepositoryMock;

    @Mock
    private SongRepository songRepositoryMock;

    @Mock
    private AlbumMapper albumMapperMock;

    @InjectMocks
    private AlbumServiceImpl albumService;

    @Test
    public void testFindAll() {
        // Arrange
        when(albumRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(albumMapperMock.toDTOsList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        albumService.findAll();

        // Assert
        verify(albumRepositoryMock, times(1)).findAll();
        verify(albumMapperMock, times(1)).toDTOsList(Collections.emptyList());
    }

    @Test
    public void testFindAllFull() {
        // Arrange
        List<Album> albums = Collections.emptyList();
        List<Song> songs = Collections.emptyList();

        Mockito.lenient().when(albumRepositoryMock.findAll()).thenReturn(albums);
        Mockito.lenient().when(songRepositoryMock.findAllByAlbum_Id(anyLong())).thenReturn(songs);

        // Act
        albumService.findAllFull();

        // Assert
        verify(albumRepositoryMock, times(1)).findAll();
        verify(songRepositoryMock, times(albums.size())).findAllByAlbum_Id(anyLong());
        verify(albumMapperMock, times(1)).toFullDTOsList(albums, Collections.emptyMap());
    }

    @Test
    public void testFindById() {
        // Arrange
        Long albumId = 1L;
        Album album = Album.builder().build();

        when(albumRepositoryMock.findById(albumId)).thenReturn(Optional.of(album));
        when(albumMapperMock.toDTO(album)).thenReturn(AlbumDTO.builder().build());

        // Act
        AlbumDTO result = albumService.findById(albumId);

        // Assert
        verify(albumRepositoryMock, times(1)).findById(albumId);
        verify(albumMapperMock, times(1)).toDTO(album);
        assertNotNull(result);
    }

    @Test
    public void testFindById_NonExistentId() {
        // Arrange
        Long albumId = 999L;
        when(albumRepositoryMock.findById(albumId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AlbumNotFoundException.class, () -> albumService.findById(albumId));

        // Assert
        verify(albumRepositoryMock, times(1)).findById(albumId);
        verify(albumMapperMock, never()).toDTO(any());
    }

    @Test
    public void testFindFullById() {
        // Arrange
        Long albumId = 1L;
        Album album = Album.builder().build();
        List<Song> songs = Collections.singletonList(Song.builder().build());

        when(albumRepositoryMock.findById(albumId)).thenReturn(Optional.of(album));
        when(songRepositoryMock.findAllByAlbum_Id(albumId)).thenReturn(songs);
        when(albumMapperMock.toFullDTO(album, songs)).thenReturn(AlbumFullDTO.builder().build());

        // Act
        AlbumFullDTO result = albumService.findFullById(albumId);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testFindFullById_NonExistentId() {
        // Arrange
        Long albumId = 1L;
        when(albumRepositoryMock.findById(albumId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AlbumNotFoundException.class, () -> albumService.findFullById(albumId));
    }
}
