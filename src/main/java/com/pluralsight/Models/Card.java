package com.pluralsight.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
    private String name;
    private int value;
    private int points;

    public Card(String name, int value, int points) {
        this.name = name;
        this.value = value;
        this.points = points;
    }
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        for (int i=2; i<11; i++) {
            for (int j=0; j<4; j++) {
                Card card = new Card(Integer.toString(i), i, i);
                deck.add(card);
            }
        }

        for (int i=0; i<4; i++) {
            deck.add(new Card("J", 11, 10));
            deck.add(new Card("Q", 12, 10));
            deck.add(new Card("K", 13, 10));
            deck.add(new Card("A", 14, 11));
        }

        Collections.shuffle(deck);

        return deck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return String.format("""
                -----------------
                Card: %s
                Points: %d
                """, this.name, this.points);
    }


}
