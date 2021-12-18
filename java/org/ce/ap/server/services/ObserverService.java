package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.impl.ObserverSer;
import main.java.org.ce.ap.server.modules.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ObserverService implements ObserverSer {
    private final EMPDatabase database;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public ObserverService(EMPDatabase database) {
        this.database = database;
    }

    /**
     * someone follows somebody.
     *
     * @param follower someone.
     * @param follow   somebody.
     * @return code.
     */
    @Override
    public int follow(User follower, User follow) {
        if (follow == null || follower == null)
            return 1;
        if (!database.users.contains(follow) || !database.users.contains(follower))
            return 20;
        database.followers.get(follow).add(follower);
        database.follows.get(follower).add(follow);
        database.notifications.get(follower).add("user @" + follower.getUsername() + " follow you in " + dateFormat.format(LocalDateTime.now())+".");
        return 0;
    }

    /**
     * someone unfollows somebody.
     *
     * @param follower someone.
     * @param follow   somebody.
     * @return code.
     */
    @Override
    public int unfollow(User follower, User follow) {
        if (follow == null || follower == null)
            return 1;
        if (!database.users.contains(follow) || !database.users.contains(follower))
            return 20;
        database.followers.get(follow).remove(follower);
        database.follows.get(follower).remove(follow);
        database.notifications.get(follower).add("user @" + follower.getUsername() + " unfollow you in " + dateFormat.format(LocalDateTime.now())+".");
        return 0;
    }

    /**
     * @param user .
     * @return users that follow this user.
     */
    @Override
    public ArrayList<User> getFollowers(User user) {
        return database.followers.get(user);
    }

    /**
     * @param user .
     * @return users that this user follow them.
     */
    @Override
    public ArrayList<User> getFollows(User user) {
        return database.follows.get(user);
    }
}
