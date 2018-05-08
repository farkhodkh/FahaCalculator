package com.example.user.fahacalculator.presenters;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.fahacalculator.R;
import com.example.user.fahacalculator.common.CalculatorParameters;
import com.example.user.fahacalculator.common.PresenterBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorActionsPresenter extends PresenterBase implements CalculatorPresenter {
    private static View fragmentView;
    private static TextView mainScreenTextView;
    private static TextView resultTextView;
    private LinearLayout mainContainer;
    private WindowManager windowManager;
    private Context context;
    private Boolean landscape;
    private int btnWidth;
    private int btnHeight;
    private double firstValue = 0;
    private double secondValue = 0;
    private static boolean fractionalPart;

    @Override
    public void attachView(View mvpView) {
        super.attachView(mvpView);
        fragmentView = (View) mvpView;
        mainScreenTextView = fragmentView.findViewById(R.id.mainScreen);
        resultTextView = fragmentView.findViewById(R.id.resultTextView);
    }

    @Override
    public void viewIsReady() {
        super.viewIsReady();
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected boolean isViewAttached() {
        return super.isViewAttached();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {

        context = fragmentView.getContext();
        mainContainer = fragmentView.findViewById(R.id.main_container);

        Point display = getDisplayParams();

        int width = display.x;
        int height = display.y;

        landscape = width > height;

        String[][] Buttons = {};

        LinearLayout.LayoutParams linearParams;
        if (landscape) {
            Buttons = CalculatorParameters.getLandscapeButtons();
            linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
            btnWidth = width / 8;
            btnHeight = height / 6;
        } else {
            Buttons = CalculatorParameters.getButtons();
            linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
            btnWidth = height / 8;
            btnHeight = width / 6;
        }

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(btnWidth, btnHeight);
        buttonParams.setMargins(10, 10, 10, 10);

        int firstLevel = Buttons.length;
        for (int x = 0; x < firstLevel; x++) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearParams.setMargins(0, 1, 0, 0);
            int secondLevel = Buttons[x].length;
            for (int y = 0; y < secondLevel; y++) {
                Button btn = new Button(context);
                btn.setBackground(fragmentView.getResources().getDrawable(R.drawable.button_selector));
                String curItem = Buttons[x][y];
                btn.setText(curItem);
                btn.setTag(curItem);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onButtonClicked(view);
                    }
                });

                linearLayout.addView(btn, buttonParams);
            }
            mainContainer.addView(linearLayout, linearParams);
        }
    }

    private void onButtonClicked(View button) {
        String btnTag = (String) button.getTag();
        updateValues(btnTag);
        appendScreensText(btnTag);
    }

    private void updateValues(String btnTag) {
        String resultValue = (String) resultTextView.getText();
        if (btnTag == CalculatorParameters.DOT_BUTTON) {
            fractionalPart = true;
        }

        String stringValue = String.valueOf(firstValue);

        if (containsNumber("[0-9]", btnTag)) {
            if(fractionalPart){
                int indexOfFraction = stringValue.indexOf(CalculatorParameters.DOT_BUTTON);
                String tempData = stringValue.substring(0, indexOfFraction) +CalculatorParameters.DOT_BUTTON + String.valueOf(Integer.parseInt(stringValue.substring(indexOfFraction+1)) + btnTag);
                firstValue = Double.parseDouble(tempData);
            }else{
                int indexOfFraction = stringValue.indexOf(CalculatorParameters.DOT_BUTTON);
                String tempData = stringValue.substring(0, indexOfFraction) + btnTag + stringValue.substring(indexOfFraction);
                firstValue = Double.parseDouble(tempData);
                secondValue = 1;
            }
        }
    }

    private void appendScreensText(String btnTag) {
        String mainScreenText = (String) mainScreenTextView.getText();

        switch (btnTag) {
            case CalculatorParameters.DEL_BUTTON:
                if (mainScreenText.length() > 0) {
                    mainScreenTextView.setText(mainScreenText.substring(0, mainScreenText.length() - 1));
                }
                break;
            case CalculatorParameters.MULTIPL_BUTTON:
                mainScreenTextView.setText(mainScreenText + btnTag);
                resultTextView.setText(String.valueOf(firstValue * secondValue));
                break;
            case CalculatorParameters.DIVISION_BUTTON:
                mainScreenTextView.setText(mainScreenText + btnTag);
                resultTextView.setText(String.valueOf(firstValue / secondValue));
            case CalculatorParameters.MINUS_BUTTON:
                mainScreenTextView.setText(mainScreenText + btnTag);
                resultTextView.setText(String.valueOf(firstValue - secondValue));
            default:
                mainScreenTextView.setText(mainScreenText + btnTag);
                break;
        }
    }

    @Override
    public View getView() {
        return super.getView();
    }

    private Point getDisplayParams() {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private boolean containsNumber(String numbers, String stringToCheck) {

        Pattern pattern = Pattern.compile(numbers);
        Matcher matcher = pattern.matcher(stringToCheck);

        while (matcher.find()) {
            return true;
        }

        return false;
    }
}
