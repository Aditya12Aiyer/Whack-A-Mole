module com.example.whackamole {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.whackamole to javafx.fxml;
    exports com.example.whackamole;
}