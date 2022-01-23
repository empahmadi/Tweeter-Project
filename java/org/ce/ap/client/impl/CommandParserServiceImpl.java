package org.ce.ap.client.impl;

import org.ce.ap.client.services.CommandParserService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this class convert client request to
 * a readable format for server.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class CommandParserServiceImpl implements CommandParserService {
    private final ConnectionServiceImpl connectionService;

    /**
     * initialize connection service.
     *
     * @param cs connection service.
     */
    public CommandParserServiceImpl(ConnectionServiceImpl cs) {
        connectionService = cs;
    }

    /**
     * this methode make a request and pass it to server and return response of server.
     *
     * @param method     .
     * @param parameters .
     * @return .
     */
    @Override
    public String makeRequest(String method, JSONObject parameters) {
        JSONObject request = new JSONObject();
        request.put("method", method);
        request.put("parameters", parameters);
        String response = connectionService.connection(request.toString());
        try {
            JSONObject res = new JSONObject(response);
            return response;
        } catch (Exception e) {
            JSONArray params = new JSONArray();
            params.put(e.toString());
            JSONObject error = new JSONObject();
            error.put("hasError", true);
            error.put("errorType", "Json format error");
            error.put("errorCode", 51);
            error.put("params", params);
            return error.toString();
        }
    }

    /**
     * try to log in someone in his account.
     *
     * @param username .
     * @param password .
     * @return response of server.
     */
    @Override
    public String login(String username, String password) {
        JSONObject parameters = new JSONObject();
        parameters.put("username", username);
        parameters.put("password", password);
        return makeRequest("login", parameters);
    }

    /**
     * try to signup someone to server.
     *
     * @param dof      date of birth.
     * @param name     .
     * @param username .
     * @param bio      .
     * @param lastname .
     * @param password .
     * @return response of server.
     */
    @Override
    public String signup(String dof, String name, String lastname, String username, String password, String bio) {
        JSONObject parameters = new JSONObject();
        parameters.put("username", username);
        parameters.put("name", name);
        parameters.put("password", password);
        parameters.put("lastname", lastname);
        parameters.put("bio", bio);
        parameters.put("dof", dof);
        return makeRequest("signup", parameters);
    }

    /**
     * get main page components.
     *
     * @return response of server.
     */
    @Override
    public String main() {
        JSONObject params = new JSONObject();
        return makeRequest("timeline", params);
    }

    /**
     * get profile of username information.
     *
     * @param username .
     * @return response of server.
     */
    @Override
    public String profile(String username) {
        JSONObject params = new JSONObject();
        params.put("username", username);
        return makeRequest("profile", params);
    }

    /**
     * send a tweet.
     *
     * @param content content of tweet.
     * @return response of server.
     */
    @Override
    public String sendTweet(String content) {
        JSONObject parameter = new JSONObject();
        parameter.put("content", content);
        return makeRequest("send-tweet", parameter);
    }

    public String notifies() {
        JSONObject parameters = new JSONObject();
        return makeRequest("notifications", parameters);
    }

    /**
     * get a list of user with given type.
     *
     * @param type     .
     * @param username .
     * @return response of server.
     */
    @Override
    public String getList(String type, String username) {
        JSONObject parameter = new JSONObject();
        parameter.put("username", username);
        return makeRequest(type, parameter);
    }

    /**
     * do an action.
     *
     * @param type type of action.
     * @param id   .
     * @return response of server.
     */
    @Override
    public String tweetAction(String type, int id) {
        JSONObject params = new JSONObject();
        params.put("tweet-id", id);
        JSONObject response = new JSONObject(makeRequest(type, params));
        if (response.getBoolean("hasError")) {
            return response.getInt("errorCode") + "";
        }
        System.out.println(response.getJSONArray("result").get(0));
        return "empty";
    }

    /**
     * do an action on users.
     *
     * @param type     type of action.
     * @param username .
     * @return response of server.
     */
    @Override
    public String userAction(String type, String username) {
        JSONObject params = new JSONObject();
        params.put("username", username);
        JSONObject response = new JSONObject(makeRequest(type, params));
        if (response.getBoolean("hasError")) {
            return response.getInt("errorCode") + "";
        }
        System.out.println(response.getJSONArray("result").get(0));
        return "empty";
    }

    /**
     * get a tweet from server.
     *
     * @param id tweet id.
     * @return response of server.
     */
    @Override
    public String getTweet(int id, String username) {
        JSONObject params = new JSONObject();
        params.put("tweet-id", id);
        params.put("username", username);
        return makeRequest("get-tweet", params);
    }

    public void exit() {
        JSONObject params = new JSONObject();
        makeRequest("exit", params);
    }
}
