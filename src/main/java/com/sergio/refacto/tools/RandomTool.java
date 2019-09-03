package com.sergio.refacto.tools;

import java.util.Random;

public class RandomTool {

    private static final int seed = new Random().nextInt();

    private static Random random;

    public static int nextInt(int bound) {
        if (random == null) {
            random = new Random(seed);
        }
        return random.nextInt(bound);
    }

    public static double nextDouble() {
        if (random == null) {
            random = new Random(seed);
        }
        return random.nextDouble();
    }
}
