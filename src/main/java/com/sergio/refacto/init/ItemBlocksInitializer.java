package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;

public class ItemBlocksInitializer {

    private static final int[] BLOCKS = {0, 1, 2, 3, 4, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 8, 9, 10, 11, 12, 13, 14, 17, 18, 0, 0, 0, 0, 0, 19, 20, 21, 22, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 42, 43, 44, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45/*72*/, 46, 47, 48, 0, 51, 0, 54, 0, 57, 0, 60, 0, 63, 0, 66, 0, 69, 0, 75, 76, 77, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 80, 81, 82, 0, 0, 0, 0, 0, 0, 0, 84, 85, 86, 87, 88, 89, 0, 90, 0, 0, 0, 0, 0, 0, 94, 100, 103, 105, 0, 111, 119, 127, 131, 133, 135, 137, 145, 153, 161, 0};

    public static Map<Short, Integer> init() {
        Map<Short, Integer> itemBlocks = new HashMap<>();

        for (int i = 1; i < Items.values().length; i++) {
            itemBlocks.put((short) i, BLOCKS[i]);
        }

        return itemBlocks;
    }
}
