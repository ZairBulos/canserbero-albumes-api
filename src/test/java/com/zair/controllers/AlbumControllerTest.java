package com.zair.controllers;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.zair.exceptions.AlbumNotFoundException;
import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;

    private List<AlbumDTO> albums;

    private List<AlbumFullDTO> albumSongs;

    @BeforeEach
    public void setUp() {
        albums = Arrays.asList(AlbumDTO.builder().build(), AlbumDTO.builder().build());
        albumSongs = Arrays.asList(AlbumFullDTO.builder().build(), AlbumFullDTO.builder().build());
    }

    @Test
    public void getAll_ReturnsListOfAlbums() throws Exception {
        when(albumService.findAll()).thenReturn(albums);

        mockMvc.perform(get("/api/albumes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllFull_ReturnsListOfAlbumsWithSongs() throws Exception {
        when(albumService.findAllFull()).thenReturn(albumSongs);

        mockMvc.perform(get("/api/albumes/canciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getById_ReturnsAlbum() throws Exception {
        Long albumId = 1L;

        when(albumService.findById(albumId)).thenReturn(AlbumDTO.builder().build());

        mockMvc.perform(get("/api/albumes/{id}", albumId))
                .andExpect(status().isOk());
    }

    @Test
    public void getByNonExistingId_ReturnsNotFound() throws Exception {
        Long albumId = 999L;

        when(albumService.findById(albumId)).thenThrow(AlbumNotFoundException.class);

        mockMvc.perform(get("/api/albumes/{id}", albumId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFullById_ReturnsAlbum() throws Exception {
        Long albumId = 1L;

        when(albumService.findFullById(albumId)).thenReturn(AlbumFullDTO.builder().build());

        mockMvc.perform(get("/api/albumes/canciones/{id}", albumId))
                .andExpect(status().isOk());
    }

    @Test
    public void getFullByNonExistingId_ReturnsNotFound() throws Exception {
        Long albumId = 999L;

        when(albumService.findFullById(albumId)).thenThrow(AlbumNotFoundException.class);

        mockMvc.perform(get("/api/albumes/canciones/{id}", albumId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getById_ReturnsAlbumWithFormattedDate() throws Exception {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy"));

        Long albumId = 1L;
        AlbumDTO albumDTO = AlbumDTO.builder()
                .id(albumId)
                .nombre("AlbumName")
                .portada("CoverImage")
                .fechaPublicacion(currentDate)
                .enlaces(Collections.emptyList())
                .build();

        when(albumService.findById(albumId)).thenReturn(albumDTO);

        mockMvc.perform(get("/api/albumes/{id}", albumId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaPublicacion", is(formattedDate)));
    }
}
