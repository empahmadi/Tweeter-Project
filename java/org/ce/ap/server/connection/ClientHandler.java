package main.java.org.ce.ap.server.connection;

import main.java.org.ce.ap.server.database.EMPDatabase;
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
    private EMPDatabase database;
    private Socket client;

    /**
     * initialize some variables.
     *
     * @param socket   client.
     * @param database database.
     */
    public ClientHandler(Socket socket, EMPDatabase database) {
        client = socket;
        this.database = database;
    }

    /**
     * methode run is for clients.
     */
    @Override
    public void run() {
        TweeterSystem system = new TweeterSystem(database);
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