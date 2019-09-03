package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.EntityType;

public class UIEntitiesInitializer {

    public static Map<EntityType, String> init() {
        Map<EntityType, String> uiEntities = new HashMap<>();

        uiEntities.put(EntityType.BLUE_BUBBLE, EntityType.BLUE_BUBBLE.getName());
        uiEntities.put(EntityType.GREEN_BUBBLE, EntityType.GREEN_BUBBLE.getName());
        uiEntities.put(EntityType.RED_BUBBLE, EntityType.RED_BUBBLE.getName());
        uiEntities.put(EntityType.BLACK_BUBBLE, EntityType.BLACK_BUBBLE.getName());
        uiEntities.put(EntityType.WHITE_BUBBLE, EntityType.WHITE_BUBBLE.getName());
        uiEntities.put(EntityType.ZOMBIE, EntityType.ZOMBIE.getName());
        uiEntities.put(EntityType.ARMORED_ZOMBIE, EntityType.ARMORED_ZOMBIE.getName());
        uiEntities.put(EntityType.SHOOTING_STAR, EntityType.SHOOTING_STAR.getName());
        uiEntities.put(EntityType.SANDBOT, EntityType.SANDBOT.getName());
        uiEntities.put(EntityType.SNOWMAN, EntityType.SNOWMAN.getName());
        uiEntities.put(EntityType.BAT, EntityType.BAT.getName());
        uiEntities.put(EntityType.BEE, EntityType.BEE.getName());
        uiEntities.put(EntityType.SKELETON, EntityType.SKELETON.getName());

        return uiEntities;
    }
}
