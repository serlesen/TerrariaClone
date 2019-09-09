package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Blocks;

public class GrassDirtInitializer {

    public static Map<Blocks, Blocks> init() {
        Map<Blocks, Blocks> grassDirt = new HashMap<>();

        grassDirt.put(Blocks.GRASS, Blocks.DIRT);
        grassDirt.put(Blocks.JUNGLE_GRASS, Blocks.DIRT);
        grassDirt.put(Blocks.SWAMP_GRASS, Blocks.MUD);
        grassDirt.put(Blocks.GRASS_TRANSPARENT, Blocks.DIRT_TRANSPARENT);

        return grassDirt;
    }
}
