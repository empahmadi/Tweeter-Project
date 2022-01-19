package org.ce.ap.client.gui.controllers;

/**
 * this interface is a framework for Errors.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public interface ErrorController {

    /**
     * initialize the error page.
     * @param size .
     * @param mode .
     * @param caption .
     * @param type .
     * @param path the path of icons.
     */
    void init(int size,int mode,String caption,int type,String path);

    /**
     * set content image address.
     * @param imageAdd image address.
     */
    void setImageAdd(String imageAdd);
    /**
     * @return content image address.
     */
    String getImageAdd();
}
