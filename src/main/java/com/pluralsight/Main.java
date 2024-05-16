package com.pluralsight;

import com.pluralsight.InteractionHandlers.GameInteractionHandler;
import com.pluralsight.UserInterfaces.Screen;
import com.pluralsight.Utilities.Inputs;

public class Main {
    public static void main(String[] args) {
        try {
            Inputs.openScanner();
            //Welcome display message
            System.out.println("\n\n\n\n\n+--------Welcome to Sekwanaa's Casino--------+");
            System.out.println("|==============You are playing:==============|");
            System.out.println("+------------------BlackJack-----------------+\n");

//TODO      Use Single-Responsibility Principles to organize code better.
            Screen screen = new Screen();

            screen.displayPlayerAddScreen();

            screen.displayPointsScreen();

            boolean isPlaying = true;
            while (isPlaying) {
                //Game screen to actually play out the game of blackjack
                screen.displayGameScreen();

                isPlaying = GameInteractionHandler.checkIfWantsToPlayAgain();
            }

            Inputs.closeScanner();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
