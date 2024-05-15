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

    }


    //Methods
    private void processStartingPoints(Player player, int wageredPoints) {
        player.setPoints(wageredPoints);
    }

    //Getters and Setters
}
