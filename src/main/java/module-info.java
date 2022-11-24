module org.haok.resourcespackmaker {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.haok.resourcespackmaker to javafx.fxml;
    exports org.haok.resourcespackmaker;
}