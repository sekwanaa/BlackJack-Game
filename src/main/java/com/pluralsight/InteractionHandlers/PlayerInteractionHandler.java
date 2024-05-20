package com.pluralsight.InteractionHandlers;

import com.pluralsight.Models.Card;
import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Inputs;

import java.util.List;

import static com.pluralsight.UserInterfaces.Screen.players;

public class PlayerInteractionHandler{
    private PlayerInteractionHandler() {
    }

    //Methods

    public static void addPlayers() {
        System.out.print("Enter player name: ");
        String playerName = Inputs.getString();
        players.add(new Player(playerName));
        if (players.size() == 4) {
            return;
        }
        System.out.print("Would you like to add another player? (Y/N): ");
        String addAnotherPlayer = Inputs.getString().toLowerCase();

        switch (addAnotherPlayer) {
            case "y":
                addPlayers();
                break;
            case "n":
                break;
            default:
                System.out.println("That's not a valid choice, please choose again...");
                addPlayers();
        }
    }

    public static Player initializeHouse(List<Card> deck, List<Card> discardPile) {
        Player house = new Player("House");
        house.createHand(deck, discardPile);
        return house;
    }

    public static void createPlayerHands(List<Card> deck, List<Card> discardPile) {
        for (Player player : players) {
            player.createHand(deck, discardPile);
        }
    }
}
