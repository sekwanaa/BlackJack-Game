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
        for (Card card : hand) {
            handTotal += card.getPoints();
        }
        return handTotal;
    }

    public void changeAcePoints() {
        for (Card card : hand) {
            if (card.getName().equals("A")) {
                card.setPoints(1);
            }
        }
    }

    public boolean checkIfBusted() {
        return this.calculateHand() > 21;
    }

    public boolean checkIfBlackJack() {
        return this.calculateHand() == 21;
    }

    public void addRandomCardToHand(List<Card> deck) {
        int randomIndex = (int) (Math.random() * deck.size() - 1) + 1;
        addCard(deck.get(randomIndex));
        deck.remove(randomIndex);
    }

    //Getters

    public List<Card> getHand() {
        return this.hand;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        this.hand.forEach(card -> output.append(card.toString()).append("\n"));
        output.append(String.format("Total: %d\n",this.calculateHand()));
        return output.toString();
    }
}
