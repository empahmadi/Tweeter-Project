package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.Test;
import main.java.org.ce.ap.client.services.ConnectionService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * this class connect client to server.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ConnectionServiceImpl implements ConnectionService{
    private final CommandParserServiceImpl cps;
    private final ConsoleViewServiceImpl cvs;
    private final DataInputStream input;
    private final DataOutputStream output;

    /**
     * this constructor is for initialize something.
     *
     * @param input server.
     * @param output .
     */
    public ConnectionServiceImpl(DataOutputStream output, DataInputStream input) {
        cps = new CommandParserServiceImpl(this);
        cvs = new ConsoleViewServiceImpl(cps);
        this.input = input;
        this.output = output;
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
        try {
            output.writeUTF(request);
            output.flush();
            if (request.contains("exit")){
                return "{}";
            }
            return input.readUTF();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
            return ioe.toString();
        }
    }
}
