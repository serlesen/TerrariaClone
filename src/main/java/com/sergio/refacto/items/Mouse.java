package com.sergio.refacto.items;

import com.sergio.refacto.dto.ItemPositionInScreen;
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

    public boolean isInBetweenInclusive(ItemPositionInScreen positionInScreen) {
        return isInBetweenInclusive(positionInScreen.getLowerX(), positionInScreen.getLowerY(), positionInScreen.getHigherX(), positionInScreen.getHigherY());
    }

    public boolean isInBetweenInclusive(int lowerX, int lowerY, int higherX, int higherY) {
        return x >= lowerX && x <= lowerY && y >= higherX && y <= higherY;
    }

    public boolean isInBetween(int lowerX, int lowerY, int higherX, int higherY) {
        return x >= lowerX && x < lowerY && y >= higherX && y < higherY;
    }
}
