package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.connection.ClientHandler;
import main.java.org.ce.ap.server.database.EMPDatabase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        ExecutorService thread = Executors.newCachedThreadPool();
        EMPDatabase database = EMPDatabase.getInstance();
        try (ServerSocket server = new ServerSocket(9321);) {
            server.setReuseAddress(true);
            while(count < 30) {
                Socket client = server.accept();
                System.out.println("new client connected " + client.getInetAddress().getHostAddress());
                count++;
                thread.execute(new ClientHandler(client,database));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}