package org.ce.ap.client.gui.controllers;

import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;

/**
 * this interface is a framework for MainControllerImpl
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface MainController {
    /**
     * prepare our home page.
     *
     * @param size     .
     * @param mode     .
     * @param bar      .
     * @param timeline .
     * @param menu     .
     * @return code.
     */
    int init(int size, String mode, MenuBar bar, VBox menu, ScrollPane timeline);

}
