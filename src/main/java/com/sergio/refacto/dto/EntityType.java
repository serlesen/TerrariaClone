package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum EntityType {
    BLUE_BUBBLE("Blue Bubble", "blue_bubble", 18, 0, 2),
    GREEN_BUBBLE("Green Bubble", "green_bubble", 25, 0, 4),
    RED_BUBBLE("Red Bubble", "red_bubble", 40, 0, 6),
    YELLOW_BUBBLE("Yellow Bubble", "yellow_bubble", 65, 1, 9),
    BLACK_BUBBLE("Black Bubble", "black_bubble", 100, 3, 14),
    WHITE_BUBBLE("White Bubble", "white_bubble", 70, 2, 11),
    ZOMBIE("Zombie", "zombie", 35, 0, 5),
    ARMORED_ZOMBIE("Armored Zombie", "armored_zombie", 45, 2, 7),
    SHOOTING_STAR("Shooting Star", "shooting_star", 25, 0, 5),
    SANDBOT("Sandbot", "sandbot", 50, 2, 3),
    SANDBOT_BULLET("Sandbot Bullet", "sandbot_bullet", 1, 0, 7),
    SNOWMAN("Snowman", "snowman", 40, 0, 6),
    BAT("Bat", "bat", 15, 0, 5),
    BEE("Bee", "bee", 1, 0, 5),
    SKELETON("Skeleton", "skeleton", 50, 1, 7),

    @Deprecated
    BUBBLE("Bubble", "bubble", 0, 0, 0),
    @Deprecated
    FAST_BUBBLE("Fast Bubble", "fast_bubble", 0, 0, 0),
    @Deprecated
    BULLET("Bullet", "bullet", 0, 0, 0);

    String name;
    String fileName;
    int healthPoints;
    int armorPoints;
    int attackPoints;
}
