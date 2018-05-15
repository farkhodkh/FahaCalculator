package com.example.user.fahacalculator.presenters;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.user.fahacalculator.calculation.Calculator;
import com.example.user.fahacalculator.common.CalculatorParameters;

import org.json.JSONArray;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CalculatorActionsPresenter implements CalculatorPresenter {
    private static View fragmentView;
    private static TextView mainScreenTextView;
    private static TextView resultTextView;
    private LinearLayout mainContainer;
    private WindowManager windowManager;
    private Context context;
    private Boolean landscape;
    private int btnWidth;
    private int btnHeight;

    private Calculator calculator = Calculator.getInstance();
    private List<String> rpnList;

    @Override
    public void attachView(View mvpView) {
        fragmentView = (View) mvpView;
        mainScreenTextView = fragmentView.findViewById(R.id.mainScreen);
        resultTextView = fragmentView.findViewById(R.id.resultTextView);
    }

    @Override
    public void viewIsReady() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

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

                btn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onButtonLongClicked(view);
                        return false;
                    }
                });

                linearLayout.addView(btn, buttonParams);
            }
            mainContainer.addView(linearLayout, linearParams);
        }
    }

    public void onButtonLongClicked(View view) {
        calculator.clearAll();
        calculator.updateData();
        setResultText();
    }

    private void onButtonClicked(View button) {
        String btnTag = (String) button.getTag();
        Observable<String> input = Observable.just(btnTag);
        input.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String btnTag) {
                if (btnTag != CalculatorParameters.EQUAL_BUTTON) {
                    calculator.input(btnTag);
                    calculator.updateData();
                } else {
                    onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (btnTag != CalculatorParameters.EQUAL_BUTTON) {
                    appendScreensText(btnTag);
                }
                setResultText();
            }
        });
    }

    private void setResultText() {
        resultTextView.setText(String.valueOf(calculator.compute()));
    }

    private void appendScreensText(String btnTag) {
        mainScreenTextView.setText(calculator.getScreenText());
    }

    private Point getDisplayParams() {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public void onResume(SharedPreferences mSettings, String APP_PREFERENCES_COUNTER) {
        calculator.fillInputList(mSettings, APP_PREFERENCES_COUNTER);
        calculator.updateData();
        setResultText();
    }

    public String getJsonObject() {
        JSONArray jsArray = new JSONArray(getInputList());
        return jsArray.toString();
    }

    private List<String> getInputList() {
        return calculator.getInputList();
    }

    public void onPause(SharedPreferences mSettings, String APP_PREFERENCES_COUNTER) {
        calculator.saveInputList(mSettings, APP_PREFERENCES_COUNTER);
    }
}
