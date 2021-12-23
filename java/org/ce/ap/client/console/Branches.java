package main.java.org.ce.ap.client.console;

import main.java.org.ce.ap.client.services.CommandParserService;

import java.util.Scanner;

public class Branches {
    private Scanner scan;
    private CommandParserService request;
    public Branches(){
        scan = new Scanner(System.in);
        request = new CommandParserService();
    }
    public void login(){
        String username, password, response;
        System.out.print("username: ");
        username = scan.nextLine();
        System.out.print("password: ");
        password = scan.nextLine();
        response = request.inputString("login",username,password);
        System.out.println(response);
    }
}
