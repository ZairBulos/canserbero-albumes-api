package com.zair.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.zair.models.dtos.LinkDTO;
import com.zair.models.dtos.SongDTO;
import com.zair.models.entities.Album;
import com.zair.models.entities.Song;
import com.zair.models.entities.SongLink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SongMapperTest {

    @Mock
    private LinkMapper linkMapperMock;

    @InjectMocks
    private SongMapper songMapper;

    @Test
    public void testToDTO() {
        List<SongLink> links = Arrays.asList(SongLink.builder().build());

        when(linkMapperMock.toDTOsListFromSongLink(links)).thenReturn(Arrays.asList(LinkDTO.builder().build()));

        Song song = Song.builder()
                .id(1L)
                .name("Song Name")
                .duration(240000L)
                .album(Album.builder().id(10L).build())
                .links(links)
                .build();

        SongDTO songDTO = songMapper.toDTO(song);

        assertEquals(1L, songDTO.getId());
        assertEquals(10L, songDTO.getAlbumId());
        assertEquals("Song Name", songDTO.getNombre());
        assertEquals(240000L, songDTO.getDuracion());
        assertEquals(1, songDTO.getEnlaces().size());
    }

    @Test
    public void testToDTOsList() {
        Song song1 = Song.builder().id(1L).album(Album.builder().id(10L).build()).build();
        Song song2 = Song.builder().id(2L).album(Album.builder().id(20L).build()).build();

        List<SongDTO> songDTOs = songMapper.toDTOsList(Arrays.asList(song1, song2));

        assertEquals(2, songDTOs.size());
        assertEquals(1L, songDTOs.get(0).getId());
        assertEquals(2L, songDTOs.get(1).getId());
    }
}
