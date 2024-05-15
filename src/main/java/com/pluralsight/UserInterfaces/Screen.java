package com.pluralsight.UserInterfaces;

import com.pluralsight.InteractionHandlers.PlayerInteractionHandler;
import com.pluralsight.InteractionHandlers.PointsInteractionHandler;
import com.pluralsight.Models.Card;
import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Inputs;
import com.pluralsight.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Screen {
    private List<Player> players;

    public Screen() {
        this.players = new ArrayList<>();
    }

    //Methods
    public void displayPlayerAddScreen() {
        System.out.println(Utilities.centerMessage("Enter up to 4 players", 46, ' '));
        Utilities.createLineofChars(46, '~');
        PlayerInteractionHandler.addPlayers(this.players);
    }

    public void displayPointsScreen() {
        for (Player player : players) {
            while (true) {
                Utilities.createBigBlankSpace();
                System.out.println(Utilities.centerMessage(player.getName(), 46, ' '));
                Utilities.createLineofChars(46, '=');
                System.out.print("How much would you like to 'Buy in' with?\n\nEnter 'Buy in' here: ");
                Integer wageredPoints = Inputs.getInt();
                if (wageredPoints == null) {
                    break;
                }
                PointsInteractionHandler.processStartingPoints(player, wageredPoints);
                break;
            }
        }
    }

    public void displayGameScreen() {
        //create an array of shuffled cards
//TODO      Maybe create 5 decks and use 5 decks so that players can't count cards.
        List<Card> deck = Card.createDeck();


        //Creating new player which is the house
        Player house = new Player("House");
        house.createHand(deck);


        //For each player, create a hand
        for (Player player : players) {
            player.createHand(deck);
        }

        //Get bets for each player
        getBets();

        //For each player, have them play out their turn against the house.
        playersPlayOutTurn(house, deck);

        //House does their turn
        housePlaysOutTurn(house, deck);

            /*players vs house, if anyone has higher than the house they win.
            anyone who draws with the house gets their money back
            if everyone busts and house also busts, no one wins, but you dont get your money back.*/
        Map<Player, String> winnersOrDraws = calculateIfHouseWon(house);

        if (winnersOrDraws == null) {
            displayWinner(house);
        } else {
            players.add(house);
            displayWinnersAndOrDraws(winnersOrDraws);
        }

        players.remove(house);
        List<Player> playersWithNoMoney = filterBrokePlayers();
        reset(playersWithNoMoney);
    }


    //Getters and setters

}
