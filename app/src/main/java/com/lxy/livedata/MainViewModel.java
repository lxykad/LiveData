package com.lxy.livedata;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.lxy.livedata.databinding.ActivityMainBinding;

/**
 * @author a
 * @date 2018/1/4
 */

public class MainViewModel implements LifecycleObserver {

    private Lifecycle mLifecycle;
    private ActivityMainBinding mBinding;
    private Context mContext;

    public MainViewModel(Lifecycle lifecycle, ActivityMainBinding binding) {
        mLifecycle = lifecycle;
        mBinding = binding;
        mContext = mBinding.tv.getContext();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        mBinding.tv.setText("用户中心");
        mBinding.setViewModel(new Presenter());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory() {
    }

    public class Presenter {

        public void clickToSkil(View view) {
            Intent intent = new Intent(mContext, SkilActivity.class);
            mContext.startActivity(intent);
        }
    }
}
