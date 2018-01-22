package com.lxy.livedata.base;

import android.app.Application;
import android.util.Log;

import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.common.HttpHelper;
import com.lxy.livedata.di.component.AppComponent;
import com.lxy.livedata.di.component.DaggerAppComponent;
import com.lxy.livedata.di.component.DaggerMainComponent;
import com.lxy.livedata.di.component.MainComponent;
import com.lxy.livedata.di.module.AppModule;
import com.lxy.livedata.di.module.MainModule;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author a
 * @date 2018/1/5
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static ApiService mApiService;

    private AppComponent mAppComponent;
    private static MainComponent mMainComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        mMainComponent = DaggerMainComponent.builder().appComponent(mAppComponent).mainModule(new MainModule()).build();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static ApiService getApiService() {
        if (mApiService == null) {

            mApiService = new Retrofit.Builder()
                    .client(getOkhttpClient().build())
                    .baseUrl(HttpHelper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(ApiService.class);
        }

        return mApiService;
    }

    public static OkHttpClient.Builder getOkhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("http_response========", message);
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return builder;
    }

    public static MainComponent getMainComponent() {
        return mMainComponent;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
