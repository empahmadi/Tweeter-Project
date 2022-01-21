package org.ce.ap.server.system;

import org.ce.ap.server.impl.AuthenticationServiceImpl;

/**
 * this page is for updating our server every 30 sec due to force exit.
 *
 * @author Eid Mohammad Ahmadi
 * @version 1.0
 */
public class AutoUpdateServer implements Runnable{
    private final AuthenticationServiceImpl au;

    /**
     * initialize authentication service.
     * @param au .
     */
    public AutoUpdateServer(AuthenticationServiceImpl au){
        this.au = au;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(30000);
                au.update();
                System.out.println("updated");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
