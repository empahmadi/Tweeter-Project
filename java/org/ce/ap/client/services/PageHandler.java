package org.ce.ap.client.services;

import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 * this interface is a framework for PageHandler.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface PageHandler {
    /**
     * application start from here.
     */
    void run();

    /**
     * running login page.
     */
    void login();

    /**
     * check information about login.
     *
     * @param username .
     * @param password .
     * @param error    .
     * @param login    .
     */
    void checkLogin(String username, String password, Label error, Scene login);

    /**
     * run signup page.
     */
    void signup();

    /**
     * check information about signup.
     */
    void checkSignup();
}
