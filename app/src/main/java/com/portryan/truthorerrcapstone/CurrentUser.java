package com.portryan.truthorerrcapstone;

public class CurrentUser {
    private static String username;
    private static String firstName;
    private static String lastName;
    private static String pronouns;

    private static int points;

    public static void init(String user, String first, String last, String pn, int pts){
        username = user;
        firstName = first;
        lastName = last;
        pronouns = pn;
        points = pts;
    }

    public static String getUsername(){
        return username;
    }

    public static String getFirstName(){
        return firstName;
    }

    public static String getLastName(){
        return lastName;
    }

    public static String getPronouns(){
        return pronouns;
    }

    public static int getPoints(){ return points;}

    public static void reset(){
        username = null;
        firstName = null;
        lastName = null;
        pronouns = null;
        points = 0;
    }


}
