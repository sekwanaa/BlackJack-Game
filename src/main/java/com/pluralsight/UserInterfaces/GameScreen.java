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
        reset();
    }


    //Methods
    private void getBets() {
        for (Player player : players) {
            double availablePoints = player.getPoints();
            if (player.getPoints() <= 0) {
                continue;
            }

            Utilities.createBigBlankSpace();
            System.out.println(Utilities.centerMessage(String.format("%s", player.getName()), 46, ' '));
            Utilities.createLineofChars(46, '=');

            while (true) {
                System.out.printf("""
                        How many would you like to bet?
                        Current points: %.2f
                        
                        Enter amount here:\s""", availablePoints);
                double betAmount = scanner.nextDouble();
                scanner.nextLine();

                if (betAmount > availablePoints || betAmount < 0) {
                    System.err.println("Please bet within how many points you have...");
                    System.out.println();
                } else {
                    player.bet(betAmount);
                    break;
                }
            }
        }
    }

    private void playersPlayOutTurn(Player house, List<Card> deck) {
        for (Player player : players) {
            if (player.getPoints() <= 0) {
                System.out.println("\n\nYou've lost all your points, loser.\n\n");
                continue;
            }
            Hand hand = player.getHand();

            boolean playing = true;
            while (playing) {
                Utilities.createBigBlankSpace();
                System.out.println(Utilities.centerMessage("|\tHouse\t|", 25, ' '));
                house.getHand().displayHouseCards();

                Utilities.createLineofChars(25, '=');
                System.out.println(Utilities.centerMessage(String.format("| %s : %.2fpts |", player.getName(), player.getPoints()), 25, ' '));

                hand.displayCards();

                if (hand.checkIfBlackJack()) {
                    System.out.println("You've got Blackjack!");
                    player.processBlackJack();
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
        }
        //check if hand has aces, if so change aces value from 11 to 1
        if (houseHand.checkIfBusted()) {
            houseHand.displayCards();
            house.setBusted(true);
            System.out.println("Busted!");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    private Map<Player, String> calculateIfHouseWon(Player house) {
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


    //if house is the winner

    private void displayWinner(Player house) {
        System.out.print("Press Enter to reveal the winners......");
        scanner.nextLine();
        Utilities.createBigBlankSpace();
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

    private void reset() {
        for (Player player : players) {
            player.getHand().clearHand();
            player.setBusted(false);
        }
    }

    //Getters and Setters
}
