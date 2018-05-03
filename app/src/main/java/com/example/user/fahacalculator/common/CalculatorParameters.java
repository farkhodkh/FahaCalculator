package com.example.user.fahacalculator.common;

public class CalculatorParameters {
    private static String[][] buttons = {
            {"Del", "√", "^", "00"},
            {"7", "8", "9", "*"},
            {"4", "5", "6", "÷"},
            {"1", "2", "3", "-"},
            {".", "0", "=", "+"}
    };

    private static String[][] landscapeButtons = {
            {"Del", "√", "^", "00", "."},
            {"7", "8", "9", "*", "÷"},
            {"3", "4", "5", "6", "-"},
            {"0", "1", "2", "=", "+"}
    };

    public static String[][] getLandscapeButtons() {
        return landscapeButtons;
    }

    public static String[][] getButtons() {
        return buttons;
    }
}
