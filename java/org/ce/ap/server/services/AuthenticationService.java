package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.modules.User;
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
     * is in working.
     *
     * @param in information.
     * @return code.
     */
    int changeInformation(JSONObject in);

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
     * @return the profile information of user.
     */
    String getProfile(String username);
}
