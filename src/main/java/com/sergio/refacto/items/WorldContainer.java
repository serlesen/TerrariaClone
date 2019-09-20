package com.sergio.refacto.items;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sergio.refacto.Entity;
import com.sergio.refacto.Inventory;
import com.sergio.refacto.Player;
import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.dto.Biome;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.Directions;
import com.sergio.refacto.dto.EntityType;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.ItemCollection;
import com.sergio.refacto.dto.ItemType;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.services.TimeService;
import com.sergio.refacto.tools.MathTool;
import com.sergio.refacto.tools.RandomTool;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static com.sergio.refacto.dto.Constants.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC)
public class WorldContainer implements Serializable {

    /** Size of the COMPLETE World in block units. */
    private static final int WIDTH = 2400;
    private static final int HEIGHT = 2400;

    /** Block size in pixels. */
    public static final int BLOCK_SIZE = 16;

    /** Amount of blocks in the chunk. */
    public static final int CHUNK_BLOCKS = 96;
    /** Size of the chunk in pixels. */
    public static final int CHUNK_SIZE = CHUNK_BLOCKS * BLOCK_SIZE;

    /** Size of the World in Chunk units. */
    public static final int WORLD_WIDTH = WIDTH / CHUNK_BLOCKS + 1;
    public static final int WORLD_HEIGHT = HEIGHT / CHUNK_BLOCKS + 1;

    private static final int[][] CL = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    
    Blocks[][][] blocks;
    Directions[][][] blocksDirections;
    Byte[][] blocksDirectionsIntensity;
    Backgrounds[][] blocksBackgrounds;
    Byte[][] blocksTextureIntensity;
    Float[][] lights;
    Float[][][] power;
    Boolean[][] lsources;
    List<Integer> lqx, lqy;
    Boolean[][] lqd;
    Boolean[][] drawn, ldrawn, rdrawn;
    Player player;
    Inventory inventory;
    ItemCollection cic;
    List<Entity> entities;
    CloudsAggregate cloudsAggregate;
    List<Integer> machinesx, machinesy;

    private List<Integer> pqx, pqy, zqx, zqy, pzqx, pzqy;
    private Boolean[][] zqd, pqd, pzqd;
    public Byte[][] zqn;
    public Byte[][][] pzqn;

    public Boolean[][][] arbprd;
    public List<Integer> updatex, updatey, updatet, updatel;
    public Boolean[][] wcnct;

    int regenerationCounter1;
    private int regenerationCounter2;
    int layer;
    private int layerTemp;
    Blocks blockTemp;

    int mx, my, icx, icy, mining, immune;

    short moveNum, moveNumTemp;
    Items moveItem, moveItemTemp;
    int msi;

    double toolAngle, toolSpeed;

    /** Tool handled by the player. */
    public BufferedImage tool;

    int currentSkyLight;

    /** Those fields are only there to serialize them when save/load a world. */
    private double timeOfDay;  // 28000 (before dusk), 32000 (after dusk)
    private int day;

    int mobCount;

    boolean ready;
    boolean showTool;
    boolean showInv;
    boolean doMobSpawn;

    private int resunlight;

    ItemCollection ic;

    boolean[][] kworlds;

    ItemCollection[][][] icmatrix;

    BufferedImage[][] worlds, fworlds;

    private String version;

    public WorldContainer() {
        currentSkyLight = 28800;
        ready = true;
        resunlight = WIDTH;
        version = "0.3_01";
    }

    private WorldContainer(Blocks[][][] blocks, Directions[][][] blocksDirections, Byte[][] BlocksDirectionsIntensity, Backgrounds[][] blocksBackgrounds, Byte[][] blocksTextureIntensity,
                           Float[][] lights, Float[][][] power, Boolean[][] drawn, Boolean[][] ldrawn, Boolean[][] rdrawn,
                           Player player, Inventory inventory, ItemCollection cic,
                           List<Entity> entities, CloudsAggregate cloudsAggregate,
                           List<Integer> machinesx, List<Integer> machinesy, Boolean[][] lsources, List<Integer> lqx, List<Integer> lqy, Boolean[][] lqd,
                           int regenerationCounter1, int regenerationCounter2, int layer, int layerTemp, Blocks blockTemp,
                           int mx, int my, int icx, int icy, int mining, int immune,
                           Items moveItem, short moveNum, Items moveItemTemp, short moveNumTemp, int msi,
                           double toolAngle, double toolSpeed, int currentSkyLight, int mobCount,
                           boolean ready, boolean showTool, boolean showInv, boolean doMobSpawn,
                           int resunlight,
                           ItemCollection ic, boolean[][] kworlds, ItemCollection[][][] icmatrix, String version) {

        this.blocks = blocks;
        this.blocksDirections = blocksDirections;
        this.blocksDirectionsIntensity = BlocksDirectionsIntensity;
        this.blocksBackgrounds = blocksBackgrounds;
        this.blocksTextureIntensity = blocksTextureIntensity;
        this.lights = lights;
        this.power = power;
        this.drawn = drawn;
        this.ldrawn = ldrawn;
        this.rdrawn = rdrawn;
        this.player = player;
        this.inventory = inventory;
        this.cic = cic;
        this.entities = entities;
        this.cloudsAggregate = cloudsAggregate;
        this.machinesx = machinesx;
        this.machinesy = machinesy;
        this.lsources = lsources;
        this.lqx = lqx;
        this.lqy = lqy;
        this.lqd = lqd;
        this.regenerationCounter1 = regenerationCounter1;
        this.regenerationCounter2 = regenerationCounter2;
        this.layer = layer;
        this.layerTemp = layerTemp;
        this.blockTemp = blockTemp;
        this.mx = mx;
        this.my = my;
        this.icx = icx;
        this.icy = icy;
        this.mining = mining;
        this.immune = immune;
        this.moveItem = moveItem;
        this.moveNum = moveNum;
        this.moveItemTemp = moveItemTemp;
        this.moveNumTemp = moveNumTemp;
        this.msi = msi;
        this.toolAngle = toolAngle;
        this.toolSpeed = toolSpeed;
        this.currentSkyLight = currentSkyLight;
        this.mobCount = mobCount;
        this.ready = ready;
        this.showTool = showTool;
        this.showInv = showInv;
        this.doMobSpawn = doMobSpawn;
        this.resunlight = resunlight;
        this.ic = ic;
        this.kworlds = kworlds;
        this.icmatrix = icmatrix;
        this.version = version;

        this.timeOfDay = TimeService.getInstance().getTime();
        this.day = TimeService.getInstance().getDay();
    }

    private void load(WorldContainer wc) {
        blocks = wc.blocks;
        blocksDirections = wc.blocksDirections;
        blocksDirectionsIntensity = wc.blocksDirectionsIntensity;
        blocksBackgrounds = wc.blocksBackgrounds;
        blocksTextureIntensity = wc.blocksTextureIntensity;
        lights = wc.lights;
        power = wc.power;
        player = wc.player;
        inventory = wc.inventory;
        cic = wc.cic;
        entities = wc.entities;
        cloudsAggregate = wc.cloudsAggregate;
        machinesx = wc.machinesx;
        machinesy = wc.machinesy;
        lsources = wc.lsources;
        lqx = wc.lqx;
        lqy = wc.lqy;
        lqd = wc.lqd;
        regenerationCounter1 = wc.regenerationCounter1;
        regenerationCounter2 = wc.regenerationCounter2;
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
        currentSkyLight = wc.currentSkyLight;
        mobCount = wc.mobCount;
        ready = wc.ready;
        showTool = wc.showTool;
        showInv = wc.showInv;
        doMobSpawn = wc.doMobSpawn;
        resunlight = wc.resunlight;
        kworlds = wc.kworlds;
        ic = wc.ic;
        icmatrix = wc.icmatrix;
        version = wc.version;
        TimeService.getInstance().load(wc.timeOfDay, wc.day);

        drawn = new Boolean[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                drawn[y][x] = false;
            }
        }
        ldrawn = new Boolean[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                ldrawn[y][x] = false;
            }
        }
        rdrawn = new Boolean[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                rdrawn[y][x] = false;
            }
        }

        player.reloadImage();
        inventory.reloadImage();
        if (cic != null) {
            inventory.renderCollection(cic);
        }
        else {
            cic = new ItemCollection(ItemType.CIC);
            inventory.renderCollection(cic);
        }
        if (ic != null) {
            inventory.renderCollection(ic);
        }
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).reloadImage();
        }

        worlds = new BufferedImage[WORLD_HEIGHT][WORLD_WIDTH];
        fworlds = new BufferedImage[WORLD_HEIGHT][WORLD_WIDTH];
    }

    public boolean loadWorld(String worldFile) {
        try {
            FileInputStream fileIn = new FileInputStream("worlds/" + worldFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            WorldContainer wc = (WorldContainer) in.readObject();
            in.close();
            fileIn.close();

            load(wc);
            worlds = new BufferedImage[WORLD_HEIGHT][WORLD_WIDTH];
            fworlds = new BufferedImage[WORLD_HEIGHT][WORLD_WIDTH];
            return true;
        } catch (Exception e) {
            log.warn("Error while loading a world", e);
            return false;
        }
    }

    public void saveWorld(String worldName) {
        try {
            WorldContainer wc = getCopy();
            FileOutputStream fileOut = new FileOutputStream("worlds/" + worldName + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(wc);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            log.error("Error while saving a world", e);
        }
    }

    public void createNewWorld(int sunlightlevel) {
        blocks = new Blocks[TerrariaClone.LAYER_SIZE][TerrariaClone.SIZE][TerrariaClone.SIZE];
        blocksDirections = new Directions[TerrariaClone.LAYER_SIZE][TerrariaClone.SIZE][TerrariaClone.SIZE];
        blocksDirectionsIntensity = new Byte[TerrariaClone.SIZE][TerrariaClone.SIZE];
        blocksBackgrounds = new Backgrounds[TerrariaClone.SIZE][TerrariaClone.SIZE];
        blocksTextureIntensity = new Byte[TerrariaClone.SIZE][TerrariaClone.SIZE];
        lights = new Float[TerrariaClone.SIZE][TerrariaClone.SIZE];
        power = new Float[TerrariaClone.LAYER_SIZE][TerrariaClone.SIZE][TerrariaClone.SIZE];
        lsources = new Boolean[TerrariaClone.SIZE][TerrariaClone.SIZE];

        arbprd = new Boolean[TerrariaClone.LAYER_SIZE][TerrariaClone.SIZE][TerrariaClone.SIZE];
        wcnct = new Boolean[TerrariaClone.SIZE][TerrariaClone.SIZE];
        zqn = new Byte[TerrariaClone.SIZE][TerrariaClone.SIZE];
        pzqn = new Byte[TerrariaClone.LAYER_SIZE][TerrariaClone.SIZE][TerrariaClone.SIZE];

        pqx = new ArrayList<>();
        pqy = new ArrayList<>();
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

        player = new Player(WIDTH * 0.5 * BLOCK_SIZE, 45);

        inventory = new Inventory();

        if (DebugContext.ITEMS != null) {
            if (DebugContext.ITEMS.equals("normal")) {
                inventory.addItem(Items.IRRADIUM_PICK, (short) 1);
                inventory.addItem(Items.IRRADIUM_AXE, (short) 1);
                inventory.addItem(Items.IRRADIUM_SWORD, (short) 1);
                inventory.addItem(Items.STONE_BRICKS, (short) 100);
                inventory.addItem(Items.WOODEN_TORCH, (short) 100);
                inventory.addItem(Items.COAL_TORCH, (short) 100);
                inventory.addItem(Items.LUMENSTONE_TORCH, (short) 100);
                inventory.addItem(Items.WORKBENCH, (short) 5);
                inventory.addItem(Items.FURNACE, (short) 5);
                inventory.addItem(Items.STONE_LIGHTER, (short) 1);
                inventory.addItem(Items.COAL, (short) 100);
                inventory.addItem(Items.MAGNETITE_ORE, (short) 100);
                inventory.addItem(Items.DIRT, (short) 100);
                inventory.addItem(Items.STONE, (short) 100);
                inventory.addItem(Items.WOOD, (short) 100);
            } else if (DebugContext.ITEMS.equals("tools")) {
                inventory.addItem(Items.WOODEN_PICK, (short) 1);
                inventory.addItem(Items.WOODEN_AXE, (short) 1);
                inventory.addItem(Items.WOODEN_SWORD, (short) 1);
                inventory.addItem(Items.STONE_PICK, (short) 1);
                inventory.addItem(Items.STONE_AXE, (short) 1);
                inventory.addItem(Items.STONE_SWORD, (short) 1);
                inventory.addItem(Items.COPPER_PICK, (short) 1);
                inventory.addItem(Items.COPPER_AXE, (short) 1);
                inventory.addItem(Items.IRON_AXE, (short) 1);
                inventory.addItem(Items.IRON_PICK, (short) 1);
                inventory.addItem(Items.SILVER_AXE, (short) 1);
                inventory.addItem(Items.GOLD_AXE, (short) 1);
                inventory.addItem(Items.SILVER_PICK, (short) 1);
                inventory.addItem(Items.COPPER_SWORD, (short) 1);
                inventory.addItem(Items.IRON_SWORD, (short) 1);
                inventory.addItem(Items.GOLD_PICK, (short) 1);
                inventory.addItem(Items.SILVER_SWORD, (short) 1);
                inventory.addItem(Items.STONE_LIGHTER, (short) 1);
                inventory.addItem(Items.ZINC_PICK, (short) 1);
                inventory.addItem(Items.ZINC_AXE, (short) 1);
                inventory.addItem(Items.ZINC_SWORD, (short) 1);
                inventory.addItem(Items.RHYMESTONE_PICK, (short) 1);
                inventory.addItem(Items.RHYMESTONE_AXE, (short) 1);
                inventory.addItem(Items.RHYMESTONE_SWORD, (short) 1);
                inventory.addItem(Items.OBDURITE_PICK, (short) 1);
                inventory.addItem(Items.OBDURITE_AXE, (short) 1);
                inventory.addItem(Items.OBDURITE_SWORD, (short) 1);
                inventory.addItem(Items.ALUMINUM_PICK, (short) 1);
                inventory.addItem(Items.ALUMINUM_AXE, (short) 1);
                inventory.addItem(Items.ALUMINUM_SWORD, (short) 1);
                inventory.addItem(Items.LEAD_PICK, (short) 1);
                inventory.addItem(Items.LEAD_AXE, (short) 1);
                inventory.addItem(Items.LEAD_SWORD, (short) 1);
                inventory.addItem(Items.MAGNETITE_PICK, (short) 1);
                inventory.addItem(Items.MAGNETITE_AXE, (short) 1);
                inventory.addItem(Items.MAGNETITE_SWORD, (short) 1);
                inventory.addItem(Items.IRRADIUM_PICK, (short) 1);
                inventory.addItem(Items.IRRADIUM_AXE, (short) 1);
                inventory.addItem(Items.IRRADIUM_SWORD, (short) 1);

                inventory.addItem(Items.GOLD_SWORD, (short) 1);
            } else if (DebugContext.ITEMS.equals("testing")) {
                inventory.addItem(Items.IRRADIUM_PICK, (short) 1);
                inventory.addItem(Items.IRRADIUM_AXE, (short) 1);
                inventory.addItem(Items.ZYTHIUM_WIRE, (short) 100);
                inventory.addItem(Items.WOOD, (short) 100);
                inventory.addItem(Items.WOODEN_TORCH, (short) 100);
                inventory.addItem(Items.COAL_TORCH, (short) 100);
                inventory.addItem(Items.LUMENSTONE_TORCH, (short) 100);
                inventory.addItem(Items.ZYTHIUM_TORCH, (short) 100);
                inventory.addItem(Items.ZYTHIUM_LAMP, (short) 100);
                inventory.addItem(Items.LEVER, (short) 100);
                inventory.addItem(Items.FURNACE, (short) 100);
                inventory.addItem(Items.STONE_LIGHTER, (short) 1);
                inventory.addItem(Items.FROSTLEAF, (short) 100);
                inventory.addItem(Items.SKYSTONE, (short) 100);
                inventory.addItem(Items.ZYTHIUM_AMPLIFIER, (short) 100);
                inventory.addItem(Items.ZYTHIUM_INVERTER, (short) 100);
                inventory.addItem(Items.BUTTON, (short) 100);
                inventory.addItem(Items.WOODEN_PRESSURE_PLATE, (short) 100);
                inventory.addItem(Items.STONE_PRESSURE_PLATE, (short) 100);
                inventory.addItem(Items.ZYTHIUM_PRESSURE_PLATE, (short) 100);
                inventory.addItem(Items.ZYTHIUM_DELAYER_1, (short) 100);
                inventory.addItem(Items.ZYTHIUM_DELAYER_2, (short) 100);
                inventory.addItem(Items.ZYTHIUM_DELAYER_4, (short) 100);
                inventory.addItem(Items.ZYTHIUM_DELAYER_8, (short) 100);
                inventory.addItem(Items.WRENCH, (short) 1);
            }
        }

        cic = new ItemCollection(ItemType.CIC);
        inventory.renderCollection(cic);

        inventory.renderCollection(TerrariaClone.armor);

        toolAngle = 4.7;
        mining = 0;

        mx = 0; my = 0;
        moveItem = Items.EMPTY;
        moveNum = 0;

        entities = new ArrayList<>(0);

        cloudsAggregate = new CloudsAggregate();

        machinesx = new ArrayList<>(0);
        machinesy = new ArrayList<>(0);

        icmatrix = new ItemCollection[TerrariaClone.LAYER_SIZE][HEIGHT][WIDTH];

        kworlds = new boolean[2][2];

        lqx = new ArrayList<>();
        lqy = new ArrayList<>();

        worlds = new BufferedImage[2][2];
        fworlds = new BufferedImage[2][2];

        // FIXME new Boolean[HEIGHT][WIDTH];
        drawn = new Boolean[TerrariaClone.SIZE][TerrariaClone.SIZE];
        rdrawn = new Boolean[TerrariaClone.SIZE][TerrariaClone.SIZE];
        ldrawn = new Boolean[TerrariaClone.SIZE][TerrariaClone.SIZE];
//        drawn = new Boolean[HEIGHT][WIDTH];
//        rdrawn = new Boolean[HEIGHT][WIDTH];
//        ldrawn = new Boolean[HEIGHT][WIDTH];

        log.info("-> Adding light sources...");

        resolvePowerMatrix();
        resolveLightMatrix(sunlightlevel);
    }

    private WorldContainer getCopy() {
        return new WorldContainer(blocks, blocksDirections, blocksDirectionsIntensity, blocksBackgrounds, blocksTextureIntensity,
                lights, power, drawn, ldrawn, rdrawn,
                player, inventory, cic,
                entities, cloudsAggregate,
                machinesx, machinesy, lsources, lqx, lqy, lqd,
                regenerationCounter1, regenerationCounter2, layer, layerTemp, blockTemp,
                mx, my, icx, icy, mining, immune,
                moveItem, moveNum, moveItemTemp, moveNumTemp, msi,
                toolAngle, toolSpeed, currentSkyLight, mobCount,
                ready, showTool, showInv, doMobSpawn,
                resunlight,
                ic, kworlds, icmatrix, version);
    }

    public void upgradeBlocksState(int blockOffsetU, int blockOffsetV) {
        for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
            for (int y = 0; y < TerrariaClone.SIZE; y++) {
                for (int x = 0; x < TerrariaClone.SIZE; x++) {
                    if (RandomTool.nextInt(22500) == 0) {
                        Blocks t = Blocks.AIR;
                        switch (blocks[l][y][x]) {
                            case SUNFLOWER_STAGE_1:
                                if (TimeService.getInstance().isNight()) {
                                    t = Blocks.SUNFLOWER_STAGE_2;
                                }
                                break;
                            case SUNFLOWER_STAGE_2:
                                if (TimeService.getInstance().isNight()) {
                                    t = Blocks.SUNFLOWER_STAGE_3;
                                }
                                break;
                            case MOONFLOWER_STAGE_1:
                                if (TimeService.getInstance().isNight()) {
                                    t = Blocks.MOONFLOWER_STAGE_2;
                                }
                                break;
                            case MOONFLOWER_STAGE_2:
                                if (TimeService.getInstance().isNight()) {
                                    t = Blocks.MOONFLOWER_STAGE_3;
                                }
                                break;
                            case DRYWEED_STAGE_1:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.DESERT) {
                                    t = Blocks.DRYWEED_STAGE_2;
                                }
                                break;
                            case DRYWEED_STAGE_2:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.DESERT) {
                                    t = Blocks.DRYWEED_STAGE_3;
                                }
                                break;
                            case GREENLEAF_STAGE_1:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.JUNGLE) {
                                    t = Blocks.GREENLEAF_STAGE_2;
                                }
                                break;
                            case GREENLEAF_STAGE_2:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.JUNGLE) {
                                    t = Blocks.GREENLEAF_STAGE_3;
                                }
                                break;
                            case FROSTLEAF_STAGE_1:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.FROST) {
                                    t = Blocks.FROSTLEAF_STAGE_2;
                                }
                                break;
                            case FROSTLEAF_STAGE_2:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.FROST) {
                                    t = Blocks.FROSTLEAF_STAGE_3;
                                }
                                break;
                            case CAVEROOT_STAGE_1:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.CAVERN || y >= 0/*stonelayer[x]*/) {
                                    t = Blocks.CAVEROOT_STAGE_2;
                                }
                                break;
                            case CAVEROOT_STAGE_2:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.CAVERN || y >= 0/*stonelayer[x]*/) {
                                    t = Blocks.CAVEROOT_STAGE_3;
                                }
                                break;
                            case SKYBLOSSOM_STAGE_1:
                                if (y <= HEIGHT * 0.08 && RandomTool.nextInt(3) == 0 || y <= HEIGHT * 0.04) {
                                    t = Blocks.SKYBLOSSOM_STAGE_2;
                                }
                                break;
                            case SKYBLOSSOM_STAGE_2:
                                if (y <= HEIGHT * 0.08 && RandomTool.nextInt(3) == 0 || y <= HEIGHT * 0.04) {
                                    t = Blocks.SKYBLOSSOM_STAGE_3;
                                }
                                break;
                            case VOID_ROT_STAGE_1:
                                if (y >= HEIGHT * 0.98) {
                                    t = Blocks.VOID_ROT_STAGE_2;
                                }
                                break;
                            case VOID_ROT_STAGE_2:
                                if (y >= HEIGHT * 0.98) {
                                    t = Blocks.VOID_ROT_STAGE_3;
                                }
                                break;
                            case MARSHLEAF_STAGE_1:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.SWAMP) {
                                    t = Blocks.MARSHLEAF_STAGE_2;
                                }
                                break;
                            case MARSHLEAF_STAGE_2:
                                if (checkBiome(x, y, blockOffsetU, blockOffsetV) == Biome.SWAMP) {
                                    t = Blocks.MARSHLEAF_STAGE_3;
                                }
                                break;
                            default:
                                break;
                        }
                        if (t != Blocks.AIR) {
                            blocks[l][y][x] = t;
                            drawn[y][x] = false;
                        }
                    }
                }
            }
        }
    }

    public void updateGrassState(int blockOffsetU, int blockOffsetV) {
        for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
            for (int y = 0; y < TerrariaClone.SIZE; y++) {
                for (int x = 0; x < TerrariaClone.SIZE; x++) {
                    if (RandomTool.nextInt(1000) == 0) {
                        if (y >= 1 && y < HEIGHT - 1) {
                            boolean doGrassGrow = false;
                            if (blocks[l][y][x] == Blocks.DIRT && hasOpenSpace(x + blockOffsetU, y + blockOffsetV, l) && blocks[l][y + RandomTool.nextInt(3) - 1 + blockOffsetU][x + RandomTool.nextInt(3) - 1 + blockOffsetV] == Blocks.GRASS) {
                                blocks[l][y][x] = Blocks.GRASS;
                                doGrassGrow = true;
                            }
                            if (blocks[l][y][x] == Blocks.DIRT && hasOpenSpace(x + blockOffsetU, y + blockOffsetV, l) && blocks[l][y + RandomTool.nextInt(3) - 1 + blockOffsetU][x + RandomTool.nextInt(3) - 1 + blockOffsetV] == Blocks.JUNGLE_GRASS) {
                                blocks[l][y][x] = Blocks.JUNGLE_GRASS;
                                doGrassGrow = true;
                            }
                            if (blocks[l][y][x] == Blocks.MUD && hasOpenSpace(x + blockOffsetU, y + blockOffsetV, l) && blocks[l][y + RandomTool.nextInt(3) - 1 + blockOffsetU][x + RandomTool.nextInt(3) - 1 + blockOffsetV] == Blocks.SWAMP_GRASS) {
                                blocks[l][y][x] = Blocks.SWAMP_GRASS;
                                doGrassGrow = true;
                            }
                            if (doGrassGrow) {
                                for (int y2 = y - 1; y2 < y + 2; y2++) {
                                    for (int x2 = x - 1; x2 < x + 2; x2++) {
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
    }


    public void updateTreesState() {
        for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
            for (int y = 0; y < TerrariaClone.SIZE; y++) {
                for (int x = 0; x < TerrariaClone.SIZE; x++) {
                    if (RandomTool.nextInt(1000) == 0) {
                        if (blocks[l][y][x] == Blocks.TREE_NO_BARK) {
                            blocks[l][y][x] = Blocks.TREE;
                        }
                    }
                }
            }
        }
    }

    private boolean hasOpenSpace(int x, int y, int l) {
        if (x <= 0 || y <= 0 || x >= TerrariaClone.SIZE || y >= TerrariaClone.SIZE) {
            // edges are not considered as open space
            return false;
        }

        return blocks[l][y - 1][x - 1] == Blocks.AIR || !blocks[l][y - 1][x - 1].isCds() ||
                blocks[l][y - 1][x] == Blocks.AIR || !blocks[l][y - 1][x].isCds() ||
                blocks[l][y - 1][x + 1] == Blocks.AIR || !blocks[l][y - 1][x + 1].isCds() ||
                blocks[l][y][x - 1] == Blocks.AIR || !blocks[l][y][x - 1].isCds() ||
                blocks[l][y][x + 1] == Blocks.AIR || !blocks[l][y][x + 1].isCds() ||
                blocks[l][y + 1][x - 1] == Blocks.AIR || !blocks[l][y + 1][x - 1].isCds() ||
                blocks[l][y + 1][x] == Blocks.AIR || !blocks[l][y + 1][x].isCds() ||
                blocks[l][y + 1][x + 1] == Blocks.AIR || !blocks[l][y + 1][x + 1].isCds();
    }

    public Biome checkBiome(int x, int y, int blockOffsetU, int blockOffsetV) {
        int desert = 0;
        int frost = 0;
        int swamp = 0;
        int jungle = 0;
        int cavern = 0;
        for (int x2 = x - 15; x2 < x + 16; x2++) {
            for (int y2 = y - 15; y2 < y + 16; y2++) {
                if (x2 + blockOffsetU >= 0 && x2 + blockOffsetU < WIDTH && y2 + blockOffsetV >= 0 && y2 + blockOffsetV < HEIGHT) {
                    if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.SAND || blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.SANDSTONE) {
                        desert += 1;
                    } else if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] != Blocks.AIR) {
                        desert -= 1;
                    }
                    if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.DIRT || blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.GRASS || blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.JUNGLE_GRASS) {
                        jungle += 1;
                    } else if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] != Blocks.AIR) {
                        jungle -= 1;
                    }
                    if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.SWAMP_GRASS || blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.MUD) {
                        swamp += 1;
                    } else if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] != Blocks.AIR) {
                        swamp -= 1;
                    }
                    if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.SNOW) {
                        frost += 1;
                    } else if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] != Blocks.AIR) {
                        frost -= 1;
                    }
                    if (blocksBackgrounds[y2 + blockOffsetV][x2 + blockOffsetU] == Backgrounds.EMPTY) {
                        cavern += 1;
                    }
                    if (blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.DIRT || blocks[1][y2 + blockOffsetV][x2 + blockOffsetU] == Blocks.STONE) {
                        cavern += 1;
                    } else {
                        cavern -= 1;
                    }
                }
            }
        }
        if (desert > 0) {
            return Biome.DESERT;
        }
        if (jungle > 0) {
            return Biome.JUNGLE;
        }
        if (swamp > 0) {
            return Biome.SWAMP;
        }
        if (frost > 0) {
            return Biome.FROST;
        }
        if (cavern > 0) {
            return Biome.CAVERN;
        }
        return Biome.OTHER;
    }

    public void updateSkyLights() {
        currentSkyLight = TimeService.getInstance().getSkyLights();
    }

    public void updateHealthPoints() {
        if (regenerationCounter1 == 0) {
            if (regenerationCounter2 == 0) {
                if (player.healthPoints < player.totalHealthPoints) {
                    player.healthPoints += 1;
                    regenerationCounter2 = 125;
                }
            } else {
                regenerationCounter2 -= 1;
            }
        } else {
            regenerationCounter1 -= 1;
        }
    }
    
    public void updateMachinesState() {
        for (int j = 0; j < machinesx.size(); j++) {
            int x = machinesx.get(j);
            int y = machinesy.get(j);
            for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
                if (icmatrix[l][y][x] != null && icmatrix[l][y][x].getType() == ItemType.FURNACE) {
                    if (icmatrix[l][y][x].isFurnaceOn()) {
                        if (icmatrix[l][y][x].getItems()[1] == Items.EMPTY) {
                            if (TerrariaClone.FUELS.get(icmatrix[l][y][x].getItems()[2]) != null) {
                                inventory.addLocationIC(icmatrix[l][y][x], 1, icmatrix[l][y][x].getItems()[2], (short) 1);
                                inventory.removeLocationIC(icmatrix[l][y][x], 2, (short) 1);
                                icmatrix[l][y][x].setFUELP(1);
                            } else {
                                icmatrix[l][y][x].setFurnaceOn(false);
                                removeBlockLighting(x, y);
                                blocks[l][y][x] = Blocks.FURNACE;
                                rdrawn[y][x] = false;
                            }
                        }
                        if (TerrariaClone.FUELS.get(icmatrix[l][y][x].getItems()[1]) != null) {
                            icmatrix[l][y][x].setFUELP(icmatrix[l][y][x].getFUELP() - TerrariaClone.FUELS.get(icmatrix[l][y][x].getItems()[1]));
                            if (icmatrix[l][y][x].getFUELP() < 0) {
                                icmatrix[l][y][x].setFUELP(0);
                                inventory.removeLocationIC(icmatrix[l][y][x], 1, icmatrix[l][y][x].getNums()[1]);
                            }
                            for (int i = 0; i < TerrariaClone.FRI1.size(); i++) {
                                if (icmatrix[l][y][x].getItems()[0] == TerrariaClone.FRI1.get(i) && icmatrix[l][y][x].getNums()[0] >= TerrariaClone.FRN1.get(i)) {
                                    icmatrix[l][y][x].setSMELTP(icmatrix[l][y][x].getSMELTP() + Blocks.findByIndex(icmatrix[l][y][x].getItems()[1].getIndex()).getFSpeed());
                                    if (icmatrix[l][y][x].getSMELTP() > 1) {
                                        icmatrix[l][y][x].setSMELTP(0);
                                        inventory.removeLocationIC(icmatrix[l][y][x], 0, TerrariaClone.FRN1.get(i));
                                        inventory.addLocationIC(icmatrix[l][y][x], 3, TerrariaClone.FRI2.get(i), TerrariaClone.FRN2.get(i));
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        icmatrix[l][y][x].setSMELTP(icmatrix[l][y][x].getSMELTP() - 0.00025);
                        if (icmatrix[l][y][x].getSMELTP() < 0) {
                            icmatrix[l][y][x].setSMELTP(0);
                        }
                    }
                }
            }
        }
    }
    
    public void updateItemCollection() {
        if (ic != null && ic.getType() == ItemType.FURNACE) {
            if (ic.isFurnaceOn()) {
                if (ic.getItems()[1] == Items.EMPTY) {
                    if (TerrariaClone.FUELS.get(ic.getItems()[2]) != null) {
                        inventory.addLocationIC(ic, 1, ic.getItems()[2], (short) 1);
                        inventory.removeLocationIC(ic, 2, (short) 1);
                        ic.setFUELP(1);
                    } else {
                        ic.setFurnaceOn(false);
                        removeBlockLighting(icx, icy);
                        blocks[layer][icy][icx] = Blocks.FURNACE;
                        rdrawn[icy][icx] = false;
                    }
                }
                if (TerrariaClone.FUELS.get(ic.getItems()[1]) != null) {
                    ic.setFUELP(ic.getFUELP() - TerrariaClone.FUELS.get(ic.getItems()[1]));
                    if (ic.getFUELP() < 0) {
                        ic.setFUELP(0);
                        inventory.removeLocationIC(ic, 1, ic.getNums()[1]);
                    }
                    for (int i = 0; i < TerrariaClone.FRI1.size(); i++) {
                        if (ic.getItems()[0] == TerrariaClone.FRI1.get(i) && ic.getNums()[0] >= TerrariaClone.FRN1.get(i)) {
                            ic.setSMELTP(ic.getSMELTP() + Blocks.findByIndex(ic.getItems()[1].getIndex()).getFSpeed());
                            if (ic.getSMELTP() > 1) {
                                ic.setSMELTP(0);
                                inventory.removeLocationIC(ic, 0, TerrariaClone.FRN1.get(i));
                                inventory.addLocationIC(ic, 3, TerrariaClone.FRI2.get(i), TerrariaClone.FRN2.get(i));
                            }
                            break;
                        }
                    }
                }
            } else {
                ic.setSMELTP(ic.getSMELTP() - 0.00025);
                if (ic.getSMELTP() < 0) {
                    ic.setSMELTP(0);
                }
            }
            inventory.updateIC(ic, -1);
        }
    }

    public void updateToolAngle() {
        if (showTool) {
            toolAngle += toolSpeed;
            if (toolAngle >= 7.8) {
                toolAngle = 4.8;
                showTool = false;
            }
        }
    }

    /**
     * When the entities are hit by the player.
     */
    public void updateEntitiesHit() {
        if (showTool) {
            Point tp1, tp2, tp3, tp4, tp5;
            if (player.imgState == ImageState.STILL_RIGHT || player.imgState.isWalkRight()) {
                tp1 = new Point((int) (player.x + Player.WIDTH / 2 + 6), (int) (player.y + Player.HEIGHT / 2));
                tp2 = new Point((int) (player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 2 * Math.cos(toolAngle) + tool.getHeight() * 2 * Math.sin(toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 2 * Math.sin(toolAngle) - tool.getHeight() * 2 * Math.cos(toolAngle)));
                tp3 = new Point((int) (player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 1 * Math.cos(toolAngle) + tool.getHeight() * 1 * Math.sin(toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 1 * Math.sin(toolAngle) - tool.getHeight() * 1 * Math.cos(toolAngle)));
                tp4 = new Point((int) (player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 0.5 * Math.cos(toolAngle) + tool.getHeight() * 0.5 * Math.sin(toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 0.5 * Math.sin(toolAngle) - tool.getHeight() * 0.5 * Math.cos(toolAngle)));
                tp5 = new Point((int) (player.x + Player.WIDTH / 2 + 6 + tool.getWidth() * 1.5 * Math.cos(toolAngle) + tool.getHeight() * 1.5 * Math.sin(toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 1.5 * Math.sin(toolAngle) - tool.getHeight() * 1.5 * Math.cos(toolAngle)));
            } else { // LEFT
                tp1 = new Point((int) (player.x + Player.WIDTH / 2 - 6), (int) (player.y + Player.HEIGHT / 2));
                tp2 = new Point((int) (player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 2 * Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight() * 2 * Math.sin((Math.PI * 1.5) - toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 2 * Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight() * 2 * Math.cos((Math.PI * 1.5) - toolAngle)));
                tp3 = new Point((int) (player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 1 * Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight() * 1 * Math.sin((Math.PI * 1.5) - toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 1 * Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight() * 1 * Math.cos((Math.PI * 1.5) - toolAngle)));
                tp4 = new Point((int) (player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 0.5 * Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight() * 0.5 * Math.sin((Math.PI * 1.5) - toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 0.5 * Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight() * 0.5 * Math.cos((Math.PI * 1.5) - toolAngle)));
                tp5 = new Point((int) (player.x + Player.WIDTH / 2 - 6 + tool.getWidth() * 1.5 * Math.cos((Math.PI * 1.5) - toolAngle) + tool.getHeight() * 1.5 * Math.sin((Math.PI * 1.5) - toolAngle)),
                        (int) (player.y + Player.HEIGHT / 2 + tool.getWidth() * 1.5 * Math.sin((Math.PI * 1.5) - toolAngle) - tool.getHeight() * 1.5 * Math.cos((Math.PI * 1.5) - toolAngle)));
            }
            for (int i = entities.size() - 1; i >= 0; i--) {
                if (entities.get(i).getEntityType() != null && !entities.get(i).isNohit() && showTool && (entities.get(i).getRect().contains(tp1) || entities.get(i).getRect().contains(tp2) || entities.get(i).getRect().contains(tp3) || entities.get(i).getRect().contains(tp4) || entities.get(i).getRect().contains(tp5)) && (entities.get(i).getEntityType() != EntityType.BEE || RandomTool.nextInt(4) == 0)) {
                    if (entities.get(i).hit(inventory.tool().getDamage(), player)) {
                        List<Items> dropList = entities.get(i).drops();
                        for (int j = 0; j < dropList.size(); j++) {
                            entities.add(new Entity(entities.get(i).getX(), entities.get(i).getY(), RandomTool.nextInt(4) - 2, -1, dropList.get(j), (short) 1));
                        }
                        entities.remove(i);
                    }
                    if (!Arrays.asList(TOOL_LIST).contains(inventory.items[inventory.selection])) {
                        inventory.durs[inventory.selection] -= 1;
                    } else {
                        inventory.durs[inventory.selection] -= 2;
                    }
                    if (inventory.durs[inventory.selection] <= 0) {
                        inventory.removeLocation(inventory.selection, inventory.nums[inventory.selection]);
                    }
                }
            }
        }
    }

    public void removeBlockLighting(int ux, int uy) {
        removeBlockLighting(ux, uy, layer);
    }

    private void removeBlockLighting(int ux, int uy, int layer) {
        int n = findNonLayeredBlockLightSource(ux, uy);
        if (n != 0) {
            lsources[uy][ux] = isNonLayeredBlockLightSource(ux, uy, layer);
            for (int axl = -n; axl < n + 1; axl++) {
                for (int ayl = -n; ayl < n + 1; ayl++) {
                    if (Math.abs(axl) + Math.abs(ayl) <= n && uy + ayl >= 0 && uy + ayl < HEIGHT && lights[uy + ayl][ux + axl] != 0) {
                        addTileToZQueue(ux + axl, uy + ayl);
                        lights[uy + ayl][ux + axl] = (float) 0;
                    }
                }
            }
            for (int axl = -n - TerrariaClone.BRIGHTEST; axl < n + 1 + TerrariaClone.BRIGHTEST; axl++) {
                for (int ayl = -n - TerrariaClone.BRIGHTEST; ayl < n + 1 + TerrariaClone.BRIGHTEST; ayl++) {
                    if (Math.abs(axl) + Math.abs(ayl) <= n + TerrariaClone.BRIGHTEST && uy + ayl >= 0 && uy + ayl < HEIGHT) {
                        if (lsources[uy + ayl][ux + axl]) {
                            addTileToQueue(ux + axl, uy + ayl);
                        }
                    }
                }
            }
        }
    }

    private int findNonLayeredBlockLightSource(int ux, int uy) {
        int n = 0;
        if (blocks[0][uy][ux] != Blocks.AIR) {
            n = Math.max(blocks[0][uy][ux].getLights(), n);
        }
        if (blocks[1][uy][ux] != Blocks.AIR) {
            n = Math.max(blocks[1][uy][ux].getLights(), n);
        }
        if (blocks[2][uy][ux] != Blocks.AIR) {
            n = Math.max(blocks[2][uy][ux].getLights(), n);
        }
        return n;
    }

    private boolean isNonLayeredBlockLightSource(int ux, int uy, int layer) {
        return (layer != 0 && blocks[0][uy][ux] != Blocks.AIR && blocks[0][uy][ux].getLights() != 0 ||
                layer != 1 && blocks[1][uy][ux] != Blocks.AIR && blocks[1][uy][ux].getLights() != 0 ||
                layer != 2 && blocks[2][uy][ux] != Blocks.AIR && blocks[2][uy][ux].getLights() != 0);
    }

    private void addTileToZQueue(int ux, int uy) {
        if (!zqd[uy][ux]) {
            zqx.add(ux);
            zqy.add(uy);
            zqn[uy][ux] = (byte) (float) lights[uy][ux];
            zqd[uy][ux] = true;
        }
    }

    private void addTileToQueue(int ux, int uy) {
        if (!lqd[uy][ux]) {
            lqx.add(ux);
            lqy.add(uy);
            lqd[uy][ux] = true;
        }
    }

    public void addTileToPQueue(int ux, int uy) {
        if (!pqd[uy][ux]) {
            pqx.add(ux);
            pqy.add(uy);
            pqd[uy][ux] = true;
        }
    }

    private void addTileToPZQueue(int ux, int uy) {
        if (!pzqd[uy][ux]) {
            pzqx.add(ux);
            pzqy.add(uy);
            pzqn[0][uy][ux] = (byte) (float) power[0][uy][ux];
            pzqn[1][uy][ux] = (byte) (float) power[1][uy][ux];
            pzqn[2][uy][ux] = (byte) (float) power[2][uy][ux];
            pzqd[uy][ux] = true;
        }
    }

    public void resolveLightMatrix(int sunlightlevel) {
        int x = 0, y = 0;
        try {
            for (int j = 0; j < lqx.size(); j++) {
                x = lqx.get(j);
                y = lqy.get(j);
                if (lsources[y][x]) {
                    int n = findBlockLightSource(x, y);
                    if (isReachedBySunlight(x, y)) {
                        lights[y][x] = MathTool.max(lights[y][x], n, sunlightlevel);
                    } else {
                        lights[y][x] = Math.max(lights[y][x], n);
                    }
                    addTileToZQueue(x, y);
                }
                for (int i = 0; i < CL.length; i++) {
                    int x2 = x + CL[i][0];
                    int y2 = y + CL[i][1];
                    if (y2 >= 0 && y2 < HEIGHT) {
                        if (!blocks[1][y2][x2].isLTrans()) {
                            if (lights[y2][x2] <= lights[y][x] - (float) 1.0) {
                                addTileToZQueue(x2, y2);
                                lights[y2][x2] = lights[y][x] - (float) 1.0;
                                addTileToQueue(x2, y2);
                            }
                        } else {
                            if (lights[y2][x2] <= lights[y][x] - (float) 2.0) {
                                addTileToZQueue(x2, y2);
                                lights[y2][x2] = lights[y][x] - (float) 2.0;
                                addTileToQueue(x2, y2);
                            }
                        }
                    }
                }

                lqd[y][x] = false;
            }

            lqx.clear();
            lqy.clear();
        } catch (IndexOutOfBoundsException e) {
            log.warn("Out of Bounds at " + y + "/" + x, e);
        }
        for (int i = 0; i < zqx.size(); i++) {
            x = zqx.get(i);
            y = zqy.get(i);
            if ((int) (float) lights[y][x] != zqn[y][x]) {
                rdrawn[y][x] = false;
            }
            zqd[y][x] = false;
        }
        zqx.clear();
        zqy.clear();
    }

    public void addSunLighting(int ux, int uy, int sunlightlevel) { // And including
        for (int y = 0; y < uy; y++) {
            if (blocks[1][y][ux].isLTrans()) {
                return;
            }
        }
        boolean addSources = false;
        for (int y = uy; y < HEIGHT - 1; y++) {
            if (blocks[1][y + 1][ux - 1].isLTrans() || blocks[1][y + 1][ux + 1].isLTrans()) {
                addSources = true;
            }
            if (addSources) {
                addTileToQueue(ux, y);
            }
            if (blocks[1][y][ux].isLTrans()) {
                return;
            }
            addTileToZQueue(ux, y);
            lights[y][ux] = (float) sunlightlevel;
            lsources[y][ux] = true;
        }
    }

    public void redoBlockLighting(int ux, int uy) {
        for (int ax = -TerrariaClone.BRIGHTEST; ax < TerrariaClone.BRIGHTEST + 1; ax++) {
            for (int ay = -TerrariaClone.BRIGHTEST; ay < TerrariaClone.BRIGHTEST + 1; ay++) {
                if (Math.abs(ax) + Math.abs(ay) <= TerrariaClone.BRIGHTEST && uy + ay >= 0 && uy + ay < HEIGHT) {
                    addTileToZQueue(ux + ax, uy + ay);
                    lights[uy + ay][ux + ax] = (float) 0;
                }
            }
        }
        for (int ax = -TerrariaClone.BRIGHTEST * 2; ax < TerrariaClone.BRIGHTEST * 2 + 1; ax++) {
            for (int ay = -TerrariaClone.BRIGHTEST * 2; ay < TerrariaClone.BRIGHTEST * 2 + 1; ay++) {
                if (Math.abs(ax) + Math.abs(ay) <= TerrariaClone.BRIGHTEST * 2 && uy + ay >= 0 && uy + ay < HEIGHT) {
                    if (lsources[uy + ay][ux + ax]) {
                        addTileToQueue(ux + ax, uy + ay);
                    }
                }
            }
        }
    }

    public void removeSunLighting(int ux, int uy, int sunlightlevel) { // And including
        for (int y = 0; y < uy; y++) {
            if (blocks[1][y][ux].isLTrans()) {
                return;
            }
        }
        int y;
        for (y = uy; y < HEIGHT; y++) {
            lsources[y][ux] = isBlockLightSource(ux, y);
            if (y != uy && blocks[1][y][ux].isLTrans()) {
                break;
            }
        }
        for (int ax = -sunlightlevel; ax < sunlightlevel + 1; ax++) {
            for (int ay = -sunlightlevel; ay < sunlightlevel + (y - uy) + 1; ay++) {
                if (uy + ay >= 0 && uy + ay < WIDTH) {
                    addTileToZQueue(ux + ax, uy + ay);
                    lights[uy + ay][ux + ax] = (float) 0;
                }
            }
        }
        for (int ax = -sunlightlevel - TerrariaClone.BRIGHTEST; ax < sunlightlevel + 1 + TerrariaClone.BRIGHTEST; ax++) {
            for (int ay = -sunlightlevel - TerrariaClone.BRIGHTEST; ay < sunlightlevel + (y - uy) + 1 + TerrariaClone.BRIGHTEST; ay++) {
                if (uy + ay >= 0 && uy + ay < HEIGHT) {
                    if (lsources[uy + ay][ux + ax]) {
                        addTileToQueue(ux + ax, uy + ay);
                    }
                }
            }
        }
    }

    private boolean isBlockLightSource(int ux, int uy) {
        return (blocks[0][uy][ux] != Blocks.AIR && blocks[0][uy][ux].getLights() != 0 ||
                blocks[1][uy][ux] != Blocks.AIR && blocks[1][uy][ux].getLights() != 0 ||
                blocks[2][uy][ux] != Blocks.AIR && blocks[2][uy][ux].getLights() != 0);
    }

    private int findBlockLightSource(int ux, int uy) {
        int n = 0;
        if (blocks[0][uy][ux] != Blocks.AIR) {
            n = Math.max(blocks[0][uy][ux].getLights(), n);
        }
        if (blocks[1][uy][ux] != Blocks.AIR) {
            n = Math.max(blocks[1][uy][ux].getLights(), n);
        }
        if (blocks[2][uy][ux] != Blocks.AIR) {
            n = Math.max(blocks[2][uy][ux].getLights(), n);
        }
        return n;
    }

    private boolean isReachedBySunlight(int ux, int uy) {
        for (int ay = 0; ay < uy + 1; ay++) {
            if (blocks[1][ay][ux].isLTrans()) {
                return false;
            }
        }
        return true;
    }

    public void addBlockLighting(int ux, int uy) {
        int n = findNonLayeredBlockLightSource(ux, uy);
        if (n != 0) {
            addTileToZQueue(ux, uy);
            lights[uy][ux] = Math.max(lights[uy][ux], n);
            lsources[uy][ux] = true;
            addTileToQueue(ux, uy);
        }
    }

    public void addAdjacentTilesToPQueue(int ux, int uy) {
        for (int i = 0; i < CL.length; i++) {
            if (uy + CL[i][1] >= 0 && uy + CL[i][1] < HEIGHT) {
                addTileToPQueue(ux + CL[i][0], uy + CL[i][1]);
            }
        }
    }

    public void addAdjacentTilesToPQueueConditionally(int ux, int uy) {
        for (int i = 0; i < CL.length; i++) {
            for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
                if (uy + CL[i][1] >= 0 && uy + CL[i][1] < HEIGHT && power[l][uy + CL[i][1]][ux + CL[i][0]] > 0) {
                    addTileToPQueue(ux + CL[i][0], uy + CL[i][1]);
                }
            }
        }
    }

    public void resolvePowerMatrix() {
        int x = 0, y = 0;
        try {
            for (int j = 0; j < pqx.size(); j++) {
                x = pqx.get(j);
                y = pqy.get(j);
                for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
                    if (blocks[l][y][x].isPower()) {
                        if (!blocks[l][y][x].isCompleteZythiumDelayer()) {
                            addTileToPQueue(x, y);
                            power[l][y][x] = (float) 5;
                        }
                    }
                }
                for (int i = 0; i < CL.length; i++) {
                    int x2 = x + CL[i][0];
                    int y2 = y + CL[i][1];
                    if (y2 >= 0 && y2 < HEIGHT) {
                        for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
                            if (power[l][y][x] > 0) {
                                if (blocks[l][y][x].getConduct() >= 0 && blocks[l][y2][x2].isReceive() && !(blocks[l][y2][x2].isCompleteZythiumAmplifier() && x < x2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        blocks[l][y2][x2].isCompleteZythiumAmplifier() && y < y2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        blocks[l][y2][x2].isCompleteZythiumAmplifier() && x > x2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        blocks[l][y2][x2].isCompleteZythiumAmplifier() && y > y2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[l][y2][x2] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(blocks[l][y][x].isCompleteZythiumAmplifier() && x < x2 && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                                blocks[l][y][x].isCompleteZythiumAmplifier() && y < y2 && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                                blocks[l][y][x].isCompleteZythiumAmplifier() && x > x2 && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                                blocks[l][y][x].isCompleteZythiumAmplifier() && y > y2 && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[l][y][x] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(blocks[l][y2][x2].isCompleteZythiumInverter() && x < x2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                blocks[l][y2][x2].isCompleteZythiumInverter() && y < y2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                blocks[l][y2][x2].isCompleteZythiumInverter() && x > x2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                blocks[l][y2][x2].isCompleteZythiumInverter() && y > y2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_UP && blocks[l][y2][x2] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(blocks[l][y][x].isCompleteZythiumInverter() && x < x2 && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                blocks[l][y][x].isCompleteZythiumInverter() && y < y2 && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                blocks[l][y][x].isCompleteZythiumInverter() && x > x2 && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                blocks[l][y][x].isCompleteZythiumInverter() && y > y2 && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_UP && blocks[l][y][x] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(blocks[l][y2][x2].isCompleteZythiumDelayer() && x < x2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                blocks[l][y2][x2].isCompleteZythiumDelayer() && y < y2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                blocks[l][y2][x2].isCompleteZythiumDelayer() && x > x2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                blocks[l][y2][x2].isCompleteZythiumDelayer() && y > y2 && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[l][y2][x2] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON) &&
                                        !(blocks[l][y][x].isCompleteZythiumDelayer() && x < x2 && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                blocks[l][y][x].isCompleteZythiumDelayer() && y < y2 && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                blocks[l][y][x].isCompleteZythiumDelayer() && x > x2 && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                blocks[l][y][x].isCompleteZythiumDelayer() && y > y2 && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[l][y][x] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                                    if (power[l][y2][x2] <= power[l][y][x] - blocks[l][y][x].getConduct()) {
                                        addTileToPZQueue(x2, y2);
                                        if (blocks[l][y2][x2].isZythiumDelayerAll()) {
                                            log.info("[DEBUG1]");
                                            updatex.add(x2);
                                            updatey.add(y2);
                                            updatet.add(TerrariaClone.DDELAY.get(blocks[l][y2][x2]));
                                            updatel.add(l);
                                        } else {
                                            power[l][y2][x2] = power[l][y][x] - (float) blocks[l][y][x].getConduct();
                                            if (blocks[l][y2][x2].getConduct() >= 0 && wcnct[y2][x2]) {
                                                if (l == 0) {
                                                    if (blocks[1][y2][x2].isReceive()) {
                                                        power[1][y2][x2] = power[0][y2][x2] - (float) blocks[0][y2][x2].getConduct();
                                                    }
                                                    if (blocks[2][y2][x2].isReceive()) {
                                                        power[2][y2][x2] = power[0][y2][x2] - (float) blocks[0][y2][x2].getConduct();
                                                    }
                                                }
                                                if (l == 1) {
                                                    if (blocks[0][y2][x2].isReceive()) {
                                                        power[0][y2][x2] = power[1][y2][x2] - (float) blocks[1][y2][x2].getConduct();
                                                    }
                                                    if (blocks[2][y2][x2].isReceive()) {
                                                        power[2][y2][x2] = power[1][y2][x2] - (float) blocks[1][y2][x2].getConduct();
                                                    }
                                                }
                                                if (l == 2) {
                                                    if (blocks[0][y2][x2].isReceive()) {
                                                        power[0][y2][x2] = power[2][y2][x2] - (float) blocks[2][y2][x2].getConduct();
                                                    }
                                                    if (blocks[1][y2][x2].isReceive()) {
                                                        power[1][y2][x2] = power[2][y2][x2] - (float) blocks[2][y2][x2].getConduct();
                                                    }
                                                }
                                            }
                                        }
                                        if (!(blocks[l][y2][x2].isZythiumInverterAll())) {
                                            addTileToPQueue(x2, y2);
                                        }
                                    }
                                    if (power[l][y][x] - blocks[l][y][x].getConduct() > 0 && blocks[l][y2][x2].isZythiumInverterAll()) {
                                        removeBlockPower(x2, y2, l);
                                        blocks[l][y2][x2] = Blocks.turnZythiumInverterOn(blocks[l][y2][x2]);
                                        removeBlockLighting(x2, y2);
                                        rdrawn[y2][x2] = false;
                                    }
                                }
                            }
                        }
                    }
                }
                pqd[y][x] = false;
                for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
                    log.info("[resolvePowerMatrix] " + x + " " + y + " " + l + " " + blocks[l][y][x] + " " + power[l][y][x]);
                    if (power[l][y][x] > 0) {
                        if (blocks[l][y][x] == Blocks.ZYTHIUM_LAMP) {
                            blocks[l][y][x] = Blocks.ZYTHIUM_LAMP_ON;
                            addBlockLighting(x, y);
                            rdrawn[y][x] = false;
                        }
                        if (blocks[l][y][x].isZythiumAmplifierAll()) {
                            log.info("Processed amplifier at " + x + " " + y);
                            blocks[l][y][x] = Blocks.turnZythiumAmplifierOn(blocks[l][y][x]);
                            addTileToPQueue(x, y);
                            addBlockLighting(x, y);
                            rdrawn[y][x] = false;
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
            for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
                if (blocks[l][y][x].isZythiumWire() && (int) (float) power[l][y][x] != pzqn[l][y][x]) {
                    removeBlockLighting(x, y, 0);
                    blocks[l][y][x] = TerrariaClone.WIREP.get((int) (float) power[l][y][x]);
                    addBlockLighting(x, y);
                    rdrawn[y][x] = false;
                }
            }
            pzqd[y][x] = false;
        }
        pzqx.clear();
        pzqy.clear();
    }

    public void removeBlockPower(int ux, int uy, int lyr) {
        removeBlockPower(ux, uy, lyr, true);
    }

    public void removeBlockPower(int ux, int uy, int lyr, boolean turnOffDelayer) {
        arbprd[lyr][uy][ux] = true;
        log.info("[rbp ] " + ux + " " + uy + " " + lyr + " " + turnOffDelayer);
        if (!(blocks[lyr][uy][ux].isZythiumDelayerOnAll() && turnOffDelayer)) {
            int ax3, ay3;
            for (int ir = 0; ir < CL.length; ir++) {
                ax3 = ux + CL[ir][0];
                ay3 = uy + CL[ir][1];
                if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                    if (!(power[lyr][ay3][ax3] == power[lyr][uy][ux] - blocks[lyr][uy][ux].getConduct()) &&
                            (!(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || blocks[lyr][ay3][ax3].isCompleteZythiumInverter()) ||
                                    !(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                            blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                            blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                            blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                            !(blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                    blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                    blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                    blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                            !(blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                    blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                    blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                    blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
                        log.info("Added tile " + ax3 + " " + ay3 + " to PQueue.");
                        addTileToPQueue(ax3, ay3);
                    }
                }
            }
            for (int ir = 0; ir < CL.length; ir++) {
                ax3 = ux + CL[ir][0];
                ay3 = uy + CL[ir][1];
                log.info(blocks[lyr][ay3][ax3] + " " + power[lyr][ay3][ax3]);
                if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                    log.info(power[lyr][uy][ux] + " " + power[lyr][ay3][ax3] + " " + blocks[lyr][uy][ux].getConduct());
                    if (power[lyr][ay3][ax3] == power[lyr][uy][ux] - blocks[lyr][uy][ux].getConduct()) {
                        if (!(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || blocks[lyr][ay3][ax3].isCompleteZythiumInverter()) ||
                                !(blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux < ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy < ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux > ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy > ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux < ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy < ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux > ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy > ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux < ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy < ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux > ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy > ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                            if (!arbprd[lyr][ay3][ax3]) {
                                rbpRecur(ax3, ay3, lyr);
                                if (blocks[lyr][ay3][ax3].getConduct() >= 0 && wcnct[ay3][ax3]) {
                                    if (lyr == 0) {
                                        if (blocks[1][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 1);
                                            if (blocks[1][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (blocks[2][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 2);
                                            if (blocks[2][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                    if (lyr == 1) {
                                        if (blocks[0][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 0);
                                            if (blocks[0][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (blocks[2][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 2);
                                            if (blocks[2][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                    if (lyr == 2) {
                                        if (blocks[0][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 0);
                                            if (blocks[0][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                        if (blocks[1][ay3][ax3].isReceive()) {
                                            rbpRecur(ax3, ay3, 1);
                                            if (blocks[1][ay3][ax3].isPower()) {
                                                addTileToPQueue(ax3, ay3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (blocks[lyr][ay3][ax3] == Blocks.ZYTHIUM_LAMP_ON || (blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || blocks[lyr][ay3][ax3].isCompleteZythiumInverter() || blocks[lyr][ay3][ax3].isCompleteZythiumDelayer()) &&
                        !(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                        !(blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                        !(blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                    if (blocks[lyr][ay3][ax3].isZythiumInverterOnAll()) {
                        blocks[lyr][ay3][ax3] = Blocks.turnZythiumInverterOff(blocks[lyr][ay3][ax3]);
                        log.info("Adding power for inverter at (" + ax3 + ", " + ay3 + ").");
                        addBlockPower(ax3, ay3);
                        addBlockLighting(ax3, ay3);
                        rdrawn[ay3][ax3] = false;
                    }
                    arbprd[lyr][uy][ux] = false;
                    removeBlockPower(ax3, ay3, lyr);
                }
            }
        }
        if (blocks[lyr][uy][ux] == Blocks.ZYTHIUM_LAMP_ON) {
            removeBlockLighting(ux, uy);
            blocks[lyr][uy][ux] = Blocks.ZYTHIUM_LAMP;
            rdrawn[uy][ux] = false;
        }
        if (blocks[lyr][uy][ux].isZythiumAmplifierOnAll()) {
            blockTemp = blocks[lyr][uy][ux];
            blocks[lyr][uy][ux] = Blocks.turnZythiumAmplifierOff(blocks[lyr][uy][ux]);
            removeBlockPower(ux, uy, lyr);
            removeBlockLighting(ux, uy);
            rdrawn[uy][ux] = false;
        }
        if (turnOffDelayer && blocks[lyr][uy][ux].isCompleteZythiumDelayer()) {
            log.info("???");
            updatex.add(ux);
            updatey.add(uy);
            updatet.add(TerrariaClone.DDELAY.get(blocks[lyr][uy][ux]));
            updatel.add(lyr);
        }
        if (!(blocks[lyr][uy][ux].isZythiumDelayerOnAll() && turnOffDelayer)) {
            power[lyr][uy][ux] = (float) 0;
        }
        arbprd[lyr][uy][ux] = false;
    }

    public void redoBlockPower(int ux, int uy, int lyr) {
        if (blocks[lyr][uy][ux].isPower() || blocks[lyr][uy][ux].isZythiumWire()) {
            addAdjacentTilesToPQueue(ux, uy);
        } else {
            removeBlockPower(ux, uy, lyr);
        }
    }

    public void addBlockPower(int ux, int uy) {
        if (blocks[1][uy][ux].isPower()) {
            if (blocks[1][uy][ux].isCompleteZythiumDelayer()) {
                log.info("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(TerrariaClone.DDELAY.get(blocks[1][uy][ux]));
                updatel.add(1);
            } else {
                addTileToPZQueue(ux, uy);
                power[1][uy][ux] = (float) 5;
                if (blocks[1][uy][ux].getConduct() >= 0 && wcnct[uy][ux]) {
                    if (blocks[0][uy][ux].isReceive()) {
                        power[0][uy][ux] = power[1][uy][ux] - (float) blocks[1][uy][ux].getConduct();
                    }
                    if (blocks[2][uy][ux].isReceive()) {
                        power[2][uy][ux] = power[1][uy][ux] - (float) blocks[1][uy][ux].getConduct();
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
        if (blocks[0][uy][ux].isPower()) {
            if (blocks[1][uy][ux].isCompleteZythiumDelayer()) {
                log.info("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(TerrariaClone.DDELAY.get(blocks[0][uy][ux]));
                updatel.add(0);
            } else {
                addTileToPZQueue(ux, uy);
                power[0][uy][ux] = (float) 5;
                if (blocks[0][uy][ux].getConduct() >= 0 && wcnct[uy][ux]) {
                    if (blocks[1][uy][ux].isReceive()) {
                        power[1][uy][ux] = power[0][uy][ux] - (float) blocks[0][uy][ux].getConduct();
                    }
                    if (blocks[2][uy][ux].isReceive()) {
                        power[2][uy][ux] = power[0][uy][ux] - (float) blocks[0][uy][ux].getConduct();
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
        if (blocks[2][uy][ux].isPower()) {
            if (blocks[1][uy][ux].isCompleteZythiumDelayer()) {
                log.info("Whaaat?");
                updatex.add(ux);
                updatey.add(uy);
                updatet.add(TerrariaClone.DDELAY.get(blocks[2][uy][ux]));
                updatel.add(2);
            } else {
                addTileToPZQueue(ux, uy);
                power[2][uy][ux] = (float) 5;
                if (blocks[2][uy][ux].getConduct() >= 0 && wcnct[uy][ux]) {
                    if (blocks[0][uy][ux].isReceive()) {
                        power[0][uy][ux] = power[2][uy][ux] - (float) blocks[2][uy][ux].getConduct();
                    }
                    if (blocks[1][uy][ux].isReceive()) {
                        power[1][uy][ux] = power[2][uy][ux] - (float) blocks[2][uy][ux].getConduct();
                    }
                }
                addTileToPQueue(ux, uy);
            }
        }
    }

    public void rbpRecur(int ux, int uy, int lyr) {
        arbprd[lyr][uy][ux] = true;
        log.info("[rbpR] " + ux + " " + uy);
        addTileToPZQueue(ux, uy);
        boolean[] remember = { false, false, false, false };
        int ax3, ay3;
        for (int ir = 0; ir < CL.length; ir++) {
            ax3 = ux + CL[ir][0];
            ay3 = uy + CL[ir][1];
            if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                if (power[lyr][ay3][ax3] != 0 && !(power[lyr][ay3][ax3] == power[lyr][uy][ux] - blocks[lyr][uy][ux].getConduct()) &&
                        (!(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || blocks[lyr][ay3][ax3].isCompleteZythiumInverter()) ||
                                !(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
                    log.info("Added tile " + ax3 + " " + ay3 + " to PQueue.");
                    addTileToPQueue(ax3, ay3);
                    remember[ir] = true;
                }
            }
        }
        for (int ir = 0; ir < CL.length; ir++) {
            log.info("[liek srsly man?] " + ir);
            ax3 = ux + CL[ir][0];
            ay3 = uy + CL[ir][1];
            log.info("[rpbRecur2] " + ax3 + " " + ay3 + " " + power[lyr][ay3][ax3]);
            if (ay3 >= 0 && ay3 < HEIGHT && power[lyr][ay3][ax3] != 0) {
                log.info("[rbpRecur] " + power[lyr][ay3][ax3] + " " + power[lyr][uy][ux] + " " + blocks[lyr][uy][ux].getConduct());
                if ((power[lyr][ay3][ax3] == power[lyr][uy][ux] - blocks[lyr][uy][ux].getConduct()) &&
                        (!(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier()) ||
                                !(blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux < ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                                        blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy < ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                                        blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && ux > ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                                        blocks[lyr][uy][ux].isCompleteZythiumAmplifier() && uy > ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                                        !(blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux < ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy < ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumInverter() && ux > ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumInverter() && uy > ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                                        !(blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux < ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy < ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumDelayer() && ux > ax3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                                                blocks[lyr][uy][ux].isCompleteZythiumDelayer() && uy > ay3 && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[lyr][uy][ux] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON))) {
                    if (!arbprd[lyr][ay3][ax3]) {
                        rbpRecur(ax3, ay3, lyr);
                        if (blocks[lyr][ay3][ax3].getConduct() >= 0 && wcnct[ay3][ax3]) {
                            if (lyr == 0) {
                                if (blocks[1][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 1);
                                    if (blocks[1][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (blocks[2][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 2);
                                    if (blocks[2][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            } else if (lyr == 1) {
                                if (blocks[0][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 0);
                                    if (blocks[0][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (blocks[2][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 2);
                                    if (blocks[2][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            } else if (lyr == 2) {
                                if (blocks[0][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 0);
                                    if (blocks[0][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                                if (blocks[1][ay3][ax3].isReceive()) {
                                    rbpRecur(ax3, ay3, 1);
                                    if (blocks[1][ay3][ax3].isPower()) {
                                        addTileToPQueue(ax3, ay3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (blocks[lyr][ay3][ax3] == Blocks.ZYTHIUM_LAMP_ON || (blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() || blocks[lyr][ay3][ax3].isCompleteZythiumInverter() || blocks[lyr][ay3][ax3].isCompleteZythiumDelayer()) &&
                    !(blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_RIGHT_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_DOWN_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_LEFT_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumAmplifier() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_AMPLIFIER_UP_ON) &&
                    !(blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_RIGHT_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_DOWN_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_LEFT_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumInverter() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_INVERTER_UP_ON) &&
                    !(blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux < ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_RIGHT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_RIGHT_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy < ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_DOWN_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_DOWN_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && ux > ax3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_LEFT_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_LEFT_ON ||
                            blocks[lyr][ay3][ax3].isCompleteZythiumDelayer() && uy > ay3 && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_1_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_2_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_4_DELAY_UP_ON && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP && blocks[lyr][ay3][ax3] != Blocks.ZYTHIUM_DELAYER_8_DELAY_UP_ON)) {
                if (blocks[lyr][ay3][ax3].isZythiumInverterOnAll()) {
                    blocks[lyr][ay3][ax3] = Blocks.turnZythiumInverterOff(blocks[lyr][ay3][ax3]);
                    log.info("Adding power for inverter at (" + ax3 + ", " + ay3 + ").");
                    addBlockPower(ax3, ay3);
                    addBlockLighting(ax3, ay3);
                    rdrawn[ay3][ax3] = false;
                }
                removeBlockPower(ax3, ay3, lyr);
            }
        }
        for (int ir = 0; ir < CL.length; ir++) {
            if (remember[ir] && uy + CL[ir][1] >= 0 && uy + CL[ir][1] < HEIGHT) {
                power[lyr][uy + CL[ir][1]][ux + CL[ir][0]] = (float) 5;
            }
        }
        power[lyr][uy][ux] = (float) 0;
        arbprd[lyr][uy][ux] = false;
    }
    
    public void updateFurnaceState() {
        icmatrix[layer][icy][icx].setFUELP(ic.getFUELP());
        icmatrix[layer][icy][icx].setSMELTP(ic.getSMELTP());
        icmatrix[layer][icy][icx].setFurnaceOn(ic.isFurnaceOn());
    }
    
    public void setItemCollection(ItemType item, int layer, int ux, int uy) {
        if (icmatrix[layer][uy][ux] != null && icmatrix[layer][uy][ux].getType() == item) {
            ic = new ItemCollection(item, icmatrix[layer][uy][ux].getItems(), icmatrix[layer][uy][ux].getNums(), icmatrix[layer][uy][ux].getDurs());
        } else {
            ic = new ItemCollection(item);
        }
        icx = ux;
        icy = uy;
        inventory.renderCollection(ic);
        showInv = true;
    }
}
