package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * this interface is a framework for timeline service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.1
 */
public interface TimelineService {
    /**
     * this timeline shows all activity of users that user follow them.
     *
     * @param user .
     * @return tweets in given format.
     */
    JSONArray timeline(User user);

    /**
     * @param user ..
     * @return related tweets to user.
     */
    String run(User user);

}
