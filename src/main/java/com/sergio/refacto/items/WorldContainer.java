package com.sergio.refacto.items;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sergio.refacto.Entity;
import com.sergio.refacto.Inventory;
import com.sergio.refacto.Player;
import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.Directions;
import com.sergio.refacto.dto.ItemCollection;
import com.sergio.refacto.dto.ItemType;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.tools.RandomTool;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class WorldContainer implements Serializable {

    private static final int[] SKY_COLORS = {28800, 28980, 29160, 29340, 29520, 29700, 29880, 30060, 30240, 30420, 30600, 30780, 30960, 31140, 31320, 31500, 31680, 31860, 32040, 32220, 72000, 72180, 72360, 72540, 72720, 72900, 73080, 73260, 73440, 73620, 73800, 73980, 74160, 74340, 74520, 74700, 74880, 75060, 75240, 75420};

    Blocks[][][] blocks;
    Directions[][][] blocksDirections;
    Byte[][] BlocksDirectionsIntensity;
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

    int regenerationCounter1;
    int regenerationCounter2;
    int layer;
    int layerTemp;
    Blocks blockTemp;

    int mx, my, icx, icy, mining, immune;

    short moveNum, moveNumTemp;
    Items moveItem, moveItemTemp;
    int msi;

    double toolAngle, toolSpeed;

    double timeOfDay;  // 28000 (before dusk), 32000 (after dusk)
    int currentSkyLight;
    int day;

    int mobCount;

    boolean ready;
    boolean showTool;
    boolean showInv;
    boolean doMobSpawn;

    int WIDTH;
    int HEIGHT;

    int WORLDWIDTH;
    int WORLDHEIGHT;

    int resunlight = WIDTH;

    ItemCollection ic;

    boolean[][] kworlds;

    ItemCollection[][][] icmatrix;

    BufferedImage[][] worlds, fworlds;

    String version;

    static final int CHUNKBLOCKS = 96;

    public WorldContainer() {
        currentSkyLight = 28800;
        ready = true;
        WIDTH = 2400;
        HEIGHT = 2400;
        WORLDWIDTH = WIDTH / CHUNKBLOCKS + 1;
        WORLDHEIGHT = HEIGHT / CHUNKBLOCKS + 1;
        resunlight = WIDTH;
        version = "0.3_01";
    }

    public WorldContainer(Blocks[][][] blocks, Directions[][][] blocksDirections, Byte[][] BlocksDirectionsIntensity, Backgrounds[][] blocksBackgrounds, Byte[][] blocksTextureIntensity,
                          Float[][] lights, Float[][][] power, Boolean[][] drawn, Boolean[][] ldrawn, Boolean[][] rdrawn,
                          Player player, Inventory inventory, ItemCollection cic,
                          List<Entity> entities, CloudsAggregate cloudsAggregate,
                          List<Integer> machinesx, List<Integer> machinesy, Boolean[][] lsources, List<Integer> lqx, List<Integer> lqy, Boolean[][] lqd,
                          int regenerationCounter1, int regenerationCounter2, int layer, int layerTemp, Blocks blockTemp,
                          int mx, int my, int icx, int icy, int mining, int immune,
                          Items moveItem, short moveNum, Items moveItemTemp, short moveNumTemp, int msi,
                          double toolAngle, double toolSpeed, double timeOfDay, int currentSkyLight, int day, int mobCount,
                          boolean ready, boolean showTool, boolean showInv, boolean doMobSpawn,
                          int WIDTH, int HEIGHT, int WORLDWIDTH, int WORLDHEIGHT,
                          int resunlight,
                          ItemCollection ic, boolean[][] kworlds, ItemCollection[][][] icmatrix, String version) {

        this.blocks = blocks;
        this.blocksDirections = blocksDirections;
        this.BlocksDirectionsIntensity = BlocksDirectionsIntensity;
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
        this.timeOfDay = timeOfDay;
        this.currentSkyLight = currentSkyLight;
        this.day = day;
        this.mobCount = mobCount;
        this.ready = ready;
        this.showTool = showTool;
        this.showInv = showInv;
        this.doMobSpawn = doMobSpawn;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.WORLDWIDTH = WORLDWIDTH;
        this.WORLDHEIGHT = WORLDHEIGHT;
        this.resunlight = resunlight;
        this.ic = ic;
        this.kworlds = kworlds;
        this.icmatrix = icmatrix;
        this.version = version;
    }

    private void load(WorldContainer wc) {
        blocks = wc.blocks;
        blocksDirections = wc.blocksDirections;
        BlocksDirectionsIntensity = wc.BlocksDirectionsIntensity;
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
        WORLDWIDTH = wc.WORLDWIDTH;
        WORLDHEIGHT = wc.WORLDHEIGHT;
        resunlight = wc.resunlight;
        kworlds = wc.kworlds;
        ic = wc.ic;
        icmatrix = wc.icmatrix;
        version = wc.version;

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
            cic = new ItemCollection(ItemType.CIC, 5);
            inventory.renderCollection(cic);
        }
        if (ic != null) {
            inventory.renderCollection(ic);
        }
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).reloadImage();
        }

        worlds = new BufferedImage[WORLDHEIGHT][WORLDWIDTH];
        fworlds = new BufferedImage[WORLDHEIGHT][WORLDWIDTH];
    }

    public boolean loadWorld(String worldFile) {
        try {
            FileInputStream fileIn = new FileInputStream("worlds/" + worldFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            WorldContainer wc = (WorldContainer) in.readObject();
            in.close();
            fileIn.close();

            load(wc);
            worlds = new BufferedImage[WORLDHEIGHT][WORLDWIDTH];
            fworlds = new BufferedImage[WORLDHEIGHT][WORLDWIDTH];
            return true;
        } catch (Exception e) {
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
            e.printStackTrace();
        }
    }

    public void createNewWorld(int size) {
        blocks = new Blocks[3][size][size];
        blocksDirections = new Directions[3][size][size];
        BlocksDirectionsIntensity = new Byte[size][size];
        blocksBackgrounds = new Backgrounds[size][size];
        blocksTextureIntensity = new Byte[size][size];
        lights = new Float[size][size];
        power = new Float[3][size][size];
        lsources = new Boolean[size][size];

        player = new Player(WIDTH * 0.5 * TerrariaClone.BLOCKSIZE, 45);

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

        cic = new ItemCollection(ItemType.CIC, 5);
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

        icmatrix = new ItemCollection[3][HEIGHT][WIDTH];

        kworlds = new boolean[2][2];

        lqx = new ArrayList<>();
        lqy = new ArrayList<>();

        worlds = new BufferedImage[2][2];
        fworlds = new BufferedImage[2][2];
    }

    public WorldContainer getCopy() {
        return new WorldContainer(blocks, blocksDirections, BlocksDirectionsIntensity, blocksBackgrounds, blocksTextureIntensity,
                lights, power, drawn, ldrawn, rdrawn,
                player, inventory, cic,
                entities, cloudsAggregate,
                machinesx, machinesy, lsources, lqx, lqy, lqd,
                regenerationCounter1, regenerationCounter2, layer, layerTemp, blockTemp,
                mx, my, icx, icy, mining, immune,
                moveItem, moveNum, moveItemTemp, moveNumTemp, msi,
                toolAngle, toolSpeed, timeOfDay, currentSkyLight, day, mobCount,
                ready, showTool, showInv, doMobSpawn,
                WIDTH, HEIGHT, WORLDWIDTH, WORLDHEIGHT,
                resunlight,
                ic, kworlds, icmatrix, version);
    }

    public void upgradeBlocksState(int u, int v, int size) {
        for (int l = 0; l < 3; l++) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (RandomTool.nextInt(22500) == 0) {
                        Blocks t = Blocks.AIR;
                        switch (blocks[l][y][x]) {
                            case SUNFLOWER_STAGE_1:
                                if (timeOfDay >= 75913 || timeOfDay < 28883) {
                                    t = Blocks.SUNFLOWER_STAGE_2;
                                }
                                break;
                            case SUNFLOWER_STAGE_2:
                                if (timeOfDay >= 75913 || timeOfDay < 28883) {
                                    t = Blocks.SUNFLOWER_STAGE_3;
                                }
                                break;
                            case MOONFLOWER_STAGE_1:
                                if (timeOfDay >= 32302 && timeOfDay < 72093) {
                                    t = Blocks.MOONFLOWER_STAGE_2;
                                }
                                break;
                            case MOONFLOWER_STAGE_2:
                                if (timeOfDay >= 32302 && timeOfDay < 72093) {
                                    t = Blocks.MOONFLOWER_STAGE_3;
                                }
                                break;
                            case DRYWEED_STAGE_1:
                                if (checkBiome(x, y, u, v).equals("desert")) {
                                    t = Blocks.DRYWEED_STAGE_2;
                                }
                                break;
                            case DRYWEED_STAGE_2:
                                if (checkBiome(x, y, u, v).equals("desert")) {
                                    t = Blocks.DRYWEED_STAGE_3;
                                }
                                break;
                            case GREENLEAF_STAGE_1:
                                if (checkBiome(x, y, u, v).equals("jungle")) {
                                    t = Blocks.GREENLEAF_STAGE_2;
                                }
                                break;
                            case GREENLEAF_STAGE_2:
                                if (checkBiome(x, y, u, v).equals("jungle")) {
                                    t = Blocks.GREENLEAF_STAGE_3;
                                }
                                break;
                            case FROSTLEAF_STAGE_1:
                                if (checkBiome(x, y, u, v).equals("frost")) {
                                    t = Blocks.FROSTLEAF_STAGE_2;
                                }
                                break;
                            case FROSTLEAF_STAGE_2:
                                if (checkBiome(x, y, u, v).equals("frost")) {
                                    t = Blocks.FROSTLEAF_STAGE_3;
                                }
                                break;
                            case CAVEROOT_STAGE_1:
                                if (checkBiome(x, y, u, v).equals("cavern") || y >= 0/*stonelayer[x]*/) {
                                    t = Blocks.CAVEROOT_STAGE_2;
                                }
                                break;
                            case CAVEROOT_STAGE_2:
                                if (checkBiome(x, y, u, v).equals("cavern") || y >= 0/*stonelayer[x]*/) {
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
                                if (checkBiome(x, y, u, v).equals("swamp")) {
                                    t = Blocks.MARSHLEAF_STAGE_2;
                                }
                                break;
                            case MARSHLEAF_STAGE_2:
                                if (checkBiome(x, y, u, v).equals("swamp")) {
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

    public void updateGrassState(int u, int v, int size) {
        for (int l = 0; l < 3; l++) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (RandomTool.nextInt(1000) == 0) {
                        if (y >= 1 && y < HEIGHT - 1) {
                            boolean doGrassGrow = false;
                            if (blocks[l][y][x] == Blocks.DIRT && hasOpenSpace(x + u, y + v, l) && blocks[l][y + RandomTool.nextInt(3) - 1 + u][x + RandomTool.nextInt(3) - 1 + v] == Blocks.GRASS) {
                                blocks[l][y][x] = Blocks.GRASS;
                                doGrassGrow = true;
                            }
                            if (blocks[l][y][x] == Blocks.DIRT && hasOpenSpace(x + u, y + v, l) && blocks[l][y + RandomTool.nextInt(3) - 1 + u][x + RandomTool.nextInt(3) - 1 + v] == Blocks.JUNGLE_GRASS) {
                                blocks[l][y][x] = Blocks.JUNGLE_GRASS;
                                doGrassGrow = true;
                            }
                            if (blocks[l][y][x] == Blocks.MUD && hasOpenSpace(x + u, y + v, l) && blocks[l][y + RandomTool.nextInt(3) - 1 + u][x + RandomTool.nextInt(3) - 1 + v] == Blocks.SWAMP_GRASS) {
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


    public void updateTreesState(int size) {
        for (int l = 0; l < 3; l++) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (RandomTool.nextInt(1000) == 0) {
                        if (blocks[1][y][x] == Blocks.TREE_NO_BARK) {
                            blocks[1][y][x] = Blocks.TREE;
                        }
                    }
                }
            }
        }
    }

    public boolean hasOpenSpace(int x, int y, int l) {
        try {
            return (blocks[l][y - 1][x - 1] == Blocks.AIR || !blocks[l][y - 1][x - 1].isCds() ||
                    blocks[l][y - 1][x] == Blocks.AIR || !blocks[l][y - 1][x].isCds() ||
                    blocks[l][y - 1][x + 1] == Blocks.AIR || !blocks[l][y - 1][x + 1].isCds() ||
                    blocks[l][y][x - 1] == Blocks.AIR || !blocks[l][y][x - 1].isCds() ||
                    blocks[l][y][x + 1] == Blocks.AIR || !blocks[l][y][x + 1].isCds() ||
                    blocks[l][y + 1][x - 1] == Blocks.AIR || !blocks[l][y + 1][x - 1].isCds() ||
                    blocks[l][y + 1][x] == Blocks.AIR || !blocks[l][y + 1][x].isCds() ||
                    blocks[l][y + 1][x + 1] == Blocks.AIR || !blocks[l][y + 1][x + 1].isCds());
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public String checkBiome(int x, int y, int u, int v) {
        int desert = 0;
        int frost = 0;
        int swamp = 0;
        int jungle = 0;
        int cavern = 0;
        for (int x2 = x - 15; x2 < x + 16; x2++) {
            for (int y2 = y - 15; y2 < y + 16; y2++) {
                if (x2 + u >= 0 && x2 + u < WIDTH && y2 + v >= 0 && y2 + v < HEIGHT) {
                    if (blocks[1][y2 + v][x2 + u] == Blocks.SAND || blocks[1][y2 + v][x2 + u] == Blocks.SANDSTONE) {
                        desert += 1;
                    } else if (blocks[1][y2 + v][x2 + u] != Blocks.AIR) {
                        desert -= 1;
                    }
                    if (blocks[1][y2 + v][x2 + u] == Blocks.DIRT || blocks[1][y2 + v][x2 + u] == Blocks.GRASS || blocks[1][y2 + v][x2 + u] == Blocks.JUNGLE_GRASS) {
                        jungle += 1;
                    } else if (blocks[1][y2 + v][x2 + u] != Blocks.AIR) {
                        jungle -= 1;
                    }
                    if (blocks[1][y2 + v][x2 + u] == Blocks.SWAMP_GRASS || blocks[1][y2 + v][x2 + u] == Blocks.MUD) {
                        swamp += 1;
                    } else if (blocks[1][y2 + v][x2 + u] != Blocks.AIR) {
                        swamp -= 1;
                    }
                    if (blocks[1][y2 + v][x2 + u] == Blocks.SNOW) {
                        frost += 1;
                    } else if (blocks[1][y2 + v][x2 + u] != Blocks.AIR) {
                        frost -= 1;
                    }
                    if (blocksBackgrounds[y2 + v][x2 + u] == Backgrounds.EMPTY) {
                        cavern += 1;
                    }
                    if (blocks[1][y2 + v][x2 + u] == Blocks.DIRT || blocks[1][y2 + v][x2 + u] == Blocks.STONE) {
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

    public void updateSkyLights() {
        currentSkyLight = SKY_COLORS[0];
        for (int i = 0; i < SKY_COLORS.length; i++) {
            if (timeOfDay >= SKY_COLORS[i]) {
                currentSkyLight = SKY_COLORS[i];
            }
        }
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
}
