package com.lxy.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lxy.livedata.databinding.ActivitySkilBinding;

/**
 * @author a
 */
public class SkilActivity extends AppCompatActivity {

    private ActivitySkilBinding mBinding;
    private SkilViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skil);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_skil);


        mViewModel = ViewModelProviders.of(this).get(SkilViewModel.class);
        mViewModel.loadData();

        mViewModel.skilBean.observe(this, skilBean -> {

            mBinding.tvDesc.setText(skilBean.results.get(0).desc);
        });
    }
}
