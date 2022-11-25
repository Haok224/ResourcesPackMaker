package org.haok.resourcespackmaker;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AppController {
    @FXML
    private Button chooseExportPath;

    @FXML
    private Button choose_ttf;

    @FXML
    private Button make;

    @FXML
    private CheckBox isZip;

    @FXML
    private TextField pack_introduction;

    @FXML
    private TextField pack_name;

    @FXML
    private TextField pack_version;

    @FXML
    private TextField path;

    @FXML
    private TextField ttf_path;

    @FXML
    void ttf_dropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if(dragboard.hasFiles()){
            List<File> files = dragboard.getFiles();
            File file = files.get(0);
            if(file.getAbsolutePath().endsWith(".ttf") || file.getAbsolutePath().endsWith(".TTF")){
                PackConfig.ttfFile = file;
                ttf_path.setText(file.getAbsolutePath());
            }
        }
    }
    @FXML
    void ttf_over(DragEvent event) {
        if(event.getGestureSource() != ttf_path){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML
    void choose_ttf(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("选择文件");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("字体文件","*.ttf"));
        File file = chooser.showOpenDialog(App.primaryStage);
        if(file == null){
            return;
        }
        ttf_path.setText(file.getAbsolutePath());
        PackConfig.ttfFile = file;
    }
    @FXML
    void howMake(ActionEvent actionEvent) {
        Stage stage = new Stage();
        TextArea area = new TextArea();
        area.setText(
                        "参考： https://www.mcbbs.net/thread-1079039-1-1.html \n" +
                        "如何制作主菜单全景图\n" +
                        "\n" +
                        "常规方式：截图\n" +
                        "\n" +
                        "最简单的方法就是在游戏里看向六个方向，分别截一张图：\n" +
                        "\n" +
                        "1. 首先要在启动器中将游戏窗口大小改为 512 x 512。\n" +
                        "2. 拍摄前要阻止各种可能的变化，防止不同截图衔接不上，最好将世界变成彻底静态的。\n" +
                        "\n" +
                        "/gamerule doDaylightCycle false 停止昼夜交替\n" +
                        "关闭飘来飘去的云\n" +
                        "如果开的光影中有各种晃动元素，也要关掉\n" +
                        "\n" +
                        "3. 将角视场调整为82\n" +
                        "4. 按下 F1（有些笔记本需要 Fn + F1）隐藏一切 UI\n" +
                        "5. 选择合适的位置，选择合适的时间，调整光影的各种设置以达到最符合氛围的效果\n" +
                        "\n" +
                        "\"/tp @p ~ ~ ~ -90 0\"，按下 F2（有些笔记本需要 Fn + F2）截图得到 \"panorama_0.png\"\n" +
                        "\"/tp @p ~ ~ ~ 0 0\"，截图得到 \"panorama_1.png\"\n" +
                        "\"/tp @p ~ ~ ~ 180 0\"，截图得到 \"panorama_2.png\"\n" +
                        "\"/tp @p ~ ~ ~ 90 0\"，截图得到 \"panorama_3.png\"\n" +
                        "\"/tp @p ~ ~ ~ -90 -90\"，截图得到 \"panorama_4.png\"\n" +
                        "\"/tp @p ~ ~ ~ -90 90\"，截图得到 \"panorama_5.png\"`\n"
                );
        stage.setScene(new Scene(area));
        area.setEditable(false);
        stage.show();
    }

    public void make(ActionEvent actionEvent) {
        PackConfig.packName = pack_name.getText();
        PackConfig.packIntroduction = pack_introduction.getText();
        try{
            PackConfig.packVersion = Integer.parseInt(pack_version.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setHeaderText("版本号输入有误");
            alert.setContentText("请不要在输入框内输入除数字以外的字符。");
            alert.show();
            return;
        }
        if (path.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setHeaderText("导出路径为空");
            alert.setContentText("请检查是否选择制作路径。");
            alert.show();
            return;
        }
        PackConfig.exportPath = new File(path.getText());
        try {
            PackConfig.makePack(isZip.isSelected());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void chooseExportPath(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择目录。");
        File file = chooser.showDialog(App.primaryStage);
        PackConfig.exportPath = file;
        path.setText(file.getAbsolutePath());
    }
}
