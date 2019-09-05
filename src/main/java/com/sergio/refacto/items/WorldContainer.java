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
import com.sergio.refacto.dto.ItemCollection;
import com.sergio.refacto.Player;
import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.ItemType;
import com.sergio.refacto.dto.Items;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class WorldContainer implements Serializable {

    Integer[][][] blocks;
    Byte[][][] blockds;
    Byte[][] blockdns;
    Byte[][] blockbgs;
    Byte[][] blockts;
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

    int rgnc1;
    int rgnc2;
    int layer;
    int layerTemp;
    int blockTemp;

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

    public WorldContainer(Integer[][][] blocks, Byte[][][] blockds, Byte[][] blockdns, Byte[][] blockbgs, Byte[][] blockts,
        Float[][] lights, Float[][][] power, Boolean[][] drawn, Boolean[][] ldrawn, Boolean[][] rdrawn,
        Player player, Inventory inventory, ItemCollection cic,
        List<Entity> entities, CloudsAggregate cloudsAggregate,
        List<Integer> machinesx, List<Integer> machinesy, Boolean[][] lsources, List<Integer> lqx, List<Integer> lqy, Boolean[][] lqd,
        int rgnc1, int rgnc2, int layer, int layerTemp, int blockTemp,
        int mx, int my, int icx, int icy, int mining, int immune,
        Items moveItem, short moveNum, Items moveItemTemp, short moveNumTemp, int msi,
        double toolAngle, double toolSpeed, double timeOfDay, int currentSkyLight, int day, int mobCount,
        boolean ready, boolean showTool, boolean showInv, boolean doMobSpawn,
        int WIDTH, int HEIGHT, int WORLDWIDTH, int WORLDHEIGHT,
        int resunlight,
        ItemCollection ic, boolean[][] kworlds, ItemCollection[][][] icmatrix, String version) {

        this.blocks = blocks;
        this.blockds = blockds;
        this.blockdns = blockdns;
        this.blockbgs = blockbgs;
        this.blockts = blockts;
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
        this.rgnc1 = rgnc1;
        this.rgnc2 = rgnc2;
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
        blockds = wc.blockds;
        blockdns = wc.blockdns;
        blockbgs = wc.blockbgs;
        blockts = wc.blockts;
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
        blocks = new Integer[3][size][size];
        blockds = new Byte[3][size][size];
        blockdns = new Byte[size][size];
        blockbgs = new Byte[size][size];
        blockts = new Byte[size][size];
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
        return new WorldContainer(blocks, blockds, blockdns, blockbgs, blockts,
                lights, power, drawn, ldrawn, rdrawn,
                player, inventory, cic,
                entities, cloudsAggregate,
                machinesx, machinesy, lsources, lqx, lqy, lqd,
                rgnc1, rgnc2, layer, layerTemp, blockTemp,
                mx, my, icx, icy, mining, immune,
                moveItem, moveNum, moveItemTemp, moveNumTemp, msi,
                toolAngle, toolSpeed, timeOfDay, currentSkyLight, day, mobCount,
                ready, showTool, showInv, doMobSpawn,
                WIDTH, HEIGHT, WORLDWIDTH, WORLDHEIGHT,
                resunlight,
                ic, kworlds, icmatrix, version);
    }
}
