package org.haok.resourcespackmaker;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class PackConfig {
    public static File ttfFile;
    public static String packName;
    public static String packIntroduction;
    public static File exportPath;
    public static int packVersion;
    public static boolean success = false;
    public static File successFile;
    public static File iconFile;
    public static File panorama0;
    public static File panorama1;
    public static File panorama2;
    public static File panorama3;
    public static File panorama4;
    public static File panorama5;
    public static File background;
    public static final Logger LOGGER = Logger.getLogger(PackConfig.class);

    public static void makePack(boolean isZip) throws Exception {
        if (success) {
            success = false;
        }
        File packPath = new File(exportPath.getAbsolutePath() + App.SEPARATOR + packName);
        File pack_mcmeta = new File(packPath.getAbsolutePath() + "" + App.SEPARATOR + "pack.mcmeta");
        LOGGER.debug("make pack dir:" + packPath.mkdirs());
        LOGGER.debug("make pack.mcmeta:" + pack_mcmeta.createNewFile());
        BufferedWriter writer = new BufferedWriter(new FileWriter(pack_mcmeta));
        writer.write("{\"pack\":{\"pack_format\":" + packVersion + ",\"description\":\"" + packIntroduction + "\"}}");
        writer.close();
        if (!(ttfFile == null)) {
            File fontPath = new File(packPath.getAbsolutePath() + "" + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "font");
            File fontFile = new File(fontPath.getAbsolutePath() + "" + App.SEPARATOR + "font.ttf");
            LOGGER.debug(fontPath.mkdirs());
            LOGGER.debug(fontFile.createNewFile());
            copy(ttfFile, fontFile);
            File json = new File(fontPath + "" + App.SEPARATOR + "default.json");
            LOGGER.debug("make font json file:" + json.createNewFile());
            BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(json));
            jsonWriter.write("{\"providers\":[{\"type\":\"ttf\",\"file\":\"minecraft:font.ttf\",\"shift\":[0,1],\"size\":11.0,\"oversample\":4.0}]}");
            jsonWriter.close();
            successFile = packPath;
            if (!(iconFile == null)) {
                File icon = new File(packPath.getAbsolutePath() + App.SEPARATOR + "pack.png");
                LOGGER.debug("make icon file:" + icon.createNewFile());
                copy(iconFile, icon);
            }
            if (isZip) {
                File zipFile = new File(exportPath.toString() + App.SEPARATOR + packName + ".zip");
                ArrayList<File> fileArrayList = new ArrayList<>();
                fileArrayList.add(new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets"));
                fileArrayList.add(pack_mcmeta);
                if (!(iconFile == null)) {
                    fileArrayList.add(iconFile);
                }
                ZipTools.toZip(zipFile.getAbsolutePath(), fileArrayList);
                successFile = zipFile;
                deleteDir(packPath);
            }
        }
        success = true;
    }

    private static void copy(File source, File dest)
            throws IOException {
        LOGGER.debug("copy file from " + source.getAbsolutePath() + "to " + dest.getAbsolutePath());
        try (InputStream input = new FileInputStream(source); OutputStream output = new FileOutputStream(dest)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : Objects.requireNonNull(children)) {
                boolean success = deleteDir
                        (new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir.delete()) {
            LOGGER.debug("dir was deleted");
            return true;
        } else {
            System.out.println("dir was not deleted(delete fail)");
            return false;
        }
    }
}
