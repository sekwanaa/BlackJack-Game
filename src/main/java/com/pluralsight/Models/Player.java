package com.pluralsight.Models;

import java.util.List;

public class Player {
    private final String name;
    private Hand hand = new Hand();
    private boolean busted = false;
    private int points;

    public Player(String name) {
        this.name = name;
    }

    //Methods
    public void createHand(List<Card> deck) {
        //Creating the house hand
        hand.addRandomCardToHand(deck);
        hand.addRandomCardToHand(deck);
    }

    public int getScore() {
        return hand.calculateHand();
    }


    //Getters and Setters

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public boolean isBusted() {
        return busted;
    }

    public void setBusted(boolean busted) {
        this.busted = busted;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
