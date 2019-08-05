package com.sergio.refacto.init;

import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Items;

import static com.sergio.refacto.dto.Constants.*;

public class UIBlocksInitializer {

    private static final String[] UI_ITEMS = {"Air", "Dirt", "Stone",
            "Copper Ore", "Iron Ore", "Silver Ore", "Gold Ore",
            "Copper Pick", "Iron Pick", "Silver Pick", "Gold Pick",
            "Copper Axe", "Iron Axe", "Silver Axe", "Gold Axe",
            "Wood", "Copper Sword", "Iron Sword", "Silver Sword", "Gold Sword",
            "Workbench",
            "Wooden Chest", "Stone Chest",
            "Copper Chest", "Iron Chest", "Silver Chest", "Gold Chest",
            "Furnace", "Coal",
            "Copper Ingot", "Iron Ingot", "Silver Ingot", "Gold Ingot",
            "Stone Lighter", "Lumenstone",
            "Wooden Torch", "Coal Torch", "Lumenstone Torch",
            "Zinc Ore", "Rhymestone Ore", "Obdurite Ore",
            "Aluminum Ore", "Lead Ore", "Uranium Ore",
            "Zythium Ore", "Silicon Ore",
            "Irradium Ore", "Nullstone", "Meltstone", "Skystone",
            "Magnetite Ore",
            "Zinc Pick", "Zinc Axe", "Zinc Sword",
            "Rhymestone Pick", "Rhymestone Axe", "Rhymestone Sword",
            "Obdurite Pick", "Obdurite Axe", "Obdurite Sword",
            "Zinc Ingot", "Rhymestone Ingot", "Obdurite Ingot",
            "Aluminum Ingot", "Lead Ingot", "Uranium Bar", "Refined Uranium",
            "Zythium Bar", "Silicon Bar",
            "Irradium Ingot", "Nullstone Bar", "Meltstone Bar", "Skystone Bar",
            "Magnetite Ingot",
            "Sand", "Snow", "Glass",
            "Sunflower Seeds", "Sunflower", "Moonflower Seeds", "Moonflower",
            "Dryweed Seeds", "Dryweed", "Greenleaf Seeds", "Greenleaf",
            "Frostleaf Seeds", "Frostleaf", "Caveroot Seeds", "Caveroot",
            "Skyblossom Seeds", "Skyblossom", "Void Rot Seeds", "Void Rot",
            "Mud", "Sandstone",
            "Marshleaf Seeds", "Marshleaf",
            "Blue Goo", "Green Goo", "Red Goo", "Yellow Goo", "Black Goo", "White Goo",
            "Astral Shard", "Rotten Chunk",
            "Copper Helmet", "Copper Chestplate", "Copper Leggings", "Copper Greaves",
            "Iron Helmet", "Iron Chestplate", "Iron Leggings", "Iron Greaves",
            "Silver Helmet", "Silver Chestplate", "Silver Leggings", "Silver Greaves",
            "Gold Helmet", "Gold Chestplate", "Gold Leggings", "Gold Greaves",
            "Zinc Helmet", "Zinc Chestplate", "Zinc Leggings", "Zinc Greaves",
            "Rhymestone Helmet", "Rhymestone Chestplate", "Rhymestone Leggings", "Rhymestone Greaves",
            "Obdurite Helmet", "Obdurite Chestplate", "Obdurite Leggings", "Obdurite Greaves",
            "Aluminum Helmet", "Aluminum Chestplate", "Aluminum Leggings", "Aluminum Greaves",
            "Lead Helmet", "Lead Chestplate", "Lead Leggings", "Lead Greaves",
            "Zythium Helmet", "Zythium Chestplate", "Zythium Leggings", "Zythium Greaves",
            "Aluminum Pick", "Aluminum Axe", "Aluminum Sword",
            "Lead Pick", "Lead Axe", "Lead Sword",
            "Zinc Chest", "Rhymestone Chest", "Obdurite Chest",
            "Wooden Pick", "Wooden Axe", "Wooden Sword",
            "Stone Pick", "Stone Axe", "Stone Sword",
            "Bark", "Cobblestone",
            "Chiseled Stone", "Chiseled Cobblestone", "Stone Bricks",
            "Clay", "Clay Bricks", "Varnish", "Varnished Wood",
            "Magnetite Pick", "Magnetite Axe", "Magnetite Sword",
            "Irradium Pick", "Irradium Axe", "Irradium Sword",
            "Zythium Wire", "Zythium Torch", "Zythium Lamp", "Lever",
            "Charcoal", "Zythium Amplifier", "Zythium Inverter", "Button",
            "Wooden Pressure Plate", "Stone Pressure Plate", "Zythium Pressure Plate",
            "Zythium Delayer", "Zythium Delayer", "Zythium Delayer", "Zythium Delayer", "Wrench"};

    public static Map<String, String> init() {
        Map<String, String> uiBlocks = new HashMap<>();

        for (int i = 1; i < Items.values().length; i++) {
            uiBlocks.put(Items.findByIndex(i).getFileName(), UI_ITEMS[i]);
        }

        return uiBlocks;
    }
}
