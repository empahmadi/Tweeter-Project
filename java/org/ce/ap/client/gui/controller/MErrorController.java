package org.ce.ap.client.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;

/**
 * this class is a controller for mini error page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class MErrorController {
    @FXML
    private ScrollPane content;
    @FXML
    private VBox list;
    @FXML
    private Label code;
    @FXML
    private Label type;
    @FXML
    private VBox params;
    private Label label;
    /**
     * initialize error page.
     *
     * @param mode   .
     * @param size   .
     * @param code   .
     * @param type   .
     * @param params .
     */
    public void init(String mode, int size, int code, String type, JSONArray params) {
        toggleScreen(size);
        toggleTheme(mode);

        this.code.setText(code + "");
        this.type.setText(type);
        if (params.length() == 0) {
            label = new Label("No Parameters");
            label.getStyleClass().add("label");
            this.params.getChildren().add(label);
        } else {
            for (Object i : params) {
                label = new Label((String) i);
                label.getStyleClass().add("label");
                this.params.getChildren().add(label);
            }
        }
    }


    public void toggleScreen(int size){
        if (size == 0) {
            this.params.getStyleClass().add("params-s");
            this.list.getStyleClass().add("list-s");
            this.content.getStyleClass().add("content-s");
        } else {
            this.params.getStyleClass().add("params-l");
            this.list.getStyleClass().add("list-l");
            this.content.getStyleClass().add("content-l");
        }
    }

    public void toggleTheme(String mode){
        this.code.getStyleClass().add(mode);
        this.type.getStyleClass().add(mode);
        this.params.getStyleClass().add(mode);
        this.list.getStyleClass().add(mode);
        this.content.getStyleClass().add(mode);
        label.getStyleClass().add(mode);
    }
}
