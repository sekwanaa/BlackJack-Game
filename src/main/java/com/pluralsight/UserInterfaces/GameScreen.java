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

//            int highestPlayerScore = 0;

            //For each player, have them play out their turn against the house.
//TODO      THIS I NEED TO DO NEXT

            for (Player player : players) {

                Hand hand = player.getHand();
                boolean playing = true;
                while (playing) {
                    Utilities.createBigBlankSpace();
                    System.out.println(Utilities.centerMessage("|\tHouse\t|", 25, ' '));
                    house.getHand().displayHouseCards();

                    System.out.println(Utilities.createLineofChars(25, '='));
                    System.out.println(Utilities.centerMessage(String.format("|\t%s\t|", player.getName()), 25, ' '));

                    player.getHand().displayCards();

                    if (hand.checkIfBlackJack()) {
                        System.out.println("You've got Blackjack!");
                        System.out.print("Press Enter to continue...");
                        scanner.nextLine();
                        break;
                    }

                    playing = processHitOrStay(hand, deck);

                    if (hand.checkIfBusted()) {
                        //check if hand has aces, if so change aces value from 11 to 1
                        hand.changeAcePoints();
                        if (hand.checkIfBusted()) {
                            hand.displayCards();
                            System.out.println("Total: " + hand.calculateHand());
                            System.out.println("Busted!");
                            System.out.print("Press Enter to continue...");
                            scanner.nextLine();
                            break;
                        }
                    }

                }

//                if (hand.calculateHand() > highestPlayerScore) {
//                    highestPlayerScore = hand.calculateHand();
//                }
                // if hand total is more than 21 (did bust)
                if (hand.calculateHand() > 21) {
                    player.setBusted(true);
                }
            }

            //House does their turn
            housePlaysOutTurn(house, deck);

            //calculate cards given to each player. Closest to 21 wins.
            List<Player> winner = new ArrayList<>();
            //Iterate through players, if they have a higher score than the current winner(s)
            //clear the List of winners and add the current player.
            for (Player player : players) {
//TODO          Fix issue of players not properly adding
                if (player.isBusted()) continue;
                if (!winner.isEmpty() && player.getHand().calculateHand() > winner.get(0).getHand().calculateHand()) {
                    winner.clear();
                    winner.add(player);
                }
                winner.add(player);
            }

            if ((winner.isEmpty() || (house.getHand().calculateHand() > winner.get(0).getHand().calculateHand()) && (house.getHand().calculateHand() <= 21))) {
                displayWinner(house);
            } else {
                displayWinner(house, winner);
            }


            isPlaying = checkIfWantsToPlayAgain();
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
            if (houseHand.checkIfBlackJack())   {
                System.out.println("The House got Blackjack!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                break;
            } else {
                houseHand.changeAcePoints();
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
                houseHand.changeAcePoints();
                if (houseHand.checkIfBusted()) {
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
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.printf("""
                The winner is: %s with %d points

                """, house.getName(), house.getHand().calculateHand());
        for (Player losingHands : players) {
            if (losingHands.getHand().calculateHand() > 21) {
                System.out.printf("%s: %d ------ Busted!\n", losingHands.getName(), losingHands.getHand().calculateHand());

            } else {
                System.out.printf("%s: %d\n", losingHands.getName(), losingHands.getHand().calculateHand());
            }
        }
    }

    //if house is not the winner
    private void displayWinner(Player houseHand, List<Player> winner) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (winner.size() == 1) {
            System.out.printf("""
                    The winner is: %s with %d points

                    """, winner.get(0).getName(), winner.get(0).getHand().calculateHand());
        } else if (winner.size() > 1) {
            System.out.println("The winners are: \n");
            winner.forEach(w -> System.out.printf("\t%s: %d points\n", w.getName(), w.getHand().calculateHand()));
        }

        if (houseHand.getHand().calculateHand() > 21) {
            System.out.printf("\n%s: %d ------ Busted!\n", houseHand.getName(), houseHand.getHand().calculateHand());
        } else {
            System.out.printf("\n%s: %d\n", houseHand.getName(), houseHand.getHand().calculateHand());
        }

        //Filter out winners to display other players
        List<Player> otherPlayers = findNonMatchingItems(players, winner);
        for (Player losingPlayers : otherPlayers) {
            if (losingPlayers.getHand().calculateHand() > 21) {
                System.out.printf("%s: %d ------ Busted!\n", losingPlayers.getName(), losingPlayers.getHand().calculateHand());
            } else {
                System.out.printf("%s: %d\n", losingPlayers.getName(), losingPlayers.getHand().calculateHand());
            }
        }
    }

    public <T> List<T> findNonMatchingItems(List<T> list1, List<T> list2) {
        List<T> nonMatchingItems = new ArrayList<>();
        Set<T> set = new HashSet<>(list2);

        for (T item : list1) {
            if (!set.contains(item)) {
                nonMatchingItems.add(item);
            }
        }
        return nonMatchingItems;
    }

    //Getters and Setters
}
