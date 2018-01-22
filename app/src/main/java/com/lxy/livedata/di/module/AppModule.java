package com.lxy.livedata.di.module;

import com.lxy.livedata.base.BaseApplication;

import dagger.Module;
import dagger.Provides;

/**
 *
 * @author a
 * @date 2018/1/17
 */

@Module
public class AppModule {

    private BaseApplication mApplication;

    public AppModule(BaseApplication application){
        mApplication = application;

    }

    @Provides
    public BaseApplication provideApplication(){
        return mApplication;
    }

}
