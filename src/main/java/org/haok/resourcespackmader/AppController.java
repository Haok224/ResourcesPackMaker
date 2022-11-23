package org.haok.resourcespackmader;


import com.leewyatt.rxcontrols.controls.RXTranslationButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class AppController {

    @FXML
    private RXTranslationButton choose_ttf;
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
}
