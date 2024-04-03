module com.example.exemplu1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.xerial.sqlitejdbc;

    opens com.example.exemplu1 to javafx.fxml;
    exports com.example.exemplu1;
}