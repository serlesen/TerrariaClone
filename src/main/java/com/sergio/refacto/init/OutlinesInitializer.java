package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class OutlinesInitializer {

    public static Map<Integer, String> init() {
        Map<Integer, String> outlines = new HashMap<>();

        for (int i = 1; i < BlockNames.values().length; i++) {
            outlines.put(i, "default");
        }

        outlines.put(7, "wood");
        outlines.put(8, "none");
        outlines.put(9, "none");
        outlines.put(10, "none");
        outlines.put(11, "none");
        outlines.put(12, "none");
        outlines.put(13, "none");
        outlines.put(14, "none");
        outlines.put(15, "tree");
        outlines.put(17, "none");
        outlines.put(20, "none");
        outlines.put(21, "none");
        outlines.put(22, "none");
        outlines.put(23, "none");
        outlines.put(24, "none");
        outlines.put(25, "none");
        outlines.put(26, "none");
        outlines.put(27, "none");
        outlines.put(28, "none");
        outlines.put(29, "none");
        outlines.put(30, "tree_root");
        outlines.put(47, "square");
        outlines.put(77, "none");
        outlines.put(78, "none");
        outlines.put(79, "none");
        outlines.put(80, "none");
        outlines.put(81, "none");
        outlines.put(82, "none");
        outlines.put(83, "tree");
        outlines.put(84, "square");
        outlines.put(85, "square");
        outlines.put(86, "square");
        outlines.put(87, "square");
        outlines.put(89, "square");
        outlines.put(90, "wood");
        outlines.put(94, "wire");
        outlines.put(95, "wire");
        outlines.put(96, "wire");
        outlines.put(97, "wire");
        outlines.put(98, "wire");
        outlines.put(99, "wire");
        outlines.put(100, "none");
        outlines.put(101, "none");
        outlines.put(102, "none");
        outlines.put(103, "square");
        outlines.put(104, "square");
        outlines.put(105, "none");
        outlines.put(106, "none");
        outlines.put(107, "none");
        outlines.put(108, "none");
        outlines.put(109, "none");
        outlines.put(110, "none");
        outlines.put(111, "none");
        outlines.put(112, "none");
        outlines.put(113, "none");
        outlines.put(114, "none");
        outlines.put(115, "none");
        outlines.put(116, "none");
        outlines.put(117, "none");
        outlines.put(118, "none");
        outlines.put(119, "none");
        outlines.put(120, "none");
        outlines.put(121, "none");
        outlines.put(122, "none");
        outlines.put(123, "none");
        outlines.put(124, "none");
        outlines.put(125, "none");
        outlines.put(126, "none");
        outlines.put(127, "none");
        outlines.put(128, "none");
        outlines.put(129, "none");
        outlines.put(130, "none");
        outlines.put(131, "none");
        outlines.put(132, "none");
        outlines.put(133, "none");
        outlines.put(134, "none");
        outlines.put(135, "none");
        outlines.put(136, "none");

        for (int i = 48; i < 72; i++) {
            outlines.put(i, "none");
        }

        for (int i = 137; i < 169; i++) {
            outlines.put(i, "none");
        }

        return outlines;
    }
}
