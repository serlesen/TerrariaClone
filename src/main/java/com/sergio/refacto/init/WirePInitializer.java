package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class WirePInitializer {

    public static Map<Integer, BlockNames> init() {
        Map<Integer, BlockNames> wireP = new HashMap<>();

        wireP.put(0, BlockNames.ZYTHIUM_WIRE);
        wireP.put(1, BlockNames.ZYTHIUM_WIRE_1_POWER);
        wireP.put(2, BlockNames.ZYTHIUM_WIRE_2_POWER);
        wireP.put(3, BlockNames.ZYTHIUM_WIRE_3_POWER);
        wireP.put(4, BlockNames.ZYTHIUM_WIRE_4_POWER);
        wireP.put(5, BlockNames.ZYTHIUM_WIRE_5_POWER);

        return wireP;
    }
}
