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

    //Methods
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

    public void constructCard() {
        // Constructing the top line of the card
        String top = "┌─────────┐\n";

        // Constructing the middle lines of the card
        String middle = String.format("│ %-8s│\n", getName());
        middle += "│         │\n";

        // Constructing the bottom lines of the card
        String bottom = String.format("│       %-2s│\n", getName());
        bottom += "└─────────┘";

        System.out.println(top + middle + bottom);
    }

    public static void constructBlankCard() {
        // Constructing the top line of the card
        String top = "┌─────────┐\n";

        // Constructing the middle lines of the card
        String middle = "│         │\n";
        middle += "│         │\n";

        // Constructing the bottom lines of the card
        String bottom = "│         │\n";
        bottom += "└─────────┘";

        System.out.println(top + middle + bottom);
    }


    //Getters and Setters
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
