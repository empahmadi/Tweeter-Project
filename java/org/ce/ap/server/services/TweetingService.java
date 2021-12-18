package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * this is a service that works on tweets and mange it.
 * this class implements TweetingSer interface because
 * that interface is a framework for tweeting service.
 * methods: send tweet, delete tweet, retweet a tweet, like, unlike, find a tweet by its id,
 * check that a string is numeric or no, run, checks that a tweet is a retweet or no, id maker.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class TweetingService {
    private final EMPDatabase database;
    private final AuthenticationService au;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public TweetingService(EMPDatabase database) {
        this.database = database;
        au = new AuthenticationService(database);
    }

    /**
     * likes a tweet.
     *
     * @param user  .
     * @param tweet .
     * @return error code.
     */
    private int like(User user, Tweet tweet) {
        for (User i : database.likes.get(tweet)) {
            if (i.equals(user)) {
                return 1;
            }
        }
        database.likes.get(tweet).add(user);
        return 0;
    }

    /**
     * unlike a tweet.
     *
     * @param user  .
     * @param tweet .
     * @return error code.
     */
    private int unlike(User user, Tweet tweet) {
        for (User i : database.likes.get(tweet)) {
            if (user.equals(i)) {
                database.likes.get(tweet).remove(user);
                return 0;
            }
        }
        return 1;
    }

    /**
     * send a tweet.
     *
     * @param user    .
     * @param content the content of tweet.
     * @return code.
     */
    private int sendTweet(User user, String content) {
        if (content.length() > 256)
            return 3;
        if (content.length() == 0)
            return 4;
        Tweet tweet = new Tweet(content, user, idMaker());
        database.retweets.put(tweet, new ArrayList<>());
        database.tweets.get(user).add(tweet);
        database.likes.put(tweet, new ArrayList<>());
        database.allTweet.add(tweet);
        return 0;
    }

    /**
     * retweet a tweet.
     *
     * @param user  .
     * @param tweet .
     */
    private void retweet(User user, Tweet tweet) {
        database.retweets.get(tweet).add(user);
        database.tweets.get(user).add(tweet);
    }

    /**
     * delete a tweet.
     *
     * @param tweet .
     * @param user  .
     * @return error code.
     */
    private int deleteTweet(Tweet tweet, User user) {
        for (Tweet i : database.tweets.get(user)) {
            if (tweet.equals(i)) {
                database.tweets.get(user).remove(tweet);
                database.likes.remove(tweet);
                database.retweets.remove(tweet);
                database.allTweet.remove(tweet);
                for (User j : database.retweets.get(tweet)) {
                    database.tweets.get(j).remove(tweet);
                }
                return 0;
            }
        }
        return 1;
    }

    /**
     * @param user .
     * @return tweets that a user tweeted.
     */
    public ArrayList<Tweet> getTweets(User user) {
        return database.tweets.get(user);
    }

    /**
     * @param tweet .
     * @return users that likes the tweet.
     */
    public ArrayList<User> getLikes(Tweet tweet) {
        return database.likes.get(tweet);
    }

    /**
     * @param tweet .
     * @return users that retweeted this tweet.
     */
    public ArrayList<User> getRetweets(Tweet tweet) {
        return database.retweets.get(tweet);
    }

    /**
     * @param tweet .
     * @return tweet with its information.
     */
    public String show(Tweet tweet) {

        return "|" + tweet.getUser().getUsername() + "   " +
                dateFormat.format(tweet.getAddingDate()) + "\n" +
                "|" + tweet.getContent() + "\n" +
                "Likes: " + database.likes.get(tweet).size() +
                "       " + "Retweets:" + database.retweets.get(tweet).size();
    }

    /**
     * @param tweet .
     * @return tweet for a list.
     */
    public String list(Tweet tweet) {
        return null;
    }

    /**
     * @param tweet .
     * @return true if tweet is a retweet else false.
     */
    public boolean isRetweet(Tweet tweet) {
        return false;
    }

    /**
     * get information and perform some action on it.
     *
     * @param username .
     * @param method   .
     * @param user     .
     * @param id       id of tweet.
     * @return errors or response.
     */
    public ArrayList<String> run(User user, String method, String username, int id) {
        int errorCode;
        if (method.equals("Send-Tweet")) {
            errorCode = sendTweet(user, username);
        }
        User user1 = au.findUser(username);
        if (user1 == null) {
            return null;
        }
        Tweet tweet = findTweet(id);
        if (tweet == null) {
            return null;
        }
        switch (method) {
            case "Delete-Tweet":
                errorCode = deleteTweet(tweet, user);
            case "Like":
                errorCode = like(user, tweet);
            case "Unlike":
                errorCode = unlike(user, tweet);
            case "retweet":
                retweet(user, tweet);
            default:
                return null;
        }
    }

    /**
     * @param id .
     * @return a tweet from database.
     */
    public Tweet findTweet(int id) {
        for (Tweet i : database.allTweet) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    /**
     * @param str .
     * @return true if str is a number else false.
     */
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int idMaker() {
        return database.getId();
    }
}
