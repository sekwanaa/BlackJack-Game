package com.pluralsight.InteractionHandlers;

import com.pluralsight.Models.Player;
import com.pluralsight.UserInterfaces.Screen;
import com.pluralsight.Utilities.Inputs;
import com.pluralsight.Utilities.Utilities;

import java.util.List;

public class PointsInteractionHandler extends Screen {
    private PointsInteractionHandler() {
    }

    //Methods

    public static void getBuyIns() {
        for (Player player : players) {
            Utilities.createBigBlankSpace();
            System.out.println(Utilities.centerMessage(player.getName(), 46, ' '));
            Utilities.createLineofChars(46, '=');
            System.out.print("How much would you like to 'Buy in' with?\n\nEnter 'Buy in' here: ");
            int wageredPoints = Inputs.getInt();
            PointsInteractionHandler.processStartingPoints(player, wageredPoints);
        }
    }

    private static void processStartingPoints(Player player, int wageredPoints) {
        player.setPoints(wageredPoints);
    }

    public static void getBets() {
        for (Player player : players) {
            double availablePoints = player.getPoints();
            if (player.getPoints() <= 0) {
                continue;
            }

            Utilities.createBigBlankSpace();
            System.out.println(Utilities.centerMessage(String.format("%s", player.getName()), 46, ' '));
            Utilities.createLineofChars(46, '=');

            while (true) {
                System.out.printf("""
                        How much would you like to bet?
                        Available funds: $%.2f
                        
                        Enter amount here: $""", availablePoints);

                double betAmount = Inputs.getDouble();

                if (betAmount > availablePoints || betAmount < 0) {
                    System.err.println("Clearly you don't have that much money...");
                    System.out.println();
                } else {
                    player.bet(betAmount);
                    break;
                }

            }
        }
    }
}
