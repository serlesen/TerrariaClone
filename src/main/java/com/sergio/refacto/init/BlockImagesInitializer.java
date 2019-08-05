package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.tools.ResourcesLoader;

import static com.sergio.refacto.tools.Constants.*;

public class BlockImagesInitializer {

    public static Map<String, BufferedImage> init() {
        Map<String, BufferedImage> blockImgs = new HashMap<>();

        for (int i = 1; i < BLOCK_NAMES.length; i++) {
            for (int j = 0; j < 8; j++) {
                blockImgs.put("blocks/" + BLOCK_NAMES[i] + "/texture" + (j+1) + ".png",
                        ResourcesLoader.loadImage("blocks/" + BLOCK_NAMES[i] + "/texture" + (j+1) + ".png"));
                if (blockImgs.get("blocks/" + BLOCK_NAMES[i] + "/texture" + (j+1) + ".png") == null) {
                    System.out.println("[ERROR] Could not load block graphic '" + BLOCK_NAMES[i] + "'.");
                }
            }
        }

        return blockImgs;
    }
}
