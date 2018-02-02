package com.lxy.livedata.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lxy.livedata.Resource;
import com.lxy.livedata.SkilActivity;
import com.lxy.livedata.SkilBean;
import com.lxy.livedata.api.ApiService;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.db.ArticleDatabase;
import com.lxy.livedata.db.SkillEntityDao;
import com.lxy.livedata.di.component.DaggerMainComponent;
import com.lxy.livedata.rx.RxHttpResponse;
import com.lxy.livedata.ui.entity.SkilEntity;
import com.lxy.livedata.utils.DateUtil;

import java.util.Date;
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

    public LiveData<List<SkilEntity>> getDBdata(int startCount) {

        LiveData<List<SkilEntity>> skillList = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                .getSkillDao()
                .getPageList(startCount);

        return skillList;
    }

    /**
     * 统一处理的错误
     */
    public MediatorLiveData<Resource<List<SkilEntity>>> getDataList(String type, int count, int page) {

        if (NetworkUtils.isConnected()) {
            return updateDb(type, count, page);
        } else {
            return getLocalData(type, count, page);
        }
    }

    /**
     * 查询本地数据库数据
     *
     * @param type
     * @param count
     * @param page
     * @return
     */
    public MediatorLiveData<Resource<List<SkilEntity>>> getLocalData(String type, int count, int page) {
        int startCount = (page - 1) * 15;
        LiveData<List<SkilEntity>> dBdata = getDBdata(startCount);

        MediatorLiveData<Resource<List<SkilEntity>>> liveData = new MediatorLiveData<>();

        dBdata.observe(SkilActivity.getInstance(), new android.arch.lifecycle.Observer<List<SkilEntity>>() {
            @Override
            public void onChanged(@Nullable List<SkilEntity> skilEntities) {
                liveData.setValue(Resource.success(skilEntities));
                SkillEntityDao skillDao = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                        .getSkillDao();
                System.out.println("db=====list1==" + liveData.getValue().data.size());
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        skillDao.deleteAllEntity(skilEntities);
                    }
                }).start();*/

            }
        });

        return liveData;
    }


    /**
     * 查询网络数据
     *
     * @param type
     * @param count
     * @param page
     * @return
     */
    public MediatorLiveData<Resource<List<SkilEntity>>> updateDb(String type, int count, int page) {

        SkillEntityDao skillDao = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                .getSkillDao();

        MediatorLiveData<Resource<List<SkilEntity>>> liveData = new MediatorLiveData<>();

        mApiService.loadList(type, count, page)
                .compose(RxHttpResponse.handResult())
                .subscribe(new Observer<List<SkilEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        liveData.setValue(Resource.loading());
                    }

                    @Override
                    public void onNext(List<SkilEntity> list) {
                        liveData.postValue(Resource.success(list));

                        for (SkilEntity entity : list) {

                            Date date = DateUtil.parseDate(entity.publishedAt);
                            long l = TimeUtils.date2Millis(date);
                            entity.sortDate = l;

                            skillDao.addEntity(entity);
                            // System.out.println("db=====long==" + l);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.setValue(Resource.error(e));
                        System.out.println("db=====err==");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return liveData;
    }

}
