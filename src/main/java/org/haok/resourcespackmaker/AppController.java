package org.haok.resourcespackmaker;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AppController {
    public Button showFile;
    public Label showText;
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
    void choose_ttf(ActionEvent ignoredActionEvent) {
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
    void howMake(ActionEvent ignoredActionEvent) {
        if (Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(new URI("https://sqwatermark.com/resguide/vanilla/texture/gui/panorama.html#%E5%A6%82%E4%BD%95%E5%88%B6%E4%BD%9C%E4%B8%BB%E8%8F%9C%E5%8D%95%E5%85%A8%E6%99%AF%E5%9B%BE"));
            } catch (IOException e) {
                //TODO log system
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                //TODO log system
                throw new RuntimeException(e);
            }
        }
    }

    public void make(ActionEvent ignoredActionEvent) {
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
        }catch (Exception r){
            r.printStackTrace();
        }
        if (PackConfig.success){
            showText.setVisible(true);
            showFile.setVisible(true);
        }
    }

    public void chooseExportPath(ActionEvent ignoredActionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择目录。");
        File file = chooser.showDialog(App.primaryStage);
        PackConfig.exportPath = file;
        path.setText(file.getAbsolutePath());
    }

    public void showFile(ActionEvent ignoredActionEvent) {
        if (showFile.isVisible()){
            if (App.SEPARATOR.equals("\\")){
                ProcessBuilder builder = new ProcessBuilder("explorer","/select,",PackConfig.successFile.getAbsolutePath());
                try {
                    builder.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (Desktop.isDesktopSupported()) {

                try {
                    Desktop.getDesktop().open(PackConfig.exportPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
