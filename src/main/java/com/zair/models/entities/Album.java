package com.zair.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "cover", nullable = false)
    private String cover;

    @Column(name = "release_date", nullable = false)
    private Integer releaseDate;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AlbumLink> links;
}
