package com.pluralsight.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Card(String name, int points) {

    //Methods
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        for (int i = 0; i < 5; i ++) { //5 decks
            for (int j = 2; j < 11; j++) { //Cards 2 - 10
                for (int k = 0; k < 4; k++) { //4 of each card
                    Card card = new Card(Integer.toString(j), j);
                    deck.add(card);
                }
            }

            //add the 3 face cards plus an Ace
            for (int j = 0; j < 4; j++) {
                deck.add(new Card("J", 10));
                deck.add(new Card("Q", 10));
                deck.add(new Card("K", 10));
                deck.add(new Card("A", 11));
            }
        }


        //shuffled 3 times
        for (int i = 0; i < 3; i++) {
            Collections.shuffle(deck);
            Collections.shuffle(deck);
            Collections.shuffle(deck);
        }

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
