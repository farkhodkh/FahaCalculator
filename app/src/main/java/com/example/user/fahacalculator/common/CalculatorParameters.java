package com.example.user.fahacalculator.common;

public class CalculatorParameters {
    public static final String PLUS_BUTTON = "+";
    public static final String MINUS_BUTTON = "-";
    public static final String MULTIPL_BUTTON = "*";
    public static final String DIVISION_BUTTON = "÷";
    public static final String DEL_BUTTON = "DEL";
    public static final String DOT_BUTTON = ".";
    public static final String EQUAL_BUTTON = "=";
    public static final String ROOT_BUTTON = "√";
    public static final String SQUARE_BUTTON = "^";
    public static final String DOUBLE_ZERO_BUTTON = "00";
    public static final String OPEN_BRACKETS_BUTTON = "(";
    public static final String CLOSE_BRACKETS_BUTTON = ")";


    private static String[][] buttons = {
            {DEL_BUTTON, ROOT_BUTTON, SQUARE_BUTTON, DOUBLE_ZERO_BUTTON},
            {"7", "8", "9", MULTIPL_BUTTON},
            {"4", "5", "6", DIVISION_BUTTON},
            {"1", "2", "3", MINUS_BUTTON},
            {DOT_BUTTON, "0", EQUAL_BUTTON, PLUS_BUTTON},
            {OPEN_BRACKETS_BUTTON, CLOSE_BRACKETS_BUTTON, "_", "_"}
    };

    private static String[][] landscapeButtons = {
            {DEL_BUTTON, ROOT_BUTTON, SQUARE_BUTTON, DOUBLE_ZERO_BUTTON, DOT_BUTTON, OPEN_BRACKETS_BUTTON},
            {"7", "8", "9", MULTIPL_BUTTON, DIVISION_BUTTON, CLOSE_BRACKETS_BUTTON},
            {"3", "4", "5", "6", MINUS_BUTTON},
            {"0", "1", "2", EQUAL_BUTTON, PLUS_BUTTON}
    };

    public static String[][] getLandscapeButtons() {
        return landscapeButtons;
    }

    public static String[][] getButtons() {
        return buttons;
    }

}
