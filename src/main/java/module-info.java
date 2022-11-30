module org.haok.resourcespackmaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.logging.log4j;

    opens org.haok.resourcespackmaker to javafx.fxml;
    exports org.haok.resourcespackmaker;
}