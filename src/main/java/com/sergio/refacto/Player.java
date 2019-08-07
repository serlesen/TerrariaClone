package com.sergio.refacto;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.URL;
import javax.imageio.ImageIO;

import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.KeyPressed;

public class Player implements Serializable {
    transient BufferedImage image;
    int ix, iy, ivx, ivy, width, height, bx1, by1, bx2, by2, thp, hp;
    double x, y, vx, vy, pvy, oldx, oldy;
    private boolean onGround, onGroundDelay, grounded;
    Rectangle rect;

    private int i, j, n;

    private int imgDelay;
    ImageState imgState;

    private int BLOCKSIZE = TerrariaClone.getBLOCKSIZE();

    public Player(double x, double y) {
        oldx = this.x = x; oldy = this.y = y;

        vx = 0;
        vy = 0;
        pvy = 0;

        onGround = false;

        image = loadImage("sprites/player/right_still.png");

        width = TerrariaClone.getPLAYERSIZEX();
        height = TerrariaClone.getPLAYERSIZEY();

        ix = (int)x;
        iy = (int)y;

        rect = new Rectangle(ix, iy, width, height);

        imgDelay = 0;
        imgState = ImageState.STILL_RIGHT;

        thp = 50;

        hp = thp;
    }

    public void update(Integer[][] blocks, KeyPressed keyPressed, int u, int v) {
        grounded = (onGround || onGroundDelay);
        if (keyPressed == KeyPressed.LEFT) {
            if (vx > -4 || DebugContext.SPEED) {
                vx = vx - 0.5;
            }
            if (imgState.isStill() || imgState.isWalkRight()) {
                imgDelay = 5;
                imgState = ImageState.WALK_LEFT_2;
                image = loadImage("sprites/player/left_walk.png");
            }
            if (imgDelay <= 0) {
                if (imgState == ImageState.WALK_LEFT_1) {
                    imgDelay = 5;
                    imgState = ImageState.WALK_LEFT_2;
                    image = loadImage("sprites/player/left_walk.png");
                } else {
                    if (imgState == ImageState.WALK_LEFT_2) {
                        imgDelay = 5;
                        imgState = ImageState.WALK_LEFT_1;
                        image = loadImage("sprites/player/left_still.png");
                    }
                }
            }
            else {
                imgDelay = imgDelay - 1;
            }
        } else if (keyPressed == KeyPressed.RIGHT) {
            if (vx < 4 || DebugContext.SPEED) {
                vx = vx + 0.5;
            }
            if (imgState.isStill() || imgState.isWalkLeft()) {
                imgDelay = 5;
                imgState = ImageState.WALK_RIGHT_2;
                image = loadImage("sprites/player/right_walk.png");
            }
            if (imgDelay <= 0) {
                if (imgState == ImageState.WALK_RIGHT_1) {
                    imgDelay = 5;
                    imgState = ImageState.WALK_RIGHT_2;
                    image = loadImage("sprites/player/right_walk.png");
                } else {
                    if (imgState == ImageState.WALK_RIGHT_2) {
                        imgDelay = 5;
                        imgState = ImageState.WALK_RIGHT_1;
                        image = loadImage("sprites/player/right_still.png");
                    }
                }
            }
            else {
                imgDelay = imgDelay - 1;
            }
        } else if (keyPressed == KeyPressed.UP) {
            if (DebugContext.FLIGHT) {
                vy -= 1;
                pvy -= 1;
            }
            else {
                if (onGround == true) {
                    vy = -7;
                    pvy = -7;
                }
            }
        } else if (keyPressed == KeyPressed.DOWN) {
            if (DebugContext.FLIGHT) {
                vy += 1;
                pvy += 1;
            }
        }
        if (!onGround) {
            vy = vy + 0.3;
            pvy = pvy + 0.3;
            if (vy > 7 && !DebugContext.FLIGHT) {
                vy = 7;
            }
        }
        if (keyPressed != KeyPressed.LEFT && keyPressed != KeyPressed.RIGHT) {
            if (Math.abs(vx) < 0.3) {
                vx = 0;
            }
            if (vx >= 0.3) {
                vx = vx - 0.3;
            }
            if (vx <= -0.3) {
                vx = vx + 0.3;
            }
            if (grounded) {
                if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                    imgState = ImageState.STILL_LEFT;
                    image = loadImage("sprites/player/left_still.png");
                } else if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                    imgState = ImageState.STILL_RIGHT;
                    image = loadImage("sprites/player/right_still.png");
                }
            }
        }

        if (!grounded) {
            if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                image = loadImage("sprites/player/left_jump.png");
            } else if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                image = loadImage("sprites/player/right_jump.png");
            }
        }

        onGroundDelay = onGround;

        oldx = x; oldy = y;

        x = x + vx;

        if (!DebugContext.NOCLIP) {
            for (i=0; i<2; i++) {
                ix = (int)x;
                iy = (int)y;
                ivx = (int)vx;
                ivy = (int)vy;

                rect = new Rectangle(ix-1, iy, width+2, height);

                bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
                bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

                for (i=bx1; i<=bx2; i++) {
                    for (j=by1; j<=by2; j++) {
                        if (blocks[j+v][i+u] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                            if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                if (oldx <= i*16 - width && vx > 0) {
                                    x = i*16 - width;
                                    vx = 0; // right
                                }
                                if (oldx >= i*16 + BLOCKSIZE && vx < 0) {
                                    x = i*16 + BLOCKSIZE;
                                    vx = 0; // left
                                }
                            }
                        }
                    }
                }
            }
        }

        y = y + vy;
        onGround = false;
        if (!DebugContext.NOCLIP) {
            for (i=0; i<2; i++) {
                ix = (int)x;
                iy = (int)y;
                ivx = (int)vx;
                ivy = (int)vy;

                rect = new Rectangle(ix, iy-1, width, height+2);

                bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
                bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

                by1 = Math.max(0, by1);
                by2 = Math.min(blocks.length - 1, by2);

                for (i=bx1; i<=bx2; i++) {
                    for (j=by1; j<=by2; j++) {
                        if (blocks[j+v][i+u] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                            if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                if (oldy <= j*16 - height && vy > 0) {
                                    y = j*16 - height;
                                    if (pvy >= 10 && !DebugContext.INVINCIBLE) {
                                        hp -= (int)((pvy - 12.5))*2;
                                    }
                                    onGround = true;
                                    vy = 0; // down
                                    pvy = 0;
                                }
                                if (oldy >= j*16 + BLOCKSIZE && vy < 0) {
                                    y = j*16 + BLOCKSIZE;
                                    vy = 0; // up
                                }
                            }
                        }
                    }
                }
            }
        }

        ix = (int)x;
        iy = (int)y;
        ivx = (int)vx;
        ivy = (int)vy;

        rect = new Rectangle(ix-1, iy-1, width+2, height+2);
    }

    public void reloadImage() {
        if (grounded) {
            if (imgState == ImageState.STILL_LEFT || imgState == ImageState.WALK_LEFT_1) {
                image = loadImage("sprites/player/left_still.png");
            } else if (imgState == ImageState.WALK_LEFT_2) {
                image = loadImage("sprites/player/left_walk.png");
            } else if (imgState == ImageState.STILL_RIGHT || imgState == ImageState.WALK_RIGHT_1) {
                image = loadImage("sprites/player/right_still.png");
            } else if (imgState == ImageState.WALK_RIGHT_2) {
                image = loadImage("sprites/player/right_walk.png");
            }
        } else {
            if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                image = loadImage("sprites/player/left_jump.png");
            } else if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                image = loadImage("sprites/player/right_jump.png");
            }
        }
    }

    public void damage(int damage, boolean useArmor, Inventory inventory) {
        int fd = damage;
        if (useArmor) {
            fd -= sumArmor();
            for (i=0; i<4; i++) {
                TerrariaClone.armor.getDurs()[i] -= 1;
                if (TerrariaClone.armor.getDurs()[i] <= 0) {
                    inventory.removeLocationIC(TerrariaClone.armor, i, TerrariaClone.armor.getNums()[i]);
                }
            }
        }
        if (fd < 1) {
            fd = 1;
        }
        hp -= fd;
    }

    public int sumArmor() {
        return (TerrariaClone.getARMOR().get(TerrariaClone.armor.getIds()[0]) +
                TerrariaClone.getARMOR().get(TerrariaClone.armor.getIds()[1]) +
                TerrariaClone.getARMOR().get(TerrariaClone.armor.getIds()[2]) +
                TerrariaClone.getARMOR().get(TerrariaClone.armor.getIds()[3]));
    }

    private static BufferedImage loadImage(String path) {
        URL url = TerrariaClone.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            System.out.println("[ERROR] could not load image '" + path + "'.");
        }
        return image;
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
