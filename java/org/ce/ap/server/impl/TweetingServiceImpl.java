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
    public synchronized int like(User user, Tweet tweet) {
        for (User i : database.tweetLikes.get(tweet)) {
            if (i.equals(user)) {
                return 1;
            }
        }
        database.tweetLikes.get(tweet).add(user);
        database.userLikes.get(user).add(tweet);
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
    public synchronized int unlike(User user, Tweet tweet) {
        for (User i : database.tweetLikes.get(tweet)) {
            if (user.equals(i)) {
                database.tweetLikes.get(tweet).remove(user);
                database.userLikes.get(user).remove(tweet);
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
    public synchronized int sendTweet(User user, String content) {
        if (content.length() > 256)
            return 3;
        if (content.length() == 0)
            return 4;
        Tweet tweet = new Tweet(content, idMaker());
        database.tweetRetweets.put(tweet, new ArrayList<>());
        database.userTweets.get(user).add(tweet);
        database.tweetLikes.put(tweet, new ArrayList<>());
        database.tweets.add(tweet);
        return 31;
    }

    /**
     * retweet a tweet.
     *
     * @param user  .
     * @param tweet .
     */
    @Override
    public synchronized int retweet(User user, Tweet tweet) {
        database.tweetRetweets.get(tweet).add(user);
        database.userTweets.get(user).add(tweet);
        database.userRetweets.get(user).add(tweet);
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
    public synchronized int deleteTweet(Tweet tweet, User user) {
        for (Tweet i : database.userTweets.get(user)) {
            if (tweet.equals(i)) {
                database.userTweets.get(user).remove(tweet);
                database.tweetLikes.remove(tweet);
                for (User j : database.tweetRetweets.get(tweet)) {
                    database.userTweets.get(j).remove(tweet);
                }
                database.tweetRetweets.remove(tweet);
                database.tweets.remove(tweet);
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
    public synchronized ArrayList<Tweet> getTweets(User user) {
        return database.userTweets.get(user);
    }

    /**
     * @param tweet .
     * @return users that likes the tweet.
     */
    @Override
    public synchronized JSONArray getLikes(Tweet tweet) {
        JSONArray likes = new JSONArray();
        for (User i : database.tweetLikes.get(tweet)) {
            likes.put(i.getUsername());
        }
        return likes;
    }

    /**
     * @param tweet .
     * @return users that retweeted this tweet.
     */
    @Override
    public synchronized JSONArray getRetweets(Tweet tweet) {
        JSONArray retweets = new JSONArray();
        for (User i : database.tweetRetweets.get(tweet)) {
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
    public synchronized JSONObject getTweet(Tweet tweet) {
        JSONObject jTweet = new JSONObject();

        jTweet.put("tweetId", tweet.getId());
        jTweet.put("content", tweet.getContent());
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
    public synchronized String run(User user, String method, JSONObject information) {
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
    public synchronized Tweet findTweet(int id) {
        for (Tweet i : database.tweets) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    private synchronized int idMaker() {
        return database.getId();
    }
}
