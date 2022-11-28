package org.haok.resourcespackmaker;

import javafx.scene.control.Alert;

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

    public static void makePack(boolean isZip) throws Exception {
        if (!check(packName)) {
            return;
        }
        if (success) {
            success = false;
        }
        File packPath = new File(exportPath.getAbsolutePath() + App.SEPARATOR + packName);
        File pack_mcmeta = new File(packPath.getAbsolutePath() + "" + App.SEPARATOR + "pack.mcmeta");
        App.log.println("make pack dir:" + packPath.mkdirs());
        App.log.println("make pack.mcmeta:" + pack_mcmeta.createNewFile());
        BufferedWriter writer = new BufferedWriter(new FileWriter(pack_mcmeta));
        writer.write("{\"pack\":{\"pack_format\":" + packVersion + ",\"description\":\"" + packIntroduction + "\"}}");
        writer.close();
        if (!(ttfFile == null)) {
            File fontPath = new File(packPath.getAbsolutePath() + "" + App.SEPARATOR + "assets" + App.SEPARATOR + "minecraft" + App.SEPARATOR + "font");
            File fontFile = new File(fontPath.getAbsolutePath() + "" + App.SEPARATOR + "font.ttf");
            App.log.println(fontPath.mkdirs());
            App.log.println(fontFile.createNewFile());
            copy(ttfFile, fontFile);
            File json = new File(fontPath + "" + App.SEPARATOR + "default.json");
            App.log.println("make font json file:" + json.createNewFile());
            BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(json));
            jsonWriter.write("{\"providers\":[{\"type\":\"ttf\",\"file\":\"minecraft:font.ttf\",\"shift\":[0,1],\"size\":11.0,\"oversample\":4.0}]}");
            jsonWriter.close();
            successFile = packPath;
            if (!(iconFile == null)) {
                File icon = new File(packPath.getAbsolutePath() + App.SEPARATOR + "pack.png");
                App.log.println("make icon file:" + icon.createNewFile());
                copy(iconFile, icon);
            }
            if (isZip) {
                File zipFile = new File(exportPath.toString() + App.SEPARATOR + packName + ".zip");
                ArrayList<File> fileArrayList = new ArrayList<>();
                fileArrayList.add(new File(packPath.getAbsolutePath() + App.SEPARATOR + "assets"));
                fileArrayList.add(pack_mcmeta);
                if (!(iconFile == null)){
                    fileArrayList.add(iconFile);
                }
                ZipTools.toZip(zipFile.getAbsolutePath(), fileArrayList);
                successFile = zipFile;
                deleteDir(packPath);
            }
        }
        success = true;
    }

    private static boolean check(String name) {
        if (name.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setHeaderText("资源包名称为空");
            alert.setContentText("请检查“基础设置”选项卡。");
            alert.show();
            return false;
        }
        return true;
    }

    private static void copy(File source, File dest)
            throws IOException {
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
        if(dir.delete()) {
            App.log.println("dir was deleted");
            return true;
        } else {
            System.out.println("dir was not deleted(delete fail)");
            return false;
        }
    }
}
