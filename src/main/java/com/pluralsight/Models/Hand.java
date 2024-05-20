package com.pluralsight.Models;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> hand;

    public Hand() {
        this.hand = new ArrayList<>();
    }

    //Methods

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public int calculateHand() {
        int handTotal = 0;
        int aceCount = 0;

        for (Card card : this.hand) {
            int points = card.points();
            if (card.name().equals("A")) {
                aceCount++;
                points = 11;  // Count ace as 11 initially
            }
            handTotal += points;
        }

        // Adjust for aces if hand total exceeds 21
        while (handTotal > 21 && aceCount > 0) {
            handTotal -= 10;  // Count one ace as 1 instead of 11
            aceCount--;
        }
        return handTotal;
    }

    public boolean checkIfBusted() {
        return this.calculateHand() > 21;
    }

    public boolean checkIfBlackJack(int turn) {
        if (turn != 1) return false;
        return this.calculateHand() == 21;
    }

    public void addRandomCardToHand(List<Card> deck, List<Card> discardPile) {
        int randomIndex = (int) (Math.random() * deck.size() - 1) + 1;
        addCard(deck.get(randomIndex));
        discardPile.add(deck.get(randomIndex));
        deck.remove(randomIndex);
    }

    public boolean displayHouseCards() {
        hand.get(0).constructCard();
        Card.constructBlankCard();
        return hand.get(0).name().equals("A");
    }

    public void displayCards() {
        for (Card card : hand) {
            card.constructCard();
        }
        System.out.println("Total: " + calculateHand());
    }

    public void clearHand() {
        hand.clear();
    }

    //Getters

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        this.hand.forEach(card -> output.append(card.toString()).append("\n"));
        output.append(String.format("Total: %d\n",this.calculateHand()));
        return output.toString();
    }
}
