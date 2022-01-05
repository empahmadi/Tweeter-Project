package main.java.org.ce.ap.server.database;

import main.java.org.ce.ap.server.impl.FileIOImpl;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class acts as database.
 *
 * @author Eid Mohammad Ahmadi.
 * @version 1.0
 */
public class EMPDatabase {
    //singleton.
    private static EMPDatabase instance = null;
    private final FileIOImpl file;
    // main variables.
    public ArrayList<User> users;
    public ArrayList<Tweet> tweets;
    // auxiliary variables.
    // user dependent:
    public HashMap<User, ArrayList<Tweet>> userTweets;
    public HashMap<User, ArrayList<Tweet>> userRetweets;
    public HashMap<User, ArrayList<User>> followers;
    public HashMap<User, ArrayList<User>> follows;
    public HashMap<User, ArrayList<String>> notifications;
    public HashMap<User, ArrayList<Tweet>> userLikes;
    // tweet dependent:
    public HashMap<Tweet, User> tweetOwner;
    public HashMap<Tweet, ArrayList<User>> tweetLikes;
    public HashMap<Tweet, ArrayList<User>> tweetRetweets;
    private int id;

    /**
     * allocate memory for our maps and lists.
     */
    private EMPDatabase() {
        users = new ArrayList<>();
        userTweets = new HashMap<>();
        followers = new HashMap<>();
        follows = new HashMap<>();
        tweetLikes = new HashMap<>();
        tweetRetweets = new HashMap<>();
        notifications = new HashMap<>();
        tweets = new ArrayList<>();
        userRetweets = new HashMap<>();
        userLikes = new HashMap<>();
        file = new FileIOImpl();
        tweetOwner = new HashMap<>();
        initialize();
    }

    /**
     * creat a database.
     *
     * @return database.
     */
    public static EMPDatabase getInstance() {
        if (instance == null) {
            instance = new EMPDatabase();
        }
        return instance;
    }

    /**
     * making id for tweets.
     *
     * @return id.
     */
    public int getId() {
        id++;
        return id;
    }

    /**
     * this method will give information from files.
     */
    public void initialize() {
        users = file.getUsers();
        tweets = file.getTweets();
        System.out.println(setFollowers(new JSONObject(file.getMap("followers"))));
        System.out.println(setFollows(new JSONObject(file.getMap("follows"))));
        System.out.println(setNotifications(new JSONObject(file.getMap("notifications"))));
        System.out.println(setUserTweets(new JSONObject(file.getMap("user-tweets"))));
        System.out.println(setUserRetweets(new JSONObject(file.getMap("user-retweets"))));
        System.out.println(setUserLikes(new JSONObject(file.getMap("user-likes"))));
        System.out.println(setTweetLikes(new JSONObject(file.getMap("tweet-likes"))));
        System.out.println(setTweetRetweets(new JSONObject(file.getMap("tweet-retweets"))));
        System.out.println(setTweetOwner(new JSONObject(file.getMap("owner"))));
        int max = 1;
        for (Tweet i : tweets) {
            if (i.getId() > max) {
                max = i.getId();
            }
        }
        id = max;
    }

    /**
     * this method will give a backup from database.
     */
    public void backup() {
        System.out.println(file.setUsers(users));
        System.out.println(file.setTweets(tweets));
        System.out.println(file.setOwnerMap(tweetOwner));
        System.out.println(file.setUUMap(followers, "followers"));
        System.out.println(file.setUUMap(follows, "follows"));
        System.out.println(file.setUSMap(notifications, "notifications"));
        System.out.println(file.setUTMap(userTweets, "user-tweets"));
        System.out.println(file.setUTMap(userRetweets, "user-retweets"));
        System.out.println(file.setUTMap(userLikes, "user-likes"));
        System.out.println(file.setTUMap(tweetLikes, "tweet-likes"));
        System.out.println(file.setTUMap(tweetRetweets, "tweet-retweets"));
    }

    /**
     * maps followers.
     *
     * @param map .
     * @return response.
     */
    private String setFollowers(JSONObject map) {
        for (String username : map.keySet()) {
            User user = findUser(username);
            followers.put(user, new ArrayList<>());
            for (Object followers : map.getJSONArray(username)) {
                User follower = findUser((String) followers);
                this.followers.get(user).add(follower);
            }
        }
        return "success";
    }

    /**
     * maps follows.
     *
     * @param map .
     * @return response.
     */
    private String setFollows(JSONObject map) {
        for (String username : map.keySet()) {
            User user = findUser(username);
            follows.put(user, new ArrayList<>());
            for (Object follows : map.getJSONArray(username)) {
                User follow = findUser((String) follows);
                this.follows.get(user).add(follow);
            }
        }
        return "success";
    }

    /**
     * maps notifications.
     *
     * @param map .
     * @return response.
     */
    private String setNotifications(JSONObject map) {
        for (String username : map.keySet()) {
            User user = findUser(username);
            notifications.put(user, new ArrayList<>());
            for (Object notification : map.getJSONArray(username)) {
                this.notifications.get(user).add((String) notification);
            }
        }
        return "success";
    }

    /**
     * maps user tweets.
     *
     * @param map .
     * @return response.
     */
    private String setUserTweets(JSONObject map) {
        for (String username : map.keySet()) {
            User user = findUser(username);
            userTweets.put(user, new ArrayList<>());
            for (Object tweets : map.getJSONArray(username)) {
                Tweet tweet = findTweet((Integer) tweets);
                userTweets.get(user).add(tweet);
            }
        }
        return "success";
    }

    /**
     * maps user retweets.
     *
     * @param map .
     * @return response.
     */
    private String setUserRetweets(JSONObject map) {
        for (String username : map.keySet()) {
            User user = findUser(username);
            userRetweets.put(user, new ArrayList<>());
            for (Object tweets : map.getJSONArray(username)) {
                Tweet tweet = findTweet((Integer) tweets);
                userRetweets.get(user).add(tweet);
            }
        }
        return "success";
    }

    /**
     * maps user likes.
     *
     * @param map .
     * @return response.
     */
    private String setUserLikes(JSONObject map) {
        for (String username : map.keySet()) {
            User user = findUser(username);
            userLikes.put(user, new ArrayList<>());
            for (Object tweets : map.getJSONArray(username)) {
                Tweet tweet = findTweet((Integer) tweets);
                userLikes.get(user).add(tweet);
            }
        }
        return "success";
    }

    /**
     * maps tweet likes.
     *
     * @param map .
     * @return response.
     */
    private String setTweetLikes(JSONObject map) {
        int id;
        for (String ID : map.keySet()) {
            id = Integer.parseInt(ID);
            Tweet tweet = findTweet(id);
            tweetLikes.put(tweet, new ArrayList<>());
            for (Object users : map.getJSONArray(ID)) {
                User user = findUser((String) users);
                tweetLikes.get(tweet).add(user);
            }
        }
        return "success";
    }

    /**
     * maps tweet retweets.
     *
     * @param map .
     * @return response.
     */
    private String setTweetRetweets(JSONObject map) {
        int id;
        for (String ID : map.keySet()) {
            id = Integer.parseInt(ID);
            Tweet tweet = findTweet(id);
            tweetRetweets.put(tweet, new ArrayList<>());
            for (Object users : map.getJSONArray(ID)) {
                User user = findUser((String) users);
                tweetRetweets.get(tweet).add(user);
            }
        }
        return "success";
    }

    /**
     * maps tweet with user.
     *
     * @param map .
     * @return response.
     */
    private String setTweetOwner(JSONObject map) {
        for (String ID : map.keySet()) {
            Tweet tweet = findTweet(Integer.parseInt(ID));
            User user = findUser((String) map.get(ID));
            tweetOwner.put(tweet, user);
        }
        return "success";
    }

    /**
     * find a user from database by its username.
     *
     * @param username .
     * @return user.
     */
    public synchronized User findUser(String username) {
        for (User i : users) {
            if (username.equals(i.getUsername())) {
                return i;
            }
        }
        return null;
    }

    /**
     * find tweet from database by its id.
     *
     * @param id .
     * @return tweet.
     */
    public synchronized Tweet findTweet(int id) {
        for (Tweet i : tweets) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    /**
     * remove a user from database and file.
     *
     * @param user .
     */
    public void removeUser(User user) {
        for (User j : followers.get(user)) {
            follows.get(j).remove(user);
        }
        followers.remove(user);
        for (User j : follows.get(user)) {
            followers.get(j).remove(user);
        }
        for (Tweet i : userTweets.get(user)) {
            removeTweet(user, i);
        }
        follows.remove(user);
        for (Tweet i : userLikes.get(user)) {
            tweetLikes.get(i).remove(user);
        }
        for (Tweet i : userRetweets.get(user)) {
            tweetRetweets.get(i).remove(user);
        }
        users.remove(user);
        userTweets.remove(user);
        notifications.remove(user);
        userRetweets.remove(user);
        userLikes.remove(user);
        System.out.println(file.deleteUser(user.getUsername()));
    }

    /**
     * delete a tweet from a user from database and files.
     *
     * @param user  .
     * @param tweet .
     * @return state of deleting.
     */
    public String removeTweet(User user, Tweet tweet) {
        if (tweetOwner.get(tweet).equals(user)) {
            tweets.remove(tweet);
            userTweets.get(user).remove(tweet);
            userLikes.get(user).remove(tweet);
            for (User i : tweetRetweets.get(tweet)) {
                userRetweets.get(i).remove(tweet);
            }
            tweetLikes.remove(tweet);
            tweetRetweets.remove(tweet);
            tweetOwner.remove(tweet);
            return file.deleteTweet(tweet.getId());
        } else if (userRetweets.get(user).contains(tweet)) {
            userRetweets.get(user).remove(tweet);
            tweetRetweets.get(tweet).remove(user);
            return "deleted";
        } else {
            return "error";
        }
    }
}
