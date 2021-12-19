package main.java.org.ce.ap.server.system;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.services.AuthenticationService;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.services.TweetingService;
import org.json.JSONObject;

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
    }

    public String requestGetter(String request){
        JSONObject jo = new JSONObject(request);
        String method = jo.getString("methode");
        if (!isLogin){

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
        String response = au.login(information);
        if (au.isNumeric(response)){
            return rm.getStatus(error.errorMaker(Integer.parseInt(response)),null);
        }else{
            return rm.getStatus(null,rm.responseCode(180));
        }
    }
    private String signup(JSONObject request){
        String response = au.signup(request);
        if(au.changeInformation(request) != 0){
            return rm.getStatus(error.errorMaker(au.changeInformation(request)),null);
        }
        if (au.isNumeric(response)){
            return rm.getStatus(error.errorMaker(Integer.parseInt(response)),null);
        }else{
            return rm.getStatus(null,rm.responseCode(6));
        }
    }



}
