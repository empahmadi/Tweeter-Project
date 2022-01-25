package org.ce.ap.client.gui.impl;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.gui.controllers.MainController;

import java.util.ArrayList;

public class MainControllerImpl implements MainController {
    @FXML
    private Pane container;
    @FXML
    private HBox home;
    private String username;

    /**
     * prepare our home page.
     *
     * @param size     .
     * @param mode     .
     * @param timeline .
     * @param menu     .
     * @return code.
     */
    @Override
    public int init(int size, String mode, MenuBar bar, VBox menu, ScrollPane timeline) {
        container.getChildren().add(0,bar);
        home.getChildren().add(0,menu);
        home.getChildren().add(1,timeline);
        return 1;
    }

    /**
     * this method change our content.
     * @param content .
     */
    public void changeContent(ScrollPane content){
        home.getChildren().set(1,content);
    }
}
