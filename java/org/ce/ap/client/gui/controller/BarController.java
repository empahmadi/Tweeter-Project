package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import org.ce.ap.client.impl.ToggleImpl;
import org.ce.ap.client.pages.Main;

/**
 * this is a controller for menu bar.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class BarController {
    @FXML
    private MenuBar content;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem size;
    @FXML
    private MenuItem light;
    @FXML
    private MenuItem dark;
    @FXML
    private ToggleButton toggle;
    private Main main;

    public void init(int size, String mode, Main main) {
        this.main = main;
        toggleScreen(size);
        toggleTheme(mode);
    }

    /**
     * this method control the action button click.
     *
     * @param evt .
     */
    @FXML
    private void btn(ActionEvent evt) {
        Node node = (Node) evt.getSource();
        System.out.println(node.getId());
        switch (node.getId()) {
            case "logout" -> main.logout();
            case "exit" -> main.exit();
            case "size" -> main.toggleScreen();
            case "light" -> main.toggleTheme("light");
            case "dark" -> main.toggleTheme("dark");
            default -> main.error("an unexpected error occurred!!! :(", "in left side menu");
        }
    }

    /**
     * change the size of screen.
     * @param size .
     */
    public void toggleScreen(int size) {

    }

    /**
     * change the theme of screen.
     * @param mode .
     */
    public void toggleTheme(String mode) {
        content.getStyleClass().add(mode);
    }
}
