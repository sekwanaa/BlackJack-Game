package com.pluralsight.Utilities;

public class Utilities {

    private Utilities() {}

    //Text Utilities
    public static String centerMessage(String message, int width, char padChar) {
        int totalPadding = width - message.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < leftPadding; i++) {
            builder.append(padChar);
        }
        builder.append(message);
        for (int i = 0; i < rightPadding; i++) {
            builder.append(padChar);
        }
        return builder.toString();
    }

    public static String createLineofChars(int width, char fillChar) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            builder.append(fillChar);
        }
        return builder.toString();
    }

    public static void createBigBlankSpace() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }


    //Validation checks

}
