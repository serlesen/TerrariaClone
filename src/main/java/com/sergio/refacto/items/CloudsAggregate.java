package com.sergio.refacto.items;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * DTO to handle all the clouds
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloudsAggregate {

    List<Cloud> clouds = new ArrayList<>();

    public void removeCloud(int index) {
        clouds.remove(index);
    }

    public void updateXUponSpeed(int index) {
        clouds.get(index).updateXUponSpeed();
    }

    public boolean isOutsideBounds(int index, int width) {
        return clouds.get(index).isOutsideBounds(width);
    }
}
