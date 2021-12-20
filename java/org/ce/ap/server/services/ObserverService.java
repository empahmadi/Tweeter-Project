package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.system.Response;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * this class is for follow and unfollowing a user.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class ObserverService {
    private final EMPDatabase database;
    private final AuthenticationService au;
    private final Response response;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * initialize some services and database.
     * @param database .
     */
    public ObserverService(EMPDatabase database, AuthenticationService au) {
        this.database = database;
        this.au = au;
        response = new Response();
    }

    /**
     * someone follows somebody.
     *
     * @param user        someone.
     * @param destination somebody.
     * @return error code.
     */
    private int follow(User user, User destination) {
        for (User i : database.followers.get(destination)) {
            if (i.equals(user)) {
                return 29;
            }
        }
        database.followers.get(destination).add(user);
        database.follows.get(user).add(destination);
        database.notifications.get(user).add("user @" + user.getUsername() + " follow you in " + dateFormat.format(LocalDateTime.now()) + ".");
        return 0;
    }

    /**
     * someone unfollows somebody.
     *
     * @param user        someone.
     * @param destination somebody.
     * @return error code.
     */
    private int unfollow(User user, User destination) {
        for (User i : database.followers.get(destination)) {
            if (i.equals(user)) {
                database.followers.get(destination).remove(user);
                database.follows.get(user).remove(destination);
                database.notifications.get(user).add("user @" + user.getUsername() + " unfollow you in " + dateFormat.format(LocalDateTime.now()) + ".");
                return 0;
            }
        }
        return 29;
    }

    /**
     * @param user .
     * @return users that follow this user.
     */
    public ArrayList<User> getFollowers(User user) {
        return database.followers.get(user);
    }

    /**
     * @param user .
     * @return users that this user follow them.
     */
    public ArrayList<User> getFollows(User user) {
        return database.follows.get(user);
    }

    /**
     * this methode manage follow and unfollow actions and handle its return codes.
     *
     * @param method    follow or unfollow.
     * @param parameter parameter include username of destination.
     * @param user      user.
     * @return response of server in JSON format.
     */
    public String run(String method, JSONObject parameter, User user) {
        User destination = au.findUser(parameter.getString("username"));
        if (destination == null) {
            return response.error(1, "following/unfollowing", null);
        }
        if (method.equals("follow")) {
            return response.responseCode(follow(user, destination), "following");
        } else if (method.equals("unfollow")) {
            return response.responseCode(unfollow(user, destination), "unfollowing");
        }
        return response.error(45, "unexpected error", null);
    }
}
