package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.BlockNames;

public class TorchesLInitializer {

    public static Map<BlockNames, BlockNames> init() {
        Map<BlockNames, BlockNames> torchesL = new HashMap<>();

        torchesL.put(BlockNames.WOODEN_TORCH, BlockNames.WOODEN_TORCH_LEFT_WALL);
        torchesL.put(BlockNames.COAL_TORCH, BlockNames.COAL_TORCH_LEFT_WALL);
        torchesL.put(BlockNames.LUMENSTONE_TORCH, BlockNames.LUMENSTONE_TORCH_LEFT_WALL);
        torchesL.put(BlockNames.ZYTHIUM_TORCH, BlockNames.ZYTHIUM_TORCH_LEFT_WALL);
        torchesL.put(BlockNames.LEVER, BlockNames.LEVER_LEFT_WALL);
        torchesL.put(BlockNames.LEVER_ON, BlockNames.LEVER_LEFT_WALL_ON);
        torchesL.put(BlockNames.BUTTON_LEFT, BlockNames.BUTTON_LEFT);
        torchesL.put(BlockNames.BUTTON_LEFT_ON, BlockNames.BUTTON_LEFT_ON);

        return torchesL;
    }
}
