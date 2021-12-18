package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.services.AuthenticationService;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.services.TweetingService;
import org.json.JSONObject;

public class test {
    public static void main(String[] args) {
        JSONObject a = new JSONObject();
        JSONObject b = new JSONObject();
        JSONObject c = new JSONObject();
        JSONObject d = new JSONObject();
        {
            a.put("username", "emp");
            a.put("name", "Eid");
            a.put("password", "try");
            a.put("dateOfBirth", "2003");
            b.put("username", "lms");
            b.put("name", "Mohammad");
            b.put("password", "try");
            b.put("dateOfBirth", "2006");
            c.put("username", "reza");
            c.put("name", "Pouya");
            c.put("password", "try");
            c.put("dateOfBirth", "2005");
            d.put("username", "ahmad");
            d.put("name", "Race");
            d.put("password", "try");
            d.put("dateOfBirth", "2004");
        }
        EMPDatabase database = EMPDatabase.getInstance();
        AuthenticationService as = new AuthenticationService(database);
        TweetingService ts = new TweetingService(database);
        ObserverService os = new ObserverService(database);
        as.signup(a);
        as.signup(b);as.signup(c);as.signup(d);
        for (User i: database.users){
            System.out.print(i.getUsername()+", "+i.getName()+"\n");
        }
        User w = as.findUser("emp");
        User x = as.findUser("lms");
        User y = as.findUser("reza");
        User z = as.findUser("ahmad");
        os.follow(x,y);
    }
}
