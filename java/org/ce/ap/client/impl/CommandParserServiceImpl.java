package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.services.CommandParserService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this class convert client request to
 * a readable format for server.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class CommandParserServiceImpl implements CommandParserService {
    private final ConnectionServiceImpl connectionService;

    /**
     * initialize connection service.
     *
     * @param cs connection service.
     */
    public CommandParserServiceImpl(ConnectionServiceImpl cs) {
        connectionService = cs;
    }

    /**
     * this methode make a request and pass it to server and return response of server.
     *
     * @param method     .
     * @param parameters .
     * @return .
     */
    @Override
    public String makeRequest(String method, JSONObject parameters) {
        JSONObject request = new JSONObject();
        request.put("method", method);
        request.put("parameters", parameters);
        return connectionService.connection(request.toString());
    }

    /**
     * try to log in someone in his account.
     *
     * @param username .
     * @param password .
     * @return response of server.
     */
    @Override
    public String login(String username, String password) {
        JSONObject parameters = new JSONObject();
        parameters.put("username", username);
        parameters.put("password", password);
        return makeRequest("login", parameters);
    }

    /**
     * try to signup someone to server.
     *
     * @param y           year of birth.
     * @param m           month of birth.
     * @param d           day of birth.
     * @param information information about sing up.
     * @return response of server.
     */
    @Override
    public String signup(int y, int m, int d, String... information) {
        JSONObject parameters = new JSONObject();
        JSONArray dof = new JSONArray();
        dof.put(y);
        dof.put(m);
        dof.put(d);
        parameters.put("username", information[0]);
        parameters.put("name", information[1]);
        parameters.put("password", information[2]);
        parameters.put("date-of-birth", parameters);
        if (information.length > 3) {
            parameters.put("lastname", information[3]);
            parameters.put("biography", information[4]);
        }
        return makeRequest("signup", parameters);
    }
}
