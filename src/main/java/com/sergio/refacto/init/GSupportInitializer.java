package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class GSupportInitializer {

    public static Map<Integer, Boolean> init() {
        Map<Integer, Boolean> gSupport = new HashMap<>();

        for (int i = 0; i < BlockNames.values().length; i++) {
            gSupport.put(i, false);
        }

        gSupport.put(15, true);
        gSupport.put(83, true);
        gSupport.put(20, true);
        gSupport.put(21, true);
        gSupport.put(22, true);
        gSupport.put(77, true);
        gSupport.put(78, true);
        gSupport.put(100, true);
        gSupport.put(105, true);
        gSupport.put(106, true);
        gSupport.put(131, true);
        gSupport.put(132, true);
        gSupport.put(133, true);
        gSupport.put(134, true);
        gSupport.put(135, true);
        gSupport.put(136, true);

        for (int i=48; i<73; i++) {
            gSupport.put(i, true);
        }

        return gSupport;
    }
}
