package com.lxy.livedata.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.lxy.livedata.ListBean;
import com.lxy.livedata.Resource;
import com.lxy.livedata.SkilBean;
import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.db.ArticleDatabase;
import com.lxy.livedata.di.component.DaggerMainComponent;
import com.lxy.livedata.rx.BaseBean;
import com.lxy.livedata.rx.RxHttpResponse;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author a
 * @date 2018/1/5
 * Repository模块负责处理数据操作
 */

public class SkilRepository {

    @Inject
    ApiService mApiService;

    public SkilRepository() {

        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .injectSkilRep(this);
    }

    public MediatorLiveData<Resource<SkilBean>> getRxData(String type, int count, int page) {

        MediatorLiveData<Resource<SkilBean>> liveData = new MediatorLiveData<>();

        mApiService.loadData(type, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SkilBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        liveData.setValue(Resource.loading());

                    }

                    @Override
                    public void onNext(SkilBean skilBean) {
                        liveData.setValue(Resource.success(skilBean));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(Resource.error(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return liveData;
    }

    public LiveData<List<SkilEntity>> getDBdata() {

        LiveData<List<SkilEntity>> skillList = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                .getSkillDao()
                .getSkillList();

        return skillList;
    }

    public MediatorLiveData<Resource<SkilBean>> updateData() {


        return null;
    }

    public MediatorLiveData<Resource<List<SkilEntity>>> getDataList(String type, int count, int page) {

        MediatorLiveData<Resource<List<SkilEntity>>> liveData = new MediatorLiveData<>();

        mApiService.loadList(type,count,page)
                .compose(RxHttpResponse.handResult())
                .subscribe(new Observer<List<SkilEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        liveData.setValue(Resource.loading());
                    }

                    @Override
                    public void onNext(List<SkilEntity> list) {
                        liveData.setValue(Resource.success(list));
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(Resource.error(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return liveData;
    }

}
