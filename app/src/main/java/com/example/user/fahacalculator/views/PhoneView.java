package com.example.user.fahacalculator.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.fahacalculator.R;
import com.example.user.fahacalculator.presenters.CalculatorActionsPresenter;

public class PhoneView extends Fragment {
    Context context;
    CalculatorActionsPresenter presenter;

    public static final String APP_PREFERENCES = "myCalculator";
    public static final String APP_PREFERENCES_COUNTER = "myCalculatorHistory";
    private SharedPreferences mSettings;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.fragment_phone_view, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        String value = "";
        presenter.onResume(mSettings, APP_PREFERENCES_COUNTER);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause(mSettings, APP_PREFERENCES_COUNTER);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_phone_view, container, false);

        presenter = new CalculatorActionsPresenter();

        presenter.attachView(view);

        presenter.initView();

        presenter.viewIsReady();

        return view;
    }
}
