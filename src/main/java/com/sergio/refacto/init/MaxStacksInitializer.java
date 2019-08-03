package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import static com.sergio.refacto.tools.Constants.*;

public class MaxStacksInitializer {

    private static final short[] STACKS = {100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 100, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100, 100, 1, 1, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1};

    public static Map<Short, Short> init() {
        Map<Short, Short> maxStacks = new HashMap<>();

        for (int i = 0; i < ITEMS.length; i++) {
            maxStacks.put((short) i, STACKS[i]);
        }

        return maxStacks;
    }
}
