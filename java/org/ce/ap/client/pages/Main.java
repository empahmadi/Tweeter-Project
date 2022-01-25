package org.ce.ap.client.pages;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.gui.controller.*;
import org.ce.ap.client.impl.CommandParserServiceImpl;
import org.ce.ap.client.impl.PageHandlerImpl;
import org.ce.ap.client.impl.ToggleImpl;
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
    private MainController controller;
    private ArrayList<ScrollPane> pages;
    private ArrayList<String> currentPage;
    private String mode;
    private int size;
    private final ToggleImpl toggle;
    /**
     * initialize some variables.
     *
     * @param cps      .
     * @param username .
     * @param main     .
     */
    public Main(CommandParserServiceImpl cps, String username, PageHandlerImpl main, String path,ToggleImpl toggle) {
        this.cps = cps;
        this.username = username;
        this.main = main;
        this.path = path;
        pages = new ArrayList<>();
        currentPage = new ArrayList<>();
        this.toggle = toggle;
    }

    /**
     * this method will init our main program.
     *
     * @param size .
     * @param mode .
     * @return main scene.
     */
    public Scene init(int size, String mode) {
        this.size = size;
        this.mode = mode;
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
                controller.init(size, mode, creatMenuBar(), createMenu(size, mode, 1), getTimeLine(size, mode, response.getJSONArray("result")));
                toggle.addController(controller);
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
    private ScrollPane getTimeLine(int size, String mode, JSONArray tweets) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("timeLine.fxml"));
            Parent root = fxmlLoader.load();
            TimeLineController controller = fxmlLoader.getController();
            controller.init(size, mode, tweets, this);
            toggle.addController(controller);
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
            TweetController controller = fxmlLoader.getController();
            controller.init(size, mode, tweet, username, this);
            toggle.addController(controller);
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
    private VBox createMenu(int size, String mode, int type) {
        VBox vBox = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = fxmlLoader.load();
            MenuController controller = fxmlLoader.getController();
            controller.init(size, mode, type, this);
            toggle.addController(controller);
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
            controller.init(mode, size, response.getInt("error-code"), response.getString("error-type"), response.getJSONArray("params"));
            currentPage.add("NONE_OF_THEM");
            toggle.addController(controller);
            changeContent((ScrollPane) root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MenuBar creatMenuBar() {
        MenuBar bar = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuBar.fxml"));
            Parent root = fxmlLoader.load();
            BarController controller = fxmlLoader.getController();
            controller.init(size, mode, this);
            toggle.addController(controller);
            bar = (MenuBar) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bar;
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
            controller.init(size, mode, this);
            currentPage.add("NONE_OF_THEM");
            toggle.addController(controller);
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
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml"));
            Parent root = fxmlLoader.load();
            ListController controller = fxmlLoader.getController();
            controller.init(size,mode,list,this);
            currentPage.add("NONE_OF_THEM");
            toggle.addController(controller);
            changeContent((ScrollPane) root);
        } catch (IOException e) {
            e.printStackTrace();
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
            profile.show(size, mode, this.username, this, response,toggle);
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
            controller.init(size, mode, this);
            toggle.addController(controller);
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
        int size = currentPage.size() - 1;
        if (currentPage.get(size).equals("home")) {
            home();
        } else if (currentPage.get(size).equals("NONE_OF_THEM")) {
        } else {
            profile(currentPage.get(size));
        }
    }

    /**
     * this method returns our page to its previous page.
     */
    public void back() {
        int size = pages.size(), size1 = currentPage.size();
        controller.changeContent(pages.get(size - 2));
        pages.remove(size - 1);
        currentPage.remove(size1 - 1);
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

    // change setting:

    public void toggleScreen(){
        main.toggleScreen();
        this.size = main.getSize();
        toggle.toggleSize(size);
    }

    public void toggleTheme(String mode){
        main.toggleTheme();
        this.mode = main.getMode();
    }

}
