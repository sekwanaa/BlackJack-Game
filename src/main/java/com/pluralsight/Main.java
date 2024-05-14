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

            //Game screen to actually play out the game of blackjack
            GameScreen gameScreen = new GameScreen(players);
            gameScreen.display();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
