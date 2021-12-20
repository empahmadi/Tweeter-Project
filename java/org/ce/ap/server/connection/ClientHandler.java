package main.java.org.ce.ap.server.connection;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.services.AuthenticationService;
import main.java.org.ce.ap.server.services.ObserverService;
import main.java.org.ce.ap.server.services.TimeLineService;
import main.java.org.ce.ap.server.services.TweetingService;
import main.java.org.ce.ap.server.system.TweeterSystem;

import java.io.*;
import java.net.Socket;

/**
 * this class is for handling multi users.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class ClientHandler implements Runnable {
    private final EMPDatabase database;
    private final AuthenticationService au;
    private final TweetingService ts;
    private final ObserverService os;
    private final TimeLineService tls;
    private Socket client;

    /**
     * initialize some variables.
     *
     * @param socket   client.
     * @param database database.
     */
    public ClientHandler(Socket socket, EMPDatabase database, AuthenticationService au,
                         TweetingService ts, ObserverService os, TimeLineService tls) {
        client = socket;
        this.database = database;
        this.au = au;
        this.tls = tls;
        this.os = os;
        this.ts = ts;
    }

    /**
     * methode run is for clients.
     */
    @Override
    public void run() {
        TweeterSystem system = new TweeterSystem(au, tls, ts, os);
        String request;
        try (DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())) {
            request = in.readUTF();
            if (request.equals("exit")) {
                client.close();
            }
            out.writeUTF(system.requestGetter(request));
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}