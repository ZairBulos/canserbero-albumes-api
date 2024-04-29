package com.zair.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.zair.models.dtos.LinkDTO;
import com.zair.models.entities.AlbumLink;
import com.zair.models.entities.SongLink;
import com.zair.models.enums.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LinkMapperTest {

    @Mock
    private Platform platform;

    @InjectMocks
    private LinkMapper linkMapper;

    @BeforeEach
    public void setup() {
        when(platform.getName()).thenReturn("MockPlatform");
    }

    @Test
    public void testToDTO_AlbumLink() {
        AlbumLink albumLink = AlbumLink.builder()
                .url("https://example.com/album")
                .platform(platform)
                .build();

        LinkDTO linkDTO = linkMapper.toDTO(albumLink);

        assertEquals("https://example.com/album", linkDTO.getUrl());
        assertEquals("MockPlatform", linkDTO.getPlataforma());
    }

    @Test
    public void testToDTO_SongLink() {
        SongLink songLink = SongLink.builder()
                .url("https://example.com/song")
                .platform(platform)
                .build();

        LinkDTO linkDTO = linkMapper.toDTO(songLink);

        assertEquals("https://example.com/song", linkDTO.getUrl());
        assertEquals("MockPlatform", linkDTO.getPlataforma());
    }

    @Test
    public void testToDTOsList_AlbumLink() {
        AlbumLink albumLink = AlbumLink.builder()
                .url("https://example.com/album")
                .platform(platform)
                .build();

        List<LinkDTO> linkDTOsList = linkMapper.toDTOsListFromAlbumLink(Arrays.asList(albumLink));

        assertEquals(1, linkDTOsList.size());
        assertEquals("https://example.com/album", linkDTOsList.get(0).getUrl());
        assertEquals("MockPlatform", linkDTOsList.get(0).getPlataforma());
    }

    @Test
    public void testToDTOsList_SongLink() {
        SongLink songLink = SongLink.builder()
                .url("https://example.com/song")
                .platform(platform)
                .build();

        List<LinkDTO> linkDTOsList = linkMapper.toDTOsListFromSongLink(Arrays.asList(songLink));

        assertEquals(1, linkDTOsList.size());
        assertEquals("https://example.com/song", linkDTOsList.get(0).getUrl());
        assertEquals("MockPlatform", linkDTOsList.get(0).getPlataforma());
    }
}
