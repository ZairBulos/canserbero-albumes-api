package com.zair.controllers;

import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.services.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Álbumes", description = "Endpoints para obtener información sobre los álbumes")
public class AlbumController {

    private final AlbumService service;

    @GetMapping("")
    @Operation(
            summary = "Obtener todos los álbumes",
            description = "Devuelve todos los álbumes disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "-"),
            @ApiResponse(responseCode = "404", description = "-"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<AlbumDTO>> getAll() {
        List<AlbumDTO> albumes = service.findAll();
        return new ResponseEntity<>(albumes, HttpStatus.OK);
    }

    @GetMapping("/canciones")
    @Operation(
            summary = "Obtener todos los álbumes con canciones",
            description = "Devuelve todos los álbumes con sus respectivas canciones"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "-"),
            @ApiResponse(responseCode = "404", description = "-"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<AlbumFullDTO>> getAllFull() {
        List<AlbumFullDTO> albumes = service.findAllFull();
        return new ResponseEntity<>(albumes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener álbum por id",
            description = "Devuelve un álbum específico por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "-"),
            @ApiResponse(responseCode = "404", description = "Álbum no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AlbumDTO> getById(
            @Parameter(description = "ID del álbum")
            @PathVariable Long id
    ) {
        AlbumDTO album = service.findById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/canciones/{id}")
    @Operation(
            summary = "Obtener álbum con canciones por id",
            description = "Devuelve un álbum específico con todas sus canciones por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "-"),
            @ApiResponse(responseCode = "404", description = "Álbum no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<AlbumFullDTO> getFullById(
            @Parameter(description = "ID del álbum")
            @PathVariable Long id
    ) {
        AlbumFullDTO album = service.findFullById(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }
}
