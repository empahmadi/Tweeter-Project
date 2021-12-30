package main.java.org.ce.ap.server.modules;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * this class holds the tweet information.
 * this class implements Serializable for serializing.
 *
 * @author Eid Mohammad Ahmadi
 * @version 2.0
 */
public class Tweet implements Serializable {
    //variables:
    private String content;
    private final LocalDateTime addingDate;
    private LocalDateTime latestUpdate;
    private final int id;
    //constructor:

    /**
     * set content of tweet and set its dates.
     *
     * @param content the content of tweet.
     */
    public Tweet(String content, int id) {
        this.id = id;
        this.content = content;
        addingDate = LocalDateTime.now();
        latestUpdate = LocalDateTime.now();
    }
    //methods:

    /**
     * @return content of tweet.
     */
    public String getContent() {
        return content;
    }

    /**
     * change the content of tweet.
     *
     * @param content new content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the date that tweet added.
     */
    public LocalDateTime getAddingDate() {
        return addingDate;
    }

    /**
     * @return the date that the latest update occurred.
     */
    public LocalDateTime getLatestUpdate() {
        return latestUpdate;
    }

    /**
     * @return the id of tweet.
     */
    public int getId() {
        return id;
    }

    /**
     * set the latest update date.
     *
     * @param latestUpdate latest update date.
     */
    public void setLatestUpdate(LocalDateTime latestUpdate) {
        this.latestUpdate = latestUpdate;
    }
}
