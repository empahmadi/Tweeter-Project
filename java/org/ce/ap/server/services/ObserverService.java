package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.modules.User;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * this interface is a framework for observer service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.1
 */
public interface ObserverService {
    /**
     * someone follows somebody.
     *
     * @param user        someone.
     * @param destination somebody.
     * @return error code.
     */
    int follow(User user, User destination);

    /**
     * someone unfollows somebody.
     *
     * @param user        someone.
     * @param destination somebody.
     * @return error code.
     */
    int unfollow(User user, User destination);

    /**
     * @param user .
     * @return users that follow this user.
     */
    ArrayList<User> getFollowers(User user);

    /**
     * @param user .
     * @return users that this user follow them.
     */
    ArrayList<User> getFollows(User user);
    /**
     * this methode manage follow and unfollow actions and handle its return codes.
     *
     * @param method    follow or unfollow.
     * @param parameter parameter include username of destination.
     * @param user      user.
     * @return response of server in JSON format.
     */
    String run(String method, JSONObject parameter, User user);
}
