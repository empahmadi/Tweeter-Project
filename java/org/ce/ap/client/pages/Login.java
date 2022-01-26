package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import org.ce.ap.client.gui.controller.LoginController;
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

    /**
     * initializing our login page.
     *
     * @param size .
     * @param mode .
     * @param main .
     * @return scene.
     */
    public Parent init(int size, String mode, PageHandlerImpl main) {
        this.main = main;
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            root = fxmlLoader.load();
            LoginController controller = fxmlLoader.getController();
            controller.init(size, mode);
            controller.setMain(this);
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.toString());
        }
        return root;
    }

    /**
     * this method check user input
     *
     * @param username .
     * @param password .
     * @param error    .
     */
    public void checkLogin(String username, String password, Label error, boolean rememberMe) {
        main.checkLogin(username, password, error, rememberMe);
    }

    /**
     * this method is for signup new users.
     */
    public void signup() {
        main.signup();
    }
}
