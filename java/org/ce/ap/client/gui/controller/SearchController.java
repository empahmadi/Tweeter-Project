package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;

public class SearchController {
    @FXML
    private ScrollPane content;
    @FXML
    private VBox list;
    @FXML
    private HBox row;
    @FXML
    private TextField field;
    @FXML
    private Button search;

    public void init(int size, String mode, Main main){
        toggleScreen(size);
        toggleTheme(mode);

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.profile(field.getText());
            }
        });
    }


    public void toggleScreen(int size){
        if (size == 1) {
            content.getStyleClass().add("content-l");
            list.getStyleClass().add("list-l");
            row.getStyleClass().add("row-l");
            field.getStyleClass().add("field-l");
            search.getStyleClass().add("search-l");
        } else {
            content.getStyleClass().add("content-s");
            list.getStyleClass().add("list-s");
            row.getStyleClass().add("row-s");
            field.getStyleClass().add("field-s");
            search.getStyleClass().add("search-s");
        }
    }

    public void toggleTheme(String mode){
        content.getStyleClass().add(mode);
        list.getStyleClass().add(mode);
        row.getStyleClass().add(mode);
        field.getStyleClass().add(mode);
        search.getStyleClass().add(mode);
    }
}
