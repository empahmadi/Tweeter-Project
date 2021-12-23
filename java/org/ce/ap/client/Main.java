package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.connector.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9321);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            System.out.println("Connection Established :)");
            Connection connector = new Connection(in, out);
            connector.run();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
