package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Login;

/**
 * this class will control everything that occurs in login page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class LoginController {
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

    public void init(int size, String mode) {
        toggleScreen(size);
        toggleTheme(mode);
        error.setVisible(false);
    }

    @FXML
    public void login() {
        main.checkLogin(username.getText(), password.getText(), error);
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
     *
     * @param main .
     */
    public void setMain(Login main) {
        this.main = main;
    }


    public void toggleScreen(int size){
        if (size == 0) {
            info.setLayoutX(125);
            box.setLayoutX(125);
            error.setLayoutX(14);
        } else {
            info.setLayoutX(375);
            box.setLayoutX(375);
            error.setLayoutX(150);
        }
    }

    public void toggleTheme(String mode){
        parent.getStyleClass().add(mode);
        username.getStyleClass().add(mode);
        password.getStyleClass().add(mode);
        signup.getStyleClass().add(mode);
        forgotPassword.getStyleClass().add(mode);
        rememberMe.getStyleClass().add(mode);
        welcome.getStyleClass().add(mode);
        guid.getStyleClass().add(mode);
        space.getStyleClass().add(mode);
        login.getStyleClass().add(mode);
    }
}
