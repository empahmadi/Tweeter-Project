package main.java.org.ce.ap.server.system;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.services.AuthenticationService;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.services.TimeLineService;
import main.java.org.ce.ap.server.services.TweetingService;
import org.json.JSONArray;
import org.json.JSONObject;

public class TweeterSystem {
    private User user;
    private final AuthenticationService au;
    private final TweetingService ts;
    private final ObserverService os;
    private final TimeLineService update;
    private Response response;
    private boolean isLogin;

    public TweeterSystem(EMPDatabase database){
        au = new AuthenticationService(database);
        ts = new TweetingService(database);
        os = new ObserverService(database);
        response = new Response();
        isLogin = false;
        update = new TimeLineService(database);
    }

    public String requestGetter(String request){
        JSONObject jo = new JSONObject(request);
        JSONObject parameter = jo.getJSONObject("ParameterValues");
        String method = jo.getString("methode");
        if (!isLogin){
            return firstRequest(parameter);
        }
        switch (method){
            case "timeline":
                return update.run(this.user);
            default:
                return response.error(56,"request not understanding",null);
        }

    }

    private String firstRequest(JSONObject request){
        String method = request.getString("methode");
        if (method.equals("login")){
            return login(request.getJSONObject("information"));
        }
        if (method.equals("signup")){
            return signup(request.getJSONObject("information"));
        }
        return null;
    }

    private String login(JSONObject information){
        String username = information.getString("username");
        int value = au.login(information);
        if (value == 30){
            user = au.findUser(username);
            isLogin = true;
        }
        return response.error(value,"validation",null);
    }
    private String signup(JSONObject request){
        JSONArray errors = au.checkInformation(request);
        if (errors.length() != 0){
            return this.response.error(1,"invalidInput",errors);
        }
        String username = request.getString("username");
        int value = au.signup(request);
        if (value == 31){
            user = au.findUser(username);
            isLogin = true;
        }
        return response.error(value,"serverError",null);
    }
}
