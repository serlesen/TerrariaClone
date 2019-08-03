package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class TorchesRInitializer {

    public static Map<Integer, Integer> init() {
        Map<Integer,Integer> torchesR = new HashMap<>();

        torchesR.put(20, 25);
        torchesR.put(21, 27);
        torchesR.put(22, 29);
        torchesR.put(100, 102);
        torchesR.put(105, 109);
        torchesR.put(106, 110);
        torchesR.put(127, 129);

        return torchesR;
    }
}
