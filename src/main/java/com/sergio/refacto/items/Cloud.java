package com.sergio.refacto.items;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cloud {
    double x;
    double y;
    double speed;

    public void updateXUponSpeed() {
        x = x + speed;
    }

    public boolean isOutsideBounds(int width) {
        return x < -250 || x > width + 250;
    }
}
