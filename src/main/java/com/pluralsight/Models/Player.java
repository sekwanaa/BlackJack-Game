package com.pluralsight.Models;

import java.util.List;

public class Player {
    private final String name;
    private Hand hand;
    private boolean busted = false;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    //Methods
    public void createHand(List<Card> deck) {
        //Creating the house hand
        hand.addRandomCardToHand(deck);
        hand.addRandomCardToHand(deck);
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
