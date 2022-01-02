package main.java.org.ce.ap.client.services;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this interface is a framework for console view service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface ConsoleViewService {

    /**
     * login or signup someone to system.
     * @return code.
     */
    int loginPage();

    /**
     * show main page.
     * @return code.
     */
    int main();

    /**
     * show a list of anything that we need it to show as a list.
     * @param type type of list.
     * @param username .
     * @return code.
     */
    int list(String type,String username);

    /**
     * show a list that its content is in client side.
     * @param items items of list.
     * @return code.
     */

    int listView(JSONArray items);

    /**
     * this method will show a profile.
     *
     * @param username .
     * @return code.
     */
    int profile(String username);

    /**
     * this method will show a tweet.
     * @param id  tweet id.
     * @return code.
     */
    int tweet(int id);

    /**
     * this method will sign up a user.
     * @return code.
     */
    int signup();

    /**
     * creat a tweet.
     * @return code.
     */
    int creatTweet();
    /**
     * this method is for exit.
     */

    void exit();
    /**
     * this method will show error that related to its code.
     *
     * @param error .
     */
    void parseError(JSONObject error);

    /**
     * this method will show error with given code.
     *
     * @param code error code.
     */
    void parseErrorByCode(int code);
}
