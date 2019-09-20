package com.sergio.refacto.services;

import com.sergio.refacto.dto.DebugContext;

/**
 * 28000 (before dusk), 32000 (after dusk)
 */
public class TimeService {

    private static final int SECONDS_IN_DAY = 86400;

    /** Sky color upon time of day. */
    private static final int[] SKY_COLORS = {28800, 28980, 29160, 29340, 29520, 29700, 29880, 30060, 30240, 30420, 30600, 30780, 30960, 31140, 31320, 31500, 31680, 31860, 32040, 32220, 72000, 72180, 72360, 72540, 72720, 72900, 73080, 73260, 73440, 73620, 73800, 73980, 74160, 74340, 74520, 74700, 74880, 75060, 75240, 75420};

    /** Time in seconds. */
    private double time;

    private int day;

    private static TimeService instance;

    private TimeService() {
        time = 0;
        day = 0;
    }

    public static TimeService getInstance() {
        if (instance == null) {
            instance = new TimeService();
        }
        return instance;
    }

    public void init() {
        time = 0;
        day = 0;
    }

    public void load(double time, int day) {
        this.time = time;
        this.day = day;
    }

    public void increaseTime() {
        time += 1.2 * DebugContext.ACCEL;
        if (time >= SECONDS_IN_DAY) {
            time = 0;
            day += 1;
        }
    }

    public boolean isNight() {
        return time >= 75913 || time < 28883;
    }

    public boolean isDay() {
        return time >= 32302 && time < 72093;
    }

    public double getTime() {
        return time;
    }

    public int getDay() {
        return day;
    }

    public double getSunsPosition() {
        return (time - 70200) / 86400 * Math.PI * 2;
    }

    public double getMoonsPosition() {
        return - getSunsPosition() - Math.PI;
    }

    public int getSkyLights() {
        for (int i = 0; i < SKY_COLORS.length; i++) {
            if (time >= SKY_COLORS[i]) {
                return SKY_COLORS[i];
            }
        }
        return SKY_COLORS[0];
    }
}
