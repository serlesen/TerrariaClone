package com.sergio.refacto.tools;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

import com.sergio.refacto.TerrariaClone;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourcesLoader {

    public BufferedImage loadImage(String path) {
        InputStream url = TerrariaClone.class.getClassLoader().getResourceAsStream(path);
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            //
        }
        return image;
    }
}
