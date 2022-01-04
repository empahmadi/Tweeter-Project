package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.services.LogService;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogServiceImpl implements LogService {
    private Logger logger = Logger.getLogger("tweeterLog");
    private FileHandler fh;

    public LogServiceImpl(){
        setup();
    }


    /**
     * this method will ready our service.
     */
    @Override
    public void setup() {
        String path = "111";
        try (FileInputStream file = new FileInputStream("D:/Project/java/Tweeter/src/main/resources/server-application.properties")) {
            Properties config = new Properties();
            config.load(file);
            path = config.get("server.log.file").toString();
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        if (path.equals("111"))
            return;
        try{
            fh = new FileHandler(path+"tweeterLog.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method will record a log.
     *
     * @param req request.
     * @param res response.
     */
    @Override
    public synchronized void recordLog(String req, String res) {
        JSONObject request = new JSONObject(req);
        JSONObject response =   new JSONObject(res);
        String info;
        if (response.getBoolean("hasError")){
            info = "error in "+response.getString("errorType")+".";
        }else{
            info = "responding was successful.";
        }
        logger.info("request: "+request.getString("method")+" --> response: "+info);
    }

}
