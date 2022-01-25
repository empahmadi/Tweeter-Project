package org.ce.ap.client.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuBar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),726.5,600);
        stage.setScene(scene);
        stage.show();
    }
}
