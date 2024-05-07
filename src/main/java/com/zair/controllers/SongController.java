package com.zair.controllers;

import com.zair.models.dtos.SongDTO;
import com.zair.services.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/canciones")
@RequiredArgsConstructor
@Tag(name = "Canciones", description = "Endpoints para obtener información sobre las canciones")
public class SongController {

    private final SongService service;

    @GetMapping("")
    @Operation(
            summary = "Obtener todas las canciones",
            description = "Devuelve todas las canciones disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "-"),
            @ApiResponse(responseCode = "404", description = "-"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<SongDTO>> getAll() {
        List<SongDTO> canciones = service.findAll();
        return new ResponseEntity<>(canciones, HttpStatus.OK);
    }

    @GetMapping("/{album}")
    @Operation(
            summary = "Obtener canciones por álbum",
            description = "Devuelve las canciones de un álbum específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "ID del álbum inválido"),
            @ApiResponse(responseCode = "404", description = "-"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<SongDTO>> getAllByAlbum(
            @Parameter(description = "ID del álbum")
            @PathVariable(name = "album") Long albumId
    ) {
        List<SongDTO> canciones = service.findAllByAlbumId(albumId);
        return new ResponseEntity<>(canciones, HttpStatus.OK);
    }

    @GetMapping("/buscarPorNombre")
    @Operation(
            summary = "Buscar canciones por nombre",
            description = "Busca canciones por nombre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "-"),
            @ApiResponse(responseCode = "404", description = "-"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<SongDTO>> getAllByName(
            @Parameter(description = "Nombre de la canción")
            @RequestParam(name = "nombre") String name
    ) {
        List<SongDTO> canciones = service.findAllByName(name);
        return new ResponseEntity<>(canciones, HttpStatus.OK);
    }
}
