package com.zair.models.dtos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
public class AlbumFullDTO extends AlbumDTO {
    private List<SongDTO> canciones;
}
