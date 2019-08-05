package com.sergio.refacto; /**

TerrariaClone (working title) [Pre-alpha 1.3]

developed by Radon Rosborough

Project mission: To program a 2D sandbox game similar to, but with many more
                 features than, Terraria.

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sergio.refacto.dto.BlockNames;
import com.sergio.refacto.dto.Directions;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.init.ArmorInitializer;
import com.sergio.refacto.init.BackgroundImagesInitializer;
import com.sergio.refacto.init.BlockCDInitializer;
import com.sergio.refacto.init.BlockDropsInitializer;
import com.sergio.refacto.init.BlockImagesInitializer;
import com.sergio.refacto.init.BlockLightsInitializer;
import com.sergio.refacto.init.BlockToolsInitializer;
import com.sergio.refacto.init.DDelayInitializer;
import com.sergio.refacto.init.DurabilityInitializer;
import com.sergio.refacto.init.FSpeedInitializer;
import com.sergio.refacto.init.FuelsInitializer;
import com.sergio.refacto.init.GSupportInitializer;
import com.sergio.refacto.init.GrassDirtInitializer;
import com.sergio.refacto.init.ItemBlocksInitializer;
import com.sergio.refacto.init.ItemImagesInitializer;
import com.sergio.refacto.init.LightLevelsInitializer;
import com.sergio.refacto.init.MaxStacksInitializer;
import com.sergio.refacto.init.OutlineImagesInitializer;
import com.sergio.refacto.init.OutlinesInitializer;
import com.sergio.refacto.init.SkyColorsInitializer;
import com.sergio.refacto.init.SkyLightsInitializer;
import com.sergio.refacto.init.ToolDamageInitializer;
import com.sergio.refacto.init.ToolDursInitializer;
import com.sergio.refacto.init.ToolSpeedInitializer;
import com.sergio.refacto.init.TorchesBInitializer;
import com.sergio.refacto.init.TorchesLInitializer;
import com.sergio.refacto.init.TorchesRInitializer;
import com.sergio.refacto.init.UIBlocksInitializer;
import com.sergio.refacto.init.UIEntitiesInitializer;
import com.sergio.refacto.init.WirePInitializer;
import com.sergio.refacto.items.Chunk;
import com.sergio.refacto.tools.ResourcesLoader;

import static com.sergio.refacto.dto.Constants.*;

public class TerrariaClone extends JApplet implements ChangeListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    static GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    BufferedImage screen;
    Color bg;

    int size = CHUNKBLOCKS*2;

    int[][] cl = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    javax.swing.Timer timer, menuTimer, paintTimer;
    File folder;
    File[] files;
    ArrayList<String> worldFiles, worldNames;
    String currentWorld;
    TextField newWorldName;

    Integer[][][] blocks;
    Byte[][][] blockds;
    Byte[][] blockdns;
    Byte[][] blockbgs;
    Byte[][] blockts;
    Float[][] lights;
    Float[][][] power;
    Boolean[][] lsources;
    ArrayList<Integer> lqx, lqy, pqx, pqy, zqx, zqy, pzqx, pzqy;
    Boolean[][] lqd, zqd, pqd, pzqd;
    Byte[][] zqn;
    Byte[][][] pzqn;
    Boolean[][][] arbprd;
    ArrayList<Integer> updatex, updatey, updatet, updatel;
    Boolean[][] wcnct;
    Boolean[][] drawn, ldrawn, rdrawn;
    Player player;
    Inventory inventory;
    static ItemCollection cic, armor;
    ArrayList<Entity> entities;
    ArrayList<Double> cloudsx, cloudsy, cloudsv;
    ArrayList<Integer> cloudsn;
    ArrayList<Integer> machinesx, machinesy;

    Chunk[][] temporarySaveFile;
    Chunk[][] chunkMatrix;

    int rgnc1 = 0;
    int rgnc2 = 0;
    int layer = 1;
    int iclayer;
    int layerTemp;
    int blockTemp;
    BufferedImage layerImg;

    Entity entity;
    String state = "loading_graphics";
    String msg = "If you are reading this then\nplease report an error.";
    String mobSpawn;

    int u, v, ou, ov, uNew, vNew;
    int x, y, i, j, k, t, wx, wy, lx, ly, tx, ty, twx, twy, tlx, tly, ux, uy, ux2, uy2, uwx, uwy, uwx2, ulx, uly, ulx2, uly2, ucx, ucy, uclx, ucly, pwx, pwy, icx, icy, n, m, dx, dy, dx2, dy2, mx, my, lsx, lsy, lsn, ax, ay, axl, ayl, nl, vc, xpos, ypos, xpos2, ypos2, x2, y2, rnum, mining, immune, width, height, xmin, xmax, ymin, ymax, intpercent, ground;
    double p, q;
    short s, miningTool;

    short moveItem, moveNum, moveDur, moveItemTemp, moveNumTemp, moveDurTemp;
    int msi = 0;

    double top, bottom, percent;

    double toolAngle, toolSpeed;

    double timeOfDay = 0; // 28000 (before dusk), 32000 (after dusk)
    int currentSkyLight = 28800;
    int day = 0;

    int mobCount = 0;

    Point tp1, tp2, tp3, tp4, tp5;

    int[] mousePos, mousePos2;

    Font font = new Font("Chalkboard", Font.BOLD, 12);
    Font mobFont = new Font("Chalkboard", Font.BOLD, 16);
    Font loadFont = new Font("Courier", Font.PLAIN, 12);
    Font menuFont = new Font("Chalkboard", Font.PLAIN, 30);
    Font worldFont = new Font("Andale Mono", Font.BOLD, 16);
    Color CYANISH = new Color(75, 163, 243);
    int loadTextPos = 0;

    BufferedImage sun, moon, cloud, logo_white, logo_black, title_screen, select_world, new_world, save_exit;
    BufferedImage[] clouds = { ResourcesLoader.loadImage("environment/cloud1.png")};
    BufferedImage wcnct_px = ResourcesLoader.loadImage("misc/wcnct.png");

    Thread thread;
    javax.swing.Timer createWorldTimer;
    boolean[] queue;

    boolean done = false;
    boolean ready = true;
    boolean showTool = false;
    boolean showInv = false;
    boolean checkBlocks = true;
    boolean mouseClicked = true;
    boolean mouseClicked2 = true;
    boolean mouseNoLongerClicked = false;
    boolean mouseNoLongerClicked2 = false;
    boolean addSources = false;
    boolean removeSources = false;
    boolean beginLight = false;
    boolean doMobSpawn = false;
    boolean keepLeaf = false;
    boolean newWorldFocus = false;
    boolean menuPressed = false;
    boolean doGenerateWorld = true;
    boolean doGrassGrow = false;
    boolean reallyAddPower = false;
    boolean reallyRemovePower = false;

    static int WIDTH = 2400;
    static int HEIGHT = 2400;

    static int BLOCKSIZE = 16;
    static int IMAGESIZE = 8;
    static int CHUNKBLOCKS = 96;
    static int CHUNKSIZE = CHUNKBLOCKS*BLOCKSIZE;
    static int PLAYERSIZEX = 20;
    static int PLAYERSIZEY = 46;
    static int seed = new Random().nextInt();

    static Random random = new Random(seed); // SEED

    static int BRIGHTEST = 21;
    static int PMAX = 10;

    static boolean DEBUG_INSTAMINE = false;
    static double DEBUG_ACCEL = 1;
    static boolean DEBUG_NOCLIP = false;
    static boolean DEBUG_LIGHT = false;
    static boolean DEBUG_REACH = true;
    static boolean DEBUG_PEACEFUL = true;
    static int DEBUG_HOSTILE = 1;
    static boolean DEBUG_F1 = false;
    static boolean DEBUG_SPEED = true;
    static boolean DEBUG_FLIGHT = true;
    static boolean DEBUG_INVINCIBLE = true;
    static int DEBUG_HERBGROW = 1;
    static int DEBUG_GRASSGROW = 1;
    static String DEBUG_MOBTEST = null;
    static boolean DEBUG_STATS = true;
    static String DEBUG_ITEMS = "testing";
    static boolean DEBUG_GPLACE = true;

    static int WORLDWIDTH = WIDTH / CHUNKBLOCKS + 1;
    static int WORLDHEIGHT = HEIGHT / CHUNKBLOCKS + 1;

    static int SUNLIGHTSPEED = 14;

    int resunlight = WIDTH;
    int sunlightlevel = 19;

    ItemCollection ic;

    BufferedImage[][] worlds, fworlds;
    boolean[][] kworlds;

    BufferedImage world;

    ItemCollection[][][] icmatrix;

    BufferedImage image, tool, mobImage;

    static String version = "0.3_01";

    static Map<Byte,BufferedImage> backgroundImgs;
    static Map<Short,BufferedImage> itemImgs;
    static Map<Short,Map<Integer,Integer>> DURABILITY;
    static Map<Integer,Integer> dur;
    static Map<Integer,Short[]> BLOCKTOOLS;
    static Map<Short,Double> TOOLSPEED;
    static Map<Short,Integer> TOOLDAMAGE;
    static Map<Integer,Short> BLOCKDROPS;
    static Map<Short,Integer> ITEMBLOCKS;
    static Map<Integer,String> OUTLINES;
    static Map<String,String> UIBLOCKS;
    static Map<String,String> UIENTITIES;
    static Map<Integer,Boolean> BLOCKCD;
    static Map<Short,Short> MAXSTACKS;
    static Map<Integer,Integer> SKYLIGHTS;
    static Map<Integer,Color> SKYCOLORS;
    static Map<Integer,BufferedImage> LIGHTLEVELS;
    static Map<String,BufferedImage> blockImgs;
    static Map<String,BufferedImage> outlineImgs;
    static Map<Integer,Integer> BLOCKLIGHTS;
    static Map<Integer,Integer> GRASSDIRT;
    static Map<Short,Integer> ARMOR;
    static Map<Short,Short> TOOLDURS;
    static Map<Short,Double> FUELS;
    static Map<Integer,Integer> WIREP;
    static Map<Integer,Integer> TORCHESL;
    static Map<Integer,Integer> TORCHESR;
    static Map<Integer,Boolean> TORCHESB;
    static Map<Integer,Boolean> GSUPPORT;
    static Map<Short,Double> FSPEED;
    static Map<Integer,Integer> DDELAY;

    ArrayList<Short> FRI1, FRN1, FRI2, FRN2;

    Graphics2D g2, wg2, fwg2, ug2, pg2;

    ObjectOutputStream output, boutput;
    ObjectInputStream input;

    static BufferedWriter log;

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

            queue = new boolean[7]; // left[0] right[1] up[2] mouse[3] rightmouse[4] shift[5] down[6]

            mousePos = new int[2];
            mousePos2 = new int[2];

            logo_white = ResourcesLoader.loadImage("interface/logo_white.png");
            logo_black = ResourcesLoader.loadImage("interface/logo_black.png");
            title_screen = ResourcesLoader.loadImage("interface/title_screen.png");
            select_world = ResourcesLoader.loadImage("interface/select_world.png");
            new_world = ResourcesLoader.loadImage("interface/new_world.png");
            save_exit = ResourcesLoader.loadImage("interface/save_exit.png");

            state = "loading_graphics";

            repaint();

            backgroundImgs = BackgroundImagesInitializer.init();

            itemImgs = ItemImagesInitializer.init();

            blockImgs = BlockImagesInitializer.init();

            outlineImgs = OutlineImagesInitializer.init();

            DURABILITY = DurabilityInitializer.init();

            BLOCKTOOLS = BlockToolsInitializer.init();

            TOOLSPEED = ToolSpeedInitializer.init();

            TOOLDAMAGE = ToolDamageInitializer.init();

            BLOCKDROPS = BlockDropsInitializer.init();

            ITEMBLOCKS = ItemBlocksInitializer.init();

            OUTLINES = OutlinesInitializer.init();

            UIBLOCKS = UIBlocksInitializer.init();

            UIENTITIES = UIEntitiesInitializer.init();

            BLOCKCD = BlockCDInitializer.init();

            MAXSTACKS = MaxStacksInitializer.init();

            SKYCOLORS = SkyColorsInitializer.init();

            SKYLIGHTS = SkyLightsInitializer.init();

            LIGHTLEVELS = LightLevelsInitializer.init();

            BLOCKLIGHTS = BlockLightsInitializer.init();

            GRASSDIRT = GrassDirtInitializer.init();

            ARMOR = ArmorInitializer.init();

            TOOLDURS = ToolDursInitializer.init();

            FUELS = FuelsInitializer.init();

            WIREP = WirePInitializer.init();

            TORCHESL = TorchesLInitializer.init();

            TORCHESR = TorchesRInitializer.init();

            TORCHESB = TorchesBInitializer.init();

            GSUPPORT = GSupportInitializer.init();

            FSPEED = FSpeedInitializer.init();

            DDELAY = DDelayInitializer.init();

            sun = ResourcesLoader.loadImage("environment/sun.png");
            moon = ResourcesLoader.loadImage("environment/moon.png");
            FRI1 = new ArrayList<>(0);
            FRN1 = new ArrayList<>(0);
            FRI2 = new ArrayList<>(0);
            FRN2 = new ArrayList<>(0);

            FRI1.add((short) 3); FRN1.add((short) 4); FRI2.add((short) 29); FRN2.add((short) 1);
            FRI1.add((short) 4); FRN1.add((short) 4); FRI2.add((short) 30); FRN2.add((short) 1);
            FRI1.add((short) 5); FRN1.add((short) 4); FRI2.add((short) 31); FRN2.add((short) 1);
            FRI1.add((short) 6); FRN1.add((short) 4); FRI2.add((short) 32); FRN2.add((short) 1);
            FRI1.add((short) 38); FRN1.add((short) 4); FRI2.add((short) 60); FRN2.add((short) 1);
            FRI1.add((short) 39); FRN1.add((short) 4); FRI2.add((short) 61); FRN2.add((short) 1);
            FRI1.add((short) 40); FRN1.add((short) 4); FRI2.add((short) 62); FRN2.add((short) 1);
            FRI1.add((short) 41); FRN1.add((short) 4); FRI2.add((short) 63); FRN2.add((short) 1);
            FRI1.add((short) 42); FRN1.add((short) 4); FRI2.add((short) 64); FRN2.add((short) 1);
            FRI1.add((short) 43); FRN1.add((short) 4); FRI2.add((short) 65); FRN2.add((short) 1);
            FRI1.add((short) 44); FRN1.add((short) 4); FRI2.add((short) 67); FRN2.add((short) 1);
            FRI1.add((short) 45); FRN1.add((short) 4); FRI2.add((short) 68); FRN2.add((short) 1);
            FRI1.add((short) 46); FRN1.add((short) 4); FRI2.add((short) 69); FRN2.add((short) 1);
            FRI1.add((short) 47); FRN1.add((short) 4); FRI2.add((short) 70); FRN2.add((short) 1);
            FRI1.add((short) 48); FRN1.add((short) 4); FRI2.add((short) 71); FRN2.add((short) 1);
            FRI1.add((short) 49); FRN1.add((short) 4); FRI2.add((short) 72); FRN2.add((short) 1);
            FRI1.add((short) 50); FRN1.add((short) 4); FRI2.add((short) 73); FRN2.add((short) 1);
            for (i=8; i>2; i--) {
                FRI1.add((short) 74); FRN1.add((short) i); FRI2.add((short) 76); FRN2.add((short) i);
                FRI1.add((short) 2); FRN1.add((short) i); FRI2.add((short) 162); FRN2.add((short) i);
                FRI1.add((short) 161); FRN1.add((short) i); FRI2.add((short) 163); FRN2.add((short) i);
                FRI1.add((short) 165); FRN1.add((short) i); FRI2.add((short) 166); FRN2.add((short) i);
                FRI1.add((short) 15); FRN1.add((short) i); FRI2.add((short) 179); FRN2.add((short) i);
            }
            for (j=97; j<103; j++) {
                FRI1.add(new Short((short)j)); FRN1.add((short) 1); FRI2.add((short) 167); FRN2.add((short) 8);
            }

            bg = CYANISH;
            state = "title_screen";

            repaint();

            menuTimer = new javax.swing.Timer(20, null);

            menuTimer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        if (queue[3]) {
                            Action mainThread = new AbstractAction() {
                                public void actionPerformed(ActionEvent ae) {
                                    try {
                                        if (ready) {
                                            ready = false;
                                            uNew = (int)((player.x - getWidth()/2 + player.width) / (double)CHUNKSIZE);
                                            vNew = (int)((player.y - getHeight()/2 + player.height) / (double)CHUNKSIZE);
                                            if (ou != uNew || ov != vNew) {
                                                ou = uNew;
                                                ov = vNew;
                                                ArrayList<Chunk> chunkTemp = new ArrayList<Chunk>(0);
                                                for (twy=0; twy<2; twy++) {
                                                    for (twx=0; twx<2; twx++) {
                                                        if (chunkMatrix[twy][twx] != null) {
                                                            chunkTemp.add(chunkMatrix[twy][twx]);
                                                            chunkMatrix[twy][twx] = null;
                                                        }
                                                    }
                                                }
                                                for (twy=0; twy<2; twy++) {
                                                    for (twx=0; twx<2; twx++) {
                                                        for (i=chunkTemp.size()-1; i>-1; i--) {
                                                            if (chunkTemp.get(i).getCx() == twx && chunkTemp.get(i).getCy() == twy) {
                                                                chunkMatrix[twy][twx] = chunkTemp.get(i);
                                                                chunkTemp.remove(i);
                                                                break;
                                                            }
                                                        }
                                                        if (chunkMatrix[twy][twx] == null) {
                                                            if (temporarySaveFile[twy][twx] != null) {
                                                                chunkMatrix[twy][twx] = temporarySaveFile[twy][twx];
                                                            }
                                                            else {
                                                                chunkMatrix[twy][twx] = new Chunk(twx+ou, twy+ov);
                                                            }
                                                        }
                                                    }
                                                }
                                                for (i=0; i<chunkTemp.size(); i++) {
                                                    temporarySaveFile[twy][twx] = chunkTemp.get(i);
                                                }
                                                chunkTemp = null;
                                                for (twy=0; twy<2; twy++) {
                                                    for (twx=0; twx<2; twx++) {
                                                        for (y=0; y<CHUNKBLOCKS; y++) {
                                                            for (x=0; x<CHUNKBLOCKS; x++) {
                                                                for (int l=0; l<3; l++) {
                                                                    blocks[l][twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getBlocks()[l][y][x];
                                                                    power[l][twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getPower()[l][y][x];
                                                                    pzqn[l][twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getPzqn()[l][y][x];
                                                                    arbprd[l][twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getArbprd()[l][y][x];
                                                                    blockds[l][twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getBlockds()[l][y][x];
                                                                }
                                                                blockdns[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getBlockdns()[y][x];
                                                                blockbgs[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getBlockbgs()[y][x];
                                                                blockts[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getBlockts()[y][x];
                                                                lights[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getLights()[y][x];
                                                                lsources[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getLsources()[y][x];
                                                                zqn[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getZqn()[y][x];
                                                                wcnct[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getWcnct()[y][x];
                                                                drawn[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getDrawn()[y][x];
                                                                rdrawn[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getRdrawn()[y][x];
                                                                ldrawn[twy*CHUNKBLOCKS+y][twx*CHUNKBLOCKS+x] = chunkMatrix[twy][twx].getLdrawn()[y][x];
                                                            }
                                                        }
                                                        worlds[twy][twx] = null;
                                                    }
                                                }
                                            }
                                            u = -ou*CHUNKBLOCKS;
                                            v = -ov*CHUNKBLOCKS;
                                            for (twy=0; twy<2; twy++) {
                                                for (twx=0; twx<2; twx++) {
                                                    kworlds[twy][twx] = false;
                                                }
                                            }
                                            boolean somevar = false;
                                            for (twy=0; twy<2; twy++) {
                                                for (twx=0; twx<2; twx++) {
                                                    int twxc = twx + ou;
                                                    int twyc = twy + ov;
                                                    if (((player.ix + getWidth()/2 + player.width >= twxc*CHUNKSIZE &&
                                                          player.ix + getWidth()/2 + player.width <= twxc*CHUNKSIZE+CHUNKSIZE) ||
                                                         (player.ix - getWidth()/2 + player.width + BLOCKSIZE >= twxc*CHUNKSIZE &&
                                                          player.ix - getWidth()/2 + player.width - BLOCKSIZE <= twxc*CHUNKSIZE+CHUNKSIZE)) &&
                                                        ((player.iy + getHeight()/2 + player.height >= twyc*CHUNKSIZE &&
                                                          player.iy + getHeight()/2 + player.height <= twyc*CHUNKSIZE+CHUNKSIZE) ||
                                                         (player.iy - getHeight()/2 + player.height >= twyc*CHUNKSIZE &&
                                                          player.iy - getHeight()/2 + player.height <= twyc*CHUNKSIZE+CHUNKSIZE))) {
                                                        kworlds[twy][twx] = true;
                                                        if (worlds[twy][twx] == null) {
                                                            worlds[twy][twx] = config.createCompatibleImage(CHUNKSIZE, CHUNKSIZE, Transparency.TRANSLUCENT);
                                                            fworlds[twy][twx] = config.createCompatibleImage(CHUNKSIZE, CHUNKSIZE, Transparency.TRANSLUCENT);
                                                            print("Created image at " + twx + " " + twy);
                                                        }
                                                        if (worlds[twy][twx] != null) {
                                                            wg2 = worlds[twy][twx].createGraphics();
                                                            fwg2 = fworlds[twy][twx].createGraphics();
                                                            for (tly=Math.max(twy*CHUNKSIZE, (int)(player.iy-getHeight()/2+player.height/2+v*BLOCKSIZE)-64); tly<Math.min(twy*CHUNKSIZE+CHUNKSIZE, (int)(player.iy+getHeight()/2-player.height/2+v*BLOCKSIZE)+128); tly+=BLOCKSIZE) {
                                                                for (tlx=Math.max(twx*CHUNKSIZE, (int)(player.ix-getWidth()/2+player.width/2+u*BLOCKSIZE)-64); tlx<Math.min(twx*CHUNKSIZE+CHUNKSIZE, (int)(player.ix+getWidth()/2-player.width/2+u*BLOCKSIZE)+112); tlx+=BLOCKSIZE) {
                                                                    tx = (int)(tlx/BLOCKSIZE);
                                                                    ty = (int)(tly/BLOCKSIZE);
                                                                    if (tx >= 0 && tx < size && ty >= 0 && ty < size) {
                                                                        if (!drawn[ty][tx]) {
                                                                            somevar = true;
                                                                            blockts[ty][tx] = (byte)random.nextInt(8);
                                                                            for (y=0; y<BLOCKSIZE; y++) {
                                                                                for (x=0; x<BLOCKSIZE; x++) {
                                                                                    try {
                                                                                        worlds[twy][twx].setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985);
                                                                                        fworlds[twy][twx].setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985);
                                                                                    }
                                                                                    catch (ArrayIndexOutOfBoundsException e) {
                                                                                        //
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (blockbgs[ty][tx] != 0) {
                                                                                wg2.drawImage(backgroundImgs.get(blockbgs[ty][tx]),
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null);
                                                                            }
                                                                            for (int l=0; l<3; l++) {
                                                                                if (blocks[l][ty][tx] != 0) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(loadBlock(blocks[l][ty][tx], blockds[l][ty][tx], blockdns[ty][tx], blockts[ty][tx], OUTLINES.get(blocks[l][ty][tx]), tx, ty, l),
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(loadBlock(blocks[l][ty][tx], blockds[l][ty][tx], blockdns[ty][tx], blockts[ty][tx], OUTLINES.get(blocks[l][ty][tx]), tx, ty, l),
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                }
                                                                                if (wcnct[ty][tx] && blocks[l][ty][tx] >= 94 && blocks[l][ty][tx] <= 99) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DEBUG_LIGHT) {
                                                                                fwg2.drawImage(LIGHTLEVELS.get((int)(float)lights[ty][tx]),
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null);
                                                                            }
                                                                            drawn[ty][tx] = true;
                                                                            rdrawn[ty][tx] = true;
                                                                            ldrawn[ty][tx] = true;
                                                                        }
                                                                        if (!rdrawn[ty][tx]) {
                                                                            somevar = true;
                                                                            for (y=0; y<BLOCKSIZE; y++) {
                                                                                for (x=0; x<BLOCKSIZE; x++) {
                                                                                    try {
                                                                                        worlds[twy][twx].setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985);
                                                                                        fworlds[twy][twx].setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985);
                                                                                    }
                                                                                    catch (ArrayIndexOutOfBoundsException e) {
                                                                                        //
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (blockbgs[ty][tx] != 0) {
                                                                                wg2.drawImage(backgroundImgs.get(blockbgs[ty][tx]),
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null);
                                                                            }
                                                                            for (int l=0; l<3; l++) {
                                                                                if (blocks[l][ty][tx] != 0) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(loadBlock(blocks[l][ty][tx], blockds[l][ty][tx], blockdns[ty][tx], blockts[ty][tx], OUTLINES.get(blocks[l][ty][tx]), tx, ty, l),
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(loadBlock(blocks[l][ty][tx], blockds[l][ty][tx], blockdns[ty][tx], blockts[ty][tx], OUTLINES.get(blocks[l][ty][tx]), tx, ty, l),
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                }
                                                                                if (wcnct[ty][tx] && blocks[l][ty][tx] >= 94 && blocks[l][ty][tx] <= 99) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DEBUG_LIGHT) {
                                                                                fwg2.drawImage(LIGHTLEVELS.get((int)(float)lights[ty][tx]),
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null);
                                                                            }
                                                                            drawn[ty][tx] = true;
                                                                            rdrawn[ty][tx] = true;
                                                                            ldrawn[ty][tx] = true;
                                                                        }
                                                                        if (!ldrawn[ty][tx] && random.nextInt(10) == 0) {
                                                                            somevar = true;
                                                                            for (y=0; y<BLOCKSIZE; y++) {
                                                                                for (x=0; x<BLOCKSIZE; x++) {
                                                                                    try {
                                                                                        worlds[twy][twx].setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985);
                                                                                        fworlds[twy][twx].setRGB(tx*BLOCKSIZE-twxc*CHUNKSIZE+x, ty*BLOCKSIZE-twyc*CHUNKSIZE+y, 9539985);
                                                                                    }
                                                                                    catch (ArrayIndexOutOfBoundsException e) {
                                                                                        //
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (blockbgs[ty][tx] != 0) {
                                                                                wg2.drawImage(backgroundImgs.get(blockbgs[ty][tx]),
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null);
                                                                            }
                                                                            for (int l=0; l<3; l++) {
                                                                                if (blocks[l][ty][tx] != 0) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(loadBlock(blocks[l][ty][tx], blockds[l][ty][tx], blockdns[ty][tx], blockts[ty][tx], OUTLINES.get(blocks[l][ty][tx]), tx, ty, l),
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(loadBlock(blocks[l][ty][tx], blockds[l][ty][tx], blockdns[ty][tx], blockts[ty][tx], OUTLINES.get(blocks[l][ty][tx]), tx, ty, l),
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                }
                                                                                if (wcnct[ty][tx] && blocks[l][ty][tx] >= 94 && blocks[l][ty][tx] <= 99) {
                                                                                    if (l == 2) {
                                                                                        fwg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                    else {
                                                                                        wg2.drawImage(wcnct_px,
                                                                                            tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                            0, 0, IMAGESIZE, IMAGESIZE,
                                                                                            null);
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (!DEBUG_LIGHT) {
                                                                                fwg2.drawImage(LIGHTLEVELS.get((int)(float)lights[ty][tx]),
                                                                                    tx*BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE - twy*CHUNKSIZE, tx*BLOCKSIZE + BLOCKSIZE - twx*CHUNKSIZE, ty*BLOCKSIZE + BLOCKSIZE - twy*CHUNKSIZE,
                                                                                    0, 0, IMAGESIZE, IMAGESIZE,
                                                                                    null);
                                                                            }
                                                                            drawn[ty][tx] = true;
                                                                            rdrawn[ty][tx] = true;
                                                                            ldrawn[ty][tx] = true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (somevar) {
                                                print("Drew at least one block.");
                                            }
                                            for (twy=0; twy<2; twy++) {
                                                for (twx=0; twx<2; twx++) {
                                                    if (!kworlds[twy][twx] && worlds[twy][twx] != null) {
                                                        worlds[twy][twx] = null;
                                                        fworlds[twy][twx] = null;
                                                        for (ty=twy*CHUNKBLOCKS; ty<twy*CHUNKBLOCKS+CHUNKBLOCKS; ty++) {
                                                            for (tx=twx*CHUNKBLOCKS; tx<twx*CHUNKBLOCKS+CHUNKBLOCKS; tx++) {
                                                                if (tx >= 0 && tx < size && ty >= 0 && ty < size) {
                                                                    drawn[ty][tx] = false;
                                                                }
                                                            }
                                                        }
                                                        print("Destroyed image at " + twx + " " + twy);
                                                    }
                                                }
                                            }
                                            updateApp();
                                            updateEnvironment();
                                            player.update(blocks[1], queue, u, v);
                                            if (timeOfDay >= 86400) {
                                                timeOfDay = 0;
                                                day += 1;
                                            }
                                            repaint();
                                            ready = true;
                                        }
                                    }
                                    catch (Exception e) {
                                        postError(e);
                                    }
                                }
                            };
                            timer = new javax.swing.Timer(20, mainThread);

                            if (state.equals("title_screen") && !menuPressed) {
                                if (mousePos[0] >= 239 && mousePos[0] <= 557) {
                                    if (mousePos[1] >= 213 && mousePos[1] <= 249) { // singleplayer
                                        findWorlds();
                                        state = "select_world";
                                        repaint();
                                        menuPressed = true;
                                    }
                                }
                            }
                            if (state.equals("select_world") && !menuPressed) {
                                if (mousePos[0] >= 186 && mousePos[0] <= 615 &&
                                    mousePos[1] >= 458 && mousePos[1] <= 484) { // create new world
                                    state = "new_world";
                                    newWorldName = new TextField(400, "New World");
                                    repaint();
                                    menuPressed = true;
                                }
                                if (mousePos[0] >= 334 && mousePos[0] <= 457 &&
                                    mousePos[1] >= 504 && mousePos[1] <= 530) { // back
                                    state = "title_screen";
                                    repaint();
                                    menuPressed = true;
                                }
                                for (i=0; i<worldFiles.size(); i++) {
                                    if (mousePos[0] >= 166 && mousePos[0] <= 470 &&
                                        mousePos[1] >= 117+i*35 && mousePos[1] <= 152+i*35) { // load world
                                        currentWorld = worldNames.get(i);
                                        state = "loading_world";
                                        bg = Color.BLACK;
                                        if (loadWorld(worldFiles.get(i))) {
                                            menuTimer.stop();
                                            bg = CYANISH;
                                            state = "ingame";
                                            ready = true;
                                            timer.start();
                                            break;
                                        }
                                    }
                                }
                            }
                            if (state.equals("new_world") && !menuPressed) {
                                if (mousePos[0] >= 186 && mousePos[0] <= 615 &&
                                    mousePos[1] >= 458 && mousePos[1] <= 484) { // create new world
                                    if (!newWorldName.getText().equals("")) {
                                        findWorlds();
                                        doGenerateWorld = true;
                                        for (i=0; i<worldNames.size(); i++) {
                                            if (newWorldName.getText().equals(worldNames.get(i))) {
                                                doGenerateWorld = false;
                                            }
                                        }
                                        if (doGenerateWorld) {
                                            menuTimer.stop();
                                            bg = Color.BLACK;
                                            state = "generating_world";
                                            currentWorld = newWorldName.getText();
                                            repaint();
                                            Action createWorldThread = new AbstractAction() {
                                                public void actionPerformed(ActionEvent ae) {
                                                    try {
                                                        createNewWorld();
                                                        bg = CYANISH;
                                                        state = "ingame";
                                                        ready = true;
                                                        timer.start();
                                                        createWorldTimer.stop();
                                                    }
                                                    catch (Exception e) {
                                                        postError(e);
                                                    }
                                                }
                                            };
                                            createWorldTimer = new javax.swing.Timer(1, createWorldThread);
                                            createWorldTimer.start();
                                        }
                                    }
                                }
                                if (mousePos[0] >= 334 && mousePos[0] <= 457 &&
                                    mousePos[1] >= 504 && mousePos[1] <= 530) { // back
                                    state = "select_world";
                                    repaint();
                                    menuPressed = true;
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        postError(e);
                    }
                }
            });

            menuTimer.start();
        }

        catch (Exception e) {
            postError(e);
        }
    }

    public void findWorlds() {
        folder = new File("worlds");
        folder.mkdir();
        files = folder.listFiles();
        worldFiles = new ArrayList<String>();
        worldNames = new ArrayList<String>();
        for (i=0; i<files.length; i++) {
            if (files[i].isFile() && files[i].getName().endsWith(".dat")) {
                worldFiles.add(files[i].getName());
                worldNames.add(files[i].getName().substring(0, files[i].getName().length()-4));
            }
        }
    }

    public void createNewWorld() {
        temporarySaveFile = new Chunk[WORLDHEIGHT][WORLDWIDTH];
        chunkMatrix = new Chunk[2][2];

        blocks = new Integer[3][size][size];
        blockds = new Byte[3][size][size];
        blockdns = new Byte[size][size];
        blockbgs = new Byte[size][size];
        blockts = new Byte[size][size];
        lights = new Float[size][size];
        power = new Float[3][size][size];
        lsources = new Boolean[size][size];
        zqn = new Byte[size][size];
        pzqn = new Byte[3][size][size];
        arbprd = new Boolean[3][size][size];
        wcnct = new Boolean[size][size];
        drawn = new Boolean[size][size];
        rdrawn = new Boolean[size][size];
        ldrawn = new Boolean[size][size];

        player = new Player(WIDTH*0.5*BLOCKSIZE, 45);

        inventory = new Inventory();

        if (DEBUG_ITEMS != null) {
            if (DEBUG_ITEMS.equals("normal")) {
                inventory.addItem((short) 172, (short) 1);
                inventory.addItem((short) 173, (short) 1);
                inventory.addItem((short) 174, (short) 1);
                inventory.addItem((short) 164, (short) 100);
                inventory.addItem((short) 35, (short) 100);
                inventory.addItem((short) 36, (short) 100);
                inventory.addItem((short) 37, (short) 100);
                inventory.addItem((short) 20, (short) 5);
                inventory.addItem((short) 27, (short) 5);
                inventory.addItem((short) 33, (short) 1);
                inventory.addItem((short) 28, (short) 100);
                inventory.addItem((short) 50, (short) 100);
                inventory.addItem((short) 1, (short) 100);
                inventory.addItem((short) 2, (short) 100);
                inventory.addItem((short) 15, (short) 100);
            }
            if (DEBUG_ITEMS.equals("tools")) {
                inventory.addItem((short) 154, (short) 1);
                inventory.addItem((short) 155, (short) 1);
                inventory.addItem((short) 156, (short) 1);
                inventory.addItem((short) 157, (short) 1);
                inventory.addItem((short) 158, (short) 1);
                inventory.addItem((short) 159, (short) 1);
                inventory.addItem((short) 7, (short) 1);
                inventory.addItem((short) 11, (short) 1);
                inventory.addItem((short) 12, (short) 1);
                inventory.addItem((short) 8, (short) 1);
                inventory.addItem((short) 13, (short) 1);
                inventory.addItem((short) 14, (short) 1);
                inventory.addItem((short) 9, (short) 1);
                inventory.addItem((short) 16, (short) 1);
                inventory.addItem((short) 17, (short) 1);
                inventory.addItem((short) 10, (short) 1);
                inventory.addItem((short) 18, (short) 1);
                inventory.addItem((short) 33, (short) 1);
                inventory.addItem((short) 51, (short) 1);
                inventory.addItem((short) 52, (short) 1);
                inventory.addItem((short) 53, (short) 1);
                inventory.addItem((short) 54, (short) 1);
                inventory.addItem((short) 55, (short) 1);
                inventory.addItem((short) 56, (short) 1);
                inventory.addItem((short) 57, (short) 1);
                inventory.addItem((short) 58, (short) 1);
                inventory.addItem((short) 59, (short) 1);
                inventory.addItem((short) 145, (short) 1);
                inventory.addItem((short) 146, (short) 1);
                inventory.addItem((short) 147, (short) 1);
                inventory.addItem((short) 148, (short) 1);
                inventory.addItem((short) 149, (short) 1);
                inventory.addItem((short) 150, (short) 1);
                inventory.addItem((short) 169, (short) 1);
                inventory.addItem((short) 170, (short) 1);
                inventory.addItem((short) 171, (short) 1);
                inventory.addItem((short) 172, (short) 1);
                inventory.addItem((short) 173, (short) 1);
                inventory.addItem((short) 174, (short) 1);

                inventory.addItem((short) 19, (short) 1);
            }
            if (DEBUG_ITEMS.equals("testing")) {
                inventory.addItem((short) 172, (short) 1);
                inventory.addItem((short) 173, (short) 1);
                inventory.addItem((short) 175, (short) 100);
                inventory.addItem((short) 15, (short) 100);
                inventory.addItem((short) 35, (short) 100);
                inventory.addItem((short) 36, (short) 100);
                inventory.addItem((short) 37, (short) 100);
                inventory.addItem((short) 176, (short) 100);
                inventory.addItem((short) 177, (short) 100);
                inventory.addItem((short) 178, (short) 100);
                inventory.addItem((short) 27, (short) 100);
                inventory.addItem((short) 33, (short) 1);
                inventory.addItem((short) 86, (short) 100);
                inventory.addItem((short) 49, (short) 100);
                inventory.addItem((short) 180, (short) 100);
                inventory.addItem((short) 181, (short) 100);
                inventory.addItem((short) 182, (short) 100);
                inventory.addItem((short) 183, (short) 100);
                inventory.addItem((short) 184, (short) 100);
                inventory.addItem((short) 185, (short) 100);
                inventory.addItem((short) 186, (short) 100);
                inventory.addItem((short) 187, (short) 100);
                inventory.addItem((short) 188, (short) 100);
                inventory.addItem((short) 189, (short) 100);
                inventory.addItem((short) 190, (short) 1);
            }
        }

        short[] tlist1 = {0, 0, 0, 0, 0};
        short[] tlist2 = {0, 0, 0, 0, 0};
        short[] tlist3 = {0, 0, 0, 0, 0};
        cic = new ItemCollection("cic", tlist1, tlist2, tlist3);
        inventory.renderCollection(cic);

        short[] tlist4 = {0, 0, 0, 0};
        short[] tlist5 = {0, 0, 0, 0};
        short[] tlist6 = {0, 0, 0, 0};
        armor = new ItemCollection("armor", tlist4, tlist5, tlist6);
        inventory.renderCollection(armor);

        toolAngle = 4.7;
        mining = 0;
        miningTool = 0;
        mx = 0; my = 0;
        moveItem = 0; moveNum = 0; moveDur = 0;

        entities = new ArrayList<Entity>(0);

        cloudsx = new ArrayList<Double>(0);
        cloudsy = new ArrayList<Double>(0);
        cloudsv = new ArrayList<Double>(0);
        cloudsn = new ArrayList<Integer>(0);

        machinesx = new ArrayList<Integer>(0);
        machinesy = new ArrayList<Integer>(0);

        icmatrix = new ItemCollection[3][HEIGHT][WIDTH];

        worlds = new BufferedImage[2][2];
        fworlds = new BufferedImage[2][2];
        kworlds = new boolean[2][2];

        pqx = new ArrayList<Integer>();
        pqy = new ArrayList<Integer>();

        pmsg("-> Adding light sources...");

        lqx = new ArrayList<Integer>();
        lqy = new ArrayList<Integer>();
        zqx = new ArrayList<Integer>();
        zqy = new ArrayList<Integer>();
        pqx = new ArrayList<Integer>();
        pqy = new ArrayList<Integer>();
        pzqx = new ArrayList<Integer>();
        pzqy = new ArrayList<Integer>();
        updatex = new ArrayList<Integer>();
        updatey = new ArrayList<Integer>();
        updatet = new ArrayList<Integer>();
        updatel = new ArrayList<Integer>();

        pmsg("-> Calculating light...");

        resolvePowerMatrix();
        resolveLightMatrix();

        pmsg("Finished generation.");
    }

    public void updateApp() {
        mousePos2[0] = mousePos[0] + player.ix - getWidth()/2 + player.width/2;
        mousePos2[1] = mousePos[1] + player.iy - getHeight()/2 + player.height/2;

        currentSkyLight = SKY_COLORS[0];
        for (i=0; i< SKY_COLORS.length; i++) {
            if (timeOfDay >= SKY_COLORS[i]) {
                currentSkyLight = SKY_COLORS[i];
            }
        }
        if (player.y/16 > HEIGHT * 0.55) {
            bg = Color.BLACK;
        }
        else {
            bg = SKYCOLORS.get(currentSkyLight);
        }

        if (rgnc1 == 0) {
            if (rgnc2 == 0) {
                if (player.hp < player.thp) {
                    player.hp += 1;
                    rgnc2 = 125;
                }
            }
            else {
                rgnc2 -= 1;
            }
        }
        else {
            rgnc1 -= 1;
        }

        for (j=0; j<machinesx.size(); j++) {
            x = machinesx.get(j); y = machinesy.get(j);
            for (int l=0; l<3; l++) {
                if (icmatrix[l][y][x] != null && icmatrix[l][y][x].type.equals("furnace")) {
                    if (icmatrix[l][y][x].F_ON) {
                        if (icmatrix[l][y][x].ids[1] == 0) {
                            if (FUELS.get(icmatrix[l][y][x].ids[2]) != null) {
                                inventory.addLocationIC(icmatrix[l][y][x], 1, icmatrix[l][y][x].ids[2], (short)1);
                                inventory.removeLocationIC(icmatrix[l][y][x], 2, (short)1);
                                icmatrix[l][y][x].FUELP = 1;
                            }
                            else {
                                icmatrix[l][y][x].F_ON = false;
                                removeBlockLighting(x, y);
                                blocks[l][y][x] = 17;
                                rdrawn[y][x] = false;
                            }
                        }
                        if (FUELS.get(icmatrix[l][y][x].ids[1]) != null) {
                            icmatrix[l][y][x].FUELP -= FUELS.get(icmatrix[l][y][x].ids[1]);
                            if (icmatrix[l][y][x].FUELP < 0) {
                                icmatrix[l][y][x].FUELP = 0;
                                inventory.removeLocationIC(icmatrix[l][y][x], 1, icmatrix[l][y][x].nums[1]);
                            }
                            for (i=0; i<FRI1.size(); i++) {
                                if (icmatrix[l][y][x].ids[0] == FRI1.get(i) && icmatrix[l][y][x].nums[0] >= FRN1.get(i)) {
                                    icmatrix[l][y][x].SMELTP += FSPEED.get(icmatrix[l][y][x].ids[1]);
                                    if (icmatrix[l][y][x].SMELTP > 1) {
                                        icmatrix[l][y][x].SMELTP = 0;
                                        inventory.removeLocationIC(icmatrix[l][y][x], 0, FRN1.get(i));
                                        inventory.addLocationIC(icmatrix[l][y][x], 3, FRI2.get(i), FRN2.get(i));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        icmatrix[l][y][x].SMELTP -= 0.00025;
                        if (icmatrix[l][y][x].SMELTP < 0) {
                            icmatrix[l][y][x].SMELTP = 0;
                        }
                    }
                }
            }
        }

        if (ic != null && ic.type.equals("furnace")) {
            if (ic.F_ON) {
                if (ic.ids[1] == 0) {
                    if (FUELS.get(ic.ids[2]) != null) {
                        inventory.addLocationIC(ic, 1, ic.ids[2], (short)1);
                        inventory.removeLocationIC(ic, 2, (short)1);
                        ic.FUELP = 1;
                    }
                    else {
                        ic.F_ON = false;
                        removeBlockLighting(icx, icy);
                        blocks[iclayer][icy][icx] = 17;
                        rdrawn[icy][icx] = false;
                    }
                }
                if (FUELS.get(ic.ids[1]) != null) {
                    ic.FUELP -= FUELS.get(ic.ids[1]);
                    if (ic.FUELP < 0) {
                        ic.FUELP = 0;
                        inventory.removeLocationIC(ic, 1, ic.nums[1]);
                    }
                    for (i=0; i<FRI1.size(); i++) {
                        if (ic.ids[0] == FRI1.get(i) && ic.nums[0] >= FRN1.get(i)) {
                            ic.SMELTP += FSPEED.get(ic.ids[1]);
                            if (ic.SMELTP > 1) {
                                ic.SMELTP = 0;
                                inventory.removeLocationIC(ic, 0, FRN1.get(i));
                                inventory.addLocationIC(ic, 3, FRI2.get(i), FRN2.get(i));
                            }
                            break;
                        }
                    }
                }
            }
            else {
                ic.SMELTP -= 0.00025;
                if (ic.SMELTP < 0) {
                    ic.SMELTP = 0;
                }
            }
            inventory.updateIC(ic, -1);
        }
        if (Math.sqrt(Math.pow(player.x+player.image.getWidth()-icx*BLOCKSIZE+BLOCKSIZE/2, 2) + Math.pow(player.y+player.image.getHeight()-icy*BLOCKSIZE+BLOCKSIZE/2, 2)) > 160) {
            if (ic != null) {
                if (!ic.type.equals("workbench")) {
                    machinesx.add(icx);
                    machinesy.add(icy);
                    icmatrix[iclayer][icy][icx] = new ItemCollection(ic.type, ic.ids, ic.nums, ic.durs);
                }
                if (ic.type.equals("workbench")) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        for (i=0; i<9; i++) {
                            if (ic.ids[i] != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                            }
                        }
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        for (i=0; i<9; i++) {
                            if (ic.ids[i] != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                            }
                        }
                    }
                }
                if (ic.type.equals("furnace")) {
                    icmatrix[iclayer][icy][icx].FUELP = ic.FUELP;
                    icmatrix[iclayer][icy][icx].SMELTP = ic.SMELTP;
                    icmatrix[iclayer][icy][icx].F_ON = ic.F_ON;
                }
                ic = null;
            }
        }

        for (int l=0; l<3; l++) {
            for (y=0; y<size; y++) {
                for (x=0; x<size; x++) {
                    if (random.nextInt(22500) == 0) {
                        t = 0;
                        switch (blocks[l][y][x]) {
                        case 48: if (timeOfDay >= 75913 || timeOfDay < 28883) t = 49; break;
                        case 49: if (timeOfDay >= 75913 || timeOfDay < 28883) t = 50; break;
                        case 51: if (timeOfDay >= 32302 && timeOfDay < 72093) t = 52; break;
                        case 52: if (timeOfDay >= 32302 && timeOfDay < 72093) t = 53; break;
                        case 54: if (checkBiome(x, y).equals("desert")) t = 55; break;
                        case 55: if (checkBiome(x, y).equals("desert")) t = 56; break;
                        case 57: if (checkBiome(x, y).equals("jungle")) t = 58; break;
                        case 58: if (checkBiome(x, y).equals("jungle")) t = 59; break;
                        case 60: if (checkBiome(x, y).equals("frost")) t = 61; break;
                        case 61: if (checkBiome(x, y).equals("frost")) t = 62; break;
                        case 63: if (checkBiome(x, y).equals("cavern") || y >= 0/*stonelayer[x]*/) t = 64; break;
                        case 64: if (checkBiome(x, y).equals("cavern") || y >= 0/*stonelayer[x]*/) t = 65; break;
                        case 66: if (y <= HEIGHT*0.08 && random.nextInt(3) == 0 || y <= HEIGHT*0.04) t = 67; break;
                        case 67: if (y <= HEIGHT*0.08 && random.nextInt(3) == 0 || y <= HEIGHT*0.04) t = 68; break;
                        case 69: if (y >= HEIGHT*0.98) t = 70; break;
                        case 70: if (y >= HEIGHT*0.98) t = 71; break;
                        case 77: if (checkBiome(x, y).equals("swamp")) t = 78; break;
                        case 78: if (checkBiome(x, y).equals("swamp")) t = 79; break;
                        default: break;
                        }
                        if (t != 0) {
                            blocks[l][y][x] = t;
                            drawn[y][x] = false;
                        }
                    }
                }
            }
        }

        for (int l=0; l<3; l++) {
            for (y=0; y<size; y++) {
                for (x=0; x<size; x++) {
                    if (random.nextInt(1000) == 0) {
                        if (y >= 1 && y < HEIGHT-1) {
                            doGrassGrow = false;
                            if (blocks[l][y][x] == 1 && hasOpenSpace(x+u, y+v, l) && blocks[l][y+random.nextInt(3)-1+u][x+random.nextInt(3)-1+v] == 72) {
                                blocks[l][y][x] = 72;
                                doGrassGrow = true;
                            }
                            if (blocks[l][y][x] == 1 && hasOpenSpace(x+u, y+v, l) && blocks[l][y+random.nextInt(3)-1+u][x+random.nextInt(3)-1+v] == 73) {
                                blocks[l][y][x] = 73;
                                doGrassGrow = true;
                            }
                            if (blocks[l][y][x] == 75 && hasOpenSpace(x+u, y+v, l) && blocks[l][y+random.nextInt(3)-1+u][x+random.nextInt(3)-1+v] == 74) {
                                blocks[l][y][x] = 74;
                                doGrassGrow = true;
                            }
                            if (doGrassGrow) {
                                for (y2=y-1; y2<y+2; y2++) {
                                    for (x2=x-1; x2<x+2; x2++) {
                                        if (y2 >= 0 && y2 < HEIGHT) {
                                            drawn[y2][x2] = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int l=0; l<3; l++) {
            for (y=0; y<size; y++) {
                for (x=0; x<size; x++) {
                    if (random.nextInt(1000) == 0) {
                        if (blocks[1][y][x] == 83) {
                            blocks[1][y][x] = 15;
                        }
                    }
                }
            }
        }

        for (i=updatex.size()-1; i>-1; i--) {
            updatet.set(i, updatet.get(i) - 1);
            if (updatet.get(i) <= 0) {
                if (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == 128) {
                    blockTemp = blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = 127;
                    rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                else if (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == 130) {
                    blockTemp = blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = 129;
                    rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                else if (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == 132) {
                    blockTemp = blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = 131;
                    rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                else if (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == 134) {
                    blockTemp = blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = 133;
                    rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                else if (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] == 136) {
                    blockTemp = blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i));
                    blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] = 135;
                    rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                else if ((blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 141 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 144)
                        || (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 149 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 152)
                        || (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 157 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 160)
                        || (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 165 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 168)) {
                    print("[DEBUG2R]");
                    blockTemp = blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)];
                    removeBlockPower(updatex.get(i), updatey.get(i), updatel.get(i), false);
                    blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] -= 4;
                    rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                else if ((blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 137 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 140)
                        || (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 145 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 148)
                        || (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 153 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 156)
                        || (blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] >= 161 && blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] <= 164)) {
                    print("[DEBUG2A]");
                    blocks[updatel.get(i)][updatey.get(i)][updatex.get(i)] += 4;
                    power[updatel.get(i)][updatey.get(i)][updatex.get(i)] = (float)5;
                    addBlockLighting(updatex.get(i), updatey.get(i));
                    addTileToPQueue(updatex.get(i), updatey.get(i));
                    rdrawn[updatey.get(i)][updatex.get(i)] = false;
                }
                updatex.remove(i);
                updatey.remove(i);
                updatet.remove(i);
                updatel.remove(i);
            }
        }

        if (!DEBUG_PEACEFUL && mobCount < 100) {
            if (msi == 1) {
                for (ay=(int)(player.iy/BLOCKSIZE)-125; ay<(int)(player.iy/BLOCKSIZE)+125; ay++) {
                    for (ax=(int)(player.ix/BLOCKSIZE)-125; ax<(int)(player.ix/BLOCKSIZE)+125; ax++) {
                        if (random.nextInt((int)(100000/DEBUG_HOSTILE)) == 0) {
                            xpos = ax+random.nextInt(20)-10;
                            ypos = ay+random.nextInt(20)-10;
                            xpos2 = ax+random.nextInt(20)-10;
                            ypos2 = ay+random.nextInt(20)-10;
                            if (xpos > 0 && xpos < WIDTH-1 && ypos > 0 && ypos < HEIGHT-1 && (blocks[1][ypos][xpos] == 0 || !BLOCK_CDS[blocks[1][ypos][xpos]] &&
                                xpos2 > 0 && xpos2 < WIDTH-1 && ypos2 > 0 && ypos2 < HEIGHT-1 && blocks[1][ypos2][xpos2] != 0 && BLOCK_CDS[blocks[1][ypos2][xpos2]])) {
                                mobSpawn = null;
                                if (!checkBiome(xpos, ypos).equals("underground")) {
                                    if ((day != 0 || DEBUG_HOSTILE > 1) && (timeOfDay >= 75913 || timeOfDay < 28883)) {
                                        if (random.nextInt(350) == 0) {
                                            rnum = random.nextInt(100);
                                            if (rnum >= 0 && rnum < 45) {
                                                mobSpawn = "blue_bubble"; // 45%
                                            }
                                            if (rnum >= 45 && rnum < 85) {
                                                mobSpawn = "green_bubble"; // 40%
                                            }
                                            if (rnum >= 85 && rnum < 100) {
                                                mobSpawn  = "red_bubble"; // 15%
                                            }
                                        }
                                    }
                                    if (timeOfDay >= 32302 && timeOfDay < 72093) {
                                        if (random.nextInt(200) == 0) {
                                            rnum = random.nextInt(100);
                                            if (rnum >= 0 && rnum < 80) {
                                                mobSpawn = "zombie"; // 80%
                                            }
                                            if (rnum >= 80 && rnum < 90) {
                                                mobSpawn = "armored_zombie"; // 10%
                                            }
                                            if (rnum >= 90 && rnum < 100) {
                                                mobSpawn = "shooting_star"; // 10%
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (random.nextInt(100) == 0) {
                                        rnum = random.nextInt(100);
                                        if (rnum >= 0 && rnum < 25) {
                                            mobSpawn = "yellow_bubble"; // 25%
                                        }
                                        if (rnum >= 25 && rnum < 45) {
                                            mobSpawn = "zombie"; // 20%
                                        }
                                        if (rnum >= 45 && rnum < 60) {
                                            mobSpawn = "armored_zombie"; // 15%
                                        }
                                        if (rnum >= 60 && rnum < 70) {
                                            mobSpawn = "black_bubble"; // 10%
                                        }
                                        if (rnum >= 70 && rnum < 85) {
                                            mobSpawn = "bat"; // 15%
                                        }
                                        if (rnum >= 85 && rnum < 100) {
                                            mobSpawn = "skeleton"; // 15%
                                        }
                                    }
                                }
                                if (mobSpawn != null && checkBiome(xpos, ypos).equals("desert")) {
                                    if (random.nextInt(3) == 0) { // 33% of all spawns in desert
                                        mobSpawn = "sandbot";
                                    }
                                }
                                if (mobSpawn != null && checkBiome(xpos, ypos).equals("frost")) {
                                    if (random.nextInt(3) == 0) { // 33% of all spawns in desert
                                        mobSpawn = "snowman";
                                    }
                                }
                                if (mobSpawn == null) {
                                    continue;
                                }
                                else if (DEBUG_MOBTEST != null) mobSpawn = DEBUG_MOBTEST;
                                if (mobSpawn.equals("blue_bubble") || mobSpawn.equals("green_bubble")
                                        || mobSpawn.equals("red_bubble") || mobSpawn.equals("yellow_bubble")
                                        || mobSpawn.equals("black_bubble") || mobSpawn.equals("white_bubble")) {
                                    xmax = 2;
                                    ymax = 2;
                                }
                                if (mobSpawn.equals("zombie")) {
                                    xmax = 2;
                                    ymax = 3;
                                }
                                if (mobSpawn.equals("armored_zombie")) {
                                    xmax = 2;
                                    ymax = 3;
                                }
                                if (mobSpawn.equals("shooting_star")) {
                                    xmax = 2;
                                    ymax = 2;
                                }
                                if (mobSpawn.equals("sandbot")) {
                                    xmax = 2;
                                    ymax = 2;
                                }
                                if (mobSpawn.equals("snowman")) {
                                    xmax = 2;
                                    ymax = 3;
                                }
                                if (mobSpawn.equals("bat")) {
                                    xmax = 1;
                                    ymax = 1;
                                }
                                if (mobSpawn.equals("bee")) {
                                    xmax = 1;
                                    ymax = 1;
                                }
                                if (mobSpawn.equals("skeleton")) {
                                    xmax = 1;
                                    ymax = 3;
                                }
                                doMobSpawn = true;
                                for (x=(int)(xpos/BLOCKSIZE); x<(int)(xpos/BLOCKSIZE+xmax); x++) {
                                    for (y=(int)(ypos/BLOCKSIZE); y<(int)(ypos/BLOCKSIZE+ymax); y++) {
                                        if (y > 0 && y < HEIGHT-1 && blocks[1][y][x] != 0 && BLOCK_CDS[blocks[1][y][x]]) {
                                            doMobSpawn = false;
                                        }
                                    }
                                }
                                if (doMobSpawn) {
                                    entities.add(new Entity(xpos*BLOCKSIZE, ypos*BLOCKSIZE, 0, 0, mobSpawn));
                                }
                            }
                        }
                    }
                }
                msi = 0;
            }
            else {
                msi = 1;
            }
        }

        mobCount = 0;

        for (i=entities.size()-1; i>-1; i--) {
            if (entities.get(i).getName() != null) {
                mobCount += 1;
                if (entities.get(i).getIx() < player.ix - 2000 || entities.get(i).getIx() > player.ix + 2000 ||
                    entities.get(i).getIy() < player.iy - 2000 || entities.get(i).getIy() > player.iy + 2000) {
                    if (random.nextInt(500) == 0) {
                        entities.remove(i);
                    }
                }
            }
        }

        if (queue[3]) {
            checkBlocks = true;
            if (showInv) {
                if (mousePos[0] >= getWidth()-save_exit.getWidth()-24 && mousePos[0] <= getWidth()-24 &&
                    mousePos[1] >= getHeight()-save_exit.getHeight()-24 && mousePos[1] <= getHeight()-24) {
                    if (mouseClicked) {
                        mouseNoLongerClicked = true;
                        saveWorld();
                        state = "title_screen";
                        timer.stop();
                        menuTimer.start();
                        return;
                    }
                }
                for (ux=0; ux<10; ux++) {
                    for (uy=0; uy<4; uy++) {
                        if (mousePos[0] >= ux*46+6 && mousePos[0] < ux*46+46 &&
                            mousePos[1] >= uy*46+6 && mousePos[1] < uy*46+46) {
                            checkBlocks = false;
                            if (mouseClicked) {
                                mouseNoLongerClicked = true;
                                if (uy != 0 || inventory.selection != ux || !showTool) {
                                    moveItemTemp = inventory.ids[uy*10+ux];
                                    moveNumTemp = inventory.nums[uy*10+ux];
                                    moveDurTemp = inventory.durs[uy*10+ux];
                                    if (moveItem == inventory.ids[uy*10+ux]) {
                                        moveNum = (short)inventory.addLocation(uy*10+ux, moveItem, moveNum, moveDur);
                                        if (moveNum == 0) {
                                            moveItem = 0;
                                            moveDur = 0;
                                        }
                                    }
                                    else {
                                        inventory.removeLocation(uy*10+ux, inventory.nums[uy*10+ux]);
                                        if (moveItem != 0) {
                                            inventory.addLocation(uy*10+ux, moveItem, moveNum, moveDur);
                                        }
                                        moveItem = moveItemTemp;
                                        moveNum = moveNumTemp;
                                        moveDur = moveDurTemp;
                                    }
                                }
                            }
                        }
                    }
                }
                for (ux=0; ux<2; ux++) {
                    for (uy=0; uy<2; uy++) {
                        if (mousePos[0] >= inventory.image.getWidth()+ux*40+75 &&
                            mousePos[0] < inventory.image.getWidth()+ux*40+115 &&
                            mousePos[1] >= uy*40+52 && mousePos[1] < uy*40+92) {
                            checkBlocks = false;
                            if (mouseClicked) {
                                mouseNoLongerClicked = true;
                                moveItemTemp = cic.ids[uy*2+ux];
                                moveNumTemp = cic.nums[uy*2+ux];
                                moveDurTemp = cic.durs[uy*2+ux];
                                if (moveItem == cic.ids[uy*2+ux]) {
                                    moveNum = (short)inventory.addLocationIC(cic, uy*2+ux, moveItem, moveNum, moveDur);
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                        moveDur = 0;
                                    }
                                }
                                else {
                                    inventory.removeLocationIC(cic, uy*2+ux, cic.nums[uy*2+ux]);
                                    if (moveItem != 0) {
                                        inventory.addLocationIC(cic, uy*2+ux, moveItem, moveNum, moveDur);
                                    }
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                    moveDur = moveDurTemp;
                                }
                            }
                        }
                    }
                }
                if (mousePos[0] >= inventory.image.getWidth()+3*40+81 &&
                    mousePos[0] < inventory.image.getWidth()+3*40+121 &&
                    mousePos[1] >= 20+52 && mousePos[1] < 20+92) {
                    checkBlocks = false;
                    if (mouseClicked) {
                        if (moveItem == cic.ids[4] && moveNum + cic.nums[4] <= MAXSTACKS.get(cic.ids[4])) {
                            moveNum += cic.nums[4];
                            inventory.useRecipeCIC(cic);
                        }
                        if (moveItem == 0) {
                            moveItem = cic.ids[4];
                            moveNum = cic.nums[4];
                            if (TOOLDURS.get(moveItem) != null) {
                                moveDur = TOOLDURS.get(moveItem);
                            }
                            inventory.useRecipeCIC(cic);
                        }
                    }
                }
                if (ic != null) {
                    if (ic.type.equals("workbench")) {
                        for (ux=0; ux<3; ux++) {
                            for (uy=0; uy<3; uy++) {
                                if (mousePos[0] >= ux*40+6 && mousePos[0] < ux*40+46 &&
                                    mousePos[1] >= uy*40+inventory.image.getHeight()+46 &&
                                    mousePos[1] < uy*40+inventory.image.getHeight()+86) {
                                    checkBlocks = false;
                                    if (mouseClicked) {
                                        mouseNoLongerClicked = true;
                                        moveItemTemp = ic.ids[uy*3+ux];
                                        moveNumTemp = ic.nums[uy*3+ux];
                                        if (moveItem == ic.ids[uy*3+ux]) {
                                            moveNum = (short)inventory.addLocationIC(ic, uy*3+ux, moveItem, moveNum, moveDur);
                                            if (moveNum == 0) {
                                                moveItem = 0;
                                            }
                                        }
                                        else {
                                            inventory.removeLocationIC(ic, uy*3+ux, ic.nums[uy*3+ux]);
                                            if (moveItem != 0) {
                                                inventory.addLocationIC(ic, uy*3+ux, moveItem, moveNum, moveDur);
                                            }
                                            moveItem = moveItemTemp;
                                            moveNum = moveNumTemp;
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos[0] >= 4*40+6 && mousePos[0] < 4*40+46 &&
                            mousePos[1] >= 1*40+inventory.image.getHeight()+46 &&
                            mousePos[1] < 1*40+inventory.image.getHeight()+86) {
                            checkBlocks = false;
                            if (mouseClicked) {
                                if (moveItem == ic.ids[9] && moveNum + ic.nums[9] <= MAXSTACKS.get(ic.ids[9])) {
                                    moveNum += ic.nums[9];
                                    inventory.useRecipeWorkbench(ic);
                                }
                                if (moveItem == 0) {
                                    moveItem = ic.ids[9];
                                    moveNum = ic.nums[9];
                                    if (TOOLDURS.get(moveItem) != null) {
                                        moveDur = TOOLDURS.get(moveItem);
                                    }
                                    inventory.useRecipeWorkbench(ic);
                                }
                            }
                        }
                    }
                    if (ic.type.equals("wooden_chest") || ic.type.equals("stone_chest") ||
                        ic.type.equals("copper_chest") || ic.type.equals("iron_chest") ||
                        ic.type.equals("silver_chest") || ic.type.equals("gold_chest") ||
                        ic.type.equals("zinc_chest") || ic.type.equals("rhymestone_chest") ||
                        ic.type.equals("obdurite_chest")) {
                        for (ux=0; ux<inventory.CX; ux++) {
                            for (uy=0; uy<inventory.CY; uy++) {
                                if (mousePos[0] >= ux*46+6 && mousePos[0] < ux*46+46 &&
                                    mousePos[1] >= uy*46+inventory.image.getHeight()+46 &&
                                    mousePos[1] < uy*46+inventory.image.getHeight()+86) {
                                    checkBlocks = false;
                                    if (mouseClicked) {
                                        mouseNoLongerClicked = true;
                                        moveItemTemp = ic.ids[uy*inventory.CX+ux];
                                        moveNumTemp = ic.nums[uy*inventory.CX+ux];
                                        if (moveItem == ic.ids[uy*inventory.CX+ux]) {
                                            moveNum = (short)inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, moveNum, moveDur);
                                            if (moveNum == 0) {
                                                moveItem = 0;
                                            }
                                        }
                                        else {
                                            inventory.removeLocationIC(ic, uy*inventory.CX+ux, ic.nums[uy*inventory.CX+ux]);
                                            if (moveItem != 0) {
                                                inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, moveNum, moveDur);
                                            }
                                            moveItem = moveItemTemp;
                                            moveNum = moveNumTemp;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (ic.type.equals("furnace")) {
                        if (mousePos[0] >= 6 && mousePos[0] < 46 &&
                            mousePos[1] >= inventory.image.getHeight()+46 &&
                            mousePos[1] < inventory.image.getHeight()+86) {
                            checkBlocks = false;
                            if (mouseClicked) {
                                mouseNoLongerClicked = true;
                                moveItemTemp = ic.ids[0];
                                moveNumTemp = ic.nums[0];
                                if (moveItem == ic.ids[0]) {
                                    moveNum = (short)inventory.addLocationIC(ic, 0, moveItem, moveNum, moveDur);
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                                else {
                                    inventory.removeLocationIC(ic, 0, ic.nums[0]);
                                    if (moveItem != 0) {
                                        inventory.addLocationIC(ic, 0, moveItem, moveNum, moveDur);
                                    }
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                    ic.SMELTP = 0;
                                }
                            }
                        }
                        if (mousePos[0] >= 6 && mousePos[0] < 46 &&
                            mousePos[1] >= inventory.image.getHeight()+142 &&
                            mousePos[1] < inventory.image.getHeight()+182) {
                            checkBlocks = false;
                            if (mouseClicked) {
                                mouseNoLongerClicked = true;
                                moveItemTemp = ic.ids[2];
                                moveNumTemp = ic.nums[2];
                                if (moveItem == ic.ids[2]) {
                                    moveNum = (short)inventory.addLocationIC(ic, 2, moveItem, moveNum, moveDur);
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                                else {
                                    inventory.removeLocationIC(ic, 2, ic.nums[2]);
                                    if (moveItem != 0) {
                                        inventory.addLocationIC(ic, 2, moveItem, moveNum, moveDur);
                                    }
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                }
                            }
                        }
                        if (mousePos[0] >= 62 && mousePos[0] < 102 &&
                            mousePos[1] >= inventory.image.getHeight()+46 &&
                            mousePos[1] < inventory.image.getHeight()+86) {
                            checkBlocks = false;
                            if (mouseClicked) {
                                mouseNoLongerClicked = true;
                                if (moveItem == 0) {
                                    moveItem = ic.ids[3];
                                    moveNum = ic.nums[3];
                                    inventory.removeLocationIC(ic, 3, ic.nums[3]);
                                }
                                else if (moveItem == ic.ids[3]) {
                                    moveNum += ic.nums[3];
                                    inventory.removeLocationIC(ic, 3, ic.nums[3]);
                                    if (moveNum > MAXSTACKS.get(moveItem)) {
                                        inventory.addLocationIC(ic, 3, moveItem, (short)(moveNum - MAXSTACKS.get(moveItem)), moveDur);
                                        moveNum = MAXSTACKS.get(moveItem);
                                    }
                                }
                            }
                        }
                    }
                }
                for (uy=0; uy<4; uy++) {
                    if (mousePos[0] >= inventory.image.getWidth() + 6 && mousePos[0] < inventory.image.getWidth() + 6 + armor.image.getWidth() &&
                        mousePos[1] >= 6 + uy*46 && mousePos[1] < 6 + uy*46+40) {
                        checkBlocks = false;
                        if (mouseClicked) {
                            mouseNoLongerClicked = true;
                            i = uy;
                            if (uy == 0 && (moveItem == (short)105 || moveItem == (short)109 || moveItem == (short)113 || moveItem == (short)117 ||
                                            moveItem == (short)121 || moveItem == (short)125 || moveItem == (short)129 || moveItem == (short)133 ||
                                            moveItem == (short)137 || moveItem == (short)141) ||
                                uy == 1 && (moveItem == (short)106 || moveItem == (short)110 || moveItem == (short)114 || moveItem == (short)118 ||
                                            moveItem == (short)122 || moveItem == (short)126 || moveItem == (short)130 || moveItem == (short)134 ||
                                            moveItem == (short)138 || moveItem == (short)142) ||
                                uy == 2 && (moveItem == (short)107 || moveItem == (short)111 || moveItem == (short)115 || moveItem == (short)119 ||
                                            moveItem == (short)123 || moveItem == (short)127 || moveItem == (short)131 || moveItem == (short)135 ||
                                            moveItem == (short)139 || moveItem == (short)143) ||
                                uy == 3 && (moveItem == (short)108 || moveItem == (short)112 || moveItem == (short)116 || moveItem == (short)120 ||
                                            moveItem == (short)124 || moveItem == (short)128 || moveItem == (short)132 || moveItem == (short)136 ||
                                            moveItem == (short)140 || moveItem == (short)144)) {
                                if (armor.ids[i] == 0) {
                                    inventory.addLocationIC(armor, i, moveItem, moveNum, moveDur);
                                    moveItem = 0;
                                    moveNum = 0;
                                }
                                else {
                                    moveItemTemp = armor.ids[i];
                                    moveNumTemp = armor.nums[i];
                                    inventory.removeLocationIC(armor, i, moveNumTemp);
                                    inventory.addLocationIC(armor, i, moveItem, moveNum, moveDur);
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                }
                            }
                            else if (moveItem == 0) {
                                moveItem = armor.ids[i];
                                moveNum = armor.nums[i];
                                inventory.removeLocationIC(armor, i, moveNum);
                            }
                        }
                    }
                }
            }
            else {
                for (ux=0; ux<10; ux++) {
                    uy = 0;
                    if (mousePos[0] >= ux*46+6 && mousePos[0] < ux*46+46 &&
                        mousePos[1] >= uy*46+6 && mousePos[1] < uy*46+46) {
                        checkBlocks = false;
                        if (mouseClicked) {
                            mouseNoLongerClicked = true;
                            inventory.select2(ux);
                        }
                    }
                }
            }
            if (mouseNoLongerClicked) {
                mouseClicked = false;
            }
            if (checkBlocks) {
                if (inventory.tool() != 0 && !showTool) {
                    tool = itemImgs.get(inventory.tool());
                    for (i=0; i<entities.size(); i++) {
                        entities.get(i).setImmune(false);
                    }
                    toolSpeed = TOOLSPEED.get(inventory.tool());
                    if (inventory.tool() == 169 || inventory.tool() == 170 || inventory.tool() == 171) {
                        toolSpeed *= ((double)inventory.durs[inventory.selection]/TOOLDURS.get(inventory.ids[inventory.selection])) * (-0.714) + 1;
                    }
                    showTool = true;
                    toolAngle = 4.7;
                    ux = (int)(mousePos2[0]/BLOCKSIZE);
                    uy = (int)(mousePos2[1]/BLOCKSIZE);
                    ux2 = (int)(mousePos2[0]/BLOCKSIZE);
                    uy2 = (int)(mousePos2[1]/BLOCKSIZE);
                    if (Math.sqrt(Math.pow(player.x+player.image.getWidth()-ux2*BLOCKSIZE+BLOCKSIZE/2, 2) + Math.pow(player.y+player.image.getHeight()-uy2*BLOCKSIZE+BLOCKSIZE/2, 2)) <= 160 ||
                        Math.sqrt(Math.pow(player.x+player.image.getWidth()-ux2*BLOCKSIZE+BLOCKSIZE/2+WIDTH*BLOCKSIZE, 2) + Math.pow(player.y+player.image.getHeight()-uy2*BLOCKSIZE+BLOCKSIZE/2, 2)) <= 160 || DEBUG_REACH) {
                        ucx = ux - CHUNKBLOCKS * ((int)(ux/CHUNKBLOCKS));
                        ucy = uy - CHUNKBLOCKS * ((int)(uy/CHUNKBLOCKS));
                        if (Arrays.asList(TOOL_LIST).contains(inventory.tool())) {
                            if (blocks[layer][uy][ux] != 0 && Arrays.asList(BLOCKTOOLS.get(blocks[layer][uy][ux])).contains(inventory.tool())) {
                                blockdns[uy][ux] = (byte)random.nextInt(5);
                                drawn[uy][ux] = false;
                                if (ux == mx && uy == my && inventory.tool() == miningTool) {
                                    mining += 1;
                                    inventory.durs[inventory.selection] -= 1;
                                    breakCurrentBlock();
                                    if (inventory.durs[inventory.selection] <= 0) {
                                        inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                                    }
                                }
                                else {
                                    mining = 1;
                                    miningTool = inventory.tool();
                                    inventory.durs[inventory.selection] -= 1;
                                    breakCurrentBlock();
                                    mx = ux;
                                    my = uy;
                                    if (inventory.durs[inventory.selection] <= 0) {
                                        inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                                    }
                                }
                                ug2 = null;
                            }
                        }
                        else if (inventory.tool() == 33) {
                            if (blocks[layer][uy][ux] == 17 || blocks[layer][uy][ux] == 23) {
                                if (icmatrix[layer][uy][ux] != null && icmatrix[layer][uy][ux].type.equals("furnace")) {
                                    inventory.durs[inventory.selection] -= 1;
                                    icmatrix[layer][uy][ux].F_ON = true;
                                    blocks[layer][uy][ux] = 23;
                                    addBlockLighting(ux, uy);
                                    if (inventory.durs[inventory.selection] <= 0) {
                                        inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                                    }
                                    rdrawn[uy][ux] = false;
                                }
                                else {
                                    if (ic != null && ic.type.equals("furnace")) {
                                        inventory.durs[inventory.selection] -= 1;
                                        ic.F_ON = true;
                                        blocks[layer][icy][icx] = 23;
                                        addBlockLighting(ux, uy);
                                        rdrawn[icy][icx] = false;
                                        if (inventory.durs[inventory.selection] <= 0) {
                                            inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                                        }
                                    }
                                }
                            }
                        }
                        else if (inventory.tool() == 190) {
                            if (blocks[layer][uy][ux] >= 137 && blocks[layer][uy][ux] <= 160) {
                                inventory.durs[inventory.selection] -= 1;
                                blocks[layer][uy][ux] += 8;
                                rdrawn[uy][ux] = false;
                                if (inventory.durs[inventory.selection] <= 0) {
                                    inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                                }
                            }
                            else if (blocks[layer][uy][ux] >= 161 && blocks[layer][uy][ux] <= 168) {
                                inventory.durs[inventory.selection] -= 1;
                                blocks[layer][uy][ux] -= 24;
                                rdrawn[uy][ux] = false;
                                if (inventory.durs[inventory.selection] <= 0) {
                                    inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                                }
                            }
                        }
                        else if (ITEMBLOCKS.get(inventory.tool()) != 0) {
                            blockTemp = ITEMBLOCKS.get(inventory.tool());
                            if (uy >= 1 && (blocks[layer][uy][ux] == 0) &&
                                    (layer == 0 && (
                                    blocks[layer][uy][ux-1] != 0 || blocks[layer][uy][ux+1] != 0 ||
                                    blocks[layer][uy-1][ux] != 0 || blocks[layer][uy+1][ux] != 0 ||
                                    blocks[layer+1][uy][ux] != 0) ||
                                    layer == 1 && (
                                    blocks[layer][uy][ux-1] != 0 || blocks[layer][uy][ux+1] != 0 ||
                                    blocks[layer][uy-1][ux] != 0 || blocks[layer][uy+1][ux] != 0 ||
                                    blocks[layer-1][uy][ux] != 0 || blocks[layer+1][uy][ux] != 0) ||
                                    layer == 2 && (
                                    blocks[layer][uy][ux-1] != 0 || blocks[layer][uy][ux+1] != 0 ||
                                    blocks[layer][uy-1][ux] != 0 || blocks[layer][uy+1][ux] != 0 ||
                                    blocks[layer-1][uy][ux] != 0)) &&
                                    !(blockTemp == 48 && (blocks[layer][uy+1][ux] != 1 && blocks[layer][uy+1][ux] != 72 && blocks[layer][uy+1][ux] != 73) || // sunflower
                                                      blockTemp == 51 && (blocks[layer][uy+1][ux] != 1 && blocks[layer][uy+1][ux] != 72 && blocks[layer][uy+1][ux] != 73) || // moonflower
                                                      blockTemp == 54 && (blocks[layer][uy+1][ux] != 45) || // dryweed
                                                      blockTemp == 57 && (blocks[layer][uy+1][ux] != 73) || // greenleaf
                                                      blockTemp == 60 && (blocks[layer][uy+1][ux] != 46) || // frostleaf
                                                      blockTemp == 63 && (blocks[layer][uy+1][ux] != 2) || // caveroot
                                                      blockTemp == 66 && (blocks[layer][uy+1][ux] != 1 && blocks[layer][uy+1][ux] != 72 && blocks[layer][uy+1][ux] != 73) || // skyblossom
                                                      blockTemp == 69 && (blocks[layer][uy+1][ux] != 2))) { // void_rot
                                if (!(TORCHESL.get(blockTemp) != null) || uy < HEIGHT - 1 && (SOLID[blocks[layer][uy+1][ux]] && blockTemp != 127 || SOLID[blocks[layer][uy][ux+1]] || SOLID[blocks[layer][uy][ux-1]])) {
                                    if (TORCHESL.get(blockTemp) != null) {
                                        if (SOLID[blocks[layer][uy+1][ux]] && blockTemp != 127) {
                                            blockTemp = blockTemp;
                                        }
                                        else if (SOLID[blocks[layer][uy][ux-1]]) {
                                            blockTemp = TORCHESL.get(blockTemp);
                                        }
                                        else if (SOLID[blocks[layer][uy][ux+1]]) {
                                            blockTemp = TORCHESR.get(blockTemp);
                                        }
                                    }
                                    if (layer == 1 && !DEBUG_GPLACE && BLOCK_CDS[blockTemp]) {
                                        for (i=0; i<entities.size(); i++) {
                                            if (entities.get(i).getName() != null && entities.get(i).getRect().intersects(new Rectangle(ux*BLOCKSIZE, uy*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                                blockTemp = 0;
                                            }
                                        }
                                        if (player.rect.intersects(new Rectangle(ux*BLOCKSIZE, uy*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                            blockTemp = 0;
                                        }
                                    }
                                    if (blockTemp != 0) {
                                        blocks[layer][uy][ux] = blockTemp;
                                        if (RECEIVES[blocks[layer][uy][ux]]) {
                                            addAdjacentTilesToPQueue(ux, uy);
                                        }
                                        if (POWERS[blocks[layer][uy][ux]]) {
                                            addBlockPower(ux, uy);
                                        }
                                        if (L_TRANS[blocks[layer][uy][ux]]) {
                                            removeSunLighting(ux, uy);
                                            redoBlockLighting(ux, uy);
                                        }
                                        addBlockLighting(ux, uy);
                                    }
                                    if (blockTemp != 0) {
                                        inventory.removeLocation(inventory.selection, (short)1);
                                        blockds[layer] = World.generate2b(blocks[layer], blockds[layer], ux, uy);
                                        for (uly=uy-1; uly<uy+2; uly++) {
                                            for (ulx=ux-1; ulx<ux+2; ulx++) {
                                                blockdns[uly][ulx] = (byte)random.nextInt(5);
                                            }
                                        }
                                        for (uly=uy-1; uly<uy+2; uly++) {
                                            for (ulx=ux-1; ulx<ux+2; ulx++) {
                                                drawn[uly][ulx] = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            mouseClicked = true;
        }
        if (queue[4]) {
            checkBlocks = true;
            if (showInv) {
                for (ux=0; ux<10; ux++) {
                    for (uy=0; uy<4; uy++) {
                        if (mousePos[0] >= ux*46+6 && mousePos[0] < ux*46+46 &&
                            mousePos[1] >= uy*46+6 && mousePos[1] < uy*46+46) {
                            checkBlocks = false;
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true;
                                moveItemTemp = inventory.ids[uy*10+ux];
                                moveNumTemp = (short)(inventory.nums[uy*10+ux]/2);
                                moveDurTemp = inventory.durs[uy*10+ux];
                                if (inventory.ids[uy*10+ux] == 0) {
                                    inventory.addLocation(uy*10+ux, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                        moveDur = 0;
                                    }
                                }
                                else if (moveItem == 0 && inventory.nums[uy*10+ux] != 1) {
                                    inventory.removeLocation(uy*10+ux, (short)(inventory.nums[uy*10+ux]/2));
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                    moveDur = moveDurTemp;
                                }
                                else if (moveItem == inventory.ids[uy*10+ux] && inventory.nums[uy*10+ux] < MAXSTACKS.get(inventory.ids[uy*10+ux])) {
                                    inventory.addLocation(uy*10+ux, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                        moveDur = 0;
                                    }
                                }
                            }
                        }
                    }
                }
                for (ux=0; ux<2; ux++) {
                    for (uy=0; uy<2; uy++) {
                        if (mousePos[0] >= inventory.image.getWidth()+ux*40+75 &&
                            mousePos[0] < inventory.image.getWidth()+ux*40+121 &&
                            mousePos[1] >= uy*40+52 && mousePos[1] < uy*40+92) {
                            checkBlocks = false;
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true;
                                moveItemTemp = cic.ids[uy*2+ux];
                                moveNumTemp = (short)(cic.nums[uy*2+ux]/2);
                                if (cic.ids[uy*2+ux] == 0) {
                                    inventory.addLocationIC(cic, uy*2+ux, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                                else if (moveItem == 0 && cic.nums[uy*2+ux] != 1) {
                                    inventory.removeLocationIC(cic, uy*2+ux, (short)(cic.nums[uy*2+ux]/2));
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                }
                                else if (moveItem == cic.ids[uy*2+ux] && cic.nums[uy*2+ux] < MAXSTACKS.get(cic.ids[uy*2+ux])) {
                                    inventory.addLocationIC(cic, uy*2+ux, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                            }
                        }
                    }
                }
                if (ic != null) {
                    if (ic.type.equals("workbench")) {
                        for (ux=0; ux<3; ux++) {
                            for (uy=0; uy<3; uy++) {
                                if (mousePos[0] >= ux*40+6 && mousePos[0] < ux*40+46 &&
                                    mousePos[1] >= uy*40+inventory.image.getHeight()+46 &&
                                    mousePos[1] < uy*40+inventory.image.getHeight()+86) {
                                    checkBlocks = false;
                                    if (mouseClicked2) {
                                        mouseNoLongerClicked2 = true;
                                        moveItemTemp = ic.ids[uy*3+ux];
                                        moveNumTemp = (short)(ic.nums[uy*3+ux]/2);
                                        if (ic.ids[uy*3+ux] == 0) {
                                            inventory.addLocationIC(ic, uy*3+ux, moveItem, (short)1, moveDur);
                                            moveNum -= 1;
                                            if (moveNum == 0) {
                                                moveItem = 0;
                                            }
                                        }
                                        else if (moveItem == 0 && ic.nums[uy*3+ux] != 1) {
                                            inventory.removeLocationIC(ic, uy*3+ux, (short)(ic.nums[uy*3+ux]/2));
                                            moveItem = moveItemTemp;
                                            moveNum = moveNumTemp;
                                        }
                                        else if (moveItem == ic.ids[uy*3+ux] && ic.nums[uy*3+ux] < MAXSTACKS.get(ic.ids[uy*3+ux])) {
                                            if (ic.ids[7] == 160 && ic.nums[7] == 51 && moveItem == 165 && uy*3+ux == 3 && ic.nums[8] == 0) {
                                                inventory.addLocationIC(ic, 8, (short)154, (short)1);
                                            }
                                            else {
                                                inventory.addLocationIC(ic, uy*3+ux, moveItem, (short)1, moveDur);
                                                moveNum -= 1;
                                                if (moveNum == 0) {
                                                    moveItem = 0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (mousePos[0] >= 4*40+6 && mousePos[0] < 4*40+46 &&
                            mousePos[1] >= 1*40+inventory.image.getHeight()+46 &&
                            mousePos[1] < 1*40+inventory.image.getHeight()+86) {
                            checkBlocks = false;
                            if (mouseClicked2) {
                                //
                            }
                        }
                    }
                    if (ic.type.equals("wooden_chest") || ic.type.equals("stone_chest") ||
                        ic.type.equals("copper_chest") || ic.type.equals("iron_chest") ||
                        ic.type.equals("silver_chest") || ic.type.equals("gold_chest") ||
                        ic.type.equals("zinc_chest") || ic.type.equals("rhymestone_chest") ||
                        ic.type.equals("obdurite_chest")) {
                        for (ux=0; ux<inventory.CX; ux++) {
                            for (uy=0; uy<inventory.CY; uy++) {
                                if (mousePos[0] >= ux*46+6 && mousePos[0] < ux*46+46 &&
                                    mousePos[1] >= uy*46+inventory.image.getHeight()+46 &&
                                    mousePos[1] < uy*46+inventory.image.getHeight()+86) {
                                    checkBlocks = false;
                                    if (mouseClicked2) {
                                        mouseNoLongerClicked2 = true;
                                        moveItemTemp = ic.ids[uy*inventory.CX+ux];
                                        moveNumTemp = (short)(ic.nums[uy*inventory.CX+ux]/2);
                                        if (ic.ids[uy*inventory.CX+ux] == 0) {
                                            inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, (short)1, moveDur);
                                            moveNum -= 1;
                                            if (moveNum == 0) {
                                                moveItem = 0;
                                            }
                                        }
                                        else if (moveItem == 0 && ic.nums[uy*inventory.CX+ux] != 1) {
                                            inventory.removeLocationIC(ic, uy*inventory.CX+ux, (short)(ic.nums[uy*inventory.CX+ux]/2));
                                            moveItem = moveItemTemp;
                                            moveNum = moveNumTemp;
                                        }
                                        else if (moveItem == ic.ids[uy*inventory.CX+ux] && ic.nums[uy*inventory.CX+ux] < MAXSTACKS.get(ic.ids[uy*inventory.CX+ux])) {
                                            inventory.addLocationIC(ic, uy*inventory.CX+ux, moveItem, (short)1, moveDur);
                                            moveNum -= 1;
                                            if (moveNum == 0) {
                                                moveItem = 0;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (ic.type.equals("furnace")) {
                        if (mousePos[0] >= 6 && mousePos[0] < 46 &&
                            mousePos[1] >= inventory.image.getHeight()+46 &&
                            mousePos[1] < inventory.image.getHeight()+86) {
                            checkBlocks = false;
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true;
                                moveItemTemp = ic.ids[0];
                                moveNumTemp = (short)(ic.nums[0]/2);
                                if (ic.ids[0] == 0) {
                                    inventory.addLocationIC(ic, 0, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                                else if (moveItem == 0 && ic.nums[0] != 1) {
                                    inventory.removeLocationIC(ic, 0, (short)(ic.nums[0]/2));
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                }
                                else if (moveItem == ic.ids[0] && ic.nums[0] < MAXSTACKS.get(ic.ids[0])) {
                                    inventory.addLocationIC(ic, 0, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                            }
                        }
                        if (mousePos[0] >= 6 && mousePos[0] < 46 &&
                            mousePos[1] >= inventory.image.getHeight()+142 &&
                            mousePos[1] < inventory.image.getHeight()+182) {
                            checkBlocks = false;
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true;
                                moveItemTemp = ic.ids[2];
                                moveNumTemp = (short)(ic.nums[2]/2);
                                if (ic.ids[2] == 0) {
                                    inventory.addLocationIC(ic, 2, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                                else if (moveItem == 0 && ic.nums[2] != 1) {
                                    inventory.removeLocationIC(ic, 2, (short)(ic.nums[2]/2));
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                }
                                else if (moveItem == ic.ids[2] && ic.nums[2] < MAXSTACKS.get(ic.ids[2])) {
                                    inventory.addLocationIC(ic, 2, moveItem, (short)1, moveDur);
                                    moveNum -= 1;
                                    if (moveNum == 0) {
                                        moveItem = 0;
                                    }
                                }
                            }
                        }
                        if (mousePos[0] >= 62 && mousePos[0] < 102 &&
                            mousePos[1] >= inventory.image.getHeight()+46 &&
                            mousePos[1] < inventory.image.getHeight()+86) {
                            checkBlocks = false;
                            if (mouseClicked2) {
                                mouseNoLongerClicked2 = true;
                                moveItemTemp = ic.ids[3];
                                moveNumTemp = (short)(ic.nums[3]/2);
                                if (moveItem == 0 && ic.nums[3] != 1) {
                                    inventory.removeLocationIC(ic, 3, (short)(ic.nums[3]/2));
                                    moveItem = moveItemTemp;
                                    moveNum = moveNumTemp;
                                }
                            }
                        }
                    }
                }
            }
            if (checkBlocks) {
                if (!(mousePos2[0] < 0 || mousePos2[0] >= WIDTH*BLOCKSIZE ||
                      mousePos2[1] < 0 || mousePos2[1] >= HEIGHT*BLOCKSIZE)) {
                    ux = (int)(mousePos2[0]/BLOCKSIZE);
                    uy = (int)(mousePos2[1]/BLOCKSIZE);
                    if (DEBUG_REACH || Math.sqrt(Math.pow(player.x+player.image.getWidth()-ux*BLOCKSIZE+BLOCKSIZE/2, 2) + Math.pow(player.y+player.image.getHeight()-uy*BLOCKSIZE+BLOCKSIZE/2, 2)) <= 160) {
                        ucx = ux - CHUNKBLOCKS * ((int)(ux/CHUNKBLOCKS));
                        ucy = uy - CHUNKBLOCKS * ((int)(uy/CHUNKBLOCKS));
                        if (blocks[layer][uy][ux] >= 8 && blocks[layer][uy][ux] <= 14 || blocks[layer][uy][ux] == 17 || blocks[layer][uy][ux] == 23 || blocks[layer][uy][ux] >= 80 && blocks[layer][uy][ux] <= 82) {
                            if (ic != null) {
                                if (!ic.type.equals("workbench")) {
                                    machinesx.add(icx);
                                    machinesy.add(icy);
                                    icmatrix[iclayer][icy][icx] = new ItemCollection(ic.type, ic.ids, ic.nums, ic.durs);
                                }
                                if (ic.type.equals("workbench")) {
                                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                                        for (i=0; i<9; i++) {
                                            if (ic.ids[i] != 0) {
                                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                                            }
                                        }
                                    }
                                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                                        for (i=0; i<9; i++) {
                                            if (ic.ids[i] != 0) {
                                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                                            }
                                        }
                                    }
                                }
                                if (ic.type.equals("furnace")) {
                                    icmatrix[iclayer][icy][icx].FUELP = ic.FUELP;
                                    icmatrix[iclayer][icy][icx].SMELTP = ic.SMELTP;
                                    icmatrix[iclayer][icy][icx].F_ON = ic.F_ON;
                                }
                                ic = null;
                            }
                            iclayer = layer;
                            for (int l=0; l<3; l++) {
                                if (blocks[l][uy][ux] == 8) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("workbench")) {
                                        ic = new ItemCollection("workbench", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("workbench", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 9) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("wooden_chest")) {
                                        ic = new ItemCollection("wooden_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("wooden_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 10) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("stone_chest")) {
                                        ic = new ItemCollection("stone_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("stone_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 11) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("copper_chest")) {
                                        ic = new ItemCollection("copper_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("copper_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 12) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("iron_chest")) {
                                        ic = new ItemCollection("iron_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("iron_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 13) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("silver_chest")) {
                                        ic = new ItemCollection("silver_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("silver_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 14) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("gold_chest")) {
                                        ic = new ItemCollection("gold_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("gold_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 80) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("zinc_chest")) {
                                        ic = new ItemCollection("zinc_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("zinc_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 81) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("rhymestone_chest")) {
                                        ic = new ItemCollection("rhymestone_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("rhymestone_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 82) {
                                    short[] tlist1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("obdurite_chest")) {
                                        ic = new ItemCollection("obdurite_chest", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                    }
                                    else {
                                        ic = new ItemCollection("obdurite_chest", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (blocks[l][uy][ux] == 17 || blocks[l][uy][ux] == 23) {
                                    short[] tlist1 = {0, 0, 0, 0};
                                    short[] tlist2 = {0, 0, 0, 0};
                                    short[] tlist3 = {0, 0, 0, 0};
                                    if (icmatrix[l][uy][ux] != null && icmatrix[l][uy][ux].type.equals("furnace")) {
                                        ic = new ItemCollection("furnace", icmatrix[l][uy][ux].ids, icmatrix[l][uy][ux].nums, icmatrix[l][uy][ux].durs);
                                        ic.FUELP = icmatrix[l][uy][ux].FUELP;
                                        ic.SMELTP = icmatrix[l][uy][ux].SMELTP;
                                        ic.F_ON = icmatrix[l][uy][ux].F_ON;
                                        icmatrix[l][uy][ux] = null;
                                    }
                                    else {
                                        ic = new ItemCollection("furnace", tlist1, tlist2, tlist3);
                                    }
                                    icx = ux;
                                    icy = uy;
                                    inventory.renderCollection(ic);
                                    showInv = true;
                                }
                                if (ic != null && blocks[l][uy][ux] != 8) {
                                    for (i=machinesx.size()-1; i>-1; i--) {
                                        if (machinesx.get(i) == icx && machinesy.get(i) == icy) {
                                            machinesx.remove(i);
                                            machinesy.remove(i);
                                        }
                                    }
                                }
                            }
                        }
                        if (blocks[layer][uy][ux] == 15) {
                            if (random.nextInt(2) == 0) {
                                entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*8-4, -3, (short)160, (short)1));
                            }
                            blocks[layer][uy][ux] = 83;
                        }
                        if (mouseClicked2) {
                            mouseNoLongerClicked2 = true;
                            blockTemp = blocks[layer][uy][ux];
                            if (blocks[layer][uy][ux] == 105 || blocks[layer][uy][ux] == 107 || blocks[layer][uy][ux] == 109) {
                                blocks[layer][uy][ux] += 1;
                                addBlockPower(ux, uy);
                                rdrawn[uy][ux] = false;
                            }
                            else if (blocks[layer][uy][ux] == 106 || blocks[layer][uy][ux] == 108 || blocks[layer][uy][ux] == 110) {
                                removeBlockPower(ux, uy, layer);
                                if (wcnct[uy][ux]) {
                                    for (int l=0; l<3; l++) {
                                        if (l != layer) {
                                            rbpRecur(ux, uy, l);
                                        }
                                    }
                                }
                                blocks[layer][uy][ux] -= 1;
                                rdrawn[uy][ux] = false;
                            }
                            if (blocks[layer][uy][ux] >= 94 && blocks[layer][uy][ux] <= 99) {
                                wcnct[uy][ux] = !wcnct[uy][ux];
                                rdrawn[uy][ux] = false;
                                redoBlockPower(ux, uy, layer);
                            }
                            if (blocks[layer][uy][ux] >= 111 && blocks[layer][uy][ux] <= 118) {
                                removeBlockPower(ux, uy, layer);
                                if (blocks[layer][uy][ux] >= 111 && blocks[layer][uy][ux] <= 113 || blocks[layer][uy][ux] >= 115 && blocks[layer][uy][ux] <= 117) {
                                    blocks[layer][uy][ux] += 1;
                                }
                                else {
                                    blocks[layer][uy][ux] -= 3;
                                }
                                blockds[layer] = World.generate2b(blocks[layer], blockds[layer], ux, uy);
                                rdrawn[uy][ux] = false;
                                addAdjacentTilesToPQueueConditionally(ux, uy);
                            }
                            if (blocks[layer][uy][ux] >= 119 && blocks[layer][uy][ux] <= 126) {
                                removeBlockPower(ux, uy, layer);
                                if (blocks[layer][uy][ux] >= 119 && blocks[layer][uy][ux] <= 121 || blocks[layer][uy][ux] >= 123 && blocks[layer][uy][ux] <= 125) {
                                    blocks[layer][uy][ux] += 1;
                                }
                                else {
                                    blocks[layer][uy][ux] -= 3;
                                }
                                blockds[layer] = World.generate2b(blocks[layer], blockds[layer], ux, uy);
                                rdrawn[uy][ux] = false;
                                addAdjacentTilesToPQueueConditionally(ux, uy);
                            }
                            if (blocks[layer][uy][ux] == 127 || blocks[layer][uy][ux] == 129) {
                                blocks[layer][uy][ux] += 1;
                                addBlockPower(ux, uy);
                                rdrawn[uy][ux] = false;
                                print("Srsly?");
                                updatex.add(ux);
                                updatey.add(uy);
                                updatet.add(50);
                                updatel.add(layer);
                            }
                            if (blocks[layer][uy][ux] >= 137 && blocks[layer][uy][ux] <= 168) {
                                if (blocks[layer][uy][ux] >= 137 && blocks[layer][uy][ux] <= 139 || blocks[layer][uy][ux] >= 141 && blocks[layer][uy][ux] <= 143 ||
                                    blocks[layer][uy][ux] >= 145 && blocks[layer][uy][ux] <= 147 || blocks[layer][uy][ux] >= 149 && blocks[layer][uy][ux] <= 151 ||
                                    blocks[layer][uy][ux] >= 153 && blocks[layer][uy][ux] <= 155 || blocks[layer][uy][ux] >= 157 && blocks[layer][uy][ux] <= 159 ||
                                    blocks[layer][uy][ux] >= 161 && blocks[layer][uy][ux] <= 163 || blocks[layer][uy][ux] >= 165 && blocks[layer][uy][ux] <= 167) {
                                    blocks[layer][uy][ux] += 1;
                                }
                                else {
                                    blocks[layer][uy][ux] -= 3;
                                }
                                blockds[layer] = World.generate2b(blocks[layer], blockds[layer], ux, uy);
                                rdrawn[uy][ux] = false;
                                redoBlockPower(ux, uy, layer);
                            }
                        }
                    }
                }
            }
            if (mouseNoLongerClicked2) {
                mouseClicked2 = false;
            }
        }
        else {
            mouseClicked2 = true;
        }
        if (showTool) {
            toolAngle += toolSpeed;
            if (toolAngle >= 7.8) {
                toolAngle = 4.8;
                showTool = false;
            }
        }
        if (!DEBUG_INVINCIBLE && player.y/16 > HEIGHT + 10) {
            vc += 1;
            if (vc >= 1/(Math.pow(1.001, player.y/16 - HEIGHT - 10) - 1.0)) {
                player.damage(1, false, inventory);
                vc = 0;
            }
        }
        else {
            vc = 0;
        }
        for (i=entities.size()-1; i>=0; i--) {
            if (entities.get(i).getNewMob() != null) {
                entities.add(entities.get(i).getNewMob());
            }
            if (entities.get(i).update(blocks[1], player, u, v)) {
                entities.remove(i);
            }
            else if (player.rect.intersects(entities.get(i).getRect())) {
                if (entities.get(i).getName() != null) {
                    if (immune <= 0) {
                        if (!DEBUG_INVINCIBLE) {
                            player.damage(entities.get(i).getAtk(), true, inventory);
                        }
                        rgnc1 = 750;
                        immune = 40;
                        if (player.x + player.width/2 < entities.get(i).getX() + entities.get(i).getWidth()/2) {
                            player.vx -= 8;
                        }
                        else {
                            player.vx += 8;
                        }
                        player.vy -= 3.5;
                    }
                }
                else if (entities.get(i).getMdelay() <= 0) {
                    n = inventory.addItem(entities.get(i).getId(), entities.get(i).getNum(), entities.get(i).getDur());
                    if (n != 0) {
                        entities.add(new Entity(entities.get(i).getX(), entities.get(i).getY(), entities.get(i).getVx(), entities.get(i).getVy(), entities.get(i).getId(), (short)(entities.get(i).getNum() - n), entities.get(i).getDur()));
                    }
                    entities.remove(i);
                }
            }
        }
        if (player.hp <= 0) {
            for (j=0; j<40; j++) {
                if (inventory.ids[j] != 0) {
                    entities.add(new Entity(player.x, player.y, -1, random.nextDouble()*6-3, inventory.ids[j], inventory.nums[j], inventory.durs[j]));
                    inventory.removeLocation(j, inventory.nums[j]);
                }
            }
            if (ic != null) {
                if (!ic.type.equals("workbench")) {
                    machinesx.add(icx);
                    machinesy.add(icy);
                    icmatrix[iclayer][icy][icx] = new ItemCollection(ic.type, ic.ids, ic.nums, ic.durs);
                }
                if (ic.type.equals("workbench")) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        for (i=0; i<9; i++) {
                            if (ic.ids[i] != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                            }
                        }
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        for (i=0; i<9; i++) {
                            if (ic.ids[i] != 0) {
                                entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                            }
                        }
                    }
                }
                if (ic.type.equals("furnace")) {
                    icmatrix[iclayer][icy][icx].FUELP = ic.FUELP;
                    icmatrix[iclayer][icy][icx].SMELTP = ic.SMELTP;
                    icmatrix[iclayer][icy][icx].F_ON = ic.F_ON;
                }
                ic = null;
            }
            else {
                if (showInv) {
                    for (i=0; i<4; i++) {
                        if (cic.ids[i] != 0) {
                            if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                                entities.add(new Entity(player.x, player.y, 2, -2, cic.ids[i], cic.nums[i], cic.durs[i], 75));
                            }
                            if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                                entities.add(new Entity(player.x, player.y, -2, -2, cic.ids[i], cic.nums[i], cic.durs[i], 75));
                            }
                            inventory.removeLocationIC(cic, i, cic.nums[i]);
                        }
                    }
                }
                showInv = !showInv;
            }
            if (moveItem != 0) {
                if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                    entities.add(new Entity(player.x, player.y, 2, -2, moveItem, moveNum, moveDur, 75));
                }
                if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                    entities.add(new Entity(player.x, player.y, -2, -2, moveItem, moveNum, moveDur, 75));
                }
                moveItem = 0;
                moveNum = 0;
            }
            for (i=0; i<4; i++) {
                if (armor.ids[i] != 0) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        entities.add(new Entity(player.x, player.y, 2, -2, armor.ids[i], armor.nums[i], armor.durs[i], 75));
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        entities.add(new Entity(player.x, player.y, -2, -2, armor.ids[i], armor.nums[i], armor.durs[i], 75));
                    }
                    inventory.removeLocationIC(armor, i, armor.nums[i]);
                }
            }
            player.x = WIDTH*0.5*BLOCKSIZE;
            player.y = 45;
            player.vx = 0;
            player.vy = 0;
            player.hp = player.thp;
            tool = null;
            showTool = false;
        }
        if (showTool) {
            if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                tp1 = new Point((int)(player.x + player.width/2 + 6), (int)(player.y + player.height/2));
                tp2 = new Point((int)(player.x + player.width/2 + 6 + tool.getWidth()*2*Math.cos(toolAngle) + tool.getHeight()*2*Math.sin(toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*2*Math.sin(toolAngle) - tool.getHeight()*2*Math.cos(toolAngle)));
                tp3 = new Point((int)(player.x + player.width/2 + 6 + tool.getWidth()*1*Math.cos(toolAngle) + tool.getHeight()*1*Math.sin(toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*1*Math.sin(toolAngle) - tool.getHeight()*1*Math.cos(toolAngle)));
                tp4 = new Point((int)(player.x + player.width/2 + 6 + tool.getWidth()*0.5*Math.cos(toolAngle) + tool.getHeight()*0.5*Math.sin(toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*0.5*Math.sin(toolAngle) - tool.getHeight()*0.5*Math.cos(toolAngle)));
                tp5 = new Point((int)(player.x + player.width/2 + 6 + tool.getWidth()*1.5*Math.cos(toolAngle) + tool.getHeight()*1.5*Math.sin(toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*1.5*Math.sin(toolAngle) - tool.getHeight()*1.5*Math.cos(toolAngle)));
            }
            if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                tp1 = new Point((int)(player.x + player.width/2 - 6), (int)(player.y + player.height/2));
                tp2 = new Point((int)(player.x + player.width/2 - 6 + tool.getWidth()*2*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*2*Math.sin((Math.PI * 1.5) - toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*2*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*2*Math.cos((Math.PI * 1.5) - toolAngle)));
                tp3 = new Point((int)(player.x + player.width/2 - 6 + tool.getWidth()*1*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*1*Math.sin((Math.PI * 1.5) - toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*1*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*1*Math.cos((Math.PI * 1.5) - toolAngle)));
                tp4 = new Point((int)(player.x + player.width/2 - 6 + tool.getWidth()*0.5*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*0.5*Math.sin((Math.PI * 1.5) - toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*0.5*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*0.5*Math.cos((Math.PI * 1.5) - toolAngle)));
                tp5 = new Point((int)(player.x + player.width/2 - 6 + tool.getWidth()*1.5*Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight()*1.5*Math.sin((Math.PI * 1.5) - toolAngle)),
                                (int)(player.y + player.height/2 + tool.getWidth()*1.5*Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight()*1.5*Math.cos((Math.PI * 1.5) - toolAngle)));
            }
            for (i=entities.size()-1; i>=0; i--) {
                if (entities.get(i).getName() != null && !entities.get(i).isNohit() && showTool && (entities.get(i).getRect().contains(tp1) || entities.get(i).getRect().contains(tp2) || entities.get(i).getRect().contains(tp3) || entities.get(i).getRect().contains(tp4) || entities.get(i).getRect().contains(tp5)) && (!entities.get(i).getName().equals("bee") || random.nextInt(4) == 0)) {
                    if (entities.get(i).hit(TOOLDAMAGE.get(inventory.tool()), player)) {
                        ArrayList<Short> dropList = entities.get(i).drops();
                        for (j=0; j<dropList.size(); j++) {
                            s = dropList.get(j);
                            entities.add(new Entity(entities.get(i).getX(), entities.get(i).getY(), random.nextInt(4)-2, -1, s, (short)1));
                        }
                        entities.remove(i);
                    }
                    if (!Arrays.asList(TOOL_LIST).contains(inventory.ids[inventory.selection])) {
                        inventory.durs[inventory.selection] -= 1;
                    }
                    else {
                        inventory.durs[inventory.selection] -= 2;
                    }
                    if (inventory.durs[inventory.selection] <= 0) {
                        inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                    }
                }
            }
        }

        Rectangle rect;
        int bx1, bx2, by1, by2;
        for (i=-1; i<entities.size(); i++) {
            if (i == -1) {
                rect = player.rect;
                width = player.width;
                height = player.height;
                p = player.x;
                q = player.y;
            }
            else {
                rect = entities.get(i).getRect();
                width = entities.get(i).getWidth();
                height = entities.get(i).getHeight();
                p = entities.get(i).getX();
                q = entities.get(i).getY();
            }
            bx1 = (int)p/BLOCKSIZE; by1 = (int)q/BLOCKSIZE;
            bx2 = (int)(p+width)/BLOCKSIZE; by2 = (int)(q+height)/BLOCKSIZE;

            bx1 = Math.max(0, bx1); by1 = Math.max(0, by1);
            bx2 = Math.min(blocks[0].length - 1, bx2); by2 = Math.min(blocks.length - 1, by2);

            for (x=bx1; x<=bx2; x++) {
                for (y=by1; y<=by2; y++) {
                    if (blocks[layer][y][x] >= 131 && blocks[layer][y][x] <= 136 && (i == -1 || blocks[layer][y][x] <= 134 && (x != -1 && entities.get(i).getName() != null || blocks[layer][y][x] <= 132))) {
                        if (blocks[layer][y][x] == 131 || blocks[layer][y][x] == 133 || blocks[layer][y][x] == 135) {
                            blocks[layer][y][x] += 1;
                            rdrawn[y][x] = false;
                            addBlockPower(x, y);
                            print("Srsly?");
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
        immune -= 1;
    }

    public boolean hasOpenSpace(int x, int y, int l) {
        try {
            return (blocks[l][y-1][x-1] == 0 || !BLOCK_CDS[blocks[l][y-1][x-1]] ||
                    blocks[l][y-1][x] == 0 || !BLOCK_CDS[blocks[l][y-1][x]] ||
                    blocks[l][y-1][x+1] == 0 || !BLOCK_CDS[blocks[l][y-1][x+1]] ||
                    blocks[l][y][x-1] == 0 || !BLOCK_CDS[blocks[l][y][x-1]] ||
                    blocks[l][y][x+1] == 0 || !BLOCK_CDS[blocks[l][y][x+1]] ||
                    blocks[l][y+1][x-1] == 0 || !BLOCK_CDS[blocks[l][y+1][x-1]] ||
                    blocks[l][y+1][x] == 0 || !BLOCK_CDS[blocks[l][y+1][x]] ||
                    blocks[l][y+1][x+1] == 0 || !BLOCK_CDS[blocks[l][y+1][x+1]]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public String checkBiome(int x, int y) {
        int desert = 0;
        int frost = 0;
        int swamp = 0;
        int jungle = 0;
        int cavern = 0;
        for (x2=x-15; x2<x+16; x2++) {
            for (y2=y-15; y2<y+16; y2++) {
                if (x2+u >= 0 && x2+u < WIDTH && y2+v >= 0 && y2+v < HEIGHT) {
                    if (blocks[1][y2+v][x2+u] == 45 || blocks[1][y2+v][x2+u] == 76) {
                        desert += 1;
                    }
                    else if (blocks[1][y2+v][x2+u] != 0) {
                        desert -= 1;
                    }
                    if (blocks[1][y2+v][x2+u] == 1 || blocks[1][y2+v][x2+u] == 72 || blocks[1][y2+v][x2+u] == 73) {
                        jungle += 1;
                    }
                    else if (blocks[1][y2+v][x2+u] != 0) {
                        jungle -= 1;
                    }
                    if (blocks[1][y2+v][x2+u] == 74 || blocks[1][y2+v][x2+u] == 75) {
                        swamp += 1;
                    }
                    else if (blocks[1][y2+v][x2+u] != 0) {
                        swamp -= 1;
                    }
                    if (blocks[1][y2+v][x2+u] == 46) {
                        frost += 1;
                    }
                    else if (blocks[1][y2+v][x2+u] != 0) {
                        frost -= 1;
                    }
                    if (blockbgs[y2+v][x2+u] == 0) {
                        cavern += 1;
                    }
                    if (blocks[1][y2+v][x2+u] == 1 || blocks[1][y2+v][x2+u] == 2) {
                        cavern += 1;
                    }
                    else {
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
        if (DEBUG_INSTAMINE || mining >= DURABILITY.get(inventory.tool()).get(blocks[layer][uy][ux])) {
            if (blocks[0][uy][ux] == 30) {
                blocks[0][uy][ux] = 0;
                for (uly=uy-1; uly<uy+2; uly++) {
                    for (ulx=ux-1; ulx<ux+2; ulx++) {
                        blockdns[uly][ulx] = (byte)random.nextInt(5);
                    }
                }
                for (uly=uy; uly<uy+3; uly++) {
                    for (ulx=ux-1; ulx<ux+2; ulx++) {
                        drawn[uly][ulx] = false;
                    }
                }
            }
            if (blocks[0][uy+1][ux] == 30) {
                blocks[0][uy+1][ux] = 0;
                for (uly=uy; uly<uy+3; uly++) {
                    for (ulx=ux-1; ulx<ux+2; ulx++) {
                        blockdns[uly][ulx] = (byte)random.nextInt(5);
                    }
                }
                for (uly=uy; uly<uy+3; uly++) {
                    for (ulx=ux-1; ulx<ux+2; ulx++) {
                        drawn[uly][ulx] = false;
                    }
                }
            }
            if (blocks[layer][uy][ux] >= 8 && blocks[layer][uy][ux] <= 14 || blocks[layer][uy][ux] == 17 || blocks[layer][uy][ux] == 23 || blocks[layer][uy][ux] >= 80 && blocks[layer][uy][ux] <= 82) {
                if (ic != null) {
                    for (i=0; i<ic.ids.length; i++) {
                        if (ic.ids[i] != 0 && !(ic.type.equals("furnace") && i == 1)) {
                            entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, ic.ids[i], ic.nums[i], ic.durs[i]));
                        }
                    }
                }
                if (icmatrix[layer][uy][ux] != null) {
                    for (i=0; i<icmatrix[layer][uy][ux].ids.length; i++) {
                        if (icmatrix[layer][uy][ux].ids[i] != 0 && !(icmatrix[layer][uy][ux].type.equals("furnace") && i == 1)) {
                            entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, icmatrix[layer][uy][ux].ids[i], icmatrix[layer][uy][ux].nums[i], icmatrix[layer][uy][ux].durs[i]));
                        }
                    }
                    icmatrix[layer][uy][ux] = null;
                }
                ic = null;
                for (i=0; i<machinesx.size(); i++) {
                    if (machinesx.get(i) == ux && machinesy.get(i) == uy) {
                        machinesx.remove(i);
                        machinesy.remove(i);
                        break;
                    }
                }
            }
            if (blocks[layer][uy][ux] != 0 && BLOCKDROPS.get(blocks[layer][uy][ux]) != 0) {
                entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, BLOCKDROPS.get(blocks[layer][uy][ux]), (short)1));
            }
            t = 0;
            switch (blocks[layer][uy][ux]) {
            case 48: t = 77; n = random.nextInt(4)-2; break;
            case 49: t = 77; n = random.nextInt(2); break;
            case 50: t = 77; n = random.nextInt(3)+1; break;
            case 51: t = 79; n = random.nextInt(4)-2; break;
            case 52: t = 79; n = random.nextInt(2); break;
            case 53: t = 79; n = random.nextInt(3)+1; break;
            case 54: t = 81; n = random.nextInt(4)-2; break;
            case 55: t = 81; n = random.nextInt(2); break;
            case 56: t = 81; n = random.nextInt(3)+1; break;
            case 57: t = 83; n = random.nextInt(4)-2; break;
            case 58: t = 83; n = random.nextInt(2); break;
            case 59: t = 83; n = random.nextInt(3)+1; break;
            case 60: t = 85; n = random.nextInt(4)-2; break;
            case 61: t = 85; n = random.nextInt(2); break;
            case 62: t = 85; n = random.nextInt(3)+1; break;
            case 63: t = 87; n = random.nextInt(4)-2; break;
            case 64: t = 87; n = random.nextInt(2); break;
            case 65: t = 87; n = random.nextInt(3)+1; break;
            case 66: t = 89; n = random.nextInt(4)-2; break;
            case 67: t = 89; n = random.nextInt(2); break;
            case 68: t = 89; n = random.nextInt(3)+1; break;
            case 69: t = 91; n = random.nextInt(4)-2; break;
            case 70: t = 91; n = random.nextInt(2); break;
            case 71: t = 91; n = random.nextInt(3)+1; break;
            case 77: t = 95; n = random.nextInt(4)-2; break;
            case 78: t = 95; n = random.nextInt(2); break;
            case 79: t = 95; n = random.nextInt(3)+1; break;
            default: break;
            }
            if (t != 0) {
                for (i=0; i<Math.max(1, n); i++) {
                    entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, (short)t, (short)1));
                }
            }
            removeBlockLighting(ux, uy);
            blockTemp = blocks[layer][uy][ux];
            blocks[layer][uy][ux] = 0;
            if (blockTemp >= 94 && blockTemp <= 99) {
                redoBlockPower(ux, uy, layer);
            }
            if (POWERS[blockTemp]) {
                removeBlockPower(ux, uy, layer);
            }
            if (L_TRANS[blockTemp]) {
                addSunLighting(ux, uy);
                redoBlockLighting(ux, uy);
            }
            addSunLighting(ux, uy);
            blockds[layer] = World.generate2b(blocks[layer], blockds[layer], ux, uy);
            for (uly=uy-1; uly<uy+2; uly++) {
                for (ulx=ux-1; ulx<ux+2; ulx++) {
                    blockdns[uly][ulx] = (byte)random.nextInt(5);
                }
            }
            for (uly=uy-1; uly<uy+2; uly++) {
                for (ulx=ux-1; ulx<ux+2; ulx++) {
                    drawn[uly][ulx] = false;
                }
            }
            for (uly=uy-4; uly<uy+5; uly++) {
                for (ulx=ux-4; ulx<ux+5; ulx++) {
                    for (int l=0; l<3; l+=2) {
                        if (uly >= 0 && uly < HEIGHT && blocks[l][uly][ulx] == 16) {
                            keepLeaf = false;
                            for (uly2=uly-4; uly2<uly+5; uly2++) {
                                for (ulx2=ulx-4; ulx2<ulx+5; ulx2++) {
                                    if (uly2 >= 0 && uly2 < HEIGHT && (blocks[1][uly2][ulx2] == 15 || blocks[1][uly2][ulx2] == 83)) {
                                        keepLeaf = true;
                                        break;
                                    }
                                }
                                if (keepLeaf) break;
                            }
                            if (!keepLeaf) {
                                blocks[l][uly][ulx] = 0;
                                blockds[l] = World.generate2b(blocks[l], blockds[l], ulx, uly);
                                for (uly2=uly-1; uly2<uly+2; uly2++) {
                                    for (ulx2=ulx-1; ulx2<ulx+2; ulx2++) {
                                        drawn[uly2][ulx2] = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            while (true) {
                if (TORCHESR.get(ITEMBLOCKS.get(BLOCKDROPS.get(blocks[layer][uy][ux-1]))) != null && TORCHESR.get(ITEMBLOCKS.get(BLOCKDROPS.get(blocks[layer][uy][ux-1]))) == blocks[layer][uy][ux-1] || BLOCKDROPS.get(blocks[layer][uy][ux-1]) != null && (BLOCKDROPS.get(blocks[layer][uy][ux-1]) == 178 || BLOCKDROPS.get(blocks[layer][uy][ux-1]) == 182)) {
                    entities.add(new Entity((ux-1)*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, BLOCKDROPS.get(blocks[layer][uy][ux-1]), (short)1));
                    removeBlockLighting(ux-1, uy);
                    if (layer == 1) {
                        addSunLighting(ux-1, uy);
                    }
                    blockTemp = blocks[layer][uy][ux-1];
                    blocks[layer][uy][ux-1] = 0;
                    if (blockTemp >= 94 && blockTemp <= 99) {
                        redoBlockPower(ux, uy, layer);
                    }
                    if (POWERS[blockTemp]) {
                        removeBlockPower(ux, uy, layer);
                    }
                    drawn[uy][ux-1] = false;
                }
                if (TORCHESL.get(ITEMBLOCKS.get(BLOCKDROPS.get(blocks[layer][uy][ux+1]))) != null && TORCHESL.get(ITEMBLOCKS.get(BLOCKDROPS.get(blocks[layer][uy][ux+1]))) == blocks[layer][uy][ux+1] || BLOCKDROPS.get(blocks[layer][uy][ux+1]) != null && (BLOCKDROPS.get(blocks[layer][uy][ux+1]) == 178 || BLOCKDROPS.get(blocks[layer][uy][ux+1]) == 182)) {
                    entities.add(new Entity((ux+1)*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, BLOCKDROPS.get(blocks[layer][uy][ux+1]), (short)1));
                    removeBlockLighting(ux+1, uy);
                    if (layer == 1) {
                        addSunLighting(ux+1, uy);
                    }
                    blockTemp = blocks[layer][uy][ux+1];
                    blocks[layer][uy][ux+1] = 0;
                    if (blockTemp >= 94 && blockTemp <= 99) {
                        redoBlockPower(ux, uy, layer);
                    }
                    if (POWERS[blockTemp]) {
                        removeBlockPower(ux, uy, layer);
                    }
                    drawn[uy][ux+1] = false;
                }
                uy -= 1;
                if (uy == -1 || !GSUPPORT.get(blocks[layer][uy][ux])) {
                    addSunLighting(ux, uy);
                    break;
                }
                if (BLOCKDROPS.get(blocks[layer][uy][ux]) != 0) {
                    entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, BLOCKDROPS.get(blocks[layer][uy][ux]), (short)1));
                }
                t = 0;
                switch (blocks[layer][uy][ux]) {
                case 48: t = 77; n = random.nextInt(4)-2; break;
                case 49: t = 77; n = random.nextInt(2); break;
                case 50: t = 77; n = random.nextInt(3)+1; break;
                case 51: t = 79; n = random.nextInt(4)-2; break;
                case 52: t = 79; n = random.nextInt(2); break;
                case 53: t = 79; n = random.nextInt(3)+1; break;
                case 54: t = 81; n = random.nextInt(4)-2; break;
                case 55: t = 81; n = random.nextInt(2); break;
                case 56: t = 81; n = random.nextInt(3)+1; break;
                case 57: t = 83; n = random.nextInt(4)-2; break;
                case 58: t = 83; n = random.nextInt(2); break;
                case 59: t = 83; n = random.nextInt(3)+1; break;
                case 60: t = 85; n = random.nextInt(4)-2; break;
                case 61: t = 85; n = random.nextInt(2); break;
                case 62: t = 85; n = random.nextInt(3)+1; break;
                case 63: t = 87; n = random.nextInt(4)-2; break;
                case 64: t = 87; n = random.nextInt(2); break;
                case 65: t = 87; n = random.nextInt(3)+1; break;
                case 66: t = 89; n = random.nextInt(4)-2; break;
                case 67: t = 89; n = random.nextInt(2); break;
                case 68: t = 89; n = random.nextInt(3)+1; break;
                case 69: t = 91; n = random.nextInt(4)-2; break;
                case 70: t = 91; n = random.nextInt(2); break;
                case 71: t = 91; n = random.nextInt(3)+1; break;
                case 77: t = 95; n = random.nextInt(4)-2; break;
                case 78: t = 95; n = random.nextInt(2); break;
                case 79: t = 95; n = random.nextInt(3)+1; break;
                default: break;
                }
                if (t != 0) {
                    for (i=0; i<Math.max(1, n); i++) {
                        entities.add(new Entity(ux*BLOCKSIZE, uy*BLOCKSIZE, random.nextDouble()*4-2, -2, (short)t, (short)1));
                    }
                }
                removeBlockLighting(ux, uy);
                blockTemp = blocks[layer][uy][ux];
                blocks[layer][uy][ux] = 0;
                if (blockTemp >= 94 && blockTemp <= 99) {
                    redoBlockPower(ux, uy, layer);
                }
                if (POWERS[blockTemp]) {
                    removeBlockPower(ux, uy, layer);
                }
                if (L_TRANS[blockTemp]) {
                    addSunLighting(ux, uy);
                    redoBlockLighting(ux, uy);
                }
                addSunLighting(ux, uy);
                blockds[layer] = World.generate2b(blocks[layer], blockds[layer], ux, uy);
                for (uly=uy-1; uly<uy+2; uly++) {
                    for (ulx=ux-1; ulx<ux+2; ulx++) {
                        blockdns[uly][ulx] = (byte)random.nextInt(5);
                    }
                }
                for (uly=uy-1; uly<uy+2; uly++) {
                    for (ulx=ux-1; ulx<ux+2; ulx++) {
                        drawn[uly][ulx] = false;
                    }
                }
                for (uly=uy-4; uly<uy+5; uly++) {
                    for (ulx=ux-4; ulx<ux+5; ulx++) {
                        for (int l=0; l<3; l+=2) {
                            if (uly >= 0 && uly < HEIGHT && blocks[l][uly][ulx] == 16) {
                                keepLeaf = false;
                                for (uly2=uly-4; uly2<uly+5; uly2++) {
                                    for (ulx2=ulx-4; ulx2<ulx+5; ulx2++) {
                                        if (uly2 >= 0 && uly2 < HEIGHT && (blocks[1][uly2][ulx2] == 15 || blocks[1][uly2][ulx2] == 83)) {
                                            keepLeaf = true;
                                            break;
                                        }
                                    }
                                    if (keepLeaf) break;
                                }
                                if (!keepLeaf) {
                                    blocks[l][uly][ulx] = 0;
                                    blockds[l] = World.generate2b(blocks[l], blockds[l], ulx, uly);
                                    for (uly2=uly-1; uly2<uly+2; uly2++) {
                                        for (ulx2=ulx-1; ulx2<ulx+2; ulx2++) {
                                            drawn[uly2][ulx2] = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            mining = 0;
        }
    }

    public void updateEnvironment() {
        timeOfDay += 1.2*DEBUG_ACCEL;
        for (i=cloudsx.size()-1; i>-1; i--) {
            cloudsx.set(i, cloudsx.get(i) + cloudsv.get(i));
            if (cloudsx.get(i) < -250 || cloudsx.get(i) > getWidth() + 250) {
                cloudsx.remove(i);
                cloudsy.remove(i);
                cloudsv.remove(i);
                cloudsn.remove(i);
            }
        }
        if (random.nextInt((int)(1500/DEBUG_ACCEL)) == 0) {
            cloudsn.add(random.nextInt(1));
            cloud = clouds[cloudsn.get(cloudsn.size()-1)];
            if (random.nextInt(2) == 0) {
                cloudsx.add((double)(-cloud.getWidth()*2));
                cloudsv.add((double)(0.1*DEBUG_ACCEL));
            }
            else {
                cloudsx.add((double)getWidth());
                cloudsv.add((double)(-0.1*DEBUG_ACCEL));
            }
            cloudsy.add(random.nextDouble()*(getHeight()-cloud.getHeight())+cloud.getHeight());
        }
    }

    public void addBlockLighting(int ux, int uy) {
        n = findNonLayeredBlockLightSource(ux, uy);
        if (n != 0) {
            addTileToZQueue(ux, uy);
            lights[uy][ux] = Math.max(lights[uy][ux], n);
            lsources[uy][ux] = true;
            addTileToQueue(ux, uy);
        }
    }

    public void addBlockPower(int ux, int uy) {
        if (POWERS[blocks[1][uy][ux]]) {
            if (blocks[1][uy][ux] >= 137 && blocks[1][uy][ux] <= 168) {
                print("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(DDELAY.get(blocks[1][uy][ux]));
                updatel.add(1);
            }
            else {
                addTileToPZQueue(ux, uy);
                power[1][uy][ux] = (float)5;
                if (CONDUCTS[blocks[1][uy][ux]] >= 0 && wcnct[uy][ux]) {
                    if (RECEIVES[blocks[0][uy][ux]]) {
                        power[0][uy][ux] = power[1][uy][ux] - (float) CONDUCTS[blocks[1][uy][ux]];
                    }
                    if (RECEIVES[blocks[2][uy][ux]]) {
                        power[2][uy][ux] = power[1][uy][ux] - (float) CONDUCTS[blocks[1][uy][ux]];
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
        if (POWERS[blocks[0][uy][ux]]) {
            if (blocks[0][uy][ux] >= 137 && blocks[0][uy][ux] <= 168) {
                print("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(DDELAY.get(blocks[0][uy][ux]));
                updatel.add(0);
            }
            else {
                addTileToPZQueue(ux, uy);
                power[0][uy][ux] = (float)5;
                if (CONDUCTS[blocks[0][uy][ux]] >= 0 && wcnct[uy][ux]) {
                    if (RECEIVES[blocks[1][uy][ux]]) {
                        power[1][uy][ux] = power[0][uy][ux] - (float) CONDUCTS[blocks[0][uy][ux]];
                    }
                    if (RECEIVES[blocks[2][uy][ux]]) {
                        power[2][uy][ux] = power[0][uy][ux] - (float) CONDUCTS[blocks[0][uy][ux]];
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
        if (POWERS[blocks[2][uy][ux]]) {
            if (blocks[2][uy][ux] >= 137 && blocks[2][uy][ux] <= 168) {
                print("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(DDELAY.get(blocks[2][uy][ux]));
                updatel.add(2);
            }
            else {
                addTileToPZQueue(ux, uy);
                power[2][uy][ux] = (float)5;
                if (CONDUCTS[blocks[2][uy][ux]] >= 0 && wcnct[uy][ux]) {
                    if (RECEIVES[blocks[0][uy][ux]]) {
                        power[0][uy][ux] = power[2][uy][ux] - (float) CONDUCTS[blocks[2][uy][ux]];
                    }
                    if (RECEIVES[blocks[1][uy][ux]]) {
                        power[1][uy][ux] = power[2][uy][ux] - (float) CONDUCTS[blocks[2][uy][ux]];
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
    }

    public void removeBlockLighting(int ux, int uy) {
        removeBlockLighting(ux, uy, layer);
    }

    public void removeBlockLighting(int ux, int uy, int layer) {
        n = findNonLayeredBlockLightSource(ux, uy);
        if (n != 0) {
            lsources[uy][ux] = isNonLayeredBlockLightSource(ux, uy, layer);
            for (axl=-n; axl<n+1; axl++) {
                for (ayl=-n; ayl<n+1; ayl++) {
                    if (Math.abs(axl)+Math.abs(ayl) <= n && uy+ayl >= 0 && uy+ayl < HEIGHT && lights[uy+ayl][ux+axl] != 0) {
                        addTileToZQueue(ux+axl, uy+ayl);
                        lights[uy+ayl][ux+axl] = (float)0;
                    }
                }
            }
            for (axl=-n-BRIGHTEST; axl<n+1+BRIGHTEST; axl++) {
                for (ayl=-n-BRIGHTEST; ayl<n+1+BRIGHTEST; ayl++) {
                    if (Math.abs(axl)+Math.abs(ayl) <= n+BRIGHTEST && uy+ayl >= 0 && uy+ayl < HEIGHT) {
                        if (lsources[uy+ayl][ux+axl]) {
                            addTileToQueue(ux+axl, uy+ayl);
                        }
                    }
                }
            }
        }
    }

    public void rbpRecur(int ux, int uy, int lyr) {
        arbprd[lyr][uy][ux] = true;
        print("[rbpR] " + ux + " " + uy);
        addTileToPZQueue(ux, uy);
        boolean[] remember = {false, false, false, false};
        int ax3, ay3;
        for (int ir=0; ir<4; ir++) {
            ax3 = ux+cl[ir][0];
            ay3 = uy+cl[ir][1];
            if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                if (power[lyr][ay3][ax3] != 0 && !(power[lyr][ay3][ax3] == power[lyr][uy][ux] - CONDUCTS[blocks[lyr][uy][ux]]) &&
                 (!(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 || blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126) ||
                  !(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux > ax3 && blocks[lyr][ay3][ax3] != 111 && blocks[lyr][ay3][ax3] != 115 ||
                    blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy > ay3 && blocks[lyr][ay3][ax3] != 112 && blocks[lyr][ay3][ax3] != 116 ||
                    blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux < ax3 && blocks[lyr][ay3][ax3] != 113 && blocks[lyr][ay3][ax3] != 117 ||
                    blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy < ay3 && blocks[lyr][ay3][ax3] != 114 && blocks[lyr][ay3][ax3] != 118) &&
                  !(blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux > ax3 && blocks[lyr][ay3][ax3] != 119 && blocks[lyr][ay3][ax3] != 123 ||
                    blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy > ay3 && blocks[lyr][ay3][ax3] != 120 && blocks[lyr][ay3][ax3] != 124 ||
                    blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux < ax3 && blocks[lyr][ay3][ax3] != 121 && blocks[lyr][ay3][ax3] != 125 ||
                    blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy < ay3 && blocks[lyr][ay3][ax3] != 122 && blocks[lyr][ay3][ax3] != 126) &&
                  !(blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux > ax3 && blocks[lyr][ay3][ax3] != 137 && blocks[lyr][ay3][ax3] != 141 && blocks[lyr][ay3][ax3] != 145 && blocks[lyr][ay3][ax3] != 149 && blocks[lyr][ay3][ax3] != 153 && blocks[lyr][ay3][ax3] != 157 && blocks[lyr][ay3][ax3] != 161 && blocks[lyr][ay3][ax3] != 165 ||
                    blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy > ay3 && blocks[lyr][ay3][ax3] != 138 && blocks[lyr][ay3][ax3] != 142 && blocks[lyr][ay3][ax3] != 146 && blocks[lyr][ay3][ax3] != 150 && blocks[lyr][ay3][ax3] != 154 && blocks[lyr][ay3][ax3] != 158 && blocks[lyr][ay3][ax3] != 162 && blocks[lyr][ay3][ax3] != 166 ||
                    blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux < ax3 && blocks[lyr][ay3][ax3] != 139 && blocks[lyr][ay3][ax3] != 143 && blocks[lyr][ay3][ax3] != 147 && blocks[lyr][ay3][ax3] != 151 && blocks[lyr][ay3][ax3] != 155 && blocks[lyr][ay3][ax3] != 159 && blocks[lyr][ay3][ax3] != 163 && blocks[lyr][ay3][ax3] != 167 ||
                    blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy < ay3 && blocks[lyr][ay3][ax3] != 140 && blocks[lyr][ay3][ax3] != 144 && blocks[lyr][ay3][ax3] != 148 && blocks[lyr][ay3][ax3] != 152 && blocks[lyr][ay3][ax3] != 156 && blocks[lyr][ay3][ax3] != 160 && blocks[lyr][ay3][ax3] != 164 && blocks[lyr][ay3][ax3] != 168))) {
                    print("Added tile " + ax3 + " " + ay3 + " to PQueue.");
                    addTileToPQueue(ax3, ay3);
                    remember[ir] = true;
                }
            }
        }
        for (int ir=0; ir<4; ir++) {
            print("[liek srsly man?] " + ir);
            ax3 = ux+cl[ir][0];
            ay3 = uy+cl[ir][1];
            print("[rpbRecur2] " + ax3 + " " + ay3 + " " + power[lyr][ay3][ax3]);
            if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                print("[rbpRecur] " + power[lyr][ay3][ax3] + " " + power[lyr][uy][ux] + " " + CONDUCTS[blocks[lyr][uy][ux]]);
                if ((power[lyr][ay3][ax3] == power[lyr][uy][ux] - CONDUCTS[blocks[lyr][uy][ux]]) &&
                 (!(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 || blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126) ||
                  !(blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && ux < ax3 && blocks[lyr][uy][ux] != 111 && blocks[lyr][uy][ux] != 115 ||
                    blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && uy < ay3 && blocks[lyr][uy][ux] != 112 && blocks[lyr][uy][ux] != 116 ||
                    blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && ux > ax3 && blocks[lyr][uy][ux] != 113 && blocks[lyr][uy][ux] != 117 ||
                    blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && uy > ay3 && blocks[lyr][uy][ux] != 114 && blocks[lyr][uy][ux] != 118) &&
                  !(blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && ux < ax3 && blocks[lyr][uy][ux] != 119 && blocks[lyr][uy][ux] != 123 ||
                    blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && uy < ay3 && blocks[lyr][uy][ux] != 120 && blocks[lyr][uy][ux] != 124 ||
                    blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && ux > ax3 && blocks[lyr][uy][ux] != 121 && blocks[lyr][uy][ux] != 125 ||
                    blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && uy > ay3 && blocks[lyr][uy][ux] != 122 && blocks[lyr][uy][ux] != 126) &&
                  !(blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && ux < ax3 && blocks[lyr][uy][ux] != 137 && blocks[lyr][uy][ux] != 141 && blocks[lyr][uy][ux] != 145 && blocks[lyr][uy][ux] != 149 && blocks[lyr][uy][ux] != 153 && blocks[lyr][uy][ux] != 157 && blocks[lyr][uy][ux] != 161 && blocks[lyr][uy][ux] != 165 ||
                    blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && uy < ay3 && blocks[lyr][uy][ux] != 138 && blocks[lyr][uy][ux] != 142 && blocks[lyr][uy][ux] != 146 && blocks[lyr][uy][ux] != 150 && blocks[lyr][uy][ux] != 154 && blocks[lyr][uy][ux] != 158 && blocks[lyr][uy][ux] != 162 && blocks[lyr][uy][ux] != 166 ||
                    blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && ux > ax3 && blocks[lyr][uy][ux] != 139 && blocks[lyr][uy][ux] != 143 && blocks[lyr][uy][ux] != 147 && blocks[lyr][uy][ux] != 151 && blocks[lyr][uy][ux] != 155 && blocks[lyr][uy][ux] != 159 && blocks[lyr][uy][ux] != 163 && blocks[lyr][uy][ux] != 167 ||
                    blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && uy > ay3 && blocks[lyr][uy][ux] != 140 && blocks[lyr][uy][ux] != 144 && blocks[lyr][uy][ux] != 148 && blocks[lyr][uy][ux] != 152 && blocks[lyr][uy][ux] != 156 && blocks[lyr][uy][ux] != 160 && blocks[lyr][uy][ux] != 164 && blocks[lyr][uy][ux] != 168))) {
                    if (!arbprd[lyr][ay3][ax3]) {
                        rbpRecur(ax3, ay3, lyr);
                        if (CONDUCTS[blocks[lyr][ay3][ax3]] >= 0 && wcnct[ay3][ax3]) {
                            if (lyr == 0) {
                                if (RECEIVES[blocks[1][ay3][ax3]]) {
                                    rbpRecur(ax3, ay3, 1);
                                    if (POWERS[blocks[1][ay3][ax3]]) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (RECEIVES[blocks[2][ay3][ax3]]) {
                                    rbpRecur(ax3, ay3, 2);
                                    if (POWERS[blocks[2][ay3][ax3]]) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            }
                            if (lyr == 1) {
                                if (RECEIVES[blocks[0][ay3][ax3]]) {
                                    rbpRecur(ax3, ay3, 0);
                                    if (POWERS[blocks[0][ay3][ax3]]) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (RECEIVES[blocks[2][ay3][ax3]]) {
                                    rbpRecur(ax3, ay3, 2);
                                    if (POWERS[blocks[2][ay3][ax3]]) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            }
                            if (lyr == 2) {
                                if (RECEIVES[blocks[0][ay3][ax3]]) {
                                    rbpRecur(ax3, ay3, 0);
                                    if (POWERS[blocks[0][ay3][ax3]]) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (RECEIVES[blocks[1][ay3][ax3]]) {
                                    rbpRecur(ax3, ay3, 1);
                                    if (POWERS[blocks[1][ay3][ax3]]) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (blocks[lyr][ay3][ax3] == 104 || (blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 || blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 || blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168) &&
              !(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux < ax3 && blocks[lyr][ay3][ax3] != 111 && blocks[lyr][ay3][ax3] != 115 ||
                blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy < ay3 && blocks[lyr][ay3][ax3] != 112 && blocks[lyr][ay3][ax3] != 116 ||
                blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux > ax3 && blocks[lyr][ay3][ax3] != 113 && blocks[lyr][ay3][ax3] != 117 ||
                blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy > ay3 && blocks[lyr][ay3][ax3] != 114 && blocks[lyr][ay3][ax3] != 118) &&
              !(blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux < ax3 && blocks[lyr][ay3][ax3] != 119 && blocks[lyr][ay3][ax3] != 123 ||
                blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy < ay3 && blocks[lyr][ay3][ax3] != 120 && blocks[lyr][ay3][ax3] != 124 ||
                blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux > ax3 && blocks[lyr][ay3][ax3] != 121 && blocks[lyr][ay3][ax3] != 125 ||
                blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy > ay3 && blocks[lyr][ay3][ax3] != 122 && blocks[lyr][ay3][ax3] != 126) &&
              !(blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux < ax3 && blocks[lyr][ay3][ax3] != 137 && blocks[lyr][ay3][ax3] != 141 && blocks[lyr][ay3][ax3] != 145 && blocks[lyr][ay3][ax3] != 149 && blocks[lyr][ay3][ax3] != 153 && blocks[lyr][ay3][ax3] != 157 && blocks[lyr][ay3][ax3] != 161 && blocks[lyr][ay3][ax3] != 165 ||
                blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy < ay3 && blocks[lyr][ay3][ax3] != 138 && blocks[lyr][ay3][ax3] != 142 && blocks[lyr][ay3][ax3] != 146 && blocks[lyr][ay3][ax3] != 150 && blocks[lyr][ay3][ax3] != 154 && blocks[lyr][ay3][ax3] != 158 && blocks[lyr][ay3][ax3] != 162 && blocks[lyr][ay3][ax3] != 166 ||
                blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux > ax3 && blocks[lyr][ay3][ax3] != 139 && blocks[lyr][ay3][ax3] != 143 && blocks[lyr][ay3][ax3] != 147 && blocks[lyr][ay3][ax3] != 151 && blocks[lyr][ay3][ax3] != 155 && blocks[lyr][ay3][ax3] != 159 && blocks[lyr][ay3][ax3] != 163 && blocks[lyr][ay3][ax3] != 167 ||
                blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy > ay3 && blocks[lyr][ay3][ax3] != 140 && blocks[lyr][ay3][ax3] != 144 && blocks[lyr][ay3][ax3] != 148 && blocks[lyr][ay3][ax3] != 152 && blocks[lyr][ay3][ax3] != 156 && blocks[lyr][ay3][ax3] != 160 && blocks[lyr][ay3][ax3] != 164 && blocks[lyr][ay3][ax3] != 168)) {
                if (blocks[lyr][ay3][ax3] >= 123 && blocks[lyr][ay3][ax3] <= 126) {
                    blocks[lyr][ay3][ax3] -= 4;
                    print("Adding power for inverter at (" + ax3 + ", " + ay3 + ").");
                    addBlockPower(ax3, ay3);
                    addBlockLighting(ax3, ay3);
                    rdrawn[ay3][ax3] = false;
                }
                removeBlockPower(ax3, ay3, lyr);
            }
        }
        for (int ir=0; ir<4; ir++) {
            if (remember[ir] && uy+cl[ir][1] >= 0 && uy+cl[ir][1] < HEIGHT) {
                power[lyr][uy+cl[ir][1]][ux+cl[ir][0]] = (float)5;
            }
        }
        power[lyr][uy][ux] = (float)0;
        arbprd[lyr][uy][ux] = false;
    }

    public void removeBlockPower(int ux, int uy, int lyr) {
        removeBlockPower(ux, uy, lyr, true);
    }

    public void removeBlockPower(int ux, int uy, int lyr, boolean turnOffDelayer) {
        arbprd[lyr][uy][ux] = true;
        print("[rbp ] " + ux + " " + uy + " " + lyr + " " + turnOffDelayer);
        if (!((blocks[lyr][uy][ux] >= 141 && blocks[lyr][uy][ux] <= 144 || blocks[lyr][uy][ux] >= 149 && blocks[lyr][uy][ux] <= 152 || blocks[lyr][uy][ux] >= 157 && blocks[lyr][uy][ux] <= 160 || blocks[lyr][uy][ux] >= 165 && blocks[lyr][uy][ux] <= 168) && turnOffDelayer)) {
            int ax3, ay3;
            for (int ir=0; ir<4; ir++) {
                ax3 = ux+cl[ir][0];
                ay3 = uy+cl[ir][1];
                if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                    if (!(power[lyr][ay3][ax3] == power[lyr][uy][ux] - CONDUCTS[blocks[lyr][uy][ux]]) &&
                     (!(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 || blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126) ||
                      !(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux > ax3 && blocks[lyr][ay3][ax3] != 111 && blocks[lyr][ay3][ax3] != 115 ||
                        blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy > ay3 && blocks[lyr][ay3][ax3] != 112 && blocks[lyr][ay3][ax3] != 116 ||
                        blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux < ax3 && blocks[lyr][ay3][ax3] != 113 && blocks[lyr][ay3][ax3] != 117 ||
                        blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy < ay3 && blocks[lyr][ay3][ax3] != 114 && blocks[lyr][ay3][ax3] != 118) &&
                      !(blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux > ax3 && blocks[lyr][ay3][ax3] != 119 && blocks[lyr][ay3][ax3] != 123 ||
                        blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy > ay3 && blocks[lyr][ay3][ax3] != 120 && blocks[lyr][ay3][ax3] != 124 ||
                        blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux < ax3 && blocks[lyr][ay3][ax3] != 121 && blocks[lyr][ay3][ax3] != 125 ||
                        blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy < ay3 && blocks[lyr][ay3][ax3] != 122 && blocks[lyr][ay3][ax3] != 126) &&
                      !(blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux > ax3 && blocks[lyr][ay3][ax3] != 137 && blocks[lyr][ay3][ax3] != 141 && blocks[lyr][ay3][ax3] != 145 && blocks[lyr][ay3][ax3] != 149 && blocks[lyr][ay3][ax3] != 153 && blocks[lyr][ay3][ax3] != 157 && blocks[lyr][ay3][ax3] != 161 && blocks[lyr][ay3][ax3] != 165 ||
                        blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy > ay3 && blocks[lyr][ay3][ax3] != 138 && blocks[lyr][ay3][ax3] != 142 && blocks[lyr][ay3][ax3] != 146 && blocks[lyr][ay3][ax3] != 150 && blocks[lyr][ay3][ax3] != 154 && blocks[lyr][ay3][ax3] != 158 && blocks[lyr][ay3][ax3] != 162 && blocks[lyr][ay3][ax3] != 166 ||
                        blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux < ax3 && blocks[lyr][ay3][ax3] != 139 && blocks[lyr][ay3][ax3] != 143 && blocks[lyr][ay3][ax3] != 147 && blocks[lyr][ay3][ax3] != 151 && blocks[lyr][ay3][ax3] != 155 && blocks[lyr][ay3][ax3] != 159 && blocks[lyr][ay3][ax3] != 163 && blocks[lyr][ay3][ax3] != 167 ||
                        blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy < ay3 && blocks[lyr][ay3][ax3] != 140 && blocks[lyr][ay3][ax3] != 144 && blocks[lyr][ay3][ax3] != 148 && blocks[lyr][ay3][ax3] != 152 && blocks[lyr][ay3][ax3] != 156 && blocks[lyr][ay3][ax3] != 160 && blocks[lyr][ay3][ax3] != 164 && blocks[lyr][ay3][ax3] != 168))) {
                        print("Added tile " + ax3 + " " + ay3 + " to PQueue.");
                        addTileToPQueue(ax3, ay3);
                    }
                }
            }
            for (int ir=0; ir<4; ir++) {
                ax3 = ux+cl[ir][0];
                ay3 = uy+cl[ir][1];
                print(blocks[lyr][ay3][ax3] + " " + power[lyr][ay3][ax3]);
                if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                    print(power[lyr][uy][ux] + " " + power[lyr][ay3][ax3] + " " + CONDUCTS[blocks[lyr][uy][ux]]);
                    if (power[lyr][ay3][ax3] == power[lyr][uy][ux] - CONDUCTS[blocks[lyr][uy][ux]]) {
                        if (!(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 || blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126) ||
                          !(blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && ux < ax3 && blocks[lyr][uy][ux] != 111 && blocks[lyr][uy][ux] != 115 ||
                            blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && uy < ay3 && blocks[lyr][uy][ux] != 112 && blocks[lyr][uy][ux] != 116 ||
                            blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && ux > ax3 && blocks[lyr][uy][ux] != 113 && blocks[lyr][uy][ux] != 117 ||
                            blocks[lyr][uy][ux] >= 111 && blocks[lyr][uy][ux] <= 118 && uy > ay3 && blocks[lyr][uy][ux] != 114 && blocks[lyr][uy][ux] != 118) &&
                          !(blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && ux < ax3 && blocks[lyr][uy][ux] != 119 && blocks[lyr][uy][ux] != 123 ||
                            blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && uy < ay3 && blocks[lyr][uy][ux] != 120 && blocks[lyr][uy][ux] != 124 ||
                            blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && ux > ax3 && blocks[lyr][uy][ux] != 121 && blocks[lyr][uy][ux] != 125 ||
                            blocks[lyr][uy][ux] >= 119 && blocks[lyr][uy][ux] <= 126 && uy > ay3 && blocks[lyr][uy][ux] != 122 && blocks[lyr][uy][ux] != 126) &&
                          !(blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && ux < ax3 && blocks[lyr][uy][ux] != 137 && blocks[lyr][uy][ux] != 141 && blocks[lyr][uy][ux] != 145 && blocks[lyr][uy][ux] != 149 && blocks[lyr][uy][ux] != 153 && blocks[lyr][uy][ux] != 157 && blocks[lyr][uy][ux] != 161 && blocks[lyr][uy][ux] != 165 ||
                            blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && uy < ay3 && blocks[lyr][uy][ux] != 138 && blocks[lyr][uy][ux] != 142 && blocks[lyr][uy][ux] != 146 && blocks[lyr][uy][ux] != 150 && blocks[lyr][uy][ux] != 154 && blocks[lyr][uy][ux] != 158 && blocks[lyr][uy][ux] != 162 && blocks[lyr][uy][ux] != 166 ||
                            blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && ux > ax3 && blocks[lyr][uy][ux] != 139 && blocks[lyr][uy][ux] != 143 && blocks[lyr][uy][ux] != 147 && blocks[lyr][uy][ux] != 151 && blocks[lyr][uy][ux] != 155 && blocks[lyr][uy][ux] != 159 && blocks[lyr][uy][ux] != 163 && blocks[lyr][uy][ux] != 167 ||
                            blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168 && uy > ay3 && blocks[lyr][uy][ux] != 140 && blocks[lyr][uy][ux] != 144 && blocks[lyr][uy][ux] != 148 && blocks[lyr][uy][ux] != 152 && blocks[lyr][uy][ux] != 156 && blocks[lyr][uy][ux] != 160 && blocks[lyr][uy][ux] != 164 && blocks[lyr][uy][ux] != 168)) {
                            if (!arbprd[lyr][ay3][ax3]) {
                                rbpRecur(ax3, ay3, lyr);
                                if (CONDUCTS[blocks[lyr][ay3][ax3]] >= 0 && wcnct[ay3][ax3]) {
                                    if (lyr == 0) {
                                        if (RECEIVES[blocks[1][ay3][ax3]]) {
                                            rbpRecur(ax3, ay3, 1);
                                            if (POWERS[blocks[1][ay3][ax3]]) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (RECEIVES[blocks[2][ay3][ax3]]) {
                                            rbpRecur(ax3, ay3, 2);
                                            if (POWERS[blocks[2][ay3][ax3]]) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                    if (lyr == 1) {
                                        if (RECEIVES[blocks[0][ay3][ax3]]) {
                                            rbpRecur(ax3, ay3, 0);
                                            if (POWERS[blocks[0][ay3][ax3]]) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (RECEIVES[blocks[2][ay3][ax3]]) {
                                            rbpRecur(ax3, ay3, 2);
                                            if (POWERS[blocks[2][ay3][ax3]]) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                    if (lyr == 2) {
                                        if (RECEIVES[blocks[0][ay3][ax3]]) {
                                            rbpRecur(ax3, ay3, 0);
                                            if (POWERS[blocks[0][ay3][ax3]]) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (RECEIVES[blocks[1][ay3][ax3]]) {
                                            rbpRecur(ax3, ay3, 1);
                                            if (POWERS[blocks[1][ay3][ax3]]) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (blocks[lyr][ay3][ax3] == 104 || (blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 || blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 || blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168) &&
                  !(blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux < ax3 && blocks[lyr][ay3][ax3] != 111 && blocks[lyr][ay3][ax3] != 115 ||
                    blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy < ay3 && blocks[lyr][ay3][ax3] != 112 && blocks[lyr][ay3][ax3] != 116 ||
                    blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && ux > ax3 && blocks[lyr][ay3][ax3] != 113 && blocks[lyr][ay3][ax3] != 117 ||
                    blocks[lyr][ay3][ax3] >= 111 && blocks[lyr][ay3][ax3] <= 118 && uy > ay3 && blocks[lyr][ay3][ax3] != 114 && blocks[lyr][ay3][ax3] != 118) &&
                  !(blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux < ax3 && blocks[lyr][ay3][ax3] != 119 && blocks[lyr][ay3][ax3] != 123 ||
                    blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy < ay3 && blocks[lyr][ay3][ax3] != 120 && blocks[lyr][ay3][ax3] != 124 ||
                    blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && ux > ax3 && blocks[lyr][ay3][ax3] != 121 && blocks[lyr][ay3][ax3] != 125 ||
                    blocks[lyr][ay3][ax3] >= 119 && blocks[lyr][ay3][ax3] <= 126 && uy > ay3 && blocks[lyr][ay3][ax3] != 122 && blocks[lyr][ay3][ax3] != 126) &&
                  !(blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux < ax3 && blocks[lyr][ay3][ax3] != 137 && blocks[lyr][ay3][ax3] != 141 && blocks[lyr][ay3][ax3] != 145 && blocks[lyr][ay3][ax3] != 149 && blocks[lyr][ay3][ax3] != 153 && blocks[lyr][ay3][ax3] != 157 && blocks[lyr][ay3][ax3] != 161 && blocks[lyr][ay3][ax3] != 165 ||
                    blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy < ay3 && blocks[lyr][ay3][ax3] != 138 && blocks[lyr][ay3][ax3] != 142 && blocks[lyr][ay3][ax3] != 146 && blocks[lyr][ay3][ax3] != 150 && blocks[lyr][ay3][ax3] != 154 && blocks[lyr][ay3][ax3] != 158 && blocks[lyr][ay3][ax3] != 162 && blocks[lyr][ay3][ax3] != 166 ||
                    blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && ux > ax3 && blocks[lyr][ay3][ax3] != 139 && blocks[lyr][ay3][ax3] != 143 && blocks[lyr][ay3][ax3] != 147 && blocks[lyr][ay3][ax3] != 151 && blocks[lyr][ay3][ax3] != 155 && blocks[lyr][ay3][ax3] != 159 && blocks[lyr][ay3][ax3] != 163 && blocks[lyr][ay3][ax3] != 167 ||
                    blocks[lyr][ay3][ax3] >= 137 && blocks[lyr][ay3][ax3] <= 168 && uy > ay3 && blocks[lyr][ay3][ax3] != 140 && blocks[lyr][ay3][ax3] != 144 && blocks[lyr][ay3][ax3] != 148 && blocks[lyr][ay3][ax3] != 152 && blocks[lyr][ay3][ax3] != 156 && blocks[lyr][ay3][ax3] != 160 && blocks[lyr][ay3][ax3] != 164 && blocks[lyr][ay3][ax3] != 168)) {
                    if (blocks[lyr][ay3][ax3] >= 123 && blocks[lyr][ay3][ax3] <= 126) {
                        blocks[lyr][ay3][ax3] -= 4;
                        print("Adding power for inverter at (" + ax3 + ", " + ay3 + ").");
                        addBlockPower(ax3, ay3);
                        addBlockLighting(ax3, ay3);
                        rdrawn[ay3][ax3] = false;
                    }
                    arbprd[lyr][uy][ux] = false;
                    removeBlockPower(ax3, ay3, lyr);
                }
            }
        }
        if (blocks[lyr][uy][ux] == 104) {
            removeBlockLighting(ux, uy);
            blocks[lyr][uy][ux] = 103;
            rdrawn[uy][ux] = false;
        }
        if (blocks[lyr][uy][ux] >= 115 && blocks[lyr][uy][ux] <= 118) {
            blockTemp = blocks[lyr][uy][ux];
            blocks[lyr][uy][ux] -= 4;
            removeBlockPower(ux, uy, lyr);
            removeBlockLighting(ux, uy);
            rdrawn[uy][ux] = false;
        }
        if (turnOffDelayer && blocks[lyr][uy][ux] >= 137 && blocks[lyr][uy][ux] <= 168) {
            print("???");
            updatex.add(ux);
            updatey.add(uy);
            updatet.add(DDELAY.get(blocks[lyr][uy][ux]));
            updatel.add(lyr);
        }
        if (!((blocks[lyr][uy][ux] >= 141 && blocks[lyr][uy][ux] <= 144 || blocks[lyr][uy][ux] >= 149 && blocks[lyr][uy][ux] <= 152 || blocks[lyr][uy][ux] >= 157 && blocks[lyr][uy][ux] <= 160 || blocks[lyr][uy][ux] >= 165 && blocks[lyr][uy][ux] <= 168) && turnOffDelayer)) {
            power[lyr][uy][ux] = (float)0;
        }
        arbprd[lyr][uy][ux] = false;
    }

    public void redoBlockLighting(int ux, int uy) {
        for (ax=-BRIGHTEST; ax<BRIGHTEST+1; ax++) {
            for (ay=-BRIGHTEST; ay<BRIGHTEST+1; ay++) {
                if (Math.abs(ax)+Math.abs(ay) <= BRIGHTEST && uy+ay >= 0 && uy+ay < HEIGHT) {
                    addTileToZQueue(ux+ax, uy+ay);
                    lights[uy+ay][ux+ax] = (float)0;
                }
            }
        }
        for (ax=-BRIGHTEST*2; ax<BRIGHTEST*2+1; ax++) {
            for (ay=-BRIGHTEST*2; ay<BRIGHTEST*2+1; ay++) {
                if (Math.abs(ax)+Math.abs(ay) <= BRIGHTEST*2 && uy+ay >= 0 && uy+ay < HEIGHT) {
                    if (lsources[uy+ay][ux+ax]) {
                        addTileToQueue(ux+ax, uy+ay);
                    }
                }
            }
        }
    }

    public void redoBlockPower(int ux, int uy, int lyr) {
        if (POWERS[blocks[lyr][uy][ux]] || blocks[lyr][uy][ux] >= 94 && blocks[lyr][uy][ux] <= 99) {
            addAdjacentTilesToPQueue(ux, uy);
        }
        else {
            removeBlockPower(ux, uy, lyr);
        }
    }

    public void addSunLighting(int ux, int uy) { // And including
        for (y=0; y<uy; y++) {
            if (L_TRANS[blocks[1][y][ux]]) {
                return;
            }
        }
        addSources = false;
        for (y=uy; y<HEIGHT-1; y++) {
            if (L_TRANS[blocks[1][y+1][ux-1]] || L_TRANS[blocks[1][y+1][ux+1]]) {
                addSources = true;
            }
            if (addSources) {
                addTileToQueue(ux, y);
            }
            if (L_TRANS[blocks[1][y][ux]]) {
                return;
            }
            addTileToZQueue(ux, y);
            lights[y][ux] = (float)sunlightlevel;
            lsources[y][ux] = true;
        }
    }

    public void removeSunLighting(int ux, int uy) { // And including
        n = sunlightlevel;
        for (y=0; y<uy; y++) {
            if (L_TRANS[blocks[1][y][ux]]) {
                return;
            }
        }
        for (y=uy; y<HEIGHT; y++) {
            lsources[y][ux] = isBlockLightSource(ux, y);
            if (y != uy && L_TRANS[blocks[1][y][ux]]) {
                break;
            }
        }
        for (ax=-n; ax<n+1; ax++) {
            for (ay=-n; ay<n+(y-uy)+1; ay++) {
                if (uy+ay >= 0 && uy+ay < WIDTH) {
                    addTileToZQueue(ux+ax, uy+ay);
                    lights[uy+ay][ux+ax] = (float)0;
                }
            }
        }
        for (ax=-n-BRIGHTEST; ax<n+1+BRIGHTEST; ax++) {
            for (ay=-n-BRIGHTEST; ay<n+(y-uy)+1+BRIGHTEST; ay++) {
                if (uy+ay >= 0 && uy+ay < HEIGHT) {
                    if (lsources[uy+ay][ux+ax]) {
                        addTileToQueue(ux+ax, uy+ay);
                    }
                }
            }
        }
    }

    public boolean isReachedBySunlight(int ux, int uy) {
        for (ay=0; ay<uy+1; ay++) {
            if (L_TRANS[blocks[1][ay][ux]]) {
                return false;
            }
        }
        return true;
    }

    public boolean isBlockLightSource(int ux, int uy) {
        return (blocks[0][uy][ux] != 0 && BLOCKLIGHTS.get(blocks[0][uy][ux]) != 0 ||
                blocks[1][uy][ux] != 0 && BLOCKLIGHTS.get(blocks[1][uy][ux]) != 0 ||
                blocks[2][uy][ux] != 0 && BLOCKLIGHTS.get(blocks[2][uy][ux]) != 0);
    }

    public boolean isNonLayeredBlockLightSource(int ux, int uy) {
        return isNonLayeredBlockLightSource(ux, uy, layer);
    }

    public boolean isNonLayeredBlockLightSource(int ux, int uy, int layer) {
        return (layer != 0 && blocks[0][uy][ux] != 0 && BLOCKLIGHTS.get(blocks[0][uy][ux]) != 0 ||
                layer != 1 && blocks[1][uy][ux] != 0 && BLOCKLIGHTS.get(blocks[1][uy][ux]) != 0 ||
                layer != 2 && blocks[2][uy][ux] != 0 && BLOCKLIGHTS.get(blocks[2][uy][ux]) != 0);
    }

    public int findBlockLightSource(int ux, int uy) {
        n = 0;
        if (blocks[0][uy][ux] != 0) n = Math.max(BLOCKLIGHTS.get(blocks[0][uy][ux]), n);
        if (blocks[1][uy][ux] != 0) n = Math.max(BLOCKLIGHTS.get(blocks[1][uy][ux]), n);
        if (blocks[2][uy][ux] != 0) n = Math.max(BLOCKLIGHTS.get(blocks[2][uy][ux]), n);
        return n;
    }

    public int findNonLayeredBlockLightSource(int ux, int uy) {
        return findNonLayeredBlockLightSource(ux, uy, layer);
    }

    public int findNonLayeredBlockLightSource(int ux, int uy, int layer) {
        n = 0;
        if (blocks[0][uy][ux] != 0) n = Math.max(BLOCKLIGHTS.get(blocks[0][uy][ux]), n);
        if (blocks[1][uy][ux] != 0) n = Math.max(BLOCKLIGHTS.get(blocks[1][uy][ux]), n);
        if (blocks[2][uy][ux] != 0) n = Math.max(BLOCKLIGHTS.get(blocks[2][uy][ux]), n);
        return n;
    }

    public void addTileToQueue(int ux, int uy) {
        if (!lqd[uy][ux]) {
            lqx.add(ux);
            lqy.add(uy);
            lqd[uy][ux] = true;
        }
    }

    public void addTileToZQueue(int ux, int uy) {
        if (!zqd[uy][ux]) {
            zqx.add(ux);
            zqy.add(uy);
            zqn[uy][ux] = (byte)(float)lights[uy][ux];
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
        for (int i2=0; i2<4; i2++) {
            if (uy+cl[i2][1] >= 0 && uy+cl[i2][1] < HEIGHT) {
                addTileToPQueue(ux+cl[i2][0], uy+cl[i2][1]);
            }
        }
    }

    public void addAdjacentTilesToPQueueConditionally(int ux, int uy) {
        for (int i2=0; i2<4; i2++) {
            for (int l=0; l<3; l++) {
                if (uy+cl[i2][1] >= 0 && uy+cl[i2][1] < HEIGHT && power[l][uy+cl[i2][1]][ux+cl[i2][0]] > 0) {
                    addTileToPQueue(ux+cl[i2][0], uy+cl[i2][1]);
                }
            }
        }
    }

    public void addTileToPZQueue(int ux, int uy) {
        if (!pzqd[uy][ux]) {
            pzqx.add(ux);
            pzqy.add(uy);
            pzqn[0][uy][ux] = (byte)(float)power[0][uy][ux];
            pzqn[1][uy][ux] = (byte)(float)power[1][uy][ux];
            pzqn[2][uy][ux] = (byte)(float)power[2][uy][ux];
            pzqd[uy][ux] = true;
        }
    }

    public void resolveLightMatrix() {
        try {
            while (true) {
                x = lqx.get(0);
                y = lqy.get(0);
                if (lsources[y][x]) {
                    n = findBlockLightSource(x, y);
                    if (isReachedBySunlight(x, y)) {
                        lights[y][x] = this.max(lights[y][x], n, sunlightlevel);
                    }
                    else {
                        lights[y][x] = Math.max(lights[y][x], n);
                    }
                    addTileToZQueue(x, y);
                }
                for (i=0; i<4; i++) {
                    x2 = x + cl[i][0];
                    y2 = y + cl[i][1];
                    if (y2 >= 0 && y2 < HEIGHT) {
                        if (!L_TRANS[blocks[1][y2][x2]]) {
                            if (lights[y2][x2] <= lights[y][x] - (float)1.0) {
                                addTileToZQueue(x2, y2);
                                lights[y2][x2] = lights[y][x] - (float)1.0;
                                addTileToQueue(x2, y2);
                            }
                        }
                        else {
                            if (lights[y2][x2] <= lights[y][x] - (float)2.0) {
                                addTileToZQueue(x2, y2);
                                lights[y2][x2] = lights[y][x] - (float)2.0;
                                addTileToQueue(x2, y2);
                            }
                        }
                    }
                }
                lqx.remove(0);
                lqy.remove(0);
                lqd[y][x] = false;
            }
        }
        catch (IndexOutOfBoundsException e) {
            //
        }
        for (i=0; i<zqx.size(); i++) {
            x = zqx.get(i);
            y = zqy.get(i);
            if ((int)(float)lights[y][x] != zqn[y][x]) {
                rdrawn[y][x] = false;
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
                for (int l=0; l<3; l++) {
                    if (POWERS[blocks[l][y][x]]) {
                        if (!(blocks[l][y][x] >= 137 && blocks[l][y][x] <= 168)) {
                            addTileToPQueue(x, y);
                            power[l][y][x] = (float)5;
                        }
                    }
                }
                for (i=0; i<4; i++) {
                    x2 = x + cl[i][0];
                    y2 = y + cl[i][1];
                    if (y2 >= 0 && y2 < HEIGHT) {
                        for (int l=0; l<3; l++) {
                            if (power[l][y][x] > 0) {
                                if (CONDUCTS[blocks[l][y][x]] >= 0 && RECEIVES[blocks[l][y2][x2]] && !(blocks[l][y2][x2] >= 111 && blocks[l][y2][x2] <= 118 && x < x2 && blocks[l][y2][x2] != 111 && blocks[l][y2][x2] != 115 ||
                                                                                                 blocks[l][y2][x2] >= 111 && blocks[l][y2][x2] <= 118 && y < y2 && blocks[l][y2][x2] != 112 && blocks[l][y2][x2] != 116 ||
                                                                                                 blocks[l][y2][x2] >= 111 && blocks[l][y2][x2] <= 118 && x > x2 && blocks[l][y2][x2] != 113 && blocks[l][y2][x2] != 117 ||
                                                                                                 blocks[l][y2][x2] >= 111 && blocks[l][y2][x2] <= 118 && y > y2 && blocks[l][y2][x2] != 114 && blocks[l][y2][x2] != 118) &&
                                                                                               !(blocks[l][y][x] >= 111 && blocks[l][y][x] <= 118 && x < x2 && blocks[l][y][x] != 111 && blocks[l][y][x] != 115 ||
                                                                                                 blocks[l][y][x] >= 111 && blocks[l][y][x] <= 118 && y < y2 && blocks[l][y][x] != 112 && blocks[l][y][x] != 116 ||
                                                                                                 blocks[l][y][x] >= 111 && blocks[l][y][x] <= 118 && x > x2 && blocks[l][y][x] != 113 && blocks[l][y][x] != 117 ||
                                                                                                 blocks[l][y][x] >= 111 && blocks[l][y][x] <= 118 && y > y2 && blocks[l][y][x] != 114 && blocks[l][y][x] != 118) &&
                                                                                               !(blocks[l][y2][x2] >= 119 && blocks[l][y2][x2] <= 126 && x < x2 && blocks[l][y2][x2] != 119 && blocks[l][y2][x2] != 123 ||
                                                                                                 blocks[l][y2][x2] >= 119 && blocks[l][y2][x2] <= 126 && y < y2 && blocks[l][y2][x2] != 120 && blocks[l][y2][x2] != 124 ||
                                                                                                 blocks[l][y2][x2] >= 119 && blocks[l][y2][x2] <= 126 && x > x2 && blocks[l][y2][x2] != 121 && blocks[l][y2][x2] != 125 ||
                                                                                                 blocks[l][y2][x2] >= 119 && blocks[l][y2][x2] <= 126 && y > y2 && blocks[l][y2][x2] != 122 && blocks[l][y2][x2] != 126) &&
                                                                                               !(blocks[l][y][x] >= 119 && blocks[l][y][x] <= 126 && x < x2 && blocks[l][y][x] != 119 && blocks[l][y][x] != 123 ||
                                                                                                 blocks[l][y][x] >= 119 && blocks[l][y][x] <= 126 && y < y2 && blocks[l][y][x] != 120 && blocks[l][y][x] != 124 ||
                                                                                                 blocks[l][y][x] >= 119 && blocks[l][y][x] <= 126 && x > x2 && blocks[l][y][x] != 121 && blocks[l][y][x] != 125 ||
                                                                                                 blocks[l][y][x] >= 119 && blocks[l][y][x] <= 126 && y > y2 && blocks[l][y][x] != 122 && blocks[l][y][x] != 126) &&
                                                                                               !(blocks[l][y2][x2] >= 137 && blocks[l][y2][x2] <= 168 && x < x2 && blocks[l][y2][x2] != 137 && blocks[l][y2][x2] != 141 && blocks[l][y2][x2] != 145 && blocks[l][y2][x2] != 149 && blocks[l][y2][x2] != 153 && blocks[l][y2][x2] != 157 && blocks[l][y2][x2] != 161 && blocks[l][y2][x2] != 165 ||
                                                                                                 blocks[l][y2][x2] >= 137 && blocks[l][y2][x2] <= 168 && y < y2 && blocks[l][y2][x2] != 138 && blocks[l][y2][x2] != 142 && blocks[l][y2][x2] != 146 && blocks[l][y2][x2] != 150 && blocks[l][y2][x2] != 154 && blocks[l][y2][x2] != 158 && blocks[l][y2][x2] != 162 && blocks[l][y2][x2] != 166 ||
                                                                                                 blocks[l][y2][x2] >= 137 && blocks[l][y2][x2] <= 168 && x > x2 && blocks[l][y2][x2] != 139 && blocks[l][y2][x2] != 143 && blocks[l][y2][x2] != 147 && blocks[l][y2][x2] != 151 && blocks[l][y2][x2] != 155 && blocks[l][y2][x2] != 159 && blocks[l][y2][x2] != 163 && blocks[l][y2][x2] != 167 ||
                                                                                                 blocks[l][y2][x2] >= 137 && blocks[l][y2][x2] <= 168 && y > y2 && blocks[l][y2][x2] != 140 && blocks[l][y2][x2] != 144 && blocks[l][y2][x2] != 148 && blocks[l][y2][x2] != 152 && blocks[l][y2][x2] != 156 && blocks[l][y2][x2] != 160 && blocks[l][y2][x2] != 164 && blocks[l][y2][x2] != 168) &&
                                                                                               !(blocks[l][y][x] >= 137 && blocks[l][y][x] <= 168 && x < x2 && blocks[l][y][x] != 137 && blocks[l][y][x] != 141 && blocks[l][y][x] != 145 && blocks[l][y][x] != 149 && blocks[l][y][x] != 153 && blocks[l][y][x] != 157 && blocks[l][y][x] != 161 && blocks[l][y][x] != 165 ||
                                                                                                 blocks[l][y][x] >= 137 && blocks[l][y][x] <= 168 && y < y2 && blocks[l][y][x] != 138 && blocks[l][y][x] != 142 && blocks[l][y][x] != 146 && blocks[l][y][x] != 150 && blocks[l][y][x] != 154 && blocks[l][y][x] != 158 && blocks[l][y][x] != 162 && blocks[l][y][x] != 166 ||
                                                                                                 blocks[l][y][x] >= 137 && blocks[l][y][x] <= 168 && x > x2 && blocks[l][y][x] != 139 && blocks[l][y][x] != 143 && blocks[l][y][x] != 147 && blocks[l][y][x] != 151 && blocks[l][y][x] != 155 && blocks[l][y][x] != 159 && blocks[l][y][x] != 163 && blocks[l][y][x] != 167 ||
                                                                                                 blocks[l][y][x] >= 137 && blocks[l][y][x] <= 168 && y > y2 && blocks[l][y][x] != 140 && blocks[l][y][x] != 144 && blocks[l][y][x] != 148 && blocks[l][y][x] != 152 && blocks[l][y][x] != 156 && blocks[l][y][x] != 160 && blocks[l][y][x] != 164 && blocks[l][y][x] != 168)) {
                                    if (power[l][y2][x2] <= power[l][y][x] - CONDUCTS[blocks[l][y][x]]) {
                                        addTileToPZQueue(x2, y2);
                                        if (blocks[l][y2][x2] >= 137 && blocks[l][y2][x2] <= 140 ||
                                            blocks[l][y2][x2] >= 145 && blocks[l][y2][x2] <= 148 ||
                                            blocks[l][y2][x2] >= 153 && blocks[l][y2][x2] <= 156 ||
                                            blocks[l][y2][x2] >= 161 && blocks[l][y2][x2] <= 164) {
                                            print("[DEBUG1]");
                                            updatex.add(x2);
                                            updatey.add(y2);
                                            updatet.add(DDELAY.get(blocks[l][y2][x2]));
                                            updatel.add(l);
                                        }
                                        else {
                                            power[l][y2][x2] = power[l][y][x] - (float) CONDUCTS[blocks[l][y][x]];
                                            if (CONDUCTS[blocks[l][y2][x2]] >= 0 && wcnct[y2][x2]) {
                                                if (l == 0) {
                                                    if (RECEIVES[blocks[1][y2][x2]]) {
                                                        power[1][y2][x2] = power[0][y2][x2] - (float) CONDUCTS[blocks[0][y2][x2]];
                                                    }
                                                    if (RECEIVES[blocks[2][y2][x2]]) {
                                                        power[2][y2][x2] = power[0][y2][x2] - (float) CONDUCTS[blocks[0][y2][x2]];
                                                    }
                                                }
                                                if (l == 1) {
                                                    if (RECEIVES[blocks[0][y2][x2]]) {
                                                        power[0][y2][x2] = power[1][y2][x2] - (float) CONDUCTS[blocks[1][y2][x2]];
                                                    }
                                                    if (RECEIVES[blocks[2][y2][x2]]) {
                                                        power[2][y2][x2] = power[1][y2][x2] - (float) CONDUCTS[blocks[1][y2][x2]];
                                                    }
                                                }
                                                if (l == 2) {
                                                    if (RECEIVES[blocks[0][y2][x2]]) {
                                                        power[0][y2][x2] = power[2][y2][x2] - (float) CONDUCTS[blocks[2][y2][x2]];
                                                    }
                                                    if (RECEIVES[blocks[1][y2][x2]]) {
                                                        power[1][y2][x2] = power[2][y2][x2] - (float) CONDUCTS[blocks[2][y2][x2]];
                                                    }
                                                }
                                            }
                                        }
                                        if (!(blocks[l][y2][x2] >= 119 && blocks[l][y2][x2] <= 122)) {
                                            addTileToPQueue(x2, y2);
                                        }
                                    }
                                    if (power[l][y][x] - CONDUCTS[blocks[l][y][x]] > 0 && blocks[l][y2][x2] >= 119 && blocks[l][y2][x2] <= 122) {
                                        removeBlockPower(x2, y2, l);
                                        blocks[l][y2][x2] += 4;
                                        removeBlockLighting(x2, y2);
                                        rdrawn[y2][x2] = false;
                                    }
                                }
                            }
                        }
                    }
                }
                pqx.remove(0);
                pqy.remove(0);
                pqd[y][x] = false;
                for (int l=0; l<3; l++) {
                    print("[resolvePowerMatrix] " + x + " " + y + " " + l + " " + blocks[l][y][x] + " " + power[l][y][x]);
                    if (power[l][y][x] > 0) {
                        if (blocks[l][y][x] == 103) {
                            blocks[l][y][x] = 104;
                            addBlockLighting(x, y);
                            rdrawn[y][x] = false;
                        }
                        if (blocks[l][y][x] >= 111 && blocks[l][y][x] <= 114) {
                            print("Processed amplifier at " + x + " " + y);
                            blocks[l][y][x] += 4;
                            addTileToPQueue(x, y);
                            addBlockLighting(x, y);
                            rdrawn[y][x] = false;
                        }
                    }
                }
            }
        }
        catch (IndexOutOfBoundsException e) {
            //
        }
        for (i=0; i<pzqx.size(); i++) {
            x = pzqx.get(i);
            y = pzqy.get(i);
            for (int l=0; l<3; l++) {
                if (blocks[l][y][x] >= 94 && blocks[l][y][x] <= 99 && (int)(float)power[l][y][x] != pzqn[l][y][x]) {
                    removeBlockLighting(x, y, 0);
                    blocks[l][y][x] = WIREP.get((int)(float)power[l][y][x]);
                    addBlockLighting(x, y);
                    rdrawn[y][x] = false;
                }
            }
            pzqd[y][x] = false;
        }
        pzqx.clear();
        pzqy.clear();
    }

    public void paint(Graphics g) {
        if (screen == null) return;
        pg2 = screen.createGraphics();
        pg2.setColor(bg);
        pg2.fillRect(0, 0, getWidth(), getHeight());
        if (state.equals("ingame")) {
            if (player.y / 16 < HEIGHT * 0.5) {
                pg2.translate(getWidth()/2, getHeight()*0.85);
                pg2.rotate((timeOfDay - 70200)/86400*Math.PI*2);

                pg2.drawImage(sun,
                    (int)(-getWidth()*0.65), 0, (int)(-getWidth()*0.65+sun.getWidth()*2), sun.getHeight()*2,
                    0, 0, sun.getWidth(), sun.getHeight(),
                    null);

                pg2.rotate(Math.PI);

                pg2.drawImage(moon,
                    (int)(-getWidth()*0.65), 0, (int)(-getWidth()*0.65+moon.getWidth()*2), moon.getHeight()*2,
                    0, 0, moon.getWidth(), moon.getHeight(),
                    null);

                pg2.rotate(-(timeOfDay - 70200)/86400*Math.PI*2-Math.PI);
                pg2.translate(-getWidth()/2, -getHeight()*0.85);

                for (i=0; i<cloudsx.size(); i++) {
                    cloud = clouds[cloudsn.get(i)];
                    pg2.drawImage(clouds[cloudsn.get(i)],
                        (int)cloudsx.get(i).doubleValue(), (int)cloudsy.get(i).doubleValue(), (int)cloudsx.get(i).doubleValue()+cloud.getWidth()*2, (int)cloudsy.get(i).doubleValue()+cloud.getHeight()*2,
                        0, 0, cloud.getWidth(), cloud.getHeight(),
                        null);
                }
            }

            for (pwy=0; pwy<2; pwy++) {
                for (pwx=0; pwx<2; pwx++) {
                    int pwxc = pwx + ou;
                    int pwyc = pwy + ov;
                    if (worlds[pwy][pwx] != null) {
                        if (((player.ix + getWidth()/2 + player.width >= pwxc*CHUNKSIZE &&
                              player.ix + getWidth()/2 + player.width <= pwxc*CHUNKSIZE+CHUNKSIZE) ||
                             (player.ix - getWidth()/2 + player.width + BLOCKSIZE >= pwxc*CHUNKSIZE &&
                              player.ix - getWidth()/2 + player.width - BLOCKSIZE <= pwxc*CHUNKSIZE+CHUNKSIZE)) &&
                            ((player.iy + getHeight()/2 + player.height >= pwyc*CHUNKSIZE &&
                              player.iy + getHeight()/2 + player.height <= pwyc*CHUNKSIZE+CHUNKSIZE) ||
                             (player.iy - getHeight()/2 + player.height >= pwyc*CHUNKSIZE &&
                              player.iy - getHeight()/2 + player.height <= pwyc*CHUNKSIZE+CHUNKSIZE))) {
                            pg2.drawImage(worlds[pwy][pwx],
                                pwxc*CHUNKSIZE -player.ix + getWidth()/2 - player.width/2, pwyc*CHUNKSIZE -player.iy + getHeight()/2 - player.height/2, pwxc*CHUNKSIZE -player.ix + getWidth()/2 - player.width/2 + CHUNKSIZE, pwyc*CHUNKSIZE -player.iy + getHeight()/2 - player.height/2 + CHUNKSIZE,
                                0, 0, CHUNKSIZE, CHUNKSIZE,
                                null);
                        }
                    }
                }
            }

            pg2.drawImage(player.image,
                getWidth()/2 - player.width/2, getHeight()/2 - player.height/2, getWidth()/2 + player.width/2, getHeight()/2 + player.height/2,
                0, 0, player.image.getWidth(), player.image.getHeight(),
                null);

            for (i=0; i<entities.size(); i++) {
                entity = entities.get(i);
                pg2.drawImage(entity.getImage(),
                    entity.getIx() -player.ix + getWidth()/2 - player.width/2, entity.getIy() -player.iy + getHeight()/2 - player.height/2, entity.getIx() -player.ix + getWidth()/2 - player.width/2 + entity.getWidth(), entity.getIy() -player.iy + getHeight()/2 - player.height/2 + entity.getHeight(),
                    0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                    null);
                pg2.drawImage(entity.getImage(),
                    entity.getIx() -player.ix + getWidth()/2 - player.width/2 - WIDTH*BLOCKSIZE, entity.getIy() -player.iy + getHeight()/2 - player.height/2, entity.getIx() -player.ix + getWidth()/2 - player.width/2 + entity.getWidth() - WIDTH*BLOCKSIZE, entity.getIy() -player.iy + getHeight()/2 - player.height/2 + entity.getHeight(),
                    0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                    null);
                pg2.drawImage(entity.getImage(),
                    entity.getIx() -player.ix + getWidth()/2 - player.width/2 + WIDTH*BLOCKSIZE, entity.getIy() -player.iy + getHeight()/2 - player.height/2, entity.getIx() -player.ix + getWidth()/2 - player.width/2 + entity.getWidth() + WIDTH*BLOCKSIZE, entity.getIy() -player.iy + getHeight()/2 - player.height/2 + entity.getHeight(),
                    0, 0, entity.getImage().getWidth(), entity.getImage().getHeight(),
                    null);
            }

            if (showTool && tool != null) {
                if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                    pg2.translate(getWidth()/2+6, getHeight()/2);
                    pg2.rotate(toolAngle);

                    pg2.drawImage(tool,
                        0, -tool.getHeight()*2, tool.getWidth()*2, 0,
                        0, 0, tool.getWidth(), tool.getHeight(),
                        null);

                    pg2.rotate(-toolAngle);
                    pg2.translate(-getWidth()/2-6, -getHeight()/2);
                }
                if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                    pg2.translate(getWidth()/2-6, getHeight()/2);
                    pg2.rotate((Math.PI * 1.5) - toolAngle);

                    pg2.drawImage(tool,
                        0, -tool.getHeight()*2, tool.getWidth()*2, 0,
                        0, 0, tool.getWidth(), tool.getHeight(),
                        null);

                    pg2.rotate(-((Math.PI * 1.5) - toolAngle));
                    pg2.translate(-getWidth()/2+6, -getHeight()/2);
                }
            }

            for (pwy=0; pwy<2; pwy++) {
                for (pwx=0; pwx<2; pwx++) {
                    int pwxc = pwx + ou;
                    int pwyc = pwy + ov;
                    if (fworlds[pwy][pwx] != null) {
                        if (((player.ix + getWidth()/2 + player.width >= pwxc*CHUNKSIZE &&
                              player.ix + getWidth()/2 + player.width <= pwxc*CHUNKSIZE+CHUNKSIZE) ||
                             (player.ix - getWidth()/2 + player.width + BLOCKSIZE >= pwxc*CHUNKSIZE &&
                              player.ix - getWidth()/2 + player.width - BLOCKSIZE <= pwxc*CHUNKSIZE+CHUNKSIZE)) &&
                            ((player.iy + getHeight()/2 + player.height >= pwyc*CHUNKSIZE &&
                              player.iy + getHeight()/2 + player.height <= pwyc*CHUNKSIZE+CHUNKSIZE) ||
                             (player.iy - getHeight()/2 + player.height >= pwyc*CHUNKSIZE &&
                              player.iy - getHeight()/2 + player.height <= pwyc*CHUNKSIZE+CHUNKSIZE))) {
                            pg2.drawImage(fworlds[pwy][pwx],
                                pwxc*CHUNKSIZE -player.ix + getWidth()/2 - player.width/2, pwyc*CHUNKSIZE -player.iy + getHeight()/2 - player.height/2, pwxc*CHUNKSIZE -player.ix + getWidth()/2 - player.width/2 + CHUNKSIZE, pwyc*CHUNKSIZE -player.iy + getHeight()/2 - player.height/2 + CHUNKSIZE,
                                0, 0, CHUNKSIZE, CHUNKSIZE,
                                null);
                        }
                    }
                }
            }

            if (showInv) {
                pg2.drawImage(inventory.image,
                    0, 0, inventory.image.getWidth(), (int)inventory.image.getHeight(),
                    0, 0, inventory.image.getWidth(), (int)inventory.image.getHeight(),
                    null);
                pg2.drawImage(armor.image,
                    inventory.image.getWidth() + 6, 6, inventory.image.getWidth() + 6 + armor.image.getWidth(), 6 + armor.image.getHeight(),
                    0, 0, armor.image.getWidth(), armor.image.getHeight(),
                    null);
                pg2.drawImage(cic.image,
                    inventory.image.getWidth() + 75, 52, inventory.image.getWidth() + 75 + cic.image.getWidth(), 52 + cic.image.getHeight(),
                    0, 0, cic.image.getWidth(), cic.image.getHeight(),
                    null);
            }
            else {
                pg2.drawImage(inventory.image,
                    0, 0, inventory.image.getWidth(), (int)inventory.image.getHeight()/4,
                    0, 0, inventory.image.getWidth(), (int)inventory.image.getHeight()/4,
                    null);
            }

            if (ic != null) {
                pg2.drawImage(ic.image,
                    6, inventory.image.getHeight() + 46, 6 + ic.image.getWidth(), inventory.image.getHeight() + 46 + ic.image.getHeight(),
                    0, 0, ic.image.getWidth(), ic.image.getHeight(),
                    null);
            }

            if (layer == 0) {
                layerImg = ResourcesLoader.loadImage("interface/layersB.png");
            }
            if (layer == 1) {
                layerImg = ResourcesLoader.loadImage("interface/layersN.png");
            }
            if (layer == 2) {
                layerImg = ResourcesLoader.loadImage("interface/layersF.png");
            }

            pg2.drawImage(layerImg,
                inventory.image.getWidth() + 58, 6, inventory.image.getWidth() + 58 + layerImg.getWidth(), 6 + layerImg.getHeight(),
                0, 0, layerImg.getWidth(), layerImg.getHeight(),
                null);

            if (showInv) {
                pg2.drawImage(save_exit,
                    getWidth()-save_exit.getWidth()-24, getHeight()-save_exit.getHeight()-24, getWidth()-24, getHeight()-24,
                    0, 0, save_exit.getWidth(), save_exit.getHeight(),
                    null);
            }

            if (moveItem != 0) {
                width = itemImgs.get(moveItem).getWidth();
                height = itemImgs.get(moveItem).getHeight();
                pg2.drawImage(itemImgs.get(moveItem),
                    mousePos[0]+12+((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), mousePos[1]+12+((int)(24-(double)12/this.max(width, height, 12)*height*2)/2), mousePos[0]+36-((int)(24-(double)12/this.max(width, height, 12)*width*2)/2), mousePos[1]+36-((int)(24-(double)12/this.max(width, height, 12)*height*2)/2),
                    0, 0, width, height,
                    null);
                if (moveNum > 1) {
                    pg2.setFont(font);
                    pg2.setColor(Color.WHITE);
                    pg2.drawString(moveNum + " ", mousePos[0]+13, mousePos[1]+38);
                }
            }
            for (i=0; i<entities.size(); i++) {
                if (UIENTITIES.get(entities.get(i).getName()) != null && entities.get(i).getRect() != null && entities.get(i).getRect().contains(new Point(mousePos2[0], mousePos2[1]))) {
                    pg2.setFont(mobFont);
                    pg2.setColor(Color.WHITE);
                    pg2.drawString(UIENTITIES.get(entities.get(i).getName()) + " (" + entities.get(i).getHp() + "/" + entities.get(i).getThp() + ")", mousePos[0], mousePos[1]);
                    break;
                }
            }
            if (showInv) {
                ymax = 4;
            }
            else {
                ymax = 1;
            }
            for (ux=0; ux<10; ux++) {
                for (uy=0; uy<ymax; uy++) {
                    if (mousePos[0] >= ux*46+6 && mousePos[0] <= ux*46+46 &&
                        mousePos[1] >= uy*46+6 && mousePos[1] <= uy*46+46 && inventory.ids[uy*10+ux] != 0) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (TOOLDURS.get((short)inventory.ids[uy*10+ux]) != null) {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(inventory.ids[uy*10+ux]).getFileName()) + " (" + (int)((double)inventory.durs[uy*10+ux]/TOOLDURS.get(inventory.ids[uy*10+ux])*100) + "%)", mousePos[0], mousePos[1]);
                        }
                        else {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(inventory.ids[uy*10+ux]).getFileName()), mousePos[0], mousePos[1]);
                        }
                    }
                }
            }
            pg2.setFont(mobFont);
            pg2.setColor(Color.WHITE);
            pg2.drawString("Health: " + player.hp + "/" + player.thp, getWidth()-125, 20);
            pg2.drawString("Armor: " + player.sumArmor(), getWidth()-125, 40);
            if (DEBUG_STATS) {
                pg2.drawString("(" + ((int)player.ix/16) + ", " + ((int)player.iy/16) + ")", getWidth()-125, 60);
                if (player.iy >= 0 && player.iy < HEIGHT*BLOCKSIZE) {
                    pg2.drawString(checkBiome((int)player.ix/16+u, (int)player.iy/16+v) + " " + lights[(int)player.iy/16+v][(int)player.ix/16+u], getWidth()-125, 80);
                }
            }
            if (showInv) {
                for (ux=0; ux<2; ux++) {
                    for (uy=0; uy<2; uy++) {
                        if (mousePos[0] >= inventory.image.getWidth()+ux*40+75 &&
                            mousePos[0] < inventory.image.getWidth()+ux*40+115 &&
                            mousePos[1] >= uy*40+52 && mousePos[1] < uy*40+92 && cic.ids[uy*2+ux] != 0) {
                            pg2.setFont(mobFont);
                            pg2.setColor(Color.WHITE);
                            if (TOOLDURS.get((short)cic.ids[uy*2+ux]) != null) {
                                pg2.drawString(UIBLOCKS.get(Items.findByIndex(cic.ids[uy*2+ux]).getFileName()) + " (" + (int)((double)cic.durs[uy*2+ux]/TOOLDURS.get(cic.ids[uy*2+ux])*100) + "%)", mousePos[0], mousePos[1]);
                            }
                            else {
                                pg2.drawString(UIBLOCKS.get(Items.findByIndex(cic.ids[uy*2+ux]).getFileName()), mousePos[0], mousePos[1]);
                            }
                        }
                    }
                }
                if (mousePos[0] >= inventory.image.getWidth()+3*40+75 &&
                    mousePos[0] < inventory.image.getWidth()+3*40+115 &&
                    mousePos[1] >= 20+52 && mousePos[1] < 20+92 && cic.ids[4] != 0) {
                    pg2.setFont(mobFont);
                    pg2.setColor(Color.WHITE);
                    if (TOOLDURS.get((short)cic.ids[4]) != null) {
                        pg2.drawString(UIBLOCKS.get(Items.findByIndex(cic.ids[4]).getFileName()) + " (" + (int)((double)cic.durs[4]/TOOLDURS.get(cic.ids[4])*100) + "%)", mousePos[0], mousePos[1]);
                    }
                    else {
                        pg2.drawString(UIBLOCKS.get(Items.findByIndex(cic.ids[4]).getFileName()), mousePos[0], mousePos[1]);
                    }
                }
                for (uy=0; uy<4; uy++) {
                    if (mousePos[0] >= inventory.image.getWidth() + 6 && mousePos[0] < inventory.image.getWidth() + 6 + armor.image.getWidth() &&
                        mousePos[1] >= 6 + uy*46 && mousePos[1] < 6 + uy*46+46 && armor.ids[uy] != 0) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (TOOLDURS.get((short)armor.ids[uy]) != null) {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(armor.ids[uy]).getFileName()) + " (" + (int)((double)armor.durs[uy]/TOOLDURS.get(armor.ids[uy])*100) + "%)", mousePos[0], mousePos[1]);
                        }
                        else {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(armor.ids[uy]).getFileName()), mousePos[0], mousePos[1]);
                        }
                    }
                }
            }
            if (ic != null) {
                if (ic.type.equals("workbench")) {
                    for (ux=0; ux<3; ux++) {
                        for (uy=0; uy<3; uy++) {
                            if (mousePos[0] >= ux*40+6 && mousePos[0] < ux*40+46 &&
                                mousePos[1] >= uy*40+inventory.image.getHeight()+46 &&
                                mousePos[1] < uy*40+inventory.image.getHeight()+86 &&
                                ic.ids[uy*3+ux] != 0) {
                                pg2.setFont(mobFont);
                                pg2.setColor(Color.WHITE);
                                if (TOOLDURS.get((short)ic.ids[uy*3+ux]) != null) {
                                    pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[uy*3+ux]).getFileName()) + " (" + (int)((double)ic.durs[uy*3+ux]/TOOLDURS.get(ic.ids[uy*3+ux])*100) + "%)", mousePos[0], mousePos[1]);
                                }
                                else {
                                    pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[uy*3+ux]).getFileName()), mousePos[0], mousePos[1]);
                                }
                            }
                        }
                    }
                    if (mousePos[0] >= 4*40+6 && mousePos[0] < 4*40+46 &&
                        mousePos[1] >= 1*40+inventory.image.getHeight()+46 &&
                        mousePos[1] < 1*40+inventory.image.getHeight()+86 &&
                        ic.ids[9] != 0) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (TOOLDURS.get((short)ic.ids[9]) != null) {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[9]).getFileName()) + " (" + (int)((double)ic.durs[9]/TOOLDURS.get(ic.ids[9])*100) + "%)", mousePos[0], mousePos[1]);
                        }
                        else {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[9]).getFileName()), mousePos[0], mousePos[1]);
                        }
                    }
                }
                if (ic.type.equals("wooden_chest") || ic.type.equals("stone_chest") ||
                    ic.type.equals("copper_chest") || ic.type.equals("iron_chest") ||
                    ic.type.equals("silver_chest") || ic.type.equals("gold_chest") ||
                    ic.type.equals("zinc_chest") || ic.type.equals("rhymestone_chest") ||
                    ic.type.equals("obdurite_chest")) {
                    for (ux=0; ux<inventory.CX; ux++) {
                        for (uy=0; uy<inventory.CY; uy++) {
                            if (mousePos[0] >= ux*46+6 && mousePos[0] < ux*46+46 &&
                                mousePos[1] >= uy*46+inventory.image.getHeight()+46 &&
                                mousePos[1] < uy*46+inventory.image.getHeight()+86 &&
                                ic.ids[uy*inventory.CX+ux] != 0) {
                                pg2.setFont(mobFont);
                                pg2.setColor(Color.WHITE);
                                if (TOOLDURS.get((short)ic.ids[uy*inventory.CX+ux]) != null) {
                                    pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[uy*inventory.CX+ux]).getFileName()) + " (" + (int)((double)ic.durs[uy*inventory.CX+ux]/TOOLDURS.get(ic.ids[uy*inventory.CX+ux])*100) + "%)", mousePos[0], mousePos[1]);
                                }
                                else {
                                    pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[uy*inventory.CX+ux]).getFileName()), mousePos[0], mousePos[1]);
                                }
                            }
                        }
                    }
                }
                if (ic.type.equals("furnace")) {
                    if (mousePos[0] >= 6 && mousePos[0] < 46 &&
                        mousePos[1] >= inventory.image.getHeight()+46 && mousePos[1] < inventory.image.getHeight()+86 &&
                        ic.ids[0] != 0) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (TOOLDURS.get((short)ic.ids[0]) != null) {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[0]).getFileName()) + " (" + (int)((double)ic.durs[0]/TOOLDURS.get(ic.ids[0])*100) + "%)", mousePos[0], mousePos[1]);
                        }
                        else {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[0]).getFileName()), mousePos[0], mousePos[1]);
                        }
                    }
                    if (mousePos[0] >= 6 && mousePos[0] < 46 &&
                        mousePos[1] >= inventory.image.getHeight()+102 && mousePos[1] < inventory.image.getHeight()+142 &&
                        ic.ids[1] != 0) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (TOOLDURS.get((short)ic.ids[1]) != null) {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[1]).getFileName()) + " (" + (int)((double)ic.durs[1]/TOOLDURS.get(ic.ids[1])*100) + "%)", mousePos[1], mousePos[1]);
                        }
                        else {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[1]).getFileName()), mousePos[0], mousePos[1]);
                        }
                    }
                    if (mousePos[0] >= 6 && mousePos[0] < 46 &&
                        mousePos[1] >= inventory.image.getHeight()+142 && mousePos[1] < inventory.image.getHeight()+182 &&
                        ic.ids[2] != 0) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (TOOLDURS.get((short)ic.ids[2]) != null) {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[2]).getFileName()) + " (" + (int)((double)ic.durs[2]/TOOLDURS.get(ic.ids[2])*100) + "%)", mousePos[2], mousePos[1]);
                        }
                        else {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[2]).getFileName()), mousePos[0], mousePos[1]);
                        }
                    }
                    if (mousePos[0] >= 62 && mousePos[0] < 102 &&
                        mousePos[1] >= inventory.image.getHeight()+46 && mousePos[1] < inventory.image.getHeight()+86 &&
                        ic.ids[3] != 0) {
                        pg2.setFont(mobFont);
                        pg2.setColor(Color.WHITE);
                        if (TOOLDURS.get((short)ic.ids[3]) != null) {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[3]).getFileName()) + " (" + (int)((double)ic.durs[3]/TOOLDURS.get(ic.ids[3])*100) + "%)", mousePos[3], mousePos[1]);
                        }
                        else {
                            pg2.drawString(UIBLOCKS.get(Items.findByIndex(ic.ids[3]).getFileName()), mousePos[0], mousePos[1]);
                        }
                    }
                }
            }
        }
        if (state.equals("loading_graphics")) {
            pg2.setFont(loadFont);
            pg2.setColor(Color.GREEN);
            pg2.drawString("Loading graphics... Please wait.", getWidth()/2-200, getHeight()/2-5);
        }
        if (state.equals("title_screen")) {
            pg2.drawImage(title_screen,
                0, 0, getWidth(), getHeight(),
                0, 0, getWidth(), getHeight(),
                null);
        }
        if (state.equals("select_world")) {
            pg2.drawImage(select_world,
                0, 0, getWidth(), getHeight(),
                0, 0, getWidth(), getHeight(),
                null);
            for (i=0; i<worldNames.size(); i++) {
                pg2.setFont(worldFont);
                pg2.setColor(Color.BLACK);
                pg2.drawString(worldNames.get(i), 180, 140+i*35);
                pg2.fillRect(166, 150+i*35, 470, 3);
            }
        }
        if (state.equals("new_world")) {
            pg2.drawImage(new_world,
                0, 0, getWidth(), getHeight(),
                0, 0, getWidth(), getHeight(),
                null);
            pg2.drawImage(newWorldName.getImage(),
                200, 240, 600, 270,
                0, 0, 400, 30,
                null);
        }
        if (state.equals("generating_world")) {
            pg2.setFont(loadFont);
            pg2.setColor(Color.GREEN);
            pg2.drawString("Generating new world... Please wait.", getWidth()/2-200, getHeight()/2-5);
        }
        ((Graphics2D)g).drawImage(screen,
            0, 0, getWidth(), getHeight(),
            0, 0, getWidth(), getHeight(),
            null);
    }

    public boolean loadWorld(String worldFile) {
        try {
            FileInputStream fileIn = new FileInputStream("worlds/" + worldFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            WorldContainer wc = (WorldContainer) in.readObject();
            in.close();
            fileIn.close();
            emptyWorldContainer(wc);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void saveWorld() {
        try {
            WorldContainer wc = createWorldContainer();
            FileOutputStream fileOut = new FileOutputStream("worlds/" + currentWorld + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(wc);
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emptyWorldContainer(WorldContainer wc) {
        blocks = wc.blocks;
        blockds = wc.blockds;
        blockdns = wc.blockdns;
        blockbgs = wc.blockbgs;
        blockts = wc.blockts;
        lights = wc.lights;
        power = wc.power;
        resetDrawn();
        player = wc.player;
        inventory = wc.inventory;
        cic = wc.cic;
        entities = wc.entities;
        cloudsx = wc.cloudsx;
        cloudsy = wc.cloudsy;
        cloudsv = wc.cloudsv;
        cloudsn = wc.cloudsn;
        machinesx = wc.machinesx;
        machinesy = wc.machinesy;
        lsources = wc.lsources;
        lqx = wc.lqx;
        lqy = wc.lqy;
        lqd = wc.lqd;
        rgnc1 = wc.rgnc1;
        rgnc2 = wc.rgnc2;
        layer = wc.layer;
        layerTemp = wc.layerTemp;
        blockTemp = wc.blockTemp;
        mx = wc.mx;
        my = wc.my;
        icx = wc.icx;
        icy = wc.icy;
        mining = wc.mining;
        immune = wc.immune;
        moveItem = wc.moveItem;
        moveNum = wc.moveNum;
        moveItemTemp = wc.moveItemTemp;
        moveNumTemp = wc.moveNumTemp;
        msi = wc.msi;
        toolAngle = wc.toolAngle;
        toolSpeed = wc.toolSpeed;
        timeOfDay = wc.timeOfDay;
        currentSkyLight = wc.currentSkyLight;
        day = wc.day;
        mobCount = wc.mobCount;
        ready = wc.ready;
        showTool = wc.showTool;
        showInv = wc.showInv;
        doMobSpawn = wc.doMobSpawn;
        WIDTH = wc.WIDTH;
        HEIGHT = wc.HEIGHT;
        random = wc.random;
        WORLDWIDTH = wc.WORLDWIDTH;
        WORLDHEIGHT = wc.WORLDHEIGHT;
        resunlight = wc.resunlight;
        kworlds = wc.kworlds;
        ic = wc.ic;
        icmatrix = wc.icmatrix;
        version = wc.version;
        player.reloadImage();
        inventory.reloadImage();
        if (cic != null) {
            inventory.renderCollection(cic);
        }
        else {
            short[] tlist1 = {0, 0, 0, 0, 0};
            short[] tlist2 = {0, 0, 0, 0, 0};
            short[] tlist3 = {0, 0, 0, 0, 0};
            cic = new ItemCollection("cic", tlist1, tlist2, tlist3);
            inventory.renderCollection(cic);
        }
        if (ic != null) {
            inventory.renderCollection(ic);
        }
        for (i=0; i<entities.size(); i++) {
            entities.get(i).reloadImage();
        }
        worlds = new BufferedImage[WORLDHEIGHT][WORLDWIDTH];
        fworlds = new BufferedImage[WORLDHEIGHT][WORLDWIDTH];
    }

    public void resetDrawn() {
        drawn = new Boolean[HEIGHT][WIDTH];
        for (y=0; y<HEIGHT; y++) {
            for (x=0; x<WIDTH; x++) {
                drawn[y][x] = false;
            }
        }
        ldrawn = new Boolean[HEIGHT][WIDTH];
        for (y=0; y<HEIGHT; y++) {
            for (x=0; x<WIDTH; x++) {
                ldrawn[y][x] = false;
            }
        }
        rdrawn = new Boolean[HEIGHT][WIDTH];
        for (y=0; y<HEIGHT; y++) {
            for (x=0; x<WIDTH; x++) {
                rdrawn[y][x] = false;
            }
        }
    }

    public WorldContainer createWorldContainer() {
        return (new WorldContainer(blocks, blockds, blockdns, blockbgs, blockts,
                                  lights, power, drawn, ldrawn, rdrawn,
                                  player, inventory, cic,
                                  entities, cloudsx, cloudsy, cloudsv, cloudsn,
                                  machinesx, machinesy, lsources, lqx, lqy, lqd,
                                  rgnc1, rgnc2, layer, layerTemp, blockTemp,
                                  mx, my, icx, icy, mining, immune,
                                  moveItem, moveNum, moveItemTemp, moveNumTemp, msi,
                                  toolAngle, toolSpeed, timeOfDay, currentSkyLight, day, mobCount,
                                  ready, showTool, showInv, doMobSpawn,
                                  WIDTH, HEIGHT, random, WORLDWIDTH, WORLDHEIGHT,
                                  resunlight,
                                  ic, kworlds, icmatrix, version));
    }

    public BufferedImage loadBlock(Integer type, Byte dir, Byte dirn, Byte tnum, String outlineName, int x, int y, int lyr) {
        int fx, fy;
        int dir_is = (int)dir;
        String dir_s = Directions.findByIndex(dir_is).getFileName();
        int dir_i = (int)dirn;
        BufferedImage outline = outlineImgs.get("outlines/" + outlineName + "/" + dir_s + (dir_i+1) + ".png");
        String bName = BlockNames.findByIndex(type).getFileName();
        BufferedImage texture = blockImgs.get("blocks/" + bName + "/texture" + (tnum+1) + ".png");
        BufferedImage image = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT);
        if (GRASSDIRT.get(type) != null) {
            BufferedImage dirtOriginal = blockImgs.get("blocks/" + BlockNames.findByIndex(GRASSDIRT.get(type)).getFileName() + "/texture" + (tnum+1) + ".png");
            BufferedImage dirt = config.createCompatibleImage(IMAGESIZE, IMAGESIZE, Transparency.TRANSLUCENT);
            for (dy=0; dy<IMAGESIZE; dy++) {
                for (dx=0; dx<IMAGESIZE; dx++) {
                    dirt.setRGB(dx, dy, dirtOriginal.getRGB(dx, dy));
                }
            }
            int dn = GRASSDIRT.get(type);
            boolean left = (blocks[lyr][y][x-1] == 0 || !BLOCK_CDS[blocks[lyr][y][x-1]]);// && (blocks[lyr][y-1][x] != dn && blocks[lyr][y+1][x] != dn) && (blocks[lyr][y-1][x-1] != dn && blocks[lyr][y+1][x-1] != dn);
            boolean right = (blocks[lyr][y][x+1] == 0 || !BLOCK_CDS[blocks[lyr][y][x+1]]);// && (blocks[lyr][y-1][x] != dn && blocks[lyr][y+1][x] != dn) && (blocks[lyr][y-1][x+1] != dn && blocks[lyr][y+1][x+1] != dn);
            boolean up = (blocks[lyr][y-1][x] == 0 || !BLOCK_CDS[blocks[lyr][y-1][x]]);// && (blocks[lyr][y][x-1] != dn && blocks[lyr][y][x+1] != dn) && (blocks[lyr][y-1][x-1] != dn && blocks[lyr][y-1][x+1] != dn);
            boolean down = (blocks[lyr][y+1][x] == 0 || !BLOCK_CDS[blocks[lyr][y+1][x]]);// && (blocks[lyr][y][x-1] != dn && blocks[lyr][y][x+1] != dn) && (blocks[lyr][y+1][x-1] != dn && blocks[lyr][y+1][x+1] != dn);
            boolean upleft = (blocks[lyr][y-1][x-1] == 0 || !BLOCK_CDS[blocks[lyr][y-1][x-1]]);// && (blocks[lyr][y-1][x] != dn && blocks[lyr][y][x-1] != dn && blocks[lyr][y-1][x-1] != dn && blocks[lyr][y-2][x] != dn && blocks[lyr][y][x-2] != dn);
            boolean upright = (blocks[lyr][y-1][x+1] == 0 || !BLOCK_CDS[blocks[lyr][y-1][x+1]]);// && (blocks[lyr][y-1][x] != dn && blocks[lyr][y][x+1] != dn && blocks[lyr][y-1][x+1] != dn && blocks[lyr][y-2][x] != dn && blocks[lyr][y][x+2] != dn);
            boolean downleft = (blocks[lyr][y+1][x-1] == 0 || !BLOCK_CDS[blocks[lyr][y+1][x-1]]);// && (blocks[lyr][y+1][x] != dn && blocks[lyr][y][x-1] != dn && blocks[lyr][y+1][x-1] != dn && blocks[lyr][y+2][x] != dn && blocks[lyr][y][x-2] != dn);
            boolean downright = (blocks[lyr][y+1][x+1] == 0 || !BLOCK_CDS[blocks[lyr][y+1][x+1]]);// && (blocks[lyr][y+1][x] != dn && blocks[lyr][y][x+1] != dn && blocks[lyr][y+1][x+1] != dn && blocks[lyr][y+2][x] != dn && blocks[lyr][y][x+2] != dn);
            int[][] pixm = new int[IMAGESIZE][IMAGESIZE];
            for (dy=0; dy<8; dy++) {
                for (dx=0; dx<8; dx++) {
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
                for (dy=0; dy<4; dy++) {
                    pixm[dy][0] = 255;
                }
                for (dx=0; dx<4; dx++) {
                    pixm[0][dx] = 255;
                }
            }
            if (up && right) {
                for (dx=4; dx<8; dx++) {
                    pixm[0][dx] = 255;
                }
                for (dy=0; dy<4; dy++) {
                    pixm[dy][7] = 255;
                }
            }
            if (right && down) {
                for (dy=4; dy<8; dy++) {
                    pixm[dy][7] = 255;
                }
                for (dx=4; dx<8; dx++) {
                    pixm[7][dx] = 255;
                }
            }
            if (down && left) {
                for (dx=0; dx<4; dx++) {
                    pixm[7][dx] = 255;
                }
                for (dy=4; dy<8; dy++) {
                    pixm[dy][0] = 255;
                }
            }
            for (dy=0; dy<8; dy++) {
                for (dx=0; dx<8; dx++) {
                    if (pixm[dy][dx] == 255) {
                        for (dy2=0; dy2<8; dy2++) {
                            for (dx2=0; dx2<8; dx2++) {
                                n = (int)(255 - 32*Math.sqrt(Math.pow(dx-dx2, 2) + Math.pow(dy-dy2, 2)));
                                if (pixm[dy2][dx2] < n) {
                                    pixm[dy2][dx2] = n;
                                }
                            }
                        }
                    }
                }
            }
            for (dy=0; dy<8; dy++) {
                for (dx=0; dx<8; dx++) {
                    dirt.setRGB(dx, dy, new Color((int)(pixm[dy][dx]/255.0 * new Color(texture.getRGB(dx, dy)).getRed() + (1 - pixm[dy][dx]/255.0) * new Color(dirt.getRGB(dx, dy)).getRed()),
                                                  (int)(pixm[dy][dx]/255.0 * new Color(texture.getRGB(dx, dy)).getGreen() + (1 - pixm[dy][dx]/255.0) * new Color(dirt.getRGB(dx, dy)).getGreen()),
                                                  (int)(pixm[dy][dx]/255.0 * new Color(texture.getRGB(dx, dy)).getBlue() + (1 - pixm[dy][dx]/255.0) * new Color(dirt.getRGB(dx, dy)).getBlue())).getRGB());
                }
            }
            texture = dirt;
        }
        for (fy=0; fy<IMAGESIZE; fy++) {
            for (fx=0; fx<IMAGESIZE; fx++) {
                if (outline.getRGB(fx, fy) == -65281 || outline.getRGB(fx, fy) == -48897) {
                    image.setRGB(fx, fy, texture.getRGB(fx, fy));
                }
                else if (outline.getRGB(fx, fy) == -16777216) {
                    image.setRGB(fx, fy, -16777216);
                }
            }
        }
        return image;
    }

    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == key.VK_LEFT || key.getKeyCode() == key.VK_A) {
            queue[0] = true;
        }
        if (key.getKeyCode() == key.VK_RIGHT || key.getKeyCode() == key.VK_D) {
            queue[1] = true;
        }
        if (key.getKeyCode() == key.VK_UP || key.getKeyCode() == key.VK_W) {
            queue[2] = true;
        }
        if (key.getKeyCode() == key.VK_DOWN || key.getKeyCode() == key.VK_S) {
            queue[6] = true;
        }
        if (key.getKeyCode() == key.VK_SHIFT) {
            queue[5] = true;
        }
        if (state.equals("ingame")) {
            if (key.getKeyCode() == key.VK_ESCAPE) {
                if (ic != null) {
                    if (!ic.type.equals("workbench")) {
                        machinesx.add(icx);
                        machinesy.add(icy);
                        icmatrix[iclayer][icy][icx] = new ItemCollection(ic.type, ic.ids, ic.nums, ic.durs);
                    }
                    if (ic.type.equals("workbench")) {
                        if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                            for (i=0; i<9; i++) {
                                if (ic.ids[i] != 0) {
                                    entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, 2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                                }
                            }
                        }
                        if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                            for (i=0; i<9; i++) {
                                if (ic.ids[i] != 0) {
                                    entities.add(new Entity(icx*BLOCKSIZE, icy*BLOCKSIZE, -2, -2, ic.ids[i], ic.nums[i], ic.durs[i], 75));
                                }
                            }
                        }
                    }
                    if (ic.type.equals("furnace")) {
                        icmatrix[iclayer][icy][icx].FUELP = ic.FUELP;
                        icmatrix[iclayer][icy][icx].SMELTP = ic.SMELTP;
                        icmatrix[iclayer][icy][icx].F_ON = ic.F_ON;
                    }
                    ic = null;
                }
                else {
                    if (showInv) {
                        for (i=0; i<4; i++) {
                            if (cic.ids[i] != 0) {
                                if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                                    entities.add(new Entity(player.x, player.y, 2, -2, cic.ids[i], cic.nums[i], cic.durs[i], 75));
                                }
                                if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                                    entities.add(new Entity(player.x, player.y, -2, -2, cic.ids[i], cic.nums[i], cic.durs[i], 75));
                                }
                                inventory.removeLocationIC(cic, i, cic.nums[i]);
                            }
                        }
                    }
                    showInv = !showInv;
                }
                if (moveItem != 0) {
                    if (player.imgState.equals("still right") || player.imgState.equals("walk right 1") || player.imgState.equals("walk right 2")) {
                        entities.add(new Entity(player.x, player.y, 2, -2, moveItem, moveNum, moveDur, 75));
                    }
                    if (player.imgState.equals("still left") || player.imgState.equals("walk left 1") || player.imgState.equals("walk left 2")) {
                        entities.add(new Entity(player.x, player.y, -2, -2, moveItem, moveNum, moveDur, 75));
                    }
                    moveItem = 0;
                    moveNum = 0;
                }
            }
            if (!showTool) {
                if (key.getKeyCode() == key.VK_1) {
                    inventory.select(1);
                }
                if (key.getKeyCode() == key.VK_2) {
                    inventory.select(2);
                }
                if (key.getKeyCode() == key.VK_3) {
                    inventory.select(3);
                }
                if (key.getKeyCode() == key.VK_4) {
                    inventory.select(4);
                }
                if (key.getKeyCode() == key.VK_5) {
                    inventory.select(5);
                }
                if (key.getKeyCode() == key.VK_6) {
                    inventory.select(6);
                }
                if (key.getKeyCode() == key.VK_7) {
                    inventory.select(7);
                }
                if (key.getKeyCode() == key.VK_8) {
                    inventory.select(8);
                }
                if (key.getKeyCode() == key.VK_9) {
                    inventory.select(9);
                }
                if (key.getKeyCode() == key.VK_0) {
                    inventory.select(0);
                }
            }
        }
        char c = 0;
        if (key.getKeyCode() == key.VK_Q) c = 'q';
        if (key.getKeyCode() == key.VK_W) c = 'w';
        if (key.getKeyCode() == key.VK_E) c = 'e';
        if (key.getKeyCode() == key.VK_R) c = 'r';
        if (key.getKeyCode() == key.VK_T) c = 't';
        if (key.getKeyCode() == key.VK_Y) c = 'y';
        if (key.getKeyCode() == key.VK_U) c = 'u';
        if (key.getKeyCode() == key.VK_I) c = 'i';
        if (key.getKeyCode() == key.VK_O) c = 'o';
        if (key.getKeyCode() == key.VK_P) c = 'p';
        if (key.getKeyCode() == key.VK_A) c = 'a';
        if (key.getKeyCode() == key.VK_S) c = 's';
        if (key.getKeyCode() == key.VK_D) c = 'd';
        if (key.getKeyCode() == key.VK_F) c = 'f';
        if (key.getKeyCode() == key.VK_G) c = 'g';
        if (key.getKeyCode() == key.VK_H) c = 'h';
        if (key.getKeyCode() == key.VK_J) c = 'j';
        if (key.getKeyCode() == key.VK_K) c = 'k';
        if (key.getKeyCode() == key.VK_L) c = 'l';
        if (key.getKeyCode() == key.VK_Z) c = 'z';
        if (key.getKeyCode() == key.VK_X) c = 'x';
        if (key.getKeyCode() == key.VK_C) c = 'c';
        if (key.getKeyCode() == key.VK_V) c = 'v';
        if (key.getKeyCode() == key.VK_B) c = 'b';
        if (key.getKeyCode() == key.VK_N) c = 'n';
        if (key.getKeyCode() == key.VK_M) c = 'm';
        if (key.getKeyCode() == key.VK_1) c = '1';
        if (key.getKeyCode() == key.VK_2) c = '2';
        if (key.getKeyCode() == key.VK_3) c = '3';
        if (key.getKeyCode() == key.VK_4) c = '4';
        if (key.getKeyCode() == key.VK_5) c = '5';
        if (key.getKeyCode() == key.VK_6) c = '6';
        if (key.getKeyCode() == key.VK_7) c = '7';
        if (key.getKeyCode() == key.VK_8) c = '8';
        if (key.getKeyCode() == key.VK_9) c = '9';
        if (key.getKeyCode() == key.VK_0) c = '0';
        if (key.getKeyCode() == key.VK_SPACE) c = ' ';
        if (key.getKeyCode() == 192) c = '`';
        if (key.getKeyCode() == key.VK_MINUS) c = '-';
        if (key.getKeyCode() == key.VK_EQUALS) c = '=';
        if (key.getKeyCode() == key.VK_OPEN_BRACKET) c = '[';
        if (key.getKeyCode() == key.VK_CLOSE_BRACKET) c = ']';
        if (key.getKeyCode() == key.VK_BACK_SLASH) c = '\\';
        if (key.getKeyCode() == key.VK_COLON) c = ':';
        if (key.getKeyCode() == key.VK_QUOTE) c = '\'';
        if (key.getKeyCode() == key.VK_COMMA) c = ',';
        if (key.getKeyCode() == key.VK_PERIOD) c = '.';
        if (key.getKeyCode() == key.VK_SLASH) c = '/';

        if (queue[5]) {
            if (c == 'q') c = 'Q';
            if (c == 'w') c = 'W';
            if (c == 'e') c = 'E';
            if (c == 'r') c = 'R';
            if (c == 't') c = 'T';
            if (c == 'y') c = 'Y';
            if (c == 'u') c = 'U';
            if (c == 'i') c = 'I';
            if (c == 'o') c = 'O';
            if (c == 'p') c = 'P';
            if (c == 'a') c = 'A';
            if (c == 's') c = 'S';
            if (c == 'd') c = 'D';
            if (c == 'f') c = 'F';
            if (c == 'g') c = 'G';
            if (c == 'h') c = 'H';
            if (c == 'j') c = 'J';
            if (c == 'k') c = 'K';
            if (c == 'l') c = 'L';
            if (c == 'z') c = 'Z';
            if (c == 'x') c = 'X';
            if (c == 'c') c = 'C';
            if (c == 'v') c = 'V';
            if (c == 'b') c = 'B';
            if (c == 'n') c = 'N';
            if (c == 'm') c = 'M';
            if (c == '1') c = '!';
            if (c == '2') c = '@';
            if (c == '3') c = '#';
            if (c == '4') c = '$';
            if (c == '5') c = '%';
            if (c == '6') c = '^';
            if (c == '7') c = '&';
            if (c == '8') c = '*';
            if (c == '9') c = '(';
            if (c == '0') c = ')';
            if (c == ' ') c = ' ';
            if (c == '`') c = '~';
            if (c == '-') c = '_';
            if (c == '=') c = '+';
            if (c == '[') c = '{';
            if (c == ']') c = '}';
            if (c == '\\') c = '|';
            if (c == ';') c = ')';
            if (c == '\'') c = '"';
            if (c == ',') c = '<';
            if (c == '.') c = '>';
            if (c == '/') c = '?';
        }

        if (state.equals("new_world") && !newWorldFocus) {
            if (c != 0) {
                newWorldName.typeKey(c);
                repaint();
            }

            if (key.getKeyCode() == 8) {
                newWorldName.deleteKey();
                repaint();
            }
        }

        if (key.getKeyCode() == key.VK_EQUALS && layer < 2) {
            layer += 1;
        }
        if (key.getKeyCode() == key.VK_MINUS && layer > 0) {
            layer -= 1;
        }
    }

    public void keyReleased(KeyEvent key) {
        if (key.getKeyCode() == key.VK_LEFT || key.getKeyCode() == key.VK_A) {
            queue[0] = false;
        }
        if (key.getKeyCode() == key.VK_RIGHT || key.getKeyCode() == key.VK_D) {
            queue[1] = false;
        }
        if (key.getKeyCode() == key.VK_UP || key.getKeyCode() == key.VK_W) {
            queue[2] = false;
        }
        if (key.getKeyCode() == key.VK_SHIFT) {
            queue[5] = false;
        }
        if (key.getKeyCode() == key.VK_DOWN || key.getKeyCode() == key.VK_S) {
            queue[6] = false;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (!queue[3]) {
            queue[3] = e.getButton() == MouseEvent.BUTTON1;
        }
        if (!queue[4]) {
            queue[4] = e.getButton() == MouseEvent.BUTTON3;
        }
    }

    public void mouseReleased(MouseEvent e) {
        queue[3] = false;
        queue[4] = false;
        menuPressed = false;
    }

    public void mouseClicked(MouseEvent e) {
        //
    }

    public void mouseMoved(MouseEvent e) {
        if (mousePos != null) {
            mousePos[0] = e.getX();
            mousePos[1] = e.getY();
        }
    }

    public void mouseDragged(MouseEvent e) {
        mousePos[0] = e.getX();
        mousePos[1] = e.getY();
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

    public static boolean[] getBLOCKCDS() {
        return BLOCK_CDS;
    }

    public static Map<Integer,Boolean> getBLOCKCD() {
        return BLOCKCD;
    }

    public static Map<Short,Short> getTOOLDURS() {
        return TOOLDURS;
    }

    public static Map<Short,Short> getMAXSTACKS() {
        return MAXSTACKS;
    }

    public static Random getRandom() {
        return random;
    }

    public static Map<Short,BufferedImage> getItemImgs() {
        return itemImgs;
    }

    public static Map<Short,Integer> getARMOR() {
        return ARMOR;
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

    public static void pmsg(String msg) {
        System.out.println(msg);
    }

/*    public void pmsg(String msg) {
        pg2 = (Graphics2D) screen.createGraphics();
        if (logo_white != null && msg != null && loadTextPos != 0) {
            pg2.drawImage(logo_white,
                getWidth()/2-logo_white.getWidth()/2, 50, getWidth()/2+logo_white.getWidth()/2, logo_white.getHeight()+50,
                0, 0, logo_white.getWidth(), logo_white.getHeight(),
                null);
            pg2.setFont(loadFont);
            pg2.setColor(Color.GREEN);
            pg2.drawString(msg, getWidth()/2-200, 100+loadTextPos*13);
            if (msg.equals("Created by Radon Rosborough.")) {
                loadTextPos += 2;
            }
            else {
                loadTextPos += 1;
            }
        }
        else {
            pg2.clearRect(0, 0, getWidth(), getHeight());
            loadTextPos = 1;
        }
    }
*/
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
        try {
            log = new BufferedWriter(new FileWriter("log.txt"));
            log.write(sb.toString());
            log.close();
        }
        catch (IOException e2) {
            //
        }
        finally {
            System.out.println(sb.toString());
        }
    }

    public static int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    public static float max(float a, float b, float c) {
        return Math.max(Math.max(a, b), c);
    }

    public static double max(double a, double b, double c) {
        return Math.max(Math.max(a, b), c);
    }

    public static int max(int a, int b, int c, int d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    public static float max(float a, float b, float c, float d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    public static double max(double a, double b, double c, double d) {
        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    public static int mod(int a, int q) {
        return ((a % q) + q) % q;
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
