package org.ce.ap.client.impl;


import javafx.stage.Stage;
import org.ce.ap.client.services.ConnectionService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * this class connect client to server.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class ConnectionServiceImpl implements ConnectionService {
    private final DataInputStream input;
    private final DataOutputStream output;
    private final PageHandlerImpl main;
    private final Socket server;

    /**
     * this constructor is for initialize something.
     *
     * @param input server.
     * @param output .
     */
    public ConnectionServiceImpl(DataOutputStream output, DataInputStream input, Stage window, Socket server) {
        CommandParserServiceImpl cps = new CommandParserServiceImpl(this);
        this.input = input;
        this.output = output;
        this.server = server;
        main = new PageHandlerImpl(cps,window,window.getScene());
    }

    /**
     * this method  is the point that application is running.
     */
    @Override
    public void run() {
        main.login();
    }

    /**
     * this method connects client to server.
     * this method send request to and get response from server.
     *
     * @param request .
     * @return response.
     */
    @Override
    public String connection(String request) {
        try {
            output.writeUTF(request);
            output.flush();
            if (request.contains("exit")){
                output.close();
                input.close();
                server.close();
                return "{}";
            }
            String res = input.readUTF();
            System.out.println(res);
            return res;
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.toString());
            JSONObject response = new JSONObject();
            JSONArray params = new JSONArray();
            params.put(ioe.toString());
            response.put("has-error",true);
            response.put("error-code",50);
            response.put("error-type","interact with server");
            response.put("params",params);
            return response.toString();
        }
    }
}
