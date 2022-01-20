package org.ce.ap.client.impl;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.ce.ap.client.pages.Login;
import org.ce.ap.client.services.PageHandler;
import org.json.JSONObject;

/**
 * this class will handle all pages.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class PageHandlerImpl implements PageHandler {
    private Stage stage;
    private Scene load;
    private int size,mode;
    private CommandParserServiceImpl cps;
    private Login login;

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
        login = new Login();
        size = 0;mode = 1;
    }


    /**
     * application start from here.
     */
    @Override
    public void run() {

    }

    /**
     * running login page.
     */
    @Override
    public void login() {
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
            run();
        }
    }

    /**
     * run signup page.
     */
    @Override
    public void signup() {

    }

    /**
     * check information about signup.
     */
    @Override
    public void checkSignup() {

    }
}
