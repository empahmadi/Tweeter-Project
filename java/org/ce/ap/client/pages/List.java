package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import org.ce.ap.client.gui.impl.ListController;
import org.json.JSONArray;

import java.io.IOException;

public class List {
    private Main main;

    public ScrollPane show(int mode, int size, JSONArray users, Main main){
        this.main = main;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("list.fxml"));
            Parent root = loader.load();
            ListController controller = loader.getController();
            controller.init(size,mode,users,this);
            return (ScrollPane) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void showProfile(String username){
        main.profile(username);
    }
}
