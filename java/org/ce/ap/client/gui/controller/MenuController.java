package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.ce.ap.client.pages.Main;

/**
 * controller for our menu.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class MenuController {
    private Main main;


    public void init(int size, String mode, int main, Main page) {
        this.main = page;
    }

    public void changeActive(String active) {

    }

    public void btn(ActionEvent evt) {
        Node node = (Node) evt.getSource();
        System.out.println(node.getId());
        switch (node.getId()) {
            case "create" -> main.creatTweet();
            case "notifications" -> main.notifications();
            case "profile" -> main.profile("this");
            case "search" -> main.search();
            case "logout" -> main.logout();
            case "exit" -> main.exit();
            case "update" -> main.update();
            case "back" -> main.back();
            case "home" -> main.home();
            default -> main.error("an unexpected error occurred!!! :(", "in left side menu");
        }
    }
}
