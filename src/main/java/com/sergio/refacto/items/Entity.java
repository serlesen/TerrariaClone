package com.sergio.refacto.items;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import com.sergio.refacto.TerrariaClone;
import com.sergio.refacto.dto.Blocks;
import com.sergio.refacto.dto.EntityType;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.tools.RandomTool;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Entity implements Serializable {

    double x, y, speedX, speedY;
    private double oldx, oldy, n;
    int intX, intY, width, height;
    private int intSpeedX, intSpeedY, bcount;
    double mdelay = 0;

    int totalHealthPoints, healthPoints, armorPoints, attackPoints;

    Items item;
    short num, dur;

    private boolean onGround, immune, grounded, onGroundDelay, nohit;

    private int dframes, imgDelay;

    private EntityType entityType, AI;

    private ImageState imgState;

    private Rectangle rect;

    private Entity newMob;

    private transient BufferedImage image;

    public Entity(double x, double y, double speedX, double speedY, EntityType entityType) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.entityType = entityType;
        oldx = x;
        oldy = y;
        intX = (int)x;
        intY = (int)y;
        nohit = false;

        switch (entityType) {
            case BLUE_BUBBLE:
            case GREEN_BUBBLE:
            case RED_BUBBLE:
            case YELLOW_BUBBLE:
            case BLACK_BUBBLE:
                AI = EntityType.BUBBLE;
                break;
            case WHITE_BUBBLE:
                AI = EntityType.FAST_BUBBLE;
                break;
            case ZOMBIE:
            case ARMORED_ZOMBIE:
            case SNOWMAN:
            case SKELETON:
                AI = EntityType.ZOMBIE;
                break;
            case SHOOTING_STAR:
                AI = EntityType.SHOOTING_STAR;
                break;
            case SANDBOT:
                AI = EntityType.SANDBOT;
                break;
            case SANDBOT_BULLET:
                AI = EntityType.BULLET;
                break;
            case BAT:
                AI = EntityType.BAT;
                break;
            case BEE:
                AI = EntityType.BEE;
                break;
        }

        totalHealthPoints = entityType.getHealthPoints();
        healthPoints = entityType.getHealthPoints();
        armorPoints = entityType.getArmorPoints();
        attackPoints = entityType.getAttackPoints();

        if (AI == EntityType.BUBBLE || AI == EntityType.FAST_BUBBLE || AI == EntityType.SHOOTING_STAR || AI == EntityType.SANDBOT || AI == EntityType.BULLET || AI == EntityType.BEE) {
            image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal.png");
        }
        if (AI == EntityType.ZOMBIE) {
            image = loadImage("sprites/monsters/" + entityType.getFileName() + "/right_still.png");
        }
        if (AI == EntityType.BAT) {
            image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal_right.png");
        }

        width = image.getWidth()*2;
        height = image.getHeight()*2;

        intX = (int)x;
        intY = (int)y;
        intSpeedX = (int) speedX;
        intSpeedY = (int) speedY;

        rect = new Rectangle(intX -1, intY, width+2, height);

        imgDelay = 0;
        bcount = 0;
        if (AI == EntityType.BAT) {
            imgState = ImageState.NORMAL_RIGHT;
            this.speedX = 3;
        } else {
            imgState = ImageState.STILL_RIGHT;
        }
    }

    public Entity(double x, double y, double speedX, double speedY, Items item, short num) {
        this(x, y, speedX, speedY, item, num, (short)0, 0);
    }

    public Entity(double x, double y, double speedX, double speedY, Items item, short num, int mdelay) {
        this(x, y, speedX, speedY, item, num, (short)0, mdelay);
    }

    public Entity(double x, double y, double speedX, double speedY, Items item, short num, short dur) {
        this(x, y, speedX, speedY, item, num, dur, 0);
    }

    public Entity(double x, double y, double speedX, double speedY, Items item, short num, short dur, int mdelay) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.item = item;
        this.num = num;
        this.dur = dur;
        this.mdelay = mdelay;
        oldx = x;
        oldy = y;
        intX = (int)x;
        intY = (int)y;

        dframes = 0;

        image = ImagesContainer.getInstance().itemImgs.get(item);

        width = image.getWidth()*2;
        height = image.getHeight()*2;
    }

    public boolean update(Blocks[][] blocks, Player player, int blockOffsetU, int blockOffsetV) {
        newMob = null;
        if (entityType == null) {
            if (!onGround) {
                speedY = speedY + 0.3;
                if (speedY > 7) {
                    speedY = 7;
                }
            }
            if (speedX < -0.15) {
                speedX = speedX + 0.15;
            }
            else if (speedX > 0.15) {
                speedX = speedX - 0.15;
            }
            else {
                speedX = 0;
            }
            collide(blocks, player, blockOffsetU, blockOffsetV);
            mdelay -= 1;
        }
        if (AI == EntityType.BULLET) {
            if (collide(blocks, player, blockOffsetU, blockOffsetV)) {
                return true;
            }
        } else if (AI == EntityType.ZOMBIE) {
            if (!onGround) {
                speedY = speedY + 0.3;
                if (speedY > 7) {
                    speedY = 7;
                }
            }
            if (x > player.x) {
                speedX = Math.max(speedX - 0.1, -1.2);
                if (imgState.isStill() || (imgState.isWalk() && imgState.isRight())) {
                    imgDelay = 10;
                    imgState = ImageState.WALK_LEFT_2;
                    image = loadImage("sprites/monsters/" + entityType.getFileName() + "/left_walk.png");
                }
                if (imgDelay <= 0) {
                    if (imgState == ImageState.WALK_LEFT_1) {
                        imgDelay = 10;
                        imgState = ImageState.WALK_LEFT_2;
                        image = loadImage("sprites/monsters/" + entityType.getFileName() + "/left_walk.png");
                    }
                    else {
                        if (imgState == ImageState.WALK_LEFT_2) {
                            imgDelay = 10;
                            imgState = ImageState.WALK_LEFT_1;
                            image = loadImage("sprites/monsters/" + entityType.getFileName() + "/left_still.png");
                        }
                    }
                }
                else {
                    imgDelay = imgDelay - 1;
                }
            }
            else {
                speedX = Math.min(speedX + 0.1, 1.2);
                if (imgState.isStill() || (imgState.isWalk() && imgState.isLeft())) {
                    imgDelay = 10;
                    imgState = ImageState.WALK_RIGHT_2;
                    image = loadImage("sprites/monsters/" + entityType.getFileName() + "/right_walk.png");
                }
                if (imgDelay <= 0) {
                    if (imgState == ImageState.WALK_RIGHT_1) {
                        imgDelay = 10;
                        imgState = ImageState.WALK_RIGHT_2;
                        image = loadImage("sprites/monsters/" + entityType.getFileName() + "/right_walk.png");
                    }
                    else {
                        if (imgState == ImageState.WALK_RIGHT_2) {
                            imgDelay = 10;
                            imgState = ImageState.WALK_RIGHT_1;
                            image = loadImage("sprites/monsters/" + entityType.getFileName() + "/right_still.png");
                        }
                    }
                }
                else {
                    imgDelay = imgDelay - 1;
                }
            }
            if (!grounded) {
                if (imgState == ImageState.STILL_LEFT || imgState.isWalkLeft()) {
                    image = loadImage("sprites/monsters/" + entityType.getFileName() + "/left_jump.png");
                }
                if (imgState == ImageState.STILL_RIGHT || imgState.isWalkRight()) {
                    image = loadImage("sprites/monsters/" + entityType.getFileName() + "/right_jump.png");
                }
            }
            collide(blocks, player, blockOffsetU, blockOffsetV);
        } else if (AI == EntityType.BUBBLE) {
            if (x > player.x) {
                speedX = Math.max(speedX - 0.1, -1.2);
            }
            else {
                speedX = Math.min(speedX + 0.1, 1.2);
            }
            if (y > player.y) {
                speedY = Math.max(speedY - 0.1, -1.2);
            }
            else {
                speedY = Math.min(speedY + 0.1, 1.2);
            }
            collide(blocks, player, blockOffsetU, blockOffsetV);
        } else if (AI == EntityType.FAST_BUBBLE) {
            if (x > player.x) {
                speedX = Math.max(speedX - 0.2, -2.4);
            }
            else {
                speedX = Math.min(speedX + 0.2, 2.4);
            }
            if (y > player.y) {
                speedY = Math.max(speedY - 0.2, -2.4);
            }
            else {
                speedY = Math.min(speedY + 0.2, 2.4);
            }
            collide(blocks, player, blockOffsetU, blockOffsetV);
        } else if (AI == EntityType.SHOOTING_STAR) {
            n = Math.atan2(player.y - y, player.x - x);
            speedX += Math.cos(n)/10;
            speedY += Math.sin(n)/10;
            if (speedX < -5) speedX = -5;
            if (speedX > 5) speedX = 5;
            if (speedY < -5) speedY = -5;
            if (speedY > 5) speedY = 5;
            collide(blocks, player, blockOffsetU, blockOffsetV);
        } else if (AI == EntityType.SANDBOT) {
            if (Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2)) > 160) {
                if (x > player.x) {
                    speedX = Math.max(speedX - 0.1, -1.2);
                }
                else {
                    speedX = Math.min(speedX + 0.1, 1.2);
                }
                if (y > player.y) {
                    speedY = Math.max(speedY - 0.1, -1.2);
                }
                else {
                    speedY = Math.min(speedY + 0.1, 1.2);
                }
            }
            else {
                if (x < player.x) {
                    speedX = Math.max(speedX - 0.1, -1.2);
                }
                else {
                    speedX = Math.min(speedX + 0.1, 1.2);
                }
                if (y < player.y) {
                    speedY = Math.max(speedY - 0.1, -1.2);
                }
                else {
                    speedY = Math.min(speedY + 0.1, 1.2);
                }
            }
            bcount += 1;
            if (bcount == 110) {
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/ready1.png");
            }
            if (bcount == 130) {
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/ready2.png");
            }
            if (bcount == 150) {
                double theta = Math.atan2(player.y - y, player.x - x);
                newMob = new Entity(x, y, Math.cos(theta)*3.5, Math.sin(theta)*3.5, EntityType.SANDBOT_BULLET);
            }
            if (bcount == 170) {
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/ready1.png");
            }
            if (bcount == 190) {
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal.png");
                bcount = 0;
            }
            collide(blocks, player, blockOffsetU, blockOffsetV);
        } else if (AI == EntityType.BAT) {
            if (speedX > 3) {
                speedX = 3;
            }
            if (speedX < 3) {
                speedX = -3;
            }
            if (y > player.y) {
                speedY = Math.max(speedY - 0.05, -2.0);
            }
            else {
                speedY = Math.min(speedY + 0.05, 2.0);
            }
            imgDelay -= 1;
            if (speedX > 0 && imgState != ImageState.NORMAL_RIGHT) {
                imgState = ImageState.NORMAL_RIGHT;
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal_right.png");
                imgDelay = 10;
            }
            if (speedX < 0 && imgState != ImageState.NORMAL_LEFT) {
                imgState = ImageState.NORMAL_LEFT;
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal_left.png");
                imgDelay = 10;
            }
            if (imgState == ImageState.NORMAL_LEFT && imgDelay <= 0) {
                imgState = ImageState.FLAP_LEFT;
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/flap_left.png");
                imgDelay = 10;
            }
            if (imgState == ImageState.NORMAL_RIGHT && imgDelay <= 0) {
                imgState = ImageState.FLAP_RIGHT;
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/flap_right.png");
                imgDelay = 10;
            }
            if (imgState == ImageState.FLAP_LEFT && imgDelay <= 0) {
                imgState = ImageState.NORMAL_LEFT;
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal_left.png");
                imgDelay = 10;
            }
            if (imgState == ImageState.FLAP_RIGHT && imgDelay <= 0) {
                imgState = ImageState.NORMAL_RIGHT;
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal_right.png");
                imgDelay = 10;
            }
            collide(blocks, player, blockOffsetU, blockOffsetV);
        } else if (AI == EntityType.BEE) {
            double theta = Math.atan2(player.y - y, player.x - x);
            speedX = Math.cos(theta)*2.5;
            speedY = Math.sin(theta)*2.5;
            collide(blocks, player, blockOffsetU, blockOffsetV);
        }
        return false;
    }

    public boolean collide(Blocks[][] blocks, Player player, int blockOffsetU, int blockOffsetV) {
        boolean rv = false;

        grounded = (onGround || onGroundDelay);

        onGroundDelay = onGround;

        oldx = x; oldy = y;

        x = x + speedX;

        for (int i=0; i<2; i++) {
            intX = (int)x;
            intY = (int)y;
            intSpeedX = (int) speedX;
            intSpeedY = (int) speedY;

            rect = new Rectangle(intX -1, intY, width+2, height);

            int bx1 = (int)x/WorldContainer.BLOCK_SIZE;
            int by1 = (int)y/WorldContainer.BLOCK_SIZE;
            int bx2 = (int)(x+width)/WorldContainer.BLOCK_SIZE;
            int by2 = (int)(y+height)/WorldContainer.BLOCK_SIZE;

            bx1 = Math.max(0, bx1); by1 = Math.max(0, by1);
            bx2 = Math.min(blocks[0].length - 1, bx2); by2 = Math.min(blocks.length - 1, by2);

            for (i=bx1; i<=bx2; i++) {
                for (int j=by1; j<=by2; j++) {
                    if (blocks[j][i] != Blocks.AIR && blocks[j+blockOffsetV][i+blockOffsetU].isCds()) {
                        if (rect.intersects(new Rectangle(i*WorldContainer.BLOCK_SIZE, j*WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                            if (oldx <= i * WorldContainer.BLOCK_SIZE - width && (speedX > 0 || AI == EntityType.SHOOTING_STAR)) {
                                x = i * WorldContainer.BLOCK_SIZE - width;
                                if (AI == EntityType.BUBBLE) {
                                    speedX = -speedX;
                                }
                                else if (AI == EntityType.ZOMBIE) {
                                    speedX = 0;
                                    if (onGround && player.x > x) {
                                        speedY = -7;
                                    }
                                }
                                else if (AI == EntityType.BAT) {
                                    speedX = -speedX;
                                }
                                else {
                                    speedX = 0; // right
                                }
                                rv = true;
                            }
                            if (oldx >= i * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE && (speedX < 0 || AI == EntityType.SHOOTING_STAR)) {
                                x = i * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE;
                                if (AI == EntityType.BUBBLE) {
                                    speedX = -speedX;
                                }
                                else if (AI == EntityType.ZOMBIE) {
                                    speedX = 0;
                                    if (onGround && player.x < x) {
                                        speedY = -7;
                                    }
                                }
                                else if (AI == EntityType.BAT) {
                                    speedX = -speedX;
                                }
                                else {
                                    speedX = 0; // left
                                }
                                rv = true;
                            }
                        }
                    }
                }
            }
        }

        y = y + speedY;
        onGround = false;

        for (int i=0; i<2; i++) {
            intX = (int)x;
            intY = (int)y;
            intSpeedX = (int) speedX;
            intSpeedY = (int) speedY;

            rect = new Rectangle(intX, intY -1, width, height+2);

            int bx1 = (int)x/WorldContainer.BLOCK_SIZE;
            int by1 = (int)y/ WorldContainer.BLOCK_SIZE;
            int bx2 = (int)(x+width)/WorldContainer.BLOCK_SIZE;
            int by2 = (int)(y+height)/WorldContainer.BLOCK_SIZE;

            bx1 = Math.max(0, bx1); by1 = Math.max(0, by1);
            bx2 = Math.min(blocks[0].length - 1, bx2); by2 = Math.min(blocks.length - 1, by2);

            for (i=bx1; i<=bx2; i++) {
                for (int j=by1; j<=by2; j++) {
                    if (blocks[j][i] != Blocks.AIR && blocks[j+blockOffsetV][i+blockOffsetU].isCds()) {
                        if (rect.intersects(new Rectangle(i*WorldContainer.BLOCK_SIZE, j*WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE, WorldContainer.BLOCK_SIZE))) {
                            if (oldy <= j * WorldContainer.BLOCK_SIZE - height && (speedY > 0 || AI == EntityType.SHOOTING_STAR)) {
                                y = j * WorldContainer.BLOCK_SIZE - height;
                                onGround = true;
                                if (AI == EntityType.BUBBLE) {
                                    speedY = -speedY;
                                }
                                else {
                                    speedY = 0; // down
                                }
                                rv = true;
                            }
                            if (oldy >= j * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE && (speedY < 0 || AI == EntityType.SHOOTING_STAR)) {
                                y = j * WorldContainer.BLOCK_SIZE + WorldContainer.BLOCK_SIZE;
                                if (AI == EntityType.BUBBLE) {
                                    speedY = -speedY;
                                }
                                else {
                                    speedY = 0; // up
                                }
                                rv = true;
                            }
                        }
                    }
                }
            }
        }

        intX = (int)x;
        intY = (int)y;
        intSpeedX = (int) speedX;
        intSpeedY = (int) speedY;

        rect = new Rectangle(intX -1, intY -1, width+2, height+2);

        return rv;
    }

    public boolean hit(int damage, Player player) {
        if (!immune && !nohit) {
            healthPoints -= Math.max(1, damage - armorPoints);
            immune = true;
            if (AI == EntityType.SHOOTING_STAR) {
                if (player.x + Player.WIDTH/2 < x + width/2) {
                    speedX = 4;
                }
                else {
                    speedX = -4;
                }
            }
            else {
                if (player.x + Player.WIDTH/2 < x + width/2) {
                    speedX += 4;
                }
                else {
                    speedX -= 4;
                }
                speedY -= 1.2;
            }
        }
        return healthPoints <= 0;
    }

    public List<Items> drops() {
        List<Items> dropList = new ArrayList<>();
        if (entityType == EntityType.BLUE_BUBBLE) {
            for (int i=0; i< RandomTool.nextInt(3); i++) {
                dropList.add(Items.BLUE_GOO);
            }
        } else if (entityType == EntityType.GREEN_BUBBLE) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.GREEN_GOO);
            }
        } else if (entityType == EntityType.RED_BUBBLE) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.RED_GOO);
            }
        } else if (entityType == EntityType.YELLOW_BUBBLE) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.YELLOW_GOO);
            }
        } else if (entityType == EntityType.BLACK_BUBBLE) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.BLACK_GOO);
            }
        } else if (entityType == EntityType.WHITE_BUBBLE) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.WHITE_GOO);
            }
        } else if (entityType == EntityType.SHOOTING_STAR) {
            for (int i=0; i<RandomTool.nextInt(2); i++) {
                dropList.add(Items.ASTRAL_SHARD);
            }
        } else if (entityType == EntityType.ZOMBIE) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.ROTTEN_CHUNK);
            }
        } else if (entityType == EntityType.ARMORED_ZOMBIE) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.ROTTEN_CHUNK);
            }
            if (RandomTool.nextInt(15) == 0) {
                dropList.add(Items.IRON_HELMET);
            }
            if (RandomTool.nextInt(15) == 0) {
                dropList.add(Items.IRON_CHESTPLATE);
            }
            if (RandomTool.nextInt(15) == 0) {
                dropList.add(Items.IRON_LEGGINGS);
            }
            if (RandomTool.nextInt(15) == 0) {
                dropList.add(Items.IRON_GREAVES);
            }
        } else if (entityType == EntityType.SANDBOT) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.SAND);
            }
            if (RandomTool.nextInt(2) == 0) {
                dropList.add(Items.ZYTHIUM_ORE);
            }
            if (RandomTool.nextInt(6) == 0) {
                dropList.add(Items.SILICON_ORE);
            }
        } else if (entityType == EntityType.SNOWMAN) {
            for (int i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.SNOW);
            }
        }
        return dropList;
    }

    public void reloadImage() {
        if (AI == EntityType.BUBBLE || AI == EntityType.SHOOTING_STAR) {
            image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal.png");
        }
        if (AI == EntityType.ZOMBIE) {
            image = loadImage("sprites/monsters/" + entityType.getFileName() + "/right_still.png");
        }
    }

    public static BufferedImage loadImage(String path) {
        URL url = TerrariaClone.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            log.error("[ERROR] could not load image '" + path + "'.", e);
        }
        return image;
    }
}
