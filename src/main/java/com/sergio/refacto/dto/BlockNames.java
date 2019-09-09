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
	AIR(0, "air", Items.EMPTY, 0, 0.001, false, null, Collections.emptyList(), false, false, false, false, false, -1),
    DIRT(1, "dirt", Items.DIRT, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    STONE(2, "stone", Items.STONE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    COPPER_ORE(3, "copper_ore", Items.COPPER_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    IRON_ORE(4, "iron_ore", Items.IRON_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    SILVER_ORE(5, "silver_ore", Items.SILVER_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    GOLD_ORE(6, "gold_ore", Items.GOLD_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    WOOD(7, "wood", Items.WOOD, 0, 0.001, false, "wood", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), true, false, false, false, true, -1),
    WORKBENCH(8, "workbench", Items.WORKBENCH, 0, 0.001, false, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), false, false, false, false, false, -1),
    WOODEN_CHEST(9, "wooden_chest",Items. WOODEN_CHEST, 0, 0.001, false, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), false, false, false, false, false, -1),
    STONE_CHEST(10, "stone_chest", Items.STONE_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    COPPER_CHEST(11, "copper_chest", Items.COPPER_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    IRON_CHEST(12, "iron_chest", Items.IRON_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    SILVER_CHEST(13, "silver_chest", Items.SILVER_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    GOLD_CHEST(14, "gold_chest", Items.GOLD_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    TREE(15, "tree", Items.WOOD, 0, 0.001, true, "tree", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), false, false, false, false, false, -1),
    LEAVES(16, "leaves", Items.EMPTY, 0, 0.001, false, "default", Collections.emptyList(), false, false, false, false, false, -1),
    FURNACE(17, "furnace", Items.FURNACE, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    COAL(18, "coal", Items.COAL, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    LUMENSTONE(19, "lumenstone", Items.LUMENSTONE, 21, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    WOODEN_TORCH(20, "wooden_torch", Items.WOODEN_TORCH, 15, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    COAL_TORCH(21, "coal_torch", Items.COAL_TORCH, 18, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    LUMENSTONE_TORCH(22, "lumenstone_torch", Items.LUMENSTONE_TORCH, 21, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    FURNACE_ON(23, "furnace_on", Items.FURNACE, 15, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    WOODEN_TORCH_LEFT_WALL(24, "wooden_torch_left", Items.WOODEN_TORCH, 15, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    WOODEN_TORCH_RIGHT_WALL(25, "wooden_torch_right", Items.WOODEN_TORCH, 15, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    COAL_TORCH_LEFT_WALL(26, "coal_torch_left", Items.COAL_TORCH, 18, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    COAL_TORCH_RIGHT_WALL(27, "coal_torch_right", Items.COAL_TORCH, 18, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    LUMENSTONE_TORCH_LEFT_WALL(28, "lumenstone_torch_left", Items.LUMENSTONE_TORCH, 21, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    LUMENSTONE_TORCH_RIGHT_WALL(29, "lumenstone_torch_right", Items.LUMENSTONE_TORCH, 21, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    TREE_ROOT(30, "tree_root", Items.EMPTY, 0, 0.001, false, "tree_root", Collections.emptyList(), false, false, false, false, false, -1),
    ZINC_ORE(31, "zinc_ore", Items.ZINC_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    RHYMESTONE_ORE(32, "rhymestone_ore", Items.RHYMESTONE_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    OBDURITE_ORE(33, "obdurite_ore", Items.OBDURITE_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    ALUMINUM_ORE(34, "aluminum_ore", Items.ALUMINUM_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    LEAD_ORE(35, "lead_ore", Items.LEAD_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    URANIUM_ORE(36, "uranium_ore", Items.URANIUM_ORE, 15, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    ZYTHIUM_ORE(37, "zythium_ore", Items.ZYTHIUM_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    ZYTHIUM_ORE_ON(38, "zythium_ore_on", Items.ZYTHIUM_ORE, 18, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    SILICON_ORE(39, "silicon_ore", Items.SILICON_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    IRRADIUM_ORE(40, "irradium_ore", Items.IRRADIUM_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    NULLSTONE(41, "nullstone", Items.NULLSTONE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    MELTSTONE(42, "meltstone", Items.MELTSTONE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    SKYSTONE(43, "skystone", Items.SKYSTONE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    MAGNETITE_ORE(44, "magnetite_ore", Items.MAGNETITE_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    SAND(45, "sand", Items.SAND, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    SNOW(46, "snow", Items.SNOW, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    GLASS(47, "glass", Items.EMPTY, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), true, false, false, false, false, -1),
    SUNFLOWER_STAGE_1(48, "sunflower_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    SUNFLOWER_STAGE_2(49, "sunflower_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    SUNFLOWER_STAGE_3(50, "sunflower_stage_3", Items.SUNFLOWER, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    MOONFLOWER_STAGE_1(51, "moonflower_stage_1", Items.EMPTY, 15, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    MOONFLOWER_STAGE_2(52, "moonflower_stage_2", Items.EMPTY, 15, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    MOONFLOWER_STAGE_3(53, "moonflower_stage_3", Items.MOONFLOWER, 115, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    DRYWEED_STAGE_1(54, "dryweed_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    DRYWEED_STAGE_2(55, "dryweed_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    DRYWEED_STAGE_3(56, "dryweed_stage_3", Items.DRYWEED, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    GREENLEAF_STAGE_1(57, "greenleaf_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    GREENLEAF_STAGE_2(58, "greenleaf_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    GREENLEAF_STAGE_3(59, "greenleaf_stage_3", Items.GREENLEAF, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    FROSTLEAF_STAGE_1(60, "frostleaf_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    FROSTLEAF_STAGE_2(61, "frostleaf_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    FROSTLEAF_STAGE_3(62, "frostleaf_stage_3", Items.FROSTLEAF, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    CAVEROOT_STAGE_1(63, "caveroot_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    CAVEROOT_STAGE_2(64, "caveroot_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    CAVEROOT_STAGE_3(65, "caveroot_stage_3", Items.CAVEROOT, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    SKYBLOSSOM_STAGE_1(66, "skyblossom_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    SKYBLOSSOM_STAGE_2(67, "skyblossom_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    SKYBLOSSOM_STAGE_3(68, "skyblossom_stage_3", Items.SKYBLOSSOM, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    VOID_ROT_STAGE_1(69, "void_rot_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    VOID_ROT_STAGE_2(70, "void_rot_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    VOID_ROT_STAGE_3(71, "void_rot_stage_3", Items.VOID_ROT, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    GRASS(72, "grass", Items.DIRT, 0, 0.001, true, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    JUNGLE_GRASS(73, "jungle_grass", Items.DIRT, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    SWAMP_GRASS(74, "swamp_grass", Items.MUD, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    MUD(75, "mud", Items.MUD, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    SANDSTONE(76, "sandstone", Items.SANDSTONE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    MARSHLEAF_STAGE_1(77, "marshleaf_stage_1", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    MARSHLEAF_STAGE_2(78, "marshleaf_stage_2", Items.EMPTY, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, false, false, -1),
    MARSHLEAF_STAGE_3(79, "marshleaf_stage_3", Items.MARSHLEAF, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58), false, false, false, false, false, -1),
    ZINC_CHEST(80, "zinc_chest", Items.ZINC_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    RHYMESTONE_CHEST(81, "rhymestone_chest", Items.RHYMESTONE_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    OBDURITE_CHEST(82, "obdurite_chest", Items.OBDURITE_CHEST, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    TREE_NO_BARK(83, "tree_no_bark", Items.WOOD, 0, 0.001, true, "tree", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), false, false, false, false, false, -1),
    COBBLESTONE(84, "cobblestone", Items.COBBLESTONE, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    CHISELED_STONE(85, "chiseled_stone", Items.CHISELED_STONE, 0, -0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    CHISELED_COBBLESTONE(86, "chiseled_cobblestone", Items.CHISELED_COBBLESTONE, 0, -0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    STONE_BRICKS(87, "stone_bricks", Items.STONE_BRICKS, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    CLAY(88, "clay", Items.CLAY, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    CLAY_BRICKS(89, "clay_bricks", Items.CLAY_BRICKS, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, true, -1),
    VARNISHED_WOOD(90, "varnished_wood", Items.VARNISHED_WOOD, 0, 0.001, false, "wood", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), true, false, false, false, true, -1),
    DIRT_TRANSPARENT(91, "dirt_trans", Items.DIRT, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, false, -1),
    MAGNETITE_ORE_TRANSPARENT(92, "magnetite_ore_trans", Items.MAGNETITE_ORE, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, false, false, -1),
    GRASS_TRANSPARENT(93, "grass_trans", Items.DIRT, 0, 0.001, false, "default", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157), true, false, false, false, false, -1),
    ZYTHIUM_WIRE(94, "zythium_wire_0", Items.ZYTHIUM_WIRE, 0, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, true, false, 0.5),
    ZYTHIUM_WIRE_1_POWER(95, "zythium_wire_1", Items.ZYTHIUM_WIRE, 6, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, true, false, 0.5),
    ZYTHIUM_WIRE_2_POWER(96, "zythium_wire_2", Items.ZYTHIUM_WIRE, 7, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, true, false, 0.5),
    ZYTHIUM_WIRE_3_POWER(97, "zythium_wire_3", Items.ZYTHIUM_WIRE, 8, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, true, false, 0.5),
    ZYTHIUM_WIRE_4_POWER(98, "zythium_wire_4", Items.ZYTHIUM_WIRE, 9, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, true, false, 0.5),
    ZYTHIUM_WIRE_5_POWER(99, "zythium_wire_5", Items.ZYTHIUM_WIRE, 10, 0.001, false, "wire", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, false, true, false, 0.5),
    ZYTHIUM_TORCH(100, "zythium_torch", Items.ZYTHIUM_TORCH, 12, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, true, false, false, 0),
    ZYTHIUM_TORCH_LEFT_WALL(101, "zythium_torch_left", Items.ZYTHIUM_TORCH, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, true, false, false, 0),
    ZYTHIUM_TORCH_RIGHT_WALL(102, "zythium_torch_right", Items.ZYTHIUM_TORCH, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 51, 52, 54, 55, 57, 58, 145, 146, 148, 149, 154, 155, 157, 158, 169, 170, 172, 173), false, false, true, false, false, 0),
    ZYTHIUM_LAMP(103, "zythium_lamp", Items.ZYTHIUM_LAMP, 0, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, true, true, -1),
    ZYTHIUM_LAMP_ON(104, "zythium_lamp_on", Items.ZYTHIUM_LAMP, 12, 0.001, false, "square", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), true, false, false, true, true, -1),
    LEVER(105, "lever", Items.LEVER, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    LEVER_ON(106, "lever_on", Items.LEVER, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, false, false, 0),
    LEVER_LEFT_WALL(107, "lever_left", Items.LEVER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    LEVER_LEFT_WALL_ON(108, "lever_left_on", Items.LEVER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, false, false, 0),
    LEVER_RIGHT_WALL(109, "lever_right", Items.LEVER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    LEVER_RIGHT_WALL_ON(110, "lever_right_on", Items.LEVER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, false, false, 0),
    ZYTHIUM_AMPLIFIER_RIGHT(111, "zythium_amplifier_right", Items.ZYTHIUM_AMPLIFIER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    ZYTHIUM_AMPLIFIER_DOWN(112, "zythium_amplifier_down", Items.ZYTHIUM_AMPLIFIER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    ZYTHIUM_AMPLIFIER_LEFT(113, "zythium_amplifier_left", Items.ZYTHIUM_AMPLIFIER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    ZYTHIUM_AMPLIFIER_UP(114, "zythium_amplifier_up", Items.ZYTHIUM_AMPLIFIER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    ZYTHIUM_AMPLIFIER_RIGHT_ON(115, "zythium_amplifier_right_on", Items.ZYTHIUM_AMPLIFIER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_AMPLIFIER_DOWN_ON(116, "zythium_amplifier_down_on", Items.ZYTHIUM_AMPLIFIER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_AMPLIFIER_LEFT_ON(117, "zythium_amplifier_left_on", Items.ZYTHIUM_AMPLIFIER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_AMPLIFIER_UP_ON(118, "zythium_amplifier_up_on", Items.ZYTHIUM_AMPLIFIER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_INVERTER_RIGHT(119, "zythium_inverter_right", Items.ZYTHIUM_INVERTER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_INVERTER_DOWN(120, "zythium_inverter_down", Items.ZYTHIUM_INVERTER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_INVERTER_LEFT(121, "zythium_inverter_left", Items.ZYTHIUM_INVERTER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_INVERTER_UP(122, "zythium_inverter_up", Items.ZYTHIUM_INVERTER, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_INVERTER_RIGHT_ON(123, "zythium_inverter_right_on", Items.ZYTHIUM_INVERTER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    ZYTHIUM_INVERTER_DOWN_ON(124, "zythium_inverter_down_on", Items.ZYTHIUM_INVERTER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    ZYTHIUM_INVERTER_LEFT_ON(125, "zythium_inverter_left_on", Items.ZYTHIUM_INVERTER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    ZYTHIUM_INVERTER_UP_ON(126, "zythium_inverter_up_on", Items.ZYTHIUM_INVERTER, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, -1),
    BUTTON_LEFT(127, "button_left", Items.BUTTON, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    BUTTON_LEFT_ON(128, "button_left_on", Items.BUTTON, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, false, false, 0),
    BUTTON_RIGHT(129, "button_right", Items.BUTTON, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    BUTTON_RIGHT_ON(130, "button_right_on", Items.BUTTON, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, false, false, 0),
    WOODEN_PRESSURE_PLATE(131, "wooden_pressure_plate", Items.WOODEN_PRESSURE_PLATE, 0, 0.001, true, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), false, false, false, false, false, -1),
    WOODEN_PRESSURE_PLATE_ON(132, "wooden_pressure_plate_on", Items.WOODEN_PRESSURE_PLATE, 0, 0.001, true, "none", Arrays.asList(11, 12, 13, 14, 52, 55, 58, 146, 149, 155, 158, 170, 173), false, false, true, false, false, 0),
    STONE_PRESSURE_PLATE(133, "stone_pressure_plate", Items.STONE_PRESSURE_PLATE, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    STONE_PRESSURE_PLATE_ON(134, "stone_pressure_plate_on", Items.STONE_PRESSURE_PLATE, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, false, false, 0),
    ZYTHIUM_PRESSURE_PLATE(135, "zythium_pressure_plate", Items.ZYTHIUM_PRESSURE_PLATE, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, false, false, -1),
    ZYTHIUM_PRESSURE_PLATE_ON(136, "zythium_pressure_plate_on", Items.ZYTHIUM_PRESSURE_PLATE, 0, 0.001, true, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, false, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_RIGHT(137, "zythium_delayer_1_right", Items.ZYTHIUM_DELAYER_1, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_DOWN(138, "zythium_delayer_1_down", Items.ZYTHIUM_DELAYER_1, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_LEFT(139, "zythium_delayer_1_left", Items.ZYTHIUM_DELAYER_1, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_UP(140, "zythium_delayer_1_up", Items.ZYTHIUM_DELAYER_1, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON(141, "zythium_delayer_1_right_on", Items.ZYTHIUM_DELAYER_1, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_DOWN_ON(142, "zythium_delayer_1_down_on", Items.ZYTHIUM_DELAYER_1, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_LEFT_ON(143, "zythium_delayer_1_left_on", Items.ZYTHIUM_DELAYER_1, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_1_DELAY_UP_ON(144, "zythium_delayer_1_up_on", Items.ZYTHIUM_DELAYER_1, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_RIGHT(145, "zythium_delayer_2_right", Items.ZYTHIUM_DELAYER_2, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_DOWN(146, "zythium_delayer_2_down", Items.ZYTHIUM_DELAYER_2, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_LEFT(147, "zythium_delayer_2_left", Items.ZYTHIUM_DELAYER_2, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_UP(148, "zythium_delayer_2_up", Items.ZYTHIUM_DELAYER_2, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON(149, "zythium_delayer_2_right_on", Items.ZYTHIUM_DELAYER_2, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_DOWN_ON(150, "zythium_delayer_2_down_on", Items.ZYTHIUM_DELAYER_2, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_LEFT_ON(151, "zythium_delayer_2_left_on", Items.ZYTHIUM_DELAYER_2, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_2_DELAY_UP_ON(152, "zythium_delayer_2_up_on", Items.ZYTHIUM_DELAYER_2, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_RIGHT(153, "zythium_delayer_4_right", Items.ZYTHIUM_DELAYER_4, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_DOWN(154, "zythium_delayer_4_down", Items.ZYTHIUM_DELAYER_4, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_LEFT(155, "zythium_delayer_4_left", Items.ZYTHIUM_DELAYER_4, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_UP(156, "zythium_delayer_4_up", Items.ZYTHIUM_DELAYER_4, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON(157, "zythium_delayer_4_right_on", Items.ZYTHIUM_DELAYER_4, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_DOWN_ON(158, "zythium_delayer_4_down_on", Items.ZYTHIUM_DELAYER_4, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_LEFT_ON(159, "zythium_delayer_4_left_on", Items.ZYTHIUM_DELAYER_4, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_4_DELAY_UP_ON(160, "zythium_delayer_4_up_on", Items.ZYTHIUM_DELAYER_4, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_RIGHT(161, "zythium_delayer_8_right", Items.ZYTHIUM_DELAYER_8, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_DOWN(162, "zythium_delayer_8_down", Items.ZYTHIUM_DELAYER_8, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_LEFT(163, "zythium_delayer_8_left", Items.ZYTHIUM_DELAYER_8, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_UP(164, "zythium_delayer_8_up", Items.ZYTHIUM_DELAYER_8, 12, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, false, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON(165, "zythium_delayer_8_right_on", Items.ZYTHIUM_DELAYER_8, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_DOWN_ON(166, "zythium_delayer_8_down_on", Items.ZYTHIUM_DELAYER_8, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_LEFT_ON(167, "zythium_delayer_8_left_on", Items.ZYTHIUM_DELAYER_8, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0),
    ZYTHIUM_DELAYER_8_DELAY_UP_ON(168, "zythium_delayer_8_up_on", Items.ZYTHIUM_DELAYER_8, 0, 0.001, false, "none", Arrays.asList(7, 8, 9, 10, 51, 54, 57, 145, 148, 154, 157, 169, 172), false, false, true, true, false, 0);

    int index;
    String fileName;
    Items drops;
    int lights;
    double fSpeed;
    boolean gSupport;
    String outline;
    List<Integer> tools;
    boolean cds;
    boolean solid;
    boolean power;
    boolean receive;
    boolean lTrans;
    double conduct;

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
    
    public boolean isZythiumWire() {
        return this == ZYTHIUM_WIRE
                || this == ZYTHIUM_WIRE_1_POWER
                || this == ZYTHIUM_WIRE_2_POWER
                || this == ZYTHIUM_WIRE_3_POWER
                || this == ZYTHIUM_WIRE_4_POWER
                || this == ZYTHIUM_WIRE_5_POWER;
    }
    
    public boolean isZythiumDelayerOnAll() {
        return this == ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_1_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_1_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_1_DELAY_UP_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_UP_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_UP_ON
                || this == ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_8_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_8_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_8_DELAY_UP_ON;
    }

    public static BlockNames turnZythiumDelayerOn(BlockNames block) {
        switch (block) {
            case ZYTHIUM_DELAYER_1_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_1_DELAY_DOWN:
                return ZYTHIUM_DELAYER_1_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_1_DELAY_LEFT:
                return ZYTHIUM_DELAYER_1_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_1_DELAY_UP:
                return ZYTHIUM_DELAYER_1_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_2_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_2_DELAY_DOWN:
                return ZYTHIUM_DELAYER_2_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_2_DELAY_LEFT:
                return ZYTHIUM_DELAYER_2_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_2_DELAY_UP:
                return ZYTHIUM_DELAYER_2_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_4_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_4_DELAY_DOWN:
                return ZYTHIUM_DELAYER_4_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_4_DELAY_LEFT:
                return ZYTHIUM_DELAYER_4_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_4_DELAY_UP:
                return ZYTHIUM_DELAYER_4_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_8_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_8_DELAY_DOWN:
                return ZYTHIUM_DELAYER_8_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_8_DELAY_LEFT:
                return ZYTHIUM_DELAYER_8_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_8_DELAY_UP:
                return ZYTHIUM_DELAYER_8_DELAY_UP_ON;
        }
        throw new UnsupportedOperationException("Turn on only accepted for ZYTHIUM_DELAYER_DELAY");
    }

    public static BlockNames turnZythiumDelayerOff(BlockNames block) {
        switch (block) {
            case ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_1_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_1_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_1_DELAY_DOWN;
            case ZYTHIUM_DELAYER_1_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_1_DELAY_LEFT;
            case ZYTHIUM_DELAYER_1_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_1_DELAY_UP;
            case ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_2_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_2_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_2_DELAY_DOWN;
            case ZYTHIUM_DELAYER_2_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_2_DELAY_LEFT;
            case ZYTHIUM_DELAYER_2_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_2_DELAY_UP;
            case ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_4_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_4_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_4_DELAY_DOWN;
            case ZYTHIUM_DELAYER_4_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_4_DELAY_LEFT;
            case ZYTHIUM_DELAYER_4_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_4_DELAY_UP;
            case ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_8_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_8_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_8_DELAY_DOWN;
            case ZYTHIUM_DELAYER_8_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_8_DELAY_LEFT;
            case ZYTHIUM_DELAYER_8_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_8_DELAY_UP;
        }
        throw new UnsupportedOperationException("Turn off only accepted for ZYTHIUM_DELAYER_DELAY_ON");
    }

    public static BlockNames increaseZythiumDelayerLevel(BlockNames block) {
        switch (block) {
            case ZYTHIUM_DELAYER_1_DELAY_RIGHT:
				return ZYTHIUM_DELAYER_2_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_1_DELAY_DOWN:
				return ZYTHIUM_DELAYER_2_DELAY_DOWN;
            case ZYTHIUM_DELAYER_1_DELAY_LEFT:
				return ZYTHIUM_DELAYER_2_DELAY_LEFT;
            case ZYTHIUM_DELAYER_1_DELAY_UP:
				return ZYTHIUM_DELAYER_2_DELAY_UP;
            case ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON:
				return ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_1_DELAY_DOWN_ON:
				return ZYTHIUM_DELAYER_2_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_1_DELAY_LEFT_ON:
				return ZYTHIUM_DELAYER_2_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_1_DELAY_UP_ON:
				return ZYTHIUM_DELAYER_2_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_2_DELAY_RIGHT:
				return ZYTHIUM_DELAYER_4_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_2_DELAY_DOWN:
				return ZYTHIUM_DELAYER_4_DELAY_DOWN;
            case ZYTHIUM_DELAYER_2_DELAY_LEFT:
				return ZYTHIUM_DELAYER_4_DELAY_LEFT;
            case ZYTHIUM_DELAYER_2_DELAY_UP:
				return ZYTHIUM_DELAYER_4_DELAY_UP;
            case ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON:
				return ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_2_DELAY_DOWN_ON:
				return ZYTHIUM_DELAYER_4_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_2_DELAY_LEFT_ON:
				return ZYTHIUM_DELAYER_4_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_2_DELAY_UP_ON:
				return ZYTHIUM_DELAYER_4_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_4_DELAY_RIGHT:
				return ZYTHIUM_DELAYER_8_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_4_DELAY_DOWN:
				return ZYTHIUM_DELAYER_8_DELAY_DOWN;
            case ZYTHIUM_DELAYER_4_DELAY_LEFT:
				return ZYTHIUM_DELAYER_8_DELAY_LEFT;
            case ZYTHIUM_DELAYER_4_DELAY_UP:
				return ZYTHIUM_DELAYER_8_DELAY_UP;
            case ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON:
				return ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_4_DELAY_DOWN_ON:
				return ZYTHIUM_DELAYER_8_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_4_DELAY_LEFT_ON:
				return ZYTHIUM_DELAYER_8_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_4_DELAY_UP_ON:
				return ZYTHIUM_DELAYER_8_DELAY_UP_ON;
        }
        throw new UnsupportedOperationException("Increase level only accepted for ZYTHIUM_DELAYER_DELAY");
    }

    public static BlockNames resetZythiumDelayerLevel(BlockNames block) {
        switch (block) {
            case ZYTHIUM_DELAYER_8_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_1_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_8_DELAY_DOWN:
                return ZYTHIUM_DELAYER_1_DELAY_DOWN;
            case ZYTHIUM_DELAYER_8_DELAY_LEFT:
                return ZYTHIUM_DELAYER_1_DELAY_LEFT;
            case ZYTHIUM_DELAYER_8_DELAY_UP:
                return ZYTHIUM_DELAYER_1_DELAY_UP;
            case ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_8_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_1_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_8_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_1_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_8_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_1_DELAY_UP_ON;
        }
        throw new UnsupportedOperationException("Reset level only accepted for ZYTHIUM_DELAYER_8_DELAY");
    }
    
    public boolean isZythiumDelayerAll() {
        return this == ZYTHIUM_DELAYER_1_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_1_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_1_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_1_DELAY_UP
                || this == ZYTHIUM_DELAYER_2_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_2_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_2_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_2_DELAY_UP
                || this == ZYTHIUM_DELAYER_4_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_4_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_4_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_4_DELAY_UP
                || this == ZYTHIUM_DELAYER_8_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_8_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_8_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_8_DELAY_UP;
    }

    public boolean isCompleteZythiumDelayer() {
        return isZythiumDelayerAll() || isZythiumDelayerOnAll();
    }

    /**
     * All but 8
     */
    public boolean isZythiumDelayerOn() {
        return this == ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_1_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_1_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_1_DELAY_UP_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_2_DELAY_UP_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_4_DELAY_UP_ON;
    }

    /**
     * All but 8
     */
    public boolean isZythiumDelayer() {
        return this == ZYTHIUM_DELAYER_1_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_1_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_1_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_1_DELAY_UP
                || this == ZYTHIUM_DELAYER_2_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_2_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_2_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_2_DELAY_UP
                || this == ZYTHIUM_DELAYER_4_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_4_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_4_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_4_DELAY_UP;
    }

    public boolean isZythiumDelayer8On() {
        return this == ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON
                || this == ZYTHIUM_DELAYER_8_DELAY_DOWN_ON
                || this == ZYTHIUM_DELAYER_8_DELAY_LEFT_ON
                || this == ZYTHIUM_DELAYER_8_DELAY_UP_ON;
    }

    public boolean isZythiumDelayer8() {
        return this == ZYTHIUM_DELAYER_8_DELAY_RIGHT
                || this == ZYTHIUM_DELAYER_8_DELAY_DOWN
                || this == ZYTHIUM_DELAYER_8_DELAY_LEFT
                || this == ZYTHIUM_DELAYER_8_DELAY_UP;
    }

    public static BlockNames turnZythiumDelayer(BlockNames block) {
        switch (block) {
            case ZYTHIUM_DELAYER_1_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_1_DELAY_DOWN;
            case ZYTHIUM_DELAYER_1_DELAY_DOWN:
                return ZYTHIUM_DELAYER_1_DELAY_LEFT;
            case ZYTHIUM_DELAYER_1_DELAY_LEFT:
                return ZYTHIUM_DELAYER_1_DELAY_UP;
            case ZYTHIUM_DELAYER_1_DELAY_UP:
                return ZYTHIUM_DELAYER_1_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_2_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_2_DELAY_DOWN;
            case ZYTHIUM_DELAYER_2_DELAY_DOWN:
                return ZYTHIUM_DELAYER_2_DELAY_LEFT;
            case ZYTHIUM_DELAYER_2_DELAY_LEFT:
                return ZYTHIUM_DELAYER_2_DELAY_UP;
            case ZYTHIUM_DELAYER_2_DELAY_UP:
                return ZYTHIUM_DELAYER_2_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_4_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_4_DELAY_DOWN;
            case ZYTHIUM_DELAYER_4_DELAY_DOWN:
                return ZYTHIUM_DELAYER_4_DELAY_LEFT;
            case ZYTHIUM_DELAYER_4_DELAY_LEFT:
                return ZYTHIUM_DELAYER_4_DELAY_UP;
            case ZYTHIUM_DELAYER_4_DELAY_UP:
                return ZYTHIUM_DELAYER_4_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_8_DELAY_RIGHT:
                return ZYTHIUM_DELAYER_8_DELAY_DOWN;
            case ZYTHIUM_DELAYER_8_DELAY_DOWN:
                return ZYTHIUM_DELAYER_8_DELAY_LEFT;
            case ZYTHIUM_DELAYER_8_DELAY_LEFT:
                return ZYTHIUM_DELAYER_8_DELAY_UP;
            case ZYTHIUM_DELAYER_8_DELAY_UP:
                return ZYTHIUM_DELAYER_8_DELAY_RIGHT;
            case ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_1_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_1_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_1_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_1_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_1_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_1_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_2_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_2_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_2_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_2_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_2_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_2_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_4_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_4_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_4_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_4_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_4_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_4_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON;
            case ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON:
                return ZYTHIUM_DELAYER_8_DELAY_DOWN_ON;
            case ZYTHIUM_DELAYER_8_DELAY_DOWN_ON:
                return ZYTHIUM_DELAYER_8_DELAY_LEFT_ON;
            case ZYTHIUM_DELAYER_8_DELAY_LEFT_ON:
                return ZYTHIUM_DELAYER_8_DELAY_UP_ON;
            case ZYTHIUM_DELAYER_8_DELAY_UP_ON:
                return ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON;
        }

        throw new UnsupportedOperationException("Turn accepted for ZYTHIUM_DELAYER");
    }

    public boolean isZythiumAmplifierAll() {
        return this == ZYTHIUM_AMPLIFIER_RIGHT
                || this == ZYTHIUM_AMPLIFIER_DOWN
                || this == ZYTHIUM_AMPLIFIER_LEFT
                || this == ZYTHIUM_AMPLIFIER_UP;
    }

    public boolean isZythiumAmplifierOnAll() {
        return this == ZYTHIUM_AMPLIFIER_RIGHT_ON
                || this == ZYTHIUM_AMPLIFIER_DOWN_ON
                || this == ZYTHIUM_AMPLIFIER_LEFT_ON
                || this == ZYTHIUM_AMPLIFIER_UP_ON;
    }

    public boolean isCompleteZythiumAmplifier() {
        return isZythiumAmplifierAll() || isZythiumAmplifierOnAll();
    }

    /**
     * All but UP
     */
    public boolean isZythiumAmplifier() {
        return this == ZYTHIUM_AMPLIFIER_RIGHT
                || this == ZYTHIUM_AMPLIFIER_DOWN
                || this == ZYTHIUM_AMPLIFIER_LEFT;
    }

    /**
     * All but UP
     */
    public boolean isZythiumAmplifierOn() {
        return this == ZYTHIUM_AMPLIFIER_RIGHT_ON
                || this == ZYTHIUM_AMPLIFIER_DOWN_ON
                || this == ZYTHIUM_AMPLIFIER_LEFT_ON;
    }

    public static BlockNames turnZythiumAmplifier(BlockNames block) {
        switch (block) {
            case ZYTHIUM_AMPLIFIER_RIGHT:
                return ZYTHIUM_AMPLIFIER_DOWN;
            case ZYTHIUM_AMPLIFIER_DOWN:
                return ZYTHIUM_AMPLIFIER_LEFT;
            case ZYTHIUM_AMPLIFIER_LEFT:
                return ZYTHIUM_AMPLIFIER_UP;
            case ZYTHIUM_AMPLIFIER_UP:
                return ZYTHIUM_AMPLIFIER_RIGHT;
            case ZYTHIUM_AMPLIFIER_RIGHT_ON:
                return ZYTHIUM_AMPLIFIER_DOWN_ON;
            case ZYTHIUM_AMPLIFIER_DOWN_ON:
                return ZYTHIUM_AMPLIFIER_LEFT_ON;
            case ZYTHIUM_AMPLIFIER_LEFT_ON:
                return ZYTHIUM_AMPLIFIER_UP_ON;
            case ZYTHIUM_AMPLIFIER_UP_ON:
                return ZYTHIUM_AMPLIFIER_RIGHT_ON;
        }
        throw new UnsupportedOperationException("Turn only accepted for ZYTHIUM_AMPLIFIER");
    }

    public static BlockNames turnZythiumAmplifierOn(BlockNames block) {
        switch (block) {
            case ZYTHIUM_AMPLIFIER_RIGHT:
                return ZYTHIUM_AMPLIFIER_RIGHT_ON;
            case ZYTHIUM_AMPLIFIER_DOWN:
                return ZYTHIUM_AMPLIFIER_DOWN_ON;
            case ZYTHIUM_AMPLIFIER_LEFT:
                return ZYTHIUM_AMPLIFIER_LEFT_ON;
            case ZYTHIUM_AMPLIFIER_UP:
                return ZYTHIUM_AMPLIFIER_UP_ON;
        }
        throw new UnsupportedOperationException("Turn on only accepted for ZYTHIUM_AMPLIFIER");
    }

    public static BlockNames turnZythiumAmplifierOff(BlockNames block) {
        switch (block) {
            case ZYTHIUM_AMPLIFIER_RIGHT_ON:
                return ZYTHIUM_AMPLIFIER_RIGHT;
            case ZYTHIUM_AMPLIFIER_DOWN_ON:
                return ZYTHIUM_AMPLIFIER_DOWN;
            case ZYTHIUM_AMPLIFIER_LEFT_ON:
                return ZYTHIUM_AMPLIFIER_LEFT;
            case ZYTHIUM_AMPLIFIER_UP_ON:
                return ZYTHIUM_AMPLIFIER_UP;
        }
        throw new UnsupportedOperationException("Turn off only accepted for ZYTHIUM_AMPLIFIER_ON");
    }

    public boolean isZythiumInverterAll() {
        return this == ZYTHIUM_INVERTER_RIGHT
                || this == ZYTHIUM_INVERTER_DOWN
                || this == ZYTHIUM_INVERTER_LEFT
                || this == ZYTHIUM_INVERTER_UP;
    }

    public boolean isZythiumInverterOnAll() {
        return this == ZYTHIUM_INVERTER_RIGHT_ON
                || this == ZYTHIUM_INVERTER_DOWN_ON
                || this == ZYTHIUM_INVERTER_LEFT_ON
                || this == ZYTHIUM_INVERTER_UP_ON;
    }

    public boolean isCompleteZythiumInverter() {
        return isZythiumInverterAll() || isZythiumInverterOnAll();
    }

    /**
     * All but UP
     */
    public boolean isZythiumInverter() {
        return this == ZYTHIUM_INVERTER_RIGHT
                || this == ZYTHIUM_INVERTER_DOWN
                || this == ZYTHIUM_INVERTER_LEFT;
    }

    /**
     * All but UP
     */
    public boolean isZythiumInverterOn() {
        return this == ZYTHIUM_INVERTER_RIGHT_ON
                || this == ZYTHIUM_INVERTER_DOWN_ON
                || this == ZYTHIUM_INVERTER_LEFT_ON;
    }

    public static BlockNames turnZythiumInverter(BlockNames block) {
        switch (block) {
            case ZYTHIUM_INVERTER_RIGHT:
                return ZYTHIUM_INVERTER_DOWN;
            case ZYTHIUM_INVERTER_DOWN:
                return ZYTHIUM_INVERTER_LEFT;
            case ZYTHIUM_INVERTER_LEFT:
                return ZYTHIUM_INVERTER_UP;
            case ZYTHIUM_INVERTER_UP:
                return ZYTHIUM_INVERTER_RIGHT;
            case ZYTHIUM_INVERTER_RIGHT_ON:
                return ZYTHIUM_INVERTER_DOWN_ON;
            case ZYTHIUM_INVERTER_DOWN_ON:
                return ZYTHIUM_INVERTER_LEFT_ON;
            case ZYTHIUM_INVERTER_LEFT_ON:
                return ZYTHIUM_INVERTER_UP_ON;
            case ZYTHIUM_INVERTER_UP_ON:
                return ZYTHIUM_INVERTER_RIGHT_ON;
        }
        throw new UnsupportedOperationException("Turn only accepted for ZYTHIUM_INVERTER");
    }

    public static BlockNames turnZythiumInverterOff(BlockNames block) {
        switch (block) {
            case ZYTHIUM_INVERTER_RIGHT_ON:
                return ZYTHIUM_INVERTER_RIGHT;
            case ZYTHIUM_INVERTER_DOWN_ON:
                return ZYTHIUM_INVERTER_DOWN;
            case ZYTHIUM_INVERTER_LEFT_ON:
                return ZYTHIUM_INVERTER_LEFT;
            case ZYTHIUM_INVERTER_UP_ON:
                return ZYTHIUM_INVERTER_UP;
        }
        throw new UnsupportedOperationException("Turn off only accepted for ZYTHIUM_INVERTER_ON");
    }

    public static BlockNames turnZythiumInverterOn(BlockNames block) {
        switch (block) {
            case ZYTHIUM_INVERTER_RIGHT:
                return ZYTHIUM_INVERTER_RIGHT_ON;
            case ZYTHIUM_INVERTER_DOWN:
                return ZYTHIUM_INVERTER_DOWN_ON;
            case ZYTHIUM_INVERTER_LEFT:
                return ZYTHIUM_INVERTER_LEFT_ON;
            case ZYTHIUM_INVERTER_UP:
                return ZYTHIUM_INVERTER_UP_ON;
        }
        throw new UnsupportedOperationException("Turn on only accepted for ZYTHIUM_INVERTER");
    }

    public boolean isLever() {
        return this == LEVER
                || this == LEVER_LEFT_WALL
                || this == LEVER_RIGHT_WALL;
    }

    public boolean isLeverOn() {
        return this == LEVER_ON
                || this == LEVER_LEFT_WALL_ON
                || this == LEVER_RIGHT_WALL_ON;
    }

    public static BlockNames turnLeverOn(BlockNames block) {
        switch (block) {
            case LEVER:
                return LEVER_ON;
            case LEVER_LEFT_WALL:
                return LEVER_LEFT_WALL_ON;
            case LEVER_RIGHT_WALL:
                return LEVER_RIGHT_WALL_ON;
        }
        throw new UnsupportedOperationException("Turn on only accepted for LEVER");
    }

    public static BlockNames turnLeverOff(BlockNames block) {
        switch (block) {
            case LEVER_ON:
                return LEVER;
            case LEVER_LEFT_WALL_ON:
                return LEVER_LEFT_WALL;
            case LEVER_RIGHT_WALL_ON:
                return LEVER_RIGHT_WALL;
        }
        throw new UnsupportedOperationException("Turn off only accepted for LEVER_ON");
    }

    public boolean isButton() {
        return this == BUTTON_LEFT
                || this == BUTTON_RIGHT;
    }

    public static BlockNames turnButtonOn(BlockNames block) {
        switch (block) {
            case BUTTON_LEFT:
                return BUTTON_LEFT_ON;
            case BUTTON_RIGHT:
                return BUTTON_RIGHT_ON;
        }
        throw new UnsupportedOperationException("Turn on only accepted for BUTTON");
    }

    public boolean isCompletePressurePlate() {
        return isPressurePlate() || isPressurePlateOn();
    }

    public boolean isPressurePlate() {
        return this == WOODEN_PRESSURE_PLATE
                || this == STONE_PRESSURE_PLATE
                || this == ZYTHIUM_PRESSURE_PLATE;
    }

    public boolean isPressurePlateOn() {
        return this == WOODEN_PRESSURE_PLATE_ON
                || this == STONE_PRESSURE_PLATE_ON
                || this == ZYTHIUM_PRESSURE_PLATE_ON;
    }

    public boolean isWoodenPressurePlate() {
        return this == WOODEN_PRESSURE_PLATE
                || this == WOODEN_PRESSURE_PLATE_ON;
    }

    public boolean isStonePressurePlate() {
        return this == STONE_PRESSURE_PLATE
                || this == STONE_PRESSURE_PLATE_ON;
    }

    public static BlockNames turnPressurePlateOn(BlockNames block) {
        switch(block) {
            case WOODEN_PRESSURE_PLATE:
                return WOODEN_PRESSURE_PLATE_ON;
            case STONE_PRESSURE_PLATE:
                return STONE_PRESSURE_PLATE_ON;
            case ZYTHIUM_PRESSURE_PLATE:
                return ZYTHIUM_PRESSURE_PLATE_ON;
        }
        throw new UnsupportedOperationException("Turn on only accepted for PRESSURE_PLATE");
    }

}
