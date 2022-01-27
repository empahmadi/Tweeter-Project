package org.ce.ap.client.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.ce.ap.client.pages.Main;

/**
 * this is a controller for menu bar.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class BarController {
    @FXML
    private ToggleButton EToggle;
    @FXML
    private MenuItem exitMode;
    @FXML
    private MenuBar content;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem size;
    @FXML
    private MenuItem light;
    @FXML
    private MenuItem dark;
    @FXML
    private ToggleButton toggle;
    @FXML
    private Menu application;
    @FXML
    private Menu view;
    @FXML
    private Menu options;
    @FXML
    private Menu help;
    @FXML
    private MenuItem about;
    @FXML
    private MenuItem helpM;



    private Main main;
    private Scene scene;
    private String path, style,mode;
    private int sizeS;

    /**
     * init page.
     *
     * @param size .
     * @param mode .
     * @param main .
     */
    public void init(int size,int exitMode, String mode, Main main, String path) {
        this.main = main;
        this.path = path;
        this.sizeS = size;
        this.mode = mode;
        style = "barLD.css";
        scene = main.getScene();
        scene.getStylesheets().add(path+style);
        toggleTheme(mode);
        if (size == 1) {
            toggle.setText("Small Screen");
        }
        if (exitMode == 1){
            EToggle.setText("System Tray");
        }
        EToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.toggleExit();
                if (exitMode == 0){
                    EToggle.setText("System Tray");
                }else
                    EToggle.setText("");
            }
        });
        toggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.toggleScreen();
            }
        });
        light.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.toggleTheme("light");
            }
        });
        dark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.toggleTheme("dark");
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.exit();
            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.logout();
            }
        });
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.About();
            }
        });
        helpM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.help();
            }
        });
        setAccelerators();
    }

    /**
     * set accelerators related to menu bar.
     */
    public void setAccelerators() {
        // toggle theme.
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN), new Runnable() {
            @Override
            public void run() {
                main.toggleTheme("light");
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN), new Runnable() {
            @Override
            public void run() {
                main.toggleTheme("dark");
            }
        });
        // toggle screen.
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), new Runnable() {
            @Override
            public void run() {
                main.toggleScreen();
            }
        });
        // show application menu
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN), new Runnable() {
            @Override
            public void run() {
                application.show();
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.V, KeyCombination.ALT_DOWN), new Runnable() {
            @Override
            public void run() {
                view.show();
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN), new Runnable() {
            @Override
            public void run() {
                options.show();
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN), new Runnable() {
            @Override
            public void run() {
                help.show();
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN), new Runnable() {
            @Override
            public void run() {
                main.logout();
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN), new Runnable() {
            @Override
            public void run() {
                main.toggleExit();
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN), new Runnable() {
            @Override
            public void run() {
                main.exit();
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F5), new Runnable() {
            @Override
            public void run() {
                main.update();
            }
        });
    }

    /**
     * change the size of screen.
     *
     * @param size .
     */
    public void toggleScreen(int size) {
        String name = "bar";
        if (sizeS == 0){
            name += "S";
        }else{
            name += "L";
        }
        if (mode.equals("light")){
            name += "L";
        }else{
            name += "D";
        }
        name+= ".css";
        scene.getStylesheets().remove(path+style);
        style = name;
        scene.getStylesheets().add(path+style);
        this.sizeS = size;
    }

    /**
     * change the theme of screen.
     *
     * @param mode .
     */
    public void toggleTheme(String mode) {
        String name = "bar";
        if (sizeS == 0){
            name += "S";
        }else{
            name += "L";
        }
        if (mode.equals("light")){
            name += "L";
        }else{
            name += "D";
        }
        name+= ".css";
        scene.getStylesheets().remove(path+style);
        style = name;
        scene.getStylesheets().add(path+style);
        this.mode = mode;
    }
}
