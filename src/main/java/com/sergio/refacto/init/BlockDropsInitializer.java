package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class BlockDropsInitializer {

    private static final short[] DROPS = {0, 1, 2, 3, 4, 5, 6, 15, 20, 21, 22, 23, 24, 25, 26, 15, 0, 27, 28, 34, 35, 36, 37, 27, 35, 35, 36, 36, 37, 37, 0, 38, 39, 40, 41, 42, 43, 44, 44, 45, 46, 47, 48, 49, 50, 74, 75, 0, 0, 0, 78, 0, 0, 80, 0, 0, 82, 0, 0, 84, 0, 0, 86, 0, 0, 88, 0, 0, 90, 0, 0, 92, 1, 1, 93, 93, 94, 0, 0, 96, 151, 152, 153, 15, 161, 162, 163, 164, 165, 166, 168, 1, 50, 1, 175, 175, 175, 175, 175, 175, 176, 176, 176, 177, 177, 178, 178, 178, 178, 178, 178, 180, 180, 180, 180, 180, 180, 180, 180, 181, 181, 181, 181, 181, 181, 181, 181, 182, 182, 182, 182, 183, 183, 184, 184, 185, 185, 186, 186, 186, 186, 186, 186, 186, 186, 187, 187, 187, 187, 187, 187, 187, 187, 188, 188, 188, 188, 188, 188, 188, 188, 189, 189, 189, 189, 189, 189, 189, 189};

    public static Map<Integer, Short> init() {
        Map<Integer, Short> blockDrops = new HashMap<>();

        for (int i = 1; i < BlockNames.values().length; i++) {
            blockDrops.put(i, DROPS[i]);
        }

        return blockDrops;
    }
}
