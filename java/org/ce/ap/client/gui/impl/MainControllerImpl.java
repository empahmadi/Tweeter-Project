package org.ce.ap.client.gui.impl;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.ce.ap.client.gui.controllers.MainController;

public class MainControllerImpl implements MainController {
    @FXML
    private Pane container;
    @FXML
    private HBox home;
    private String username;

    /**
     * prepare our home page.
     *
     * @param size .
     * @param mode .
     * @param timeline .
     * @return code.
     */
    @Override
    public int init(int size, int mode, ScrollPane timeline) {
        home.getChildren().add(timeline);
        return 1;
    }
}
