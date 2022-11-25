package org.haok.resourcespackmaker;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class PackConfig {
    public static File ttfFile;
    public static String packName;
    public static String packIntroduction;
    public static File exportPath;
    public static int packVersion;
    public static void makePack(boolean isZip) throws IOException {
        if(!check(packName)){
            return;
        }

        File packPath = new File(exportPath.getAbsolutePath()+"\\"+packName);
        File pack_mcmeta = new File(packPath.getAbsolutePath()+"\\pack.mcmeta");
        System.out.println(packPath.mkdirs());
        System.out.println(pack_mcmeta.createNewFile());
        BufferedWriter writer = new BufferedWriter(new FileWriter(pack_mcmeta));
        writer.write("{\"pack\":{\"pack_format\":"+packVersion+",\"description\":\""+packIntroduction+"\"}}");
        writer.close();
        if(!(ttfFile == null)){
            File fontFile = new File(packPath.getAbsolutePath()+"\\assets\\minecraft\\font\\font.ttf");
            File fontPath = new File(packPath.getAbsolutePath()+"\\assets\\minecraft\\font");
            fontPath.mkdirs();
            System.out.println(fontFile.createNewFile());
            copyFileUsingFileStreams(ttfFile,fontFile);
        }
    }

    private static boolean check(String name) {
        if (name.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setHeaderText("资源包名称为空");
            alert.setContentText("请检查“基础设置”选项卡。");
            alert.show();
            return false;
        }
        return true;
    }

    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }
}
