package com.sergio.refacto.items;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mouse {

    int x;
    int y;

    boolean clicked = true;
    boolean released = false;

    public boolean isInBetweenInclusive(int lowerX, int lowerY, int higherX, int higherY) {
        return x >= lowerX && x <= lowerY && y >= higherX && y <= higherY;
    }

    public boolean isInBetween(int lowerX, int lowerY, int higherX, int higherY) {
        return x >= lowerX && x < lowerY && y >= higherX && y < higherY;
    }
}
