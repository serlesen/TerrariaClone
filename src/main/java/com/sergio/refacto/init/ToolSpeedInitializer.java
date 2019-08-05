package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;

public class ToolSpeedInitializer {

    public static Map<Short, Double> init() {
        Map<Short, Double> toolSpeed = new HashMap<>();

        for (int i = 1; i < Items.values().length; i++) {
            toolSpeed.put((short) i, 0.175);
        }

        toolSpeed.put((short) 154, 0.100);// wood:   P100 S100
        toolSpeed.put((short) 155, 0.100);
        toolSpeed.put((short) 156, 0.100);
        toolSpeed.put((short) 157, 0.110);// stone:  P110 S105
        toolSpeed.put((short) 158, 0.110);
        toolSpeed.put((short) 159, 0.105);
        toolSpeed.put((short) 7, 0.120);  // copper: P120 S110
        toolSpeed.put((short) 11, 0.120);
        toolSpeed.put((short) 16, 0.110);
        toolSpeed.put((short) 8, 0.130);  // iron:   P130 S115
        toolSpeed.put((short) 12, 0.130);
        toolSpeed.put((short) 17, 0.115);
        toolSpeed.put((short) 9, 0.140);  // silver: P140 S120
        toolSpeed.put((short) 13, 0.140);
        toolSpeed.put((short) 18, 0.120);
        toolSpeed.put((short) 10, 0.150); // gold:   P150 S125
        toolSpeed.put((short) 14, 0.150);
        toolSpeed.put((short) 19, 0.125);
        toolSpeed.put((short) 51, 0.160); // zinc:   P160 S130
        toolSpeed.put((short) 52, 0.160);
        toolSpeed.put((short) 53, 0.130);
        toolSpeed.put((short) 54, 0.170); // rhyme:  P170 S135
        toolSpeed.put((short) 55, 0.170);
        toolSpeed.put((short) 56, 0.135);
        toolSpeed.put((short) 57, 0.180); // obdur:  P180 S140
        toolSpeed.put((short) 58, 0.180);
        toolSpeed.put((short) 59, 0.140);
        toolSpeed.put((short) 145, 0.350);// alumin: P250 S175
        toolSpeed.put((short) 146, 0.350);
        toolSpeed.put((short) 147, 0.245);
        toolSpeed.put((short) 148, 0.130);// lead:   P130 S115
        toolSpeed.put((short) 149, 0.130);
        toolSpeed.put((short) 150, 0.115);
        toolSpeed.put((short) 169, 0.250); // magne:  P350 S245
        toolSpeed.put((short) 170, 0.250);
        toolSpeed.put((short) 171, 0.175);
        toolSpeed.put((short) 172, 0.350); // irrad:  P350 S245
        toolSpeed.put((short) 173, 0.350);
        toolSpeed.put((short) 174, 0.245);

        toolSpeed.put((short) 33, 0.125); // stone lighter
        
        return toolSpeed;
    }
}
