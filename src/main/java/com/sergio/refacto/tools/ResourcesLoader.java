package com.sergio.refacto.tools;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

import com.sergio.refacto.TerrariaClone;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ResourcesLoader {

    public BufferedImage loadImage(String path) {
        InputStream url = TerrariaClone.class.getClassLoader().getResourceAsStream(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        } catch (Exception e) {
            log.error("[ERROR] could not load image '" + path + "'.", e);
        }
        return image;
    }
}
