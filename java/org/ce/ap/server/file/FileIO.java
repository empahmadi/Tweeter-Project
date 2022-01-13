package org.ce.ap.server.file;

import org.ce.ap.server.modules.Tweet;
import org.ce.ap.server.modules.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * this class is a framework for file io
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface FileIO {
    /**
     * backup from users.
     *
     * @param users .
     * @return response.
     */
    String setUsers(ArrayList<User> users);

    /**
     * delete a user from database.
     *
     * @param username .
     * @return success state.
     */
    String deleteUser(String username);

    /**
     * delete a tweet from database.
     *
     * @param id .
     * @return success state.
     */
    String deleteTweet(int id);

    /**
     * backup from tweets.
     *
     * @param tweets .
     * @return response.
     */
    String setTweets(ArrayList<Tweet> tweets);

    /**
     * backup from maps that is from tweet to user.
     *
     * @param tu   tweet to user.
     * @param type type of this map.
     * @return response.
     */
    String setTUMap(HashMap<Tweet, ArrayList<User>> tu, String type);

    /**
     * backup from maps that is from user to user.
     *
     * @param uu   user to user.
     * @param type type of this map.
     * @return response.
     */
    String setUUMap(HashMap<User, ArrayList<User>> uu, String type);

    /**
     * backup from maps that is from user to tweet.
     *
     * @param ut   user to user.
     * @param type type of this map.
     * @return response.
     */
    String setUTMap(HashMap<User, ArrayList<Tweet>> ut, String type);

    /**
     * backup from maps that is from user to String.
     *
     * @param us   user to String.
     * @param type type of this map.
     * @return response.
     */
    String setUSMap(HashMap<User, ArrayList<String>> us, String type);

    /**
     * this method give users from file and return it in an arraylist.
     *
     * @return users.
     */
    ArrayList<User> getUsers();

    /**
     * this method give tweets from files and return it in an arraylist.
     *
     * @return tweets
     */
    ArrayList<Tweet> getTweets();

    /**
     * get a mapping which related to type.
     *
     * @param type .
     * @return map.
     */
    String getMap(String type);

    /**
     * get files addresses.
     */
    String giveAddress(String name);

    /**
     * this method read object from a file.
     *
     * @param address address of file.
     * @return object.
     */
    Object reader(String address);

    /**
     * this method will save the map between tweet and user.
     * @param owner .
     * @return saving status
     */
    String setOwnerMap(HashMap<Tweet, User> owner);
}
