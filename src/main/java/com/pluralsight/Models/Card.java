package com.pluralsight.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Card(String name, int points) {

    //Methods
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        for (int i = 2; i < 11; i++) {
            for (int j = 0; j < 4; j++) {
                Card card = new Card(Integer.toString(i), i);
                deck.add(card);
            }
        }

        for (int i = 0; i < 4; i++) {
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
        top += String.format("│ %-8s│\n", name());

        // Constructing the middle lines of the card
        String middle = "│         │\n";
        middle += "│         │\n";

        // Constructing the bottom lines of the card
        String bottom = "│         │\n";
        bottom += String.format("│       %-2s│\n", name());
        bottom += "└─────────┘";

        System.out.println(top + middle + bottom);
    }

    public static void constructBlankCard() {
        // Constructing the top line of the card
        String top = "┌─────────┐\n";
        top += "│         │\n";

        // Constructing the middle lines of the card
        String middle = "│         │\n";
        middle += "│         │\n";

        // Constructing the bottom lines of the card
        String bottom = "│         │\n";
        bottom += "│         │\n";
        bottom += "└─────────┘";

        System.out.println(top + middle + bottom);
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
