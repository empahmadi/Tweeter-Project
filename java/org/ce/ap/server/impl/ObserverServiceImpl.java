package org.ce.ap.server.impl;

import org.ce.ap.server.database.EMPDatabase;
import org.ce.ap.server.modules.User;
import org.ce.ap.server.services.ObserverService;
import org.ce.ap.server.system.Response;
import org.json.JSONArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * this class is for follow and unfollowing a user.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class ObserverServiceImpl implements ObserverService {
    private final EMPDatabase database;
    private final AuthenticationServiceImpl au;
    private final Response response;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * initialize some services and database.
     *
     * @param database .
     */
    public ObserverServiceImpl(EMPDatabase database, AuthenticationServiceImpl au) {
        this.database = database;
        this.au = au;
        response = new Response();
    }

    /**
     * someone follows somebody.
     *
     * @param user     someone.
     * @param username somebody.
     * @return error code.
     */
    @Override
    public synchronized String follow(User user, String username) {
        User destination = au.findUser(username);
        int code = 21;
        if (user != null) {
            for (User i : database.followers.get(destination)) {
                if (i.equals(user)) {
                    code = 23;
                    break;
                }
            }
            if (code == 21) {
                database.followers.get(destination).add(user);
                database.follows.get(user).add(destination);
                database.notifications.get(destination).add("user @" + user.getUsername() + " follow you in " + dateFormat.format(LocalDateTime.now()) + ".");
                code = 35;
            }
        }
        return response.responseCode(code, "following");
    }

    /**
     * someone unfollows somebody.
     *
     * @param user     someone.
     * @param username somebody.
     * @return error code.
     */
    @Override
    public synchronized String unfollow(User user, String username) {
        User destination = au.findUser(username);
        int code = 21;
        if (destination != null) {
            for (User i : database.followers.get(destination)) {
                if (i.equals(user)) {
                    database.followers.get(destination).remove(user);
                    database.follows.get(user).remove(destination);
                    database.notifications.get(destination).add("user @" + user.getUsername() + " unfollow you in " + dateFormat.format(LocalDateTime.now()) + ".");
                    code = 39;
                    break;
                }
            }
            if (code == 21) {
                code = 24;
            }
        }
        return response.responseCode(code, "unfollowing");
    }

    /**
     * @param user .
     * @return users that follow this user.
     */
    @Override
    public synchronized ArrayList<User> getFollowers(User user) {
        return database.followers.get(user);
    }

    /**
     * @param user .
     * @return users that this user follow them.
     */
    @Override
    public synchronized ArrayList<User> getFollows(User user) {
        return database.follows.get(user);
    }

    /**
     * get followers of a user.
     *
     * @param username .
     * @return followers in json format.
     */
    @Override
    public synchronized String getJFollowers(String username) {
        User user = au.findUser(username);
        if (user == null) {
            return response.error(21, "finding-user", null);
        }
        ArrayList<User> users = getFollowers(user);
        JSONArray result = new JSONArray();
        for (User i : users) {
            result.put(i.getUsername());
        }
        return response.response(users.size(), result);
    }

    /**
     * get follows of a user.
     *
     * @param username .
     * @return follows in json format.
     */
    @Override
    public synchronized String getJFollows(String username) {
        User user = au.findUser(username);
        if (user == null) {
            return response.error(21, "finding-user", null);
        }
        ArrayList<User> users = getFollows(user);
        JSONArray result = new JSONArray();
        for (User i : users) {
            result.put(i.getUsername());
        }
        return response.response(users.size(), result);
    }
}
