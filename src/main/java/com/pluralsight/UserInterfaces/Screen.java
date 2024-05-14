package com.pluralsight.UserInterfaces;

import com.pluralsight.Models.Player;

import java.util.List;
import java.util.Scanner;

public abstract class Screen {
    public static final Scanner scanner = new Scanner(System.in);
    protected List<Player> players;

    public Screen(List<Player> players) {
        this.players = players;
    }
    //Methods
    public abstract void display();


    //Getters and setters

}
