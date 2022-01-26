package org.ce.ap.client.services;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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
     * @param rememberMe .
     */
    void checkLogin(String username, String password, Label error, boolean rememberMe);

    /**
     * run signup page.
     */
    void signup();

    /**
     * check information about signup.
     * @param name .
     * @param username .
     * @param lastname .
     * @param password .
     * @param dateOfBirth .
     * @param sex .
     * @param bio .
     * @para
     */
    void checkSignup(String name, String username, String lastname, String password, String dateOfBirth, String sex, String bio, TextArea error, Scene signup);
}
