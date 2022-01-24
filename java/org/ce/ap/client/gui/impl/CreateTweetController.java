package org.ce.ap.client.gui.impl;

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
        this.main = main;
        content.getStyleClass().add(mode);
        row1.getStyleClass().add(mode);
        row2.getStyleClass().add(mode);
        list.getStyleClass().add(mode);
        if (mode.equals("light")){
            cancel.getStyleClass().add("btn-l");
            create.getStyleClass().add("btn-l");
        }else{
            cancel.getStyleClass().add("btn-d");
            create.getStyleClass().add("btn-d");
        }
        text.getStyleClass().add(mode);
        if (size == 0){
            content.getStyleClass().add("content-s");
            list.getStyleClass().add("list-s");
            row1.getStyleClass().add("row1-s");
            row2.getStyleClass().add("row2-s");
            text.getStyleClass().add("text-s");
            error.getStyleClass().add("error-s");
        }else{
            content.getStyleClass().add("content-l");
            list.getStyleClass().add("list-l");
            row1.getStyleClass().add("row1-l");
            row2.getStyleClass().add("row2-l");
            text.getStyleClass().add("text-l");
            error.getStyleClass().add("error-l");
        }
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
}
