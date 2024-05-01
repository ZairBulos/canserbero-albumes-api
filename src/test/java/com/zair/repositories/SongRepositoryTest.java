package com.zair.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.zair.models.entities.Album;
import com.zair.models.entities.AlbumLink;
import com.zair.models.entities.Song;
import com.zair.models.entities.SongLink;
import com.zair.models.enums.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private Album album;

    @BeforeEach
    public void setUp() {
        // Given
        AlbumLink albumLink = AlbumLink.builder().url("https://applemusic.com/album").platform(Platform.APPLE_MUSIC).build();
        album = Album.builder().name("Album").cover("https://cover.jpg").releaseDate(2024).links(Arrays.asList(albumLink)).build();
        albumLink.setAlbum(album);
        albumRepository.save(album);

        SongLink songLink1 = SongLink.builder().url("https://youtube.com/song").platform(Platform.YOUTUBE).build();
        SongLink songLink2 = SongLink.builder().url("https://spotify.com/song").platform(Platform.SPOTIFY).build();
        Song song1 = Song.builder().name("Song 1").duration(120000L).album(album).links(Arrays.asList(songLink1)).build();
        Song song2 = Song.builder().name("Song 2").duration(240000L).album(album).links(Arrays.asList(songLink2)).build();
        songLink1.setSong(song1);
        songLink2.setSong(song2);
        songRepository.saveAll(Arrays.asList(song1, song2));
    }

    @Test
    public void givenAlbumId_whenFindAllByAlbumId_thenReturnsListOfSongs() {
        // Given
        Long albumId = album.getId();

        // When
        List<Song> songs = songRepository.findAllByAlbum_Id(albumId);

        // Then
        assertThat(songs).isNotEmpty();
        assertThat(songs.size()).isEqualTo(2);
    }

    @Test
    public void givenName_whenFindAllByNameContainingIgnoreCase_thenReturnsListOfSongs() {
        // Given
        String searchTerm = "song";

        // When
        List<Song> songs = songRepository.findAllByNameContainingIgnoreCase(searchTerm);

        // Then
        assertThat(songs).isNotEmpty();
        assertThat(songs.size()).isEqualTo(2);
    }
}
