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
        setAccelerator();
    }



    private void setAccelerator(){

    }

    public void changeActive(String active) {

    }

    public void btn(ActionEvent evt) {
        Node node = (Node) evt.getSource();
        switch (node.getId()) {
            case "create" -> main.creatTweet();
            case "notifications" -> main.notifications();
            case "profile" -> main.profile("this");
            case "search" -> main.search();
            case "logout" -> main.logout();
            case "exit" -> main.exit();
            case "update" -> main.update();
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
        menu.getStyleClass().remove(0);
        home.getStyleClass().remove(0);
        create.getStyleClass().remove(0);
        profile.getStyleClass().remove(0);
        notifications.getStyleClass().remove(0);
        search.getStyleClass().remove(0);
        logout.getStyleClass().remove(0);
        exit.getStyleClass().remove(0);
        String btnSize = "btn-";
        if (size == 0) {
            menu.getStyleClass().add(0,"menu-s");
            btnSize += "s";
        } else {
            menu.getStyleClass().add(0,"menu-l");
            btnSize += "la";
        }
        home.getStyleClass().add(0,btnSize);
        create.getStyleClass().add(0,btnSize);
        profile.getStyleClass().add(0,btnSize);
        notifications.getStyleClass().add(0,btnSize);
        search.getStyleClass().add(0,btnSize);
        exit.getStyleClass().add(0,btnSize);
        logout.getStyleClass().add(0,btnSize);
    }

    /**
     * change the theme of screen.
     *
     * @param mode .
     */
    public void toggleTheme(String mode) {
        menu.getStyleClass().remove(1);
        create.getStyleClass().remove(1);
        home.getStyleClass().remove(1);
        copyRight.getStyleClass().remove(1);
        logout.getStyleClass().remove(1);
        logo.getStyleClass().remove(1);
        notifications.getStyleClass().remove(1);
        profile.getStyleClass().remove(1);
        search.getStyleClass().remove(1);
        exit.getStyleClass().remove(1);
        String btnMode = "btn-";
        if (mode.equals("dark")) {
            menu.getStyleClass().add(1,"dark");
            logo.getStyleClass().add(1,"dark");
            copyRight.getStyleClass().add(1,"dark");
            home.getStyleClass().add(1,"home-d");
            create.getStyleClass().add(1,"create-d");
            notifications.getStyleClass().add(1,"notify-d");
            profile.getStyleClass().add(1,"prof-d");
            search.getStyleClass().add(1,"search-d");
            logout.getStyleClass().add(1,"logout-d");
            exit.getStyleClass().add(1,"exit-d");
            btnMode += "d";
        } else {
            menu.getStyleClass().add(1,"light");
            logo.getStyleClass().add(1,"light");
            copyRight.getStyleClass().add(1,"light");
            home.getStyleClass().add(1,"home-l");
            create.getStyleClass().add(1,"create-l");
            notifications.getStyleClass().add(1,"notify-l");
            profile.getStyleClass().add(1,"prof-l");
            search.getStyleClass().add(1,"search-l");
            logout.getStyleClass().add(1,"logout-l");
            exit.getStyleClass().add(1,"exit-l");
            btnMode += "l";
        }
        home.getStyleClass().remove(2);
        create.getStyleClass().remove(2);
        notifications.getStyleClass().remove(2);
        profile.getStyleClass().remove(2);
        search.getStyleClass().remove(2);
        exit.getStyleClass().remove(2);
        logout.getStyleClass().remove(2);

        home.getStyleClass().add(2,btnMode);
        create.getStyleClass().add(2,btnMode);
        notifications.getStyleClass().add(2,btnMode);
        profile.getStyleClass().add(2,btnMode);
        search.getStyleClass().add(2,btnMode);
        exit.getStyleClass().add(2,btnMode);
        logout.getStyleClass().add(2,btnMode);
    }
}
