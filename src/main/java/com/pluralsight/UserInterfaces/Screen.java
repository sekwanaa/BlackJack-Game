package com.pluralsight.UserInterfaces;

import java.util.List;
import java.util.Scanner;

public abstract class Screen {
    public static final Scanner scanner = new Scanner(System.in);
    protected List<String> players;

    public Screen(List<String> players) {
        this.players = players;
    }
    //Methods
    public abstract void display();


    //Getters and setters

}
