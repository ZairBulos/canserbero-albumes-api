package com.zair.controllers;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.zair.exceptions.InvalidAlbumIdException;
import com.zair.models.dtos.SongDTO;
import com.zair.services.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(SongController.class)
public class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    private List<SongDTO> songs;

    @BeforeEach
    public void setUp() {
        songs = Arrays.asList(SongDTO.builder().build(), SongDTO.builder().build());
    }

    @Test
    public void getAll_ReturnsListOfSongs() throws Exception {
        when(songService.findAll()).thenReturn(songs);

        mockMvc.perform(get("/api/canciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllByAlbum_ReturnsSongsForAlbum() throws Exception {
        Long albumId = 1L;

        when(songService.findAllByAlbumId(albumId)).thenReturn(songs);

        mockMvc.perform(get("/api/canciones/{album}", albumId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllByNonExistingAlbum_ReturnsBadRequest() throws Exception {
        Long albumId = 999L;

        when(songService.findAllByAlbumId(albumId)).thenThrow(InvalidAlbumIdException.class);

        mockMvc.perform(get("/api/canciones/{album}", albumId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllByName_ReturnsSongsWithName() throws Exception {
        String songName = "song name";

        when(songService.findAllByName(songName)).thenReturn(songs);

        mockMvc.perform(get("/api/canciones/buscarPorNombre").param("nombre", songName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
