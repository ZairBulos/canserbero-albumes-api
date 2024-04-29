package com.zair.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum Platform {
    YOUTUBE("YouTube"),
    SPOTIFY("Spotify"),
    APPLE_MUSIC("Apple Music");

    private final String name;
}
