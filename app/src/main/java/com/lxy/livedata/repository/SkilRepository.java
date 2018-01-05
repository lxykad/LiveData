package com.lxy.livedata.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.lxy.livedata.SkilBean;
import com.lxy.livedata.base.BaseApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a
 * @date 2018/1/5
 * Repository模块负责处理数据操作
 */

public class SkilRepository {

    public LiveData<SkilBean> getSkilData() {

        MutableLiveData<SkilBean> data = new MutableLiveData<>();

        BaseApplication.getApiService()
                .loadSkilData("Android", 1, 1)
                .enqueue(new Callback<SkilBean>() {
                    @Override
                    public void onResponse(Call<SkilBean> call, Response<SkilBean> response) {
                        data.setValue(response.body());
                        System.out.println("1111============setdata");
                    }

                    @Override
                    public void onFailure(Call<SkilBean> call, Throwable t) {

                    }
                });

        System.out.println("1111============returndata");
        return data;
    }
}
