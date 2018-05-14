package com.example.user.fahacalculator.calculation;

import android.util.Log;

import com.example.user.fahacalculator.common.CalculatorParameters;

import java.util.ArrayList;
import java.util.List;


public class Calculator {
    //reverse Polish notation
    private static ArrayList<String> rpnArrayList;
    //stack
    private static ArrayList<String> stackArray;

    private static ArrayList<String> inputList;

    private int lastStack;

    public static ArrayList<String> getRpnArrayList() {
        return rpnArrayList;
    }

    public static Calculator instance;

    public Calculator() {
        rpnArrayList = new ArrayList();
        stackArray = new ArrayList();
        inputList = new ArrayList();
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
        String operators = "÷*-+()";

        return operators.contains(item);
    }

    private boolean thisIsNumber(String item) {
        try {
            Integer.parseInt(item);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private double evaluateExpression(Double leftItem, Double rightItem, String operator) {
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
            case "÷":
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

        if(btnTag== CalculatorParameters.DEL_BUTTON){
            if(inputList.size()>0) {
                inputList.remove(inputList.size() - 1);
            }
            //TODO Удаление элементов из трех массивов синхронизировать!
            if ((rpnArrayList.size()==0&&lastStack==1)||(stackArray.size()==0&&lastStack==2)){
                return;
            }

            if(lastStack==1||stackArray.size()==0){
                rpnArrayList.remove(rpnArrayList.size()-1);
                lastStack = 2;
            }else if(lastStack==2){
                stackArray.remove(stackArray.size()-1);
                lastStack = 1;
            }

            return;
        }

        if (thisIsNumber(btnTag)) {
            if(lastStack == 1){
                String uppItem = rpnArrayList.get(rpnArrayList.size()-1) + btnTag;

                rpnArrayList.add(rpnArrayList.size()-1, uppItem);
                rpnArrayList.remove(rpnArrayList.size()-1);

                inputList.add(inputList.size()-1, uppItem);
                inputList.remove(inputList.size()-1);
            }else {
                rpnArrayList.add(btnTag);
                inputList.add(btnTag);
            }
            lastStack = 1;
        } else {

            if (stackArray.size() == 0 || btnTag.equals("(")) {
                stackArray.add(btnTag);
                inputList.add(btnTag);
                lastStack = 2;
                return;
            }

            int stackSize = stackArray.size() - 1;
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
                    lastStack = 1;
                }
                while (check);
            } else if (operatorPriority(lastItem) >= operatorPriority(btnTag)) {
                rpnArrayList.add(lastItem);
                stackArray.remove(stackSize);
                stackArray.add(btnTag);
                inputList.add(btnTag);
                lastStack = 2;
            } else {
                stackArray.add(btnTag);
                inputList.add(btnTag);
                lastStack = 2;
            }
        }
    }

    private List<String> getTempArray(){
        List<String> tempArray = new ArrayList<>();

        for (int i = 0; i < rpnArrayList.size(); i++) {
            tempArray.add(rpnArrayList.get(i));
        }

        if (tempArray.size() == 0) {
            return tempArray;
        }

        int stackSize = stackArray.size()-1;
        boolean check = true;
        do {
            if (stackSize < 0) {
                check = false;
            }else {
                tempArray.add(stackArray.get(stackSize));
            }
            stackSize--;
        }
        while (check);
        return tempArray;
    }

    public Double compute() {

        List<String> tempArray = getTempArray();
        List<String> resultArray = new ArrayList<>();

        if(tempArray.size()==0){
            return 0d;
        }

        String stackItem;
        int tempArraySize;
        for (int i = 0; i < tempArray.size(); i++) {
            stackItem = tempArray.get(i);
            if (thisIsOperator(stackItem)) {
                tempArraySize = tempArray.size();
                if(tempArraySize<3){
                    return 0d;
                }
                tempArraySize--;
                String firstItem = tempArray.get(tempArraySize - 2);
                String secondItem = tempArray.get(tempArraySize - 1);

                if (firstItem == null || secondItem == null||!thisIsNumber(firstItem)||!thisIsNumber(secondItem)) {
                    return 0d;
                }
                Double result = evaluateExpression(Double.parseDouble(firstItem), Double.parseDouble(secondItem), stackItem);
                resultArray.remove(tempArraySize - 1);
                resultArray.remove(tempArraySize - 2);
                resultArray.add(String.valueOf(result));
            } else {
                resultArray.add(String.valueOf(stackItem));
            }
        }

        return Double.parseDouble(resultArray.get(0));
    }

    public String getScreenText() {
        String resultText = "";
        for(int i = 0; i<inputList.size();i++){
            resultText = resultText + inputList.get(i);
        }
        return resultText;
    }
}
