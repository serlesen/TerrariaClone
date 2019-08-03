package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class GrassDirtInitializer {

    public static Map<Integer, Integer> init() {
        Map<Integer, Integer> grassDirt = new HashMap<>();

        grassDirt.put(72, 1);
        grassDirt.put(73, 1);
        grassDirt.put(74, 75);
        grassDirt.put(93, 91);

        return grassDirt;
    }
}
