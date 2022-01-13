package org.ce.ap.client.impl;

import org.ce.ap.client.services.ConsoleViewService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

/**
 * this class convert response information in json format to
 * a readable format for client.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ConsoleViewServiceImpl implements ConsoleViewService {
    private final Scanner scan = new Scanner(System.in);
    private final CommandParserServiceImpl cps;
    private String username;

    /**
     * initialize command parser service.
     *
     * @param cps .
     */
    public ConsoleViewServiceImpl(CommandParserServiceImpl cps) {
        this.cps = cps;
    }

    /**
     * login or signup someone to system.
     *
     * @return code.
     */
    @Override
    public int loginPage() {
        String username, password, result;
        System.out.println("please fill this fields(if you don't have account in tweeter fill signup instead username):");
        System.out.print("username: ");
        username = scan.nextLine();
        if (username.equals("signup")) {
            return signup();
        }
        System.out.print("password: ");
        password = scan.nextLine();
        JSONObject response = new JSONObject(cps.login(username, password));
        if (response.getBoolean("hasError")) {
            parseError(response);
            return 0;
        }
        result = response.getJSONArray("result").getJSONObject(0).getString("response");
        System.out.println(result);
        this.username = username;
        return 1;
    }

    /**
     * show main page.
     *
     * @return code.
     */
    @Override
    public int main() {
        JSONObject response = new JSONObject(cps.main());
        JSONObject tweet;
        String command, search = "", userShow;
        int value = 1;
        while (true) {
            if (response.getBoolean("hasError")) {
                parseError(response);
                return 10;
            }
            System.out.printf("%-20s%-20s%-20s%-20s\n%-20s%-20s%-20s%-20s\n", "new_tweet", "followers", "follows", "update", "profile", "notifications", "search", "exit");
            for (int i = 0; i < response.getInt("count"); i++) {
                tweet = response.getJSONArray("result").getJSONObject(i);
                if (tweet.getBoolean("is-retweet")) {
                    userShow = tweet.getString("username") + " retweet from " + tweet.get("main");
                } else {
                    userShow = tweet.getString("username");
                }
                System.out.printf("%d)\n|%-30s%-30s%-20s|\n|%-256s|\n|%-12s%-6s%-15s\n\n", (i + 1), userShow,
                        " ", tweet.getString("creationDate"), tweet.getString("content"), "Likes(" + tweet.getJSONArray("likes").length() + ")",
                        " ", "retweets(" + tweet.getJSONArray("retweets").length() + ")");
            }
            System.out.println("Enter a command from top menu or enter the number of tweet.");
            System.out.print("command: ");
            command = scan.nextLine();
            if (command.equals("search")) {
                System.out.print("enter the username: ");
                search = scan.nextLine();
            }
            if (isNumeric(command)) {
                int index = Integer.parseInt(command);
                value = tweet(response.getJSONArray("result").getJSONObject(index - 1).getInt("tweet-id"),
                        response.getJSONArray("result").getJSONObject(index - 1).getString("username"));
            }
            if (command.equals("update")) {
                value = 5;
            } else {
                switch (command) {
                    case "new_tweet" -> value = creatTweet();
                    case "followers" -> value = list("get-followers", username);
                    case "follows" -> value = list("get-follows", username);
                    case "profile" -> value = profile(username);
                    case "notifications" -> notifies();
                    case "search" -> value = profile(search);
                    case "exit" -> {
                        exit();
                        return 0;
                    }
                    default -> System.out.println("please enter a valid command!!!");
                }
            }
            if (value == 5) {
                response = new JSONObject(cps.main());
            } else if (value == 10) {
                exit();
                return 0;
            }
        }
    }

    /**
     * show a list of anything that we need it to show as a list.
     *
     * @param type     type of list.
     * @param username .
     * @return code.
     */
    @Override
    public int list(String type, String username) {
        JSONObject response;
        JSONArray result;
        int size, i, value = 1, check = 0;
        String command;
        while (true) {
            response = new JSONObject(cps.getList(type, username));
            if (response.getBoolean("hasError")) {
                parseError(response);
                return 1;
            }
            result = response.getJSONArray("result");
            size = response.getInt("count");
            for (i = 0; i < size; i++) {
                System.out.println(result.getString(i));
            }
            System.out.printf("%-30s%-30s%-30s%-30s\n", "username", "back", "main", "exit");
            System.out.print("command: ");
            command = scan.nextLine();
            for (i = 0; i < size; i++) {
                if (command.equals(result.getString(i))) {
                    value = profile(command);
                    check = 10;
                }
            }
            switch (command) {
                case "back":
                    return 1;
                case "main":
                    return 5;
                case "exit":
                    return 10;
            }
            if (check == 0)
                System.out.println("'" + command + "' not specified!!!");
            else {
                if (value != 1)
                    return value;
            }
        }
    }

    /**
     * show notifications for user.
     */
    @Override
    public void notifies() {
        JSONObject response = new JSONObject(cps.notifies());
        JSONArray result = response.getJSONArray("result");
        for (int i = 0; i < response.getInt("count"); i++) {
            System.out.println(result.get(i));
        }
    }

    /**
     * show a list that its content is in client side.
     *
     * @param items items of list.
     * @return code.
     */
    @Override
    public int listView(JSONArray items) {
        String command;
        int value = 1, check = 0;
        while (true) {
            for (Object i : items) {
                System.out.println((String) i);
            }
            System.out.printf("%-30s%-30s%-30s%-30s\n", "username", "back", "main", "exit");
            System.out.print("command: ");
            command = scan.nextLine();
            switch (command) {
                case "back":
                    return 1;
                case "main":
                    return 5;
                case "exit":
                    return 10;
                default:
                    for (int i = 0; i < items.length(); i++) {
                        if (command.equals(items.getString(i))) {
                            value = profile(command);
                            check = 10;
                        }
                    }
                    break;
            }
            if (check == 0) {
                System.out.println("'" + command + "' not specified!!!");
            } else if (value != 1) {
                return value;
            }
        }
    }

    /**
     * this method will show a profile.
     *
     * @param username .
     * @return code.
     */
    @Override
    public int profile(String username) {
        JSONObject response, user, tweet;
        int size, i, value = 1, index;
        String command, res, userShow;
        while (true) {
            response = new JSONObject(cps.profile(username));
            if (response.getBoolean("hasError")) {
                parseError(response);
                return 1;
            }
            user = response.getJSONArray("result").getJSONObject(0);
            size = response.getInt("count");
            System.out.printf("Name: %-34sLastname: %-30sUsername: %-30s\nBio: %-256s\n", user.getString("name"),
                    user.getString("lastname"), user.getString("username"), user.getString("biography"));
            System.out.printf("%-60s%-60s\n", "followers(" + user.getJSONArray("followers").length() + ")",
                    "follows(" + user.getJSONArray("follows").length() + ")");
            if (!this.username.equals(user.getString("username"))) {
                if (user.getBoolean("follow-state")) {
                    System.out.printf("%-30s", "unfollow");
                } else {
                    System.out.printf("%-30s", "follow");
                }
            } else {
                System.out.printf("%-30s", "delete-account");
            }
            System.out.printf("%-30s%-30s%-30s\n", "back", "main", "exit");
            for (i = 1; i < size; i++) {
                tweet = response.getJSONArray("result").getJSONObject(i);
                if (tweet.getBoolean("is-retweet")) {
                    userShow = tweet.getString("username") + " retweet from " + tweet.get("main");
                } else {
                    userShow = tweet.getString("username");
                }
                System.out.printf("%d)\n|%-30s%-31s%-19s|\n|%-80s|\n|%-20s%-37s%-23s|\n\n", (i), userShow,
                        " ", tweet.getString("creationDate"), tweet.getString("content"), "Likes(" + tweet.getJSONArray("likes").length() + ")",
                        " ", "retweets(" + tweet.getJSONArray("retweets").length() + ")");
            }
            System.out.println("enter any command from top of profile or any number of tweet.");
            System.out.print("command: ");
            command = scan.nextLine();

            if (isNumeric(command)) {
                index = Integer.parseInt(command);
                if (index >= 0 && index <= size) {
                    value = tweet(response.getJSONArray("result").getJSONObject(index).getInt("tweet-id"),
                            response.getJSONArray("result").getJSONObject(index).getString("username"));
                } else {
                    System.out.println("'" + command + "' not specified!!!");
                }
            } else {
                if (command.equals("follow") || command.equals("unfollow")) {
                    if (!this.username.equals(user.getString("username"))) {
                        if (command.equals("follow")) {
                            if (!user.getBoolean("follow-state")) {
                                res = cps.userAction("follow", user.getString("username"));
                                if (isNumeric(res)) {
                                    parseErrorByCode(Integer.parseInt(res));
                                }
                            } else {
                                System.out.println("'" + command + "' not specified!!!");
                            }
                        } else {
                            if (!user.getBoolean("follow-state")) {
                                System.out.println("'" + command + "' not specified!!!");
                            } else {
                                res = cps.userAction("unfollow", user.getString("username"));
                                if (isNumeric(res)) {
                                    parseErrorByCode(Integer.parseInt(res));
                                }
                            }
                        }

                    } else {
                        System.out.println("'" + command + "' not specified!!!");
                    }
                } else if (command.equals("delete-account")) {
                    if (username.equals(user.getString("username"))) {
                        res = cps.userAction("delete-account", username);
                        if (isNumeric(res)) {
                            parseErrorByCode(Integer.parseInt(res));
                        }
                        return 10;
                    } else {
                        System.out.println("'" + command + "' not specified!!!");
                    }
                } else {
                    switch (command) {
                        case "followers":
                            value = list("get-followers", user.getString("username"));
                            break;
                        case "follows":
                            value = list("get-follows", user.getString("username"));
                            break;
                        case "notifications":
                        case "back":
                            return 1;
                        case "main":
                            return 5;
                        case "exit":
                            return 10;
                        default:
                            System.out.println("'" + command + "' not specified!!!");
                    }
                }
                if (value != 1) {
                    return value;
                }
            }
        }
    }

    /**
     * this method will show a tweet.
     *
     * @param id .
     * @return code.
     */
    @Override
    public int tweet(int id, String owner) {
        JSONObject tweet, response;
        String command, res, userShow;
        int value = 1;
        while (true) {
            response = new JSONObject(cps.getTweet(id, owner));
            tweet = response.getJSONArray("result").getJSONObject(0);
            if (tweet.getBoolean("is-retweet")) {
                userShow = tweet.getString("username") + " retweet from " + tweet.get("main");
            } else {
                userShow = tweet.getString("username");
            }
            System.out.printf("|%-30s%-30s%-20s|\n|%-256s|\n|%-12s%-6s%-15s\n\n", userShow,
                    " ", tweet.getString("creationDate"), tweet.getString("content"), "Likes(" + tweet.getJSONArray("likes").length() + ")",
                    " ", "retweets(" + tweet.getJSONArray("retweets").length() + ")");
            System.out.printf("%-30s%-30s%-30s%-30s\n", "retweet",
                    "likes", "retweets", tweet.getString("username"));
            if (!username.equals(tweet.getString("username"))) {
                if (tweet.getBoolean("like-state")) {
                    System.out.printf("%-30s", "unlike");
                } else {
                    System.out.printf("%-30s", "like");
                }
            } else {
                System.out.printf("%-30s", "delete-tweet");
            }
            System.out.printf("%-30s%-30s%-30s\n", "back", "main", "exit");
            System.out.print("command:");
            command = scan.nextLine();
            if (command.equals("like") || command.equals("unlike")) {
                if (!username.equals(tweet.getString("username"))) {
                    if (command.equals("like")) {
                        if (!tweet.getBoolean("like-state")) {
                            res = cps.tweetAction("like", id);
                            if (isNumeric(res)) {
                                parseErrorByCode(Integer.parseInt(res));
                            }
                        } else {
                            System.out.println("'" + command + "' not specified!!!");
                        }
                    } else {
                        if (!tweet.getBoolean("like-state")) {
                            System.out.println("'" + command + "' not specified!!!");
                        } else {
                            res = cps.tweetAction("unlike", id);
                            if (isNumeric(res)) {
                                parseErrorByCode(Integer.parseInt(res));
                            }
                        }
                    }

                } else {
                    System.out.println("'" + command + "' not specified!!!");
                }
            } else if (command.equals("delete-tweet")) {
                if (username.equals(tweet.getString("username"))) {
                    res = cps.tweetAction("delete-tweet", id);
                    if (isNumeric(res)) {
                        parseErrorByCode(Integer.parseInt(res));
                    }
                    return 1;
                } else {
                    System.out.println("'" + command + "' not specified!!!");
                }
            } else if (command.equals(tweet.getString("username"))) {
                value = profile(command);
            } else {
                switch (command) {
                    case "likes":
                        value = listView(tweet.getJSONArray("likes"));
                        break;
                    case "retweets":
                        value = listView(tweet.getJSONArray("retweets"));
                        break;
                    case "retweet":
                        res = cps.tweetAction(command, tweet.getInt("tweet-id"));
                        if (isNumeric(res)) {
                            parseErrorByCode(Integer.parseInt(res));
                        }
                        break;
                    case "back":
                        return 1;
                    case "main":
                        return 5;
                    case "exit":
                        return 10;
                    default:
                        System.out.println("'" + command + "' not specified!!!");
                }
            }
            if (value != 1) {
                return value;
            }
        }
    }

    /**
     * this method will sign up a user.
     *
     * @return code.
     */
    @Override
    public int signup() {
        String username, name, password, lastname, bio, date;
        int y, m, d;
        System.out.print("username: ");
        username = scan.nextLine();
        System.out.print("name: ");
        name = scan.nextLine();
        System.out.print("password: ");
        password = scan.nextLine();
        System.out.println("date of birth: ");
        System.out.print("year: ");
        date = scan.nextLine();
        y = Integer.parseInt(date);
        System.out.print("month: ");
        date = scan.nextLine();
        m = Integer.parseInt(date);
        System.out.print("day: ");
        date = scan.nextLine();
        d = Integer.parseInt(date);
        System.out.print("lastname: ");
        lastname = scan.nextLine();
        System.out.print("biography: ");
        bio = scan.nextLine();
        String res = cps.signup(y, m, d, name, lastname, username, password, bio);
        JSONObject response = new JSONObject(res);
        if (response.getBoolean("hasError")) {
            parseError(response);
            return 1;
        }
        String result = response.getJSONArray("result").getJSONObject(0).getString("response");
        this.username = username;
        return 1;
    }

    /**
     * creat a tweet.
     *
     * @return code.
     */
    @Override
    public int creatTweet() {
        String content;
        System.out.print("tweet text: ");
        content = scan.nextLine();
        JSONObject response = new JSONObject(cps.sendTweet(content));
        if (response.getBoolean("hasError")) {
            parseError(response);
            return 1;
        }
        System.out.println(response.getJSONArray("result").getJSONObject(0).getString("response"));
        return 1;
    }

    /**
     * this method is for exit.
     */
    @Override
    public void exit() {
        cps.exit();
    }

    /**
     * this method will show error that related to its code.
     *
     * @param error .
     */
    @Override
    public void parseError(JSONObject error) {
        System.out.println("an error occurred in "+error.getString("errorType"));
        parseErrorByCode(error.getInt("errorCode"));
        for (Object i: error.getJSONArray("params")){
            System.out.println((String)i);
        }
    }

    /**
     * this method will show error with given code.
     *
     * @param code error code.
     */
    @Override
    public void parseErrorByCode(int code) {
        switch (code) {
            case 1 -> System.out.println("unexpected error occurred!!!");
            case 2 -> System.out.println("your tweet is too long it must be under 256 characters!!!");
            case 3 -> System.out.println("you not entered anything!!!");
            case 11 -> System.out.println("tweet not found!!!");
            case 12 -> System.out.println("error occurred in deleting tweet!!!");
            case 13 -> System.out.println("you already like this tweet!!!");
            case 14 -> System.out.println("you don't like this tweet yet!!!");
            case 21 -> System.out.println("user not found!!!");
            case 22 -> System.out.println("password is incorrect!!!");
            case 23 -> System.out.println("you already follow this user!!!");
            case 24 -> System.out.println("you don't follow this user yet!!!");
            case 25 -> System.out.println("invalid input!!!");
        }
        System.out.println(code);
    }

    /**
     * checks that a string is a number or no.
     *
     * @param str .
     * @return true if string was a number else false.
     */
    public boolean isNumeric(String str) {
        try {
            int number = Integer.parseInt(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
