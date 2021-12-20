package main.java.org.ce.ap.server.system;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * this class is for formatting the response of server.
 * format of this class is JSON.
 * this class also define the code errors.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class Response {


    /**
     * this method is for error response.
     *
     * @param code   error code.
     * @param type   error type.
     * @param params information about error.
     * @return response of server in unique format (JSON).
     */
    public String error(int code, String type, JSONArray params) {
        JSONObject jResponse = new JSONObject();
        jResponse.put("errorType", type);
        jResponse.put("errorCode", code);
        jResponse.put("params", params);
        return jResponse.toString();
    }

    /**
     * this class is for none error response.
     *
     * @param count  the size of json array.
     * @param result the result of request.
     * @return response of server in unique format (JSON).
     */
    public String response(int count, JSONArray result) {
        JSONObject jResponse = new JSONObject();
        jResponse.put("count", count);
        jResponse.put("result", result);
        return jResponse.toString();
    }

    /**
     * this method handle a code that is not clear that it is an error or not.
     *
     * @param code .
     * @param type .
     * @return response.
     */
    public String responseCode(int code, String type) {
        if (!(code >= 30 && code <= 40)) {
            return error(code, type, null);
        }
        JSONObject jResponse = new JSONObject();
        jResponse.put("count", 1);
        jResponse.put("result", successCode(code));
        return jResponse.toString();
    }

    /**
     * it gives a code and return its string value in json array.
     *
     * @param code .
     * @return string value of a success code.
     */
    private JSONArray successCode(int code) {
        return null;
    }

}
