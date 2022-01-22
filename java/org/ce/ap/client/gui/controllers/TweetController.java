package org.ce.ap.client.gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

public interface TweetController {
    /**
     * this method doing all action that depends on tweet.
     * @param event event.
     */
    void tweetAction(ActionEvent event);

    /**
     * make a tweet in json format and creat a layout for it.
     *
     * @param info information about tweet.
     * @param mainUser user that wants this tweet.
     * @return tweet in vbox format.
     */
    VBox tweetMaker(JSONObject info,String mainUser);
}
