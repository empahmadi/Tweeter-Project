package main.java.org.ce.ap.server.connection;

import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.ObserverServiceImpl;
import main.java.org.ce.ap.server.impl.TimeLineServiceImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
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
    private final AuthenticationServiceImpl au;
    private final TweetingServiceImpl ts;
    private final ObserverServiceImpl os;
    private final TimeLineServiceImpl tls;
    private Socket client;

    /**
     * initialize some variables.
     *
     * @param socket   client.
     * @param au Authentication service.
     * @param os observer service.
     * @param tls timeline service.
     * @param ts tweeting service.
     */
    public ClientHandler(Socket socket, AuthenticationServiceImpl au,
                         TweetingServiceImpl ts, ObserverServiceImpl os, TimeLineServiceImpl tls) {
        client = socket;
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