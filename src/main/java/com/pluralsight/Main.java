package com.pluralsight;

import com.pluralsight.Models.Player;
import com.pluralsight.UserInterfaces.GameScreen;
import com.pluralsight.UserInterfaces.PlayerAddScreen;
import com.pluralsight.UserInterfaces.PointsScreen;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            //create empty list of players
            List<Player> players = new ArrayList<>();

            //Welcome display message
            System.out.println("\n\n\n\n\n+--------Welcome to Sekwanaa's Casino--------+");
            System.out.println("|==============You are playing:==============|");
            System.out.println("+------------------BlackJack-----------------+\n");

            // Screen to ask who is playing. each player is added to the list
            PlayerAddScreen playerAddScreen = new PlayerAddScreen(players);
            playerAddScreen.display();

                // Screen to ask each player how many points they would like to wager, for now can wager any points
                PointsScreen pointsScreen = new PointsScreen(players);
                pointsScreen.display();

            boolean isPlaying = true;
            while (isPlaying) {
                //Game screen to actually play out the game of blackjack
                GameScreen gameScreen = new GameScreen(players);
                gameScreen.display();

                isPlaying = checkIfWantsToPlayAgain();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkIfWantsToPlayAgain() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Would you all like to play again? (Y/N): ");
            String playAgain = scanner.nextLine().toLowerCase();
            switch (playAgain) {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("That is not a valid choice, try again.");
                    break;
            }
        }
    }
}
