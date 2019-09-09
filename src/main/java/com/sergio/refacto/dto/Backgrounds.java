package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 0    None
 * 1    Dirt/None downleft
 * 2    Dirt/None downright
 * 3    Dirt/None left
 * 4    Dirt/None right
 * 5    Dirt/None up
 * 6    Dirt/None upleft
 * 7    Dirt/None upright
 * 8    Dirt
 * 9    Stone/Dirt downleft
 * 10    Stone/Dirt downright
 * 11    Stone/Dirt left
 * 12    Stone/Dirt right
 * 13    Stone/Dirt up
 * 14    Stone/Dirt upleft
 * 15    Stone/Dirt upright
 * 16    Stone
 * 17    Stone/None down
 */
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Backgrounds {

    EMPTY("solid/empty.png"),
    DIRT_DOWN_LEFT("dirt_none/downleft.png"),
    DIRT_DOWN_RIGHT("dirt_none/downright.png"),
    DIRT_LEFT("dirt_none/left.png"),
    DIRT_RIGHT("dirt_none/right.png"),
    DIRT_UP("dirt_none/up.png"),
    DIRT_UP_LEFT("dirt_none/upleft.png"),
    DIRT_UP_RIGHT("dirt_none/upright.png"),
    SOLID_DIRT("solid/dirt.png"),
    STONE_DOWN_LEFT("stone_dirt/downleft.png"),
    STONE_DOWN_RIGHT("stone_dirt/downright.png"),
    STONE_LEFT("stone_dirt/left.png"),
    STONE_RIGHT("stone_dirt/right.png"),
    STONE_UP("stone_dirt/up.png"),
    STONE_UP_LEFT("stone_dirt/upleft.png"),
    STONE_UP_RIGHT("stone_dirt/upright.png"),
    STONE("solid/stone.png"),
    STONE_DOWN("stone_none/down.png");

    String fileName;
}
