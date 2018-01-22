package com.lxy.livedata;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.databinding.ActivityMainBinding;
import com.lxy.livedata.di.Qualifier.MainFier;
import com.lxy.livedata.di.User;
import com.lxy.livedata.di.component.DaggerMainComponent;
import com.lxy.livedata.di.module.MainModule;

import javax.inject.Inject;

/**
 * @author a
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Inject
    @MainFier("params")
    User mUser;

    @Inject
    @MainFier("params")
    User mUser2;

    @Inject
    BaseApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getLifecycle().addObserver(new MainViewModel(getLifecycle(), mBinding));

       // BaseApplication.getMainComponent().inject(this);
        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .mainModule(new MainModule())
                .build()
                .inject(this);


        //   System.out.println("1111====main====" + mUser.name);
        System.out.println("1111===app====" + application);

    }
}
