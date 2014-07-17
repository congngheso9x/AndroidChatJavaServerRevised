package com.gabi;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gabriel.mercea on 7/16/2014.
 */

public class UsersList {

    private static BufferedReader fileBufferedReader;
    private static FileWriter fileWriter;

    public static void loadUsersList(){
        try {

            fileWriter = new FileWriter("usersList.txt", true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String username, String password) throws IOException {

        StringTokenizer stringTokenizer;
        String userAndPass = "";

        boolean exista=false;

        fileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("usersList.txt")));

        while ((userAndPass = fileBufferedReader.readLine()) != null) {
            stringTokenizer = new StringTokenizer(userAndPass);
            if(username.equals(stringTokenizer.nextToken())){
                exista=true;
                break;
            }
        }

        if(exista==false) {
            fileWriter.append(username + " " + password + String.format("%n"));
            fileWriter.flush();
        }
    }

    public static boolean checkForLogin(String user, String password) {
        StringTokenizer stringTokenizer;
        String userAndPass = "";

        String userTemp = "";
        String passwordTemp = "";

        try {

            fileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("usersList.txt")));

            while ((userAndPass = fileBufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(userAndPass);
                userTemp = stringTokenizer.nextToken();
                passwordTemp = stringTokenizer.nextToken();
                if(user.equals(userTemp) && password.equals(passwordTemp)){
                    return true;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean checkForRegistration(String user){
        StringTokenizer stringTokenizer;
        String userAndPass = "";

        try {

            fileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("usersList.txt")));

            while ((userAndPass = fileBufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(userAndPass);
                if(user.equals(stringTokenizer.nextToken())){
                    return true;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void getUsersList(){
        String userAndPass="";

        StringTokenizer stringTokenizer;

        String user="";

        try {

            System.out.println("Available users:\n-------------------------------------------------------------------");

            fileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("usersList.txt")));

            while ((userAndPass = fileBufferedReader.readLine()) != null) {

                stringTokenizer = new StringTokenizer(userAndPass);

                System.out.println(user = stringTokenizer.nextToken());
            }

            System.out.println("-------------------------------------------------------------------");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
