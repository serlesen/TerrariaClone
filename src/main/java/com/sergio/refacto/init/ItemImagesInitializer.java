package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.tools.ResourcesLoader;

import static com.sergio.refacto.tools.Constants.*;

public class ItemImagesInitializer {

    public static Map<Short, BufferedImage> init() {
        Map<Short, BufferedImage> itemImgs = new HashMap<>();

        for (int i = 1; i < ITEMS.length; i++) {
            itemImgs.put((short) i, ResourcesLoader.loadImage("items/" + ITEMS[i] + ".png"));
            if (itemImgs.get((short) i) == null) {
                System.out.println("[ERROR] Could not load item graphic '" + ITEMS[i] + "'.");
            }
        }

        return itemImgs;
    }
}
