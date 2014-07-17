package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends JFrame {

        TextArea userEntry;
        TextField userInput;

        Socket socket;

        InputStreamReader isr;
        BufferedReader br;
        PrintStream ps;

        public Client(){
            super("Java Client for Android Chat");
            userEntry = new TextArea();
            add(userEntry, BorderLayout.CENTER);
            userInput = new TextField();
            userInput.addActionListener(sendMessage);
            add(userInput, BorderLayout.SOUTH);
            this.setSize(640, 480);
            this.setVisible(true);
        }

        ActionListener sendMessage = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!userInput.getText().toString().equals("")) {
                    sendMessage(userInput.getText().toString());
                    userInput.setText("");
                }
            }
        };


    public void sendMessage(final String message){
        ps.println(message);
        ps.flush();
    }

    public void showMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userEntry.append(message);
            }
        }).start();
    }

    public void run(){
        try {
            showMessage("Establishing connection...\n");
            socket = new Socket("127.0.0.1", 5000);
            showMessage("Connected\n");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            showMessage("Setting up streams...\n");

            isr = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(isr);
            ps = new PrintStream(socket.getOutputStream());
            ps.flush();

            showMessage("Streams are set up\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            String message = "";

            while(!(message = br.readLine()).contains("> end")){
                showMessage("\n"+message);
            }

            showMessage("Closing streams...\n");

            socket.close();
            isr.close();
            br.close();
            ps.close();

            showMessage("Streams are closed\n");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

        public static void main(String[] args) {
            Client client = new Client();
            client.setDefaultCloseOperation(Client.EXIT_ON_CLOSE);
            client.run();
        }
}
