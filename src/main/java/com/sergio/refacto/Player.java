package com.sergio.refacto;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.KeyPressed;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Player implements Serializable {
    transient BufferedImage image;
    int ix, iy, width, height, totalHealthPoints, healthPoints;
    double x, y, vx, vy, pvy, oldx, oldy;
    private boolean onGround, onGroundDelay, grounded;
    Rectangle rect;

    private int imgDelay;
    ImageState imgState;

    private int BLOCKSIZE = TerrariaClone.getBLOCKSIZE();

    public Player(double x, double y) {
        oldx = this.x = x; oldy = this.y = y;

        vx = 0;
        vy = 0;
        pvy = 0;

        onGround = false;

        image = ResourcesLoader.loadImage("sprites/player/right_still.png");

        width = TerrariaClone.getPLAYERSIZEX();
        height = TerrariaClone.getPLAYERSIZEY();

        ix = (int)x;
        iy = (int)y;

        rect = new Rectangle(ix, iy, width, height);

        imgDelay = 0;
        imgState = ImageState.STILL_RIGHT;

        totalHealthPoints = 50;

        healthPoints = totalHealthPoints;
    }

    public void update(Blocks[][] blocks, KeyPressed keyPressed, int u, int v) {
        grounded = (onGround || onGroundDelay);

        handleMovement(keyPressed);

        if (!onGround) {
            vy += 0.3;
            pvy += 0.3;
            if (vy > 7 && !DebugContext.FLIGHT) {
                vy = 7;
            }
        }

        handleStopping(keyPressed);

        if (!grounded) {
            if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                image = ResourcesLoader.loadImage("sprites/player/left_jump.png");
            } else if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                image = ResourcesLoader.loadImage("sprites/player/right_jump.png");
            }
        }

        onGroundDelay = onGround;

        oldx = x; oldy = y;

        x = x + vx;

        if (!DebugContext.NOCLIP) {
            for (int k = 0; k < 2; k++) {

                ix = (int) x;
                iy = (int) y;

                rect = new Rectangle(ix-1, iy, width+2, height);

                int bx1 = (int)x/BLOCKSIZE;
                int by1 = (int)y/BLOCKSIZE;
                int bx2 = (int)(x+width)/BLOCKSIZE;
                int by2 = (int)(y+height)/BLOCKSIZE;

                for (int i = bx1; i <= bx2; i++) {
                    for (int j = by1; j <= by2; j++) {
                        if (blocks[j+v][i+u] != Blocks.AIR && blocks[j+v][i+u].isCds()) {
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
            for (int k = 0; k < 2; k++) {

                ix = (int) x;
                iy = (int) y;

                rect = new Rectangle(ix, iy - 1, width, height+2);

                int bx1 = (int)x/BLOCKSIZE;
                int by1 = (int)y/BLOCKSIZE;
                int bx2 = (int)(x+width)/BLOCKSIZE;
                int by2 = (int)(y+height)/BLOCKSIZE;

                by1 = Math.max(0, by1);
                by2 = Math.min(blocks.length - 1, by2);

                for (int i = bx1; i <= bx2; i++) {
                    for (int j = by1; j <= by2; j++) {
                        if (blocks[j+v][i+u] != Blocks.AIR && blocks[j+v][i+u].isCds()) {
                            if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                                if (oldy <= j*16 - height && vy > 0) {
                                    y = j*16 - height;
                                    if (pvy >= 10 && !DebugContext.INVINCIBLE) {
                                        healthPoints -= (int)((pvy - 12.5))*2;
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

        ix = (int) x;
        iy = (int) y;

        rect = new Rectangle(ix - 1, iy - 1, width+2, height+2);
    }

    private void handleStopping(KeyPressed keyPressed) {
        if (keyPressed != KeyPressed.LEFT && keyPressed != KeyPressed.RIGHT) {
            if (Math.abs(vx) < 0.3) {
                vx = 0;
            }
            if (vx >= 0.3) {
                vx -= 0.3;
            }
            if (vx <= -0.3) {
                vx += 0.3;
            }
            if (grounded) {
                if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                    imgState = ImageState.STILL_LEFT;
                    image = ResourcesLoader.loadImage("sprites/player/left_still.png");
                } else if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                    imgState = ImageState.STILL_RIGHT;
                    image = ResourcesLoader.loadImage("sprites/player/right_still.png");
                }
            }
        }
    }

    private void handleMovement(KeyPressed keyPressed) {
        if (keyPressed == KeyPressed.LEFT) {
            if (vx > -4 || DebugContext.SPEED) {
                vx -= 0.5;
            }
            if (imgState.isStill() || imgState.isWalkRight()) {
                setMovementImageTo(ImageState.WALK_LEFT_2, "sprites/player/left_walk.png");
            }
            if (imgDelay <= 0) {
                if (imgState == ImageState.WALK_LEFT_1) {
                    setMovementImageTo(ImageState.WALK_LEFT_2, "sprites/player/left_walk.png");
                } else {
                    if (imgState == ImageState.WALK_LEFT_2) {
                        setMovementImageTo(ImageState.WALK_LEFT_1, "sprites/player/left_still.png");
                    }
                }
            } else {
                imgDelay = imgDelay - 1;
            }
        } else if (keyPressed == KeyPressed.RIGHT) {
            if (vx < 4 || DebugContext.SPEED) {
                vx += 0.5;
            }
            if (imgState.isStill() || imgState.isWalkLeft()) {
                setMovementImageTo(ImageState.WALK_RIGHT_2, "sprites/player/right_walk.png");
            }
            if (imgDelay <= 0) {
                if (imgState == ImageState.WALK_RIGHT_1) {
                    setMovementImageTo(ImageState.WALK_RIGHT_2, "sprites/player/right_walk.png");
                } else {
                    if (imgState == ImageState.WALK_RIGHT_2) {
                        setMovementImageTo(ImageState.WALK_RIGHT_1, "sprites/player/right_still.png");
                    }
                }
            } else {
                imgDelay = imgDelay - 1;
            }
        } else if (keyPressed == KeyPressed.UP) {
            if (DebugContext.FLIGHT) {
                vy -= 1;
                pvy -= 1;
            } else if (onGround) {
                    vy = -7;
                    pvy = -7;
            }
        } else if (keyPressed == KeyPressed.DOWN) {
            if (DebugContext.FLIGHT) {
                vy += 1;
                pvy += 1;
            }
        }
    }

    private void setMovementImageTo(ImageState walkLeft2, String path) {
        imgDelay = 5;
        imgState = walkLeft2;
        image = ResourcesLoader.loadImage(path);
    }

    public void reloadImage() {
        if (grounded) {
            if (imgState == ImageState.STILL_LEFT || imgState == ImageState.WALK_LEFT_1) {
                image = ResourcesLoader.loadImage("sprites/player/left_still.png");
            } else if (imgState == ImageState.WALK_LEFT_2) {
                image = ResourcesLoader.loadImage("sprites/player/left_walk.png");
            } else if (imgState == ImageState.STILL_RIGHT || imgState == ImageState.WALK_RIGHT_1) {
                image = ResourcesLoader.loadImage("sprites/player/right_still.png");
            } else if (imgState == ImageState.WALK_RIGHT_2) {
                image = ResourcesLoader.loadImage("sprites/player/right_walk.png");
            }
        } else {
            if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                image = ResourcesLoader.loadImage("sprites/player/left_jump.png");
            } else if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                image = ResourcesLoader.loadImage("sprites/player/right_jump.png");
            }
        }
    }

    public void damage(int damage, boolean useArmor, Inventory inventory) {
        int fd = damage;
        if (useArmor) {
            fd -= sumArmor();
            for (int i = 0; i < 4; i++) {
                TerrariaClone.armor.getDurs()[i] -= 1;
                if (TerrariaClone.armor.getDurs()[i] <= 0) {
                    inventory.removeLocationIC(TerrariaClone.armor, i, TerrariaClone.armor.getNums()[i]);
                }
            }
        }
        if (fd < 1) {
            fd = 1;
        }
        healthPoints -= fd;
    }

    public int sumArmor() {
        return (TerrariaClone.armor.getIds()[0].getArmor() +
                TerrariaClone.armor.getIds()[1].getArmor() +
                TerrariaClone.armor.getIds()[2].getArmor() +
                TerrariaClone.armor.getIds()[3].getArmor());
    }
}
