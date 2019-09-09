package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;

public class FuelsInitializer {

    public static Map<Items, Double> init() {
        Map<Items, Double> fuels = new HashMap<>();

        fuels.put(Items.WOOD, 0.01);
        fuels.put(Items.COAL, 0.001);
        fuels.put(Items.BARK, 0.02);
        fuels.put(Items.VARNISHED_WOOD, 0.01);
        fuels.put(Items.CHARCOAL, 0.0035);
        fuels.put(Items.WORKBENCH, 0.0025);
        fuels.put(Items.WOODEN_CHEST, 0.00125);
        fuels.put(Items.WOODEN_TORCH, 0.02);
        fuels.put(Items.COAL_TORCH, 0.011);
        fuels.put(Items.SUNFLOWER_SEEDS, 0.02);
        fuels.put(Items.MOONFLOWER_SEEDS, 0.02);
        fuels.put(Items.DRYWEED_SEEDS, 0.02);
        fuels.put(Items.GREENLEAF_SEEDS, 0.02);
        fuels.put(Items.FROSTLEAF_SEEDS, 0.02);
        fuels.put(Items.CAVEROOT_SEEDS, 0.02);
        fuels.put(Items.SKYBLOSSOM_SEEDS, 0.0035);
        fuels.put(Items.VOID_ROT_SEEDS, 0.02);
        fuels.put(Items.MARSHLEAF_SEEDS, 0.02);
        fuels.put(Items.SUNFLOWER, 0.01);
        fuels.put(Items.MOONFLOWER, 0.01);
        fuels.put(Items.DRYWEED, 0.01);
        fuels.put(Items.GREENLEAF, 0.01);
        fuels.put(Items.FROSTLEAF, 0.01);
        fuels.put(Items.CAVEROOT, 0.01);
        fuels.put(Items.SKYBLOSSOM, 0.01);
        fuels.put(Items.VOID_ROT, 0.01);
        fuels.put(Items.MARSHLEAF, 0.01);
        fuels.put(Items.BLUE_GOO, 0.0035);
        fuels.put(Items.GREEN_GOO, 0.0035);
        fuels.put(Items.RED_GOO, 0.0035);
        fuels.put(Items.YELLOW_GOO, 0.0035);
        fuels.put(Items.BLACK_GOO, 0.0035);
        fuels.put(Items.WHITE_GOO, 0.0035);
        fuels.put(Items.WOODEN_PICK, 0.002);
        fuels.put(Items.WOODEN_AXE, 0.002);
        fuels.put(Items.WOODEN_SWORD, 0.00333);
        
        return fuels;
    }
}
