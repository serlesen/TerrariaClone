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
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import com.sergio.refacto.init.DDelayInitializer;
import com.sergio.refacto.init.DurabilityInitializer;
import com.sergio.refacto.init.FuelsInitializer;
import com.sergio.refacto.init.GrassDirtInitializer;
import com.sergio.refacto.init.SkyColorsInitializer;
import com.sergio.refacto.init.TorchesLInitializer;
import com.sergio.refacto.init.TorchesRInitializer;
import com.sergio.refacto.init.WirePInitializer;
import com.sergio.refacto.items.Chunk;
import com.sergio.refacto.items.Cloud;
import com.sergio.refacto.items.Entity;
import com.sergio.refacto.items.ImagesContainer;
import com.sergio.refacto.items.Mouse;
import com.sergio.refacto.items.Player;
import com.sergio.refacto.items.TextField;
import com.sergio.refacto.items.WorldContainer;
import com.sergio.refacto.services.InventoryService;
import com.sergio.refacto.services.OutlinesService;
import com.sergio.refacto.services.PaintService;
import com.sergio.refacto.services.TimeService;
import com.sergio.refacto.services.WorldService;
import com.sergio.refacto.tools.RandomTool;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static com.sergio.refacto.dto.Constants.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC)
public class TerrariaClone extends JApplet implements KeyListener, MouseListener, MouseMotionListener {

    private WorldService worldService;
    private PaintService paintService;

    /** Size (in block units) of the in-memory game. */
    public static final int SIZE = WorldContainer.CHUNK_BLOCKS * 2;

    private static final int TIMER_DELAY = 100;

    public static final int LAYER_SIZE = 3;

    static GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    BufferedImage screen;
    Color bg;

    javax.swing.Timer inGameTimer, menuTimer;
    List<FileInfo> filesInfo;
    String currentWorld;
    TextField newWorldName;

    WorldContainer worldContainer;

    public static ItemCollection armor;

    Chunk[][] temporarySaveFile;
    Chunk[][] chunkMatrix;

    State state = State.LOADING_GRAPHICS;

    /** Indexes of the in-memory chunks in the COMPLETE world. */
    int ou, ov, uNew, vNew;
    double p, q;
    Items miningTool;

    short moveDur;

    /** Position of the mouse (in pixels) in the screen. */
    public Mouse mousePos;

    /** Position of the mouse (in pixels) in the complete world. */
    public Mouse mousePos2;

    Font font = new Font("Chalkboard", Font.BOLD, 12);
    Font mobFont = new Font("Chalkboard", Font.BOLD, 16);
    Font loadFont = new Font("Courier", Font.PLAIN, 12);
    Font worldFont = new Font("Andale Mono", Font.BOLD, 16);
    Color CYANISH = new Color(75, 163, 243);

    javax.swing.Timer createWorldTimer;
    KeyPressed keyPressed;
    MousePressed mousePressed;

    boolean checkBlocks = true;
    boolean menuPressed = false;

    public static final int IMAGESIZE = 8;
    public static final int BRIGHTEST = 21;

    int sunlightlevel = 19;

    static Map<Short, Map<Integer, Integer>> DURABILITY;
    static Map<Integer, Color> SKYCOLORS;
    static Map<Blocks, Blocks> GRASSDIRT;
    public static Map<Items, Double> FUELS;
    public static Map<Integer, Blocks> WIREP;
    static Map<Blocks, Blocks> TORCHESL;
    static Map<Blocks, Blocks> TORCHESR;
    public static Map<Blocks, Integer> DDELAY;

    public static List<Items> FRI1, FRI2;
    public static List<Short> FRN1, FRN2;

    public static void main(String[] args) {
        JFrame f = new JFrame("TerrariaClone: Infinite worlds!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setFocusable(false);

        JApplet ap = new TerrariaClone();
        ap.setFocusable(true);
        f.add("Center", ap);
        f.pack();

        f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent aE) {
//                ap.requestFocus();
                ap.requestFocusInWindow();
            }
        });

        ap.init();
    }

    public void init() {
        try {
            paintService = new PaintService();
            worldService = new WorldService();
            TimeService.getInstance().init();
            InventoryService.getInstance().init();

            setLayout(new BorderLayout());
            bg = Color.BLACK;

            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);

            screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            mousePos = new Mouse();
            mousePos2 = new Mouse();

            worldContainer = new WorldContainer();

            state = State.LOADING_GRAPHICS;

            repaint();

            DURABILITY = DurabilityInitializer.init();

            SKYCOLORS = SkyColorsInitializer.init();

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

            inGameTimer = new Timer(TIMER_DELAY, null);
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
        int blockOffsetU = -ou * WorldContainer.CHUNK_BLOCKS;
        int blockOffsetV = -ov * WorldContainer.CHUNK_BLOCKS;
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
                        Graphics2D wg2 = worldContainer.worlds[twy][twx].createGraphics();
                        Graphics2D fwg2 = worldContainer.fworlds[twy][twx].createGraphics();
                        for (int tly = Math.max(twy * WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intY - getHeight() / 2 + Player.HEIGHT / 2 + blockOffsetV * WorldContainer.BLOCK_SIZE) - 64); tly < Math.min(twy * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intY + getHeight() / 2 - Player.HEIGHT / 2 + blockOffsetV * WorldContainer.BLOCK_SIZE) + 128); tly += WorldContainer.BLOCK_SIZE) {
                            for (int tlx = Math.max(twx * WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intX - getWidth() / 2 + Player.WIDTH / 2 + blockOffsetU * WorldContainer.BLOCK_SIZE) - 64); tlx < Math.min(twx * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE, (int) (worldContainer.player.intX + getWidth() / 2 - Player.WIDTH / 2 + blockOffsetU * WorldContainer.BLOCK_SIZE) + 112); tlx += WorldContainer.BLOCK_SIZE) {
                                int tx = (int) (tlx / WorldContainer.BLOCK_SIZE);
                                int ty = (int) (tly / WorldContainer.BLOCK_SIZE);
                                if (tx >= 0 && tx < SIZE && ty >= 0 && ty < SIZE) {
                                    if (!worldContainer.drawn[ty][tx]) {
                                        somevar = true;
                                        drawBlocks(twy, twx, wg2, fwg2, tx, ty);
                                    }
                                    if (!worldContainer.rdrawn[ty][tx]) {
                                        somevar = true;
                                        drawBlocks(twy, twx, wg2, fwg2, tx, ty);
                                    }
                                    if (!worldContainer.ldrawn[ty][tx] && RandomTool.nextInt(10) == 0) {
                                        somevar = true;
                                        drawBlocks(twy, twx, wg2, fwg2, tx, ty);
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
        updateApp(blockOffsetU, blockOffsetV);
        updateEnvironment();
        worldContainer.player.update(worldContainer.blocks[1], keyPressed, blockOffsetU, blockOffsetV);

        repaint();
        worldContainer.ready = true;
    }

    private void drawBlocks(int twy, int twx, Graphics2D wg2, Graphics2D fwg2, int tx, int ty) {
        worldContainer.blocksTextureIntensity[ty][tx] = (byte) RandomTool.nextInt(8);

        paintService.paintBackground(this, wg2, twx, twy, tx, ty);
        paintService.paintBlock(this, wg2, fwg2, twx, twy, tx, ty);
        paintService.paintLights(this, fwg2, twx, twy, tx, ty);

        worldContainer.drawn[ty][tx] = true;
        worldContainer.rdrawn[ty][tx] = true;
        worldContainer.ldrawn[ty][tx] = true;
    }

    private void loadWorldContainer() {
        for (int twy = 0; twy < 2; twy++) {
            for (int twx = 0; twx < 2; twx++) {
                for (int y = 0; y < WorldContainer.CHUNK_BLOCKS; y++) {
                    for (int x = 0; x < WorldContainer.CHUNK_BLOCKS; x++) {
                        for (int l = 0; l < LAYER_SIZE; l++) {
                            worldContainer.blocks[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocks()[l][y][x];
                            worldContainer.power[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getPower()[l][y][x];
                            worldContainer.pzqn[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getPzqn()[l][y][x];
                            worldContainer.arbprd[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getArbprd()[l][y][x];
                            worldContainer.blocksDirections[l][twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksDirections()[l][y][x];
                        }
                        worldContainer.blocksDirectionsIntensity[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksDirectionsIntensity()[y][x];
                        worldContainer.blocksBackgrounds[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksBackgrounds()[y][x];
                        worldContainer.blocksTextureIntensity[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getBlocksTextureIntesity()[y][x];
                        worldContainer.lights[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getLights()[y][x];
                        worldContainer.lsources[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getLsources()[y][x];
                        worldContainer.zqn[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getZqn()[y][x];
                        worldContainer.wcnct[twy * WorldContainer.CHUNK_BLOCKS + y][twx * WorldContainer.CHUNK_BLOCKS + x] = chunkMatrix[twy][twx].getWcnct()[y][x];
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
                        createWorldTimer = new Timer(TIMER_DELAY, createWorldThread);
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

        armor = new ItemCollection(ItemType.ARMOR);

        worldContainer.createNewWorld(sunlightlevel);

        miningTool = Items.EMPTY;
        moveDur = 0;

        log.info("Finished generation.");
    }

    public void updateApp(int blockOffsetU, int blockOffsetV) {
        mousePos2.setX(mousePos.getX() + worldContainer.player.intX - getWidth() / 2 + Player.WIDTH / 2);
        mousePos2.setY(mousePos.getY() + worldContainer.player.intY - getHeight() / 2 + Player.HEIGHT / 2);

        worldContainer.updateSkyLights();

        if (worldContainer.player.y / WorldContainer.BLOCK_SIZE > HEIGHT * 0.55) {
            bg = Color.BLACK;
        } else {
            bg = SKYCOLORS.get(worldContainer.currentSkyLight);
        }

        worldContainer.updateHealthPoints();
        worldContainer.updateMachinesState();
        worldContainer.updateItemCollection();

        if (Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - worldContainer.icx * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - worldContainer.icy * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2)) > 160) {
            if (worldContainer.ic != null) {
                if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                    worldContainer.machinesx.add(worldContainer.icx);
                    worldContainer.machinesy.add(worldContainer.icy);
                    worldContainer.icmatrix[worldContainer.layer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                            if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                            if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                } else if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    worldContainer.updateFurnaceState();
                }
                worldContainer.ic = null;
            }
        }

        worldContainer.upgradeBlocksState(blockOffsetU, blockOffsetV);
        worldContainer.updateGrassState(blockOffsetU, blockOffsetV);
        worldContainer.updateTreesState();

        for (int i = worldContainer.updatex.size() - 1; i > -1; i--) {
            worldContainer.updatet.set(i, worldContainer.updatet.get(i) - 1);
            if (worldContainer.updatet.get(i) <= 0) {
                if (worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] == Blocks.BUTTON_LEFT_ON) {
                    worldContainer.removeBlockPower(worldContainer.updatex.get(i), worldContainer.updatey.get(i), worldContainer.updatel.get(i));
                    worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = Blocks.BUTTON_LEFT;
                    worldContainer.rdrawn[worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = false;
                } else if (worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] == Blocks.BUTTON_RIGHT_ON) {
                    worldContainer.removeBlockPower(worldContainer.updatex.get(i), worldContainer.updatey.get(i), worldContainer.updatel.get(i));
                    worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = Blocks.BUTTON_RIGHT;
                    worldContainer.rdrawn[worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = false;
                } else if (worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] == Blocks.WOODEN_PRESSURE_PLATE_ON) {
                    worldContainer.removeBlockPower(worldContainer.updatex.get(i), worldContainer.updatey.get(i), worldContainer.updatel.get(i));
                    worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = Blocks.WOODEN_PRESSURE_PLATE;
                    worldContainer.rdrawn[worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = false;
                } else if (worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] == Blocks.STONE_PRESSURE_PLATE_ON) {
                    worldContainer.removeBlockPower(worldContainer.updatex.get(i), worldContainer.updatey.get(i), worldContainer.updatel.get(i));
                    worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = Blocks.STONE_PRESSURE_PLATE;
                    worldContainer.rdrawn[worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = false;
                } else if (worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] == Blocks.ZYTHIUM_PRESSURE_PLATE_ON) {
                    worldContainer.removeBlockPower(worldContainer.updatex.get(i), worldContainer.updatey.get(i), worldContainer.updatel.get(i));
                    worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = Blocks.ZYTHIUM_PRESSURE_PLATE;
                    worldContainer.rdrawn[worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = false;
                } else if (worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)].isZythiumDelayerOnAll()) {
                    log.info("[DEBUG2R]");
                    worldContainer.removeBlockPower(worldContainer.updatex.get(i), worldContainer.updatey.get(i), worldContainer.updatel.get(i), false);
                    worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = Blocks.turnZythiumDelayerOff(worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)]);
                    worldContainer.rdrawn[worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = false;
                } else if (worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)].isZythiumDelayerAll()) {
                    log.info("[DEBUG2A]");
                    worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = Blocks.turnZythiumDelayerOn(worldContainer.blocks[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)]);
                    worldContainer.power[worldContainer.updatel.get(i)][worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = (float) 5;
                    worldContainer.addBlockLighting(worldContainer.updatex.get(i), worldContainer.updatey.get(i));
                    worldContainer.addTileToPQueue(worldContainer.updatex.get(i), worldContainer.updatey.get(i));
                    worldContainer.rdrawn[worldContainer.updatey.get(i)][worldContainer.updatex.get(i)] = false;
                }
                worldContainer.updatex.remove(i);
                worldContainer.updatey.remove(i);
                worldContainer.updatet.remove(i);
                worldContainer.updatel.remove(i);
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
                                if ((TimeService.getInstance().getDay() != 0 || DebugContext.HOSTILE > 1) && TimeService.getInstance().isNight()) {
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
                                if (TimeService.getInstance().isDay()) {
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
                                if (mobSpawn != null && worldContainer.checkBiome(xpos, ypos, blockOffsetU, blockOffsetV) == Biome.DESERT) {
                                    if (RandomTool.nextInt(3) == 0) { // 33% of all spawns in desert
                                        mobSpawn = EntityType.SANDBOT;
                                    }
                                }
                                if (mobSpawn != null && worldContainer.checkBiome(xpos, ypos, blockOffsetU, blockOffsetV) == Biome.FROST) {
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
                if (worldContainer.entities.get(i).getIntX() < worldContainer.player.intX - 2000 || worldContainer.entities.get(i).getIntX() > worldContainer.player.intX + 2000 ||
                        worldContainer.entities.get(i).getIntY() < worldContainer.player.intY - 2000 || worldContainer.entities.get(i).getIntY() > worldContainer.player.intY + 2000) {
                    if (RandomTool.nextInt(500) == 0) {
                        worldContainer.entities.remove(i);
                    }
                }
            }
        }

        if (mousePressed == MousePressed.LEFT_MOUSE) {
            if (handleLeftClick(blockOffsetU, blockOffsetV)) {
                return;
            }
        } else {
            mousePos.setClicked(true);
        }
        if (mousePressed == MousePressed.RIGHT_MOUSE) {
            handleRightClick();
        } else {
            mousePos2.setClicked(true);
        }

        worldContainer.player.updateToolAngle();

        int vc = 0;
        if (!DebugContext.INVINCIBLE && worldContainer.player.y / WorldContainer.BLOCK_SIZE > HEIGHT + 10) {
            vc += 1;
            if (vc >= 1 / (Math.pow(1.001, worldContainer.player.y / WorldContainer.BLOCK_SIZE - HEIGHT - 10) - 1.0)) {
                worldContainer.player.damage(1, false);
                vc = 0;
            }
        } else {
            vc = 0;
        }
        for (int i = worldContainer.entities.size() - 1; i >= 0; i--) {
            if (worldContainer.entities.get(i).getNewMob() != null) {
                worldContainer.entities.add(worldContainer.entities.get(i).getNewMob());
            }
            if (worldContainer.entities.get(i).update(worldContainer.blocks[1], worldContainer.player, blockOffsetU, blockOffsetV)) {
                worldContainer.entities.remove(i);
            } else if (worldContainer.player.occupation.intersects(worldContainer.entities.get(i).getRect())) {
                if (worldContainer.entities.get(i).getEntityType() != null) {
                    if (worldContainer.immune <= 0) {
                        if (!DebugContext.INVINCIBLE) {
                            worldContainer.player.damage(worldContainer.entities.get(i).getAttackPoints(), true);
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
                    int n = InventoryService.getInstance().addItem(worldContainer.inventory, worldContainer.entities.get(i).getItem(), worldContainer.entities.get(i).getNum(), worldContainer.entities.get(i).getDur());
                    if (n != 0) {
                        worldContainer.entities.add(new Entity(worldContainer.entities.get(i).getX(), worldContainer.entities.get(i).getY(), worldContainer.entities.get(i).getSpeedX(), worldContainer.entities.get(i).getSpeedY(), worldContainer.entities.get(i).getItem(), (short) (worldContainer.entities.get(i).getNum() - n), worldContainer.entities.get(i).getDur()));
                    }
                    worldContainer.entities.remove(i);
                }
            }
        }
        if (worldContainer.player.healthPoints <= 0) {
            for (int j = 0; j < 40; j++) {
                if (worldContainer.inventory.getItems()[j] != Items.EMPTY) {
                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -1, RandomTool.nextDouble() * 6 - 3, worldContainer.inventory.getItems()[j], worldContainer.inventory.getNums()[j], worldContainer.inventory.getDurs()[j]));
                    InventoryService.getInstance().removeLocation(worldContainer.inventory, j, worldContainer.inventory.getNums()[j]);
                }
            }
            if (worldContainer.ic != null) {
                if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                    worldContainer.machinesx.add(worldContainer.icx);
                    worldContainer.machinesy.add(worldContainer.icy);
                    worldContainer.icmatrix[worldContainer.layer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic.getType(), worldContainer.ic.getItems(), worldContainer.ic.getNums(), worldContainer.ic.getDurs());
                } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                            if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                            if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                }
                if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    worldContainer.updateFurnaceState();
                }
                worldContainer.ic = null;
            } else {
                if (worldContainer.showInv) {
                    for (int i = 0; i < 4; i++) {
                        if (worldContainer.cic.getItems()[i] != Items.EMPTY) {
                            if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                                worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, worldContainer.cic.getItems()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                            }
                            if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                                worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, worldContainer.cic.getItems()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                            }
                            InventoryService.getInstance().removeLocation(worldContainer.cic, i, worldContainer.cic.getNums()[i]);
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
                if (armor.getItems()[i] != Items.EMPTY) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, armor.getItems()[i], armor.getNums()[i], armor.getDurs()[i], 75));
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, armor.getItems()[i], armor.getNums()[i], armor.getDurs()[i], 75));
                    }
                    InventoryService.getInstance().removeLocation(armor, i, armor.getNums()[i]);
                }
            }
            worldContainer.player.x = WIDTH * 0.5 * WorldContainer.BLOCK_SIZE;
            worldContainer.player.y = 45;
            worldContainer.player.speedX = 0;
            worldContainer.player.speedY = 0;
            worldContainer.player.healthPoints = worldContainer.player.totalHealthPoints;
            worldContainer.player.tool = null;
            worldContainer.player.showTool = false;
        }

        worldContainer.updateEntitiesHit();

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
            int bx1 = (int) p / WorldContainer.BLOCK_SIZE;
            int by1 = (int) q / WorldContainer.BLOCK_SIZE;
            int bx2 = (int) (p + width) / WorldContainer.BLOCK_SIZE;
            int by2 = (int) (q + height) / WorldContainer.BLOCK_SIZE;

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
                            worldContainer.addBlockPower(x, y);
                            log.info("Srsly?");
                            worldContainer.updatex.add(x);
                            worldContainer.updatey.add(y);
                            worldContainer.updatet.add(0);
                            worldContainer.updatel.add(0);
                        }
                    }
                }
            }
        }

        worldContainer.resolvePowerMatrix();
        worldContainer.resolveLightMatrix(sunlightlevel);
        worldContainer.immune -= 1;
    }

    private boolean handleLeftClick(int blockOffsetU, int blockOffsetV) {
        checkBlocks = true;
        if (worldContainer.showInv) {
            if (mousePos.isInBetweenInclusive(getWidth() - ImagesContainer.getInstance().saveExit.getWidth() - 24, getWidth() - 24, getHeight() - ImagesContainer.getInstance().saveExit.getHeight() - 24, getHeight() - 24)) {
                if (mousePos.isClicked()) {
                    mousePos.setReleased(true);
                    worldContainer.saveWorld(currentWorld);
                    state = State.TITLE_SCREEN;
                    inGameTimer.stop();
                    menuTimer.start();
                    return true;
                }
            }
            for (int ux = 0; ux < 10; ux++) {
                for (int uy = 0; uy < 4; uy++) {
                    if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + 6, uy * 46 + 46)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            if (uy != 0 || worldContainer.inventory.getSelection() != ux || !worldContainer.player.showTool) {
                                Items moveItemTemp = worldContainer.inventory.getItems()[uy * 10 + ux];
                                short moveNumTemp = worldContainer.inventory.getNums()[uy * 10 + ux];
                                short moveDurTemp = worldContainer.inventory.getDurs()[uy * 10 + ux];
                                if (worldContainer.moveItem == worldContainer.inventory.getItems()[uy * 10 + ux]) {
                                    worldContainer.moveNum = (short) InventoryService.getInstance().addLocation(worldContainer.inventory, uy * 10 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    if (worldContainer.moveNum == 0) {
                                        worldContainer.moveItem = Items.EMPTY;
                                        moveDur = 0;
                                    }
                                } else {
                                    InventoryService.getInstance().removeLocation(worldContainer.inventory, uy * 10 + ux, worldContainer.inventory.getNums()[uy * 10 + ux]);
                                    if (worldContainer.moveItem != Items.EMPTY) {
                                        InventoryService.getInstance().addLocation(worldContainer.inventory, uy * 10 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                    }
                                    worldContainer.moveItem = moveItemTemp;
                                    worldContainer.moveNum = moveNumTemp;
                                    moveDur = moveDurTemp;
                                }
                            }
                        }
                    }
                }
            }
            for (int ux = 0; ux < 2; ux++) {
                for (int uy = 0; uy < 2; uy++) {
                    if (mousePos.isInBetween(worldContainer.inventory.getImage().getWidth() + ux * 40 + 75, worldContainer.inventory.getImage().getWidth() + ux * 40 + 115, uy * 40 + 52, uy * 40 + 92)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            Items moveItemTemp = worldContainer.cic.getItems()[uy * 2 + ux];
                            short moveNumTemp = worldContainer.cic.getNums()[uy * 2 + ux];
                            short moveDurTemp = worldContainer.cic.getDurs()[uy * 2 + ux];
                            if (worldContainer.moveItem == worldContainer.cic.getItems()[uy * 2 + ux]) {
                                worldContainer.moveNum = (short) InventoryService.getInstance().addLocation(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                    moveDur = 0;
                                }
                            } else {
                                InventoryService.getInstance().removeLocation(worldContainer.cic, uy * 2 + ux, worldContainer.cic.getNums()[uy * 2 + ux]);
                                if (worldContainer.moveItem != Items.EMPTY) {
                                    InventoryService.getInstance().addLocation(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                }
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                                moveDur = moveDurTemp;
                            }
                        }
                    }
                }
            }
            if (mousePos.isInBetween(worldContainer.inventory.getImage().getWidth() + 3 * 40 + 81, worldContainer.inventory.getImage().getWidth() + 3 * 40 + 121, 20 + 52, 20 + 92)) {
                checkBlocks = false;
                if (mousePos.isClicked()) {
                    if (worldContainer.moveItem == worldContainer.cic.getItems()[4] && worldContainer.moveNum + worldContainer.cic.getNums()[4] <= worldContainer.cic.getItems()[4].getMaxStacks()) {
                        worldContainer.moveNum += worldContainer.cic.getNums()[4];
                        InventoryService.getInstance().useRecipeCIC(worldContainer.cic);
                    }
                    if (worldContainer.moveItem == Items.EMPTY) {
                        worldContainer.moveItem = worldContainer.cic.getItems()[4];
                        worldContainer.moveNum = worldContainer.cic.getNums()[4];
                        if (worldContainer.moveItem.getDurability() != null) {
                            moveDur = worldContainer.moveItem.getDurability().shortValue();
                        }
                        InventoryService.getInstance().useRecipeCIC(worldContainer.cic);
                    }
                }
            }
            if (worldContainer.ic != null) {
                if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                    for (int ux = 0; ux < 3; ux++) {
                        for (int uy = 0; uy < 3; uy++) {
                            if (mousePos.isInBetween(ux * 40 + 6, ux * 40 + 46, uy * 40 + worldContainer.inventory.getImage().getHeight() + 46, uy * 40 + worldContainer.inventory.getImage().getHeight() + 86)) {
                                checkBlocks = false;
                                if (mousePos.isClicked()) {
                                    mousePos.setReleased(true);
                                    Items moveItemTemp = worldContainer.ic.getItems()[uy * 3 + ux];
                                    short moveNumTemp = worldContainer.ic.getNums()[uy * 3 + ux];
                                    if (worldContainer.moveItem == worldContainer.ic.getItems()[uy * 3 + ux]) {
                                        worldContainer.moveNum = (short) InventoryService.getInstance().addLocation(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                        if (worldContainer.moveNum == 0) {
                                            worldContainer.moveItem = Items.EMPTY;
                                        }
                                    } else {
                                        InventoryService.getInstance().removeLocation(worldContainer.ic, uy * 3 + ux, worldContainer.ic.getNums()[uy * 3 + ux]);
                                        if (worldContainer.moveItem != Items.EMPTY) {
                                            InventoryService.getInstance().addLocation(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                        }
                                        worldContainer.moveItem = moveItemTemp;
                                        worldContainer.moveNum = moveNumTemp;
                                    }
                                }
                            }
                        }
                    }
                    if (mousePos.isInBetween(4 * 40 + 6, 4 * 40 + 46, 1 * 40 + worldContainer.inventory.getImage().getHeight() + 46, 1 * 40 + worldContainer.inventory.getImage().getHeight() + 86)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            if (worldContainer.moveItem == worldContainer.ic.getItems()[9] && worldContainer.moveNum + worldContainer.ic.getNums()[9] <= worldContainer.ic.getItems()[9].getMaxStacks()) {
                                worldContainer.moveNum += worldContainer.ic.getNums()[9];
                                InventoryService.getInstance().useRecipeWorkbench(worldContainer.ic);
                            }
                            if (worldContainer.moveItem == Items.EMPTY) {
                                worldContainer.moveItem = worldContainer.ic.getItems()[9];
                                worldContainer.moveNum = worldContainer.ic.getNums()[9];
                                if (worldContainer.moveItem.getDurability() != null) {
                                    moveDur = worldContainer.moveItem.getDurability().shortValue();
                                }
                                InventoryService.getInstance().useRecipeWorkbench(worldContainer.ic);
                            }
                        }
                    }
                } else if (worldContainer.ic.getType() == ItemType.WOODEN_CHEST || worldContainer.ic.getType() == ItemType.STONE_CHEST ||
                        worldContainer.ic.getType() == ItemType.COPPER_CHEST || worldContainer.ic.getType() == ItemType.IRON_CHEST ||
                        worldContainer.ic.getType() == ItemType.SILVER_CHEST || worldContainer.ic.getType() == ItemType.GOLD_CHEST ||
                        worldContainer.ic.getType() == ItemType.ZINC_CHEST || worldContainer.ic.getType() == ItemType.RHYMESTONE_CHEST ||
                        worldContainer.ic.getType() == ItemType.OBDURITE_CHEST) {
                    for (int ux = 0; ux < InventoryService.getInstance().CX; ux++) {
                        for (int uy = 0; uy < InventoryService.getInstance().CY; uy++) {
                            if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + worldContainer.inventory.getImage().getHeight() + 46, uy * 46 + worldContainer.inventory.getImage().getHeight() + 86)) {
                                checkBlocks = false;
                                if (mousePos.isClicked()) {
                                    mousePos.setReleased(true);
                                    Items moveItemTemp = worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux];
                                    short moveNumTemp = worldContainer.ic.getNums()[uy * InventoryService.getInstance().CX + ux];
                                    if (worldContainer.moveItem == worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux]) {
                                        worldContainer.moveNum = (short) InventoryService.getInstance().addLocation(worldContainer.ic, uy * InventoryService.getInstance().CX + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                        if (worldContainer.moveNum == 0) {
                                            worldContainer.moveItem = Items.EMPTY;
                                        }
                                    } else {
                                        InventoryService.getInstance().removeLocation(worldContainer.ic, uy * InventoryService.getInstance().CX + ux, worldContainer.ic.getNums()[uy * InventoryService.getInstance().CX + ux]);
                                        if (worldContainer.moveItem != Items.EMPTY) {
                                            InventoryService.getInstance().addLocation(worldContainer.ic, uy * InventoryService.getInstance().CX + ux, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                        }
                                        worldContainer.moveItem = moveItemTemp;
                                        worldContainer.moveNum = moveNumTemp;
                                    }
                                }
                            }
                        }
                    }
                } else if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    if (mousePos.isInBetween(6, 46, worldContainer.inventory.getImage().getHeight() + 46, worldContainer.inventory.getImage().getHeight() + 86)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            Items moveItemTemp = worldContainer.ic.getItems()[0];
                            short moveNumTemp = worldContainer.ic.getNums()[0];
                            if (worldContainer.moveItem == worldContainer.ic.getItems()[0]) {
                                worldContainer.moveNum = (short) InventoryService.getInstance().addLocation(worldContainer.ic, 0, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                }
                            } else {
                                InventoryService.getInstance().removeLocation(worldContainer.ic, 0, worldContainer.ic.getNums()[0]);
                                if (worldContainer.moveItem != Items.EMPTY) {
                                    InventoryService.getInstance().addLocation(worldContainer.ic, 0, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                }
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                                worldContainer.ic.setSMELTP(0);
                            }
                        }
                    }
                    if (mousePos.isInBetween(6, 46, worldContainer.inventory.getImage().getHeight() + 142, worldContainer.inventory.getImage().getHeight() + 182)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            Items moveItemTemp = worldContainer.ic.getItems()[2];
                            short moveNumTemp = worldContainer.ic.getNums()[2];
                            if (worldContainer.moveItem == worldContainer.ic.getItems()[2]) {
                                worldContainer.moveNum = (short) InventoryService.getInstance().addLocation(worldContainer.ic, 2, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                }
                            } else {
                                InventoryService.getInstance().removeLocation(worldContainer.ic, 2, worldContainer.ic.getNums()[2]);
                                if (worldContainer.moveItem != Items.EMPTY) {
                                    InventoryService.getInstance().addLocation(worldContainer.ic, 2, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                }
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                            }
                        }
                    }
                    if (mousePos.isInBetween(62, 102, worldContainer.inventory.getImage().getHeight() + 46, worldContainer.inventory.getImage().getHeight() + 86)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            if (worldContainer.moveItem == Items.EMPTY) {
                                worldContainer.moveItem = worldContainer.ic.getItems()[3];
                                worldContainer.moveNum = worldContainer.ic.getNums()[3];
                                InventoryService.getInstance().removeLocation(worldContainer.ic, 3, worldContainer.ic.getNums()[3]);
                            } else if (worldContainer.moveItem == worldContainer.ic.getItems()[3]) {
                                worldContainer.moveNum += worldContainer.ic.getNums()[3];
                                InventoryService.getInstance().removeLocation(worldContainer.ic, 3, worldContainer.ic.getNums()[3]);
                                if (worldContainer.moveNum > worldContainer.moveItem.getMaxStacks()) {
                                    InventoryService.getInstance().addLocation(worldContainer.ic, 3, worldContainer.moveItem, (short) (worldContainer.moveNum - worldContainer.moveItem.getMaxStacks()), moveDur);
                                    worldContainer.moveNum = (short) worldContainer.moveItem.getMaxStacks();
                                }
                            }
                        }
                    }
                }
            }
            for (int uy = 0; uy < 4; uy++) {
                if (mousePos.isInBetween(worldContainer.inventory.getImage().getWidth() + 6, worldContainer.inventory.getImage().getWidth() + 6 + armor.getImage().getWidth(), 6 + uy * 46, 6 + uy * 46 + 40)) {
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
                            if (armor.getItems()[i] == Items.EMPTY) {
                                InventoryService.getInstance().addLocation(armor, i, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                worldContainer.moveItem = Items.EMPTY;
                                worldContainer.moveNum = 0;
                            } else {
                                Items moveItemTemp = armor.getItems()[i];
                                short moveNumTemp = armor.getNums()[i];
                                InventoryService.getInstance().removeLocation(armor, i, moveNumTemp);
                                InventoryService.getInstance().addLocation(armor, i, worldContainer.moveItem, worldContainer.moveNum, moveDur);
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                            }
                        } else if (worldContainer.moveItem == Items.EMPTY) {
                            worldContainer.moveItem = armor.getItems()[i];
                            worldContainer.moveNum = armor.getNums()[i];
                            InventoryService.getInstance().removeLocation(armor, i, worldContainer.moveNum);
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
                        worldContainer.inventory.setSelection(InventoryService.getInstance().select2(worldContainer.inventory, ux, worldContainer.inventory.getSelection()));
                    }
                }
            }
        }
        if (mousePos.isReleased()) {
            mousePos.setClicked(false);
        }
        if (checkBlocks) {
            if (worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()] != Items.EMPTY && !worldContainer.player.showTool) {
                worldContainer.player.tool = ImagesContainer.getInstance().itemImgs.get(worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()]);
                for (int i = 0; i < worldContainer.entities.size(); i++) {
                    worldContainer.entities.get(i).setImmune(false);
                }
                worldContainer.player.toolSpeed = worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()].getSpeed();
                if (worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()] == Items.MAGNETITE_PICK || worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()] == Items.MAGNETITE_AXE || worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()] == Items.MAGNETITE_SWORD) {
                    worldContainer.player.toolSpeed *= ((double) worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] / worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()].getDurability()) * (-0.714) + 1;
                }
                worldContainer.player.showTool = true;
                worldContainer.player.toolAngle = 4.7;
                int ux = mousePos2.getX() / WorldContainer.BLOCK_SIZE + blockOffsetU;
                int uy = mousePos2.getY() / WorldContainer.BLOCK_SIZE + blockOffsetV;
                int ux2 = mousePos2.getX() / WorldContainer.BLOCK_SIZE;
                int uy2 = mousePos2.getY() / WorldContainer.BLOCK_SIZE;
                if (Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2)) <= 160 ||
                        Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2 + WIDTH * WorldContainer.BLOCK_SIZE, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy2 * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE / 2, 2)) <= 160 || DebugContext.REACH) {
                    if (Arrays.asList(TOOL_LIST).contains(worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()])) {
                        if (worldContainer.blocks[worldContainer.layer][uy][ux] != Blocks.AIR && worldContainer.blocks[worldContainer.layer][uy][ux].getTools().contains(worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()])) {
                            worldContainer.blocksDirectionsIntensity[uy][ux] = (byte) RandomTool.nextInt(5);
                            worldContainer.drawn[uy][ux] = false;
                            if (ux == worldContainer.mx && uy == worldContainer.my && worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()] == miningTool) {
                                worldContainer.mining += 1;
                                worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] -= 1;
                                breakCurrentBlock(ux, uy);
                                if (worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] <= 0) {
                                    InventoryService.getInstance().removeLocation(worldContainer.inventory, worldContainer.inventory.getSelection(), worldContainer.inventory.getNums()[worldContainer.inventory.getSelection()]);
                                }
                            } else {
                                worldContainer.mining = 1;
                                miningTool = worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()];
                                worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] -= 1;
                                breakCurrentBlock(ux, uy);
                                worldContainer.mx = ux;
                                worldContainer.my = uy;
                                if (worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] <= 0) {
                                    InventoryService.getInstance().removeLocation(worldContainer.inventory, worldContainer.inventory.getSelection(), worldContainer.inventory.getNums()[worldContainer.inventory.getSelection()]);
                                }
                            }
                        }
                    } else if (worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()] == Items.STONE_LIGHTER) {
                        if (worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE || worldContainer.blocks[worldContainer.layer][uy][ux] == Blocks.FURNACE_ON) {
                            if (worldContainer.icmatrix[worldContainer.layer][uy][ux] != null && worldContainer.icmatrix[worldContainer.layer][uy][ux].getType() == ItemType.FURNACE) {
                                worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] -= 1;
                                worldContainer.icmatrix[worldContainer.layer][uy][ux].setFurnaceOn(true);
                                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.FURNACE_ON;
                                worldContainer.addBlockLighting(ux, uy);
                                if (worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] <= 0) {
                                    InventoryService.getInstance().removeLocation(worldContainer.inventory, worldContainer.inventory.getSelection(), worldContainer.inventory.getNums()[worldContainer.inventory.getSelection()]);
                                }
                                worldContainer.rdrawn[uy][ux] = false;
                            } else {
                                if (worldContainer.ic != null && worldContainer.ic.getType() == ItemType.FURNACE) {
                                    worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] -= 1;
                                    worldContainer.ic.setFurnaceOn(true);
                                    worldContainer.blocks[worldContainer.layer][worldContainer.icy][worldContainer.icx] = Blocks.FURNACE_ON;
                                    worldContainer.addBlockLighting(ux, uy);
                                    worldContainer.rdrawn[worldContainer.icy][worldContainer.icx] = false;
                                    if (worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] <= 0) {
                                        InventoryService.getInstance().removeLocation(worldContainer.inventory, worldContainer.inventory.getSelection(), worldContainer.inventory.getNums()[worldContainer.inventory.getSelection()]);
                                    }
                                }
                            }
                        }
                    } else if (worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()] == Items.WRENCH) {
                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayerOn()) {
                            worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] -= 1;
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.increaseZythiumDelayerLevel(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.rdrawn[uy][ux] = false;
                            if (worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] <= 0) {
                                InventoryService.getInstance().removeLocation(worldContainer.inventory, worldContainer.inventory.getSelection(), worldContainer.inventory.getNums()[worldContainer.inventory.getSelection()]);
                            }
                        } else if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer8() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer8On()) {
                            worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] -= 1;
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.resetZythiumDelayerLevel(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.rdrawn[uy][ux] = false;
                            if (worldContainer.inventory.getDurs()[worldContainer.inventory.getSelection()] <= 0) {
                                InventoryService.getInstance().removeLocation(worldContainer.inventory, worldContainer.inventory.getSelection(), worldContainer.inventory.getNums()[worldContainer.inventory.getSelection()]);
                            }
                        }
                    } else if (Blocks.findByIndex(worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()].getBlockIndex()) != Blocks.AIR) {
                        Blocks blockTemp = Blocks.findByIndex(worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()].getBlockIndex());
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
                                !(blockTemp == Blocks.SUNFLOWER_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // sunflower
                                        blockTemp == Blocks.MOONFLOWER_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // moonflower
                                        blockTemp == Blocks.DRYWEED_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.SAND) || // dryweed
                                        blockTemp == Blocks.GREENLEAF_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // greenleaf
                                        blockTemp == Blocks.FROSTLEAF_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.SNOW) || // frostleaf
                                        blockTemp == Blocks.CAVEROOT_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.STONE) || // caveroot
                                        blockTemp == Blocks.SKYBLOSSOM_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.JUNGLE_GRASS) || // skyblossom
                                        blockTemp == Blocks.VOID_ROT_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != Blocks.STONE))) { // void_rot
                            if (!(TORCHESL.get(blockTemp) != null) || uy < HEIGHT - 1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux].isSolid() && blockTemp != Blocks.BUTTON_LEFT || worldContainer.blocks[worldContainer.layer][uy][ux + 1].isSolid() || worldContainer.blocks[worldContainer.layer][uy][ux - 1].isSolid())) {
                                if (TORCHESL.get(blockTemp) != null) {
                                    if (worldContainer.blocks[worldContainer.layer][uy + 1][ux].isSolid() && blockTemp != Blocks.BUTTON_LEFT) {
                                        // do nothing
                                    } else if (worldContainer.blocks[worldContainer.layer][uy][ux - 1].isSolid()) {
                                        blockTemp = TORCHESL.get(blockTemp);
                                    } else if (worldContainer.blocks[worldContainer.layer][uy][ux + 1].isSolid()) {
                                        blockTemp = TORCHESR.get(blockTemp);
                                    }
                                }
                                if (worldContainer.layer == 1 && !DebugContext.GPLACE && blockTemp.isCds()) {
                                    for (int i = 0; i < worldContainer.entities.size(); i++) {
                                        if (worldContainer.entities.get(i).getEntityType() != null && worldContainer.entities.get(i).getRect().intersects(new Rectangle(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                                            blockTemp = Blocks.AIR;
                                        }
                                    }
                                    if (worldContainer.player.occupation.intersects(new Rectangle(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                                        blockTemp = Blocks.AIR;
                                    }
                                }
                                if (blockTemp != Blocks.AIR) {
                                    worldContainer.blocks[worldContainer.layer][uy][ux] = blockTemp;
                                    if (worldContainer.blocks[worldContainer.layer][uy][ux].isReceive()) {
                                        worldContainer.addAdjacentTilesToPQueue(ux, uy);
                                    }
                                    if (worldContainer.blocks[worldContainer.layer][uy][ux].isPower()) {
                                        worldContainer.addBlockPower(ux, uy);
                                    }
                                    if (worldContainer.blocks[worldContainer.layer][uy][ux].isLTrans()) {
                                        worldContainer.removeSunLighting(ux, uy, sunlightlevel);
                                        worldContainer.redoBlockLighting(ux, uy);
                                    }
                                    worldContainer.addBlockLighting(ux, uy);
                                }
                                if (blockTemp != Blocks.AIR) {
                                    InventoryService.getInstance().removeLocation(worldContainer.inventory, worldContainer.inventory.getSelection(), (short) 1);
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
        return false;
    }

    private void handleRightClick() {
        checkBlocks = true;
        if (worldContainer.showInv) {
            for (int ux = 0; ux < 10; ux++) {
                for (int uy = 0; uy < 4; uy++) {
                    if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + 6, uy * 46 + 46)) {
                        checkBlocks = false;
                        if (mousePos2.isClicked()) {
                            mousePos2.setReleased(true);
                            Items moveItemTemp = worldContainer.inventory.getItems()[uy * 10 + ux];
                            short moveNumTemp = (short) (worldContainer.inventory.getNums()[uy * 10 + ux] / 2);
                            short moveDurTemp = worldContainer.inventory.getDurs()[uy * 10 + ux];
                            if (worldContainer.inventory.getItems()[uy * 10 + ux] == Items.EMPTY) {
                                InventoryService.getInstance().addLocation(worldContainer.inventory, uy * 10 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                worldContainer.moveNum -= 1;
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                    moveDur = 0;
                                }
                            } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.inventory.getNums()[uy * 10 + ux] != 1) {
                                InventoryService.getInstance().removeLocation(worldContainer.inventory, uy * 10 + ux, (short) (worldContainer.inventory.getNums()[uy * 10 + ux] / 2));
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                                moveDur = moveDurTemp;
                            } else if (worldContainer.moveItem == worldContainer.inventory.getItems()[uy * 10 + ux] && worldContainer.inventory.getNums()[uy * 10 + ux] < worldContainer.inventory.getItems()[uy * 10 + ux].getMaxStacks()) {
                                InventoryService.getInstance().addLocation(worldContainer.inventory, uy * 10 + ux, worldContainer.moveItem, (short) 1, moveDur);
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
                    if (mousePos.isInBetween(worldContainer.inventory.getImage().getWidth() + ux * 40 + 75, worldContainer.inventory.getImage().getWidth() + ux * 40 + 121, uy * 40 + 52, uy * 40 + 92)) {
                        checkBlocks = false;
                        if (mousePos2.isClicked()) {
                            mousePos2.setReleased(true);
                            Items moveItemTemp = worldContainer.cic.getItems()[uy * 2 + ux];
                            short moveNumTemp = (short) (worldContainer.cic.getNums()[uy * 2 + ux] / 2);
                            if (worldContainer.cic.getItems()[uy * 2 + ux] == Items.EMPTY) {
                                InventoryService.getInstance().addLocation(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                worldContainer.moveNum -= 1;
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                }
                            } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.cic.getNums()[uy * 2 + ux] != 1) {
                                InventoryService.getInstance().removeLocation(worldContainer.cic, uy * 2 + ux, (short) (worldContainer.cic.getNums()[uy * 2 + ux] / 2));
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                            } else if (worldContainer.moveItem == worldContainer.cic.getItems()[uy * 2 + ux] && worldContainer.cic.getNums()[uy * 2 + ux] < worldContainer.cic.getItems()[uy * 2 + ux].getMaxStacks()) {
                                InventoryService.getInstance().addLocation(worldContainer.cic, uy * 2 + ux, worldContainer.moveItem, (short) 1, moveDur);
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
                            if (mousePos.isInBetween(ux * 40 + 6, ux * 40 + 46, uy * 40 + worldContainer.inventory.getImage().getHeight() + 46, uy * 40 + worldContainer.inventory.getImage().getHeight() + 86)) {
                                checkBlocks = false;
                                if (mousePos2.isClicked()) {
                                    mousePos2.setReleased(true);
                                    Items moveItemTemp = worldContainer.ic.getItems()[uy * 3 + ux];
                                    short moveNumTemp = (short) (worldContainer.ic.getNums()[uy * 3 + ux] / 2);
                                    if (worldContainer.ic.getItems()[uy * 3 + ux] == Items.EMPTY) {
                                        InventoryService.getInstance().addLocation(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, (short) 1, moveDur);
                                        worldContainer.moveNum -= 1;
                                        if (worldContainer.moveNum == 0) {
                                            worldContainer.moveItem = Items.EMPTY;
                                        }
                                    } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[uy * 3 + ux] != 1) {
                                        InventoryService.getInstance().removeLocation(worldContainer.ic, uy * 3 + ux, (short) (worldContainer.ic.getNums()[uy * 3 + ux] / 2));
                                        worldContainer.moveItem = moveItemTemp;
                                        worldContainer.moveNum = moveNumTemp;
                                    } else if (worldContainer.moveItem == worldContainer.ic.getItems()[uy * 3 + ux] && worldContainer.ic.getNums()[uy * 3 + ux] < worldContainer.ic.getItems()[uy * 3 + ux].getMaxStacks()) {
                                        if (worldContainer.ic.getItems()[7] == Items.BARK && worldContainer.ic.getNums()[7] == 51 && worldContainer.moveItem == Items.CLAY && uy * 3 + ux == 3 && worldContainer.ic.getNums()[8] == 0) {
                                            InventoryService.getInstance().addLocation(worldContainer.ic, 8, Items.WOODEN_PICK, (short) 1);
                                        } else {
                                            InventoryService.getInstance().addLocation(worldContainer.ic, uy * 3 + ux, worldContainer.moveItem, (short) 1, moveDur);
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
                    if (mousePos.isInBetween(4 * 40 + 6, 4 * 40 + 46, 1 * 40 + worldContainer.inventory.getImage().getHeight() + 46, 1 * 40 + worldContainer.inventory.getImage().getHeight() + 86)) {
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
                    for (int ux = 0; ux < InventoryService.getInstance().CX; ux++) {
                        for (int uy = 0; uy < InventoryService.getInstance().CY; uy++) {
                            if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + worldContainer.inventory.getImage().getHeight() + 46, uy * 46 + worldContainer.inventory.getImage().getHeight() + 86)) {
                                checkBlocks = false;
                                if (mousePos2.isClicked()) {
                                    mousePos2.setReleased(true);
                                    Items moveItemTemp = worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux];
                                    short moveNumTemp = (short) (worldContainer.ic.getNums()[uy * InventoryService.getInstance().CX + ux] / 2);
                                    if (worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux] == Items.EMPTY) {
                                        InventoryService.getInstance().addLocation(worldContainer.ic, uy * InventoryService.getInstance().CX + ux, worldContainer.moveItem, (short) 1, moveDur);
                                        worldContainer.moveNum -= 1;
                                        if (worldContainer.moveNum == 0) {
                                            worldContainer.moveItem = Items.EMPTY;
                                        }
                                    } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[uy * InventoryService.getInstance().CX + ux] != 1) {
                                        InventoryService.getInstance().removeLocation(worldContainer.ic, uy * InventoryService.getInstance().CX + ux, (short) (worldContainer.ic.getNums()[uy * InventoryService.getInstance().CX + ux] / 2));
                                        worldContainer.moveItem = moveItemTemp;
                                        worldContainer.moveNum = moveNumTemp;
                                    } else if (worldContainer.moveItem == worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux] && worldContainer.ic.getNums()[uy * InventoryService.getInstance().CX + ux] < worldContainer.ic.getItems()[uy * InventoryService.getInstance().CX + ux].getMaxStacks()) {
                                        InventoryService.getInstance().addLocation(worldContainer.ic, uy * InventoryService.getInstance().CX + ux, worldContainer.moveItem, (short) 1, moveDur);
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
                    if (mousePos.isInBetween(6, 46, worldContainer.inventory.getImage().getHeight() + 46, worldContainer.inventory.getImage().getHeight() + 86)) {
                        checkBlocks = false;
                        if (mousePos2.isClicked()) {
                            mousePos2.setReleased(true);
                            Items moveItemTemp = worldContainer.ic.getItems()[0];
                            short moveNumTemp = (short) (worldContainer.ic.getNums()[0] / 2);
                            if (worldContainer.ic.getItems()[0] == Items.EMPTY) {
                                InventoryService.getInstance().addLocation(worldContainer.ic, 0, worldContainer.moveItem, (short) 1, moveDur);
                                worldContainer.moveNum -= 1;
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                }
                            } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[0] != 1) {
                                InventoryService.getInstance().removeLocation(worldContainer.ic, 0, (short) (worldContainer.ic.getNums()[0] / 2));
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                            } else if (worldContainer.moveItem == worldContainer.ic.getItems()[0] && worldContainer.ic.getNums()[0] < worldContainer.ic.getItems()[0].getMaxStacks()) {
                                InventoryService.getInstance().addLocation(worldContainer.ic, 0, worldContainer.moveItem, (short) 1, moveDur);
                                worldContainer.moveNum -= 1;
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                }
                            }
                        }
                    }
                    if (mousePos.isInBetween(6, 46, worldContainer.inventory.getImage().getHeight() + 142, worldContainer.inventory.getImage().getHeight() + 182)) {
                        checkBlocks = false;
                        if (mousePos2.isClicked()) {
                            mousePos2.setReleased(true);
                            Items moveItemTemp = worldContainer.ic.getItems()[2];
                            short moveNumTemp = (short) (worldContainer.ic.getNums()[2] / 2);
                            if (worldContainer.ic.getItems()[2] == Items.EMPTY) {
                                InventoryService.getInstance().addLocation(worldContainer.ic, 2, worldContainer.moveItem, (short) 1, moveDur);
                                worldContainer.moveNum -= 1;
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                }
                            } else if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[2] != 1) {
                                InventoryService.getInstance().removeLocation(worldContainer.ic, 2, (short) (worldContainer.ic.getNums()[2] / 2));
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
                            } else if (worldContainer.moveItem == worldContainer.ic.getItems()[2] && worldContainer.ic.getNums()[2] < worldContainer.ic.getItems()[2].getMaxStacks()) {
                                InventoryService.getInstance().addLocation(worldContainer.ic, 2, worldContainer.moveItem, (short) 1, moveDur);
                                worldContainer.moveNum -= 1;
                                if (worldContainer.moveNum == 0) {
                                    worldContainer.moveItem = Items.EMPTY;
                                }
                            }
                        }
                    }
                    if (mousePos.isInBetween(62, 102, worldContainer.inventory.getImage().getHeight() + 46, worldContainer.inventory.getImage().getHeight() + 86)) {
                        checkBlocks = false;
                        if (mousePos2.isClicked()) {
                            mousePos2.setReleased(true);
                            Items moveItemTemp = worldContainer.ic.getItems()[3];
                            short moveNumTemp = (short) (worldContainer.ic.getNums()[3] / 2);
                            if (worldContainer.moveItem == Items.EMPTY && worldContainer.ic.getNums()[3] != 1) {
                                InventoryService.getInstance().removeLocation(worldContainer.ic, 3, (short) (worldContainer.ic.getNums()[3] / 2));
                                worldContainer.moveItem = moveItemTemp;
                                worldContainer.moveNum = moveNumTemp;
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
                                worldContainer.icmatrix[worldContainer.layer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                            } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                                if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                                    for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                                        if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                            worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                        }
                                    }
                                }
                                if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                                    for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                                        if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                            worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                        }
                                    }
                                }
                            }
                            if (worldContainer.ic.getType() == ItemType.FURNACE) {
                                worldContainer.updateFurnaceState();
                            }
                            worldContainer.ic = null;
                        }
                        for (int l = 0; l < LAYER_SIZE; l++) {
                            if (worldContainer.blocks[l][uy][ux] == Blocks.WORKBENCH) {
                                worldContainer.setItemCollection(ItemType.WORKBENCH, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.WOODEN_CHEST) {
                                worldContainer.setItemCollection(ItemType.WOODEN_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.STONE_CHEST) {
                                worldContainer.setItemCollection(ItemType.STONE_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.COPPER_CHEST) {
                                worldContainer.setItemCollection(ItemType.COPPER_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.IRON_CHEST) {
                                worldContainer.setItemCollection(ItemType.IRON_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.SILVER_CHEST) {
                                worldContainer.setItemCollection(ItemType.SILVER_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.GOLD_CHEST) {
                                worldContainer.setItemCollection(ItemType.GOLD_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.ZINC_CHEST) {
                                worldContainer.setItemCollection(ItemType.ZINC_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.RHYMESTONE_CHEST) {
                                worldContainer.setItemCollection(ItemType.RHYMESTONE_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.OBDURITE_CHEST) {
                                worldContainer.setItemCollection(ItemType.OBDURITE_CHEST, l, ux, uy);
                            } else if (worldContainer.blocks[l][uy][ux] == Blocks.FURNACE || worldContainer.blocks[l][uy][ux] == Blocks.FURNACE_ON) {
                                if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.FURNACE) {
                                    worldContainer.ic = new ItemCollection(ItemType.FURNACE, worldContainer.icmatrix[l][uy][ux].getItems(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    worldContainer.ic.setFUELP(worldContainer.icmatrix[l][uy][ux].getFUELP());
                                    worldContainer.ic.setSMELTP(worldContainer.icmatrix[l][uy][ux].getSMELTP());
                                    worldContainer.ic.setFurnaceOn(worldContainer.icmatrix[l][uy][ux].isFurnaceOn());
                                    worldContainer.icmatrix[l][uy][ux] = null;
                                } else {
                                    worldContainer.ic = new ItemCollection(ItemType.FURNACE);
                                }
                                worldContainer.icx = ux;
                                worldContainer.icy = uy;
                                InventoryService.getInstance().renderCollection(worldContainer.ic);
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
                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isLever()) {
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnLeverOn(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.addBlockPower(ux, uy);
                            worldContainer.rdrawn[uy][ux] = false;
                        } else if (worldContainer.blocks[worldContainer.layer][uy][ux].isLeverOn()) {
                            worldContainer.removeBlockPower(ux, uy, worldContainer.layer);
                            if (worldContainer.wcnct[uy][ux]) {
                                for (int l = 0; l < LAYER_SIZE; l++) {
                                    if (l != worldContainer.layer) {
                                        worldContainer.rbpRecur(ux, uy, l);
                                    }
                                }
                            }
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnLeverOff(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.rdrawn[uy][ux] = false;
                        }
                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumWire()) {
                            worldContainer.wcnct[uy][ux] = !worldContainer.wcnct[uy][ux];
                            worldContainer.rdrawn[uy][ux] = false;
                            worldContainer.redoBlockPower(ux, uy, worldContainer.layer);
                        }
                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isCompleteZythiumAmplifier()) {
                            worldContainer.removeBlockPower(ux, uy, worldContainer.layer);
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnZythiumAmplifier(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                            worldContainer.rdrawn[uy][ux] = false;
                            worldContainer.addAdjacentTilesToPQueueConditionally(ux, uy);
                        }
                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumInverterAll() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumInverterOnAll()) {
                            worldContainer.removeBlockPower(ux, uy, worldContainer.layer);
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnZythiumInverter(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                            worldContainer.rdrawn[uy][ux] = false;
                            worldContainer.addAdjacentTilesToPQueueConditionally(ux, uy);
                        }
                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isButton()) {
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnButtonOn(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.addBlockPower(ux, uy);
                            worldContainer.rdrawn[uy][ux] = false;
                            log.info("Srsly?");
                            worldContainer.updatex.add(ux);
                            worldContainer.updatey.add(uy);
                            worldContainer.updatet.add(50);
                            worldContainer.updatel.add(worldContainer.layer);
                        }
                        if (worldContainer.blocks[worldContainer.layer][uy][ux].isCompleteZythiumDelayer()) {
                            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.turnZythiumDelayer(worldContainer.blocks[worldContainer.layer][uy][ux]);
                            worldContainer.blocksDirections[worldContainer.layer] = OutlinesService.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blocksDirections[worldContainer.layer], ux, uy);
                            worldContainer.rdrawn[uy][ux] = false;
                            worldContainer.redoBlockPower(ux, uy, worldContainer.layer);
                        }
                    }
                }
            }
        }
        if (mousePos2.isReleased()) {
            mousePos2.setClicked(false);
        }
    }

    private void breakCurrentBlock(int ux, int uy) {
        if (DebugContext.INSTAMINE || worldContainer.mining >= DURABILITY.get(worldContainer.inventory.getItems()[worldContainer.inventory.getSelection()]).get(worldContainer.blocks[worldContainer.layer][uy][ux])) {
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
                    for (int i = 0; i < worldContainer.ic.getItems().length; i++) {
                        if (worldContainer.ic.getItems()[i] != Items.EMPTY && !(worldContainer.ic.getType() == ItemType.FURNACE && i == 1)) {
                            worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i]));
                        }
                    }
                }
                if (worldContainer.icmatrix[worldContainer.layer][uy][ux] != null) {
                    for (int i = 0; i < worldContainer.icmatrix[worldContainer.layer][uy][ux].getItems().length; i++) {
                        if (worldContainer.icmatrix[worldContainer.layer][uy][ux].getItems()[i] != Items.EMPTY && !(worldContainer.icmatrix[worldContainer.layer][uy][ux].getType() == ItemType.FURNACE && i == 1)) {
                            worldContainer.entities.add(new Entity(ux * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.icmatrix[worldContainer.layer][uy][ux].getItems()[i], worldContainer.icmatrix[worldContainer.layer][uy][ux].getNums()[i], worldContainer.icmatrix[worldContainer.layer][uy][ux].getDurs()[i]));
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
            worldContainer.removeBlockLighting(ux, uy);
            Blocks blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
            worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.AIR;
            if (blockTemp.isZythiumWire()) {
                worldContainer.redoBlockPower(ux, uy, worldContainer.layer);
            }
            if (blockTemp.isPower()) {
                worldContainer.removeBlockPower(ux, uy, worldContainer.layer);
            }
            if (blockTemp.isLTrans()) {
                worldContainer.addSunLighting(ux, uy, sunlightlevel);
                worldContainer.redoBlockLighting(ux, uy);
            }
            worldContainer.addSunLighting(ux, uy, sunlightlevel);
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
                if (TORCHESR.get(Blocks.findByIndex(worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops().getBlockIndex())) != null && TORCHESR.get(Blocks.findByIndex(worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops().getBlockIndex())) == worldContainer.blocks[worldContainer.layer][uy][ux - 1] || (worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops() == Items.LEVER || worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops() == Items.BUTTON)) {
                    worldContainer.entities.add(new Entity((ux - 1) * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops(), (short) 1));
                    worldContainer.removeBlockLighting(ux - 1, uy);
                    if (worldContainer.layer == 1) {
                        worldContainer.addSunLighting(ux - 1, uy, sunlightlevel);
                    }
                    blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux - 1];
                    worldContainer.blocks[worldContainer.layer][uy][ux - 1] = Blocks.AIR;
                    if (blockTemp.isZythiumWire()) {
                        worldContainer.redoBlockPower(ux, uy, worldContainer.layer);
                    }
                    if (blockTemp.isPower()) {
                        worldContainer.removeBlockPower(ux, uy, worldContainer.layer);
                    }
                    worldContainer.drawn[uy][ux - 1] = false;
                }
                if (TORCHESL.get(Blocks.findByIndex(worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops().getBlockIndex())) != null && TORCHESL.get(Blocks.findByIndex(worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops().getBlockIndex())) == worldContainer.blocks[worldContainer.layer][uy][ux + 1] || (worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops() == Items.LEVER || worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops() == Items.BUTTON)) {
                    worldContainer.entities.add(new Entity((ux + 1) * WorldContainer.BLOCK_SIZE, uy * WorldContainer.BLOCK_SIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops(), (short) 1));
                    worldContainer.removeBlockLighting(ux + 1, uy);
                    if (worldContainer.layer == 1) {
                        worldContainer.addSunLighting(ux + 1, uy, sunlightlevel);
                    }
                    blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux + 1];
                    worldContainer.blocks[worldContainer.layer][uy][ux + 1] = Blocks.AIR;
                    if (blockTemp.isZythiumWire()) {
                        worldContainer.redoBlockPower(ux, uy, worldContainer.layer);
                    }
                    if (blockTemp.isPower()) {
                        worldContainer.removeBlockPower(ux, uy, worldContainer.layer);
                    }
                    worldContainer.drawn[uy][ux + 1] = false;
                }
                uy -= 1;
                if (uy == -1 || !worldContainer.blocks[worldContainer.layer][uy][ux].isGSupport()) {
                    worldContainer.addSunLighting(ux, uy, sunlightlevel);
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
                worldContainer.removeBlockLighting(ux, uy);
                blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
                worldContainer.blocks[worldContainer.layer][uy][ux] = Blocks.AIR;
                if (blockTemp.isZythiumWire()) {
                    worldContainer.redoBlockPower(ux, uy, worldContainer.layer);
                }
                if (blockTemp.isPower()) {
                    worldContainer.removeBlockPower(ux, uy, worldContainer.layer);
                }
                if (blockTemp.isLTrans()) {
                    worldContainer.addSunLighting(ux, uy, sunlightlevel);
                    worldContainer.redoBlockLighting(ux, uy);
                }
                worldContainer.addSunLighting(ux, uy, sunlightlevel);
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

    private void updateEnvironment() {
        TimeService.getInstance().increaseTime();

        for (int i = worldContainer.cloudsAggregate.getClouds().size() - 1; i > -1; i--) {
            worldContainer.cloudsAggregate.updateXUponSpeed(i);
            if (worldContainer.cloudsAggregate.isOutsideBounds(i, getWidth())) {
                worldContainer.cloudsAggregate.removeCloud(i);
            }
        }
        if (RandomTool.nextInt((int) (1500 / DebugContext.ACCEL)) == 0) {

            Cloud cloud = new Cloud();
            BufferedImage cloudImage = ImagesContainer.getInstance().cloudsImage;
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

    public void paint(Graphics g) {
        if (screen == null) {
            return;
        }

        paintService.paint(this, g);
    }

    public BufferedImage loadBlock(Blocks type, Directions direction, Byte directionIntensity, Byte textureIntensity, String outlineName, int x, int y, int lyr) {
        BufferedImage outline = ImagesContainer.getInstance().outlineImgs.get("outlines/" + outlineName + "/" + direction.getFileName() + (directionIntensity + 1) + ".png");
        String bName = type.getFileName();
        BufferedImage texture = ImagesContainer.getInstance().blockImgs.get("blocks/" + bName + "/texture" + (textureIntensity + 1) + ".png");
        BufferedImage image = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT);
        if (GRASSDIRT.get(type) != null) {
            BufferedImage dirtOriginal = ImagesContainer.getInstance().blockImgs.get("blocks/" + GRASSDIRT.get(type).getFileName() + "/texture" + (textureIntensity + 1) + ".png");
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
                        worldContainer.icmatrix[worldContainer.layer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                    } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                        if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                            for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                                if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                    worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, 2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                }
                            }
                        }
                        if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                            for (int i = 0; i < ItemType.WORKBENCH.getItemCollectionIterable(); i++) {
                                if (worldContainer.ic.getItems()[i] != Items.EMPTY) {
                                    worldContainer.entities.add(new Entity(worldContainer.icx * WorldContainer.BLOCK_SIZE, worldContainer.icy * WorldContainer.BLOCK_SIZE, -2, -2, worldContainer.ic.getItems()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                }
                            }
                        }
                    }
                    if (worldContainer.ic.getType() == ItemType.FURNACE) {
                        worldContainer.updateFurnaceState();
                    }

                    worldContainer.ic = null;
                } else {
                    if (worldContainer.showInv) {
                        for (int i = 0; i < 4; i++) {
                            if (worldContainer.cic.getItems()[i] != Items.EMPTY) {
                                if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, 2, -2, worldContainer.cic.getItems()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                                }
                                if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                                    worldContainer.entities.add(new Entity(worldContainer.player.x, worldContainer.player.y, -2, -2, worldContainer.cic.getItems()[i], worldContainer.cic.getNums()[i], worldContainer.cic.getDurs()[i], 75));
                                }
                                InventoryService.getInstance().removeLocation(worldContainer.cic, i, worldContainer.cic.getNums()[i]);
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
            if (!worldContainer.player.showTool) {
                if (key.getKeyCode() >= KeyEvent.VK_0 && key.getKeyCode() <= KeyEvent.VK_9) {
                    worldContainer.inventory.setSelection(InventoryService.getInstance().select(worldContainer.inventory, key.getKeyCode() - KeyEvent.VK_0, worldContainer.inventory.getSelection()));
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
        log.info("Handling key released");
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


    public void keyTyped(KeyEvent key) {
        //
    }

    public void mouseEntered(MouseEvent e) {
        //
    }

    public void mouseExited(MouseEvent e) {
        //
    }

}
