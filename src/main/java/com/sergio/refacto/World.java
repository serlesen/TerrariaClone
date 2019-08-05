package com.sergio.refacto;

import java.util.Random;

import com.sergio.refacto.dto.Constants;
import com.sergio.refacto.tools.PerlinNoise;

public class World {

    private static int x, y;
    private static int x2, y2, i, j, n, xpos, ypos, xpos2, ypos2;
    private static double f;
    private static short type;

    private static Random random;

    private static boolean[][] coordlist;
    private static boolean[][] coordlist2;

    public static Object[] generateChunk(int cx, int cy, Random random) {
        int size = TerrariaClone.CHUNKBLOCKS;
        Integer[][][] blocks = new Integer[3][size][size];
        Byte[][][] blockds = new Byte[3][size][size];
        Byte[][] blockdns = new Byte[size][size];
        Byte[][] blockbgs = new Byte[size][size];
        Byte[][] blockts = new Byte[size][size];
        Float[][] lights = new Float[size][size];
        Float[][][] power = new Float[3][size][size];
        Boolean[][] lsources = new Boolean[size][size];
        Byte[][] zqn = new Byte[size][size];
        Byte[][][] pzqn = new Byte[3][size][size];
        Boolean[][][] arbprd = new Boolean[3][size][size];
        Boolean[][] wcnct = new Boolean[size][size];
        Boolean[][] drawn = new Boolean[size][size];
        Boolean[][] rdrawn = new Boolean[size][size];
        Boolean[][] ldrawn = new Boolean[size][size];
        for (y=0; y<size; y++) {
            for (x=0; x<size; x++) {
                for (int l=0; l<3; l++) {
                    if (l == 1 && cy*size+y >= PerlinNoise.perlinNoise((cx*size+x) / 10.0, 0.5, 0) * 30 + 50) {
                        blocks[l][y][x] = 1; // dirt
                    }
                    else {
                        blocks[l][y][x] = 0;
                    }
                    arbprd[l][y][x] = false;
                    power[l][y][x] = (float)0;
                }
                blockdns[y][x] = (byte)random.nextInt(5);
                blockbgs[y][x] = 0;
                blockts[y][x] = (byte)random.nextInt(8);
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
        Object[] rv = {blocks, blockds, blockdns, blockbgs, blockts, lights, power, lsources, zqn, pzqn, arbprd, wcnct, drawn, rdrawn, ldrawn};
        return rv;
    }

    public static Byte[][] generateOutlines(Integer[][] blocks) {
        return World.generate2(blocks, false);
    }

    public static Byte[][] generate2(Integer[][] blocks, boolean msg) {
        int x, y;
        int width = blocks[0].length;
        int height = blocks.length;
        if (msg) {
            pmsg("-> Creating outlines...");
        }
        Byte[][] blockds = new Byte[height][width];
        Byte[][] blockdns = new Byte[height][width];
        boolean left, right, up, down, upleft, upright, downleft, downright;
        for (y=0; y<height; y++) {
            for (x2=0; x2<width; x2++) {
                left = false; right = false; up = false; down = false;
                upleft = false; upright = false; downleft = false; downright = false;
                x = mod(x2,width);
                if (y > 0 && y < height-1 && blocks[y][x] != 0) {
                    left = connect(x-1, y, x, y, blocks);
                    right = connect(x+1, y, x, y, blocks);
                    up = connect(x, y-1, x, y, blocks);
                    down = connect(x, y+1, x, y, blocks);
                    upleft = connect(x-1, y-1, x, y, blocks);
                    upright = connect(x+1, y-1, x, y, blocks);
                    downleft = connect(x-1, y+1, x, y, blocks);
                    downright = connect(x+1, y+1, x, y, blocks);
                    if (left) {
                        if (right) {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 0;
                                }
                                else {
                                    if (upleft) {
                                        if (upright) {
                                            blockds[y][x] = 1;
                                        }
                                        else {
                                            blockds[y][x] = 2;
                                        }
                                    }
                                    else {
                                        if (upright) {
                                            blockds[y][x] = 3;
                                        }
                                        else {
                                            blockds[y][x] = 4;
                                        }
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        if (downleft) {
                                            blockds[y][x] = 5;
                                        }
                                        else {
                                            blockds[y][x] = 6;
                                        }
                                    }
                                    else {
                                        if (downleft) {
                                            blockds[y][x] = 7;
                                        }
                                        else {
                                            blockds[y][x] = 8;
                                        }
                                    }
                                }
                                else {
                                    blockds[y][x] = 9;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    if (downleft) {
                                        if (upleft) {
                                            blockds[y][x] = 10;
                                        }
                                        else {
                                            blockds[y][x] = 11;
                                        }
                                    }
                                    else {
                                        if (upleft) {
                                            blockds[y][x] = 12;
                                        }
                                        else {
                                            blockds[y][x] = 13;
                                        }
                                    }
                                }
                                else {
                                    if (upleft) {
                                        blockds[y][x] = 14;
                                    }
                                    else {
                                        blockds[y][x] = 15;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downleft) {
                                        blockds[y][x] = 16;
                                    }
                                    else {
                                        blockds[y][x] = 17;
                                    }
                                }
                                else {
                                    blockds[y][x] = 18;
                                }
                            }
                        }
                    }
                    else {
                        if (right) {
                            if (up) {
                                if (down) {
                                    if (upright) {
                                        if (downright) {
                                            blockds[y][x] = 19;
                                        }
                                        else {
                                            blockds[y][x] = 20;
                                        }
                                    }
                                    else {
                                        if (downright) {
                                            blockds[y][x] = 21;
                                        }
                                        else {
                                            blockds[y][x] = 22;
                                        }
                                    }
                                }
                                else {
                                    if (upright) {
                                        blockds[y][x] = 23;
                                    }
                                    else {
                                        blockds[y][x] = 24;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        blockds[y][x] = 25;
                                    }
                                    else {
                                        blockds[y][x] = 26;
                                    }
                                }
                                else {
                                    blockds[y][x] = 27;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 28;
                                }
                                else {
                                    blockds[y][x] = 29;
                                }
                            }
                            else {
                                if (down) {
                                    blockds[y][x] = 30;
                                }
                                else {
                                    blockds[y][x] = 31;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockds;
    }

    public static Byte[][] generate2b(Integer[][] blocks, Byte[][] blockds, int xpos, int ypos) {
        int x, y;
        int width = blocks[0].length;
        int height = blocks.length;
        boolean left, right, up, down, upleft, upright, downleft, downright;
        left = false; right = false; up = false; down = false;
        upleft = false; upright = false; downleft = false; downright = false;
        for (y=ypos-1; y<ypos+2; y++) {
            for (x2=xpos-1; x2<xpos+2; x2++) {
                x = mod(x2,width);
                if (y > 0 && y < height-1 && blocks[y][x] != 0) {
                    left = connect(x-1, y, x, y, blocks);
                    right = connect(x+1, y, x, y, blocks);
                    up = connect(x, y-1, x, y, blocks);
                    down = connect(x, y+1, x, y, blocks);
                    upleft = connect(x-1, y-1, x, y, blocks);
                    upright = connect(x+1, y-1, x, y, blocks);
                    downleft = connect(x-1, y+1, x, y, blocks);
                    downright = connect(x+1, y+1, x, y, blocks);
                    if (left) {
                        if (right) {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 0;
                                }
                                else {
                                    if (upleft) {
                                        if (upright) {
                                            blockds[y][x] = 1;
                                        }
                                        else {
                                            blockds[y][x] = 2;
                                        }
                                    }
                                    else {
                                        if (upright) {
                                            blockds[y][x] = 3;
                                        }
                                        else {
                                            blockds[y][x] = 4;
                                        }
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        if (downleft) {
                                            blockds[y][x] = 5;
                                        }
                                        else {
                                            blockds[y][x] = 6;
                                        }
                                    }
                                    else {
                                        if (downleft) {
                                            blockds[y][x] = 7;
                                        }
                                        else {
                                            blockds[y][x] = 8;
                                        }
                                    }
                                }
                                else {
                                    blockds[y][x] = 9;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    if (downleft) {
                                        if (upleft) {
                                            blockds[y][x] = 10;
                                        }
                                        else {
                                            blockds[y][x] = 11;
                                        }
                                    }
                                    else {
                                        if (upleft) {
                                            blockds[y][x] = 12;
                                        }
                                        else {
                                            blockds[y][x] = 13;
                                        }
                                    }
                                }
                                else {
                                    if (upleft) {
                                        blockds[y][x] = 14;
                                    }
                                    else {
                                        blockds[y][x] = 15;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downleft) {
                                        blockds[y][x] = 16;
                                    }
                                    else {
                                        blockds[y][x] = 17;
                                    }
                                }
                                else {
                                    blockds[y][x] = 18;
                                }
                            }
                        }
                    }
                    else {
                        if (right) {
                            if (up) {
                                if (down) {
                                    if (upright) {
                                        if (downright) {
                                            blockds[y][x] = 19;
                                        }
                                        else {
                                            blockds[y][x] = 20;
                                        }
                                    }
                                    else {
                                        if (downright) {
                                            blockds[y][x] = 21;
                                        }
                                        else {
                                            blockds[y][x] = 22;
                                        }
                                    }
                                }
                                else {
                                    if (upright) {
                                        blockds[y][x] = 23;
                                    }
                                    else {
                                        blockds[y][x] = 24;
                                    }
                                }
                            }
                            else {
                                if (down) {
                                    if (downright) {
                                        blockds[y][x] = 25;
                                    }
                                    else {
                                        blockds[y][x] = 26;
                                    }
                                }
                                else {
                                    blockds[y][x] = 27;
                                }
                            }
                        }
                        else {
                            if (up) {
                                if (down) {
                                    blockds[y][x] = 28;
                                }
                                else {
                                    blockds[y][x] = 29;
                                }
                            }
                            else {
                                if (down) {
                                    blockds[y][x] = 30;
                                }
                                else {
                                    blockds[y][x] = 31;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockds;
    }

    public static boolean connect(int b1, int b2) {
        return (b1 != 0 && b1 == b2 ||
            b1 == 1 && b2 == 72 ||
            b2 == 1 && b1 == 72 ||
            b1 == 1 && b2 == 73 ||
            b2 == 1 && b1 == 73 ||
            b1 == 75 && b2 == 74 ||
            b2 == 75 && b1 == 74 ||
            b1 == 91 && b2 == 93 ||
            b2 == 91 && b1 == 93 ||
            b2 >= 94 && b2 <= 99 && Constants.WIRE_C[b1] ||
            b1 == 103 && b2 == 104 ||
            b2 == 103 && b1 == 104 ||
            b1 == 15 && b2 == 83 ||
            b2 == 83 && b1 == 15);
    }

    public static boolean connect(int x1, int y1, int x2, int y2, Integer[][] blocks) {
        return y1 > 0 && y1 < blocks.length-1 && connect(blocks[y1][mod(x1,blocks[0].length)], blocks[y2][mod(x2,blocks[0].length)]);
    }

    public static void pmsg(String msg) {
        TerrariaClone.pmsg(msg);
    }

    public static int mod(int a, int q) {
        return TerrariaClone.mod(a, q);
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
