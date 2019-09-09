package com.sergio.refacto.services;

import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.Constants;
import com.sergio.refacto.dto.Directions;
import com.sergio.refacto.tools.MathTool;

/**
 * Updates the outline block/directions upon the given one.
 */
public class OutlinesService {

    public static Directions[][] generateOutlines(Blocks[][] blocks) {
        int width = blocks[0].length;
        int height = blocks.length;
        Directions[][] blocksDirections = new Directions[height][width];
        return generate(blocks, blocksDirections, 0, 0, width, height);
    }

    public static Directions[][] generateOutlines(Blocks[][] blocks, Directions[][] blocksDirections, int xpos, int ypos) {
        return generate(blocks, blocksDirections, xpos-1, ypos-1, xpos+2, ypos+2);
    }

    private static Directions[][] generate(Blocks[][] blocks, Directions[][] blocksDirections, int initialX, int initialY, int xpos, int ypos) {
        int width = blocks[0].length;
        int height = blocks.length;
        boolean left, right, up, down, upleft, upright, downleft, downright;
        for (int y=initialY; y<ypos; y++) {
            for (int x2=initialX; x2<xpos; x2++) {
                int x = MathTool.mod(x2,width);
                if (y > 0 && y < height-1 && blocks[y][x] != Blocks.AIR) {
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
                                    blocksDirections[y][x] = Directions.CENTER;
                                } else {
                                    if (upleft) {
                                        if (upright) {
                                            blocksDirections[y][x] = Directions.TDOWN_BOTH;
                                        } else {
                                            blocksDirections[y][x] = Directions.TDOWN_CW;
                                        }
                                    } else {
                                        if (upright) {
                                            blocksDirections[y][x] = Directions.TDOWN_CCW;
                                        } else {
                                            blocksDirections[y][x] = Directions.TDOWN;
                                        }
                                    }
                                }
                            } else {
                                if (down) {
                                    if (downright) {
                                        if (downleft) {
                                            blocksDirections[y][x] = Directions.TUP_BOTH;
                                        } else {
                                            blocksDirections[y][x] = Directions.TUP_CW;
                                        }
                                    } else {
                                        if (downleft) {
                                            blocksDirections[y][x] = Directions.TUP_CCW;
                                        } else {
                                            blocksDirections[y][x] = Directions.TUP;
                                        }
                                    }
                                } else {
                                    blocksDirections[y][x] = Directions.LEFTRIGHT;
                                }
                            }
                        } else {
                            if (up) {
                                if (down) {
                                    if (downleft) {
                                        if (upleft) {
                                            blocksDirections[y][x] = Directions.TRIGHT_BOTH;
                                        } else {
                                            blocksDirections[y][x] = Directions.TRIGHT_CW;
                                        }
                                    } else {
                                        if (upleft) {
                                            blocksDirections[y][x] = Directions.TRIGHT_CCW;
                                        } else {
                                            blocksDirections[y][x] = Directions.TRIGHT;
                                        }
                                    }
                                } else {
                                    if (upleft) {
                                        blocksDirections[y][x] = Directions.UPLEFTDIAG;
                                    } else {
                                        blocksDirections[y][x] = Directions.UPLEFT;
                                    }
                                }
                            } else {
                                if (down) {
                                    if (downleft) {
                                        blocksDirections[y][x] = Directions.DOWNLEFTDIAG;
                                    } else {
                                        blocksDirections[y][x] = Directions.DOWNLEFT;
                                    }
                                } else {
                                    blocksDirections[y][x] = Directions.LEFT;
                                }
                            }
                        }
                    } else {
                        if (right) {
                            if (up) {
                                if (down) {
                                    if (upright) {
                                        if (downright) {
                                            blocksDirections[y][x] = Directions.TLEFT_BOTH;
                                        } else {
                                            blocksDirections[y][x] = Directions.TLEFT_CW;
                                        }
                                    } else {
                                        if (downright) {
                                            blocksDirections[y][x] = Directions.TLEFT_CCW;
                                        } else {
                                            blocksDirections[y][x] = Directions.TLEFT;
                                        }
                                    }
                                } else {
                                    if (upright) {
                                        blocksDirections[y][x] = Directions.UPRIGHTDIAG;
                                    } else {
                                        blocksDirections[y][x] = Directions.UPRIGHT;
                                    }
                                }
                            } else {
                                if (down) {
                                    if (downright) {
                                        blocksDirections[y][x] = Directions.DOWNRIGHTDIAG;
                                    } else {
                                        blocksDirections[y][x] = Directions.DOWNRIGHT;
                                    }
                                } else {
                                    blocksDirections[y][x] = Directions.RIGHT;
                                }
                            }
                        } else {
                            if (up) {
                                if (down) {
                                    blocksDirections[y][x] = Directions.UPDOWN;
                                } else {
                                    blocksDirections[y][x] = Directions.UP;
                                }
                            } else {
                                if (down) {
                                    blocksDirections[y][x] = Directions.DOWN;
                                } else {
                                    blocksDirections[y][x] = Directions.SINGLE;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blocksDirections;
    }

    private static boolean connect(Blocks b1, Blocks b2) {
        return ((b1 != Blocks.AIR && b1 == b2) ||
                (b1 == Blocks.DIRT && b2 == Blocks.GRASS) ||
                (b2 == Blocks.DIRT && b1 == Blocks.GRASS) ||
                (b1 == Blocks.DIRT && b2 == Blocks.JUNGLE_GRASS) ||
                (b2 == Blocks.DIRT && b1 == Blocks.JUNGLE_GRASS) ||
                (b1 == Blocks.MUD && b2 == Blocks.SWAMP_GRASS) ||
                (b2 == Blocks.MUD && b1 == Blocks.SWAMP_GRASS) ||
                (b1 == Blocks.DIRT_TRANSPARENT && b2 == Blocks.GRASS_TRANSPARENT) ||
                (b2 == Blocks.DIRT_TRANSPARENT && b1 == Blocks.GRASS_TRANSPARENT) ||
                (b2.isZythiumWire() && Constants.WIRE_C[b1.getIndex()]) ||
                (b1 == Blocks.ZYTHIUM_LAMP && b2 == Blocks.ZYTHIUM_LAMP_ON) ||
                (b2 == Blocks.ZYTHIUM_LAMP && b1 == Blocks.ZYTHIUM_LAMP_ON) ||
                (b1 == Blocks.TREE && b2 == Blocks.TREE_NO_BARK) ||
                (b1 == Blocks.TREE_NO_BARK && b2 == Blocks.TREE));
    }

    private static boolean connect(int x1, int y1, int x2, int y2, Blocks[][] blocks) {
        return y1 > 0 && y1 < blocks.length-1
                && connect(blocks[y1][MathTool.mod(x1, blocks[0].length)], blocks[y2][MathTool.mod(x2, blocks[0].length)]);
    }

}
