package com.zair.mappers;

import com.zair.models.dtos.AlbumDTO;
import com.zair.models.dtos.AlbumFullDTO;
import com.zair.models.entities.Album;
import com.zair.models.entities.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlbumMapper {

    private final LinkMapper linkMapper;
    private final SongMapper songMapper;

    public AlbumDTO toDTO(Album source) {
        if (source == null) return null;

        return AlbumDTO.builder()
                .id(source.getId())
                .nombre(source.getName())
                .portada(source.getCover())
                .fechaPublicacion(source.getReleaseDate())
                .enlaces(linkMapper.toDTOsListFromAlbumLink(source.getLinks()))
                .build();
    }

    public AlbumFullDTO toFullDTO(Album source, List<Song> songs) {
        if (source == null) return null;

        return AlbumFullDTO.builder()
                .id(source.getId())
                .nombre(source.getName())
                .portada(source.getCover())
                .fechaPublicacion(source.getReleaseDate())
                .enlaces(linkMapper.toDTOsListFromAlbumLink(source.getLinks()))
                .canciones(songMapper.toDTOsList(songs))
                .build();
    }

    public List<AlbumDTO> toDTOsList(List<Album> source) {
        if (source == null || source.isEmpty()) return null;

        return source.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AlbumFullDTO> toFullDTOsList(List<Album> source, Map<Long, List<Song>> songs) {
        if (source == null || source.isEmpty() || songs == null || songs.isEmpty()) return null;

        return source.stream()
                .map(album -> toFullDTO(album, songs.get(album.getId())))
                .collect(Collectors.toList());
    }
}
