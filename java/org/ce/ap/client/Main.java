package main.java.org.ce.ap.client;


import main.java.org.ce.ap.client.impl.ConnectionServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    /**
     * This application is based on server which means every time that user wants to use
     * from this application must connect to server.
     * after connecting to server application will run.
     * @param args .
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9321);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            System.out.println("Connection Established :)");
            ConnectionServiceImpl connector = new ConnectionServiceImpl(in, out);
            connector.run();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
