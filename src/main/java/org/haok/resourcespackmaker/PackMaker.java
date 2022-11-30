package org.haok.resourcespackmaker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static org.haok.resourcespackmaker.util.Util.*;
import static org.haok.resourcespackmaker.PackConfig.*;

public class PackMaker {
    public static void makePack(boolean isZip) throws Exception {
        if (success) {
            success = false;
        }
        File packPath = new File(exportPath.getAbsolutePath() + App.SEPARATOR + packName);
        File pack_mcmeta = new File(packPath.getAbsolutePath() + App.SEPARATOR + "pack.mcmeta");
        LOGGER.debug("make pack directory:" + packPath.mkdirs());
        LOGGER.debug("make pack.mcmeta:" + pack_mcmeta.createNewFile());
        BufferedWriter writer = new BufferedWriter(new FileWriter(pack_mcmeta));
        writer.write("{\"pack\":{\"pack_format\":" + packVersion + ",\"description\":\"" + packIntroduction + "\"}}");
        writer.close();
        if (!(ttfFile == null)) {
            File fontPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "font");
            LOGGER.debug("make font directory"+fontPath.mkdirs());
            File fontFile = new File(fontPath.getAbsolutePath() + App.SEPARATOR + "font.ttf");
            copy(ttfFile, fontFile);
            File json = new File(fontPath + App.SEPARATOR + "default.json");
            LOGGER.debug("make font json file:" + json.createNewFile());
            BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(json));
            jsonWriter.write("{\"providers\":[{\"type\":\"ttf\",\"file\":\"minecraft:font.ttf\",\"shift\":[0,1],\"size\":11.0,\"oversample\":4.0}]}");
            jsonWriter.close();
            File icon = null;
            if (!(iconFile == null)) {
                icon = new File(packPath.getAbsolutePath() + App.SEPARATOR + "pack.png");
                copy(iconFile, icon);
            }
            if (panorama0 != null) {
                File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
                LOGGER.debug("make panorama directory:" + panoramaPath.mkdirs());
                File panorama_0 = new File(panoramaPath.getAbsolutePath() + "panorama_0.png");
                copy(panorama0, panorama_0);
            }
            if (panorama1 != null) {
                File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
                LOGGER.debug("make panorama directory:" + panoramaPath.mkdirs());
                File panorama_1 = new File(panoramaPath.getAbsolutePath() + "panorama_1.png");
                copy(panorama0, panorama_1);
            }
            if (panorama2 != null) {
                File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
                LOGGER.debug("make panorama directory:" + panoramaPath.mkdirs());
                File panorama_2 = new File(panoramaPath.getAbsolutePath() + "panorama_2.png");
                copy(panorama0, panorama_2);
            }
            if (panorama3 != null) {
                File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
                LOGGER.debug("make panorama directory:" + panoramaPath.mkdirs());
                File panorama_3 = new File(panoramaPath.getAbsolutePath() + "panorama_3.png");
                copy(panorama0, panorama_3);
            }
            if (panorama4 != null) {
                File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
                LOGGER.debug("make panorama directory:" + panoramaPath.mkdirs());
                File panorama_4 = new File(panoramaPath.getAbsolutePath() + "panorama_4.png");
                copy(panorama0, panorama_4);
            }
            if (panorama5 != null) {
                File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
                LOGGER.debug("make panorama directory:" + panoramaPath.mkdirs());
                File panorama_5 = new File(panoramaPath.getAbsolutePath() + "panorama_5.png");
                copy(panorama0, panorama_5);
            }
            if (background != null) {
                File panoramaPath = new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "textures" + App.SEPARATOR + "gui" + App.SEPARATOR + "title" + App.SEPARATOR + "background");
                LOGGER.debug("make panorama directory:" + panoramaPath.mkdirs());
                File panorama_overlay = new File(panoramaPath.getAbsolutePath() + App.SEPARATOR + "panorama_overlay.png");
                copy(background, panorama_overlay);
            }
            successFile = packPath;
            if (isZip) {
                File zipFile = new File(exportPath.toString() + App.SEPARATOR + packName + ".zip");
                ArrayList<File> fileArrayList = new ArrayList<>();
                fileArrayList.add(new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets"));
                fileArrayList.add(pack_mcmeta);
                if (!(iconFile == null)) {
                    fileArrayList.add(icon);
                }
                toZip(zipFile.getAbsolutePath(), fileArrayList);
                successFile = zipFile;
                deleteDir(packPath);
            }
        }
        success = true;
    }
}
