package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.connection.ClientHandler;
import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.services.AuthenticationService;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.services.TimeLineService;
import main.java.org.ce.ap.server.services.TweetingService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        ExecutorService thread = Executors.newCachedThreadPool();
        EMPDatabase database = EMPDatabase.getInstance();
        AuthenticationService au = new AuthenticationService(database);
        TweetingService ts = new TweetingService(database, au);
        TimeLineService tls = new TimeLineService(database,au);
        ObserverService os = new ObserverService(database, au);
        try (ServerSocket server = new ServerSocket(9321);) {
            server.setReuseAddress(true);
            while (count < 30) {
                Socket client = server.accept();
                System.out.println("new client connected " + client.getInetAddress().getHostAddress());
                count++;
                thread.execute(new ClientHandler(client, database, au, ts, os, tls));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}