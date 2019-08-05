package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;
import com.sergio.refacto.tools.ResourcesLoader;

public class ItemImagesInitializer {

    public static Map<Short, BufferedImage> init() {
        Map<Short, BufferedImage> itemImgs = new HashMap<>();

        for (int i = 1; i < Items.values().length; i++) {
            itemImgs.put((short) i, ResourcesLoader.loadImage("items/" + Items.findByIndex(i).getFileName() + ".png"));
            if (itemImgs.get((short) i) == null) {
                System.out.println("[ERROR] Could not load item graphic '" + Items.findByIndex(i).getFileName() + "'.");
            }
        }

        return itemImgs;
    }
}
