package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;

import java.util.ArrayList;

/**
 * this class is for sorting the related tweets to a user.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class TimeLineService {
    private final EMPDatabase database;

    /**
     * this constructor is for initialize some variables.
     * @param database .
     */
    public TimeLineService(EMPDatabase database) {
        this.database = database;
    }

    /**
     * @param user user.
     * @return update(by adding time) tweets for user.
     */
    public ArrayList<Tweet> byAddingTime(User user) {
        ArrayList<Tweet> tweets = getTweets(user);
        Tweet temp;
        int k, j;
        for (k = 0; k < tweets.size(); k++) {
            for (j = k; j < tweets.size(); j++) {
                if (tweets.get(k).getAddingDate().compareTo(tweets.get(j).getAddingDate()) < 0) {
                    temp = tweets.get(j);
                    tweets.set(j, tweets.get(k));
                    tweets.set(k, temp);
                }
            }
        }
        delRepeated(tweets);
        return tweets;
    }

    /**
     * @param user user.
     * @return update(by any update) tweets for user.
     */
    public ArrayList<Tweet> latestUpdate(User user) {
        ArrayList<Tweet> tweets = getTweets(user);
        Tweet temp;
        int k, j;
        for (k = 0; k < tweets.size(); k++) {
            for (j = k; j < tweets.size(); j++) {
                if (tweets.get(k).getLatestUpdate().compareTo(tweets.get(j).getLatestUpdate()) < 0) {
                    temp = tweets.get(j);
                    tweets.set(j, tweets.get(k));
                    tweets.set(k, temp);
                }
            }
        }
        delRepeated(tweets);
        return tweets;
    }

    private ArrayList<Tweet> getTweets(User user) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (User i : database.followers.get(user)) {
            tweets.addAll(database.tweets.get(i));
        }
        for (User i : database.follows.get(user)) {
            tweets.addAll(database.tweets.get(i));
        }
        tweets.addAll(database.tweets.get(user));
        return tweets;
    }

    /**
     * delete repeated tweets.
     *
     * @param tweets tweets.
     */
    private void delRepeated(ArrayList<Tweet> tweets) {
        int i, j, size = tweets.size();
        for (i = 0; i < size; i++) {
            if (i == tweets.size() - 1)
                break;
            for (j = i + 1; j < size; j++) {
                if (j == tweets.size())
                    break;
                if (tweets.get(i).equals(tweets.get(j))) {
                    tweets.remove(j);
                    j--;

                }
            }
        }
    }

    /**
     * @param method methode.
     * @param user   user
     * @return related tweets.
     */
    public ArrayList<Tweet> run(String method, User user) {
        return switch (method) {
            case "by-latest-update" -> latestUpdate(user);
            case "by-adding-time" -> byAddingTime(user);
            default -> null;
        };
    }

}
