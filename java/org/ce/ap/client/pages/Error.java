package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ce.ap.client.gui.controller.ErrorController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Handling error page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.2
 */
public class Error {
    /**
     * prepare our error page.
     *
     * @return scene.
     */
    private Parent init(int size, int code, String mode) {
        String path = null, caption;
        Parent root = null;
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            path = config.get("client.app").toString();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        if (code == 1) {
            caption = "Error Occurred In Connection!!! :(";
        } else {
            caption = "Error Occurred In Application!!! :(";
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
            root = fxmlLoader.load();
            ErrorController controller = fxmlLoader.getController();
            controller.init(size, mode, caption, code, path);
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.toString());
        }
        return root;
    }

    /**
     * show our error page.
     *
     * @param code  error code.
     */
    public Parent show(int code, int size, String mode) {
        return init(size, code, mode);
    }

}
