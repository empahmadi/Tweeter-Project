package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ce.ap.client.gui.impl.ErrorControllerImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Error {
    /**
     * prepare our error page.
     *
     * @return scene.
     */
    private Scene init(int size, int code,int mode) {
        String path = null,caption;
        Scene scene = null;
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            path = config.get("client.app.images").toString();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        if (code == 1){
            caption = "Error Occurred In Connection!!! :(";
        }else{
            caption = "Error Occurred In Application!!! :(";
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("loading.fxml"));
            ErrorControllerImpl error = fxmlLoader.getController();
            error.init(size,mode,caption,code,path);
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        return scene;
    }

    /**
     * show our error page.
     *
     * @param stage our stage.
     * @param code  error code.
     */
    public Scene show(Stage stage, int code, int size, int mode) {
        return init(size,code,mode);
    }

}
