package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Blocks;

public class DDelayInitializer {

    public static Map<Blocks, Integer> init() {
        Map<Blocks, Integer> dDelay = new HashMap<>();

        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_UP, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON, 10);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_UP, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON, 20);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_UP, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON, 40);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT, 80);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN, 80);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT, 80);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_UP, 80);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON, 80);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON, 80);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON, 80);
        dDelay.put(Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON, 80);


        return dDelay;
    }
}
