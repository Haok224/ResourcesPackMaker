package org.haok.resourcespackmaker;

import javafx.scene.control.Alert;

import java.io.File;

public class PackConfig {
    public static File ttfFile;
    public static String packName;
    public static String packIntroduction;
    public static File exportPath;
    public static void makePack(){
        if(!check(packName)){
            return;
        }
        System.out.println();
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
}
