package com.sergio.refacto;

import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LightConverter {

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
        for (int i=0; i<17; i++) {
            System.out.print("Generate new textures [" + i + "] for: ");
            String name = (new Scanner(System.in)).nextLine();
            BufferedImage light = loadImage("light/" + i + ".png");
            for (int j=1; j<9; j++) {
                BufferedImage texture = loadImage("blocks/" + name + "/texture" + j + ".png");
                texture.createGraphics().drawImage(light,
                    0, 0, IMAGE_SIZE, IMAGE_SIZE,
                    0, 0, IMAGE_SIZE, IMAGE_SIZE,
                    null);
                try {
                    ImageIO.write(texture, "png", new File("blocks/" + name + "/texture" + j + ".png"));
                }
                catch (IOException e) {
                    log.error("Error in writing file.", e);
                }
            }
        }
    }

    private static BufferedImage loadImage(String path) {
        URL url = RandConverter.class.getResource(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            log.error("Error: could not load image '" + path + "'.", e);
        }
        return image;
    }
}
