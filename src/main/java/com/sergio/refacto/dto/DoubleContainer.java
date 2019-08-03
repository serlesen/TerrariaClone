package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoubleContainer {
    double[] doubles;
    public DoubleContainer(double[] doubles) {
        this.doubles = doubles;
    }
}
