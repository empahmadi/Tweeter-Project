package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;

/**
 * controller for our menu.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class MenuController {
    @FXML
    private VBox menu;
    @FXML
    private ImageView logo;
    @FXML
    private Button home;
    @FXML
    private Button create;
    @FXML
    private Button notifications;
    @FXML
    private Button profile;
    @FXML
    private Button search;
    @FXML
    private Button logout;
    @FXML
    private Button exit;
    @FXML
    private Label copyRight;
    private Main main;


    public void init(int size, String mode, int main, Main page) {
        this.main = page;
        toggleTheme(mode);
        toggleScreen(size);
    }

    public void changeActive(String active) {

    }

    public void btn(ActionEvent evt) {
        Node node = (Node) evt.getSource();
        System.out.println(node.getId());
        switch (node.getId()) {
            case "create" -> main.creatTweet();
            case "notifications" -> main.notifications();
            case "profile" -> main.profile("this");
            case "search" -> main.search();
            case "logout" -> main.logout();
            case "exit" -> main.exit();
            case "update" -> main.update();
            case "back" -> main.back();
            case "home" -> main.home();
            default -> main.error("an unexpected error occurred!!! :(", "in left side menu");
        }
    }

    /**
     * change the size of screen.
     *
     * @param size .
     */
    public void toggleScreen(int size) {
        String btnSize = "btn-";
        if (size == 0) {
            menu.getStyleClass().add("menu-s");
            btnSize += "s";
        } else {
            menu.getStyleClass().add("menu-l");
            btnSize += "la";
        }
        home.getStyleClass().add(btnSize);
        create.getStyleClass().add(btnSize);
        profile.getStyleClass().add(btnSize);
        notifications.getStyleClass().add(btnSize);
        search.getStyleClass().add(btnSize);
        exit.getStyleClass().add(btnSize);
        logout.getStyleClass().add(btnSize);
    }

    /**
     * change the theme of screen.
     *
     * @param mode .
     */
    public void toggleTheme(String mode) {
        String btnMode = "btn-";
        if (mode.equals("dark")) {
            menu.getStyleClass().add("dark");
            logo.getStyleClass().add("dark");
            copyRight.getStyleClass().add("dark");
            home.getStyleClass().add("home-d");
            create.getStyleClass().add("create-d");
            notifications.getStyleClass().add("notify-d");
            profile.getStyleClass().add("prof-d");
            search.getStyleClass().add("search-d");
            logout.getStyleClass().add("logout-d");
            exit.getStyleClass().add("exit-d");
            btnMode += "d";
        } else {
            menu.getStyleClass().add("light");
            logo.getStyleClass().add("light");
            copyRight.getStyleClass().add("light");
            home.getStyleClass().add("home-l");
            create.getStyleClass().add("create-l");
            notifications.getStyleClass().add("notify-l");
            profile.getStyleClass().add("prof-l");
            search.getStyleClass().add("search-l");
            logout.getStyleClass().add("logout-l");
            exit.getStyleClass().add("exit-l");
            btnMode += "l";
        }
        home.getStyleClass().add(btnMode);
        create.getStyleClass().add(btnMode);
        notifications.getStyleClass().add(btnMode);
        profile.getStyleClass().add(btnMode);
        search.getStyleClass().add(btnMode);
        exit.getStyleClass().add(btnMode);
        logout.getStyleClass().add(btnMode);
    }
}
