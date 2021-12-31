package main.java.org.ce.ap.server.system;

import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimeLineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this system manage user requests and return server response.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class TweeterSystem {
    private User user;
    private final AuthenticationServiceImpl au;
    private final TweetingServiceImpl ts;
    private final ObserverServiceImpl os;
    private final TimeLineServiceImpl update;
    private final Response response;
    private boolean isLogin;

    /**
     * initialize services and database.
     *
     * @param au  authentication service.
     * @param tls timeline service.
     * @param ts  tweeting service.
     * @param os  observer service.
     */
    public TweeterSystem(AuthenticationServiceImpl au, TimeLineServiceImpl tls,
                         TweetingServiceImpl ts, ObserverServiceImpl os) {
        this.au = au;
        this.ts = ts;
        this.os = os;
        response = new Response();
        isLogin = false;
        update = tls;
    }

    /**
     * get a request and pass to related service or methods.
     *
     * @param request .
     * @return the response of server in json format.
     */
    public String requestGetter(String request) {
        JSONObject jo = new JSONObject(request);
        JSONObject parameter = jo.getJSONObject("parameters");
        String method = jo.getString("method");
        if (!isLogin) {
            return firstRequest(jo);
        }
        switch (method) {
            case "timeline":
                return update.run(this.user);
            case "get-tweet":
            case "like":
            case "unlike":
            case "send-tweet":
            case "delete-tweet":
            case "retweet":
                return ts.run(this.user,method,parameter);
            case "follow":
            case "unfollow":
                return os.run(method, parameter, user);
            case "profile":
                return au.getProfile(parameter.getString("username"));
            default:
                return response.error(56, "request not understanding", null);
        }
    }

    /**
     * at first connection client most authenticated.
     * this method do this authentication.
     *
     * @param request user information.
     * @return response of server.
     */
    private String firstRequest(JSONObject request) {
        String method = request.getString("method");
        if (method.equals("login")) {
            return login(request.getJSONObject("parameters"));
        }
        if (method.equals("signup")) {
            return signup(request.getJSONObject("parameters"));
        }
        return response.error(8,"Authentication",null);
    }

    /**
     * this method is for login.
     *
     * @param information .
     * @return server response.
     */
    private String login(JSONObject information) {
        String username = information.getString("username");
        int value = au.login(information);
        if (value == 30) {
            user = au.findUser(username);
            isLogin = true;
            return response.responseCode(value,"login");
        }
        return response.error(value, "validation", null);
    }

    /**
     * this method is for signup.
     *
     * @param request information.
     * @return server response.
     */
    private String signup(JSONObject request) {
        JSONArray errors = au.checkInformation(request);
        if (errors.length() != 0) {
            return this.response.error(1, "invalidInput", errors);
        }
        String username = request.getString("username");
        int value = au.signup(request);
        if (value == 30) {
            user = au.findUser(username);
            isLogin = true;
            return response.responseCode(value,"signup");
        }
        return response.error(value, "serverError", null);
    }
}
