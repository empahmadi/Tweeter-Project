package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.gui.impl.MainControllerImpl;
import org.ce.ap.client.gui.impl.TimeLineControllerImpl;
import org.ce.ap.client.gui.impl.TweetControllerImpl;
import org.ce.ap.client.impl.CommandParserServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Main {
    private final CommandParserServiceImpl cps;
    private final String username;

    /**
     * initialize some variables.
     * @param cps .
     * @param username .
     */
    public Main(CommandParserServiceImpl cps, String username) {
        this.cps = cps;
        this.username = username;
    }


    public Scene init(int size, int mode) {
        Scene scene = null;
        JSONObject response = new JSONObject(cps.main());
        if (response.getBoolean("hasError")) {

        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = fxmlLoader.load();
            MainControllerImpl controller = fxmlLoader.getController();
            controller.init(size, mode, getTimeLine(size,mode,response.getJSONArray("result")));
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    /**
     * @param size     .
     * @param mode     .
     * @param tweets   .
     * @return a set of graphical user interface tweets.
     */
    private ScrollPane getTimeLine(int size, int mode, JSONArray tweets) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("timeLine.fxml"));
            Parent root = fxmlLoader.load();
            TimeLineControllerImpl controller = fxmlLoader.getController();
            controller.init(size, mode, tweets, this);
            return (ScrollPane) root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get a graphical user interface tweet.
     *
     * @param tweet .
     * @return graphical user interface tweet.
     */
    public VBox getTweet(JSONObject tweet) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tweet.fxml"));
            Parent root = fxmlLoader.load();
            TweetControllerImpl controller = fxmlLoader.getController();
            controller.tweetMaker(tweet, username);
            return (VBox) root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
