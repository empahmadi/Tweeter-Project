package org.ce.ap.client.gui.impl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.gui.controllers.TweetController;
import org.ce.ap.client.impl.CommandParserServiceImpl;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * this class is an event handler for tweets.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class TweetControllerImpl implements TweetController, Initializable {
    @FXML
    private Hyperlink name;
    @FXML
    private Hyperlink username;
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
    private Pane identity;
    @FXML
    private Label date;
    @FXML
    private Image profileImage;
    @FXML
    private Button like;
    @FXML
    private Hyperlink delete;
    @FXML
    private Hyperlink likes;
    @FXML
    private Hyperlink retweets;
    @FXML
    private Button retweet;
    private CommandParserServiceImpl cps;
    private JSONObject JInfo;
    private String mainUser;
    private StringProperty likeAdd = new SimpleStringProperty(this,"likeAdd","");
    private StringProperty retweetAdd = new SimpleStringProperty(this,"likeAdd","");
    private StringProperty profileImageAdd = new SimpleStringProperty(this,"likeAdd","");
    private StringProperty imageAdd = new SimpleStringProperty(this,"likeAdd","");

    /**
     * @return like icon address.
     */
    public String getLikeAdd() {
        return likeAdd.get();
    }

    /**
     * set like icon address.
     * @param likeAdd like icon address.
     */
    public void setLikeAdd(String likeAdd) {
        this.likeAdd.set(likeAdd);
    }
    /**
     * @return retweet icon address.
     */
    public String getRetweetAdd() {
        return retweetAdd.get();
    }

    /**
     * set retweet icon address.
     * @param retweetAdd retweet icon address.
     */
    public void setRetweetAdd(String retweetAdd) {
        this.retweetAdd.set(retweetAdd);
    }
    /**
     * @return profile image address.
     */
    public String getProfileImageAdd() {
        return profileImageAdd.get();
    }
    /**
     * set profile image address.
     * @param profileImageAdd profile image address.
     */
    public void setProfileImageAdd(String profileImageAdd) {
        this.profileImageAdd.set(profileImageAdd);
    }
    /**
     * @return content image address.
     */
    public String getImageAdd() {
        return imageAdd.getValue();
    }

    /**
     * set content image address.
     * @param imageAdd image address.
     */
    public void setImageAdd(String imageAdd) {
        this.imageAdd.setValue(imageAdd);
    }


    /**
     * this method doing all action that depends on tweet.
     *
     * @param event event.
     */
    @Override
    public void tweetAction(ActionEvent event) {

    }

    /**
     * make a tweet in json format and creat a layout for it.
     *
     * @param info information about tweet.
     * @param mainUser user that wants this tweet.
     * @return tweet in vbox format.
     */
    @Override
    public VBox tweetMaker(JSONObject info, String mainUser) {
        if (info.getBoolean("is-retweet")) {
            isRetweet.setText("Retweet from " + info.get("main"));
        } else {
            isRetweet.setVisible(false);
        }
        username.setText(info.getString("username"));
        name.setText(info.getString("name"));
        date.setText(info.getString("creationDate"));
        textContent.setText(info.getString("content"));
        likes.setText("("+info.getJSONArray("likes").length()+")");
        retweets.setText("("+info.getJSONArray("retweets").length()+")");
        if (info.getBoolean("like-state")) {
            setLikeAdd("@icons/unlikedL.png");
        } else {
            setLikeAdd("@icons/likedL.png");
        }
        setRetweetAdd("@icons/unretweetedL.png");
        delete.setVisible(mainUser.equals(info.getString("username")));
        if (info.getBoolean("has-image")){
            setImageAdd(info.getString("image-address"));
        }else{
            imageContent.setVisible(false);
        }
        if (info.getBoolean("has-profile-image")){
            setProfileImageAdd(info.getString("profileImage"));
        }else {
            setProfileImageAdd("@icons/userW.png");
        }
        return tweet;
    }

    /**
     * set command parser service.
     *
     * @param cps .
     */
    public void setCps(CommandParserServiceImpl cps) {
        this.cps = cps;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLikeAdd("no-set-yet");
        setRetweetAdd("no-set-yet");
        setProfileImageAdd("no-set-yet");
        setImageAdd("no-set-yet");
    }
}
