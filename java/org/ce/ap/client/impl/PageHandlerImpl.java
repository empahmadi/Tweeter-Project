package org.ce.ap.client.impl;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.ce.ap.client.pages.Error;
import org.ce.ap.client.pages.Login;
import org.ce.ap.client.pages.Main;
import org.ce.ap.client.pages.Signup;
import org.ce.ap.client.services.PageHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * this class will handle all pages.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class PageHandlerImpl implements PageHandler {
    private final Stage stage;
    private final Scene load;
    private final Scene main;
    private int size;
    private String mode;
    private int exitMode;
    private final CommandParserServiceImpl cps;
    private String username;
    private String password;
    private int rememberMe;
    private ToggleImpl toggle;

    /**
     * this constructor will initialize our variables.
     *
     * @param cps   .
     * @param stage .
     * @param load  .
     */
    public PageHandlerImpl(CommandParserServiceImpl cps, Stage stage, Scene load, String mode, int size, int exitMode, Scene main) {
        this.stage = stage;
        this.load = load;
        this.cps = cps;
        this.size = size;
        this.mode = mode;
        rememberMe = 0;
        password = "null";
        username = "null";
        this.exitMode = exitMode;
        this.toggle = new ToggleImpl();
        this.main = main;
    }

    /**
     * application starts from here after initializing setting.
     */
    public void main() {
        Error error = new Error();
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-setting.properties")) {
            Properties config = new Properties();
            config.load(file);
            rememberMe = Integer.parseInt(config.get("client.login.rememberMe").toString());
            if (rememberMe == 1) {
                username = config.get("client.login.username").toString();
                password = config.get("client.login.password").toString();
                rememberMeCheck(username, password);
            } else {
                login();
            }
        } catch (IOException ioe) {
            Parent root = error.show(2, size, mode);
            if (root != null)
                main.setRoot(root);
            System.out.println(ioe.toString());
        }
    }


    /**
     * application start from here.
     */
    @Override
    public void run() {
        Main main = new Main(cps, username, this, toggle,exitMode,stage);
        main.setAccelerators(this.main);
        Parent root = main.init(size, mode);
        if (root != null)
            this.main.setRoot(root);
        else {
            error();
        }
    }

    /**
     * running login page.
     */
    @Override
    public void login() {
        Login login = new Login();
        main.setRoot(login.init(size, mode, this));
    }

    /**
     * check information about login.
     *
     * @param username   .
     * @param password   .
     * @param error      .
     * @param rememberMe .
     */
    @Override
    public void checkLogin(String username, String password, Label error, boolean rememberMe) {
        stage.setScene(load);
        System.out.println(rememberMe);
        JSONObject response = new JSONObject(cps.login(username, password));
        if (response.getBoolean("has-error")) {
            error.setVisible(true);
        } else {
            this.username = username;
            if(rememberMe){
                this.rememberMe = 1;
                this.password = password;
            }
            run();
        }
    }

    /**
     * checks remember me information.
     *
     * @param username .
     * @param password .
     */
    public void rememberMeCheck(String username, String password) {
        JSONObject response = new JSONObject(cps.login(username, password));
        if (response.getBoolean("has-error")) {
            login();
        } else {
            this.username = username;
            run();
        }
    }

    public void addRememberMe(String username, String password) {
        rememberMe = 1;
        this.username = username;
        this.password = password;
    }

    /**
     * run signup page.
     */
    @Override
    public void signup() {
        Signup signup = new Signup();
        stage.setScene(signup.init(size, mode, this));
    }

    /**
     * check information about signup.
     *
     * @param name        .
     * @param username    .
     * @param lastname    .
     * @param password    .
     * @param dateOfBirth .
     * @param sex         .
     * @param bio         .
     * @param error       .
     * @param signup      .
     */
    @Override
    public void checkSignup(String name, String username, String lastname, String password, String dateOfBirth, String sex, String bio, TextArea error, Scene signup) {
        stage.setScene(load);
        JSONObject response = new JSONObject(cps.signup(dateOfBirth, name, lastname, username, password, bio));
        if (response.getBoolean("has-error")) {
            JSONArray errors = response.getJSONArray("params");
            StringBuilder text = new StringBuilder();
            for (Object i : errors) {
                text.append((String) i);
            }
            error.setVisible(true);
            error.setText(text.toString());
            stage.setScene(signup);
        } else {
            this.username = username;
            login();
        }
    }

    /**
     * show error
     */
    public void error() {
        Error error = new Error();
        Parent root = error.show(561, size, mode);
        if (root != null)
            main.setRoot(root);
    }

    /**
     * toggle size.
     */
    public void toggleScreen() {
        if (size == 0) {
            fullScreenMode();
        } else {
            smallScreenMode();
        }
    }

    /**
     * toggle theme.
     */
    public void toggleTheme(String mode) {
        if (mode.equals("light") || mode.equals("dark")) {
            this.mode = mode;
        }
    }

    /**
     * toggle exit mode.
     */
    public void toggleExitMode() {
        if (exitMode == 0) {
            exitMode = 1;
        } else {
            exitMode = 0;
        }
    }

    /**
     * @return theme.
     */
    public String getMode() {
        return mode;
    }

    /**
     * @return size.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return exit mode.
     */
    public int getExitMode() {
        return exitMode;
    }

    /**
     * this method will save our setting.
     */
    public void saveSetting() {
        Error error = new Error();
        try {
            File file = new File("D:/Project/java/Tweeter/src/main/resources/client-setting.properties");
            Properties config = new Properties();
            config.setProperty("client.screen.size", size + "");
            config.setProperty("client.screen.mode", mode);
            config.setProperty("client.screen.exit", exitMode + "");
            config.setProperty("client.login.rememberMe", rememberMe + "");
            config.setProperty("client.login.username", username);
            config.setProperty("client.login.password", password);
            FileOutputStream output = new FileOutputStream(file);
            config.store(output, "properties");
            output.close();
        } catch (FileNotFoundException ioe) {
            Parent root = error.show(2, size, mode);
            if (root != null)
                main.setRoot(root);
            System.out.println(ioe.toString());
        } catch (IOException e) {
            Parent root = error.show(2, size, mode);
            if (root != null)
                main.setRoot(root);
            System.out.println(e.toString());
        }
    }

    public void fullScreenMode(){
        size = 1;
    }

    public void smallScreenMode(){
        size = 0;
        toggle.toggleSize(0);
        stage.setWidth(940);
        stage.setHeight(650);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
}
