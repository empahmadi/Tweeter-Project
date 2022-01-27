package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * this class is an event handler for tweets.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class TweetController {
    @FXML
    private Pane userPanel;
    private Main main;
    private String path;
    private Scene scene;
    private JSONArray jLikes;
    private JSONArray jRetweets;
    private int id;
    private int size;
    private String mode;
    private String style;
    private String owner;
    @FXML
    private Button name;
    @FXML
    private Button username;
    @FXML
    private Button isRetweet;
    @FXML
    private VBox tweet;
    @FXML
    private HBox information;
    @FXML
    private Label textContent;
    @FXML
    private ImageView imageContent;
    @FXML
    private HBox caption;
    @FXML
    private Button profile;
    @FXML
    private Label date;
    @FXML
    private Button like;
    @FXML
    private Button delete;
    @FXML
    private Button likes;
    @FXML
    private Button retweets;
    @FXML
    private Button retweet;
    @FXML
    private Pane cap1;
    @FXML
    private Pane cap2;
    @FXML
    private Pane cap3;


    /**
     * this method doing all action that depends on tweet.
     *
     * @param event event.
     */
    @FXML
    public void click(ActionEvent event) {
        Node node = (Node) event.getSource();
        switch (node.getId()) {
            case "username", "name", "profile":
                main.profile(username.getText());
                break;
            case "like":
                main.like(id,like,likes,jLikes);
                break;
            case "likes":
                main.userList(jLikes);
                break;
            case "retweet":
                main.retweet(id,retweets,jRetweets);
                break;
            case "retweets":
                main.userList(jRetweets);
                break;
            case "delete":
                main.delete(id);
                break;
            case "isRetweet":
                main.profile(owner);
                break;
            default:
                main.error("error in tweet action!!!", "TweetControllerImpl, click()");
        }
    }

    /**
     * make a tweet in json format and creat a layout for it.
     *
     * @param size     .
     * @param mode     .
     * @param info     information about tweet.
     * @param mainUser user that wants this tweet.
     * @param main     .
     */
    public void init(int size, String mode, JSONObject info, String mainUser, Main main, String path) {
        this.main = main;
        this.path = path;
        style = "tweetLL.css";
        this.size = size;
        this.mode = mode;
        this.scene = main.getScene();
        toggleScreen(size);
        // initialization:
        if(mainUser.equals(info.getString("username"))){
            retweet.setVisible(false);
        }
        name.setText(info.getString("name"));
        username.setText(info.getString("username"));
        like.setText(info.getString("like-state"));
        likes.setText("Likes(" + info.getJSONArray("likes").length() + ")");
        retweet.setText("Retweet");
        retweets.setText("Retweets(" + info.getJSONArray("retweets").length() + ")");
        date.setText(info.getString("creationDate"));
        textContent.setText(info.getString("content"));
        textContent.setWrapText(true);
        delete.setVisible(mainUser.equals(info.getString("username")));
        if (info.getBoolean("is-retweet")){
            isRetweet.setText("this tweet is a retweet from "+info.getString("main"));
            owner = info.getString("main");
            isRetweet.setVisible(true);
        }else{
            isRetweet.setVisible(false);
        }
        if (!info.getBoolean("has-image")) {
            tweet.getChildren().remove(imageContent);
        }
        jLikes = info.getJSONArray("likes");
        jRetweets = info.getJSONArray("retweets");
        id = info.getInt("tweet-id");
    }

    public void toggleScreen(int size){
        String name = "tweet";
        if (mode.equals("light")){
            name += "L";
        }else{
            name += "D";
        }
        if (size == 0){
            name += "S";
        }else{
            name += "L";
        }
        name+= ".css";
        scene.getStylesheets().remove(path+style);
        style = name;
        scene.getStylesheets().add(path+style);
        this.size = size;
    }

    public void toggleTheme(String mode){
        String name = "tweet";
        if (mode.equals("light")){
            name += "L";
        }else{
            name += "D";
        }
        if (size == 0){
            name += "S";
        }else{
            name += "L";
        }
        name+= ".css";
        scene.getStylesheets().remove(path+style);
        style = name;
        scene.getStylesheets().add(path+style);
        this.mode = mode;
    }
}
