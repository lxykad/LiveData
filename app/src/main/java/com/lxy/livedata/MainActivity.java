package com.lxy.livedata;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lxy.livedata.databinding.ActivityMainBinding;

/**
 * @author a
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getLifecycle().addObserver(new MainViewModel(getLifecycle(), mBinding));
        
    }
}
