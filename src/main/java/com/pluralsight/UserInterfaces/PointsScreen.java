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
            while(true) {
                Utilities.createBigBlankSpace();
                System.out.println(Utilities.centerMessage(player.getName(), 46, ' '));
                Utilities.createLineofChars(46, '=');
                System.out.print("How much would you like to 'Buy in' with?\n\nEnter 'Buy in' here: ");
                if (scanner.hasNextInt()) {
                    int wageredPoints = scanner.nextInt();
                    scanner.nextLine();
                    processStartingPoints(player, wageredPoints);
                    break;
                } else {
                    scanner.nextLine();
                    System.out.print("\nPlease enter a valid input. Press ENTER to continue...");
                    scanner.nextLine();
                }
            }
        }
    }


    //Methods
    private void processStartingPoints(Player player, int wageredPoints) {
        player.setPoints(wageredPoints);
    }

    //Getters and Setters
}
