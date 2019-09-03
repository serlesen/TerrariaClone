package com.sergio.refacto.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sergio.refacto.dto.FileInfo;

public class WorldService {

    public List<FileInfo> findWorlds() {
        File folder = new File("worlds");
        folder.mkdir();
        File[] files = folder.listFiles();

        List<FileInfo> filesInfo = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile() && files[i].getName().endsWith(".dat")) {
                filesInfo.add(FileInfo.builder()
                        .fileName(files[i].getName())
                        .name(files[i].getName().substring(0, files[i].getName().length()-4))
                        .build());
            }
        }

        return filesInfo;
    }

}
