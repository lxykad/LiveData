package com.lxy.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SkilActivity extends AppCompatActivity {

    private SkilViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skil);


        mViewModel = ViewModelProviders.of(this).get(SkilViewModel.class);
        mViewModel.setData("haha");
    }
}
