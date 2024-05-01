package com.zair.models.dtos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
public class AlbumDTO {
    private final Long id;
    private final String nombre;
    private final String portada;
    private final Integer fechaPublicacion;
    private final List<LinkDTO> enlaces;
}
