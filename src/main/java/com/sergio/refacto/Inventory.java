package com.sergio.refacto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import com.sergio.refacto.dto.ItemType;
import com.sergio.refacto.dto.RecipeItem;

public class Inventory implements Serializable {

    int i, j, k, x, y, n, px, py, selection, width, height;
    private double fpx, fpy;
    private short r;

    transient BufferedImage image, box, box_selected;
    private Font font = new Font("Chalkboard", Font.PLAIN, 12);

    private transient Graphics2D g2;

    short[] ids;
    short[] nums;
    short[] durs;

    private int trolx = 37;
    private int troly = 17;

    int CX, CY;

    private ItemCollection ic;

    private Map<String, RecipeItem[]> RECIPES;

    public Inventory() {
        ids = new short[40];
        nums = new short[40];
        durs = new short[40];
        for (i=0; i<40; i++) {
            ids[i] = 0;
            nums[i] = 0;
            durs[i] = 0;
        }
        selection = 0;
        image = new BufferedImage(466, 190, BufferedImage.TYPE_INT_ARGB);
        box = loadImage("interface/inventory.png");
        box_selected = loadImage("interface/inventory_selected.png");
        g2 = image.createGraphics();
        for (x=0; x<10; x++) {
            for (y=0; y<4; y++) {
                if (x == 0 && y == 0) {
                    g2.drawImage(box_selected,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
                else {
                    g2.drawImage(box,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
            }
        }

        RECIPES = new HashMap<>();

        RecipeItem[] list_thing1 = {
            new RecipeItem(new short[]{15, 15, 15, 0, 15, 0, 0, 15, 0}, (short) 154, (short) 1), // Wooden Pick
            new RecipeItem(new short[]{2, 2, 2, 0, 15, 0, 0, 15, 0}, (short) 157, (short) 1), // Stone Pick
            new RecipeItem(new short[]{29, 29, 29, 0, 15, 0, 0, 15, 0}, (short) 7, (short) 1), // Copper Pick
            new RecipeItem(new short[]{30, 30, 30, 0, 15, 0, 0, 15, 0}, (short) 8, (short) 1), // Iron Pick
            new RecipeItem(new short[]{31, 31, 31, 0, 15, 0, 0, 15, 0}, (short) 9, (short) 1), // Silver Pick
            new RecipeItem(new short[]{32, 32, 32, 0, 15, 0, 0, 15, 0}, (short) 10, (short) 1), // Gold Pick
            new RecipeItem(new short[]{60, 60, 60, 0, 15, 0, 0, 15, 0}, (short) 51, (short) 1), // Zinc Pick
            new RecipeItem(new short[]{61, 61, 61, 0, 15, 0, 0, 15, 0}, (short) 54, (short) 1), // Rhymestone Pick
            new RecipeItem(new short[]{62, 62, 62, 0, 15, 0, 0, 15, 0}, (short) 57, (short) 1), // Obdurite Pick
            new RecipeItem(new short[]{73, 73, 73, 0, 15, 0, 0, 15, 0}, (short) 169, (short) 1), // Magnetite Pick
            new RecipeItem(new short[]{69, 69, 69, 0, 15, 0, 0, 15, 0}, (short) 172, (short) 1), // Irradium Pick
            new RecipeItem(new short[]{15, 15, 0, 15, 15, 0, 0, 15, 0}, (short) 155, (short) 1), // Wooden Axe
            new RecipeItem(new short[]{0, 15, 15, 0, 15, 15, 0, 15, 0}, (short) 155, (short) 1),
            new RecipeItem(new short[]{15, 15, 0, 15, 15, 0, 15, 0, 0}, (short) 155, (short) 1),
            new RecipeItem(new short[]{0, 15, 15, 0, 15, 15, 0, 0, 15}, (short) 155, (short) 1),
            new RecipeItem(new short[]{2, 2, 0, 2, 15, 0, 0, 15, 0}, (short) 158, (short) 1), // Stone Axe
            new RecipeItem(new short[]{0, 2, 2, 0, 15, 2, 0, 15, 0}, (short) 158, (short) 1),
            new RecipeItem(new short[]{2, 2, 0, 15, 2, 0, 15, 0, 0}, (short) 158, (short) 1),
            new RecipeItem(new short[]{0, 2, 2, 0, 2, 15, 0, 0, 15}, (short) 158, (short) 1),
            new RecipeItem(new short[]{29, 29, 0, 29, 15, 0, 0, 15, 0}, (short) 11, (short) 1), // Copper Axe
            new RecipeItem(new short[]{0, 29, 29, 0, 15, 29, 0, 15, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{29, 29, 0, 15, 29, 0, 15, 0, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{0, 29, 29, 0, 29, 15, 0, 0, 15}, (short) 11, (short) 1),
            new RecipeItem(new short[]{30, 30, 0, 30, 15, 0, 0, 15, 0}, (short) 11, (short) 1), // Iron Axe
            new RecipeItem(new short[]{0, 30, 30, 0, 15, 30, 0, 15, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{30, 30, 0, 15, 30, 0, 15, 0, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{0, 30, 30, 0, 30, 15, 0, 0, 15}, (short) 11, (short) 1),
            new RecipeItem(new short[]{31, 31, 0, 31, 15, 0, 0, 15, 0}, (short) 11, (short) 1), // Silver Axe
            new RecipeItem(new short[]{0, 31, 31, 0, 15, 31, 0, 15, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{31, 31, 0, 15, 31, 0, 15, 0, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{0, 31, 31, 0, 31, 15, 0, 0, 15}, (short) 11, (short) 1),
            new RecipeItem(new short[]{32, 32, 0, 32, 15, 0, 0, 15, 0}, (short) 11, (short) 1), // Gold Axe
            new RecipeItem(new short[]{0, 32, 32, 0, 15, 32, 0, 15, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{32, 32, 0, 15, 32, 0, 15, 0, 0}, (short) 11, (short) 1),
            new RecipeItem(new short[]{0, 32, 32, 0, 32, 15, 0, 0, 15}, (short) 11, (short) 1),
            new RecipeItem(new short[]{60, 60, 0, 60, 15, 0, 0, 15, 0}, (short) 52, (short) 1), // Zinc Axe
            new RecipeItem(new short[]{0, 60, 60, 0, 15, 60, 0, 15, 0}, (short) 52, (short) 1),
            new RecipeItem(new short[]{60, 60, 0, 15, 60, 0, 15, 0, 0}, (short) 52, (short) 1),
            new RecipeItem(new short[]{0, 60, 60, 0, 60, 15, 0, 0, 15}, (short) 52, (short) 1),
            new RecipeItem(new short[]{61, 61, 0, 61, 15, 0, 0, 15, 0}, (short) 55, (short) 1), // Rhymestone Axe
            new RecipeItem(new short[]{0, 61, 61, 0, 15, 61, 0, 15, 0}, (short) 55, (short) 1),
            new RecipeItem(new short[]{61, 61, 0, 15, 61, 0, 15, 0, 0}, (short) 55, (short) 1),
            new RecipeItem(new short[]{0, 61, 61, 0, 61, 15, 0, 0, 15}, (short) 55, (short) 1),
            new RecipeItem(new short[]{62, 62, 0, 62, 15, 0, 0, 15, 0}, (short) 58, (short) 1), // Obdurite Axe
            new RecipeItem(new short[]{0, 62, 62, 0, 15, 62, 0, 15, 0}, (short) 58, (short) 1),
            new RecipeItem(new short[]{62, 62, 0, 15, 62, 0, 15, 0, 0}, (short) 58, (short) 1),
            new RecipeItem(new short[]{0, 62, 62, 0, 62, 15, 0, 0, 15}, (short) 58, (short) 1),
            new RecipeItem(new short[]{73, 73, 0, 73, 15, 0, 0, 15, 0}, (short) 170, (short) 1), // Magnetite Axe
            new RecipeItem(new short[]{0, 73, 73, 0, 15, 73, 0, 15, 0}, (short) 170, (short) 1),
            new RecipeItem(new short[]{73, 73, 0, 15, 73, 0, 15, 0, 0}, (short) 170, (short) 1),
            new RecipeItem(new short[]{0, 73, 73, 0, 73, 15, 0, 0, 15}, (short) 170, (short) 1),
            new RecipeItem(new short[]{69, 69, 0, 69, 15, 0, 0, 15, 0}, (short) 169, (short) 1), // Irradium Axe
            new RecipeItem(new short[]{0, 69, 69, 0, 15, 69, 0, 15, 0}, (short) 169, (short) 1),
            new RecipeItem(new short[]{69, 69, 0, 15, 69, 0, 15, 0, 0}, (short) 169, (short) 1),
            new RecipeItem(new short[]{0, 69, 69, 0, 69, 15, 0, 0, 15}, (short) 169, (short) 1),
            new RecipeItem(new short[]{15, 0, 0, 15, 0, 0, 15, 0, 0}, (short) 156, (short) 1), // Wooden Sword
            new RecipeItem(new short[]{0, 15, 0, 0, 15, 0, 0, 15, 0}, (short) 156, (short) 1),
            new RecipeItem(new short[]{0, 0, 15, 0, 0, 15, 0, 0, 15}, (short) 156, (short) 1),
            new RecipeItem(new short[]{2, 0, 0, 2, 0, 0, 15, 0, 0}, (short) 159, (short) 1), // Stone Sword
            new RecipeItem(new short[]{0, 2, 0, 0, 2, 0, 0, 15, 0}, (short) 159, (short) 1),
            new RecipeItem(new short[]{0, 0, 2, 0, 0, 2, 0, 0, 15}, (short) 159, (short) 1),
            new RecipeItem(new short[]{29, 0, 0, 29, 0, 0, 15, 0, 0}, (short) 16, (short) 1), // Copper Sword
            new RecipeItem(new short[]{0, 29, 0, 0, 29, 0, 0, 15, 0}, (short) 16, (short) 1),
            new RecipeItem(new short[]{0, 0, 29, 0, 0, 29, 0, 0, 15}, (short) 16, (short) 1),
            new RecipeItem(new short[]{30, 0, 0, 30, 0, 0, 15, 0, 0}, (short) 17, (short) 1), // Iron Sword
            new RecipeItem(new short[]{0, 30, 0, 0, 30, 0, 0, 15, 0}, (short) 17, (short) 1),
            new RecipeItem(new short[]{0, 0, 30, 0, 0, 30, 0, 0, 15}, (short) 17, (short) 1),
            new RecipeItem(new short[]{31, 0, 0, 31, 0, 0, 15, 0, 0}, (short) 18, (short) 1), // Silver Sword
            new RecipeItem(new short[]{0, 31, 0, 0, 31, 0, 0, 15, 0}, (short) 18, (short) 1),
            new RecipeItem(new short[]{0, 0, 31, 0, 0, 31, 0, 0, 15}, (short) 18, (short) 1),
            new RecipeItem(new short[]{32, 0, 0, 32, 0, 0, 15, 0, 0}, (short) 19, (short) 1), // Gold Sword
            new RecipeItem(new short[]{0, 32, 0, 0, 32, 0, 0, 15, 0}, (short) 19, (short) 1),
            new RecipeItem(new short[]{0, 0, 32, 0, 0, 32, 0, 0, 15}, (short) 19, (short) 1),
            new RecipeItem(new short[]{38, 0, 0, 38, 0, 0, 15, 0, 0}, (short) 19, (short) 1), // Zinc Sword
            new RecipeItem(new short[]{0, 38, 0, 0, 38, 0, 0, 15, 0}, (short) 19, (short) 1),
            new RecipeItem(new short[]{0, 0, 38, 0, 0, 38, 0, 0, 15}, (short) 19, (short) 1),
            new RecipeItem(new short[]{39, 0, 0, 39, 0, 0, 15, 0, 0}, (short) 19, (short) 1), // Rhymestone Sword
            new RecipeItem(new short[]{0, 39, 0, 0, 39, 0, 0, 15, 0}, (short) 19, (short) 1),
            new RecipeItem(new short[]{0, 0, 39, 0, 0, 39, 0, 0, 15}, (short) 19, (short) 1),
            new RecipeItem(new short[]{40, 0, 0, 40, 0, 0, 15, 0, 0}, (short) 19, (short) 1), // Obdurite Sword
            new RecipeItem(new short[]{0, 40, 0, 0, 40, 0, 0, 15, 0}, (short) 19, (short) 1),
            new RecipeItem(new short[]{0, 0, 40, 0, 0, 40, 0, 0, 15}, (short) 19, (short) 1),
            new RecipeItem(new short[]{73, 0, 0, 73, 0, 0, 15, 0, 0}, (short) 171, (short) 1), // Magnetite Sword
            new RecipeItem(new short[]{0, 73, 0, 0, 73, 0, 0, 15, 0}, (short) 171, (short) 1),
            new RecipeItem(new short[]{0, 0, 73, 0, 0, 73, 0, 0, 15}, (short) 171, (short) 1),
            new RecipeItem(new short[]{69, 0, 0, 69, 0, 0, 15, 0, 0}, (short) 174, (short) 1), // Irradium Sword
            new RecipeItem(new short[]{0, 69, 0, 0, 69, 0, 0, 15, 0}, (short) 174, (short) 1),
            new RecipeItem(new short[]{0, 0, 69, 0, 0, 69, 0, 0, 15}, (short) 174, (short) 1),
            new RecipeItem(new short[]{63, 0, 63, 0, 63, 0, 0, 63, 0}, (short) 190, (short) 1), // Wrench
            new RecipeItem(new short[]{15, 0, 0, 2, 0, 0, 175, 0, 0}, (short) 178, (short) 1), // Lever
            new RecipeItem(new short[]{0, 15, 0, 0, 2, 0, 0, 175, 0}, (short) 178, (short) 1),
            new RecipeItem(new short[]{0, 0, 15, 0, 0, 2, 0, 0, 175}, (short) 178, (short) 1),
            new RecipeItem(new short[]{29, 29, 29, 29, 0, 29, 0, 0, 0}, (short) 105, (short) 1), // Copper Helmet
            new RecipeItem(new short[]{0, 0, 0, 29, 29, 29, 29, 0, 29}, (short) 105, (short) 1),
            new RecipeItem(new short[]{29, 0, 29, 29, 29, 29, 29, 29, 29}, (short) 106, (short) 1), // Copper Chestplate
            new RecipeItem(new short[]{29, 29, 29, 29, 0, 29, 29, 0, 29}, (short) 107, (short) 1), // Copper Leggings
            new RecipeItem(new short[]{29, 0, 29, 29, 0, 29, 0, 0, 0}, (short) 108, (short) 1), // Copper Greaves
            new RecipeItem(new short[]{0, 0, 0, 29, 0, 29, 29, 0, 29}, (short) 108, (short) 1),
            new RecipeItem(new short[]{30, 30, 30, 30, 0, 30, 0, 0, 0}, (short) 109, (short) 1), // Iron Helmet
            new RecipeItem(new short[]{0, 0, 0, 30, 30, 30, 30, 0, 30}, (short) 109, (short) 1),
            new RecipeItem(new short[]{30, 0, 30, 30, 30, 30, 30, 30, 30}, (short) 110, (short) 1), // Iron Chestplate
            new RecipeItem(new short[]{30, 30, 30, 30, 0, 30, 30, 0, 30}, (short) 111, (short) 1), // Iron Leggings
            new RecipeItem(new short[]{30, 0, 30, 30, 0, 30, 0, 0, 0}, (short) 112, (short) 1), // Iron Greaves
            new RecipeItem(new short[]{0, 0, 0, 30, 0, 30, 30, 0, 30}, (short) 112, (short) 1),
            new RecipeItem(new short[]{31, 31, 31, 31, 0, 31, 0, 0, 0}, (short) 113, (short) 1), // Silver Helmet
            new RecipeItem(new short[]{0, 0, 0, 31, 31, 31, 31, 0, 31}, (short) 113, (short) 1),
            new RecipeItem(new short[]{31, 0, 31, 31, 31, 31, 31, 31, 31}, (short) 114, (short) 1), // Silver Chestplate
            new RecipeItem(new short[]{31, 31, 31, 31, 0, 31, 31, 0, 31}, (short) 115, (short) 1), // Silver Leggings
            new RecipeItem(new short[]{31, 0, 31, 31, 0, 31, 0, 0, 0}, (short) 116, (short) 1), // Silver Greaves
            new RecipeItem(new short[]{0, 0, 0, 31, 0, 31, 31, 0, 31}, (short) 116, (short) 1),
            new RecipeItem(new short[]{32, 32, 32, 32, 0, 32, 0, 0, 0}, (short) 117, (short) 1), // Gold Helmet
            new RecipeItem(new short[]{0, 0, 0, 32, 32, 32, 32, 0, 32}, (short) 117, (short) 1),
            new RecipeItem(new short[]{32, 0, 32, 32, 32, 32, 32, 32, 32}, (short) 118, (short) 1), // Gold Chestplate
            new RecipeItem(new short[]{32, 32, 32, 32, 0, 32, 32, 0, 32}, (short) 119, (short) 1), // Gold Leggings
            new RecipeItem(new short[]{32, 0, 32, 32, 0, 32, 0, 0, 0}, (short) 120, (short) 1), // Gold Greaves
            new RecipeItem(new short[]{0, 0, 0, 32, 0, 32, 32, 0, 32}, (short) 120, (short) 1),
            new RecipeItem(new short[]{60, 60, 60, 60, 0, 60, 0, 0, 0}, (short) 121, (short) 1), // Zinc Helmet
            new RecipeItem(new short[]{0, 0, 0, 60, 60, 60, 60, 0, 60}, (short) 121, (short) 1),
            new RecipeItem(new short[]{60, 0, 60, 60, 60, 60, 60, 60, 60}, (short) 122, (short) 1), // Zinc Chestplate
            new RecipeItem(new short[]{60, 60, 60, 60, 0, 60, 60, 0, 60}, (short) 123, (short) 1), // Zinc Leggings
            new RecipeItem(new short[]{60, 0, 60, 60, 0, 60, 0, 0, 0}, (short) 124, (short) 1), // Zinc Greaves
            new RecipeItem(new short[]{0, 0, 0, 60, 0, 60, 60, 0, 60}, (short) 124, (short) 1),
            new RecipeItem(new short[]{61, 61, 61, 61, 0, 61, 0, 0, 0}, (short) 125, (short) 1), // Rhymestone Helmet
            new RecipeItem(new short[]{0, 0, 0, 61, 61, 61, 61, 0, 61}, (short) 125, (short) 1),
            new RecipeItem(new short[]{61, 0, 61, 61, 61, 61, 61, 61, 61}, (short) 126, (short) 1), // Rhymestone Chestplate
            new RecipeItem(new short[]{61, 61, 61, 61, 0, 61, 61, 0, 61}, (short) 127, (short) 1), // Rhymestone Leggings
            new RecipeItem(new short[]{61, 0, 61, 61, 0, 61, 0, 0, 0}, (short) 128, (short) 1), // Rhymestone Greaves
            new RecipeItem(new short[]{0, 0, 0, 61, 0, 61, 61, 0, 61}, (short) 128, (short) 1),
            new RecipeItem(new short[]{62, 62, 62, 62, 0, 62, 0, 0, 0}, (short) 129, (short) 1), // Obdurite Helmet
            new RecipeItem(new short[]{0, 0, 0, 62, 62, 62, 62, 0, 62}, (short) 129, (short) 1),
            new RecipeItem(new short[]{62, 0, 62, 62, 62, 62, 62, 62, 62}, (short) 130, (short) 1), // Obdurite Chestplate
            new RecipeItem(new short[]{62, 62, 62, 62, 0, 62, 62, 0, 62}, (short) 131, (short) 1), // Obdurite Leggings
            new RecipeItem(new short[]{62, 0, 62, 62, 0, 62, 0, 0, 0}, (short) 132, (short) 1), // Obdurite Greaves
            new RecipeItem(new short[]{0, 0, 0, 62, 0, 62, 62, 0, 62}, (short) 132, (short) 1),
            new RecipeItem(new short[]{63, 63, 63, 63, 0, 63, 0, 0, 0}, (short) 133, (short) 1), // Aluminum Helmet
            new RecipeItem(new short[]{0, 0, 0, 63, 63, 63, 63, 0, 63}, (short) 133, (short) 1),
            new RecipeItem(new short[]{63, 0, 63, 63, 63, 63, 63, 63, 63}, (short) 134, (short) 1), // Aluminum Chestplate
            new RecipeItem(new short[]{63, 63, 63, 63, 0, 63, 63, 0, 63}, (short) 135, (short) 1), // Aluminum Leggings
            new RecipeItem(new short[]{63, 0, 63, 63, 0, 63, 0, 0, 0}, (short) 136, (short) 1), // Aluminum Greaves
            new RecipeItem(new short[]{0, 0, 0, 63, 0, 63, 63, 0, 63}, (short) 136, (short) 1),
            new RecipeItem(new short[]{64, 64, 64, 64, 0, 64, 0, 0, 0}, (short) 137, (short) 1), // Lead Helmet
            new RecipeItem(new short[]{0, 0, 0, 64, 64, 64, 64, 0, 64}, (short) 137, (short) 1),
            new RecipeItem(new short[]{64, 0, 64, 64, 64, 64, 64, 64, 64}, (short) 138, (short) 1), // Lead Chestplate
            new RecipeItem(new short[]{64, 64, 64, 64, 0, 64, 64, 0, 64}, (short) 139, (short) 1), // Lead Leggings
            new RecipeItem(new short[]{64, 0, 64, 64, 0, 64, 0, 0, 0}, (short) 140, (short) 1), // Lead Greaves
            new RecipeItem(new short[]{0, 0, 0, 64, 0, 64, 64, 0, 64}, (short) 140, (short) 1),
            new RecipeItem(new short[]{15, 15, 15, 15, 0, 15, 15, 15, 15}, (short) 21, (short) 1), // Wooden Chest
            new RecipeItem(new short[]{2, 2, 2, 2, 21, 2, 2, 2, 2}, (short) 22, (short) 1), // Stone Chest
            new RecipeItem(new short[]{29, 29, 29, 29, 22, 29, 29, 29, 29}, (short) 23, (short) 1), // Copper Chest
            new RecipeItem(new short[]{30, 30, 30, 30, 22, 30, 30, 30, 30}, (short) 24, (short) 1), // Iron Chest
            new RecipeItem(new short[]{31, 31, 31, 31, 22, 31, 31, 31, 31}, (short) 25, (short) 1), // Silver Chest
            new RecipeItem(new short[]{32, 32, 32, 32, 22, 32, 32, 32, 32}, (short) 26, (short) 1), // Gold Chest
            new RecipeItem(new short[]{60, 60, 60, 60, 22, 60, 60, 60, 60}, (short) 151, (short) 1), // Zinc Chest
            new RecipeItem(new short[]{61, 61, 61, 61, 22, 61, 61, 61, 61}, (short) 152, (short) 1), // Rhymestone Chest
            new RecipeItem(new short[]{62, 62, 62, 62, 22, 62, 62, 62, 62}, (short) 153, (short) 1), // Obdurite Chest
            new RecipeItem(new short[]{76, 76, 76, 76, 34, 76, 76, 175, 76}, (short) 177, (short) 1), // Zythium Lamp
            new RecipeItem(new short[]{76, 76, 76, 175, 44, 175, 76, 76, 76}, (short) 180, (short) 1), // Zythium Amplifier
            new RecipeItem(new short[]{76, 76, 76, 44, 175, 44, 76, 76, 76}, (short) 181, (short) 1), // Zythium Inverter
            new RecipeItem(new short[]{76, 175, 76, 175, 175, 175, 76, 175, 76}, (short) 186, (short) 1), // Zythium Delayer
            new RecipeItem(new short[]{15, 15, 0, 15, 15, 0, 0, 0, 0}, (short) 20, (short) 1), // Workbench
            new RecipeItem(new short[]{0, 15, 15, 0, 15, 15, 0, 0, 0}, (short) 20, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 15, 15, 0, 15, 15, 0}, (short) 20, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 15, 15, 0, 15, 15}, (short) 20, (short) 1),
            new RecipeItem(new short[]{160, 160, 0, 160, 160, 0, 0, 0, 0}, (short) 15, (short) 1), // Bark -> Wood
            new RecipeItem(new short[]{0, 160, 160, 0, 160, 160, 0, 0, 0}, (short) 15, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 160, 160, 0, 160, 160, 0}, (short) 15, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 160, 160, 0, 160, 160}, (short) 15, (short) 1),
            new RecipeItem(new short[]{2, 2, 0, 2, 2, 0, 0, 0, 0}, (short) 161, (short) 4), // Cobblestone
            new RecipeItem(new short[]{0, 2, 2, 0, 2, 2, 0, 0, 0}, (short) 161, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 2, 2, 0, 2, 2, 0}, (short) 161, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 2, 2, 0, 2, 2}, (short) 161, (short) 4),
            new RecipeItem(new short[]{162, 162, 0, 162, 162, 0, 0, 0, 0}, (short) 163, (short) 4), // Chiseled Cobblestone
            new RecipeItem(new short[]{0, 162, 162, 0, 162, 162, 0, 0, 0}, (short) 163, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 162, 162, 0, 162, 162, 0}, (short) 163, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 162, 162, 0, 162, 162}, (short) 163, (short) 4),
            new RecipeItem(new short[]{163, 163, 0, 163, 163, 0, 0, 0, 0}, (short) 164, (short) 4), // Stone Bricks
            new RecipeItem(new short[]{0, 163, 163, 0, 163, 163, 0, 0, 0}, (short) 164, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 163, 163, 0, 163, 163, 0}, (short) 164, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 163, 163, 0, 163, 163}, (short) 164, (short) 4),
            new RecipeItem(new short[]{2, 2, 2, 2, 0, 2, 2, 2, 2}, (short) 27, (short) 1), // Furnace
            new RecipeItem(new short[]{67, 67, 67, 0, 0, 0, 0, 0, 0}, (short) 175, (short) 10), // Zythium Wire
            new RecipeItem(new short[]{0, 0, 0, 67, 67, 67, 0, 0, 0}, (short) 175, (short) 20),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 0, 67, 67, 67}, (short) 175, (short) 20),
            new RecipeItem(new short[]{2, 0, 0, 0, 2, 0, 0, 0, 0}, (short) 33, (short) 1), // Stone Lighter
            new RecipeItem(new short[]{0, 2, 0, 0, 0, 2, 0, 0, 0}, (short) 33, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 2, 0, 0, 0, 2, 0}, (short) 33, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 2, 0, 0, 0, 2}, (short) 33, (short) 1),
            new RecipeItem(new short[]{0, 2, 0, 2, 0, 0, 0, 0, 0}, (short) 33, (short) 1),
            new RecipeItem(new short[]{0, 0, 2, 0, 2, 0, 0, 0, 0}, (short) 33, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 2, 0, 2, 0, 0}, (short) 33, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 2, 0, 2, 0}, (short) 33, (short) 1),
            new RecipeItem(new short[]{15, 0, 0, 15, 0, 0, 0, 0, 0}, (short) 35, (short) 4),  // Wooden Torch
            new RecipeItem(new short[]{0, 15, 0, 0, 15, 0, 0, 0, 0}, (short) 35, (short) 4),
            new RecipeItem(new short[]{0, 0, 15, 0, 0, 15, 0, 0, 0}, (short) 35, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 15, 0, 0, 15, 0, 0}, (short) 35, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 15, 0, 0, 15, 0}, (short) 35, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 15, 0, 0, 15}, (short) 35, (short) 4),
            new RecipeItem(new short[]{28, 0, 0, 15, 0, 0, 0, 0, 0}, (short) 36, (short) 4), // Coal Torch
            new RecipeItem(new short[]{0, 28, 0, 0, 15, 0, 0, 0, 0}, (short) 36, (short) 4),
            new RecipeItem(new short[]{0, 0, 28, 0, 0, 15, 0, 0, 0}, (short) 36, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 28, 0, 0, 15, 0, 0}, (short) 36, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 28, 0, 0, 15, 0}, (short) 36, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 28, 0, 0, 15}, (short) 36, (short) 4),
            new RecipeItem(new short[]{34, 0, 0, 15, 0, 0, 0, 0, 0}, (short) 37, (short) 4), // Lumenstone Torch
            new RecipeItem(new short[]{0, 34, 0, 0, 15, 0, 0, 0, 0}, (short) 37, (short) 4),
            new RecipeItem(new short[]{0, 0, 34, 0, 0, 15, 0, 0, 0}, (short) 37, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 34, 0, 0, 15, 0, 0}, (short) 37, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 34, 0, 0, 15, 0}, (short) 37, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 34, 0, 0, 15}, (short) 37, (short) 4),
            new RecipeItem(new short[]{44, 0, 0, 15, 0, 0, 0, 0, 0}, (short) 176, (short) 4), // Zythium Torch
            new RecipeItem(new short[]{0, 44, 0, 0, 15, 0, 0, 0, 0}, (short) 176, (short) 4),
            new RecipeItem(new short[]{0, 0, 44, 0, 0, 15, 0, 0, 0}, (short) 176, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 44, 0, 0, 15, 0, 0}, (short) 176, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 44, 0, 0, 15, 0}, (short) 176, (short) 4),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 44, 0, 0, 15}, (short) 176, (short) 4),
            new RecipeItem(new short[]{15, 15, 0, 0, 0, 0, 0, 0, 0}, (short) 183, (short) 1), // Wooden Pressure Plate
            new RecipeItem(new short[]{0, 15, 15, 0, 0, 0, 0, 0, 0}, (short) 183, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 15, 15, 0, 0, 0, 0}, (short) 183, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 15, 15, 0, 0, 0}, (short) 183, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 0, 15, 15, 0}, (short) 183, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 0, 0, 15, 15}, (short) 183, (short) 1),
            new RecipeItem(new short[]{2, 2, 0, 0, 0, 0, 0, 0, 0}, (short) 184, (short) 1), // Stone Pressure Plate
            new RecipeItem(new short[]{0, 2, 2, 0, 0, 0, 0, 0, 0}, (short) 184, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 2, 2, 0, 0, 0, 0}, (short) 184, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 2, 2, 0, 0, 0}, (short) 184, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 0, 2, 2, 0}, (short) 184, (short) 1),
            new RecipeItem(new short[]{0, 0, 0, 0, 0, 0, 0, 2, 2}, (short) 184, (short) 1),
            new RecipeItem(new short[]{162, 44, 162, 0, 175, 0, 0, 0, 0}, (short) 185, (short) 1), // Zythium Pressure Plate
            new RecipeItem(new short[]{0, 0, 0, 162, 44, 162, 0, 175, 0}, (short) 185, (short) 1)
        };

        RECIPES.put("workbench", list_thing1);

        RecipeItem[] list_thing2 = {
            new RecipeItem(new short[]{15, 15, 15, 15}, (short) 20, (short) 1), // Workbench
            new RecipeItem(new short[]{160, 160, 160, 160}, (short) 15, (short) 1), // Bark -> Wood
            new RecipeItem(new short[]{2, 2, 2, 2}, (short) 161, (short) 4), // Cobblestone
            new RecipeItem(new short[]{162, 162, 162, 162}, (short) 163, (short) 4), // Chiseled Cobblestone
            new RecipeItem(new short[]{163, 163, 163, 163}, (short) 164, (short) 4), // Stone Bricks
            new RecipeItem(new short[]{2, 0, 0, 2}, (short) 33, (short) 1), // Stone Lighter
            new RecipeItem(new short[]{0, 2, 2, 0}, (short) 33, (short) 1),
            new RecipeItem(new short[]{15, 0, 15, 0}, (short) 35, (short) 4),  // Wooden Torch
            new RecipeItem(new short[]{0, 15, 0, 15}, (short) 35, (short) 4),
            new RecipeItem(new short[]{28, 0, 15, 0}, (short) 36, (short) 4), // Coal Torch
            new RecipeItem(new short[]{0, 28, 0, 15}, (short) 36, (short) 4),
            new RecipeItem(new short[]{34, 0, 15, 0}, (short) 37, (short) 4), // Lumenstone Torch
            new RecipeItem(new short[]{0, 34, 0, 15}, (short) 37, (short) 4),
            new RecipeItem(new short[]{44, 0, 15, 0}, (short) 176, (short) 4), // Zythium Torch
            new RecipeItem(new short[]{0, 44, 0, 15}, (short) 176, (short) 4),
            new RecipeItem(new short[]{15, 15, 0, 0}, (short) 183, (short) 1), // Wooden Pressure Plate
            new RecipeItem(new short[]{0, 0, 15, 15}, (short) 183, (short) 1),
            new RecipeItem(new short[]{2, 2, 0, 0}, (short) 184, (short) 1), // Stone Pressure Plate
            new RecipeItem(new short[]{0, 0, 2, 2}, (short) 184, (short) 1)
        };

        RECIPES.put("cic", list_thing2);

        RecipeItem[] list_thing3 = {
            new RecipeItem(new short[]{15, 167, 0, 0, 0, 0, 0, 0, 0}, (short) 168, (short) 1),
            new RecipeItem(new short[]{162, 0, 0, 0, 0, 0, 0, 0, 0}, (short) 182, (short) 1)
        };

        RECIPES.put("shapeless", list_thing3);

        RecipeItem[] list_thing4 = {
            new RecipeItem(new short[]{15, 167, 0, 0}, (short) 168, (short) 1),
            new RecipeItem(new short[]{162, 0, 0, 0}, (short) 182, (short) 1)
        };

        RECIPES.put("shapeless_cic", list_thing4);
    }

    public int addItem(short item, short quantity) {
        if (TerrariaClone.getTOOLDURS().get(item) != null) {
            return addItem(item, quantity, TerrariaClone.getTOOLDURS().get(item));
        }
        else {
            return addItem(item, quantity, (short)0);
        }
    }

    public int addItem(short item, short quantity, short durability) {
        for (i=0; i<40; i++) {
            if (ids[i] == item && nums[i] < TerrariaClone.getMAXSTACKS().get(ids[i])) {
                if (TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i] >= quantity) {
                    nums[i] += quantity;
                    update(i);
                    return 0;
                }
                else {
                    quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i];
                    nums[i] = TerrariaClone.getMAXSTACKS().get(ids[i]);
                    update(i);
                }
            }
        }
        for (i=0; i<40; i++) {
            if (ids[i] == 0) {
                ids[i] = item;
                if (quantity <= TerrariaClone.getMAXSTACKS().get(ids[i])) {
                    nums[i] = quantity;
                    durs[i] = durability;
                    update(i);
                    return 0;
                }
                else {
                    nums[i] = TerrariaClone.getMAXSTACKS().get(ids[i]);
                    durs[i] = durability;
                    quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]);
                }
            }
        }
        return quantity;
    }

    public int addLocation(int i, short item, short quantity, short durability) {
        if (ids[i] == item) {
            if (TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i] >= quantity) {
                nums[i] += quantity;
                update(i);
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]) - nums[i];
                nums[i] = TerrariaClone.getMAXSTACKS().get(ids[i]);
                update(i);
            }
        }
        else {
            if (quantity <= TerrariaClone.getMAXSTACKS().get(ids[i])) {
                ids[i] = item;
                nums[i] = quantity;
                durs[i] = durability;
                update(i);
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ids[i]);
                return quantity;
            }
        }
        return quantity;
    }

    public int removeLocation(int i, short quantity) {
        if (nums[i] >= quantity) {
            nums[i] -= quantity;
            if (nums[i] == 0) {
                ids[i] = 0;
            }
            update(i);
            return 0;
        }
        else {
            quantity -= nums[i];
            nums[i] = 0;
            ids[i] = 0;
            update(i);
        }
        return quantity;
    }

    public void reloadImage() {
        image = new BufferedImage(466, 190, BufferedImage.TYPE_INT_ARGB);
        box = loadImage("interface/inventory.png");
        box_selected = loadImage("interface/inventory_selected.png");
        g2 = image.createGraphics();
        for (x=0; x<10; x++) {
            for (y=0; y<4; y++) {
                if (x == 0 && y == 0) {
                    g2.drawImage(box_selected,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
                else {
                    g2.drawImage(box,
                        x*46+6, y*46+6, x*46+46, y*46+46,
                        0, 0, 40, 40,
                        null);
                    if (y == 0) {
                        g2.setFont(font);
                        g2.setColor(Color.BLACK);
                        g2.drawString(f(x) + " ", x*46+trolx, y*46+troly);
                    }
                }
            }
        }
        for (i=0; i<40; i++) {
            update(i);
        }
    }

    public void update(int i) {
        py = (int)(i/10);
        px = i-(py*10);
        for (x=px*46+6; x<px*46+46; x++) {
            for (y=py*46+6; y<py*46+46; y++) {
                image.setRGB(x, y, 9539985);
            }
        }
        g2 = image.createGraphics();
        if (i == selection) {
            g2.drawImage(box_selected,
                px*46+6, py*46+6, px*46+46, py*46+46,
                0, 0, 40, 40,
                null);
            if (py == 0) {
                g2.setFont(font);
                g2.setColor(Color.BLACK);
                g2.drawString(f(px) + " ", px*46+trolx, py*46+troly);
            }
        }
        else {
            g2.drawImage(box,
                px*46+6, py*46+6, px*46+46, py*46+46,
                0, 0, 40, 40,
                null);
            if (py == 0) {
                g2.setFont(font);
                g2.setColor(Color.BLACK);
                g2.drawString(f(px) + " ", px*46+trolx, py*46+troly);
            }
        }
        if (ids[i] != 0) {
            width = TerrariaClone.getItemImgs().get(ids[i]).getWidth();
            height = TerrariaClone.getItemImgs().get(ids[i]).getHeight();
            g2.drawImage(TerrariaClone.getItemImgs().get(ids[i]),
                px*46+14+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+14+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*46+38-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+38-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                0, 0, width, height,
                null);

            if (nums[i] > 1) {
                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString(nums[i] + " ", px*46+15, py*46+40);
            }
        }
    }

    public void select(int i) {
        if (i == 0) {
            n = selection;
            selection = 9;
            update(n);
            update(9);
        }
        else {
            n = selection;
            selection = i - 1;
            update(n);
            update(i - 1);
        }
    }

    public void select2(int i) {
        n = selection;
        selection = i;
        update(n);
        update(i);
    }

    public short tool() {
        return ids[selection];
    }

    public void renderCollection(ItemCollection ic) {
        if (ic.getType() == ItemType.CIC) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/cic.png"));
                for (i=0; i<4; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.ARMOR) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/armor.png"));
                CX = 1;
                CY = 4;
                for (i=0; i<4; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.WORKBENCH) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/workbench.png"));
                for (i=0; i<9; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.WOODEN_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/wooden_chest.png"));
                CX = 3;
                CY = 3;
                for (i=0; i<9; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.STONE_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/stone_chest.png"));
                CX = 5;
                CY = 3;
                for (i=0; i<15; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.COPPER_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/copper_chest.png"));
                CX = 5;
                CY = 4;
                for (i=0; i<20; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.IRON_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/iron_chest.png"));
                CX = 7;
                CY = 4;
                for (i=0; i<28; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.SILVER_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/silver_chest.png"));
                CX = 7;
                CY = 5;
                for (i=0; i<35; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.GOLD_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/gold_chest.png"));
                CX = 7;
                CY = 6;
                for (i=0; i<42; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.ZINC_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/zinc_chest.png"));
                CX = 7;
                CY = 8;
                for (i=0; i<56; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.RHYMESTONE_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/rhymestone_chest.png"));
                CX = 8;
                CY = 9;
                for (i=0; i<72; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.OBDURITE_CHEST) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/obdurite_chest.png"));
                CX = 10;
                CY = 10;
                for (i=0; i<100; i++) {
                    updateIC(ic, i);
                }
            }
        } else if (ic.getType() == ItemType.FURNACE) {
            if (ic.getImage() == null) {
                ic.setImage(loadImage("interface/furnace.png"));
                for (i=-1; i<4; i++) {
                    updateIC(ic, i);
                }
            }
        }
    }

    public int addLocationIC(ItemCollection ic, int i, short item, short quantity) {
        return addLocationIC(ic, i, item, quantity, (short)0);
    }

    public int addLocationIC(ItemCollection ic, int i, short item, short quantity, short durability) {
        if (ic.getIds()[i] == item) {
            if (TerrariaClone.getMAXSTACKS().get(ic.getIds()[i]) - ic.getNums()[i] >= quantity) {
                ic.getNums()[i] += quantity;
                if (ic.getImage() != null) {
                    updateIC(ic, i);
                }
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ic.getIds()[i]) - ic.getNums()[i];
                ic.getNums()[i] = TerrariaClone.getMAXSTACKS().get(ic.getIds()[i]);
                if (ic.getImage() != null) {
                    updateIC(ic, i);
                }
            }
        } else {
            if (quantity <= TerrariaClone.getMAXSTACKS().get(ic.getIds()[i])) {
                ic.getIds()[i] = item;
                ic.getNums()[i] = quantity;
                ic.getNums()[i] = durability;
                if (ic.getImage() != null) {
                    updateIC(ic, i);
                }
                return 0;
            }
            else {
                quantity -= TerrariaClone.getMAXSTACKS().get(ic.getIds()[i]);
                return quantity;
            }
        }
        return quantity;
    }

    public int removeLocationIC(ItemCollection ic, int i, short quantity) {
        if (ic.getNums()[i] >= quantity) {
            ic.getNums()[i] -= quantity;
            if (ic.getNums()[i] == 0) {
                ic.getIds()[i] = 0;
            }
            if (ic.getImage() != null) {
                updateIC(ic, i);
            }
            return 0;
        }
        else {
            quantity -= ic.getNums()[i];
            ic.getNums()[i] = 0;
            ic.getIds()[i] = 0;
            if (ic.getImage() != null) {
                updateIC(ic, i);
            }
        }
        return quantity;
    }

    public void updateIC(ItemCollection ic, int i) {
        if (ic.getType() == ItemType.CIC) {
            py = (int)(i/2);
            px = i-(py*2);
            for (x=px*40; x<px*40+40; x++) {
                for (y=py*40; y<py*40+40; y++) {
                    ic.getImage().setRGB(x, y, 9539985);
                }
            }
            g2 = ic.getImage().createGraphics();
            g2.drawImage(box,
                px*40, py*40, px*40+40, py*40+40,
                0, 0, 40, 40,
                null);
            if (ic.getIds()[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.getIds()[i]),
                    px*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);
                if (ic.getNums()[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.getNums()[i] + " ", px*40+9, py*40+34);
                }
            }
            ic.getIds()[4] = 0;
            ic.getIds()[4] = 0;
            for (RecipeItem r2 : RECIPES.get("cic")) {
                if (ic.areIdsEquals(r2.getValues())) {
                    ic.getIds()[4] = r2.getId();
                    ic.getNums()[4] = r2.getNum();
                    if (TerrariaClone.getTOOLDURS().get(r2.getId()) != null)
                        ic.getDurs()[4] = TerrariaClone.getTOOLDURS().get(r2.getId());
                    break;
                }
            }
            for (RecipeItem r2 : RECIPES.get("shapeless_cic")) {
                if (ic.areInvalidIds(r2.getValues())) {
                    ic.getIds()[4] = r2.getId();
                    ic.getNums()[4] = r2.getNum();
                    if (TerrariaClone.getTOOLDURS().get(r2.getId()) != null)
                        ic.getDurs()[4] = TerrariaClone.getTOOLDURS().get(r2.getId());
                    break;
                }
            }
            for (x=3*40; x<3*40+40; x++) {
                for (y=20; y<20+40; y++) {
                    ic.getImage().setRGB(x, y, 9539985);
                }
            }
            g2 = ic.getImage().createGraphics();
            g2.drawImage(box,
                3*40, 20, 3*40+40, 20+40,
                0, 0, 40, 40,
                null);
            if (ic.getIds()[4] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.getIds()[4]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.getIds()[4]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.getIds()[4]),
                    3*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 20+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), 3*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 20+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);

                if (ic.getNums()[4] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.getNums()[4] + " ", 3*40+9, 20+34);
                }
            }
        } else if (ic.getType() == ItemType.ARMOR) {
            py = (int)(i/CX);
            px = i-(py*CX);
            for (x=px*46; x<px*46+40; x++) {
                for (y=py*46; y<py*46+40; y++) {
                    ic.getImage().setRGB(x, y, 9539985);
                }
            }
            g2 = ic.getImage().createGraphics();
            g2.drawImage(box,
                px*46, py*46, px*46+40, py*46+40,
                0, 0, 40, 40,
                null);
            if (ic.getIds()[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.getIds()[i]),
                    px*46+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*46+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);

                if (ic.getNums()[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.getNums()[i] + " ", px*46+9, py*46+34);
                }
            }
        } else if (ic.getType() == ItemType.WORKBENCH) {
            py = (int)(i/3);
            px = i-(py*3);
            for (x=px*40; x<px*40+40; x++) {
                for (y=py*40; y<py*40+40; y++) {
                    ic.getImage().setRGB(x, y, 9539985);
                }
            }
            g2 = ic.getImage().createGraphics();
            g2.drawImage(box,
                px*40, py*40, px*40+40, py*40+40,
                0, 0, 40, 40,
                null);
            if (ic.getIds()[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.getIds()[i]),
                    px*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);
                if (ic.getNums()[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.getNums()[i] + " ", px*40+9, py*40+34);
                }
            }
            ic.getIds()[9] = 0;
            for (RecipeItem r2 : RECIPES.get("workbench")) {
                if (ic.areIdsEquals(r2.getValues())) {
                    ic.getIds()[9] = r2.getId();
                    ic.getNums()[9] = r2.getNum();
                    if (TerrariaClone.getTOOLDURS().get(r2.getId()) != null)
                        ic.getDurs()[9] = TerrariaClone.getTOOLDURS().get(r2.getId());
                    break;
                }
            }
            for (RecipeItem r2 : RECIPES.get("shapeless")) {
                if (ic.areInvalidIds(r2.getValues())) {
                    ic.getIds()[9] = r2.getId();
                    ic.getNums()[9] = r2.getNum();
                    if (TerrariaClone.getTOOLDURS().get(r2.getId()) != null)
                        ic.getDurs()[9] = TerrariaClone.getTOOLDURS().get(r2.getId());
                    break;
                }
            }
            for (x=4*40; x<4*40+40; x++) {
                for (y=1*40; y<1*40+40; y++) {
                    ic.getImage().setRGB(x, y, 9539985);
                }
            }
            g2 = ic.getImage().createGraphics();
            g2.drawImage(box,
                4*40, 1*40, 4*40+40, 1*40+40,
                0, 0, 40, 40,
                null);
            if (ic.getIds()[9] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.getIds()[9]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.getIds()[9]).getHeight();
                    g2.drawImage(TerrariaClone.getItemImgs().get(ic.getIds()[9]),
                        4*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 1*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), 4*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), 1*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                        0, 0, width, height,
                        null);

                if (ic.getNums()[9] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.getNums()[9] + " ", 4*40+9, 1*40+34);
                }
            }
        } else if (ic.getType() == ItemType.WOODEN_CHEST || ic.getType() == ItemType.STONE_CHEST ||
            ic.getType() == ItemType.COPPER_CHEST || ic.getType() == ItemType.IRON_CHEST ||
            ic.getType() == ItemType.SILVER_CHEST || ic.getType() == ItemType.GOLD_CHEST ||
            ic.getType() == ItemType.ZINC_CHEST || ic.getType() == ItemType.RHYMESTONE_CHEST ||
            ic.getType() == ItemType.OBDURITE_CHEST) {
            py = (int)(i/CX);
            px = i-(py*CX);
            for (x=px*46; x<px*46+40; x++) {
                for (y=py*46; y<py*46+40; y++) {
                    ic.getImage().setRGB(x, y, 9539985);
                }
            }
            g2 = ic.getImage().createGraphics();
            g2.drawImage(box,
                px*46, py*46, px*46+40, py*46+40,
                0, 0, 40, 40,
                null);
            if (ic.getIds()[i] != 0) {
                width = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getWidth();
                height = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getHeight();
                g2.drawImage(TerrariaClone.getItemImgs().get(ic.getIds()[i]),
                    px*46+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), px*46+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), py*46+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);

                if (ic.getNums()[i] > 1) {
                    g2.setFont(font);
                    g2.setColor(Color.WHITE);
                    g2.drawString(ic.getNums()[i] + " ", px*46+9, py*46+34);
                }
            }
        } else if (ic.getType() == ItemType.FURNACE) {
            if (i == -1) {
                for (y=0; y<5; y++) {
                    for (x=0; x<ic.getFUELP()*38; x++) {
                        ic.getImage().setRGB(x+1, y+51, new Color(255, 0, 0).getRGB());
                    }
                    for (x=(int)(ic.getFUELP()*38); x<38; x++) {
                        ic.getImage().setRGB(x+1, y+51, new Color(145, 145, 145).getRGB());
                    }
                }
                for (x=0; x<5; x++) {
                    for (y=0; y<ic.getSMELTP()*38; y++) {
                        ic.getImage().setRGB(x+40, y+1, new Color(255, 0, 0).getRGB());
                    }
                    for (y=(int)(ic.getSMELTP()*38); y<38; y++) {
                        ic.getImage().setRGB(x+40, y+1, new Color(145, 145, 145).getRGB());
                    }
                }
            }
            else {
                if (i == 0) {
                    fpx = 0;
                    fpy = 0;
                }
                if (i == 1) {
                    fpx = 0;
                    fpy = 1.4;
                }
                if (i == 2) {
                    fpx = 0;
                    fpy = 2.4;
                }
                if (i == 3) {
                    fpx = 1.4;
                    fpy = 0;
                }
                for (x=(int)(fpx*40); x<fpx*40+40; x++) {
                    for (y=(int)(fpy*40); y<fpy*40+40; y++) {
                        ic.getImage().setRGB(x, y, 9539985);
                    }
                }
                g2 = ic.getImage().createGraphics();
                g2.drawImage(box,
                    (int)(fpx*40), (int)(fpy*40), (int)(fpx*40+40), (int)(fpy*40+40),
                    0, 0, 40, 40,
                    null);
                if (ic.getIds()[i] != 0) {
                    width = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getWidth();
                    height = TerrariaClone.getItemImgs().get(ic.getIds()[i]).getHeight();
                    g2.drawImage(TerrariaClone.getItemImgs().get(ic.getIds()[i]),
                        (int)(fpx*40+8+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2)), (int)(fpy*40+8+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2)), (int)(fpx*40+32-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2)), (int)(fpy*40+32-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2)),
                        0, 0, width, height,
                        null);

                    if (ic.getNums()[i] > 1) {
                        g2.setFont(font);
                        g2.setColor(Color.WHITE);
                        g2.drawString(ic.getNums()[i] + " ", (int)(fpx*40+9), (int)(fpy*40+34));
                    }
                }
            }
        }
    }

    public void useRecipeWorkbench(ItemCollection ic) {
        for (RecipeItem r2 : RECIPES.get("workbench")) {
            if (ic.areIdsEquals(r2.getValues())) {
                for (i=0; i<9; i++) {
                    removeLocationIC(ic, i, (short) 1);
                    updateIC(ic, i);
                }
            }
        }
        for (RecipeItem r2 : RECIPES.get("shapeless")) {
            if (ic.areInvalidIds(r2.getValues())) {
                List<Short> r3 = new ArrayList<>(11);
                for (k=0; k<r2.getValues().length; k++) {
                    r3.add(r2.getValues()[k]);
                }
                for (k=0; k<9; k++) {
                    n = r3.indexOf(ic.getIds()[k]);
                    r3.remove(n);
                    removeLocationIC(ic, k, (short) 1);
                    updateIC(ic, k);
                }
                break;
            }
        }
    }

    public void useRecipeCIC(ItemCollection ic) {
        for (RecipeItem r2 : RECIPES.get("cic")) {
            if (ic.areIdsEquals(r2.getValues())) {
                for (i=0; i<4; i++) {
                    removeLocationIC(ic, i, (short) 1);
                    updateIC(ic, i);
                }
            }
        }
        for (RecipeItem r2 : RECIPES.get("shapeless_cic")) {
            if (ic.areInvalidIds(r2.getValues())) {
                List<Short> r3 = new ArrayList<>(6);
                for (k=0; k<r2.getValues().length-2; k++) {
                    r3.add(r2.getValues()[k]);
                }
                for (k=0; k<4; k++) {
                    n = r3.indexOf(ic.getIds()[k]);
                    r3.remove(n);
                    removeLocationIC(ic, k, (short) 1);
                    updateIC(ic, k);
                }
                break;
            }
        }
    }

    private static BufferedImage loadImage(String path) {
        URL url = TerrariaClone.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            System.out.println("[ERROR] could not load image '" + path + "'.");
        }
        return image;
    }

    private static int f(int x) {
        if (x == 9) {
            return 0;
        }
        return x + 1;
    }

    public static int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static void print(int text) {
        System.out.println(text);
    }

    public static void print(double text) {
        System.out.println(text);
    }

    public static void print(short text) {
        System.out.println(text);
    }

    public static void print(boolean text) {
        System.out.println(text);
    }

    public static void print(Object text) {
        System.out.println(text);
    }
}
