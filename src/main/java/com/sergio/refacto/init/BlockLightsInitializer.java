package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import static com.sergio.refacto.tools.Constants.*;

public class BlockLightsInitializer {

    public static Map<Integer, Integer> init() {
        Map<Integer, Integer> blockLights = new HashMap<>();

        for (int i = 0; i < BLOCK_NAMES.length; i++) {
            blockLights.put(i, 0);
        }

        blockLights.put(19, 21);
        blockLights.put(20, 15);
        blockLights.put(21, 18);
        blockLights.put(22, 21);
        blockLights.put(23, 15);
        blockLights.put(24, 15);
        blockLights.put(25, 15);
        blockLights.put(26, 18);
        blockLights.put(27, 18);
        blockLights.put(28, 21);
        blockLights.put(29, 21);
        blockLights.put(36, 15);
        blockLights.put(36, 15);
        blockLights.put(38, 18);
        blockLights.put(51, 15);
        blockLights.put(52, 15);
        blockLights.put(53, 15);
        blockLights.put(95, 6);
        blockLights.put(96, 7);
        blockLights.put(97, 8);
        blockLights.put(98, 9);
        blockLights.put(99, 10);
        blockLights.put(100, 12);
        blockLights.put(101, 12);
        blockLights.put(102, 12);
        blockLights.put(104, 21);
        blockLights.put(112, 12);
        blockLights.put(114, 12);
        blockLights.put(116, 12);
        blockLights.put(118, 12);
        blockLights.put(123, 12);
        blockLights.put(124, 12);
        blockLights.put(125, 12);
        blockLights.put(126, 12);

        blockLights.put(137, 12);
        blockLights.put(138, 12);
        blockLights.put(139, 12);
        blockLights.put(140, 12);
        blockLights.put(145, 12);
        blockLights.put(146, 12);
        blockLights.put(147, 12);
        blockLights.put(148, 12);
        blockLights.put(153, 12);
        blockLights.put(154, 12);
        blockLights.put(155, 12);
        blockLights.put(156, 12);
        blockLights.put(161, 12);
        blockLights.put(162, 12);
        blockLights.put(163, 12);
        blockLights.put(164, 12);

        return blockLights;
    }
}
