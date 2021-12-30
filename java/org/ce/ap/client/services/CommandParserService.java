package main.java.org.ce.ap.client.services;

import org.json.JSONObject;

/**
 * this interface is a framework for command parser service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface CommandParserService {
    /**
     * this methode make a request and pass it to server and return response of server.
     * @param method .
     * @param parameters .
     * @return .
     */
    String makeRequest(String method, JSONObject parameters);

    /**
     * try to log in someone in his account.
     * @param username .
     * @param password .
     * @return response of server.
     */
    String login(String username, String password);

    /**
     * try to signup someone to server.
     * @param y year of birth.
     * @param m month of birth.
     * @param d day of birth.
     * @param information information about sing up.
     * @return response of server.
     */
    String signup(int y,int m, int d,String... information);
}
