package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.services.ConsoleViewService;
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
     * convert server response to a specific format.
     *
     * @param response .
     * @param part     .
     * @return .
     */
    public String response(String response, String part) {
        JSONObject res = new JSONObject(response);
        return res.getString(part);
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
        String command,search = "";
        int value = 1;
        while (true) {
            if (response.getBoolean("hasError")) {
                parseError(response);
                return 10;
            }
            System.out.printf("%-20s%-20s%-20s%-20s\n%-20s%-20s%-20s%-20s\n", "new_tweet", "followers", "follows", "update", "profile", "notifications", "search", "exit");
            for (int i = 0; i < response.getInt("count"); i++) {
                tweet = response.getJSONArray("result").getJSONObject(i);
                System.out.printf("%d)\n|%-30s%-30s%-20s|\n|%-256s|\n|%-12s%-6s%-15s\n\n", (i + 1), tweet.getString("username"),
                        " ", tweet.getString("creationDate"), tweet.getString("content"), "Likes(" + tweet.getJSONArray("likes").length() + ")",
                        " ", "retweets(" + tweet.getJSONArray("retweets").length() + ")");
            }
            System.out.println("Enter a command from top menu or enter the number of tweet.");
            System.out.print("command: ");
            command = scan.nextLine();
            if(command.equals("search")){
                System.out.print("enter the username: ");
                search = scan.nextLine();
            }
            if (isNumeric(command)) {
                int index = Integer.parseInt(command);
                value = tweet(response.getJSONArray("result").getJSONObject(index - 1).getInt("tweet-id"));
            }if(command.equals("update")){
                value = 5;
            }else {
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
            System.out.print("username: ");
            command = scan.nextLine();
            for (i = 0; i < size; i++) {
                if (command.equals(result.getString(i))) {
                    value = profile(command);
                    check = 10;
                }
            }
            if (check == 0)
                System.out.println("'" + command + "' not specified!!!");
            else {
                if (value != 1)
                    return value;
            }
        }
    }

    public void notifies(){
        JSONObject response = new JSONObject(cps.notifies());
        JSONArray result = response.getJSONArray("result");
        for (int i= 0;i < response.getInt("count");i++){
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
        int value = 1;
        while (true) {
            for (Object i : items) {
                System.out.println((String) i);
            }
            System.out.printf("%-30s%-30s%-30s", "username", "back", "main");
            System.out.print("Enter a username: ");
            command = scan.nextLine();
            if (command.equals("back")) {
                return 1;
            } else if (command.equals("main")) {
                return 5;
            } else {
                value = profile(command);
            }
            if (value != 1) {
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
        String command, res;
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
                System.out.printf("%d)\n|%-30s%-31s%-19s|\n|%-80s|\n|%-20s%-37s%-23s|\n\n", (i), tweet.getString("username"),
                        " ", tweet.getString("creationDate"), tweet.getString("content"), "Likes(" + tweet.getJSONArray("likes").length() + ")",
                        " ", "retweets(" + tweet.getJSONArray("retweets").length() + ")");
            }
            System.out.println("enter any command from top of profile or any number of tweet.");
            System.out.print("command: ");
            command = scan.nextLine();

            if (isNumeric(command)) {
                index = Integer.parseInt(command);
                if (index >= 0 && index <= size) {
                    value = tweet(response.getJSONArray("result").getJSONObject(index).getInt("tweet-id"));
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
    public int tweet(int id) {
        JSONObject tweet, response;
        String command, res;
        int value = 1;
        while (true) {
            response = new JSONObject(cps.getTweet(id));
            tweet = response.getJSONArray("result").getJSONObject(0);
            System.out.printf("|%-30s%-30s%-20s|\n|%-256s|\n|%-12s%-6s%-15s\n\n", tweet.getString("username"),
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
                    case "retweets":
                        value = list(command, tweet.getString("username"));
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
        String username, name, password, lastname, bio,date;
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
        String res = cps.signup(y,m,d,name,lastname,username,password,bio);
        JSONObject response = new JSONObject(res);
        if (response.getBoolean("hasError")) {
            parseError(response);
            return 1;
        }
        String result = response.getJSONArray("result").getJSONObject(0).getString("response");
        System.out.println(result);
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
        System.out.println("error occurred");
    }

    /**
     * this method will show error with given code.
     *
     * @param code error code.
     */
    @Override
    public void parseErrorByCode(int code) {
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
