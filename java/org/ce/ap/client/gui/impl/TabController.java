package org.ce.ap.client.gui.impl;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * this class is a controller for tab page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class TabController {
    @FXML
    private TabPane content;
    @FXML
    private Tab tweets;
    @FXML
    private Tab likes;
    @FXML
    private Tab retweets;
    @FXML
    private Tab info;
    @FXML
    private VBox list;
    @FXML
    private VBox list1;
    @FXML
    private VBox list2;

    /**
     * this method initialize our tabs.
     *
     * @param size   .
     * @param mode   .
     * @param tweets .
     * @param likes  .
     * @param user   .
     */
    public void init(int size, String mode, ArrayList<VBox> tweets, ArrayList<VBox> likes, ArrayList<VBox> retweets, JSONObject user) {
        content.getStyleClass().add(mode);
        this.tweets.getStyleClass().add(mode);
        this.likes.getStyleClass().add(mode);
        this.retweets.getStyleClass().add(mode);
        info.getStyleClass().add(mode);
        list.getStyleClass().add(mode);
        list1.getStyleClass().add(mode);
        list2.getStyleClass().add(mode);
        if (size == 1) {
            content.getStyleClass().add("content-l");
            this.tweets.getStyleClass().add("tab-l");
            this.retweets.getStyleClass().add("tab-l");
            this.likes.getStyleClass().add("tab-l");
            info.getStyleClass().add("tab-l");
            list.getStyleClass().add("list-l");
            list1.getStyleClass().add("list-l");
            list2.getStyleClass().add("list-l");
        } else {
            content.getStyleClass().add("content-s");
            this.tweets.getStyleClass().add("tab-s");
            this.retweets.getStyleClass().add("tab-s");
            this.likes.getStyleClass().add("tab-s");
            info.getStyleClass().add("tab-s");
            list.getStyleClass().add("list-s");
            list1.getStyleClass().add("list-s");
            list2.getStyleClass().add("list-s");
        }
        for (VBox i : tweets) {
            this.list.getChildren().add(i);
        }
        for (VBox i : likes) {
            this.list1.getChildren().add(i);
        }
        for (VBox i : retweets) {
            this.list2.getChildren().add(i);
        }
    }
}
