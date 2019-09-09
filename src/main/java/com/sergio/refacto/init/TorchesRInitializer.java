package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Blocks;

public class TorchesRInitializer {

    public static Map<Blocks, Blocks> init() {
        Map<Blocks, Blocks> torchesR = new HashMap<>();

        torchesR.put(Blocks.WOODEN_TORCH, Blocks.WOODEN_TORCH_RIGHT_WALL);
        torchesR.put(Blocks.COAL_TORCH, Blocks.COAL_TORCH_RIGHT_WALL);
        torchesR.put(Blocks.LUMENSTONE_TORCH, Blocks.LUMENSTONE_TORCH_RIGHT_WALL);
        torchesR.put(Blocks.ZYTHIUM_TORCH, Blocks.ZYTHIUM_TORCH_RIGHT_WALL);
        torchesR.put(Blocks.LEVER, Blocks.LEVER_RIGHT_WALL);
        torchesR.put(Blocks.LEVER_ON, Blocks.LEVER_RIGHT_WALL_ON);
        torchesR.put(Blocks.BUTTON_LEFT, Blocks.BUTTON_RIGHT);

        return torchesR;
    }
}
