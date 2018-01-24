package com.lxy.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.databinding.ActivitySkilBinding;
import com.lxy.livedata.di.Qualifier.MainFier;
import com.lxy.livedata.di.User;
import com.lxy.livedata.di.component.DaggerMainComponent;

import javax.inject.Inject;

/**
 * @author a
 */
public class SkilActivity extends AppCompatActivity {

    private ActivitySkilBinding mBinding;
    private SkilViewModel mViewModel;

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
        mViewModel.loadData();

        // 数据更新时 可以收到通知
        // onstop 时 自动阻断数据流
       /* mViewModel.skilBean.observe(this, skilBean -> {

            mBinding.tvDesc.setText(skilBean.results.get(0).desc);

        });*/

        // 同上
       /* mViewModel.skilBean.observe(this, new Observer<SkilBean>() {
            @Override
            public void onChanged(@Nullable SkilBean skilBean) {
                mBinding.tvDesc.setText(skilBean.results.get(0).desc);
            }

        });*/

        mViewModel.skilBean.observe(this, new Observer<Resource<SkilBean>>() {
            @Override
            public void onChanged(@Nullable Resource<SkilBean> skilBeanResource) {

                System.out.println("status=======" + skilBeanResource.status);
            }
        });

    }
}
