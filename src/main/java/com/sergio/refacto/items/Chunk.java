package com.sergio.refacto.items;

import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.tools.PerlinNoise;
import com.sergio.refacto.tools.RandomTool;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chunk {

    int cx, cy;

    Blocks[][][] blocks;
    Byte[][][] blockds;
    Byte[][] blockdns;
    Backgrounds[][] blockbgs;
    Byte[][] blockts;
    Float[][] lights;
    Float[][][] power;
    Boolean[][] lsources;
    Byte[][] zqn;
    Byte[][][] pzqn;
    Boolean[][][] arbprd;
    Boolean[][] wcnct;
    Boolean[][] drawn, rdrawn, ldrawn;

    public Chunk(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;

        int size = TerrariaClone.CHUNKBLOCKS;
        blocks = new Blocks[3][size][size];
        blockds = new Byte[3][size][size];
        blockdns = new Byte[size][size];
        blockbgs = new Backgrounds[size][size];
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
        for (int y=0; y<size; y++) {
            for (int x=0; x<size; x++) {
                for (int l=0; l<3; l++) {
                    if (l == 1 && cy*size+y >= PerlinNoise.perlinNoise((cx*size+x) / 10.0, 0.5, 0) * 30 + 50) {
                        blocks[l][y][x] = Blocks.DIRT; // dirt
                    }
                    else {
                        blocks[l][y][x] = Blocks.AIR;
                    }
                    arbprd[l][y][x] = false;
                    power[l][y][x] = (float)0;
                }
                blockdns[y][x] = (byte) RandomTool.nextInt(5);
                blockbgs[y][x] = Backgrounds.EMPTY;
                blockts[y][x] = (byte) RandomTool.nextInt(8);
                lights[y][x] = (float)19;
                lsources[y][x] = false;
                wcnct[y][x] = false;
                drawn[y][x] = false;
                rdrawn[y][x] = false;
                ldrawn[y][x] = false;
                blockds[0][y][x] = (byte)0;
                blockds[2][y][x] = (byte)0;
            }
        }
        blockds[1] = World.generateOutlines(blocks[1]);
    }
}
