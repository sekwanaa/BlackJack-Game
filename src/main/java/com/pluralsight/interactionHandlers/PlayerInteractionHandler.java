package com.pluralsight.interactionHandlers;

import com.pluralsight.dataManagers.UserDAO;
import com.pluralsight.models.Card;
import com.pluralsight.models.Player;
import com.pluralsight.util.Inputs;
import com.pluralsight.util.Text;

import java.util.List;

import static com.pluralsight.ui.Screen.players;

public class PlayerInteractionHandler{
    private static final UserDAO userDAO = new UserDAO();

    //Methods

    public static void addPlayers() {
        System.out.print("Do you have an account with us? (Y/N): >");
        String hasAccount = Inputs.getString();

        if (hasAccount.equalsIgnoreCase("y")) {
            Player player = getLoginInfo();
            if (player != null) {
                players.add(player);
            } else {
                System.out.println("This is not a valid username / password combination.");
                Inputs.awaitInput();
                addPlayers();
            }
        } else if (hasAccount.equalsIgnoreCase("n")) {
            System.out.print("Would you like to create an account with us? (Y/N): >");
            String wouldLikeToMakeAnAccount = Inputs.getString();

            if (wouldLikeToMakeAnAccount.equalsIgnoreCase("y")) {
                Player player = processAccountCreation();
                players.add(player);
            }
            else if (wouldLikeToMakeAnAccount.equalsIgnoreCase("n")) {
                System.out.print("Enter player name: ");
                String playerName = Inputs.getString();
                players.add(new Player(playerName));
            } else {
                Inputs.erroneousInput();
                addPlayers();
            }
        } else {
            Inputs.erroneousInput();
            addPlayers();
        }

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

    private static Player getLoginInfo() {
        Text.clearConsole();
        System.out.print("Enter username: >");
        String username = Inputs.getString();
        System.out.print("Enter password: >");
        String password = Inputs.getString();

        return userDAO.getUser(username, password);
    }

    private static Player processAccountCreation() {
        System.out.print("Enter username: >");
        String username = Inputs.getString();
        System.out.print("Enter password: >");
        String password = Inputs.getString();
        System.out.print("Re-Enter password:");
        String reEnteredPassword = Inputs.getString();

        if (!password.equals(reEnteredPassword)) {
            System.out.println("Sorry, the passwords did not match please try again.");
            Inputs.awaitInput();
            processAccountCreation();
        }

        userDAO.createUser(username, password, 0);

        return userDAO.getUser(username, password);
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
