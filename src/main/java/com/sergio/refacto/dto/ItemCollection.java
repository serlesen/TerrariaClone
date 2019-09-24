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
    short[] nums, durs;
    Items[] items;
    transient BufferedImage image;
    double FUELP = 0;
    double SMELTP = 0;
    boolean furnaceOn = false;
    int selection;

    public ItemCollection(ItemType type, Items[] items, short[] nums, short[] durs) {
        this.type = type;
        this.items = items;
        this.nums = nums;
        this.durs = durs;
    }

    public ItemCollection(ItemType type) {
        Items[] list1 = new Items[type.getItemCollectionSize()];
        short[] list2 = new short[type.getItemCollectionSize()];
        short[] list3 = new short[type.getItemCollectionSize()];
        for (int i = 0; i < type.getItemCollectionSize(); i++) {
            list1[i] = Items.EMPTY;
            list2[i] = 0;
            list3[i] = 0;
        }
        this.type = type;
        this.items = list1;
        this.nums = list2;
        this.durs = list3;
    }

    public ItemCollection(ItemCollection ic) {
        this.type = ic.type;
        this.items = ic.items;
        this.nums = ic.nums;
        this.durs = ic.durs;
    }

    public boolean areIdsEquals(Items[] inputValues) {
        boolean valid = true;

        for (int i = 0; i < inputValues.length; i++) {
            if (items[i] != inputValues[i]) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public boolean areInvalidIds(Items[] inputValues) {
        boolean valid = true;

        List<Items> r3 = new ArrayList<>(6);
        for (int j = 0; j < inputValues.length - 2; j++) {
            r3.add(inputValues[j]);
        }
        for (int j = 0; j < inputValues.length; j++) {
            if (!r3.contains(items[j])) {
                valid = false;
                break;
            }
            else {
                r3.remove(items[j]);
            }
        }

        return valid;
    }
}
