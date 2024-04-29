package com.zair.models.dtos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
public class SongDTO {
    private final Long id;
    private final Long albumId;
    private final String nombre;
    private final Long duracion;
    private final List<LinkDTO> enlaces;
}
