package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.impl.CommandParserServiceImpl;
import main.java.org.ce.ap.client.impl.ConnectionServiceImpl;
import main.java.org.ce.ap.client.impl.ConsoleViewServiceImpl;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimeLineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.server.system.TweeterSystem;

import java.io.IOException;

public class Test {
    CommandParserServiceImpl cps  = new CommandParserServiceImpl(this);
    ConsoleViewServiceImpl cvs = new ConsoleViewServiceImpl(cps);
    EMPDatabase database = EMPDatabase.getInstance();
    TweetingServiceImpl ts = new TweetingServiceImpl(database);
    AuthenticationServiceImpl au = new AuthenticationServiceImpl(database, ts);
    TimeLineServiceImpl tls = new TimeLineServiceImpl(database);
    ObserverServiceImpl os = new ObserverServiceImpl(database, au);
    TweeterSystem system = new TweeterSystem(au,tls,ts,os);
    public static void main(String[] args) {
        Test test = new Test();
        test.run();
    }
    public void run() {
        int value = 0;
        while (value == 0)
            value = cvs.loginPage();
        cvs.main();
    }


    public String connection(String request) {
        String response = system.requestGetter(request);
        System.out.println(response);
        return response;
    }
}
