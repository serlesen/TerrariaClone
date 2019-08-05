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
    EMPTY(0, "air"),
    DIRT(1, "blocks/dirt"),
    STONE(2, "blocks/stone"),
    COPPER_ORE(3, "ores/copper_ore"),
    IRON_ORE(4, "ores/iron_ore"),
    SILVER_ORE(5, "ores/silver_ore"),
    GOLD_ORE(6, "ores/gold_ore"),
    COPPER_PICK(7, "tools/copper_pick"),
    IRON_PICK(8, "tools/iron_pick"),
    SILVER_PICK(9, "tools/silver_pick"),
    GOLD_PICK(10, "tools/gold_pick"),
    COPPER_AXE(11, "tools/copper_axe"),
    IRON_AXE(12, "tools/iron_axe"),
    SILVER_AXE(13, "tools/silver_axe"),
    GOLD_AXE(14, "tools/gold_axe"),
    WOOD(15, "blocks/wood"),
    COPPER_SWORD(16, "tools/copper_sword"),
    IRON_SWORD(17, "tools/iron_sword"),
    SILVER_SWORD(18, "tools/silver_sword"),
    GOLD_SWORD(19, "tools/gold_sword"),
    WORKBENCH(20, "machines/workbench"),
    WOODEN_CHEST(21, "machines/wooden_chest"),
    STONE_CHEST(22, "machines/stone_chest"),
    COPPER_CHEST(23, "machines/copper_chest"),
    IRON_CHEST(24, "machines/iron_chest"),
    SILVER_CHEST(25, "machines/silver_chest"),
    GOLD_CHEST(26, "machines/gold_chest"),
    FURNACE(27, "machines/furnace"),
    COAL(28, "ores/coal"),
    COPPER_INGOT(29, "ingots/copper_ingot"),
    IRON_INGOT(30, "ingots/iron_ingot"),
    SILVER_INGOT(31, "ingots/silver_ingot"),
    GOLD_INGOT(32, "ingots/gold_ingot"),
    STONE_LIGHTER(33, "tools/stone_lighter"),
    LUMENSTONE(34, "ores/lumenstone"),
    WOODEN_TORCH(35, "torches/wooden_torch"),
    COAL_TORCH(36, "torches/coal_torch"),
    LUMENSTONE_TORCH(37, "torches/lumenstone_torch"),
    ZINC_ORE(38, "ores/zinc_ore"),
    RHYMESTONE_ORE(39, "ores/rhymestone_ore"),
    OBDURITE_ORE(40, "ores/obdurite_ore"),
    ALUMINUM_ORE(41, "ores/aluminum_ore"),
    LEAD_ORE(42, "ores/lead_ore"),
    URANIUM_ORE(43, "ores/uranium_ore"),
    ZYTHIUM_ORE(44, "ores/zythium_ore"),
    SILICON_ORE(45, "ores/silicon_ore"),
    IRRADIUM_ORE(46, "ores/irradium_ore"),
    NULLSTONE(47, "ores/nullstone"),
    MELTSTONE(48, "ores/meltstone"),
    SKYSTONE(49, "ores/skystone"),
    MAGNETITE_ORE(50, "ores/magnetite_ore"),
    ZINC_PICK(51, "tools/zinc_pick"),
    ZINC_AXE(52, "tools/zinc_axe"),
    ZINC_SWORD(53, "tools/zinc_sword"),
    RHYMESTONE_PICK(54, "tools/rhymestone_pick"),
    RHYMESTONE_AXE(55, "tools/rhymestone_axe"),
    RHYMESTONE_SWORD(56, "tools/rhymestone_sword"),
    OBDURITE_PICK(57, "tools/obdurite_pick"),
    OBDURITE_AXE(58, "tools/obdurite_axe"),
    OBDURITE_SWORD(59, "tools/obdurite_sword"),
    ZINC_INGOT(60, "ingots/zinc_ingot"),
    RHYMESTONE_INGOT(61, "ingots/rhymestone_ingot"),
    OBDURITE_INGOT(62, "ingots/obdurite_ingot"),
    ALUMINUM_INGOT(63, "ingots/aluminum_ingot"),
    LEAD_INGOT(64, "ingots/lead_ingot"),
    URANIUM_BAR(65, "ingots/uranium_bar"),
    REFINED_URANIUM(66, "ingots/refined_uranium"),
    ZYTHIUM_BAR(67, "ingots/zythium_bar"),
    SILICON_BAR(68, "ingots/silicon_bar"),
    IRRADIUM_INGOT(69, "ingots/irradium_ingot"),
    NULLSTONE_BAR(70, "ingots/nullstone_bar"),
    MELTSTONE_BAR(71, "ingots/meltstone_bar"),
    SKYSTONE_BAR(72, "ingots/skystone_bar"),
    MAGNETITE_INGOT(73, "ingots/magnetite_ingot"),
    SAND(74, "blocks/sand"),
    SNOW(75, "blocks/snow"),
    GLASS(76, "blocks/glass"),
    SUNFLOWER_SEEDS(77, "seeds/sunflower_seeds"),
    SUNFLOWER(78, "herbs/sunflower"),
    MOONFLOWER_SEEDS(79, "seeds/moonflower_seeds"),
    MOONFLOWER(80, "herbs/moonflower"),
    DRYWEED_SEEDS(81, "seeds/dryweed_seeds"),
    DRYWEED(82, "herbs/dryweed"),
    GREENLEAF_SEEDS(83, "seeds/greenleaf_seeds"),
    GREENLEAF(84, "herbs/greenleaf"),
    FROSTLEAF_SEEDS(85, "seeds/frostleaf_seeds"),
    FROSTLEAF(86, "herbs/frostleaf"),
    CAVEROOT_SEEDS(87, "seeds/caveroot_seeds"),
    CAVEROOT(88, "herbs/caveroot"),
    SKYBLOSSOM_SEEDS(89, "seeds/skyblossom_seeds"),
    SKYBLOSSOM(90, "herbs/skyblossom"),
    VOID_ROT_SEEDS(91, "seeds/void_rot_seeds"),
    VOID_ROT(92, "herbs/void_rot"),
    MUD(93, "blocks/mud"),
    SANDSTONE(94, "blocks/sandstone"),
    MARSHLEAF_SEEDS(95, "seeds/marshleaf_seeds"),
    MARSHLEAF(96, "herbs/marshleaf"),
    BLUE_GOO(97, "goo/blue_goo"),
    GREEN_GOO(98, "goo/green_goo"),
    RED_GOO(99, "goo/red_goo"),
    YELLOW_GOO(100, "goo/yellow_goo"),
    BLACK_GOO(101, "goo/black_goo"),
    WHITE_GOO(102, "goo/white_goo"),
    ASTRAL_SHARD(103, "goo/astral_shard"),
    ROTTEN_CHUNK(104, "goo/rotten_chunk"),
    COPPER_HELMET(105, "armor/copper_helmet"),
    COPPER_CHESTPLATE(106, "armor/copper_chestplate"),
    COPPER_LEGGINGS(107, "armor/copper_leggings"),
    COPPER_GREAVES(108, "armor/copper_greaves"),
    IRON_HELMET(109, "armor/iron_helmet"),
    IRON_CHESTPLATE(110, "armor/iron_chestplate"),
    IRON_LEGGINGS(111, "armor/iron_leggings"),
    IRON_GREAVES(112, "armor/iron_greaves"),
    SILVER_HELMET(113, "armor/silver_helmet"),
    SILVER_CHESTPLATE(114, "armor/silver_chestplate"),
    SILVER_LEGGINGS(115, "armor/silver_leggings"),
    SILVER_GREAVES(116, "armor/silver_greaves"),
    GOLD_HELMET(117, "armor/gold_helmet"),
    GOLD_CHESTPLATE(118, "armor/gold_chestplate"),
    GOLD_LEGGINGS(119, "armor/gold_leggings"),
    GOLD_GREAVES(120, "armor/gold_greaves"),
    ZINC_HELMET(121, "armor/zinc_helmet"),
    ZINC_CHESTPLATE(122, "armor/zinc_chestplate"),
    ZINC_LEGGINGS(123, "armor/zinc_leggings"),
    ZINC_GREAVES(124, "armor/zinc_greaves"),
    RHYMESTONE_HELMET(125, "armor/rhymestone_helmet"),
    RHYMESTONE_CHESTPLATE(126, "armor/rhymestone_chestplate"),
    RHYMESTONE_LEGGINGS(127, "armor/rhymestone_leggings"),
    RHYMESTONE_GREAVES(128, "armor/rhymestone_greaves"),
    OBDURITE_HELMET(129, "armor/obdurite_helmet"),
    OBDURITE_CHESTPLATE(130, "armor/obdurite_chestplate"),
    OBDURITE_LEGGINGS(131, "armor/obdurite_leggings"),
    OBDURITE_GREAVES(132, "armor/obdurite_greaves"),
    ALUMINUM_HELMET(133, "armor/aluminum_helmet"),
    ALUMINUM_CHESTPLATE(134, "armor/aluminum_chestplate"),
    ALUMINUM_LEGGINGS(135, "armor/aluminum_leggings"),
    ALUMINUM_GREAVES(136, "armor/aluminum_greaves"),
    LEAD_HELMET(137, "armor/lead_helmet"),
    LEAD_CHESTPLATE(138, "armor/lead_chestplate"),
    LEAD_LEGGINGS(139, "armor/lead_leggings"),
    LEAD_GREAVES(140, "armor/lead_greaves"),
    ZYTHIUM_HELMET(141, "armor/zythium_helmet"),
    ZYTHIUM_CHESTPLATE(142, "armor/zythium_chestplate"),
    ZYTHIUM_LEGGINGS(143, "armor/zythium_leggings"),
    ZYTHIUM_GREAVES(144, "armor/zythium_greaves"),
    ALUMINUM_PICK(145, "tools/aluminum_pick"),
    ALUMINUM_AXE(146, "tools/aluminum_axe"),
    ALUMINUM_SWORD(147, "tools/aluminum_sword"),
    LEAD_PICK(148, "tools/lead_pick"),
    LEAD_AXE(149, "tools/lead_axe"),
    LEAD_SWORD(150, "tools/lead_sword"),
    ZINC_CHEST(151, "machines/zinc_chest"),
    RHYMESTONE_CHEST(152, "machines/rhymestone_chest"),
    OBDURITE_CHEST(153, "machines/obdurite_chest"),
    WOODEN_PICK(154, "tools/wooden_pick"),
    WOODEN_AXE(155, "tools/wooden_axe"),
    WOODEN_SWORD(156, "tools/wooden_sword"),
    STONE_PICK(157, "tools/stone_pick"),
    STONE_AXE(158, "tools/stone_axe"),
    STONE_SWORD(159, "tools/stone_sword"),
    BARK(160, "goo/bark"),
    COBBLESTONE(161, "blocks/cobblestone"),
    CHISELED_STONE(162, "blocks/chiseled_stone"),
    CHISELED_COBBLESTONE(163, "blocks/chiseled_cobblestone"),
    STONE_BRICKS(164, "blocks/stone_bricks"),
    CLAY(165, "blocks/clay"),
    CLAY_BRICKS(166, "blocks/clay_bricks"),
    VARNISH(167, "potions/varnish"),
    VARNISHED_WOOD(168, "blocks/varnished_wood"),
    MAGNETITE_PICK(169, "tools/magnetite_pick"),
    MAGNETITE_AXE(170, "tools/magnetite_axe"),
    MAGNETITE_SWORD(171, "tools/magnetite_sword"),
    IRRADIUM_PICK(172, "tools/irradium_pick"),
    IRRADIUM_AXE(173, "tools/irradium_axe"),
    IRRADIUM_SWORD(174, "tools/irradium_sword"),
    ZYTHIUM_WIRE(175, "wires/zythium_wire"),
    ZYTHIUM_TORCH(176, "torches/zythium_torch"),
    ZYTHIUM_LAMP(177, "blocks/zythium_lamp"),
    LEVER(178, "misc/lever"),
    CHARCOAL(179, "dust/charcoal"),
    ZYTHIUM_AMPLIFIER(180, "wires/zythium_amplifier"),
    ZYTHIUM_INVERTER(181, "wires/zythium_inverter"),
    BUTTON(182, "misc/button"),
    WOODEN_PRESSURE_PLATE(183, "misc/wooden_pressure_plate"),
    STONE_PRESSURE_PLATE(184, "misc/stone_pressure_plate"),
    ZYTHIUM_PRESSURE_PLATE(185, "misc/zythium_pressure_plate"),
    ZYTHIUM_DELAYER_1(186, "wires/zythium_delayer_1"),
    ZYTHIUM_DELAYER_2(187, "wires/zythium_delayer_2"),
    ZYTHIUM_DELAYER_4(188, "wires/zythium_delayer_4"),
    ZYTHIUM_DELAYER_8(189, "wires/zythium_delayer_8"),
    WRENCH(190, "tools/wrench");

    int index;
    String fileName;

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
