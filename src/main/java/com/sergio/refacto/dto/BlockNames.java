package com.sergio.refacto.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 0    Air
 * 1    Dirt
 * 2    Stone
 * 3    Copper Ore
 * 4    Iron Ore
 * 5    Silver Ore
 * 6    Gold Ore
 * 7    Wood
 * 8    Workbench
 * 9    Wooden Chest
 * 10    Stone Chest
 * 11    Copper Chest
 * 12    Iron Chest
 * 13    Silver Chest
 * 14    Gold Chest
 * 15    Tree
 * 16    Leaves
 * 17    Furnace
 * 18    Coal
 * 19    Lumenstone
 * 20    Wooden Torch
 * 21    Coal Torch
 * 22    Lumenstone Torch
 * 23    Furnace (on)
 * 24    Wooden Torch (left wall)
 * 25    Wooden Torch (right wall)
 * 26    Coal Torch (left wall)
 * 27    Coal Torch (right wall)
 * 28    Lumenstone Torch (left wall)
 * 29    Lumenstone Torch (right wall)
 * 30    Tree (root)
 * 31    Zinc Ore
 * 32    Rhymestone Ore
 * 33    Obdurite Ore
 * 34    Aluminum Ore
 * 35    Lead Ore
 * 36    Uranium Ore
 * 37    Zythium Ore
 * 38    Zythium Ore (on)
 * 39    Silicon Ore
 * 40    Irradium Ore
 * 41    Nullstone
 * 42    Meltstone
 * 43    Skystone
 * 44    Magnetite Ore
 * 45    Sand
 * 46    Snow
 * 47    Glass
 * 48    Sunflower (stage 1)
 * 49    Sunflower (stage 2)
 * 50    Sunflower (stage 3)
 * 51    Moonflower (stage 1)
 * 52    Moonflower (stage 2)
 * 53    Moonflower (stage 3)
 * 54    Dryweed (stage 1)
 * 55    Dryweed (stage 2)
 * 56    Dryweed (stage 3)
 * 57    Greenleaf (stage 1)
 * 58    Greenleaf (stage 2)
 * 59    Greenleaf (stage 3)
 * 60    Frostleaf (stage 1)
 * 61    Frostleaf (stage 2)
 * 62    Frostleaf (stage 3)
 * 63    Caveroot (stage 1)
 * 64    Caveroot (stage 2)
 * 65    Caveroot (stage 3)
 * 66    Skyblossom (stage 1)
 * 67    Skyblossom (stage 2)
 * 68    Skyblossom (stage 3)
 * 69    Void Rot (stage 1)
 * 70    Void Rot (stage 2)
 * 71    Void Rot (stage 3)
 * 72    Grass
 * 73    Jungle Grass
 * 74    Swamp Grass
 * 75    Mud
 * 76    Sandstone
 * 77    Marshleaf (stage 1)
 * 78    Marshleaf (stage 2)
 * 79    Marshleaf (stage 3)
 * 80    Zinc Chest
 * 81    Rhymestone Chest
 * 82    Obdurite Chest
 * 83    Tree (no bark)
 * 84    Cobblestone
 * 85    Chiseled Stone
 * 86    Chiseled Cobblestone
 * 87    Stone Bricks
 * 88    Clay
 * 89    Clay Bricks
 * 90    Varnished Wood
 * 91    Dirt (transparent)
 * 92    Magnetite Ore (transparent)
 * 93    Grass (transparent)
 * 94    Zythium Wire
 * 95    Zythium Wire (1 power)
 * 96    Zythium Wire (2 power)
 * 97    Zythium Wire (3 power)
 * 98    Zythium Wire (4 power)
 * 99    Zythium Wire (5 power)
 * 100    Zythium Torch
 * 101    Zythium Torch (left wall)
 * 102    Zythium Torch (right wall)
 * 103    Zythium Lamp
 * 104    Zythium Lamp (on)
 * 105    Lever
 * 106    Lever (on)
 * 107    Lever (left wall)
 * 108    Lever (left wall, on)
 * 109    Lever (right wall)
 * 110    Lever (right wall, on)
 * 111    Zythium Amplifier (right)
 * 112    Zythium Amplifier (down)
 * 113    Zythium Amplifier (left)
 * 114    Zythium Amplifier (up)
 * 115    Zythium Amplifier (right, on)
 * 116    Zythium Amplifier (down, on)
 * 117    Zythium Amplifier (left, on)
 * 118    Zythium Amplifier (up, on)
 * 119    Zythium Inverter (right)
 * 120    Zythium Inverter (down)
 * 121    Zythium Inverter (left)
 * 122    Zythium Inverter (up)
 * 123    Zythium Inverter (right, on)
 * 124    Zythium Inverter (down, on)
 * 125    Zythium Inverter (left, on)
 * 126    Zythium Inverter (up, on)
 * 127    Button (left)
 * 128    Button (left, on)
 * 129    Button (right)
 * 130    Button (right, on)
 * 131    Wooden Pressure Plate
 * 132    Wooden Pressure Plate (on)
 * 133    Stone Pressure Plate
 * 134    Stone Pressure Plate (on)
 * 135    Zythium Pressure Plate
 * 136    Zythium Pressure Plate (on)
 * 137    Zythium Delayer (1 delay, right)
 * 138    Zythium Delayer (1 delay, down)
 * 139    Zythium Delayer (1 delay, left)
 * 140    Zythium Delayer (1 delay, up)
 * 141    Zythium Delayer (1 delay, right, on)
 * 142    Zythium Delayer (1 delay, down, on)
 * 143    Zythium Delayer (1 delay, left, on)
 * 144    Zythium Delayer (1 delay, up, on)
 * 145    Zythium Delayer (2 delay, right)
 * 146    Zythium Delayer (2 delay, down)
 * 147    Zythium Delayer (2 delay, left)
 * 148    Zythium Delayer (2 delay, up)
 * 149    Zythium Delayer (2 delay, right, on)
 * 150    Zythium Delayer (2 delay, down, on)
 * 151    Zythium Delayer (2 delay, left, on)
 * 152    Zythium Delayer (2 delay, up, on)
 * 153    Zythium Delayer (4 delay, right)
 * 154    Zythium Delayer (4 delay, down)
 * 155    Zythium Delayer (4 delay, left)
 * 156    Zythium Delayer (4 delay, up)
 * 157    Zythium Delayer (4 delay, right, on)
 * 158    Zythium Delayer (4 delay, down, on)
 * 159    Zythium Delayer (4 delay, left, on)
 * 160    Zythium Delayer (4 delay, up, on)
 * 161    Zythium Delayer (8 delay, right)
 * 162    Zythium Delayer (8 delay, down)
 * 163    Zythium Delayer (8 delay, left)
 * 164    Zythium Delayer (8 delay, up)
 * 165    Zythium Delayer (8 delay, right, on)
 * 166    Zythium Delayer (8 delay, down, on)
 * 167    Zythium Delayer (8 delay, left, on)
 * 168    Zythium Delayer (8 delay, up, on)
 */
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum BlockNames {
    AIR(0, "air"),
    DIRT(1, "dirt"),
    STONE(2, "stone"),
    COPPER_ORE(3, "copper_ore"),
    IRON_ORE(4, "iron_ore"),
    SILVER_ORE(5, "silver_ore"),
    GOLD_ORE(6, "gold_ore"),
    WOOD(7, "wood"),
    WORKBENCH(8, "workbench"),
    WOODEN_CHEST(9, "wooden_chest"),
    STONE_CHEST(10, "stone_chest"),
    COPPER_CHEST(11, "copper_chest"),
    IRON_CHEST(12, "iron_chest"),
    SILVER_CHEST(13, "silver_chest"),
    GOLD_CHEST(14, "gold_chest"),
    TREE(15, "tree"),
    LEAVES(16, "leaves"),
    FURNACE(17, "furnace"),
    COAL(18, "coal"),
    LUMENSTONE(19, "lumenstone"),
    WOODEN_TORCH(20, "wooden_torch"),
    COAL_TORCH(21, "coal_torch"),
    LUMENSTONE_TORCH(22, "lumenstone_torch"),
    FURNACE_ON(23, "furnace_on"),
    WOODEN_TORCH_LEFT_WALL(24, "wooden_torch_left"),
    WOODEN_TORCH_RIGHT_WALL(25, "wooden_torch_right"),
    COAL_TORCH_LEFT_WALL(26, "coal_torch_left"),
    COAL_TORCH_RIGHT_WALL(27, "coal_torch_right"),
    LUMENSTONE_TORCH_LEFT_WALL(28, "lumenstone_torch_left"),
    LUMENSTONE_TORCH_RIGHT_WALL(29, "lumenstone_torch_right"),
    TREE_ROOT(30, "tree_root"),
    ZINC_ORE(31, "zinc_ore"),
    RHYMESTONE_ORE(32, "rhymestone_ore"),
    OBDURITE_ORE(33, "obdurite_ore"),
    ALUMINUM_ORE(34, "aluminum_ore"),
    LEAD_ORE(35, "lead_ore"),
    URANIUM_ORE(36, "uranium_ore"),
    ZYTHIUM_ORE(37, "zythium_ore"),
    ZYTHIUM_ORE_ON(38, "zythium_ore_on"),
    SILICON_ORE(39, "silicon_ore"),
    IRRADIUM_ORE(40, "irradium_ore"),
    NULLSTONE(41, "nullstone"),
    MELTSTONE(42, "meltstone"),
    SKYSTONE(43, "skystone"),
    MAGNETITE_ORE(44, "magnetite_ore"),
    SAND(45, "sand"),
    SNOW(46, "snow"),
    GLASS(47, "glass"),
    SUNFLOWER_STAGE_1(48, "sunflower_stage_1"),
    SUNFLOWER_STAGE_2(49, "sunflower_stage_2"),
    SUNFLOWER_STAGE_3(50, "sunflower_stage_3"),
    MOONFLOWER_STAGE_1(51, "moonflower_stage_1"),
    MOONFLOWER_STAGE_2(52, "moonflower_stage_2"),
    MOONFLOWER_STAGE_3(53, "moonflower_stage_3"),
    DRYWEED_STAGE_1(54, "dryweed_stage_1"),
    DRYWEED_STAGE_2(55, "dryweed_stage_2"),
    DRYWEED_STAGE_3(56, "dryweed_stage_3"),
    GREENLEAF_STAGE_1(57, "greenleaf_stage_1"),
    GREENLEAF_STAGE_2(58, "greenleaf_stage_2"),
    GREENLEAF_STAGE_3(59, "greenleaf_stage_3"),
    FROSTLEAF_STAGE_1(60, "frostleaf_stage_1"),
    FROSTLEAF_STAGE_2(61, "frostleaf_stage_2"),
    FROSTLEAF_STAGE_3(62, "frostleaf_stage_3"),
    CAVEROOT_STAGE_1(63, "caveroot_stage_1"),
    CAVEROOT_STAGE_2(64, "caveroot_stage_2"),
    CAVEROOT_STAGE_3(65, "caveroot_stage_3"),
    SKYBLOSSOM_STAGE_1(66, "skyblossom_stage_1"),
    SKYBLOSSOM_STAGE_2(67, "skyblossom_stage_2"),
    SKYBLOSSOM_STAGE_3(68, "skyblossom_stage_3"),
    VOID_ROT_STAGE_1(69, "void_rot_stage_1"),
    VOID_ROT_STAGE_2(70, "void_rot_stage_2"),
    VOID_ROT_STAGE_3(71, "void_rot_stage_3"),
    GRASS(72, "grass"),
    JUNGLE_GRASS(73, "jungle_grass"),
    SWAMP_GRASS(74, "swamp_grass"),
    MUD(75, "mud"),
    SANDSTONE(76, "sandstone"),
    MARSHLEAF_STAGE_1(77, "marshleaf_stage_1"),
    MARSHLEAF_STAGE_2(78, "marshleaf_stage_2"),
    MARSHLEAF_STAGE_3(79, "marshleaf_stage_3"),
    ZINC_CHEST(80, "zinc_chest"),
    RHYMESTONE_CHEST(81, "rhymestone_chest"),
    OBDURITE_CHEST(82, "obdurite_chest"),
    TREE_NO_BARK(83, "tree_no_bark"),
    COBBLESTONE(84, "cobblestone"),
    CHISELED_STONE(85, "chiseled_stone"),
    CHISELED_COBBLESTONE(86, "chiseled_cobblestone"),
    STONE_BRICKS(87, "stone_bricks"),
    CLAY(88, "clay"),
    CLAY_BRICKS(89, "clay_bricks"),
    VARNISHED_WOOD(90, "varnished_wood"),
    DIRT_TRANSPARENT(91, "dirt_trans"),
    MAGNETITE_ORE_TRANSPARENT(92, "magnetite_ore_trans"),
    GRASS_TRANSPARENT(93, "grass_trans"),
    ZYTHIUM_WIRE(94, "zythium_wire_0"),
    ZYTHIUM_WIRE_1_POWER(95, "zythium_wire_1"),
    ZYTHIUM_WIRE_2_POWER(96, "zythium_wire_2"),
    ZYTHIUM_WIRE_3_POWER(97, "zythium_wire_3"),
    ZYTHIUM_WIRE_4_POWER(98, "zythium_wire_4"),
    ZYTHIUM_WIRE_5_POWER(99, "zythium_wire_5"),
    ZYTHIUM_TORCH(100, "zythium_torch"),
    ZYTHIUM_TORCH_LEFT_WALL(101, "zythium_torch_left"),
    ZYTHIUM_TORCH_RIGHT_WALL(102, "zythium_torch_right"),
    ZYTHIUM_LAMP(103, "zythium_lamp"),
    ZYTHIUM_LAMP_ON(104, "zythium_lamp_on"),
    LEVER(105, "lever"),
    LEVER_ON(106, "lever_on"),
    LEVER_LEFT_WALL(107, "lever_left"),
    LEVER_LEFT_WALL_ON(108, "lever_left_on"),
    LEVER_RIGHT_WALL(109, "lever_right"),
    LEVER_RIGHT_WALL_ON(110, "lever_right_on"),
    ZYTHIUM_AMPLIFIER_RIGHT(111, "zythium_amplifier_right"),
    ZYTHIUM_AMPLIFIER_DOWN(112, "zythium_amplifier_down"),
    ZYTHIUM_AMPLIFIER_LEFT(113, "zythium_amplifier_left"),
    ZYTHIUM_AMPLIFIER_UP(114, "zythium_amplifier_up"),
    ZYTHIUM_AMPLIFIER_RIGHT_ON(115, "zythium_amplifier_right_on"),
    ZYTHIUM_AMPLIFIER_DOWN_ON(116, "zythium_amplifier_down_on"),
    ZYTHIUM_AMPLIFIER_LEFT_ON(117, "zythium_amplifier_left_on"),
    ZYTHIUM_AMPLIFIER_UP_ON(118, "zythium_amplifier_up_on"),
    ZYTHIUM_INVERTER_RIGHT(119, "zythium_inverter_right"),
    ZYTHIUM_INVERTER_DOWN(120, "zythium_inverter_down"),
    ZYTHIUM_INVERTER_LEFT(121, "zythium_inverter_left"),
    ZYTHIUM_INVERTER_UP(122, "zythium_inverter_up"),
    ZYTHIUM_INVERTER_RIGHT_ON(123, "zythium_inverter_right_on"),
    ZYTHIUM_INVERTER_DOWN_ON(124, "zythium_inverter_down_on"),
    ZYTHIUM_INVERTER_LEFT_ON(125, "zythium_inverter_left_on"),
    ZYTHIUM_INVERTER_UP_ON(126, "zythium_inverter_up_on"),
    BUTTON_LEFT(127, "button_left"),
    BUTTON_LEFT_ON(128, "button_left_on"),
    BUTTON_RIGHT(129, "button_right"),
    BUTTON_RIGHT_ON(130, "button_right_on"),
    WOODEN_PRESSURE_PLATE(131, "wooden_pressure_plate"),
    WOODEN_PRESSURE_PLATE_ON(132, "wooden_pressure_plate_on"),
    STONE_PRESSURE_PLATE(133, "stone_pressure_plate"),
    STONE_PRESSURE_PLATE_ON(134, "stone_pressure_plate_on"),
    ZYTHIUM_PRESSURE_PLATE(135, "zythium_pressure_plate"),
    ZYTHIUM_PRESSURE_PLATE_ON(136, "zythium_pressure_plate_on"),
    ZYTHIUM_DELAYER_1_DELAY_RIGHT(137, "zythium_delayer_1_right"),
    ZYTHIUM_DELAYER_1_DELAY_DOWN(138, "zythium_delayer_1_down"),
    ZYTHIUM_DELAYER_1_DELAY_LEFT(139, "zythium_delayer_1_left"),
    ZYTHIUM_DELAYER_1_DELAY_UP(140, "zythium_delayer_1_up"),
    ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON(141, "zythium_delayer_1_right_on"),
    ZYTHIUM_DELAYER_1_DELAY_DOWN_ON(142, "zythium_delayer_1_down_on"),
    ZYTHIUM_DELAYER_1_DELAY_LEFT_ON(143, "zythium_delayer_1_left_on"),
    ZYTHIUM_DELAYER_1_DELAY_UP_ON(144, "zythium_delayer_1_up_on"),
    ZYTHIUM_DELAYER_2_DELAY_RIGHT(145, "zythium_delayer_2_right"),
    ZYTHIUM_DELAYER_2_DELAY_DOWN(146, "zythium_delayer_2_down"),
    ZYTHIUM_DELAYER_2_DELAY_LEFT(147, "zythium_delayer_2_left"),
    ZYTHIUM_DELAYER_2_DELAY_UP(148, "zythium_delayer_2_up"),
    ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON(149, "zythium_delayer_2_right_on"),
    ZYTHIUM_DELAYER_2_DELAY_DOWN_ON(150, "zythium_delayer_2_down_on"),
    ZYTHIUM_DELAYER_2_DELAY_LEFT_ON(151, "zythium_delayer_2_left_on"),
    ZYTHIUM_DELAYER_2_DELAY_UP_ON(152, "zythium_delayer_2_up_on"),
    ZYTHIUM_DELAYER_4_DELAY_RIGHT(153, "zythium_delayer_4_right"),
    ZYTHIUM_DELAYER_4_DELAY_DOWN(154, "zythium_delayer_4_down"),
    ZYTHIUM_DELAYER_4_DELAY_LEFT(155, "zythium_delayer_4_left"),
    ZYTHIUM_DELAYER_4_DELAY_UP(156, "zythium_delayer_4_up"),
    ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON(157, "zythium_delayer_4_right_on"),
    ZYTHIUM_DELAYER_4_DELAY_DOWN_ON(158, "zythium_delayer_4_down_on"),
    ZYTHIUM_DELAYER_4_DELAY_LEFT_ON(159, "zythium_delayer_4_left_on"),
    ZYTHIUM_DELAYER_4_DELAY_UP_ON(160, "zythium_delayer_4_up_on"),
    ZYTHIUM_DELAYER_8_DELAY_RIGHT(161, "zythium_delayer_8_right"),
    ZYTHIUM_DELAYER_8_DELAY_DOWN(162, "zythium_delayer_8_down"),
    ZYTHIUM_DELAYER_8_DELAY_LEFT(163, "zythium_delayer_8_left"),
    ZYTHIUM_DELAYER_8_DELAY_UP(164, "zythium_delayer_8_up"),
    ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON(165, "zythium_delayer_8_right_on"),
    ZYTHIUM_DELAYER_8_DELAY_DOWN_ON(166, "zythium_delayer_8_down_on"),
    ZYTHIUM_DELAYER_8_DELAY_LEFT_ON(167, "zythium_delayer_8_left_on"),
    ZYTHIUM_DELAYER_8_DELAY_UP_ON(168, "zythium_delayer_8_up_on");

    int index;
    String fileName;

    private static final Map<Integer, BlockNames> BLOCK_NAMES_MAP;

    static {
        BLOCK_NAMES_MAP = new HashMap<>();
        for (BlockNames blockNames : BlockNames.values()) {
            BLOCK_NAMES_MAP.put(blockNames.getIndex(), blockNames);
        }
    }

    public static BlockNames findByIndex(int index) {
        return BLOCK_NAMES_MAP.get(index);
    }
}
