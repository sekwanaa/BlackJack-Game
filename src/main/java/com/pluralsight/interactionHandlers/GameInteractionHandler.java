package com.pluralsight.interactionHandlers;

import com.pluralsight.dataManagers.UserDAO;
import com.pluralsight.models.*;
import com.pluralsight.util.Inputs;
import com.pluralsight.util.Text;

import java.util.*;

import static com.pluralsight.ui.Screen.players;

public class GameInteractionHandler {

    public static void playersPlayOutTurn(Player house, List<Card> deck, List<Card> discardPile) {
        int turn = 1;
        boolean allPlayersDone;
        do {
            allPlayersDone = true;

            for (Player player : players) {
                if (player.getPoints() <= 0) {
                    if (!player.isDoneTurn()) {
                        System.out.println("\n\nYou've lost all your money, loser.\n\n");
                        continue;
                    }
                }

                if (player.isDoneTurn()) continue;

                Hand hand = player.getHand();


                Text.clearConsole();
                System.out.println(Text.centerMessage("|\tHouse\t|", 25, ' '));
                boolean showingAnAce = house.getHand().displayHouseCards();

                Text.createLineofChars(25, '=');
                System.out.println(Text.centerMessage(String.format("| %s : $%.2f |", player.getName(), player.getPoints()), 25, ' '));
                System.out.println(Text.centerMessage(String.format("Current Bet: $%.2f", player.getBetAmount()), 25, ' '));

                hand.displayCards();

                if (hand.checkIfBlackJack(turn)) {
                    System.out.println("You've got Blackjack!");
                    player.processBlackJack();
                    player.setDoneTurn(true);
                    Inputs.awaitInput();
                    continue;
                }

                if (hand.calculateHand() == 21) {
                    System.out.println("You've got 21!");
                    player.setDoneTurn(true);
                    Inputs.awaitInput();
                    continue;
                }

                boolean playing = processPlayerChoice(player, deck, discardPile, showingAnAce, turn);

                if (hand.checkIfBusted()) {
                    hand.displayCards();
                    player.setBusted(true);
                    player.setDoneTurn(true);
                    System.out.println("Busted!");
                    Inputs.awaitInput();
                } else if (!playing) {
                    player.setDoneTurn(true);
                } else {
                    allPlayersDone = false;
                }

            }
            turn++;
        } while (!allPlayersDone);
    }

    private static boolean processPlayerChoice(Player player, List<Card> deck, List<Card> discardPile, boolean showingAnAce, int turn) {
        Hand hand = player.getHand();
        while (true) {
            System.out.print("""
                What would you like to do?
                
                [1] Hit
                [2] Stay
                """);
            if (turn == 1 && showingAnAce) {
                System.out.println("[3] Take out insurance");

                System.out.print("\nEnter choice: ");
                int userChoice = Inputs.getInt();
                switch (userChoice) {
                    case 1:
                        hand.addRandomCardToHand(deck, discardPile);
                        return true;
                    case 2:
                        return false;
                    case 3:
                        PointsInteractionHandler.processInsurance(player);
                        return false;
                    default:
                        System.out.println("That's not a valid choice, please choose from one of the options...");
                        break;
                }
            } else {
                System.out.print("\nEnter choice: ");
                int userChoice = Inputs.getInt();
                switch (userChoice) {
                    case 1:
                        hand.addRandomCardToHand(deck, discardPile);
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("That's not a valid choice, please choose from one of the options...");
                        break;
                }
            }
        }
    }

    public static void housePlaysOutTurn(Player house, List<Card> deck, List<Card> discardPile) {
        Hand houseHand = house.getHand();
        int turn = 1;
        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n|\tHouse\t|");
            houseHand.displayCards();
            if (houseHand.checkIfBlackJack(turn)) {
                PointsInteractionHandler.processHouseBlackJack();
                System.out.println("The House got Blackjack!");
                break;
            } else {
                PointsInteractionHandler.processHouseNoBlackJack();
                if (houseHand.calculateHand() < 17) {
                    houseHand.addRandomCardToHand(deck, discardPile);
                    turn++;
                    System.out.print("Press Enter to play dealer next card...");
                    Inputs.awaitInputNoMessage();
                } else {
                    break;
                }
            }
        }
        //check if hand has aces, if so change aces value from 11 to 1
        if (houseHand.checkIfBusted()) {
            houseHand.displayCards();
            house.setBusted(true);
            System.out.println("Busted!");
        }
    }

    public static Map<Player, String> calculateIfHouseWon(Player house) {
        Map<Player, String> winnersOrDraws = new HashMap<>();
        for (Player player : players) {
            if ((house.isBusted() && !player.isBusted()) || (!player.isBusted() && player.getScore() > house.getScore())) {
                player.processWin();
                winnersOrDraws.put(player, "winner");
                continue;
            } else if (!player.isBusted() && player.getScore() == house.getScore()) {
                player.processDraw();
                winnersOrDraws.put(player, "draw");
                continue;
            }
            player.processLoss();
        }

        if (winnersOrDraws.isEmpty()) {
            return null;
        }
        return winnersOrDraws;
    }

    public static void displayWinners(Map<Player, String> winnersOrDraws, Player house) {
        if (winnersOrDraws == null) { //if house won
            Inputs.awaitInput();
            Text.clearConsole();
            System.out.printf("""
                    The winner is: %s with %d points

                    """, house.getName(), house.getHand().calculateHand());

            Text.createLineofChars(40, '=');

            for (Player player : players) {
                if (player.isBusted()) {
                    System.out.printf("%s: %d ------ Busted!\n", player.getName(), player.getHand().calculateHand());

                } else {
                    System.out.printf("%s: %d\n", player.getName(), player.getHand().calculateHand());
                }
            }
        } else { //If house didn't win
            players.add(house);
            Inputs.awaitInput();
            Text.clearConsole();
            for (Map.Entry<Player, String> playerStringEntry : winnersOrDraws.entrySet())
                if (playerStringEntry.getValue().equals("draw")) {
                    System.out.printf("""
                            There was a draw between the house and %s with %d points
                            """, playerStringEntry.getKey().getName(), playerStringEntry.getKey().getHand().calculateHand());
                } else if (playerStringEntry.getValue().equals("winner"))
                    System.out.printf("""
                            %s won with %d points
                            """, playerStringEntry.getKey().getName(), playerStringEntry.getKey().getHand().calculateHand());

            System.out.println();
            System.out.println(Text.centerMessage(" Points ", 46, '='));

            for (Player player : players) {
                if (player.isBusted()) {
                    System.out.printf("%s: %d ------ Busted!\n", player.getName(), player.getHand().calculateHand());
                } else {
                    System.out.printf("%s: %d\n", player.getName(), player.getHand().calculateHand());
                }
            }
        }
    }


    private static List<Player> filterBrokePlayers() {
        List<Player> brokePlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getPoints() <= 0) {
                brokePlayers.add(player);
            }
        }
        return brokePlayers;
    }

    public static void reset(Player house, List<Card> deck, List<Card> discardPile) {
        players.remove(house);
        List<Player> brokePlayers = filterBrokePlayers();
        for (Player brokePlayer : brokePlayers) {
            players.remove(brokePlayer);
        }

        if (players.isEmpty()) {
            System.out.println("\n\nEveryone has lost their money...\n");
            System.exit(0);
        }

        for (Player player : players) {
            player.getHand().clearHand();
            player.setBusted(false);
            player.setDoneTurn(false);
        }

        deck.addAll(discardPile);

        for (int i = 0; i < 3; i++) { //Shuffle deck 3 times
            Collections.shuffle(deck);
        }
    }

    public static boolean checkIfWantsToPlayAgain() {
        while (true) {
            System.out.print("Would you all like to play again? (Y/N): ");
            String playAgain = Inputs.getString().toLowerCase();
            switch (playAgain) {
                case "y":
                    return true;
                case "n":
                    for (Player player : players) {
                        if (player.getUser_id() != 0) {
                            UserDAO userDAO = new UserDAO();
                            userDAO.updateUser(player.getUser_id(), player.getName(), "points", player.getPoints());
                        }
                    }
                    return false;
                default:
                    System.out.println("That is not a valid choice, try again.");
                    break;
            }
        }
    }
}
