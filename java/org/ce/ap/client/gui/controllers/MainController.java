package org.ce.ap.client.gui.controllers;

import javafx.scene.control.ScrollPane;
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
     * @param timeline .
     * @return code.
     */
    int init(int size, int mode, ScrollPane timeline);

}
