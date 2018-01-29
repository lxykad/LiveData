package com.lxy.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.lxy.livedata.repository.SkilRepository;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.List;

/**
 * @author a
 * @date 2018/1/4
 */

public class SkilViewModel extends ViewModel {

    public MediatorLiveData<Resource<List<SkilEntity>>> skilBean;
    public MediatorLiveData<Resource<SkilBean>> liveDataBean;
    public SkilRepository skilRepository;

    public LiveData<Resource> mDataState;
    public LiveData<List<SkilEntity>> mList;

    public SkilViewModel() {

    }

    public SkilViewModel(SkilRepository repository) {
        System.out.println("=========SkilViewModel====");
    }

    public void loadData(String type, int count, int page) {
        if (skilBean != null) {
            return;
        }
        skilRepository = new SkilRepository();
       // skilBean = skilRepository.getDataList(type, count, page);
        liveDataBean = skilRepository.getRxData(type,count,page);
    }

    public MediatorLiveData<Resource<List<SkilEntity>>> getSkilBean() {

        return skilBean;
    }

    public MediatorLiveData<Resource<SkilBean>> getLiveDataBean() {

        return liveDataBean;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        System.out.println("=========clear====");
    }
}
