package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.modules.User;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * this interface is a framework for authentication service.
 *
 * @author Eid Mohammad Ahmadi.
 * @version 2.0.
 */
public interface AuthenticationSer {
    /**
     * signing in a user.
     *
     * @param information information about login.
     * @return code.
     */
    int login(JSONObject information);

    /**
     * signing up a user.
     *
     * @param in information about signup.
     * @return code.
     */
    int signup(JSONObject in);

    /**
     * remove a user.
     *
     * @param user user.
     * @return code.
     */
    int removeUser(User user);

    /**
     * add a user
     *
     * @param user .
     */
    void addUser(User user);

    /**
     * @param username .
     * @return a user with given username.
     */
    User findUser(String username);

    /**
     * change the information about user.
     *
     * @param in information.
     * @return code.
     */
    int changeInformation(JSONObject in);

    /**
     * check the validation of information that user entered.
     * @param in information.
     * @return errors.
     */
    ArrayList<String> checkInformation(JSONObject in);

}
