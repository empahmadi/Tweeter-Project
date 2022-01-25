package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Profile;
import org.json.JSONObject;

/**
 * this class is a controller for profile page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ProfileController {
    private Profile main;
    @FXML
    private ScrollPane parent;
    @FXML
    private VBox list;

    public void init(int size, String mode, String main, JSONObject info, Profile mainPage, TabPane tab) {
        this.main = mainPage;
        JSONObject user, tweet;
        user = info.getJSONArray("result").getJSONObject(0);
        HBox head = new HBox();
        ImageView profileImage = new ImageView();
        Button edit = new Button("Edit");
        Label name = new Label(user.getString("name"));
        Label username = new Label(user.getString("username"));
        Label bio = new Label(user.getString("biography"));
        Label doj = new Label(user.getString("date-of-join"));
        HBox foot = new HBox();
        Button followers = new Button("followers(" + user.getJSONArray("followers").length() + ")");
        Button follow = new Button(user.getString("follow-state"));
        Button follows = new Button("follows(" + user.getJSONArray("follows").length() + ")");
        // initialize:
        head.getChildren().add(profileImage);
        if (main.equals(user.getString("username"))) {
            head.getChildren().add(edit);
            follow.setVisible(false);
        }
        foot.getChildren().add(0, followers);
        foot.getChildren().add(1, follows);
        foot.getChildren().add(2, follow);
        //styling:
        head.getStyleClass().add(mode);
        edit.getStyleClass().add(mode);
        name.getStyleClass().add(mode);
        username.getStyleClass().add(mode);
        bio.getStyleClass().add(mode);
        doj.getStyleClass().add(mode);
        foot.getStyleClass().add(mode);
        followers.getStyleClass().add(mode);
        follows.getStyleClass().add(mode);
        follow.getStyleClass().add(mode);
        parent.getStyleClass().add(mode);
        list.getStyleClass().add(mode);
        head.getStyleClass().add("head");
        edit.getStyleClass().add("edit");
        name.getStyleClass().add("label1");
        username.getStyleClass().add("label2");
        bio.getStyleClass().add("label1");
        doj.getStyleClass().add("label2");
        foot.getStyleClass().add("foot");
        followers.getStyleClass().add("btn");
        follows.getStyleClass().add("btn");
        follow.getStyleClass().add("btn");
        profileImage.getStyleClass().add("image");
        if (size == 1) {
            parent.getStyleClass().add("content-l");
            list.getStyleClass().add("list-l");
        } else {
            parent.getStyleClass().add("content-s");
            list.getStyleClass().add("list-s");
        }
        list.getChildren().add(0, head);
        list.getChildren().add(1, name);
        list.getChildren().add(2, username);
        list.getChildren().add(3, bio);
        list.getChildren().add(4, doj);
        list.getChildren().add(5, foot);
        list.getChildren().add(6, tab);
        // actions:
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // create an edit page and pass it to main.changeContent().
            }
        });
        followers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPage.list(user.getJSONArray("followers"));
            }
        });
        follows.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPage.list(user.getJSONArray("follows"));
            }
        });
        follow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPage.follow(user.getString("username"), user.getString("follow-state"));
            }
        });
    }
}
