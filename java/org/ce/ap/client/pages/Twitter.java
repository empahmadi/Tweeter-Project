package org.ce.ap.client.pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ce.ap.client.impl.ConnectionServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class Twitter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("loading.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 650);
        stage.setTitle("Tweeter");
        stage.setWidth(900);
        stage.setHeight(650);
        stage.setScene(scene);
        stage.show();
        connectToServer(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void connectToServer(Stage stage) {
        Error error = new Error();
        int port = 1111;
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/client-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            port = Integer.parseInt(config.get("server.port").toString());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        try (Socket socket = new Socket("localhost", port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Connection Established :)");
            ConnectionServiceImpl cs = new ConnectionServiceImpl(output, input, stage);
            cs.run();
        } catch (IOException ioe) {
            stage.setScene(error.show(stage, 1, 0, 0));
            System.out.println(ioe.toString());
        }
    }

}
