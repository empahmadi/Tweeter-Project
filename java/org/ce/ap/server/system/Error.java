package main.java.org.ce.ap.server.system;


import java.util.ArrayList;

public class Error {

    private String successCode(int code) {
        return switch (code) {
            case 30 -> "login was successful and welcome to Tweeter.";
            case 31 -> "signup was successful and welcome to Tweeter.";
            default -> "an unexpected error occurred!!!";
        };
    }

    public String error(int code){
        return null;
    }

    public String signupError(ArrayList<String> errors){
        return null;
    }

}
