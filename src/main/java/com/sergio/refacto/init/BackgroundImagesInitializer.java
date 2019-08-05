package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.tools.ResourcesLoader;

public class BackgroundImagesInitializer {

    public static Map<Byte, BufferedImage> init() {
        Map<Byte,BufferedImage> backgroundImgs = new HashMap<>();

        String[] bgs = {"solid/empty", "dirt_none/downleft", "dirt_none/downright", "dirt_none/left", "dirt_none/right", "dirt_none/up", "dirt_none/upleft", "dirt_none/upright",
                "solid/dirt", "stone_dirt/downleft", "stone_dirt/downright", "stone_dirt/left", "stone_dirt/right", "stone_dirt/up", "stone_dirt/upleft", "stone_dirt/upright",
                "solid/stone", "stone_none/down"};

        for (int i = 0; i < bgs.length; i++) {
            backgroundImgs.put((byte) i, ResourcesLoader.loadImage("backgrounds/" + bgs[i] + ".png"));
        }

        return backgroundImgs;
    }
}
