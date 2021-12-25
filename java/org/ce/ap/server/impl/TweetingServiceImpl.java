package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.services.TweetingService;
import main.java.org.ce.ap.server.system.Response;
import org.json.JSONArray;
import org.json.JSONObject;

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
public class TweetingServiceImpl implements TweetingService {
    private final EMPDatabase database;
    private final Response response;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * this constructor gives some features and make usable them for this class.
     *
     * @param database .
     */
    public TweetingServiceImpl(EMPDatabase database) {
        this.database = database;
        response = new Response();
    }

    /**
     * likes a tweet.
     *
     * @param user  .
     * @param tweet .
     * @return error code.
     */
    @Override
    public int like(User user, Tweet tweet) {
        for (User i : database.likes.get(tweet)) {
            if (i.equals(user)) {
                return 1;
            }
        }
        database.likes.get(tweet).add(user);
        database.like.get(user).add(tweet);
        return 32;
    }

    /**
     * unlike a tweet.
     *
     * @param user  .
     * @param tweet .
     * @return error code.
     */
    @Override
    public int unlike(User user, Tweet tweet) {
        for (User i : database.likes.get(tweet)) {
            if (user.equals(i)) {
                database.likes.get(tweet).remove(user);
                database.like.get(user).remove(tweet);
                return 33;
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
    @Override
    public int sendTweet(User user, String content) {
        if (content.length() > 256)
            return 3;
        if (content.length() == 0)
            return 4;
        Tweet tweet = new Tweet(content, user, idMaker());
        database.retweets.put(tweet, new ArrayList<>());
        database.tweets.get(user).add(tweet);
        database.likes.put(tweet, new ArrayList<>());
        database.allTweet.add(tweet);
        return 31;
    }

    /**
     * retweet a tweet.
     *
     * @param user  .
     * @param tweet .
     */
    @Override
    public int retweet(User user, Tweet tweet) {
        database.retweets.get(tweet).add(user);
        database.tweets.get(user).add(tweet);
        database.retweet.get(user).add(tweet);
        return 38;
    }

    /**
     * delete a tweet.
     *
     * @param tweet .
     * @param user  .
     * @return error code.
     */
    @Override
    public int deleteTweet(Tweet tweet, User user) {
        for (Tweet i : database.tweets.get(user)) {
            if (tweet.equals(i)) {
                database.tweets.get(user).remove(tweet);
                database.likes.remove(tweet);
                for (User j : database.retweets.get(tweet)) {
                    database.tweets.get(j).remove(tweet);
                }
                database.retweets.remove(tweet);
                database.allTweet.remove(tweet);
                return 34;
            }
        }
        return 1;
    }

    /**
     * @param user .
     * @return tweets that a user tweeted.
     */
    @Override
    public ArrayList<Tweet> getTweets(User user) {
        return database.tweets.get(user);
    }

    /**
     * @param tweet .
     * @return users that likes the tweet.
     */
    @Override
    public JSONArray getLikes(Tweet tweet) {
        JSONArray likes = new JSONArray();
        for (User i : database.likes.get(tweet)) {
            likes.put(i.getUsername());
        }
        return likes;
    }

    /**
     * @param tweet .
     * @return users that retweeted this tweet.
     */
    @Override
    public JSONArray getRetweets(Tweet tweet) {
        JSONArray retweets = new JSONArray();
        for (User i : database.retweets.get(tweet)) {
            retweets.put(i.getUsername());
        }
        return retweets;
    }

    /**
     * give a tweet and change its information to JSON format.
     *
     * @param tweet tweet.
     * @return information of tweet.
     */
    @Override
    public JSONObject getTweet(Tweet tweet) {
        JSONObject jTweet = new JSONObject();
        jTweet.put("tweetId", tweet.getId());
        jTweet.put("content", tweet.getContent());
        jTweet.put("username", tweet.getUser().getUsername());
        jTweet.put("creationDate", dateFormat.format(tweet.getAddingDate()));
        jTweet.put("Likes", getLikes(tweet));
        jTweet.put("retweets", getRetweets(tweet));
        return jTweet;
    }

    /**
     * get information and perform some action on it.
     *
     * @param information more information about action.
     * @param method      .
     * @param user        .
     * @return errors or response.
     */
    @Override
    public String run(User user, String method, JSONObject information) {
        if (method.equals("send-tweet")) {
            return response.responseCode(sendTweet(user, information.getString("content")), "sending-tweet");
        }
        Tweet tweet = findTweet(information.getInt("tweet-id"));
        if (tweet == null) {
            return response.error(11, "finding-tweet", null);
        }
        if (method.equals("like")) {
            return response.responseCode(like(user, tweet), "liking-tweet");
        }
        if (method.equals("unlike")) {
            return response.responseCode(unlike(user, tweet), "unliking-tweet");
        }
        if (!tweet.getUser().equals(user)) {
            return response.error(95, "not-permission", null);
        }
        return switch (method) {
            case "delete-tweet" -> response.responseCode(deleteTweet(tweet, user), "deleting-tweet");
            case "retweet" -> response.responseCode(retweet(user, tweet), "retweeting");
            default -> null;
        };
    }

    /**
     * @param id .
     * @return a tweet from database.
     */
    @Override
    public Tweet findTweet(int id) {
        for (Tweet i : database.allTweet) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    private int idMaker() {
        return database.getId();
    }
}
