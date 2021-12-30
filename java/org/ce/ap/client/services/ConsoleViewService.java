package main.java.org.ce.ap.client.services;

/**
 * this interface is a framework for console view service.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface ConsoleViewService {
    /**
     * this method get information and destination page and send information to that page.
     * @return code .
     */
    int consoleSys(String information, String page);

    /**
     * this method is for single response such
     * @param text
     */
    void singleRes(String text);

    /**
     * login or signup someone to system.
     * @return code.
     */
    int loginPage();

    /**
     * show main page.
     * @return code.
     */
    int main();

    /**
     * show a list of anything that we need it to show as a list.
     * @return code.
     */
    int list();

    /**
     * this method will show a profile.
     * @return code.
     */
    int profile();

    /**
     * this method will show a tweet.
     * @return code.
     */
    int tweet();

    /**
     * this method will sign up a user.
     * @return code.
     */
    int signup();

    /**
     * this method will show error that related to its code.
     *
     * @param code .
     */
    void parseError(int code);
}
