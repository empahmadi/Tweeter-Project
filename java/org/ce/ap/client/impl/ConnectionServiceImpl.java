package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.services.ConnectionService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class connect client to server.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ConnectionServiceImpl implements ConnectionService,Runnable {
    private Socket server;
    private final CommandParserServiceImpl cps;
    private final ConsoleViewServiceImpl cvs;

    /**
     * this constructor is for initialize something.
     *
     * @param server server.
     */
    public ConnectionServiceImpl(Socket server) {
        cps = new CommandParserServiceImpl(this);
        cvs = new ConsoleViewServiceImpl(cps);
        this.server = server;
    }

    /**
     * this method  is the point that application is running.
     */
    @Override
    public void run() {
        int value = 0;
        while (value == 0)
            value = cvs.loginPage();
        cvs.main();
    }

    /**
     * this method connects client to server.
     * this method send request to and get response from server.
     *
     * @param request .
     * @return response.
     */
    @Override
    public String connection(String request) {
        try(DataOutputStream output = new DataOutputStream(server.getOutputStream());
            DataInputStream input = new DataInputStream(server.getInputStream())) {
            output.writeUTF(request);
            return input.readUTF();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
            return ioe.toString();
        }
    }
}
