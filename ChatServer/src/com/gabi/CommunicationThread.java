package com.gabi;

/**
 * Created by gabriel.mercea on 7/16/2014.
 */
public class CommunicationThread extends Thread {

    private int id;

    public CommunicationThread(int id) {
        this.id=id;
    }

    public void run(){
        Server.communicate(id);
    }
}
