package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Biome {
    DESERT("desert"),
    JUNGLE("jungle"),
    SWAMP("swamp"),
    FROST("frost"),
    CAVERN("cavern"),
    UNDERGROUND("underground"),
    OTHER("other");
    
    String title;
}
