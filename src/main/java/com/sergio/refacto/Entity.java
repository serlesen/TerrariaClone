package com.sergio.refacto;

import java.awt.*;
import java.awt.image.*;
import java.io.Serializable;
import java.net.URL;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

import com.sergio.refacto.dto.EntityType;
import com.sergio.refacto.dto.ImageState;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.tools.RandomTool;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Entity implements Serializable {

    double x, y, vx, vy, oldx, oldy, n;
    int ix, iy, ivx, ivy, width, height, bx1, bx2, by1, by2, bcount;
    int i, j, k;
    int BLOCKSIZE = TerrariaClone.getBLOCKSIZE();
    double mdelay = 0;

    int totalHealthPoints, healthPoints, armorPoints, attackPoints;

    Items item;
    short num, dur;

    boolean onGround, immune, grounded, onGroundDelay, nohit;

    int dframes, imgDelay;

    EntityType entityType, AI;

    ImageState imgState;

    Rectangle rect;

    Entity newMob;

    transient BufferedImage image;

    public Entity(double x, double y, double vx, double vy, EntityType entityType) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.entityType = entityType;
        oldx = x;
        oldy = y;
        ix = (int)x;
        iy = (int)y;
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

        ix = (int)x;
        iy = (int)y;
        ivx = (int)vx;
        ivy = (int)vy;

        rect = new Rectangle(ix-1, iy, width+2, height);

        imgDelay = 0;
        bcount = 0;
        if (AI == EntityType.BAT) {
            imgState = ImageState.NORMAL_RIGHT;
            this.vx = 3;
        } else {
            imgState = ImageState.STILL_RIGHT;
        }
    }

    public Entity(double x, double y, double vx, double vy, Items item, short num) {
        this(x, y, vx, vy, item, num, (short)0, 0);
    }

    public Entity(double x, double y, double vx, double vy, Items item, short num, int mdelay) {
        this(x, y, vx, vy, item, num, (short)0, mdelay);
    }

    public Entity(double x, double y, double vx, double vy, Items item, short num, short dur) {
        this(x, y, vx, vy, item, num, dur, 0);
    }

    public Entity(double x, double y, double vx, double vy, Items item, short num, short dur, int mdelay) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.item = item;
        this.num = num;
        this.dur = dur;
        this.mdelay = mdelay;
        oldx = x;
        oldy = y;
        ix = (int)x;
        iy = (int)y;

        dframes = 0;

        image = TerrariaClone.getItemImgs().get(item.getIndex());

        width = image.getWidth()*2; height = image.getHeight()*2;
    }

    public boolean update(Integer[][] blocks, Player player, int u, int v) {
        newMob = null;
        if (entityType == null) {
            if (!onGround) {
                vy = vy + 0.3;
                if (vy > 7) {
                    vy = 7;
                }
            }
            if (vx < -0.15) {
                vx = vx + 0.15;
            }
            else if (vx > 0.15) {
                vx = vx - 0.15;
            }
            else {
                vx = 0;
            }
            collide(blocks, player, u, v);
            mdelay -= 1;
        }
        if (AI == EntityType.BULLET) {
            if (collide(blocks, player, u, v)) {
                return true;
            }
        } else if (AI == EntityType.ZOMBIE) {
            if (!onGround) {
                vy = vy + 0.3;
                if (vy > 7) {
                    vy = 7;
                }
            }
            if (x > player.x) {
                vx = Math.max(vx - 0.1, -1.2);
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
                vx = Math.min(vx + 0.1, 1.2);
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
            collide(blocks, player, u, v);
        } else if (AI == EntityType.BUBBLE) {
            if (x > player.x) {
                vx = Math.max(vx - 0.1, -1.2);
            }
            else {
                vx = Math.min(vx + 0.1, 1.2);
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.1, -1.2);
            }
            else {
                vy = Math.min(vy + 0.1, 1.2);
            }
            collide(blocks, player, u, v);
        } else if (AI == EntityType.FAST_BUBBLE) {
            if (x > player.x) {
                vx = Math.max(vx - 0.2, -2.4);
            }
            else {
                vx = Math.min(vx + 0.2, 2.4);
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.2, -2.4);
            }
            else {
                vy = Math.min(vy + 0.2, 2.4);
            }
            collide(blocks, player, u, v);
        } else if (AI == EntityType.SHOOTING_STAR) {
            n = Math.atan2(player.y - y, player.x - x);
            vx += Math.cos(n)/10;
            vy += Math.sin(n)/10;
            if (vx < -5) vx = -5;
            if (vx > 5) vx = 5;
            if (vy < -5) vy = -5;
            if (vy > 5) vy = 5;
            collide(blocks, player, u, v);
        } else if (AI == EntityType.SANDBOT) {
            if (Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2)) > 160) {
                if (x > player.x) {
                    vx = Math.max(vx - 0.1, -1.2);
                }
                else {
                    vx = Math.min(vx + 0.1, 1.2);
                }
                if (y > player.y) {
                    vy = Math.max(vy - 0.1, -1.2);
                }
                else {
                    vy = Math.min(vy + 0.1, 1.2);
                }
            }
            else {
                if (x < player.x) {
                    vx = Math.max(vx - 0.1, -1.2);
                }
                else {
                    vx = Math.min(vx + 0.1, 1.2);
                }
                if (y < player.y) {
                    vy = Math.max(vy - 0.1, -1.2);
                }
                else {
                    vy = Math.min(vy + 0.1, 1.2);
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
            collide(blocks, player, u, v);
        } else if (AI == EntityType.BAT) {
            if (vx > 3) {
                vx = 3;
            }
            if (vx < 3) {
                vx = -3;
            }
            if (y > player.y) {
                vy = Math.max(vy - 0.05, -2.0);
            }
            else {
                vy = Math.min(vy + 0.05, 2.0);
            }
            imgDelay -= 1;
            if (vx > 0 && imgState != ImageState.NORMAL_RIGHT) {
                imgState = ImageState.NORMAL_RIGHT;
                image = loadImage("sprites/monsters/" + entityType.getFileName() + "/normal_right.png");
                imgDelay = 10;
            }
            if (vx < 0 && imgState != ImageState.NORMAL_LEFT) {
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
            collide(blocks, player, u, v);
        } else if (AI == EntityType.BEE) {
            double theta = Math.atan2(player.y - y, player.x - x);
            vx = Math.cos(theta)*2.5;
            vy = Math.sin(theta)*2.5;
            collide(blocks, player, u, v);
        }
        return false;
    }

    public boolean collide(Integer[][] blocks, Player player, int u, int v) {
        boolean rv = false;

        grounded = (onGround || onGroundDelay);

        onGroundDelay = onGround;

        oldx = x; oldy = y;

        x = x + vx;

        for (i=0; i<2; i++) {
            ix = (int)x;
            iy = (int)y;
            ivx = (int)vx;
            ivy = (int)vy;

            rect = new Rectangle(ix-1, iy, width+2, height);

            bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
            bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

            bx1 = Math.max(0, bx1); by1 = Math.max(0, by1);
            bx2 = Math.min(blocks[0].length - 1, bx2); by2 = Math.min(blocks.length - 1, by2);

            for (i=bx1; i<=bx2; i++) {
                for (j=by1; j<=by2; j++) {
                    if (blocks[j][i] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                        if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                            if (oldx <= i*16 - width && (vx > 0 || AI == EntityType.SHOOTING_STAR)) {
                                x = i*16 - width;
                                if (AI == EntityType.BUBBLE) {
                                    vx = -vx;
                                }
                                else if (AI == EntityType.ZOMBIE) {
                                    vx = 0;
                                    if (onGround && player.x > x) {
                                        vy = -7;
                                    }
                                }
                                else if (AI == EntityType.BAT) {
                                    vx = -vx;
                                }
                                else {
                                    vx = 0; // right
                                }
                                rv = true;
                            }
                            if (oldx >= i*16 + BLOCKSIZE && (vx < 0 || AI == EntityType.SHOOTING_STAR)) {
                                x = i*16 + BLOCKSIZE;
                                if (AI == EntityType.BUBBLE) {
                                    vx = -vx;
                                }
                                else if (AI == EntityType.ZOMBIE) {
                                    vx = 0;
                                    if (onGround && player.x < x) {
                                        vy = -7;
                                    }
                                }
                                else if (AI == EntityType.BAT) {
                                    vx = -vx;
                                }
                                else {
                                    vx = 0; // left
                                }
                                rv = true;
                            }
                        }
                    }
                }
            }
        }

        y = y + vy;
        onGround = false;

        for (i=0; i<2; i++) {
            ix = (int)x;
            iy = (int)y;
            ivx = (int)vx;
            ivy = (int)vy;

            rect = new Rectangle(ix, iy-1, width, height+2);

            bx1 = (int)x/BLOCKSIZE; by1 = (int)y/BLOCKSIZE;
            bx2 = (int)(x+width)/BLOCKSIZE; by2 = (int)(y+height)/BLOCKSIZE;

            bx1 = Math.max(0, bx1); by1 = Math.max(0, by1);
            bx2 = Math.min(blocks[0].length - 1, bx2); by2 = Math.min(blocks.length - 1, by2);

            for (i=bx1; i<=bx2; i++) {
                for (j=by1; j<=by2; j++) {
                    if (blocks[j][i] != 0 && TerrariaClone.getBLOCKCD().get(blocks[j+v][i+u])) {
                        if (rect.intersects(new Rectangle(i*BLOCKSIZE, j*BLOCKSIZE, BLOCKSIZE, BLOCKSIZE))) {
                            if (oldy <= j*16 - height && (vy > 0 || AI == EntityType.SHOOTING_STAR)) {
                                y = j*16 - height;
                                onGround = true;
                                if (AI == EntityType.BUBBLE) {
                                    vy = -vy;
                                }
                                else {
                                    vy = 0; // down
                                }
                                rv = true;
                            }
                            if (oldy >= j*16 + BLOCKSIZE && (vy < 0 || AI == EntityType.SHOOTING_STAR)) {
                                y = j*16 + BLOCKSIZE;
                                if (AI == EntityType.BUBBLE) {
                                    vy = -vy;
                                }
                                else {
                                    vy = 0; // up
                                }
                                rv = true;
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

        return rv;
    }

    public boolean hit(int damage, Player player) {
        if (!immune && !nohit) {
            healthPoints -= Math.max(1, damage - armorPoints);
            immune = true;
            if (AI == EntityType.SHOOTING_STAR) {
                if (player.x + player.width/2 < x + width/2) {
                    vx = 4;
                }
                else {
                    vx = -4;
                }
            }
            else {
                if (player.x + player.width/2 < x + width/2) {
                    vx += 4;
                }
                else {
                    vx -= 4;
                }
                vy -= 1.2;
            }
        }
        return healthPoints <= 0;
    }

    public List<Items> drops() {
        List<Items> dropList = new ArrayList<>();
        if (entityType == EntityType.BLUE_BUBBLE) {
            for (i=0; i< RandomTool.nextInt(3); i++) {
                dropList.add(Items.BLUE_GOO);
            }
        } else if (entityType == EntityType.GREEN_BUBBLE) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.GREEN_GOO);
            }
        } else if (entityType == EntityType.RED_BUBBLE) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.RED_GOO);
            }
        } else if (entityType == EntityType.YELLOW_BUBBLE) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.YELLOW_GOO);
            }
        } else if (entityType == EntityType.BLACK_BUBBLE) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.BLACK_GOO);
            }
        } else if (entityType == EntityType.WHITE_BUBBLE) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.WHITE_GOO);
            }
        } else if (entityType == EntityType.SHOOTING_STAR) {
            for (i=0; i<RandomTool.nextInt(2); i++) {
                dropList.add(Items.ASTRAL_SHARD);
            }
        } else if (entityType == EntityType.ZOMBIE) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.ROTTEN_CHUNK);
            }
        } else if (entityType == EntityType.ARMORED_ZOMBIE) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
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
            for (i=0; i<RandomTool.nextInt(3); i++) {
                dropList.add(Items.SAND);
            }
            if (RandomTool.nextInt(2) == 0) {
                dropList.add(Items.ZYTHIUM_ORE);
            }
            if (RandomTool.nextInt(6) == 0) {
                dropList.add(Items.SILICON_ORE);
            }
        } else if (entityType == EntityType.SNOWMAN) {
            for (i=0; i<RandomTool.nextInt(3); i++) {
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
//            System.out.println("[ERROR] could not load image '" + path + "'.");
        }
        return image;
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
