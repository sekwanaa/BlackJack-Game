package com.pluralsight.InteractionHandlers;

import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Inputs;

import java.util.List;

public class PlayerInteractionHandler {
    private PlayerInteractionHandler() {
    }

    //Methods

    public static void addPlayers(List<Player> players) {
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
                addPlayers(players);
                break;
            case "n":
                break;
            default:
                System.out.println("That's not a valid choice, please choose again...");
                addPlayers(players);
        }

    }
}
