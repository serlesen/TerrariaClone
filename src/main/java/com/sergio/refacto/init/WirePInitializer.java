package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Blocks;

public class WirePInitializer {

    public static Map<Integer, Blocks> init() {
        Map<Integer, Blocks> wireP = new HashMap<>();

        wireP.put(0, Blocks.ZYTHIUM_WIRE);
        wireP.put(1, Blocks.ZYTHIUM_WIRE_1_POWER);
        wireP.put(2, Blocks.ZYTHIUM_WIRE_2_POWER);
        wireP.put(3, Blocks.ZYTHIUM_WIRE_3_POWER);
        wireP.put(4, Blocks.ZYTHIUM_WIRE_4_POWER);
        wireP.put(5, Blocks.ZYTHIUM_WIRE_5_POWER);

        return wireP;
    }
}
