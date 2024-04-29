package com.zair.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
public class AlbumDTO {
    private final Long id;
    private final String nombre;
    private final String portada;
    @JsonFormat(pattern = "dd 'de' MMMM 'de' yyyy")
    private final LocalDate fechaPublicacion;
    private final List<LinkDTO> enlaces;
}
