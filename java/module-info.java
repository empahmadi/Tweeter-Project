module org.ce.ap.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.logging;


    opens org.ce.ap.client to javafx.fxml;
    exports org.ce.ap.client;
}