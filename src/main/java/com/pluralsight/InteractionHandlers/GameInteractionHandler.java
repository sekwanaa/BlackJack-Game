package com.pluralsight.InteractionHandlers;

import com.pluralsight.Models.*;
import com.pluralsight.Utilities.Inputs;
import com.pluralsight.Utilities.Utilities;

import java.util.*;

import static com.pluralsight.UserInterfaces.Screen.players;

public class GameInteractionHandler {

    public static void playersPlayOutTurn(Player house, List<Card> deck, List<Card> discardPile) {
        for (Player player : players) {
            if (player.getPoints() <= 0) {
                System.out.println("\n\nYou've lost all your money, loser.\n\n");
                continue;
            }
            Hand hand = player.getHand();

            int turn = 1;
            boolean playing = true;
            while (playing) {
                Utilities.clearConsole();
                System.out.println(Utilities.centerMessage("|\tHouse\t|", 25, ' '));
                house.getHand().displayHouseCards();

                Utilities.createLineofChars(25, '=');
                System.out.println(Utilities.centerMessage(String.format("| %s : $%.2f |", player.getName(), player.getPoints()), 25, ' '));
                System.out.println(Utilities.centerMessage(String.format("Current Bet: $%.2f", player.getBetAmount()), 25, ' '));

                hand.displayCards();

                if (hand.checkIfBlackJack(turn)) {
                    System.out.println("You've got Blackjack!");
                    player.processBlackJack();
                    System.out.print("Press Enter to continue...");
                    Inputs.awaitInput();
                    break;
                }

                if (hand.calculateHand() == 21) {
                    System.out.println("You've got 21!");
                    System.out.print("Press Enter to continue...");
                    Inputs.awaitInput();
                    break;
                }

                playing = processHitOrStay(hand, deck, discardPile);

                if (hand.checkIfBusted()) {
                    hand.displayCards();
                    player.setBusted(true);
                    System.out.println("Busted!");
                    System.out.print("Press Enter to continue...");
                    Inputs.awaitInput();
                    break;
                }
                turn++;
            }
        }
    }

    private static boolean processHitOrStay(Hand hand, List<Card> deck, List<Card> discardPile) {
        System.out.println("Would you like to hit or stay?");
        String hitOrStay = Inputs.getString().toLowerCase();
        switch (hitOrStay) {
            case "hit":
                hand.addRandomCardToHand(deck, discardPile);
                break;
            case "stay":
                return false;
            default:
                System.out.println("That's not a valid choice, please enter 'hit' or 'stay'...");
                break;
        }
        return true;
    }

    public static void housePlaysOutTurn(Player house, List<Card> deck, List<Card> discardPile) {
        Hand houseHand = house.getHand();
        int turn = 1;
        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n|\tHouse\t|");
            houseHand.displayCards();
            if (houseHand.checkIfBlackJack(turn)) {
                System.out.println("The House got Blackjack!");
                System.out.print("Press Enter to continue...");
                Inputs.awaitInput();
                break;
            } else {
                if (houseHand.calculateHand() < 17) {
                    houseHand.addRandomCardToHand(deck, discardPile);
                    turn++;
                    System.out.print("Press Enter to play dealer next card...");
                    Inputs.awaitInput();
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
            System.out.print("\n\nPress ENTER to view winners...");
            Inputs.awaitInput();
            Utilities.clearConsole();
            System.out.printf("""
                    The winner is: %s with %d points

                    """, house.getName(), house.getHand().calculateHand());

            Utilities.createLineofChars(40, '=');

            for (Player player : players) {
                if (player.isBusted()) {
                    System.out.printf("%s: %d ------ Busted!\n", player.getName(), player.getHand().calculateHand());

                } else {
                    System.out.printf("%s: %d\n", player.getName(), player.getHand().calculateHand());
                }
            }
        } else { //If house didn't win
            players.add(house);
            System.out.print("\n\nPress ENTER to view winners...");
            Inputs.awaitInput();
            Utilities.clearConsole();
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
            System.out.println(Utilities.centerMessage(" Points ", 46, '='));

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
                    return false;
                default:
                    System.out.println("That is not a valid choice, try again.");
                    break;
            }
        }
    }
}
