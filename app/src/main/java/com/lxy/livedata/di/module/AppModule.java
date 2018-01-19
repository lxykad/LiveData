package com.lxy.livedata.di.module;

import com.google.gson.Gson;


import dagger.Module;
import dagger.Provides;

/**
 *
 * @author a
 * @date 2018/1/17
 */

@Module
public class AppModule {

    @Provides
    public Gson provideGson(){
        return new Gson();
    }
}
