package org.ce.ap.client.gui.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.ce.ap.client.pages.Signup;

/**
 * this class is a controller for signup page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class SignupController {
    @FXML
    private JFXTextArea bio;
    private Signup main;
    @FXML
    private TextArea error;
    @FXML
    private Pane form;
    @FXML
    private TextField name;
    @FXML
    private TextField lastname;
    @FXML
    private TextField username;
    @FXML
    private DatePicker date;
    @FXML
    private TextField password;
    @FXML
    private TextField confirm;
    @FXML
    private ChoiceBox sex;
    @FXML
    private Button submit;

    public void init(int size, String mode, Signup main) {
        error.setVisible(false);
        this.main = main;
        if (size == 0) {
            form.getStyleClass().add("container-s");
        } else {
            form.getStyleClass().add("container-l");
        }
        form.getStyleClass().add(mode);
        error.getStyleClass().add(mode);
    }


    public void submit(ActionEvent event) {
        if (!isEmpty()) {
            if (confirm.getText().equals(password.getText())) {
                main.checkSignup(name.getText(), username.getText(), lastname.getText(), password.getText(), date.getEditor().getText(), (String) sex.getValue(), bio.getText(), error);
            } else {
                error.setText("password is not the same of confirm");
                error.setVisible(true);
            }
        } else {
            error.setVisible(true);
        }
    }

    private boolean isEmpty() {
        StringBuilder text = new StringBuilder();
        if (name.getText().equals("")) {
            text.append("name must be more than one character!!!\n");
        }
        if (lastname.getText().equals("")) {
            text.append("lastname must be more than one character!!!\n");
        }
        if (username.getText().equals("")) {
            text.append("username must be more than one character!!!\n");
        }
        if (password.getText().equals("")) {
            text.append("password must be more than one character!!!\n");
        }
        if (date.getEditor().getText().equals("")) {
            text.append("date of birth must set!!!\n");
        }
        if (bio.getText().equals("")) {
            text.append("biography must be more than one character!!!\n");
        } else if (bio.getText().length() > 256) {
            text.append("biography must be less than 256 characters!!!\n");
        }
        if (sex.getValue().equals("Gender")) {
            text.append("gender must set!!!\n");
        }
        if (text.length() != 0) {
            error.setText(text.toString());
            return true;
        } else {
            return false;
        }
    }
}
