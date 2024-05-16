package com.pluralsight.UserInterfaces;

import com.pluralsight.InteractionHandlers.GameInteractionHandler;
import com.pluralsight.InteractionHandlers.PlayerInteractionHandler;
import com.pluralsight.InteractionHandlers.PointsInteractionHandler;
import com.pluralsight.Models.Card;
import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Inputs;
import com.pluralsight.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Screen {
    protected static List<Player> players = new ArrayList<>();

    //Methods
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
//TODO      Maybe create 5 decks and use 5 decks so that players can't count cards.
        List<Card> deck = Card.createDeck();

        Player house = PlayerInteractionHandler.initializeHouse(deck);

        PlayerInteractionHandler.createPlayerHands(deck);

        PointsInteractionHandler.getBets();

        GameInteractionHandler.playersPlayOutTurn(house, deck);

        GameInteractionHandler.housePlaysOutTurn(house, deck);

        /*players vs house, if anyone has higher than the house they win.
        anyone who draws with the house gets their money back
        if everyone busts and house also busts, no one wins, but you dont get your money back.*/
        Map<Player, String> winnersOrDraws = GameInteractionHandler.calculateIfHouseWon(house);

        GameInteractionHandler.displayWinners(winnersOrDraws, house);

        GameInteractionHandler.reset(house);
    }


    //Getters and setters

}
