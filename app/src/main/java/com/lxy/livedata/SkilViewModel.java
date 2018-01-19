package com.lxy.livedata;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.lxy.livedata.repository.SkilRepository;

/**
 * @author a
 * @date 2018/1/4
 */

public class SkilViewModel extends ViewModel implements LifecycleObserver{

    public LiveData<SkilBean> skilBean;
    public SkilRepository skilRepository;

    public void loadData() {
        if (skilBean != null) {
            return;
        }
        skilRepository = new SkilRepository();
        skilBean = skilRepository.getSkilData();
    }

    public LiveData<SkilBean> getSkilBean() {

        return skilBean;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        System.out.println("=========clear====");
    }
}
