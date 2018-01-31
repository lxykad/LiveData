package com.lxy.livedata.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;
import android.view.animation.BounceInterpolator;

import com.lxy.livedata.Resource;
import com.lxy.livedata.SkilActivity;
import com.lxy.livedata.SkilBean;
import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.db.ArticleDatabase;
import com.lxy.livedata.db.SkillEntityDao;
import com.lxy.livedata.di.User;
import com.lxy.livedata.di.component.DaggerMainComponent;
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

    /**
     * 未处理
     */
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

    /**
     * 统一处理的错误
     */
    public MediatorLiveData<Resource<List<SkilEntity>>> getDataList(String type, int count, int page) {

        LiveData<List<SkilEntity>> dBdata = getDBdata();

        MediatorLiveData<Resource<List<SkilEntity>>> liveData = new MediatorLiveData<>();

        dBdata.observe(SkilActivity.getInstance(), new android.arch.lifecycle.Observer<List<SkilEntity>>() {
            @Override
            public void onChanged(@Nullable List<SkilEntity> skilEntities) {
                liveData.setValue(Resource.success(skilEntities));
                SkillEntityDao skillDao = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                        .getSkillDao();
                System.out.println("db=====list1==" + liveData.getValue().data.size());
            }
        });

        // 更新数据库
      //  updateDb(type, count, page);

        return liveData;
    }

    public void updateDb(String type, int count, int page) {

        SkillEntityDao skillDao = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                .getSkillDao();

        MediatorLiveData<Resource<List<SkilEntity>>> liveData = new MediatorLiveData<>();

        mApiService.loadList(type, count, page)
                .compose(RxHttpResponse.handResult())
                .subscribe(new Observer<List<SkilEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  liveData.setValue(Resource.loading());
                    }

                    @Override
                    public void onNext(List<SkilEntity> list) {
                        // liveData.setValue(Resource.success(list));
                      //  System.out.println("db=====success==");
                       //  skillDao.addEntityList(list);
                        for (SkilEntity entity : list) {
                            skillDao.addEntity(entity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  liveData.setValue(Resource.error(e));
                        System.out.println("db=====err==");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
