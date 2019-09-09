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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sergio.refacto.dto.BlockNames;
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
import com.sergio.refacto.items.World;
import com.sergio.refacto.items.WorldContainer;
import com.sergio.refacto.services.WorldService;
import com.sergio.refacto.tools.MathTool;
import com.sergio.refacto.tools.RandomTool;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.extern.slf4j.Slf4j;

import static com.sergio.refacto.dto.Constants.*;

@Slf4j
public class TerrariaClone extends JApplet implements ChangeListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private WorldService worldService = new WorldService();

    static GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    BufferedImage screen;
    Color bg;

    int size = CHUNKBLOCKS * 2;

    int[][] cl = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    javax.swing.Timer timer, menuTimer;
    List<FileInfo> filesInfo;
    String currentWorld;
    TextField newWorldName;

    private WorldContainer worldContainer;

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
    BufferedImage layerImg;

    Entity entity;
    State state = State.LOADING_GRAPHICS;
    EntityType mobSpawn;

    int u, v, ou, ov, uNew, vNew;
    int x, y, i, j, tx, ty, twx, twy, tlx, tly, ux, uy, ux2, uy2, ulx, uly, ulx2, uly2, ucx, ucy, pwx, pwy, n, dx, dy, dx2, dy2, ax, ay, axl, ayl, vc, xpos, ypos, xpos2, ypos2, x2, y2, rnum, width, height, xmax, ymax;
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

    BufferedImage sun, moon, cloudImage, logo_white, logo_black, title_screen, select_world, new_world, save_exit;
    BufferedImage[] cloudsImages = { ResourcesLoader.loadImage("environment/cloud1.png") };
    BufferedImage wcnct_px = ResourcesLoader.loadImage("misc/wcnct.png");

    javax.swing.Timer createWorldTimer;
    KeyPressed keyPressed;
    MousePressed mousePressed;

    boolean checkBlocks = true;
    boolean addSources = false;
    boolean keepLeaf = false;
    boolean newWorldFocus = false;
    boolean menuPressed = false;
    boolean doGenerateWorld = true;
    boolean doGrassGrow = false;

    public static final int CHUNKBLOCKS = 96;
    public static final int BLOCKSIZE = 16;
    private static final int IMAGESIZE = 8;
    private static final int CHUNKSIZE = CHUNKBLOCKS * BLOCKSIZE;
    private static final int PLAYERSIZEX = 20;
    private static final int PLAYERSIZEY = 46;

    private static final int BRIGHTEST = 21;

    int sunlightlevel = 19;

    BufferedImage tool;

    static Map<Byte, BufferedImage> backgroundImgs;
    static Map<Items, BufferedImage> itemImgs;
    static Map<Short, Map<Integer, Integer>> DURABILITY;
    static Map<EntityType, String> UIENTITIES;
    static Map<Integer, Color> SKYCOLORS;
    static Map<Integer, BufferedImage> LIGHTLEVELS;
    static Map<String, BufferedImage> blockImgs;
    static Map<String, BufferedImage> outlineImgs;
    static Map<Integer, Integer> GRASSDIRT;
    static Map<Short, Double> FUELS;
    static Map<Integer, BlockNames> WIREP;
    static Map<BlockNames, BlockNames> TORCHESL;
    static Map<BlockNames, BlockNames> TORCHESR;
    static Map<Integer, Integer> DDELAY;

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

            logo_white = ResourcesLoader.loadImage("interface/logo_white.png");
            logo_black = ResourcesLoader.loadImage("interface/logo_black.png");
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

            sun = ResourcesLoader.loadImage("environment/sun.png");
            moon = ResourcesLoader.loadImage("environment/moon.png");
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

            menuTimer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        if (mousePressed == MousePressed.LEFT_MOUSE) {
                            Action mainThread = new AbstractAction() {
                                public void actionPerformed(ActionEvent ae) {
                                    try {
                                        if (worldContainer.ready) {
                                            worldContainer.ready = false;
                                            uNew = (int) ((worldContainer.player.x - getWidth() / 2 + worldContainer.player.width) / (double) CHUNKSIZE);
                                            vNew = (int) ((worldContainer.player.y - getHeight() / 2 + worldContainer.player.height) / (double) CHUNKSIZE);
                                            if (ou != uNew || ov != vNew) {
                                                ou = uNew;
                                                ov = vNew;
                                                List<Chunk> chunkTemp = new ArrayList<>(0);
                                                for (twy = 0; twy < 2; twy++) {
                                                    for (twx = 0; twx < 2; twx++) {
                                                        if (chunkMatrix[twy][twx] != null) {
                                                            chunkTemp.add(chunkMatrix[twy][twx]);
                                                            chunkMatrix[twy][twx] = null;
                                                        }
                                                    }
                                                }
                                                for (twy = 0; twy < 2; twy++) {
                                                    for (twx = 0; twx < 2; twx++) {
                                                        for (i = chunkTemp.size() - 1; i > -1; i--) {
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
                                                for (i = 0; i < chunkTemp.size(); i++) {
                                                    temporarySaveFile[twy][twx] = chunkTemp.get(i);
                                                }
                                                chunkTemp = null;
                                                for (twy = 0; twy < 2; twy++) {
                                                    for (twx = 0; twx < 2; twx++) {
                                                        for (y = 0; y < CHUNKBLOCKS; y++) {
                                                            for (x = 0; x < CHUNKBLOCKS; x++) {
                                                                for (int l = 0; l < 3; l++) {
                                                                    worldContainer.blocks[l][twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getBlocks()[l][y][x];
                                                                    worldContainer.power[l][twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getPower()[l][y][x];
                                                                    pzqn[l][twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getPzqn()[l][y][x];
                                                                    arbprd[l][twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getArbprd()[l][y][x];
                                                                    worldContainer.blockds[l][twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getBlockds()[l][y][x];
                                                                }
                                                                worldContainer.blockdns[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getBlockdns()[y][x];
                                                                worldContainer.blockbgs[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getBlockbgs()[y][x];
                                                                worldContainer.blockts[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getBlockts()[y][x];
                                                                worldContainer.lights[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getLights()[y][x];
                                                                worldContainer.lsources[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getLsources()[y][x];
                                                                zqn[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getZqn()[y][x];
                                                                wcnct[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getWcnct()[y][x];
                                                                worldContainer.drawn[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getDrawn()[y][x];
                                                                worldContainer.rdrawn[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getRdrawn()[y][x];
                                                                worldContainer.ldrawn[twy * CHUNKBLOCKS + y][twx * CHUNKBLOCKS + x] = chunkMatrix[twy][twx].getLdrawn()[y][x];
                                                            }
                                                        }
                                                        worldContainer.worlds[twy][twx] = null;
                                                    }
                                                }
                                            }
                                            u = -ou * CHUNKBLOCKS;
                                            v = -ov * CHUNKBLOCKS;
                                            for (twy = 0; twy < 2; twy++) {
                                                for (twx = 0; twx < 2; twx++) {
                                                    worldContainer.kworlds[twy][twx] = false;
                                                }
                                            }
                                            boolean somevar = false;
                                            for (twy = 0; twy < 2; twy++) {
                                                for (twx = 0; twx < 2; twx++) {
                                                    int twxc = twx + ou;
                                                    int twyc = twy + ov;
                                                    if (((worldContainer.player.ix + getWidth() / 2 + worldContainer.player.width >= twxc * CHUNKSIZE &&
                                                            worldContainer.player.ix + getWidth() / 2 + worldContainer.player.width <= twxc * CHUNKSIZE + CHUNKSIZE) ||
                                                            (worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width + BLOCKSIZE >= twxc * CHUNKSIZE &&
                                                                    worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width - BLOCKSIZE <= twxc * CHUNKSIZE + CHUNKSIZE)) &&
                                                            ((worldContainer.player.iy + getHeight() / 2 + worldContainer.player.height >= twyc * CHUNKSIZE &&
                                                                    worldContainer.player.iy + getHeight() / 2 + worldContainer.player.height <= twyc * CHUNKSIZE + CHUNKSIZE) ||
                                                                    (worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height >= twyc * CHUNKSIZE &&
                                                                            worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height <= twyc * CHUNKSIZE + CHUNKSIZE))) {
                                                        worldContainer.kworlds[twy][twx] = true;
                                                        if (worldContainer.worlds[twy][twx] == null) {
                                                            worldContainer.worlds[twy][twx] = config.createCompatibleImage(CHUNKSIZE, CHUNKSIZE, Transparency.TRANSLUCENT);
                                                            worldContainer.fworlds[twy][twx] = config.createCompatibleImage(CHUNKSIZE, CHUNKSIZE, Transparency.TRANSLUCENT);
                                                            log.info("Created image at " + twx + " " + twy);
                                                        }
                                                        if (worldContainer.worlds[twy][twx] != null) {
                                                            wg2 = worldContainer.worlds[twy][twx].createGraphics();
                                                            fwg2 = worldContainer.fworlds[twy][twx].createGraphics();
                                                            for (tly = Math.max(twy * CHUNKSIZE, (int) (worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height / 2 + v * BLOCKSIZE) - 64); tly < Math.min(twy * CHUNKSIZE + CHUNKSIZE, (int) (worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2 + v * BLOCKSIZE) + 128); tly += BLOCKSIZE) {
                                                                for (tlx = Math.max(twx * CHUNKSIZE, (int) (worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width / 2 + u * BLOCKSIZE) - 64); tlx < Math.min(twx * CHUNKSIZE + CHUNKSIZE, (int) (worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 + u * BLOCKSIZE) + 112); tlx += BLOCKSIZE) {
                                                                    tx = (int) (tlx / BLOCKSIZE);
                                                                    ty = (int) (tly / BLOCKSIZE);
                                                                    if (tx >= 0 && tx < size && ty >= 0 && ty < size) {
                                                                        if (!worldContainer.drawn[ty][tx]) {
                                                                            somevar = true;
                                                                            worldContainer.blockts[ty][tx] = (byte) RandomTool.nextInt(8);
                                                                            for (y = 0; y < BLOCKSIZE; y++) {
                                                                                for (x = 0; x < BLOCKSIZE; x++) {
                                                                                    try {
                                                                                        worldContainer.worlds[twy][twx].setRGB(tx * BLOCKSIZE - twxc * CHUNKSIZE + x, ty * BLOCKSIZE - twyc * CHUNKSIZE + y, 9539985);
                                                                                        worldContainer.fworlds[twy][twx].setRGB(tx * BLOCKSIZE - twxc * CHUNKSIZE + x, ty * BLOCKSIZE - twyc * CHUNKSIZE + y, 9539985);
                                                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                                                        //
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (worldContainer.blockbgs[ty][tx] != 0) {
                                                                                wg2.drawImage(backgroundImgs.get(worldContainer.blockbgs[ty][tx]),
                                                                                        tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                        0, 0, IMAGESIZE, IMAGESIZE,
                                                                                        null);
                                                                            }
                                                                            for (int l = 0; l < 3; l++) {
                                                                                if (worldContainer.blocks[l][ty][tx] != BlockNames.AIR) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blockds[l][ty][tx], worldContainer.blockdns[ty][tx], worldContainer.blockts[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    } else {
                                                                                        wg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blockds[l][ty][tx], worldContainer.blockdns[ty][tx], worldContainer.blockts[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    }
                                                                                }
                                                                                if (wcnct[ty][tx] && worldContainer.blocks[l][ty][tx].isZythiumWire()) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    } else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DebugContext.LIGHT) {
                                                                                fwg2.drawImage(LIGHTLEVELS.get((int) (float) worldContainer.lights[ty][tx]),
                                                                                        tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                        0, 0, IMAGESIZE, IMAGESIZE,
                                                                                        null);
                                                                            }
                                                                            worldContainer.drawn[ty][tx] = true;
                                                                            worldContainer.rdrawn[ty][tx] = true;
                                                                            worldContainer.ldrawn[ty][tx] = true;
                                                                        }
                                                                        if (!worldContainer.rdrawn[ty][tx]) {
                                                                            somevar = true;
                                                                            for (y = 0; y < BLOCKSIZE; y++) {
                                                                                for (x = 0; x < BLOCKSIZE; x++) {
                                                                                    try {
                                                                                        worldContainer.worlds[twy][twx].setRGB(tx * BLOCKSIZE - twxc * CHUNKSIZE + x, ty * BLOCKSIZE - twyc * CHUNKSIZE + y, 9539985);
                                                                                        worldContainer.fworlds[twy][twx].setRGB(tx * BLOCKSIZE - twxc * CHUNKSIZE + x, ty * BLOCKSIZE - twyc * CHUNKSIZE + y, 9539985);
                                                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                                                        //
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (worldContainer.blockbgs[ty][tx] != 0) {
                                                                                wg2.drawImage(backgroundImgs.get(worldContainer.blockbgs[ty][tx]),
                                                                                        tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                        0, 0, IMAGESIZE, IMAGESIZE,
                                                                                        null);
                                                                            }
                                                                            for (int l = 0; l < 3; l++) {
                                                                                if (worldContainer.blocks[l][ty][tx] != BlockNames.AIR) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blockds[l][ty][tx], worldContainer.blockdns[ty][tx], worldContainer.blockts[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    } else {
                                                                                        wg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blockds[l][ty][tx], worldContainer.blockdns[ty][tx], worldContainer.blockts[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    }
                                                                                }
                                                                                if (wcnct[ty][tx] && worldContainer.blocks[l][ty][tx].isZythiumWire()) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    } else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DebugContext.LIGHT) {
                                                                                fwg2.drawImage(LIGHTLEVELS.get((int) (float) worldContainer.lights[ty][tx]),
                                                                                        tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                        0, 0, IMAGESIZE, IMAGESIZE,
                                                                                        null);
                                                                            }
                                                                            worldContainer.drawn[ty][tx] = true;
                                                                            worldContainer.rdrawn[ty][tx] = true;
                                                                            worldContainer.ldrawn[ty][tx] = true;
                                                                        }
                                                                        if (!worldContainer.ldrawn[ty][tx] && RandomTool.nextInt(10) == 0) {
                                                                            somevar = true;
                                                                            for (y = 0; y < BLOCKSIZE; y++) {
                                                                                for (x = 0; x < BLOCKSIZE; x++) {
                                                                                    try {
                                                                                        worldContainer.worlds[twy][twx].setRGB(tx * BLOCKSIZE - twxc * CHUNKSIZE + x, ty * BLOCKSIZE - twyc * CHUNKSIZE + y, 9539985);
                                                                                        worldContainer.fworlds[twy][twx].setRGB(tx * BLOCKSIZE - twxc * CHUNKSIZE + x, ty * BLOCKSIZE - twyc * CHUNKSIZE + y, 9539985);
                                                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                                                        //
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (worldContainer.blockbgs[ty][tx] != 0) {
                                                                                wg2.drawImage(backgroundImgs.get(worldContainer.blockbgs[ty][tx]),
                                                                                        tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                        0, 0, IMAGESIZE, IMAGESIZE,
                                                                                        null);
                                                                            }
                                                                            for (int l = 0; l < 3; l++) {
                                                                                if (worldContainer.blocks[l][ty][tx] != BlockNames.AIR) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blockds[l][ty][tx], worldContainer.blockdns[ty][tx], worldContainer.blockts[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    } else {
                                                                                        wg2.drawImage(loadBlock(worldContainer.blocks[l][ty][tx], worldContainer.blockds[l][ty][tx], worldContainer.blockdns[ty][tx], worldContainer.blockts[ty][tx], worldContainer.blocks[l][ty][tx].getOutline(), tx, ty, l),
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    }
                                                                                }
                                                                                if (wcnct[ty][tx] && worldContainer.blocks[l][ty][tx].isZythiumWire()) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    } else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                                tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
                                                                                                0, 0, IMAGESIZE, IMAGESIZE,
                                                                                                null);
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DebugContext.LIGHT) {
                                                                                fwg2.drawImage(LIGHTLEVELS.get((int) (float) worldContainer.lights[ty][tx]),
                                                                                        tx * BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE - twy * CHUNKSIZE, tx * BLOCKSIZE + BLOCKSIZE - twx * CHUNKSIZE, ty * BLOCKSIZE + BLOCKSIZE - twy * CHUNKSIZE,
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
                                            for (twy = 0; twy < 2; twy++) {
                                                for (twx = 0; twx < 2; twx++) {
                                                    if (!worldContainer.kworlds[twy][twx] && worldContainer.worlds[twy][twx] != null) {
                                                        worldContainer.worlds[twy][twx] = null;
                                                        worldContainer.fworlds[twy][twx] = null;
                                                        for (ty = twy * CHUNKBLOCKS; ty < twy * CHUNKBLOCKS + CHUNKBLOCKS; ty++) {
                                                            for (tx = twx * CHUNKBLOCKS; tx < twx * CHUNKBLOCKS + CHUNKBLOCKS; tx++) {
                                                                if (tx >= 0 && tx < size && ty >= 0 && ty < size) {
                                                                    worldContainer.drawn[ty][tx] = false;
                                                                }
                                                            }
                                                        }
                                                        log.info("Destroyed image at " + twx + " " + twy);
                                                    }
                                                }
                                            }
                                            updateApp();
                                            updateEnvironment();
                                            worldContainer.player.update(worldContainer.blocks[1], keyPressed, u, v);
                                            if (worldContainer.timeOfDay >= 86400) {
                                                worldContainer.timeOfDay = 0;
                                                worldContainer.day += 1;
                                            }
                                            repaint();
                                            worldContainer.ready = true;
                                        }
                                    } catch (Exception e) {
                                        postError(e);
                                    }
                                }
                            };
                            timer = new javax.swing.Timer(20, mainThread);

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
                                for (i = 0; i < filesInfo.size(); i++) {
                                    if (mousePos.isInBetweenInclusive(166, 470, 117 + i * 35, 152 + i * 35)) { // load world
                                        currentWorld = filesInfo.get(i).getName();
                                        state = State.LOADING_WORLD;
                                        bg = Color.BLACK;
                                        if (worldContainer.loadWorld(filesInfo.get(i).getFileName())) {
                                            menuTimer.stop();
                                            bg = CYANISH;
                                            state = State.IN_GAME;
                                            worldContainer.ready = true;
                                            timer.start();
                                            break;
                                        }
                                    }
                                }
                            }
                            if (state == State.NEW_WORLD && !menuPressed) {
                                if (mousePos.isInBetweenInclusive(ItemPositionInScreen.CREATE_NEW_WORLD_MENU)) {
                                    if (!newWorldName.getText().equals("")) {
                                        filesInfo = worldService.findWorlds();
                                        doGenerateWorld = true;
                                        for (i = 0; i < filesInfo.size(); i++) {
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
                                                        timer.start();
                                                        createWorldTimer.stop();
                                                    } catch (Exception e) {
                                                        postError(e);
                                                    }
                                                }
                                            };
                                            createWorldTimer = new javax.swing.Timer(1, createWorldThread);
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
                    } catch (Exception e) {
                        postError(e);
                    }
                }
            });

            menuTimer.start();
        } catch (Exception e) {
            postError(e);
        }
    }

    public void createNewWorld() {
        temporarySaveFile = new Chunk[worldContainer.WORLDHEIGHT][worldContainer.WORLDWIDTH];
        chunkMatrix = new Chunk[2][2];

        armor = new ItemCollection(ItemType.ARMOR, 4);

        worldContainer.createNewWorld(size);

        zqn = new Byte[size][size];
        pzqn = new Byte[3][size][size];
        arbprd = new Boolean[3][size][size];
        wcnct = new Boolean[size][size];
        worldContainer.drawn = new Boolean[size][size];
        worldContainer.rdrawn = new Boolean[size][size];
        worldContainer.ldrawn = new Boolean[size][size];

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

    public void updateApp() {
        mousePos2.setX(mousePos.getX() + worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width / 2);
        mousePos2.setY(mousePos.getY() + worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height / 2);

        worldContainer.currentSkyLight = SKY_COLORS[0];
        for (i = 0; i < SKY_COLORS.length; i++) {
            if (worldContainer.timeOfDay >= SKY_COLORS[i]) {
                worldContainer.currentSkyLight = SKY_COLORS[i];
            }
        }
        if (worldContainer.player.y / 16 > HEIGHT * 0.55) {
            bg = Color.BLACK;
        } else {
            bg = SKYCOLORS.get(worldContainer.currentSkyLight);
        }

        if (worldContainer.rgnc1 == 0) {
            if (worldContainer.rgnc2 == 0) {
                if (worldContainer.player.hp < worldContainer.player.thp) {
                    worldContainer.player.hp += 1;
                    worldContainer.rgnc2 = 125;
                }
            } else {
                worldContainer.rgnc2 -= 1;
            }
        } else {
            worldContainer.rgnc1 -= 1;
        }

        for (j = 0; j < worldContainer.machinesx.size(); j++) {
            x = worldContainer.machinesx.get(j);
            y = worldContainer.machinesy.get(j);
            for (int l = 0; l < 3; l++) {
                if (worldContainer.icmatrix[l][y][x] != null && worldContainer.icmatrix[l][y][x].getType() == ItemType.FURNACE) {
                    if (worldContainer.icmatrix[l][y][x].isF_ON()) {
                        if (worldContainer.icmatrix[l][y][x].getIds()[1] == Items.EMPTY) {
                            if (FUELS.get(worldContainer.icmatrix[l][y][x].getIds()[2]) != null) {
                                worldContainer.inventory.addLocationIC(worldContainer.icmatrix[l][y][x], 1, worldContainer.icmatrix[l][y][x].getIds()[2], (short) 1);
                                worldContainer.inventory.removeLocationIC(worldContainer.icmatrix[l][y][x], 2, (short) 1);
                                worldContainer.icmatrix[l][y][x].setFUELP(1);
                            } else {
                                worldContainer.icmatrix[l][y][x].setF_ON(false);
                                removeBlockLighting(x, y);
                                worldContainer.blocks[l][y][x] = BlockNames.FURNACE;
                                worldContainer.rdrawn[y][x] = false;
                            }
                        }
                        if (FUELS.get(worldContainer.icmatrix[l][y][x].getIds()[1]) != null) {
                            worldContainer.icmatrix[l][y][x].setFUELP(worldContainer.icmatrix[l][y][x].getFUELP() - FUELS.get(worldContainer.icmatrix[l][y][x].getIds()[1]));
                            if (worldContainer.icmatrix[l][y][x].getFUELP() < 0) {
                                worldContainer.icmatrix[l][y][x].setFUELP(0);
                                worldContainer.inventory.removeLocationIC(worldContainer.icmatrix[l][y][x], 1, worldContainer.icmatrix[l][y][x].getNums()[1]);
                            }
                            for (i = 0; i < FRI1.size(); i++) {
                                if (worldContainer.icmatrix[l][y][x].getIds()[0] == FRI1.get(i) && worldContainer.icmatrix[l][y][x].getNums()[0] >= FRN1.get(i)) {
                                    worldContainer.icmatrix[l][y][x].setSMELTP(worldContainer.icmatrix[l][y][x].getSMELTP() + BlockNames.findByIndex(worldContainer.icmatrix[l][y][x].getIds()[1].getIndex()).getFSpeed());
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
            if (worldContainer.ic.isF_ON()) {
                if (worldContainer.ic.getIds()[1] == Items.EMPTY) {
                    if (FUELS.get(worldContainer.ic.getIds()[2]) != null) {
                        worldContainer.inventory.addLocationIC(worldContainer.ic, 1, worldContainer.ic.getIds()[2], (short) 1);
                        worldContainer.inventory.removeLocationIC(worldContainer.ic, 2, (short) 1);
                        worldContainer.ic.setFUELP(1);
                    } else {
                        worldContainer.ic.setF_ON(false);
                        removeBlockLighting(worldContainer.icx, worldContainer.icy);
                        worldContainer.blocks[iclayer][worldContainer.icy][worldContainer.icx] = BlockNames.FURNACE;
                        worldContainer.rdrawn[worldContainer.icy][worldContainer.icx] = false;
                    }
                }
                if (FUELS.get(worldContainer.ic.getIds()[1]) != null) {
                    worldContainer.ic.setFUELP(worldContainer.ic.getFUELP() - FUELS.get(worldContainer.ic.getIds()[1]));
                    if (worldContainer.ic.getFUELP() < 0) {
                        worldContainer.ic.setFUELP(0);
                        worldContainer.inventory.removeLocationIC(worldContainer.ic, 1, worldContainer.ic.getNums()[1]);
                    }
                    for (i = 0; i < FRI1.size(); i++) {
                        if (worldContainer.ic.getIds()[0] == FRI1.get(i) && worldContainer.ic.getNums()[0] >= FRN1.get(i)) {
                            worldContainer.ic.setSMELTP(worldContainer.ic.getSMELTP() + BlockNames.findByIndex(worldContainer.ic.getIds()[1].getIndex()).getFSpeed());
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
        if (Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - worldContainer.icx * BLOCKSIZE + BLOCKSIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - worldContainer.icy * BLOCKSIZE + BLOCKSIZE / 2, 2)) > 160) {
            if (worldContainer.ic != null) {
                if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                    worldContainer.machinesx.add(worldContainer.icx);
                    worldContainer.machinesy.add(worldContainer.icy);
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                        for (i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        for (i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                } else if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setSMELTP(worldContainer.ic.getSMELTP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setF_ON(worldContainer.ic.isF_ON());
                }
                worldContainer.ic = null;
            }
        }

        for (int l = 0; l < 3; l++) {
            for (y = 0; y < size; y++) {
                for (x = 0; x < size; x++) {
                    if (RandomTool.nextInt(22500) == 0) {
                        BlockNames t = BlockNames.AIR;
                        switch (worldContainer.blocks[l][y][x]) {
                            case SUNFLOWER_STAGE_1:
                                if (worldContainer.timeOfDay >= 75913 || worldContainer.timeOfDay < 28883) {
                                    t = BlockNames.SUNFLOWER_STAGE_2;
                                }
                                break;
                            case SUNFLOWER_STAGE_2:
                                if (worldContainer.timeOfDay >= 75913 || worldContainer.timeOfDay < 28883) {
                                    t = BlockNames.SUNFLOWER_STAGE_3;
                                }
                                break;
                            case MOONFLOWER_STAGE_1:
                                if (worldContainer.timeOfDay >= 32302 && worldContainer.timeOfDay < 72093) {
                                    t = BlockNames.MOONFLOWER_STAGE_2;
                                }
                                break;
                            case MOONFLOWER_STAGE_2:
                                if (worldContainer.timeOfDay >= 32302 && worldContainer.timeOfDay < 72093) {
                                    t = BlockNames.MOONFLOWER_STAGE_3;
                                }
                                break;
                            case DRYWEED_STAGE_1:
                                if (checkBiome(x, y).equals("desert")) {
                                    t = BlockNames.DRYWEED_STAGE_2;
                                }
                                break;
                            case DRYWEED_STAGE_2:
                                if (checkBiome(x, y).equals("desert")) {
                                    t = BlockNames.DRYWEED_STAGE_3;
                                }
                                break;
                            case GREENLEAF_STAGE_1:
                                if (checkBiome(x, y).equals("jungle")) {
                                    t = BlockNames.GREENLEAF_STAGE_2;
                                }
                                break;
                            case GREENLEAF_STAGE_2:
                                if (checkBiome(x, y).equals("jungle")) {
                                    t = BlockNames.GREENLEAF_STAGE_3;
                                }
                                break;
                            case FROSTLEAF_STAGE_1:
                                if (checkBiome(x, y).equals("frost")) {
                                    t = BlockNames.FROSTLEAF_STAGE_2;
                                }
                                break;
                            case FROSTLEAF_STAGE_2:
                                if (checkBiome(x, y).equals("frost")) {
                                    t = BlockNames.FROSTLEAF_STAGE_3;
                                }
                                break;
                            case CAVEROOT_STAGE_1:
                                if (checkBiome(x, y).equals("cavern") || y >= 0/*stonelayer[x]*/) {
                                    t = BlockNames.CAVEROOT_STAGE_2;
                                }
                                break;
                            case CAVEROOT_STAGE_2:
                                if (checkBiome(x, y).equals("cavern") || y >= 0/*stonelayer[x]*/) {
                                    t = BlockNames.CAVEROOT_STAGE_3;
                                }
                                break;
                            case SKYBLOSSOM_STAGE_1:
                                if (y <= HEIGHT * 0.08 && RandomTool.nextInt(3) == 0 || y <= HEIGHT * 0.04) {
                                    t = BlockNames.SKYBLOSSOM_STAGE_2;
                                }
                                break;
                            case SKYBLOSSOM_STAGE_2:
                                if (y <= HEIGHT * 0.08 && RandomTool.nextInt(3) == 0 || y <= HEIGHT * 0.04) {
                                    t = BlockNames.SKYBLOSSOM_STAGE_3;
                                }
                                break;
                            case VOID_ROT_STAGE_1:
                                if (y >= HEIGHT * 0.98) {
                                    t = BlockNames.VOID_ROT_STAGE_2;
                                }
                                break;
                            case VOID_ROT_STAGE_2:
                                if (y >= HEIGHT * 0.98) {
                                    t = BlockNames.VOID_ROT_STAGE_3;
                                }
                                break;
                            case MARSHLEAF_STAGE_1:
                                if (checkBiome(x, y).equals("swamp")) {
                                    t = BlockNames.MARSHLEAF_STAGE_2;
                                }
                                break;
                            case MARSHLEAF_STAGE_2:
                                if (checkBiome(x, y).equals("swamp")) {
                                    t = BlockNames.MARSHLEAF_STAGE_3;
                                }
                                break;
                            default:
                                break;
                        }
                        if (t != BlockNames.AIR) {
                            worldContainer.blocks[l][y][x] = t;
                            worldContainer.drawn[y][x] = false;
                        }
                    }
                }
            }
        }

        for (int l = 0; l < 3; l++) {
            for (y = 0; y < size; y++) {
                for (x = 0; x < size; x++) {
                    if (RandomTool.nextInt(1000) == 0) {
                        if (y >= 1 && y < HEIGHT - 1) {
                            doGrassGrow = false;
                            if (worldContainer.blocks[l][y][x] == BlockNames.DIRT && hasOpenSpace(x + u, y + v, l) && worldContainer.blocks[l][y + RandomTool.nextInt(3) - 1 + u][x + RandomTool.nextInt(3) - 1 + v] == BlockNames.GRASS) {
                                worldContainer.blocks[l][y][x] = BlockNames.GRASS;
                                doGrassGrow = true;
                            }
                            if (worldContainer.blocks[l][y][x] == BlockNames.DIRT && hasOpenSpace(x + u, y + v, l) && worldContainer.blocks[l][y + RandomTool.nextInt(3) - 1 + u][x + RandomTool.nextInt(3) - 1 + v] == BlockNames.JUNGLE_GRASS) {
                                worldContainer.blocks[l][y][x] = BlockNames.JUNGLE_GRASS;
                                doGrassGrow = true;
                            }
                            if (worldContainer.blocks[l][y][x] == BlockNames.MUD && hasOpenSpace(x + u, y + v, l) && worldContainer.blocks[l][y + RandomTool.nextInt(3) - 1 + u][x + RandomTool.nextInt(3) - 1 + v] == BlockNames.SWAMP_GRASS) {
                                worldContainer.blocks[l][y][x] = BlockNames.SWAMP_GRASS;
                                doGrassGrow = true;
                            }
                            if (doGrassGrow) {
                                for (y2 = y - 1; y2 < y + 2; y2++) {
                                    for (x2 = x - 1; x2 < x + 2; x2++) {
                                        if (y2 >= 0 && y2 < HEIGHT) {
                                            worldContainer.drawn[y2][x2] = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int l = 0; l < 3; l++) {
            for (y = 0; y < size; y++) {
                for (x = 0; x < size; x++) {
                    if (RandomTool.nextInt(1000) == 0) {
                        if (worldContainer.blocks[1][y][x] == BlockNames.TREE_NO_BARK) {
                            worldContainer.blocks[1][y][x] = BlockNames.TREE;
                        }
                    }
                }
            }
        }

        for (i = updatex.size() - 1; i > -1; i--) {
            updatet.set(i, updatet.get(i) - 1);
            if (updatet.get(i) <= 0) {
                if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == BlockNames.BUTTON_LEFT_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = BlockNames.BUTTON_LEFT;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == BlockNames.BUTTON_RIGHT_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = BlockNames.BUTTON_RIGHT;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == BlockNames.WOODEN_PRESSURE_PLATE_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = BlockNames.WOODEN_PRESSURE_PLATE;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == BlockNames.STONE_PRESSURE_PLATE_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = BlockNames.STONE_PRESSURE_PLATE;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == BlockNames.ZYTHIUM_PRESSURE_PLATE_ON) {
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = BlockNames.ZYTHIUM_PRESSURE_PLATE;
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)].isZythiumDelayerOnAll()) {
                    log.info("[DEBUG2R]");
                    worldContainer.blockTemp = worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i), false);
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = BlockNames.turnZythiumDelayerOff(worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)]);
                    worldContainer.rdrawn[updatey.get(i)][updatex.get(i)] = false;
                } else if (worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)].isZythiumDelayerAll()) {
                    log.info("[DEBUG2A]");
                    worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = BlockNames.turnZythiumDelayerOn(worldContainer.blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)]);
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
                for (ay = (int) (worldContainer.player.iy / BLOCKSIZE) - 125; ay < (int) (worldContainer.player.iy / BLOCKSIZE) + 125; ay++) {
                    for (ax = (int) (worldContainer.player.ix / BLOCKSIZE) - 125; ax < (int) (worldContainer.player.ix / BLOCKSIZE) + 125; ax++) {
                        if (RandomTool.nextInt((int) (100000 / DebugContext.HOSTILE)) == 0) {
                            xpos = ax + RandomTool.nextInt(20) - 10;
                            ypos = ay + RandomTool.nextInt(20) - 10;
                            xpos2 = ax + RandomTool.nextInt(20) - 10;
                            ypos2 = ay + RandomTool.nextInt(20) - 10;
                            if (xpos > 0 && xpos < WIDTH - 1 && ypos > 0 && ypos < HEIGHT - 1 && (worldContainer.blocks[1][ypos][xpos] == BlockNames.AIR || !worldContainer.blocks[1][ypos][xpos].isCds() &&
                                    xpos2 > 0 && xpos2 < WIDTH - 1 && ypos2 > 0 && ypos2 < HEIGHT - 1 && worldContainer.blocks[1][ypos2][xpos2] != BlockNames.AIR && worldContainer.blocks[1][ypos2][xpos2].isCds())) {
                                mobSpawn = null;
                                if (!checkBiome(xpos, ypos).equals("underground")) {
                                    if ((worldContainer.day != 0 || DebugContext.HOSTILE > 1) && (worldContainer.timeOfDay >= 75913 || worldContainer.timeOfDay < 28883)) {
                                        if (RandomTool.nextInt(350) == 0) {
                                            rnum = RandomTool.nextInt(100);
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
                                            rnum = RandomTool.nextInt(100);
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
                                } else {
                                    if (RandomTool.nextInt(100) == 0) {
                                        rnum = RandomTool.nextInt(100);
                                        if (rnum >= 0 && rnum < 25) {
                                            mobSpawn = EntityType.YELLOW_BUBBLE; // 25%
                                        }
                                        if (rnum >= 25 && rnum < 45) {
                                            mobSpawn = EntityType.ZOMBIE; // 20%
                                        }
                                        if (rnum >= 45 && rnum < 60) {
                                            mobSpawn = EntityType.ARMORED_ZOMBIE; // 15%
                                        }
                                        if (rnum >= 60 && rnum < 70) {
                                            mobSpawn = EntityType.BLACK_BUBBLE; // 10%
                                        }
                                        if (rnum >= 70 && rnum < 85) {
                                            mobSpawn = EntityType.BAT; // 15%
                                        }
                                        if (rnum >= 85 && rnum < 100) {
                                            mobSpawn = EntityType.SKELETON; // 15%
                                        }
                                    }
                                }
                                if (mobSpawn != null && checkBiome(xpos, ypos).equals("desert")) {
                                    if (RandomTool.nextInt(3) == 0) { // 33% of all spawns in desert
                                        mobSpawn = EntityType.SANDBOT;
                                    }
                                }
                                if (mobSpawn != null && checkBiome(xpos, ypos).equals("frost")) {
                                    if (RandomTool.nextInt(3) == 0) { // 33% of all spawns in desert
                                        mobSpawn = EntityType.SNOWMAN;
                                    }
                                }
                                if (mobSpawn == null) {
                                    continue;
                                } else if (DebugContext.MOBTEST != null) {
                                    mobSpawn = DebugContext.MOBTEST;
                                }
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
                                for (x = (int) (xpos / BLOCKSIZE); x < (int) (xpos / BLOCKSIZE + xmax); x++) {
                                    for (y = (int) (ypos / BLOCKSIZE); y < (int) (ypos / BLOCKSIZE + ymax); y++) {
                                        if (y > 0 && y < HEIGHT - 1 && worldContainer.blocks[1][y][x] != BlockNames.AIR && worldContainer.blocks[1][y][x].isCds()) {
                                            worldContainer.doMobSpawn = false;
                                        }
                                    }
                                }
                                if (worldContainer.doMobSpawn) {
                                    worldContainer.entities.add(new Entity(xpos * BLOCKSIZE, ypos * BLOCKSIZE, 0, 0, mobSpawn));
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

        for (i = worldContainer.entities.size() - 1; i > -1; i--) {
            if (worldContainer.entities.get(i).getEntityType() != null) {
                worldContainer.mobCount += 1;
                if (worldContainer.entities.get(i).getIx() < worldContainer.player.ix - 2000 || worldContainer.entities.get(i).getIx() > worldContainer.player.ix + 2000 ||
                        worldContainer.entities.get(i).getIy() < worldContainer.player.iy - 2000 || worldContainer.entities.get(i).getIy() > worldContainer.player.iy + 2000) {
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
                        timer.stop();
                        menuTimer.start();
                        return;
                    }
                }
                for (ux = 0; ux < 10; ux++) {
                    for (uy = 0; uy < 4; uy++) {
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
                for (ux = 0; ux < 2; ux++) {
                    for (uy = 0; uy < 2; uy++) {
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
                        for (ux = 0; ux < 3; ux++) {
                            for (uy = 0; uy < 3; uy++) {
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
                        for (ux = 0; ux < worldContainer.inventory.CX; ux++) {
                            for (uy = 0; uy < worldContainer.inventory.CY; uy++) {
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
                for (uy = 0; uy < 4; uy++) {
                    if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + 6, worldContainer.inventory.image.getWidth() + 6 + armor.getImage().getWidth(), 6 + uy * 46, 6 + uy * 46 + 40)) {
                        checkBlocks = false;
                        if (mousePos.isClicked()) {
                            mousePos.setReleased(true);
                            i = uy;
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
                for (ux = 0; ux < 10; ux++) {
                    uy = 0;
                    if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + 6, uy * 46 + 46)) {
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
                    for (i = 0; i < worldContainer.entities.size(); i++) {
                        worldContainer.entities.get(i).setImmune(false);
                    }
                    worldContainer.toolSpeed = worldContainer.inventory.tool().getSpeed();
                    if (worldContainer.inventory.tool() == Items.MAGNETITE_PICK || worldContainer.inventory.tool() == Items.MAGNETITE_AXE || worldContainer.inventory.tool() == Items.MAGNETITE_SWORD) {
                        worldContainer.toolSpeed *= ((double) worldContainer.inventory.durs[worldContainer.inventory.selection] / worldContainer.inventory.items[worldContainer.inventory.selection].getDurability()) * (-0.714) + 1;
                    }
                    worldContainer.showTool = true;
                    worldContainer.toolAngle = 4.7;
                    ux = mousePos2.getX() / BLOCKSIZE;
                    uy = mousePos2.getY() / BLOCKSIZE;
                    ux2 = mousePos2.getX() / BLOCKSIZE;
                    uy2 = mousePos2.getY() / BLOCKSIZE;
                    if (Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux2 * BLOCKSIZE + BLOCKSIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy2 * BLOCKSIZE + BLOCKSIZE / 2, 2)) <= 160 ||
                            Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux2 * BLOCKSIZE + BLOCKSIZE / 2 + WIDTH * BLOCKSIZE, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy2 * BLOCKSIZE + BLOCKSIZE / 2, 2)) <= 160 || DebugContext.REACH) {
                        ucx = ux - CHUNKBLOCKS * ((int) (ux / CHUNKBLOCKS));
                        ucy = uy - CHUNKBLOCKS * ((int) (uy / CHUNKBLOCKS));
                        if (Arrays.asList(TOOL_LIST).contains(worldContainer.inventory.tool())) {
                            if (worldContainer.blocks[worldContainer.layer][uy][ux] != BlockNames.AIR && worldContainer.blocks[worldContainer.layer][uy][ux].getTools().contains(worldContainer.inventory.tool())) {
                                worldContainer.blockdns[uy][ux] = (byte) RandomTool.nextInt(5);
                                worldContainer.drawn[uy][ux] = false;
                                if (ux == worldContainer.mx && uy == worldContainer.my && worldContainer.inventory.tool() == miningTool) {
                                    worldContainer.mining += 1;
                                    worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                    breakCurrentBlock();
                                    if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                    }
                                } else {
                                    worldContainer.mining = 1;
                                    miningTool = worldContainer.inventory.tool();
                                    worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                    breakCurrentBlock();
                                    worldContainer.mx = ux;
                                    worldContainer.my = uy;
                                    if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                    }
                                }
                                ug2 = null;
                            }
                        } else if (worldContainer.inventory.tool() == Items.STONE_LIGHTER) {
                            if (worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.FURNACE || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.FURNACE_ON) {
                                if (worldContainer.icmatrix[worldContainer.layer][uy][ux] != null && worldContainer.icmatrix[worldContainer.layer][uy][ux].getType() == ItemType.FURNACE) {
                                    worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                    worldContainer.icmatrix[worldContainer.layer][uy][ux].setF_ON(true);
                                    worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.FURNACE_ON;
                                    addBlockLighting(ux, uy);
                                    if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                    }
                                    worldContainer.rdrawn[uy][ux] = false;
                                } else {
                                    if (worldContainer.ic != null && worldContainer.ic.getType() == ItemType.FURNACE) {
                                        worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                        worldContainer.ic.setF_ON(true);
                                        worldContainer.blocks[worldContainer.layer][worldContainer.icy][worldContainer.icx] = BlockNames.FURNACE_ON;
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
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.increaseZythiumDelayerLevel(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.rdrawn[uy][ux] = false;
                                if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                    worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                }
                            } else if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer8() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumDelayer8On()) {
                                worldContainer.inventory.durs[worldContainer.inventory.selection] -= 1;
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.resetZythiumDelayerLevel(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.rdrawn[uy][ux] = false;
                                if (worldContainer.inventory.durs[worldContainer.inventory.selection] <= 0) {
                                    worldContainer.inventory.removeLocation(worldContainer.inventory.selection, worldContainer.inventory.nums[worldContainer.inventory.selection]);
                                }
                            }
                        } else if (worldContainer.inventory.tool().getBlocks() != BlockNames.AIR) {
                            worldContainer.blockTemp = worldContainer.inventory.tool().getBlocks();
                            if (uy >= 1 && (worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.AIR) &&
                                    (worldContainer.layer == 0 && (
                                            worldContainer.blocks[worldContainer.layer][uy][ux - 1] != BlockNames.AIR || worldContainer.blocks[worldContainer.layer][uy][ux + 1] != BlockNames.AIR ||
                                                    worldContainer.blocks[worldContainer.layer][uy - 1][ux] != BlockNames.AIR || worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.AIR ||
                                                    worldContainer.blocks[worldContainer.layer + 1][uy][ux] != BlockNames.AIR) ||
                                            worldContainer.layer == 1 && (
                                                    worldContainer.blocks[worldContainer.layer][uy][ux - 1] != BlockNames.AIR || worldContainer.blocks[worldContainer.layer][uy][ux + 1] != BlockNames.AIR ||
                                                            worldContainer.blocks[worldContainer.layer][uy - 1][ux] != BlockNames.AIR || worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.AIR ||
                                                            worldContainer.blocks[worldContainer.layer - 1][uy][ux] != BlockNames.AIR || worldContainer.blocks[worldContainer.layer + 1][uy][ux] != BlockNames.AIR) ||
                                            worldContainer.layer == 2 && (
                                                    worldContainer.blocks[worldContainer.layer][uy][ux - 1] != BlockNames.AIR || worldContainer.blocks[worldContainer.layer][uy][ux + 1] != BlockNames.AIR ||
                                                            worldContainer.blocks[worldContainer.layer][uy - 1][ux] != BlockNames.AIR || worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.AIR ||
                                                            worldContainer.blocks[worldContainer.layer - 1][uy][ux] != BlockNames.AIR)) &&
                                    !(worldContainer.blockTemp == BlockNames.SUNFLOWER_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.JUNGLE_GRASS) || // sunflower
                                            worldContainer.blockTemp == BlockNames.MOONFLOWER_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.JUNGLE_GRASS) || // moonflower
                                            worldContainer.blockTemp == BlockNames.DRYWEED_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.SAND) || // dryweed
                                            worldContainer.blockTemp == BlockNames.GREENLEAF_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.JUNGLE_GRASS) || // greenleaf
                                            worldContainer.blockTemp == BlockNames.FROSTLEAF_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.SNOW) || // frostleaf
                                            worldContainer.blockTemp == BlockNames.CAVEROOT_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.STONE) || // caveroot
                                            worldContainer.blockTemp == BlockNames.SKYBLOSSOM_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.DIRT && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.GRASS && worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.JUNGLE_GRASS) || // skyblossom
                                            worldContainer.blockTemp == BlockNames.VOID_ROT_STAGE_1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux] != BlockNames.STONE))) { // void_rot
                                if (!(TORCHESL.get(worldContainer.blockTemp) != null) || uy < HEIGHT - 1 && (worldContainer.blocks[worldContainer.layer][uy + 1][ux].isSolid() && worldContainer.blockTemp != BlockNames.BUTTON_LEFT || worldContainer.blocks[worldContainer.layer][uy][ux + 1].isSolid() || worldContainer.blocks[worldContainer.layer][uy][ux - 1].isSolid())) {
                                    if (TORCHESL.get(worldContainer.blockTemp) != null) {
                                        if (worldContainer.blocks[worldContainer.layer][uy + 1][ux].isSolid() && worldContainer.blockTemp != BlockNames.BUTTON_LEFT) {
                                            worldContainer.blockTemp = worldContainer.blockTemp;
                                        } else if (worldContainer.blocks[worldContainer.layer][uy][ux - 1].isSolid()) {
                                            worldContainer.blockTemp = TORCHESL.get(worldContainer.blockTemp);
                                        } else if (worldContainer.blocks[worldContainer.layer][uy][ux + 1].isSolid()) {
                                            worldContainer.blockTemp = TORCHESR.get(worldContainer.blockTemp);
                                        }
                                    }
                                    if (worldContainer.layer == 1 && !DebugContext.GPLACE && worldContainer.blockTemp.isCds()) {
                                        for (i = 0; i < worldContainer.entities.size(); i++) {
                                            if (worldContainer.entities.get(i).getEntityType() != null && worldContainer.entities.get(i).getRect().intersects(new Rectangle(ux * BLOCKSIZE, uy * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                                worldContainer.blockTemp = BlockNames.AIR;
                                            }
                                        }
                                        if (worldContainer.player.rect.intersects(new Rectangle(ux * BLOCKSIZE, uy * BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                            worldContainer.blockTemp = BlockNames.AIR;
                                        }
                                    }
                                    if (worldContainer.blockTemp != BlockNames.AIR) {
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
                                    if (worldContainer.blockTemp != BlockNames.AIR) {
                                        worldContainer.inventory.removeLocation(worldContainer.inventory.selection, (short) 1);
                                        worldContainer.blockds[worldContainer.layer] = World.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blockds[worldContainer.layer], ux, uy);
                                        for (uly = uy - 1; uly < uy + 2; uly++) {
                                            for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                                                worldContainer.blockdns[uly][ulx] = (byte) RandomTool.nextInt(5);
                                            }
                                        }
                                        for (uly = uy - 1; uly < uy + 2; uly++) {
                                            for (ulx = ux - 1; ulx < ux + 2; ulx++) {
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
                for (ux = 0; ux < 10; ux++) {
                    for (uy = 0; uy < 4; uy++) {
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
                for (ux = 0; ux < 2; ux++) {
                    for (uy = 0; uy < 2; uy++) {
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
                        for (ux = 0; ux < 3; ux++) {
                            for (uy = 0; uy < 3; uy++) {
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
                        for (ux = 0; ux < worldContainer.inventory.CX; ux++) {
                            for (uy = 0; uy < worldContainer.inventory.CY; uy++) {
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
                if (!(mousePos2.getX() < 0 || mousePos2.getX() >= WIDTH * BLOCKSIZE ||
                        mousePos2.getY() < 0 || mousePos2.getY() >= HEIGHT * BLOCKSIZE)) {
                    ux = (int) (mousePos2.getX() / BLOCKSIZE);
                    uy = (int) (mousePos2.getY() / BLOCKSIZE);
                    if (DebugContext.REACH || Math.sqrt(Math.pow(worldContainer.player.x + worldContainer.player.image.getWidth() - ux * BLOCKSIZE + BLOCKSIZE / 2, 2) + Math.pow(worldContainer.player.y + worldContainer.player.image.getHeight() - uy * BLOCKSIZE + BLOCKSIZE / 2, 2)) <= 160) {
                        ucx = ux - CHUNKBLOCKS * ((int) (ux / CHUNKBLOCKS));
                        ucy = uy - CHUNKBLOCKS * ((int) (uy / CHUNKBLOCKS));
                        if (worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.WORKBENCH
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.WOODEN_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.STONE_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.COPPER_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.IRON_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.SILVER_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.GOLD_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.FURNACE
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.FURNACE_ON
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.ZINC_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.RHYMESTONE_CHEST
                                || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.OBDURITE_CHEST) {
                            if (worldContainer.ic != null) {
                                if (worldContainer.ic.getType() != ItemType.WORKBENCH) {
                                    worldContainer.machinesx.add(worldContainer.icx);
                                    worldContainer.machinesy.add(worldContainer.icy);
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx] = new ItemCollection(worldContainer.ic);
                                } else if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                                    if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                                        for (i = 0; i < 9; i++) {
                                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                                worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                            }
                                        }
                                    }
                                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                                        for (i = 0; i < 9; i++) {
                                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                                worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                            }
                                        }
                                    }
                                }
                                if (worldContainer.ic.getType() == ItemType.FURNACE) {
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getSMELTP());
                                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setF_ON(worldContainer.ic.isF_ON());
                                }
                                worldContainer.ic = null;
                            }
                            iclayer = worldContainer.layer;
                            for (int l = 0; l < 3; l++) {
                                if (worldContainer.blocks[l][uy][ux] == BlockNames.WORKBENCH) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.WORKBENCH) {
                                        worldContainer.ic = new ItemCollection(ItemType.WORKBENCH, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.WORKBENCH, 10);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.WOODEN_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.WOODEN_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.WOODEN_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.WOODEN_CHEST, 9);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.STONE_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.STONE_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.STONE_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.STONE_CHEST, 15);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.COPPER_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.COPPER_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.COPPER_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.COPPER_CHEST, 20);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.IRON_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.IRON_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.IRON_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.IRON_CHEST, 28);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.SILVER_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.SILVER_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.SILVER_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.SILVER_CHEST, 35);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.GOLD_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.GOLD_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.GOLD_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.GOLD_CHEST, 42);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.ZINC_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.ZINC_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.ZINC_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.ZINC_CHEST, 56);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.RHYMESTONE_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.RHYMESTONE_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.RHYMESTONE_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.RHYMESTONE_CHEST, 72);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.OBDURITE_CHEST) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.OBDURITE_CHEST) {
                                        worldContainer.ic = new ItemCollection(ItemType.OBDURITE_CHEST, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.OBDURITE_CHEST, 100);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                } else if (worldContainer.blocks[l][uy][ux] == BlockNames.FURNACE || worldContainer.blocks[l][uy][ux] == BlockNames.FURNACE_ON) {
                                    if (worldContainer.icmatrix[l][uy][ux] != null && worldContainer.icmatrix[l][uy][ux].getType() == ItemType.FURNACE) {
                                        worldContainer.ic = new ItemCollection(ItemType.FURNACE, worldContainer.icmatrix[l][uy][ux].getIds(), worldContainer.icmatrix[l][uy][ux].getNums(), worldContainer.icmatrix[l][uy][ux].getDurs());
                                        worldContainer.ic.setFUELP(worldContainer.icmatrix[l][uy][ux].getFUELP());
                                        worldContainer.ic.setSMELTP(worldContainer.icmatrix[l][uy][ux].getSMELTP());
                                        worldContainer.ic.setF_ON(worldContainer.icmatrix[l][uy][ux].isF_ON());
                                        worldContainer.icmatrix[l][uy][ux] = null;
                                    } else {
                                        worldContainer.ic = new ItemCollection(ItemType.FURNACE, 4);
                                    }
                                    worldContainer.icx = ux;
                                    worldContainer.icy = uy;
                                    worldContainer.inventory.renderCollection(worldContainer.ic);
                                    worldContainer.showInv = true;
                                }
                                if (worldContainer.ic != null && worldContainer.blocks[l][uy][ux] != BlockNames.WORKBENCH) {
                                    for (i = worldContainer.machinesx.size() - 1; i > -1; i--) {
                                        if (worldContainer.machinesx.get(i) == worldContainer.icx && worldContainer.machinesy.get(i) == worldContainer.icy) {
                                            worldContainer.machinesx.remove(i);
                                            worldContainer.machinesy.remove(i);
                                        }
                                    }
                                }
                            }
                        }
                        if (worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.TREE) {
                            if (RandomTool.nextInt(2) == 0) {
                                worldContainer.entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 8 - 4, -3, Items.BARK, (short) 1));
                            }
                            worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.TREE_NO_BARK;
                        }
                        if (mousePos2.isClicked()) {
                            mousePos2.setReleased(true);
                            worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isLever()) {
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.turnLeverOn(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                addBlockPower(ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                            } else if (worldContainer.blocks[worldContainer.layer][uy][ux].isLeverOn()) {
                                removeBlockPower(ux, uy, worldContainer.layer);
                                if (wcnct[uy][ux]) {
                                    for (int l = 0; l < 3; l++) {
                                        if (l != worldContainer.layer) {
                                            rbpRecur(ux, uy, l);
                                        }
                                    }
                                }
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.turnLeverOff(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.rdrawn[uy][ux] = false;
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumWire()) {
                                wcnct[uy][ux] = !wcnct[uy][ux];
                                worldContainer.rdrawn[uy][ux] = false;
                                redoBlockPower(ux, uy, worldContainer.layer);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isCompleteZythiumAmplifier()) {
                                removeBlockPower(ux, uy, worldContainer.layer);
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.turnZythiumAmplifier(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.blockds[worldContainer.layer] = World.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blockds[worldContainer.layer], ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                                addAdjacentTilesToPQueueConditionally(ux, uy);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumInverterAll() || worldContainer.blocks[worldContainer.layer][uy][ux].isZythiumInverterOnAll()) {
                                removeBlockPower(ux, uy, worldContainer.layer);
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.turnZythiumInverter(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.blockds[worldContainer.layer] = World.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blockds[worldContainer.layer], ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                                addAdjacentTilesToPQueueConditionally(ux, uy);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isButton()) {
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.turnButtonOn(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                addBlockPower(ux, uy);
                                worldContainer.rdrawn[uy][ux] = false;
                                log.info("Srsly?");
                                updatex.add(ux);
                                updatey.add(uy);
                                updatet.add(50);
                                updatel.add(worldContainer.layer);
                            }
                            if (worldContainer.blocks[worldContainer.layer][uy][ux].isCompleteZythiumDelayer()) {
                                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.turnZythiumDelayer(worldContainer.blocks[worldContainer.layer][uy][ux]);
                                worldContainer.blockds[worldContainer.layer] = World.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blockds[worldContainer.layer], ux, uy);
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
        if (!DebugContext.INVINCIBLE && worldContainer.player.y / 16 > HEIGHT + 10) {
            vc += 1;
            if (vc >= 1 / (Math.pow(1.001, worldContainer.player.y / 16 - HEIGHT - 10) - 1.0)) {
                worldContainer.player.damage(1, false, worldContainer.inventory);
                vc = 0;
            }
        } else {
            vc = 0;
        }
        for (i = worldContainer.entities.size() - 1; i >= 0; i--) {
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
                        worldContainer.rgnc1 = 750;
                        worldContainer.immune = 40;
                        if (worldContainer.player.x + worldContainer.player.width / 2 < worldContainer.entities.get(i).getX() + worldContainer.entities.get(i).getWidth() / 2) {
                            worldContainer.player.vx -= 8;
                        } else {
                            worldContainer.player.vx += 8;
                        }
                        worldContainer.player.vy -= 3.5;
                    }
                } else if (worldContainer.entities.get(i).getMdelay() <= 0) {
                    n = worldContainer.inventory.addItem(worldContainer.entities.get(i).getItem(), worldContainer.entities.get(i).getNum(), worldContainer.entities.get(i).getDur());
                    if (n != 0) {
                        worldContainer.entities.add(new Entity(worldContainer.entities.get(i).getX(), worldContainer.entities.get(i).getY(), worldContainer.entities.get(i).getVx(), worldContainer.entities.get(i).getVy(), worldContainer.entities.get(i).getItem(), (short) (worldContainer.entities.get(i).getNum() - n), worldContainer.entities.get(i).getDur()));
                    }
                    worldContainer.entities.remove(i);
                }
            }
        }
        if (worldContainer.player.hp <= 0) {
            for (j = 0; j < 40; j++) {
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
                        for (i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                    if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                        for (i = 0; i < 9; i++) {
                            if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                            }
                        }
                    }
                }
                if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setSMELTP(worldContainer.ic.getSMELTP());
                    worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setF_ON(worldContainer.ic.isF_ON());
                }
                worldContainer.ic = null;
            } else {
                if (worldContainer.showInv) {
                    for (i = 0; i < 4; i++) {
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
            for (i = 0; i < 4; i++) {
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
            worldContainer.player.x = WIDTH * 0.5 * BLOCKSIZE;
            worldContainer.player.y = 45;
            worldContainer.player.vx = 0;
            worldContainer.player.vy = 0;
            worldContainer.player.hp = worldContainer.player.thp;
            tool = null;
            worldContainer.showTool = false;
        }
        if (worldContainer.showTool) {
            if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                tp1 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 + 6), (int) (worldContainer.player.y + worldContainer.player.height / 2));
                tp2 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 + 6 + tool.getWidth() * 2 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 2 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 2 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 2 * Math.cos(worldContainer.toolAngle)));
                tp3 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 + 6 + tool.getWidth() * 1 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 1 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 1 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 1 * Math.cos(worldContainer.toolAngle)));
                tp4 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 + 6 + tool.getWidth() * 0.5 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 0.5 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 0.5 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 0.5 * Math.cos(worldContainer.toolAngle)));
                tp5 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 + 6 + tool.getWidth() * 1.5 * Math.cos(worldContainer.toolAngle) + tool.getHeight() * 1.5 * Math.sin(worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 1.5 * Math.sin(worldContainer.toolAngle) - tool.getHeight() * 1.5 * Math.cos(worldContainer.toolAngle)));
            }
            if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                tp1 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 - 6), (int) (worldContainer.player.y + worldContainer.player.height / 2));
                tp2 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 - 6 + tool.getWidth() * 2 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 2 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 2 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 2 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
                tp3 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 - 6 + tool.getWidth() * 1 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 1 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 1 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 1 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
                tp4 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 - 6 + tool.getWidth() * 0.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 0.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 0.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 0.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
                tp5 = new Point((int) (worldContainer.player.x + worldContainer.player.width / 2 - 6 + tool.getWidth() * 1.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle) + tool.getHeight() * 1.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle)),
                        (int) (worldContainer.player.y + worldContainer.player.height / 2 + tool.getWidth() * 1.5 * Math.sin((Math.PI * 1.5) - worldContainer.toolAngle) - tool.getHeight() * 1.5 * Math.cos((Math.PI * 1.5) - worldContainer.toolAngle)));
            }
            for (i = worldContainer.entities.size() - 1; i >= 0; i--) {
                if (worldContainer.entities.get(i).getEntityType() != null && !worldContainer.entities.get(i).isNohit() && worldContainer.showTool && (worldContainer.entities.get(i).getRect().contains(tp1) || worldContainer.entities.get(i).getRect().contains(tp2) || worldContainer.entities.get(i).getRect().contains(tp3) || worldContainer.entities.get(i).getRect().contains(tp4) || worldContainer.entities.get(i).getRect().contains(tp5)) && (!worldContainer.entities.get(i).getEntityType().equals("bee") || RandomTool.nextInt(4) == 0)) {
                    if (worldContainer.entities.get(i).hit(worldContainer.inventory.tool().getDamage(), worldContainer.player)) {
                        List<Items> dropList = worldContainer.entities.get(i).drops();
                        for (j = 0; j < dropList.size(); j++) {
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

        Rectangle rect;
        int bx1, bx2, by1, by2;
        for (i = -1; i < worldContainer.entities.size(); i++) {
            if (i == -1) {
                rect = worldContainer.player.rect;
                width = worldContainer.player.width;
                height = worldContainer.player.height;
                p = worldContainer.player.x;
                q = worldContainer.player.y;
            } else {
                rect = worldContainer.entities.get(i).getRect();
                width = worldContainer.entities.get(i).getWidth();
                height = worldContainer.entities.get(i).getHeight();
                p = worldContainer.entities.get(i).getX();
                q = worldContainer.entities.get(i).getY();
            }
            bx1 = (int) p / BLOCKSIZE;
            by1 = (int) q / BLOCKSIZE;
            bx2 = (int) (p + width) / BLOCKSIZE;
            by2 = (int) (q + height) / BLOCKSIZE;

            bx1 = Math.max(0, bx1);
            by1 = Math.max(0, by1);
            bx2 = Math.min(worldContainer.blocks[0].length - 1, bx2);
            by2 = Math.min(worldContainer.blocks.length - 1, by2);

            for (x = bx1; x <= bx2; x++) {
                for (y = by1; y <= by2; y++) {
                    if (worldContainer.blocks[worldContainer.layer][y][x].isCompletePressurePlate()
                            && (i == -1 || (worldContainer.blocks[worldContainer.layer][y][x].isStonePressurePlate() || worldContainer.blocks[worldContainer.layer][y][x].isWoodenPressurePlate())
                            && (x != -1 && worldContainer.entities.get(i).getEntityType() != null || worldContainer.blocks[worldContainer.layer][y][x].isWoodenPressurePlate()))) {
                        if (worldContainer.blocks[worldContainer.layer][y][x].isPressurePlate()) {
                            worldContainer.blocks[worldContainer.layer][y][x] = BlockNames.turnPressurePlateOn(worldContainer.blocks[worldContainer.layer][y][x]);
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

    public boolean hasOpenSpace(int x, int y, int l) {
        try {
            return (worldContainer.blocks[l][y - 1][x - 1] == BlockNames.AIR || !worldContainer.blocks[l][y - 1][x - 1].isCds() ||
                    worldContainer.blocks[l][y - 1][x] == BlockNames.AIR || !worldContainer.blocks[l][y - 1][x].isCds() ||
                    worldContainer.blocks[l][y - 1][x + 1] == BlockNames.AIR || !worldContainer.blocks[l][y - 1][x + 1].isCds() ||
                    worldContainer.blocks[l][y][x - 1] == BlockNames.AIR || !worldContainer.blocks[l][y][x - 1].isCds() ||
                    worldContainer.blocks[l][y][x + 1] == BlockNames.AIR || !worldContainer.blocks[l][y][x + 1].isCds() ||
                    worldContainer.blocks[l][y + 1][x - 1] == BlockNames.AIR || !worldContainer.blocks[l][y + 1][x - 1].isCds() ||
                    worldContainer.blocks[l][y + 1][x] == BlockNames.AIR || !worldContainer.blocks[l][y + 1][x].isCds() ||
                    worldContainer.blocks[l][y + 1][x + 1] == BlockNames.AIR || !worldContainer.blocks[l][y + 1][x + 1].isCds());
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public String checkBiome(int x, int y) {
        int desert = 0;
        int frost = 0;
        int swamp = 0;
        int jungle = 0;
        int cavern = 0;
        for (x2 = x - 15; x2 < x + 16; x2++) {
            for (y2 = y - 15; y2 < y + 16; y2++) {
                if (x2 + u >= 0 && x2 + u < WIDTH && y2 + v >= 0 && y2 + v < HEIGHT) {
                    if (worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.SAND || worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.SANDSTONE) {
                        desert += 1;
                    } else if (worldContainer.blocks[1][y2 + v][x2 + u] != BlockNames.AIR) {
                        desert -= 1;
                    }
                    if (worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.DIRT || worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.GRASS || worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.JUNGLE_GRASS) {
                        jungle += 1;
                    } else if (worldContainer.blocks[1][y2 + v][x2 + u] != BlockNames.AIR) {
                        jungle -= 1;
                    }
                    if (worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.SWAMP_GRASS || worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.MUD) {
                        swamp += 1;
                    } else if (worldContainer.blocks[1][y2 + v][x2 + u] != BlockNames.AIR) {
                        swamp -= 1;
                    }
                    if (worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.SNOW) {
                        frost += 1;
                    } else if (worldContainer.blocks[1][y2 + v][x2 + u] != BlockNames.AIR) {
                        frost -= 1;
                    }
                    if (worldContainer.blockbgs[y2 + v][x2 + u] == 0) {
                        cavern += 1;
                    }
                    if (worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.DIRT || worldContainer.blocks[1][y2 + v][x2 + u] == BlockNames.STONE) {
                        cavern += 1;
                    } else {
                        cavern -= 1;
                    }
                }
            }
        }
        if (desert > 0) {
            return "desert";
        }
        if (jungle > 0) {
            return "jungle";
        }
        if (swamp > 0) {
            return "swamp";
        }
        if (frost > 0) {
            return "frost";
        }
        if (cavern > 0) {
            return "cavern";
        }
        return "other";
    }

    public void breakCurrentBlock() {
        if (DebugContext.INSTAMINE || worldContainer.mining >= DURABILITY.get(worldContainer.inventory.tool()).get(worldContainer.blocks[worldContainer.layer][uy][ux])) {
            if (worldContainer.blocks[0][uy][ux] == BlockNames.TREE_ROOT) {
                worldContainer.blocks[0][uy][ux] = BlockNames.AIR;
                for (uly = uy - 1; uly < uy + 2; uly++) {
                    for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.blockdns[uly][ulx] = (byte) RandomTool.nextInt(5);
                    }
                }
                for (uly = uy; uly < uy + 3; uly++) {
                    for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.drawn[uly][ulx] = false;
                    }
                }
            }
            if (worldContainer.blocks[0][uy + 1][ux] == BlockNames.TREE_ROOT) {
                worldContainer.blocks[0][uy + 1][ux] = BlockNames.AIR;
                for (uly = uy; uly < uy + 3; uly++) {
                    for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.blockdns[uly][ulx] = (byte) RandomTool.nextInt(5);
                    }
                }
                for (uly = uy; uly < uy + 3; uly++) {
                    for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.drawn[uly][ulx] = false;
                    }
                }
            }
            if (worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.WORKBENCH
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.WOODEN_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.STONE_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.COPPER_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.IRON_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.SILVER_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.GOLD_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.FURNACE
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.FURNACE_ON
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.ZINC_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.RHYMESTONE_CHEST
                    || worldContainer.blocks[worldContainer.layer][uy][ux] == BlockNames.OBDURITE_CHEST) {
                if (worldContainer.ic != null) {
                    for (i = 0; i < worldContainer.ic.getIds().length; i++) {
                        if (worldContainer.ic.getIds()[i] != Items.EMPTY && !(worldContainer.ic.getType() == ItemType.FURNACE && i == 1)) {
                            worldContainer.entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i]));
                        }
                    }
                }
                if (worldContainer.icmatrix[worldContainer.layer][uy][ux] != null) {
                    for (i = 0; i < worldContainer.icmatrix[worldContainer.layer][uy][ux].getIds().length; i++) {
                        if (worldContainer.icmatrix[worldContainer.layer][uy][ux].getIds()[i] != Items.EMPTY && !(worldContainer.icmatrix[worldContainer.layer][uy][ux].getType() == ItemType.FURNACE && i == 1)) {
                            worldContainer.entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.icmatrix[worldContainer.layer][uy][ux].getIds()[i], worldContainer.icmatrix[worldContainer.layer][uy][ux].getNums()[i], worldContainer.icmatrix[worldContainer.layer][uy][ux].getDurs()[i]));
                        }
                    }
                    worldContainer.icmatrix[worldContainer.layer][uy][ux] = null;
                }
                worldContainer.ic = null;
                for (i = 0; i < worldContainer.machinesx.size(); i++) {
                    if (worldContainer.machinesx.get(i) == ux && worldContainer.machinesy.get(i) == uy) {
                        worldContainer.machinesx.remove(i);
                        worldContainer.machinesy.remove(i);
                        break;
                    }
                }
            }
            if (worldContainer.blocks[worldContainer.layer][uy][ux] != BlockNames.AIR && worldContainer.blocks[worldContainer.layer][uy][ux].getDrops() != Items.EMPTY) {
                worldContainer.entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux].getDrops(), (short) 1));
            }
            Items t = Items.EMPTY;
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
                for (i = 0; i < Math.max(1, n); i++) {
                    worldContainer.entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, t, (short) 1));
                }
            }
            removeBlockLighting(ux, uy);
            worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
            worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.AIR;
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
            worldContainer.blockds[worldContainer.layer] = World.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blockds[worldContainer.layer], ux, uy);
            for (uly = uy - 1; uly < uy + 2; uly++) {
                for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                    worldContainer.blockdns[uly][ulx] = (byte) RandomTool.nextInt(5);
                }
            }
            for (uly = uy - 1; uly < uy + 2; uly++) {
                for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                    worldContainer.drawn[uly][ulx] = false;
                }
            }
            for (uly = uy - 4; uly < uy + 5; uly++) {
                for (ulx = ux - 4; ulx < ux + 5; ulx++) {
                    for (int l = 0; l < 3; l += 2) {
                        if (uly >= 0 && uly < HEIGHT && worldContainer.blocks[l][uly][ulx] == BlockNames.LEAVES) {
                            keepLeaf = false;
                            for (uly2 = uly - 4; uly2 < uly + 5; uly2++) {
                                for (ulx2 = ulx - 4; ulx2 < ulx + 5; ulx2++) {
                                    if (uly2 >= 0 && uly2 < HEIGHT && (worldContainer.blocks[1][uly2][ulx2] == BlockNames.TREE || worldContainer.blocks[1][uly2][ulx2] == BlockNames.TREE_NO_BARK)) {
                                        keepLeaf = true;
                                        break;
                                    }
                                }
                                if (keepLeaf) {
                                    break;
                                }
                            }
                            if (!keepLeaf) {
                                worldContainer.blocks[l][uly][ulx] = BlockNames.AIR;
                                worldContainer.blockds[l] = World.generateOutlines(worldContainer.blocks[l], worldContainer.blockds[l], ulx, uly);
                                for (uly2 = uly - 1; uly2 < uly + 2; uly2++) {
                                    for (ulx2 = ulx - 1; ulx2 < ulx + 2; ulx2++) {
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
                    worldContainer.entities.add(new Entity((ux - 1) * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux - 1].getDrops(), (short) 1));
                    removeBlockLighting(ux - 1, uy);
                    if (worldContainer.layer == 1) {
                        addSunLighting(ux - 1, uy);
                    }
                    worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux - 1];
                    worldContainer.blocks[worldContainer.layer][uy][ux - 1] = BlockNames.AIR;
                    if (worldContainer.blockTemp.isZythiumWire()) {
                        redoBlockPower(ux, uy, worldContainer.layer);
                    }
                    if (worldContainer.blockTemp.isPower()) {
                        removeBlockPower(ux, uy, worldContainer.layer);
                    }
                    worldContainer.drawn[uy][ux - 1] = false;
                }
                if (TORCHESL.get(worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops().getBlocks()) != null && TORCHESL.get(worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops().getBlocks()) == worldContainer.blocks[worldContainer.layer][uy][ux + 1] || (worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops() == Items.LEVER || worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops() == Items.BUTTON)) {
                    worldContainer.entities.add(new Entity((ux + 1) * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux + 1].getDrops(), (short) 1));
                    removeBlockLighting(ux + 1, uy);
                    if (worldContainer.layer == 1) {
                        addSunLighting(ux + 1, uy);
                    }
                    worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux + 1];
                    worldContainer.blocks[worldContainer.layer][uy][ux + 1] = BlockNames.AIR;
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
                    worldContainer.entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, worldContainer.blocks[worldContainer.layer][uy][ux].getDrops(), (short) 1));
                }
                t = Items.EMPTY;
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
                    for (i = 0; i < Math.max(1, n); i++) {
                        worldContainer.entities.add(new Entity(ux * BLOCKSIZE, uy * BLOCKSIZE, RandomTool.nextDouble() * 4 - 2, -2, t, (short) 1));
                    }
                }
                removeBlockLighting(ux, uy);
                worldContainer.blockTemp = worldContainer.blocks[worldContainer.layer][uy][ux];
                worldContainer.blocks[worldContainer.layer][uy][ux] = BlockNames.AIR;
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
                worldContainer.blockds[worldContainer.layer] = World.generateOutlines(worldContainer.blocks[worldContainer.layer], worldContainer.blockds[worldContainer.layer], ux, uy);
                for (uly = uy - 1; uly < uy + 2; uly++) {
                    for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.blockdns[uly][ulx] = (byte) RandomTool.nextInt(5);
                    }
                }
                for (uly = uy - 1; uly < uy + 2; uly++) {
                    for (ulx = ux - 1; ulx < ux + 2; ulx++) {
                        worldContainer.drawn[uly][ulx] = false;
                    }
                }
                for (uly = uy - 4; uly < uy + 5; uly++) {
                    for (ulx = ux - 4; ulx < ux + 5; ulx++) {
                        for (int l = 0; l < 3; l += 2) {
                            if (uly >= 0 && uly < HEIGHT && worldContainer.blocks[l][uly][ulx] == BlockNames.LEAVES) {
                                keepLeaf = false;
                                for (uly2 = uly - 4; uly2 < uly + 5; uly2++) {
                                    for (ulx2 = ulx - 4; ulx2 < ulx + 5; ulx2++) {
                                        if (uly2 >= 0 && uly2 < HEIGHT && (worldContainer.blocks[1][uly2][ulx2] == BlockNames.TREE || worldContainer.blocks[1][uly2][ulx2] == BlockNames.TREE_NO_BARK)) {
                                            keepLeaf = true;
                                            break;
                                        }
                                    }
                                    if (keepLeaf) {
                                        break;
                                    }
                                }
                                if (!keepLeaf) {
                                    worldContainer.blocks[l][uly][ulx] = BlockNames.AIR;
                                    worldContainer.blockds[l] = World.generateOutlines(worldContainer.blocks[l], worldContainer.blockds[l], ulx, uly);
                                    for (uly2 = uly - 1; uly2 < uly + 2; uly2++) {
                                        for (ulx2 = ulx - 1; ulx2 < ulx + 2; ulx2++) {
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
        for (i = worldContainer.cloudsAggregate.getClouds().size() - 1; i > -1; i--) {
            worldContainer.cloudsAggregate.updateXUponSpeed(i);
            if (worldContainer.cloudsAggregate.isOutsideBounds(i, getWidth())) {
                worldContainer.cloudsAggregate.removeCloud(i);
            }
        }
        if (RandomTool.nextInt((int) (1500 / DebugContext.ACCEL)) == 0) {

            Cloud cloud = new Cloud();
            cloud.setN(RandomTool.nextInt(1));
            cloudImage = cloudsImages[cloud.getN()];
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
        n = findNonLayeredBlockLightSource(ux, uy);
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
        n = findNonLayeredBlockLightSource(ux, uy);
        if (n != 0) {
            worldContainer.lsources[uy][ux] = isNonLayeredBlockLightSource(ux, uy, layer);
            for (axl = -n; axl < n + 1; axl++) {
                for (ayl = -n; ayl < n + 1; ayl++) {
                    if (Math.abs(axl) + Math.abs(ayl) <= n && uy + ayl >= 0 && uy + ayl < HEIGHT && worldContainer.lights[uy + ayl][ux + axl] != 0) {
                        addTileToZQueue(ux + axl, uy + ayl);
                        worldContainer.lights[uy + ayl][ux + axl] = (float) 0;
                    }
                }
            }
            for (axl = -n - BRIGHTEST; axl < n + 1 + BRIGHTEST; axl++) {
                for (ayl = -n - BRIGHTEST; ayl < n + 1 + BRIGHTEST; ayl++) {
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
                                !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
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
                                !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
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
            if (worldContainer.blocks[lyr][ay3][ax3] == BlockNames.ZYTHIUM_LAMP_ON || (worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer()) &&
                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                if (worldContainer.blocks[lyr][ay3][ax3].isZythiumInverterOnAll()) {
                    worldContainer.blocks[lyr][ay3][ax3] = BlockNames.turnZythiumInverterOff(worldContainer.blocks[lyr][ay3][ax3]);
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
                                    !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                            worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                            !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                                            !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                    worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
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
                                !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][uy][ux] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
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
                if (worldContainer.blocks[lyr][ay3][ax3] == BlockNames.ZYTHIUM_LAMP_ON || (worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() || worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer()) &&
                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                        !(worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                worldContainer.blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[lyr][ay3][ax3] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                    if (worldContainer.blocks[lyr][ay3][ax3].isZythiumInverterOnAll()) {
                        worldContainer.blocks[lyr][ay3][ax3] = BlockNames.turnZythiumInverterOff(worldContainer.blocks[lyr][ay3][ax3]);
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
        if (worldContainer.blocks[lyr][uy][ux] == BlockNames.ZYTHIUM_LAMP_ON) {
            removeBlockLighting(ux, uy);
            worldContainer.blocks[lyr][uy][ux] = BlockNames.ZYTHIUM_LAMP;
            worldContainer.rdrawn[uy][ux] = false;
        }
        if (worldContainer.blocks[lyr][uy][ux].isZythiumAmplifierOnAll()) {
            worldContainer.blockTemp = worldContainer.blocks[lyr][uy][ux];
            worldContainer.blocks[lyr][uy][ux] = BlockNames.turnZythiumAmplifierOff(worldContainer.blocks[lyr][uy][ux]);
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
        for (ax = -BRIGHTEST; ax < BRIGHTEST + 1; ax++) {
            for (ay = -BRIGHTEST; ay < BRIGHTEST + 1; ay++) {
                if (Math.abs(ax) + Math.abs(ay) <= BRIGHTEST && uy + ay >= 0 && uy + ay < HEIGHT) {
                    addTileToZQueue(ux + ax, uy + ay);
                    worldContainer.lights[uy + ay][ux + ax] = (float) 0;
                }
            }
        }
        for (ax = -BRIGHTEST * 2; ax < BRIGHTEST * 2 + 1; ax++) {
            for (ay = -BRIGHTEST * 2; ay < BRIGHTEST * 2 + 1; ay++) {
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
        for (y = 0; y < uy; y++) {
            if (worldContainer.blocks[1][y][ux].isLTrans()) {
                return;
            }
        }
        addSources = false;
        for (y = uy; y < HEIGHT - 1; y++) {
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
        n = sunlightlevel;
        for (y = 0; y < uy; y++) {
            if (worldContainer.blocks[1][y][ux].isLTrans()) {
                return;
            }
        }
        for (y = uy; y < HEIGHT; y++) {
            worldContainer.lsources[y][ux] = isBlockLightSource(ux, y);
            if (y != uy && worldContainer.blocks[1][y][ux].isLTrans()) {
                break;
            }
        }
        for (ax = -n; ax < n + 1; ax++) {
            for (ay = -n; ay < n + (y - uy) + 1; ay++) {
                if (uy + ay >= 0 && uy + ay < WIDTH) {
                    addTileToZQueue(ux + ax, uy + ay);
                    worldContainer.lights[uy + ay][ux + ax] = (float) 0;
                }
            }
        }
        for (ax = -n - BRIGHTEST; ax < n + 1 + BRIGHTEST; ax++) {
            for (ay = -n - BRIGHTEST; ay < n + (y - uy) + 1 + BRIGHTEST; ay++) {
                if (uy + ay >= 0 && uy + ay < HEIGHT) {
                    if (worldContainer.lsources[uy + ay][ux + ax]) {
                        addTileToQueue(ux + ax, uy + ay);
                    }
                }
            }
        }
    }

    public boolean isReachedBySunlight(int ux, int uy) {
        for (ay = 0; ay < uy + 1; ay++) {
            if (worldContainer.blocks[1][ay][ux].isLTrans()) {
                return false;
            }
        }
        return true;
    }

    public boolean isBlockLightSource(int ux, int uy) {
        return (worldContainer.blocks[0][uy][ux] != BlockNames.AIR && worldContainer.blocks[0][uy][ux].getLights() != 0 ||
                worldContainer.blocks[1][uy][ux] != BlockNames.AIR && worldContainer.blocks[1][uy][ux].getLights() != 0 ||
                worldContainer.blocks[2][uy][ux] != BlockNames.AIR && worldContainer.blocks[2][uy][ux].getLights() != 0);
    }

    public boolean isNonLayeredBlockLightSource(int ux, int uy) {
        return isNonLayeredBlockLightSource(ux, uy, worldContainer.layer);
    }

    public boolean isNonLayeredBlockLightSource(int ux, int uy, int layer) {
        return (layer != 0 && worldContainer.blocks[0][uy][ux] != BlockNames.AIR && worldContainer.blocks[0][uy][ux].getLights() != 0 ||
                layer != 1 && worldContainer.blocks[1][uy][ux] != BlockNames.AIR && worldContainer.blocks[1][uy][ux].getLights() != 0 ||
                layer != 2 && worldContainer.blocks[2][uy][ux] != BlockNames.AIR && worldContainer.blocks[2][uy][ux].getLights() != 0);
    }

    public int findBlockLightSource(int ux, int uy) {
        n = 0;
        if (worldContainer.blocks[0][uy][ux] != BlockNames.AIR) {
            n = Math.max(worldContainer.blocks[0][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[1][uy][ux] != BlockNames.AIR) {
            n = Math.max(worldContainer.blocks[1][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[2][uy][ux] != BlockNames.AIR) {
            n = Math.max(worldContainer.blocks[2][uy][ux].getLights(), n);
        }
        return n;
    }

    public int findNonLayeredBlockLightSource(int ux, int uy) {
        return findNonLayeredBlockLightSource(ux, uy, worldContainer.layer);
    }

    public int findNonLayeredBlockLightSource(int ux, int uy, int layer) {
        n = 0;
        if (worldContainer.blocks[0][uy][ux] != BlockNames.AIR) {
            n = Math.max(worldContainer.blocks[0][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[1][uy][ux] != BlockNames.AIR) {
            n = Math.max(worldContainer.blocks[1][uy][ux].getLights(), n);
        }
        if (worldContainer.blocks[2][uy][ux] != BlockNames.AIR) {
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
            for (int l = 0; l < 3; l++) {
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
        try {
            while (true) {
                x = worldContainer.lqx.get(0);
                y = worldContainer.lqy.get(0);
                if (worldContainer.lsources[y][x]) {
                    n = findBlockLightSource(x, y);
                    if (isReachedBySunlight(x, y)) {
                        worldContainer.lights[y][x] = MathTool.max(worldContainer.lights[y][x], n, sunlightlevel);
                    } else {
                        worldContainer.lights[y][x] = Math.max(worldContainer.lights[y][x], n);
                    }
                    addTileToZQueue(x, y);
                }
                for (i = 0; i < 4; i++) {
                    x2 = x + cl[i][0];
                    y2 = y + cl[i][1];
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
                worldContainer.lqx.remove(0);
                worldContainer.lqy.remove(0);
                worldContainer.lqd[y][x] = false;
            }
        } catch (IndexOutOfBoundsException e) {
            //
        }
        for (i = 0; i < zqx.size(); i++) {
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
        try {
            while (true) {
                x = pqx.get(0);
                y = pqy.get(0);
                for (int l = 0; l < 3; l++) {
                    if (worldContainer.blocks[l][y][x].isPower()) {
                        if (!worldContainer.blocks[l][y][x].isCompleteZythiumDelayer()) {
                            addTileToPQueue(x, y);
                            worldContainer.power[l][y][x] = (float) 5;
                        }
                    }
                }
                for (i = 0; i < 4; i++) {
                    x2 = x + cl[i][0];
                    y2 = y + cl[i][1];
                    if (y2 >= 0 && y2 < HEIGHT) {
                        for (int l = 0; l < 3; l++) {
                            if (worldContainer.power[l][y][x] > 0) {
                                if (worldContainer.blocks[l][y][x].getConduct() >= 0 && worldContainer.blocks[l][y2][x2].isReceive() && !(worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && x < x2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && y < y2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && x > x2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        worldContainer.blocks[l][y2][x2].isCompleteZythiumAmplifier() && y > y2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && x < x2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && y < y2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && x > x2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumAmplifier() && y > y2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_UP && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && x < x2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && y < y2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && x > x2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumInverter() && y > y2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && x < x2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_RIGHT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && y < y2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_DOWN && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_DOWN_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && x > x2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_LEFT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_LEFT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumInverter() && y > y2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_UP && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && x < x2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && y < y2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && x > x2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[l][y2][x2].isCompleteZythiumDelayer() && y > y2 && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[l][y2][x2] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON) &&
                                        !(worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && x < x2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && y < y2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && x > x2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                worldContainer.blocks[l][y][x].isCompleteZythiumDelayer() && y > y2 && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_1_DELAY_UP_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_2_DELAY_UP_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_4_DELAY_UP_ON && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP && worldContainer.blocks[l][y][x] != BlockNames.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
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
                                        worldContainer.blocks[l][y2][x2] = BlockNames.turnZythiumInverterOn(worldContainer.blocks[l][y2][x2]);
                                        removeBlockLighting(x2, y2);
                                        worldContainer.rdrawn[y2][x2] = false;
                                    }
                                }
                            }
                        }
                    }
                }
                pqx.remove(0);
                pqy.remove(0);
                pqd[y][x] = false;
                for (int l = 0; l < 3; l++) {
                    log.info("[resolvePowerMatrix] " + x + " " + y + " " + l + " " + worldContainer.blocks[l][y][x] + " " + worldContainer.power[l][y][x]);
                    if (worldContainer.power[l][y][x] > 0) {
                        if (worldContainer.blocks[l][y][x] == BlockNames.ZYTHIUM_LAMP) {
                            worldContainer.blocks[l][y][x] = BlockNames.ZYTHIUM_LAMP_ON;
                            addBlockLighting(x, y);
                            worldContainer.rdrawn[y][x] = false;
                        }
                        if (worldContainer.blocks[l][y][x].isZythiumAmplifierAll()) {
                            log.info("Processed amplifier at " + x + " " + y);
                            worldContainer.blocks[l][y][x] = BlockNames.turnZythiumAmplifierOn(worldContainer.blocks[l][y][x]);
                            addTileToPQueue(x, y);
                            addBlockLighting(x, y);
                            worldContainer.rdrawn[y][x] = false;
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            //
        }
        for (i = 0; i < pzqx.size(); i++) {
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
        pg2 = screen.createGraphics();
        pg2.setColor(bg);
        pg2.fillRect(0, 0, getWidth(), getHeight());
        if (state == State.IN_GAME) {
            if (worldContainer.player.y / 16 < HEIGHT * 0.5) {
                pg2.translate(getWidth() / 2, getHeight() * 0.85);
                pg2.rotate((worldContainer.timeOfDay - 70200) / 86400 * Math.PI * 2);

                pg2.drawImage(sun,
                        (int) (-getWidth() * 0.65), 0, (int) (-getWidth() * 0.65 + sun.getWidth() * 2), sun.getHeight() * 2,
                        0, 0, sun.getWidth(), sun.getHeight(),
                        null);

                pg2.rotate(Math.PI);

                pg2.drawImage(moon,
                        (int) (-getWidth() * 0.65), 0, (int) (-getWidth() * 0.65 + moon.getWidth() * 2), moon.getHeight() * 2,
                        0, 0, moon.getWidth(), moon.getHeight(),
                        null);

                pg2.rotate(-(worldContainer.timeOfDay - 70200) / 86400 * Math.PI * 2 - Math.PI);
                pg2.translate(-getWidth() / 2, -getHeight() * 0.85);

                for (i = 0; i < worldContainer.cloudsAggregate.getClouds().size(); i++) {
                    cloudImage = cloudsImages[worldContainer.cloudsAggregate.getClouds().get(i).getN()];
                    pg2.drawImage(cloudsImages[worldContainer.cloudsAggregate.getClouds().get(i).getN()],
                            (int) worldContainer.cloudsAggregate.getClouds().get(i).getX(), (int) worldContainer.cloudsAggregate.getClouds().get(i).getY(), (int) worldContainer.cloudsAggregate.getClouds().get(i).getX() + cloudImage.getWidth() * 2, (int) worldContainer.cloudsAggregate.getClouds().get(i).getY() + cloudImage.getHeight() * 2,
                            0, 0, cloudImage.getWidth(), cloudImage.getHeight(),
                            null);
                }
            }

            for (pwy = 0; pwy < 2; pwy++) {
                for (pwx = 0; pwx < 2; pwx++) {
                    int pwxc = pwx + ou;
                    int pwyc = pwy + ov;
                    if (worldContainer.worlds[pwy][pwx] != null) {
                        if (((worldContainer.player.ix + getWidth() / 2 + worldContainer.player.width >= pwxc * CHUNKSIZE &&
                                worldContainer.player.ix + getWidth() / 2 + worldContainer.player.width <= pwxc * CHUNKSIZE + CHUNKSIZE) ||
                                (worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width + BLOCKSIZE >= pwxc * CHUNKSIZE &&
                                        worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width - BLOCKSIZE <= pwxc * CHUNKSIZE + CHUNKSIZE)) &&
                                ((worldContainer.player.iy + getHeight() / 2 + worldContainer.player.height >= pwyc * CHUNKSIZE &&
                                        worldContainer.player.iy + getHeight() / 2 + worldContainer.player.height <= pwyc * CHUNKSIZE + CHUNKSIZE) ||
                                        (worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height >= pwyc * CHUNKSIZE &&
                                                worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height <= pwyc * CHUNKSIZE + CHUNKSIZE))) {
                            pg2.drawImage(worldContainer.worlds[pwy][pwx],
                                    pwxc * CHUNKSIZE - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2, pwyc * CHUNKSIZE - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2, pwxc * CHUNKSIZE - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 + CHUNKSIZE, pwyc * CHUNKSIZE - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2 + CHUNKSIZE,
                                    0, 0, CHUNKSIZE, CHUNKSIZE,
                                    null);
                        }
                    }
                }
            }

            pg2.drawImage(worldContainer.player.image,
                    getWidth() / 2 - worldContainer.player.width / 2, getHeight() / 2 - worldContainer.player.height / 2, getWidth() / 2 + worldContainer.player.width / 2, getHeight() / 2 + worldContainer.player.height / 2,
                    0, 0, worldContainer.player.image.getWidth(), worldContainer.player.image.getHeight(),
                    null);

            for (i = 0; i < worldContainer.entities.size(); i++) {
                entity = worldContainer.entities.get(i);
                pg2.drawImage(entity.getImage(),
                        entity.getIx() - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2, entity.getIy() - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2, entity.getIx() - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 + entity.getWidth(), entity.getIy() - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2 + entity.getHeight(),
                        0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                        null);
                pg2.drawImage(entity.getImage(),
                        entity.getIx() - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 - WIDTH * BLOCKSIZE, entity.getIy() - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2, entity.getIx() - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 + entity.getWidth() - WIDTH * BLOCKSIZE, entity.getIy() - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2 + entity.getHeight(),
                        0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                        null);
                pg2.drawImage(entity.getImage(),
                        entity.getIx() - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 + WIDTH * BLOCKSIZE, entity.getIy() - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2, entity.getIx() - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 + entity.getWidth() + WIDTH * BLOCKSIZE, entity.getIy() - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2 + entity.getHeight(),
                        0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                        null);
            }

            if (worldContainer.showTool && tool != null) {
                if (worldContainer.player.imgState == ImageState.STILL_RIGHT || worldContainer.player.imgState.isWalkRight()) {
                    pg2.translate(getWidth() / 2 + 6, getHeight() / 2);
                    pg2.rotate(worldContainer.toolAngle);

                    pg2.drawImage(tool,
                            0, -tool.getHeight() * 2, tool.getWidth() * 2, 0,
                            0, 0, tool.getWidth(), tool.getHeight(),
                            null);

                    pg2.rotate(-worldContainer.toolAngle);
                    pg2.translate(-getWidth() / 2 - 6, -getHeight() / 2);
                }
                if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                    pg2.translate(getWidth() / 2 - 6, getHeight() / 2);
                    pg2.rotate((Math.PI * 1.5) - worldContainer.toolAngle);

                    pg2.drawImage(tool,
                            0, -tool.getHeight() * 2, tool.getWidth() * 2, 0,
                            0, 0, tool.getWidth(), tool.getHeight(),
                            null);

                    pg2.rotate(-((Math.PI * 1.5) - worldContainer.toolAngle));
                    pg2.translate(-getWidth() / 2 + 6, -getHeight() / 2);
                }
            }

            for (pwy = 0; pwy < 2; pwy++) {
                for (pwx = 0; pwx < 2; pwx++) {
                    int pwxc = pwx + ou;
                    int pwyc = pwy + ov;
                    if (worldContainer.fworlds[pwy][pwx] != null) {
                        if (((worldContainer.player.ix + getWidth() / 2 + worldContainer.player.width >= pwxc * CHUNKSIZE &&
                                worldContainer.player.ix + getWidth() / 2 + worldContainer.player.width <= pwxc * CHUNKSIZE + CHUNKSIZE) ||
                                (worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width + BLOCKSIZE >= pwxc * CHUNKSIZE &&
                                        worldContainer.player.ix - getWidth() / 2 + worldContainer.player.width - BLOCKSIZE <= pwxc * CHUNKSIZE + CHUNKSIZE)) &&
                                ((worldContainer.player.iy + getHeight() / 2 + worldContainer.player.height >= pwyc * CHUNKSIZE &&
                                        worldContainer.player.iy + getHeight() / 2 + worldContainer.player.height <= pwyc * CHUNKSIZE + CHUNKSIZE) ||
                                        (worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height >= pwyc * CHUNKSIZE &&
                                                worldContainer.player.iy - getHeight() / 2 + worldContainer.player.height <= pwyc * CHUNKSIZE + CHUNKSIZE))) {
                            pg2.drawImage(worldContainer.fworlds[pwy][pwx],
                                    pwxc * CHUNKSIZE - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2, pwyc * CHUNKSIZE - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2, pwxc * CHUNKSIZE - worldContainer.player.ix + getWidth() / 2 - worldContainer.player.width / 2 + CHUNKSIZE, pwyc * CHUNKSIZE - worldContainer.player.iy + getHeight() / 2 - worldContainer.player.height / 2 + CHUNKSIZE,
                                    0, 0, CHUNKSIZE, CHUNKSIZE,
                                    null);
                        }
                    }
                }
            }

            if (worldContainer.showInv) {
                pg2.drawImage(worldContainer.inventory.image,
                        0, 0, worldContainer.inventory.image.getWidth(), (int) worldContainer.inventory.image.getHeight(),
                        0, 0, worldContainer.inventory.image.getWidth(), (int) worldContainer.inventory.image.getHeight(),
                        null);
                pg2.drawImage(armor.getImage(),
                        worldContainer.inventory.image.getWidth() + 6, 6, worldContainer.inventory.image.getWidth() + 6 + armor.getImage().getWidth(), 6 + armor.getImage().getHeight(),
                        0, 0, armor.getImage().getWidth(), armor.getImage().getHeight(),
                        null);
                pg2.drawImage(worldContainer.cic.getImage(),
                        worldContainer.inventory.image.getWidth() + 75, 52, worldContainer.inventory.image.getWidth() + 75 + worldContainer.cic.getImage().getWidth(), 52 + worldContainer.cic.getImage().getHeight(),
                        0, 0, worldContainer.cic.getImage().getWidth(), worldContainer.cic.getImage().getHeight(),
                        null);
            } else {
                pg2.drawImage(worldContainer.inventory.image,
                        0, 0, worldContainer.inventory.image.getWidth(), (int) worldContainer.inventory.image.getHeight() / 4,
                        0, 0, worldContainer.inventory.image.getWidth(), (int) worldContainer.inventory.image.getHeight() / 4,
                        null);
            }

            if (worldContainer.ic != null) {
                pg2.drawImage(worldContainer.ic.getImage(),
                        6, worldContainer.inventory.image.getHeight() + 46, 6 + worldContainer.ic.getImage().getWidth(), worldContainer.inventory.image.getHeight() + 46 + worldContainer.ic.getImage().getHeight(),
                        0, 0, worldContainer.ic.getImage().getWidth(), worldContainer.ic.getImage().getHeight(),
                        null);
            }

            if (worldContainer.layer == 0) {
                layerImg = ResourcesLoader.loadImage("interface/layersB.png");
            }
            if (worldContainer.layer == 1) {
                layerImg = ResourcesLoader.loadImage("interface/layersN.png");
            }
            if (worldContainer.layer == 2) {
                layerImg = ResourcesLoader.loadImage("interface/layersF.png");
            }

            pg2.drawImage(layerImg,
                    worldContainer.inventory.image.getWidth() + 58, 6, worldContainer.inventory.image.getWidth() + 58 + layerImg.getWidth(), 6 + layerImg.getHeight(),
                    0, 0, layerImg.getWidth(), layerImg.getHeight(),
                    null);

            if (worldContainer.showInv) {
                pg2.drawImage(save_exit,
                        getWidth() - save_exit.getWidth() - 24, getHeight() - save_exit.getHeight() - 24, getWidth() - 24, getHeight() - 24,
                        0, 0, save_exit.getWidth(), save_exit.getHeight(),
                        null);
            }

            if (worldContainer.moveItem != Items.EMPTY) {
                width = itemImgs.get(worldContainer.moveItem).getWidth();
                height = itemImgs.get(worldContainer.moveItem).getHeight();
                pg2.drawImage(itemImgs.get(worldContainer.moveItem),
                        mousePos.getX() + 12 + ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * width * 2) / 2), mousePos.getY() + 12 + ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * height * 2) / 2), mousePos.getX() + 36 - ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * width * 2) / 2), mousePos.getY() + 36 - ((int) (24 - (double) 12 / MathTool.max(width, height, 12) * height * 2) / 2),
                        0, 0, width, height,
                        null);
                if (worldContainer.moveNum > 1) {
                    pg2.setFont(font);
                    pg2.setColor(Color.WHITE);
                    pg2.drawString(worldContainer.moveNum + " ", mousePos.getX() + 13, mousePos.getY() + 38);
                }
            }
            for (i = 0; i < worldContainer.entities.size(); i++) {
                if (UIENTITIES.get(worldContainer.entities.get(i).getEntityType().getName()) != null && worldContainer.entities.get(i).getRect() != null && worldContainer.entities.get(i).getRect().contains(new Point(mousePos2.getX(), mousePos2.getY()))) {
                    pg2.setFont(mobFont);
                    pg2.setColor(Color.WHITE);
                    pg2.drawString(UIENTITIES.get(worldContainer.entities.get(i).getEntityType()) + " (" + worldContainer.entities.get(i).getHealthPoints() + "/" + worldContainer.entities.get(i).getTotalHealthPoints() + ")", mousePos.getX(), mousePos.getY());
                    break;
                }
            }
            if (worldContainer.showInv) {
                ymax = 4;
            } else {
                ymax = 1;
            }
            for (ux = 0; ux < 10; ux++) {
                for (uy = 0; uy < ymax; uy++) {
                    if (mousePos.isInBetweenInclusive(ux * 46 + 6, ux * 46 + 46, uy * 46 + 6, uy * 46 + 46) && worldContainer.inventory.items[uy * 10 + ux] != Items.EMPTY) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (worldContainer.inventory.items[uy * 10 + ux].getDurability() != null) {
                            pg2.drawString(worldContainer.inventory.items[uy * 10 + ux].getUiName() + " (" + (int) ((double) worldContainer.inventory.durs[uy * 10 + ux] / worldContainer.inventory.items[uy * 10 + ux].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                        } else {
                            pg2.drawString(worldContainer.inventory.items[uy * 10 + ux].getUiName(), mousePos.getX(), mousePos.getY());
                        }
                    }
                }
            }
            pg2.setFont(mobFont);
            pg2.setColor(Color.WHITE);
            pg2.drawString("Health: " + worldContainer.player.hp + "/" + worldContainer.player.thp, getWidth() - 125, 20);
            pg2.drawString("Armor: " + worldContainer.player.sumArmor(), getWidth() - 125, 40);
            if (DebugContext.STATS) {
                pg2.drawString("(" + ((int) worldContainer.player.ix / 16) + ", " + ((int) worldContainer.player.iy / 16) + ")", getWidth() - 125, 60);
                if (worldContainer.player.iy >= 0 && worldContainer.player.iy < HEIGHT * BLOCKSIZE) {
                    pg2.drawString(checkBiome((int) worldContainer.player.ix / 16 + u, (int) worldContainer.player.iy / 16 + v) + " " + worldContainer.lights[(int) worldContainer.player.iy / 16 + v][(int) worldContainer.player.ix / 16 + u], getWidth() - 125, 80);
                }
            }
            if (worldContainer.showInv) {
                for (ux = 0; ux < 2; ux++) {
                    for (uy = 0; uy < 2; uy++) {
                        if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + ux * 40 + 75, worldContainer.inventory.image.getWidth() + ux * 40 + 115, uy * 40 + 52, uy * 40 + 92) && worldContainer.cic.getIds()[uy * 2 + ux] != Items.EMPTY) {
                            pg2.setFont(mobFont);
                            pg2.setColor(Color.WHITE);
                            if (worldContainer.cic.getIds()[uy * 2 + ux].getDurability() != null) {
                                pg2.drawString(worldContainer.cic.getIds()[uy * 2 + ux].getUiName() + " (" + (int) ((double) worldContainer.cic.getDurs()[uy * 2 + ux] / worldContainer.cic.getIds()[uy * 2 + ux].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                            } else {
                                pg2.drawString(worldContainer.cic.getIds()[uy * 2 + ux].getUiName(), mousePos.getX(), mousePos.getY());
                            }
                        }
                    }
                }
                if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + 3 * 40 + 75, worldContainer.inventory.image.getWidth() + 3 * 40 + 115, 20 + 52, 20 + 92) && worldContainer.cic.getIds()[4] != Items.EMPTY) {
                    pg2.setFont(mobFont);
                    pg2.setColor(Color.WHITE);
                    if (worldContainer.cic.getIds()[4].getDurability() != null) {
                        pg2.drawString(worldContainer.cic.getIds()[4].getUiName() + " (" + (int) ((double) worldContainer.cic.getDurs()[4] / worldContainer.cic.getIds()[4].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                    } else {
                        pg2.drawString(worldContainer.cic.getIds()[4].getUiName(), mousePos.getX(), mousePos.getY());
                    }
                }
                for (uy = 0; uy < 4; uy++) {
                    if (mousePos.isInBetween(worldContainer.inventory.image.getWidth() + 6, worldContainer.inventory.image.getWidth() + 6 + armor.getImage().getWidth(), 6 + uy * 46, 6 + uy * 46 + 46) && armor.getIds()[uy] != Items.EMPTY) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (armor.getIds()[uy].getDurability() != null) {
                            pg2.drawString(armor.getIds()[uy].getUiName() + " (" + (int) ((double) armor.getDurs()[uy] / armor.getIds()[uy].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                        } else {
                            pg2.drawString(armor.getIds()[uy].getUiName(), mousePos.getX(), mousePos.getY());
                        }
                    }
                }
            }
            if (worldContainer.ic != null) {
                if (worldContainer.ic.getType() == ItemType.WORKBENCH) {
                    for (ux = 0; ux < 3; ux++) {
                        for (uy = 0; uy < 3; uy++) {
                            if (mousePos.isInBetween(ux * 40 + 6, ux * 40 + 46, uy * 40 + worldContainer.inventory.image.getHeight() + 46, uy * 40 + worldContainer.inventory.image.getHeight() + 86) &&
                                    worldContainer.ic.getIds()[uy * 3 + ux] != Items.EMPTY) {
                                pg2.setFont(mobFont);
                                pg2.setColor(Color.WHITE);
                                if (worldContainer.ic.getIds()[uy * 3 + ux].getDurability() != null) {
                                    pg2.drawString(worldContainer.ic.getIds()[uy * 3 + ux].getUiName() + " (" + (int) ((double) worldContainer.ic.getDurs()[uy * 3 + ux] / worldContainer.ic.getIds()[uy * 3 + ux].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                                } else {
                                    pg2.drawString(worldContainer.ic.getIds()[uy * 3 + ux].getUiName(), mousePos.getX(), mousePos.getY());
                                }
                            }
                        }
                    }
                    if (mousePos.isInBetween(4 * 40 + 6, 4 * 40 + 46, 1 * 40 + worldContainer.inventory.image.getHeight() + 46, 1 * 40 + worldContainer.inventory.image.getHeight() + 86) &&
                            worldContainer.ic.getIds()[9] != Items.EMPTY) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (worldContainer.ic.getIds()[9].getDurability() != null) {
                            pg2.drawString(worldContainer.ic.getIds()[9].getUiName() + " (" + (int) ((double) worldContainer.ic.getDurs()[9] / worldContainer.ic.getIds()[9].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                        } else {
                            pg2.drawString(worldContainer.ic.getIds()[9].getUiName(), mousePos.getX(), mousePos.getY());
                        }
                    }
                } else if (worldContainer.ic.getType() == ItemType.WOODEN_CHEST || worldContainer.ic.getType() == ItemType.STONE_CHEST ||
                        worldContainer.ic.getType() == ItemType.COPPER_CHEST || worldContainer.ic.getType() == ItemType.IRON_CHEST ||
                        worldContainer.ic.getType() == ItemType.SILVER_CHEST || worldContainer.ic.getType() == ItemType.GOLD_CHEST ||
                        worldContainer.ic.getType() == ItemType.ZINC_CHEST || worldContainer.ic.getType() == ItemType.RHYMESTONE_CHEST ||
                        worldContainer.ic.getType() == ItemType.OBDURITE_CHEST) {
                    for (ux = 0; ux < worldContainer.inventory.CX; ux++) {
                        for (uy = 0; uy < worldContainer.inventory.CY; uy++) {
                            if (mousePos.isInBetween(ux * 46 + 6, ux * 46 + 46, uy * 46 + worldContainer.inventory.image.getHeight() + 46, uy * 46 + worldContainer.inventory.image.getHeight() + 86) &&
                                    worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux] != Items.EMPTY) {
                                pg2.setFont(mobFont);
                                pg2.setColor(Color.WHITE);
                                if (worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux].getDurability() != null) {
                                    pg2.drawString(worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux].getUiName() + " (" + (int) ((double) worldContainer.ic.getDurs()[uy * worldContainer.inventory.CX + ux] / worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                                } else {
                                    pg2.drawString(worldContainer.ic.getIds()[uy * worldContainer.inventory.CX + ux].getUiName(), mousePos.getX(), mousePos.getY());
                                }
                            }
                        }
                    }
                } else if (worldContainer.ic.getType() == ItemType.FURNACE) {
                    if (mousePos.isInBetween(6, 46, worldContainer.inventory.image.getHeight() + 46, worldContainer.inventory.image.getHeight() + 86) &&
                            worldContainer.ic.getIds()[0] != Items.EMPTY) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (worldContainer.ic.getIds()[0].getDurability() != null) {
                            pg2.drawString(worldContainer.ic.getIds()[0].getUiName() + " (" + (int) ((double) worldContainer.ic.getDurs()[0] / worldContainer.ic.getIds()[0].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                        } else {
                            pg2.drawString(worldContainer.ic.getIds()[0].getUiName(), mousePos.getX(), mousePos.getY());
                        }
                    }
                    if (mousePos.isInBetween(6, 46, worldContainer.inventory.image.getHeight() + 102, worldContainer.inventory.image.getHeight() + 142) &&
                            worldContainer.ic.getIds()[1] != Items.EMPTY) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (worldContainer.ic.getIds()[1].getDurability() != null) {
                            pg2.drawString(worldContainer.ic.getIds()[1].getUiName() + " (" + (int) ((double) worldContainer.ic.getDurs()[1] / worldContainer.ic.getIds()[1].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                        } else {
                            pg2.drawString(worldContainer.ic.getIds()[1].getUiName(), mousePos.getX(), mousePos.getY());
                        }
                    }
                    if (mousePos.isInBetween(6, 46, worldContainer.inventory.image.getHeight() + 142, worldContainer.inventory.image.getHeight() + 182) &&
                            worldContainer.ic.getIds()[2] != Items.EMPTY) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (worldContainer.ic.getIds()[2].getDurability() != null) {
                            pg2.drawString(worldContainer.ic.getIds()[2].getUiName() + " (" + (int) ((double) worldContainer.ic.getDurs()[2] / worldContainer.ic.getIds()[2].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                        } else {
                            pg2.drawString(worldContainer.ic.getIds()[2].getUiName(), mousePos.getX(), mousePos.getY());
                        }
                    }
                    if (mousePos.isInBetween(62, 102, worldContainer.inventory.image.getHeight() + 46, worldContainer.inventory.image.getHeight() + 86) &&
                            worldContainer.ic.getIds()[3] != Items.EMPTY) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (worldContainer.ic.getIds()[3].getDurability() != null) {
                            pg2.drawString(worldContainer.ic.getIds()[3].getUiName() + " (" + (int) ((double) worldContainer.ic.getDurs()[3] / worldContainer.ic.getIds()[3].getDurability() * 100) + "%)", mousePos.getX(), mousePos.getY());
                        } else {
                            pg2.drawString(worldContainer.ic.getIds()[3].getUiName(), mousePos.getX(), mousePos.getY());
                        }
                    }
                }
            }
        }
        if (state == State.LOADING_GRAPHICS) {
            pg2.setFont(loadFont);
            pg2.setColor(Color.GREEN);
            pg2.drawString("Loading graphics... Please wait.", getWidth() / 2 - 200, getHeight() / 2 - 5);
        }
        if (state == State.TITLE_SCREEN) {
            pg2.drawImage(title_screen,
                    0, 0, getWidth(), getHeight(),
                    0, 0, getWidth(), getHeight(),
                    null);
        }
        if (state == State.SELECT_WORLD) {
            pg2.drawImage(select_world,
                    0, 0, getWidth(), getHeight(),
                    0, 0, getWidth(), getHeight(),
                    null);
            for (i = 0; i < filesInfo.size(); i++) {
                pg2.setFont(worldFont);
                pg2.setColor(Color.BLACK);
                pg2.drawString(filesInfo.get(i).getName(), 180, 140 + i * 35);
                pg2.fillRect(166, 150 + i * 35, 470, 3);
            }
        }
        if (state == State.NEW_WORLD) {
            pg2.drawImage(new_world,
                    0, 0, getWidth(), getHeight(),
                    0, 0, getWidth(), getHeight(),
                    null);
            pg2.drawImage(newWorldName.getImage(),
                    200, 240, 600, 270,
                    0, 0, 400, 30,
                    null);
        }
        if (state == State.GENERATING_WORLD) {
            pg2.setFont(loadFont);
            pg2.setColor(Color.GREEN);
            pg2.drawString("Generating new world... Please wait.", getWidth() / 2 - 200, getHeight() / 2 - 5);
        }
        ((Graphics2D) g).drawImage(screen,
                0, 0, getWidth(), getHeight(),
                0, 0, getWidth(), getHeight(),
                null);
    }

    public BufferedImage loadBlock(BlockNames type, Byte dir, Byte dirn, Byte tnum, String outlineName, int x, int y, int lyr) {
        int fx, fy;
        int dir_is = (int) dir;
        String dir_s = Directions.findByIndex(dir_is).getFileName();
        int dir_i = (int) dirn;
        BufferedImage outline = outlineImgs.get("outlines/" + outlineName + "/" + dir_s + (dir_i + 1) + ".png");
        String bName = type.getFileName();
        BufferedImage texture = blockImgs.get("blocks/" + bName + "/texture" + (tnum + 1) + ".png");
        BufferedImage image = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT);
        if (GRASSDIRT.get(type) != null) {
            BufferedImage dirtOriginal = blockImgs.get("blocks/" + BlockNames.findByIndex(GRASSDIRT.get(type)).getFileName() + "/texture" + (tnum + 1) + ".png");
            BufferedImage dirt = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT);
            for (dy = 0; dy < IMAGESIZE; dy++) {
                for (dx = 0; dx < IMAGESIZE; dx++) {
                    dirt.setRGB(dx, dy, dirtOriginal.getRGB(dx, dy));
                }
            }
            int dn = GRASSDIRT.get(type);
            boolean left = (worldContainer.blocks[lyr][y][x - 1] == BlockNames.AIR || !worldContainer.blocks[lyr][y][x - 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y+1][x] != dn) && (worldContainer.blocks[lyr][y-1][x-1] != dn && worldContainer.blocks[lyr][y+1][x-1] != dn);
            boolean right = (worldContainer.blocks[lyr][y][x + 1] == BlockNames.AIR || !worldContainer.blocks[lyr][y][x + 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y+1][x] != dn) && (worldContainer.blocks[lyr][y-1][x+1] != dn && worldContainer.blocks[lyr][y+1][x+1] != dn);
            boolean up = (worldContainer.blocks[lyr][y - 1][x] == BlockNames.AIR || !worldContainer.blocks[lyr][y - 1][x].isCds());// && (worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y][x+1] != dn) && (worldContainer.blocks[lyr][y-1][x-1] != dn && worldContainer.blocks[lyr][y-1][x+1] != dn);
            boolean down = (worldContainer.blocks[lyr][y + 1][x] == BlockNames.AIR || !worldContainer.blocks[lyr][y + 1][x].isCds());// && (worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y][x+1] != dn) && (worldContainer.blocks[lyr][y+1][x-1] != dn && worldContainer.blocks[lyr][y+1][x+1] != dn);
            boolean upleft = (worldContainer.blocks[lyr][y - 1][x - 1] == BlockNames.AIR || !worldContainer.blocks[lyr][y - 1][x - 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y-1][x-1] != dn && worldContainer.blocks[lyr][y-2][x] != dn && worldContainer.blocks[lyr][y][x-2] != dn);
            boolean upright = (worldContainer.blocks[lyr][y - 1][x + 1] == BlockNames.AIR || !worldContainer.blocks[lyr][y - 1][x + 1].isCds());// && (worldContainer.blocks[lyr][y-1][x] != dn && worldContainer.blocks[lyr][y][x+1] != dn && worldContainer.blocks[lyr][y-1][x+1] != dn && worldContainer.blocks[lyr][y-2][x] != dn && worldContainer.blocks[lyr][y][x+2] != dn);
            boolean downleft = (worldContainer.blocks[lyr][y + 1][x - 1] == BlockNames.AIR || !worldContainer.blocks[lyr][y + 1][x - 1].isCds());// && (worldContainer.blocks[lyr][y+1][x] != dn && worldContainer.blocks[lyr][y][x-1] != dn && worldContainer.blocks[lyr][y+1][x-1] != dn && worldContainer.blocks[lyr][y+2][x] != dn && worldContainer.blocks[lyr][y][x-2] != dn);
            boolean downright = (worldContainer.blocks[lyr][y + 1][x + 1] == BlockNames.AIR || !worldContainer.blocks[lyr][y + 1][x + 1].isCds());// && (worldContainer.blocks[lyr][y+1][x] != dn && worldContainer.blocks[lyr][y][x+1] != dn && worldContainer.blocks[lyr][y+1][x+1] != dn && worldContainer.blocks[lyr][y+2][x] != dn && worldContainer.blocks[lyr][y][x+2] != dn);
            int[][] pixm = new int[IMAGESIZE][IMAGESIZE];
            for (dy = 0; dy < 8; dy++) {
                for (dx = 0; dx < 8; dx++) {
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
                for (dy = 0; dy < 4; dy++) {
                    pixm[dy][0] = 255;
                }
                for (dx = 0; dx < 4; dx++) {
                    pixm[0][dx] = 255;
                }
            }
            if (up && right) {
                for (dx = 4; dx < 8; dx++) {
                    pixm[0][dx] = 255;
                }
                for (dy = 0; dy < 4; dy++) {
                    pixm[dy][7] = 255;
                }
            }
            if (right && down) {
                for (dy = 4; dy < 8; dy++) {
                    pixm[dy][7] = 255;
                }
                for (dx = 4; dx < 8; dx++) {
                    pixm[7][dx] = 255;
                }
            }
            if (down && left) {
                for (dx = 0; dx < 4; dx++) {
                    pixm[7][dx] = 255;
                }
                for (dy = 4; dy < 8; dy++) {
                    pixm[dy][0] = 255;
                }
            }
            for (dy = 0; dy < 8; dy++) {
                for (dx = 0; dx < 8; dx++) {
                    if (pixm[dy][dx] == 255) {
                        for (dy2 = 0; dy2 < 8; dy2++) {
                            for (dx2 = 0; dx2 < 8; dx2++) {
                                n = (int) (255 - 32 * Math.sqrt(Math.pow(dx - dx2, 2) + Math.pow(dy - dy2, 2)));
                                if (pixm[dy2][dx2] < n) {
                                    pixm[dy2][dx2] = n;
                                }
                            }
                        }
                    }
                }
            }
            for (dy = 0; dy < 8; dy++) {
                for (dx = 0; dx < 8; dx++) {
                    dirt.setRGB(dx, dy, new Color((int) (pixm[dy][dx] / 255.0 * new Color(texture.getRGB(dx, dy)).getRed() + (1 - pixm[dy][dx] / 255.0) * new Color(dirt.getRGB(dx, dy)).getRed()),
                            (int) (pixm[dy][dx] / 255.0 * new Color(texture.getRGB(dx, dy)).getGreen() + (1 - pixm[dy][dx] / 255.0) * new Color(dirt.getRGB(dx, dy)).getGreen()),
                            (int) (pixm[dy][dx] / 255.0 * new Color(texture.getRGB(dx, dy)).getBlue() + (1 - pixm[dy][dx] / 255.0) * new Color(dirt.getRGB(dx, dy)).getBlue())).getRGB());
                }
            }
            texture = dirt;
        }
        for (fy = 0; fy < IMAGESIZE; fy++) {
            for (fx = 0; fx < IMAGESIZE; fx++) {
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
                            for (i = 0; i < 9; i++) {
                                if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                    worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, 2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                }
                            }
                        }
                        if (worldContainer.player.imgState == ImageState.STILL_LEFT || worldContainer.player.imgState.isWalkLeft()) {
                            for (i = 0; i < 9; i++) {
                                if (worldContainer.ic.getIds()[i] != Items.EMPTY) {
                                    worldContainer.entities.add(new Entity(worldContainer.icx * BLOCKSIZE, worldContainer.icy * BLOCKSIZE, -2, -2, worldContainer.ic.getIds()[i], worldContainer.ic.getNums()[i], worldContainer.ic.getDurs()[i], 75));
                                }
                            }
                        }
                    }
                    if (worldContainer.ic.getType() == ItemType.FURNACE) {
                        worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setFUELP(worldContainer.ic.getFUELP());
                        worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setSMELTP(worldContainer.ic.getSMELTP());
                        worldContainer.icmatrix[iclayer][worldContainer.icy][worldContainer.icx].setF_ON(worldContainer.ic.isF_ON());
                    }
                    worldContainer.ic = null;
                } else {
                    if (worldContainer.showInv) {
                        for (i = 0; i < 4; i++) {
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
                if (key.getKeyCode() == key.VK_1) {
                    worldContainer.inventory.select(1);
                }
                if (key.getKeyCode() == key.VK_2) {
                    worldContainer.inventory.select(2);
                }
                if (key.getKeyCode() == key.VK_3) {
                    worldContainer.inventory.select(3);
                }
                if (key.getKeyCode() == key.VK_4) {
                    worldContainer.inventory.select(4);
                }
                if (key.getKeyCode() == key.VK_5) {
                    worldContainer.inventory.select(5);
                }
                if (key.getKeyCode() == key.VK_6) {
                    worldContainer.inventory.select(6);
                }
                if (key.getKeyCode() == key.VK_7) {
                    worldContainer.inventory.select(7);
                }
                if (key.getKeyCode() == key.VK_8) {
                    worldContainer.inventory.select(8);
                }
                if (key.getKeyCode() == key.VK_9) {
                    worldContainer.inventory.select(9);
                }
                if (key.getKeyCode() == key.VK_0) {
                    worldContainer.inventory.select(0);
                }
            }
        }
        char c = 0;
        if (key.getKeyCode() == key.VK_Q) {
            c = 'q';
        }
        if (key.getKeyCode() == key.VK_W) {
            c = 'w';
        }
        if (key.getKeyCode() == key.VK_E) {
            c = 'e';
        }
        if (key.getKeyCode() == key.VK_R) {
            c = 'r';
        }
        if (key.getKeyCode() == key.VK_T) {
            c = 't';
        }
        if (key.getKeyCode() == key.VK_Y) {
            c = 'y';
        }
        if (key.getKeyCode() == key.VK_U) {
            c = 'u';
        }
        if (key.getKeyCode() == key.VK_I) {
            c = 'i';
        }
        if (key.getKeyCode() == key.VK_O) {
            c = 'o';
        }
        if (key.getKeyCode() == key.VK_P) {
            c = 'p';
        }
        if (key.getKeyCode() == key.VK_A) {
            c = 'a';
        }
        if (key.getKeyCode() == key.VK_S) {
            c = 's';
        }
        if (key.getKeyCode() == key.VK_D) {
            c = 'd';
        }
        if (key.getKeyCode() == key.VK_F) {
            c = 'f';
        }
        if (key.getKeyCode() == key.VK_G) {
            c = 'g';
        }
        if (key.getKeyCode() == key.VK_H) {
            c = 'h';
        }
        if (key.getKeyCode() == key.VK_J) {
            c = 'j';
        }
        if (key.getKeyCode() == key.VK_K) {
            c = 'k';
        }
        if (key.getKeyCode() == key.VK_L) {
            c = 'l';
        }
        if (key.getKeyCode() == key.VK_Z) {
            c = 'z';
        }
        if (key.getKeyCode() == key.VK_X) {
            c = 'x';
        }
        if (key.getKeyCode() == key.VK_C) {
            c = 'c';
        }
        if (key.getKeyCode() == key.VK_V) {
            c = 'v';
        }
        if (key.getKeyCode() == key.VK_B) {
            c = 'b';
        }
        if (key.getKeyCode() == key.VK_N) {
            c = 'n';
        }
        if (key.getKeyCode() == key.VK_M) {
            c = 'm';
        }
        if (key.getKeyCode() == key.VK_1) {
            c = '1';
        }
        if (key.getKeyCode() == key.VK_2) {
            c = '2';
        }
        if (key.getKeyCode() == key.VK_3) {
            c = '3';
        }
        if (key.getKeyCode() == key.VK_4) {
            c = '4';
        }
        if (key.getKeyCode() == key.VK_5) {
            c = '5';
        }
        if (key.getKeyCode() == key.VK_6) {
            c = '6';
        }
        if (key.getKeyCode() == key.VK_7) {
            c = '7';
        }
        if (key.getKeyCode() == key.VK_8) {
            c = '8';
        }
        if (key.getKeyCode() == key.VK_9) {
            c = '9';
        }
        if (key.getKeyCode() == key.VK_0) {
            c = '0';
        }
        if (key.getKeyCode() == key.VK_SPACE) {
            c = ' ';
        }
        if (key.getKeyCode() == 192) {
            c = '`';
        }
        if (key.getKeyCode() == key.VK_MINUS) {
            c = '-';
        }
        if (key.getKeyCode() == key.VK_EQUALS) {
            c = '=';
        }
        if (key.getKeyCode() == key.VK_OPEN_BRACKET) {
            c = '[';
        }
        if (key.getKeyCode() == key.VK_CLOSE_BRACKET) {
            c = ']';
        }
        if (key.getKeyCode() == key.VK_BACK_SLASH) {
            c = '\\';
        }
        if (key.getKeyCode() == key.VK_COLON) {
            c = ':';
        }
        if (key.getKeyCode() == key.VK_QUOTE) {
            c = '\'';
        }
        if (key.getKeyCode() == key.VK_COMMA) {
            c = ',';
        }
        if (key.getKeyCode() == key.VK_PERIOD) {
            c = '.';
        }
        if (key.getKeyCode() == key.VK_SLASH) {
            c = '/';
        }

        if (keyPressed == KeyPressed.SHIFT) {
            if (c == 'q') {
                c = 'Q';
            }
            if (c == 'w') {
                c = 'W';
            }
            if (c == 'e') {
                c = 'E';
            }
            if (c == 'r') {
                c = 'R';
            }
            if (c == 't') {
                c = 'T';
            }
            if (c == 'y') {
                c = 'Y';
            }
            if (c == 'u') {
                c = 'U';
            }
            if (c == 'i') {
                c = 'I';
            }
            if (c == 'o') {
                c = 'O';
            }
            if (c == 'p') {
                c = 'P';
            }
            if (c == 'a') {
                c = 'A';
            }
            if (c == 's') {
                c = 'S';
            }
            if (c == 'd') {
                c = 'D';
            }
            if (c == 'f') {
                c = 'F';
            }
            if (c == 'g') {
                c = 'G';
            }
            if (c == 'h') {
                c = 'H';
            }
            if (c == 'j') {
                c = 'J';
            }
            if (c == 'k') {
                c = 'K';
            }
            if (c == 'l') {
                c = 'L';
            }
            if (c == 'z') {
                c = 'Z';
            }
            if (c == 'x') {
                c = 'X';
            }
            if (c == 'c') {
                c = 'C';
            }
            if (c == 'v') {
                c = 'V';
            }
            if (c == 'b') {
                c = 'B';
            }
            if (c == 'n') {
                c = 'N';
            }
            if (c == 'm') {
                c = 'M';
            }
            if (c == '1') {
                c = '!';
            }
            if (c == '2') {
                c = '@';
            }
            if (c == '3') {
                c = '#';
            }
            if (c == '4') {
                c = '$';
            }
            if (c == '5') {
                c = '%';
            }
            if (c == '6') {
                c = '^';
            }
            if (c == '7') {
                c = '&';
            }
            if (c == '8') {
                c = '*';
            }
            if (c == '9') {
                c = '(';
            }
            if (c == '0') {
                c = ')';
            }
            if (c == ' ') {
                c = ' ';
            }
            if (c == '`') {
                c = '~';
            }
            if (c == '-') {
                c = '_';
            }
            if (c == '=') {
                c = '+';
            }
            if (c == '[') {
                c = '{';
            }
            if (c == ']') {
                c = '}';
            }
            if (c == '\\') {
                c = '|';
            }
            if (c == ';') {
                c = ')';
            }
            if (c == '\'') {
                c = '"';
            }
            if (c == ',') {
                c = '<';
            }
            if (c == '.') {
                c = '>';
            }
            if (c == '/') {
                c = '?';
            }
        }

        if (state == State.NEW_WORLD && !newWorldFocus) {
            if (c != 0) {
                newWorldName.typeKey(c);
                repaint();
            }

            if (key.getKeyCode() == 8) {
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

    public static int getBLOCKSIZE() {
        return BLOCKSIZE;
    }

    public static int getIMAGESIZE() {
        return IMAGESIZE;
    }

    public static int getPLAYERSIZEX() {
        return PLAYERSIZEX;
    }

    public static int getPLAYERSIZEY() {
        return PLAYERSIZEY;
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

    public static void postError(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception in thread " + e.getClass().getName());
        if (e.getMessage() != null) {
            sb.append(": ");
            sb.append(e.getMessage());
        }
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append("\n        at " + ste.toString());
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("log.txt"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e2) {
            //
        } finally {
            log.error(sb.toString());
        }
    }
}
