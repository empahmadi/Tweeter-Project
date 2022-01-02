package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.services.AuthenticationService;
import main.java.org.ce.ap.server.system.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class is for operation that related to user account like:
 * signup, sign in, delete account, change information, get profile etc.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    private final EMPDatabase database;
    private final Response response;
    private final TweetingServiceImpl ts;

    /**
     * this constructor connects this class to database.
     * and initialize some variables.
     *
     * @param database .
     */
    public AuthenticationServiceImpl(EMPDatabase database, TweetingServiceImpl ts) {
        this.database = database;
        response = new Response();
        this.ts = ts;
    }

    /**
     * signing in a user.
     *
     * @param information information about login.
     * @return code.
     */
    @Override
    public synchronized int login(JSONObject information) {
        User user = findUser(information.getString("username"));
        if (user == null) {
            return 1;
        }
        byte[] hash = getSHA(information.get("password").toString());
        if (hash == null) {
            return 3;
        }
        if (Arrays.equals(hash, user.getPassword())) {
            return 30;
        }
        return 1;
    }

    /**
     * signing up a user.
     *
     * @param in information about signup.
     * @return code.
     */
    @Override
    public synchronized int signup(JSONObject in) {
        byte[] hash = getSHA(in.getString("password"));
        if (hash == null) {
            return 1;
        }
        String date = in.getInt("year") + "-" + in.getInt("month") +
                "-" + in.getInt("day");
        User user = new User(in.getString("username"), hash,
                in.getString("name"), date);
        user.setLastname(in.getString("lastname"));
        user.setBiography(in.getString("bio"));
        addUser(user);
        return 30;
    }

    /**
     * remove a user.
     *
     * @param username user.
     * @return code.
     */
    @Override
    public synchronized int removeUser(String username) {
        User user = findUser(username);
        if (user == null)
            return 1;
        if (!database.users.contains(user))
            return 20;
        database.removeUser(user);
        return 0;
    }

    /**
     * add a user
     *
     * @param user .
     */
    @Override
    public void addUser(User user) {
        database.users.add(user);
        database.userTweets.put(user, new ArrayList<>());
        database.follows.put(user, new ArrayList<>());
        database.followers.put(user, new ArrayList<>());
        database.notifications.put(user, new ArrayList<>());
        database.userRetweets.put(user, new ArrayList<>());
        database.userLikes.put(user, new ArrayList<>());
    }

    /**
     * @param username .
     * @return a user with given username.
     */
    @Override
    public synchronized User findUser(String username) {
        for (User i : database.users) {
            if (username.equals(i.getUsername())) {
                return i;
            }
        }
        return null;
    }


    /**
     * check the validation of information that user entered.
     *
     * @param in information.
     * @return errors.
     */
    @Override
    public synchronized JSONArray checkInformation(JSONObject in) {
        JSONArray errors = new JSONArray();
        // username:
        if (in.getString("username").length() <= 1) {
            errors.put("Username must include at least 2 characters.");
        }
        if (in.getString("username").length() >= 81) {
            errors.put("Username must include maximum 80 characters.");
        }
        for (User i : database.users) {
            if (i.getUsername().equals(in.getString("username"))) {
                errors.put("Username already exist pleas identify a new username.");
            }
        }
        //name,password.
        if (in.getString("name").length() == 0) {
            errors.put("Name must include at least one character.");
        }
        if (in.getString("password").length() == 4) {
            errors.put("Password must include at least 4 character.");
        }
        //date of birth.
        if (in.getInt("year") >= 2020) {
            errors.put("You are so young for using this app");
        }
        if (in.getInt("year") <= 1900) {
            errors.put("You entered an invalid year(the year must be greater than 1900).");
        }
        if (in.getInt("month") > 12) {
            errors.put("You entered an invalid month.");
        }
        if (in.getInt("day") > 31) {
            errors.put("you are so young for using this app");
        }
        if (in.getString("lastname").length() == 0) {
            errors.put("lastname should have at least one character");
        }
        if (in.getString("bio").length() == 0) {
            errors.put("biography should have at least one character");
        }
        if (in.getString("bio").length() >= 256) {
            errors.put("lastname should have maximum 256 characters");
        }
        return errors;
    }

    /**
     * coding the password.
     *
     * @param input primary password.
     * @return coded password.
     */
    @Override
    public byte[] getSHA(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param username .
     * @return the profile information of user.
     */
    @Override
    public synchronized String getProfile(User main, String username) {
        int check = 0;
        User user = findUser(username);
        if (user == null) {
            return response.error(5, "showing-profile", null);
        }
        JSONArray result = new JSONArray();
        JSONObject profile = new JSONObject();
        JSONArray followers = new JSONArray();
        JSONArray follows = new JSONArray();
        for (User i : database.followers.get(user)) {
            followers.put(i.getUsername());
        }
        for (User i : database.follows.get(user)) {
            follows.put(i.getUsername());
        }
        profile.put("username", user.getUsername());
        profile.put("name", user.getName());
        profile.put("date-of-birth", user.getDateOfBirth());
        if (user.getLastname() != null) {
            profile.put("lastname", user.getLastname());
        } else {
            profile.put("lastname", "no set yet!!!");
            check++;
        }
        if (user.getBiography() != null) {
            profile.put("biography", user.getBiography());
        } else {
            profile.put("biography", "no-set-yet!!!");
            check++;
        }
        profile.put("profile-is-complete", check == 0);
        profile.put("followers", followers);
        profile.put("follows", follows);
        profile.put("follow-state", follows(main, user));
        result.put(profile);
        if (database.userTweets.get(user).size() != 0) {
            for (Tweet i : database.userTweets.get(user)) {
                result.put(ts.getTweet(user, i));
            }
        }
        return response.response(result.length(), result);
    }

    /**
     * check that a follow follows user or no.
     *
     * @param follow .
     * @param user   .
     * @return true if follows else false.
     */
    @Override
    public boolean follows(User follow, User user) {
        for (User i : database.followers.get(user)) {
            if (i.equals(follow)) {
                return true;
            }
        }
        return false;
    }

    public void update(){
        database.backup();
    }

    public synchronized String notifications(User user){
        JSONArray result = new JSONArray();
        for (int i = 0;i < database.notifications.get(user).size();i++){
            result.put(database.notifications.get(user).get(i));
        }
        return response.response(result.length(),result);
    }
}
