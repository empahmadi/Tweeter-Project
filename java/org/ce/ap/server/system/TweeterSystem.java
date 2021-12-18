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
    private boolean isLogin;

    public TweeterSystem(EMPDatabase database){
        au = new AuthenticationService(database);
        ts = new TweetingService(database);
        os = new ObserverService(database);
    }

    public JSONObject requestGetter(JSONObject request){
        String method = request.getString("methode");
        switch (method){

        }
        return null;

    }

}
