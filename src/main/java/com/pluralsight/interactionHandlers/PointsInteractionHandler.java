package com.pluralsight.interactionHandlers;

import com.pluralsight.models.Player;
import com.pluralsight.util.Inputs;
import com.pluralsight.util.Text;

import java.util.Optional;

import static com.pluralsight.ui.Screen.players;


public class PointsInteractionHandler {
    private PointsInteractionHandler() {
    }

    //Methods

    public static void getBuyIns() {
        for (Player player : players) {
            Text.clearConsole();
            System.out.println(Text.centerMessage(player.getName(), 46, ' '));
            Text.createLineofChars(46, '=');
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

            Text.clearConsole();
            System.out.println(Text.centerMessage(String.format("%s", player.getName()), 46, ' '));
            Text.createLineofChars(46, '=');

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

    public static void processInsurance(Player player) {
        while (true) {
            Text.clearConsole();
            System.out.println(Text.centerMessage(" Type 'x' at anytime to cancel ", 46, '='));
            System.out.print("How much would you like to put down as insurance?: ");
            Optional<Integer> insuranceAmount = Inputs.getIntWithXCancellation();

            if (insuranceAmount.isEmpty()) break;

            if (insuranceAmount.get() > player.getBetAmount() / 2 || insuranceAmount.get() < 0) {
                System.out.printf("You can only place a bet between 0 and %.2f\n", player.getBetAmount() / 2);
            } else {
                player.setInsuranceBetAmount(insuranceAmount.get());
                break;
            }
        }
    }

    public static void processHouseBlackJack() {
        for (Player player : players) {
            if (player.getInsuranceBetAmount() != 0) {
                player.processInsuranceWin();
            }
        }
    }

    public static void processHouseBlackJack(Player player) {
        if (player.getInsuranceBetAmount() != 0) {
            player.processInsuranceWin();
        }
    }

    public static void processHouseNoBlackJack() {
        for (Player player : players) {
            if (player.getInsuranceBetAmount() != 0) {
                player.processInsuranceLoss();
                player.setInsuranceBetAmount(0);
            }
        }
    }
}
