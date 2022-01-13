package org.ce.ap.server.services;

import org.ce.ap.server.modules.User;
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
     * @param user     someone.
     * @param username somebody.
     * @return error code.
     */
    String follow(User user, String username);

    /**
     * someone unfollows somebody.
     *
     * @param user     someone.
     * @param username somebody.
     * @return error code.
     */
    String unfollow(User user, String username);

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
     * get followers of a user.
     *
     * @param username .
     * @return followers in json format.
     */
    String getJFollowers(String username);

    /**
     * get follows of a user.
     *
     * @param username .
     * @return follows in json format.
     */
    String getJFollows(String username);
}
