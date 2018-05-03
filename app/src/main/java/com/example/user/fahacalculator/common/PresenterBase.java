package com.example.user.fahacalculator.common;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.user.fahacalculator.presenters.CalculatorPresenter;

public abstract class PresenterBase<F extends View> implements CalculatorPresenter {

    private F view;

    public void attachView(View mvpView) {
        view = (F) mvpView;
    }

    @Override
    public void viewIsReady() {

    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() {

    }

    protected boolean isViewAttached() {
        return view != null;
    }

    public F getView() {
        return view;
    }

    public boolean isTablet() {
        return false;
    }

    public abstract void initView();
}
