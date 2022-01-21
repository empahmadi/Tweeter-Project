package org.ce.ap.client.services;

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
     * @param dof date of birth.
     * @param name .
     * @param username .
     * @param bio .
     * @param lastname .
     * @param password .
     * @return response of server.
     */
    String signup(String dof, String name,String lastname,String username,String password,String bio);

    /**
     * get main page components.
     * @return response of server.
     */
    String main();

    /**
     * get profile of username information.
     * @param username .
     * @return response of server.
     */
    String profile(String username);

    /**
     * send a tweet.
     * @param content content of tweet.
     * @return response of server.
     */
    String sendTweet(String content);

    /**
     * get a list of user with given type.
     * @param type .
     * @param username .
     * @return response of server.
     */
    String getList(String type, String username);

    /**
     * do an action on tweets.
     * @param type type of action.
     * @param id  .
     * @return response of server.
     */
    String tweetAction(String type,int id);

    /**
     * do an action on users.
     * @param type type of action.
     * @param username  .
     * @return response of server.
     */
    String userAction(String type,String username);

    /**
     * get a tweet from server.
     * @param id tweet id.
     * @return response of server.
     */
    String getTweet(int id, String username);
}
