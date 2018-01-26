package com.lxy.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.lxy.livedata.base.BaseApplication;
import com.lxy.livedata.databinding.ActivitySkilBinding;
import com.lxy.livedata.di.Qualifier.MainFier;
import com.lxy.livedata.di.User;
import com.lxy.livedata.di.component.DaggerMainComponent;
import com.lxy.livedata.ui.SkilAdapter;
import com.lxy.livedata.ui.entity.SkilEntity;
import com.lxy.livedata.utils.LoadingUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author a
 */
public class SkilActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    private ActivitySkilBinding mBinding;
    private SkilViewModel mViewModel;
    private SkilAdapter mAdapter;
    private List<SkilEntity> mList;
    private int mPage = 1;

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
        loadData();
    }

    public void initAdapter() {
        mList = new ArrayList<>();
        mAdapter = new SkilAdapter(R.layout.list_item_skil, mList);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.refreshLayout.setOnRefreshListener(this);
    }

    public void loadData() {
        mViewModel.loadData("Android", 15, mPage);
        subscribeData();
    }

    public void subscribeData() {

        // 数据更新时 可以收到通知
        // onstop 时 自动阻断数据流
        mViewModel.skilBean.observe(this, skilBeanResource -> {
            NetworkState status = skilBeanResource.status;
            switch (status) {
                case LOADING:
                    if (mPage == 1) {
                        LoadingUtil.showLoading(this);
                    }
                    break;
                case SUCCESS:
                    LoadingUtil.dismiss(this);
                    setList(skilBeanResource.data.results);
                    break;
                case FAILED:
                    LoadingUtil.dismiss(this);
                    break;
            }
        });
    }

    public void setList(List<SkilEntity> list) {

        if (mBinding.refreshLayout.isRefreshing()) {
            mBinding.refreshLayout.setRefreshing(false);
        }

        if (mPage == 1) {
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();

        } else {
            // adapter.loadMoreEnd(true)// 没有分页数据时
            // adapter.loadMoreEnd() //底部显示没有更多数据
            mAdapter.loadMoreComplete();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onLoadMoreRequested() {
        System.out.println("=========loadmore====");
        mPage++;
        loadData();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        loadData();
    }
}
