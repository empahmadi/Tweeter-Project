package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.modules.User;

import java.util.ArrayList;

public interface ObserverSer {
    /**
     * someone follows somebody.
     *
     * @param follower someone.
     * @param follow   somebody.
     * @return code.
     */
    int follow(User follower, User follow);

    /**
     * someone unfollows somebody.
     *
     * @param follower someone.
     * @param follow   somebody.
     * @return code.
     */
    int unfollow(User follower, User follow);

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
}
