package com.lxy.livedata.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.Nullable;

import com.lxy.livedata.Resource;
import com.lxy.livedata.SkilActivity;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.db.ArticleDatabase;
import com.lxy.livedata.db.SkillEntityDao;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

/**
 * @author a
 * @date 2018/2/2
 */

public class LocalDataSource implements SkilDataSource {

    private static final LocalDataSource instance = new LocalDataSource();

    private LocalDataSource() {

    }

    public static LocalDataSource getInstance() {
        return instance;
    }

    @Override
    public MediatorLiveData<Resource<List<SkilEntity>>> getData(String type, int count, int page) {
        int startCount = (page - 1) * 15;
        LiveData<List<SkilEntity>> dBdata = getDBdata(startCount);

        MediatorLiveData<Resource<List<SkilEntity>>> liveData = new MediatorLiveData<>();

        dBdata.observe(SkilActivity.getInstance(), new android.arch.lifecycle.Observer<List<SkilEntity>>() {
            @Override
            public void onChanged(@Nullable List<SkilEntity> skilEntities) {
                liveData.setValue(Resource.success(skilEntities));
                SkillEntityDao skillDao = ArticleDatabase.getInstance(BaseApplication.getInstance().getApplicationContext())
                        .getSkillDao();
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
}
