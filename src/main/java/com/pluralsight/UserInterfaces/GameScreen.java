package com.pluralsight.UserInterfaces;

import com.pluralsight.Models.Card;
import com.pluralsight.Models.Hand;
import com.pluralsight.Models.Player;
import com.pluralsight.Utilities.Utilities;

import java.util.*;

public class GameScreen extends Screen {


    public GameScreen(List<Player> players) {
        super(players);
    }

    //Methods
    public void display() {
        boolean isPlaying = true;
        while (isPlaying) {

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

            //For each player, have them play out their turn against the house.
            playersPlayOutTurn(house, deck);

            //House does their turn
            housePlaysOutTurn(house, deck);

            //calculate cards given to each player. Closest to 21 wins.
//            List<Player> winner = getWinners(highestPlayerScore);

            Map<Player, String> winnersOrDraws = calculateIfHouseWon(house);

            if (winnersOrDraws == null) {
                displayWinner(house);
            } else {
                players.add(house);
                displayWinnersAndOrDraws(winnersOrDraws);
            }

            players.remove(house);
            isPlaying = checkIfWantsToPlayAgain();
        }
    }


    //Methods

    private List<Player> getWinners(int highestPlayerScore) {
        List<Player> winner = new ArrayList<>();
        //Iterate through players, if they have a score equal to the highestCurrentScore, add them to winner list
        for (Player player : players) {
            if (player.getScore() == highestPlayerScore) {
                winner.add(player);
            }
        }
        return winner;
    }

    private Map<Player, String> calculateIfHouseWon(Player house) {
        Map<Player, String> winnersOrDraws = new HashMap<>();
        for (Player player : players) {
            if ((house.isBusted() && !player.isBusted()) || (!player.isBusted() && player.getScore() > house.getScore())) {
                winnersOrDraws.put(player, "winner");
            } else if (!player.isBusted() && player.getScore() == house.getScore()) {
                winnersOrDraws.put(player, "draw");
            }
        }

        if (winnersOrDraws.isEmpty()) {
            return null;
        }
        return winnersOrDraws;
    }

    private void playersPlayOutTurn(Player house, List<Card> deck) {
        for (Player player : players) {

            Hand hand = player.getHand();
            boolean playing = true;
            while (playing) {
                Utilities.createBigBlankSpace();
                System.out.println(Utilities.centerMessage("|\tHouse\t|", 25, ' '));
                house.getHand().displayHouseCards();

                Utilities.createLineofChars(25, '=');
                System.out.println(Utilities.centerMessage(String.format("| %s : %dpts |", player.getName(), player.getPoints()), 25, ' '));

                hand.displayCards();

                if (hand.checkIfBlackJack()) {
                    System.out.println("You've got Blackjack!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    break;
                }

                playing = processHitOrStay(hand, deck);

                if (hand.checkIfBusted()) {
                    hand.displayCards();
                    player.setBusted(true);
                    System.out.println("Busted!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    break;

                }

            }
        }
    }

    private boolean checkIfWantsToPlayAgain() {
        while (true) {
            System.out.print("Would you all like to play again? (Y/N): ");
            String playAgain = scanner.nextLine().toLowerCase();
            switch (playAgain) {
                case "y":
                    for (Player player : players) {
                        player.getHand().clearHand();
                    }
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("That is not a valid choice, try again.");
                    break;
            }
        }
    }

    private void housePlaysOutTurn(Player house, List<Card> deck) {
        Hand houseHand = house.getHand();
        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n|\tHouse\t|");
            houseHand.displayCards();
            if (houseHand.checkIfBlackJack()) {
                System.out.println("The House got Blackjack!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                break;
            } else {
                if (houseHand.calculateHand() < 17) {
                    houseHand.addRandomCardToHand(deck);
                    System.out.print("Press Enter to play dealer next card...");
                    scanner.nextLine();
                } else {
                    break;
                }
            }
            //check if hand has aces, if so change aces value from 11 to 1
            if (houseHand.checkIfBusted()) {
                if (houseHand.checkIfBusted()) {
                    house.setBusted(true);
                    houseHand.displayCards();
                    System.out.println("Total: " + houseHand.calculateHand());
                    System.out.println("Busted!");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    break;
                }
            }
        }
    }

    private static boolean processHitOrStay(Hand hand, List<Card> deck) {
        System.out.println("Would you like to hit or stay?");
        String hitOrStay = scanner.nextLine().toLowerCase();
        switch (hitOrStay) {
            case "hit":
                hand.addRandomCardToHand(deck);
                break;
            case "stay":
                return false;
            default:
                System.out.println("That's not a valid choice, please enter 'hit' or 'stay'...");
                break;
        }
        return true;
    }


    //if house is the winner
    private void displayWinner(Player house) {
        System.out.print("Press Enter to reveal the winners......");
        scanner.nextLine();
        Utilities.createBigBlankSpace();
        System.out.printf("""
                The winner is: %s with %d points

                """, house.getName(), house.getHand().calculateHand());

        Utilities.createLineofChars(40, '=');

        for (Player losingHands : players) {
            if (losingHands.isBusted()) {
                System.out.printf("%s: %d ------ Busted!\n", losingHands.getName(), losingHands.getHand().calculateHand());

            } else {
                System.out.printf("%s: %d\n", losingHands.getName(), losingHands.getHand().calculateHand());
            }
        }
    }

    //if house is not the winner
    private void displayWinnersAndOrDraws(Map<Player, String> winnersAndOrDraws) {
        Utilities.createBigBlankSpace();
        for (Map.Entry<Player, String> playerStringEntry : winnersAndOrDraws.entrySet())
            if (playerStringEntry.getValue().equals("draw")) {
                System.out.printf("""
                    There was a draw between the house and %s with %d points

                    """, playerStringEntry.getKey().getName(), playerStringEntry.getKey().getHand().calculateHand());
            } else if (playerStringEntry.getValue().equals("winner"))
            System.out.printf("""
                    %s won with %d points

                    """, playerStringEntry.getKey().getName(), playerStringEntry.getKey().getHand().calculateHand());


        List<Player> otherPlayers = findNonMatchingItems(players, winnersAndOrDraws);
        Utilities.createLineofChars(40, '=');

        for (Player player : otherPlayers) {
            if (player.isBusted()) {
                System.out.printf("%s: %d ------ Busted!\n", player.getName(), player.getHand().calculateHand());
            } else {
                System.out.printf("%s: %d\n", player.getName(), player.getHand().calculateHand());
            }
        }
    }

    public List<Player> findNonMatchingItems(List<Player> list1, Map<Player, String> winnersOrDraws) {
        List<Player> nonMatchingItems = new ArrayList<>();

        for (Player item : list1) {
            if (!winnersOrDraws.containsKey(item)) {
                nonMatchingItems.add(item);
            }
        }
        return nonMatchingItems;
    }

    //Getters and Setters
}
