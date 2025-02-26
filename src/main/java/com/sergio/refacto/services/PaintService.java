package com.sergio.refacto.services;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.sergio.refacto.items.Entity;
import com.sergio.refacto.items.Player;
import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.EntityType;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.ItemType;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.init.UIEntitiesInitializer;
import com.sergio.refacto.items.ImagesContainer;
import com.sergio.refacto.items.WorldContainer;
import com.sergio.refacto.tools.MathTool;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaintService {

    BufferedImage sun, moon;

    Graphics2D graphics;

    private static Map<EntityType, String> UIENTITIES;

    public PaintService() {
        UIENTITIES = UIEntitiesInitializer.init();

        sun = ResourcesLoader.loadImage("environment/sun.png");
        moon = ResourcesLoader.loadImage("environment/moon.png");
    }

    public void paintLights(TerrariaClone graphicContainer, Graphics g, int twx, int twy, int tx, int ty) {
        if (!DebugContext.LIGHT) {
            g.drawImage(ImagesContainer.getInstance().lightLevelsImgs.get((int) (float) graphicContainer.worldContainer.lights[ty][tx]),
                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                    0, 0, TerrariaClone.IMAGESIZE, TerrariaClone.IMAGESIZE,
                    null);
        }
    }

    public void paintBlock(TerrariaClone graphicContainer, Graphics worldGraphic, Graphics fWorldGraphic, int twx, int twy, int tx, int ty) {
        for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
            if (graphicContainer.worldContainer.blocks[l][ty][tx] != Blocks.AIR) {
                Graphics g;
                if (l == 2) {
                    g = fWorldGraphic;
                } else {
                    g = worldGraphic;
                }
                g.drawImage(graphicContainer.loadBlock(graphicContainer.worldContainer.blocks[l][ty][tx], graphicContainer.worldContainer.blocksDirections[l][ty][tx], graphicContainer.worldContainer.blocksDirectionsIntensity[ty][tx], graphicContainer.worldContainer.blocksTextureIntensity[ty][tx], graphicContainer.worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                        tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                        0, 0, TerrariaClone.IMAGESIZE, TerrariaClone.IMAGESIZE,
                        null);
            } else if (graphicContainer.worldContainer.wcnct[ty][tx] && graphicContainer.worldContainer.blocks[l][ty][tx].isZythiumWire()) {
                Graphics g;
                if (l == 2) {
                    g = fWorldGraphic;
                } else {
                    g = worldGraphic;
                }
                g.drawImage(ImagesContainer.getInstance().wcnct_px,
                        tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                        0, 0, TerrariaClone.IMAGESIZE, TerrariaClone.IMAGESIZE,
                        null);
            }
        }
    }

    public void paintBackground(TerrariaClone graphicContainer, Graphics g, int twx, int twy, int tx, int ty) {
        for (int y = 0; y < WorldContainer.BLOCK_SIZE; y++) {
            for (int x = 0; x < WorldContainer.BLOCK_SIZE; x++) {
                graphicContainer.worldContainer.worlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE + y, 9539985);
                graphicContainer.worldContainer.fworlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE + y, 9539985);
            }
        }
        if (graphicContainer.worldContainer.blocksBackgrounds[ty][tx] != Backgrounds.EMPTY) {
            g.drawImage(ImagesContainer.getInstance().backgroundImgs.get(graphicContainer.worldContainer.blocksBackgrounds[ty][tx]),
                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                    0, 0, TerrariaClone.IMAGESIZE, TerrariaClone.IMAGESIZE,
                    null);
        }
    }

    public void paint(TerrariaClone graphicContainer, Graphics g) {
        if (graphics == null) {
            graphics = graphicContainer.screen.createGraphics();
        }
        graphics.setColor(graphicContainer.bg);
        graphics.fillRect(0, 0, graphicContainer.getWidth(), graphicContainer.getHeight());

        switch (graphicContainer.state) {
            case IN_GAME:
                paintInGameScreen(graphicContainer);
                break;
            case LOADING_GRAPHICS:
                graphics.setFont(graphicContainer.loadFont);
                graphics.setColor(Color.GREEN);
                graphics.drawString("Loading graphics... Please wait.", graphicContainer.getWidth() / 2 - 200, graphicContainer.getHeight() / 2 - 5);
                break;
            case TITLE_SCREEN:
                graphics.drawImage(ImagesContainer.getInstance().titleScreen,
                        0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                        0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                        null);
                break;
            case SELECT_WORLD:
                graphics.drawImage(ImagesContainer.getInstance().selectWorld,
                        0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                        0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                        null);
                for (int i = 0; i < graphicContainer.filesInfo.size(); i++) {
                    graphics.setFont(graphicContainer.worldFont);
                    graphics.setColor(Color.BLACK);
                    graphics.drawString(graphicContainer.filesInfo.get(i).getName(), 180, 140 + i * 35);
                    graphics.fillRect(166, 150 + i * 35, 470, 3);
                }
                break;
            case NEW_WORLD:
                graphics.drawImage(ImagesContainer.getInstance().newWorld,
                        0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                        0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                        null);
                graphics.drawImage(graphicContainer.newWorldName.getImage(),
                        200, 240, 600, 270,
                        0, 0, 400, 30,
                        null);
                break;
            case GENERATING_WORLD:
                graphics.setFont(graphicContainer.loadFont);
                graphics.setColor(Color.GREEN);
                graphics.drawString("Generating new world... Please wait.", graphicContainer.getWidth() / 2 - 200, graphicContainer.getHeight() / 2 - 5);
                break;
        }

        g.drawImage(graphicContainer.screen,
                0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                0, 0, graphicContainer.getWidth(), graphicContainer.getHeight(),
                null);
    }

    private void paintInGameScreen(TerrariaClone graphicContainer) {
        paintSunMoonClouds(graphicContainer);

        for (int pwy = 0; pwy < 2; pwy++) {
            for (int pwx = 0; pwx < 2; pwx++) {
                int pwxc = pwx + graphicContainer.ou;
                int pwyc = pwy + graphicContainer.ov;
                if (graphicContainer.worldContainer.worlds[pwy][pwx] != null) {
                    if (graphicContainer.worldContainer.player.isPlayerIntoChunk(pwxc, pwyc, graphicContainer.getWidth(), graphicContainer.getHeight())) {
                        graphics.drawImage(graphicContainer.worldContainer.worlds[pwy][pwx],
                                pwxc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2, pwyc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2, pwxc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2 + WorldContainer.CHUNK_SIZE, pwyc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2 + WorldContainer.CHUNK_SIZE,
                                0, 0, WorldContainer.CHUNK_SIZE, WorldContainer.CHUNK_SIZE,
                                null);
                    }
                }
            }
        }

        paintPlayer(graphicContainer);

        paintAllEntities(graphicContainer);

        paintTool(graphicContainer);

        for (int pwy = 0; pwy < 2; pwy++) {
            for (int pwx = 0; pwx < 2; pwx++) {
                int pwxc = pwx + graphicContainer.ou;
                int pwyc = pwy + graphicContainer.ov;
                if (graphicContainer.worldContainer.fworlds[pwy][pwx] != null) {
                    if (graphicContainer.worldContainer.player.isPlayerIntoChunk(pwxc, pwyc, graphicContainer.getWidth(), graphicContainer.getHeight())) {
                        graphics.drawImage(graphicContainer.worldContainer.fworlds[pwy][pwx],
                                pwxc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2, pwyc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2, pwxc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2 + WorldContainer.CHUNK_SIZE, pwyc * WorldContainer.CHUNK_SIZE - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2 + WorldContainer.CHUNK_SIZE,
                                0, 0, WorldContainer.CHUNK_SIZE, WorldContainer.CHUNK_SIZE,
                                null);
                    }
                }
            }
        }

        paintInventory(graphicContainer);

        if (graphicContainer.worldContainer.ic != null) {
            graphics.drawImage(graphicContainer.worldContainer.ic.getImage(),
                    6, graphicContainer.worldContainer.inventory.getImage().getHeight() + 46, 6 + graphicContainer.worldContainer.ic.getImage().getWidth(), graphicContainer.worldContainer.inventory.getImage().getHeight() + 46 + graphicContainer.worldContainer.ic.getImage().getHeight(),
                    0, 0, graphicContainer.worldContainer.ic.getImage().getWidth(), graphicContainer.worldContainer.ic.getImage().getHeight(),
                    null);
        }

        paintCurrentLayer(graphicContainer);

        if (graphicContainer.worldContainer.showInv) {
            graphics.drawImage(ImagesContainer.getInstance().saveExit,
                    graphicContainer.getWidth() - ImagesContainer.getInstance().saveExit.getWidth() - 24, graphicContainer.getHeight() - ImagesContainer.getInstance().saveExit.getHeight() - 24, graphicContainer.getWidth() - 24, graphicContainer.getHeight() - 24,
                    0, 0, ImagesContainer.getInstance().saveExit.getWidth(), ImagesContainer.getInstance().saveExit.getHeight(),
                    null);
        }

        if (graphicContainer.worldContainer.moveItem != Items.EMPTY) {
            int width = ImagesContainer.getInstance().itemImgs.get(graphicContainer.worldContainer.moveItem).getWidth();
            int height = ImagesContainer.getInstance().itemImgs.get(graphicContainer.worldContainer.moveItem).getHeight();
            graphics.drawImage(ImagesContainer.getInstance().itemImgs.get(graphicContainer.worldContainer.moveItem),
                    graphicContainer.mousePos.getX() + 12 + ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * width * 2) / 2), graphicContainer.mousePos.getY() + 12 + ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * height * 2) / 2), graphicContainer.mousePos.getX() + 36 - ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * width * 2) / 2), graphicContainer.mousePos.getY() + 36 - ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * height * 2) / 2),
                    0, 0, width, height,
                    null);
            if (graphicContainer.worldContainer.moveNum > 1) {
                graphics.setFont(graphicContainer.font);
                graphics.setColor(Color.WHITE);
                graphics.drawString(graphicContainer.worldContainer.moveNum + " ", graphicContainer.mousePos.getX() + 13, graphicContainer.mousePos.getY() + 38);
            }
        }


        for (int i = 0; i < graphicContainer.worldContainer.entities.size(); i++) {
            if (UIENTITIES.get(graphicContainer.worldContainer.entities.get(i).getEntityType().getName()) != null && graphicContainer.worldContainer.entities.get(i).getRect() != null && graphicContainer.worldContainer.entities.get(i).getRect().contains(new Point(graphicContainer.mousePos2.getX(), graphicContainer.mousePos2.getY()))) {
                graphics.setFont(graphicContainer.mobFont);
                graphics.setColor(Color.WHITE);
                graphics.drawString(UIENTITIES.get(graphicContainer.worldContainer.entities.get(i).getEntityType()) + " (" + graphicContainer.worldContainer.entities.get(i).getHealthPoints() + "/" + graphicContainer.worldContainer.entities.get(i).getTotalHealthPoints() + ")", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                break;
            }
        }

        paintInventoryItems(graphicContainer);

        graphics.setFont(graphicContainer.mobFont);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Health: " + graphicContainer.worldContainer.player.healthPoints + "/" + graphicContainer.worldContainer.player.totalHealthPoints, graphicContainer.getWidth() - 125, 20);
        graphics.drawString("armor: " + graphicContainer.worldContainer.player.sumArmor(), graphicContainer.getWidth() - 125, 40);
        if (DebugContext.STATS) {
            graphics.drawString("(" + ((int) graphicContainer.worldContainer.player.intX / WorldContainer.BLOCK_SIZE) + ", " + ((int) graphicContainer.worldContainer.player.intY / WorldContainer.BLOCK_SIZE) + ")", graphicContainer.getWidth() - 125, 60);
            if (graphicContainer.worldContainer.player.intY >= 0 && graphicContainer.worldContainer.player.intY < graphicContainer.HEIGHT * WorldContainer.BLOCK_SIZE) {
                int u = -graphicContainer.ou * WorldContainer.CHUNK_BLOCKS;
                int v = -graphicContainer.ov * WorldContainer.CHUNK_BLOCKS;
                graphics.drawString(graphicContainer.worldContainer.checkBiome((int) graphicContainer.worldContainer.player.intX / WorldContainer.BLOCK_SIZE + u, (int) graphicContainer.worldContainer.player.intY / WorldContainer.BLOCK_SIZE + v, u, v).getTitle() + " " + graphicContainer.worldContainer.lights[(int) graphicContainer.worldContainer.player.intY / 16 + v][(int) graphicContainer.worldContainer.player.intX / 16 + u], graphicContainer.getWidth() - 125, 80);
            }
        }
        if (graphicContainer.worldContainer.showInv) {
            for (int ux = 0; ux < 2; ux++) {
                for (int uy = 0; uy < 2; uy++) {
                    if (graphicContainer.mousePos.isInBetween(graphicContainer.worldContainer.inventory.getImage().getWidth() + ux * 40 + 75, graphicContainer.worldContainer.inventory.getImage().getWidth() + ux * 40 + 115, uy * 40 + 52, uy * 40 + 92) && graphicContainer.worldContainer.cic.getItems()[uy * 2 + ux] != Items.EMPTY) {
                        graphics.setFont(graphicContainer.mobFont);
                        graphics.setColor(Color.WHITE);
                        if (graphicContainer.worldContainer.cic.getItems()[uy * 2 + ux].getDurability() != null) {
                            graphics.drawString(graphicContainer.worldContainer.cic.getItems()[uy * 2 + ux].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.cic.getDurs()[uy * 2 + ux] / graphicContainer.worldContainer.cic.getItems()[uy * 2 + ux].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                        } else {
                            graphics.drawString(graphicContainer.worldContainer.cic.getItems()[uy * 2 + ux].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                        }
                    }
                }
            }
            if (graphicContainer.mousePos.isInBetween(graphicContainer.worldContainer.inventory.getImage().getWidth() + 3 * 40 + 75, graphicContainer.worldContainer.inventory.getImage().getWidth() + 3 * 40 + 115, 20 + 52, 20 + 92) && graphicContainer.worldContainer.cic.getItems()[4] != Items.EMPTY) {
                graphics.setFont(graphicContainer.mobFont);
                graphics.setColor(Color.WHITE);
                if (graphicContainer.worldContainer.cic.getItems()[4].getDurability() != null) {
                    graphics.drawString(graphicContainer.worldContainer.cic.getItems()[4].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.cic.getDurs()[4] / graphicContainer.worldContainer.cic.getItems()[4].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                } else {
                    graphics.drawString(graphicContainer.worldContainer.cic.getItems()[4].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                }
            }
            for (int uy = 0; uy < 4; uy++) {
                if (graphicContainer.mousePos.isInBetween(graphicContainer.worldContainer.inventory.getImage().getWidth() + 6, graphicContainer.worldContainer.inventory.getImage().getWidth() + 6 + graphicContainer.armor.getImage().getWidth(), 6 + uy * 46, 6 + uy * 46 + 46) && graphicContainer.armor.getItems()[uy] != Items.EMPTY) {
                    graphics.setFont(graphicContainer.mobFont);
                    graphics.setColor(Color.WHITE);
                    if (graphicContainer.armor.getItems()[uy].getDurability() != null) {
                        graphics.drawString(graphicContainer.armor.getItems()[uy].getUiName() + " (" + (int) ((double) graphicContainer.armor.getDurs()[uy] / graphicContainer.armor.getItems()[uy].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    } else {
                        graphics.drawString(graphicContainer.armor.getItems()[uy].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    }
                }
            }
        }
        if (graphicContainer.worldContainer.ic != null) {
            if (graphicContainer.worldContainer.ic.getType() == ItemType.WORKBENCH) {
                // paint WORKBENCH
                for (int ux = 0; ux < 3; ux++) {
                    for (int uy = 0; uy < 3; uy++) {
                        if (graphicContainer.mousePos.isInBetween(ux * 40 + 6, ux * 40 + 46, uy * 40 + graphicContainer.worldContainer.inventory.getImage().getHeight() + 46, uy * 40 + graphicContainer.worldContainer.inventory.getImage().getHeight() + 86) &&
                                graphicContainer.worldContainer.ic.getItems()[uy * 3 + ux] != Items.EMPTY) {
                            graphics.setFont(graphicContainer.mobFont);
                            graphics.setColor(Color.WHITE);
                            if (graphicContainer.worldContainer.ic.getItems()[uy * 3 + ux].getDurability() != null) {
                                graphics.drawString(graphicContainer.worldContainer.ic.getItems()[uy * 3 + ux].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.ic.getDurs()[uy * 3 + ux] / graphicContainer.worldContainer.ic.getItems()[uy * 3 + ux].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                            } else {
                                graphics.drawString(graphicContainer.worldContainer.ic.getItems()[uy * 3 + ux].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                            }
                        }
                    }
                }
                if (graphicContainer.mousePos.isInBetween(4 * 40 + 6, 4 * 40 + 46, 1 * 40 + graphicContainer.worldContainer.inventory.getImage().getHeight() + 46, 1 * 40 + graphicContainer.worldContainer.inventory.getImage().getHeight() + 86) &&
                        graphicContainer.worldContainer.ic.getItems()[9] != Items.EMPTY) {
                    graphics.setFont(graphicContainer.mobFont);
                    graphics.setColor(Color.WHITE);
                    if (graphicContainer.worldContainer.ic.getItems()[9].getDurability() != null) {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[9].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.ic.getDurs()[9] / graphicContainer.worldContainer.ic.getItems()[9].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    } else {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[9].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    }
                }
            } else if (graphicContainer.worldContainer.ic.getType() == ItemType.WOODEN_CHEST || graphicContainer.worldContainer.ic.getType() == ItemType.STONE_CHEST ||
                    graphicContainer.worldContainer.ic.getType() == ItemType.COPPER_CHEST || graphicContainer.worldContainer.ic.getType() == ItemType.IRON_CHEST ||
                    graphicContainer.worldContainer.ic.getType() == ItemType.SILVER_CHEST || graphicContainer.worldContainer.ic.getType() == ItemType.GOLD_CHEST ||
                    graphicContainer.worldContainer.ic.getType() == ItemType.ZINC_CHEST || graphicContainer.worldContainer.ic.getType() == ItemType.RHYMESTONE_CHEST ||
                    graphicContainer.worldContainer.ic.getType() == ItemType.OBDURITE_CHEST) {
                // paint CHEST graphicContainer.armor
                for (int ux = 0; ux < InventoryService.getInstance().CX; ux++) {
                    for (int uy = 0; uy < InventoryService.getInstance().CY; uy++) {
                        if (graphicContainer.mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + graphicContainer.worldContainer.inventory.getImage().getHeight() + 46, uy * 46 + graphicContainer.worldContainer.inventory.getImage().getHeight() + 86) &&
                                graphicContainer.worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux] != Items.EMPTY) {
                            graphics.setFont(graphicContainer.mobFont);
                            graphics.setColor(Color.WHITE);
                            if (graphicContainer.worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux].getDurability() != null) {
                                graphics.drawString(graphicContainer.worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.ic.getDurs()[uy * InventoryService.getInstance().CX + ux] / graphicContainer.worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                            } else {
                                graphics.drawString(graphicContainer.worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                            }
                        }
                    }
                }
            } else if (graphicContainer.worldContainer.ic.getType() == ItemType.FURNACE) {
                // paint FURNACE
                if (graphicContainer.mousePos.isInBetween(6, 46, graphicContainer.worldContainer.inventory.getImage().getHeight() + 46, graphicContainer.worldContainer.inventory.getImage().getHeight() + 86) &&
                        graphicContainer.worldContainer.ic.getItems()[0] != Items.EMPTY) {
                    graphics.setFont(graphicContainer.mobFont);
                    graphics.setColor(Color.WHITE);
                    if (graphicContainer.worldContainer.ic.getItems()[0].getDurability() != null) {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[0].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.ic.getDurs()[0] / graphicContainer.worldContainer.ic.getItems()[0].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    } else {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[0].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    }
                }
                if (graphicContainer.mousePos.isInBetween(6, 46, graphicContainer.worldContainer.inventory.getImage().getHeight() + 102, graphicContainer.worldContainer.inventory.getImage().getHeight() + 142) &&
                        graphicContainer.worldContainer.ic.getItems()[1] != Items.EMPTY) {
                    graphics.setFont(graphicContainer.mobFont);
                    graphics.setColor(Color.WHITE);
                    if (graphicContainer.worldContainer.ic.getItems()[1].getDurability() != null) {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[1].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.ic.getDurs()[1] / graphicContainer.worldContainer.ic.getItems()[1].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    } else {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[1].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    }
                }
                if (graphicContainer.mousePos.isInBetween(6, 46, graphicContainer.worldContainer.inventory.getImage().getHeight() + 142, graphicContainer.worldContainer.inventory.getImage().getHeight() + 182) &&
                        graphicContainer.worldContainer.ic.getItems()[2] != Items.EMPTY) {
                    graphics.setFont(graphicContainer.mobFont);
                    graphics.setColor(Color.WHITE);
                    if (graphicContainer.worldContainer.ic.getItems()[2].getDurability() != null) {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[2].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.ic.getDurs()[2] / graphicContainer.worldContainer.ic.getItems()[2].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    } else {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[2].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    }
                }
                if (graphicContainer.mousePos.isInBetween(62, 102, graphicContainer.worldContainer.inventory.getImage().getHeight() + 46, graphicContainer.worldContainer.inventory.getImage().getHeight() + 86) &&
                        graphicContainer.worldContainer.ic.getItems()[3] != Items.EMPTY) {
                    graphics.setFont(graphicContainer.mobFont);
                    graphics.setColor(Color.WHITE);
                    if (graphicContainer.worldContainer.ic.getItems()[3].getDurability() != null) {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[3].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.ic.getDurs()[3] / graphicContainer.worldContainer.ic.getItems()[3].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    } else {
                        graphics.drawString(graphicContainer.worldContainer.ic.getItems()[3].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    }
                }
            }
        }
    }

    private void paintPlayer(TerrariaClone graphicContainer) {
        graphics.drawImage(graphicContainer.worldContainer.player.image,
                graphicContainer.getWidth() / 2 - Player.WIDTH / 2, graphicContainer.getHeight() / 2 - Player.HEIGHT / 2,
                graphicContainer.getWidth() / 2 + Player.WIDTH / 2, graphicContainer.getHeight() / 2 + Player.HEIGHT / 2,
                0, 0, graphicContainer.worldContainer.player.image.getWidth(), graphicContainer.worldContainer.player.image.getHeight(),
                null);
    }

    private void paintInventoryItems(TerrariaClone graphicContainer) {
        int ymax;
        if (graphicContainer.worldContainer.showInv) {
            ymax = 4;
        } else {
            ymax = 1;
        }
        for (int ux = 0; ux < 10; ux++) {
            for (int uy = 0; uy < ymax; uy++) {
                if (graphicContainer.mousePos.isInBetweenInclusive(ux * 46 + 6, ux * 46 + 46, uy * 46 + 6, uy * 46 + 46) && graphicContainer.worldContainer.inventory.getItems()[uy * 10 + ux] != Items.EMPTY) {
                    graphics.setFont(graphicContainer.mobFont);
                    graphics.setColor(Color.WHITE);
                    if (graphicContainer.worldContainer.inventory.getItems()[uy * 10 + ux].getDurability() != null) {
                        graphics.drawString(graphicContainer.worldContainer.inventory.getItems()[uy * 10 + ux].getUiName() + " (" + (int) ((double) graphicContainer.worldContainer.inventory.getDurs()[uy * 10 + ux] / graphicContainer.worldContainer.inventory.getItems()[uy * 10 + ux].getDurability() * 100) + "%)", graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    } else {
                        graphics.drawString(graphicContainer.worldContainer.inventory.getItems()[uy * 10 + ux].getUiName(), graphicContainer.mousePos.getX(), graphicContainer.mousePos.getY());
                    }
                }
            }
        }
    }

    private void paintCurrentLayer(TerrariaClone graphicContainer) {
        BufferedImage layerImg = null;
        if (graphicContainer.worldContainer.layer == 0) {
            layerImg = ResourcesLoader.loadImage("interface/layersB.png");
        } else if (graphicContainer.worldContainer.layer == 1) {
            layerImg = ResourcesLoader.loadImage("interface/layersN.png");
        } else if (graphicContainer.worldContainer.layer == 2) {
            layerImg = ResourcesLoader.loadImage("interface/layersF.png");
        }
        graphics.drawImage(layerImg,
                graphicContainer.worldContainer.inventory.getImage().getWidth() + 58, 6, graphicContainer.worldContainer.inventory.getImage().getWidth() + 58 + layerImg.getWidth(), 6 + layerImg.getHeight(),
                0, 0, layerImg.getWidth(), layerImg.getHeight(),
                null);
    }

    private void paintInventory(TerrariaClone graphicContainer) {
        if (graphicContainer.worldContainer.showInv) {
            graphics.drawImage(graphicContainer.worldContainer.inventory.getImage(),
                    0, 0, graphicContainer.worldContainer.inventory.getImage().getWidth(), (int) graphicContainer.worldContainer.inventory.getImage().getHeight(),
                    0, 0, graphicContainer.worldContainer.inventory.getImage().getWidth(), (int) graphicContainer.worldContainer.inventory.getImage().getHeight(),
                    null);
            graphics.drawImage(graphicContainer.armor.getImage(),
                    graphicContainer.worldContainer.inventory.getImage().getWidth() + 6, 6, graphicContainer.worldContainer.inventory.getImage().getWidth() + 6 + graphicContainer.armor.getImage().getWidth(), 6 + graphicContainer.armor.getImage().getHeight(),
                    0, 0, graphicContainer.armor.getImage().getWidth(), graphicContainer.armor.getImage().getHeight(),
                    null);
            graphics.drawImage(graphicContainer.worldContainer.cic.getImage(),
                    graphicContainer.worldContainer.inventory.getImage().getWidth() + 75, 52, graphicContainer.worldContainer.inventory.getImage().getWidth() + 75 + graphicContainer.worldContainer.cic.getImage().getWidth(), 52 + graphicContainer.worldContainer.cic.getImage().getHeight(),
                    0, 0, graphicContainer.worldContainer.cic.getImage().getWidth(), graphicContainer.worldContainer.cic.getImage().getHeight(),
                    null);
        } else {
            graphics.drawImage(graphicContainer.worldContainer.inventory.getImage(),
                    0, 0, graphicContainer.worldContainer.inventory.getImage().getWidth(), (int) graphicContainer.worldContainer.inventory.getImage().getHeight() / 4,
                    0, 0, graphicContainer.worldContainer.inventory.getImage().getWidth(), (int) graphicContainer.worldContainer.inventory.getImage().getHeight() / 4,
                    null);
        }
    }

    private void paintTool(TerrariaClone graphicContainer) {
        if (graphicContainer.worldContainer.player.showTool && graphicContainer.worldContainer.player.tool != null) {
            if (graphicContainer.worldContainer.player.imgState == ImageState.STILL_RIGHT || graphicContainer.worldContainer.player.imgState.isWalkRight()) {
                graphics.translate(graphicContainer.getWidth() / 2 + 6, graphicContainer.getHeight() / 2);
                graphics.rotate(graphicContainer.worldContainer.player.toolAngle);

                graphics.drawImage(graphicContainer.worldContainer.player.tool,
                        0, -graphicContainer.worldContainer.player.tool.getHeight() * 2, graphicContainer.worldContainer.player.tool.getWidth() * 2, 0,
                        0, 0, graphicContainer.worldContainer.player.tool.getWidth(), graphicContainer.worldContainer.player.tool.getHeight(),
                        null);

                graphics.rotate(-graphicContainer.worldContainer.player.toolAngle);
                graphics.translate(-graphicContainer.getWidth() / 2 - 6, -graphicContainer.getHeight() / 2);
            }
            if (graphicContainer.worldContainer.player.imgState == ImageState.STILL_LEFT || graphicContainer.worldContainer.player.imgState.isWalkLeft()) {
                graphics.translate(graphicContainer.getWidth() / 2 - 6, graphicContainer.getHeight() / 2);
                graphics.rotate((Math.PI * 1.5) - graphicContainer.worldContainer.player.toolAngle);

                graphics.drawImage(graphicContainer.worldContainer.player.tool,
                        0, -graphicContainer.worldContainer.player.tool.getHeight() * 2, graphicContainer.worldContainer.player.tool.getWidth() * 2, 0,
                        0, 0, graphicContainer.worldContainer.player.tool.getWidth(), graphicContainer.worldContainer.player.tool.getHeight(),
                        null);

                graphics.rotate(-((Math.PI * 1.5) - graphicContainer.worldContainer.player.toolAngle));
                graphics.translate(-graphicContainer.getWidth() / 2 + 6, -graphicContainer.getHeight() / 2);
            }
        }
    }

    private void paintAllEntities(TerrariaClone graphicContainer) {
        for (int i = 0; i < graphicContainer.worldContainer.entities.size(); i++) {
            Entity entity = graphicContainer.worldContainer.entities.get(i);
            graphics.drawImage(entity.getImage(),
                    entity.getIntX() - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2, entity.getIntY() - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2, entity.getIntX() - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2 + entity.getWidth(), entity.getIntY() - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2 + entity.getHeight(),
                    0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                    null);
            graphics.drawImage(entity.getImage(),
                    entity.getIntX() - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2 - graphicContainer.WIDTH * WorldContainer.BLOCK_SIZE, entity.getIntY() - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2, entity.getIntX() - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2 + entity.getWidth() - graphicContainer.WIDTH * WorldContainer.BLOCK_SIZE, entity.getIntY() - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2 + entity.getHeight(),
                    0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                    null);
            graphics.drawImage(entity.getImage(),
                    entity.getIntX() - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2 + graphicContainer.WIDTH * WorldContainer.BLOCK_SIZE, entity.getIntY() - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2, entity.getIntX() - graphicContainer.worldContainer.player.intX + graphicContainer.getWidth() / 2 - Player.WIDTH / 2 + entity.getWidth() + graphicContainer.WIDTH * WorldContainer.BLOCK_SIZE, entity.getIntY() - graphicContainer.worldContainer.player.intY + graphicContainer.getHeight() / 2 - Player.HEIGHT / 2 + entity.getHeight(),
                    0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                    null);
        }
    }

    private void paintSunMoonClouds(TerrariaClone graphicContainer) {
        if (graphicContainer.worldContainer.player.y / WorldContainer.BLOCK_SIZE < graphicContainer.HEIGHT * 0.5) {
            graphics.translate(graphicContainer.getWidth() / 2, graphicContainer.getHeight() * 0.85);
            graphics.rotate(TimeService.getInstance().getSunsPosition());

            graphics.drawImage(sun,
                    (int) (-graphicContainer.getWidth() * 0.65), 0, (int) (-graphicContainer.getWidth() * 0.65 + sun.getWidth() * 2), sun.getHeight() * 2,
                    0, 0, sun.getWidth(), sun.getHeight(),
                    null);

            graphics.rotate(Math.PI);

            graphics.drawImage(moon,
                    (int) (-graphicContainer.getWidth() * 0.65), 0, (int) (-graphicContainer.getWidth() * 0.65 + moon.getWidth() * 2), moon.getHeight() * 2,
                    0, 0, moon.getWidth(), moon.getHeight(),
                    null);

            graphics.rotate(TimeService.getInstance().getMoonsPosition());
            graphics.translate(-graphicContainer.getWidth() / 2, -graphicContainer.getHeight() * 0.85);

            for (int i = 0; i < graphicContainer.worldContainer.cloudsAggregate.getClouds().size(); i++) {
                BufferedImage cloudImage = ImagesContainer.getInstance().cloudsImage;
                graphics.drawImage(cloudImage,
                        (int) graphicContainer.worldContainer.cloudsAggregate.getClouds().get(i).getX(), (int) graphicContainer.worldContainer.cloudsAggregate.getClouds().get(i).getY(), (int) graphicContainer.worldContainer.cloudsAggregate.getClouds().get(i).getX() + cloudImage.getWidth() * 2, (int) graphicContainer.worldContainer.cloudsAggregate.getClouds().get(i).getY() + cloudImage.getHeight() * 2,
                        0, 0, cloudImage.getWidth(), cloudImage.getHeight(),
                        null);
            }
        }
    }
}
