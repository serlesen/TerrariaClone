package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class DDelayInitializer {

    public static Map<Integer, Integer> init() {
        Map<Integer, Integer> dDelay = new HashMap<>();

        for (int i = 137; i < 145; i++) {
            dDelay.put(i, 10);
        }
        for (int i = 145; i < 153; i++) {
            dDelay.put(i, 20);
        }
        for (int i = 153; i < 161; i++) {
            dDelay.put(i, 40);
        }
        for (int i = 161; i < 169; i++) {
            dDelay.put(i, 80);
        }

        return dDelay;
    }
}
