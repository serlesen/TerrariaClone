package com.sergio.refacto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ItemType {
    ARMOR(4, 4),
    CIC(5, 4),
    COPPER_CHEST(20, 20),
    FURNACE(4, 4),
    GOLD_CHEST(42, 42),
    IRON_CHEST(28, 28),
    OBDURITE_CHEST(100, 100),
    RHYMESTONE_CHEST(72, 72),
    SILVER_CHEST(35, 35),
    STONE_CHEST(15, 15),
    WORKBENCH(10, 9),
    WOODEN_CHEST(9, 9),
    ZINC_CHEST(56, 56);

    int itemCollectionSize;
    int itemCollectionIterable;
}
