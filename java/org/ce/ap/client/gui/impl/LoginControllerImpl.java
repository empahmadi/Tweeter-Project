package org.ce.ap.client.gui.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.gui.controllers.LoginController;
import org.ce.ap.client.pages.Login;

/**
 * this class will control everything that occurs in login page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class LoginControllerImpl implements LoginController {
    private Login main;
    @FXML
    private Label error;
    @FXML
    private VBox info;
    @FXML
    private ImageView logo;
    @FXML
    private Label welcome;
    @FXML
    private Label guid;
    @FXML
    private Label space;
    @FXML
    private VBox box;
    @FXML
    private Label rememberMe;
    @FXML
    private CheckBox check;
    @FXML
    private Button forgotPassword;
    @FXML
    private Button signup;
    @FXML
    private Pane parent;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;

    public void init(int size, int mode) {
        if (size == 0) {
            parent.getStyleClass().add("container-s");
            info.getStyleClass().add("info-s");
            box.getStyleClass().add("fields-s");
            username.getStyleClass().add("field-s");
            password.getStyleClass().add("field-s");
            signup.getStyleClass().add("btn-s");
            forgotPassword.getStyleClass().add("btn-s");
            rememberMe.getStyleClass().add("label-s");
            welcome.getStyleClass().add("first-s");
            guid.getStyleClass().add("first-s");
            space.getStyleClass().add("break-s");
            login.getStyleClass().add("btn-s");
            error.getStyleClass().add("error-s");
        } else {
            parent.getStyleClass().add("container-l");
            info.getStyleClass().add("info-l");
            box.getStyleClass().add("fields-l");
            username.getStyleClass().add("field-l");
            password.getStyleClass().add("field-l");
            signup.getStyleClass().add("btn-l");
            forgotPassword.getStyleClass().add("btn-l");
            rememberMe.getStyleClass().add("label-l");
            welcome.getStyleClass().add("first-l");
            guid.getStyleClass().add("first-l");
            space.getStyleClass().add("break-l");
            login.getStyleClass().add("btn-l");
            error.getStyleClass().add("error-l");
        }
        if (mode == 1){
            parent.getStyleClass().add("light");
            info.getStyleClass().add("light");
            box.getStyleClass().add("light");
            username.getStyleClass().add("light");
            password.getStyleClass().add("light");
            signup.getStyleClass().add("light");
            forgotPassword.getStyleClass().add("light");
            rememberMe.getStyleClass().add("light");
            welcome.getStyleClass().add("light");
            guid.getStyleClass().add("light");
            space.getStyleClass().add("light");
            login.getStyleClass().add("light");
            error.getStyleClass().add("light");
        }else{
            parent.getStyleClass().add("dark");
            info.getStyleClass().add("dark");
            box.getStyleClass().add("dark");
            username.getStyleClass().add("dark");
            password.getStyleClass().add("dark");
            signup.getStyleClass().add("dark");
            forgotPassword.getStyleClass().add("dark");
            rememberMe.getStyleClass().add("dark");
            welcome.getStyleClass().add("dark");
            guid.getStyleClass().add("dark");
            space.getStyleClass().add("dark");
            login.getStyleClass().add("dark");
            error.getStyleClass().add("dark");
        }
        error.setVisible(false);
    }

    @FXML
    public void login() {
        main.checkLogin(username.getText(),password.getText(),error);
    }

    @FXML
    public void signup(ActionEvent actionEvent) {
        main.signup();
    }

    @FXML
    public void forgotPassword() {

    }

    /**
     * set command parser service.
     * @param main .
     */
    public void setMain(Login main) {
        this.main = main;
    }
}
