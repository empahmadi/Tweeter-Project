package org.ce.ap.client.impl;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.ce.ap.client.pages.Login;
import org.ce.ap.client.pages.Main;
import org.ce.ap.client.pages.Signup;
import org.ce.ap.client.services.PageHandler;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this class will handle all pages.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class PageHandlerImpl implements PageHandler {
    private final Stage stage;
    private final Scene load;
    private final int size;
    private final int mode;
    private final CommandParserServiceImpl cps;
    private String username;

    /**
     * this constructor will initialize our variables.
     * @param cps .
     * @param stage .
     * @param load .
     */
    public PageHandlerImpl(CommandParserServiceImpl cps,Stage stage,Scene load){
        this.stage = stage;
        this.load = load;
        this.cps = cps;
        size = 0;mode = 1;
    }


    /**
     * application start from here.
     */
    @Override
    public void run() {
        Main main = new Main(cps,username);
        stage.setScene(main.init(size,mode));
    }

    /**
     * running login page.
     */
    @Override
    public void login() {
        Login login = new Login();
        Scene scene = login.init(size,mode,this);
        stage.setScene(scene);
    }

    /**
     * check information about login.
     *
     * @param username .
     * @param password .
     * @param error    .
     * @param login .
     */
    @Override
    public void checkLogin(String username, String password, Label error,Scene login) {
        stage.setScene(load);
        JSONObject response = new JSONObject(cps.login(username, password));
        if (response.getBoolean("hasError")) {
            error.setVisible(true);
            stage.setScene(login);
        }else{
            this.username = username;
            run();
        }
    }

    /**
     * run signup page.
     */
    @Override
    public void signup() {
        Signup signup = new Signup();
        stage.setScene(signup.init(size,mode,this));
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
     * @param error .
     * @param signup .
     */
    @Override
    public void checkSignup(String name, String username, String lastname, String password, String dateOfBirth, String sex, String bio, TextArea error, Scene signup) {
        stage.setScene(load);
        JSONObject response = new JSONObject(cps.signup(dateOfBirth,name,lastname,username,password,bio));
        if (response.getBoolean("hasError")) {
            JSONArray errors = response.getJSONArray("params");
            StringBuilder text = new StringBuilder();
            for(Object i:errors){
                text.append((String)i);
            }
            error.setVisible(true);
            error.setText(text.toString());
            stage.setScene(signup);
        }else{
            this.username = username;
            run();
        }
    }


}
