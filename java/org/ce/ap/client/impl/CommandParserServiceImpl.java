package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.services.CommandParserService;
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
        try{
            JSONObject res = new JSONObject(response);
            return connectionService.connection(request.toString());
        }catch (Exception e){
            System.out.println(e.toString());
            return "10";
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
     * @param y           year of birth.
     * @param m           month of birth.
     * @param d           day of birth.
     * @param information information about sing up.
     * @return response of server.
     */
    @Override
    public String signup(int y, int m, int d, String... information) {
        JSONObject parameters = new JSONObject();
        JSONArray dof = new JSONArray();
        dof.put(y);
        dof.put(m);
        dof.put(d);
        parameters.put("username", information[0]);
        parameters.put("name", information[1]);
        parameters.put("password", information[2]);
        parameters.put("date-of-birth", parameters);
        if (information.length > 3) {
            parameters.put("lastname", information[3]);
            parameters.put("biography", information[4]);
        }
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
        return  makeRequest("timeline",params);
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
        params.put("username",username);
        return makeRequest("profile",params);
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
        parameter.put("content",content);
        return makeRequest("send-tweet",parameter);
    }

    /**
     * get a list of user with given type.
     *
     * @param type .
     * @param username .
     * @return response of server.
     */
    @Override
    public String getList(String type, String username) {
        JSONObject parameter = new JSONObject();
        parameter.put("username",username);
        return makeRequest(type,parameter);
    }

    /**
     * do an action.
     *
     * @param type type of action.
     * @param id .
     * @return response of server.
     */
    @Override
    public String tweetAction(String type, int id) {
        JSONObject params = new JSONObject();
        params.put("tweet-id",id);
        JSONObject response = new JSONObject(makeRequest(type,params));
        if (response.getBoolean("hasError")){
            return response.getInt("errorCode")+"";
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
        params.put("username",username);
        JSONObject response = new JSONObject(makeRequest(type,params));
        if (response.getBoolean("hasError")){
            return response.getInt("errorCode")+"";
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
    public String getTweet(int id) {
        JSONObject params = new JSONObject();
        params.put("tweet-id",id);
        return makeRequest("get-tweet",params);
    }
}