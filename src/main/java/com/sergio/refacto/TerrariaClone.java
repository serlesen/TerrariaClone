package com.sergio.refacto; 

/**
 * TerrariaClone (working title) [Pre-alpha 1.3]
 * <p>
 * developed by Radon Rosborough
 * <p>
 * Project mission: To program a 2D sandbox game similar to, but with many more
 * features than, Terraria.
 **/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.dto.Biome;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.Directions;
import com.sergio.refacto.dto.EntityType;
import com.sergio.refacto.dto.FileInfo;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.ItemCollection;
import com.sergio.refacto.dto.ItemPositionInScreen;
import com.sergio.refacto.dto.ItemType;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.dto.KeyPressed;
import com.sergio.refacto.dto.MousePressed;
import com.sergio.refacto.dto.State;
import com.sergio.refacto.init.BackgroundImagesInitializer;
import com.sergio.refacto.init.BlockImagesInitializer;
import com.sergio.refacto.init.DDelayInitializer;
import com.sergio.refacto.init.DurabilityInitializer;
import com.sergio.refacto.init.FuelsInitializer;
import com.sergio.refacto.init.GrassDirtInitializer;
import com.sergio.refacto.init.ItemImagesInitializer;
import com.sergio.refacto.init.LightLevelsInitializer;
import com.sergio.refacto.init.OutlineImagesInitializer;
import com.sergio.refacto.init.SkyColorsInitializer;
import com.sergio.refacto.init.TorchesLInitializer;
import com.sergio.refacto.init.TorchesRInitializer;
import com.sergio.refacto.init.UIEntitiesInitializer;
import com.sergio.refacto.init.WirePInitializer;
import com.sergio.refacto.items.Chunk;
import com.sergio.refacto.items.Cloud;
import com.sergio.refacto.items.Mouse;
import com.sergio.refacto.items.TextField;
import com.sergio.refacto.items.WorldContainer;
import com.sergio.refacto.services.OutlinesService;
import com.sergio.refacto.services.PaintService;
import com.sergio.refacto.services.WorldService;
import com.sergio.refacto.tools.MathTool;
import com.sergio.refacto.tools.RandomTool;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static com.sergio.refacto.dto.Constants.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC)
public class TerrariaClone extends JApplet implements ChangeListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private WorldService worldService;
    private PaintService paintService;

    /** Size (in block units) of the in-memory game. */
    public static final int SIZE = WorldContainer.CHUNK_BLOCKS * 2;

    public static final int LAYER_SIZE = 3;

    static GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    BufferedImage screen;
    Color bg;

    int[][] cl = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    javax.swing.Timer inGameTimer, menuTimer;
    List<FileInfo> filesInfo;
    String currentWorld;
    TextField newWorldName;

    WorldContainer worldContainer;

    List<Integer> pqx, pqy, zqx, zqy, pzqx, pzqy;
    Boolean[][] zqd, pqd, pzqd;
    Byte[][] zqn;
    Byte[][][] pzqn;
    Boolean[][][] arbprd;
    List<Integer> updatex, updatey, updatet, updatel;
    Boolean[][] wcnct;
    public static ItemCollection armor;

    Chunk[][] temporarySaveFile;
    Chunk[][] chunkMatrix;

    int iclayer;

    State state = State.LOADING_GRAPHICS;

    int ou, ov, uNew, vNew;
    double p, q;
    Items miningTool;

    short moveDur, moveDurTemp;

    Point tp1, tp2, tp3, tp4, tp5;

    public Mouse mousePos, mousePos2;

    Font font = new Font("Chalkboard", Font.BOLD, 12);
    Font mobFont = new Font("Chalkboard", Font.BOLD, 16);
    Font loadFont = new Font("Courier", Font.PLAIN, 12);
    Font worldFont = new Font("Andale Mono", Font.BOLD, 16);
    Color CYANISH = new Color(75, 163, 243);

    BufferedImage title_screen, select_world, new_world, save_exit;
    BufferedImage[] cloudsImages = { ResourcesLoader.loadImage("environment/cloud1.png") };
    BufferedImage wcnct_px = ResourcesLoader.loadImage("misc/wcnct.png");

    javax.swing.Timer createWorldTimer;
    KeyPressed keyPressed;
    MousePressed mousePressed;

    boolean checkBlocks = true;
    boolean menuPressed = false;

    private static final int IMAGESIZE = 8;
    private static final int BRIGHTEST = 21;

    int sunlightlevel = 19;

    BufferedImage tool;

    static Map<Backgrounds, BufferedImage> backgroundImgs;
    public static Map<Items, BufferedImage> itemImgs;
    static Map<Short, Map<Integer, Integer>> DURABILITY;
    public static Map<EntityType, String> UIENTITIES;
    static Map<Integer, Color> SKYCOLORS;
    static Map<Integer, BufferedImage> LIGHTLEVELS;
    static Map<String, BufferedImage> blockImgs;
    static Map<String, BufferedImage> outlineImgs;
    static Map<Blocks, Blocks> GRASSDIRT;
    static Map<Items, Double> FUELS;
    static Map<Integer, Blocks> WIREP;
    static Map<Blocks, Blocks> TORCHESL;
    static Map<Blocks, Blocks> TORCHESR;
    static Map<Blocks, Integer> DDELAY;

    List<Items> FRI1, FRI2;
    List<Short> FRN1, FRN2;

    Graphics2D wg2, fwg2, ug2, pg2;

    public static void main(String[] args) {
        JFrame f = new JFrame("TerrariaClone: Infinite worlds!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        JApplet ap = new TerrariaClone();
        ap.setFocusable(true);
        f.add("Center", ap);
        f.pack();

        ap.init();
    }

    public void init() {
        try {
            paintService = new PaintService();
            worldService = new WorldService();

            setLayout(new BorderLayout());
            bg = Color.BLACK;

            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);
            addMouseWheelListener(this);

            screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            mousePos = new Mouse();
            mousePos2 = new Mouse();

            worldContainer = new WorldContainer();

            title_screen = ResourcesLoader.loadImage("interface/title_screen.png");
            select_world = ResourcesLoader.loadImage("interface/select_world.png");
            new_world = ResourcesLoader.loadImage("interface/new_world.png");
            save_exit = ResourcesLoader.loadImage("interface/save_exit.png");

            state = State.LOADING_GRAPHICS;

            repaint();

            backgroundImgs = BackgroundImagesInitializer.init();

            itemImgs = ItemImagesInitializer.init();

            blockImgs = BlockImagesInitializer.init();

            outlineImgs = OutlineImagesInitializer.init();

            DURABILITY = DurabilityInitializer.init();

            UIENTITIES = UIEntitiesInitializer.init();

            SKYCOLORS = SkyColorsInitializer.init();

            LIGHTLEVELS = LightLevelsInitializer.init();

            GRASSDIRT = GrassDirtInitializer.init();

            FUELS = FuelsInitializer.init();

            WIREP = WirePInitializer.init();

            TORCHESL = TorchesLInitializer.init();

            TORCHESR = TorchesRInitializer.init();

            DDELAY = DDelayInitializer.init();

            FRI1 = new ArrayList<>(0);
            FRN1 = new ArrayList<>(0);
            FRI2 = new ArrayList<>(0);
            FRN2 = new ArrayList<>(0);

            FRI1.add(Items.COPPER_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.COPPER_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.IRON_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.IRON_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.SILVER_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.SILVER_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.GOLD_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.GOLD_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.ZINC_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.ZINC_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.RHYMESTONE_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.RHYMESTONE_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.OBDURITE_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.OBDURITE_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.ALUMINUM_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.ALUMINUM_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.LEAD_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.LEAD_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.URANIUM_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.URANIUM_BAR);
            FRN2.add((short) 1);
            FRI1.add(Items.ZYTHIUM_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.ZYTHIUM_BAR);
            FRN2.add((short) 1);
            FRI1.add(Items.SILICON_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.SILICON_BAR);
            FRN2.add((short) 1);
            FRI1.add(Items.IRRADIUM_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.IRRADIUM_INGOT);
            FRN2.add((short) 1);
            FRI1.add(Items.NULLSTONE);
            FRN1.add((short) 4);
            FRI2.add(Items.NULLSTONE_BAR);
            FRN2.add((short) 1);
            FRI1.add(Items.MELTSTONE);
            FRN1.add((short) 4);
            FRI2.add(Items.MELTSTONE_BAR);
            FRN2.add((short) 1);
            FRI1.add(Items.SKYSTONE);
            FRN1.add((short) 4);
            FRI2.add(Items.SKYSTONE_BAR);
            FRN2.add((short) 1);
            FRI1.add(Items.MAGNETITE_ORE);
            FRN1.add((short) 4);
            FRI2.add(Items.MAGNETITE_INGOT);
            FRN2.add((short) 1);
            for (int i = 8; i > 2; i--) {
                FRI1.add(Items.SAND);
                FRN1.add((short) i);
                FRI2.add(Items.GLASS);
                FRN2.add((short) i);
                FRI1.add(Items.STONE);
                FRN1.add((short) i);
                FRI2.add(Items.CHISELED_STONE);
                FRN2.add((short) i);
                FRI1.add(Items.COBBLESTONE);
                FRN1.add((short) i);
                FRI2.add(Items.CHISELED_COBBLESTONE);
                FRN2.add((short) i);
                FRI1.add(Items.CLAY);
                FRN1.add((short) i);
                FRI2.add(Items.CLAY_BRICKS);
                FRN2.add((short) i);
                FRI1.add(Items.WOOD);
                FRN1.add((short) i);
                FRI2.add(Items.CHARCOAL);
                FRN2.add((short) i);
            }
            for (int i = 97; i < 103; i++) {
                FRI1.add(Items.findByIndex(i));
                FRN1.add((short) 1);
                FRI2.add(Items.VARNISH);
                FRN2.add((short) 8);
            }

            bg = CYANISH;
            state = State.TITLE_SCREEN;

            repaint();

            menuTimer = new javax.swing.Timer(20, null);
            menuTimer.addActionListener(ae -> {
                try {
                    if (mousePressed == MousePressed.LEFT_MOUSE) {
                        handleMenuBehavior();
                    }
                } catch (Exception e) {
                    log.error("Error while handling menu behavior", e);
                }
            });

            inGameTimer = new Timer(20, null);
            inGameTimer.addActionListener(ae -> {
                try {
                    if (worldContainer.ready) {
                        handleInGameBehavior();
                    }
                } catch (Exception e) {
                    log.error("Error while handling inGame behavior", e);
                }
            });

            menuTimer.start();
        } catch (Exception e) {
            log.error("Error initializing the application", e);
        }
    }

    private void handleInGameBehavior() {
        worldContainer.ready = false;
        uNew = (int) ((worldContainer.player.x - getWidth() / 2 + Player.WIDTH) / (double) WorldContainer.CHUNK_SIZE);
        vNew = (int) ((worldContainer.player.y - getHeight() / 2 + Player.HEIGHT) / (double) WorldContainer.CHUNK_SIZE);
        if (ou != uNew || ov != vNew) {
            ou = uNew;
            ov = vNew;

            loadChunks();
            loadWorldContainer();
        }
        int u = -ou * WorldContainer.CHUNK_BLOCKS;
        int v = -ov * WorldContainer.CHUNK_BLOCKS;
        for (int twy = 0; twy < 2; twy++) {
            for (int twx = 0; twx < 2; twx++) {
                worldContainer.kworlds[twy][twx] = false;
            }
        }
        boolean somevar = false;
        for (int twy = 0; twy < 2; twy++) {
            for (int twx = 0; twx < 2; twx++) {
                int twxc = twx + ou;
                int twyc = twy + ov;
                if (worldContainer.player.isPlayerIntoChunk(twxc, twyc, getWidth(), getHeight())) {
                    worldContainer.kworlds[twy][twx] = true;
                    if (worldContainer.worlds[twy][twx] == null) {
                        worldContainer.worlds[twy][twx] = config.createCompatibleImage(WorldContainer.CHUNK_SIZE, WorldContainer.CHUNK_SIZE, Transparency.TRANSLUCENT);
                        worldContainer.fworlds[twy][twx] = config.createCompatibleImage(WorldContainer.CHUNK_SIZE, WorldContainer.CHUNK_SIZE, Transparency.TRANSLUCENT);
                        log.info("Created image at " + twx + " " + twy);
                    }
                    if (worldContainer.worlds[twy][twx] != null) {
                        wg2 = worldContainer.worlds[twy][twx].createGraphics();
                        fwg2 = worldContainer.fworlds[twy][twx].createGraphics();
                        for (int tly = Math.max(twy * WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intY - getHeight() / 2 + Player.HEIGHT / 2 + v * WorldContainer.BLOCK_SIZE) - 64); tly < Math.min(twy * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intY + getHeight() / 2 - Player.HEIGHT / 2 + v * WorldContainer.BLOCK_SIZE) + 128); tly += WorldContainer.BLOCK_SIZE) {
                            for (int tlx = Math.max(twx * WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intX - getWidth() / 2 + Player.WIDTH / 2 + u * WorldContainer.BLOCK_SIZE) - 64); tlx < Math.min(twx * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intX + getWidth() / 2 - Player.WIDTH / 2 + u * WorldContainer.BLOCK_SIZE) + 112); tlx += WorldContainer.BLOCK_SIZE) {
                                int tx = (int) (tlx / WorldContainer.BLOCK_SIZE);
                                int ty = (int) (tly / WorldContainer.BLOCK_SIZE);
                                if (tx >= 0 && tx < SIZE && ty >= 0 && ty < SIZE) {
                                    if (!worldContainer.drawn[ty][tx]) {
                                        somevar = true;
                                        worldContainer.blocksTextureIntensity[ty][tx] = (byte) RandomTool.nextInt(8);
                                        for (int y = 0; y < WorldContainer.BLOCK_SIZE; y++) {
                                            for (int x = 0; x < WorldContainer.BLOCK_SIZE; x++) {
                                                try {
                                                    worldContainer.worlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twxc * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twyc * WorldContainer.CHUNK_SIZE + y, 9539985);
                                                    worldContainer.fworlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twxc * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twyc * WorldContainer.CHUNK_SIZE + y, 9539985);
                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                    //log.warn("Out of Bounds for worlds at " + twy + "/" + twx, e);
                                                }
                                            }
                                        }
                                        if (worldContainer.blocksBackgrounds[ty][tx] != Backgrounds.EMPTY) {
                                            wg2.drawImage(backgroundImgs.get(worldContainer.blocksBackgrounds[ty][tx]),
                                                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                    null);
                                        }
                                        for (int l = 0; l < LAYER_SIZE; l++) {
                                            if (worldContainer.blocks[l][ty][tx] != Blocks.AIR) {
                                                if (l == 2) {
                                                    fwg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blocksDirections[l][ty][tx], worldContainer.blocksDirectionsIntensity[ty][tx], worldContainer.blocksTextureIntensity[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                } else {
                                                    wg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blocksDirections[l][ty][tx], worldContainer.blocksDirectionsIntensity[ty][tx], worldContainer.blocksTextureIntensity[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                }
                                            }
                                            if (wcnct[ty][tx] && worldContainer.blocks[l][ty][tx].isZythiumWire()) {
                                                if (l == 2) {
                                                    fwg2.drawImage(wcnct_px,
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                } else {
                                                    wg2.drawImage(wcnct_px,
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                }
                                            }
                                        }
                                        if (!DebugContext.LIGHT) {
                                            fwg2.drawImage(LIGHTLEVELS.get((int) (float) worldContainer.lights[ty][tx]),
                                                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                    null);
                                        }
                                        worldContainer.drawn[ty][tx] = true;
                                        worldContainer.rdrawn[ty][tx] = true;
                                        worldContainer.ldrawn[ty][tx] = true;
                                    }
                                    if (!worldContainer.rdrawn[ty][tx]) {
                                        somevar = true;
                                        for (int y = 0; y < WorldContainer.BLOCK_SIZE; y++) {
                                            for (int x = 0; x < WorldContainer.BLOCK_SIZE; x++) {
                                                try {
                                                    worldContainer.worlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twxc * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twyc * WorldContainer.CHUNK_SIZE + y, 9539985);
                                                    worldContainer.fworlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twxc * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twyc * WorldContainer.CHUNK_SIZE + y, 9539985);
                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                    //log.warn("Out of Bounds for worlds at " + twy + "/" + twx, e);
                                                }
                                            }
                                        }
                                        if (worldContainer.blocksBackgrounds[ty][tx] != Backgrounds.EMPTY) {
                                            wg2.drawImage(backgroundImgs.get(worldContainer.blocksBackgrounds[ty][tx]),
                                                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                    null);
                                        }
                                        for (int l = 0; l < LAYER_SIZE; l++) {
                                            if (worldContainer.blocks[l][ty][tx] != Blocks.AIR) {
                                                if (l == 2) {
                                                    fwg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blocksDirections[l][ty][tx], worldContainer.blocksDirectionsIntensity[ty][tx], worldContainer.blocksTextureIntensity[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                } else {
                                                    wg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blocksDirections[l][ty][tx], worldContainer.blocksDirectionsIntensity[ty][tx], worldContainer.blocksTextureIntensity[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                }
                                            }
                                            if (wcnct[ty][tx] && worldContainer.blocks[l][ty][tx].isZythiumWire()) {
                                                if (l == 2) {
                                                    fwg2.drawImage(wcnct_px,
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                } else {
                                                    wg2.drawImage(wcnct_px,
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                }
                                            }
                                        }
                                        if (!DebugContext.LIGHT) {
                                            fwg2.drawImage(LIGHTLEVELS.get((int) (float) worldContainer.lights[ty][tx]),
                                                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                    null);
                                        }
                                        worldContainer.drawn[ty][tx] = true;
                                        worldContainer.rdrawn[ty][tx] = true;
                                        worldContainer.ldrawn[ty][tx] = true;
                                    }
                                    if (!worldContainer.ldrawn[ty][tx] && RandomTool.nextInt(10) == 0) {
                                        somevar = true;
                                        for (int y = 0; y < WorldContainer.BLOCK_SIZE; y++) {
                                            for (int x = 0; x < WorldContainer.BLOCK_SIZE; x++) {
                                                try {
                                                    worldContainer.worlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twxc * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twyc * WorldContainer.CHUNK_SIZE + y, 9539985);
                                                    worldContainer.fworlds[twy][twx].setRGB(tx * WorldContainer.BLOCK_SIZE - twxc * WorldContainer.CHUNK_SIZE + x, ty * WorldContainer.BLOCK_SIZE - twyc * WorldContainer.CHUNK_SIZE + y, 9539985);
                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                    //log.warn("Out of Bounds for worlds at " + twy + "/" + twx, e);
                                                }
                                            }
                                        }
                                        if (worldContainer.blocksBackgrounds[ty][tx] != Backgrounds.EMPTY) {
                                            wg2.drawImage(backgroundImgs.get(worldContainer.blocksBackgrounds[ty][tx]),
                                                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                    null);
                                        }
                                        for (int l = 0; l < LAYER_SIZE; l++) {
                                            if (worldContainer.blocks[l][ty][tx] != Blocks.AIR) {
                                                if (l == 2) {
                                                    fwg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blocksDirections[l][ty][tx], worldContainer.blocksDirectionsIntensity[ty][tx], worldContainer.blocksTextureIntensity[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                } else {
                                                    wg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blocksDirections[l][ty][tx], worldContainer.blocksDirectionsIntensity[ty][tx], worldContainer.blocksTextureIntensity[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                }
                                            }
                                            if (wcnct[ty][tx] && worldContainer.blocks[l][ty][tx].isZythiumWire()) {
                                                if (l == 2) {
                                                    fwg2.drawImage(wcnct_px,
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                } else {
                                                    wg2.drawImage(wcnct_px,
                                                            tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                            null);
                                                }
                                            }
                                        }
                                        if (!DebugContext.LIGHT) {
                                            fwg2.drawImage(LIGHTLEVELS.get((int) (float) worldContainer.lights[ty][tx]),
                                                    tx * WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE, tx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twx * WorldContainer.CHUNK_SIZE, ty * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE - twy * WorldContainer.CHUNK_SIZE,
                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                    null);
                                        }
                                        worldContainer.drawn[ty][tx] = true;
                                        worldContainer.rdrawn[ty][tx] = true;
                                        worldContainer.ldrawn[ty][tx] = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (somevar) {
            log.info("Drew at least one block.");

        }
        for (int twy = 0; twy < 2; twy++) {
            for (int twx = 0; twx < 2; twx++) {
                if (!worldContainer.kworlds[twy][twx] && worldContainer.worlds[twy][twx] != null) {
                    worldContainer.worlds[twy][twx] = null;
                    worldContainer.fworlds[twy][twx] = null;
                    for (int ty = twy * WorldContainer.CHUNK_BLOCKS; ty < twy * WorldContainer.CHUNK_BLOCKS + WorldContainer.CHUNK_BLOCKS; ty++) {
                        for (int tx = twx * WorldContainer.CHUNK_BLOCKS; tx < twx * WorldContainer.CHUNK_BLOCKS + WorldContainer.CHUNK_BLOCKS; tx++) {
                            if (tx >= 0 && tx < SIZE && ty >= 0 && ty < SIZE) {
                                worldContainer.drawn[ty][tx] = false;
                            }
                        }
                    }
                    log.info("Destroyed image at " + twx + " " + twy);
                }
            }
        }
        updateApp(u, v);
        updateEnvironment();
        worldContainer.player.update(worldContainer.blocks[1], keyPressed, u, v);
        if (worldContainer.timeOfDay >= 86400) {
            worldContainer.timeOfDay = 0;
            worldContainer.day += 1;
        }

        repaint();
        worldContainer.ready = true;
    }

    private void loadWorldContainer() {
        for (int twy = 0; twy < 2; twy++) {
            for (int twx = 0; twx < 2; twx++) {
                for (int y = 0; y < WorldContainer.CHUNK_BLOCKS; y++) {
                    for (int x = 0; x < WorldContainer.CHUNK_BLOCKS; x++) {
                        for (int l = 0; l < LAYER_SIZE; l++) {
                            worldContainer.blocks[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocks()[l][y][x];
                            worldContainer.power[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getPower()[l][y][x];
                            pzqn[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getPzqn()[l][y][x];
                            arbprd[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getArbprd()[l][y][x];
                            worldContainer.blocksDirections[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksDirections()[l][y][x];
                        }
                        worldContainer.blocksDirectionsIntensity[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksDirectionsIntensity()[y][x];
                        worldContainer.blocksBackgrounds[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksBackgrounds()[y][x];
                        worldContainer.blocksTextureIntensity[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksTextureIntesity()[y][x];
                        worldContainer.lights[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getLights()[y][x];
                        worldContainer.lsources[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getLsources()[y][x];
                        zqn[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getZqn()[y][x];
                        wcnct[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getWcnct()[y][x];
                        worldContainer.drawn[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getDrawn()[y][x];
                        worldContainer.rdrawn[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getRdrawn()[y][x];
                        worldContainer.ldrawn[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getLdrawn()[y][x];
                    }
                }
                worldContainer.worlds[twy][twx] = null;
            }
        }
    }

    private void loadChunks() {
        List<Chunk> chunkTemp = new ArrayList<>(0);
        for (int twy = 0; twy < 2; twy++) {
            for (int twx = 0; twx < 2; twx++) {
                if (chunkMatrix[twy][twx] != null) {
                    chunkTemp.add(chunkMatrix[twy][twx]);
                    chunkMatrix[twy][twx] = null;
                }
            }
        }
        int twy, twx = 0;
        for (twy = 0; twy < 2; twy++) {
            for (twx = 0; twx < 2; twx++) {
                for (int i = chunkTemp.size() - 1; i > -1; i--) {
                    if (chunkTemp.get(i).getCx() == twx && chunkTemp.get(i).getCy() == twy) {
                        chunkMatrix[twy][twx] = chunkTemp.get(i);
                        chunkTemp.remove(i);
                        break;
                    }
                }
                if (chunkMatrix[twy][twx] == null) {
                    if (temporarySaveFile[twy][twx] != null) {
                        chunkMatrix[twy][twx] = temporarySaveFile[twy][twx];
                    } else {
                        chunkMatrix[twy][twx] = new Chunk(twx + ou, twy + ov);
                    }
                }
            }
        }
        for (int i = 0; i < chunkTemp.size(); i++) {
            temporarySaveFile[twy][twx] = chunkTemp.get(i);
        }
    }

    private void handleMenuBehavior() {
        if (state == State.TITLE_SCREEN && !menuPressed) {
            if (mousePos.isInBetweenInclusive(ItemPositionInScreen.SINGLE_PLAYER_MENU)) {
                filesInfo = worldService.findWorlds();
                state = State.SELECT_WORLD;
                repaint();
                menuPressed = true;
            }
        } else if (state == State.SELECT_WORLD && !menuPressed) {
            if (mousePos.isInBetweenInclusive(ItemPositionInScreen.CREATE_NEW_WORLD_MENU)) {
                state = State.NEW_WORLD;
                newWorldName = new TextField(400, "New World");
                repaint();
                menuPressed = true;
            } else if (mousePos.isInBetweenInclusive(ItemPositionInScreen.BACK_BUTTON)) {
                state = State.TITLE_SCREEN;
                repaint();
                menuPressed = true;
            }
            for (int i = 0; i < filesInfo.size(); i++) {
                if (mousePos.isInBetweenInclusive(166, 470, 117 + i * 35, 152 + i * 35)) { // load world
                    currentWorld = filesInfo.get(i).getName();
                    state = State.LOADING_WORLD;
                    bg = Color.BLACK;
                    if (worldContainer.loadWorld(filesInfo.get(i).getFileName())) {
                        menuTimer.stop();
                        bg = CYANISH;
                        state = State.IN_GAME;
                        worldContainer.ready = true;
                        inGameTimer.start();
                        break;
                    }
                }
            }
        }
        if (state == State.NEW_WORLD && !menuPressed) {
            if (mousePos.isInBetweenInclusive(ItemPositionInScreen.CREATE_NEW_WORLD_MENU)) {
                if (!newWorldName.getText().equals("")) {
                    filesInfo = worldService.findWorlds();
                    boolean doGenerateWorld = true;
                    for (int i = 0; i < filesInfo.size(); i++) {
                        if (newWorldName.getText().equals(filesInfo.get(i).getName())) {
                            doGenerateWorld = false;
                            break;
                        }
                    }
                    if (doGenerateWorld) {
                        menuTimer.stop();
                        bg = Color.BLACK;
                        state = State.GENERATING_WORLD;
                        currentWorld = newWorldName.getText();
                        repaint();
                        Action createWorldThread = new AbstractAction() {
                            public void actionPerformed(ActionEvent ae) {
                                try {
                                    createNewWorld();
                                    bg = CYANISH;
                                    state = State.IN_GAME;
                                    worldContainer.ready = true;
                                    inGameTimer.start();
                                    createWorldTimer.stop();
                                } catch (Exception e) {
                                    log.error("Error creating new world", e);
                                }
                            }
                        };
                        createWorldTimer = new Timer(1, createWorldThread);
                        createWorldTimer.start();
                    }
                }
            }
            if (mousePos.isInBetweenInclusive(ItemPositionInScreen.BACK_BUTTON)) {
                state = State.SELECT_WORLD;
                repaint();
                menuPressed = true;
            }
        }
    }

    public void createNewWorld() {
        temporarySaveFile = new Chunk[WorldContainer.WORLD_HEIGHT][WorldContainer.WORLD_WIDTH];
        chunkMatrix = new Chunk[2][2];

        armor = new ItemCollection(ItemType.ARMOR, 4);

        worldContainer.createNewWorld();

        zqn = new Byte[SIZE][SIZE];
        pzqn = new Byte[TerrariaClone.LAYER_SIZE][SIZE][SIZE];
        arbprd = new Boolean[TerrariaClone.LAYER_SIZE][SIZE][SIZE];
        wcnct = new Boolean[SIZE][SIZE];

        miningTool = Items.EMPTY;
        moveDur = 0;

        pqx = new ArrayList<>();
        pqy = new ArrayList<>();

        log.info("-> Adding light sources...");

        zqx = new ArrayList<>();
        zqy = new ArrayList<>();
        pqx = new ArrayList<>();
        pqy = new ArrayList<>();
        pzqx = new ArrayList<>();
        pzqy = new ArrayList<>();
        updatex = new ArrayList<>();
        updatey = new ArrayList<>();
        updatet = new ArrayList<>();
        updatel = new ArrayList<>();

        log.info("-> Calculating light...");

        resolvePowerMatrix();
        resolveLightMatrix();

        log.info("Finished generation.");
    }

    public void updateApp(int u, int v) {
        mousePos2.setX(mousePos.getX() + worldContainer.player.intX - getWidth() / 2 + Player.WIDTH / 2);
        mousePos2.setY(mousePos.getY() + worldContainer.player.intY - getHeight() / 2 + Player.HEIGHT / 2);

        worldContainer.updateSkyLights();

        if (worldContainer.player.y / 16 > HEIGHT * 0.55) {
            bg = Color.BLACK;
        } else {
            bg = SKYCOLORS.get(worldContainer.currentSkyLight);
        }

        worldContainer.updateHealthPoints();

        for (int j = 0; j < worldContainer.machinesx.size(); j++) {
            int x = worldContainer.machinesx.get(j);
            int y = worldContainer.machinesy.get(j);
            for (int l = 0; l < LAYER_SIZE; l++) {
                if (worldContainer.icmatrix[l][y][x] != null && worldContainer.icmatrix[l][y][x].getType() == ItemType.FURNACE) {
                    if (worldContainer.icmatrix[l][y][x].isFurnaceOn()) {
                        if (worldContainer.icmatrix[l][y][x].getIds()[1] == Items.EMPTY) {
                            if (FUELS.get(worldContainer.icmatrix[l][y][x].getIds()[2]) != null) {
                                worldContainer.inventory.addLocationIC(worldContainer.icmatrix[l][y][x], 1, worldContainer.icmatrix[l][y][x].getIds()[2], (short) 1);
                                worldContainer.inventory.removeLocationIC(worldContainer.icmatrix[l][y][x], 2, (short) 1);
                                worldContainer.icmatrix[l][y][x].setFUELP(1);
                            } else {
                                worldContainer.icmatrix[l][y][x].setFurnaceOn(false);
                                removeBlockLighting(x, y);
                                worldContainer.blocks[l][y][x] = Blocks.FURNACE;
                                worldContainer.rdrawn[y][x] = false;
                            }
                        }
                        if (FUELS.get(worldContainer.icmatrix[l][y][x].getIds()[1]) != null) {
                            worldContainer.icmatrix[l][y][x].setFUELP(worldContainer.icmatrix[l][y][x].getFUELP() - FUELS.get(worldContainer.icmatrix[l][y][x].getIds()[1]));
                            if (worldContainer.icmatrix[l][y][x].getFUELP() < 0) {
                                worldContainer.icmatrix[l][y][x].setFUELP(0);
                                worldContainer.inventory.removeLocationIC(worldContainer.icmatrix[l][y][x], 1, worldContainer.icmatrix[l][y][x].getNums()[1]);
                            }
                            for (int i = 0; i < FRI1.size(); i++) {
                                if (worldContainer.icmatrix[l][y][x].getIds()[0] == FRI1.get(i) && worldContainer.icmatrix[l][y][x].getNums()[0] >= FRN1.get(i)) {
                                    worldContainer.icmatrix[l][y][x].setSMELTP(worldContainer.icmatrix[l][y][x].getSMELTP() + Blocks.findByIndex(worldContainer.icmatrix[l][y][x].getIds()[1].getIndex()).getFSpeed());
                                    if (worldContainer.icmatrix[l][y][x].getSMELTP() > 1) {
                                        worldContainer.icmatrix[l][y][x].setSMELTP(0);
                                        worldContainer.inventory.removeLocationIC(worldContainer.icmatrix[l][y][x], 0, FRN1.get(i));
                                        worldContainer.inventory.addLocationIC(worldContainer.icmatrix[l][y][x], 3, FRI2.get(i), FRN2.get(i));
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        worldContainer.icmatrix[l][y][x].setSMELTP(worldContainer.icmatrix[l][y][x].getSMELTP() - 0.00025);
                        if (worldContainer.icmatrix[l][y][x].getSMELTP() < 0) {
                            worldContainer.icmatrix[l][y][x].setSMELTP(0);
                        }
                    }
                }
            }
        }

        if (worldContainer.ic != null && worldContainer.ic.getType() == ItemType.FURNACE) {
            if (worldContainer.ic.isFurnaceOn()) {
                if (worldContainer.ic.getIds()[1] == Items.EMPTY) {
                    if (FUELS.get(worldContainer.ic.getIds()[2]) != null) {
                        worldContainer.inventory.addLocationIC(worldContainer.ic, 1, worldContainer.ic.getIds()[2], (short) 1);
                        worldContainer.inventory.removeLocationIC(worldContainer.ic, 2, (short) 1);
                        worldContainer.ic.setFUELP(1);
                    } else {
                        worldContainer.ic.setFurnaceOn(false);
                        removeBlockLighting(worldContainer.icx, worldContainer.icy);
                        worldContainer.blocks[iclayer][worldContainer.icy][worldContainer.icx] = Blocks.FURNACE;
                        worldContainer.rdrawn[worldContainer.icy][worldContainer.icx] = false;
                    }
                }
                if (FUELS.get(worldContainer.ic.getIds()[1]) != null) {
                    worldContainer.ic.setFUELP(worldContainer.ic.getFUELP() - FUELS.get(worldContainer.ic.getIds()[1]));
                    if (worldContainer.ic.getFUELP() < 0) {
                        worldContainer.ic.setFUELP(0);
                        worldContainer.inventory.removeLocationIC(worldContainer.ic, 1, worldContainer.ic.getNums()[1]);
                    }
                    for (int i = 0; i < FRI1.size(); i++) {
                        if (worldContainer.ic.getIds()[0] == FRI1.get(i) && worldContainer.ic.getNums()[0] >= FRN1.get(i)) {
                            worldContainer.ic.setSMELTP(worldContainer.ic.getSMELTP() + Blocks.findByIndex(worldContainer.ic.getIds()[1].getIndex()).getFSpeed());
                            if (worldContainer.ic.getSMELTP() > 1) {
                                worldContainer.ic.setSMELTP(0);
                                worldContainer.inventory.removeLocationIC(worldContainer.ic, 0, FRN1.get(i));
                                worldContainer.inventory.addLocationIC(worldContainer.ic, 3, FRI2.get(i), FRN2.get(i));
                            }
                            break;
                        }
                    }
                }
            } else {
                worldContainer.ic.setSMELTP(worldContainer.ic.getSMELTP() - 0.00025);
                if (worldContainer.ic.getSMELTP() < 0) {
                    worldContainer.ic.setSMELTP(0);
                }
            }
            worldContainer.inventory.updateIC(worldContainer.ic, -1);
        }
        if (Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - worldContainer.icx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - worldContainer.icy * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2)) > 160) {
            if (worldContainer.ic != null) {
                if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                    worldContainer.machinesx.add(worldContainer.icx);
                    worldContainer.machinesy.add(worldContainer.icy);
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        for (int i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        for (int i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                } else if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setSMELTP(worldContainer.ic.getSMELTP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFurnaceOn(worldContainer.ic.isFurnaceOn());
                }
                worldContainer.ic = null;
            }
        }

        worldContainer.upgradeBlocksState(u, v);
        worldContainer.updateGrassState(u, v);
        worldContainer.updateTreesState();

        for (int i = updatex.size() - 1; i > -1; i--) {
            updatet.set(i, updatet.get(i) - 1);
            if (updatet.get(i) <= 0) {
                if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == Blocks.BUTTON_LEFT_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = Blocks.BUTTON_LEFT;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == Blocks.BUTTON_RIGHT_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = Blocks.BUTTON_RIGHT;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == Blocks.WOODEN_PRESSURE_PLATE_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = Blocks.WOODEN_PRESSURE_PLATE;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == Blocks.STONE_PRESSURE_PLATE_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = Blocks.STONE_PRESSURE_PLATE;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == Blocks.ZYTHIUM_PRESSURE_PLATE_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = Blocks.ZYTHIUM_PRESSURE_PLATE;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)].isZythiumDelayerOnAll()) {
                    log.info("[DEBUG2R]");
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i), false);
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = Blocks.turnZythiumDelayerOff(worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)]);
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)].isZythiumDelayerAll()) {
                    log.info("[DEBUG2A]");
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = Blocks.turnZythiumDelayerOn(worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)]);
                    worldContainer.power[updatel.get(i)][updatey.get(i)][updatex.get(i)] = (float) 5;
                    addBlockLighting(updatex.get(i), updatey.get(i));
                    addTileToPQueue(updatex.get(i), updatey.get(i));
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                updatex.remove(i);
                updatey.remove(i);
                updatet.remove(i);
                updatel.remove(i);
            }
        }

        if (!DebugContext.PEACEFUL && worldContainer.mobCount < 100) {
            if (worldContainer.msi == 1) {
                for (int ay = (int) (worldContainer.player.intY / WorldContainer.BLOCK_SIZE) - 125; ay < (int) (worldContainer.player.intY / WorldContainer.BLOCK_SIZE) + 125; ay++) {
                    for (int ax = (int) (worldContainer.player.intX / WorldContainer.BLOCK_SIZE) - 125; ax < (int) (worldContainer.player.intX / WorldContainer.BLOCK_SIZE) + 125; ax++) {
                        if (RandomTool.nextInt((int) (100000 / DebugContext.HOSTILE)) == 0) {
                            int xpos = ax + RandomTool.nextInt(20) - 10;
                            int ypos = ay + RandomTool.nextInt(20) - 10;
                            int xpos2 = ax + RandomTool.nextInt(20) - 10;
                            int ypos2 = ay + RandomTool.nextInt(20) - 10;
                            if (xpos > 0 && xpos < WIDTH - 1 && ypos > 0 && ypos < HEIGHT - 1 && (worldContainer.blocks[1][ypos][xpos] == Blocks.AIR || !worldContainer.blocks[1][ypos][xpos].isCds() &&
                                    xpos2 > 0 && xpos2 < WIDTH - 1 && ypos2 > 0 && ypos2 < HEIGHT - 1 && worldContainer.blocks[1][ypos2][xpos2] != Blocks.AIR && worldContainer.blocks[1][ypos2][xpos2].isCds())) {
                                EntityType mobSpawn = null;
                                if ((worldContainer.day != 0 || DebugContext.HOSTILE > 1) && (worldContainer.timeOfDay >= 75913 || worldContainer.timeOfDay < 28883)) {
                                    if (RandomTool.nextInt(350) == 0) {
                                        int rnum = RandomTool.nextInt(100);
                                        if (rnum >= 0 && rnum < 45) {
                                            mobSpawn = EntityType.BLUE_BUBBLE; // 45%
                                        }
                                        if (rnum >= 45 && rnum < 85) {
                                            mobSpawn = EntityType.GREEN_BUBBLE; // 40%
                                        }
                                        if (rnum >= 85 && rnum < 100) {
                                            mobSpawn = EntityType.RED_BUBBLE; // 15%
                                        }
                                    }
                                }
                                if (worldContainer.timeOfDay >= 32302 && worldContainer.timeOfDay < 72093) {
                                    if (RandomTool.nextInt(200) == 0) {
                                        int rnum = RandomTool.nextInt(100);
                                        if (rnum >= 0 && rnum < 80) {
                                            mobSpawn = EntityType.ZOMBIE; // 80%
                                        }
                                        if (rnum >= 80 && rnum < 90) {
                                            mobSpawn = EntityType.ARMORED_ZOMBIE; // 10%
                                        }
                                        if (rnum >= 90 && rnum < 100) {
                                            mobSpawn = EntityType.SHOOTING_STAR; // 10%
                                        }
                                    }
                                }
                                if (mobSpawn != null && worldContainer.checkBiome(xpos, ypos, u, v) == Biome.DESERT) {
                                    if (RandomTool.nextInt(3) == 0) { // 33% of all spawns in desert
                                        mobSpawn = EntityType.SANDBOT;
                                    }
                                }
                                if (mobSpawn != null && worldContainer.checkBiome(xpos, ypos, u, v) == Biome.FROST) {
                                    if (RandomTool.nextInt(3) == 0) { // 33% of all spawns in desert
                                        mobSpawn = EntityType.SNOWMAN;
                                    }
                                }
                                if (mobSpawn == null) {
                                    continue;
                                } else if (DebugContext.MOBTEST != null) {
                                    mobSpawn = DebugContext.MOBTEST;
                                }
                                int xmax = 0, ymax = 0;
                                if (mobSpawn == EntityType.BLUE_BUBBLE || mobSpawn == EntityType.GREEN_BUBBLE
                                        || mobSpawn == EntityType.RED_BUBBLE || mobSpawn == EntityType.YELLOW_BUBBLE
                                        || mobSpawn == EntityType.BLACK_BUBBLE || mobSpawn == EntityType.WHITE_BUBBLE) {
                                    xmax = 2;
                                    ymax = 2;
                                } else if (mobSpawn == EntityType.ZOMBIE) {
                                    xmax = 2;
                                    ymax = 3;
                                } else if (mobSpawn == EntityType.ARMORED_ZOMBIE) {
                                    xmax = 2;
                                    ymax = 3;
                                } else if (mobSpawn == EntityType.SHOOTING_STAR) {
                                    xmax = 2;
                                    ymax = 2;
                                } else if (mobSpawn == EntityType.SANDBOT) {
                                    xmax = 2;
                                    ymax = 2;
                                } else if (mobSpawn == EntityType.SNOWMAN) {
                                    xmax = 2;
                                    ymax = 3;
                                } else if (mobSpawn == EntityType.BAT) {
                                    xmax = 1;
                                    ymax = 1;
                                } else if (mobSpawn == EntityType.BEE) {
                                    xmax = 1;
                                    ymax = 1;
                                } else if (mobSpawn == EntityType.SKELETON) {
                                    xmax = 1;
                                    ymax = 3;
                                }
                                worldContainer.doMobSpawn = true;
                                for (int x = xpos / WorldContainer.BLOCK_SIZE; x < (xpos / WorldContainer.BLOCK_SIZE + xmax); x++) {
                                    for (int y = ypos / WorldContainer.BLOCK_SIZE; y < (ypos / WorldContainer.BLOCK_SIZE + ymax); y++) {
                                        if (y > 0 && y < HEIGHT - 1 && worldContainer.blocks[1][y][x] != Blocks.AIR && worldContainer.blocks[1][y][x].isCds()) {
                                            worldContainer.doMobSpawn = false;
                                        }
                                    }
                                }
                                if (worldContainer.doMobSpawn) {
                                    worldContainer.entities.add(new Entity(xpos * WorldContainer.BLOCK_SIZE, ypos * WorldContainer.BLOCK_SIZE, 0, 0, mobSpawn));
                                }
                            }
                        }
                    }
                }
                worldContainer.msi = 0;
            } else {
                worldContainer.msi = 1;
            }
        }

        worldContainer.mobCount = 0;

        for (int i = worldContainer.entities.size() - 1; i > -1; i--) {
            if (worldContainer.entities.get(i).getEntityType() != null) {
                worldContainer.mobCount += 1;
                if (worldContainer.entities.get(i).getIx() < worldContainer.player.intX - 2000 || worldContainer.entities.get(i).getIx() > worldContainer.player.intX + 2000 ||
                        worldContainer.entities.get(i).getIy() < worldContainer.player.intY - 2000 || worldContainer.entities.get(i).getIy() > worldContainer.player.intY + 2000) {
                    if (RandomTool.nextInt(500) == 0) {
                        worldContainer.entities.remove(i);
                    }
                }
            }
        }

        if (mousePressed == MousePressed.LEFT_MOUSE) {
            checkBlocks = true;
            if (worldContainer.showInv) {
                if (mousePos.isInBetweenInclusive(getWidth() - save_exit.getWidth() - 24, getWidth() - 24, getHeight() - save_exit.getHeight() - 24, getHeight() - 24)) {
                    if (mousePos.isClicked()) {
                        mousePos.setReleased(true);
                        worldContainer.saveWorld(currentWorld);
                        state = State.TITLE_SCREEN;
                        inGameTimer.stop();
                        menuTimer.start();
                        return;
                    }
                }
                for (int ux = 0; ux < 10; ux++) {
                    for (int uy = 0; uy < 4; uy++) {
                        if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + 6, uy * 46 + 46)) {
                            checkBlocks = false;
                            if (mousePos.isClicked()) {
                                mousePos.setReleased(true);
                                if (uy != 0 || worldContainer.inventory.selection != ux || !worldContainer.showTool) {
                                    worldContainer.moveItemTemp = worldContainer.inventory.items[uy * 10 + ux];
                                    worldContainer.moveNumTemp = worldContainer.inventory.nums[uy * 10 + ux];
                                    moveDurTemp = worldContainer.inventory.durs[uy * 10 + ux];
                                    if (worldContainer.moveItem == worldContainer.inventory.items[uy * 10 + ux]) {
                                        worldContainer.moveNum = (short) worldContainer.inventory.addLocation(uy * 10 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                        if (worldContainer.moveNum == 0) {
                                            worldContainer.moveItem = Items.EMPTY;
                                            moveDur = 0;
                                        }
                                    } else {
                                        worldContainer.inventory.removeLocation(uy * 10 + ux, worldContainer.inventory.nums[uy * 10 + ux]);
                                        if (worldContainer.moveItem != Items.EMPTY) {
                                            worldContainer.inventory.addLocation(uy * 10 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                        }
                                        worldContainer.moveItem = worldContainer.moveItemTemp;
                                        worldContainer.moveNum = worldContainer.moveNumTemp;
                                        moveDur = moveDurTemp;
                                    }
                                }
                            }
                        }
                    }
                }
                for (int ux = 0; ux < 2; ux++) {
                    for (int uy = 0; uy < 2; uy++) {
                        if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + ux * 40 + 75, worldContainer.inventory.image.getWidth() + ux * 40 + 115, uy * 40 + 52, uy * 40 + 92)) {
                            checkBlocks = false;
                            if (mousePos.isClicked()) {
                                mousePos.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.cic.getIds()[uy * 2 + ux];
                                worldContainer.moveNumTemp = worldContainer.cic.getNums()[uy * 2 + ux];
                                moveDurTemp = worldContainer.cic.getDurs()[uy * 2 + ux];
                                if (worldContainer.moveItem == worldContainer.cic.getIds()[uy * 2 + ux]) {
                                    worldContainer.moveNum = (short) worldContainer.inventory.addLocationIC(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                        moveDur = 0;
                                    }
                                } else {
                                    worldContainer.inventory.removeLocationIC(worldContainer.cic, uy * 2 + ux, worldContainer.cic.getNums()[uy * 2 + ux]);
                                    if (worldContainer.moveItem != Items.EMPTY) {
                                        worldContainer.inventory.addLocationIC(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    }
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                    moveDur = moveDurTemp;
                                }
                            }
                        }
                    }
                }
                if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + 3 * 40 + 81, worldContainer.inventory.image.getWidth() + 3 * 40 + 121, 20 + 52, 20 + 92)) {
                    checkBlocks = false;
                    if (mousePos.isClicked()) {
                        if (worldContainer.moveItem == worldContainer.cic.getIds()[4] && worldContainer.moveNum + worldContainer.cic.getNums()[4] <= worldContainer.cic.getIds()[4].getMaxStacks()) {
                            worldContainer.moveNum += worldContainer.cic.getNums()[4];
                            worldContainer.inventory.useRecipeCIC(worldContainer.cic);
                        }
                        if (worldContainer.moveItem == Items.EMPTY) {
                            worldContainer.moveItem = worldContainer.cic.getIds()[4];
                            worldContainer.moveNum = worldContainer.cic.getNums()[4];
                            if (worldContainer.moveItem.getDurability() != null) {
                                moveDur = worldContainer.moveItem.getDurability().shortValue();
                            }
                            worldContainer.inventory.useRecipeCIC(worldContainer.cic);
                        }
                    }
                }
                if (worldContainer.ic != null) {
                    if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                        for (int ux = 0; ux < 3; ux++) {
                            for (int uy = 0; uy < 3; uy++) {
                                if (mousePos.isInBetween(ux * 40 + 6, ux * 40 + 46, uy * 40 + worldContainer.inventory.image.getHeight() + 46, uy * 40 + worldContainer.inventory.image.getHeight() + 86)) {
                                    checkBlocks = false;
                                    if (mousePos.isClicked()) {
                                        mousePos.setReleased(true);
                                        worldContainer.moveItemTemp = worldContainer.ic.getIds()[uy * 3 + ux];
                                        worldContainer.moveNumTemp = worldContainer.ic.getNums()[uy * 3 + ux];
                                        if (worldContainer.moveItem == worldContainer.ic.getIds()[uy * 3 + ux]) {
                                            worldContainer.moveNum = (short) worldContainer.inventory.addLocationIC(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                            if (worldContainer.moveNum == 0) {
                                                worldContainer.moveItem = Items.EMPTY;
                                            }
                                        } else {
                                            worldContainer.inventory.removeLocationIC(worldContainer.ic, uy * 3 + ux, worldContainer.ic.getNums()[uy * 3 + ux]);
                                            if (worldContainer.moveItem != Items.EMPTY) {
                                                worldContainer.inventory.addLocationIC(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                            }
                                            worldContainer.moveItem = worldContainer.moveItemTemp;
                                            worldContainer.moveNum = worldContainer.moveNumTemp;
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos.isInBetween(4 * 40 + 6, 4 * 40 + 46, 1 * 40 + worldContainer.inventory.image.getHeight() + 46, 1 * 40 + worldContainer.inventory.image.getHeight() + 86)) {
                            checkBlocks = false;
                            if (mousePos.isClicked()) {
                                if (worldContainer.moveItem == worldContainer.ic.getIds()[9] && worldContainer.moveNum + worldContainer.ic.getNums()[9] <= worldContainer.ic.getIds()[9].getMaxStacks()) {
                                    worldContainer.moveNum += worldContainer.ic.getNums()[9];
                                    worldContainer.inventory.useRecipeWorkbench(worldContainer.ic);
                                }
                                if (worldContainer.moveItem == Items.EMPTY) {
                                    worldContainer.moveItem = worldContainer.ic.getIds()[9];
                                    worldContainer.moveNum = worldContainer.ic.getNums()[9];
                                    if (worldContainer.moveItem.getDurability() != null) {
                                        moveDur = worldContainer.moveItem.getDurability().shortValue();
                                    }
                                    worldContainer.inventory.useRecipeWorkbench(worldContainer.ic);
                                }
                            }
                        }
                    } else if (worldContainer.ic.getType() == ItemType.WOODEN_CHEST || worldContainer.ic.getType() == ItemType.STONE_CHEST ||
                            worldContainer.ic.getType() == ItemType.COPPER_CHEST || worldContainer.ic.getType() == ItemType.IRON_CHEST ||
                            worldContainer.ic.getType() == ItemType.SILVER_CHEST || worldContainer.ic.getType() == ItemType.GOLD_CHEST ||
                            worldContainer.ic.getType() == ItemType.ZINC_CHEST || worldContainer.ic.getType() == ItemType.RHYMESTONE_CHEST ||
                            worldContainer.ic.getType() == ItemType.OBDURITE_CHEST) {
                        for (int ux = 0; ux < worldContainer.inventory.CX; ux++) {
                            for (int uy = 0; uy < worldContainer.inventory.CY; uy++) {
                                if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + worldContainer.inventory.image.getHeight() + 46, uy * 46 + worldContainer.inventory.image.getHeight() + 86)) {
                                    checkBlocks = false;
                                    if (mousePos.isClicked()) {
                                        mousePos.setReleased(true);
                                        worldContainer.moveItemTemp = worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux];
                                        worldContainer.moveNumTemp = worldContainer.ic.getNums()[uy * worldContainer.inventory.CX + ux];
                                        if (worldContainer.moveItem == worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux]) {
                                            worldContainer.moveNum = (short) worldContainer.inventory.addLocationIC(worldContainer.ic, uy * worldContainer.inventory.CX + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                            if (worldContainer.moveNum == 0) {
                                                worldContainer.moveItem = Items.EMPTY;
                                            }
                                        } else {
                                            worldContainer.inventory.removeLocationIC(worldContainer.ic, uy * worldContainer.inventory.CX + ux, worldContainer.ic.getNums()[uy * worldContainer.inventory.CX + ux]);
                                            if (worldContainer.moveItem != Items.EMPTY) {
                                                worldContainer.inventory.addLocationIC(worldContainer.ic, uy * worldContainer.inventory.CX + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                            }
                                            worldContainer.moveItem = worldContainer.moveItemTemp;
                                            worldContainer.moveNum = worldContainer.moveNumTemp;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (worldContainer.ic.getType() == ItemType.FURNACE) {
                        if (mousePos.isInBetween(6, 46, worldContainer.inventory.image.getHeight() + 46, worldContainer.inventory.image.getHeight() + 86)) {
                            checkBlocks = false;
                            if (mousePos.isClicked()) {
                                mousePos.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.ic.getIds()[0];
                                worldContainer.moveNumTemp = worldContainer.ic.getNums()[0];
                                if (worldContainer.moveItem == worldContainer.ic.getIds()[0]) {
                                    worldContainer.moveNum = (short) worldContainer.inventory.addLocationIC(worldContainer.ic, 0, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                } else {
                                    worldContainer.inventory.removeLocationIC(worldContainer.ic, 0, worldContainer.ic.getNums()[0]);
                                    if (worldContainer.moveItem != Items.EMPTY) {
                                        worldContainer.inventory.addLocationIC(worldContainer.ic, 0, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    }
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                    worldContainer.ic.setSMELTP(0);
                                }
                            }
                        }
                        if (mousePos.isInBetween(6, 46, worldContainer.inventory.image.getHeight() + 142, worldContainer.inventory.image.getHeight() + 182)) {
                            checkBlocks = false;
                            if (mousePos.isClicked()) {
                                mousePos.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.ic.getIds()[2];
                                worldContainer.moveNumTemp = worldContainer.ic.getNums()[2];
                                if (worldContainer.moveItem == worldContainer.ic.getIds()[2]) {
                                    worldContainer.moveNum = (short) worldContainer.inventory.addLocationIC(worldContainer.ic, 2, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                } else {
                                    worldContainer.inventory.removeLocationIC(worldContainer.ic, 2, worldContainer.ic.getNums()[2]);
                                    if (worldContainer.moveItem != Items.EMPTY) {
                                        worldContainer.inventory.addLocationIC(worldContainer.ic, 2, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    }
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                }
                            }
                        }
                        if (mousePos.isInBetween(62, 102, worldContainer.inventory.image.getHeight() + 46, worldContainer.inventory.image.getHeight() + 86)) {
                            checkBlocks = false;
                            if (mousePos.isClicked()) {
                                mousePos.setReleased(true);
                                if (worldContainer.moveItem == Items.EMPTY) {
                                    worldContainer.moveItem = worldContainer.ic.getIds()[3];
                                    worldContainer.moveNum = worldContainer.ic.getNums()[3];
                                    worldContainer.inventory.removeLocationIC(worldContainer.ic, 3, worldContainer.ic.getNums()[3]);
                                } else if (worldContainer.moveItem == worldContainer.ic.getIds()[3]) {
                                    worldContainer.moveNum += worldContainer.ic.getNums()[3];
                                    worldContainer.inventory.removeLocationIC(worldContainer.ic, 3, worldContainer.ic.getNums()[3]);
                                    if (worldContainer.moveNum > worldContainer.moveItem.getMaxStacks()) {
                                        worldContainer.inventory.addLocationIC(worldContainer.ic, 3, worldContainer.moveItem, (short) (worldContainer.moveNum - worldContainer.moveItem.getMaxStacks()), moveDur);
                                        worldContainer.moveNum = (short) worldContainer.moveItem.getMaxStacks();
                                    }
                                }
                            }
                        }
                    }
                }
                for (int uy = 0; uy < 4; uy++) {
                    if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + 6, worldContainer.inventory.image.getWidth() + 6 + armor.getImage().getWidth(), 6 + uy * 46, 6 + uy * 46 + 40)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            int i = uy;
                            if (uy == 0 && (worldContainer.moveItem == Items.COPPER_HELMET || worldContainer.moveItem == Items.IRON_HELMET || worldContainer.moveItem == Items.SILVER_HELMET || worldContainer.moveItem == Items.GOLD_HELMET ||
                                    worldContainer.moveItem == Items.ZINC_HELMET || worldContainer.moveItem == Items.RHYMESTONE_HELMET || worldContainer.moveItem == Items.OBDURITE_HELMET || worldContainer.moveItem == Items.ALUMINUM_HELMET ||
                                    worldContainer.moveItem == Items.LEAD_HELMET || worldContainer.moveItem == Items.ZYTHIUM_HELMET) ||
                                    uy == 1 && (worldContainer.moveItem == Items.COPPER_CHESTPLATE || worldContainer.moveItem == Items.IRON_CHESTPLATE || worldContainer.moveItem == Items.SILVER_CHESTPLATE || worldContainer.moveItem == Items.GOLD_CHESTPLATE ||
                                            worldContainer.moveItem == Items.ZINC_CHESTPLATE || worldContainer.moveItem == Items.RHYMESTONE_CHESTPLATE || worldContainer.moveItem == Items.OBDURITE_CHESTPLATE || worldContainer.moveItem == Items.ALUMINUM_CHESTPLATE ||
                                            worldContainer.moveItem == Items.LEAD_CHESTPLATE || worldContainer.moveItem == Items.ZYTHIUM_CHESTPLATE) ||
                                    uy == 2 && (worldContainer.moveItem == Items.COPPER_LEGGINGS || worldContainer.moveItem == Items.IRON_LEGGINGS || worldContainer.moveItem == Items.SILVER_LEGGINGS || worldContainer.moveItem == Items.GOLD_LEGGINGS ||
                                            worldContainer.moveItem == Items.ZINC_LEGGINGS || worldContainer.moveItem == Items.RHYMESTONE_LEGGINGS || worldContainer.moveItem == Items.OBDURITE_LEGGINGS || worldContainer.moveItem == Items.ALUMINUM_LEGGINGS ||
                                            worldContainer.moveItem == Items.LEAD_LEGGINGS || worldContainer.moveItem == Items.ZYTHIUM_LEGGINGS) ||
                                    uy == 3 && (worldContainer.moveItem == Items.COPPER_GREAVES || worldContainer.moveItem == Items.IRON_GREAVES || worldContainer.moveItem == Items.SILVER_GREAVES || worldContainer.moveItem == Items.GOLD_GREAVES ||
                                            worldContainer.moveItem == Items.ZINC_GREAVES || worldContainer.moveItem == Items.RHYMESTONE_GREAVES || worldContainer.moveItem == Items.OBDURITE_GREAVES || worldContainer.moveItem == Items.ALUMINUM_GREAVES ||
                                            worldContainer.moveItem == Items.LEAD_GREAVES || worldContainer.moveItem == Items.ZYTHIUM_GREAVES)) {
                                if (armor.getIds()[i] == Items.EMPTY) {
                                    worldContainer.inventory.addLocationIC(armor, i, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    worldContainer.moveItem = Items.EMPTY;
                                    worldContainer.moveNum = 0;
                                } else {
                                    worldContainer.moveItemTemp = armor.getIds()[i];
                                    worldContainer.moveNumTemp = armor.getNums()[i];
                                    worldContainer.inventory.removeLocationIC(armor, i, worldContainer.moveNumTemp);
                                    worldContainer.inventory.addLocationIC(armor, i, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                }
                            } else if (worldContainer.moveItem == Items.EMPTY) {
                                worldContainer.moveItem = armor.getIds()[i];
                                worldContainer.moveNum = armor.getNums()[i];
                                worldContainer.inventory.removeLocationIC(armor, i, worldContainer.moveNum);
                            }
                        }
                    }
                }
            } else {
                for (int ux = 0; ux < 10; ux++) {
                    if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, 6, 46)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            worldContainer.inventory.select2(ux);
                        }
                    }
                }
            }
            if (mousePos.isReleased()) {
                mousePos.setClicked(false);
            }
            if (checkBlocks) {
                if (worldContainer.inventory.tool() != Items.EMPTY && !worldContainer.showTool) {
                    tool = itemImgs.get(worldContainer.inventory.tool());
                    for (int i = 0; i < worldContainer.entities.size(); i++) {
                        worldContainer.entities.get(i).setImmune(false);
                    }
                    worldContainer.toolSpeed = worldContainer.inventory.tool().getSpeed();
                    if (worldContainer.inventory.tool() == Items.MAGNETITE_PICK || worldContainer.inventory.tool() == Items.MAGNETITE_AXE || worldContainer.inventory.tool() == Items.MAGNETITE_SWORD) {
                        worldContainer.toolSpeed *= ((double) worldContainer.inventory.durs[worldContainer.inventory.selection] / worldContainer.inventory.items[worldContainer.inventory.selection].getDurability()) * (-0.714) + 1;
                    }
                    worldContainer.showTool = true;
                    worldContainer.toolAngle = 4.7;
                    int ux = mousePos2.getX() / WorldContainer.BLOCK_SIZE;
                    int uy = mousePos2.getY() / WorldContainer.BLOCK_SIZE;
                    int ux2 = mousePos2.getX() / WorldContainer.BLOCK_SIZE;
                    int uy2 = mousePos2.getY() / WorldContainer.BLOCK_SIZE;
                    if (Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2)) <= 160 ||
                            Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2 + WIDTH * WorldContainer.BLOCK_SIZE, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2)) <= 160 || DebugContext.REACH) {
                        if (Arrays.asList(TOOL_LIST).contains(worldContainer.inventory.tool())) {
                            if (worldContainer.blocks[worldContainer.layer][uy][ux] != Blocks.AIR && worldContainer.blocks[worldContainer.layer][uy][ux].getTools().contains(worldContainer.inventory.tool())) {
                                worldContainer.blocksDirectionsIntensity[uy][ux] = (byte) RandomTool.nextInt(5);
                                worldContainer.drawn[uy][ux] = false;
                                if (ux == worldContainer.mx && uy == worldContainer.my && worldContainer.inventory.tool() == miningTool) {
                                    worldContainer.mining += 1;
                                    worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                    breakCurrentBlock(ux, uy);
                                    if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                    }
                                } else {
                                    worldContainer.mining = 1;
                                    miningTool = worldContainer.inventory.tool();
                                    worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                    breakCurrentBlock(ux, uy);
                                    worldContainer.mx = ux;
                                    worldContainer.my = uy;
                                    if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                    }
                                }
                                ug2 = null;
                            }
                        } else if (worldContainer.inventory.tool() == Items.STONE_LIGHTER) {
                            if (worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE_ON) {
                                if (worldContainer.icmatrix[worldContainer.layer][uy][ux] != null && worldContainer.icmatrix[worldContainer.layer][uy][ux].getType() == ItemType.FURNACE) {
                                    worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                    worldContainer.icmatrix[worldContainer.layer][uy][ux].setFurnaceOn(true);
                                    worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.FURNACE_ON;
                                    addBlockLighting(ux, uy);
                                    if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                    }
                                    worldContainer.rdrawn[uy][ux] = false;
                                } else {
                                    if (worldContainer.ic != null && worldContainer.ic.getType() == ItemType.FURNACE) {
                                        worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                        worldContainer.ic.setFurnaceOn(true);
                                        worldContainer.blocks[worldContainer.layer][worldContainer.icy][worldContainer.icx] = Blocks.FURNACE_ON;
                                        addBlockLighting(ux, uy);
                                        worldContainer.rdrawn[worldContainer.icy][worldContainer.icx] = false;
                                        if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                            worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                        }
                                    }
                                }
                            }
                        } else if (worldContainer.inventory.tool() == Items.WRENCH) {
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayerOn()) {
                                worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.increaseZythiumDelayerLevel(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.rdrawn[uy][ux] = false;
                                if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                    worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                }
                            } else if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer8() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer8On()) {
                                worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.resetZythiumDelayerLevel(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.rdrawn[uy][ux] = false;
                                if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                    worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                }
                            }
                        } else if (worldContainer.inventory.tool().getBlocks() != Blocks.AIR) {
                            worldContainer.blockTemp = worldContainer.inventory.tool().getBlocks();
                            if (uy >= 1 && (worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.AIR) &&
                                    (worldContainer.layer == 0 && (
                                            worldContainer.blocks[worldContainer.layer][uy][ux - 1] != Blocks.AIR || worldContainer.blocks[worldContainer.layer][uy][ux + 1] != Blocks.AIR ||
                                                    worldContainer.blocks[worldContainer.layer][uy - 1][ux] != Blocks.AIR || worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.AIR ||
                                                    worldContainer.blocks[worldContainer.layer + 1][uy][ux] != Blocks.AIR) ||
                                            worldContainer.layer == 1 && (
                                                    worldContainer.blocks[worldContainer.layer][uy][ux - 1] != Blocks.AIR || worldContainer.blocks[worldContainer.layer][uy][ux + 1] != Blocks.AIR ||
                                                            worldContainer.blocks[worldContainer.layer][uy - 1][ux] != Blocks.AIR || worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.AIR ||
                                                            worldContainer.blocks[worldContainer.layer - 1][uy][ux] != Blocks.AIR || worldContainer.blocks[worldContainer.layer + 1][uy][ux] != Blocks.AIR) ||
                                            worldContainer.layer == 2 && (
                                                    worldContainer.blocks[worldContainer.layer][uy][ux - 1] != Blocks.AIR || worldContainer.blocks[worldContainer.layer][uy][ux + 1] != Blocks.AIR ||
                                                            worldContainer.blocks[worldContainer.layer][uy - 1][ux] != Blocks.AIR || worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.AIR ||
                                                            worldContainer.blocks[worldContainer.layer - 1][uy][ux] != Blocks.AIR)) &&
                                    !(worldContainer.blockTemp == Blocks.SUNFLOWER_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // sunflower
                                            worldContainer.blockTemp == Blocks.MOONFLOWER_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // moonflower
                                            worldContainer.blockTemp == Blocks.DRYWEED_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.SAND) || // dryweed
                                            worldContainer.blockTemp == Blocks.GREENLEAF_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // greenleaf
                                            worldContainer.blockTemp == Blocks.FROSTLEAF_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.SNOW) || // frostleaf
                                            worldContainer.blockTemp == Blocks.CAVEROOT_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.STONE) || // caveroot
                                            worldContainer.blockTemp == Blocks.SKYBLOSSOM_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // skyblossom
                                            worldContainer.blockTemp == Blocks.VOID_ROT_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.STONE))) { // void_rot
                                if (!(TORCHESL.get(worldContainer.blockTemp) != null) || uy < HEIGHT - 1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux].isSolid() && worldContainer.blockTemp != Blocks.BUTTON_LEFT || worldContainer.blocks[worldContainer.layer][uy][ux + 1].isSolid() || worldContainer.blocks[worldContainer.layer][uy][ux - 1].isSolid())) {
                                    if (TORCHESL.get(worldContainer.blockTemp) != null) {
                                        if (worldContainer.blocks[worldContainer.layer][uy + 1][ux].isSolid() && worldContainer.blockTemp != Blocks.BUTTON_LEFT) {
                                            worldContainer.blockTemp = worldContainer.blockTemp;
                                        } else if (worldContainer.blocks[worldContainer.layer][uy][ux - 1].isSolid()) {
                                            worldContainer.blockTemp = TORCHESL.get(worldContainer.blockTemp);
                                        } else if (worldContainer.blocks[worldContainer.layer][uy][ux + 1].isSolid()) {
                                            worldContainer.blockTemp = TORCHESR.get(worldContainer.blockTemp);
                                        }
                                    }
                                    if (worldContainer.layer == 1 && !DebugContext.GPLACE && worldContainer.blockTemp.isCds()) {
                                        for (int i = 0; i < worldContainer.entities.size(); i++) {
                                            if (worldContainer.entities.get(i).getEntityType() != null && worldContainer.entities.get(i).getRect().intersects(new Rectangle(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                                                worldContainer.blockTemp = Blocks.AIR;
                                            }
                                        }
                                        if (worldContainer.player.rect.intersects(new Rectangle(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                                            worldContainer.blockTemp = Blocks.AIR;
                                        }
                                    }
                                    if (worldContainer.blockTemp != Blocks.AIR) {
                                        worldContainer.blocks[worldContainer.layer][uy][ux] = worldContainer.blockTemp;
                                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isReceive()) {
                                            addAdjacentTilesToPQueue(ux, uy);
                                        }
                                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isPower()) {
                                            addBlockPower(ux, uy);
                                        }
                                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isLTrans()) {
                                            removeSunLighting(ux, uy);
                                            redoBlockLighting(ux, uy);
                                        }
                                        addBlockLighting(ux, uy);
                                    }
                                    if (worldContainer.blockTemp != Blocks.AIR) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, (short) 1);
                                        worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                                        for (int uly = uy - 1; uly < uy + 2; uly++) {
                                            for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                                                worldContainer.blocksDirectionsIntensity[uly][ulx] = (byte) RandomTool.nextInt(5);
                                            }
                                        }
                                        for (int uly = uy - 1; uly < uy + 2; uly++) {
                                            for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                                                worldContainer.drawn[uly][ulx] = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            mousePos.setClicked(true);
        }
        if (mousePressed == MousePressed.RIGHT_MOUSE) {
            checkBlocks = true;
            if (worldContainer.showInv) {
                for (int ux = 0; ux < 10; ux++) {
                    for (int uy = 0; uy < 4; uy++) {
                        if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + 6, uy * 46 + 46)) {
                            checkBlocks = false;
                            if (mousePos2.isClicked()) {
                                mousePos2.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.inventory.items[uy * 10 + ux];
                                worldContainer.moveNumTemp = (short) (worldContainer.inventory.nums[uy * 10 + ux] / 2);
                                moveDurTemp = worldContainer.inventory.durs[uy * 10 + ux];
                                if (worldContainer.inventory.items[uy * 10 + ux] == Items.EMPTY) {
                                    worldContainer.inventory.addLocation(uy * 10 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                        moveDur = 0;
                                    }
                                } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.inventory.nums[uy * 10 + ux] != 1) {
                                    worldContainer.inventory.removeLocation(uy * 10 + ux, (short) (worldContainer.inventory.nums[uy * 10 + ux] / 2));
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                    moveDur = moveDurTemp;
                                } else if (worldContainer.moveItem == worldContainer.inventory.items[uy * 10 + ux] && worldContainer.inventory.nums[uy * 10 + ux] < worldContainer.inventory.items[uy * 10 + ux].getMaxStacks()) {
                                    worldContainer.inventory.addLocation(uy * 10 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                        moveDur = 0;
                                    }
                                }
                            }
                        }
                    }
                }
                for (int ux = 0; ux < 2; ux++) {
                    for (int uy = 0; uy < 2; uy++) {
                        if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + ux * 40 + 75, worldContainer.inventory.image.getWidth() + ux * 40 + 121, uy * 40 + 52, uy * 40 + 92)) {
                            checkBlocks = false;
                            if (mousePos2.isClicked()) {
                                mousePos2.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.cic.getIds()[uy * 2 + ux];
                                worldContainer.moveNumTemp = (short) (worldContainer.cic.getNums()[uy * 2 + ux] / 2);
                                if (worldContainer.cic.getIds()[uy * 2 + ux] == Items.EMPTY) {
                                    worldContainer.inventory.addLocationIC(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.cic.getNums()[uy * 2 + ux] != 1) {
                                    worldContainer.inventory.removeLocationIC(worldContainer.cic, uy * 2 + ux, (short) (worldContainer.cic.getNums()[uy * 2 + ux] / 2));
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                } else if (worldContainer.moveItem == worldContainer.cic.getIds()[uy * 2 + ux] && worldContainer.cic.getNums()[uy * 2 + ux] < worldContainer.cic.getIds()[uy * 2 + ux].getMaxStacks()) {
                                    worldContainer.inventory.addLocationIC(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                }
                            }
                        }
                    }
                }
                if (worldContainer.ic != null) {
                    if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                        for (int ux = 0; ux < 3; ux++) {
                            for (int uy = 0; uy < 3; uy++) {
                                if (mousePos.isInBetween(ux * 40 + 6, ux * 40 + 46, uy * 40 + worldContainer.inventory.image.getHeight() + 46, uy * 40 + worldContainer.inventory.image.getHeight() + 86)) {
                                    checkBlocks = false;
                                    if (mousePos2.isClicked()) {
                                        mousePos2.setReleased(true);
                                        worldContainer.moveItemTemp = worldContainer.ic.getIds()[uy * 3 + ux];
                                        worldContainer.moveNumTemp = (short) (worldContainer.ic.getNums()[uy * 3 + ux] / 2);
                                        if (worldContainer.ic.getIds()[uy * 3 + ux] == Items.EMPTY) {
                                            worldContainer.inventory.addLocationIC(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                            worldContainer.moveNum -= 1;
                                            if (worldContainer.moveNum == 0) {
                                                worldContainer.moveItem = Items.EMPTY;
                                            }
                                        } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[uy * 3 + ux] != 1) {
                                            worldContainer.inventory.removeLocationIC(worldContainer.ic, uy * 3 + ux, (short) (worldContainer.ic.getNums()[uy * 3 + ux] / 2));
                                            worldContainer.moveItem = worldContainer.moveItemTemp;
                                            worldContainer.moveNum = worldContainer.moveNumTemp;
                                        } else if (worldContainer.moveItem == worldContainer.ic.getIds()[uy * 3 + ux] && worldContainer.ic.getNums()[uy * 3 + ux] < worldContainer.ic.getIds()[uy * 3 + ux].getMaxStacks()) {
                                            if (worldContainer.ic.getIds()[7] == Items.BARK && worldContainer.ic.getNums()[7] == 51 && worldContainer.moveItem == Items.CLAY && uy * 3 + ux == 3 && worldContainer.ic.getNums()[8] == 0) {
                                                worldContainer.inventory.addLocationIC(worldContainer.ic, 8, Items.WOODEN_PICK, (short) 1);
                                            } else {
                                                worldContainer.inventory.addLocationIC(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                                worldContainer.moveNum -= 1;
                                                if (worldContainer.moveNum == 0) {
                                                    worldContainer.moveItem = Items.EMPTY;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos.isInBetween(4 * 40 + 6, 4 * 40 + 46, 1 * 40 + worldContainer.inventory.image.getHeight() + 46, 1 * 40 + worldContainer.inventory.image.getHeight() + 86)) {
                            checkBlocks = false;
                            if (mousePos2.isClicked()) {
                                //
                            }
                        }
                    } else if (worldContainer.ic.getType() == ItemType.WOODEN_CHEST || worldContainer.ic.getType() == ItemType.STONE_CHEST ||
                            worldContainer.ic.getType() == ItemType.COPPER_CHEST || worldContainer.ic.getType() == ItemType.IRON_CHEST ||
                            worldContainer.ic.getType() == ItemType.SILVER_CHEST || worldContainer.ic.getType() == ItemType.GOLD_CHEST ||
                            worldContainer.ic.getType() == ItemType.ZINC_CHEST || worldContainer.ic.getType() == ItemType.RHYMESTONE_CHEST ||
                            worldContainer.ic.getType() == ItemType.OBDURITE_CHEST) {
                        for (int ux = 0; ux < worldContainer.inventory.CX; ux++) {
                            for (int uy = 0; uy < worldContainer.inventory.CY; uy++) {
                                if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + worldContainer.inventory.image.getHeight() + 46, uy * 46 + worldContainer.inventory.image.getHeight() + 86)) {
                                    checkBlocks = false;
                                    if (mousePos2.isClicked()) {
                                        mousePos2.setReleased(true);
                                        worldContainer.moveItemTemp = worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux];
                                        worldContainer.moveNumTemp = (short) (worldContainer.ic.getNums()[uy * worldContainer.inventory.CX + ux] / 2);
                                        if (worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux] == Items.EMPTY) {
                                            worldContainer.inventory.addLocationIC(worldContainer.ic, uy * worldContainer.inventory.CX + ux, worldContainer.moveItem, (short) 1, moveDur);
                                            worldContainer.moveNum -= 1;
                                            if (worldContainer.moveNum == 0) {
                                                worldContainer.moveItem = Items.EMPTY;
                                            }
                                        } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[uy * worldContainer.inventory.CX + ux] != 1) {
                                            worldContainer.inventory.removeLocationIC(worldContainer.ic, uy * worldContainer.inventory.CX + ux, (short) (worldContainer.ic.getNums()[uy * worldContainer.inventory.CX + ux] / 2));
                                            worldContainer.moveItem = worldContainer.moveItemTemp;
                                            worldContainer.moveNum = worldContainer.moveNumTemp;
                                        } else if (worldContainer.moveItem == worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux] && worldContainer.ic.getNums()[uy * worldContainer.inventory.CX + ux] < worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux].getMaxStacks()) {
                                            worldContainer.inventory.addLocationIC(worldContainer.ic, uy * worldContainer.inventory.CX + ux, worldContainer.moveItem, (short) 1, moveDur);
                                            worldContainer.moveNum -= 1;
                                            if (worldContainer.moveNum == 0) {
                                                worldContainer.moveItem = Items.EMPTY;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (worldContainer.ic.getType() == ItemType.FURNACE) {
                        if (mousePos.isInBetween(6, 46, worldContainer.inventory.image.getHeight() + 46, worldContainer.inventory.image.getHeight() + 86)) {
                            checkBlocks = false;
                            if (mousePos2.isClicked()) {
                                mousePos2.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.ic.getIds()[0];
                                worldContainer.moveNumTemp = (short) (worldContainer.ic.getNums()[0] / 2);
                                if (worldContainer.ic.getIds()[0] == Items.EMPTY) {
                                    worldContainer.inventory.addLocationIC(worldContainer.ic, 0, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[0] != 1) {
                                    worldContainer.inventory.removeLocationIC(worldContainer.ic, 0, (short) (worldContainer.ic.getNums()[0] / 2));
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                } else if (worldContainer.moveItem == worldContainer.ic.getIds()[0] && worldContainer.ic.getNums()[0] < worldContainer.ic.getIds()[0].getMaxStacks()) {
                                    worldContainer.inventory.addLocationIC(worldContainer.ic, 0, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                }
                            }
                        }
                        if (mousePos.isInBetween(6, 46, worldContainer.inventory.image.getHeight() + 142, worldContainer.inventory.image.getHeight() + 182)) {
                            checkBlocks = false;
                            if (mousePos2.isClicked()) {
                                mousePos2.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.ic.getIds()[2];
                                worldContainer.moveNumTemp = (short) (worldContainer.ic.getNums()[2] / 2);
                                if (worldContainer.ic.getIds()[2] == Items.EMPTY) {
                                    worldContainer.inventory.addLocationIC(worldContainer.ic, 2, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[2] != 1) {
                                    worldContainer.inventory.removeLocationIC(worldContainer.ic, 2, (short) (worldContainer.ic.getNums()[2] / 2));
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                } else if (worldContainer.moveItem == worldContainer.ic.getIds()[2] && worldContainer.ic.getNums()[2] < worldContainer.ic.getIds()[2].getMaxStacks()) {
                                    worldContainer.inventory.addLocationIC(worldContainer.ic, 2, worldContainer.moveItem, (short) 1, moveDur);
                                    worldContainer.moveNum -= 1;
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                    }
                                }
                            }
                        }
                        if (mousePos.isInBetween(62, 102, worldContainer.inventory.image.getHeight() + 46, worldContainer.inventory.image.getHeight() + 86)) {
                            checkBlocks = false;
                            if (mousePos2.isClicked()) {
                                mousePos2.setReleased(true);
                                worldContainer.moveItemTemp = worldContainer.ic.getIds()[3];
                                worldContainer.moveNumTemp = (short) (worldContainer.ic.getNums()[3] / 2);
                                if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[3] != 1) {
                                    worldContainer.inventory.removeLocationIC(worldContainer.ic, 3, (short) (worldContainer.ic.getNums()[3] / 2));
                                    worldContainer.moveItem = worldContainer.moveItemTemp;
                                    worldContainer.moveNum = worldContainer.moveNumTemp;
                                }
                            }
                        }
                    }
                }
            }
            if (checkBlocks) {
                if (!(mousePos2.getX() < 0 || mousePos2.getX() >= WIDTH * WorldContainer.BLOCK_SIZE ||
                        mousePos2.getY() < 0 || mousePos2.getY() >= HEIGHT * WorldContainer.BLOCK_SIZE)) {
                    int ux = mousePos2.getX() / WorldContainer.BLOCK_SIZE;
                    int uy = mousePos2.getY() / WorldContainer.BLOCK_SIZE;
                    if (DebugContext.REACH || Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2)) <= 160) {
                        int ucx = ux - WorldContainer.CHUNK_BLOCKS * (ux / WorldContainer.CHUNK_BLOCKS);
                        int ucy = uy - WorldContainer.CHUNK_BLOCKS * (uy / WorldContainer.CHUNK_BLOCKS);
                        if (worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.WORKBENCH
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.WOODEN_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.STONE_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.COPPER_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.IRON_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.SILVER_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.GOLD_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE_ON
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.ZINC_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.RHYMESTONE_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.OBDURITE_CHEST) {
                            if (worldContainer.ic != null) {
                                if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                                    worldContainer.machinesx.add(worldContainer.icx);
                                    worldContainer.machinesy.add(worldContainer.icy);
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                                } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                                        for (int i = 0; i < 9; i++) {
                                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                            }
                                        }
                                    }
                                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                                        for (int i = 0; i < 9; i++) {
                                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                            }
                                        }
                                    }
                                }
                                if (worldContainer.ic.getType() == ItemType.FURNACE) {
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getSMELTP());
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFurnaceOn(worldContainer.ic.isFurnaceOn());
                                }
                                worldContainer.ic = null;
                            }
                            iclayer = worldContainer.layer;
                            for (int l = 0; l < LAYER_SIZE; l++) {
                                if (worldContainer.blocks[l][uy][ux] == Blocks.WORKBENCH) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.WORKBENCH) {
                                        worldContainer.ic = new ItemCollection(ItemType.WORKBENCH, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.WORKBENCH, 10);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.WOODEN_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.WOODEN_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.WOODEN_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.WOODEN_CHEST, 9);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.STONE_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.STONE_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.STONE_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.STONE_CHEST, 15);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.COPPER_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.COPPER_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.COPPER_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.COPPER_CHEST, 20);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.IRON_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.IRON_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.IRON_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.IRON_CHEST, 28);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.SILVER_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.SILVER_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.SILVER_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.SILVER_CHEST, 35);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.GOLD_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.GOLD_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.GOLD_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.GOLD_CHEST, 42);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.ZINC_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.ZINC_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.ZINC_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.ZINC_CHEST, 56);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.RHYMESTONE_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.RHYMESTONE_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.RHYMESTONE_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.RHYMESTONE_CHEST, 72);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.OBDURITE_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.OBDURITE_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.OBDURITE_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.OBDURITE_CHEST, 100);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == Blocks.FURNACE || worldContainer.blocks[l][uy][ux] == Blocks.FURNACE_ON) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.FURNACE) {
                                        worldContainer.ic = new ItemCollection(ItemType.FURNACE, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                        worldContainer.ic.setFUELP(worldContainer.icmatrix[l][uy][ux].getFUELP());
                                        worldContainer.ic.setSMELTP(worldContainer.icmatrix[l][uy][ux].getSMELTP());
                                        worldContainer.ic.setFurnaceOn(worldContainer.icmatrix[l][uy][ux].isFurnaceOn());
                                        worldContainer.icmatrix[l][uy][ux] = null;
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.FURNACE, 4);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                }
                                if (worldContainer.ic != null && worldContainer.blocks[l][uy][ux] != Blocks.WORKBENCH) {
                                    for (int i = worldContainer.machinesx.size() - 1; i > -1; i--) {
                                        if (worldContainer.machinesx.get(i) == worldContainer.icx && worldContainer.machinesy.get(i) == worldContainer.icy) {
                                            worldContainer.machinesx.remove(i);
                                            worldContainer.machinesy.remove(i);
                                        }
                                    }
                                }
                            }
                        }
                        if (worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.TREE) {
                            if (RandomTool.nextInt(2) == 0) {
                                worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 8 - 4, -3, Items.BARK, (short) 1));
                            }
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.TREE_NO_BARK;
                        }
                        if (mousePos2.isClicked()) {
                            mousePos2.setReleased(true);
                            worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isLever()) {
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnLeverOn(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                addBlockPower(ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                            } else if (worldContainer.blocks[worldContainer.layer][uy][ux].isLeverOn()) {
                                removeBlockPower(ux, uy, worldContainer.layer);
                                if (wcnct[uy][ux]) {
                                    for (int l = 0; l < LAYER_SIZE; l++) {
                                        if (l != worldContainer.layer) {
                                            rbpRecur(ux, uy, l);
                                        }
                                    }
                                }
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnLeverOff(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.rdrawn[uy][ux] = false;
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumWire()) {
                                wcnct[uy][ux] = !wcnct[uy][ux];
                                worldContainer.rdrawn[uy][ux] = false;
                                redoBlockPower(ux, uy, worldContainer.layer);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isCompleteZythiumAmplifier()) {
                                removeBlockPower(ux, uy, worldContainer.layer);
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnZythiumAmplifier(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                                addAdjacentTilesToPQueueConditionally(ux, uy);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumInverterAll() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumInverterOnAll()) {
                                removeBlockPower(ux, uy, worldContainer.layer);
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnZythiumInverter(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                                addAdjacentTilesToPQueueConditionally(ux, uy);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isButton()) {
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnButtonOn(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                addBlockPower(ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                                log.info("Srsly?");
                                updatex.add(ux);
                                updatey.add(uy);
                                updatet.add(50);
                                updatel.add(worldContainer.layer);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isCompleteZythiumDelayer()) {
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnZythiumDelayer(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                                redoBlockPower(ux, uy, worldContainer.layer);
                            }
                        }
                    }
                }
            }
            if (mousePos2.isReleased()) {
                mousePos2.setClicked(false);
            }
        } else {
            mousePos2.setClicked(true);
        }
        if (worldContainer.showTool) {
            worldContainer.toolAngle += worldContainer.toolSpeed;
            if (worldContainer.toolAngle >= 7.8) {
                worldContainer.toolAngle = 4.8;
                worldContainer.showTool = false;
            }
        }
        int vc = 0;
        if (!DebugContext.INVINCIBLE && worldContainer.player.y / 16 > HEIGHT + 10) {
            vc += 1;
            if (vc >= 1 / (Math.pow(1.001, worldContainer.player.y / 16 - HEIGHT - 10) - 1.0)) {
                worldContainer.player.damage(1, false, worldContainer.inventory);
                vc = 0;
            }
        } else {
            vc = 0;
        }
        for (int i = worldContainer.entities.size() - 1; i >= 0; i--) {
            if (worldContainer.entities.get(i).getNewMob() != null) {
                worldContainer.entities.add(worldContainer.entities.get(i).getNewMob());
            }
            if (worldContainer.entities.get(i).update(worldContainer.blocks[1], worldContainer.player, u, v)) {
                worldContainer.entities.remove(i);
            } else if (worldContainer.player.rect.intersects(worldContainer.entities.get(i).getRect())) {
                if (worldContainer.entities.get(i).getEntityType() != null) {
                    if (worldContainer.immune <= 0) {
                        if (!DebugContext.INVINCIBLE) {
                            worldContainer.player.damage(worldContainer.entities.get(i).getAttackPoints(), true, worldContainer.inventory);
                        }
                        worldContainer.regenerationCounter1 = 750;
                        worldContainer.immune = 40;
                        if (worldContainer.player.x + Player.WIDTH / 2 < worldContainer.entities.get(i).getX() + worldContainer.entities.get(i).getWidth() / 2) {
                            worldContainer.player.speedX -= 8;
                        } else {
                            worldContainer.player.speedX += 8;
                        }
                        worldContainer.player.speedY -= 3.5;
                    }
                } else if (worldContainer.entities.get(i).getMdelay() <= 0) {
                    int n = worldContainer.inventory.addItem(worldContainer.entities.get(i).getItem(), worldContainer.entities.get(i).getNum(), worldContainer.entities.get(i).getDur());
                    if (n != 0) {
                        worldContainer.entities.add(new Entity(worldContainer.entities.get(i).getX(), worldContainer.entities.get(i).getY(), worldContainer.entities.get(i).getVx(), worldContainer.entities.get(i).getVy(), worldContainer.entities.get(i).getItem(), (short) (worldContainer.entities.get(i).getNum() - n), worldContainer.entities.get(i).getDur()));
                    }
                    worldContainer.entities.remove(i);
                }
            }
        }
        if (worldContainer.player.healthPoints <= 0) {
            for (int j = 0; j < 40; j++) {
                if (worldContainer.inventory.items[j] != Items.EMPTY) {
                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -1, RandomTool.nextDouble() * 6 - 3, worldContainer.inventory.items[j], worldContainer.inventory.nums[j], worldContainer.inventory.durs[j]));
                    worldContainer.inventory.removeLocation(j, worldContainer.inventory.nums[j]);
                }
            }
            if (worldContainer.ic != null) {
                if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                    worldContainer.machinesx.add(worldContainer.icx);
                    worldContainer.machinesy.add(worldContainer.icy);
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic.getType(), worldContainer.ic.getIds(), worldContainer.ic.getNums(), worldContainer.ic.getDurs());
                } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        for (int i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        for (int i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                }
                if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setSMELTP(worldContainer.ic.getSMELTP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFurnaceOn(worldContainer.ic.isFurnaceOn());
                }
                worldContainer.ic = null;
            } else {
                if (worldContainer.showInv) {
                    for (int i = 0; i < 4; i++) {
                        if (worldContainer.cic.getIds()[i] != Items.EMPTY) {
                            if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                                worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, worldContainer.cic.getIds()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                            }
                            if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                                worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, worldContainer.cic.getIds()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                            }
                            worldContainer.inventory.removeLocationIC(worldContainer.cic, i, worldContainer.cic.getNums()[i]);
                        }
                    }
                }
                worldContainer.showInv = !worldContainer.showInv;
            }
            if (worldContainer.moveItem != Items.EMPTY) {
                if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, worldContainer.moveItem, worldContainer.moveNum, moveDur, 75));
                }
                if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, worldContainer.moveItem, worldContainer.moveNum, moveDur, 75));
                }
                worldContainer.moveItem = Items.EMPTY;
                worldContainer.moveNum = 0;
            }
            for (int i = 0; i < 4; i++) {
                if (armor.getIds()[i] != Items.EMPTY) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, armor.getIds()[i], armor.getNums()[i], armor.getDurs()[i], 75));
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, armor.getIds()[i], armor.getNums()[i], armor.getDurs()[i], 75));
                    }
                    worldContainer.inventory.removeLocationIC(armor, i, armor.getNums()[i]);
                }
            }
            worldContainer.player.x = WIDTH * 0.5 * WorldContainer.BLOCK_SIZE;
            worldContainer.player.y = 45;
            worldContainer.player.speedX = 0;
            worldContainer.player.speedY = 0;
            worldContainer.player.healthPoints = worldContainer.player.totalHealthPoints;
            tool = null;
            worldContainer.showTool = false;
        }
        if (worldContainer.showTool) {
            if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                tp1 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 + 6), (int) (worldContainer.player.y + Player.HEIGHT / 2));
                tp2 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 2 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 2 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 2 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 2 * Math.cos(worldContainer.toolAngle)));
                tp3 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 1 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 1 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 1 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 1 * Math.cos(worldContainer.toolAngle)));
                tp4 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 0.5 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 0.5 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 0.5 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 0.5 * Math.cos(worldContainer.toolAngle)));
                tp5 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 1.5 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 1.5 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 1.5 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 1.5 * Math.cos(worldContainer.toolAngle)));
            }
            if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                tp1 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 - 6), (int) (worldContainer.player.y + Player.HEIGHT / 2));
                tp2 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 2 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 2 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 2 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 2 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
                tp3 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 1 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 1 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 1 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 1 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
                tp4 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 0.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 0.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 0.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 0.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
                tp5 = new Point((int) (worldContainer.player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 1.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 1.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + Player.HEIGHT / 2 + tool.getWidth() * 1.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 1.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
            }
            for (int i = worldContainer.entities.size() - 1; i >= 0; i--) {
                if (worldContainer.entities.get(i).getEntityType() != null && !worldContainer.entities.get(i).isNohit() && worldContainer.showTool && (worldContainer.entities.get(i).getRect().contains(tp1) || worldContainer.entities.get(i).getRect().contains(tp2) || worldContainer.entities.get(i).getRect().contains(tp3) || worldContainer.entities.get(i).getRect().contains(tp4) || worldContainer.entities.get(i).getRect().contains(tp5)) && (!worldContainer.entities.get(i).getEntityType().equals("bee") || RandomTool.nextInt(4) == 0)) {
                    if (worldContainer.entities.get(i).hit(worldContainer.inventory.tool().getDamage(), worldContainer.player)) {
                        List<Items> dropList = worldContainer.entities.get(i).drops();
                        for (int j = 0; j < dropList.size(); j++) {
                            worldContainer.entities.add(new Entity(worldContainer.entities.get(i).getX(), worldContainer.entities.get(i).getY(), RandomTool.nextInt(4) - 2, -1, dropList.get(j), (short) 1));
                        }
                        worldContainer.entities.remove(i);
                    }
                    if (!Arrays.asList(TOOL_LIST).contains(worldContainer.inventory.items[worldContainer.inventory.selection])) {
                        worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                    } else {
                        worldContainer.inventory.durs[worldContainer.inventory.selection] -= 2;
                    }
                    if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                    }
                }
            }
        }

        int bx1, bx2, by1, by2;
        for (int i = -1; i < worldContainer.entities.size(); i++) {
            int width, height;
            if (i == -1) {
                width = Player.WIDTH;
                height = Player.HEIGHT;
                p = worldContainer.player.x;
                q = worldContainer.player.y;
            } else {
                width = worldContainer.entities.get(i).getWidth();
                height = worldContainer.entities.get(i).getHeight();
                p = worldContainer.entities.get(i).getX();
                q = worldContainer.entities.get(i).getY();
            }
            bx1 = (int) p / WorldContainer.BLOCK_SIZE;
            by1 = (int) q / WorldContainer.BLOCK_SIZE;
            bx2 = (int) (p + width) / WorldContainer.BLOCK_SIZE;
            by2 = (int) (q + height) / WorldContainer.BLOCK_SIZE;

            bx1 = Math.max(0, bx1);
            by1 = Math.max(0, by1);
            bx2 = Math.min(worldContainer.blocks[0].length - 1, bx2);
            by2 = Math.min(worldContainer.blocks.length - 1, by2);

            for (int x = bx1; x <= bx2; x++) {
                for (int y = by1; y <= by2; y++) {
                    if (worldContainer.blocks[worldContainer.layer][y][x].isCompletePressurePlate()
                            && (i == -1 || (worldContainer.blocks[worldContainer.layer][y][x].isStonePressurePlate() || worldContainer.blocks[worldContainer.layer][y][x].isWoodenPressurePlate())
                            && (x != -1 && worldContainer.entities.get(i).getEntityType() != null || worldContainer.blocks[worldContainer.layer][y][x].isWoodenPressurePlate()))) {
                        if (worldContainer.blocks[worldContainer.layer][y][x].isPressurePlate()) {
                            worldContainer.blocks[worldContainer.layer][y][x] = Blocks.turnPressurePlateOn(worldContainer.blocks[worldContainer.layer][y][x]);
                            worldContainer.rdrawn[y][x] = false;
                            addBlockPower(x, y);
                            log.info("Srsly?");
                            updatex.add(x);
                            updatey.add(y);
                            updatet.add(0);
                            updatel.add(0);
                        }
                    }
                }
            }
        }

        resolvePowerMatrix();
        resolveLightMatrix();
        worldContainer.immune -= 1;
    }

    public void breakCurrentBlock(int ux, int uy) {
        if (DebugContext.INSTAMINE || worldContainer.mining >= DURABILITY.get(worldContainer.inventory.tool()).get(worldContainer.blocks[worldContainer.layer][uy][ux])) {
            if (worldContainer.blocks[0][uy][ux] == Blocks.TREE_ROOT) {
                worldContainer.blocks[0][uy][ux] = Blocks.AIR;
                for (int uly = uy - 1; uly < uy + 2; uly++) {
                    for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.blocksDirectionsIntensity[uly][ulx] = (byte) RandomTool.nextInt(5);
                    }
                }
                for (int uly = uy; uly < uy + 3; uly++) {
                    for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.drawn[uly][ulx] = false;
                    }
                }
            }
            if (worldContainer.blocks[0][uy + 1][ux] == Blocks.TREE_ROOT) {
                worldContainer.blocks[0][uy + 1][ux] = Blocks.AIR;
                for (int uly = uy; uly < uy + 3; uly++) {
                    for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.blocksDirectionsIntensity[uly][ulx] = (byte) RandomTool.nextInt(5);
                    }
                }
                for (int uly = uy; uly < uy + 3; uly++) {
                    for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.drawn[uly][ulx] = false;
                    }
                }
            }
            if (worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.WORKBENCH
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.WOODEN_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.STONE_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.COPPER_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.IRON_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.SILVER_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.GOLD_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE_ON
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.ZINC_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.RHYMESTONE_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.OBDURITE_CHEST) {
                if (worldContainer.ic != null) {
                    for (int i = 0; i < worldContainer.ic.getIds().length; i++) {
                        if (worldContainer.ic.getIds()[i] != Items.EMPTY && !(worldContainer.ic.getType() == ItemType.FURNACE && i == 1)) {
                            worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i]));
                        }
                    }
                }
                if (worldContainer.icmatrix[worldContainer.layer][uy][ux] != null) {
                    for (int i = 0; i < worldContainer.icmatrix[worldContainer.layer][uy][ux].getIds().length; i++) {
                        if (worldContainer.icmatrix[worldContainer.layer][uy][ux].getIds()[i] != Items.EMPTY && !(worldContainer.icmatrix[worldContainer.layer][uy][ux].getType() == ItemType.FURNACE && i == 1)) {
                            worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.icmatrix[worldContainer.layer][uy][ux].getIds()[i], worldContainer.icmatrix[worldContainer.layer][uy][ux].getNums()[i], worldContainer.icmatrix[worldContainer.layer][uy][ux].getDurs()[i]));
                        }
                    }
                    worldContainer.icmatrix[worldContainer.layer][uy][ux] = null;
                }
                worldContainer.ic = null;
                for (int i = 0; i < worldContainer.machinesx.size(); i++) {
                    if (worldContainer.machinesx.get(i) == ux && worldContainer.machinesy.get(i) == uy) {
                        worldContainer.machinesx.remove(i);
                        worldContainer.machinesy.remove(i);
                        break;
                    }
                }
            }
            if (worldContainer.blocks[worldContainer.layer][uy][ux] != Blocks.AIR && worldContainer.blocks[worldContainer.layer][uy][ux].getDrops() != Items.EMPTY) {
                worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux].getDrops(), (short) 1));
            }
            Items t = Items.EMPTY;
            int n = 0;
            switch (worldContainer.blocks[worldContainer.layer][uy][ux]) {
                case SUNFLOWER_STAGE_1:
                    t = Items.SUNFLOWER_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case SUNFLOWER_STAGE_2:
                    t = Items.SUNFLOWER_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case SUNFLOWER_STAGE_3:
                    t = Items.SUNFLOWER_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case MOONFLOWER_STAGE_1:
                    t = Items.MOONFLOWER_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case MOONFLOWER_STAGE_2:
                    t = Items.MOONFLOWER_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case MOONFLOWER_STAGE_3:
                    t = Items.MOONFLOWER_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case DRYWEED_STAGE_1:
                    t = Items.DRYWEED_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case DRYWEED_STAGE_2:
                    t = Items.DRYWEED_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case DRYWEED_STAGE_3:
                    t = Items.DRYWEED_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case GREENLEAF_STAGE_1:
                    t = Items.GREENLEAF_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case GREENLEAF_STAGE_2:
                    t = Items.GREENLEAF_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case GREENLEAF_STAGE_3:
                    t = Items.GREENLEAF_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case FROSTLEAF_STAGE_1:
                    t = Items.FROSTLEAF_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case FROSTLEAF_STAGE_2:
                    t = Items.FROSTLEAF_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case FROSTLEAF_STAGE_3:
                    t = Items.FROSTLEAF_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case CAVEROOT_STAGE_1:
                    t = Items.CAVEROOT_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case CAVEROOT_STAGE_2:
                    t = Items.CAVEROOT_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case CAVEROOT_STAGE_3:
                    t = Items.CAVEROOT_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case SKYBLOSSOM_STAGE_1:
                    t = Items.SKYBLOSSOM_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case SKYBLOSSOM_STAGE_2:
                    t = Items.SKYBLOSSOM_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case SKYBLOSSOM_STAGE_3:
                    t = Items.SKYBLOSSOM_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case VOID_ROT_STAGE_1:
                    t = Items.VOID_ROT_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case VOID_ROT_STAGE_2:
                    t = Items.VOID_ROT_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case VOID_ROT_STAGE_3:
                    t = Items.VOID_ROT_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                case MARSHLEAF_STAGE_1:
                    t = Items.MARSHLEAF_SEEDS;
                    n = RandomTool.nextInt(4) - 2;
                    break;
                case MARSHLEAF_STAGE_2:
                    t = Items.MARSHLEAF_SEEDS;
                    n = RandomTool.nextInt(2);
                    break;
                case MARSHLEAF_STAGE_3:
                    t = Items.MARSHLEAF_SEEDS;
                    n = RandomTool.nextInt(3) + 1;
                    break;
                default:
                    break;
            }
            if (t != Items.EMPTY) {
                for (int i = 0; i < Math.max(1, n); i++) {
                    worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, t, (short) 1));
                }
            }
            removeBlockLighting(ux, uy);
            worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.AIR;
            if (worldContainer.blockTemp.isZythiumWire()) {
                redoBlockPower(ux, uy, worldContainer.layer);
            }
            if (worldContainer.blockTemp.isPower()) {
                removeBlockPower(ux, uy, worldContainer.layer);
            }
            if (worldContainer.blockTemp.isLTrans()) {
                addSunLighting(ux, uy);
                redoBlockLighting(ux, uy);
            }
            addSunLighting(ux, uy);
            worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
            for (int uly = uy - 1; uly < uy + 2; uly++) {
                for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                    worldContainer.blocksDirectionsIntensity[uly][ulx] = (byte) RandomTool.nextInt(5);
                }
            }
            for (int uly = uy - 1; uly < uy + 2; uly++) {
                for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                    worldContainer.drawn[uly][ulx] = false;
                }
            }
            for (int uly = uy - 4; uly < uy + 5; uly++) {
                for (int ulx = ux - 4; ulx < ux + 5; ulx++) {
                    for (int l = 0; l < LAYER_SIZE; l += 2) {
                        if (uly >= 0 && uly < HEIGHT && worldContainer.blocks[l][uly][ulx] == Blocks.LEAVES) {
                            boolean keepLeaf = false;
                            for (int uly2 = uly - 4; uly2 < uly + 5; uly2++) {
                                for (int ulx2 = ulx - 4; ulx2 < ulx + 5; ulx2++) {
                                    if (uly2 >= 0 && uly2 < HEIGHT && (worldContainer.blocks[1][uly2][ulx2] == Blocks.TREE || worldContainer.blocks[1][uly2][ulx2] == Blocks.TREE_NO_BARK)) {
                                        keepLeaf = true;
                                        break;
                                    }
                                }
                                if (keepLeaf) {
                                    break;
                                }
                            }
                            if (!keepLeaf) {
                                worldContainer.blocks[l][uly][ulx] = Blocks.AIR;
                                worldContainer.blocksDirections[l] = OutlinesService.generateOutlines(worldContainer.blocks[l], worldContainer.blocksDirections[l], ulx, uly);
                                for (int uly2 = uly - 1; uly2 < uly + 2; uly2++) {
                                    for (int ulx2 = ulx - 1; ulx2 < ulx + 2; ulx2++) {
                                        worldContainer.drawn[uly2][ulx2] = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            while (true) {
                if (TORCHESR.get(worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops().getBlocks()) != null && TORCHESR.get(worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops().getBlocks()) == worldContainer.blocks[worldContainer.layer][uy][ux - 1] || (worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops() == Items.LEVER || worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops() == Items.BUTTON)) {
                    worldContainer.entities.add(new Entity((ux - 1) * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops(), (short) 1));
                    removeBlockLighting(ux - 1, uy);
                    if (worldContainer.layer == 1) {
                        addSunLighting(ux - 1, uy);
                    }
                    worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux - 1];
                    worldContainer.blocks[worldContainer.layer][uy][ux - 1] = Blocks.AIR;
                    if (worldContainer.blockTemp.isZythiumWire()) {
                        redoBlockPower(ux, uy, worldContainer.layer);
                    }
                    if (worldContainer.blockTemp.isPower()) {
                        removeBlockPower(ux, uy, worldContainer.layer);
                    }
                    worldContainer.drawn[uy][ux - 1] = false;
                }
                if (TORCHESL.get(worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops().getBlocks()) != null && TORCHESL.get(worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops().getBlocks()) == worldContainer.blocks[worldContainer.layer][uy][ux + 1] || (worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops() == Items.LEVER || worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops() == Items.BUTTON)) {
                    worldContainer.entities.add(new Entity((ux + 1) * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops(), (short) 1));
                    removeBlockLighting(ux + 1, uy);
                    if (worldContainer.layer == 1) {
                        addSunLighting(ux + 1, uy);
                    }
                    worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux + 1];
                    worldContainer.blocks[worldContainer.layer][uy][ux + 1] = Blocks.AIR;
                    if (worldContainer.blockTemp.isZythiumWire()) {
                        redoBlockPower(ux, uy, worldContainer.layer);
                    }
                    if (worldContainer.blockTemp.isPower()) {
                        removeBlockPower(ux, uy, worldContainer.layer);
                    }
                    worldContainer.drawn[uy][ux + 1] = false;
                }
                uy -= 1;
                if (uy == -1 || !worldContainer.blocks[worldContainer.layer][uy][ux].isGSupport()) {
                    addSunLighting(ux, uy);
                    break;
                }
                if (worldContainer.blocks[worldContainer.layer][uy][ux].getDrops() != Items.EMPTY) {
                    worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux].getDrops(), (short) 1));
                }
                t = Items.EMPTY;
                n = 0;
                switch (worldContainer.blocks[worldContainer.layer][uy][ux]) {
                    case SUNFLOWER_STAGE_1:
                        t = Items.SUNFLOWER_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case SUNFLOWER_STAGE_2:
                        t = Items.SUNFLOWER_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case SUNFLOWER_STAGE_3:
                        t = Items.SUNFLOWER_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case MOONFLOWER_STAGE_1:
                        t = Items.MOONFLOWER_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case MOONFLOWER_STAGE_2:
                        t = Items.MOONFLOWER_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case MOONFLOWER_STAGE_3:
                        t = Items.MOONFLOWER_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case DRYWEED_STAGE_1:
                        t = Items.DRYWEED_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case DRYWEED_STAGE_2:
                        t = Items.DRYWEED_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case DRYWEED_STAGE_3:
                        t = Items.DRYWEED_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case GREENLEAF_STAGE_1:
                        t = Items.GREENLEAF_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case GREENLEAF_STAGE_2:
                        t = Items.GREENLEAF_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case GREENLEAF_STAGE_3:
                        t = Items.GREENLEAF_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case FROSTLEAF_STAGE_1:
                        t = Items.FROSTLEAF_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case FROSTLEAF_STAGE_2:
                        t = Items.FROSTLEAF_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case FROSTLEAF_STAGE_3:
                        t = Items.FROSTLEAF_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case CAVEROOT_STAGE_1:
                        t = Items.CAVEROOT_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case CAVEROOT_STAGE_2:
                        t = Items.CAVEROOT_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case CAVEROOT_STAGE_3:
                        t = Items.CAVEROOT_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case SKYBLOSSOM_STAGE_1:
                        t = Items.SKYBLOSSOM_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case SKYBLOSSOM_STAGE_2:
                        t = Items.SKYBLOSSOM_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case SKYBLOSSOM_STAGE_3:
                        t = Items.SKYBLOSSOM_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case VOID_ROT_STAGE_1:
                        t = Items.VOID_ROT_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case VOID_ROT_STAGE_2:
                        t = Items.VOID_ROT_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case VOID_ROT_STAGE_3:
                        t = Items.VOID_ROT_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    case MARSHLEAF_STAGE_1:
                        t = Items.MARSHLEAF_SEEDS;
                        n = RandomTool.nextInt(4) - 2;
                        break;
                    case MARSHLEAF_STAGE_2:
                        t = Items.MARSHLEAF_SEEDS;
                        n = RandomTool.nextInt(2);
                        break;
                    case MARSHLEAF_STAGE_3:
                        t = Items.MARSHLEAF_SEEDS;
                        n = RandomTool.nextInt(3) + 1;
                        break;
                    default:
                        break;
                }
                if (t != Items.EMPTY) {
                    for (int i = 0; i < Math.max(1, n); i++) {
                        worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, t, (short) 1));
                    }
                }
                removeBlockLighting(ux, uy);
                worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.AIR;
                if (worldContainer.blockTemp.isZythiumWire()) {
                    redoBlockPower(ux, uy, worldContainer.layer);
                }
                if (worldContainer.blockTemp.isPower()) {
                    removeBlockPower(ux, uy, worldContainer.layer);
                }
                if (worldContainer.blockTemp.isLTrans()) {
                    addSunLighting(ux, uy);
                    redoBlockLighting(ux, uy);
                }
                addSunLighting(ux, uy);
                worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                for (int uly = uy - 1; uly < uy + 2; uly++) {
                    for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.blocksDirectionsIntensity[uly][ulx] = (byte) RandomTool.nextInt(5);
                    }
                }
                for (int uly = uy - 1; uly < uy + 2; uly++) {
                    for (int ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.drawn[uly][ulx] = false;
                    }
                }
                for (int uly = uy - 4; uly < uy + 5; uly++) {
                    for (int ulx = ux - 4; ulx < ux + 5; ulx++) {
                        for (int l = 0; l < LAYER_SIZE; l += 2) {
                            if (uly >= 0 && uly < HEIGHT && worldContainer.blocks[l][uly][ulx] == Blocks.LEAVES) {
                                boolean keepLeaf = false;
                                for (int uly2 = uly - 4; uly2 < uly + 5; uly2++) {
                                    for (int ulx2 = ulx - 4; ulx2 < ulx + 5; ulx2++) {
                                        if (uly2 >= 0 && uly2 < HEIGHT && (worldContainer.blocks[1][uly2][ulx2] == Blocks.TREE || worldContainer.blocks[1][uly2][ulx2] == Blocks.TREE_NO_BARK)) {
                                            keepLeaf = true;
                                            break;
                                        }
                                    }
                                    if (keepLeaf) {
                                        break;
                                    }
                                }
                                if (!keepLeaf) {
                                    worldContainer.blocks[l][uly][ulx] = Blocks.AIR;
                                    worldContainer.blocksDirections[l] = OutlinesService.generateOutlines(worldContainer.blocks[l], worldContainer.blocksDirections[l], ulx, uly);
                                    for (int uly2 = uly - 1; uly2 < uly + 2; uly2++) {
                                        for (int ulx2 = ulx - 1; ulx2 < ulx + 2; ulx2++) {
                                            worldContainer.drawn[uly2][ulx2] = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            worldContainer.mining = 0;
        }
    }

    public void updateEnvironment() {
        worldContainer.timeOfDay += 1.2 * DebugContext.ACCEL;
        for (int i = worldContainer.cloudsAggregate.getClouds().size() - 1; i > -1; i--) {
            worldContainer.cloudsAggregate.updateXUponSpeed(i);
            if (worldContainer.cloudsAggregate.isOutsideBounds(i, getWidth())) {
                worldContainer.cloudsAggregate.removeCloud(i);
            }
        }
        if (RandomTool.nextInt((int) (1500 / DebugContext.ACCEL)) == 0) {

            Cloud cloud = new Cloud();
            cloud.setN(RandomTool.nextInt(1));
            BufferedImage cloudImage = cloudsImages[cloud.getN()];
            if (RandomTool.nextInt(2) == 0) {
                cloud.setX(-cloudImage.getWidth() * 2);
                cloud.setSpeed(0.1 * DebugContext.ACCEL);
            } else {
                cloud.setX(getWidth());
                cloud.setSpeed(-0.1 * DebugContext.ACCEL);
            }
            cloud.setY(RandomTool.nextDouble() * (getHeight() - cloudImage.getHeight()) + cloudImage.getHeight());
            worldContainer.cloudsAggregate.getClouds().add(cloud);
        }
    }

    public void addBlockLighting(int ux, int uy) {
        int n = findNonLayeredBlockLightSource(ux, uy);
        if (n != 0) {
            addTileToZQueue(ux, uy);
            worldContainer.lights[uy][ux] = Math.max(worldContainer.lights[uy][ux], n);
            worldContainer.lsources[uy][ux] = true;
            addTileToQueue(ux, uy);
        }
    }

    public void addBlockPower(int ux, int uy) {
        if (worldContainer.blocks[1][uy][ux].isPower()) {
            if (worldContainer.blocks[1][uy][ux].isCompleteZythiumDelayer()) {
                log.info("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(DDELAY.get(worldContainer.blocks[1][uy][ux]));
                updatel.add(1);
            } else {
                addTileToPZQueue(ux, uy);
                worldContainer.power[1][uy][ux] = (float) 5;
                if (worldContainer.blocks[1][uy][ux].getConduct() >= 0 && wcnct[uy][ux]) {
                    if (worldContainer.blocks[0][uy][ux].isReceive()) {
                        worldContainer.power[0][uy][ux] = worldContainer.power[1][uy][ux] - (float) worldContainer.blocks[1][uy][ux].getConduct();
                    }
                    if (worldContainer.blocks[2][uy][ux].isReceive()) {
                        worldContainer.power[2][uy][ux] = worldContainer.power[1][uy][ux] - (float) worldContainer.blocks[1][uy][ux].getConduct();
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
        if (worldContainer.blocks[0][uy][ux].isPower()) {
            if (worldContainer.blocks[1][uy][ux].isCompleteZythiumDelayer()) {
                log.info("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(DDELAY.get(worldContainer.blocks[0][uy][ux]));
                updatel.add(0);
            } else {
                addTileToPZQueue(ux, uy);
                worldContainer.power[0][uy][ux] = (float) 5;
                if (worldContainer.blocks[0][uy][ux].getConduct() >= 0 && wcnct[uy][ux]) {
                    if (worldContainer.blocks[1][uy][ux].isReceive()) {
                        worldContainer.power[1][uy][ux] = worldContainer.power[0][uy][ux] - (float) worldContainer.blocks[0][uy][ux].getConduct();
                    }
                    if (worldContainer.blocks[2][uy][ux].isReceive()) {
                        worldContainer.power[2][uy][ux] = worldContainer.power[0][uy][ux] - (float) worldContainer.blocks[0][uy][ux].getConduct();
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
        if (worldContainer.blocks[2][uy][ux].isPower()) {
            if (worldContainer.blocks[1][uy][ux].isCompleteZythiumDelayer()) {
                log.info("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(DDELAY.get(worldContainer.blocks[2][uy][ux]));
                updatel.add(2);
            } else {
                addTileToPZQueue(ux, uy);
                worldContainer.power[2][uy][ux] = (float) 5;
                if (worldContainer.blocks[2][uy][ux].getConduct() >= 0 && wcnct[uy][ux]) {
                    if (worldContainer.blocks[0][uy][ux].isReceive()) {
                        worldContainer.power[0][uy][ux] = worldContainer.power[2][uy][ux] - (float) worldContainer.blocks[2][uy][ux].getConduct();
                    }
                    if (worldContainer.blocks[1][uy][ux].isReceive()) {
                        worldContainer.power[1][uy][ux] = worldContainer.power[2][uy][ux] - (float) worldContainer.blocks[2][uy][ux].getConduct();
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
    }

    public void removeBlockLighting(int ux, int uy) {
        removeBlockLighting(ux, uy, worldContainer.layer);
    }

    public void removeBlockLighting(int ux, int uy, int layer) {
        int n = findNonLayeredBlockLightSource(ux, uy);
        if (n != 0) {
            worldContainer.lsources[uy][ux] = isNonLayeredBlockLightSource(ux, uy, layer);
            for (int axl = -n; axl < n + 1; axl++) {
                for (int ayl = -n; ayl < n + 1; ayl++) {
                    if (Math.abs(axl) + Math.abs(ayl) <= n && uy + ayl >= 0 && uy + ayl < HEIGHT && worldContainer.lights[uy + ayl][ux + axl] != 0) {
                        addTileToZQueue(ux + axl, uy + ayl);
                        worldContainer.lights[uy + ayl][ux + axl] = (float) 0;
                    }
                }
            }
            for (int axl = -n - BRIGHTEST; axl < n + 1 + BRIGHTEST; axl++) {
                for (int ayl = -n - BRIGHTEST; ayl < n + 1 + BRIGHTEST; ayl++) {
                    if (Math.abs(axl) + Math.abs(ayl) <= n + BRIGHTEST && uy + ayl >= 0 && uy + ayl < HEIGHT) {
                        if (worldContainer.lsources[uy + ayl][ux + axl]) {
                            addTileToQueue(ux + axl, uy + ayl);
                        }
                    }
                }
            }
        }
    }

    public void rbpRecur(int ux, int uy, int lyr) {
        arbprd[lyr][uy][ux] = true;
        log.info("[rbpR] " + ux + " " + uy);
        addTileToPZQueue(ux, uy);
        boolean[] remember = { false, false, false, false };
        int ax3, ay3;
        for (int ir = 0; ir < 4; ir++) {
            ax3 = ux + cl[ir][0];
            ay3 = uy + cl[ir][1];
            if (ay3 >= 0 && ay3 < HEIGHT && worldContainer.power[lyr][ay3][ax3] != 0) {
                if (worldContainer.power[lyr][ay3][ax3] != 0 && !(worldContainer.power[lyr][ay3][ax3] == worldContainer.power[lyr][uy][ux] - worldContainer.blocks[lyr][uy][ux].getConduct()) &&
                        (!(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter()) ||
                                !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
                    log.info("Added tile " + ax3 + " " + ay3 + " to PQueue.");
                    addTileToPQueue(ax3, ay3);
                    remember[ir] = true;
                }
            }
        }
        for (int ir = 0; ir < 4; ir++) {
            log.info("[liek srsly man?] " + ir);
            ax3 = ux + cl[ir][0];
            ay3 = uy + cl[ir][1];
            log.info("[rpbRecur2] " + ax3 + " " + ay3 + " " + worldContainer.power[lyr][ay3][ax3]);
            if (ay3 >= 0 && ay3 < HEIGHT && worldContainer.power[lyr][ay3][ax3] != 0) {
                log.info("[rbpRecur] " + worldContainer.power[lyr][ay3][ax3] + " " + worldContainer.power[lyr][uy][ux] + " " + worldContainer.blocks[lyr][uy][ux].getConduct());
                if ((worldContainer.power[lyr][ay3][ax3] == worldContainer.power[lyr][uy][ux] - worldContainer.blocks[lyr][uy][ux].getConduct()) &&
                        (!(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier()) ||
                                !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
                    if (!arbprd[lyr][ay3][ax3]) {
                        rbpRecur(ax3, ay3, lyr);
                        if (worldContainer.blocks[lyr][ay3][ax3].getConduct() >= 0 && wcnct[ay3][ax3]) {
                            if (lyr == 0) {
                                if (worldContainer.blocks[1][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 1);
                                    if (worldContainer.blocks[1][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (worldContainer.blocks[2][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 2);
                                    if (worldContainer.blocks[2][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            } else if (lyr == 1) {
                                if (worldContainer.blocks[0][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 0);
                                    if (worldContainer.blocks[0][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (worldContainer.blocks[2][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 2);
                                    if (worldContainer.blocks[2][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            } else if (lyr == 2) {
                                if (worldContainer.blocks[0][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 0);
                                    if (worldContainer.blocks[0][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (worldContainer.blocks[1][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 1);
                                    if (worldContainer.blocks[1][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (worldContainer.blocks[lyr][ay3][ax3] == Blocks.ZYTHIUM_LAMP_ON || (worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer()) &&
                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                if (worldContainer.blocks[lyr][ay3][ax3].isZythiumInverterOnAll()) {
                    worldContainer.blocks[lyr][ay3][ax3] = Blocks.turnZythiumInverterOff(worldContainer.blocks[lyr][ay3][ax3]);
                    log.info("Adding power for inverter at (" + ax3 + ", " + ay3 + ").");
                    addBlockPower(ax3, ay3);
                    addBlockLighting(ax3, ay3);
                    worldContainer.rdrawn[ay3][ax3] = false;
                }
                removeBlockPower(ax3, ay3, lyr);
            }
        }
        for (int ir = 0; ir < 4; ir++) {
            if (remember[ir] && uy + cl[ir][1] >= 0 && uy + cl[ir][1] < HEIGHT) {
                worldContainer.power[lyr][uy + cl[ir][1]][ux + cl[ir][0]] = (float) 5;
            }
        }
        worldContainer.power[lyr][uy][ux] = (float) 0;
        arbprd[lyr][uy][ux] = false;
    }

    public void removeBlockPower(int ux, int uy, int lyr) {
        removeBlockPower(ux, uy, lyr, true);
    }

    public void removeBlockPower(int ux, int uy, int lyr, boolean turnOffDelayer) {
        arbprd[lyr][uy][ux] = true;
        log.info("[rbp ] " + ux + " " + uy + " " + lyr + " " + turnOffDelayer);
        if (!(worldContainer.blocks[lyr][uy][ux].isZythiumDelayerOnAll() && turnOffDelayer)) {
            int ax3, ay3;
            for (int ir = 0; ir < 4; ir++) {
                ax3 = ux + cl[ir][0];
                ay3 = uy + cl[ir][1];
                if (ay3 >= 0 && ay3 < HEIGHT && worldContainer.power[lyr][ay3][ax3] != 0) {
                    if (!(worldContainer.power[lyr][ay3][ax3] == worldContainer.power[lyr][uy][ux] - worldContainer.blocks[lyr][uy][ux].getConduct()) &&
                            (!(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter()) ||
                                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                            !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                            !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
                        log.info("Added tile " + ax3 + " " + ay3 + " to PQueue.");
                        addTileToPQueue(ax3, ay3);
                    }
                }
            }
            for (int ir = 0; ir < 4; ir++) {
                ax3 = ux + cl[ir][0];
                ay3 = uy + cl[ir][1];
                log.info(worldContainer.blocks[lyr][ay3][ax3] + " " + worldContainer.power[lyr][ay3][ax3]);
                if (ay3 >= 0 && ay3 < HEIGHT && worldContainer.power[lyr][ay3][ax3] != 0) {
                    log.info(worldContainer.power[lyr][uy][ux] + " " + worldContainer.power[lyr][ay3][ax3] + " " + worldContainer.blocks[lyr][uy][ux].getConduct());
                    if (worldContainer.power[lyr][ay3][ax3] == worldContainer.power[lyr][uy][ux] - worldContainer.blocks[lyr][uy][ux].getConduct()) {
                        if (!(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter()) ||
                                !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                            if (!arbprd[lyr][ay3][ax3]) {
                                rbpRecur(ax3, ay3, lyr);
                                if (worldContainer.blocks[lyr][ay3][ax3].getConduct() >= 0 && wcnct[ay3][ax3]) {
                                    if (lyr == 0) {
                                        if (worldContainer.blocks[1][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 1);
                                            if (worldContainer.blocks[1][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (worldContainer.blocks[2][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 2);
                                            if (worldContainer.blocks[2][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                    if (lyr == 1) {
                                        if (worldContainer.blocks[0][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 0);
                                            if (worldContainer.blocks[0][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (worldContainer.blocks[2][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 2);
                                            if (worldContainer.blocks[2][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                    if (lyr == 2) {
                                        if (worldContainer.blocks[0][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 0);
                                            if (worldContainer.blocks[0][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (worldContainer.blocks[1][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 1);
                                            if (worldContainer.blocks[1][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (worldContainer.blocks[lyr][ay3][ax3] == Blocks.ZYTHIUM_LAMP_ON || (worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer()) &&
                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                    if (worldContainer.blocks[lyr][ay3][ax3].isZythiumInverterOnAll()) {
                        worldContainer.blocks[lyr][ay3][ax3] = Blocks.turnZythiumInverterOff(worldContainer.blocks[lyr][ay3][ax3]);
                        log.info("Adding power for inverter at (" + ax3 + ", " + ay3 + ").");
                        addBlockPower(ax3, ay3);
                        addBlockLighting(ax3, ay3);
                        worldContainer.rdrawn[ay3][ax3] = false;
                    }
                    arbprd[lyr][uy][ux] = false;
                    removeBlockPower(ax3, ay3, lyr);
                }
            }
        }
        if (worldContainer.blocks[lyr][uy][ux] == Blocks.ZYTHIUM_LAMP_ON) {
            removeBlockLighting(ux, uy);
            worldContainer.blocks[lyr][uy][ux] = Blocks.ZYTHIUM_LAMP;
            worldContainer.rdrawn[uy][ux] = false;
        }
        if (worldContainer.blocks[lyr][uy][ux].isZythiumAmplifierOnAll()) {
            worldContainer.blockTemp = worldContainer.blocks[lyr][uy][ux];
            worldContainer.blocks[lyr][uy][ux] = Blocks.turnZythiumAmplifierOff(worldContainer.blocks[lyr][uy][ux]);
            removeBlockPower(ux, uy, lyr);
            removeBlockLighting(ux, uy);
            worldContainer.rdrawn[uy][ux] = false;
        }
        if (turnOffDelayer && worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer()) {
            log.info("???");
            updatex.add(ux);
            updatey.add(uy);
            updatet.add(DDELAY.get(worldContainer.blocks[lyr][uy][ux]));
            updatel.add(lyr);
        }
        if (!(worldContainer.blocks[lyr][uy][ux].isZythiumDelayerOnAll() && turnOffDelayer)) {
            worldContainer.power[lyr][uy][ux] = (float) 0;
        }
        arbprd[lyr][uy][ux] = false;
    }

    public void redoBlockLighting(int ux, int uy) {
        for (int ax = -BRIGHTEST; ax < BRIGHTEST + 1; ax++) {
            for (int ay = -BRIGHTEST; ay < BRIGHTEST + 1; ay++) {
                if (Math.abs(ax) + Math.abs(ay) <= BRIGHTEST && uy + ay >= 0 && uy + ay < HEIGHT) {
                    addTileToZQueue(ux + ax, uy + ay);
                    worldContainer.lights[uy + ay][ux + ax] = (float) 0;
                }
            }
        }
        for (int ax = -BRIGHTEST * 2; ax < BRIGHTEST * 2 + 1; ax++) {
            for (int ay = -BRIGHTEST * 2; ay < BRIGHTEST * 2 + 1; ay++) {
                if (Math.abs(ax) + Math.abs(ay) <= BRIGHTEST * 2 && uy + ay >= 0 && uy + ay < HEIGHT) {
                    if (worldContainer.lsources[uy + ay][ux + ax]) {
                        addTileToQueue(ux + ax, uy + ay);
                    }
                }
            }
        }
    }

    public void redoBlockPower(int ux, int uy, int lyr) {
        if (worldContainer.blocks[lyr][uy][ux].isPower() || worldContainer.blocks[lyr][uy][ux].isZythiumWire()) {
            addAdjacentTilesToPQueue(ux, uy);
        } else {
            removeBlockPower(ux, uy, lyr);
        }
    }

    public void addSunLighting(int ux, int uy) { // And including
        for (int y = 0; y < uy; y++) {
            if (worldContainer.blocks[1][y][ux].isLTrans()) {
                return;
            }
        }
        boolean addSources = false;
        for (int y = uy; y < HEIGHT - 1; y++) {
            if (worldContainer.blocks[1][y + 1][ux - 1].isLTrans() || worldContainer.blocks[1][y + 1][ux + 1].isLTrans()) {
                addSources = true;
            }
            if (addSources) {
                addTileToQueue(ux, y);
            }
            if (worldContainer.blocks[1][y][ux].isLTrans()) {
                return;
            }
            addTileToZQueue(ux, y);
            worldContainer.lights[y][ux] = (float) sunlightlevel;
            worldContainer.lsources[y][ux] = true;
        }
    }

    public void removeSunLighting(int ux, int uy) { // And including
        int n = sunlightlevel;
        for (int y = 0; y < uy; y++) {
            if (worldContainer.blocks[1][y][ux].isLTrans()) {
                return;
            }
        }
        int y;
        for (y = uy; y < HEIGHT; y++) {
            worldContainer.lsources[y][ux] = isBlockLightSource(ux, y);
            if (y != uy && worldContainer.blocks[1][y][ux].isLTrans()) {
                break;
            }
        }
        for (int ax = -n; ax < n + 1; ax++) {
            for (int ay = -n; ay < n + (y - uy) + 1; ay++) {
                if (uy + ay >= 0 && uy + ay < WIDTH) {
                    addTileToZQueue(ux + ax, uy + ay);
                    worldContainer.lights[uy + ay][ux + ax] = (float) 0;
                }
            }
        }
        for (int ax = -n - BRIGHTEST; ax < n + 1 + BRIGHTEST; ax++) {
            for (int ay = -n - BRIGHTEST; ay < n + (y - uy) + 1 + BRIGHTEST; ay++) {
                if (uy + ay >= 0 && uy + ay < HEIGHT) {
                    if (worldContainer.lsources[uy + ay][ux + ax]) {
                        addTileToQueue(ux + ax, uy + ay);
                    }
                }
            }
        }
    }

    public boolean isReachedBySunlight(int ux, int uy) {
        for (int ay = 0; ay < uy + 1; ay++) {
            if (worldContainer.blocks[1][ay][ux].isLTrans()) {
                return false;
            }
        }
        return true;
    }

    public boolean isBlockLightSource(int ux, int uy) {
        return (worldContainer.blocks[0][uy][ux] != Blocks.AIR && worldContainer.blocks[0][uy][ux].getLights() != 0 ||
                worldContainer.blocks[1][uy][ux] != Blocks.AIR && worldContainer.blocks[1][uy][ux].getLights() != 0 ||
                worldContainer.blocks[2][uy][ux] != Blocks.AIR && worldContainer.blocks[2][uy][ux].getLights() != 0);
    }

    public boolean isNonLayeredBlockLightSource(int ux, int uy) {
        return isNonLayeredBlockLightSource(ux, uy, worldContainer.layer);
    }

    public boolean isNonLayeredBlockLightSource(int ux, int uy, int layer) {
        return (layer != 0 && worldContainer.blocks[0][uy][ux] != Blocks.AIR && worldContainer.blocks[0][uy][ux].getLights() != 0 ||
                layer != 1 && worldContainer.blocks[1][uy][ux] != Blocks.AIR && worldContainer.blocks[1][uy][ux].getLights() != 0 ||
                layer != 2 && worldContainer.blocks[2][uy][ux] != Blocks.AIR && worldContainer.blocks[2][uy][ux].getLights() != 0);
    }

    public int findBlockLightSource(int ux, int uy) {
        int n = 0;
        if (worldContainer.blocks[0][uy][ux] != Blocks.AIR) {
            n = Math.max(worldContainer.blocks[0][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[1][uy][ux] != Blocks.AIR) {
            n = Math.max(worldContainer.blocks[1][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[2][uy][ux] != Blocks.AIR) {
            n = Math.max(worldContainer.blocks[2][uy][ux].getLights(), n);
        }
        return n;
    }

    public int findNonLayeredBlockLightSource(int ux, int uy) {
        int n = 0;
        if (worldContainer.blocks[0][uy][ux] != Blocks.AIR) {
            n = Math.max(worldContainer.blocks[0][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[1][uy][ux] != Blocks.AIR) {
            n = Math.max(worldContainer.blocks[1][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[2][uy][ux] != Blocks.AIR) {
            n = Math.max(worldContainer.blocks[2][uy][ux].getLights(), n);
        }
        return n;
    }

    public void addTileToQueue(int ux, int uy) {
        if (!worldContainer.lqd[uy][ux]) {
            worldContainer.lqx.add(ux);
            worldContainer.lqy.add(uy);
            worldContainer.lqd[uy][ux] = true;
        }
    }

    public void addTileToZQueue(int ux, int uy) {
        if (!zqd[uy][ux]) {
            zqx.add(ux);
            zqy.add(uy);
            zqn[uy][ux] = (byte) (float) worldContainer.lights[uy][ux];
            zqd[uy][ux] = true;
        }
    }

    public void addTileToPQueue(int ux, int uy) {
        if (!pqd[uy][ux]) {
            pqx.add(ux);
            pqy.add(uy);
            pqd[uy][ux] = true;
        }
    }

    public void addAdjacentTilesToPQueue(int ux, int uy) {
        for (int i2 = 0; i2 < 4; i2++) {
            if (uy + cl[i2][1] >= 0 && uy + cl[i2][1] < HEIGHT) {
                addTileToPQueue(ux + cl[i2][0], uy + cl[i2][1]);
            }
        }
    }

    public void addAdjacentTilesToPQueueConditionally(int ux, int uy) {
        for (int i2 = 0; i2 < 4; i2++) {
            for (int l = 0; l < LAYER_SIZE; l++) {
                if (uy + cl[i2][1] >= 0 && uy + cl[i2][1] < HEIGHT && worldContainer.power[l][uy + cl[i2][1]][ux + cl[i2][0]] > 0) {
                    addTileToPQueue(ux + cl[i2][0], uy + cl[i2][1]);
                }
            }
        }
    }

    public void addTileToPZQueue(int ux, int uy) {
        if (!pzqd[uy][ux]) {
            pzqx.add(ux);
            pzqy.add(uy);
            pzqn[0][uy][ux] = (byte) (float) worldContainer.power[0][uy][ux];
            pzqn[1][uy][ux] = (byte) (float) worldContainer.power[1][uy][ux];
            pzqn[2][uy][ux] = (byte) (float) worldContainer.power[2][uy][ux];
            pzqd[uy][ux] = true;
        }
    }

    public void resolveLightMatrix() {
        int x = 0, y = 0;
        try {
            for (int j = 0; j < worldContainer.lqx.size(); j++) {
                x = worldContainer.lqx.get(j);
                y = worldContainer.lqy.get(j);
                if (worldContainer.lsources[y][x]) {
                    int n = findBlockLightSource(x, y);
                    if (isReachedBySunlight(x, y)) {
                        worldContainer.lights[y][x] = MathTool.max(worldContainer.lights[y][x], n, sunlightlevel);
                    } else {
                        worldContainer.lights[y][x] = Math.max(worldContainer.lights[y][x], n);
                    }
                    addTileToZQueue(x, y);
                }
                for (int i = 0; i < 4; i++) {
                    int x2 = x + cl[i][0];
                    int y2 = y + cl[i][1];
                    if (y2 >= 0 && y2 < HEIGHT) {
                        if (!worldContainer.blocks[1][y2][x2].isLTrans()) {
                            if (worldContainer.lights[y2][x2] <= worldContainer.lights[y][x] - (float) 1.0) {
                                addTileToZQueue(x2, y2);
                                worldContainer.lights[y2][x2] = worldContainer.lights[y][x] - (float) 1.0;
                                addTileToQueue(x2, y2);
                            }
                        } else {
                            if (worldContainer.lights[y2][x2] <= worldContainer.lights[y][x] - (float) 2.0) {
                                addTileToZQueue(x2, y2);
                                worldContainer.lights[y2][x2] = worldContainer.lights[y][x] - (float) 2.0;
                                addTileToQueue(x2, y2);
                            }
                        }
                    }
                }

                worldContainer.lqd[y][x] = false;
            }

            worldContainer.lqx.clear();
            worldContainer.lqy.clear();
        } catch (IndexOutOfBoundsException e) {
            log.warn("Out of Bounds at " + y + "/" + x, e);
        }
        for (int i = 0; i < zqx.size(); i++) {
            x = zqx.get(i);
            y = zqy.get(i);
            if ((int) (float) worldContainer.lights[y][x] != zqn[y][x]) {
                worldContainer.rdrawn[y][x] = false;
            }
            zqd[y][x] = false;
        }
        zqx.clear();
        zqy.clear();
    }

    public void resolvePowerMatrix() {
        int x = 0, y = 0;
        try {
            for (int j = 0; j < pqx.size(); j++) {
                x = pqx.get(j);
                y = pqy.get(j);
                for (int l = 0; l < 3; l++) {
                    if (worldContainer.blocks[l][y][x].isPower()) {
                        if (!worldContainer.blocks[l][y][x].isCompleteZythiumDelayer()) {
                            addTileToPQueue(x, y);
                            worldContainer.power[l][y][x] = (float) 5;
                        }
                    }
                }
                for (int i = 0; i < 4; i++) {
                    int x2 = x + cl[i][0];
                    int y2 = y + cl[i][1];
                    if (y2 >= 0 && y2 < HEIGHT) {
                        for (int l = 0; l < 3; l++) {
                            if (worldContainer.power[l][y][x] > 0) {
                                if (worldContainer.blocks[l][y][x].getConduct() >= 0 && worldContainer.blocks[l][y2][x2].isReceive() && !(worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && x < x2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && y < y2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && x > x2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && y > y2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && x < x2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && y < y2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && x > x2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && y > y2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && x < x2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && y < y2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && x > x2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && y > y2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && x < x2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && y < y2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && x > x2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && y > y2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_UP && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && x < x2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && y < y2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && x > x2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && y > y2 && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON) &&
                                        !(worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && x < x2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && y < y2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && x > x2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && y > y2 && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                                    if (worldContainer.power[l][y2][x2] <= worldContainer.power[l][y][x] - worldContainer.blocks[l][y][x].getConduct()) {
                                        addTileToPZQueue(x2, y2);
                                        if (worldContainer.blocks[l][y2][x2].isZythiumDelayerAll()) {
                                            log.info("[DEBUG1]");
                                            updatex.add(x2);
                                            updatey.add(y2);
                                            updatet.add(DDELAY.get(worldContainer.blocks[l][y2][x2]));
                                            updatel.add(l);
                                        } else {
                                            worldContainer.power[l][y2][x2] = worldContainer.power[l][y][x] - (float) worldContainer.blocks[l][y][x].getConduct();
                                            if (worldContainer.blocks[l][y2][x2].getConduct() >= 0 && wcnct[y2][x2]) {
                                                if (l == 0) {
                                                    if (worldContainer.blocks[1][y2][x2].isReceive()) {
                                                        worldContainer.power[1][y2][x2] = worldContainer.power[0][y2][x2] - (float) worldContainer.blocks[0][y2][x2].getConduct();
                                                    }
                                                    if (worldContainer.blocks[2][y2][x2].isReceive()) {
                                                        worldContainer.power[2][y2][x2] = worldContainer.power[0][y2][x2] - (float) worldContainer.blocks[0][y2][x2].getConduct();
                                                    }
                                                }
                                                if (l == 1) {
                                                    if (worldContainer.blocks[0][y2][x2].isReceive()) {
                                                        worldContainer.power[0][y2][x2] = worldContainer.power[1][y2][x2] - (float) worldContainer.blocks[1][y2][x2].getConduct();
                                                    }
                                                    if (worldContainer.blocks[2][y2][x2].isReceive()) {
                                                        worldContainer.power[2][y2][x2] = worldContainer.power[1][y2][x2] - (float) worldContainer.blocks[1][y2][x2].getConduct();
                                                    }
                                                }
                                                if (l == 2) {
                                                    if (worldContainer.blocks[0][y2][x2].isReceive()) {
                                                        worldContainer.power[0][y2][x2] = worldContainer.power[2][y2][x2] - (float) worldContainer.blocks[2][y2][x2].getConduct();
                                                    }
                                                    if (worldContainer.blocks[1][y2][x2].isReceive()) {
                                                        worldContainer.power[1][y2][x2] = worldContainer.power[2][y2][x2] - (float) worldContainer.blocks[2][y2][x2].getConduct();
                                                    }
                                                }
                                            }
                                        }
                                        if (!(worldContainer.blocks[l][y2][x2].isZythiumInverterAll())) {
                                            addTileToPQueue(x2, y2);
                                        }
                                    }
                                    if (worldContainer.power[l][y][x] - worldContainer.blocks[l][y][x].getConduct() > 0 && worldContainer.blocks[l][y2][x2].isZythiumInverterAll()) {
                                        removeBlockPower(x2, y2, l);
                                        worldContainer.blocks[l][y2][x2] = Blocks.turnZythiumInverterOn(worldContainer.blocks[l][y2][x2]);
                                        removeBlockLighting(x2, y2);
                                        worldContainer.rdrawn[y2][x2] = false;
                                    }
                                }
                            }
                        }
                    }
                }
                pqd[y][x] = false;
                for (int l = 0; l < 3; l++) {
                    log.info("[resolvePowerMatrix] " + x + " " + y + " " + l + " " + worldContainer.blocks[l][y][x] + " " + worldContainer.power[l][y][x]);
                    if (worldContainer.power[l][y][x] > 0) {
                        if (worldContainer.blocks[l][y][x] == Blocks.ZYTHIUM_LAMP) {
                            worldContainer.blocks[l][y][x] = Blocks.ZYTHIUM_LAMP_ON;
                            addBlockLighting(x, y);
                            worldContainer.rdrawn[y][x] = false;
                        }
                        if (worldContainer.blocks[l][y][x].isZythiumAmplifierAll()) {
                            log.info("Processed amplifier at " + x + " " + y);
                            worldContainer.blocks[l][y][x] = Blocks.turnZythiumAmplifierOn(worldContainer.blocks[l][y][x]);
                            addTileToPQueue(x, y);
                            addBlockLighting(x, y);
                            worldContainer.rdrawn[y][x] = false;
                        }
                    }
                }
            }

            pqx.clear();
            pqy.clear();

        } catch (IndexOutOfBoundsException e) {
            log.warn("Out of Bounds at " + y + "/" + x, e);
        }
        for (int i = 0; i < pzqx.size(); i++) {
            x = pzqx.get(i);
            y = pzqy.get(i);
            for (int l = 0; l < 3; l++) {
                if (worldContainer.blocks[l][y][x].isZythiumWire() && (int) (float) worldContainer.power[l][y][x] != pzqn[l][y][x]) {
                    removeBlockLighting(x, y, 0);
                    worldContainer.blocks[l][y][x] = WIREP.get((int) (float) worldContainer.power[l][y][x]);
                    addBlockLighting(x, y);
                    worldContainer.rdrawn[y][x] = false;
                }
            }
            pzqd[y][x] = false;
        }
        pzqx.clear();
        pzqy.clear();
    }

    public void paint(Graphics g) {
        if (screen == null) {
            return;
        }

        paintService.paint(this, g);
    }

    public BufferedImage loadBlock(Blocks type, Directions direction, Byte directionIntensity, Byte textureIntensity, String outlineName, int x, int y, int lyr) {
        BufferedImage outline = outlineImgs.get("outlines/" + outlineName + "/" + direction.getFileName() + (directionIntensity + 1) + ".png");
        String bName = type.getFileName();
        BufferedImage texture = blockImgs.get("blocks/" + bName + "/texture" + (textureIntensity + 1) + ".png");
        BufferedImage image = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT);
        if (GRASSDIRT.get(type) != null) {
            BufferedImage dirtOriginal = blockImgs.get("blocks/" + GRASSDIRT.get(type).getFileName() + "/texture" + (textureIntensity + 1) + ".png");
            BufferedImage dirt = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT);
            for (int dy = 0; dy < IMAGESIZE; dy++) {
                for (int dx = 0; dx < IMAGESIZE; dx++) {
                    dirt.setRGB(dx, dy, dirtOriginal.getRGB(dx, dy));
                }
            }
            boolean left = (worldContainer.blocks[lyr][y][x - 1] == Blocks.AIR || !worldContainer.blocks[lyr][y][x - 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y+1][x] != dn) && (worldContainer.blocks[lyr][y-1][x-1] != dn && worldContainer.blocks[lyr][y+1][x-1] != dn);
            boolean right = (worldContainer.blocks[lyr][y][x + 1] == Blocks.AIR || !worldContainer.blocks[lyr][y][x + 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y+1][x] != dn) && (worldContainer.blocks[lyr][y-1][x+1] != dn && worldContainer.blocks[lyr][y+1][x+1] != dn);
            boolean up = (worldContainer.blocks[lyr][y - 1][x] == Blocks.AIR || !worldContainer.blocks[lyr][y - 1][x].isCds());// && (worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y][x+1] != dn) && (worldContainer.blocks[lyr][y-1][x-1] != dn && worldContainer.blocks[lyr][y-1][x+1] != dn);
            boolean down = (worldContainer.blocks[lyr][y + 1][x] == Blocks.AIR || !worldContainer.blocks[lyr][y + 1][x].isCds());// && (worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y][x+1] != dn) && (worldContainer.blocks[lyr][y+1][x-1] != dn && worldContainer.blocks[lyr][y+1][x+1] != dn);
            boolean upleft = (worldContainer.blocks[lyr][y - 1][x - 1] == Blocks.AIR || !worldContainer.blocks[lyr][y - 1][x - 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y-1][x-1] != dn && worldContainer.blocks[lyr][y-2][x] != dn && worldContainer.blocks[lyr][y][x-2] != dn);
            boolean upright = (worldContainer.blocks[lyr][y - 1][x + 1] == Blocks.AIR || !worldContainer.blocks[lyr][y - 1][x + 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y][x+1] != dn && worldContainer.blocks[lyr][y-1][x+1] != dn && worldContainer.blocks[lyr][y-2][x] != dn && worldContainer.blocks[lyr][y][x+2] != dn);
            boolean downleft = (worldContainer.blocks[lyr][y + 1][x - 1] == Blocks.AIR || !worldContainer.blocks[lyr][y + 1][x - 1].isCds());// && (worldContainer.blocks[lyr][y+1][x] != dn && worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y+1][x-1] != dn && worldContainer.blocks[lyr][y+2][x] != dn && worldContainer.blocks[lyr][y][x-2] != dn);
            boolean downright = (worldContainer.blocks[lyr][y + 1][x + 1] == Blocks.AIR || !worldContainer.blocks[lyr][y + 1][x + 1].isCds());// && (worldContainer.blocks[lyr][y+1][x] != dn && worldContainer.blocks[lyr][y][x+1] != dn && worldContainer.blocks[lyr][y+1][x+1] != dn && worldContainer.blocks[lyr][y+2][x] != dn && worldContainer.blocks[lyr][y][x+2] != dn);
            int[][] pixm = new int[IMAGESIZE][IMAGESIZE];
            for (int dy = 0; dy < IMAGESIZE; dy++) {
                for (int dx = 0; dx < IMAGESIZE; dx++) {
                    pixm[dy][dx] = 0;
                }
            }
            if (left) {
                pixm[3][0] = 255;
                pixm[4][0] = 255;
            }
            if (right) {
                pixm[3][7] = 255;
                pixm[4][7] = 255;
            }
            if (up) {
                pixm[0][3] = 255;
                pixm[0][4] = 255;
            }
            if (down) {
                pixm[7][3] = 255;
                pixm[7][4] = 255;
            }
            if (upleft) {
                pixm[0][0] = 255;
            }
            if (upright) {
                pixm[0][7] = 255;
            }
            if (downleft) {
                pixm[7][0] = 255;
            }
            if (downright) {
                pixm[7][7] = 255;
            }
            if (left && up) {
                for (int d = 0; d < 4; d++) {
                    pixm[d][0] = 255;
                    pixm[0][d] = 255;
                }
            }
            if (up && right) {
                for (int dx = 4; dx < 8; dx++) {
                    pixm[0][dx] = 255;
                }
                for (int dy = 0; dy < 4; dy++) {
                    pixm[dy][7] = 255;
                }
            }
            if (right && down) {
                for (int d = 4; d < 8; d++) {
                    pixm[d][7] = 255;
                    pixm[7][d] = 255;
                }
            }
            if (down && left) {
                for (int dx = 0; dx < 4; dx++) {
                    pixm[7][dx] = 255;
                }
                for (int dy = 4; dy < 8; dy++) {
                    pixm[dy][0] = 255;
                }
            }
            for (int dy = 0; dy < IMAGESIZE; dy++) {
                for (int dx = 0; dx < IMAGESIZE; dx++) {
                    if (pixm[dy][dx] == 255) {
                        for (int dy2 = 0; dy2 < IMAGESIZE; dy2++) {
                            for (int dx2 = 0; dx2 < IMAGESIZE; dx2++) {
                                int n = (int) (255 - 32 * Math.sqrt(Math.pow(dx - dx2, 2) + Math.pow(dy - dy2, 2)));
                                if (pixm[dy2][dx2] < n) {
                                    pixm[dy2][dx2] = n;
                                }
                            }
                        }
                    }
                }
            }
            for (int dy = 0; dy < IMAGESIZE; dy++) {
                for (int dx = 0; dx < IMAGESIZE; dx++) {
                    dirt.setRGB(dx, dy, new Color((int) (pixm[dy][dx] / 255.0 * new Color(texture.getRGB(dx, dy)).getRed() + (1 - pixm[dy][dx] / 255.0) * new Color(dirt.getRGB(dx, dy)).getRed()),
                            (int) (pixm[dy][dx] / 255.0 * new Color(texture.getRGB(dx, dy)).getGreen() + (1 - pixm[dy][dx] / 255.0) * new Color(dirt.getRGB(dx, dy)).getGreen()),
                            (int) (pixm[dy][dx] / 255.0 * new Color(texture.getRGB(dx, dy)).getBlue() + (1 - pixm[dy][dx] / 255.0) * new Color(dirt.getRGB(dx, dy)).getBlue())).getRGB());
                }
            }
            texture = dirt;
        }
        for (int fy = 0; fy < IMAGESIZE; fy++) {
            for (int fx = 0; fx < IMAGESIZE; fx++) {
                if (outline.getRGB(fx, fy) == -65281 || outline.getRGB(fx, fy) == -48897) {
                    image.setRGB(fx, fy, texture.getRGB(fx, fy));
                } else if (outline.getRGB(fx, fy) == -16777216) {
                    image.setRGB(fx, fy, -16777216);
                }
            }
        }
        return image;
    }

    public void keyPressed(KeyEvent key) {
        log.info("Handling key pressed");

        if (key.getKeyCode() == key.VK_LEFT || key.getKeyCode() == key.VK_A) {
            keyPressed = KeyPressed.LEFT;
        } else if (key.getKeyCode() == key.VK_RIGHT || key.getKeyCode() == key.VK_D) {
            keyPressed = KeyPressed.RIGHT;
        } else if (key.getKeyCode() == key.VK_UP || key.getKeyCode() == key.VK_W) {
            keyPressed = KeyPressed.UP;
        } else if (key.getKeyCode() == key.VK_DOWN || key.getKeyCode() == key.VK_S) {
            keyPressed = KeyPressed.DOWN;
        } else if (key.getKeyCode() == key.VK_SHIFT) {
            keyPressed = KeyPressed.SHIFT;
        }

        if (state == State.IN_GAME) {
            if (key.getKeyCode() == key.VK_ESCAPE) {
                if (worldContainer.ic != null) {
                    if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                        worldContainer.machinesx.add(worldContainer.icx);
                        worldContainer.machinesy.add(worldContainer.icy);
                        worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                    } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                        if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                            for (int i = 0; i < 9; i++) {
                                if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                    worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                }
                            }
                        }
                        if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                            for (int i = 0; i < 9; i++) {
                                if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                    worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                }
                            }
                        }
                    }
                    if (worldContainer.ic.getType() == ItemType.FURNACE) {
                        worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                        worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setSMELTP(worldContainer.ic.getSMELTP());
                        worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFurnaceOn(worldContainer.ic.isFurnaceOn());
                    }
                    worldContainer.ic = null;
                } else {
                    if (worldContainer.showInv) {
                        for (int i = 0; i < 4; i++) {
                            if (worldContainer.cic.getIds()[i] != Items.EMPTY) {
                                if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, worldContainer.cic.getIds()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                                }
                                if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, worldContainer.cic.getIds()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                                }
                                worldContainer.inventory.removeLocationIC(worldContainer.cic, i, worldContainer.cic.getNums()[i]);
                            }
                        }
                    }
                    worldContainer.showInv = !worldContainer.showInv;
                }
                if (worldContainer.moveItem != Items.EMPTY) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, worldContainer.moveItem, worldContainer.moveNum, moveDur, 75));
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, worldContainer.moveItem, worldContainer.moveNum, moveDur, 75));
                    }
                    worldContainer.moveItem = Items.EMPTY;
                    worldContainer.moveNum = 0;
                }
            }
            if (!worldContainer.showTool) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_1:
                        worldContainer.inventory.select(1);
                        break;
                    case KeyEvent.VK_2:
                        worldContainer.inventory.select(2);
                        break;
                    case KeyEvent.VK_3:
                        worldContainer.inventory.select(3);
                        break;
                    case KeyEvent.VK_4:
                        worldContainer.inventory.select(4);
                        break;
                    case KeyEvent.VK_5:
                        worldContainer.inventory.select(5);
                        break;
                    case KeyEvent.VK_6:
                        worldContainer.inventory.select(6);
                        break;
                    case KeyEvent.VK_7:
                        worldContainer.inventory.select(7);
                        break;
                    case KeyEvent.VK_8:
                        worldContainer.inventory.select(8);
                        break;
                    case KeyEvent.VK_9:
                        worldContainer.inventory.select(9);
                        break;
                    case KeyEvent.VK_0:
                        worldContainer.inventory.select(0);
                        break;
                }
            }
        }

        if (state == State.NEW_WORLD) {
            if ((key.getKeyChar() >= KeyEvent.VK_A && key.getKeyChar() <= KeyEvent.VK_Z)
                || (key.getKeyChar() >= KeyEvent.VK_1 && key.getKeyChar() <= KeyEvent.VK_2)) {
                newWorldName.typeKey(key.getKeyChar());
                repaint();
            }

            if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                newWorldName.deleteKey();
                repaint();
            }
        }

        if (key.getKeyCode() == key.VK_EQUALS && worldContainer.layer < 2) {
            worldContainer.layer += 1;
        }
        if (key.getKeyCode() == key.VK_MINUS && worldContainer.layer > 0) {
            worldContainer.layer -= 1;
        }
    }

    public void keyReleased(KeyEvent key) {
        if (key.getKeyCode() == key.VK_LEFT || key.getKeyCode() == key.VK_A
                || key.getKeyCode() == key.VK_RIGHT || key.getKeyCode() == key.VK_D
                || key.getKeyCode() == key.VK_UP || key.getKeyCode() == key.VK_W
                || key.getKeyCode() == key.VK_SHIFT
                || key.getKeyCode() == key.VK_DOWN || key.getKeyCode() == key.VK_S) {
            keyPressed = KeyPressed.NONE;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (mousePressed != MousePressed.LEFT_MOUSE && e.getButton() == MouseEvent.BUTTON1) {
            mousePressed = MousePressed.LEFT_MOUSE;
        }
        if (mousePressed != MousePressed.RIGHT_MOUSE && e.getButton() == MouseEvent.BUTTON3) {
            mousePressed = MousePressed.RIGHT_MOUSE;
        }
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = MousePressed.NONE;
        menuPressed = false;
    }

    public void mouseClicked(MouseEvent e) {
        //
    }

    public void mouseMoved(MouseEvent e) {
        if (mousePos != null) {
            mousePos.setX(e.getX());
            mousePos.setY(e.getY());
        }
    }

    public void mouseDragged(MouseEvent e) {
        mousePos.setX(e.getX());
        mousePos.setY(e.getY());
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }


    public static Map<Items, BufferedImage> getItemImgs() {
        return itemImgs;
    }

    public void keyTyped(KeyEvent key) {
        //
    }

    public void stateChanged(ChangeEvent e) {
        //
    }

    public void mouseEntered(MouseEvent e) {
        //
    }

    public void mouseExited(MouseEvent e) {
        //
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        //
    }

    public void update() {
        //
    }
}
