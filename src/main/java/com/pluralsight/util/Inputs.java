package com.pluralsight.util;

import java.util.Optional;
import java.util.Scanner;

public class Inputs {
    private static Scanner scanner;

    private Inputs() {
    }

    //Methods
    // Method to open the Scanner
    public static void openScanner() {
        scanner = new Scanner(System.in);
    }

    // Method to close the Scanner
    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }

    // Helper method to make sure scanner is open when calling a method
    private static void ensureScannerIsOpen() {
        if (scanner == null) {
            openScanner();
        }
    }

    public static String getString() {
        ensureScannerIsOpen();
        return scanner.nextLine();
    }

    public static int getInt() {
        ensureScannerIsOpen();
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid number... Please enter an integer:");
            scanner.next(); // Discard invalid input
        }
        int input = scanner.nextInt();
        scanner.nextLine(); //eat CRLF
        return input;
    }

    public static Optional<Integer> getIntWithXCancellation() {
        ensureScannerIsOpen();
        if (!scanner.hasNextInt()) {
            String cancellation = scanner.nextLine();

            if (cancellation.equalsIgnoreCase("x")) return Optional.empty();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); //eat CRLF
        return Optional.of(input);
    }

    public static double getDouble() {
        ensureScannerIsOpen();
        while (!scanner.hasNextDouble()) {
            System.out.println("That's not a valid double... Please enter a double:");
            scanner.next(); // Discard invalid input
        }
        double input = scanner.nextDouble();
        scanner.nextLine(); //eat CRLF
        return input;
    }

    public static void erroneousInput() {
        ensureScannerIsOpen();
        awaitInput();
    }

    public static void awaitInput() {
        ensureScannerIsOpen();
        System.out.print("Press ENTER to continue...");
        scanner.nextLine();
    }

    public static void awaitInputNoMessage() {
        ensureScannerIsOpen();
        scanner.nextLine();
    }
}
