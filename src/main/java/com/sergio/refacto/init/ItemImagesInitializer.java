package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemImagesInitializer {

    public static Map<Items, BufferedImage> init() {
        Map<Items, BufferedImage> itemImgs = new HashMap<>();

        for (Items item : Items.values()) {
            if (item == Items.EMPTY) {
                continue;
            }
            itemImgs.put(item, ResourcesLoader.loadImage("items/" + item.getFileName() + ".png"));
            if (itemImgs.get(item) == null) {
                log.error("[ERROR] Could not load item graphic '" + item.getFileName() + "'.");
            }
        }

        return itemImgs;
    }
}
