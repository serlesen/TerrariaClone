package com.sergio.refacto.dto;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemCollection implements Serializable {

    ItemType type;
    short[] ids, nums, durs;
    transient BufferedImage image;
    double FUELP = 0;
    double SMELTP = 0;
    boolean F_ON = false;

    public ItemCollection(ItemType type, short[] ids, short[] nums, short[] durs) {
        this.type = type;
        this.ids = ids;
        this.nums = nums;
        this.durs = durs;
    }

    public ItemCollection(ItemType type, int size) {
        short[] list1 = new short[size];
        short[] list2 = new short[size];
        short[] list3 = new short[size];
        for (int i = 0; i < size; i++) {
            list1[i] = 0;
            list2[i] = 0;
            list3[i] = 0;
        }
        this.type = type;
        this.ids = list1;
        this.nums = list2;
        this.durs = list3;
    }

    public ItemCollection(ItemCollection ic) {
        this.type = ic.type;
        this.ids = ic.ids;
        this.nums = ic.nums;
        this.durs = ic.durs;
    }

    public boolean areIdsEquals(short[] inputValues) {
        boolean valid = true;

        for (int i = 0; i < inputValues.length; i++) {
            if (ids[i] != inputValues[i]) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public boolean areInvalidIds(short[] inputValues) {
        boolean valid = true;

        List<Short> r3 = new ArrayList<>(6);
        for (int j = 0; j < inputValues.length - 2; j++) {
            r3.add(inputValues[j]);
        }
        for (int j = 0; j < inputValues.length; j++) {
            int n = r3.indexOf(ids[j]);
            if (n == -1) {
                valid = false;
                break;
            }
            else {
                r3.remove(n);
            }
        }

        return valid;
    }
}
