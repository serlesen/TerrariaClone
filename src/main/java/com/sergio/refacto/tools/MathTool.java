package com.sergio.refacto.tools;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathTool {

    public int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    public float max(float a, float b, float c) {
        return Math.max(Math.max(a, b), c);
    }

    public double max(double a, double b, double c) {
        return Math.max(Math.max(a, b), c);
    }

    public int max(int a, int b, int c, int d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    public float max(float a, float b, float c, float d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    public double max(double a, double b, double c, double d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    public int mod(int a, int q) {
        return ((a % q) + q) % q;
    }
}
