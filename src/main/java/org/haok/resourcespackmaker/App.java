package org.haok.resourcespackmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends Application {
    static Stage primaryStage;
    static final String SEPARATOR = System.getProperty("file.separator");
    public static PrintStream log;
    private static final Logger LOGGER = Logger.getLogger(App.class);

    @Override
    public void start(Stage stage) throws IOException {
        LOGGER.debug("Application start.");
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("资源包制作器");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}