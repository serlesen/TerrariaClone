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

        blocks = new Blocks[TerrariaClone.LAYER_SIZE][WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        blocksDirections = new Directions[TerrariaClone.LAYER_SIZE][WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        BlocksDirectionsIntensity = new Byte[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        blocksBackgrounds = new Backgrounds[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        blocksTextureIntesity = new Byte[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        lights = new Float[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        power = new Float[TerrariaClone.LAYER_SIZE][WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        lsources = new Boolean[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        zqn = new Byte[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        pzqn = new Byte[TerrariaClone.LAYER_SIZE][WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        arbprd = new Boolean[TerrariaClone.LAYER_SIZE][WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        wcnct = new Boolean[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        drawn = new Boolean[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        rdrawn = new Boolean[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        ldrawn = new Boolean[WorldContainer.CHUNK_BLOCKS][WorldContainer.CHUNK_BLOCKS];
        for (int y = 0; y < WorldContainer.CHUNK_BLOCKS; y++) {
            for (int x = 0; x < WorldContainer.CHUNK_BLOCKS; x++) {
                for (int l = 0; l < TerrariaClone.LAYER_SIZE; l++) {
                    if (l == 1 && cy* WorldContainer.CHUNK_BLOCKS +y >= PerlinNoise.perlinNoise((cx* WorldContainer.CHUNK_BLOCKS +x) / 10.0, 0.5, 0) * 30 + 50) {
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
