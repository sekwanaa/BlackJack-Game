package com.pluralsight.UserInterfaces;

import com.pluralsight.Models.Player;

import java.util.List;

public class PointsScreen extends Screen{
    private int points;

    public PointsScreen(List<Player> players) {
        super(players);
    }

    //Methods
    @Override
    public void display() {
        System.out.print("How many points would you like to wager?\n\nEnter amount here: ");
        int wageredPoints = scanner.nextInt();
    }

    //Getters and Setters

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
