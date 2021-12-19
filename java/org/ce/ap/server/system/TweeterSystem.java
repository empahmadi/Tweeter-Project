package main.java.org.ce.ap.server.system;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.services.AuthenticationService;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.services.TweetingService;
import org.json.JSONObject;

import java.util.ArrayList;

public class TweeterSystem {
    private User user;
    private final AuthenticationService au;
    private final TweetingService ts;
    private final ObserverService os;
    private Error error;
    private ResponseMaker rm;
    private boolean isLogin;

    public TweeterSystem(EMPDatabase database){
        au = new AuthenticationService(database);
        ts = new TweetingService(database);
        os = new ObserverService(database);
        error = new Error();
        rm = new ResponseMaker();
        isLogin = false;
    }

    public String requestGetter(String request){
        JSONObject jo = new JSONObject(request);
        String method = jo.getString("methode");
        if (!isLogin){
            return firstRequest(jo.getJSONObject("ParameterValues"));
        }
        switch (method){

        }
        return null;

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
        return error.error(value);
    }
    private String signup(JSONObject request){
        ArrayList<String> errors = au.checkInformation(request);
        if (errors.size() != 0){
            return this.error.signupError(errors);
        }
        String username = request.getString("username");
        int value = au.signup(request);
        if (value == 31){
            user = au.findUser(username);
            isLogin = true;
        }
        return error.error(value);
    }
}
