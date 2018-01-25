package com.lxy.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.databinding.ActivitySkilBinding;
import com.lxy.livedata.db.ArticleDatabase;
import com.lxy.livedata.di.Qualifier.MainFier;
import com.lxy.livedata.di.User;
import com.lxy.livedata.di.component.DaggerMainComponent;
import com.lxy.livedata.ui.SkilAdapter;
import com.lxy.livedata.ui.entity.SkilEntity;
import com.lxy.livedata.utils.LoadingUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.transform.Source;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author a
 */
public class SkilActivity extends AppCompatActivity {

    private ActivitySkilBinding mBinding;
    private SkilViewModel mViewModel;
    private SkilAdapter mAdapter;
    private List<SkilEntity> mList;

    @Inject
    @MainFier("no_params")
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_skil);

        DaggerMainComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .injectSkil(this);

        mViewModel = ViewModelProviders.of(this).get(SkilViewModel.class);

        initAdapter();

        mViewModel.loadData();

        subscribeData();
    }

    public void initAdapter() {
        mList = new ArrayList<>();
        mAdapter = new SkilAdapter(R.layout.list_item_skil, mList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    public void subscribeData() {

        // 数据更新时 可以收到通知
        // onstop 时 自动阻断数据流
        mViewModel.skilBean.observe(this, skilBeanResource -> {
            NetworkState status = skilBeanResource.status;
            switch (status) {
                case LOADING:
                    LoadingUtil.showLoading(this);
                    break;
                case SUCCESS:
                    LoadingUtil.dismiss(this);
                    mAdapter.addData(skilBeanResource.data.results);
                    get(null);
                    break;
                case FAILED:
                    LoadingUtil.dismiss(this);
                    break;
            }
        });

    }

    public void get(List<SkilEntity> list) {

        new Thread(new Runnable() {
            @Override
            public void run() {
              /*  ArticleDatabase.getInstance(SkilActivity.this)
                        .getSkillDao()
                        .addEntityList(list);*/

/*
                List<SkilEntity> skillList = ArticleDatabase.getInstance(SkilActivity.this)
                        .getSkillDao()
                        .getSkillList();


                ArticleDatabase.getInstance(SkilActivity.this)
                        .getSkillDao()
                        .deleteAllEntity(skillList);*/

                SkilEntity entity = new SkilEntity();
                entity.desc = "1";

                List<SkilEntity> skillList = ArticleDatabase.getInstance(SkilActivity.this)
                        .getSkillDao()
                        .getSkillList();

                System.out.println("size=========" + skillList.size());

            }
        }).start();
    }
}
