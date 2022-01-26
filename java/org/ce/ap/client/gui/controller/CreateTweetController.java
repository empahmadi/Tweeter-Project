package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;

public class CreateTweetController {
    @FXML
    private Label info;
    @FXML
    private VBox list;
    @FXML
    private TextArea text;
    @FXML
    private Button cancel;
    @FXML
    private Button create;
    @FXML
    private HBox row2;
    @FXML
    private HBox row1;
    private Main main;
    @FXML
    private ScrollPane content;
    @FXML
    private Label error;


    public void init(int size, String mode, Main main) {
        content.getStyleClass().add(0,"content-s");
        list.getStyleClass().add(0,"list-s");
        row1.getStyleClass().add(0,"row1-s");
        row2.getStyleClass().add(0,"row2-s");
        text.getStyleClass().add(0,"text-s");
        cancel.getStyleClass().add(0,"btn-s");
        create.getStyleClass().add(0,"btn-s");
        error.getStyleClass().add(0,"error-s");
        content.getStyleClass().add(1,"light");
        list.getStyleClass().add(1,"light");
        row1.getStyleClass().add(1,"light");
        row2.getStyleClass().add(1,"light");
        text.getStyleClass().add(1,"light");
        cancel.getStyleClass().add(1,"light");
        create.getStyleClass().add(1,"light");
        error.getStyleClass().add(1,"light");
        info.getStyleClass().add(0,"light");
        this.main = main;
        toggleScreen(size);
        toggleTheme(mode);
        error.setVisible(false);
    }

    @FXML
    public void btn(ActionEvent evt) {
        Node node = (Node) evt.getSource();
        System.out.println(node.getId());
        if (node.getId().equals("create")) {
            if (text.getText().length() == 0) {
                error.setText("sorry you not entered any thing!!!");
                error.setVisible(true);
            } else if (text.getText().length() > 256) {
                error.setText("your tweet must include under 256 character!!!");
                error.setVisible(true);
            } else {
                main.sendTweet(text.getText(),error);
            }
        } else {
            main.home();
        }
    }

    public void toggleScreen(int size){
        if (size == 0){
            content.getStyleClass().set(0,"content-s");
            list.getStyleClass().set(0,"list-s");
            row1.getStyleClass().set(0,"row1-s");
            row2.getStyleClass().set(0,"row2-s");
            text.getStyleClass().set(0,"text-s");
            cancel.getStyleClass().set(0,"btn-s");
            create.getStyleClass().set(0,"btn-s");
            error.getStyleClass().set(0,"error-s");
        }else{
            content.getStyleClass().set(0,"content-l");
            list.getStyleClass().set(0,"list-l");
            row1.getStyleClass().set(0,"row1-l");
            row2.getStyleClass().set(0,"row2-l");
            text.getStyleClass().set(0,"text-l");
            cancel.getStyleClass().set(0,"btn-l");
            create.getStyleClass().set(0,"btn-l");
            error.getStyleClass().set(0,"error-l");
        }
    }

    public void toggleTheme(String mode){
        content.getStyleClass().set(1,mode);
        row1.getStyleClass().set(1,mode);
        row2.getStyleClass().set(1,mode);
        list.getStyleClass().set(1,mode);
        cancel.getStyleClass().set(1,mode);
        create.getStyleClass().set(1,mode);
        error.getStyleClass().set(1,mode);
        info.getStyleClass().set(0,mode);
    }
}
