package com.sergio.refacto.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	AIR(0, "air", 0, 0, 0.001, false, null, Collections.emptyList()),
    DIRT(1, "dirt", 1, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    STONE(2, "stone", 2, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    COPPER_ORE(3, "copper_ore", 3, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    IRON_ORE(4, "iron_ore", 4, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SILVER_ORE(5, "silver_ore", 5, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    GOLD_ORE(6, "gold_ore", 6, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    WOOD(7, "wood", 15, 0, 0.001, false, "wood", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    WORKBENCH(8, "workbench", 20, 0, 0.001, false, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    WOODEN_CHEST(9, "wooden_chest", 21, 0, 0.001, false, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    STONE_CHEST(10, "stone_chest", 22, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    COPPER_CHEST(11, "copper_chest", 23, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    IRON_CHEST(12, "iron_chest", 24, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SILVER_CHEST(13, "silver_chest", 25, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    GOLD_CHEST(14, "gold_chest", 26, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    TREE(15, "tree", 15, 0, 0.001, true, "tree", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    LEAVES(16, "leaves", 0, 0, 0.001, false, "default", Collections.emptyList()),
    FURNACE(17, "furnace", 27, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    COAL(18, "coal", 28, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LUMENSTONE(19, "lumenstone", 34, 21, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    WOODEN_TORCH(20, "wooden_torch", 35, 15, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    COAL_TORCH(21, "coal_torch", 36, 18, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    LUMENSTONE_TORCH(22, "lumenstone_torch", 37, 21, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    FURNACE_ON(23, "furnace_on", 27, 15, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    WOODEN_TORCH_LEFT_WALL(24, "wooden_torch_left", 35, 15, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    WOODEN_TORCH_RIGHT_WALL(25, "wooden_torch_right", 35, 15, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    COAL_TORCH_LEFT_WALL(26, "coal_torch_left", 36, 18, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    COAL_TORCH_RIGHT_WALL(27, "coal_torch_right", 36, 18, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    LUMENSTONE_TORCH_LEFT_WALL(28, "lumenstone_torch_left", 37, 21, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    LUMENSTONE_TORCH_RIGHT_WALL(29, "lumenstone_torch_right", 37, 21, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    TREE_ROOT(30, "tree_root", 0, 0, 0.001, false, "tree_root", Arrays.asList()),
    ZINC_ORE(31, "zinc_ore", 38, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    RHYMESTONE_ORE(32, "rhymestone_ore", 39, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    OBDURITE_ORE(33, "obdurite_ore", 40, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ALUMINUM_ORE(34, "aluminum_ore", 41, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LEAD_ORE(35, "lead_ore", 42, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    URANIUM_ORE(36, "uranium_ore", 43, 15, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_ORE(37, "zythium_ore", 44, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_ORE_ON(38, "zythium_ore_on", 44, 18, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SILICON_ORE(39, "silicon_ore", 45, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    IRRADIUM_ORE(40, "irradium_ore", 46, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    NULLSTONE(41, "nullstone", 47, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    MELTSTONE(42, "meltstone", 48, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SKYSTONE(43, "skystone", 49, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    MAGNETITE_ORE(44, "magnetite_ore", 50, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SAND(45, "sand", 74, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SNOW(46, "snow", 75, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    GLASS(47, "glass", 0, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    SUNFLOWER_STAGE_1(48, "sunflower_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    SUNFLOWER_STAGE_2(49, "sunflower_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    SUNFLOWER_STAGE_3(50, "sunflower_stage_3", 78, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    MOONFLOWER_STAGE_1(51, "moonflower_stage_1", 0, 15, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    MOONFLOWER_STAGE_2(52, "moonflower_stage_2", 0, 15, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    MOONFLOWER_STAGE_3(53, "moonflower_stage_3", 80, 115, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    DRYWEED_STAGE_1(54, "dryweed_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    DRYWEED_STAGE_2(55, "dryweed_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    DRYWEED_STAGE_3(56, "dryweed_stage_3", 82, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    GREENLEAF_STAGE_1(57, "greenleaf_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    GREENLEAF_STAGE_2(58, "greenleaf_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    GREENLEAF_STAGE_3(59, "greenleaf_stage_3", 84, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    FROSTLEAF_STAGE_1(60, "frostleaf_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    FROSTLEAF_STAGE_2(61, "frostleaf_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    FROSTLEAF_STAGE_3(62, "frostleaf_stage_3", 86, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    CAVEROOT_STAGE_1(63, "caveroot_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    CAVEROOT_STAGE_2(64, "caveroot_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    CAVEROOT_STAGE_3(65, "caveroot_stage_3", 88, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    SKYBLOSSOM_STAGE_1(66, "skyblossom_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    SKYBLOSSOM_STAGE_2(67, "skyblossom_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    SKYBLOSSOM_STAGE_3(68, "skyblossom_stage_3", 90, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    VOID_ROT_STAGE_1(69, "void_rot_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    VOID_ROT_STAGE_2(70, "void_rot_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    VOID_ROT_STAGE_3(71, "void_rot_stage_3", 92, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    GRASS(72, "grass", 1, 0, 0.001, true, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    JUNGLE_GRASS(73, "jungle_grass", 1, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SWAMP_GRASS(74, "swamp_grass", 93, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    MUD(75, "mud", 93, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    SANDSTONE(76, "sandstone", 94, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    MARSHLEAF_STAGE_1(77, "marshleaf_stage_1", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    MARSHLEAF_STAGE_2(78, "marshleaf_stage_2", 0, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    MARSHLEAF_STAGE_3(79, "marshleaf_stage_3", 96, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58)),
    ZINC_CHEST(80, "zinc_chest", 151, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    RHYMESTONE_CHEST(81, "rhymestone_chest", 152, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    OBDURITE_CHEST(82, "obdurite_chest", 153, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    TREE_NO_BARK(83, "tree_no_bark", 15, 0, 0.001, true, "tree", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    COBBLESTONE(84, "cobblestone", 161, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    CHISELED_STONE(85, "chiseled_stone", 162, 0, -0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    CHISELED_COBBLESTONE(86, "chiseled_cobblestone", 163, 0, -0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    STONE_BRICKS(87, "stone_bricks", 164, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    CLAY(88, "clay", 165, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    CLAY_BRICKS(89, "clay_bricks", 166, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    VARNISHED_WOOD(90, "varnished_wood", 168, 0, 0.001, false, "wood", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    DIRT_TRANSPARENT(91, "dirt_trans", 1, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    MAGNETITE_ORE_TRANSPARENT(92, "magnetite_ore_trans", 50, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    GRASS_TRANSPARENT(93, "grass_trans", 1, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157)),
    ZYTHIUM_WIRE(94, "zythium_wire_0", 175, 0, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_WIRE_1_POWER(95, "zythium_wire_1", 175, 6, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_WIRE_2_POWER(96, "zythium_wire_2", 175, 7, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_WIRE_3_POWER(97, "zythium_wire_3", 175, 8, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_WIRE_4_POWER(98, "zythium_wire_4", 175, 9, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_WIRE_5_POWER(99, "zythium_wire_5", 175, 10, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_TORCH(100, "zythium_torch", 176, 12, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_TORCH_LEFT_WALL(101, "zythium_torch_left", 176, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_TORCH_RIGHT_WALL(102, "zythium_torch_right", 176, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173)),
    ZYTHIUM_LAMP(103, "zythium_lamp", 177, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_LAMP_ON(104, "zythium_lamp_on", 177, 12, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LEVER(105, "lever", 178, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LEVER_ON(106, "lever_on", 178, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LEVER_LEFT_WALL(107, "lever_left", 178, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LEVER_LEFT_WALL_ON(108, "lever_left_on", 178, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LEVER_RIGHT_WALL(109, "lever_right", 178, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    LEVER_RIGHT_WALL_ON(110, "lever_right_on", 178, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_RIGHT(111, "zythium_amplifier_right", 180, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_DOWN(112, "zythium_amplifier_down", 180, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_LEFT(113, "zythium_amplifier_left", 180, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_UP(114, "zythium_amplifier_up", 180, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_RIGHT_ON(115, "zythium_amplifier_right_on", 180, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_DOWN_ON(116, "zythium_amplifier_down_on", 180, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_LEFT_ON(117, "zythium_amplifier_left_on", 180, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_AMPLIFIER_UP_ON(118, "zythium_amplifier_up_on", 180, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_RIGHT(119, "zythium_inverter_right", 181, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_DOWN(120, "zythium_inverter_down", 181, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_LEFT(121, "zythium_inverter_left", 181, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_UP(122, "zythium_inverter_up", 181, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_RIGHT_ON(123, "zythium_inverter_right_on", 181, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_DOWN_ON(124, "zythium_inverter_down_on", 181, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_LEFT_ON(125, "zythium_inverter_left_on", 181, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_INVERTER_UP_ON(126, "zythium_inverter_up_on", 181, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    BUTTON_LEFT(127, "button_left", 182, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    BUTTON_LEFT_ON(128, "button_left_on", 182, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    BUTTON_RIGHT(129, "button_right", 182, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    BUTTON_RIGHT_ON(130, "button_right_on", 182, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    WOODEN_PRESSURE_PLATE(131, "wooden_pressure_plate", 183, 0, 0.001, true, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    WOODEN_PRESSURE_PLATE_ON(132, "wooden_pressure_plate_on", 183, 0, 0.001, true, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173)),
    STONE_PRESSURE_PLATE(133, "stone_pressure_plate", 184, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    STONE_PRESSURE_PLATE_ON(134, "stone_pressure_plate_on", 184, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_PRESSURE_PLATE(135, "zythium_pressure_plate", 185, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_PRESSURE_PLATE_ON(136, "zythium_pressure_plate_on", 185, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_RIGHT(137, "zythium_delayer_1_right", 186, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_DOWN(138, "zythium_delayer_1_down", 186, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_LEFT(139, "zythium_delayer_1_left", 186, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_UP(140, "zythium_delayer_1_up", 186, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON(141, "zythium_delayer_1_right_on", 186, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_DOWN_ON(142, "zythium_delayer_1_down_on", 186, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_LEFT_ON(143, "zythium_delayer_1_left_on", 186, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_1_DELAY_UP_ON(144, "zythium_delayer_1_up_on", 186, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_RIGHT(145, "zythium_delayer_2_right", 187, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_DOWN(146, "zythium_delayer_2_down", 187, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_LEFT(147, "zythium_delayer_2_left", 187, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_UP(148, "zythium_delayer_2_up", 187, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON(149, "zythium_delayer_2_right_on", 187, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_DOWN_ON(150, "zythium_delayer_2_down_on", 187, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_LEFT_ON(151, "zythium_delayer_2_left_on", 187, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_2_DELAY_UP_ON(152, "zythium_delayer_2_up_on", 187, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_RIGHT(153, "zythium_delayer_4_right", 188, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_DOWN(154, "zythium_delayer_4_down", 188, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_LEFT(155, "zythium_delayer_4_left", 188, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_UP(156, "zythium_delayer_4_up", 188, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON(157, "zythium_delayer_4_right_on", 188, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_DOWN_ON(158, "zythium_delayer_4_down_on", 188, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_LEFT_ON(159, "zythium_delayer_4_left_on", 188, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_4_DELAY_UP_ON(160, "zythium_delayer_4_up_on", 188, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_RIGHT(161, "zythium_delayer_8_right", 189, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_DOWN(162, "zythium_delayer_8_down", 189, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_LEFT(163, "zythium_delayer_8_left", 189, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_UP(164, "zythium_delayer_8_up", 189, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON(165, "zythium_delayer_8_right_on", 189, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_DOWN_ON(166, "zythium_delayer_8_down_on", 189, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_LEFT_ON(167, "zythium_delayer_8_left_on", 189, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172)),
    ZYTHIUM_DELAYER_8_DELAY_UP_ON(168, "zythium_delayer_8_up_on", 189, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172));

    int index;
    String fileName;
    int drops;
    int lights;
    double fSpeed;
    boolean gSupport;
    String outline;
    List<Integer> tools;

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
