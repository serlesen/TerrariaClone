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
	EMPTY(0, "air", 0, 100, 1, 0.175, null, 0, "Air"),
	DIRT(1, "blocks/dirt", 0, 100, 1, 0.175, null, 1, "Dirt"),
	STONE(2, "blocks/stone", 0, 100, 1, 0.175, null, 2, "Stone"),
	COPPER_ORE(3, "ores/copper_ore", 0, 100, 1, 0.175, null, 3, "Copper Ore"),
	IRON_ORE(4, "ores/iron_ore", 0, 100, 1, 0.175, null, 4, "Iron Ore"),
	SILVER_ORE(5, "ores/silver_ore", 0, 100, 1, 0.175, null, 5, "Silver Ore"),
	GOLD_ORE(6, "ores/gold_ore", 0, 100, 1, 0.175, null, 6, "Gold Ore"),
	COPPER_PICK(7, "tools/copper_pick", 0, 1, 2, 0.12, 400, 0, "Copper Pick"),
	IRON_PICK(8, "tools/iron_pick", 0, 1, 3, 0.13, 500, 0, "Iron Pick"),
	SILVER_PICK(9, "tools/silver_pick", 0, 1, 3, 0.14, 600, 0, "Silver Pick"),
	GOLD_PICK(10, "tools/gold_pick", 0, 1, 4, 0.15, 800, 0, "Gold Pick"),
	COPPER_AXE(11, "tools/copper_axe", 0, 1, 3, 0.12, 400, 0, "Copper Axe"),
	IRON_AXE(12, "tools/iron_axe", 0, 1, 4, 0.13, 500, 0, "Iron Axe"),
	SILVER_AXE(13, "tools/silver_axe", 0, 1, 5, 0.14, 600, 0, "Silver Axe"),
	GOLD_AXE(14, "tools/gold_axe", 0, 1, 6, 0.15, 800, 0, "Gold Axe"),
	WOOD(15, "blocks/wood", 0, 100, 1, 0.175, null, 7, "Wood"),
	COPPER_SWORD(16, "tools/copper_sword", 0, 1, 5, 0.11, 250, 0, "Copper Sword"),
	IRON_SWORD(17, "tools/iron_sword", 0, 1, 8, 0.115, 300, 0, "Iron Sword"),
	SILVER_SWORD(18, "tools/silver_sword", 0, 1, 13, 0.12, 400, 0, "Silver Sword"),
	GOLD_SWORD(19, "tools/gold_sword", 0, 1, 18, 0.125, 600, 0, "Gold Sword"),
	WORKBENCH(20, "machines/workbench", 0, 100, 1, 0.175, null, 8, "Workbench"),
	WOODEN_CHEST(21, "machines/wooden_chest", 0, 100, 1, 0.175, null, 9, "Wooden Chest"),
	STONE_CHEST(22, "machines/stone_chest", 0, 100, 1, 0.175, null, 10, "Stone Chest"),
	COPPER_CHEST(23, "machines/copper_chest", 0, 100, 1, 0.175, null, 11, "Copper Chest"),
	IRON_CHEST(24, "machines/iron_chest", 0, 100, 1, 0.175, null, 12, "Iron Chest"),
	SILVER_CHEST(25, "machines/silver_chest", 0, 100, 1, 0.175, null, 13, "Silver Chest"),
	GOLD_CHEST(26, "machines/gold_chest", 0, 100, 1, 0.175, null, 14, "Gold Chest"),
	FURNACE(27, "machines/furnace", 0, 100, 1, 0.175, null, 17, "Furnace"),
	COAL(28, "ores/coal", 0, 100, 1, 0.175, null, 18, "Coal"),
	COPPER_INGOT(29, "ingots/copper_ingot", 0, 100, 1, 0.175, null, 0, "Copper Ingot"),
	IRON_INGOT(30, "ingots/iron_ingot", 0, 100, 1, 0.175, null, 0, "Iron Ingot"),
	SILVER_INGOT(31, "ingots/silver_ingot", 0, 100, 1, 0.175, null, 0, "Silver Ingot"),
	GOLD_INGOT(32, "ingots/gold_ingot", 0, 100, 1, 0.175, null, 0, "Gold Ingot"),
	STONE_LIGHTER(33, "tools/stone_lighter", 0, 1, 1, 0.125, 100, 0, "Stone Lighter"),
	LUMENSTONE(34, "ores/lumenstone", 0, 100, 1, 0.175, null, 19, "Lumenstone"),
	WOODEN_TORCH(35, "torches/wooden_torch", 0, 100, 1, 0.175, null, 20, "Wooden Torch"),
	COAL_TORCH(36, "torches/coal_torch", 0, 100, 1, 0.175, null, 21, "Coal Torch"),
	LUMENSTONE_TORCH(37, "torches/lumenstone_torch", 0, 100, 1, 0.175, null, 22, "Lumenstone Torch"),
	ZINC_ORE(38, "ores/zinc_ore", 0, 100, 1, 0.175, null, 31, "Zinc Ore"),
	RHYMESTONE_ORE(39, "ores/rhymestone_ore", 0, 100, 1, 0.175, null, 32, "Rhymestone Ore"),
	OBDURITE_ORE(40, "ores/obdurite_ore", 0, 100, 1, 0.175, null, 33, "Obdurite Ore"),
	ALUMINUM_ORE(41, "ores/aluminum_ore", 0, 100, 1, 0.175, null, 34, "Aluminum Ore"),
	LEAD_ORE(42, "ores/lead_ore", 0, 100, 1, 0.175, null, 35, "Lead Ore"),
	URANIUM_ORE(43, "ores/uranium_ore", 0, 100, 1, 0.175, null, 36, "Uranium Ore"),
	ZYTHIUM_ORE(44, "ores/zythium_ore", 0, 100, 1, 0.175, null, 37, "Zythium Ore"),
	SILICON_ORE(45, "ores/silicon_ore", 0, 100, 1, 0.175, null, 39, "Silicon Ore"),
	IRRADIUM_ORE(46, "ores/irradium_ore", 0, 100, 1, 0.175, null, 40, "Irradium Ore"),
	NULLSTONE(47, "ores/nullstone", 0, 100, 1, 0.175, null, 41, "Nullstone"),
	MELTSTONE(48, "ores/meltstone", 0, 100, 1, 0.175, null, 42, "Meltstone"),
	SKYSTONE(49, "ores/skystone", 0, 100, 1, 0.175, null, 43, "Skystone"),
	MAGNETITE_ORE(50, "ores/magnetite_ore", 0, 100, 1, 0.175, null, 44, "Magnetite Ore"),
	ZINC_PICK(51, "tools/zinc_pick", 0, 1, 6, 0.16, 1100, 0, "Zinc Pick"),
	ZINC_AXE(52, "tools/zinc_axe", 0, 1, 9, 0.16, 1100, 0, "Zinc Axe"),
	ZINC_SWORD(53, "tools/zinc_sword", 0, 1, 24, 0.13, 950, 0, "Zinc Sword"),
	RHYMESTONE_PICK(54, "tools/rhymestone_pick", 0, 1, 8, 0.17, 1350, 0, "Rhymestone Pick"),
	RHYMESTONE_AXE(55, "tools/rhymestone_axe", 0, 1, 11, 0.17, 1350, 0, "Rhymestone Axe"),
	RHYMESTONE_SWORD(56, "tools/rhymestone_sword", 0, 1, 30, 0.135, null, 0, "Rhymestone Sword"),
	OBDURITE_PICK(57, "tools/obdurite_pick", 0, 1, 20, 0.18, 1600, 0, "Obdurite Pick"),
	OBDURITE_AXE(58, "tools/obdurite_axe", 0, 1, 30, 0.18, 1600, 0, "Obdurite Axe"),
	OBDURITE_SWORD(59, "tools/obdurite_sword", 0, 1, 75, 0.14, 1600, 0, "Obdurite Sword"),
	ZINC_INGOT(60, "ingots/zinc_ingot", 0, 100, 1, 0.175, null, 0, "Zinc Ingot"),
	RHYMESTONE_INGOT(61, "ingots/rhymestone_ingot", 0, 100, 1, 0.175, null, 0, "Rhymestone Ingot"),
	OBDURITE_INGOT(62, "ingots/obdurite_ingot", 0, 100, 1, 0.175, null, 0, "Obdurite Ingot"),
	ALUMINUM_INGOT(63, "ingots/aluminum_ingot", 0, 100, 1, 0.175, null, 0, "Aluminum Ingot"),
	LEAD_INGOT(64, "ingots/lead_ingot", 0, 100, 1, 0.175, null, 0, "Lead Ingot"),
	URANIUM_BAR(65, "ingots/uranium_bar", 0, 100, 1, 0.175, null, 0, "Uranium Bar"),
	REFINED_URANIUM(66, "ingots/refined_uranium", 0, 100, 1, 0.175, null, 0, "Refined Uranium"),
	ZYTHIUM_BAR(67, "ingots/zythium_bar", 0, 100, 1, 0.175, null, 0, "Zythium Bar"),
	SILICON_BAR(68, "ingots/silicon_bar", 0, 100, 1, 0.175, null, 0, "Silicon Bar"),
	IRRADIUM_INGOT(69, "ingots/irradium_ingot", 0, 100, 1, 0.175, null, 0, "Irradium Ingot"),
	NULLSTONE_BAR(70, "ingots/nullstone_bar", 0, 100, 1, 0.175, null, 0, "Nullstone Bar"),
	MELTSTONE_BAR(71, "ingots/meltstone_bar", 0, 100, 1, 0.175, null, 0, "Meltstone Bar"),
	SKYSTONE_BAR(72, "ingots/skystone_bar", 0, 100, 1, 0.175, null, 0, "Skystone Bar"),
	MAGNETITE_INGOT(73, "ingots/magnetite_ingot", 0, 100, 1, 0.175, null, 0, "Magnetite Ingot"),
	SAND(74, "blocks/sand", 0, 100, 1, 0.175, null, 45, "Sand"),
	SNOW(75, "blocks/snow", 0, 100, 1, 0.175, null, 46, "Snow"),
	GLASS(76, "blocks/glass", 0, 100, 1, 0.175, null, 47, "Glass"),
	SUNFLOWER_SEEDS(77, "seeds/sunflower_seeds", 0, 100, 1, 0.175, null, 48, "Sunflower Seeds"),
	SUNFLOWER(78, "herbs/sunflower", 0, 100, 1, 0.175, null, 0, "Sunflower"),
	MOONFLOWER_SEEDS(79, "seeds/moonflower_seeds", 0, 100, 1, 0.175, null, 51, "Moonflower Seeds"),
	MOONFLOWER(80, "herbs/moonflower", 0, 100, 1, 0.175, null, 0, "Moonflower"),
	DRYWEED_SEEDS(81, "seeds/dryweed_seeds", 0, 100, 1, 0.175, null, 54, "Dryweed Seeds"),
	DRYWEED(82, "herbs/dryweed", 0, 100, 1, 0.175, null, 0, "Dryweed"),
	GREENLEAF_SEEDS(83, "seeds/greenleaf_seeds", 0, 100, 1, 0.175, null, 57, "Greenleaf Seeds"),
	GREENLEAF(84, "herbs/greenleaf", 0, 100, 1, 0.175, null, 0, "Greenleaf"),
	FROSTLEAF_SEEDS(85, "seeds/frostleaf_seeds", 0, 100, 1, 0.175, null, 60, "Frostleaf Seeds"),
	FROSTLEAF(86, "herbs/frostleaf", 0, 100, 1, 0.175, null, 0, "Frostleaf"),
	CAVEROOT_SEEDS(87, "seeds/caveroot_seeds", 0, 100, 1, 0.175, null, 63, "Caveroot Seeds"),
	CAVEROOT(88, "herbs/caveroot", 0, 100, 1, 0.175, null, 0, "Caveroot"),
	SKYBLOSSOM_SEEDS(89, "seeds/skyblossom_seeds", 0, 100, 1, 0.175, null, 66, "Skyblossom Seeds"),
	SKYBLOSSOM(90, "herbs/skyblossom", 0, 100, 1, 0.175, null, 0, "Skyblossom"),
	VOID_ROT_SEEDS(91, "seeds/void_rot_seeds", 0, 100, 1, 0.175, null, 69, "Void Rot Seeds"),
	VOID_ROT(92, "herbs/void_rot", 0, 100, 1, 0.175, null, 0, "Void Rot"),
	MUD(93, "blocks/mud", 0, 100, 1, 0.175, null, 75, "Mud"),
	SANDSTONE(94, "blocks/sandstone", 0, 100, 1, 0.175, null, 76, "Sandstone"),
	MARSHLEAF_SEEDS(95, "seeds/marshleaf_seeds", 0, 100, 1, 0.175, null, 77, "Marshleaf Seeds"),
	MARSHLEAF(96, "herbs/marshleaf", 0, 100, 1, 0.175, null, 0, "Marshleaf"),
	BLUE_GOO(97, "goo/blue_goo", 0, 100, 1, 0.175, null, 0, "Blue Goo"),
	GREEN_GOO(98, "goo/green_goo", 0, 100, 1, 0.175, null, 0, "Green Goo"),
	RED_GOO(99, "goo/red_goo", 0, 100, 1, 0.175, null, 0, "Red Goo"),
	YELLOW_GOO(100, "goo/yellow_goo", 0, 100, 1, 0.175, null, 0, "Yellow Goo"),
	BLACK_GOO(101, "goo/black_goo", 0, 100, 1, 0.175, null, 0, "Black Goo"),
	WHITE_GOO(102, "goo/white_goo", 0, 100, 1, 0.175, null, 0, "White Goo"),
	ASTRAL_SHARD(103, "goo/astral_shard", 0, 100, 1, 0.175, null, 0, "Astral Shard"),
	ROTTEN_CHUNK(104, "goo/rotten_chunk", 0, 100, 1, 0.175, null, 0, "Rotten Chunk"),
	COPPER_HELMET(105, "armor/copper_helmet", 1, 1, 1, 0.175, 200, 0, "Copper Helmet"),
	COPPER_CHESTPLATE(106, "armor/copper_chestplate", 2, 1, 1, 0.175, 200, 0, "Copper Chestplate"),
	COPPER_LEGGINGS(107, "armor/copper_leggings", 1, 1, 1, 0.175, 200, 0, "Copper Leggings"),
	COPPER_GREAVES(108, "armor/copper_greaves", 1, 1, 1, 0.175, 200, 0, "Copper Greaves"),
	IRON_HELMET(109, "armor/iron_helmet", 1, 1, 1, 0.175, 200, 0, "Iron Helmet"),
	IRON_CHESTPLATE(110, "armor/iron_chestplate", 3, 1, 1, 0.175, 200, 0, "Iron Chestplate"),
	IRON_LEGGINGS(111, "armor/iron_leggings", 2, 1, 1, 0.175, 200, 0, "Iron Leggings"),
	IRON_GREAVES(112, "armor/iron_greaves", 1, 1, 1, 0.175, 200, 0, "Iron Greaves"),
	SILVER_HELMET(113, "armor/silver_helmet", 2, 1, 1, 0.175, 200, 0, "Silver Helmet"),
	SILVER_CHESTPLATE(114, "armor/silver_chestplate", 4, 1, 1, 0.175, 200, 0, "Silver Chestplate"),
	SILVER_LEGGINGS(115, "armor/silver_leggings", 3, 1, 1, 0.175, 200, 0, "Silver Leggings"),
	SILVER_GREAVES(116, "armor/silver_greaves", 1, 1, 1, 0.175, 200, 0, "Silver Greaves"),
	GOLD_HELMET(117, "armor/gold_helmet", 3, 1, 1, 0.175, 200, 0, "Gold Helmet"),
	GOLD_CHESTPLATE(118, "armor/gold_chestplate", 6, 1, 1, 0.175, 200, 0, "Gold Chestplate"),
	GOLD_LEGGINGS(119, "armor/gold_leggings", 5, 1, 1, 0.175, 200, 0, "Gold Leggings"),
	GOLD_GREAVES(120, "armor/gold_greaves", 2, 1, 1, 0.175, 200, 0, "Gold Greaves"),
	ZINC_HELMET(121, "armor/zinc_helmet", 4, 1, 1, 0.175, 200, 0, "Zinc Helmet"),
	ZINC_CHESTPLATE(122, "armor/zinc_chestplate", 7, 1, 1, 0.175, 200, 0, "Zinc Chestplate"),
	ZINC_LEGGINGS(123, "armor/zinc_leggings", 6, 1, 1, 0.175, 200, 0, "Zinc Leggings"),
	ZINC_GREAVES(124, "armor/zinc_greaves", 3, 1, 1, 0.175, 200, 0, "Zinc Greaves"),
	RHYMESTONE_HELMET(125, "armor/rhymestone_helmet", 5, 1, 1, 0.175, 200, 0, "Rhymestone Helmet"),
	RHYMESTONE_CHESTPLATE(126, "armor/rhymestone_chestplate", 6, 1, 1, 0.175, 200, 0, "Rhymestone Chestplate"),
	RHYMESTONE_LEGGINGS(127, "armor/rhymestone_leggings", 7, 1, 1, 0.175, 200, 0, "Rhymestone Leggings"),
	RHYMESTONE_GREAVES(128, "armor/rhymestone_greaves", 4, 1, 1, 0.175, 200, 0, "Rhymestone Greaves"),
	OBDURITE_HELMET(129, "armor/obdurite_helmet", 7, 1, 1, 0.175, 200, 0, "Obdurite Helmet"),
	OBDURITE_CHESTPLATE(130, "armor/obdurite_chestplate", 12, 1, 1, 0.175, 200, 0, "Obdurite Chestplate"),
	OBDURITE_LEGGINGS(131, "armor/obdurite_leggings", 10, 1, 1, 0.175, 200, 0, "Obdurite Leggings"),
	OBDURITE_GREAVES(132, "armor/obdurite_greaves", 6, 1, 1, 0.175, 200, 0, "Obdurite Greaves"),
	ALUMINUM_HELMET(133, "armor/aluminum_helmet", 4, 1, 1, 0.175, 200, 0, "Aluminum Helmet"),
	ALUMINUM_CHESTPLATE(134, "armor/aluminum_chestplate", 7, 1, 1, 0.175, 200, 0, "Aluminum Chestplate"),
	ALUMINUM_LEGGINGS(135, "armor/aluminum_leggings", 6, 1, 1, 0.175, 200, 0, "Aluminum Leggings"),
	ALUMINUM_GREAVES(136, "armor/aluminum_greaves", 3, 1, 1, 0.175, 200, 0, "Aluminum Greaves"),
	LEAD_HELMET(137, "armor/lead_helmet", 2, 1, 1, 0.175, 200, 0, "Lead Helmet"),
	LEAD_CHESTPLATE(138, "armor/lead_chestplate", 4, 1, 1, 0.175, 200, 0, "Lead Chestplate"),
	LEAD_LEGGINGS(139, "armor/lead_leggings", 3, 1, 1, 0.175, 200, 0, "Lead Leggings"),
	LEAD_GREAVES(140, "armor/lead_greaves", 1, 1, 1, 0.175, 200, 0, "Lead Greaves"),
	ZYTHIUM_HELMET(141, "armor/zythium_helmet", 10, 1, 1, 0.175, null, 0, "Zythium Helmet"),
	ZYTHIUM_CHESTPLATE(142, "armor/zythium_chestplate", 18, 1, 1, 0.175, null, 0, "Zythium Chestplate"),
	ZYTHIUM_LEGGINGS(143, "armor/zythium_leggings", 14, 1, 1, 0.175, null, 0, "Zythium Leggings"),
	ZYTHIUM_GREAVES(144, "armor/zythium_greaves", 8, 1, 1, 0.175, null, 0, "Zythium Greaves"),
	ALUMINUM_PICK(145, "tools/aluminum_pick", 0, 1, 7, 0.35, 200, 0, "Aluminum Pick"),
	ALUMINUM_AXE(146, "tools/aluminum_axe", 0, 1, 10, 0.35, 200, 0, "Aluminum Axe"),
	ALUMINUM_SWORD(147, "tools/aluminum_sword", 0, 1, 27, 0.245, 100, 0, "Aluminum Sword"),
	LEAD_PICK(148, "tools/lead_pick", 0, 1, 4, 0.13, 2400, 0, "Lead Pick"),
	LEAD_AXE(149, "tools/lead_axe", 0, 1, 5, 0.13, 2400, 0, "Lead Axe"),
	LEAD_SWORD(150, "tools/lead_sword", 0, 1, 9, 0.115, 1600, 0, "Lead Sword"),
	ZINC_CHEST(151, "machines/zinc_chest", 0, 100, 1, 0.175, null, 80, "Zinc Chest"),
	RHYMESTONE_CHEST(152, "machines/rhymestone_chest", 0, 100, 1, 0.175, null, 81, "Rhymestone Chest"),
	OBDURITE_CHEST(153, "machines/obdurite_chest", 0, 100, 1, 0.175, null, 82, "Obdurite Chest"),
	WOODEN_PICK(154, "tools/wooden_pick", 0, 1, 1, 0.1, 200, 0, "Wooden Pick"),
	WOODEN_AXE(155, "tools/wooden_axe", 0, 1, 1, 0.1, 200, 0, "Wooden Axe"),
	WOODEN_SWORD(156, "tools/wooden_sword", 0, 1, 3, 0.1, 100, 0, "Wooden Sword"),
	STONE_PICK(157, "tools/stone_pick", 0, 1, 1, 0.11, 300, 0, "Stone Pick"),
	STONE_AXE(158, "tools/stone_axe", 0, 1, 2, 0.11, 300, 0, "Stone Axe"),
	STONE_SWORD(159, "tools/stone_sword", 0, 1, 4, 0.105, 150, 0, "Stone Sword"),
	BARK(160, "goo/bark", 0, 100, 1, 0.175, null, 0, "Bark"),
	COBBLESTONE(161, "blocks/cobblestone", 0, 100, 1, 0.175, null, 84, "Cobblestone"),
	CHISELED_STONE(162, "blocks/chiseled_stone", 0, 100, 1, 0.175, null, 85, "Chiseled Stone"),
	CHISELED_COBBLESTONE(163, "blocks/chiseled_cobblestone", 0, 100, 1, 0.175, null, 86, "Chiseled Cobblestone"),
	STONE_BRICKS(164, "blocks/stone_bricks", 0, 100, 1, 0.175, null, 87, "Stone Bricks"),
	CLAY(165, "blocks/clay", 0, 100, 1, 0.175, null, 88, "Clay Bricks"),
	CLAY_BRICKS(166, "blocks/clay_bricks", 0, 100, 1, 0.175, null, 89, "Clay"),
	VARNISH(167, "potions/varnish", 0, 100, 1, 0.175, null, 0, "Varnish"),
	VARNISHED_WOOD(168, "blocks/varnished_wood", 0, 100, 1, 0.175, null, 90, "Varnished Wood"),
	MAGNETITE_PICK(169, "tools/magnetite_pick", 0, 1, 1, 0.25, 1200, 0, "Magnetite Pick"),
	MAGNETITE_AXE(170, "tools/magnetite_axe", 0, 1, 1, 0.25, 1200, 0, "Magnetite Axe"),
	MAGNETITE_SWORD(171, "tools/magnetite_sword", 0, 1, 1, 0.175, 1200, 0, "Magnetite Sword"),
	IRRADIUM_PICK(172, "tools/irradium_pick", 0, 1, 1, 0.35, 4000, 0, "Irradium Pick"),
	IRRADIUM_AXE(173, "tools/irradium_axe", 0, 1, 1, 0.35, 4000, 0, "Irradium Axe"),
	IRRADIUM_SWORD(174, "tools/irradium_sword", 0, 1, 1, 0.245, 4000, 0, "Irradium Sword"),
	ZYTHIUM_WIRE(175, "wires/zythium_wire", 0, 100, 1, 0.175, null, 94, "Zythium Wire"),
	ZYTHIUM_TORCH(176, "torches/zythium_torch", 0, 100, 1, 0.175, null, 100, "Zythium Torch"),
	ZYTHIUM_LAMP(177, "blocks/zythium_lamp", 0, 100, 1, 0.175, null, 103, "Zythium Lamp"),
	LEVER(178, "misc/lever", 0, 100, 1, 0.175, null, 105, "Lever"),
	CHARCOAL(179, "dust/charcoal", 0, 100, 1, 0.175, null, 0, "Charcoal"),
	ZYTHIUM_AMPLIFIER(180, "wires/zythium_amplifier", 0, 100, 1, 0.175, null, 111, "Zythium Amplifier"),
	ZYTHIUM_INVERTER(181, "wires/zythium_inverter", 0, 100, 1, 0.175, null, 119, "Zythium Inverter"),
	BUTTON(182, "misc/button", 0, 100, 1, 0.175, null, 127, "Button"),
	WOODEN_PRESSURE_PLATE(183, "misc/wooden_pressure_plate", 0, 100, 1, 0.175, null, 131, "Wooden Pressure Plate"),
	STONE_PRESSURE_PLATE(184, "misc/stone_pressure_plate", 0, 100, 1, 0.175, null, 133, "Stone Pressure Plate"),
	ZYTHIUM_PRESSURE_PLATE(185, "misc/zythium_pressure_plate", 0, 100, 1, 0.175, null, 135, "Zythium Pressure Plate"),
	ZYTHIUM_DELAYER_1(186, "wires/zythium_delayer_1", 0, 100, 1, 0.175, null, 137, "Zythium Delayer"),
	ZYTHIUM_DELAYER_2(187, "wires/zythium_delayer_2", 0, 100, 1, 0.175, null, 145, "Zythium Delayer"),
	ZYTHIUM_DELAYER_4(188, "wires/zythium_delayer_4", 0, 100, 1, 0.175, null, 153, "Zythium Delayer"),
	ZYTHIUM_DELAYER_8(189, "wires/zythium_delayer_8", 0, 100, 1, 0.175, null, 161, "Zythium Delayer"),
	WRENCH(190, "tools/wrench", 0, 1, 1, 0.175, 400, 0, "Wrench");

    int index;
    String fileName;
    int armor;
    int maxStacks;
    int damage;
    double speed;
    Integer durability;
    int blockIndex;
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
