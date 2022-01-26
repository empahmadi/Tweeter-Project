package org.ce.ap.client.gui.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.Main;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * this class is a controller for list page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ListController {
    private Main main;
    @FXML
    private Label notFound;
    @FXML
    private ScrollPane parent;
    @FXML
    private VBox list;
    private ArrayList<HBox> lists;

    /**
     * this method init our requirement for a list.
     *
     * @param size  .
     * @param mode  .
     * @param items .
     * @param main  .
     */
    public void init(int size, String mode, JSONArray items, Main main) {
        this.main = main;
        notFound.setVisible(false);
        toggleScreen(size);
        lists = new ArrayList<>();
        if (items.length() == 0) {
            notFound.setVisible(true);
        } else {
            for (Object i : items) {
                list.getChildren().add(createItem(mode, (String) i));
            }
        }
        toggleTheme(mode);
    }

    /**
     * create an item for our list.
     *
     * @param mode .
     * @param user .
     * @return item of list
     */
    private HBox createItem(String mode, String user) {
        HBox item = new HBox();
        Button username = new Button(user);
        item.getChildren().add(username);
        username.getStyleClass().add("username");
        item.getStyleClass().add("item");
        lists.add(item);
        item.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                main.profile(username.getText());
            }
        });
        return item;
    }

    public void toggleScreen(int size) {
        if (size == 0) {
            list.getStyleClass().add("list-s");
            parent.getStyleClass().add("content-s");
        } else {
            list.getStyleClass().add("list-l");
            parent.getStyleClass().add("content-l");
        }
    }

    public void toggleTheme(String mode) {
        list.getStyleClass().add(mode);
        parent.getStyleClass().add(mode);
        for (HBox i: lists){
            i.getStyleClass().add(mode);
            i.getChildren().get(0).getStyleClass().add(mode);
        }
        notFound.getStyleClass().add(mode);
    }
}
