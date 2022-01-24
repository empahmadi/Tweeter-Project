package org.ce.ap.client.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.ce.ap.client.gui.impl.ProfileController;
import org.ce.ap.client.gui.impl.TabController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
     *
     * @param size     .
     * @param mode     .
     * @param username .
     * @param main     .
     * @param info     .
     */
    public void show(int size, String mode, String username, Main main, JSONObject info) {
        this.main = main;
        TabPane tab = getTabPane(size, mode, info.getJSONArray("result"));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
            controller.init(size, mode, username, info, this,tab);
            main.changeContent((ScrollPane) root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TabPane getTabPane(int size, String mode, JSONArray info) {
        ArrayList<VBox> tweets = new ArrayList<>();
        ArrayList<VBox> retweets = new ArrayList<>();
        ArrayList<VBox> likes = new ArrayList<>();
        for (Object i : info.getJSONArray(1)) {
            tweets.add(main.getTweet((JSONObject) i));
        }
        for (Object i : info.getJSONArray(2)) {
            retweets.add(main.getTweet((JSONObject) i));
        }
        for (Object i : info.getJSONArray(3)) {
            likes.add(main.getTweet((JSONObject) i));
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tab.fxml"));
            Parent root = fxmlLoader.load();
            TabController controller = fxmlLoader.getController();
            controller.init(size, mode,tweets,likes,retweets,info.getJSONObject(0));
            return (TabPane) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * show a list of users.
     * @param list .
     */
    public void list(JSONArray list){
        main.userList(list);
    }

    /**
     * follow a user.
     * @param username .
     * @param action .
     */
    public void follow(String username, String action){
        main.follow(username,action);
    }
}
