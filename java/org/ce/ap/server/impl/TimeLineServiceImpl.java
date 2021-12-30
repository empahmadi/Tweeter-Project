package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.services.TimelineService;
import main.java.org.ce.ap.server.system.Response;
import org.json.JSONArray;

import java.util.ArrayList;

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
        ArrayList<Tweet> tweets = new ArrayList<>();
        JSONArray timeline = new JSONArray();
        for (User i : database.follows.get(user)) {
            tweets.addAll(database.userTweets.get(i));
        }
        for (User i : database.follows.get(user)) {
            tweets.addAll(database.UserRetweets.get(i));
        }
        for (User i : database.follows.get(user)) {
            tweets.addAll(database.userLikes.get(i));
        }
        delRepeated(tweets);
        for (Tweet i : tweets) {
            timeline.put(ts.getTweet(i));
        }
        return timeline;
    }

    /**
     * delete repeated tweets.
     *
     * @param tweets tweets.
     */
    @Override
    public void delRepeated(ArrayList<Tweet> tweets) {
        int i, j, size = tweets.size();
        for (i = 0; i < size; i++) {
            if (i == tweets.size() - 1)
                break;
            for (j = i + 1; j < size; j++) {
                if (j == tweets.size())
                    break;
                if (tweets.get(i).equals(tweets.get(j))) {
                    tweets.remove(j);
                    j--;

                }
            }
        }
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
