package com.lxy.livedata.di.component;

import com.lxy.livedata.MainActivity;
import com.lxy.livedata.SkilActivity;
import com.lxy.livedata.di.module.MainModule;
import com.lxy.livedata.di.scope.ActivityScope;
import com.lxy.livedata.repository.SkilRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author a
 * @date 2018/1/17
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);

    void injectSkil(SkilActivity activity);

    void injectSkilRep(SkilRepository skilRepository);
}
