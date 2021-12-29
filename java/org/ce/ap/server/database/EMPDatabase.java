package main.java.org.ce.ap.server.database;

import main.java.org.ce.ap.server.file.FileIO;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;

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
    private final FileIO file;
    // main variables.
    public ArrayList<User> users;
    public ArrayList<Tweet> allTweet;
    // auxiliary variables.
    // user dependent:
    public HashMap<User, ArrayList<Tweet>> tweets;
    public HashMap<User, ArrayList<Tweet>> retweet;
    public HashMap<User, ArrayList<User>> followers;
    public HashMap<User, ArrayList<User>> follows;
    public HashMap<User, ArrayList<String>> notifications;
    // tweet dependent:
    public HashMap<Tweet, ArrayList<User>> likes;
    public HashMap<Tweet, ArrayList<User>> retweets;
    // other
    public HashMap<User, ArrayList<Tweet>> like;
    private int id;

    /**
     * allocate memory for our maps and lists.
     */
    private EMPDatabase() {
        users = new ArrayList<>();
        tweets = new HashMap<>();
        followers = new HashMap<>();
        follows = new HashMap<>();
        likes = new HashMap<>();
        retweets = new HashMap<>();
        notifications = new HashMap<>();
        allTweet = new ArrayList<>();
        retweet = new HashMap<>();
        like = new HashMap<>();
        id = 0;
        file = new FileIO();
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
        allTweet = file.getTweets();
    }
}
