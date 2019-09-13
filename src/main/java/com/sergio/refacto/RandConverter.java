package com.sergio.refacto;

import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandConverter {

    private static final int IMAGE_SIZE = 8;

    static String[] dirs = {"center", "tdown_both", "tdown_cw", "tdown_ccw",
        "tdown", "tup_both", "tup_cw", "tup_ccw",
        "tup", "leftright", "tright_both", "tright_cw",
        "tright_ccw", "tright", "upleftdiag", "upleft",
        "downleftdiag", "downleft", "left", "tleft_both",
        "tleft_cw", "tleft_ccw", "tleft", "uprightdiag",
        "upright", "downrightdiag", "downright", "right",
        "updown", "up", "down", "single"};

    public static void main(String[] args) {
        System.out.print("[D]uplicate, [R]andomize, or [O]utline? ");
        char option = new Scanner(System.in).next().charAt(0);
        while (true) {
            System.out.print("Generate new textures for: ");
            String name = (new Scanner(System.in)).nextLine();
            if (name.equals("exit")) {
                break;
            }
            if (option == 'O') {
                for (int k=0; k<dirs.length; k++) {
                    for (int j=2; j<6; j++) {
                        BufferedImage texture = loadImage("outlines/" + name + "/" + dirs[k] + "1.png");
                        int i, x, y;
                        int[] xy;
                        int[][] coords = new int[IMAGE_SIZE * IMAGE_SIZE][2];
                        BufferedImage result;
                        for (i=0; i<7; i++) {
                            for (x=0; x< IMAGE_SIZE; x++) {
                                for (y=0; y< IMAGE_SIZE; y++) {
                                    coords[x* IMAGE_SIZE +y][0] = x;
                                    coords[x* IMAGE_SIZE +y][1] = y;
                                }
                            }
                            result = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
                            for (x=0; x< IMAGE_SIZE; x++) {
                                for (y=0; y< IMAGE_SIZE; y++) {
                                    xy = coords[x* IMAGE_SIZE +y];
                                    result.setRGB(xy[0], xy[1], texture.getRGB(x, y));
                                }
                            }
                            try {
                                ImageIO.write(result, "png", new File("outlines/" + name + "/" + dirs[k] + j + ".png"));
                            } catch (IOException e) {
                                log.error("Error in writing file.", e);
                            }
                        }
                    }
                }
            }
            else {
                BufferedImage texture = loadImage("blocks/" + name + "/texture1.png");
                int i, x, y;
                int[] xy;
                int[][] coords = new int[IMAGE_SIZE * IMAGE_SIZE][2];
                BufferedImage result;
                for (i=0; i<7; i++) {
                    for (x=0; x< IMAGE_SIZE; x++) {
                        for (y=0; y< IMAGE_SIZE; y++) {
                            coords[x* IMAGE_SIZE +y][0] = x;
                            coords[x* IMAGE_SIZE +y][1] = y;
                        }
                    }
                    if (option == 'R') {
                        Collections.shuffle(Arrays.asList(coords));
                    }
                    result = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
                    for (x=0; x< IMAGE_SIZE; x++) {
                        for (y=0; y< IMAGE_SIZE; y++) {
                            xy = coords[x* IMAGE_SIZE +y];
                            result.setRGB(xy[0], xy[1], texture.getRGB(x, y));
                        }
                    }
                    try {
                        ImageIO.write(result, "png", new File("blocks/" + name + "/texture" + (i+2) + ".png"));
                    } catch (IOException e) {
                        log.error("Error in writing file.", e);
                    }
                }
            }
        }
    }

    private static BufferedImage loadImage(String path) {
        URL url = RandConverter.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        } catch (Exception e) {
            log.error("Error: could not load image '" + path + "'.", e);
        }
        return image;
    }
}
