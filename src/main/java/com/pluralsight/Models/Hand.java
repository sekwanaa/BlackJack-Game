package com.pluralsight.Models;

import com.pluralsight.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
