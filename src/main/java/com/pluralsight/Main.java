package com.pluralsight;

import com.pluralsight.interactionHandlers.GameInteractionHandler;
import com.pluralsight.ui.Screen;
import com.pluralsight.util.Inputs;

public class Main {
    public static void main(String[] args) {
        try {
            Inputs.openScanner();
            Screen screen = new Screen();

            screen.displayWelcomeMessage();

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
