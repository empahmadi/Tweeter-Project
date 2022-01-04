package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.connection.ClientHandler;
import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.impl.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int count = 0, port = 1111;
        ExecutorService thread = Executors.newCachedThreadPool();
        EMPDatabase database = EMPDatabase.getInstance();
        TweetingServiceImpl ts = new TweetingServiceImpl(database);
        AuthenticationServiceImpl au = new AuthenticationServiceImpl(database, ts);
        TimeLineServiceImpl tls = new TimeLineServiceImpl(database);
        ObserverServiceImpl os = new ObserverServiceImpl(database, au);
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/server-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            port = Integer.parseInt(config.get("server.port").toString());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        try (ServerSocket server = new ServerSocket(port)) {
            while (count < 30) {
                Socket client = server.accept();
                System.out.println("new client connected " + client.getInetAddress().getHostAddress());
                count++;
                thread.execute(new ClientHandler(client, au, ts, os, tls));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}