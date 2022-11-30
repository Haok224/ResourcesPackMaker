package org.haok.resourcespackmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.haok.resourcespackmaker.log.LogFactory;
import org.haok.resourcespackmaker.log.LogPrintStream;

public class App extends Application {
    static Stage primaryStage;
    static final String SEPARATOR = System.getProperty("file.separator");
    public static LogPrintStream logger = LogFactory.getLogger(App.class);
    @Override
    public void start(Stage stage) throws Exception {
        logger.println("------------------Application start------------------");
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("资源包制作器");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            logger.println("------------------Application end------------------");
            stage.close();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}