package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import org.ce.ap.client.gui.impl.ProfileController;
import org.json.JSONObject;

import java.io.IOException;

/**
 * this class is for make a profile page.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class Profile {
    private Main main;

    /**
     * this method will show a profile.
     * @param size .
     * @param mode .
     * @param username .
     * @param main .
     * @param info .
     */
    public void show(int size, int mode, String username, Main main, JSONObject info){
        this.main = main;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
            controller.init(size,mode,username,info,this);
            main.changeContent((ScrollPane) root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
