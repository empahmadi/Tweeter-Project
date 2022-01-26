package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.ce.ap.client.pages.Main;

public class SearchController {
    @FXML
    private ScrollPane content;
    @FXML
    private HBox row;
    @FXML
    private TextField field;
    @FXML
    private Button search;

    /**
     * initialize our page elements.
     * @param size .
     * @param mode .
     * @param main .
     */
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

    /**
     * change the size of page.
     * @param size .
     */
    public void toggleScreen(int size){
        if (size == 1) {
            content.getStyleClass().add("content-l");
            row.getStyleClass().add("row-l");
        } else {
            content.getStyleClass().add("content-s");
            row.getStyleClass().add("row-s");
        }
    }

    /**
     * change the theme of page.
     * @param mode .
     */
    public void toggleTheme(String mode){
        content.getStyleClass().add(mode);
        row.getStyleClass().add(mode);
        field.getStyleClass().add(mode);
        search.getStyleClass().add(mode);
    }
}
