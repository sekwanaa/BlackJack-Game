package com.pluralsight.ui;

import com.pluralsight.interactionHandlers.GameInteractionHandler;
import com.pluralsight.interactionHandlers.PlayerInteractionHandler;
import com.pluralsight.interactionHandlers.PointsInteractionHandler;
import com.pluralsight.models.Card;
import com.pluralsight.models.Player;
import com.pluralsight.util.Inputs;
import com.pluralsight.util.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Screen {
    public static List<Player> players = new ArrayList<>();

    //Methods
    public void displayHomeScreen() {
        displayWelcomeMessage();

        System.out.print("""
                Select an option
                
                [1] Play
                
                [0] Quit
                
                Enter choice: >""");

        String userChoice = Inputs.getString();

        switch (userChoice) {
            case "1" -> {displayPlayerAddScreen();

                displayPointsScreen();

                boolean isPlaying = true;
                while (isPlaying) {
                    //Game screen to actually play out the game of blackjack
                    displayGameScreen();

                    isPlaying = GameInteractionHandler.checkIfWantsToPlayAgain();
                }
            }
            case "0" -> System.exit(0);
            default -> {
                Inputs.erroneousInput();
                displayHomeScreen();
            }
        }
    }

    public void displayWelcomeMessage() {
        System.out.println("\n\n\n\n\n+--------Welcome to Sekwanaa's Casino--------+\n");



    }

    public void displayPlayerAddScreen() {
        Text.clearConsole();
        System.out.println(Text.centerMessage(" You are playing: ", 46, '='));
        System.out.println(Text.centerMessage(" BlackJack ", 46, '-'));
        System.out.println();
        System.out.println(Text.centerMessage("Enter up to 4 players", 46, ' '));
        Text.createLineofChars(46, '~');
        PlayerInteractionHandler.addPlayers();
    }

    public void displayPointsScreen() {
        PointsInteractionHandler.getBuyIns();
    }

    public void displayGameScreen() {
        //create an array of shuffled cards
        List<Card> deck = Card.createDeck();
        List<Card> discardPile = new ArrayList<>();

        Player house = PlayerInteractionHandler.initializeHouse(deck, discardPile);

        PlayerInteractionHandler.createPlayerHands(deck, discardPile);

        PointsInteractionHandler.getBets();

        GameInteractionHandler.playersPlayOutTurn(house, deck, discardPile);

        GameInteractionHandler.housePlaysOutTurn(house, deck, discardPile);

        /*players vs house, if anyone has higher than the house they win.
        anyone who draws with the house gets their money back
        if everyone busts and house also busts, no one wins, but you dont get your money back.*/
        Map<Player, String> winnersOrDraws = GameInteractionHandler.calculateIfHouseWon(house);

        GameInteractionHandler.displayWinners(winnersOrDraws, house);

        GameInteractionHandler.reset(house, deck, discardPile);
    }

}
