package org.ce.ap.client.gui.impl;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;
import org.json.JSONArray;
import org.json.JSONObject;

public class TimeLineControllerImpl {
    @FXML
    private ScrollPane parent;
    @FXML
    private VBox list;

    public void init(int size, int mode, JSONArray tweets, Main main){
        for (Object i: tweets){
            list.getChildren().add(main.getTweet((JSONObject) i));
        }
    }
}
