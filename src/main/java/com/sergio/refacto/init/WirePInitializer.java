package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class WirePInitializer {

    public static Map<Integer, Integer> init() {
        Map<Integer, Integer> wireP = new HashMap<>();

        wireP.put(0, 94);
        wireP.put(1, 95);
        wireP.put(2, 96);
        wireP.put(3, 97);
        wireP.put(4, 98);
        wireP.put(5, 99);

        return wireP;
    }
}
