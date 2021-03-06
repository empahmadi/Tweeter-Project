package org.ce.ap.server.services;

import org.ce.ap.server.modules.User;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this interface is a framework for authentication service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.1
 */
public interface AuthenticationService {
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
     * @param username user.
     * @return code.
     */
    int removeUser(String username);

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
     * check the validation of information that user entered.
     *
     * @param in information.
     * @return errors.
     */
    JSONArray checkInformation(JSONObject in);


    /**
     * coding the password.
     *
     * @param input primary password.
     * @return coded password.
     */
    byte[] getSHA(String input);

    /**
     * @param username .
     * @param main     main user.
     * @return the profile information of user.
     */
    String getProfile(User main, String username);

    /**
     * check that a follow follows user or no.
     *
     * @param follow .
     * @param user   .
     * @return true if follows else false.
     */
    boolean follows(User follow, User user);

    /**
     * it shows notifications.
     * @param user .
     * @return response of server.
     */
    String notifications(User user);
}
