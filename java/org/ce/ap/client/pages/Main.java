package org.ce.ap.client.pages;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.gui.impl.*;
import org.ce.ap.client.impl.CommandParserServiceImpl;
import org.ce.ap.client.impl.PageHandlerImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * this class is our main class.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class Main {
    private final CommandParserServiceImpl cps;
    private final String username;
    private final PageHandlerImpl main;
    private final String path;
    private MainControllerImpl controller;
    private ArrayList<ScrollPane> pages;
    private ArrayList<String> currentPage;
    private int size, mode;
    private String modeS;

    /**
     * initialize some variables.
     *
     * @param cps      .
     * @param username .
     * @param main     .
     */
    public Main(CommandParserServiceImpl cps, String username, PageHandlerImpl main, String path) {
        this.cps = cps;
        this.username = username;
        this.main = main;
        this.path = path;
        pages = new ArrayList<>();
        currentPage = new ArrayList<>();
    }

    /**
     * this method will init our main program.
     *
     * @param size .
     * @param mode .
     * @return main scene.
     */
    public Scene init(int size, int mode) {
        this.size = size;
        this.mode = mode;
        if (mode == 0)
            modeS = "dark";
        else
            modeS = "light";
        Scene scene = null;
        JSONObject response = new JSONObject(cps.main());
        if (response.getBoolean("has-error")) {
            main.error();
            return null;
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
                Parent root = fxmlLoader.load();
                controller = fxmlLoader.getController();
                controller.init(size, mode, createMenu(size, mode, 1), getTimeLine(size, mode, response.getJSONArray("result")));
                scene = new Scene(root);
                currentPage.add("home");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scene;
        }
    }
    // helper methods for creating mini pages:

    /**
     * @param size   .
     * @param mode   .
     * @param tweets .
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
            controller.init(size, modeS, tweet, username, this);
            return (VBox) root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * this method will create a menu for us.
     *
     * @param size .
     * @param mode .
     * @param type .
     * @return menu.
     */
    private VBox createMenu(int size, int mode, int type) {
        VBox vBox = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = fxmlLoader.load();
            MenuController controller = fxmlLoader.getController();
            controller.init(size, mode, type, this);
            vBox = (VBox) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vBox;
    }

    /**
     * this page prepared for showing errors.
     *
     * @param response .
     */
    public void error(JSONObject response) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MError.fxml"));
            Parent root = fxmlLoader.load();
            MErrorController controller = fxmlLoader.getController();
            controller.init(modeS, size, response.getInt("error-code"), response.getString("error-type"), response.getJSONArray("params"));
            currentPage.add("NONE_OF_THEM");
            changeContent((ScrollPane) root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // menu options:

    /**
     * this method will send a tweet.
     */
    public void creatTweet() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createTweet.fxml"));
            Parent root = fxmlLoader.load();
            CreateTweetController controller = fxmlLoader.getController();
            controller.init(size, modeS, this);
            currentPage.add("NONE_OF_THEM");
            changeContent((ScrollPane) root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * show list of users.
     *
     * @param list .
     */
    public void userList(JSONArray list) {
        List page = new List();
        ScrollPane pane = page.show(mode, size, list, this);
        if (pane != null) {
            currentPage.add("NONE_OF_THEM");
            changeContent(pane);
        }
    }

    /**
     * this method will show a profile
     *
     * @param username .
     */
    public void profile(String username) {
        if (username.equals("this")) {
            username = this.username;
        }
        Profile profile = new Profile();
        JSONObject response = new JSONObject(cps.profile(username));
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            currentPage.add(username);
            profile.show(size, modeS, this.username, this, response);
        }
    }

    /**
     * this method will search a user.
     */
    public void search() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search.fxml"));
            Parent root = fxmlLoader.load();
            SearchController controller = fxmlLoader.getController();
            controller.init(size, modeS, this);
            currentPage.add("NONE_OF_THEM");
            changeContent((ScrollPane) root);
        } catch (IOException e) {
            exceptionError(e.toString());
        }
    }

    public void notifications() {

    }

    public void logout() {

    }

    /**
     * this method for exiting from application.
     */
    public void exit() {
        cps.exit();
        Platform.exit();
        System.exit(0);
    }

    /**
     * this methode will update a page.
     */
    public void update() {
        int size = currentPage.size()-1;
        if (currentPage.get(size).equals("home")){
            home();
        }else if(currentPage.get(size).equals("NONE_OF_THEM")){
        }else{
            profile(currentPage.get(size));
        }
    }

    /**
     * this method returns our page to its previous page.
     */
    public void back() {
        int size = pages.size(),size1 = currentPage.size();
        controller.changeContent(pages.get(size - 2));
        pages.remove(size - 1);
        currentPage.remove(size1-1);
    }

    /**
     * this method navigate our page to home.
     */
    public void home() {
        JSONObject response = new JSONObject(cps.main());
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            changeContent(getTimeLine(size, mode, response.getJSONArray("result")));
        }
    }

    /**
     * this method will show an error that may occur in controllers.
     *
     * @param description .
     * @param loc         .
     */
    public void error(String description, String loc) {
        JSONObject response = new JSONObject();
        JSONArray params = new JSONArray();
        params.put(description);
        response.put("has-error", true);
        response.put("error-code", 560);
        response.put("error-type", loc);
        response.put("params", params);
        error(response);
    }

    //extra methode

    /**
     * change our pages.
     *
     * @param root .
     */
    public void changeContent(ScrollPane root) {
        pages.add(root);
        controller.changeContent(root);
    }

    /**
     * change our page without saving that page.
     *
     * @param root .
     */
    public void changeContentWithoutSaving(ScrollPane root) {
        controller.changeContent(root);
    }

    /**
     * this method is for showing exceptions that may occur in application.
     *
     * @param text .
     */
    public void exceptionError(String text) {
        JSONObject response = new JSONObject();
        JSONArray params = new JSONArray();
        params.put(text);
        response.put("has-error", true);
        response.put("error-code", 562);
        response.put("error-type", "Exception Error");
        response.put("params", params);
        error(response);
    }

    //actions:
    //user actions:

    /**
     * this method will follow username.
     *
     * @param username .
     * @param action   .
     */
    public void follow(String username, String action) {
        JSONObject response = new JSONObject(cps.userAction(action, username));
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            profile(username);
        }
    }
    //tweet actions:

    /**
     * send a tweet.
     *
     * @param content .
     * @param error   .
     */
    public void sendTweet(String content, Label error) {
        JSONObject response = new JSONObject(cps.sendTweet(content));
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            error.setText("tweet sent :)");
            error.setStyle("-fx-background-color: green !important;");
            home();
        }
    }

    /**
     * this method will like a tweet.
     *
     * @param id     .
     * @param like   .
     * @param likes  .
     * @param jLikes .
     */
    public void like(int id, Button like, Button likes, JSONArray jLikes) {
        JSONObject response = new JSONObject(cps.tweetAction(like.getText(), id));
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            if (like.getText().equals("Like")) {
                like.setText("Unlike");
                jLikes.put(this.username);
            } else {
                like.setText("Like");
                for (int i = 0; i < jLikes.length(); i++) {
                    if (jLikes.getString(i).equals(this.username)) {
                        jLikes.remove(i);
                        break;
                    }
                }
            }
            likes.setText("Likes(" + jLikes.length() + ")");
        }
    }

    /**
     * this method will retweet a tweet.
     *
     * @param id        .
     * @param retweets  .
     * @param jRetweets .
     */
    public void retweet(int id, Button retweets, JSONArray jRetweets) {
        JSONObject response = new JSONObject(cps.tweetAction("retweet", id));
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            jRetweets.put(this.username);
            retweets.setText("Likes(" + jRetweets.length() + ")");
        }
    }

    /**
     * this method will delete a tweet.
     *
     * @param id .
     */
    public void delete(int id) {
        JSONObject response = new JSONObject(cps.tweetAction("delete-tweet", id));
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            update();
        }
    }

}
