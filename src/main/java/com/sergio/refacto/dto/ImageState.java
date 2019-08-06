package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ImageState {
    FLAP_RIGHT(true, true, false, false, false),
    FLAP_LEFT(false, true, false,  false,false),
    NORMAL_RIGHT(true, false, true, false, false),
    NORMAL_LEFT(false, false, true, false, false),
    STILL_RIGHT(true, false, false, true, false),
    STILL_LEFT(false, false, false, true, false),
    WALK_RIGHT_1(true, false, false, false, true),
    WALK_LEFT_1(false, false, false, false, true),
    WALK_RIGHT_2(true, false, false, false, true),
    WALK_LEFT_2(false, false, false, false, true);

    boolean right;
    boolean flap;
    boolean normal;
    boolean still;
    boolean walk;

    public boolean isLeft() {
        return !right;
    }

    public boolean isWalkLeft() {
        return walk && !right;
    }

    public boolean isWalkRight() {
        return walk && right;
    }
}
