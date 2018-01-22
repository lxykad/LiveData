package com.lxy.livedata.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.lxy.livedata.SkilBean;
import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.di.component.DaggerMainComponent;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a
 * @date 2018/1/5
 * Repository模块负责处理数据操作
 */

public class SkilRepository {

    @Inject
    ApiService mApiService;

    public LiveData<SkilBean> getSkilData() {

        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .injectSkilRep(this);

        // 暂时这样不优雅的实现 以后修改
        MutableLiveData<SkilBean> data = new MutableLiveData<>();

        mApiService
                .loadSkilData("Android", 1, 2)
                .enqueue(new Callback<SkilBean>() {
                    @Override
                    public void onResponse(Call<SkilBean> call, Response<SkilBean> response) {
                        data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<SkilBean> call, Throwable t) {

                    }

                });
        System.out.println("1111====api====" + mApiService);

        return data;
    }
}
