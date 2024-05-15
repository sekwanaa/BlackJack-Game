package com.pluralsight.Utilities;

import java.util.Scanner;

public class Inputs {
    private static final Scanner scanner = new Scanner(System.in);
    private Inputs() {}

    //Methods
    public static String getString() {
        return scanner.nextLine();
    }

    public static Integer getInt() {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            scanner.nextLine();
            System.out.print("\nPlease enter a valid input. Press ENTER to continue...");
            scanner.nextLine();
        }
        return null;
    }
}
