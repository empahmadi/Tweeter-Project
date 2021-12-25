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
     * @param user  .
     * @param tweet .
     * @return error code.
     */
    int like(User user, Tweet tweet);

    /**
     * unlike a tweet.
     *
     * @param user  .
     * @param tweet .
     * @return error code.
     */
    int unlike(User user, Tweet tweet);

    /**
     * send a tweet.
     *
     * @param user    .
     * @param content the content of tweet.
     * @return code.
     */
    int sendTweet(User user, String content);

    /**
     * retweet a tweet.
     *
     * @param user  .
     * @param tweet .
     */
    int retweet(User user, Tweet tweet);

    /**
     * delete a tweet.
     *
     * @param tweet .
     * @param user  .
     * @return error code.
     */
    int deleteTweet(Tweet tweet, User user);

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
     * get information and perform some action on it.
     *
     * @param information more information about action.
     * @param method      .
     * @param user        .
     * @return errors or response.
     */
    String run(User user, String method, JSONObject information);

    /**
     * @param id .
     * @return a tweet from database.
     */
    Tweet findTweet(int id);
}
