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
     * @param user .
     * @param id   .
     * @return error code.
     */
    @Override
    public synchronized String like(User user, int id) {
        Tweet tweet = findTweet(id);
        int code = 1;
        if (tweet != null) {
            for (User i : database.tweetLikes.get(tweet)) {
                if (i.equals(user)) {
                    code = 20;
                    break;
                }
            }
            if (code != 20) {
                database.tweetLikes.get(tweet).add(user);
                database.userLikes.get(user).add(tweet);
                code = 32;
            } else
                code = 1;
        }
        return response.responseCode(code, "liking tweet");
    }

    /**
     * unlike a tweet.
     *
     * @param user .
     * @param id   .
     * @return error code.
     */
    @Override
    public synchronized String unlike(User user, int id) {
        Tweet tweet = findTweet(id);
        int code = 1;
        if (tweet != null) {
            for (User i : database.tweetLikes.get(tweet)) {
                if (user.equals(i)) {
                    database.tweetLikes.get(tweet).remove(user);
                    database.userLikes.get(user).remove(tweet);
                    code = 33;
                    break;
                }
            }
        }
        return response.responseCode(code, "unliking tweet");
    }

    /**
     * send a tweet.
     *
     * @param user    .
     * @param content the content of tweet.
     * @return code.
     */
    @Override
    public synchronized String sendTweet(User user, String content) {
        int code;
        if (content.length() > 256)
            code = 3;
        else if (content.length() == 0)
            code = 4;
        else {
            Tweet tweet = new Tweet(content, idMaker());
            database.tweetRetweets.put(tweet, new ArrayList<>());
            database.userTweets.get(user).add(tweet);
            database.tweetLikes.put(tweet, new ArrayList<>());
            database.tweets.add(tweet);
            code = 31;
        }
        return response.responseCode(code, "sending tweet");
    }

    /**
     * retweet a tweet.
     *
     * @param user .
     * @param id   .
     */
    @Override
    public synchronized String retweet(User user, int id) {
        Tweet tweet = findTweet(id);
        if (tweet == null) {
            return response.error(1, "retweeting", null);
        }
        database.tweetRetweets.get(tweet).add(user);
        database.userTweets.get(user).add(tweet);
        database.userRetweets.get(user).add(tweet);
        return response.responseCode(38, "retweeting");
    }

    /**
     * delete a tweet.
     *
     * @param id   .
     * @param user .
     * @return error code.
     */
    @Override
    public synchronized String deleteTweet(int id, User user) {
        Tweet tweet = findTweet(id);
        int code = 1;
        if (tweet != null) {
            code = 34;
            database.removeTweet(user,tweet);
        }
        return response.responseCode(code, "deleting tweet");
    }

    /**
     * @param user .
     * @return tweets that a user tweeted.
     */
    @Override
    public ArrayList<Tweet> getTweets(User user) {
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
     * @param user  user that wants this tweet.
     * @return information of tweet.
     */
    @Override
    public synchronized JSONObject getTweet(User user, Tweet tweet) {
        JSONObject jTweet = new JSONObject();
        jTweet.put("tweet-id", tweet.getId());
        jTweet.put("content", tweet.getContent());
        jTweet.put("creationDate", dateFormat.format(tweet.getAddingDate()));
        jTweet.put("likes", getLikes(tweet));
        jTweet.put("retweets", getRetweets(tweet));
        jTweet.put("like-state", likes(tweet, user));
        for (User j : database.userTweets.keySet()) {
            for (Tweet i : database.userTweets.get(j)) {
                if (i.equals(tweet)) {
                    jTweet.put("username", j.getUsername());
                    return jTweet;
                }
            }
        }

        jTweet.put("username", "error");
        return jTweet;
    }

    /**
     * this method get a tweet by its id.
     *
     * @param id .
     * @return tweet in json format.
     */
    @Override
    public synchronized String getTweetByID(User user, int id) {
        Tweet tweet = findTweet(id);
        if (tweet == null) {
            return response.error(1, "finding tweet", null);
        }
        JSONArray result = new JSONArray();
        result.put(getTweet(user, tweet));
        return response.response(1, result);
    }

    /**
     * this method get users that retweet a tweet.
     *
     * @param id .
     * @return response of server in json format.
     */
    @Override
    public synchronized String getJRetweets(int id) {
        Tweet tweet = findTweet(id);
        if (tweet == null) {
            return response.error(1, "finding-tweet", null);
        }
        JSONArray result = getRetweets(tweet);
        return response.response(result.length(), result);
    }

    /**
     * this method get users that likes a tweet.
     *
     * @param id .
     * @return response of server in json format.
     */
    @Override
    public synchronized String getJLikes(int id) {
        Tweet tweet = findTweet(id);
        if (tweet == null) {
            return response.error(1, "finding-tweet", null);
        }
        JSONArray result = getLikes(tweet);
        return response.response(result.length(), result);
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

    /**
     * check that user likes this tweet or no.
     *
     * @param tweet .
     * @param user  .
     * @return true if likes else false.
     */
    @Override
    public boolean likes(Tweet tweet, User user) {
        for (User i : database.tweetLikes.get(tweet)) {
            if (user.equals(i)) {
                return true;
            }
        }
        return false;
    }
}
