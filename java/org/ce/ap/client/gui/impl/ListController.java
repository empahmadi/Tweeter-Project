package org.ce.ap.client.gui.impl;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ce.ap.client.pages.List;
import org.json.JSONArray;

/**
 * this class is a controller for list page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ListController {
    private List main;
    @FXML
    private Label notFound;
    @FXML
    private ScrollPane parent;
    @FXML
    private VBox list;

    /**
     * this method init our requirement for a list.
     *
     * @param size  .
     * @param mode  .
     * @param items .
     * @param main  .
     */
    public void init(int size, String mode, JSONArray items, List main) {
        this.main = main;
        notFound.setVisible(false);
        if (size == 0) {
            list.getStyleClass().add("list-s");
            parent.getStyleClass().add("parent-s");
        } else {
            list.getStyleClass().add("list-l");
            parent.getStyleClass().add("parent-l");
        }
        if (items.length() == 0) {
            notFound.setVisible(true);
        } else {
            for (Object i : items) {
                list.getChildren().add(createItem(size, mode, (String) i));
            }
        }
    }

    /**
     * create an item for our list.
     *
     * @param size .
     * @param mode .
     * @param user .
     * @return item of list
     */
    private HBox createItem(int size, String mode, String user) {
        HBox item = new HBox();
        Button username = new Button(user);
        username.getStyleClass().add("username");
        if (size == 0) {
            item.getStyleClass().add("item-s");
        } else {
            item.getStyleClass().add("item-l");
        }
        item.getStyleClass().add(mode);
        username.getStyleClass().add(mode);

        item.getChildren().add(username);
        item.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                main.showProfile(username.getText());
            }
        });
        return item;
    }
}
