package org.ce.ap.server.impl;

import org.ce.ap.server.file.FileIO;
import org.ce.ap.server.modules.Tweet;
import org.ce.ap.server.modules.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * this class is for interaction with files.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class FileIOImpl implements FileIO {

    public FileIOImpl() {

    }

    /**
     * backup from users.
     *
     * @param users .
     * @return response.
     */
    @Override
    public String setUsers(ArrayList<User> users) {
        String path = giveAddress("server.users.file");
        if (path == null) {
            return "Error in finding file directory";
        }
        for (User i : users) {
            try (FileOutputStream file = new FileOutputStream(path + i.getUsername() + ".user");
                 ObjectOutputStream writer = new ObjectOutputStream(file)) {
                writer.writeObject(i);
            } catch (IOException ioe) {
                return ioe.toString();
            }
        }
        return "backup from users was successful";
    }

    /**
     * delete a user from database.
     *
     * @param username .
     * @return success state.
     */
    @Override
    public String deleteUser(String username) {
        String path = giveAddress("server.users.file");
        if (path != null) {
            File file = new File(path + username + ".user");
            if (file.delete()) {
                return "deleted";
            } else {
                return "error in deleting file!!!";
            }
        } else {
            return "error in finding file!!!";
        }
    }

    /**
     * delete a tweet from database.
     *
     * @param id .
     * @return success state.
     */
    @Override
    public String deleteTweet(int id) {
        String path = giveAddress("server.tweets.file");
        if (path != null) {
            File file = new File(path + id + ".tweet");
            if (file.delete()) {
                return "deleted";
            } else {
                return "error in deleting file!!!";
            }
        } else {
            return "error in finding file!!!";
        }
    }

    /**
     * backup from tweets.
     *
     * @param tweets .
     * @return response.
     */
    @Override
    public String setTweets(ArrayList<Tweet> tweets) {
        String path = giveAddress("server.tweets.file");
        if (path == null) {
            return "Error in finding file directory";
        }
        for (Tweet i : tweets) {
            try (FileOutputStream file = new FileOutputStream(path + i.getId() + ".tweet");
                 ObjectOutputStream writer = new ObjectOutputStream(file)) {
                writer.writeObject(i);
            } catch (IOException ioe) {
                return ioe.toString();
            }
        }
        return "backup from tweets was Successful";
    }
    /**
     * this method will save the map between tweet and user.
     * @param owner .
     * @return saving status
     */
    public String setOwnerMap(HashMap<Tweet, User> owner){
        String path = giveAddress("server.maps.file");
        if (path == null) {
            return "Error in finding file directory";
        }
        JSONObject map = new JSONObject();
        for (Tweet i : owner.keySet()) {
            map.put(i.getId()+"",owner.get(i).getUsername());
        }
        try (FileWriter file = new FileWriter(path + "owner.json")) {
            file.write(map.toString());
        } catch (IOException ioe) {
            return ioe.toString();
        }
        return "backup from tweet-user map was successful";
    }

    /**
     * backup from maps that is from tweet to user.
     *
     * @param tu   tweet to user.
     * @param type type of this map.
     * @return response.
     */
    @Override
    public String setTUMap(HashMap<Tweet, ArrayList<User>> tu, String type) {
        String path = giveAddress("server.maps.file");
        if (path == null) {
            return "Error in finding file directory";
        }
        JSONObject map = new JSONObject();
        JSONArray parameters;
        for (Tweet i : tu.keySet()) {
            parameters = new JSONArray();
            for (User j : tu.get(i)) {
                parameters.put(j.getUsername());
            }
            map.put(i.getId() + "", parameters);
        }
        try (FileWriter file = new FileWriter(path + type + ".json")) {
            file.write(map.toString());
        } catch (IOException ioe) {
            return ioe.toString();
        }
        return "backup from "+type+" was successful";
    }

    /**
     * backup from maps that is from user to user.
     *
     * @param uu   user to user.
     * @param type type of this map.
     * @return response.
     */
    @Override
    public String setUUMap(HashMap<User, ArrayList<User>> uu, String type) {
        String path = giveAddress("server.maps.file");
        if (path == null) {
            return "Error in finding file directory";
        }
        JSONObject map = new JSONObject();
        JSONArray parameters;
        for (User i : uu.keySet()) {
            parameters = new JSONArray();
            for (User j : uu.get(i)) {
                parameters.put(j.getUsername());
            }
            map.put(i.getUsername(), parameters);
        }
        try (FileWriter file = new FileWriter(path + type + ".json")) {
            file.write(map.toString());
        } catch (IOException ioe) {
            return ioe.toString();
        }
        return "successful";
    }

    /**
     * backup from maps that is from user to tweet.
     *
     * @param ut   user to user.
     * @param type type of this map.
     * @return response.
     */
    @Override
    public String setUTMap(HashMap<User, ArrayList<Tweet>> ut, String type) {
        String path = giveAddress("server.maps.file");
        if (path == null) {
            return "Error in finding file directory";
        }
        JSONObject map = new JSONObject();
        JSONArray parameters;
        for (User i : ut.keySet()) {
            parameters = new JSONArray();
            for (Tweet j : ut.get(i)) {
                parameters.put(j.getId());
            }
            map.put(i.getUsername(), parameters);
        }
        try (FileWriter file = new FileWriter(path + type + ".json")) {
            file.write(map.toString());
        } catch (IOException ioe) {
            return ioe.toString();
        }
        return "successful";
    }

    /**
     * backup from maps that is from user to String.
     *
     * @param us   user to String.
     * @param type type of this map.
     * @return response.
     */
    @Override
    public String setUSMap(HashMap<User, ArrayList<String>> us, String type) {
        String path = giveAddress("server.maps.file");
        if (path == null) {
            return "Error in finding file directory";
        }
        JSONObject map = new JSONObject();
        JSONArray parameters;
        for (User i : us.keySet()) {
            parameters = new JSONArray();
            for (String j : us.get(i)) {
                parameters.put(j);
            }
            map.put(i.getUsername() + "", parameters);
        }
        try (FileWriter file = new FileWriter(path + type + ".json")) {
            file.write(map.toString());
        } catch (IOException ioe) {
            return ioe.toString();
        }
        return "successful";
    }

    /**
     * this method give users from file and return it in an arraylist.
     *
     * @return users.
     */
    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        String path = giveAddress("server.users.file");
        if (path == null) {
            System.out.println("Problem in finding users path!!!");
            return null;
        }
        File folder = new File(path);
        File[] list = folder.listFiles();
        if (list == null) {
            System.out.println("Problem in finding users in its directory!!!");
            return null;
        }
        if (list.length != 0) {
            for (File i : list) {
                User user = (User) reader(i.toString());
                if (user == null) {
                    System.out.println("problem in reading user file!!!");
                    return null;
                }
                users.add(user);
            }
        }
        return users;
    }

    /**
     * this method give tweets from files and return it in an arraylist.
     *
     * @return tweets
     */
    @Override
    public ArrayList<Tweet> getTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();
        String path = giveAddress("server.tweets.file");
        if (path == null) {
            System.out.println("Problem in finding tweets path!!!");
            return null;
        }
        File folder = new File(path);
        File[] list = folder.listFiles();
        if (list == null) {
            System.out.println("Problem in finding tweets in its directory!!!");
            return null;
        }
        if (list.length != 0) {
            for (File i : list) {
                Tweet tweet = (Tweet) reader(i.toString());
                if (tweet == null) {
                    System.out.println("problem in reading tweet file!!!");
                    return null;
                }
                tweets.add(tweet);
            }
        }
        return tweets;
    }

    /**
     * get a mapping which related to type.
     *
     * @param type .
     * @return map.
     */
    @Override
    public String getMap(String type) {
        ArrayList<String[]> maps = new ArrayList<>();
        String path = giveAddress("server.maps.file");
        if (path == null) {
            System.out.println(type + "is invalid!!!");
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(path + type + ".json"))) {
            String line;
            StringBuilder json = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return json.toString();
        } catch (IOException ioe) {
            System.out.println("Error in reading map file " + path);
            System.out.println(ioe.toString());
            return null;
        }
    }

    /**
     * get files addresses.
     */
    @Override
    public String giveAddress(String name) {
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/server-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            return config.get(name).toString();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
            return null;
        }
    }

    /**
     * this method read object from a file.
     *
     * @param address address of file.
     * @return object.
     */
    @Override
    public Object reader(String address) {
        try (FileInputStream file = new FileInputStream(address);
             ObjectInputStream read = new ObjectInputStream(file)) {
            return read.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
