package com.sergio.refacto.items;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.DebugContext;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.KeyPressed;
import com.sergio.refacto.services.InventoryService;
import com.sergio.refacto.tools.ResourcesLoader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Player implements Serializable {

    private static final int MAX_FLIGHT_SPEED = 14;
    private static final int MAX_GRAVITY_SPEED = 7;

    public static final int WIDTH = 20;
    public static final int HEIGHT = 46;

    transient BufferedImage image;
    int intX, intY, totalHealthPoints, healthPoints;
    private double pvy, oldX, oldY;

    /** Specifies the position (in pixels) of the player in the world. */
    double x, y;

    /** Specifies the speed vector (in pixels) of the player. */
    double speedX, speedY;

    private boolean onGround, onGroundDelay, grounded;

    /** Represents the rectangle occupied by the player in the world. */
    Rectangle occupation;

    private int imgDelay;
    ImageState imgState;

    public Player(double x, double y) {
        oldX = this.x = x; oldY = this.y = y;

        speedX = 0;
        speedY = 0;
        pvy = 0;

        onGround = false;

        image = ResourcesLoader.loadImage("sprites/player/right_still.png");

        intX = (int)x;
        intY = (int)y;

        occupation = new Rectangle(intX, intY, WIDTH, HEIGHT);

        imgDelay = 0;
        imgState = ImageState.STILL_RIGHT;

        totalHealthPoints = 50;

        healthPoints = totalHealthPoints;
    }

    public void update(Blocks[][] blocks, KeyPressed keyPressed, int blockOffsetU, int blockOffsetV) {
        grounded = (onGround || onGroundDelay);

        handleMovement(keyPressed);

        handleFalling();

        handleStopping(keyPressed);

        if (!grounded) {
            if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                image = ResourcesLoader.loadImage("sprites/player/left_jump.png");
            } else if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                image = ResourcesLoader.loadImage("sprites/player/right_jump.png");
            }
        }

        onGroundDelay = onGround;

        oldX = x; oldY = y;

        x = x + speedX;
        handleHorizontalBlockage(blocks, blockOffsetU, blockOffsetV);

        y = y + speedY;
        onGround = false;
        handleVerticalBlockage(blocks, blockOffsetU, blockOffsetV);

        intX = (int) x;
        intY = (int) y;

        occupation = new Rectangle(intX - 1, intY - 1, WIDTH+2, HEIGHT+2);
    }

    /**
     * A vertical blockage is when the player runs vertically into a solid block that can't be overstepped.
     * This means that we stop the vertical speed and position the player just in front of the block.
     *
     * @param blocks
     * @param blockOffsetU
     * @param blockOffsetV
     */
    private void handleVerticalBlockage(Blocks[][] blocks, int blockOffsetU, int blockOffsetV) {
        if (!DebugContext.NOCLIP) {
            for (int k = 0; k < 2; k++) {

                occupation = new Rectangle((int) x, (int) y - 1, WIDTH, HEIGHT + 2);

                int borderX1 = (int) x / WorldContainer.BLOCK_SIZE;
                int borderY1 = (int) y / WorldContainer.BLOCK_SIZE;
                int borderX2 = (int) (x + WIDTH) / WorldContainer.BLOCK_SIZE;
                int borderY2 = (int) (y + HEIGHT) / WorldContainer.BLOCK_SIZE;

                borderY1 = Math.max(0, borderY1);
                borderY2 = Math.min(blocks.length - 1, borderY2);

                for (int i = borderX1; i <= borderX2; i++) {
                    for (int j = borderY1; j <= borderY2; j++) {
                        if (blocks[j+blockOffsetV][i+blockOffsetU] != Blocks.AIR && blocks[j+blockOffsetV][i+blockOffsetU].isCds()) {
                            if (occupation.intersects(new Rectangle(i*WorldContainer.BLOCK_SIZE, j*WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                                if (oldY <= j * WorldContainer.BLOCK_SIZE - HEIGHT && speedY > 0) {
                                    y = j * WorldContainer.BLOCK_SIZE - HEIGHT;
                                    if (pvy >= 10 && !DebugContext.INVINCIBLE) {
                                        healthPoints -= (int)((pvy - 12.5))*2;
                                    }
                                    onGround = true;
                                    speedY = 0; // down
                                    pvy = 0;
                                } else if (oldY >= j * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE && speedY < 0) {
                                    y = j * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE;
                                    speedY = 0; // up
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * A vertical blockage is when the player runs horizontally into a solid block that can't be overstepped.
     * This means that we stop the horizontal speed and position the player just in front of the block.
     *
     * @param blocks
     * @param blockOffsetU
     * @param blockOffsetV
     */
    private void handleHorizontalBlockage(Blocks[][] blocks, int blockOffsetU, int blockOffsetV) {
        if (!DebugContext.NOCLIP) {
            for (int k = 0; k < 2; k++) {

                occupation = new Rectangle((int) x - 1, (int) y, WIDTH + 2, HEIGHT);

                int borderX1 = (int) x / WorldContainer.BLOCK_SIZE;
                int borderY1 = (int) y / WorldContainer.BLOCK_SIZE;
                int borderX2 = (int) (x + WIDTH) / WorldContainer.BLOCK_SIZE;
                int borderY2 = (int) (y + HEIGHT) / WorldContainer.BLOCK_SIZE;

                for (int i = borderX1; i <= borderX2; i++) {
                    for (int j = borderY1; j <= borderY2; j++) {
                        if (blocks[j+blockOffsetV][i+blockOffsetU] != Blocks.AIR && blocks[j+blockOffsetV][i+blockOffsetU].isCds()) {
                            if (occupation.intersects(new Rectangle(i*WorldContainer.BLOCK_SIZE, j*WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                                if (oldX <= i * WorldContainer.BLOCK_SIZE - WIDTH && speedX > 0) {
                                    x = i * WorldContainer.BLOCK_SIZE - WIDTH;
                                    speedX = 0; // right
                                } else if (oldX >= i * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE && speedX < 0) {
                                    x = i * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE;
                                    speedX = 0; // left
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleFalling() {
        if (!onGround) {
            speedY += 0.3;
            pvy += 0.3;
            if (speedY > MAX_FLIGHT_SPEED && DebugContext.FLIGHT) {
                speedY = MAX_FLIGHT_SPEED;
            } else if (speedY > MAX_GRAVITY_SPEED && !DebugContext.FLIGHT) {
                speedY = MAX_GRAVITY_SPEED;
            }
        }
    }

    private void handleStopping(KeyPressed keyPressed) {
        if (keyPressed != KeyPressed.LEFT && keyPressed != KeyPressed.RIGHT) {
            if (Math.abs(speedX) < 0.3) {
                speedX = 0;
            }
            if (speedX >= 0.3) {
                speedX -= 0.3;
            }
            if (speedX <= -0.3) {
                speedX += 0.3;
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
            if (speedX > -4 || DebugContext.SPEED) {
                speedX -= 0.5;
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
                imgDelay -= 1;
            }
        } else if (keyPressed == KeyPressed.RIGHT) {
            if (speedX < 4 || DebugContext.SPEED) {
                speedX += 0.5;
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
                imgDelay -= 1;
            }
        } else if (keyPressed == KeyPressed.UP) {
            if (DebugContext.FLIGHT) {
                speedY -= 1;
                pvy -= 1;
                if (speedY < (-1 * MAX_FLIGHT_SPEED)) {
                    speedY = (-1 * MAX_FLIGHT_SPEED);
                    pvy = (-1 * MAX_FLIGHT_SPEED);
                }
            } else if (onGround) {
                speedY = -7;
                pvy = -7;
            }
        } else if (keyPressed == KeyPressed.DOWN) {
            if (DebugContext.FLIGHT) {
                speedY += 1;
                pvy += 1;
                if (speedY < MAX_FLIGHT_SPEED) {
                    speedY = MAX_FLIGHT_SPEED;
                    pvy = MAX_FLIGHT_SPEED;
                }
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

    public void damage(int damage, boolean useArmor) {
        int fd = damage;
        if (useArmor) {
            fd -= sumArmor();
            for (int i = 0; i < 4; i++) {
                TerrariaClone.armor.getDurs()[i] -= 1;
                if (TerrariaClone.armor.getDurs()[i] <= 0) {
                    InventoryService.getInstance().removeLocation(TerrariaClone.armor, i, TerrariaClone.armor.getNums()[i]);
                }
            }
        }
        if (fd < 1) {
            fd = 1;
        }
        healthPoints -= fd;
    }

    public int sumArmor() {
        return (TerrariaClone.armor.getItems()[0].getArmor() +
                TerrariaClone.armor.getItems()[1].getArmor() +
                TerrariaClone.armor.getItems()[2].getArmor() +
                TerrariaClone.armor.getItems()[3].getArmor());
    }
    
    public boolean isPlayerIntoChunk(int chunkU, int chunkV, int screenWidth, int screenHeight) {
        return ((intX + screenWidth / 2 + Player.WIDTH >= chunkU * WorldContainer.CHUNK_SIZE && intX + screenWidth / 2 + Player.WIDTH <= chunkU * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE)
                || (intX - screenWidth / 2 + Player.WIDTH + WorldContainer.BLOCK_SIZE >= chunkU * WorldContainer.CHUNK_SIZE && intX - screenWidth / 2 + Player.WIDTH - WorldContainer.BLOCK_SIZE <= chunkU * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE))
            && ((intY + screenHeight / 2 + Player.HEIGHT >= chunkV * WorldContainer.CHUNK_SIZE && intY + screenHeight / 2 + Player.HEIGHT <= chunkV * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE)
                || (intY - screenHeight / 2 + Player.HEIGHT >= chunkV * WorldContainer.CHUNK_SIZE && intY - screenHeight / 2 + Player.HEIGHT <= chunkV * WorldContainer.CHUNK_SIZE + WorldContainer.CHUNK_SIZE));
    }
}
