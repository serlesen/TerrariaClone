package com.sergio.refacto.items;

import java.awt.image.BufferedImage;
import java.util.Map;

import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.dto.Items;
import com.sergio.refacto.init.BackgroundImagesInitializer;
import com.sergio.refacto.init.BlockImagesInitializer;
import com.sergio.refacto.init.ItemImagesInitializer;
import com.sergio.refacto.init.LightLevelsInitializer;
import com.sergio.refacto.init.OutlineImagesInitializer;
import com.sergio.refacto.tools.ResourcesLoader;

public class ImagesContainer {

    private static ImagesContainer instance;

    public BufferedImage titleScreen, selectWorld, newWorld, saveExit;

    public BufferedImage cloudsImage;
    public BufferedImage wcnct_px;

    public Map<Backgrounds, BufferedImage> backgroundImgs;
    public Map<Items, BufferedImage> itemImgs;
    public Map<Integer, BufferedImage> lightLevelsImgs;
    public Map<String, BufferedImage> blockImgs;
    public Map<String, BufferedImage> outlineImgs;

    private ImagesContainer() {
        titleScreen = ResourcesLoader.loadImage("interface/title_screen.png");
        selectWorld = ResourcesLoader.loadImage("interface/select_world.png");
        newWorld = ResourcesLoader.loadImage("interface/new_world.png");
        saveExit = ResourcesLoader.loadImage("interface/save_exit.png");
        cloudsImage = ResourcesLoader.loadImage("environment/cloud1.png");
        wcnct_px = ResourcesLoader.loadImage("misc/wcnct.png");

        backgroundImgs = BackgroundImagesInitializer.init();
        itemImgs = ItemImagesInitializer.init();
        blockImgs = BlockImagesInitializer.init();
        outlineImgs = OutlineImagesInitializer.init();
        lightLevelsImgs = LightLevelsInitializer.init();
    }

    public static ImagesContainer getInstance() {
        if (instance == null) {
            instance = new ImagesContainer();
        }
        return instance;
    }
}
