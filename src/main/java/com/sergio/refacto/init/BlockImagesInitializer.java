package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockImagesInitializer {

    public static Map<String, BufferedImage> init() {
        Map<String, BufferedImage> blockImgs = new HashMap<>();

        for (int i = 1; i < BlockNames.values().length; i++) {
            for (int j = 0; j < 8; j++) {
                blockImgs.put("blocks/" + BlockNames.findByIndex(i).getFileName() + "/texture" + (j+1) + ".png",
                        ResourcesLoader.loadImage("blocks/" + BlockNames.findByIndex(i).getFileName() + "/texture" + (j+1) + ".png"));
                if (blockImgs.get("blocks/" + BlockNames.findByIndex(i).getFileName() + "/texture" + (j+1) + ".png") == null) {
                    log.error("[ERROR] Could not load block graphic '" + BlockNames.findByIndex(i).getFileName() + "'.");
                }
            }
        }

        return blockImgs;
    }
}
