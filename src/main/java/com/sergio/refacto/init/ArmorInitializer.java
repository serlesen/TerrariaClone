package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;

public class ArmorInitializer {

    public static Map<Short, Integer> init() {
        Map<Short, Integer> armor = new HashMap<>();

        for (int i = 0; i < Items.values().length; i++) {
            armor.put((short) i, 0);
        }

        armor.put((short) 105 , 1);
        armor.put((short) 106 , 2);
        armor.put((short) 107 , 1);
        armor.put((short) 108 , 1);
        armor.put((short) 109 , 1);
        armor.put((short) 110 , 3);
        armor.put((short) 111 , 2);
        armor.put((short) 112 , 1);
        armor.put((short) 113 , 2);
        armor.put((short) 114 , 4);
        armor.put((short) 115 , 3);
        armor.put((short) 116 , 1);
        armor.put((short) 117 , 3);
        armor.put((short) 118 , 6);
        armor.put((short) 119 , 5);
        armor.put((short) 120 , 2);
        armor.put((short) 121 , 4);
        armor.put((short) 122 , 7);
        armor.put((short) 123 , 6);
        armor.put((short) 124 , 3);
        armor.put((short) 125 , 5);
        armor.put((short) 126 , 9);
        armor.put((short) 127 , 7);
        armor.put((short) 128 , 4);
        armor.put((short) 129 , 7);
        armor.put((short) 130 , 12);
        armor.put((short) 131 , 10);
        armor.put((short) 132 , 6);
        armor.put((short) 133 , 4);
        armor.put((short) 134 , 7);
        armor.put((short) 135 , 6);
        armor.put((short) 136 , 3);
        armor.put((short) 137 , 2);
        armor.put((short) 138 , 4);
        armor.put((short) 139 , 3);
        armor.put((short) 140 , 1);
        armor.put((short) 141 , 10);
        armor.put((short) 142 , 18);
        armor.put((short) 143 , 14);
        armor.put((short) 144 , 8);
        
        return armor;
    }
}
