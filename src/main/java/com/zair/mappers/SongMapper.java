package com.zair.mappers;

import com.zair.models.dtos.SongDTO;
import com.zair.models.entities.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SongMapper {

    private final LinkMapper linkMapper;

    public SongDTO toDTO(Song source) {
        if (source == null) return null;

        return SongDTO.builder()
                .id(source.getId())
                .albumId(source.getAlbum().getId())
                .nombre(source.getName())
                .duracion(source.getDuration())
                .enlaces(linkMapper.toDTOsListFromSongLink(source.getLinks()))
                .build();
    }

    public List<SongDTO> toDTOsList(List<Song> source) {
        if (source == null || source.isEmpty()) return null;

        return source.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
