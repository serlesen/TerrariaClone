package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.tools.ResourcesLoader;

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
public class BackgroundImagesInitializer {

    private static final String[] BGS = {"solid/empty", "dirt_none/downleft", "dirt_none/downright", "dirt_none/left", "dirt_none/right", "dirt_none/up", "dirt_none/upleft", "dirt_none/upright",
            "solid/dirt", "stone_dirt/downleft", "stone_dirt/downright", "stone_dirt/left", "stone_dirt/right", "stone_dirt/up", "stone_dirt/upleft", "stone_dirt/upright",
            "solid/stone", "stone_none/down"};

    public static Map<Byte, BufferedImage> init() {
        Map<Byte,BufferedImage> backgroundImgs = new HashMap<>();

        for (int i = 0; i < BGS.length; i++) {
            backgroundImgs.put((byte) i, ResourcesLoader.loadImage("backgrounds/" + BGS[i] + ".png"));
        }

        return backgroundImgs;
    }
}
