package com.sergio.refacto;

import java.awt.image.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemCollection implements Serializable {

    String type;
    short[] ids, nums, durs;
    transient BufferedImage image;
    double FUELP = 0;
    double SMELTP = 0;
    boolean F_ON = false;
    short recipeNum;

    public ItemCollection(String type, short[] ids, short[] nums, short[] durs) {
        this.type = type;
        this.ids = ids;
        this.nums = nums;
        this.durs = durs;
    }

    public ItemCollection(String type, int size) {
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

    public boolean areIdsEquals(Short[] inputIds) {
        boolean valid = true;

        for (int i = 0; i < 4; i++) {
            if (ids[i] != inputIds[i]) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public boolean areInvalidIds(Short[] inputIds) {
        boolean valid = true;

        List<Short> r3 = new ArrayList<>(6);
        for (int j=0; j<inputIds.length-2; j++) {
            r3.add(inputIds[j]);
        }
        for (int j=0; j<4; j++) {
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
