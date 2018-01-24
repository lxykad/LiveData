package com.lxy.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.lxy.livedata.repository.SkilRepository;

/**
 * @author a
 * @date 2018/1/4
 */

public class SkilViewModel extends ViewModel {

    public MediatorLiveData<Resource<SkilBean>> skilBean;
    public SkilRepository skilRepository;

    public LiveData<Resource> mDataState;

    public SkilViewModel() {

    }

    public SkilViewModel(SkilRepository repository) {
        System.out.println("=========SkilViewModel====");
    }

    public void loadData() {
        if (skilBean != null) {
            return;
        }
        skilRepository = new SkilRepository();
        skilBean = skilRepository.getRxData();
    }

    public MediatorLiveData<Resource<SkilBean>> getSkilBean() {

        return skilBean;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        System.out.println("=========clear====");
    }
}
