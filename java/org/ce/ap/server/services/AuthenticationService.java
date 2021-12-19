package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class AuthenticationService {
    private final EMPDatabase database;

    public AuthenticationService(EMPDatabase database) {
        this.database = database;
    }

    /**
     * signing in a user.
     *
     * @param information information about login.
     * @return code.
     */
    public int login(JSONObject information) {
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
    public int signup(JSONObject in) {
        byte[] hash = getSHA(in.getString("password"));
        if (hash == null) {
            return 1;
        }
        User user = new User(in.getString("username"), hash,
                in.getString("name"), in.getString("dateOfBirth"));
        addUser(user);
        return 31;
    }

    /**
     * remove a user.
     *
     * @param user user.
     * @return code.
     */
    public int removeUser(User user) {
        if (user == null)
            return 1;
        if (!database.users.contains(user))
            return 20;
        for (User j : database.followers.get(user)) {
            database.follows.get(j).remove(user);
        }
        database.followers.remove(user);
        for (User j : database.follows.get(user)) {
            database.followers.get(j).remove(user);
        }
        database.follows.remove(user);
        database.users.remove(user);
        database.tweets.remove(user);
        database.notifications.remove(user);
        return 0;
    }

    /**
     * add a user
     *
     * @param user .
     */
    private void addUser(User user) {
        database.users.add(user);
        database.tweets.put(user, new ArrayList<>());
        database.follows.put(user, new ArrayList<>());
        database.followers.put(user, new ArrayList<>());
        database.notifications.put(user, new ArrayList<>());
    }

    /**
     * @param username .
     * @return a user with given username.
     */
    public User findUser(String username) {
        for (User i : database.users) {
            if (username.equals(i.getUsername())) {
                return i;
            }
        }
        return null;
    }

    /**
     * change the information about user.
     * is in working.
     *
     * @param in information.
     * @return code.
     */
    public int changeInformation(JSONObject in) {
        return 0;
    }

    /**
     * check the validation of information that user entered.
     *
     * @param in information.
     * @return errors.
     */
    public JSONArray checkInformation(JSONObject in) {
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
        if (in.getJSONArray("dateOfBirth").getInt(0) >= 2020) {
            errors.put("You are so young for using this app");
        }
        if (in.getJSONArray("dateOfBirth").getInt(0) <= 1900) {
            errors.put("You entered an invalid year(the year must be greater than 1900).");
        }
        if (in.getJSONArray("dateOfBirth").getInt(1) > 12) {
            errors.put("You entered an invalid month.");
        }
        if (in.getJSONArray("dateOfBirth").getInt(2) > 31) {
            errors.put("you are so young for using this app");
        }
        //unnecessary.
        return errors;
    }

    /**
     * coding the password.
     *
     * @param input primary password.
     * @return coded password.
     */
    private static byte[] getSHA(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * is in working...
     *
     * @param user .
     * @return the profile information of user.
     */
    public JSONObject getProfile(User user) {
        System.out.print("folk");
        return null;
    }
}
