package com.zair.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.models.dtos.LinkDTO;
import com.zair.models.entities.Album;
import com.zair.models.entities.AlbumLink;
import com.zair.models.entities.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class AlbumMapperTest {

    @Mock
    private LinkMapper linkMapperMock;

    @Mock
    private SongMapper songMapperMock;

    @InjectMocks
    private AlbumMapper albumMapper;

    @Test
    public void testToDTO() {
        List<AlbumLink> links = Arrays.asList(AlbumLink.builder().build());

        when(linkMapperMock.toDTOsListFromAlbumLink(links)).thenReturn(Arrays.asList(LinkDTO.builder().build()));

        Album album = Album.builder()
                .id(1L)
                .name("Album Name")
                .cover("http://cover.jpg")
                .releaseDate(2024)
                .links(links)
                .build();

        AlbumDTO albumDTO = albumMapper.toDTO(album);

        assertEquals(1L, albumDTO.getId());
        assertEquals("Album Name", albumDTO.getNombre());
        assertEquals("http://cover.jpg", albumDTO.getPortada());
        assertEquals(2024, albumDTO.getFechaPublicacion());
        assertEquals(1, albumDTO.getEnlaces().size());
    }

    @Test
    public void testToDTOsList() {
        Album album1 = Album.builder().id(1L).build();
        Album album2 = Album.builder().id(2L).build();

        List<AlbumDTO> albumDTOsList = albumMapper.toDTOsList(Arrays.asList(album1, album2));

        assertEquals(2, albumDTOsList.size());
        assertEquals(1L, albumDTOsList.get(0).getId());
        assertEquals(2L, albumDTOsList.get(1).getId());
    }

    @Test
    public void testToFullDTO() {
        Album album = Album.builder().id(1L).build();
        List<Song> songs = Arrays.asList(Song.builder().id(1L).build());

        when(linkMapperMock.toDTOsListFromAlbumLink(any())).thenReturn(Collections.emptyList());
        when(songMapperMock.toDTOsList(any())).thenReturn(Collections.emptyList());

        AlbumFullDTO albumFullDTO = albumMapper.toFullDTO(album, songs);

        assertEquals(1L, albumFullDTO.getId());
        assertEquals(Collections.emptyList(), albumFullDTO.getEnlaces());
        assertEquals(Collections.emptyList(), albumFullDTO.getCanciones());
    }

    @Test
    public void testToFullDTOsList() {
        Album album1 = Album.builder().id(1L).build();
        Album album2 = Album.builder().id(2L).build();
        Map<Long, List<Song>> songs = Collections.singletonMap(1L, Arrays.asList(Song.builder().id(1L).build()));

        when(linkMapperMock.toDTOsListFromAlbumLink(any())).thenReturn(Collections.emptyList());
        when(songMapperMock.toDTOsList(any())).thenReturn(Collections.emptyList());

        List<AlbumFullDTO> albumFullDTOsList = albumMapper.toFullDTOsList(Arrays.asList(album1, album2), songs);

        assertEquals(2, albumFullDTOsList.size());
        assertEquals(1L, albumFullDTOsList.get(0).getId());
        assertEquals(2L, albumFullDTOsList.get(1).getId());
        assertEquals(Collections.emptyList(), albumFullDTOsList.get(0).getEnlaces());
        assertEquals(Collections.emptyList(), albumFullDTOsList.get(0).getCanciones());
    }
}
