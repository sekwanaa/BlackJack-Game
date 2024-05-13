package com.pluralsight.UserInterfaces;

import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Utilities;

import java.util.List;

public class PlayerAddScreen extends Screen{

    public PlayerAddScreen (List<Player> players) {
        super(players);
    }

    //Methods
    public void display() {
        System.out.println(Utilities.centerMessage("Enter up to 4 players", 46, '-'));
        addPlayers();
        boolean isAddingPlayers = true;
        while (isAddingPlayers) {
            if (this.players.size() == 4) {
                break;
            }
            System.out.print("Would you like to add another player? (Y/N): ");
            String addAnotherPlayer = scanner.nextLine().toLowerCase();

            switch (addAnotherPlayer) {
                case "y":
                    addPlayers();
                    break;
                case "n":
                    isAddingPlayers = false;
                    break;
                default:
                    System.out.println("That's not a valid choice, please choose again...");
            }
        }
    }

    private void addPlayers() {
        System.out.print("Enter player name: ");
        String playerName = scanner.nextLine();
        this.players.add(new Player(playerName));
    }
}
