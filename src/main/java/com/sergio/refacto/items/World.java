package com.sergio.refacto.items;

import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Constants;
import com.sergio.refacto.tools.MathTool;

public class World {

    public static Byte[][] generateOutlines(Integer[][] blocks) {
        int width = blocks[0].length;
        int height = blocks.length;
        Byte[][] blockds = new Byte[height][width];
        return generate(blocks, blockds, width, height);
    }

    public static Byte[][] generateOutlines(Integer[][] blocks, Byte[][] blockds, int xpos, int ypos) {
        return generate(blocks, blockds, xpos+2, ypos+2);
    }

    private static Byte[][] generate(Integer[][] blocks, Byte[][] blockds, int xpos, int ypos) {
        int width = blocks[0].length;
        int height = blocks.length;
        boolean left, right, up, down, upleft, upright, downleft, downright;
        for (int y=ypos-1; y<ypos; y++) {
            for (int x2=xpos-1; x2<xpos; x2++) {
                int x = MathTool.mod(x2,width);
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

    private static boolean connect(int b1, int b2) {
        return ((b1 != 0 && b1 == b2) ||
                (b1 == 1 && b2 == 72) ||
                (b2 == 1 && b1 == 72) ||
                (b1 == 1 && b2 == 73) ||
                (b2 == 1 && b1 == 73) ||
                (b1 == 75 && b2 == 74) ||
                (b2 == 75 && b1 == 74) ||
                (b1 == 91 && b2 == 93) ||
                (b2 == 91 && b1 == 93) ||
                (b2 >= 94 && b2 <= 99 && Constants.WIRE_C[b1]) ||
                (b1 == 103 && b2 == 104) ||
                (b2 == 103 && b1 == 104) ||
                (b1 == 15 && b2 == 83) ||
                (b2 == 83 && b1 == 15));
    }

    private static boolean connect(int x1, int y1, int x2, int y2, Integer[][] blocks) {
        return y1 > 0 && y1 < blocks.length-1
                && connect(blocks[y1][MathTool.mod(x1, blocks[0].length)], blocks[y2][MathTool.mod(x2, blocks[0].length)]);
    }

    private static void pmsg(String msg) {
        TerrariaClone.pmsg(msg);
    }
}
