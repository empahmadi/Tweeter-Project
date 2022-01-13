package org.ce.ap.client.services;

/**
 * this interface is a framework for connection service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface ConnectionService {

    /**
     * this method  is the point that application is running.
     */
    void run();

    /**
     * this method connects client to server.
     * this method send request to and get response from server.
     *
     * @param request .
     * @return response.
     */
    String connection(String request);
}
