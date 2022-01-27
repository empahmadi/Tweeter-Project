package org.ce.ap.client.pages;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ce.ap.client.gui.controller.*;
import org.ce.ap.client.impl.CommandParserServiceImpl;
import org.ce.ap.client.impl.PageHandlerImpl;
import org.ce.ap.client.impl.ToggleImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

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
    private MainController controller;
    private ArrayList<String> currentPage;
    private String mode;
    private Stage stage;
    private int size;
    private int exitMode;
    private final ToggleImpl toggle;

    /**
     * initialize some variables.
     *
     * @param cps      .
     * @param username .
     * @param main     .
     */
    public Main(CommandParserServiceImpl cps, String username, PageHandlerImpl main, ToggleImpl toggle, int exitMode, Stage stage) {
        this.cps = cps;
        this.username = username;
        this.main = main;
        currentPage = new ArrayList<>();
        this.toggle = toggle;
        this.exitMode = exitMode;
        this.stage = stage;
    }

    /**
     * this method will init our main program.
     *
     * @param size .
     * @param mode .
     * @return main scene.
     */
    public Parent init(int size, String mode) {
        this.size = size;
        this.mode = mode;
        Parent root = null;
        JSONObject response = new JSONObject(cps.main());
        if (response.getBoolean("has-error")) {
            main.error();
            return null;
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
                root = fxmlLoader.load();
                controller = fxmlLoader.getController();
                controller.init(size, mode, creatMenuBar(), createMenu(size, mode, 1), getTimeLine(size, mode, response.getJSONArray("result")));
                toggle.addController(controller);
                currentPage.add("home");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return root;
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
            controller.init(size, mode, tweet, username, this, getClass().getResource("style/").toExternalForm());
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
            controller.init(size, exitMode, mode, this, getClass().getResource("style/").toExternalForm());
            toggle.addController(controller);
            bar = (MenuBar) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bar;
    }

    public void help(){
        String path = "";
        JSONObject about = null;
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            path = config.get("client.help").toString();
        } catch (IOException ioe) {
            exceptionError(ioe.toString());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "help.json"))) {
            String line;
            StringBuilder json = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            about = new JSONObject(json.toString());
        } catch (IOException ioe) {
            exceptionError(ioe.toString());
        }
        ArrayList<String> aboutMe = new ArrayList<>();
        aboutMe.add("control+E: " + about.getString("control+E"));
        aboutMe.add("control+U: " + about.getString("control+U"));
        aboutMe.add("control+D: " + about.getString("control+D"));
        aboutMe.add("control+L: " + about.getString("control+L"));
        aboutMe.add("control+F: " + about.getString("control+F"));
        aboutMe.add("alt+A: " + about.getString("alt+A"));
        aboutMe.add("alt+O: " + about.getString("alt+O"));
        aboutMe.add("alt+V: " + about.getString("alt+V"));
        aboutMe.add("alt+H: " + about.getString("alt+H"));
        aboutMe.add("F5: " + about.getString("F5"));
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
            Parent root = fxmlLoader.load();
            AboutController controller = fxmlLoader.getController();
            controller.init(size, mode, aboutMe);
            currentPage.add("NONE_OF_THEM");
            toggle.addController(controller);
            changeContent((ScrollPane) root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void About() {
        String path = "";
        JSONObject about = null;
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            path = config.get("client.help").toString();
        } catch (IOException ioe) {
            exceptionError(ioe.toString());
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "about.json"))) {
            String line;
            StringBuilder json = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            about = new JSONObject(json.toString());
        } catch (IOException ioe) {
            exceptionError(ioe.toString());
        }
        ArrayList<String> aboutMe = new ArrayList<>();
        aboutMe.add("Name: " + about.getString("name"));
        aboutMe.add("Email: " + about.getString("email"));
        aboutMe.add("ID: " + about.getString("id"));
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
            Parent root = fxmlLoader.load();
            AboutController controller = fxmlLoader.getController();
            controller.init(size, mode, aboutMe);
            currentPage.add("NONE_OF_THEM");
            toggle.addController(controller);
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml"));
            Parent root = fxmlLoader.load();
            ListController controller = fxmlLoader.getController();
            controller.init(size, mode, list, this);
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
            profile.show(size, mode, this.username, this, response, toggle, stage);
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
        JSONObject response = new JSONObject(cps.notifies());
        if (response.getBoolean("has-error")) {
            error(response);
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notify.fxml"));
                Parent root = fxmlLoader.load();
                NoteController controller = fxmlLoader.getController();
                controller.init(size, mode, response.getJSONArray("result"));
                toggle.addController(controller);
                currentPage.add("NONE_OF_THEM");
                changeContent((ScrollPane) root);
            } catch (IOException e) {
                exceptionError(e.toString());
            }
        }
    }

    /**
     * this method logout the user.
     */
    public void logout() {
        JSONObject response = new JSONObject(cps.logout());
        main.login();
    }

    /**
     * this method for exiting from application.
     */
    public void exit() {
        if (exitMode == 0) {
            cps.exit();
            main.saveSetting();
            Platform.exit();
            System.exit(0);
        } else {
            main.saveSetting();
            System.out.println("hid");
            stage.hide();
        }
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

    public void setAccelerators(Scene scene) {
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN), new Runnable() {
            @Override
            public void run() {
                exit();
            }
        });
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
            retweets.setText("Retweets(" + jRetweets.length() + ")");
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

    /**
     * toggle screen size.
     */
    public void toggleScreen() {
        main.toggleScreen();
        this.size = main.getSize();
        toggle.toggleSize(size);
    }

    /**
     * toggle theme.
     */
    public void toggleTheme(String mode) {
        main.toggleTheme(mode);
        this.mode = main.getMode();
        toggle.toggleTheme(this.mode);
    }

    /**
     * toggle theme.
     */
    public void toggleExit() {
        main.toggleExitMode();
        this.exitMode = main.getExitMode();
    }

    /**
     * @return main scene.
     */
    public Scene getScene() {
        return stage.getScene();
    }

}
