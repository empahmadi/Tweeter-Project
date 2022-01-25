package org.ce.ap.client.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;
import org.json.JSONArray;
import org.json.JSONObject;

public class TimeLineController {
    @FXML
    private ScrollPane parent;
    @FXML
    private VBox list;

    public void init(int size, String mode, JSONArray tweets, Main main){
        for (Object i: tweets){
            list.getChildren().add(main.getTweet((JSONObject) i));
        }
        main.incrementIndex();
        Label last = new Label();
        last.setMaxHeight(100);
        last.setMinHeight(100);
        last.setPrefHeight(100);
        last.setMaxWidth(100);
        last.setMinWidth(100);
        last.setPrefWidth(100);
        list.getChildren().add(last);

    }


    public void toggleScreen(int size){

    }

    public void toggleTheme(String mode){

    }
}
