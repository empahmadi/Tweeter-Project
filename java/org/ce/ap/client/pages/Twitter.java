package org.ce.ap.client.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ce.ap.client.impl.ConnectionServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

/**
 * this class is that class that our application will start.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class Twitter extends Application {
    private String mode;
    private int size;
    private Scene scene;
    private int exitMode;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("loading.fxml"));
        stage.setTitle("Tweeter");
        initSetting();
        if (size == 0) {
            scene = new Scene(fxmlLoader.load(), 900, 650);
            stage.setWidth(940);
            stage.setHeight(650);
            stage.setScene(scene);
        } else {
            scene = new Scene(fxmlLoader.load(), 1800, 900);
            stage.setMaximized(true);
            stage.setScene(scene);
        }
        stage.show();
        connectToServer(stage, scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * established connect.
     *
     * @param stage .
     */
    private void connectToServer(Stage stage, Scene scene) {
        Error error = new Error();
        int port = 1111;
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            port = Integer.parseInt(config.get("server.port").toString());
        } catch (IOException ioe) {
            Parent root = error.show(2, 0, mode);
            if (root != null)
                scene.setRoot(root);
            System.out.println(ioe);
        }
        try {
            Socket socket = new Socket("localhost", port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connection Established :)");
            ConnectionServiceImpl cs = new ConnectionServiceImpl(output, input, stage, socket, mode, size, exitMode, scene);
            cs.run();
        } catch (IOException ioe) {
            Parent root = error.show(1, 0, mode);
            if (root != null)
                scene.setRoot(root);
            System.out.println(ioe.toString());
        }
    }

    /**
     * init our setting.
     *
     */
    public void initSetting() {
        Error error = new Error();
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-setting.properties")) {
            Properties config = new Properties();
            config.load(file);
            mode = config.get("client.screen.mode").toString();
            size = Integer.parseInt(config.get("client.screen.size").toString());
            exitMode = Integer.parseInt(config.get("client.screen.exit").toString());
        } catch (IOException ioe) {
            Parent root = error.show(2, 0, mode);
            if (root != null)
                scene.setRoot(root);
            System.out.println(ioe.toString());
        }
    }
}
