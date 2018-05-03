package com.example.user.fahacalculator.views;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.user.fahacalculator.R;
import com.example.user.fahacalculator.presenters.CalculatorActionsPresenter;

import java.util.jar.Attributes;

public class PhoneView extends Fragment {

    CalculatorActionsPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
