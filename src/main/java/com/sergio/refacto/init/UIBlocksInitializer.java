package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import static com.sergio.refacto.tools.Constants.*;

public class UIBlocksInitializer {

    public static Map<String, String> init() {
        Map<String, String> uiBlocks = new HashMap<>();

        for (int i = 1; i < ITEMS.length; i++) {
            uiBlocks.put(ITEMS[i], UI_ITEMS[i]);
        }

        return uiBlocks;
    }
}
