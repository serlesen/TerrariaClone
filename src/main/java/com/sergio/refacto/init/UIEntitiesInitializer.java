package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class UIEntitiesInitializer {

    public static Map<String, String> init() {
        Map<String, String> uiEntities = new HashMap<>();

        uiEntities.put("blue_bubble", "Blue Bubble");
        uiEntities.put("green_bubble", "Green Bubble");
        uiEntities.put("red_bubble", "Red Bubble");
        uiEntities.put("black_bubble", "Black Bubble");
        uiEntities.put("white_bubble", "White Bubble");
        uiEntities.put("zombie", "Zombie");
        uiEntities.put("armored_zombie", "Armored Zombie");
        uiEntities.put("shooting_star", "Shooting Star");
        uiEntities.put("sandbot", "Sandbot");
        uiEntities.put("snowman", "Snowman");
        uiEntities.put("bat", "Bat");
        uiEntities.put("bee", "Bee");
        uiEntities.put("skeleton", "Skeleton");

        return uiEntities;
    }
}
