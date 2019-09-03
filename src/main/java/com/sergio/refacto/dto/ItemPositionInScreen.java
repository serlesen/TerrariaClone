package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ItemPositionInScreen {
    SINGLE_PLAYER_MENU(239, 557, 213, 249),
    CREATE_NEW_WORLD_MENU(186, 615, 458, 484),
    BACK_BUTTON(334, 457, 504, 530);

    int lowerX;
    int lowerY;
    int higherX;
    int higherY;


}
