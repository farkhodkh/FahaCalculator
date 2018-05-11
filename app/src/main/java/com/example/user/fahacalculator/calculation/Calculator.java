package com.example.user.fahacalculator.calculation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Float.parseFloat;

public class Calculator {
    //reverse Polish notation
    private static ArrayList<String> rpnArrayList;
    //stack
    private static ArrayList<String> stackArray;

    public static Calculator instance;

    public Calculator() {
        rpnArrayList = new ArrayList();
        stackArray = new ArrayList();
    }

    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }

        return instance;
    }

    private int operatorPriority(String operator) {
        int result = 0;
        switch (operator) {
            case "*":
                result = 3;
                break;
            case "/":
                result = 3;
                break;
            case "+":
                result = 2;
                break;
            case "-":
                result = 2;
                break;
            case "(":
                result = 1;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    private boolean thisIsOperator(String item) {
        String operators = "/*-+()";

        return operators.contains(item);
    }

    private boolean thisIsNumber(String item) {
        return !isNaN(parseFloat(item)) && isFinite(item);
    }

    private double evaluatedExpression(Double leftItem, Double rightItem, String operator) {
        Double result = 0d;
        switch (operator) {
            case "+":
                result = leftItem + rightItem;
                break;
            case "-":
                result = leftItem - rightItem;
                break;
            case "*":
                result = leftItem * rightItem;
                break;
            case "/":
                result = leftItem / rightItem;
                break;
            default:
                result = 0d;
                break;
        }

        return result;
    }

    public void inputData(String btnTag) {
        if (btnTag == null || btnTag.equals("")) {
            return;
        }

        if (thisIsNumber(btnTag)) {
            rpnArrayList.add(btnTag);
        } else {

            if (stackArray.size() == 0 || btnTag.equals("(")) {
                stackArray.add(btnTag);
                return;
            }

            int stackSize = stackArray.size();
            String lastItem = stackArray.get(stackSize);

            if (btnTag.equals(")")) {
                boolean check = true;
                lastItem = stackArray.get(stackSize);
                do {
                    if (lastItem.equals("(")) {
                        stackArray.remove(stackSize);
                        check = false;
                    }

                    rpnArrayList.add(lastItem);
                    stackArray.remove(stackSize);
                    stackSize--;
                }
                while (check);
            } else if (operatorPriority(lastItem) >= operatorPriority(btnTag)) {
                rpnArrayList.add(lastItem);
                stackArray.remove(stackSize);
                stackArray.add(btnTag);
            } else {
                stackArray.add(btnTag);
            }
        }
    }

    public Double compute() {

        List<String> tempArray = new ArrayList<>();
        List<String> resultArray = new ArrayList<>();

        for (int i = 0; i < rpnArrayList.size(); i++) {
            tempArray.add(rpnArrayList.get(i));
        }

        if (tempArray.size() == 0) {
            return 0d;
        }

        int stackSize = stackArray.size();
        boolean check = true;
        do {
            if (stackSize < 0) {
                check = false;
            }
            tempArray.add(stackArray.get(stackSize));
            stackSize--;
        }
        while (check);

        String stackItem;
        int tempAraySize;
        for(int i = 0; i<tempArray.size(); i++){
            stackItem = tempArray.get(i);
            if(thisIsOperator(stackItem)){
                tempAraySize = tempArray.size();

                String firstItem = tempArray.get(tempAraySize-2);
                String secondItem = tempArray.get(tempAraySize-1);;

                if(firstItem==null||secondItem==null){
                    return 0d;
                }
                Double result = evaluatedExpression(Double.parseDouble(firstItem), Double.parseDouble(secondItem), stackItem);
                resultArray.remove(tempAraySize-1);
                resultArray.remove(tempAraySize-2);
                resultArray.add(String.valueOf(result));
            }else{
                resultArray.add(String.valueOf(stackItem));
            }
        }

        return Double.parseDouble(resultArray.get(0));
    }
}
