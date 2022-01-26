package org.ce.ap.client.gui.controller;

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
    private VBox list3;
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
        toggleTheme(mode);
        toggleScreen(size);
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

    public void toggleScreen(int size) {
        content.getStyleClass().remove(0);
        tweets.getStyleClass().remove(0);
        retweets.getStyleClass().remove(0);
        likes.getStyleClass().remove(0);
        info.getStyleClass().remove(0);
        list.getStyleClass().remove(0);
        list1.getStyleClass().remove(0);
        list2.getStyleClass().remove(0);
        list3.getStyleClass().remove(0);
        if (size == 1) {
            content.getStyleClass().add("content-l");
            tweets.getStyleClass().add("tab-l");
            retweets.getStyleClass().add("tab-l");
            likes.getStyleClass().add("tab-l");
            info.getStyleClass().add("tab-l");
            list.getStyleClass().add("list-l");
            list1.getStyleClass().add("list-l");
            list2.getStyleClass().add("list-l");
            list3.getStyleClass().add("list-l");
        } else {
            content.getStyleClass().add("content-s");
            tweets.getStyleClass().add("tab-s");
            retweets.getStyleClass().add("tab-s");
            likes.getStyleClass().add("tab-s");
            info.getStyleClass().add("tab-s");
            list.getStyleClass().add("list-s");
            list1.getStyleClass().add("list-s");
            list2.getStyleClass().add("list-s");
            list3.getStyleClass().add("list-s");
        }
    }

    public void toggleTheme(String mode) {
        content.getStyleClass().remove(1);
        list.getStyleClass().remove(1);
        list1.getStyleClass().remove(1);
        list2.getStyleClass().remove(1);
        list3.getStyleClass().remove(1);
        content.getStyleClass().add(mode);
        list.getStyleClass().add(mode);
        list1.getStyleClass().add(mode);
        list2.getStyleClass().add(mode);
        list3.getStyleClass().add(mode);


        tweets.getStyleClass().remove(1);
        likes.getStyleClass().remove(1);
        retweets.getStyleClass().remove(1);
        info.getStyleClass().remove(1);

        tweets.getStyleClass().add(1,mode);
        likes.getStyleClass().add(1,mode);
        retweets.getStyleClass().add(1,mode);
        info.getStyleClass().add(1,mode);
    }
}
