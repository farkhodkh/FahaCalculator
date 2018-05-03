package com.example.user.fahacalculator.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.user.fahacalculator.R;
import com.example.user.fahacalculator.common.CalculatorParameters;
import com.example.user.fahacalculator.common.PresenterBase;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class CalculatorActionsPresenter extends PresenterBase implements CalculatorPresenter {
    private static View fragmentView;
    private LinearLayout mainContainer;
    private WindowManager windowManager;
    private Context context;
    private Boolean landscape;

    @Override
    public void attachView(View mvpView) {
        super.attachView(mvpView);
        fragmentView = (View) mvpView;
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
        int btnWidth;
        int btnHeight;
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
                btn.setText(Buttons[x][y]);
                btn.setTag(1);
                btn.setTextSize(19);
                Observable btnClick = (Observable) RxView.clicks(btn).subscribe(aVoid -> {
                    onButtonClicked();
                });

                linearLayout.addView(btn, buttonParams);
            }
            mainContainer.addView(linearLayout, linearParams);
        }
    }

    private void onButtonClicked(Button button) {
        int btmTag = (int) button.getTag();

        switch (btmTag) {
            case 1:
                Log.d("BTN", "onButtonClicked: " + 1);
                break;
            default:
                Log.d("BTN", "Default");
                break;
        }
    }

    @Override
    public View getView() {
        return super.getView();
    }

    private Point getDisplayParams() {
        @SuppressLint("ServiceCast")
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}
