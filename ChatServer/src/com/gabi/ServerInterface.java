package com.gabi;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gabriel.mercea on 7/16/2014.
 */

public class ServerInterface extends JFrame {

    private static Server server;

    private static TextArea userEntry;
    private static TextArea logEntry;
    private static TextField serverInput;

    public ServerInterface(){
        super("Java Server for Android Chat");
        setDefaultCloseOperation(ServerInterface.EXIT_ON_CLOSE);
        userEntry = new TextArea();
        add(userEntry, BorderLayout.WEST);
        logEntry = new TextArea();
        add(logEntry, BorderLayout.EAST);
        serverInput = new TextField();
        add(serverInput, BorderLayout.SOUTH);
        this.setSize(640, 480);
        this.setVisible(true);
    }

    public static void main(String[] arg){
        new ServerInterface();
    }
}
