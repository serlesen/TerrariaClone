package com.sergio.refacto.items;

import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.Directions;
import com.sergio.refacto.services.OutlinesService;
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
    Directions[][][] blocksDirections;
    Byte[][] BlocksDirectionsIntensity;
    Backgrounds[][] blocksBackgrounds;
    Byte[][] blocksTextureIntesity;
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
        blocksDirections = new Directions[3][size][size];
        BlocksDirectionsIntensity = new Byte[size][size];
        blocksBackgrounds = new Backgrounds[size][size];
        blocksTextureIntesity = new Byte[size][size];
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
                BlocksDirectionsIntensity[y][x] = (byte) RandomTool.nextInt(5);
                blocksBackgrounds[y][x] = Backgrounds.EMPTY;
                blocksTextureIntesity[y][x] = (byte) RandomTool.nextInt(8);
                lights[y][x] = (float)19;
                lsources[y][x] = false;
                wcnct[y][x] = false;
                drawn[y][x] = false;
                rdrawn[y][x] = false;
                ldrawn[y][x] = false;
                blocksDirections[0][y][x] = Directions.CENTER;
                blocksDirections[2][y][x] = Directions.CENTER;
            }
        }
        blocksDirections[1] = OutlinesService.generateOutlines(blocks[1]);
    }
}
