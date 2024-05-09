package com.pluralsight;

import com.pluralsight.Models.Card;
import com.pluralsight.Models.Hand;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n\n\n\n\n+--------Welcome to Sekwanaa's Casino--------+");
        System.out.println("|==============You are playing:==============|");
        System.out.println("+------------------BlackJack-----------------+\n");
        //create list of players and ask who is playing. each player is added to the list
        List<String> players = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            addPlayers(scanner, players);
            boolean isAddingPlayers = true;
            while (isAddingPlayers) {
                if (players.size() == 4) {
                    break;
                }
                System.out.print("Would you like to add another player? (Y/N): ");
                String addAnotherPlayer = scanner.nextLine().toLowerCase();

                switch (addAnotherPlayer) {
                    case "y":
                        addPlayers(scanner, players);
                        break;
                    case "n":
                        isAddingPlayers = false;
                        break;
                    default:
                        System.out.println("That's not a valid choice, please choose again...");
                }
            }


            //create an array of shuffled cards
            List<Card> deck = Card.createDeck();

            //Creating the house hand
            Hand houseHand = new Hand("House");
            addRandomCardToHand(houseHand, deck);
            addRandomCardToHand(houseHand, deck);

            //List to store each player's hand
            List<Hand> playerHands = new ArrayList<>();
            //For each player, create a hand
            for (String player : players) {
                Hand hand = new Hand(player);

                //each player is given a hand of cards
                for (int i = 0; i < 2; i++) {
                    //use random number generator to pick which card is given to each player.
                    addRandomCardToHand(hand, deck);
                }
                playerHands.add(hand);
            }

            Set<Hand> playersWhoDidNotBust = new HashSet<>();
            int highestPlayerScore = 0;

            for (Hand hand : playerHands) {

                boolean playing = true;
                while (playing) {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n|\tHouse\t|");
                    displayCards(houseHand);

                    System.out.println("===================");
                    System.out.printf("|%-4s%s%-4s|\n", " ", hand.getPlayer(), " ");
                    displayCards(hand);

                    if (hand.checkIfBlackJack()) {
                        System.out.println("You've got Blackjack!");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();
                        break;
                    }

                    System.out.println("Would you like to hit or stay?");
                    String hitOrStay = scanner.nextLine().toLowerCase();
                    switch (hitOrStay) {
                        case "hit":
                            addRandomCardToHand(hand, deck);
                            break;
                        case "stay":
                            playing = false;
                            break;
                    }

                    if (hand.checkIfBusted()) {
                        //check if hand has aces, if so change aces value from 11 to 1
                        hand.changeAcePoints();
                        if (hand.checkIfBusted()) {
                            displayCards(hand);
                            System.out.println("Total: " + hand.calculateHand());
                            System.out.println("Busted!");
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine();
                            break;
                        }
                    }

                }

                // if hand total is less than 21 (did not bust)
                if (hand.calculateHand() <= 21) {
                    playersWhoDidNotBust.add(hand);
                    if (hand.calculateHand() > highestPlayerScore) {
                        highestPlayerScore = hand.calculateHand();
                    }
//                    System.out.println("ADDED " + hand.getPlayer() + " " + hand.calculateHand());
                }
            }

            //Dealer does their turn
            while (houseHand.calculateHand() < 21) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n|\tHouse\t|");
                displayCards(houseHand);
                if (highestPlayerScore == 21) {
                    addRandomCardToHand(houseHand, deck);
                } else if (houseHand.calculateHand() > highestPlayerScore) {
                    break;
                } else if (houseHand.calculateHand() < highestPlayerScore) {
                    addRandomCardToHand(houseHand, deck);
                } else {
                    System.out.println("Something went wrong.");
                }
            }

            //calculate cards given to each player. Closest to 21 wins.
            List<Hand> winner = new ArrayList<>();
            for (Hand potentiallyWinningHand : playersWhoDidNotBust) {
                if (winner.isEmpty()) {
                    winner.add(potentiallyWinningHand);
                } else if (potentiallyWinningHand.calculateHand() > winner.get(0).calculateHand()) {
                    winner.clear();
                    winner.add(potentiallyWinningHand);
                } else if (potentiallyWinningHand.calculateHand() == winner.get(0).calculateHand()) {
                    winner.add(potentiallyWinningHand);
                }
            }

            if (houseHand.calculateHand() >= highestPlayerScore && houseHand.calculateHand() <= 21) {
                displayWinner(houseHand, playerHands);
            } else {
                displayWinner(houseHand, winner, playerHands);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayCards(Hand hand) {
        for (Card card : hand.getHand()) {
            // Constructing the top line of the card
            String top = "┌─────────┐\n";

            // Constructing the middle lines of the card
            String middle = String.format("│ %-8s│\n", card.getName());
            middle += "│         │\n";

            // Constructing the bottom lines of the card
            String bottom = String.format("│       %-2s│\n", card.getName());
            bottom += "└─────────┘";

            System.out.println(top + middle + bottom);
        }
        System.out.println("Total: " + hand.calculateHand());
    }

    //if house is the winner
    private static void displayWinner(Hand house, List<Hand> playerHands) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.printf("""
                        The winner is: %s with %d points
                        
                        """, house.getPlayer(), house.calculateHand());
        for (Hand losingHands : playerHands) {
            if (losingHands.calculateHand() > 21) {
                System.out.printf("%s: %d ------ Busted!\n", losingHands.getPlayer(), losingHands.calculateHand());

            } else {
                System.out.printf("%s: %d\n", losingHands.getPlayer(), losingHands.calculateHand());
            }
        }
    }

    //if house is not the winner
    private static void displayWinner(Hand houseHand, List<Hand> winner, List<Hand> playerHands) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (winner.size() == 1) {
            System.out.printf("""
                        The winner is: %s with %d points
                        
                        """, winner.get(0).getPlayer(), winner.get(0).calculateHand());
        } else if (winner.size() > 1) {
            System.out.println("The winners are: \n");
            winner.forEach(w -> System.out.printf("\t%s: %d points\n", w.getPlayer(), w.calculateHand()));
        }

        if (houseHand.calculateHand() > 21) {
            System.out.printf("\n%s: %d ------ Busted!\n", houseHand.getPlayer(), houseHand.calculateHand());
        } else {
            System.out.printf("\n%s: %d\n", houseHand.getPlayer(), houseHand.calculateHand());
        }

        //Filter out winners to display other players
        List<Hand> otherPlayers = findNonMatchingItems(playerHands, winner);
        for (Hand losingHands : otherPlayers) {
            if (losingHands.calculateHand() > 21) {
                System.out.printf("%s: %d ------ Busted!\n", losingHands.getPlayer(), losingHands.calculateHand());
            } else {
                System.out.printf("%s: %d\n", losingHands.getPlayer(), losingHands.calculateHand());
            }
        }
    }

    public static <T> List<T> findNonMatchingItems(List<T> list1, List<T> list2) {
        List<T> nonMatchingItems = new ArrayList<>();
        Set<T> set = new HashSet<>(list2);

        for (T item : list1) {
            if (!set.contains(item)) {
                nonMatchingItems.add(item);
            }
        }
        return nonMatchingItems;
    }

    private static void addPlayers(Scanner scanner, List<String> players) {
        System.out.print("Enter player name: ");
        String playerName = scanner.nextLine();
        players.add(playerName);
    }

    private static void addRandomCardToHand(Hand hand, List<Card> deck) {
        int index = (int) (Math.random() * deck.size() - 1) + 1;
        hand.addCard(deck.get(index));
        deck.remove(index);
    }

}
