package main.java.org.ce.ap.server.database;

import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;

import java.util.ArrayList;
import java.util.HashMap;

public class EMPDatabase {
    //singleton.
    private static EMPDatabase instance = null;
    // main variables.
    public ArrayList<User> users;
    public ArrayList<Tweet> allTweet;
    // auxiliary variables.
    public HashMap<User,ArrayList<Tweet>> tweets;
    public HashMap<User,ArrayList<User>> followers;
    public HashMap<User,ArrayList<User>> follows;
    public HashMap<Tweet,ArrayList<User>> likes;
    public HashMap<Tweet,ArrayList<User>> retweets;
    public HashMap<User,ArrayList<String>> notifications;
    private int id;

    private EMPDatabase(){
        users = new ArrayList<>();
        tweets = new HashMap<>();
        followers = new HashMap<>();
        follows = new HashMap<>();
        likes = new HashMap<>();
        retweets = new HashMap<>();
        notifications = new HashMap<>();
        allTweet = new ArrayList<>();
        id = 0;
    }

    public static EMPDatabase getInstance(){
        if (instance == null){
            instance = new EMPDatabase();
        }
        return instance;
    }

    public int getId(){
        id++;
        return id;
    }
}
