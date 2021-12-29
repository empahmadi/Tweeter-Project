package main.java.org.ce.ap.server.file;

import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;

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
public class FileIO {

    public FileIO(){
    }


    /**
     * this method give users from file and return it in an arraylist.
     * @return users.
     */
    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        String path = giveAddress("server.users.file");
        if (path == null){
            System.out.println("Problem in finding users path!!!");
            return null;
        }
        File folder = new File(path);
        File[] list = folder.listFiles();
        if(list == null){
            System.out.println("Problem in finding users in its directory!!!");
            return null;
        }
        if (list.length != 0) {
            for (File i : list) {
                User user = (User)reader(i.toString());
                if (user == null){
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
     * @return tweets
     */
    public ArrayList<Tweet> getTweets(){
        ArrayList<Tweet> tweets = new ArrayList<>();
        String path = giveAddress("server.tweets.file");
        if (path == null){
            System.out.println("Problem in finding tweets path!!!");
            return null;
        }
        File folder = new File(path);
        File[] list = folder.listFiles();
        if(list == null){
            System.out.println("Problem in finding tweets in its directory!!!");
            return null;
        }
        if (list.length != 0) {
            for (File i : list) {
                Tweet tweet= (Tweet)reader(i.toString());
                if (tweet == null){
                    System.out.println("problem in reading tweet file!!!");
                    return null;
                }
                tweets.add(tweet);
            }
        }
        return tweets;
    }

    /**
     * update the addresses of files.
     */
    private String giveAddress(String name) {
        try(FileInputStream file = new FileInputStream("../resources/server-application.properties")){
            Properties config = new Properties();
            config.load(file);
            return config.get(name).toString();
        } catch (IOException ioe){
            System.out.println(ioe.toString());
            return null;
        }
    }

    /**
     * this method read object from a file.
     * @param address address of file.
     * @return object.
     */
    private Object reader(String address){
        try (FileInputStream file = new FileInputStream(address);
             ObjectInputStream read = new ObjectInputStream(file)) {
            return read.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
