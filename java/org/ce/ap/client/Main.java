package main.java.org.ce.ap.client;


import main.java.org.ce.ap.client.impl.ConnectionServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class Main {
    /**
     * This application is based on server which means every time that user wants to use
     * from this application must connect to server.
     * after connecting to server application will run.
     * @param args .
     */
    public static void main(String[] args) {
        int port = 1111;
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            port = (Integer)config.get("server.port");
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        try(Socket socket = new Socket("localhost",port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())){
            System.out.println("Connection Established :)");
            ConnectionServiceImpl cs = new ConnectionServiceImpl(output,input);
            cs.run();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
