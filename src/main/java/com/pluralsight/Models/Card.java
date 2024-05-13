package com.pluralsight.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
    private final String name;
    private int points;

    public Card(String name, int points) {
        this.name = name;
        this.points = points;
    }
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        for (int i=2; i<11; i++) {
            for (int j=0; j<4; j++) {
                Card card = new Card(Integer.toString(i), i);
                deck.add(card);
            }
        }

        for (int i=0; i<4; i++) {
            deck.add(new Card("J", 10));
            deck.add(new Card("Q", 10));
            deck.add(new Card("K", 10));
            deck.add(new Card("A", 11));
        }

        Collections.shuffle(deck);

        return deck;
    }

    public String getName() {
        return name;
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
