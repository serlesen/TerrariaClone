package com.sergio.refacto.services;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sergio.refacto.dto.ItemCollection;
import com.sergio.refacto.dto.ItemType;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.dto.RecipeItem;
import com.sergio.refacto.dto.RecipeType;
import com.sergio.refacto.items.ImagesContainer;
import com.sergio.refacto.tools.MathTool;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryService implements Serializable {

	private static InventoryService instance;

	private transient BufferedImage box;
	private transient BufferedImage box_selected;
    private static final Font FONT = new Font("Chalkboard", Font.PLAIN, 12);

    private static final int TROLX = 37;
    private static final int TROLY = 17;

	public int CX, CY;

    private static final Map<RecipeType, RecipeItem[]> RECIPES = new HashMap<>();

    private static final int BOX_SIZE = 40;
    private static final int MARGIN = 6;
    private static final int BOX_WITH_MARGIN = BOX_SIZE + MARGIN;

    private InventoryService() {
		// private constructor
	}

	public static InventoryService getInstance() {
    	if (instance == null) {
    		instance = new InventoryService();
		}

    	return instance;
	}

    public void init() {
        RecipeItem[] workbenchRecipes = {
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.WOODEN_PICK, (short) 1), // Wooden Pick
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.STONE, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.STONE_PICK, (short) 1), // Stone Pick
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.COPPER_PICK, (short) 1), // Copper Pick
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRON_PICK, (short) 1), // Iron Pick
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.SILVER_PICK, (short) 1), // Silver Pick
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.GOLD_PICK, (short) 1), // Gold Pick
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.ZINC_PICK, (short) 1), // Zinc Pick
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.RHYMESTONE_PICK, (short) 1), // Rhymestone Pick
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.OBDURITE_PICK, (short) 1), // Obdurite Pick
            new RecipeItem(new Items[]{Items.MAGNETITE_INGOT, Items.MAGNETITE_INGOT, Items.MAGNETITE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.MAGNETITE_PICK, (short) 1), // Magnetite Pick
            new RecipeItem(new Items[]{Items.IRRADIUM_INGOT, Items.IRRADIUM_INGOT, Items.IRRADIUM_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRRADIUM_PICK, (short) 1), // Irradium Pick
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.WOODEN_AXE, (short) 1), // Wooden Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.WOODEN_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.WOODEN_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.EMPTY, Items.STONE, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.STONE_AXE, (short) 1), // Stone Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.WOOD, Items.STONE, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.STONE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.EMPTY, Items.WOOD, Items.STONE, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.STONE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.STONE, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.STONE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.COPPER_AXE, (short) 1), // Copper Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.WOOD, Items.COPPER_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.COPPER_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.WOOD, Items.COPPER_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.COPPER_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.COPPER_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRON_AXE, (short) 1), // Iron Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.WOOD, Items.IRON_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRON_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.WOOD, Items.IRON_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.IRON_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.IRON_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.SILVER_AXE, (short) 1), // Silver Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.WOOD, Items.SILVER_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.SILVER_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.WOOD, Items.SILVER_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.SILVER_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.SILVER_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.GOLD_AXE, (short) 1), // Gold Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.WOOD, Items.GOLD_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.GOLD_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.WOOD, Items.GOLD_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.GOLD_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.GOLD_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.ZINC_AXE, (short) 1), // Zinc Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.WOOD, Items.ZINC_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.ZINC_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.WOOD, Items.ZINC_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.ZINC_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.ZINC_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.RHYMESTONE_AXE, (short) 1), // Rhymestone Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.WOOD, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.RHYMESTONE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.WOOD, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.RHYMESTONE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.RHYMESTONE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.OBDURITE_AXE, (short) 1), // Obdurite Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.WOOD, Items.OBDURITE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.OBDURITE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.WOOD, Items.OBDURITE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.OBDURITE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.OBDURITE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.MAGNETITE_INGOT, Items.MAGNETITE_INGOT, Items.EMPTY, Items.MAGNETITE_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.MAGNETITE_AXE, (short) 1), // Magnetite Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.MAGNETITE_INGOT, Items.MAGNETITE_INGOT, Items.EMPTY, Items.WOOD, Items.MAGNETITE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.MAGNETITE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.MAGNETITE_INGOT, Items.MAGNETITE_INGOT, Items.EMPTY, Items.WOOD, Items.MAGNETITE_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.MAGNETITE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.MAGNETITE_INGOT, Items.MAGNETITE_INGOT, Items.EMPTY, Items.MAGNETITE_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.MAGNETITE_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.IRRADIUM_INGOT, Items.IRRADIUM_INGOT, Items.EMPTY, Items.IRRADIUM_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRRADIUM_AXE, (short) 1), // Irradium Axe
            new RecipeItem(new Items[]{Items.EMPTY, Items.IRRADIUM_INGOT, Items.IRRADIUM_INGOT, Items.EMPTY, Items.WOOD, Items.IRRADIUM_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRRADIUM_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.IRRADIUM_INGOT, Items.IRRADIUM_INGOT, Items.EMPTY, Items.WOOD, Items.IRRADIUM_INGOT, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.IRRADIUM_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.IRRADIUM_INGOT, Items.IRRADIUM_INGOT, Items.EMPTY, Items.IRRADIUM_INGOT, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.IRRADIUM_AXE, (short) 1),
            new RecipeItem(new Items[]{Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_SWORD, (short) 1), // Wooden Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.WOODEN_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.WOODEN_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.STONE, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.STONE_SWORD, (short) 1), // Stone Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.STONE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.STONE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.COPPER_SWORD, (short) 1), // Copper Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.COPPER_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.COPPER_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.IRON_SWORD, (short) 1), // Iron Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRON_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.IRON_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.SILVER_SWORD, (short) 1), // Silver Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.SILVER_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.SILVER_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.GOLD_SWORD, (short) 1), // Gold Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.GOLD_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.GOLD_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.ZINC_ORE, Items.EMPTY, Items.EMPTY, Items.ZINC_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.ZINC_SWORD, (short) 1), // Zinc Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.ZINC_ORE, Items.EMPTY, Items.EMPTY, Items.ZINC_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.ZINC_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.ZINC_ORE, Items.EMPTY, Items.EMPTY, Items.ZINC_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.ZINC_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.RHYMESTONE_ORE, Items.EMPTY, Items.EMPTY, Items.RHYMESTONE_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.RHYMESTONE_SWORD, (short) 1), // Rhymestone Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.RHYMESTONE_ORE, Items.EMPTY, Items.EMPTY, Items.RHYMESTONE_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.RHYMESTONE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.RHYMESTONE_ORE, Items.EMPTY, Items.EMPTY, Items.RHYMESTONE_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.RHYMESTONE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.OBDURITE_ORE, Items.EMPTY, Items.EMPTY, Items.OBDURITE_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.OBDURITE_SWORD, (short) 1), // Obdurite Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.OBDURITE_ORE, Items.EMPTY, Items.EMPTY, Items.OBDURITE_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.OBDURITE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.OBDURITE_ORE, Items.EMPTY, Items.EMPTY, Items.OBDURITE_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.OBDURITE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.MAGNETITE_INGOT, Items.EMPTY, Items.EMPTY, Items.MAGNETITE_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.MAGNETITE_SWORD, (short) 1), // Magnetite Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.MAGNETITE_INGOT, Items.EMPTY, Items.EMPTY, Items.MAGNETITE_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.MAGNETITE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.MAGNETITE_INGOT, Items.EMPTY, Items.EMPTY, Items.MAGNETITE_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.MAGNETITE_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.IRRADIUM_INGOT, Items.EMPTY, Items.EMPTY, Items.IRRADIUM_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.IRRADIUM_SWORD, (short) 1), // Irradium Sword
            new RecipeItem(new Items[]{Items.EMPTY, Items.IRRADIUM_INGOT, Items.EMPTY, Items.EMPTY, Items.IRRADIUM_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.IRRADIUM_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.IRRADIUM_INGOT, Items.EMPTY, Items.EMPTY, Items.IRRADIUM_INGOT, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.IRRADIUM_SWORD, (short) 1),
            new RecipeItem(new Items[]{Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.EMPTY, Items.EMPTY, Items.ALUMINUM_INGOT, Items.EMPTY},
					Items.WRENCH, (short) 1), // Wrench
            new RecipeItem(new Items[]{Items.WOOD, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_WIRE, Items.EMPTY, Items.EMPTY},
					Items.LEVER, (short) 1), // Lever
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_WIRE, Items.EMPTY},
					Items.LEVER, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_WIRE},
					Items.LEVER, (short) 1),
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.COPPER_HELMET, (short) 1), // Copper Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT},
					Items.COPPER_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT},
					Items.COPPER_CHESTPLATE, (short) 1), // Copper Chestplate
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT},
					Items.COPPER_LEGGINGS, (short) 1), // Copper Leggings
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.COPPER_GREAVES, (short) 1), // Copper Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.EMPTY, Items.COPPER_INGOT},
					Items.COPPER_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.IRON_HELMET, (short) 1), // Iron Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT},
					Items.IRON_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT},
					Items.IRON_CHESTPLATE, (short) 1), // Iron Chestplate
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT},
					Items.IRON_CHESTPLATE, (short) 1), // Iron Leggings
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.IRON_GREAVES, (short) 1), // Iron Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.EMPTY, Items.IRON_INGOT},
					Items.IRON_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.SILVER_HELMET, (short) 1), // Silver Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT},
					Items.SILVER_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT},
					Items.SILVER_CHESTPLATE, (short) 1), // Silver Chestplate
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT},
					Items.SILVER_LEGGINGS, (short) 1), // Silver Leggings
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.SILVER_GREAVES, (short) 1), // Silver Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.EMPTY, Items.SILVER_INGOT},
					Items.SILVER_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.GOLD_HELMET, (short) 1), // Gold Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT},
					Items.GOLD_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT},
					Items.GOLD_CHESTPLATE, (short) 1), // Gold Chestplate
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT},
					Items.GOLD_LEGGINGS, (short) 1), // Gold Leggings
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.GOLD_GREAVES, (short) 1), // Gold Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.EMPTY, Items.GOLD_INGOT},
					Items.GOLD_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZINC_HELMET, (short) 1), // Zinc Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT},
					Items.ZINC_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT},
					Items.ZINC_CHESTPLATE, (short) 1), // Zinc Chestplate
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT},
					Items.ZINC_LEGGINGS, (short) 1), // Zinc Leggings
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZINC_GREAVES, (short) 1), // Zinc Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.EMPTY, Items.ZINC_INGOT},
					Items.ZINC_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.RHYMESTONE_HELMET, (short) 1), // Rhymestone Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT},
					Items.RHYMESTONE_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT},
					Items.RHYMESTONE_CHESTPLATE, (short) 1), // Rhymestone Chestplate
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT},
					Items.RHYMESTONE_LEGGINGS, (short) 1), // Rhymestone Leggings
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.RHYMESTONE_GREAVES, (short) 1), // Rhymestone Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.EMPTY, Items.RHYMESTONE_INGOT},
					Items.RHYMESTONE_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.OBDURITE_HELMET, (short) 1), // Obdurite Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT},
					Items.OBDURITE_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT},
					Items.OBDURITE_CHESTPLATE, (short) 1), // Obdurite Chestplate
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT},
					Items.OBDURITE_LEGGINGS, (short) 1), // Obdurite Leggings
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.OBDURITE_GREAVES, (short) 1), // Obdurite Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.EMPTY, Items.OBDURITE_INGOT},
					Items.OBDURITE_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ALUMINUM_HELMET, (short) 1), // Aluminum Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT},
					Items.ALUMINUM_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT},
					Items.ALUMINUM_CHESTPLATE, (short) 1), // Aluminum Chestplate
            new RecipeItem(new Items[]{Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT},
					Items.ALUMINUM_LEGGINGS, (short) 1), // Aluminum Leggings
            new RecipeItem(new Items[]{Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ALUMINUM_GREAVES, (short) 1), // Aluminum Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT, Items.ALUMINUM_INGOT, Items.EMPTY, Items.ALUMINUM_INGOT},
					Items.ALUMINUM_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.LEAD_HELMET, (short) 1), // Lead Helmet
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT},
					Items.LEAD_HELMET, (short) 1),
            new RecipeItem(new Items[]{Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT},
					Items.LEAD_CHESTPLATE, (short) 1), // Lead Chestplate
            new RecipeItem(new Items[]{Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT},
					Items.LEAD_LEGGINGS, (short) 1), // Lead Leggings
            new RecipeItem(new Items[]{Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.LEAD_GREAVES, (short) 1), // Lead Greaves
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT, Items.LEAD_INGOT, Items.EMPTY, Items.LEAD_INGOT},
					Items.LEAD_GREAVES, (short) 1),
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.WOOD, Items.WOOD},
					Items.WOODEN_CHEST, (short) 1), // Wooden Chest
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.STONE, Items.STONE, Items.WOODEN_CHEST, Items.STONE, Items.STONE, Items.STONE, Items.STONE},
					Items.STONE_CHEST, (short) 1), // Stone Chest
            new RecipeItem(new Items[]{Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.STONE_CHEST, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.COPPER_INGOT},
					Items.COPPER_CHEST, (short) 1), // Copper Chest
            new RecipeItem(new Items[]{Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.STONE_CHEST, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT},
					Items.IRON_CHEST, (short) 1), // Iron Chest
            new RecipeItem(new Items[]{Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.STONE_CHEST, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT, Items.SILVER_INGOT},
					Items.SILVER_CHEST, (short) 1), // Silver Chest
            new RecipeItem(new Items[]{Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.STONE_CHEST, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT},
					Items.GOLD_CHEST, (short) 1), // Gold Chest
            new RecipeItem(new Items[]{Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.STONE_CHEST, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT, Items.ZINC_INGOT},
					Items.ZINC_CHEST, (short) 1), // Zinc Chest
            new RecipeItem(new Items[]{Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.STONE_CHEST, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT, Items.RHYMESTONE_INGOT},
					Items.RHYMESTONE_CHEST, (short) 1), // Rhymestone Chest
            new RecipeItem(new Items[]{Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.STONE_CHEST, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT, Items.OBDURITE_INGOT},
					Items.OBDURITE_CHEST, (short) 1), // Obdurite Chest
            new RecipeItem(new Items[]{Items.GLASS, Items.GLASS, Items.GLASS, Items.GLASS, Items.LUMENSTONE, Items.GLASS, Items.GLASS, Items.ZYTHIUM_WIRE, Items.GLASS},
					Items.ZYTHIUM_LAMP, (short) 1), // Zythium Lamp
            new RecipeItem(new Items[]{Items.GLASS, Items.GLASS, Items.GLASS, Items.ZYTHIUM_WIRE, Items.ZYTHIUM_ORE, Items.ZYTHIUM_WIRE, Items.GLASS, Items.GLASS, Items.GLASS},
					Items.ZYTHIUM_AMPLIFIER, (short) 1), // Zythium Amplifier
            new RecipeItem(new Items[]{Items.GLASS, Items.GLASS, Items.GLASS, Items.ZYTHIUM_ORE, Items.ZYTHIUM_WIRE, Items.ZYTHIUM_ORE, Items.GLASS, Items.GLASS, Items.GLASS},
					Items.ZYTHIUM_INVERTER, (short) 1), // Zythium Inverter
            new RecipeItem(new Items[]{Items.GLASS, Items.ZYTHIUM_WIRE, Items.GLASS, Items.ZYTHIUM_WIRE, Items.ZYTHIUM_WIRE, Items.ZYTHIUM_WIRE, Items.GLASS, Items.ZYTHIUM_WIRE, Items.GLASS},
					Items.ZYTHIUM_DELAYER_1, (short) 1), // Zythium Delayer
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WORKBENCH, (short) 1), // Workbench
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WORKBENCH, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY},
					Items.WORKBENCH, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.WOOD, Items.WOOD},
					Items.WORKBENCH, (short) 1),
            new RecipeItem(new Items[]{Items.BARK, Items.BARK, Items.EMPTY, Items.BARK, Items.BARK, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOOD, (short) 1), // Bark -> Wood
            new RecipeItem(new Items[]{Items.EMPTY, Items.BARK, Items.BARK, Items.EMPTY, Items.BARK, Items.BARK, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOOD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.BARK, Items.BARK, Items.EMPTY, Items.BARK, Items.BARK, Items.EMPTY},
					Items.WOOD, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.BARK, Items.BARK, Items.EMPTY, Items.BARK, Items.BARK},
					Items.WOOD, (short) 1),
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.COBBLESTONE, (short) 4), // Cobblestone
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.COBBLESTONE, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY},
					Items.COBBLESTONE, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.STONE, Items.STONE},
					Items.COBBLESTONE, (short) 4),
            new RecipeItem(new Items[]{Items.CHISELED_STONE, Items.CHISELED_STONE, Items.EMPTY, Items.CHISELED_STONE, Items.CHISELED_STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.CHISELED_COBBLESTONE, (short) 4), // Chiseled Cobblestone
            new RecipeItem(new Items[]{Items.EMPTY, Items.CHISELED_STONE, Items.CHISELED_STONE, Items.EMPTY, Items.CHISELED_STONE, Items.CHISELED_STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.CHISELED_COBBLESTONE, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.CHISELED_STONE, Items.CHISELED_STONE, Items.EMPTY, Items.CHISELED_STONE, Items.CHISELED_STONE, Items.EMPTY},
					Items.CHISELED_COBBLESTONE, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.CHISELED_STONE, Items.CHISELED_STONE, Items.EMPTY, Items.CHISELED_STONE, Items.CHISELED_STONE},
					Items.CHISELED_COBBLESTONE, (short) 4),
            new RecipeItem(new Items[]{Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.EMPTY, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_BRICKS, (short) 4), // Stone Bricks
            new RecipeItem(new Items[]{Items.EMPTY, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.EMPTY, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_BRICKS, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.EMPTY, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.EMPTY},
					Items.STONE_BRICKS, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.EMPTY, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE},
					Items.STONE_BRICKS, (short) 4),
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.STONE, Items.STONE, Items.EMPTY, Items.STONE, Items.STONE, Items.STONE, Items.STONE},
					Items.FURNACE, (short) 1), // Furnace
            new RecipeItem(new Items[]{Items.ZYTHIUM_BAR, Items.ZYTHIUM_BAR, Items.ZYTHIUM_BAR, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZYTHIUM_WIRE, (short) 10), // Zythium Wire
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_BAR, Items.ZYTHIUM_BAR, Items.ZYTHIUM_BAR, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZYTHIUM_WIRE, (short) 20),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_BAR, Items.ZYTHIUM_BAR, Items.ZYTHIUM_BAR},
					Items.ZYTHIUM_WIRE, (short) 20),
            new RecipeItem(new Items[]{Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1), // Stone Lighter
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.STONE, Items.EMPTY, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.EMPTY, Items.STONE, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_TORCH, (short) 4),  // Wooden Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.WOODEN_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.WOODEN_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.COAL, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.COAL_TORCH, (short) 4), // Coal Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.COAL, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.COAL_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.COAL, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.COAL_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.COAL, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.COAL_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.COAL, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.COAL_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.COAL, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.COAL_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.LUMENSTONE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.LUMENSTONE_TORCH, (short) 4), // Lumenstone Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.LUMENSTONE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.LUMENSTONE_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.LUMENSTONE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.LUMENSTONE_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.LUMENSTONE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.LUMENSTONE_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.LUMENSTONE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.LUMENSTONE_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.LUMENSTONE, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.LUMENSTONE_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.ZYTHIUM_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZYTHIUM_TORCH, (short) 4), // Zythium Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.ZYTHIUM_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZYTHIUM_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZYTHIUM_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.ZYTHIUM_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.ZYTHIUM_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.ZYTHIUM_ORE, Items.EMPTY, Items.EMPTY, Items.WOOD},
					Items.ZYTHIUM_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_PRESSURE_PLATE, (short) 1), // Wooden Pressure Plate
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.WOOD, Items.EMPTY},
					Items.WOODEN_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.WOOD, Items.WOOD},
					Items.WOODEN_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_PRESSURE_PLATE, (short) 1), // Stone Pressure Plate
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.STONE_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY},
					Items.STONE_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.STONE, Items.STONE},
					Items.STONE_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.CHISELED_STONE, Items.ZYTHIUM_ORE, Items.CHISELED_STONE, Items.EMPTY, Items.ZYTHIUM_WIRE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.ZYTHIUM_PRESSURE_PLATE, (short) 1), // Zythium Pressure Plate
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.CHISELED_STONE, Items.ZYTHIUM_ORE, Items.CHISELED_STONE, Items.EMPTY, Items.ZYTHIUM_WIRE, Items.EMPTY},
					Items.ZYTHIUM_PRESSURE_PLATE, (short) 1)
        };

        RECIPES.put(RecipeType.WORKBENCH, workbenchRecipes);

        RecipeItem[] cicRecipes = {
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.WOOD, Items.WOOD},
					Items.WORKBENCH, (short) 1), // Workbench
            new RecipeItem(new Items[]{Items.BARK, Items.BARK, Items.BARK, Items.BARK},
					Items.WOOD, (short) 1), // Bark -> Wood
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.STONE, Items.STONE},
					Items.COBBLESTONE, (short) 4), // Cobblestone
            new RecipeItem(new Items[]{Items.CHISELED_STONE, Items.CHISELED_STONE, Items.CHISELED_STONE, Items.CHISELED_STONE},
					Items.CHISELED_COBBLESTONE, (short) 4), // Chiseled Cobblestone
            new RecipeItem(new Items[]{Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE, Items.CHISELED_COBBLESTONE},
					Items.STONE_BRICKS, (short) 4), // Stone Bricks
            new RecipeItem(new Items[]{Items.STONE, Items.EMPTY, Items.EMPTY, Items.STONE},
					Items.STONE_LIGHTER, (short) 1), // Stone Lighter
            new RecipeItem(new Items[]{Items.EMPTY, Items.STONE, Items.STONE, Items.EMPTY},
					Items.STONE_LIGHTER, (short) 1),
            new RecipeItem(new Items[]{Items.WOOD, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.WOODEN_TORCH, (short) 4),  // Wooden Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.WOOD, Items.EMPTY, Items.WOOD},
					Items.WOODEN_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.COAL, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.COAL_TORCH, (short) 4), // Coal Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.COAL, Items.EMPTY, Items.WOOD},
					Items.COAL_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.LUMENSTONE, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.LUMENSTONE_TORCH, (short) 4), // Lumenstone Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.LUMENSTONE, Items.EMPTY, Items.WOOD},
					Items.LUMENSTONE_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.ZYTHIUM_ORE, Items.EMPTY, Items.WOOD, Items.EMPTY},
					Items.ZYTHIUM_TORCH, (short) 4), // Zythium Torch
            new RecipeItem(new Items[]{Items.EMPTY, Items.ZYTHIUM_ORE, Items.EMPTY, Items.WOOD},
					Items.ZYTHIUM_TORCH, (short) 4),
            new RecipeItem(new Items[]{Items.WOOD, Items.WOOD, Items.EMPTY, Items.EMPTY},
					Items.WOODEN_PRESSURE_PLATE, (short) 1), // Wooden Pressure Plate
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.WOOD, Items.WOOD},
					Items.WOODEN_PRESSURE_PLATE, (short) 1),
            new RecipeItem(new Items[]{Items.STONE, Items.STONE, Items.EMPTY, Items.EMPTY},
					Items.STONE_PRESSURE_PLATE, (short) 1), // Stone Pressure Plate
            new RecipeItem(new Items[]{Items.EMPTY, Items.EMPTY, Items.STONE, Items.STONE},
					Items.STONE_PRESSURE_PLATE, (short) 1)
        };

        RECIPES.put(RecipeType.CIC, cicRecipes);

        RecipeItem[] shapelessRecipes = {
            new RecipeItem(new Items[]{Items.WOOD, Items.VARNISH, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.VARNISHED_WOOD, (short) 1),
            new RecipeItem(new Items[]{Items.CHISELED_STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.BUTTON, (short) 1)
        };

        RECIPES.put(RecipeType.SHAPELESS, shapelessRecipes);

        RecipeItem[] shapelessCicRecipes = {
            new RecipeItem(new Items[]{Items.WOOD, Items.VARNISH, Items.EMPTY, Items.EMPTY},
					Items.VARNISHED_WOOD, (short) 1),
            new RecipeItem(new Items[]{Items.CHISELED_STONE, Items.EMPTY, Items.EMPTY, Items.EMPTY},
					Items.BUTTON, (short) 1)
        };

        RECIPES.put(RecipeType.SHAPELESS_CIC, shapelessCicRecipes);
    }

    public int addItem(ItemCollection ic, Items item, short quantity) {
        if (item.getDurability() != null) {
            return addItem(ic, item, quantity, item.getDurability().shortValue());
        }
        else {
            return addItem(ic, item, quantity, (short)0);
        }
    }

    public int addItem(ItemCollection ic, Items item, short quantity, short durability) {
        for (int i = 0; i < ic.getType().getItemCollectionIterable(); i++) {
            if (ic.getItems()[i] == item && ic.getNums()[i] < ic.getItems()[i].getMaxStacks()) {
                if (quantity + ic.getNums()[i] <= ic.getItems()[i].getMaxStacks()) {
					ic.getNums()[i] += quantity;
                    update(ic, i);
                    return 0;
                } else {
                    quantity -= ic.getItems()[i].getMaxStacks() - ic.getNums()[i];
					ic.getNums()[i] = (short) ic.getItems()[i].getMaxStacks();
					update(ic, i);
                }
            }
        }
        for (int i = 0; i < ic.getType().getItemCollectionIterable(); i++) {
            if (ic.getItems()[i] == Items.EMPTY) {
				ic.getItems()[i] = item;
                if (quantity <= ic.getItems()[i].getMaxStacks()) {
					ic.getNums()[i] = quantity;
					ic.getDurs()[i] = durability;
					update(ic, i);
                    return 0;
                } else {
					ic.getNums()[i] = (short) ic.getItems()[i].getMaxStacks();
					ic.getDurs()[i] = durability;
                    quantity -= ic.getItems()[i].getMaxStacks();
					update(ic, i);
                }
            }
        }
        return quantity;
    }

	public int addLocation(ItemCollection ic, int i, Items item, short quantity) {
		return addLocation(ic, i, item, quantity, (short)0);
	}

	public int addLocation(ItemCollection ic, int i, Items item, short quantity, short durability) {
		if (ic.getItems()[i] == item) {
			if (ic.getItems()[i].getMaxStacks() - ic.getNums()[i] >= quantity) {
				ic.getNums()[i] += quantity;
				if (ic.getImage() != null) {
					update(ic, i);
				}
				return 0;
			}
			else {
				quantity -= ic.getItems()[i].getMaxStacks() - ic.getNums()[i];
				ic.getNums()[i] = (short) ic.getItems()[i].getMaxStacks();
				if (ic.getImage() != null) {
					update(ic, i);
				}
			}
		} else {
			if (quantity <= ic.getItems()[i].getMaxStacks()) {
				ic.getItems()[i] = item;
				ic.getNums()[i] = quantity;
				ic.getDurs()[i] = durability;
				if (ic.getImage() != null) {
					update(ic, i);
				}
				return 0;
			}
			else {
				quantity -= ic.getItems()[i].getMaxStacks();
				return quantity;
			}
		}
		return quantity;
	}

	public int removeLocation(ItemCollection ic, int i, short quantity) {
		if (ic.getNums()[i] >= quantity) {
			ic.getNums()[i] -= quantity;
			if (ic.getNums()[i] == 0) {
				ic.getItems()[i] = Items.EMPTY;
			}
			if (ic.getImage() != null) {
				update(ic, i);
			}
			return 0;
		}
		else {
			quantity -= ic.getNums()[i];
			ic.getNums()[i] = 0;
			ic.getItems()[i] = Items.EMPTY;
			if (ic.getImage() != null) {
				update(ic, i);
			}
		}
		return quantity;
	}

	/**
	 * Draw the template box of the item.
	 * @param selected if the item is the selected one or not.
	 * @param x horizontal position of the item
	 * @param y vertical position of the item
	 */
    private void drawBox(boolean selected, int x, int y, Graphics2D g2) {
		if (selected) {
			g2.drawImage(box_selected,
					x * BOX_WITH_MARGIN + MARGIN, y * BOX_WITH_MARGIN + MARGIN, x * BOX_WITH_MARGIN + BOX_WITH_MARGIN, y * BOX_WITH_MARGIN + BOX_WITH_MARGIN,
					0, 0, BOX_SIZE, BOX_SIZE,
					null);
		} else {
			g2.drawImage(box,
					x * BOX_WITH_MARGIN + MARGIN, y * BOX_WITH_MARGIN + MARGIN, x * BOX_WITH_MARGIN + BOX_WITH_MARGIN, y * BOX_WITH_MARGIN + BOX_WITH_MARGIN,
					0, 0, BOX_SIZE, BOX_SIZE,
					null);
		}

		if (y == 0) {
			g2.setFont(FONT);
			g2.setColor(Color.BLACK);
			g2.drawString(increment(x) + " ", x * BOX_WITH_MARGIN+ TROLX, y * BOX_WITH_MARGIN+ TROLY);
		}
	}

	/**
	 * Draw the image item of the given collection.
	 * @param ic items collection
	 * @param i index of the item
	 * @param x horizontal position of the item
	 * @param y veritcal position of the item
	 * @param boxWidth width of the box (with the margin) where the image will be drawn
	 * @param boxHeight height of the box (with the margin) where the image will be drawn
	 */
	private void drawImage(ItemCollection ic, int i, int x, int y, int boxWidth, int boxHeight) {
		Graphics2D g2 = ic.getImage().createGraphics();
		g2.drawImage(box,
				x * boxWidth, y * boxHeight, x * boxWidth + 40, y * boxHeight + 40,
				0, 0, BOX_SIZE, BOX_SIZE,
				null);

		if (ic.getItems()[i] != Items.EMPTY) {
			int width = ImagesContainer.getInstance().itemImgs.get(ic.getItems()[i]).getWidth();
			int height = ImagesContainer.getInstance().itemImgs.get(ic.getItems()[i]).getHeight();
			g2.drawImage(ImagesContainer.getInstance().itemImgs.get(ic.getItems()[i]),
					x * boxWidth + 8 + ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * width * 2) / 2),
					y * boxHeight + 8 + ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * height * 2) / 2),
					x * boxWidth + 32 - ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * width * 2) / 2),
					y * boxHeight + 32 - ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * height * 2) / 2),
					0, 0, width, height,
					null);

			if (ic.getNums()[i] > 1) {
				g2.setFont(FONT);
				g2.setColor(Color.WHITE);
				g2.drawString(ic.getNums()[i] + " ", x * boxWidth + 9, y * boxHeight + 34);
			}
		}
	}

	public void update(ItemCollection ic, int i) {
		if (ic.getType() == ItemType.INVENTORY) {
			int py = i/10;
			int px = i-(py*10);
			for (int x = px * BOX_WITH_MARGIN + MARGIN; x < px * BOX_WITH_MARGIN + BOX_WITH_MARGIN; x++) {
				for (int y = py * BOX_WITH_MARGIN + MARGIN; y< py * BOX_WITH_MARGIN + BOX_WITH_MARGIN; y++) {
					ic.getImage().setRGB(x, y, 9539985);
				}
			}
			Graphics2D g2 = ic.getImage().createGraphics();

			drawBox(i == ic.getSelection(), px, py, g2);

			if (ic.getItems()[i] != Items.EMPTY) {
				int width = ImagesContainer.getInstance().itemImgs.get(ic.getItems()[i]).getWidth();
				int height = ImagesContainer.getInstance().itemImgs.get(ic.getItems()[i]).getHeight();
				g2.drawImage(ImagesContainer.getInstance().itemImgs.get(ic.getItems()[i]),
						px*BOX_WITH_MARGIN+14+((int)(24-(double)12/MathTool.max(width, height, 12)*width*2)/2), py*BOX_WITH_MARGIN+14+((int)(24-(double)12/ MathTool.max(width, height, 12)*height*2)/2), px*BOX_WITH_MARGIN+38-((int)(24-(double)12/MathTool.max(width, height, 12)*width*2)/2), py*BOX_WITH_MARGIN+38-((int)(24-(double)12/MathTool.max(width, height, 12)*height*2)/2),
						0, 0, width, height,
						null);

				if (ic.getNums()[i] > 1) {
					g2.setFont(FONT);
					g2.setColor(Color.WHITE);
					g2.drawString(ic.getNums()[i] + " ", px*BOX_WITH_MARGIN+15, py*BOX_WITH_MARGIN+40);
				}
			}
		} else if (ic.getType() == ItemType.CIC) {
			int py = (int)(i/2);
			int px = i-(py*2);
			for (int x=px*40; x<px*40+40; x++) {
				for (int y=py*40; y<py*40+40; y++) {
					ic.getImage().setRGB(x, y, 9539985);
				}
			}

			drawImage(ic, i, px, py, 40, 40);

			ic.getItems()[4] = Items.EMPTY;
			ic.getItems()[4] = Items.EMPTY;
			for (RecipeItem r2 : RECIPES.get(RecipeType.CIC)) {
				if (ic.areIdsEquals(r2.getIngredients())) {
					ic.getItems()[4] = r2.getResultingItem();
					ic.getNums()[4] = r2.getNum();
					if (r2.getResultingItem().getDurability() != null)
						ic.getDurs()[4] = r2.getResultingItem().getDurability().shortValue();
					break;
				}
			}
			for (RecipeItem r2 : RECIPES.get(RecipeType.SHAPELESS_CIC)) {
				if (ic.areInvalidIds(r2.getIngredients())) {
					ic.getItems()[4] = r2.getResultingItem();
					ic.getNums()[4] = r2.getNum();
					if (r2.getResultingItem().getDurability() != null)
						ic.getDurs()[4] = r2.getResultingItem().getDurability().shortValue();
					break;
				}
			}
			for (int x=3*40; x<3*40+40; x++) {
				for (int y=20; y<20+40; y++) {
					ic.getImage().setRGB(x, y, 9539985);
				}
			}

			drawImage(ic, 4, 3, 1, 40, 20);

		} else if (ic.getType() == ItemType.ARMOR) {
			int py = (int)(i/CX);
			int px = i-(py*CX);
			for (int x=px*BOX_WITH_MARGIN; x<px*BOX_WITH_MARGIN+40; x++) {
				for (int y=py*BOX_WITH_MARGIN; y<py*BOX_WITH_MARGIN+40; y++) {
					ic.getImage().setRGB(x, y, 9539985);
				}
			}
			drawImage(ic, i, px, py, BOX_WITH_MARGIN, BOX_WITH_MARGIN);

		} else if (ic.getType() == ItemType.WORKBENCH) {
			int py = (int)(i/3);
			int px = i-(py*3);
			for (int x=px*40; x<px*40+40; x++) {
				for (int y=py*40; y<py*40+40; y++) {
					ic.getImage().setRGB(x, y, 9539985);
				}
			}

			drawImage(ic, i, px, py, 40, 40);

			ic.getItems()[9] = Items.EMPTY;
			for (RecipeItem r2 : RECIPES.get(RecipeType.WORKBENCH)) {
				if (ic.areIdsEquals(r2.getIngredients())) {
					ic.getItems()[9] = r2.getResultingItem();
					ic.getNums()[9] = r2.getNum();
					if (r2.getResultingItem().getDurability() != null)
						ic.getDurs()[9] = r2.getResultingItem().getDurability().shortValue();
					break;
				}
			}
			for (RecipeItem r2 : RECIPES.get(RecipeType.SHAPELESS)) {
				if (ic.areInvalidIds(r2.getIngredients())) {
					ic.getItems()[9] = r2.getResultingItem();
					ic.getNums()[9] = r2.getNum();
					if (r2.getResultingItem().getDurability() != null)
						ic.getDurs()[9] = r2.getResultingItem().getDurability().shortValue();
					break;
				}
			}
			for (int x=4*40; x<4*40+40; x++) {
				for (int y=1*40; y<1*40+40; y++) {
					ic.getImage().setRGB(x, y, 9539985);
				}
			}

			drawImage(ic, 9, 4, 1, 40, 40);

		} else if (ic.getType() == ItemType.WOODEN_CHEST || ic.getType() == ItemType.STONE_CHEST ||
				ic.getType() == ItemType.COPPER_CHEST || ic.getType() == ItemType.IRON_CHEST ||
				ic.getType() == ItemType.SILVER_CHEST || ic.getType() == ItemType.GOLD_CHEST ||
				ic.getType() == ItemType.ZINC_CHEST || ic.getType() == ItemType.RHYMESTONE_CHEST ||
				ic.getType() == ItemType.OBDURITE_CHEST) {
			int py = (int)(i/CX);
			int px = i-(py*CX);
			for (int x=px*BOX_WITH_MARGIN; x<px*BOX_WITH_MARGIN+40; x++) {
				for (int y=py*BOX_WITH_MARGIN; y<py*BOX_WITH_MARGIN+40; y++) {
					ic.getImage().setRGB(x, y, 9539985);
				}
			}

			drawImage(ic, i, px, py, BOX_WITH_MARGIN, BOX_WITH_MARGIN);

		} else if (ic.getType() == ItemType.FURNACE) {
			if (i == -1) {
				for (int y=0; y<5; y++) {
					for (int x=0; x<ic.getFUELP()*38; x++) {
						ic.getImage().setRGB(x+1, y+51, new Color(255, 0, 0).getRGB());
					}
					for (int x=(int)(ic.getFUELP()*38); x<38; x++) {
						ic.getImage().setRGB(x+1, y+51, new Color(145, 145, 145).getRGB());
					}
				}
				for (int x=0; x<5; x++) {
					for (int y=0; y<ic.getSMELTP()*38; y++) {
						ic.getImage().setRGB(x+40, y+1, new Color(255, 0, 0).getRGB());
					}
					for (int y=(int)(ic.getSMELTP()*38); y<38; y++) {
						ic.getImage().setRGB(x+40, y+1, new Color(145, 145, 145).getRGB());
					}
				}
			}
			else {
				double fpx = 0, fpy = 0;
				if (i == 0) {
					fpx = 0;
					fpy = 0;
				} else if (i == 1) {
					fpx = 0;
					fpy = 1.4;
				} else if (i == 2) {
					fpx = 0;
					fpy = 2.4;
				} else if (i == 3) {
					fpx = 1.4;
					fpy = 0;
				}
				for (int x=(int)(fpx*40); x<fpx*40+40; x++) {
					for (int y=(int)(fpy*40); y<fpy*40+40; y++) {
						ic.getImage().setRGB(x, y, 9539985);
					}
				}

				drawImage(ic, i, (int) fpx, (int) fpy, 40, 40);
			}
		}
	}

    public int select(ItemCollection ic, int i, int actualSelection) {
        if (i == 0) {
            int n = actualSelection;
            update(ic, n);
			update(ic, 9);
            return 9;
        } else {
            int n = actualSelection;
			update(ic, n);
			update(ic, i - 1);
            return i - 1;
        }
    }

    public int select2(ItemCollection ic, int i, int actualSelection) {
        int n = actualSelection;
		update(ic, n);
		update(ic, i);
        return i;
    }

    public void renderCollection(ItemCollection ic) {
		if (ic.getType() == ItemType.INVENTORY) {
			ic.setImage(new BufferedImage(466, 190, BufferedImage.TYPE_INT_ARGB));
			box = ResourcesLoader.loadImage("interface/inventory.png");
			box_selected = ResourcesLoader.loadImage("interface/inventory_selected.png");
			Graphics2D g2 = ic.getImage().createGraphics();
			for (int x=0; x<10; x++) {
				for (int y=0; y<4; y++) {
					drawBox(x == 0 && y == 0, x, y, g2);
				}
			}
			for (int i=0; i<ItemType.INVENTORY.getItemCollectionIterable(); i++) {
				update(ic, i);
			}
		} else if (ic.getType() == ItemType.CIC) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/cic.png"));
                for (int i=0; i<ItemType.CIC.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.ARMOR) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/armor.png"));
                CX = 1;
                CY = 4;
                for (int i=0; i<ItemType.ARMOR.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.WORKBENCH) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/workbench.png"));
                for (int i=0; i<ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.WOODEN_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/wooden_chest.png"));
                CX = 3;
                CY = 3;
                for (int i=0; i<ItemType.WOODEN_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.STONE_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/stone_chest.png"));
                CX = 5;
                CY = 3;
                for (int i=0; i<ItemType.STONE_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.COPPER_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/copper_chest.png"));
                CX = 5;
                CY = 4;
                for (int i=0; i<ItemType.COPPER_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.IRON_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/iron_chest.png"));
                CX = 7;
                CY = 4;
                for (int i=0; i<ItemType.IRON_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.SILVER_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/silver_chest.png"));
                CX = 7;
                CY = 5;
                for (int i=0; i<ItemType.SILVER_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.GOLD_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/gold_chest.png"));
                CX = 7;
                CY = 6;
                for (int i=0; i<ItemType.GOLD_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.ZINC_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/zinc_chest.png"));
                CX = 7;
                CY = 8;
                for (int i=0; i<ItemType.ZINC_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.RHYMESTONE_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/rhymestone_chest.png"));
                CX = 8;
                CY = 9;
                for (int i=0; i<ItemType.RHYMESTONE_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.OBDURITE_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/obdurite_chest.png"));
                CX = 10;
                CY = 10;
                for (int i=0; i<ItemType.OBDURITE_CHEST.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.FURNACE) {
            if (ic.getImage() == null) {
                ic.setImage(ResourcesLoader.loadImage("interface/furnace.png"));
                for (int i=-1; i<ItemType.FURNACE.getItemCollectionIterable(); i++) {
                    update(ic, i);
                }
            }
        }
    }

    public void useRecipeWorkbench(ItemCollection ic) {
        for (RecipeItem r2 : RECIPES.get(RecipeType.WORKBENCH)) {
            if (ic.areIdsEquals(r2.getIngredients())) {
                for (int i=0; i<ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                    removeLocation(ic, i, (short) 1);
                    update(ic, i);
                }
            }
        }
        for (RecipeItem r2 : RECIPES.get(RecipeType.SHAPELESS)) {
            if (ic.areInvalidIds(r2.getIngredients())) {
                List<Items> r3 = new ArrayList<>(11);
                for (int k=0; k<r2.getIngredients().length; k++) {
                    r3.add(r2.getIngredients()[k]);
                }
                for (int k=0; k<ItemType.WORKBENCH.getItemCollectionIterable(); k++) {
                    r3.remove(ic.getItems()[k]);
                    removeLocation(ic, k, (short) 1);
                    update(ic, k);
                }
                break;
            }
        }
    }

    public void useRecipeCIC(ItemCollection ic) {
        for (RecipeItem r2 : RECIPES.get(RecipeType.CIC)) {
            if (ic.areIdsEquals(r2.getIngredients())) {
                for (int i=0; i<ItemType.CIC.getItemCollectionIterable(); i++) {
                    removeLocation(ic, i, (short) 1);
                    update(ic, i);
                }
            }
        }
        for (RecipeItem r2 : RECIPES.get(RecipeType.SHAPELESS_CIC)) {
            if (ic.areInvalidIds(r2.getIngredients())) {
                List<Items> r3 = new ArrayList<>(6);
                for (int k=0; k<r2.getIngredients().length-2; k++) {
                    r3.add(r2.getIngredients()[k]);
                }
                for (int k=0; k<ItemType.CIC.getItemCollectionIterable(); k++) {
                    r3.remove(ic.getItems()[k]);
                    removeLocation(ic, k, (short) 1);
                    update(ic, k);
                }
                break;
            }
        }
    }

    private static int increment(int x) {
        if (x == 9) {
            return 0;
        }
        return x + 1;
    }
}
