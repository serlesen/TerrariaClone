package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class TorchesRInitializer {

    public static Map<BlockNames, BlockNames> init() {
        Map<BlockNames, BlockNames> torchesR = new HashMap<>();

        torchesR.put(BlockNames.WOODEN_TORCH, BlockNames.WOODEN_TORCH_RIGHT_WALL);
        torchesR.put(BlockNames.COAL_TORCH, BlockNames.COAL_TORCH_RIGHT_WALL);
        torchesR.put(BlockNames.LUMENSTONE_TORCH, BlockNames.LUMENSTONE_TORCH_RIGHT_WALL);
        torchesR.put(BlockNames.ZYTHIUM_TORCH, BlockNames.ZYTHIUM_TORCH_RIGHT_WALL);
        torchesR.put(BlockNames.LEVER, BlockNames.LEVER_RIGHT_WALL);
        torchesR.put(BlockNames.LEVER_ON, BlockNames.LEVER_RIGHT_WALL_ON);
        torchesR.put(BlockNames.BUTTON_LEFT, BlockNames.BUTTON_RIGHT);

        return torchesR;
    }
}
