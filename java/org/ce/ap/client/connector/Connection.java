package main.java.org.ce.ap.client.connector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Connection {

    private final DataInputStream input;
    private final DataOutputStream output;

    public Connection(DataInputStream in,DataOutputStream out){
        input = in;
        output = out;
    }



    public void run() {
        // something to do with console or gui to get request.
        // passing request to connection and get it response.
    }

    public String connection(String request) {
        try {
            output.writeUTF(request);
            output.flush();
            return input.readUTF();
        }catch (IOException ioe){
            System.out.println(ioe.toString());
            return ioe.toString();
        }
    }
}
