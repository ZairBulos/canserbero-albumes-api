package com.zair.controllers;

import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/albumes")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService service;

    @GetMapping("")
    public ResponseEntity<List<AlbumDTO>> getAll() {
        List<AlbumDTO> albumes = service.findAll();
        return new ResponseEntity<>(albumes, HttpStatus.OK);
    }

    @GetMapping("/canciones")
    public ResponseEntity<List<AlbumFullDTO>> getAllFull() {
        List<AlbumFullDTO> albumes = service.findAllFull();
        return new ResponseEntity<>(albumes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getById(@PathVariable Long id) {
        AlbumDTO album = service.findById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/canciones/{id}")
    public ResponseEntity<AlbumFullDTO> getFullById(@PathVariable Long id) {
        AlbumFullDTO album = service.findFullById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }
}
