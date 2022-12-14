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
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AppController {
    public Button showFile;
    public Label showText;
    public TextField icon;
    public ImageView iconView;
    public Button chooseIcon;
    public Label please;
    public Label english;
    public Label chinese;
    public TextField path0;
    public TextField path1;
    public TextField path2;
    public TextField path3;
    public TextField path4;
    public TextField path5;
    public TextField backgroundPath;
    public Button btnBackground;
    public Button btn5;
    public Button btn4;
    public Button btn3;
    public Button btn2;
    public Button btn1;
    public Button btn0;
    public TextField loadBackground0Path;
    public Button chooseLoadBackground0;
    public ImageView loadBackground0View;
    public TextField loadBackgroundPath1;
    public Button chooseLoadBackground1;
    public ImageView loadBackgroundView1;
    @FXML
    private Button chooseExportPath;

    @FXML
    private Button chooseTTF;

    @FXML
    private Button make;

    @FXML
    private CheckBox isZip;

    @FXML
    private TextField packIntroduction;

    @FXML
    private TextField pack_name;

    @FXML
    private TextField pack_version;

    @FXML
    private TextField path;

    @FXML
    private TextField ttf_path;

    @FXML
    void TTFDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            List<File> files = dragboard.getFiles();
            File file = files.get(0);
            if (file.getAbsolutePath().endsWith(".ttf") || file.getAbsolutePath().endsWith(".TTF")) {
                setFont(file);
            }
        }
    }

    @FXML
    void TTFOver(DragEvent event) {
        if (event.getGestureSource() != ttf_path) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void chooseTTF(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("????????????");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("????????????", "*.ttf"));
        File file = chooser.showOpenDialog(App.primaryStage);
        if (file == null) {
            return;
        }
        ttf_path.setText(file.getAbsolutePath());
        setFont(file);
    }

    private void setFont(File file) {
        PackConfig.ttfFile = file;
        ttf_path.setText(file.getAbsolutePath());
        try {
            english.setFont(Font.loadFont(new FileInputStream(file), 16));
            chinese.setFont(Font.loadFont(new FileInputStream(file), 16));
        } catch (FileNotFoundException e) {
            fileNotFound(e);
        }
    }

    public void make() {
        PackConfig.packName = pack_name.getText();
        PackConfig.packIntroduction = packIntroduction.getText();
        try {
            PackConfig.packVersion = Integer.parseInt(pack_version.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("??????");
            alert.setHeaderText("?????????????????????");
            alert.setContentText("?????????????????????????????????????????????????????????");
            alert.show();
            return;
        }
        if (path.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("??????");
            alert.setHeaderText("??????????????????");
            alert.setContentText("????????????????????????????????????");
            alert.show();
            return;
        }
        if (PackConfig.packName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("??????");
            alert.setHeaderText("?????????????????????");
            alert.setContentText("???????????????????????????????????????");
            alert.show();
        }
        PackConfig.exportPath = new File(path.getText());
        try {
            PackMaker.makePack(isZip.isSelected());
        } catch (Exception e) {
            App.logger.warn(e);
        }
        if (PackConfig.success) {
            showText.setVisible(true);
            showFile.setVisible(true);
        }
    }

    public void chooseExportPath() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("???????????????");
        File file = chooser.showDialog(App.primaryStage);
        if (!(file == null)) {
            PackConfig.exportPath = file;
            path.setText(file.getAbsolutePath());
        }
    }

    public void showFile() {
        if (showFile.isVisible()) {
            if (App.SEPARATOR.equals("\\")) {
                ProcessBuilder builder = new ProcessBuilder("explorer", "/select,", PackConfig.successFile.getAbsolutePath());
                try {
                    builder.start();
                } catch (IOException e) {
                    App.logger.warn(e);
                }
            } else if (Desktop.isDesktopSupported()) {

                try {
                    Desktop.getDesktop().open(PackConfig.exportPath);
                } catch (IOException e) {
                    App.logger.warn(e);
                }
            }
        }
    }

    public void iconDropped(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            List<File> files = dragboard.getFiles();
            File file = files.get(0);
            if (isSquare(file)) return;
            iconView.setImage(new Image(file.getAbsolutePath()));
            icon.setText(file.getAbsolutePath());
            please.setVisible(false);
            PackConfig.iconFile = file;
        }
    }

    private boolean isSquare(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image.getHeight() != image.getWidth()) {
                please.setVisible(true);
                return true;
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                fileNotFound(e);
            } else {
                App.logger.warn(e);
            }
        }
        return false;
    }

    public static void fileNotFound(IOException exception) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("??????????????????????????????");
        alert.setHeaderText("??????????????????????????????");
        alert.setContentText("????????????????????????");
        App.logger.warn(exception);
        alert.show();
    }

    public void iconOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != icon) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void chooseIcon() {
        File file = chooseImage();
        if (isSquare(file)) return;
        please.setVisible(false);
        icon.setText(file.getAbsolutePath());
        iconView.setImage(new Image(file.getAbsolutePath()));
        PackConfig.iconFile = file;
    }

    public void chooseBackground() {
        File file = chooseImage();
        backgroundPath.setText(file.getAbsolutePath());
        PackConfig.background = file;
    }

    public void choose5() {
        File file = chooseImage();
        path5.setText(file.getAbsolutePath());
        PackConfig.panorama5 = file;
    }

    public void choose4() {
        File file = chooseImage();
        path4.setText(file.getAbsolutePath());
        PackConfig.panorama4 = file;
    }

    public void choose3() {
        File file = chooseImage();
        path3.setText(file.getAbsolutePath());
        PackConfig.panorama3 = file;
    }

    public void choose2() {
        File file = chooseImage();
        path2.setText(file.getAbsolutePath());
        PackConfig.panorama2 = file;
    }

    public void choose1() {
        File file = chooseImage();
        path1.setText(file.getAbsolutePath());
        PackConfig.panorama1 = file;
    }

    public void choose0() {
        File file = chooseImage();
        path0.setText(file.getAbsolutePath());
        PackConfig.panorama0 = file;
    }

    private static File chooseImage() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("????????????", "*.png"));
        chooser.setTitle("????????????");
        File file = chooser.showOpenDialog(App.primaryStage);
        App.logger.info("choose image file:"+file);
        return file;
    }

    public void chooseLoadBackground0() {
        File file = chooseImage();
        PackConfig.loadBackground0 = file;
        loadBackground0View.setImage(new Image(file.getAbsolutePath()));
        loadBackground0Path.setText(file.getAbsolutePath());
    }

    public void chooseLoadBackground1() {
        //TODO
        //File file = chooseImage();
        System.out.println("TODO!!!");
    }
}
