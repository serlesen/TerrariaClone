package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;

public class ToolDamageInitializer {

    public static Map<Short, Integer> init() {
        Map<Short, Integer> toolDamage = new HashMap<>();

        for (int i = 0; i < Items.values().length; i++) {
            toolDamage.put((short) i, 1);
        }

        toolDamage.put((short) 7, 2);
        toolDamage.put((short) 8, 3);
        toolDamage.put((short) 9, 3);
        toolDamage.put((short) 10, 4);
        toolDamage.put((short) 11, 3);
        toolDamage.put((short) 12, 4);
        toolDamage.put((short) 13, 5);
        toolDamage.put((short) 14, 6);
        toolDamage.put((short) 16, 5);
        toolDamage.put((short) 17, 8);
        toolDamage.put((short) 18, 13);
        toolDamage.put((short) 19, 18);
        toolDamage.put((short) 51, 6);
        toolDamage.put((short) 52, 9);
        toolDamage.put((short) 53, 24);
        toolDamage.put((short) 54, 8);
        toolDamage.put((short) 55, 11);
        toolDamage.put((short) 56, 30);
        toolDamage.put((short) 57, 10);
        toolDamage.put((short) 58, 15);
        toolDamage.put((short) 59, 38);
        toolDamage.put((short) 145, 7);
        toolDamage.put((short) 146, 10);
        toolDamage.put((short) 147, 27);
        toolDamage.put((short) 148, 4);
        toolDamage.put((short) 149, 5);
        toolDamage.put((short) 150, 9);
        toolDamage.put((short) 154, 1);
        toolDamage.put((short) 155, 1);
        toolDamage.put((short) 156, 3);
        toolDamage.put((short) 157, 1);
        toolDamage.put((short) 158, 2);
        toolDamage.put((short) 159, 4);
        toolDamage.put((short) 57, 20);
        toolDamage.put((short) 58, 30);
        toolDamage.put((short) 59, 75);

        return toolDamage;
    }
}
