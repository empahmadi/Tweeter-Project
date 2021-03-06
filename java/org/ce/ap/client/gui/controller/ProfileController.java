package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.ce.ap.client.pages.Profile;
import org.json.JSONObject;

import java.io.*;
import java.util.Properties;

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
    private HBox head;
    private ImageView profileImage;
    private Stage stage;
    private Button edit;
    private Label name;
    private Label username;
    private Label bio;
    private Label doj;
    private HBox foot;
    private Button followers;
    private Button follow;
    private Button follows;

    public void init(int size, String mode, String main, JSONObject info, Profile mainPage, TabPane tab, Stage stage) {
        this.stage = stage;
        this.main = mainPage;
        String path;
        JSONObject user;
        user = info.getJSONArray("result").getJSONObject(0);
        head = new HBox();
        File file = new File(user.getString("profile-image"));
        profileImage = new ImageView();
        Image image = new Image(file.toURI().toString(),100,100,false,false);
        profileImage.setImage(image);
        profileImage.setFitHeight(100);
        profileImage.setFitWidth(100);
        edit = new Button("Edit");
        name = new Label(user.getString("name"));
        username = new Label(user.getString("username"));
        bio = new Label(user.getString("biography"));
        doj = new Label(user.getString("date-of-join"));
        foot = new HBox();
        followers = new Button("followers(" + user.getJSONArray("followers").length() + ")");
        follow = new Button(user.getString("follow-state"));
        follows = new Button("follows(" + user.getJSONArray("follows").length() + ")");
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
        head.getStyleClass().add(0, "head");
        edit.getStyleClass().add(0, "edit");
        name.getStyleClass().add(0, "label1");
        username.getStyleClass().add(0, "label2");
        bio.getStyleClass().add(0, "label1");
        doj.getStyleClass().add(0, "label2");
        foot.getStyleClass().add(0, "foot");
        followers.getStyleClass().add(0, "btn");
        follows.getStyleClass().add(0, "btn");
        follow.getStyleClass().add(0, "btn");
        profileImage.getStyleClass().add(0, "image");
        head.getStyleClass().add(1, mode);
        edit.getStyleClass().add(1, mode);
        name.getStyleClass().add(1, mode);
        username.getStyleClass().add(1, mode);
        bio.getStyleClass().add(1, mode);
        doj.getStyleClass().add(1, mode);
        foot.getStyleClass().add(1, mode);
        followers.getStyleClass().add(1, mode);
        follows.getStyleClass().add(1, mode);
        follow.getStyleClass().add(1, mode);
        toggleTheme(mode);
        toggleScreen(size);
        list.getChildren().add(0, head);
        list.getChildren().add(1, name);
        list.getChildren().add(2, username);
        list.getChildren().add(3, bio);
        list.getChildren().add(4, doj);
        list.getChildren().add(5, foot);
        list.getChildren().add(6, tab);
        // actions:
        profileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (user.getString("username").equals(main)) {
                    String path = giveAddress();
                    if (path != null) {
                        path += user.getString("username") + ".png";
                        getImage(path);
                    }
                }
            }
        });
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

    public void getImage(String path) {
        FileChooser chooser = new FileChooser();
        File file;
        chooser.setTitle("Uploading profile");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.png"));
        file = chooser.showSaveDialog(stage);
        if (file != null) {
            try (InputStream in = new FileInputStream(file);
                 OutputStream out = new FileOutputStream(path)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    public void toggleScreen(int size) {
        parent.getStyleClass().remove(1);
        list.getStyleClass().remove(0);
        head.getStyleClass().remove(0);

        if (size == 1) {
            parent.getStyleClass().add(1, "content-l");
            list.getStyleClass().add(0, "list-l");
            head.getStyleClass().add(0, "head-l");
        } else {
            parent.getStyleClass().add(1, "content-s");
            list.getStyleClass().add(0, "list-s");
            head.getStyleClass().add(0, "head-s");

        }
    }

    public void toggleTheme(String mode) {
        head.getStyleClass().remove(1);
        edit.getStyleClass().remove(1);
        name.getStyleClass().remove(1);
        username.getStyleClass().remove(1);
        bio.getStyleClass().remove(1);
        doj.getStyleClass().remove(1);
        foot.getStyleClass().remove(1);
        followers.getStyleClass().remove(1);
        follows.getStyleClass().remove(1);
        follow.getStyleClass().remove(1);
        head.getStyleClass().add(1, mode);
        edit.getStyleClass().add(1, mode);
        name.getStyleClass().add(1, mode);
        username.getStyleClass().add(1, mode);
        bio.getStyleClass().add(1, mode);
        doj.getStyleClass().add(1, mode);
        foot.getStyleClass().add(1, mode);
        followers.getStyleClass().add(1, mode);
        follows.getStyleClass().add(1, mode);
        follow.getStyleClass().add(1, mode);
        // main:
        parent.getStyleClass().remove(2);
        list.getStyleClass().remove(1);
        parent.getStyleClass().add(2, mode);
        list.getStyleClass().add(1, mode);
    }

    /**
     * get the image address.
     * @return address.
     */
    public String giveAddress() {
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/server-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            return config.get("server.users.images").toString();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
            return null;
        }
    }
}
