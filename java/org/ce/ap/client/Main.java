package main.java.org.ce.ap.client;


import main.java.org.ce.ap.client.impl.ConnectionServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    /**
     * This application is based on server which means every time that user wants to use
     * from this application must connect to server.
     * after connecting to server application will run.
     * @param args .
     */
    public static void main(String[] args) {
        ExecutorService execute = Executors.newCachedThreadPool();
        try (Socket socket = new Socket("localhost", 9321)){
            System.out.println("Connection Established :)");
            execute.execute(new ConnectionServiceImpl(socket));
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
