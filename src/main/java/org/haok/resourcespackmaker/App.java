package org.haok.resourcespackmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

public class App extends Application {
    static Stage primaryStage;
    static final String SEPARATOR = System.getProperty("file.separator");
    public static PrintStream log;
    public static final String START_TIME = new Date().toString().replace(" ","_").replace(":","_");
    public static final String WORK_DIR = System.getProperty("user.dir");
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("资源包制作器");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        File logFile =  new File(WORK_DIR+SEPARATOR+"log"+SEPARATOR+START_TIME+".log");
        File logDir = new File(WORK_DIR+SEPARATOR+"log");
        System.out.println(logFile);
        System.out.println(logDir);
        try {
            System.out.println(logDir.mkdirs());
            System.out.println(logFile.createNewFile());
            FileOutputStream stream = new FileOutputStream(logFile);
            log = new PrintStream(stream);
            log.println("Application start at "+START_TIME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        launch();
    }
}