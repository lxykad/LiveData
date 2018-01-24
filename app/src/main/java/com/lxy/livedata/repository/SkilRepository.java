package com.lxy.livedata.repository;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.lxy.livedata.Resource;
import com.lxy.livedata.SkilBean;
import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.di.component.DaggerMainComponent;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    public SkilRepository (){

        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .injectSkilRep(this);
    }

    public LiveData<SkilBean> getSkilData() {

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

        return data;
    }

    public MediatorLiveData<Resource<SkilBean>> getRxData(){

        MutableLiveData<SkilBean> data = new MutableLiveData<>();
        MediatorLiveData<Resource<SkilBean>> liveData = new MediatorLiveData<>();

        mApiService.loadData("Android", 1, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SkilBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        liveData.setValue(Resource.loading());

                    }

                    @Override
                    public void onNext(SkilBean skilBean) {
                       // data.setValue(skilBean);
                        liveData.setValue(Resource.success(skilBean));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return liveData;

       // mApiService.loadData("Android", 1, 2)


    }
}
