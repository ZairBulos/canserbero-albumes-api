package com.zair.mappers;

import com.zair.models.dtos.LinkDTO;
import com.zair.models.entities.AlbumLink;
import com.zair.models.entities.SongLink;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LinkMapper {

    public LinkDTO toDTO(AlbumLink source) {
        if (source == null) return null;

        return LinkDTO.builder()
                .url(source.getUrl())
                .plataforma(source.getPlatform().getName())
                .build();
    }

    public LinkDTO toDTO(SongLink source) {
        if (source == null) return null;

        return LinkDTO.builder()
                .url(source.getUrl())
                .plataforma(source.getPlatform().getName())
                .build();
    }

    public List<LinkDTO> toDTOsListFromAlbumLink(List<AlbumLink> source) {
        if (source == null || source.isEmpty()) return null;

        return source.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<LinkDTO> toDTOsListFromSongLink(List<SongLink> source) {
        if (source == null || source.isEmpty()) return null;

        return source.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
