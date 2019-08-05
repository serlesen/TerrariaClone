package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class FSpeedInitializer {

    public static Map<Short, Double> init() {
        Map<Short,Double> fSpeed = new HashMap<>();

        for (int i = 0; i < BlockNames.values().length; i++) {
            fSpeed.put((short) i, 0.001);
        }

        fSpeed.put((short) 85, -0.001);
        fSpeed.put((short) 86, -0.001);

        return fSpeed;
    }
}
