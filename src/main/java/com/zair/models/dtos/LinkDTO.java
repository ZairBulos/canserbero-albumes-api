package com.zair.models.dtos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class LinkDTO {
    private final String url;
    private final String plataforma;
}
