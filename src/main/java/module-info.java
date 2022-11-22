module org.haok.resourcespackmader {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.haok.resourcespackmader to javafx.fxml;
    exports org.haok.resourcespackmader;
}