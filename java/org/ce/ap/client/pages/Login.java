package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import org.ce.ap.client.gui.impl.LoginControllerImpl;
import org.ce.ap.client.impl.PageHandlerImpl;

import java.io.IOException;

/**
 * this class is for login page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class Login {
    private PageHandlerImpl main;
    private Scene scene;
    /**
     * initializing our login page.
     * @param size .
     * @param mode .
     * @param main .
     * @return scene.
     */
    public Scene init(int size, String mode,PageHandlerImpl main){
        this.main = main;
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            LoginControllerImpl controller = fxmlLoader.getController();
            controller.init(size, mode);
            controller.setMain(this);
            scene = new Scene(root, 900, 650);
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.toString());
        }
        this.scene = scene;
        return scene;
    }

    /**
     * this method check user input
     * @param username .
     * @param password .
     * @param error .
     */
    public void checkLogin(String username, String password, Label error){
        main.checkLogin(username,password,error,scene);
    }

    /**
     * this method is for signup new users.
     */
    public void signup(){
        main.signup();
    }
}
