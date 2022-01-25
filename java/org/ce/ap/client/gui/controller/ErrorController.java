package org.ce.ap.client.gui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * this class is a controller for error page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ErrorController {
    @FXML
    private Pane parent;
    @FXML
    private Label caption;
    @FXML
    private ImageView image;
    @FXML
    private Button back;
    private StringProperty imageAdd = new SimpleStringProperty(this, "likeAdd", "");
    private String path;
    private String mode;

    /**
     * initialize the error page.
     *
     * @param size    .
     * @param mode    .
     * @param caption .
     * @param type    .
     * @param path    path of icons.
     */
    public void init(int size, String mode, String caption, int type, String path) {
        parent.getStyleClass().add("container");
        if (size == 1) {
            parent.getStyleClass().add("large");
            this.caption.getStyleClass().add("large");
            image.getStyleClass().add("large");
            back.getStyleClass().add("large");
        } else {
            parent.getStyleClass().add("small");
            this.caption.getStyleClass().add("caption-s");
            image.getStyleClass().add("image-s");
            back.getStyleClass().add("btn-s");
        }
        parent.getStyleClass().add(mode);
        this.caption.getStyleClass().add(mode);
        image.getStyleClass().add(mode);
        back.getStyleClass().add(mode);

        this.caption.setText(caption);
        this.mode = mode;
        this.path = path;
    }

    /**
     * @return content image address.
     */
    public String getImageAdd() {
        return imageAdd.getValue();
    }

    /**
     * set content image address.
     *
     * @param imageAdd image address.
     */
    public void setImageAdd(String imageAdd) {
        this.imageAdd.setValue(imageAdd);
    }
}
