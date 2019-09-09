package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockImagesInitializer {

    public static Map<String, BufferedImage> init() {
        Map<String, BufferedImage> blockImgs = new HashMap<>();

        for (int i = 1; i < Blocks.values().length; i++) {
            for (int j = 0; j < 8; j++) {
                blockImgs.put("blocks/" + Blocks.findByIndex(i).getFileName() + "/texture" + (j+1) + ".png",
                        ResourcesLoader.loadImage("blocks/" + Blocks.findByIndex(i).getFileName() + "/texture" + (j+1) + ".png"));
                if (blockImgs.get("blocks/" + Blocks.findByIndex(i).getFileName() + "/texture" + (j+1) + ".png") == null) {
                    log.error("[ERROR] Could not load block graphic '" + Blocks.findByIndex(i).getFileName() + "'.");
                }
            }
        }

        return blockImgs;
    }
}
