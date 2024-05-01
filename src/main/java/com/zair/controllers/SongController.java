package com.zair.controllers;

import com.zair.models.dtos.SongDTO;
import com.zair.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/canciones")
@RequiredArgsConstructor
public class SongController {

    private final SongService service;

    @GetMapping("")
    public ResponseEntity<List<SongDTO>> getAll() {
        List<SongDTO> canciones = service.findAll();
        return new ResponseEntity<>(canciones, HttpStatus.OK);
    }

    @GetMapping("/{album}")
    public ResponseEntity<List<SongDTO>> getAllByAlbum(@PathVariable(name = "album") Long albumId) {
        List<SongDTO> canciones = service.findAllByAlbumId(albumId);
        return new ResponseEntity<>(canciones, HttpStatus.OK);
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<SongDTO>> getAllByName(@RequestParam(name = "nombre") String name) {
        List<SongDTO> canciones = service.findAllByName(name);
        return new ResponseEntity<>(canciones, HttpStatus.OK);
    }
}
