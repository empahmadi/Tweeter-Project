package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * this interface is a framework for tweeting service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.1
 */
public interface TweetingService {
    /**
     * likes a tweet.
     *
     * @param user .
     * @param id   .
     * @return error code.
     */
    String like(User user, int id);

    /**
     * unlike a tweet.
     *
     * @param user .
     * @param id   .
     * @return error code.
     */
    String unlike(User user, int id);

    /**
     * send a tweet.
     *
     * @param user    .
     * @param content the content of tweet.
     * @return code.
     */
    String sendTweet(User user, String content);

    /**
     * retweet a tweet.
     *
     * @param user .
     * @param id   .
     */
    String retweet(User user, int id);

    /**
     * delete a tweet.
     *
     * @param id   .
     * @param user .
     * @return error code.
     */
    String deleteTweet(int id, User user);

    /**
     * @param user .
     * @return tweets that a user tweeted.
     */
    ArrayList<Tweet> getTweets(User user);

    /**
     * @param tweet .
     * @return users that likes the tweet.
     */
    JSONArray getLikes(Tweet tweet);

    /**
     * @param tweet .
     * @return users that retweeted this tweet.
     */
    JSONArray getRetweets(Tweet tweet);

    /**
     * give a tweet and change its information to JSON format.
     *
     * @param tweet tweet.
     * @return information of tweet.
     */
    JSONObject getTweet(Tweet tweet);

    /**
     * get a tweet by its id.
     *
     * @param id .
     * @return tweet in json format.
     */
    String getTweetByID(int id);

    /**
     * @param id .
     * @return a tweet from database.
     */
    Tweet findTweet(int id);

    /**
     * this method get users that retweet a tweet.
     *
     * @param id .
     * @return response of server in json format.
     */
    String getJRetweets(int id);

    /**
     * this method get users that likes a tweet.
     *
     * @param id .
     * @return response of server in json format.
     */
    String getJLikes(int id);
}
