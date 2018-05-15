package com.example.user.fahacalculator.presenters;

import android.view.View;

public interface CalculatorPresenter<F extends View> {
    void attachView(F view);

    void viewIsReady();

    void detachView();

    void destroy();
}
