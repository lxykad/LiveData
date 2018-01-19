package com.lxy.livedata.di.component;

import com.lxy.livedata.MainActivity;
import com.lxy.livedata.SkilActivity;
import com.lxy.livedata.di.module.MainModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author a
 * @date 2018/1/17
 */

@Singleton
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);

    void injectSkil(SkilActivity activity);
}
