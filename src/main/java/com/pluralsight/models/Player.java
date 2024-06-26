package com.pluralsight.models;

import java.util.List;

public class Player {
    private int user_id;
    private final String name;
    private final Hand hand;
    private boolean doneTurn, busted;
    private double originalBetAmount, insuranceBetAmount, points;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.busted = false;
    }

    public Player(int user_id, String name) {
        this.name = name;
        this.user_id = user_id;
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

    public void processInsuranceLoss() { this.points -= insuranceBetAmount; }

    public void processInsuranceWin() {
        this.points += 2 * insuranceBetAmount;
        this.insuranceBetAmount = 0;
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

    public void setPoints(double points) {
        this.points = points;
    }

    public double getBetAmount() {
        return originalBetAmount;
    }

    public void setOriginalBetAmount(double originalBetAmount) {
        this.originalBetAmount = originalBetAmount;
    }

    public double getInsuranceBetAmount() {
        return insuranceBetAmount;
    }

    public void setInsuranceBetAmount(double insuranceBetAmount) {
        this.insuranceBetAmount = insuranceBetAmount;
    }

    public boolean isDoneTurn() {
        return doneTurn;
    }

    public void setDoneTurn(boolean doneTurn) {
        this.doneTurn = doneTurn;
    }

    public int getUser_id() {
        return user_id;
    }
}
