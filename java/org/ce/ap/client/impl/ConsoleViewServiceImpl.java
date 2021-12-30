package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.services.CommandParserService;
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

    /**
     * initialize command parser service.
     *
     * @param cps
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
     * this method get information and destination page and send information to that page.
     *
     * @param information
     * @param page
     * @return code .
     */
    @Override
    public int consoleSys(String information, String page) {
        return 0;
    }

    /**
     * this method is for single response such
     *
     * @param text
     */
    @Override
    public void singleRes(String text) {

    }

    /**
     * login or signup someone to system.
     *
     * @return code.
     */
    @Override
    public int loginPage() {
        String username, password, result;
        System.out.print("please fill this fields(if you don't have account in tweeter fill signup instead username):");
        System.out.print("username: ");
        username = scan.nextLine();
        if (username.equals("signup")) {
            return signup();
        }
        System.out.print("password: ");
        password = scan.nextLine();
        JSONObject response = new JSONObject(cps.login(username, password));
        if (response.getBoolean("hasError")) {
            parseError(response.getInt("errorCode"));
            for (Object i : response.getJSONArray("params")) {
                System.out.println((String) i);
            }
            return 0;
        }
        result = response.getJSONArray("result").getJSONObject(0).getString("response");
        System.out.println(result);
        return 1;
    }

    /**
     * show main page.
     *
     * @return code.
     */
    @Override
    public int main() {
        return 0;
    }

    /**
     * show a list of anything that we need it to show as a list.
     *
     * @return code.
     */
    @Override
    public int list() {
        return 0;
    }

    /**
     * this method will show a profile.
     *
     * @return code.
     */
    @Override
    public int profile() {
        return 0;
    }

    /**
     * this method will show a tweet.
     *
     * @return code.
     */
    @Override
    public int tweet() {
        return 0;
    }

    /**
     * this method will sign up a user.
     *
     * @return code.
     */
    @Override
    public int signup() {
        String username, name, password, lastname = "", bio = "";
        int y, m, d;
        boolean skip;
        System.out.print("username: ");
        username = scan.nextLine();
        System.out.print("name: ");
        name = scan.nextLine();
        System.out.print("password: ");
        password = scan.nextLine();
        System.out.print("date of birth(yyyy mm dd): ");
        y = scan.nextInt();
        m = scan.nextInt();
        d = scan.nextInt();
        System.out.print("do you want to complete your profile now(yes or no): ");
        skip = scan.nextLine().equals("yes");
        if (skip) {
            System.out.print("lastname: ");
            lastname = scan.nextLine();
            System.out.print("biography: ");
            bio = scan.nextLine();
        }
        JSONObject response = new JSONObject(cps.signup(y, m, d, username, name, password, lastname, bio));
        if (response.getBoolean("hasError")) {
            parseError(response.getInt("errorCode"));
            for (Object i : response.getJSONArray("params")) {
                System.out.println((String) i);
            }
            return 0;
        }
        String result = response.getJSONArray("result").getJSONObject(0).getString("response");
        System.out.println(result);
        return 1;
    }

    /**
     * this method will show error that related to its code.
     *
     * @param code .
     */
    @Override
    public void parseError(int code) {

    }


}
