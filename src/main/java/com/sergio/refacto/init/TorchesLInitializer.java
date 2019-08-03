package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class TorchesLInitializer {

    public static Map<Integer, Integer> init() {
        Map<Integer, Integer> torchesL = new HashMap<>();

        torchesL.put(20, 24);
        torchesL.put(21, 26);
        torchesL.put(22, 28);
        torchesL.put(100, 101);
        torchesL.put(105, 107);
        torchesL.put(106, 108);
        torchesL.put(127, 127);
        torchesL.put(128, 128);

        return torchesL;
    }
}
