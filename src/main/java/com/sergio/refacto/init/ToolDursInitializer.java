package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

public class ToolDursInitializer {

    public static Map<Short, Short> init() {
        Map<Short, Short> toolDurs = new HashMap<>();

        toolDurs.put((short) 7, (short) 400);   // copper: P0200 A0200 S0125
        toolDurs.put((short) 8, (short) 500);   // iron:   P0250 A0250 S0150
        toolDurs.put((short) 9, (short) 600);   // silver: P0300 A0300 S0200
        toolDurs.put((short) 10, (short) 800);  // gold:   P0400 A0400 S0300
        toolDurs.put((short) 11, (short) 400);
        toolDurs.put((short) 12, (short) 500);
        toolDurs.put((short) 13, (short) 600);
        toolDurs.put((short) 14, (short) 800);
        toolDurs.put((short) 16, (short) 250);
        toolDurs.put((short) 17, (short) 300);
        toolDurs.put((short) 18, (short) 400);
        toolDurs.put((short) 19, (short) 600);
        toolDurs.put((short) 33, (short) 100);
        toolDurs.put((short) 51, (short) 1100);  // zinc:   P0550 A0550 S0475
        toolDurs.put((short) 52, (short) 1100);
        toolDurs.put((short) 53, (short) 950);
        toolDurs.put((short) 54, (short) 1350);  // rhyme:  P0675 A0675 S0625
        toolDurs.put((short) 55, (short) 1350);
        toolDurs.put((short) 56, (short) 1250);
        toolDurs.put((short) 57, (short) 1600);  // obdur:  P0800 A0800 S0800
        toolDurs.put((short) 58, (short) 1600);
        toolDurs.put((short) 59, (short) 1600);
        toolDurs.put((short) 145, (short) 200); // alumin: P0100 A0100 S0050
        toolDurs.put((short) 146, (short) 200);
        toolDurs.put((short) 147, (short) 100);
        toolDurs.put((short) 148, (short) 2400);// lead:   P1200 A1200 S0800
        toolDurs.put((short) 149, (short) 2400);
        toolDurs.put((short) 150, (short) 1600);
        toolDurs.put((short) 154, (short) 200); // wood:   P0100 A0100 S0050
        toolDurs.put((short) 155, (short) 200);
        toolDurs.put((short) 156, (short) 100);
        toolDurs.put((short) 157, (short) 300); // stone:  P0150 A0150 S0075
        toolDurs.put((short) 158, (short) 300);
        toolDurs.put((short) 159, (short) 150);
        toolDurs.put((short) 169, (short) 1200); // magne:  P0600 A0600 S0600
        toolDurs.put((short) 170, (short) 1200);
        toolDurs.put((short) 171, (short) 1200);
        toolDurs.put((short) 172, (short) 4000);// irrad:  P2000 A2000 S2000
        toolDurs.put((short) 173, (short) 4000);
        toolDurs.put((short) 174, (short) 4000);
        toolDurs.put((short) 190, (short) 400);

        toolDurs.put((short) 105, (short) 200); // copper: 0300
        toolDurs.put((short) 106, (short) 200); // copper: 0300
        toolDurs.put((short) 107, (short) 200); // copper: 0300
        toolDurs.put((short) 108, (short) 200); // copper: 0300
        toolDurs.put((short) 109, (short) 200); // iron:   0400
        toolDurs.put((short) 110, (short) 200); // iron:   0400
        toolDurs.put((short) 111, (short) 200); // iron:   0400
        toolDurs.put((short) 112, (short) 200); // iron:   0400
        toolDurs.put((short) 113, (short) 200); // silver: 0550
        toolDurs.put((short) 114, (short) 200); // silver: 0550
        toolDurs.put((short) 115, (short) 200); // silver: 0550
        toolDurs.put((short) 116, (short) 200); // silver: 0550
        toolDurs.put((short) 117, (short) 200); // gold:   0700
        toolDurs.put((short) 118, (short) 200); // gold:   0700
        toolDurs.put((short) 119, (short) 200); // gold:   0700
        toolDurs.put((short) 120, (short) 200); // gold:   0700
        toolDurs.put((short) 121, (short) 200); // zinc:   0875
        toolDurs.put((short) 122, (short) 200); // zinc:   0875
        toolDurs.put((short) 123, (short) 200); // zinc:   0875
        toolDurs.put((short) 124, (short) 200); // zinc:   0875
        toolDurs.put((short) 125, (short) 200); // rhyme:  1000
        toolDurs.put((short) 126, (short) 200); // rhyme:  1000
        toolDurs.put((short) 127, (short) 200); // rhyme:  1000
        toolDurs.put((short) 128, (short) 200); // rhyme:  1000
        toolDurs.put((short) 129, (short) 200); // obdur:  1400
        toolDurs.put((short) 130, (short) 200); // obdur:  1400
        toolDurs.put((short) 131, (short) 200); // obdur:  1400
        toolDurs.put((short) 132, (short) 200); // obdur:  1400
        toolDurs.put((short) 133, (short) 200); // alumin: 0150
        toolDurs.put((short) 134, (short) 200); // alumin: 0150
        toolDurs.put((short) 135, (short) 200); // alumin: 0150
        toolDurs.put((short) 136, (short) 200); // alumin: 0150
        toolDurs.put((short) 137, (short) 200); // lead:   2000
        toolDurs.put((short) 138, (short) 200); // lead:   2000
        toolDurs.put((short) 139, (short) 200); // lead:   2000
        toolDurs.put((short) 140, (short) 200); // lead:   2000
        
        return toolDurs;
    }
}
