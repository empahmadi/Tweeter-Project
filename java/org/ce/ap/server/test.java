package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimeLineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.server.system.TweeterSystem;
import org.json.JSONArray;
import org.json.JSONObject;

public class test {
    public static void main(String[] args) {
        test main= new test();
        JSONObject request = new JSONObject();
        JSONArray date = new JSONArray();
        JSONArray date2 = new JSONArray();
        JSONObject a = new JSONObject();
        JSONObject b = new JSONObject();
        JSONObject c = new JSONObject();
        JSONObject d = new JSONObject();
        {
            a.put("username", "emp");
            a.put("name", "Eid");
            a.put("password", "try");
            date.put(2000);
            date.put(10);
            date.put(23);
            a.put("date-of-birth", date);
            b.put("username", "lms");
            b.put("name", "Mohammad");
            b.put("password", "try");
            date2.put(2006);
            date2.put(10);
            date2.put(11);
            b.put("date-of-birth", date2);
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
        TweetingServiceImpl ts = new TweetingServiceImpl(database);
        AuthenticationServiceImpl as = new AuthenticationServiceImpl(database,ts);
        ObserverServiceImpl os = new ObserverServiceImpl(database,as);
        TimeLineServiceImpl tls = new TimeLineServiceImpl(database);
        TweeterSystem system = new TweeterSystem(as, tls, ts,os);
        TweeterSystem sys2 = new TweeterSystem(as,tls,ts,os);
        //////////////////////////////////////////////////////////////////
        request.put("method","signup");
        request.put("parameters",a);
        System.out.println(system.requestGetter(request.toString()));
        /////////////////////////////////////////////////////////////////
        System.out.println(system.requestGetter(main.profile("emp")));
        /////////////////////////////////////////////////////////////////
        JSONObject tweet = new JSONObject();
        tweet.put("method","send-tweet");
        JSONObject info1 = new JSONObject();
        info1.put("content","Hi every one how are you?");
        tweet.put("parameters",info1);
        System.out.println(system.requestGetter(tweet.toString()));
        System.out.println(system.requestGetter(main.profile("emp")));
        /////////////////////////////////////////////////////////////////
        request.put("method","signup");
        request.put("parameters",b);
        System.out.println(sys2.requestGetter(request.toString()));
        /////////////////////////////////////////////////////////////////
        System.out.println(sys2.requestGetter(main.unfollow("emp")));
        /////////////////////////////////////////////////////////////////
        System.out.println(sys2.requestGetter(main.follow("emp")));
        System.out.println(system.requestGetter(main.profile("emp")));
        //////////////////////////////////////////////////////////////////
        System.out.println(sys2.requestGetter(main.timeline()));
    }

    public String profile(String username){
        JSONObject request = new JSONObject();
        request.put("method","profile");
        JSONObject parameter = new JSONObject();
        parameter.put("username",username);
        request.put("parameters",parameter);
        return request.toString();
    }

    public String follow(String username){
        JSONObject request = new JSONObject();
        request.put("method","follow");
        JSONObject parameter = new JSONObject();
        parameter.put("username",username);
        request.put("parameters",parameter);
        return request.toString();
    }
    public String unfollow(String username){
        JSONObject request = new JSONObject();
        request.put("method","unfollow");
        JSONObject parameter = new JSONObject();
        parameter.put("username",username);
        request.put("parameters",parameter);
        return request.toString();
    }
    public String like(String username, int id){
        JSONObject request = new JSONObject();
        request.put("method","like");
        JSONObject parameter = new JSONObject();
        parameter.put("username",username);
        parameter.put("tweet-id",id);
        request.put("parameters",parameter);
        return request.toString();
    }
    public String unlike(String username, int id){
        JSONObject request = new JSONObject();
        request.put("method","unlike");
        JSONObject parameter = new JSONObject();
        parameter.put("username",username);
        parameter.put("tweet-id",id);
        request.put("parameters",parameter);
        return request.toString();
    }
    public String deleteTweet(int id){
        JSONObject request = new JSONObject();
        request.put("method","delete-tweet");
        JSONObject parameter = new JSONObject();
        parameter.put("tweet-id",id);
        request.put("parameters",parameter);
        return request.toString();
    }
    public String sendTweet(int id){
        JSONObject request = new JSONObject();
        request.put("method","delete-tweet");
        JSONObject parameter = new JSONObject();
        parameter.put("tweet-id",id);
        request.put("parameters",parameter);
        return request.toString();
    }
    public String retweet(int id){
        JSONObject request = new JSONObject();
        request.put("method","delete-tweet");
        JSONObject parameter = new JSONObject();
        parameter.put("tweet-id",id);
        request.put("parameters",parameter);
        return request.toString();
    }
    public String notification(){
        JSONObject request = new JSONObject();
        request.put("method","notification");
        JSONObject parameter = new JSONObject();
        request.put("parameters",parameter);
        return request.toString();
    }
    public String timeline(){
        JSONObject request = new JSONObject();
        request.put("method","timeline");
        JSONObject parameter = new JSONObject();
        request.put("parameters",parameter);
        return request.toString();
    }

}
