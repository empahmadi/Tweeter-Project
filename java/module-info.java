module org.ce.ap.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.logging;
    requires com.jfoenix;

    exports org.ce.ap.client.pages;
    opens org.ce.ap.client.pages to javafx.fxml;
    exports org.ce.ap.client.gui.impl;
    opens org.ce.ap.client.gui.impl to javafx.fxml;
}