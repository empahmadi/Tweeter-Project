package org.ce.ap.client.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this class is a controller for timeline page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class TimeLineController {
    @FXML
    private ScrollPane parent;
    @FXML
    private VBox list;

    /**
     * init our page.
     *
     * @param size   .
     * @param mode   .
     * @param tweets .
     * @param main   .
     */
    public void init(int size, String mode, JSONArray tweets, Main main) {
        for (Object i : tweets) {
            list.getChildren().add(main.getTweet((JSONObject) i));
        }
        Label last = new Label();
        last.setMaxHeight(100);
        last.setMinHeight(100);
        last.setPrefHeight(100);
        last.setMaxWidth(100);
        last.setMinWidth(100);
        last.setPrefWidth(100);
        list.getChildren().add(last);
        toggleScreen(size);
        toggleTheme(mode);
    }

    /**
     * change the size of screen.
     *
     * @param size .
     */
    public void toggleScreen(int size) {
        parent.getStyleClass().remove(1);
        list.getStyleClass().remove(0);
        if (size == 0) {
            parent.getStyleClass().add(1,"content-s");
            list.getStyleClass().add(0,"list-s");
        } else {
            parent.getStyleClass().add(1,"content-l");
            list.getStyleClass().add(0,"list-l");
        }
    }

    /**
     * change the mode of screen.
     *
     * @param mode .
     */
    public void toggleTheme(String mode) {
        parent.getStyleClass().remove(2);
        parent.getStyleClass().add(2,mode);
        list.getStyleClass().remove(1);
        list.getStyleClass().add(1,mode);
    }
}
