package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class FuelsInitializer {

    public static Map<Short, Double> init() {
        Map<Short, Double> fuels = new HashMap<>();

        fuels.put((short) 15, 0.01);
        fuels.put((short) 28, 0.001);
        fuels.put((short) 160, 0.02);
        fuels.put((short) 168, 0.01);
        fuels.put((short) 179, 0.0035);
        fuels.put((short) 20, 0.0025);
        fuels.put((short) 21, 0.00125);
        fuels.put((short) 35, 0.02);
        fuels.put((short) 36, 0.011);
        fuels.put((short) 77, 0.02);
        fuels.put((short) 79, 0.02);
        fuels.put((short) 81, 0.02);
        fuels.put((short) 83, 0.02);
        fuels.put((short) 85, 0.02);
        fuels.put((short) 87, 0.02);
        fuels.put((short) 89, 0.0035);
        fuels.put((short) 91, 0.02);
        fuels.put((short) 95, 0.02);
        fuels.put((short) 78, 0.01);
        fuels.put((short) 80, 0.01);
        fuels.put((short) 82, 0.01);
        fuels.put((short) 84, 0.01);
        fuels.put((short) 86, 0.01);
        fuels.put((short) 88, 0.01);
        fuels.put((short) 90, 0.01);
        fuels.put((short) 92, 0.01);
        fuels.put((short) 96, 0.01);
        for (int i = 97; i < 103; i++) {
            fuels.put((short) i, 0.0035);
        }
        fuels.put((short) 154, 0.002);
        fuels.put((short) 155, 0.002);
        fuels.put((short) 156, 0.00333);
        
        return fuels;
    }
}
