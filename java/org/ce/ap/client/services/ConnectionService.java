package main.java.org.ce.ap.client.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * this class connect client to server.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ConnectionService {

    private final DataInputStream input;
    private final DataOutputStream output;

    /**
     * this constructor is for initialize something.
     *
     * @param in  input.
     * @param out output.
     */
    public ConnectionService(DataInputStream in, DataOutputStream out) {
        input = in;
        output = out;
    }

    /**
     * this method  is the point that application is running.
     */
    public void run() {
        // something to do with console or gui to get request.
        // passing request to connection and get it response.
    }

    /**
     * this method connects client to server.
     * this method send request to and get response from server.
     *
     * @param request .
     * @return response.
     */
    public String connection(String request) {
        try {
            output.writeUTF(request);
            output.flush();
            return input.readUTF();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
            return ioe.toString();
        }
    }
}
