package com.sergio.refacto.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 *      0    Empty
 *      1    Dirt
 *      2    Stone
 *      3    Copper Ore
 *      4    Iron Ore
 *      5    Silver Ore
 *      6    Gold Ore
 *      7    Copper Pick
 *      8    Iron Pick
 *      9    Silver Pick
 *      10    Gold Pick
 *      11    Copper Axe
 *      12    Iron Axe
 *      13    Silver Axe
 *      14    Gold Axe
 *      15    Wood
 *      16    Copper Sword
 *      17    Iron Sword
 *      18    Silver Sword
 *      19    Gold Sword
 *      20    Workbench
 *      21    Wooden Chest
 *      22    Stone Chest
 *      23    Copper Chest
 *      24    Iron Chest
 *      25    Silver Chest
 *      26    Gold Chest
 *      27    Furnace
 *      28    Coal
 *      29    Copper Ingot
 *      30    Iron Ingot
 *      31    Silver Ingot
 *      32    Gold Ingot
 *      33    Stone Lighter
 *      34    Lumenstone
 *      35    Wooden Torch
 *      36    Coal Torch
 *      37    Lumenstone Torch
 *      38    Zinc Ore
 *      39    Rhymestone Ore
 *      40    Obdurite Ore
 *      41    Aluminum Ore
 *      42    Lead Ore
 *      43    Uranium Ore
 *      44    Zythium Ore
 *      45    Silicon Ore
 *      46    Irradium Ore
 *      47    Nullstone
 *      48    Meltstone
 *      49    Skystone
 *      50    Magnetite Ore
 *      51    Zinc Pick
 *      52    Zinc Axe
 *      53    Zinc Sword
 *      54    Rhymestone Pick
 *      55    Rhymestone Axe
 *      56    Rhymestone Sword
 *      57    Obdurite Pick
 *      58    Obdurite Axe
 *      59    Obdurite Sword
 *      60    Zinc Ingot
 *      61    Rhymestone Ingot
 *      62    Obdurite Ingot
 *      63    Aluminum Ingot
 *      64    Lead Ingot
 *      65    Uranium Bar
 *      66    Refined Uranium
 *      67    Zythium Bar
 *      68    Silicon Bar
 *      69    Irradium Ingot
 *      70    Nullstone Bar
 *      71    Meltstone Bar
 *      72    Skystone Bar
 *      73    Magnetite Ingot
 *      74    Sand
 *      75    Snow
 *      76    Glass
 *      77    Sunflower Seeds
 *      78    Sunflower
 *      79    Moonflower Seeds
 *      80    Moonflower
 *      81    Dryweed Seeds
 *      82    Dryweed
 *      83    Greenleaf Seeds
 *      84    Greenleaf
 *      85    Frostleaf Seeds
 *      86    Frostleaf
 *      87    Caveroot Seeds
 *      88    Caveroot
 *      89    Skyblossom Seeds
 *      90    Skyblossom
 *      91    Void Rot Seeds
 *      92    Void Rot
 *      93    Mud
 *      94    Sandstone
 *      95    Marshleaf Seeds
 *      96    Marshleaf
 *      97    Blue Goo
 *      98    Green Goo
 *      99    Red Goo
 *      100    Yellow Goo
 *      101    Black Goo
 *      102    White Goo
 *      103    Astral Shard
 *      104    Rotten Chunk
 *      105    Copper Helmet
 *      106    Copper Chestplate
 *      107    Copper Leggings
 *      108    Copper Greaves
 *      109    Iron Helmet
 *      110    Iron Chestplate
 *      111    Iron Leggings
 *      112    Iron Greaves
 *      113    Silver Helmet
 *      114    Silver Chestplate
 *      115    Silver Leggings
 *      116    Silver Greaves
 *      117    Gold Helmet
 *      118    Gold Chestplate
 *      119    Gold Leggings
 *      120    Gold Greaves
 *      121    Zinc Helmet
 *      122    Zinc Chestplate
 *      123    Zinc Leggings
 *      124    Zinc Greaves
 *      125    Rhymestone Helmet
 *      126    Rhymestone Chestplate
 *      127    Rhymestone Leggings
 *      128    Rhymestone Greaves
 *      129    Obdurite Helmet
 *      130    Obdurite Chestplate
 *      131    Obdurite Leggings
 *      132    Obdurite Greaves
 *      133    Aluminum Helmet
 *      134    Aluminum Chestplate
 *      135    Aluminum Leggings
 *      136    Aluminum Greaves
 *      137    Lead Helmet
 *      138    Lead Chestplate
 *      139    Lead Leggings
 *      140    Lead Greaves
 *      141    Zythium Helmet
 *      142    Zythium Chestplate
 *      143    Zythium Leggings
 *      144    Zythium Greaves
 *      145    Aluminum Pick
 *      146    Aluminum Axe
 *      147    Aluminum Sword
 *      148    Lead Pick
 *      149    Lead Axe
 *      150    Lead Sword
 *      151    Zinc Chest
 *      152    Rhymestone Chest
 *      153    Obdurite Chest
 *      154    Wooden Pick
 *      155    Wooden Axe
 *      156    Wooden Sword
 *      157    Stone Pick
 *      158    Stone Axe
 *      159    Stone Sword
 *      160    Bark
 *      161    Cobblestone
 *      162    Chiseled Stone
 *      163    Chiseled Cobblestone
 *      164    Stone Bricks
 *      165    Clay
 *      166    Clay Bricks
 *      167    Varnish
 *      168    Varnished Wood
 *      169    Magnetite Pick
 *      170    Magnetite Axe
 *      171    Magnetite Sword
 *      172    Irradium Pick
 *      173    Irradium Axe
 *      174    Irradium Sword
 *      175    Zythium Wire
 *      176    Zythium Torch
 *      177    Zythium Lamp
 *      178    Lever
 *      179    Charcoal
 *      180    Zythium Amplifier
 *      181    Zythium Inverter
 *      182    Button
 *      183    Wooden Pressure Plate
 *      184    Stone Pressure Plate
 *      185    Zythium Pressure Plate
 *      186    Zythium Delayer (1)
 *      187    Zythium Delayer (2)
 *      188    Zythium Delayer (4)
 *      189    Zythium Delayer (8)
 *      190    Wrench
 */
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Items {
	EMPTY(0, "air", 0, 100, 1, 0.175, null, Blocks.AIR, "Air"),
	DIRT(1, "blocks/dirt", 0, 100, 1, 0.175, null, Blocks.DIRT, "Dirt"),
	STONE(2, "blocks/stone", 0, 100, 1, 0.175, null, Blocks.STONE, "Stone"),
	COPPER_ORE(3, "ores/copper_ore", 0, 100, 1, 0.175, null, Blocks.COPPER_ORE, "Copper Ore"),
	IRON_ORE(4, "ores/iron_ore", 0, 100, 1, 0.175, null, Blocks.IRON_ORE, "Iron Ore"),
	SILVER_ORE(5, "ores/silver_ore", 0, 100, 1, 0.175, null, Blocks.SILVER_ORE, "Silver Ore"),
	GOLD_ORE(6, "ores/gold_ore", 0, 100, 1, 0.175, null, Blocks.GOLD_ORE, "Gold Ore"),
	COPPER_PICK(7, "tools/copper_pick", 0, 1, 2, 0.12, 400, Blocks.AIR, "Copper Pick"),
	IRON_PICK(8, "tools/iron_pick", 0, 1, 3, 0.13, 500, Blocks.AIR, "Iron Pick"),
	SILVER_PICK(9, "tools/silver_pick", 0, 1, 3, 0.14, 600, Blocks.AIR, "Silver Pick"),
	GOLD_PICK(10, "tools/gold_pick", 0, 1, 4, 0.15, 800, Blocks.AIR, "Gold Pick"),
	COPPER_AXE(11, "tools/copper_axe", 0, 1, 3, 0.12, 400, Blocks.AIR, "Copper Axe"),
	IRON_AXE(12, "tools/iron_axe", 0, 1, 4, 0.13, 500, Blocks.AIR, "Iron Axe"),
	SILVER_AXE(13, "tools/silver_axe", 0, 1, 5, 0.14, 600, Blocks.AIR, "Silver Axe"),
	GOLD_AXE(14, "tools/gold_axe", 0, 1, 6, 0.15, 800, Blocks.AIR, "Gold Axe"),
	WOOD(15, "blocks/wood", 0, 100, 1, 0.175, null, Blocks.WOOD, "Wood"),
	COPPER_SWORD(16, "tools/copper_sword", 0, 1, 5, 0.11, 250, Blocks.AIR, "Copper Sword"),
	IRON_SWORD(17, "tools/iron_sword", 0, 1, 8, 0.115, 300, Blocks.AIR, "Iron Sword"),
	SILVER_SWORD(18, "tools/silver_sword", 0, 1, 13, 0.12, 400, Blocks.AIR, "Silver Sword"),
	GOLD_SWORD(19, "tools/gold_sword", 0, 1, 18, 0.125, 600, Blocks.AIR, "Gold Sword"),
	WORKBENCH(20, "machines/workbench", 0, 100, 1, 0.175, null, Blocks.WORKBENCH, "Workbench"),
	WOODEN_CHEST(21, "machines/wooden_chest", 0, 100, 1, 0.175, null, Blocks.WOODEN_CHEST, "Wooden Chest"),
	STONE_CHEST(22, "machines/stone_chest", 0, 100, 1, 0.175, null, Blocks.STONE_CHEST, "Stone Chest"),
	COPPER_CHEST(23, "machines/copper_chest", 0, 100, 1, 0.175, null, Blocks.COPPER_CHEST, "Copper Chest"),
	IRON_CHEST(24, "machines/iron_chest", 0, 100, 1, 0.175, null, Blocks.IRON_CHEST, "Iron Chest"),
	SILVER_CHEST(25, "machines/silver_chest", 0, 100, 1, 0.175, null, Blocks.SILVER_CHEST, "Silver Chest"),
	GOLD_CHEST(26, "machines/gold_chest", 0, 100, 1, 0.175, null, Blocks.GOLD_CHEST, "Gold Chest"),
	FURNACE(27, "machines/furnace", 0, 100, 1, 0.175, null, Blocks.FURNACE, "Furnace"),
	COAL(28, "ores/coal", 0, 100, 1, 0.175, null, Blocks.COAL, "Coal"),
	COPPER_INGOT(29, "ingots/copper_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Copper Ingot"),
	IRON_INGOT(30, "ingots/iron_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Iron Ingot"),
	SILVER_INGOT(31, "ingots/silver_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Silver Ingot"),
	GOLD_INGOT(32, "ingots/gold_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Gold Ingot"),
	STONE_LIGHTER(33, "tools/stone_lighter", 0, 1, 1, 0.125, 100, Blocks.AIR, "Stone Lighter"),
	LUMENSTONE(34, "ores/lumenstone", 0, 100, 1, 0.175, null, Blocks.LUMENSTONE, "Lumenstone"),
	WOODEN_TORCH(35, "torches/wooden_torch", 0, 100, 1, 0.175, null, Blocks.WOODEN_TORCH, "Wooden Torch"),
	COAL_TORCH(36, "torches/coal_torch", 0, 100, 1, 0.175, null, Blocks.COAL_TORCH, "Coal Torch"),
	LUMENSTONE_TORCH(37, "torches/lumenstone_torch", 0, 100, 1, 0.175, null, Blocks.LUMENSTONE_TORCH, "Lumenstone Torch"),
	ZINC_ORE(38, "ores/zinc_ore", 0, 100, 1, 0.175, null, Blocks.ZINC_ORE, "Zinc Ore"),
	RHYMESTONE_ORE(39, "ores/rhymestone_ore", 0, 100, 1, 0.175, null, Blocks.RHYMESTONE_ORE, "Rhymestone Ore"),
	OBDURITE_ORE(40, "ores/obdurite_ore", 0, 100, 1, 0.175, null, Blocks.OBDURITE_ORE, "Obdurite Ore"),
	ALUMINUM_ORE(41, "ores/aluminum_ore", 0, 100, 1, 0.175, null, Blocks.ALUMINUM_ORE, "Aluminum Ore"),
	LEAD_ORE(42, "ores/lead_ore", 0, 100, 1, 0.175, null, Blocks.LEAD_ORE, "Lead Ore"),
	URANIUM_ORE(43, "ores/uranium_ore", 0, 100, 1, 0.175, null, Blocks.URANIUM_ORE, "Uranium Ore"),
	ZYTHIUM_ORE(44, "ores/zythium_ore", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_ORE, "Zythium Ore"),
	SILICON_ORE(45, "ores/silicon_ore", 0, 100, 1, 0.175, null, Blocks.SILICON_ORE, "Silicon Ore"),
	IRRADIUM_ORE(46, "ores/irradium_ore", 0, 100, 1, 0.175, null, Blocks.IRRADIUM_ORE, "Irradium Ore"),
	NULLSTONE(47, "ores/nullstone", 0, 100, 1, 0.175, null, Blocks.NULLSTONE, "Nullstone"),
	MELTSTONE(48, "ores/meltstone", 0, 100, 1, 0.175, null, Blocks.MELTSTONE, "Meltstone"),
	SKYSTONE(49, "ores/skystone", 0, 100, 1, 0.175, null, Blocks.SKYSTONE, "Skystone"),
	MAGNETITE_ORE(50, "ores/magnetite_ore", 0, 100, 1, 0.175, null, Blocks.MAGNETITE_ORE, "Magnetite Ore"),
	ZINC_PICK(51, "tools/zinc_pick", 0, 1, 6, 0.16, 1100, Blocks.AIR, "Zinc Pick"),
	ZINC_AXE(52, "tools/zinc_axe", 0, 1, 9, 0.16, 1100, Blocks.AIR, "Zinc Axe"),
	ZINC_SWORD(53, "tools/zinc_sword", 0, 1, 24, 0.13, 950, Blocks.AIR, "Zinc Sword"),
	RHYMESTONE_PICK(54, "tools/rhymestone_pick", 0, 1, 8, 0.17, 1350, Blocks.AIR, "Rhymestone Pick"),
	RHYMESTONE_AXE(55, "tools/rhymestone_axe", 0, 1, 11, 0.17, 1350, Blocks.AIR, "Rhymestone Axe"),
	RHYMESTONE_SWORD(56, "tools/rhymestone_sword", 0, 1, 30, 0.135, null, Blocks.AIR, "Rhymestone Sword"),
	OBDURITE_PICK(57, "tools/obdurite_pick", 0, 1, 20, 0.18, 1600, Blocks.AIR, "Obdurite Pick"),
	OBDURITE_AXE(58, "tools/obdurite_axe", 0, 1, 30, 0.18, 1600, Blocks.AIR, "Obdurite Axe"),
	OBDURITE_SWORD(59, "tools/obdurite_sword", 0, 1, 75, 0.14, 1600, Blocks.AIR, "Obdurite Sword"),
	ZINC_INGOT(60, "ingots/zinc_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Zinc Ingot"),
	RHYMESTONE_INGOT(61, "ingots/rhymestone_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Rhymestone Ingot"),
	OBDURITE_INGOT(62, "ingots/obdurite_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Obdurite Ingot"),
	ALUMINUM_INGOT(63, "ingots/aluminum_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Aluminum Ingot"),
	LEAD_INGOT(64, "ingots/lead_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Lead Ingot"),
	URANIUM_BAR(65, "ingots/uranium_bar", 0, 100, 1, 0.175, null, Blocks.AIR, "Uranium Bar"),
	REFINED_URANIUM(66, "ingots/refined_uranium", 0, 100, 1, 0.175, null, Blocks.AIR, "Refined Uranium"),
	ZYTHIUM_BAR(67, "ingots/zythium_bar", 0, 100, 1, 0.175, null, Blocks.AIR, "Zythium Bar"),
	SILICON_BAR(68, "ingots/silicon_bar", 0, 100, 1, 0.175, null, Blocks.AIR, "Silicon Bar"),
	IRRADIUM_INGOT(69, "ingots/irradium_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Irradium Ingot"),
	NULLSTONE_BAR(70, "ingots/nullstone_bar", 0, 100, 1, 0.175, null, Blocks.AIR, "Nullstone Bar"),
	MELTSTONE_BAR(71, "ingots/meltstone_bar", 0, 100, 1, 0.175, null, Blocks.AIR, "Meltstone Bar"),
	SKYSTONE_BAR(72, "ingots/skystone_bar", 0, 100, 1, 0.175, null, Blocks.AIR, "Skystone Bar"),
	MAGNETITE_INGOT(73, "ingots/magnetite_ingot", 0, 100, 1, 0.175, null, Blocks.AIR, "Magnetite Ingot"),
	SAND(74, "blocks/sand", 0, 100, 1, 0.175, null, Blocks.SAND, "Sand"),
	SNOW(75, "blocks/snow", 0, 100, 1, 0.175, null, Blocks.SNOW, "Snow"),
	GLASS(76, "blocks/glass", 0, 100, 1, 0.175, null, Blocks.GLASS, "Glass"),
	SUNFLOWER_SEEDS(77, "seeds/sunflower_seeds", 0, 100, 1, 0.175, null, Blocks.SUNFLOWER_STAGE_1, "Sunflower Seeds"),
	SUNFLOWER(78, "herbs/sunflower", 0, 100, 1, 0.175, null, Blocks.AIR, "Sunflower"),
	MOONFLOWER_SEEDS(79, "seeds/moonflower_seeds", 0, 100, 1, 0.175, null, Blocks.MOONFLOWER_STAGE_1, "Moonflower Seeds"),
	MOONFLOWER(80, "herbs/moonflower", 0, 100, 1, 0.175, null, Blocks.AIR, "Moonflower"),
	DRYWEED_SEEDS(81, "seeds/dryweed_seeds", 0, 100, 1, 0.175, null, Blocks.DRYWEED_STAGE_1, "Dryweed Seeds"),
	DRYWEED(82, "herbs/dryweed", 0, 100, 1, 0.175, null, Blocks.AIR, "Dryweed"),
	GREENLEAF_SEEDS(83, "seeds/greenleaf_seeds", 0, 100, 1, 0.175, null, Blocks.GREENLEAF_STAGE_1, "Greenleaf Seeds"),
	GREENLEAF(84, "herbs/greenleaf", 0, 100, 1, 0.175, null, Blocks.AIR, "Greenleaf"),
	FROSTLEAF_SEEDS(85, "seeds/frostleaf_seeds", 0, 100, 1, 0.175, null, Blocks.FROSTLEAF_STAGE_1, "Frostleaf Seeds"),
	FROSTLEAF(86, "herbs/frostleaf", 0, 100, 1, 0.175, null, Blocks.AIR, "Frostleaf"),
	CAVEROOT_SEEDS(87, "seeds/caveroot_seeds", 0, 100, 1, 0.175, null, Blocks.CAVEROOT_STAGE_1, "Caveroot Seeds"),
	CAVEROOT(88, "herbs/caveroot", 0, 100, 1, 0.175, null, Blocks.AIR, "Caveroot"),
	SKYBLOSSOM_SEEDS(89, "seeds/skyblossom_seeds", 0, 100, 1, 0.175, null, Blocks.SKYBLOSSOM_STAGE_1, "Skyblossom Seeds"),
	SKYBLOSSOM(90, "herbs/skyblossom", 0, 100, 1, 0.175, null, Blocks.AIR, "Skyblossom"),
	VOID_ROT_SEEDS(91, "seeds/void_rot_seeds", 0, 100, 1, 0.175, null, Blocks.VOID_ROT_STAGE_1, "Void Rot Seeds"),
	VOID_ROT(92, "herbs/void_rot", 0, 100, 1, 0.175, null, Blocks.AIR, "Void Rot"),
	MUD(93, "blocks/mud", 0, 100, 1, 0.175, null, Blocks.MUD, "Mud"),
	SANDSTONE(94, "blocks/sandstone", 0, 100, 1, 0.175, null, Blocks.SANDSTONE, "Sandstone"),
	MARSHLEAF_SEEDS(95, "seeds/marshleaf_seeds", 0, 100, 1, 0.175, null, Blocks.MARSHLEAF_STAGE_1, "Marshleaf Seeds"),
	MARSHLEAF(96, "herbs/marshleaf", 0, 100, 1, 0.175, null, Blocks.AIR, "Marshleaf"),
	BLUE_GOO(97, "goo/blue_goo", 0, 100, 1, 0.175, null, Blocks.AIR, "Blue Goo"),
	GREEN_GOO(98, "goo/green_goo", 0, 100, 1, 0.175, null, Blocks.AIR, "Green Goo"),
	RED_GOO(99, "goo/red_goo", 0, 100, 1, 0.175, null, Blocks.AIR, "Red Goo"),
	YELLOW_GOO(100, "goo/yellow_goo", 0, 100, 1, 0.175, null, Blocks.AIR, "Yellow Goo"),
	BLACK_GOO(101, "goo/black_goo", 0, 100, 1, 0.175, null, Blocks.AIR, "Black Goo"),
	WHITE_GOO(102, "goo/white_goo", 0, 100, 1, 0.175, null, Blocks.AIR, "White Goo"),
	ASTRAL_SHARD(103, "goo/astral_shard", 0, 100, 1, 0.175, null, Blocks.AIR, "Astral Shard"),
	ROTTEN_CHUNK(104, "goo/rotten_chunk", 0, 100, 1, 0.175, null, Blocks.AIR, "Rotten Chunk"),
	COPPER_HELMET(105, "armor/copper_helmet", 1, 1, 1, 0.175, 200, Blocks.AIR, "Copper Helmet"),
	COPPER_CHESTPLATE(106, "armor/copper_chestplate", 2, 1, 1, 0.175, 200, Blocks.AIR, "Copper Chestplate"),
	COPPER_LEGGINGS(107, "armor/copper_leggings", 1, 1, 1, 0.175, 200, Blocks.AIR, "Copper Leggings"),
	COPPER_GREAVES(108, "armor/copper_greaves", 1, 1, 1, 0.175, 200, Blocks.AIR, "Copper Greaves"),
	IRON_HELMET(109, "armor/iron_helmet", 1, 1, 1, 0.175, 200, Blocks.AIR, "Iron Helmet"),
	IRON_CHESTPLATE(110, "armor/iron_chestplate", 3, 1, 1, 0.175, 200, Blocks.AIR, "Iron Chestplate"),
	IRON_LEGGINGS(111, "armor/iron_leggings", 2, 1, 1, 0.175, 200, Blocks.AIR, "Iron Leggings"),
	IRON_GREAVES(112, "armor/iron_greaves", 1, 1, 1, 0.175, 200, Blocks.AIR, "Iron Greaves"),
	SILVER_HELMET(113, "armor/silver_helmet", 2, 1, 1, 0.175, 200, Blocks.AIR, "Silver Helmet"),
	SILVER_CHESTPLATE(114, "armor/silver_chestplate", 4, 1, 1, 0.175, 200, Blocks.AIR, "Silver Chestplate"),
	SILVER_LEGGINGS(115, "armor/silver_leggings", 3, 1, 1, 0.175, 200, Blocks.AIR, "Silver Leggings"),
	SILVER_GREAVES(116, "armor/silver_greaves", 1, 1, 1, 0.175, 200, Blocks.AIR, "Silver Greaves"),
	GOLD_HELMET(117, "armor/gold_helmet", 3, 1, 1, 0.175, 200, Blocks.AIR, "Gold Helmet"),
	GOLD_CHESTPLATE(118, "armor/gold_chestplate", 6, 1, 1, 0.175, 200, Blocks.AIR, "Gold Chestplate"),
	GOLD_LEGGINGS(119, "armor/gold_leggings", 5, 1, 1, 0.175, 200, Blocks.AIR, "Gold Leggings"),
	GOLD_GREAVES(120, "armor/gold_greaves", 2, 1, 1, 0.175, 200, Blocks.AIR, "Gold Greaves"),
	ZINC_HELMET(121, "armor/zinc_helmet", 4, 1, 1, 0.175, 200, Blocks.AIR, "Zinc Helmet"),
	ZINC_CHESTPLATE(122, "armor/zinc_chestplate", 7, 1, 1, 0.175, 200, Blocks.AIR, "Zinc Chestplate"),
	ZINC_LEGGINGS(123, "armor/zinc_leggings", 6, 1, 1, 0.175, 200, Blocks.AIR, "Zinc Leggings"),
	ZINC_GREAVES(124, "armor/zinc_greaves", 3, 1, 1, 0.175, 200, Blocks.AIR, "Zinc Greaves"),
	RHYMESTONE_HELMET(125, "armor/rhymestone_helmet", 5, 1, 1, 0.175, 200, Blocks.AIR, "Rhymestone Helmet"),
	RHYMESTONE_CHESTPLATE(126, "armor/rhymestone_chestplate", 6, 1, 1, 0.175, 200, Blocks.AIR, "Rhymestone Chestplate"),
	RHYMESTONE_LEGGINGS(127, "armor/rhymestone_leggings", 7, 1, 1, 0.175, 200, Blocks.AIR, "Rhymestone Leggings"),
	RHYMESTONE_GREAVES(128, "armor/rhymestone_greaves", 4, 1, 1, 0.175, 200, Blocks.AIR, "Rhymestone Greaves"),
	OBDURITE_HELMET(129, "armor/obdurite_helmet", 7, 1, 1, 0.175, 200, Blocks.AIR, "Obdurite Helmet"),
	OBDURITE_CHESTPLATE(130, "armor/obdurite_chestplate", 12, 1, 1, 0.175, 200, Blocks.AIR, "Obdurite Chestplate"),
	OBDURITE_LEGGINGS(131, "armor/obdurite_leggings", 10, 1, 1, 0.175, 200, Blocks.AIR, "Obdurite Leggings"),
	OBDURITE_GREAVES(132, "armor/obdurite_greaves", 6, 1, 1, 0.175, 200, Blocks.AIR, "Obdurite Greaves"),
	ALUMINUM_HELMET(133, "armor/aluminum_helmet", 4, 1, 1, 0.175, 200, Blocks.AIR, "Aluminum Helmet"),
	ALUMINUM_CHESTPLATE(134, "armor/aluminum_chestplate", 7, 1, 1, 0.175, 200, Blocks.AIR, "Aluminum Chestplate"),
	ALUMINUM_LEGGINGS(135, "armor/aluminum_leggings", 6, 1, 1, 0.175, 200, Blocks.AIR, "Aluminum Leggings"),
	ALUMINUM_GREAVES(136, "armor/aluminum_greaves", 3, 1, 1, 0.175, 200, Blocks.AIR, "Aluminum Greaves"),
	LEAD_HELMET(137, "armor/lead_helmet", 2, 1, 1, 0.175, 200, Blocks.AIR, "Lead Helmet"),
	LEAD_CHESTPLATE(138, "armor/lead_chestplate", 4, 1, 1, 0.175, 200, Blocks.AIR, "Lead Chestplate"),
	LEAD_LEGGINGS(139, "armor/lead_leggings", 3, 1, 1, 0.175, 200, Blocks.AIR, "Lead Leggings"),
	LEAD_GREAVES(140, "armor/lead_greaves", 1, 1, 1, 0.175, 200, Blocks.AIR, "Lead Greaves"),
	ZYTHIUM_HELMET(141, "armor/zythium_helmet", 10, 1, 1, 0.175, null, Blocks.AIR, "Zythium Helmet"),
	ZYTHIUM_CHESTPLATE(142, "armor/zythium_chestplate", 18, 1, 1, 0.175, null, Blocks.AIR, "Zythium Chestplate"),
	ZYTHIUM_LEGGINGS(143, "armor/zythium_leggings", 14, 1, 1, 0.175, null, Blocks.AIR, "Zythium Leggings"),
	ZYTHIUM_GREAVES(144, "armor/zythium_greaves", 8, 1, 1, 0.175, null, Blocks.AIR, "Zythium Greaves"),
	ALUMINUM_PICK(145, "tools/aluminum_pick", 0, 1, 7, 0.35, 200, Blocks.AIR, "Aluminum Pick"),
	ALUMINUM_AXE(146, "tools/aluminum_axe", 0, 1, 10, 0.35, 200, Blocks.AIR, "Aluminum Axe"),
	ALUMINUM_SWORD(147, "tools/aluminum_sword", 0, 1, 27, 0.245, 100, Blocks.AIR, "Aluminum Sword"),
	LEAD_PICK(148, "tools/lead_pick", 0, 1, 4, 0.13, 2400, Blocks.AIR, "Lead Pick"),
	LEAD_AXE(149, "tools/lead_axe", 0, 1, 5, 0.13, 2400, Blocks.AIR, "Lead Axe"),
	LEAD_SWORD(150, "tools/lead_sword", 0, 1, 9, 0.115, 1600, Blocks.AIR, "Lead Sword"),
	ZINC_CHEST(151, "machines/zinc_chest", 0, 100, 1, 0.175, null, Blocks.ZINC_CHEST, "Zinc Chest"),
	RHYMESTONE_CHEST(152, "machines/rhymestone_chest", 0, 100, 1, 0.175, null, Blocks.RHYMESTONE_CHEST, "Rhymestone Chest"),
	OBDURITE_CHEST(153, "machines/obdurite_chest", 0, 100, 1, 0.175, null, Blocks.OBDURITE_CHEST, "Obdurite Chest"),
	WOODEN_PICK(154, "tools/wooden_pick", 0, 1, 1, 0.1, 200, Blocks.AIR, "Wooden Pick"),
	WOODEN_AXE(155, "tools/wooden_axe", 0, 1, 1, 0.1, 200, Blocks.AIR, "Wooden Axe"),
	WOODEN_SWORD(156, "tools/wooden_sword", 0, 1, 3, 0.1, 100, Blocks.AIR, "Wooden Sword"),
	STONE_PICK(157, "tools/stone_pick", 0, 1, 1, 0.11, 300, Blocks.AIR, "Stone Pick"),
	STONE_AXE(158, "tools/stone_axe", 0, 1, 2, 0.11, 300, Blocks.AIR, "Stone Axe"),
	STONE_SWORD(159, "tools/stone_sword", 0, 1, 4, 0.105, 150, Blocks.AIR, "Stone Sword"),
	BARK(160, "goo/bark", 0, 100, 1, 0.175, null, Blocks.AIR, "Bark"),
	COBBLESTONE(161, "blocks/cobblestone", 0, 100, 1, 0.175, null, Blocks.COBBLESTONE, "Cobblestone"),
	CHISELED_STONE(162, "blocks/chiseled_stone", 0, 100, 1, 0.175, null, Blocks.CHISELED_STONE, "Chiseled Stone"),
	CHISELED_COBBLESTONE(163, "blocks/chiseled_cobblestone", 0, 100, 1, 0.175, null, Blocks.CHISELED_COBBLESTONE, "Chiseled Cobblestone"),
	STONE_BRICKS(164, "blocks/stone_bricks", 0, 100, 1, 0.175, null, Blocks.STONE_BRICKS, "Stone Bricks"),
	CLAY(165, "blocks/clay", 0, 100, 1, 0.175, null, Blocks.CLAY, "Clay Bricks"),
	CLAY_BRICKS(166, "blocks/clay_bricks", 0, 100, 1, 0.175, null, Blocks.CLAY_BRICKS, "Clay"),
	VARNISH(167, "potions/varnish", 0, 100, 1, 0.175, null, Blocks.AIR, "Varnish"),
	VARNISHED_WOOD(168, "blocks/varnished_wood", 0, 100, 1, 0.175, null, Blocks.VARNISHED_WOOD, "Varnished Wood"),
	MAGNETITE_PICK(169, "tools/magnetite_pick", 0, 1, 1, 0.25, 1200, Blocks.AIR, "Magnetite Pick"),
	MAGNETITE_AXE(170, "tools/magnetite_axe", 0, 1, 1, 0.25, 1200, Blocks.AIR, "Magnetite Axe"),
	MAGNETITE_SWORD(171, "tools/magnetite_sword", 0, 1, 1, 0.175, 1200, Blocks.AIR, "Magnetite Sword"),
	IRRADIUM_PICK(172, "tools/irradium_pick", 0, 1, 1, 0.35, 4000, Blocks.AIR, "Irradium Pick"),
	IRRADIUM_AXE(173, "tools/irradium_axe", 0, 1, 1, 0.35, 4000, Blocks.AIR, "Irradium Axe"),
	IRRADIUM_SWORD(174, "tools/irradium_sword", 0, 1, 1, 0.245, 4000, Blocks.AIR, "Irradium Sword"),
	ZYTHIUM_WIRE(175, "wires/zythium_wire", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_WIRE, "Zythium Wire"),
	ZYTHIUM_TORCH(176, "torches/zythium_torch", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_TORCH, "Zythium Torch"),
	ZYTHIUM_LAMP(177, "blocks/zythium_lamp", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_LAMP, "Zythium Lamp"),
	LEVER(178, "misc/lever", 0, 100, 1, 0.175, null, Blocks.LEVER, "Lever"),
	CHARCOAL(179, "dust/charcoal", 0, 100, 1, 0.175, null, Blocks.AIR, "Charcoal"),
	ZYTHIUM_AMPLIFIER(180, "wires/zythium_amplifier", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_AMPLIFIER_RIGHT, "Zythium Amplifier"),
	ZYTHIUM_INVERTER(181, "wires/zythium_inverter", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_INVERTER_RIGHT, "Zythium Inverter"),
	BUTTON(182, "misc/button", 0, 100, 1, 0.175, null, Blocks.BUTTON_LEFT, "Button"),
	WOODEN_PRESSURE_PLATE(183, "misc/wooden_pressure_plate", 0, 100, 1, 0.175, null, Blocks.WOODEN_PRESSURE_PLATE, "Wooden Pressure Plate"),
	STONE_PRESSURE_PLATE(184, "misc/stone_pressure_plate", 0, 100, 1, 0.175, null, Blocks.STONE_PRESSURE_PLATE, "Stone Pressure Plate"),
	ZYTHIUM_PRESSURE_PLATE(185, "misc/zythium_pressure_plate", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_PRESSURE_PLATE, "Zythium Pressure Plate"),
	ZYTHIUM_DELAYER_1(186, "wires/zythium_delayer_1", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT, "Zythium Delayer"),
	ZYTHIUM_DELAYER_2(187, "wires/zythium_delayer_2", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT, "Zythium Delayer"),
	ZYTHIUM_DELAYER_4(188, "wires/zythium_delayer_4", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT, "Zythium Delayer"),
	ZYTHIUM_DELAYER_8(189, "wires/zythium_delayer_8", 0, 100, 1, 0.175, null, Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT, "Zythium Delayer"),
	WRENCH(190, "tools/wrench", 0, 1, 1, 0.175, 400, Blocks.AIR, "Wrench");

    int index;
    String fileName;
    int armor;
    int maxStacks;
    int damage;
    double speed;
    Integer durability;
    Blocks blocks;
    String uiName;

    private static final Map<Integer, Items> ITEMS_MAP;

    static {
        ITEMS_MAP = new HashMap<>();
        for (Items item : Items.values()) {
            ITEMS_MAP.put(item.getIndex(), item);
        }
    }

    public static Items findByIndex(int index) {
        return ITEMS_MAP.get(index);
    }
}
