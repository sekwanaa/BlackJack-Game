package com.pluralsight.UserInterfaces;

import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Utilities;

import java.util.List;

public class PointsScreen extends Screen{

    public PointsScreen(List<Player> players) {
        super(players);
    }

    //Methods
    @Override
    public void display() {
        for (Player player : players) {
            System.out.println(Utilities.centerMessage(player.getName(), 46, ' '));
            Utilities.createLineofChars(46, '=');
            System.out.print("How many points would you like to buy in with?\n\nEnter amount here: ");
            int wageredPoints = scanner.nextInt();
            processStartingPoints(player, wageredPoints);
        }
    }


    //Methods
    private void processStartingPoints(Player player, int wageredPoints) {
        player.setPoints(wageredPoints);
    }

    //Getters and Setters
}
