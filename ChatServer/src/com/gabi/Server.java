package com.gabi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by gabriel.mercea on 7/16/2014.
 */

public class Server implements Runnable{

    private static ServerSocket serverSocket;

    private static final int USER_LIMIT=1024;

    // I don't like lists
    private static Socket[] sockets = new Socket[USER_LIMIT];
    private static InputStreamReader[] inputStreamReaders = new InputStreamReader[USER_LIMIT];
    private static BufferedReader[] bufferedReaders = new BufferedReader[USER_LIMIT];
    private static PrintStream[] printStreams = new PrintStream[USER_LIMIT];

    private static int maxClientId=0;
    private static int clientsOnline=0;

    public static void sendMessage(String message, int id){
        if(sockets[id] != null && sockets[id].isConnected()) {
            printStreams[id].println(message);
            printStreams[id].flush();
        }
    }

    public static void postMessage(String message){
        for(int i=0, counter=0; i<=maxClientId || counter<clientsOnline; i++) {
            if(!(sockets[i].equals(null)) && sockets[i].isConnected()) {
                sendMessage(message, i);
                counter++;
            }
        }
    }

    public void run(){
        try {
            if (serverSocket == null) {
                serverSocket = new ServerSocket(5000);
                UsersList.loadUsersList();
            }

            new Thread(checkForConnections).start();

            closeRemainingStreams();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void communicate(int id) {
        try {
            String message = "";
            while (!(message = bufferedReaders[id].readLine()).equals("Client> end") && sockets[id] != null && sockets[id].isConnected()) {
                postMessage("Client"+id+"> "+message);
            }
            closeStreams(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Runnable checkForConnections = new Runnable() {
        @Override
        public void run() {
            try {
                int i=0;
                while(true) {
                    if (sockets[i] == null || !(sockets[i].isConnected())) {
                        System.out.println("Waiting for client "+i+"...");
                        sockets[i] = serverSocket.accept();
                        if(i>=maxClientId) maxClientId=i;
                        System.out.println("Client "+i+" has connected");
                        setUpStreams(i);
                        new CommunicationThread(i).start();
                    }
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static void setUpStreams(int id){

        if(inputStreamReaders[id] == null && printStreams[id] == null && sockets[id].isConnected()) {
            System.out.println("setting up streams...");
            try {
                inputStreamReaders[id] = new InputStreamReader(sockets[id].getInputStream());
                bufferedReaders[id] = new BufferedReader(inputStreamReaders[id]);
                printStreams[id] = new PrintStream(sockets[id].getOutputStream());
                System.out.println("streams are set up");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("could not set up streams");
            }
        }
    }

    public static void closeStreams(int id){
        System.out.println("closing streams...");

            if (inputStreamReaders[id] != null && printStreams[id] != null && bufferedReaders[id] != null && sockets[id].isConnected()) {
                try {
                    System.out.println("closing connections with client "+id+"...");
                    inputStreamReaders[id].close();
                    bufferedReaders[id].close();
                    printStreams[id].close();

                    sockets[id].close();

                    inputStreamReaders[id] = null;
                    bufferedReaders[id] = null;
                    printStreams[id] = null;

                    sockets[id] = null;
                } catch (IOException e) {
                    System.out.println("could not close connection with client "+id);
                    e.printStackTrace();
                }
            }
    }

    public static void closeRemainingStreams(){
        System.out.println("closing streams...");

        for(int i=0; i<maxClientId; i++) {

            if (inputStreamReaders[i] != null && printStreams[i] != null && bufferedReaders[i] != null && sockets[i].isConnected()) {
                try {
                    System.out.println("closing connections with client "+i+"...");
                    inputStreamReaders[i].close();
                    bufferedReaders[i].close();
                    printStreams[i].close();

                    sockets[i].close();

                    inputStreamReaders[i] = null;
                    bufferedReaders[i] = null;
                    printStreams[i] = null;

                    sockets[i] = null;
                } catch (IOException e) {
                    System.out.println("could not close connection with client "+i);
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }
}
