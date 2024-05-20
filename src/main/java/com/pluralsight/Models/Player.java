package com.pluralsight.Models;

import java.util.List;

public class Player {
    private final String name;
    private final Hand hand;
    private boolean busted;
    private double points;
    private double originalBetAmount;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.busted = false;
    }

    //Methods
    public void createHand(List<Card> deck, List<Card> discardPile) {
        //Creating the house hand
        hand.addRandomCardToHand(deck, discardPile);
        hand.addRandomCardToHand(deck, discardPile);
    }

    public void bet(double betAmount) {
        this.originalBetAmount = betAmount;
    }

    public void processBlackJack() {
        this.points += (originalBetAmount * 1.5);
    }

    public void processWin() {
        this.points += originalBetAmount;
    }

    public void processDraw() {
        this.points += 0;
    }

    public void processLoss() {
        this.points -= originalBetAmount;
    }

    //Getters and Setters
    public int getScore() {
        return hand.calculateHand();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean isBusted() {
        return busted;
    }

    public void setBusted(boolean busted) {
        this.busted = busted;
    }

    public double getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getBetAmount() {
        return originalBetAmount;
    }
}
