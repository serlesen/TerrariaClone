package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.tools.ResourcesLoader;

import static com.sergio.refacto.tools.Constants.*;

public class OutlineImagesInitializer {

    public static Map<String, BufferedImage> init() {
        Map<String, BufferedImage> outlineImgs = new HashMap<>();

        String[] outlineNameList = {"default", "wood", "none", "tree", "tree_root", "square", "wire"};

        for (int i = 0; i < outlineNameList.length; i++) {
            for (int j = 0; j < DIRS.length; j++) {
                for (int k = 0; k < 5; k++) {
                    outlineImgs.put("outlines/" + outlineNameList[i] + "/" + DIRS[j] + (k+1) + ".png",
                            ResourcesLoader.loadImage("outlines/" + outlineNameList[i] + "/" + DIRS[j] + (k+1) + ".png"));
                }
            }
        }

        return outlineImgs;
    }
}
