package main.java.org.ce.ap.server.services;

import main.java.org.ce.ap.server.database.EMPDatabase;
import main.java.org.ce.ap.server.modules.Tweet;
import main.java.org.ce.ap.server.modules.User;
import main.java.org.ce.ap.server.system.Response;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * this class is for sorting the related tweets to a user.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class TimeLineService {
    private final EMPDatabase database;
    private final Response response;
    private final TweetingService ts;

    /**
     * this constructor is for initialize some variables.
     * @param database .
     */
    public TimeLineService(EMPDatabase database) {
        this.database = database;
        response = new Response();
        ts = new TweetingService(database);
    }

    /**
     * this timeline shows all activity of users that user follow them.
     * @param user .
     * @return tweets in given format.
     */
    public JSONArray timeline(User user){
        ArrayList<Tweet> tweets = new ArrayList<>();
        JSONArray timeline = new JSONArray();
        for (User i: database.follows.get(user)){
            tweets.addAll(database.tweets.get(i));
        }
        for (User i: database.follows.get(user)){
            tweets.addAll(database.retweet.get(i));
        }
        for (User i: database.follows.get(user)){
            tweets.addAll(database.like.get(i));
        }
        delRepeated(tweets);
        for (Tweet i: tweets){
            timeline.put(ts.getTweet(i));
        }
        return timeline;
    }

    /**
     * delete repeated tweets.
     *
     * @param tweets tweets.
     */
    private void delRepeated(ArrayList<Tweet> tweets) {
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
    public String run(User user) {
        return response.response(timeline(user).length(),timeline(user));
    }

}
