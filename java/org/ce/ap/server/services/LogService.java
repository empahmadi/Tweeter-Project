package main.java.org.ce.ap.server.services;


/**
 * this interface is a framework for record log.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface LogService {
    /**
     * this method will ready our service.
     */
    void setup();

    /**
     * this method will record a log.
     * @param req request.
     * @param res response.
     */
    void recordLog(String req, String res);
}
