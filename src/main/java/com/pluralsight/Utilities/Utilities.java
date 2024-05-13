package com.pluralsight.Utilities;

public class Utilities {

    private Utilities() {}

    //Text Utilities
    public static String centerMessage(String message, int width, char padChar) {
        int totalPadding = width - message.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return String.valueOf(padChar).repeat(Math.max(0, leftPadding)) +
                message +
                String.valueOf(padChar).repeat(Math.max(0, rightPadding));
    }

    public static String createLineofChars(int width, char fillChar) {
        return String.valueOf(fillChar).repeat(Math.max(0, width));
    }

    public static void createBigBlankSpace() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }


    //Validation checks

}
