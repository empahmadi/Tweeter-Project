package org.ce.ap.client.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * this class is a controller for notifications page.
 *
 * @author Ahmadi
 * @version 1.0
 */
public class NoteController {
    @FXML
    private ScrollPane parent;
    @FXML
    private VBox list;
    private ArrayList<TextField> notes;

    /**
     * this method initialize our page.
     *
     * @param size  .
     * @param mode  .
     * @param notes .
     */
    public void init(int size, String mode, JSONArray notes) {
        this.notes = new ArrayList<>();
        for (Object i : notes) {
            TextField note = new TextField((String) i);
            note.getStyleClass().add(mode);
            this.notes.add(note);
            list.getChildren().add(note);
        }
        toggleScreen(size);

    }

    /**
     * this method toggle our screen.
     *
     * @param size .
     */
    public void toggleScreen(int size) {
        if (size == 0) {
            parent.getStyleClass().set(0, "content-s");
            list.getStyleClass().set(0, "list-s");
        } else {
            parent.getStyleClass().set(0, "content-l");
            list.getStyleClass().set(0, "list-l");
        }
    }

    /**
     * this method toggle our theme.
     *
     * @param mode .
     */
    public void toggleMode(String mode) {
        parent.getStyleClass().set(1, mode);
        list.getStyleClass().set(1, mode);
        for (TextField i : notes)
            i.getStyleClass().set(0, mode);
    }
}
