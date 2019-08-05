package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import static com.sergio.refacto.dto.Constants.*;

public class BlockCDInitializer {

    public static Map<Integer, Boolean> init() {
        Map<Integer, Boolean> blockCD = new HashMap<>();

        for (int i = 1; i < BLOCK_CDS.length; i++) {
            blockCD.put(i, BLOCK_CDS[i]);
        }

        return blockCD;
    }
}
