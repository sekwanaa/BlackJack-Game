package com.pluralsight.util;

public class Text {

    private Text() {}

    //Text Utilities
    public static String centerMessage(String message, int width, char padChar) {
        int totalPadding = width - message.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return String.valueOf(padChar).repeat(Math.max(0, leftPadding)) +
                message +
                String.valueOf(padChar).repeat(Math.max(0, rightPadding));
    }

    public static void createLineofChars(int width, char fillChar) {
        System.out.println(String.valueOf(fillChar).repeat(Math.max(0, width)));
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }


    //Validation checks

}
