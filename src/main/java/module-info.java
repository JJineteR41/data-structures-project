module org.example.interf {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens org.example.interf to javafx.fxml;
    exports org.example.interf;
}