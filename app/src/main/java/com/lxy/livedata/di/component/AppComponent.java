package com.lxy.livedata.di.component;

import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.di.module.AppModule;

import dagger.Component;

/**
 * @author a
 * @date 2018/1/17
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    BaseApplication getApplication();
}
