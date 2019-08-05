package com.sergio.refacto.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 0    center
 * 1    tdown_both
 * 2    tdown_cw
 * 3    tdown_ccw
 * 4    tdown
 * 5    tup_both
 * 6    tup_cw
 * 7    tup_ccw
 * 8    tup
 * 9    leftright
 * 10    tright_both
 * 11    tright_cw
 * 12    tright_ccw
 * 13    tright
 * 14    upleftdiag
 * 15    upleft
 * 16    downleftdiag
 * 17    downleft
 * 18    left
 * 19    tleft_both
 * 20    tleft_cw
 * 21    tleft_ccw
 * 22    tleft
 * 23    uprightdiag
 * 24    upright
 * 25    downrightdiag
 * 26    downright
 * 27    right
 * 28    updown
 * 29    up
 * 30    down
 * 31    single
 */
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Directions {
    CENTER(0, "center"),
    TDOWN_BOTH(1, "tdown_both"),
    TDOWN_CW(2, "tdown_cw"),
    TDOWN_CCW(3, "tdown_ccw"),
    TDOWN(4, "tdown"),
    TUP_BOTH(5, "tup_both"),
    TUP_CW(6, "tup_cw"),
    TUP_CCW(7, "tup_ccw"),
    TUP(8, "tup"),
    LEFTRIGHT(9, "leftright"),
    TRIGHT_BOTH(10, "tright_both"),
    TRIGHT_CW(11, "tright_cw"),
    TRIGHT_CCW(12, "tright_ccw"),
    TRIGHT(13, "tright"),
    UPLEFTDIAG(14, "upleftdiag"),
    UPLEFT(15, "upleft"),
    DOWNLEFTDIAG(16, "downleftdiag"),
    DOWNLEFT(17, "downleft"),
    LEFT(18, "left"),
    TLEFT_BOTH(19, "tleft_both"),
    TLEFT_CW(20, "tleft_cw"),
    TLEFT_CCW(21, "tleft_ccw"),
    TLEFT(22, "tleft"),
    UPRIGHTDIAG(23, "uprightdiag"),
    UPRIGHT(24, "upright"),
    DOWNRIGHTDIAG(25, "downrightdiag"),
    DOWNRIGHT(26, "downright"),
    RIGHT(27, "right"),
    UPDOWN(28, "updown"),
    UP(29, "up"),
    DOWN(30, "down"),
    SINGLE(31, "single");

    int index;
    String fileName;

    private static final Map<Integer, Directions> DIRECTIONS_MAP;

    static {
        DIRECTIONS_MAP = new HashMap<>();
        for (Directions direction : Directions.values()) {
            DIRECTIONS_MAP.put(direction.getIndex(), direction);
        }
    }

    public static Directions findByIndex(int index) {
        return DIRECTIONS_MAP.get(index);
    }
}
