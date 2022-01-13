package org.ce.ap.server.impl;

import org.ce.ap.server.database.EMPDatabase;
import org.ce.ap.server.modules.Tweet;
import org.ce.ap.server.modules.User;
import org.ce.ap.server.services.TimelineService;
import org.ce.ap.server.system.Response;
import org.json.JSONArray;

/**
 * this class is for sorting the related tweets to a user.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class TimeLineServiceImpl implements TimelineService {
    private final EMPDatabase database;
    private final Response response;
    private final TweetingServiceImpl ts;

    /**
     * this constructor is for initialize some variables.
     *
     * @param database .
     */
    public TimeLineServiceImpl(EMPDatabase database) {
        this.database = database;
        response = new Response();
        ts = new TweetingServiceImpl(database);
    }

    /**
     * this timeline shows all activity of users that user follow them.
     *
     * @param user .
     * @return tweets in given format.
     */
    @Override
    public JSONArray timeline(User user) {
        JSONArray timeline = new JSONArray();
        for (User i : database.follows.get(user)) {
            for (Tweet j:database.userTweets.get(i)){
                timeline.put(ts.getTweet(i,j));
            }
            for (Tweet j:database.userRetweets.get(i)){
                timeline.put(ts.getTweet(i,j));
            }
            for (Tweet j : database.userLikes.get(i)) {
                timeline.put(ts.getTweet(database.tweetOwner.get(j),j));
            }
        }
        return timeline;
    }

    /**
     * @param user ..
     * @return related tweets to user.
     */
    @Override
    public synchronized String run(User user) {
        return response.response(timeline(user).length(), timeline(user));
    }

}
