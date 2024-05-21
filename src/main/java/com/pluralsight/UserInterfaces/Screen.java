package com.pluralsight.UserInterfaces;

import com.pluralsight.InteractionHandlers.GameInteractionHandler;
import com.pluralsight.InteractionHandlers.PlayerInteractionHandler;
import com.pluralsight.InteractionHandlers.PointsInteractionHandler;
import com.pluralsight.Models.Card;
import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Screen {
    public static List<Player> players = new ArrayList<>();

    //Methods

    public void displayWelcomeMessage() {
        System.out.println("\n\n\n\n\n+--------Welcome to Sekwanaa's Casino--------+");
        System.out.println("|==============You are playing:==============|");
        System.out.println("+------------------BlackJack-----------------+\n");
    }
    public void displayPlayerAddScreen() {
        System.out.println(Utilities.centerMessage("Enter up to 4 players", 46, ' '));
        Utilities.createLineofChars(46, '~');
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


    //Getters and setters

}
