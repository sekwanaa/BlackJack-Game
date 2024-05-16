package com.pluralsight.InteractionHandlers;

import com.pluralsight.Models.Card;
import com.pluralsight.Models.Player;
import com.pluralsight.UserInterfaces.Screen;
import com.pluralsight.Utilities.Inputs;

import java.util.List;

public class PlayerInteractionHandler extends Screen {
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


    public static Player initializeHouse(List<Card> deck) {
        Player house = new Player("House");
        house.createHand(deck);
        return house;
    }

    public static void createPlayerHands(List<Card> deck) {
        for (Player player : players) {
            player.createHand(deck);
        }
    }
}