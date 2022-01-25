package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import org.ce.ap.client.gui.controller.SignupController;
import org.ce.ap.client.impl.PageHandlerImpl;

import java.io.IOException;
/**
 * this class is for signup a new user.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class Signup {
    private PageHandlerImpl main;
    private Scene scene;
    public Scene init(int size, String mode, PageHandlerImpl main){
        this.main = main;
        Scene scene = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = fxmlLoader.load();
            SignupController controller = fxmlLoader.getController();
            controller.init(size,mode,this);
            scene = new Scene(root, 900, 650);
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.toString());
        }
        this.scene = scene;
        return scene;
    }

    /**
     * check information about signup.
     *
     * @param name        .
     * @param username    .
     * @param lastname    .
     * @param password    .
     * @param dateOfBirth .
     * @param sex         .
     * @param bio         .
     * @param error .
     */
    public void checkSignup(String name, String username, String lastname, String password, String dateOfBirth, String sex, String bio, TextArea error){
        main.checkSignup(name,username,lastname,password,dateOfBirth,sex,bio,error,scene);
    }
}
