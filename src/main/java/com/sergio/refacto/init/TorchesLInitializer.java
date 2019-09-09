package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Blocks;

public class TorchesLInitializer {

    public static Map<Blocks, Blocks> init() {
        Map<Blocks, Blocks> torchesL = new HashMap<>();

        torchesL.put(Blocks.WOODEN_TORCH, Blocks.WOODEN_TORCH_LEFT_WALL);
        torchesL.put(Blocks.COAL_TORCH, Blocks.COAL_TORCH_LEFT_WALL);
        torchesL.put(Blocks.LUMENSTONE_TORCH, Blocks.LUMENSTONE_TORCH_LEFT_WALL);
        torchesL.put(Blocks.ZYTHIUM_TORCH, Blocks.ZYTHIUM_TORCH_LEFT_WALL);
        torchesL.put(Blocks.LEVER, Blocks.LEVER_LEFT_WALL);
        torchesL.put(Blocks.LEVER_ON, Blocks.LEVER_LEFT_WALL_ON);
        torchesL.put(Blocks.BUTTON_LEFT, Blocks.BUTTON_LEFT);
        torchesL.put(Blocks.BUTTON_LEFT_ON, Blocks.BUTTON_LEFT_ON);

        return torchesL;
    }
}
