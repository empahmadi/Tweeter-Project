package org.ce.ap.client.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;

import java.util.ArrayList;

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
    private ArrayList<Label> labels;
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
        labels = new ArrayList<>();
        this.code.setText(code + "");
        this.type.setText(type);
        if (params.length() == 0) {
            Label label = new Label("No Parameters");
            label.getStyleClass().add("label");
            this.params.getChildren().add(label);
            labels.add(label);
        } else {
            for (Object i : params) {
                Label label = new Label((String) i);
                label.getStyleClass().add("label");
                this.params.getChildren().add(label);
                labels.add(label);
            }
        }
        toggleTheme(mode);
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
        code.getStyleClass().add(mode);
        type.getStyleClass().add(mode);
        params.getStyleClass().add(mode);
        list.getStyleClass().add(mode);
        content.getStyleClass().add(mode);
        setLabelTheme(mode);
    }

    private void setLabelTheme(String mode){
        for (Label i: labels){
            i.getStyleClass().add(mode);
        }
    }
}
