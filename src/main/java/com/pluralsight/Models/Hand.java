package com.pluralsight.Models;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private String player;
    private List<Card> hand;

    public Hand(String playerName) {
        this.player = playerName;
        this.hand = new ArrayList<>();
    }

    //Methods

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public int calculateHand() {
        int handTotal = 0;
        for (Card card : hand) {
            handTotal += card.points();
        }
        return handTotal;
    }

    //Getters

    public List<Card> getHand() {
        return this.hand;
    }

    public String getPlayer() {
        return this.player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        this.hand.forEach(card -> output.append(card.toString()).append("\n"));
        output.append(String.format("Total: %d\n",this.calculateHand()));
        return output.toString();
    }
}
