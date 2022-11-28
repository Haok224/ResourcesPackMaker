package org.haok.resourcespackmaker;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AppController {
    public Button showFile;
    public Label showText;
    public TextField icon;
    public ImageView iconView;
    public Button chooseIcon;
    public Label please;
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

    public void make() {
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
            App.log.println(e);
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
        } catch (Exception e) {
            App.log.println(e);
        }
        if (PackConfig.success){
            showText.setVisible(true);
            showFile.setVisible(true);
        }
    }

    public void chooseExportPath() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择目录。");
        File file = chooser.showDialog(App.primaryStage);
        if (!(file == null)){
            PackConfig.exportPath = file;
            path.setText(file.getAbsolutePath());
        }
    }

    public void showFile() {
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

    public void iconDropped(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()){
            List<File> files = dragboard.getFiles();
            File file = files.get(0);
            try {
                BufferedImage image = ImageIO.read(file);
                if (image.getHeight() != image.getWidth()){
                    please.setVisible(true);
                    return;
                }
            } catch (IOException e) {
                App.log.println(e);
            }
            iconView.setImage(new Image(file.getAbsolutePath()));
            icon.setText(file.getAbsolutePath());
            please.setVisible(false);
        }
    }

    public void iconOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != icon){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void chooseIcon() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(App.primaryStage);
        if (!(file == null)){
            try {
                BufferedImage image = ImageIO.read(file);
                if (image.getHeight() != image.getWidth()){
                    please.setVisible(true);
                    return;
                }
            } catch (IOException e) {
                App.log.println(e);
            }
            please.setVisible(false);
            icon.setText(file.getAbsolutePath());
            iconView.setImage(new Image(file.getAbsolutePath()));
        }
    }
}
