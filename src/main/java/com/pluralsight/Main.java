package com.pluralsight;

import com.pluralsight.ui.Screen;
import com.pluralsight.util.Inputs;

public class Main {
    public static void main(String[] args) {
        try {
            Inputs.openScanner();

            Screen screen = new Screen();
            screen.displayHomeScreen();

            Inputs.closeScanner();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
