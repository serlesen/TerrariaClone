package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class TorchesBInitializer {

    public static Map<Integer, Boolean> init() {
        Map<Integer,Boolean> torchesB = new HashMap<>();

        for (int i = 0; i < BlockNames.values().length; i++) {
            torchesB.put(i, false);
        }

        torchesB.put(20, true);
        torchesB.put(21, true);
        torchesB.put(22, true);
        torchesB.put(100, true);
        torchesB.put(24, true);
        torchesB.put(26, true);
        torchesB.put(28, true);
        torchesB.put(101, true);
        torchesB.put(25, true);
        torchesB.put(27, true);
        torchesB.put(29, true);
        torchesB.put(102, true);
        torchesB.put(105, true);
        torchesB.put(106, true);
        torchesB.put(107, true);
        torchesB.put(108, true);
        torchesB.put(109, true);
        torchesB.put(110, true);
        torchesB.put(127, true);
        torchesB.put(128, true);
        torchesB.put(129, true);
        torchesB.put(130, true);

        return torchesB;
    }
}
